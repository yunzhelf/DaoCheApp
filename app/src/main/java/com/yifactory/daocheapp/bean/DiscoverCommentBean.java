package com.yifactory.daocheapp.bean;

import java.io.Serializable;

public class DiscoverCommentBean implements Serializable{

	private String qId;

	private String uId;

	private String rId;

	private String answerBody;

	private String reciverName;

	private int itemPosition;

	public int getItemPosition() {
		return itemPosition;
	}

	public void setItemPosition(int itemPosition) {
		this.itemPosition = itemPosition;
	}

	public String getqId() {
		return qId;
	}

	public void setqId(String qId) {
		this.qId = qId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getAnswerBody() {
		return answerBody;
	}

	public void setAnswerBody(String answerBody) {
		this.answerBody = answerBody;
	}

	public String getReciverName() {
		return reciverName;
	}

	public void setReciverName(String reciverName) {
		this.reciverName = reciverName;
	}
}
