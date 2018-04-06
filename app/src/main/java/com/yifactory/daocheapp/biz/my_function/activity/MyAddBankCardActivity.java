package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.BankCardBean;
import com.yifactory.daocheapp.event.AddBankCardSuccessMsg;
import com.yifactory.daocheapp.utils.BankCheck;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.utils.ValidationUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyAddBankCardActivity extends BaseActivity {

    @BindView(R.id.add_bank_name_et)
    TextView bankNameEt;
    @BindView(R.id.add_bank_num_et)
    EditText bankNumEt;
    @BindView(R.id.add_bank_address_et)
    EditText bankAddressEt;
    @BindView(R.id.add_bank_username_et)
    EditText bankUserNameEt;
    @BindView(R.id.add_bank_userphone_et)
    EditText bankUserPhoneEt;
    private String mUId;
    private Dialog mDialog;


    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("添加银行卡");
        return true;
    }

    @Override
    protected void addListener() {
        bankNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String contentStr = s.toString();
                if (contentStr.length() == 6) {
                    String nameOfBank = BankCheck.getNameOfBank(contentStr);
                    bankNameEt.setText(nameOfBank);
                } else if (contentStr.length() < 6) {
                    bankNameEt.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "请求中");
    }

    private void addBankEvent(){
        String bankNum = bankNumEt.getText().toString().trim();
        if(TextUtils.isEmpty(bankNum)){
            showToast("请输入银行卡号");
            return;
        }
        if (!BankCheck.checkBankCard(bankNum)) {
            showToast("银行卡号不合法");
            return;
        }
        String bankName = bankNameEt.getText().toString().trim();
        final String bankAddress = bankAddressEt.getText().toString().trim();
        if(TextUtils.isEmpty(bankAddress)){
            showToast("请输入开户行地址");
            return;
        }
        String bankUserName = bankUserNameEt.getText().toString().trim();
        if(TextUtils.isEmpty(bankUserName)){
            showToast("请输入真实姓名");
            return;
        }
        String bankUserPhone = bankUserPhoneEt.getText().toString().trim();
        if(TextUtils.isEmpty(bankUserPhone)){
            showToast("请输入预留电话号");
            return;
        }
        if (!ValidationUtil.isPhone(bankUserPhone)){
            showToast("请输入正确的电话号");
            return;
        }

        Map<String,RequestBody> bankMap = new HashMap<>();
        RequestBody uIdBody = RequestBody.create(MediaType.parse("text/plain"), mUId);
        RequestBody bankNumBody = RequestBody.create(MediaType.parse("text/plain"),bankNum);
        RequestBody bankNameBody = RequestBody.create(MediaType.parse("text/plain"),bankName);
        RequestBody bankAddressBody = RequestBody.create(MediaType.parse("text/plain"),bankAddress);
        RequestBody bankUserNameBody = RequestBody.create(MediaType.parse("text/plain"),bankUserName);
        RequestBody bankUserPhoneBody = RequestBody.create(MediaType.parse("text/plain"),bankUserPhone);
        bankMap.put("uId",uIdBody);
        bankMap.put("bankName",bankNameBody);
        bankMap.put("bankAddress",bankAddressBody);
        bankMap.put("bankNum",bankNumBody);
        bankMap.put("userName",bankUserNameBody);
        bankMap.put("userMobile",bankUserPhoneBody);
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .addUserBankRecord(bankMap)
                .compose(Transformer.<BankCardBean>switchSchedulers())
                .subscribe(new CommonObserver<BankCardBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mDialog.dismiss();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(BankCardBean bankCardBean) {
                        mDialog.dismiss();
                        if (bankCardBean != null && bankCardBean.getResponseState().equals("1")) {
                            showToast("添加成功");
                            EventBus.getDefault().post(new AddBankCardSuccessMsg());
                            finish();
                        } else {
                            showToast(bankCardBean.getMsg());
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_add_bank_card;
    }

    @OnClick({R.id.naviFrameLeft,R.id.add_bank_btn})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.add_bank_btn:
                    addBankEvent();
                    break;
            }
        }
    }
}
