package com.canteen.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * 类描述：此类异常会回滚事务
 * @version: 1.0
 * @author: zlh
 * @version: 2013-4-1 下午12:50:16
 */
public class FatalException extends RuntimeException
{
    /**
     *
     */
    private static final long serialVersionUID = 5668703504902207799L;

    private static Log log = LogFactory.getLog(FatalException.class);

    private String code; // 错误代码
    private String msg; // 错误描述

    public FatalException(String code)
    {
        super(ExceptionMsg.getMsg(code));
        this.setMsg(ExceptionMsg.getMsg(code));
        this.setCode(code);
    }

    public FatalException(String[] messages)
    {
        super(messages[0]);
        this.setMsg(messages[0]);
        this.setCode(ExceptionMsg.SERVICETIME_CODE);
    }

    /**
     * 推荐抛出的异常格式
     * @param code 错误码
     * @param detail 错误详情,用于log日志中显示的
     */
    public FatalException(String code,String detail)
    {
        super(ExceptionMsg.getMsg(code));
        StringBuffer sb = new StringBuffer();
        this.setCode(code);
        this.setMsg(ExceptionMsg.getMsg(code));
        StackTraceElement[] stacks = this.getStackTrace();
        StackTraceElement stack = stacks[0];
        sb.append("\n ======EXCEPTION LOG====== \n <CLASS: ").append(stack.getFileName()).append("; METHOD: ").append(stack.getMethodName()).append("; LINE NUMBER: ").append(stack.getLineNumber()).append("; MESSAGE:" + detail);
        log.error(sb.toString());
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "FatalException [code=" + code + ", msg=" + msg + "]";
    }

}
