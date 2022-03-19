define([], function() {

	/**
	 * 引入页面组件
	 * <code language="html">
	 * 		<include params="url:name"></include>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.name = 'app_cubedemo.componentDemos.layoutDemos.borderlayout.tmp';
	 * 				
	 * 				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**
		 * 页面路径，例如：demo.content.detail，如果url中带有参数（例：demo.content.detail?id=1），参数会自动赋值给params属性。
		 * 页面路径也可设置为全路径，例：http://localhost:8080/project/demo/index.html，全路径时配合type属性使用。
		 * @default
		 * 		null
		 */
		self.url = null;
		
		/**
	     * 获取或设置加载页面的模式。当页面为外部页面时生效（url以http开头）。
	     * <p>
	     * 该字段可选的值包括：
	     * <ul>
	     * <li><b>ajax</b> - 以 ajax 的方式加载url.</li>
	     * <li><b>iframe</b> - 以 iframe 的方式加载url.</li>
	     * <li><b>viewModel</b> - 以 viewModel 的方式加载url.</li>
	     * </ul>
	     * </p>
	     * 
	     * @default ajax
	     */
	    self.type = "ajax";
	    
	    /**
	     * 获取或设置iframe的高度，type 为iframe时起作用。
	     * 
	     * @default 100%
	     */
	    self.iframeHeight = "100%";
		
		/**
		 * @ignore
		 */
		self._url = null;
		
		/**
		 * @ignore
		 */
		self.ajaxHtml = "";
		
		/**
		 * 是否外部页面
		 * @ignore
		 */
		self.isOut = false;
		
		/**
		 * 传递给加载页面ViewModel的参数，一个对象
		 * @default
		 * 		{}
		 */
		self.params = {};
		
		/**
		 * 原生参数
		 * @ignore
		 * 		{}
		 */
		self.oldparams = {};
		
		/**
		 * 解析后的参数
		 * @ignore
		 */
		self._params = {};
		
		/**
		 * @ignore
		 */
		self._init = function() {
			self.oldparams = params.params;
			self.compurl();
			self.urlSub = cube.subscribe(self.url, self.compurl);
		}
		
		/**
		 * @ignore
		 */
		self.compurl = function(){
			var _url = self.url();
			if (cube.notEmpty(_url) && _url.indexOf("http") == 0 && self.type() != "viewModel") {
				self.isOut(true);
			}
			
			if (self.isOut() && "ajax" == self.type()) {
	        	_loadInAjax(_url);
	        } else {
	        	var _params = cube.clone(self.oldparams);
				
				if (_url != null && _url.indexOf("?") != -1) {
					var str = _url.split("?")[1];
					var strs = str.split("&");
					for (var i = 0; i < strs.length; i ++) { 
						var kv = strs[i].split("=");
						_params[kv[0]] = unescape(kv[1]); 
					}
					
					_url = _url.split("?")[0];
				}
				
				self._params(_params);
	        }
			
			self._url(_url);
		}
		
		/**
	     * 以 AJAX 方式加载 url。
	     */
	    function _loadInAjax(p_url) {
	    	$.ajax({
	    		url: p_url,
	    		crossDomain: !sameDomain(p_url),
	    		success: function(response,status,xhr){
	    			var ajaxHtml = response;
	    			self.ajaxHtml(ajaxHtml);
	    		}
	    	});
	    }
	    
	    /**
	     * 域名是否相同
	     */
	    function sameDomain(p_url) {  
	    	if (cube.notEmpty(p_url) && p_url.indexOf("http") != 0)  {
	    		return true;
	    	}
	    	
		    var durl = /((http|https):\/\/[^\/]+)\//i;
		    var domain = p_url.match(durl);  
		    if(domain && domain.length > 1 && domain[1] == (window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: ''))) {
		    	return true;
		    }
		    
		    $.support.cors = true;
		    return false;
		}
		
		cube.endViewModel(self, params);
 	}
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 	   this.urlSub.dispose();
 	}
 	
	return ViewModel;
});