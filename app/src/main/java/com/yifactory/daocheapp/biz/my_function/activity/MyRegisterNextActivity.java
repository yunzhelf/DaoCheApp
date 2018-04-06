package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.RegisterBean;
import com.yifactory.daocheapp.utils.AddressPickTask;
import com.yifactory.daocheapp.utils.ButtonDialogFragment;
import com.yifactory.daocheapp.utils.DataCleanUtils;
import com.yifactory.daocheapp.utils.OssUploadImgUtil;
import com.yifactory.daocheapp.utils.OssServerUtil;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.yifactory.daocheapp.widget.addressSelected.entity.City;
import com.yifactory.daocheapp.widget.addressSelected.entity.County;
import com.yifactory.daocheapp.widget.addressSelected.entity.Province;
import com.yifactory.daocheapp.widget.flowLayout.FlowLayout;
import com.yifactory.daocheapp.widget.flowLayout.TagAdapter;
import com.yifactory.daocheapp.widget.flowLayout.TagFlowLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class MyRegisterNextActivity extends BaseActivity {

    @BindView(R.id.rootLayout)
    AutoLinearLayout rootLayout;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.company_name_et)
    EditText companyNameEt;
    @BindView(R.id.now_area_et)
    EditText nowAreaEt;
    @BindView(R.id.address_tv)
    TextView mTv_address;
    @BindView(R.id.job_area_tv)
    TextView jobAreaTv;
    @BindView(R.id.job_time_et)
    EditText jobTimeEt;
    @BindView(R.id.area_view)
    View mV_area;

    private String[] tagArray = {"运营管理", "销售管理", "市场营销", "衍生业务", "客户维护", "售后管理", "人事行政", "财务管理"};
    private String phone;
    private String verifyCode;
    private String pwd;
    private String mName;
    private String mSex;
    private String areaType;
    private String trainAttention;
    private String userHeadImgUrl;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    File file = new File(Environment.getExternalStorageDirectory() + "/daocheuser_head.jpg");
                    if (file.exists()) {
                        file.delete();
                    }
                    registerEventFinish();
                    break;
                case 500:
                    closeDialog();
                    int mark = msg.arg1;
                    if (mark == 1) {
                        showToast("网络异常");
                    } else if (mark == 2) {
                        showToast("服务器异常，请稍后再试");
                    }
                    break;
            }
        }
    };
    private Dialog mDialog;

    private void registerEventFinish() {
        String headImg = "http://"+ OssUploadImgUtil.USER_HEAD_BUKET+".oss-cn-beijing.aliyuncs.com/" + userHeadImgUrl;
        RxHttpUtils.createApi(ApiService.class)
                .addUser(phone, verifyCode, pwd, "", mCompanyNameStr, mJobNowStr
                        , Integer.valueOf(mJobTimeStr), areaType, trainAttention, mAddressStr, mSex, mName, headImg)
                .compose(Transformer.<RegisterBean>switchSchedulers())
                .subscribe(new CommonObserver<RegisterBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        closeDialog();
                    }

                    @Override
                    protected void onSuccess(RegisterBean userBean) {
                        closeDialog();
                        showToast("注册成功");
                        finish();
                    }
                });
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private String mCompanyNameStr;
    private String mJobNowStr;
    private String mAddressStr;
    private String mJobTimeStr;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("注册");
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        initFlowLayout();
    }

    private void initFlowLayout() {

        mFlowLayout.setAdapter(new TagAdapter<String>(Arrays.asList(tagArray)) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(MyRegisterNextActivity.this).inflate(R.layout.layout_my_userinfo_needs,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                trainAttention = "";
                if (selectPosSet.size() > 0) {
                    for (Integer integer : selectPosSet) {
                        trainAttention += tagArray[integer] + ",";
                    }
                    trainAttention = trainAttention.substring(0, trainAttention.length() - 1);
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        String sex = intent.getStringExtra("sex");
        if (sex.equals("男")) {
            mSex = "1";
        } else {
            mSex = "2";
        }
        phone = intent.getStringExtra("phone");
        verifyCode = intent.getStringExtra("verifyCode");
        pwd = intent.getStringExtra("pwd");
    }

    @OnClick({R.id.naviFrameLeft, R.id.finish_btn, R.id.address_layout, R.id.job_area_layout})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    giveUpOperate();
                    break;
                case R.id.finish_btn:
                    registerEvent();
                    break;
                case R.id.address_layout:
                    selectLocation();
                    break;
                case R.id.job_area_layout:
                    selectTypeEvent();
                    break;
            }
        }
    }

    private CustomPopWindow mCustomPopWindowArea;

    private void selectTypeEvent() {
        if (mCustomPopWindowArea == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.pop_register_area_select_layout, null);
            final TextView oneTv = (TextView) contentView.findViewById(R.id.one_tv);
            final TextView twoTv = (TextView) contentView.findViewById(R.id.two_tv);
            final TextView threeTv = (TextView) contentView.findViewById(R.id.three_tv);
            final TextView fourTv = (TextView) contentView.findViewById(R.id.four_tv);
            final TextView fiveTv = (TextView) contentView.findViewById(R.id.five_tv);
            final TextView sixTv = (TextView) contentView.findViewById(R.id.six_tv);
            mCustomPopWindowArea = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .create()
                    .showAtLocation(rootLayout, Gravity.CENTER, 0, 0);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindowArea != null) {
                        mCustomPopWindowArea.dissmiss();
                    }
                    switch (v.getId()) {
                        case R.id.one_tv:
                            areaType = "4S店";
                            jobAreaTv.setText("4S店");
                            oneTv.setTextColor(Color.parseColor("#4087fd"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.two_tv:
                            areaType = "二手车";
                            jobAreaTv.setText("二手车");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#4087fd"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.three_tv:
                            areaType = "汽车超市";
                            jobAreaTv.setText("汽车超市");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#4087fd"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.four_tv:
                            areaType = "维修店";
                            jobAreaTv.setText("维修店");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#4087fd"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.five_tv:
                            areaType = "职业老师";
                            jobAreaTv.setText("职业老师");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#4087fd"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.six_tv:
                            areaType = "媒体人";
                            jobAreaTv.setText("媒体人");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#4087fd"));
                            break;
                    }
                }
            };
            oneTv.setOnClickListener(listener);
            twoTv.setOnClickListener(listener);
            threeTv.setOnClickListener(listener);
            fourTv.setOnClickListener(listener);
            fiveTv.setOnClickListener(listener);
            sixTv.setOnClickListener(listener);
        } else {
            mCustomPopWindowArea
                    .showAtLocation(rootLayout, Gravity.CENTER, 0, 0);
        }
    }

    private void selectLocation() {
        AddressPickTask task = new AddressPickTask(this);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                mTv_address.setText(province.getAreaName() + " " + city.getAreaName() + " " + county.getAreaName());
            }
        });
        task.execute("辽宁", "沈阳", "浑南新区");
    }

    private void registerEvent() {
        mCompanyNameStr = companyNameEt.getText().toString();
        if (TextUtils.isEmpty(mCompanyNameStr)) {
            showToast("请输入公司名称");
            return;
        }
        mJobNowStr = nowAreaEt.getText().toString();
        if (TextUtils.isEmpty(mJobNowStr)) {
            showToast("请输入职位");
            return;
        }
        mAddressStr = mTv_address.getText().toString().trim();
        if (TextUtils.isEmpty(mAddressStr)) {
            showToast("请选择所在区域");
            return;
        }
        if (TextUtils.isEmpty(areaType)) {
            showToast("请选择行业属性");
            return;
        }
        mJobTimeStr = jobTimeEt.getText().toString().trim();
        if (TextUtils.isEmpty(mJobTimeStr)) {
            showToast("请输入从业年限");
            return;
        }
        if (TextUtils.isEmpty(trainAttention)) {
            showToast("请选择个人需求提升点");
            return;
        }
        uploadHeadToALiYunServer();
    }

    private void uploadHeadToALiYunServer() {
        if (mDialog == null) {
            mDialog = SDDialogUtil.newLoading(this, "请求中...");
        }
//        File headFile = new File(Environment.getExternalStorageDirectory().toString(), "/daocheuser_head.jpg");
        userHeadImgUrl = UUID.randomUUID().toString() + ".jpg";
        PutObjectRequest put = new PutObjectRequest(OssUploadImgUtil.USER_HEAD_BUKET, userHeadImgUrl, Environment.getExternalStorageDirectory().toString() + "/daocheuser_head.jpg");
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = OssServerUtil.getSingleTon().oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                /*Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());*/
                Message msg = Message.obtain();
                msg.what = 200;
                handle.sendMessage(msg);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                Message msg = Message.obtain();
                if (clientExcepion != null) {
                    msg.arg1 = 1;
                }
                if (serviceException != null) {
                    msg.arg1 = 2;
                }
                msg.what = 500;
                handle.sendMessage(msg);
            }
        });
        // task.cancel(); // 可以取消任务
// task.waitUntilFinished(); // 可以等待任务完成
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_register_next;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            giveUpOperate();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void giveUpOperate() {
        ButtonDialogFragment buttonDialogFragment = new ButtonDialogFragment();
        buttonDialogFragment.show("提示：", "是否放弃本次编辑？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, getSupportFragmentManager());
    }
}
