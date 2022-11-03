package com.smartxin.basemodule.ui.picture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.gyf.immersionbar.ImmersionBar;
import com.smartxin.basemodule.R;
import com.smartxin.basemodule.base.BaseDbActivity;
import com.smartxin.basemodule.databinding.ActivityShowPictureBinding;
import com.smartxin.basemodule.utils.StringUtils;


public class ShowPictureActivity extends BaseDbActivity<ActivityShowPictureBinding> {

    private String url = "";

    public static void StartShowPicture(Context activity, String url) {

        Intent intent = new Intent(activity, ShowPictureActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.tbBase.tvPageTitle.setText("查看图片");

        ImmersionBar.with(this).titleBar(mBinding.tbBase.toolbar).init();
        PhotoView photoView = findViewById(R.id.photo_view);

        url = getIntent().getStringExtra("url");
        if (!StringUtils.isEmpty(url)) {
            Glide.with(ShowPictureActivity.this)
                    .load(url)
                    .into(photoView);
        }
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_show_picture;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}