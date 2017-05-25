package com.logapp.log.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.Toast;

import com.logapp.log.PlantformManager;
import com.logapp.log.R;
import com.logapp.log.adapter.LogFileAdapter;
import com.logapp.log.services.LogService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {
    private static final String TAG = "LogActivity";
    private RecyclerView rvLog;
    private LogFileAdapter fileAdapter;
    private long mExitTime = 0;
    private long mSpaceTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();
    }

    private void initView() {
        rvLog = (RecyclerView) findViewById(R.id.rvLog);
    }

    private void initDate() {
        List<File> fileList = getLogFiles();
        if (null == fileList || fileList.isEmpty()) {
            return;
        }
        fileAdapter = new LogFileAdapter(LogActivity.this, getLogFiles());
        rvLog.setLayoutManager(new LinearLayoutManager(this));
        rvLog.setAdapter(fileAdapter);
    }

    private List<File> getLogFiles() {
        List<File> fileList = new ArrayList<>();
        String folder = PlantformManager.getInstance(this).getLogPath();
        File file = new File(folder);
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (File f : childFiles) {
                fileList.add(f);
            }
        }
        return fileList;
    }

    private void stopLogService() {
        Intent intent = new Intent(this, LogService.class);
        stopService(intent);
    }

    /**
     * 退出程序
     */
    private void exitApp() {
        if ((System.currentTimeMillis() - mExitTime) > mSpaceTime) {
            Toast.makeText(this, "再按一次结束监控并退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            stopLogService();
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
