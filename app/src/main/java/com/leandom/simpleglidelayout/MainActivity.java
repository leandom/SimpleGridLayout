package com.leandom.simpleglidelayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

/**
 * 动态珊格实现另类实现
 */
public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    private String[] assetsFileNames = new String[]{
            "data0.txt",
            "data1.txt",
            "data2.txt",
            "data3.txt"
    };

    private int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(assetsFileNames[i++ % assetsFileNames.length]);
            }
        });
        loadData(assetsFileNames[i++ % assetsFileNames.length]);
    }

    private void loadData(String assetsName) {

        // 模拟网络请求
        DataFactory.getData(this, assetsName, new DataFactory.OnDataLoadListener<ImagesModel>() {
            @Override
            public void onSuccess(ImagesModel result) {
                showImages(result);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError() {
                // Log.e(TAG, "onError ");
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void showImages(ImagesModel imagesModel) {

        LinearLayout layoutImageRoot = (LinearLayout) findViewById(R.id.layout_image_root);
        layoutImageRoot.removeAllViews();
        layoutImageRoot.setOrientation(LinearLayout.VERTICAL);
        setDivider(layoutImageRoot);
        for (ImagesModel.Row row : imagesModel.getImages()) {
            // 新加一行
            LinearLayout rowLinearLayout = createLinearLayout();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getHeight(row.getHeight()), 0);
            layoutImageRoot.addView(rowLinearLayout, lp);
            addItemBean(rowLinearLayout, row);
        }
    }

    private void addItemBean(LinearLayout parent, ImagesModel.ItemBean itemBean) {

        LayoutInflater inflater = LayoutInflater.from(this);
        if (itemBean.hasChildren()) {
            String orientationStr = itemBean.getOrientation();
            int orientation = "v".equals(orientationStr) ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL;
            parent.setOrientation(orientation);
            setDivider(parent);
            for (ImagesModel.ItemBean childItem : itemBean.getChildren()) {
                LinearLayout childLinearLayout = createLinearLayout();
                // childLinearLayout.setOrientation(orientation);
                LinearLayout.LayoutParams lp;
                if (parent.getOrientation() == LinearLayout.VERTICAL) {
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, childItem.getWeight());
                } else {
                    lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, childItem.getWeight());
                }
                parent.addView(childLinearLayout, lp);
                // 递归
                addItemBean(childLinearLayout, childItem);
            }

        } else {
            parent.setOrientation(LinearLayout.HORIZONTAL);
            View view = inflater.inflate(R.layout.item_image, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            parent.addView(view);
            loadimage(itemBean.getImage(), imageView);
        }
    }

    private LinearLayout createLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        return linearLayout;
    }

    private void loadimage(String url, ImageView imageView) {
        Glide.with(this).load(url).error(R.drawable.error).into(imageView);
    }

    private void setDivider(LinearLayout linearLayout) {
        if (linearLayout.getOrientation() == LinearLayout.VERTICAL) {
            linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_h));
        } else {
            linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_v));
        }
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    }

    private int getHeight(int pxIn720) {
        int width = getResources().getDisplayMetrics().widthPixels;
        return pxIn720 * width / 720;
    }
}
