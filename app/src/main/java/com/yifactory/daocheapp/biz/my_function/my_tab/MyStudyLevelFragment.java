package com.yifactory.daocheapp.biz.my_function.my_tab;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.DiscoverBean;
import com.yifactory.daocheapp.bean.StudyDateBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyStudyLevelDayAdapter;
import com.yifactory.daocheapp.biz.my_function.adapter.MyStudyLevelMonthAdapter;
import com.yifactory.daocheapp.biz.my_function.adapter.MyStudyLevelWeekAdapter;
import com.yifactory.daocheapp.utils.DateUtil;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.yifactory.daocheapp.widget.addressSelected.picker.DateTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyStudyLevelFragment extends BaseFragment implements MyStudyLevelMonthAdapter.RecyclerVScrollEventCallBack,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.study_level_linechart)
    LineChart lineChart;
    @BindView(R.id.day_tv)
    TextView dayTv;
    @BindView(R.id.week_tv)
    TextView weekTv;
    @BindView(R.id.month_tv)
    TextView monthTv;
    @BindView(R.id.day_total_tv)
    TextView dayTotalTv;
    @BindView(R.id.week_total_tv)
    TextView weekTotalTv;
    @BindView(R.id.month_total_tv)
    TextView monthTotalTv;
    @BindView(R.id.study_level_rv)
    RecyclerView mRv_study_level;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private String[] xData = {"日","一", "二", "三", "四", "五", "六", "(周)"};
    private int[] yData = new int[8];
    private int tabPosition = 0;
    private List<StudyDateBean.DataBean.DayDataBean> dayList = new ArrayList<>();
    private List<StudyDateBean.DataBean.WeekDataBean> weekList = new ArrayList<>();
    private List<StudyDateBean.DataBean.MonthDataBean> monthList = new ArrayList<>();
    private Dialog mDialog;
    private TimePickerView pvTime;

    public static MyStudyLevelFragment newInstance() {
        MyStudyLevelFragment fragment = new MyStudyLevelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initChart();
    }

    private void initViewValue(StudyDateBean.DataBean studyDateBean){
        dayList.clear();
        weekList.clear();
        monthList.clear();
        if(studyDateBean.getDayData().getSsDay() != null){
            dayTotalTv.setText("本日累计学习时长" + String.valueOf(studyDateBean.getDayData().getDaySum()/60) + "分钟");
            dayList.add(studyDateBean.getDayData());
        }else{
            dayTotalTv.setText("本日累计学习时长0分钟");
        }
        if(studyDateBean.getWeekData().size() > 0){
            weekTotalTv.setText("本周累计学习时长" + String.valueOf(studyDateBean.getWeekData().get(0).getWeekTotal()/60) + "分钟");
            weekList = studyDateBean.getWeekData();
        }else{
            weekTotalTv.setText("本周累计学习时长0分钟");
        }
        if(studyDateBean.getMonthData().size() > 0){
            monthTotalTv.setText("本月累计学习时长" + String.valueOf(studyDateBean.getMonthData().get(0).getMonthTotal()/60) + "分钟");
            monthList = studyDateBean.getMonthData();
        }else{
            monthTotalTv.setText("本月累计学习时长0分钟");
        }
        setYData(studyDateBean.getWeekData());
        initList();
    }

    private void initChart() {
        lineChart.setBorderColor(Color.rgb(213, 216, 214));
        lineChart.setGridBackgroundColor(Color.rgb(255, 255, 255));
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.getAxisRight().setEnabled(false); //设置右侧不显示数字
        lineChart.setDescription("");

        XAxis xl = lineChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的数据在底部显示
        xl.setTextSize(10f); // 设置字体大小
        xl.setTextColor(Color.rgb(64, 135, 253));
        xl.setSpaceBetweenLabels(0); // 设置数据之间的间距'

        YAxis y2 = lineChart.getAxisLeft();
        y2.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y2.setTextSize(10f); // s设置字体大小
        y2.setAxisMinValue(0f);
        y2.setStartAtZero(false);
        y2.setTextColor(Color.rgb(64, 135, 253));
        y2.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return "" + (int)value;
            }
        });
        setData(xData, yData);
    }

    private void setData(String[] xx, int[] yy) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xx.length; i++) {
            xVals.add(xx[i]);
        }
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < yy.length; i++) {
            yVals.add(new Entry(yy[i], i));
        }

        LineDataSet set1 = new LineDataSet(yVals, "轨迹");
        set1.setDrawCubic(false);  //设置曲线为圆滑的线
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set1.setDrawCircles(false);  //设置有圆点
        set1.setDrawValues(false); //不显示y值
        set1.setLineWidth(1f);    //设置线的宽度
        set1.setHighlightEnabled(false); //不显示定位线
