package com.logapp.log.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.logapp.log.services.LogService;
import com.logapp.log.R;

/**
 * 透明主页
 * Created by wong on 17-5-16.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent);
        startLogService();
        finish();
    }

    /**
     * 开始日志的记录
     */
    private void startLogService() {
        Intent intent = new Intent(this, LogService.class);
        startService(intent);
    }
}
