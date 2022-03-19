//************************************************************************
//  文 件 名:  SM4_Context.java
//  系统名称：电网统一视频监控平台
//  Copyright@2003-2013 State Grid Corporation of China, All Rights Reserved
//  版本信息：V2.0
//  版    权:  NARI
//  创建 人:  tukai
//************************************************************************
package com.raysdata.riskdataanalyzeserver.utils.sm4;
/**
 * 概述：
 * 功能：
 * 作者：tukai
 * 创建时间:2017-1-20 上午9:58:22
 */
public class SM4_Context {

	public int mode;

	public long[] sk;

	public boolean isPadding;

	public SM4_Context() {
		this.mode = 1;
		this.isPadding = true;
		this.sk = new long[32];
	}
}
