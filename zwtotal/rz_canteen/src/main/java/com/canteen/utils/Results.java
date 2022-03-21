package com.canteen.utils;

/**
 *  建议后面使用通用返回实体
 * @author tdj
 *
 */
public class Results<T> {

	/**
	 * 返回码信息
	 */
	private String code;

	/**
	 * 返回消息
	 */
	private String message;

	/**
	 * 返回数据
	 */
	private T Data;



	public Results() {
		super();
	}


	public Results(String code, String message) {
		super();
		this.code = code;
		this.message = message;

	}

	public Results(String code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		Data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}

}
