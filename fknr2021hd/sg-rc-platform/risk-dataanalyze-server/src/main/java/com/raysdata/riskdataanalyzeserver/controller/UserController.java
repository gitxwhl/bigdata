//package com.raysdata.riskdataanalyzeserver.controller;
//
//import com.nariit.rmcp.common.vo.WrappedResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//public class UserController {
//
//
//
//    /**
//     * 根据userId获取用户姓名
//     *
//     * @return 用户姓名
//     * @throws IOException
//     */
//    @RequestMapping(method = RequestMethod.GET, value = "/user/getUser")
//    public WrappedResult getUser(HttpServletRequest request, HttpServletRequest res) throws IOException {
//        //HttpServletResponse response = (HttpServletResponse) res;
//        UserInfo userInfo =  (UserInfo) request.getSession().getAttribute("user");
//        if(null == userInfo) {
//            String token = ParamsUtil.getToken(request);
//            if(StringUtils.isNotBlank(token)) {
//                String userStr = (String)redisUtil.get(token+"-user");
//                if(StringUtils.isNotBlank(userStr)) {
//                    JSONObject jsonObject=JSONObject.fromObject(userStr);
//                    userInfo = (UserInfo)JSONObject.toBean(jsonObject, UserInfo.class);
//                    request.getSession().setAttribute("user", userInfo);
//                }
//            }
//        }
///*        if(null == userInfo) {
//        	String redirectUrl = iscSso+"/login?service="+busiUrl+"index.html";
//    		response.sendRedirect(redirectUrl);
//        }*/
//        return WrappedResult.successWrapedResult(userInfo);
//
//    }
//
//
//}
