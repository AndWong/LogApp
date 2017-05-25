package com.logapp.log.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.logapp.log.R;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.io.File;
import java.util.List;

/**
 * Log文件适配器
 * Created by wong on 17-5-16.
 */
public class LogFileAdapter extends BaseQuickAdapter<File, BaseViewHolder> {
    private Context mContext;

    public LogFileAdapter(Context context, List<File> data) {
        super(R.layout.item_file, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final File item) {
        TextView name = helper.getView(R.id.txName);
        TextView flag = helper.getView(R.id.txFlag);
        name.setText(item.getParentFile().getName() + File.separator + item.getName());
        if ((getData().size() - 1) == getData().indexOf(item)) {
            flag.setVisibility(View.VISIBLE);
            ((SwipeMenuLayout) helper.getConvertView().findViewById(R.id.swLayout)).setSwipeEnable(false);
        } else {
            flag.setVisibility(View.GONE);
        }
        helper.setOnClickListener(R.id.rlLog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri fileUri = Uri.fromFile(item);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
                shareIntent.setType("*/*");
                mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });
        helper.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getData().indexOf(item);
                if (getData().size() - 1 != index) {
                    remove(index);
                    notifyDataSetChanged();
                    item.delete();
                }
            }
        });
    }
}
