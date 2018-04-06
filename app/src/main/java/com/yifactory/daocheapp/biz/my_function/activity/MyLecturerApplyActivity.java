package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.yifactory.daocheapp.bean.AddTeacherRegistBean;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.biz.my_function.adapter.UploadPicAdapter;
import com.yifactory.daocheapp.event.TiXianSuccessMsg;
import com.yifactory.daocheapp.utils.AddressPickTask;
import com.yifactory.daocheapp.utils.BankCheck;
import com.yifactory.daocheapp.utils.EditTextUtil;
import com.yifactory.daocheapp.utils.OssServerUtil;
import com.yifactory.daocheapp.utils.OssUploadImgUtil;
import com.yifactory.daocheapp.utils.PictureCompressUtil;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.utils.ValidationUtil;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.yifactory.daocheapp.widget.addressSelected.entity.City;
import com.yifactory.daocheapp.widget.addressSelected.entity.County;
import com.yifactory.daocheapp.widget.addressSelected.entity.Province;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyLecturerApplyActivity extends BaseActivity implements View.OnTouchListener {
    @BindView(R.id.rootLayout)
    AutoLinearLayout mRootViewLayout;
    @BindView(R.id.pic_rv)
    RecyclerView mRv_pic;
    @BindView(R.id.working_experience_et)
    EditText mEt_workingExperience;
    @BindView(R.id.name_et)
    EditText mNameEt;
    @BindView(R.id.sex_tv)
    TextView mSexTv;
    @BindView(R.id.sex_selected_layout)
    AutoLinearLayout mSexSelectedLayout;
    @BindView(R.id.age_et)
    EditText mAgeEt;
    @BindView(R.id.address_layout)
    AutoLinearLayout mAddressLayout;
    @BindView(R.id.address_tv)
    TextView mAddressTv;
    @BindView(R.id.details_address_et)
    EditText mDetailsAddressEt;
    @BindView(R.id.phone_et)
    EditText mPhoneEt;
    @BindView(R.id.now_company_et)
    EditText mNowCompanyEt;
    @BindView(R.id.working_year_et)
    EditText mWorkingYearEt;
    @BindView(R.id.previous_honor_et)
    EditText mPreviousHonorEt;
    @BindView(R.id.field_expertise_et)
    EditText mFieldExpertiseEt;
    @BindView(R.id.introduce_myself_et)
    EditText mIntroduceMyselfEt;
    @BindView(R.id.bank_card_et)
    EditText mBankCardEt;
    @BindView(R.id.bank_name_tv)
    TextView mBankNameTv;
    @BindView(R.id.bank_adderss_et)
    EditText mBankAdderssEt;
    @BindView(R.id.real_name_et)
    EditText mRealNameEt;
    @BindView(R.id.leave_phone_et)
    EditText mLeavePhoneEt;
    @BindView(R.id.submit_btn)
    Button mSubmitBtn;
    private UploadPicAdapter mUploadPicAdapter;
    private String mUId;
    private String mNameStr;
    private String mSexMark;
    private String mAgeStr;
    private String mAddressStr;
    private String mDetailsAddressStr;
    private String mPhoneStr;
    private String mNowCompanyStr;
    private String mWorkingYearStr;
    private String mWorkingExperienceStr;
    private String mPreviousHonorStr;
    private String mFieldExpertiseStr;
    private String mIntroduceMySelfStr;
    private String mBankCardStr;
    private String mBankNameStr;
    private String mBankAddressStr;
    private String mRealNameStr;
    private String mLeavePhoneStr;
    private List<String> uploadImgUrl = new ArrayList<>();
    private int uploadSuccessNum = 0;
    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String userHeadImgUrl = (String) msg.obj;
            switch (msg.what) {
                case 200:
                    uploadSuccessNum++;
                    String imgUrlStr = "http://" + OssUploadImgUtil.USER_HEAD_BUKET + ".oss-cn-beijing.aliyuncs.com/" + userHeadImgUrl;
                    uploadImgUrl.add(imgUrlStr);
                    break;
                case 500:
                    cancelDialog();
                    int mark = msg.arg1;
                    if (mark == 1) {
                        showToast("网络异常");
                    } else if (mark == 2) {
                        showToast("服务器异常");
                    }
                    finish();
                    break;
            }
            if (uploadSuccessNum == realPicUrlList.size()) {
                requestServer();
            }
        }
    };

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("讲师申请");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void addListener() {
        mEt_workingExperience.setOnTouchListener(this);
        mPreviousHonorEt.setOnTouchListener(this);
        mFieldExpertiseEt.setOnTouchListener(this);
        mIntroduceMyselfEt.setOnTouchListener(this);

        mBankCardEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String contentStr = s.toString();
                if (contentStr.length() == 6) {
                    String nameOfBank = BankCheck.getNameOfBank(contentStr);
                    mBankNameTv.setText(nameOfBank);
                } else if (contentStr.length() < 6) {
                    mBankNameTv.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initView() {
        initPicRv();
    }

    private List<File> mFileList = new ArrayList<>();
    private List<String> picUrlList = new ArrayList<>();

    private void initPicRv() {
        picUrlList.add("伪试图");
        mUploadPicAdapter = new UploadPicAdapter(picUrlList, this);
        mRv_pic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRv_pic.setAdapter(mUploadPicAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDialog = SDDialogUtil.newLoading(this, "提交中...");
        LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(this);
        if (userInfoBean != null) {
            mUId = userInfoBean.getUId();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lecturer_apply;
    }

    @OnClick({R.id.naviFrameLeft, R.id.sex_selected_layout, R.id.address_layout, R.id.submit_btn})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.sex_selected_layout:
                    selectSexPopupWindow();
                    break;
                case R.id.address_layout:
                    selectLocation();
                    break;
                case R.id.submit_btn:
                    submitEvent();
                    break;
            }
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
                mAddressTv.setText(province.getAreaName() + " " + city.getAreaName() + " " + county.getAreaName());
            }
        });
        task.execute("辽宁", "沈阳", "浑南新区");
    }

    private CustomPopWindow mCustomPopWindow_SelectedSex;

    private void selectSexPopupWindow() {
        if (mCustomPopWindow_SelectedSex == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.popup_window_select_sex, null);
            TextView menTv = (TextView) contentView.findViewById(R.id.men_tv);
            TextView womenTv = (TextView) contentView.findViewById(R.id.women_tv);
            TextView cancelTv = (TextView) contentView.findViewById(R.id.cancel_tv);

            mCustomPopWindow_SelectedSex = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .create()
                    .showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindow_SelectedSex != null) {
                        mCustomPopWindow_SelectedSex.dissmiss();
                    }
                    switch (v.getId()) {
                        case R.id.men_tv:
                            mSexTv.setText("男");
                            break;
                        case R.id.women_tv:
                            mSexTv.setText("女");
                            break;
                    }
                }
            };
            menTv.setOnClickListener(listener);
            womenTv.setOnClickListener(listener);
            cancelTv.setOnClickListener(listener);
        } else {
            mCustomPopWindow_SelectedSex.showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);
        }
    }

    private Dialog mDialog;

    private void submitEvent() {
        mNameStr = mNameEt.getText().toString().trim();
        if (TextUtils.isEmpty(mNameStr)) {
            showToast("请输入姓名");
            return;
        }
        if (mFileList.size() == 0) {
            showToast("请上传照片");
            return;
        }
        if (mFileList.size() > 0 && mFileList.size() < 3) {
            showToast("请至少上传三张照片");
            return;
        }
        String sexStr = mSexTv.getText().toString().trim();
        if (TextUtils.isEmpty(sexStr)) {
            showToast("请选择性别");
            return;
        }
        mSexMark = "";
        if (sexStr.equals("男")) {
            mSexMark = "0";
        } else {
            mSexMark = "1";
        }
        mAgeStr = mAgeEt.getText().toString().trim();
        if (TextUtils.isEmpty(mAgeStr)) {
            showToast("请输入年龄");
            return;
        }
        mAddressStr = mAddressTv.getText().toString().trim();
        if (TextUtils.isEmpty(mAddressStr)) {
            showToast("请选择省市");
            return;
        }
        mDetailsAddressStr = mDetailsAddressEt.getText().toString().trim();
        if (TextUtils.isEmpty(mDetailsAddressStr)) {
            showToast("请输入详细住址");
            return;
        }
        mPhoneStr = mPhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(mPhoneStr)) {
            showToast("请输入联系电话");
            return;
        }
        if (!ValidationUtil.isPhone(mPhoneStr)) {
            showToast("联系电话格式错误");
            return;
        }
        mNowCompanyStr = mNowCompanyEt.getText().toString().trim();
        if (TextUtils.isEmpty(mNowCompanyStr)) {
            showToast("请输入在职公司");
            return;
        }
        mWorkingYearStr = mWorkingYearEt.getText().toString().trim();
        if (TextUtils.isEmpty(mWorkingYearStr)) {
            showToast("请输入从业年限");
            return;
        }
        mWorkingExperienceStr = mEt_workingExperience.getText().toString().trim();
        if (TextUtils.isEmpty(mWorkingExperienceStr)) {
            showToast("请输入从业经历");
            return;
        }
        mPreviousHonorStr = mPreviousHonorEt.getText().toString().trim();
        if (TextUtils.isEmpty(mPreviousHonorStr)) {
            showToast("请输入曾经荣誉");
            return;
        }
        mFieldExpertiseStr = mFieldExpertiseEt.getText().toString().trim();
        if (TextUtils.isEmpty(mFieldExpertiseStr)) {
            showToast("请输入擅长领域");
            return;
        }
        mIntroduceMySelfStr = mIntroduceMyselfEt.getText().toString().trim();
        if (TextUtils.isEmpty(mIntroduceMySelfStr)) {
            showToast("请输入自我评价");
            return;
        }
        mBankCardStr = mBankCardEt.getText().toString().trim();
        if (TextUtils.isEmpty(mBankCardStr)) {
            showToast("请输入银行卡号");
            return;
        }
        if (!BankCheck.checkBankCard(mBankCardStr)) {
            showToast("银行卡号格式错误");
            return;
        }
        mBankNameStr = mBankNameTv.getText().toString().trim();

        mBankAddressStr = mBankAdderssEt.getText().toString().trim();
        if (TextUtils.isEmpty(mBankAddressStr)) {
            showToast("请输入开户行地址");
            return;
        }
        mRealNameStr = mRealNameEt.getText().toString().trim();
        if (TextUtils.isEmpty(mRealNameStr)) {
            showToast("请输入真实姓名");
            return;
        }
        mLeavePhoneStr = mLeavePhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(mLeavePhoneStr)) {
            showToast("请输入预留手机号");
            return;
        }
        if (!ValidationUtil.isPhone(mLeavePhoneStr)) {
            showToast("预留手机号格式错误");
            return;
        }
        mDialog.show();
        for (int i = 0; i < realPicUrlList.size(); i++) {
            String s = realPicUrlList.get(i);
            uploadHeadToALiYunServer(s);
        }
    }

    private void uploadHeadToALiYunServer(String imgUrl) {
        final String userHeadImgUrl = UUID.randomUUID().toString() + ".jpg";
        PutObjectRequest put = new PutObjectRequest(OssUploadImgUtil.USER_HEAD_BUKET, userHeadImgUrl, imgUrl);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("521", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = OssServerUtil.getSingleTon().oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                /*Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());*/
                Message msg = Message.obtain();
                msg.obj = userHeadImgUrl;
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
                msg.obj = userHeadImgUrl;
                msg.what = 500;
                handle.sendMessage(msg);
            }
        });
        // task.cancel(); // 可以取消任务
