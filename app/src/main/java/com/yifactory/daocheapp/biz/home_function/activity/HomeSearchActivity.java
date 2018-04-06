package com.yifactory.daocheapp.biz.home_function.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.retrofit.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.yifactory.daocheapp.widget.flowLayout.FlowLayout;
import com.yifactory.daocheapp.widget.flowLayout.TagAdapter;
import com.yifactory.daocheapp.widget.flowLayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeSearchActivity extends BaseActivity {

    @BindView(R.id.search_et)
    EditText mEt_search;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    List<String> tagList = new ArrayList<>();
    SPreferenceUtil mEightModuleFcIdSp;
    TagAdapter<String> adapter;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        mEightModuleFcIdSp = new SPreferenceUtil(HomeSearchActivity.this, "eightModuleFcId.sp");
        tagList = mEightModuleFcIdSp.getSearch();
        initFlowLayout();
    }

    private void initFlowLayout() {
        adapter = new TagAdapter<String>(tagList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(HomeSearchActivity.this).inflate(R.layout.layout_home_search_tag,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlowLayout.setAdapter(adapter);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                startActivity(new Intent(HomeSearchActivity.this, HomeSearchJieGuo.class).putExtra("content", tagList.get(position)));
                return true;
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @OnClick({R.id.back_layout, R.id.search_tv, R.id.activity_home_search_clear})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.back_layout:
                    finish();
                    break;
                case R.id.search_tv:
                    if (TextUtils.isEmpty(mEt_search.getText().toString())) {
                        ToastUtils.showToast("请输入搜索内容");
                        return;
                    }
                    Boolean isCheck = false;
                    for (int i = 0; i < tagList.size(); i++) {
                        if (tagList.get(i).equals(mEt_search.getText().toString())) {
                            isCheck = true;
                        }
                    }
                    if (!isCheck) {
                        tagList.add(mEt_search.getText().toString());
                        mEightModuleFcIdSp.setSearch(tagList);
                    }
                    adapter.notifyDataChanged();
                    startActivity(new Intent(HomeSearchActivity.this, HomeSearchJieGuo.class).putExtra("content", mEt_search.getText().toString()));
                    break;
                case R.id.activity_home_search_clear:
                    tagList.clear();
                    mEightModuleFcIdSp.setSearch(tagList);
                    adapter.notifyDataChanged();
                    break;
            }
        }
    }
}
