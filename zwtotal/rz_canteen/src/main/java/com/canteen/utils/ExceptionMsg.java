package com.canteen.utils;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * 类描述：异常消息对象
 * @version: 1.0
 * @author: zlh
 * @version: 2013-4-1 下午12:46:17
 */
public class ExceptionMsg
{


    /**************************操作异常**********************************/
    public static final String SUCCESS_FLAG = "00000";
    public static final String SUCCESS_MESSAGE = "操作成功！";
    public static final String FAILED_FLAG = "00001";
    public static final String FAILED_MESSAGE = "操作失败，请联系管理员！";
    public static final String SESSION_TIMEOUT_FLAG = "00002";
    public static final String SESSION_TIMEOUT_MESSAGE = "会话超时！";

    /**************************配置异常**********************************/
    // public static final String SERVICEEXCEPTION = "99";
    public static final String SERVICETIME_CODE = "990001";

    /***********************模板异常*******************************/
    // public static final String TEMPLATEEXCEPTION = "00";
    public static final String DATACONVERT_CODE = "000001";
    public static final String NURSESORTERROR = "000001";
    public static final String TEMPLATECONFIGERROR = "000002";

    /*************************系统管理*************************************/
    public static final String XTGL = "01";
    public static final String XTGL_BCCWBM = "001001"; // 系统管理--保存错误编码
    /***************************权限*******************************************/
    // public static final String PERMISSION = "02";
    public static final String USERNAMEERROR_CODE = "02001";
    public static final String NOTENOUGHPERMISSION_CODE = "02002";
    public static final String ERROR_CODE0 = "02003";
    public static final String ERROR_CODE1 = "02004";
    public static final String ERROR_CODE2 = "02005";
    public static final String ERROR_CODE3 = "02006";
    public static final String ERROR_CODE4 = "02007";

    public static final String ERROR_CODE6 = "02007";

    public static final String ERROR_CODE7 = "02009";
    public static final String ERROR_CODE8 = "02010";
    public static final String ERROR_CODE9 = "02011";
    public static final String ERROR_CODE10 = "02012";
    public static final String ERROR_CODE11 = "02013";
    public static final String ERROR_CODE12 = "02014";
    public static final String ERROR_CODE13 = "02015";
    public static final String ERROR_CODE14 = "02016";
    public static final String ERROR_CODE15 = "02017";
    public static final String ERROR_CODE16 = "02018";
    public static final String ERROR_CODE17 = "02019";
    public static final String ERROR_CODE18 = "02020";
    public static final String ERROR_CODE19 = "02021";
    public static final String ERROR_CODE20 = "02022";
    public static final String ERROR_CODE21 = "02023";
    public static final String ERROR_CODE22 = "02024";

    /***************************文件*******************************************/
    public static final String ERROR_CODE50 = "050001";
    public static final String ERROR_CODE51 = "050002";
    public static final String ERROR_CODE52 = "050003";
    public static final String ERROR_CODE53 = "050004";


    /**************************ftp*******************************************/
    public static final String ERROR_CODE60 = "060000";
    public static final String ERROR_CODE61 = "060001";
    public static final String ERROR_CODE62 = "060002";
    public static final String ERROR_CODE63 = "060003";
    public static final String ERROR_CODE64 = "060004";
    public static final String ERROR_CODE65 = "060005";
    public static final String ERROR_CODE66 = "060006";
    public static final String ERROR_CODE67 = "060007";
    public static final String ERROR_CODE68 = "060008";


    public static final String GOODSERROR_CODE1 = "GOODS001";
    public static final String GOODSERROR_CODE2 = "GOODS002";
    public static final String MEMEXECUTEERROR = "04001";// 内存保存失败
    public static final String ERROR_CODE5 = "02008";


    private static Map<String,String> map;

    public static String getMsg(String code)
    {
        if(map == null) map = new HashMap<String,String>();
        map.put(SESSION_TIMEOUT_FLAG,"会话超时！");
        /***********************模板异常*******************************/
        map.put(XTGL_BCCWBM,"保存错误！");
        /*************************系统管理*************************************/
        map.put(DATACONVERT_CODE,"数据转换异常！");
        /***************************权限*******************************************/
        map.put(USERNAMEERROR_CODE,"用户名或密码错误！");
        map.put(NOTENOUGHPERMISSION_CODE,"权限不够！");
        map.put(SERVICETIME_CODE,"服务器异常！");
        map.put(MEMEXECUTEERROR,"内存调度失败！");

        map.put(ERROR_CODE0,"用户名或密码不能为空！");
        map.put(ERROR_CODE2,"旧密码输入错误！");
        map.put(ERROR_CODE3,"该机构编码已存在！");
        map.put(ERROR_CODE4,"没有权限！");
        map.put(ERROR_CODE5,"工号或登录名已存在！");

        map.put(ERROR_CODE6,"综合查询失败！");

        map.put(ERROR_CODE7,"未查到任何数据！");
        map.put(ERROR_CODE8,"文件导出失败！");
        map.put(ERROR_CODE9,"该用户已存在！");
        map.put(ERROR_CODE10,"您所在用户组不存在！");
        map.put(ERROR_CODE11,"激活帐号失败,激活码有误！");
        map.put(ERROR_CODE12,"您的帐号已经被激活,请确认！");
        map.put(ERROR_CODE13,"邮箱，昵称，手机号其中一个已被人提前注册，请重新注册！");
        map.put(ERROR_CODE14,"该帐号还未注册，请先注册后，再进行激活！");
        map.put(ERROR_CODE15,"邮件发送失败！");
        map.put(ERROR_CODE16,"用户不存在！");
        map.put(ERROR_CODE17,"该参数已存在！");
        map.put(ERROR_CODE18,"该IP已经存在！");
        map.put(ERROR_CODE19,"人员编号已经存在！");
        map.put(ERROR_CODE20,"该模板名称已经存在！");
        map.put(ERROR_CODE21,"该编号已经存在,请重新输入！");
        //FTP服务器
        map.put(ERROR_CODE60,"FTP上传文件失败！");
        map.put(ERROR_CODE61,"FTP服务器用户名、密码配置出错！");
        map.put(ERROR_CODE62,"FTP服务器连接报错,请检查FTP服务器IP！");
        map.put(ERROR_CODE63,"FTP服务器目录创建报错，可能是服务器权限不够！！");
        map.put(ERROR_CODE65,"FTP上传文件成功！");
        map.put(ERROR_CODE66,"FTP下载文件失败！");
        map.put(ERROR_CODE67,"FTP下载文件不存在！");
        map.put(ERROR_CODE68,"FTP下载文件成功！");

        //文件
        map.put(ERROR_CODE50,"文件不存在！");
        map.put(ERROR_CODE51,"导入文件类型不对，应为xls或xlxs!");
        map.put(ERROR_CODE52,"文件导入失败!");
        map.put(ERROR_CODE53,"文件导入成功!");
        map.put(GOODSERROR_CODE1, "该商品入库已达到库存上线，无法入库！");
        map.put(GOODSERROR_CODE2, "该商品出库数量大于库存量，无法出库！");
        return map.get(code);
    }

}
