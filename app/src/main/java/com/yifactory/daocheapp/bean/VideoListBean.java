package com.yifactory.daocheapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class VideoListBean implements Parcelable {


    /**
     * data : [{"buyState":0,"createTime":"2018-03-09 11:07:12","creator":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"http://wzdimg.oss-cn-beijing.aliyuncs.com/BEXAQFKRZHJHSCIBSRDYUEXFFFKLSUXY.jpg","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":"1"},"deleteFlag":0,"goldCount":5,"harvest":"获得收货","indexImg":"pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg","rId":"89325d122cb04754999dcb17a7392a88","scId":"1","secondTitle":"钢铁是怎样炼成的","showCounts":2,"state":0,"suitPerson":"适合人群","title":"第一集","totalMinute":120,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-13 16:13:23","videoContent":"视频内容","videoDetail":"视频详细","videoPath":"阿里资源Id"}]
     * msg : 查询成功
     * responseState : 1
     */

    public String msg;
    public String responseState;
    public List<PlayVideoBean.DataBean.HotBean> data;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeString(this.responseState);
        dest.writeTypedList(this.data);
    }

    public VideoListBean() {
    }

    protected VideoListBean(Parcel in) {
        this.msg = in.readString();
        this.responseState = in.readString();
        this.data = in.createTypedArrayList(PlayVideoBean.DataBean.HotBean.CREATOR);
    }

    public static final Parcelable.Creator<VideoListBean> CREATOR = new Parcelable.Creator<VideoListBean>() {
        @Override
        public VideoListBean createFromParcel(Parcel source) {
            return new VideoListBean(source);
        }

        @Override
        public VideoListBean[] newArray(int size) {
            return new VideoListBean[size];
        }
    };
}
