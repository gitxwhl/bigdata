/**
 * 提供一个权限相关的工具类。
 * 
 * @example
 * <code language="JavaScript">
 * define(["PermissionUtil"], function(permissionUtil) {
 * 		//以下代码需在页面渲染完成之后执行，否则会找不到dom节点，
 * 		//普通节点可在viewmodel的self.onload方法中，menu、navbar、toolbar组件可在其onRendered回调事件中。
 * 		permissionUtil.baseUrl = cube.gatewayURL + "/permission/";
		permissionUtil.bindDom("add", $("#addBtn"));
 * })
 * </code>
 */
define(["RESTClient"], function(RestClient) {
	
    var me = {};
    
    /**
     * 获取或设置判断权限的服务地址。
     */
    me.baseUrl = null;
    
    /**
     * 设置 {@link baseUrl} 的值。
     */
    me.setBaseUrl = function(p_baseUrl) {
        if (me.baseUrl != p_baseUrl) {
            me.baseUrl = p_baseUrl;
        }
    };
    
    /**
     * 根据后端返回的权限设置dom是否显示，该函数返回值为后端返回的json数组。
     * @param code 功能编码，必须，该参数将传递后端，参数名为code
     * @param dom Dom节点，可不设置
     * 
     * 后端返回结果需为json数组。
     * @example
     * <code language="JavaScript">
	 * [
	 * 	{
	 * 		"code": "menu1", //功能编码
	 * 		"readOnly": false, //是否只读
	 * 		"visible": false //是否显示
	 * 	}
	 * ]
	 * </code>
	 * 
	 * 注意：如果dom为menu、navbar、toolbar组件，子项需设置code属性，同时返回json中code与菜单子项的code属性一致，菜单子项会根据权限设置是否显示。
     */
    me.bindDom = function(code, dom) {
    	var restClient = new RestClient();
		var result = restClient.getSync(me.baseUrl, {
			"code": code
		});
		
    	if (cube.notEmpty(dom)) {
    		if (!(dom instanceof jQuery)) {
        		dom = $(dom);
    		}
        	
    		if (cube.notEmpty(result) && cube.isArray(result) && result.length > 0) {
    	    	if (dom.is("menu") || dom.is("navbar") || dom.is("toolbar")) {
	    			for (var i = 0; i < result.length; i++) {
	    				var ele = dom.find("[code='" + result[i].code + "']");
	    				if (result[i].readOnly == true) {
	    					ele.attr("readonly", "readonly");
	    				} else {
	    					dom.attr("readonly", "");
	    				}
	    				
	    				if (result[i].visible == false) {
	    					ele.hide();
	    				} else {
	    					ele.show();
	    				}
					}
    	    	} else if(result[0].code == code) {
    	    		if (result[0].readOnly == true) {
    					dom.attr("readonly", "readonly");
    				} else {
    					dom.attr("readonly", "");
    				}
    				
    				if (result[0].visible == false) {
    					dom.hide();
    				} else {
    					ele.show();
    				}
    	    	}
    		}
    	}
    	
    	return result;
    };
    
    /**
     * 从后端获取令牌，令牌会存入cookie，同时之后所有请求url会携带token参数。
     * @param url 后台url
     */
    me.getToken = function(url) {
    	var restClient = new RestClient();
		var result = restClient.getSync(cube.cubepath(url), null);
		if (cube.isString(result)) {
			document.cookie = "token = " + result;
		}
		
		return result;
    };
    
    /**
     * 从后端获登录用户信息。
     * @param url 后台url
     * @param type 从后台获取的用户ID的处理方式，
     */
    me.getUserInfo = function(url, type) {
    	var restClient = new RestClient();
		var result = restClient.getSync(cube.cubepath(url), {"requestType": "getUserId"});
		if (cube.notEmpty(result)) {
			cube.cl_u_id = result.userId;
		}
		
		return result;
    };
    
    return me;
});