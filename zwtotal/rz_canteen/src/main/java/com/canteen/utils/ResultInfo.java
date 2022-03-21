package com.canteen.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 公共的结果类，用于前台向后台返回消息的类
 *
 */

public class ResultInfo implements Serializable
{
    private static final long serialVersionUID = 5016711677594262244L;
    /**
     * 返回的标志位
     */
    private String flag;
    private long total;
    private List<Object> rows;

    //列合计
    private List<Map<String,String>> footer;

    public List<Map<String, String>> getFooter() {
        return footer;
    }

    public void setFooter(List<Map<String, String>> footer) {
        this.footer = footer;
    }

    /**
     * 返回的提示消息
     */
    private String message;

    public Object data = null;

    public ResultInfo()
    {
        this.flag = ExceptionMsg.SUCCESS_FLAG;
        this.message=ExceptionMsg.SUCCESS_MESSAGE;
    }

    public static ResultInfo getFailedInfo()
    {
        return new ResultInfo(ExceptionMsg.FAILED_FLAG,ExceptionMsg.FAILED_MESSAGE);
    }

    public static ResultInfo getFailedInfo(String code)
    {

        return new ResultInfo(code,ExceptionMsg.getMsg(code));
    }

    public static ResultInfo getSuccessInfo()
    {
        return new ResultInfo(ExceptionMsg.SUCCESS_FLAG,ExceptionMsg.SUCCESS_MESSAGE);
    }
    public static ResultInfo getSuccessInfo(Object obj)
    {
        ResultInfo info = new ResultInfo(ExceptionMsg.SUCCESS_FLAG,ExceptionMsg.SUCCESS_MESSAGE);
        info.setData(obj);
        return info;
    }
    public static ResultInfo getSuccessListInfo(List<Object> obj)
    {
        ResultInfo info = new ResultInfo(ExceptionMsg.SUCCESS_FLAG,ExceptionMsg.SUCCESS_MESSAGE);
        info.setRows(obj);
        return info;
    }
    public ResultInfo(String flag)
    {
        super();
        this.flag = flag;
        this.message = ExceptionMsg.getMsg(flag);
    }
    public ResultInfo(String flag,String message)
    {
        super();
        this.flag = flag;
        this.message = message;
    }

    public ResultInfo(FatalException fatalException)
    {
        super();
        this.flag = fatalException.getCode();
        this.message = fatalException.getMsg();
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<Object> getRows()
    {
        return rows;
    }

    public void setRows(List<Object> rows)
    {
        this.rows = rows;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String toJsonString()
    {
        return JSONObject.fromObject(this).toString();
    }
}
