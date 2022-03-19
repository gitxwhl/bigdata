define([], function() {

	/**
	 * 面板组件
	 * <code language="html">
	 * 		<panelcontainer params="
     *      					width: width,
     *      					height: height,
     *      					titleheight: titleheight,
     *      					bgcolor: bgcolor,
     *      					visible: visible,
     *      					content: panelContents,
     *      					customBtns: customBtns
	 *		"></panelcontainer>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.width = "400px";
	 *  			self.height = "400px";
	 *   			self.titleheight = "pannel";
	 *    			self.bgcolor = "red";
	 *     			self.visible = true;
	 * 				self.panelContents = "dddd";//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
	 * 				self.customBtns = [{//标题栏自定义按钮
	 * 					text : "按钮",
	 *				 	visible: true, //不设置时默认为true
	 *			 		btnColor:"red",//文字颜色
	 *				 	backgroundImage:'none',//背景图片
	 *				 	backgroundColor:'blue',//背景颜色
	 *				 	btnIcon: 'map-marker',//图标
	 *				 	click: function() {//点击事件
	 *				           //点击按钮时触发
	 *				       }
	 *				 	}];
	 * 
	 * 				self.templateOptions = {name: 'app_cubedemo.componentDemos.containerDemos.accordion.panel2Templ',
	 *	   									params: {title: '面板2的模板'}}
	 * 				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
    function ViewModel(params) {
        var self = this;
        
        /**
         * 整体宽度
         * @default
         * 		400px
         */
        self.width = "400px";
        
         /**
         * 内容高度
         * @default
         * 		300px
         */
        self.height = "300px";
        
        /**
         * 标题样式 当设置为“title”时，内容高度为0，底边距为
         * @default
         * 		panel
         */
        self.type = "panel";
        
         /**
         * 标题高度
         * @default
         * 		40px
         */
        self.titleheight = "40px";
        
        /**
         * 面板底边距
         * @default
         * 		null
         */
        self.marginBottom = null;
        
         /**
         * 标题背景色
         * @default
         * 		#eee
         */
        self.bgcolor = "#eee";
        
         /**
         * 是否显示标题内容
         * @default
         * 		true
         */
        self.visible = true;
        
         /**
         * 面板内容，当content属性存在时优先显示其内容，否则显示templateOptions配置的页面。
         * 同时该属性值比组件标签嵌套内容优先级高。
         * 
         * @default
         * 		空对象
         */
        self.content = "";
        
        /**
         * 当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
         * 
         * @example
         * 
         * <code language="JavaScript">
		 *	  {
		 *			 name: 'app_cubedemo.componentDemos.containerDemos.panelcontainer.Templ',
		 *		  	 params: {title: 'tab1的模板'}
		 *	  }
		 *  </code>
		 *  
		 *  name为页面路径，params为要传递给该页面对应ViewModel的参数
         * 
         * @default
         * 		空对象
         */
        self.templateOptions = null;
        
         /**
         * 标题内容
         * @default
         * 		空
         */
        self.title = "";
        
		/**
		 * 自定义按钮
		 * [{
		 *		text : "收藏",
		 *      visible: true, //不设置时默认为true
		 *      type: "primary", //按钮样式，可选的值包括：default、primary、success、info、warning、danger、inverse、link
		 *		click: function(dialog) {
		 *          //点击按钮时触发
		 *          dialog.closeDialog();
		 *      }
		 *	}, {
		 *		text : "查看",
		 *		click: function() {
		 *          //点击按钮时触发
		 *      }
		 *	}]
		 *
		 * @default []
		 */
		self.customBtns = [];
        
        /**
		 * @ignore
		 */
		self.ajaxHtml = "";
		
		/**
		 * 是否外部页面
		 * @ignore
		 */
		self.isOut = false;
        
        var _empty = false;
        
        /**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function (){
			var templateOptions = self.templateOptions();
			if (cube.notEmpty(templateOptions)) {
				if(cube.notEmpty(templateOptions.name) && templateOptions.name.indexOf("http") == 0 && templateOptions.type != "viewModel") {
					self.isOut(true);
				}
				
				if (cube.isEmpty(templateOptions.type)) {
					templateOptions.type = "ajax";
				}
				
				if (cube.isEmpty(templateOptions.iframeHeight)) {
					templateOptions.iframeHeight = self.height();
				}
				
				if (self.isOut() && templateOptions.type == "ajax") {
					self.getAjaxHtml(templateOptions.name);
				}
			}
			if(self.type() == 'title'){
				self.height("0px");
				self.marginBottom("0px");
			}
		};
		
		/**
		 * @ignore
		 */
		self.customClick = function(viewMode, event) {
			if (cube.notEmpty(viewMode.click) && typeof(viewMode.click) == "function") {
				viewMode.click(self);
			}
		};
		
        /**
	     * @ignore
	     */
		self.getAjaxHtml = function(p_url) {
			$.ajax({
	    		url: p_url,
	    		crossDomain: cube.crosDomain(p_url),
	    		success: function(response,status,xhr){
	    			var ajaxHtml = response;
	    			self.ajaxHtml(ajaxHtml);
	    		}
	    	});
		};
		
        /**
	     * @ignore
	     */
	    self.onloading = function(node, viewModel) {
	    	var $e = $(node);
	    	var html = $e.contents().not("div.cube.accordion");
	    	var outer = $("<div></div>");
	    	var inner = $("<div></div>");
	    	inner.append(html);
	    	outer.append(inner);
	    	if (self.content() == "" && self.templateOptions() == null) {
	    		_empty = true;
	    		self.content(outer.html());
	    	}
	    	outer.remove();
	    };
	    
		/**
	     * @ignore
	     */
	    self.onload = function(node, viewModel) {
	    	if (_empty){
	    		var $e = $(node);
				cube.applyBindings($e.find(">.accordion>.accordion-group>.accordion-body").children(".accordion-inner").children(".panel-content").children(), viewModel);
	    	}
	    };

        cube.endViewModel (self,params);
    }
    //释放资源，包括compoted/subscrib/setInterval资源等。
    ViewModel.prototype.dispose = function () {
    	
    }
    return ViewModel;
});