//        set1.setHighLightColor(Color.rgb(64, 135, 253)); //定位线颜色
        set1.setColor(Color.rgb(64, 135, 253));    //设置曲线的颜色
        LineData data = new LineData(xVals, set1);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    private void setYData(List<StudyDateBean.DataBean.WeekDataBean> weekDataList){
        if(weekDataList == null || weekDataList.size() == 0){
            for (int i = 0; i < 7; i++){
                yData[i] = 0;
            }
        }
        for (int i = 0; i < weekDataList.size(); i++){
            yData[i] = weekDataList.get(i).getDaySum()/60;
        }
        setData(xData,yData);
    }

    private void initList(){
        if(tabPosition == 0){
            MyStudyLevelDayAdapter dayAdapter = new MyStudyLevelDayAdapter(R.layout.item_my_study_level_day);
            mRv_study_level.setLayoutManager(new LinearLayoutManager(mActivity));
            mRv_study_level.setAdapter(dayAdapter);
            dayAdapter.setNewData(dayList);
        }else if(tabPosition == 1){
            MyStudyLevelWeekAdapter weekAdapter = new MyStudyLevelWeekAdapter(R.layout.item_my_study_level_day);
            mRv_study_level.setLayoutManager(new LinearLayoutManager(mActivity));
            mRv_study_level.setAdapter(weekAdapter);
            weekAdapter.setNewData(weekList);
        }else{
            MyStudyLevelMonthAdapter monthAdapter = new MyStudyLevelMonthAdapter(R.layout.item_my_study_level_day,this);
            mRv_study_level.setLayoutManager(new LinearLayoutManager(mActivity));
            mRv_study_level.setAdapter(monthAdapter);
            monthAdapter.setNewData(monthList);
        }

    }

    private void getStudyDataEvent(String time){
        mDialog = SDDialogUtil.newLoading(mActivity,"正在加载数据，请稍后");
        mDialog.show();
        String uId = UserInfoUtil.getUserInfoBean(mActivity).getUId();
        RxHttpUtils.createApi(ApiService.class)
                .getStudyDate(uId,time)
                .compose(Transformer.<StudyDateBean>switchSchedulers())
                .subscribe(new CommonObserver<StudyDateBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast("数据错误:" + errorMsg);
                        if(mDialog.isShowing()){
                            mDialog.cancel();
                        }
                    }

                    @Override
                    protected void onSuccess(StudyDateBean studyDateBean) {
                        if(mDialog.isShowing()){
                            mDialog.cancel();
                        }
                        if(studyDateBean != null && studyDateBean.getResponseState().equals("1")){
                            initViewValue(studyDateBean.getData().get(0));
                        }
                    }
                });
    }

    @Override
    protected void initData(Bundle arguments) {
        getStudyDataEvent(String.valueOf(System.currentTimeMillis()/1000));
        initTimePicker();
    }

    private void selectDate(){
        if(pvTime != null){
            pvTime.show();
        }
    }
    private void initTimePicker() {
        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date2, View v) {
                    String time = DateUtil.getTime(date2);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date date = formatter.parse(time);
                        getStudyDataEvent(String.valueOf(date.getTime()/1000));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }).setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                @Override
                public void customLayout(View v) {
                    Button tvSubmit = (Button) v.findViewById(R.id.btnSubmit);
                    Button ivCancel = (Button) v.findViewById(R.id.btnCancel);
                    tvSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pvTime.dismiss();
                        }
                    });
                    ivCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pvTime.returnData();
                            pvTime.dismiss();
                        }
                    });
                }
            })
            .setType(new boolean[]{true, true, true, false, false, false})
            .setCancelText("取消")
            .setSubmitText("确定")
            .setContentSize(20)
            .setTitleSize(20)
            .setOutSideCancelable(true)
            .isCyclic(true)
            .setTextColorCenter(Color.BLACK)
            .setTitleColor(Color.BLACK)
            .isCenterLabel(false)
            .isDialog(true)
            .build();
    pvTime.setDate(Calendar.getInstance());
}

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @OnClick({R.id.day_tv, R.id.week_tv, R.id.month_tv,R.id.rili_iv})
    public void onClickTabEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.day_tv:
                    tabPosition = 0;
                    dayTv.setBackgroundResource(R.drawable.shape_sagement_interview_txt_checked_left);
                    dayTv.setTextColor(Color.parseColor("#FFFFFF"));
                    weekTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    weekTv.setTextColor(Color.parseColor("#000000"));
                    monthTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    monthTv.setTextColor(Color.parseColor("#000000"));
                    break;
                case R.id.week_tv:
                    tabPosition = 1;
                    dayTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    dayTv.setTextColor(Color.parseColor("#000000"));
                    weekTv.setBackgroundColor(Color.parseColor("#4087fd"));
                    weekTv.setTextColor(Color.parseColor("#FFFFFF"));
                    monthTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    monthTv.setTextColor(Color.parseColor("#000000"));
                    break;
                case R.id.month_tv:
                    tabPosition = 2;
                    dayTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    dayTv.setTextColor(Color.parseColor("#000000"));
                    weekTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    weekTv.setTextColor(Color.parseColor("#000000"));
                    monthTv.setBackgroundResource(R.drawable.shape_sagement_interview_txt_checked_right);
                    monthTv.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                case R.id.rili_iv:
                    selectDate();
                    break;
            }
            initList();
        }
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void scrollEvent(int position) {
        mRv_study_level.scrollToPosition(position);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_my_study_level;
    }
}
