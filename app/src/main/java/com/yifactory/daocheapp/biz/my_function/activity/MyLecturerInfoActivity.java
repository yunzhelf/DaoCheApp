package com.yifactory.daocheapp.biz.my_function.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.TeacherBean;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MyLecturerInfoActivity extends BaseActivity {
    public static int TEACHER_INFO = 0;
    @BindView(R.id.photo_iv)
    ImageView photoIv;
    @BindView(R.id.creator_tv)
    TextView createrTv;
    @BindView(R.id.teacher_introduce_tv)
    TextView introduceTv;
    @BindView(R.id.teacher_achievement_tv)
    TextView achievementTv;
    @BindView(R.id.teacher_experience_tv)
    TextView experienceTv;

    private TeacherBean.DataBean teacher;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("讲师资料");
        bar.setRightText("编辑");
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {

    }

    private void initViewValue(){
        if(teacher != null){
            RequestOptions options = new RequestOptions().placeholder(R.drawable.lecturer_photo);
            Glide.with(this).load(teacher.getHeadImg()).apply(options).into(photoIv);
            createrTv.setText(teacher.getName()); // + " | " + teacher.getOldHonor()
            introduceTv.setText(teacher.getSelfAssessment());
            achievementTv.setText(teacher.getOldHonor());
            experienceTv.setText(teacher.getJobUbdergo());
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        getTeacherInfoEvent();

    }

    private void getTeacherInfoEvent(){
        String uId = UserInfoUtil.getUserInfoBean(this).getUId();
        if(uId == null){
            showToast("没有用户信息");
            return;
        }
        RxHttpUtils.createApi(ApiService.class)
                .getRegistTeacherInfo(uId)
                .compose(Transformer.<TeacherBean>switchSchedulers())
                .subscribe(new CommonObserver<TeacherBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(TeacherBean teacherBean) {
                        if(teacherBean != null && teacherBean.getResponseState().equals("1")){
                            teacher = teacherBean.getData().get(0);
                            initViewValue();
                        }else{
                            showToast("没有相关信息");
                            finish();
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lecturer_info;
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    Intent editLecturer = new Intent(this, MyEditLecturerInfoActivity.class);
                    editLecturer.putExtra("teacherInfo", teacher);
                    startActivityForResult(editLecturer,TEACHER_INFO);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        if(requestCode == TEACHER_INFO){
            teacher = data.getParcelableExtra("teacher");
            initViewValue();
        }
    }
}
