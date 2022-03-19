/**
 * 管理全局内容，提供全局方法。cube对象为全局对象，可直接使用。
 */
/*
 * 解决IE下不支持Array的IndexOf方法
 */
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function(elt/*, from*/) {
        var len = this.length >>> 0;
        var from = Number(arguments[1]) || 0;
        from = (from < 0) ? Math.ceil(from) : Math.floor(from);
        if (from < 0){
        	from += len;
        }
        	
        for (; from < len; from++) {
            if (from in this && this[from] === elt){
            	return from;
            }
        }
        return - 1;
    };
}

/**
 * 管理全局内容，对knockout的封装。
 * 后续如果替换mvvm的框架，或者该框架重新实现，框架升级时，不影响业务系统。
 */
CUBE = function()
{
	var self = this;
	
	/**
     * 获取或设置一个 Boolean 值，表示当前是否开发模式。如果为 true 表示当前是开发模式；反之当前为运行时模式，在运行时模式将加载压缩过的框架js文件。
     * 当在入口页面引入framework.min.js时该属性将会自动被设置为false。
     * 
     * @default true
     */
	self.debugMode = true;
	
	/**
     * 获取或设置一个 Boolean 值，表示当debugMode属性设置false时，加载所有组件压缩的一个js（设置为true），还是加载组件的各个压缩js（设置为false）。
     * 
     * @default true
     */
	self.oneComponentsmin = true;
	
	/**
     * 获取或设置一个 Boolean 值，表示当debugMode属性设置false，同时oneComponentsmin属性为true时，是否加载项目自定义组件的单一压缩文件。
     * 
     * @default false
     */
	self.loadExtComponentsmin = false;
	
	 /**
     * 获取设置一个 Boolean 值，表示当debugMode属性设置false时（运行时模式）是否同时也加载压缩过的页面js文件。
     * 
     * @default true
     */
	self.syncDebugMode = true;
	
	/**
	 * 获取是否调试LESS，使用全局变量$CUBE_DEBUGLESS设置该属性值，可选：true、false
	 * 
	 * @default false
	 */
	self.debugLess = false;
	
	/**
	 * 获取主题样式，使用全局变量$CUBE_THEME设置该属性值，可选：middle、small
	 * 
	 * @default 空
	 */
	self.theme = "";
	
	 /**
     * 获取设置框架的国际化信息。
     * 
     * @default zh_CN
     */
	self.locale = "zh_CN";
	
	/**
     * 获取设置框架的国际化信息文件路径。
     * 
     * @default cube
     */
	self.localePath = "cube";
	
	/**
	 * 设置模块名称
	 * 
	 * @default 空
	 */
	self.bundleName = "";
	
	/**
	 * 设置场景名称，该属性值影响cube.mappath的返回值。
	 * 
	 * @default 空
	 */
	self.name = "";
	
	/**
     * 获取设置的错误类型，如果为 normal 表示正常提示错误信息，如果为 friendly 表示不显示后台错误信息。
     * 
     * @default normal
     */
	self.errorType = "normal";
	
	/**
	 * 获取或设置一个数字，表示RESTClient工具请求的默认超时时间，以毫秒为单位。
	 * 
	 * @default 60000
	 */
	self.timeout = 60000;
	
	/**
	 * 设置jQuery ajaxSetup的默认配置，当该属性不为null时覆盖框架的ajaxSetup配置。
	 * 
	 * @default normal
	 */
	self.ajaxSetupOptions = null;
	
	 /**
     * 当IE版本小于9.0时跨域请求是否使用代理，该属性主要适用于微服务请求。
     * 
     * @default false
     */
    self.ieCorsProxy = false;
     
    /**
     * 跨域代理地址，当 {@link ieCorsProxy}属性开启时生效。
     * 
     * @default /corsproxy
     */
    self.ieCorsProxyHost = "~/corsproxy";
	
	 /**
     * 获取当前工程的根路径。
     */
	self.rootPath = null;
	
	/**
	 * 请求微服务后台时的授权码全局设置，也可通过{@link RESTClient}的{@link RESTClient.authorization}属性设置或通过在baseUrl属性后拼接“?authorization=授权码”设置。
	 * 
	 * @default null
	 */
	self.authorization = null;
	
	/**
	 * 自定义请求的header的全局设置，RESTClient和数据组件的customHeaders属性没有赋值时将使用该处设置的值。
	 * @example
	 * <code language="JavaScript">
	 * { 
	 * 		headRestType: "form", 
	 *      headMsg: "success"
	 * }
	 * </code>
	 * 
	 * @default null
	 */
	self.customHeaders = null;
	
	/**
	 * 视图页面文件后缀
	 * @default html
	 */
	self.fileSuffix = "html"
		
	/**
	 * 设置框架组件默认值
	 * @example
	 * <code language="JavaScript">
	 * {
	 * 		"datagrid": {//组件名称
	 * 			pageSize: 15,//组件属性
	 * 			loadMeta: false
	 * 		},
	 * 		"searchbox": {
	 * 			editorWidth: "120px"
	 * 		}
	 * }
	 * </code>
	 * 
	 *  @default 空对象
	 */
	self.componentDefaultValue = {};
	
	/**
	 * 设置前后端请求时参数加密的回调函数，当该函数不为null时会在RESTClient的send方法中调用该函数对参数进行处理。
	 * @param 一个json对象，前端请求到后端的参数
	 * 
	 *  @example
	 * <code language="JavaScript">
	 * cube.encryptData = function(p_data) {
	 *    p_data.params = {}
	 *    
	 *    return p_data;
	 * }
	 * </code>
	 * 
	 * @default null
	 * 
	 */
	self.encryptData = null;
	
	/**
	 * @ignore
	 */
	self.dialogCount = 0;
	
	/**
	 * require资源配置
	 */
	self.requirePaths = null;
	
	/**
	 * 初始化
	 * @ignore
	 */
	self.init = function(){
		var src = "";
		//路径获取
		var index = 0;
		var scripts = document.getElementsByTagName("script");
		if (scripts.length > 0) {
			for (var i = 0; i < scripts.length; i++) {
				if (scripts[i].src && scripts[i].src.indexOf("cube/scripts/framework") != -1) {
					src = scripts[i].src;
					index = i;
					break;
				}
			}
		}
		
		if (!(src.substring(0, "http".length) == "http"))
			src = scripts[index].getAttribute('src', 4);

		var _frameworkPath = "/cube/scripts/framework.js";
		var _frameworkMinPath = "/cube/scripts/framework.min.js";

		//获取跟路径
		if (src.substring(src.length - _frameworkPath.length) == _frameworkPath) {
			self.rootPath = src.substr(0, src.length - _frameworkPath.length);
		} else if (src.substring(src.length - _frameworkMinPath.length) == _frameworkMinPath) {
			self.rootPath = src.substr(0, src.length - _frameworkMinPath.length);
			self.debugMode = false;
		}
		
		var min = "";
		var minsuf = "";
		
		if (!self.debugMode) {
			min = "min/";
			minsuf = ".min";
		}
		
		self.requirePaths = {
	    	"text":                 	"cube/scripts/libs/" + min + "require.text" + minsuf,
			"sammy":					"cube/scripts/libs/" + min + "sammy" + minsuf,
			"bootstrap":				"cube/scripts/libs/" + min + "bootstrap" + minsuf,
			"jqprint":                  "cube/scripts/libs/" + min + "jqprint" + minsuf,
			"entityContainer":          "cube/scripts/utils/" + min + "EntityContainer" + minsuf,
			"treeEntityContainer":      "cube/scripts/utils/" + min + "TreeEntityContainer" + minsuf,
			"JSONUtil":                 "cube/scripts/utils/" + min + "JsonUtil" + minsuf,
			"CheckUtil":                "cube/scripts/utils/" + min + "CheckUtil" + minsuf,
			"Validator":                "cube/scripts/utils/" + min + "Validator" + minsuf,
			"RESTClient":               "cube/scripts/utils/" + min + "RESTClient" + minsuf,
			"GUIDUtil":                 "cube/scripts/utils/" + min + "GUIDUtil" + minsuf,
			"FileUploader":             "cube/scripts/utils/" + min + "FileUploader" + minsuf,
			"PrintUtil":                "cube/scripts/utils/" + min + "PrintUtil" + minsuf,
			"ExcelUtil":                "cube/scripts/utils/" + min + "ExcelUtil" + minsuf,
			"PermissionUtil":           "cube/scripts/utils/" + min + "PermissionUtil" + minsuf,
			"PinyinUtil":               "cube/scripts/utils/" + min + "PinyinUtil" + minsuf,
			"cropper":                  "cube/scripts/libs/cropper.min",
			"cryptocore":               "cube/scripts/libs/" + min + "cryptocore" + minsuf,
			"md5":                      "cube/scripts/libs/" + min + "md5" + minsuf,
			"echarts":                  "cube/scripts/libs/echarts.min-4.1.0",
			// "echarts":                  "cube/scripts/libs/echarts.min",
			"axios":					"cube/scripts/libs/min/axios.min",
			"croppercss":               "cube/resources/css/cropper.min.css"
	    }
		
		//配置require资源加载
		require = {
				baseUrl: self.rootPath,
			    paths: self.requirePaths
		};

		self.ieVersion = self.getIEVersion();
		self.ieRealVersion = self.getIERealVersion();
		
		document.write("<!--[if IE]<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge;\" /><![endif]-->\r\n");
		
		//加载框架所需css
		if (typeof($CUBE_THEME) != "undefined" && $CUBE_THEME != "") {
			self.theme = $CUBE_THEME;
			self._includeFrameworkCSS("bootstrap-" + $CUBE_THEME.toLowerCase() + "2.3.2" + minsuf + ".css");
		} else {
			self._includeFrameworkCSS("bootstrap2.3.2" + minsuf + ".css");
		}
		self._includeFrameworkCSS("bootstrap-ie6" + minsuf + ".css", 6);
		
		if (typeof($CUBE_RESPONSIVE) != "undefined" && $CUBE_RESPONSIVE == true) {
			document.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n");
			self._includeFrameworkCSS("bootstrap-responsive.css");
		}
		
		self._includeFrameworkCSS("ie" + minsuf + ".css", 6);
		self._includeFrameworkCSS("font-awesome" + minsuf + ".css");
		self._includeFrameworkCSS("font-awesome-ie7.min.css", 7);
		
		if (typeof($CUBE_THEME) != "undefined" && $CUBE_THEME != "") {
			self._includeFrameworkCSS("cube-" + $CUBE_THEME.toLowerCase() + minsuf + ".css");
		} else {
			self._includeFrameworkCSS("cube" + minsuf + ".css");
		}
		
		self._includeFrameworkCSS("cropper.min.css");
        self._includeFrameworkCSS("override.css");
		
		//加载框架所需JS
		if (typeof jQuery == 'undefined') {
			self._includeFrameworkJs("libs/jquery-1.11.1.min.js");
		}
		
		self._includeFrameworkJs(self.debugMode?"libs/json2.js":"libs/min/json2.min.js");
		self._includeFrameworkJs(self.debugMode?"libs/knockout-3.2.0.js":"libs/min/knockout-3.2.0.min.js");
		self._includeFrameworkJs(self.debugMode?"libs/knockout.mapping.js":"libs/min/knockout.mapping.min.js");
		self._includeFrameworkJs(self.debugMode?"libs/knockout.cube.js":"libs/min/knockout.cube.min.js");
		self._includeFrameworkJs(self.debugMode?"libs/pubsub.js":"libs/min/pubsub.min.js");
		
		self._includeFrameworkJs(self.debugMode?"utils/ObjectUtil.js":"utils/min/ObjectUtil.min.js");
		
		if (self.isIE6()) {
			self._includeFrameworkJs(self.debugMode?"libs/bootstrap-ie.js":"libs/min/bootstrap-ie.min.js");
		}
		
		if (typeof($CUBE_DEBUGLESS) != "undefined" && $CUBE_DEBUGLESS == true) {
			self.debugLess = $CUBE_DEBUGLESS;
			self._includeFrameworkLess("custom-bootstrap.less");
			self._includeFrameworkLess("custom-cube.less");
			self._includeFrameworkJs("libs/less.min.js");
		}
		
		document.createElement("section");
	}

	/**
	 * 加载框架所需css
	 * @ignore
	 */
	self._includeFrameworkCSS = function(name, ieVersion) {
		if (typeof ieVersion == "undefined" || (self.ieVersion > 0 && self.ieVersion <= ieVersion)) {
			document.write("<link rel='stylesheet' href='" + self.rootPath + "/cube/resources/css/" + name + "'>");
		}
	}
	
	/**
	 * 调试LESS时加载框架less
	 * @ignore
	 */
	self._includeFrameworkLess = function(name) {
		document.write("<link rel='stylesheet/less' type='text/css' href='" + self.rootPath + "/cube/resources/css/" + name + "'>");
	}
	
	/**
	 * 加载框架所需JS
	 * @ignore
	 */
	self._includeFrameworkJs = function(name) {
		document.write("<script type='text/javascript' src='" + self.rootPath + "/cube/scripts/" + name + "'></script>");
	}
	
	/**
     * 加载国际化资源脚本。
     * @ignore
     */
    self.includeMessages = function(p_path) {
    	var ppath = self.localePath;
    	if (typeof p_path != "undefined"){
    		ppath = p_path;
    	}
    	
    	require([self.rootPath + "/" + ppath + "/resources/locales/" + self.locale + "/messages.js"]);
    };
    
    /**
     * 返回一个字符串，表示国际化信息。
     * 
     * @param p_key
     *            一个字符串，表示指定的键。
     * @param [p_args=null]
     *            一个数组或 JSON 对象，传递给key对应的value中{0}、{1}...的参数。
     */
    self.msg = function(p_key, p_args) {
    	var p_string = cube.messages[p_key];
        if (p_string &&　p_string.indexOf("{") != -1 && p_string.indexOf("}") != -1){
        	if (typeof p_args == "string" || typeof p_args == "number"){
        		p_string = p_string.replace("{0}", p_args);
        	} else if ($.isArray(p_args) || $.isPlainObject(p_args)){
                for (var i in p_args) {
                	p_string = p_string.replace("{" + i + "}", p_args[i]);
                }
            }
        }
    	return p_string;
    }
    
    
    /**
     * 显示错误提示窗口。
     * 
     * @param title 窗口标题
     * @param content 窗口内容
     * 
     */
    var _errorNode = null;
    self.showError = function(title, content){
    	if(arguments.length == 1){
    		content = title;
    		title = "错误信息";
    	}
    	
    	if (self.errorType && self.errorType == "friendly"){
    		return;
    	}else if(self.errorType && self.errorType == "console"){
    		if (typeof(console) != "undefined" &&  typeof(console.error) != "undefined"){
	            console.error(title + ":" + content);
	            return;
	        }
    	}
    	//依赖组件注册
		self.importComponent("dialog.msgdialog");
		
		if (_errorNode != null) {
			_errorNode.remove();
		}
		
		var zIndex = 1070 + self.dialogCount*20;
		
		_errorNode = self.bindComponent("msgdialog", {
			isShowDialog: true,
			title: title, 
			content: content,
			msgType: "error",
			zIndex: zIndex,
			__comtype: "msgdialog"
		});
    }
    
    /**
     * 显示confirm提示窗口
     * @param title 窗口标题
     * @param content 窗口内容
     * @param callback 窗口确定按钮事件
     * @param isShowCancelBtn 是否显示取消按钮，默认为true
     */
    self.confirm = function(title, content, callback, isShowCancelBtn) {
    	 var zIndex = 1070 + self.dialogCount*20;
    	 var id = new Date().getTime();
    	 
    	//依赖组件注册
		self.importComponent("dialog.msgdialog");
		
		if (_errorNode != null) {
			_errorNode.remove();
		}
		
		_errorNode = document.createElement("div"); 
		_errorNode = $(_errorNode);
		$("body").append(_errorNode);
		
		_errorNode.attr("id",  id);
		
		var bindings = {
			component: {
				name: "msgdialog",
				params: {
					isShowDialog: true,
					title: title || " ", 
					content: content,
					msgType: "confirm",
					zIndex: zIndex,
					isShowCancelBtn: isShowCancelBtn,
					onConfirmBtnClick: callback,
					eleId: id,
					__comtype: "msgdialog"
				 }
			}
		}
		
		self.bindComponentByNode(_errorNode, bindings);
    }
    
    /**
     * 显示信息提示窗口
     *  @param type 窗口类型，一个字符串（可选：info、warning、error, confirm，默认为info），或一个对象（参考msgdialog组件API属性）
     *  @param content 窗口内容
     *  @param callback 窗口确定按钮事件，type为confirm有效
     *  @param title 窗口标题
     *  @param isShowCancelBtn 是否显示取消按钮，默认为true
     */
    self.indicate = function(type, content, callback, title, isShowCancelBtn) {
    	 var zIndex = 1070 + self.dialogCount*20;
    	 var id = new Date().getTime();
    	
    	 var params = null;
    	 if (typeof(type) == "object") {
    		 params = type;
    		 
    		 if (typeof(params.isShowDialog) == "undefined") {
    			 params.isShowDialog = true;
    		 }
    		 
    		 if (typeof(params.showTime) == "undefined") {
    			 params.showTime = (type == "confirm" ? 0 : 3000);
    		 }
    		 
    		 params.zIndex = zIndex;
    		 params.eleId = id,
    		 params.__comtype = "msgdialog";
    	 } else {
    		 if (type == "prompt") {
        		 type = "confirm";
        	 }
        	 
        	 var types = ["info","warning","error","confirm"];
        	 if (types.indexOf(type) == -1) {
        	 	content = type;
        	 	type = "info";
        	 }
        	 
        	 if (arguments.length == 3 && typeof(callback) != "function") {
        		 title = callback;
        	 }
        	 
        	 params = {
				isShowDialog: true,
				title: title || " ", 
				content: content,
				msgType: type,
				showTime: type == "confirm" ? 0 : 3000,
				zIndex: zIndex,
				isShowCancelBtn: isShowCancelBtn,
				onConfirmBtnClick: callback,
				eleId: id,
				__comtype: "msgdialog"
			 }
    	}
    	
    	 
    	//依赖组件注册
		self.importComponent("dialog.msgdialog");
		
		if (_errorNode != null) {
			_errorNode.remove();
		}
		
		_errorNode = document.createElement("div"); 
		_errorNode = $(_errorNode);
		$("body").append(_errorNode);
		
		_errorNode.attr("id",  id);
		
		var bindings = {
			component: {
				name: "msgdialog",
				params: params
			}
		}
		
		self.bindComponentByNode(_errorNode, bindings);
    }
    
    /**
     * 显示消息提示框
     * @param srcDom 触发弹框的元素，可选
     * @param options 一个json对象，设置参数，参考popoverdialog组件API属性
     */
    self.showPopDialog = function(srcDom, options){
    	//依赖组件注册
		self.importComponent("dialog.popoverdialog");
		
		var id = new Date().getTime();
		
		var node = document.createElement("div"); 
		node = $(node);
		$("body").append(node);
		
		if (arguments.length == 1) {
			options = srcDom;
		}
		
		options.eleId = id;
		node.attr("id",  id);
		
		if (typeof(options.isShowDialog) == "undefined") {
			options.isShowDialog = true;
		}
		
		if (typeof(options.zIndex) == "undefined") {
			options.zIndex = 1060 + self.dialogCount*20;
		}
		
		if (srcDom instanceof jQuery) {
			srcDom = srcDom.get(0);
		}
		
		if (srcDom && srcDom.getBoundingClientRect) {
			var popoverDirection = options.popoverDirection;
			if (!popoverDirection) {
				popoverDirection = "right";
			}
			var content = options.content;
			var scrollTop = document.body.scrollTop? document.body.scrollTop:document.documentElement.scrollTop;
			var scrollLeft = document.body.scrollLeft? document.body.scrollLeft:document.documentElement.scrollLeft;
			var offsetWidth = srcDom.offsetWidth;
			
			switch (popoverDirection) {
			case "top": 
				var top = srcDom.getBoundingClientRect().top + scrollTop;
				var left = srcDom.getBoundingClientRect().left + scrollLeft;
				options.top = top + "px";
				options.left = left + "px";
				options.eleWidth = offsetWidth;
				break;
			case "right": 
				var top = srcDom.getBoundingClientRect().top + scrollTop;
				var right = srcDom.getBoundingClientRect().right + scrollLeft;
				options.top = top-6 + "px";
				options.left = right + "px";
				break;
			case "left": 
				var top = srcDom.getBoundingClientRect().top + scrollTop;
				var right = srcDom.getBoundingClientRect().right + scrollLeft;
				var width = srcDom.clientWidth;
				options.top = top-6 + "px";
				options.right = right + width + 55 + "px";
				options.left = "inherit";
				break;
			case "bottom": 
				var bottom = srcDom.getBoundingClientRect().bottom + scrollTop;
				var left = srcDom.getBoundingClientRect().left + scrollLeft;
				options.top = bottom + "px";
				options.left = left + "px";
				options.eleWidth = offsetWidth;
				break;
			}
		} else {
			options.isFixed = true;
		}
		
		options.__comtype = "popoverdialog";
		
		var bindings = {
			component: {
				name: "popoverdialog",
				params: options
			}
		}
		
		self.bindComponentByNode(node, bindings);
		return node;
    }
    
    /**
     *  创建一个模态窗口
     *  @param options 一个json对象，设置窗口参数，参考modaldialog组件API属性，注意当isShowDialog属性为true或不设置时显示窗口。
     *  例：
     *  cube.showDialog({
     *  	isShowDialog: true,
     *  	title:"窗口标题",
     *  	content: "窗口内容"
     *  });
     */
    self.showDialog = function(options){
    	self.dialogCount++;
    	//依赖组件注册
		self.importComponent("dialog.modaldialog");
		
		var crosDomain = false;
		var id = new Date().getTime();
		
		var doc = document;
		if (window.name && window.name.indexOf("iframe_") == 0) {
			try {
				if (top.document && top.cube) {
					doc = top.document;
					self.dialogCount = self.dialogCount + top.cube.dialogCount;
				}
			} catch (e) {
				crosDomain = true;
			}
		}
		
		var node = doc.createElement("div"); 
		node = $(node);
		$("body", doc).append(node);
		
		if (typeof(options.eleId) == "undefined") {
			options.eleId = id;
		} else {
			id = options.eleId;
		}
		
		node.attr("id",  id);
		
		if (typeof(options.isShowDialog) == "undefined") {
			options.isShowDialog = true;
		}
		
		if (typeof(options.zIndex) == "undefined") {
			options.zIndex = 1050 + self.dialogCount*20;
		}
		
		options.__comtype = "modaldialog";
		
		if (crosDomain) {
			self._showCrosDialog(options);
		} else {
			var bindings = {
				component: {
					name: "modaldialog",
					params: options
				}
			}
			
			self.bindComponentByNode(node, bindings);
		}
		
		return node;
    }
    
    /**
     *  创建一个遮罩层
     *  @param p_options 一个json对象，设置遮罩层参数，非必须，参考loading组件API属性，注意当isShow属性为true或不设置时显示。
     *  @param p_node 一个dom节点的jQuery对象，非必须，当该参数不为空时，遮罩层在该节点范围内（遮罩层为绝对定位，因此受该节点position样式影响）。
     *  @param p_show 一个boolean值，非必须，当p_show为false时清除遮罩层。
     *  
     *  例：
     *  cube.showLoading();//显示遮罩层
     *  cube.showLoading(false);//清除遮罩层
     *  
     *  cube.showLoading({
     *  	content: "加载中..."
     *  });//显示遮罩层
     *  cube.showLoading(false);//清除遮罩层
     *  
     *  cube.showLoading($("#content"));//显示遮罩层，content的position样式为relative
     *  cube.showLoading($("#content"), false);//清除遮罩层
     *  
     */
    var loadingNode = null;
    self.showLoading = function(p_options, p_node, p_show) {
    	if (cube.isBoolean(p_options)) {
    		p_show = p_options;
    		p_options = {};
    	} if (p_options instanceof jQuery) {
    		if (cube.isBoolean(p_node)) {
    			p_show = p_node;
    		}
    		p_node = p_options;
    		p_options = {};
    	} else if (cube.isEmpty(p_options)) {
    		p_options = {};
    	}
    	
    	if (cube.isBoolean(p_node)) {
    		p_show = p_node;
    		p_node = null;
    		if (!cube.isObject(p_options)) {
    			p_node = p_options;
    		}
    	}
    	
    	if (p_show === false) {
    		if (loadingNode != null) {
    			loadingNode.remove();
    		}
    		
    		if (p_node != null) {
    			p_node = $(p_node);
    			var loadingId = p_node.attr("loadingId");
    			if (cube.notEmpty(loadingId)) {
    				var n = p_node.children("#"+loadingId);
    				if (n != null) {
    					n.remove();
    				}
    			}
    		}
    	} else {
    		self.dialogCount++;
        	//依赖组件注册
    		self.importComponent("controls.loading");
    		
    		var id = new Date().getTime();
    		var node = $("<div></div>"); 
    		node.attr("id",  id);
    		
    		if (cube.isEmpty(p_node)) {
    			var doc = document;
    			if (window.name && window.name.indexOf("iframe_") == 0) {
    				try {
    					if (top.document && top.cube) {
    						doc = top.document;
    						self.dialogCount = self.dialogCount + top.cube.dialogCount;
    					}
    				} catch (e) {
    					
    				}
    			}
    			$("body", doc).append(node);
    			loadingNode = node;
    		} else {
    			$(p_node).append(node);
    			$(p_node).attr("loadingId", id);
    		}
    		
    		if (typeof(p_options.isShow) == "undefined") {
    			p_options.isShow = true;
    		}
    		
    		if (typeof(p_options.zIndex) == "undefined") {
    			p_options.zIndex = 1050 + self.dialogCount*20;
    		}
    		
    		p_options.__comtype = "loading";
    		
    		var bindings = {
    			component: {
    				name: "loading",
    				params: p_options
    			}
    		}
    		
    		self.bindComponentByNode(node, bindings);
    		
    		return node;
    	}
    }
    
    /**
     * 显示右键菜单
     * @param event 鼠标右键事件
     * @param options 一个json对象，设置参数，参考contextmenu组件API属性
     */
    var contextmenuNode = null;
    self.showContextmenu = function(event, options) {
    	//依赖组件注册
		self.importComponent("controls.contextmenu");
		
		if (contextmenuNode != null) {
			contextmenuNode.remove();
		}
		
		var id = new Date().getTime();
		
		var node = document.createElement("div"); 
		node = $(node);
		$("body").append(node);
		contextmenuNode = node;
		
		options.eleId = id;
		node.attr("id",  id);
		
		if (typeof(options.isShowMenu) == "undefined") {
			options.isShowMenu = true;
		}
		
		if (typeof(options.zIndex) == "undefined") {
			options.zIndex = 1060 + self.dialogCount*20;
		}
		
		options.popoverDirection = "right";
		options.top = event.clientY + "px";
		options.left = event.clientX + "px";
		
		options.__comtype = "contextmenu";
		
		var bindings = {
			component: {
				name: "contextmenu",
				params: options
			}
		}
		
		self.bindComponentByNode(node, bindings);
		return node;
    }
    
    /**
	 * @ignore
	 */
    self._showCrosDialog = function(options) {
    	var onmessage = function(event){
			var data = event.data;
			var origin = event.origin;
			data = JSON.parse(data);
			var eventType = data.eventType;
			delete data.eventType;
			
			if (typeof options.onCloseBtnClick != "undefined") {
				options.onCloseBtnClick(data);
			}
			
			if (eventType == "onCloseBtnClick" && typeof options.onCloseBtnClick != "undefined") {
				options.onCloseBtnClick(data);
			} else if (eventType == "onConfirmBtnClick" && typeof options.onConfirmBtnClick != "undefined") {
				options.onConfirmBtnClick(data);
			} else if (typeof(options.customBtns) != "undefined") {
				for (var i = 0;i < options.customBtns.length; i++) {
					if (data.text == options.customBtns[i].text) {
						options.customBtns[i].click();
						break;
					}
				}
			}
		}
		
		if (window.addEventListener) {
			window.addEventListener("message", onmessage, false);
		} else {
			window.attachEvent("onmessage", onmessage);
		}
		
		var templateOptions = options.templateOptions;
		if (templateOptions) {
			var p_vvm = templateOptions.name;
			if (p_vvm.indexOf("http") != 0) {
				var pathStrs = p_vvm.split('.');
				if (pathStrs.length >= 2) {
					var _webpagePath = 'pages/' + pathStrs[1]+'/';
					if (self.bundleName != "") {
						_webpagePath = self.bundleName + "/" + _webpagePath;
					}
					
					for (var i = 1; i < pathStrs.length-1; i++) {
						_webpagePath += pathStrs[i]+'/';
					}
					_webpagePath += pathStrs[pathStrs.length-1];
				}
			}
			
			templateOptions.name = self.rootPath + "/" + _webpagePath;
			templateOptions.type = "viewModel";
		}
		
		options.__dialog__ = true;
		top.postMessage(JSON.stringify(options), "*");
    }
    
    /**
     * 返回一个url对应的地址。
     * 
     * @param p_url
     *            一个字符串，表示特定的地址。 
     *            <p>
     *            该参数的形式包括:
     *            <ul>
     *            <li><b>~/</b> - 以“~/”开头表示获取项目的根目录地址或网站虚拟路径。</li>
     *            <li><b>./</b> - 以“./”开头表示获取 cube 的根目录地址。</li>
     *            <li><b>$/</b> - 以“$/”开头表示获取 cube 的 resources 目录地址。</li>
     *            <li><b>$locale/</b> - 以“$locale/”开头表示获取 cube 的 resources/locales 目录地址。</li>
     *            </ul>
     *            </p>
     * @example
     * 假如前项目
     * 根目录为：http://localhost:8080/project，
     * 
     * cube.mappath("~/style.css");//返回值：http://localhost:8080/project/style.css
     * cube.mappath("./style.css");//返回值：http://localhost:8080/project/cube/style.css
     * cube.mappath("$/style.css");//返回值：http://localhost:8080/project/cube/resources/style.css
     * cube.mappath("$locale/zh_CN/messages.js");//返回值：http://localhost:8080/project/cube/resources/locales/zh_CN/messages.js
     */
    self.cubepath = function(p_url) {
        if (typeof (p_url) != "string") return null;
        
        var url = p_url;
        if (url.indexOf("~/") == 0) {
            url = self.rootPath + url.substring(1);
        } else if (url.indexOf("./") == 0) {
            url = self.rootPath + "/cube" + url.substring(1);
        } else if (url.indexOf("$/") == 0) {
            url = self.rootPath + "/cube/resources" + url.substring(1);
        } else if (url.indexOf("$locale/") == 0) {
            url = self.rootPath + "/cube/resources/locales/" + self.locale + url.substring(7);
        }

        return url;
    };
    
     /**
     * 返回一个场景对应的地址。（需提前设置cube.bundleName与cube.name属性，不设置时默认为空）
     * 
     * @param p_url
     *            一个字符串，表示特定的地址。 
     *            <p>
     *            该参数的形式包括:
     *            <ul>
     *            <li><b>~/</b> - 以“~/”开头表示获取当前模块（bundleName）的目录地址。</li>
     *            <li><b>~/../</b> - 以“~/../”开头表示获取当前项目的根目录地址。。
     *            <li><b>./</b> - 以“./”开头表示获取当前场景的根目录地址。</li>
     *            <li><b>$/</b> - 以“$/”开头表示获取当前场景的 resources 目录地址。</li>
     *            <li><b>$locale/</b> - 以“$locale/”开头表示获取当前场景的 resources/locales 目录地址。</li>
     *            <li><b>其余</b> - 其余请参见 cube.cubepath 方法。</li>
     *            </ul>
     *            </p>
     * @example
     * 假如前项目
     * 根目录为：http://localhost:8080/project，
     * cube.bundleName为：personal，
     * cube.name为：department
     * 
     * cube.mappath("~/style.css");//返回值：http://localhost:8080/project/personal/style.css
     * cube.mappath("~/../style.css");//返回值：http://localhost:8080/project/style.css
     * cube.mappath("./style.css");//返回值：http://localhost:8080/project/personal/department/style.css
     * cube.mappath("$/style.css");//返回值：http://localhost:8080/project/personal/department/resources/style.css
     * cube.mappath("$locale/zh.js");//返回值：http://localhost:8080/project/personal/department/resources/locales/zh.js
     */
    self.mappath = function(p_url) {
        if (typeof (p_url) != "string") return null;

        var rootPath = self.rootPath; 
        if(rootPath.lastIndexOf("/") != rootPath.length - 1) {
        	rootPath += "/";
        }
        
    	var bundlePath = rootPath;
		bundlePath += self.bundleName != "" ? (self.bundleName + "/") : "";
		
        if (p_url.indexOf("~/../") == 0) {
            return rootPath + p_url.substr(5);
        } else if (p_url.indexOf("~/") == 0) {
            return bundlePath + p_url.substr(2);
        } else if (p_url.indexOf("./") == 0) {
            return bundlePath + self.name + p_url.substr(1);
        } else if (p_url.indexOf("$/") == 0) {
            return bundlePath + self.name + "/resources" + p_url.substr(1);
        } else if (p_url.indexOf("$locale/") == 0) {
            return bundlePath + self.name + "/resources/locales/"
                    + self.locale + p_url.substr(7);
        } else {
            return self.cubepath(p_url);
        }
    };
    
    /**
     * 获取随机数
     */
    self.random = function (p_number){
    	var seed = new Date().getTime();
    	seed = (seed * 9301 + 49297) % 233280;
    	if (p_number == null || parseInt(p_number) == NaN)
    		return Math.ceil(seed/233280.0 * 10000000000000000);
    	else
    		return Math.ceil(seed/233280.0 * parseInt(p_number));
    }
	
	/**
	 * 判断浏览器是否支持html5
	 */
	self.isSupportHtml5 = function() {
		if (window.applicationCache) {
            return true;
        } else {
            return false;
        }
	};
	
	/**
	 * 判断浏览器是否IE6
	 */
	self.isIE6 = function() {
		return navigator.userAgent.toLowerCase().indexOf('msie 6.0') > -1;
	};
	
	/**
     * 获取IE浏览器版本号
     */
    self.getIEVersion = function() {
        var rv = -1;
        if (navigator.appName == 'Microsoft Internet Explorer') {
            var ua = navigator.userAgent;
            var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
            if (re.exec(ua) != null){
            	rv = parseFloat(RegExp.$1);
            }
    	} else if ((/Trident\/7\./).test(navigator.userAgent)){
    		rv = 11;
    	}
        
        return rv;
    }
    
    /**
     * 获取IE真实版本
     */
    self.getIERealVersion = function() {
    	var ieVersion = document && (function() {
	        var version = 3, div = document.createElement('div');

	        while (
	            div.innerHTML = '<!--[if gt IE ' + (++version) + ']><i></i><![endif]-->',
	            iElems = div.getElementsByTagName('i'),
	            iElems[0]
	        ) {}
	        return version > 4 ? version : -1;
	    }());
		
		return ieVersion;
    }
	
	//-----以下为knockout相关------
	
	/**
	 * 已注册组件
	 * @ignore
	 */
	self.importedComponents = []; 
	
	/**
	 * 已注册vvm
	 * @ignore
	 */
	self.loadedPagePartVVM = []; 
	
	/**
	 * pureComputed
	 */
	self.compWritable = function(p_obj) {
		return ko.pureComputed(p_obj);
	};
	
	/**
	 * computed
	 */
	self.notPureCompWritable = function(p_obj) {
		return ko.computed(p_obj);
	};
	
	/**
	 * pureComputed
	 */
	self.comp = function(p_func, p_obj) {
		return ko.pureComputed(p_func,p_obj);
	};
	
	/**
	 * computed
	 */
	self.notPureComp = function(p_func, p_obj) {
		return ko.computed(p_func,p_obj);
	};
	
	/**
	 * 将对象转换为可监控跟踪状态
	 * @param p_obj 对象
	 */
	self.obj = function(p_obj) {
		if(typeof p_obj == "function") {
			return p_obj;
		}
		return ko.observable(p_obj);
	}
	
	/**
	 * 判断对象是否为observable状态
	 */
	self.isObservable = function(value) {
		return ko.isObservable(value);
	}
	
	/**
	 * 将数组转换为可监控跟踪状态
	 * @param p_arr 数组对象
	 * @returns 监控状态的数组
	 */
	self.array = function(p_arr) {
		if(typeof p_arr == "function") {
			return p_arr;
		}
		return ko.observableArray(p_arr);
	}
	
	/**
	 * 属性订阅
	 * @param p_observable：处于监控状态的属性
	 * 		  p_callback：回调函数，当属性发生变化时将调用该函数
	 * 		  p_event：触发事件名称	
	 */
	self.subscribe = function(p_observable, p_callback, p_callbackTarget, p_event) {
		if(p_observable && typeof p_observable == "function" && typeof p_observable.subscribe == "function"){
			return p_observable.subscribe(p_callback, p_callbackTarget, p_event);
		}
		return null;
	}
	
	/**
	 * 调用属性订阅，如果属性订阅为默认事件，通过直接为属性赋值即可调用订阅，该方法主要用于自定义事件的订阅调用
	 * @param 
	 * 		  p_observable：已经订阅事件的监控属性
	 * 		  p_value：传递的参数
	 * 		  p_event：触发事件名称	
	 */
	self.notifySubscribers = function(p_observable, p_value, p_event) {
		if(p_observable && typeof p_observable == "function" && typeof p_observable.notifySubscribers == "function"){
			return p_observable.notifySubscribers(p_value, p_event);
		}
		return null;
	}
	
	/**
	 * 绑定页面，开始视图展现
	 * @param p_pvm ViewModel
	 */
	self.startWebPage = function(p_pvm) {
		self.includeMessages();
		if (!self.debugMode && self.oneComponentsmin) {
			
			if (self.loadExtComponentsmin) {
				require([self.rootPath + "/cube/components/components.ext.js"]);
		        require(['text!cube/components/components.ext.html'],function (template) {
		            self.templateext = $(template);
		        })
			}
	        
			require([self.rootPath + "/cube/components/components.min.js"]);
	        require(['text!cube/components/components.min.html'],function (template) {
	            self.template = $(template);
	            ko.applyBindings(p_pvm);
	        })
	        
		} else {
			ko.applyBindings(p_pvm);
		}
		
		self._setAjax();
		self._setAllowCrosDialog();
	}
	
	/**
	 * 注册公共组件
	 * @param p_componentName: 组件名称: componentType.componentName
	 *        注意：componentName必须全局唯一。
	 */
	self.importComponent = function(p_componentName) {
		if(self.debugMode && typeof(console) != "undefined" && typeof(console.log) != "undefined")
			console.log("加载组件："+p_componentName);
		if(p_componentName == null) {
			return;
		} else{
			var _componentParams = p_componentName.split('.');
			if(_componentParams.length==2) {
				var _componentType = _componentParams[0];
				var _componentName = _componentParams[1];
				if(self.importedComponents.indexOf(_componentName)  < 0) {
					try {
						if (self.debugMode || !self.oneComponentsmin) {
							ko.components.register(_componentName, {
								viewModel :  {
									require : self.rootPath+'/cube/components/'+_componentType+'/'+_componentName+'/'+_componentName + (self.debugMode ? "" : ".min") + '.js'
								},
								template : {
									require : 'text!'+self.rootPath+'/cube/components/'+_componentType+'/'+_componentName+'/'+_componentName+'.html'
								}  
							});
						} else {
							ko.components.register(_componentName, {
								viewModel :  {
									require : _componentName
								},
								template : {
									id: _componentName
								}  
							});
						}
						self.importedComponents.push(_componentName);
					} catch(e) {
						alert(e);
					}
				} else {
					if(self.debugMode && typeof(console) != "undefined" && typeof(console.log) != "undefined")
						console.log('组件'+_componentName+'已经注册。');
				}
			} else {
				alert('组件参数错误：'+p_componentName);
			}
		}
	};
	
	/**
	 * 注册webpage内的vvm
	 * @param p_vvm
	 * @ignore
	 */
	self.loadSubWebPageVVM = function(p_vvm) {
		var config = {};
		if(self.debugMode  && typeof(console) != "undefined" && typeof(console.log) != "undefined")
			console.log("加载vvm："+p_vvm);
		if(p_vvm == null) {
			return;
		}
		else if(self.loadedPagePartVVM.indexOf(p_vvm) < 0) {
			if (p_vvm.indexOf("http") == 0) {
				config = {
					viewModel : {
						require : p_vvm + "viewmodel."+(!self.debugMode && self.syncDebugMode?"min.":"")+"js"
					},
					template : {
						require : "text!" + p_vvm + "view." + self.fileSuffix
					}
				}
					
				ko.components.register(p_vvm, config);
				self.loadedPagePartVVM.push(p_vvm);
			} else {
				var pathStrs = p_vvm.split('.');
				if (pathStrs.length >= 2) {
					
					var _webpagePath ='pages/'+pathStrs[0]+'/';
					
					if (self.bundleName != "") {
						_webpagePath = self.bundleName + "/" + _webpagePath;
					}
					
					for (var i = 1; i < pathStrs.length - 1; i++) {
						_webpagePath += pathStrs[i] + '/';
					}
					_webpagePath += pathStrs[pathStrs.length - 1];

					config = {
						viewModel : {
							require : self.rootPath+'/'+_webpagePath+'viewmodel.'+(!self.debugMode && self.syncDebugMode?'min.':'')+'js'
						},
						template : {
							require : 'text!' + self.rootPath+'/'+_webpagePath+'view.' + self.fileSuffix
						}
					}
					
					ko.components.register(p_vvm, config);
					self.loadedPagePartVVM.push(p_vvm);
				} else {
					alert('字符串错误：' + p_vvm);
				}
			}
		} else {
			if(self.debugMode  && typeof(console) != "undefined" && typeof(console.log) != "undefined")
				console.log("webpage vvm: "+p_vvm+"已经注册。");
		}
		
		return config;
	};
	
	/**
	 * 初始化组件的视图模型属性。由于外部传入的内容有可能为不可变/可变/未传入等情况，在组件内部统一处理为observable属性。
	 * @param param: 组件viewmode的params参数对应的某一个属性
	 * @param initVal：如果组件没有传入参数，希望设置的初始值
	 * @param type：包括obj和arr两种
	 */
	self.initComponentProperty = function(param, initVal, type) {
		if(type == null)
			type = 'obj';
		if(type == 'obj') {
			if(typeof param == "function") {
				return param;
			} else if(param != null) {
				return cube.obj(param);
			}
			else {
				return cube.obj(initVal);
			}
		} else if(type == 'arr') {
			if(typeof param == "function") {
				return param;
			} else if(param != null) {
				return cube.array(param);
			}
			else {
				return cube.array(initVal);
			}
		}
		return null;
	};
	
	/**
	 * 根据节点获取其绑定的ViewModel
	 * @param node 一个dom节点对象或jquery节点对象
	 */
	self.getPageViewModelByNode = function(node){
		if(node instanceof jQuery){
			node = node.children().get(0);
		}else{
			node = node.childNodes[0];
		}
		
		if (node) {
			return ko.dataFor(node);
		}
		
		return null;
	}
	
	/**
	 * 已有元素绑定组件
	 * @param node：元素
	 * @param name：组件名称
	 * @param params：组件参数
	 */
	self.bindSubWebPageVVM = function(node, name, params){
		if(!node instanceof jQuery){
			node = $(node);
		}
		if(name){
			node.attr("data-bind", "component: {name : '" + name + "',params:" +  (params ? ko.toJSON(params) : "{}") + "}");
		}
		
		ko.applyBindingsToNode(node[0]);
	}
	
	/**
	 * 已有元素绑定组件
	 * @param node：元素
	 * @param bindings：要绑定的对象
	 */
	self.bindComponentByNode = function(node, bindings){
		if(!node instanceof jQuery){
			ko.applyBindingsToNode(node, bindings);
		}else{
			ko.applyBindingsToNode(node[0], bindings);
		}
	}
	
	/**
	 * 给节点绑定ViewModel
	 * @param node：节点对象
	 * @param viewModel：ViewModel
	 */
	self.bindTemlate = function(node, viewModel){
		if(node instanceof jQuery){
			node = node.get(0);
		}
		
		ko.applyBindingsToNode(node, null, viewModel);
	}
	
	/**
	 * 以创建组件标签形式绑定组件
	 * @param name：节点名称
	 * @param params：组件参数
	 */
	self.bindComponent = function(name, params){
		var node = document.createElement(name); 
		node = $(node);
		$("body").append(node);
		var id = new Date().getTime();
		params.eleId = id;
		
		node.attr("id",  id);
		node.attr("params",  ko.toJSON(params));
		ko.applyBindingsToNode(node[0]);
		return node;
	}
	
	/**
	 * 给节点及子节点绑定ViewModel
	 * @param node：节点对象
	 * @param viewModel：ViewModel
	 */
	self.applyBindings = function(node, viewModel){
		if(node instanceof jQuery){
			node = node.get(0);
		}
		
		if (node) {
			ko.applyBindings(viewModel, node);
		}
	}
	
	/**
	 * 如果每个pages资源通过webpage.js进行引入，则可以调用该方法。
	 *  @param params为一个js对象，
	 *  例：
	 *  {
	 *     components: [],
	 *     vvms: []
	 *  }
	 */
	self.loadWebpage = function(params) {
		var _components = params.components == null? []: params.components;
		//当前webpage关联vvm
		var _vvms = params.vvms == null? []: params.vvms;
		
		$.each(_components,function(){
			cube.importComponent(this.toString());
		});
		$.each(_vvms,function(){
			cube.loadSubWebPageVVM(this.toString());
		});
	}
	
	/**
	 * @ignore
	 */
	self._setAjax = function() {
		//允许跨域请求
		requirejs.config({
			config:{
				text:{
					useXhr: function (url, protocol, hostname, port) {
						return true;
					}
				}
			}
		});
		
		if (self.ajaxSetupOptions == null) {
			self.ajaxSetupOptions = {
	            global: true,
	            complete: function(e) {
	                var sessionstate = e.getResponseHeader("sessionstate");
	                var redirecturl = e.getResponseHeader("redirecturl");
	                
	                if ((sessionstate != null && sessionstate == 'timeout') || e.responseText == "timeout") {
	                	cube.indicate("info", cube.msg("SESSION_TIMEOUT"));
	                	if (redirecturl != null) {
	                		redirecturl = encodeURI(redirecturl);
	                		redirecturl = redirecturl.replace("&","&amp;").replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#039;").replace(/javascript/gi, "");
	                		if (!redirecturl.toLowerCase().indexOf("http://") && !redirecturl.toLowerCase().indexOf("https://") )
	                			top.window.location.href = redirecturl;
	                	} else {
	                    	top.window.location.reload();
	                    }
	                } else if (e.responseText == "authorizeFail") {
	                	cube.indicate("warning", cube.msg("OPERATION_NOT_ALLOWED"));
	                }
	            }
	        }
		}
		$.ajaxSetup(self.ajaxSetupOptions);
		
		 // 指定预处理参数选项的函数
        $.ajaxPrefilter( function(options, originalOptions, jqXHR){
        	var version = self.getIEVersion();
        	if (self.ieCorsProxy && version > -1 && version <= 9.0 && self.crosDomain(options.url))
        	{
        		var url = options.url;
        		var beforeSend = options.beforeSend;
        		options.beforeSend = function (xhr, option) {
        			if (typeof(beforeSend) == "function") {
        				beforeSend(xhr, option);
        			}
        			xhr.setRequestHeader("X-CorsProxy-Url", url);
                };
                
                options.url = self.mappath(self.ieCorsProxyHost);
                options.crossDomain = false;
        	}
        });
	}
	
	/**
	 * @ignore
	 */
	self._setAllowCrosDialog = function() {
		var onmessage = function(event) {
			var source = event.source;
			var data = event.data;
			var origin = event.origin;
			data = JSON.parse(data);
			if (typeof(data.__dialog__) != "undefined") {
				data.onCloseBtnClick = function(args) {
					args.eventType = "onCloseBtnClick";
					source.postMessage(JSON.stringify(args), "*");
				}
				
				data.onConfirmBtnClick = function(args) {
					args.eventType = "onConfirmBtnClick";
					source.postMessage(JSON.stringify(args), "*");
				}
				
				if (typeof(data.customBtns) != "undefined") {
					for (var i = 0;i < data.customBtns.length; i++) {
						data.customBtns[i].click = function(args) {
							source.postMessage(JSON.stringify(args), "*");
						}
					}
				}
				
				self.showDialog(data);
			}
		}
		
		if (window.addEventListener) {
			window.addEventListener("message", onmessage, false);
		} else {
			window.attachEvent("onmessage", onmessage);
		}
	}
	
	/**
	 * @ignore
	 * 以异步或同步方式进行 REST 服务调用，超时timeout时间为60000
	 * @param p_path 指定的 REST 服务路径，
     * @param p_method 指定 HTTP 方法的名称。如“GET”、“POST”等。
     * @param [p_data] 指定传入的参数信息。通常是一个 JSON 对象。如 { id: 1984, name: "中文" } 会被转换成“id=1984&name=%E4%B8%AD%E6%96%87”作为参数拼入 URL（使用 HTTP POST 方法时，是作为内容传递），其中中文部分会使用 UTF-8 进行编码。
     * @param [p_async=true] 一个 Boolean 值，表示是否采用异步调用。true 表示异步调用；false 表示同步调用。
     * @param [p_callback] 一个 Function 对象，表示回调方法。方法的原型为 function(p_context)，其中 p_context 是一个 JSON 对象，其中 successful 表示调用是否成功，如果成功则返回 true，并且可通过 result 字段获取返回值；如果失败则返回 false，并且可通过 error 字段获取失败的异常对象。
	 * @param [p_isCubeobj] 一个Boolean值。表示当前的json对象是否为cube obj对象。
	 */
	self.interactToServer = function(p_path, p_method, p_data, p_async,  p_isCubeObj, p_callback) {
		
		var url = p_path != null ? p_path : "";
		
        var method = p_method != null ? p_method.toUpperCase() : "GET";

        var async = true;
        if (p_async != null)
        {
        	async = p_async;
        }
        
        //如果是cubeobj，需要先通过ko.toJSON转换为json对象
        var isCubeObj = p_isCubeObj == null ? false : p_isCubeObj;
        
        var ajaxSetting = {
            url: url,
            timeout:60000,
            type: method,
            contentType: "application/json",
            dataType: "json",
            cache: false,
            async: async,
            data: isCubeObj ? ko.toJSON(p_data) : p_data,
            success: function(p_resultValue) {
                if ($.isFunction(p_callback))
                {
                	if(isCubeObj) {
                		var vm = ko.mapping.fromJS(p_resultValue);
                		delete vm.__ko_mapping__;
                		p_callback(vm);
                	} else {
                		p_callback(p_resultValue);
                	}
                }
            },
            error: function(p_request, p_status, p_err) {
                if ($.isFunction(p_callback))
                {
                    //var error = _resolveError(p_request, url, p_status, p_err);
                    var error = new Error(p_request.responseText);
                    error.url= url;
                	var context = {
                        successful: false,
                        error: error
                    };
                    p_callback(context);
                }
            }
        };
        
        var req =  $.ajax(ajaxSetting);
        return req;
	}
	
	//-----以下为pubsub订阅发布相关------
	
	/**
	 *	发布主题消息，将数据传递给它的订阅者
	 *	@param 
	 *		topic: 字符串，发布的消息
	 *		data：传递给订阅者的数据
	 */
	self.publishTopic = function(topic, data){
		return PubSub.publish(topic, data);
	}
	
	/**
	 *	发布同步主题消息，将数据传递给它的订阅者，与publishTopic的区别为：发布消息时立即执行订阅者所有回调函数，publishTopic所有延迟。
	 *	@param 
	 *		topic: 字符串，发布的消息
	 *		data：传递给订阅者的数据
	 */
	self.publishTopicSync = function(topic, data){
		return PubSub.publishSync(topic, data);
	}
	
	/**
	 *	订阅主题
	 *	订阅/发布机制，用于组件间调用传参
	 *	发布时使用如下代码：
	 *		cube.publish('menuStatChanged', e.text );
	 *	参数说明：'menuStatChanged'为发布主题的名称。必选。
	 *			e.text 为参数。必选。
	 *
	 *	订阅时使用如下代码：
	 *		var onMenuChanged = function( topics , data ){ 
	 * 			console.log( topics + ": " + data ); 
	 * 		};
	 * 		cube.subscribeTopic('1', 'menuStatChanged', onMenuChanged );
	 * 	参数说明：
	 * 			'1'为订阅编号。必选。
	 * 			'menuStatChanged'为发布主题的名称。必选。
	 * 			onMenuChanged为需要执行的事件。必选。
	 * 
	 * 	举例说明：当订阅者订阅了'menuStatChanged'主题后，如果发布者发布了'menuStatChanged'主题，
	 * 	那么就会执行订阅者的订阅事件onMenuChanged。每个主题可以由多个订阅者订阅，当发布该主题时，
	 *	所有订阅该主题的订阅者都会执行订阅事件。
	 *  注意事项: 在订阅时，每个主题的多个订阅者存在一个id号，在订阅时需保证id号不重复，如果重复，那么新的订阅者无法订阅成功，需要使用新的id号。
	 *
	 *	@param 
	 *		id: 数字，订阅编号，必须，多个订阅者之间订阅编号不能重复
	 *		topic: 字符串，订阅的主题
	 *		func：回调函数，当新消息发布时将被调用
	 *	@return
	 *		返回唯一的令牌，如果退订时需要用到
	 *
	 */
	self.subscribeTopic = function(id, topic, func){
		return PubSub.subscribe(id, topic, func);
	}
	
	/**
	 *	退订订阅主题
	 *	@param 
	 *		value: 令牌、函数或主题
	 *		当value为token令牌时，删除具体订阅者
	 *		当value为function时，删除所有订阅回调函数为该function的订阅者
	 *		当value为topic主题时，删除所有订阅主题为该topic的订阅者
	 *	@return
	 *		boolean，是否退订成功
	 */
	self.unsubscribeTopic = function(value){
		return PubSub.unsubscribe(value);
	}
	
	/**
	 *	清除所有订阅者
	 */
	self.clearAllSubscribeTopic = function(){
		PubSub.clearAllSubscriptions();
	}
	
	/**
	 *	清除指定主题的订阅者
	 *	@param 
	 *		topic: 字符串，订阅的主题
	 */
	self.clearSubscribeTopic = function(topic){
		PubSub.clearSubscriptions(topic);
	}
	
	//----pubsub订阅发布相关结束------
	
	/**
	 * 清除页面、组件缓存
	 * @param p_componentName：页面组件名称
	 * 
	 * @example
	 * cube.clearComponentCached("datacontainer.datagrid");
	 * cube.clearComponentCached("app_demo.content.department");
	 */
	self.clearComponentCached = function(p_componentName) {
		if (cube.notEmpty(p_componentName)) {
			var viewModel = "";
			var template = "";
			
			if (self.importedComponents.indexOf(p_componentName) != -1) {
				var _componentParams = p_componentName.split('.');
				if (_componentParams.length == 2) {
					var _componentType = _componentParams[0];
					var _componentName = _componentParams[1];
					viewModel = self.rootPath + '/cube/components/' + _componentType+'/' + _componentName + '/' + _componentName + (self.debugMode ? "" : ".min") + '.js'
					template = 'text!' + self.rootPath+'/cube/components/' + _componentType+'/' + _componentName+'/' + _componentName+'.html'
				}
			} else {
				if (p_componentName.indexOf("http") == 0) {
						viewModel = p_vvm + "viewmodel." + (!self.debugMode && self.syncDebugMode?"min.":"")+"js"
						template = "text!" + p_vvm + "view." + self.fileSuffix
				} else {
					var pathStrs = p_componentName.split('.');
					if (pathStrs.length >= 2) {
						var _webpagePath = 'pages/' + pathStrs[0]+'/';
						if (self.bundleName != "") {
							_webpagePath = self.bundleName + "/" + _webpagePath;
						}
						
						for (var i = 1; i<pathStrs.length-1; i++) {
							_webpagePath += pathStrs[i]+'/';
						}
						
						_webpagePath += pathStrs[pathStrs.length-1];
						viewModel = self.rootPath + '/' + _webpagePath + 'viewmodel.' + (!self.debugMode && self.syncDebugMode?'min.':'') + 'js'
						template = 'text!' + self.rootPath+'/' + _webpagePath+'view.' + self.fileSuffix
					}
				}
			}
			
			if (viewModel) {
				requirejs.undef(viewModel);
			}
			
			if (template) {
				requirejs.undef(template);
			}
			
			ko.components.clearCachedDefinition(p_componentName)
		}
	}
	
	/**
	 * 清除节点绑定
	 * @param node：节点
	 */
	self.cleanNode = function(node) {
		if (node instanceof jQuery) {
			node = node.get(0);
		}
		ko.cleanNode(node);
	}
	
	/**
	 * 使用params自动初始化ViewModel属性，同时将属性转变为监控状态
	 * @param viewModel：ViewModel对象
	 * @param params：ViewModel接收的参数
	 */
	self.endViewModel = function(viewModel, params){
		if (cube.isEmpty(viewModel)) {
			return;
		}
		
		if (cube.isEmpty(params)) {
			params = {};
		}
		
		var tagName = params.__tagName;
		var componentDefaultValue = self.componentDefaultValue[tagName] || {};
		
		for ( var key in viewModel){
   		 	var value, argValue = params[key], defaultValue = viewModel[key];
   		 	if (typeof(componentDefaultValue[key]) != "undefined") {
   		 		defaultValue = componentDefaultValue[key];
   		 	}
   		 	
   		 	if (typeof defaultValue === "function" && typeof(componentDefaultValue[key]) == "undefined") {
   		 		continue;
   		 	}
   		 	
            if(typeof argValue != "undefined"){
           	 	value = argValue;
            }else{
           	 	value = defaultValue;
            }
            
            if(value instanceof Array){
           	 	value = self.array(value);
            }else if(typeof value != "undefined" && typeof value !== "function"){
           	 	value = self.obj(value);
            }
            
            viewModel[key] = value;
        }
   	 
   	 	if(typeof viewModel._init === "function" && params._lazyInit !== true){
   	 		viewModel._init();
   	 		viewModel._initialized = true;
        }
	}
	
    /**
     * 判断是否跨域
     * 
     * @param url
     */
    self.crosDomain = function(p_url) {  
    	if (typeof(p_url) == "undefined" || !p_url || (p_url.indexOf("http://") == -1 && p_url.indexOf("https://") == -1)) {
    		return false;
    	}
    	
	    var durl = /((http|https):\/\/[^\/]+)\//i;
	    var domain = p_url.match(durl);  
	    if (domain && domain.length > 1 && domain[1] == (window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: ''))) {
	    	return false;
	    }
	    
	    return true;
	}
    
    /**
     * 获取url中的params参数值，以对象形式返回
     */
    self.getUrlParams = function() {
		var params = null;
		var url = location.href;
		var name = "params";
		
		if (url.indexOf("?") != -1) {
			var arr_url = url.split('?');
			var base = arr_url[0];
			var str = arr_url[1];
			if (str.toLowerCase().indexOf(name) != -1) {
				var index = -1;
				var strs = str.split("&");
				for (var i = 0; i < strs.length; i++) {
					var param = strs[i].split("=");
					if (cube.trim(param[0]).toLowerCase() == name) {
						try {
							params = JSON.parse(cube.trim(param[1]).replace(/%22/g, "\""));
							if (params.dialog) {
								params.__iframe = true;
								
								var onmessage = function(event) {
									var source = event.source;
									var data = event.data;
									var origin = event.origin;
									data = JSON.parse(data);
									if (typeof(data.__result__) != "undefined") {
										if (cube.notEmpty(cube.__params) && cube.notEmpty(cube.__params.dialog)) {
											if (cube.isFunction(cube.__params.dialog.result)) {
												result = cube.__params.dialog.result();
											} else {
												result = cube.__params.dialog.result;
											}
											top.postMessage(JSON.stringify(result), "*");
										}
									}
								}
								
								if (window.addEventListener) {
									window.addEventListener("message", onmessage, false);
								} else {
									window.attachEvent("onmessage", onmessage);
								}
							}
						} catch (e) {
							
						}
						break;
					}
				}
			}
		}
		
		return params;
    }
};
cube = new CUBE();
cube.init();
