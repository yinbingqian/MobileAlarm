package com.lnpdit.mobilealarm.webservice;

/**
 * rest返回(Activity接受类)
 * 
 * @author huanyu 类名称：ResultRest 创建时间:2015-3-4 下午4:58:26 TODO
 */
public class ResultRest {
	private Object resultObj;// rest返回内容
	private ResultState resultState;// rest请求状态
	private Class<?> cls;
	private String methord;
	private long id;
	private int loaction;

	/** 请求状态 **/
	public enum ResultState {
		成功, 失败
	}

	public Object getResultObj() {
		return resultObj;
	}

	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}

	public ResultState getResultState() {
		return resultState;
	}

	public void setResultState(ResultState resultState) {
		this.resultState = resultState;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}

	public String getMethord() {
		return methord;
	}

	public void setMethord(String methord) {
		this.methord = methord;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * event 校验
	 * 
	 * @return
	 */
	public boolean eventLocation(Class<?> cls, String methord) {
		if (this.cls == cls && this.methord == methord)
			return true;
		return false;
	}

	/**
	 * rest 请求校验
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return resultState == ResultState.成功 ? true : false;
	}

	public int getLoaction() {
		return loaction;
	}

	public void setLoaction(int loaction) {
		this.loaction = loaction;
	}
	
}