// task.waitUntilFinished(); // 可以等待任务完成
    }

    private void requestServer() {
        StringBuilder stringBuilder = new StringBuilder();
        if (uploadImgUrl.size() > 0) {
            for (int i = 0; i < uploadImgUrl.size(); i++) {
                String s = uploadImgUrl.get(i);
                stringBuilder.append(s);
                if (i != uploadImgUrl.size() - 1) {
                    stringBuilder.append(",");
                }
            }
        }
        RxHttpUtils.createApi(ApiService.class)
                .addTeacherRegist(mUId, stringBuilder.toString(), mNameStr, mSexMark, Integer.valueOf(mAgeStr), mAddressStr + mDetailsAddressStr, mPhoneStr,
                        mNowCompanyStr, Integer.valueOf(mWorkingYearStr), mWorkingExperienceStr, "", mPreviousHonorStr,
                        mFieldExpertiseStr, mIntroduceMySelfStr, mBankNameStr, mBankAddressStr, mBankCardStr, mRealNameStr, mLeavePhoneStr)
                .compose(Transformer.<AddTeacherRegistBean>switchSchedulers())
                .subscribe(new CommonObserver<AddTeacherRegistBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        cancelDialog();
                    }

                    @Override
                    protected void onSuccess(AddTeacherRegistBean userBean) {
                        cancelDialog();
                        String msg = userBean.getMsg();
                        showToast(msg);
                        EventBus.getDefault().post(new TiXianSuccessMsg());
                        finish();
                    }
                });
    }

    private void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view.getId() == R.id.working_experience_et && EditTextUtil.canVerticalScroll(mEt_workingExperience)) {
            conflictSolve(view, event);
        } else if (view.getId() == R.id.previous_honor_et && EditTextUtil.canVerticalScroll(mPreviousHonorEt)) {
            conflictSolve(view, event);
        } else if (view.getId() == R.id.field_expertise_et && EditTextUtil.canVerticalScroll(mFieldExpertiseEt)) {
            conflictSolve(view, event);
        } else if (view.getId() == R.id.introduce_myself_et && EditTextUtil.canVerticalScroll(mIntroduceMyselfEt)) {
            conflictSolve(view, event);
        }
        return false;
    }

    private void conflictSolve(View view, MotionEvent event) {
        view.getParent().requestDisallowInterceptTouchEvent(true);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults[0] == 0) {
                    selectPicEvent();
                }
                break;
        }
    }

    private void selectPicEvent() {
        PhotoPicker.builder()
                .setPhotoCount(6)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(MyLecturerApplyActivity.this, PhotoPicker.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                handleUploadPicEvent(photos);
            }
        }
    }

    private ProgressDialog dialog;
    private List<String> realPicUrlList = new ArrayList<>();
    private void handleUploadPicEvent(ArrayList<String> photos) {
        if (dialog == null) {
            dialog = ProgressDialog.show(this, null, "数据处理中...", true, true);
        }
        PictureCompressUtil.getInstance().startCompress(this, photos, new PictureCompressUtil.CompressedPicResultCallBack() {
            @Override
            public void showResult(List<String> photos, List<File> list) {
                dialog.dismiss();
                mFileList.addAll(list);
                for (File file : list) {
                    realPicUrlList.add(file.getAbsolutePath());
                }
                picUrlList.clear();
                picUrlList.addAll(realPicUrlList);
                picUrlList.add("伪视图");
                mUploadPicAdapter.notifyDataSetChanged();
            }
        });
    }
}
