define([], function() {

	/**
	 * Border布局
	 * <code language="html">
	 * 		<borderlayout params="
	 *						top: top,
	 *						main: main,
	 *						center: center,
	 *						right: right,
	 *						bottom: bottom
	 *					"></borderlayout>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.top = {
	 *								content: "top内容",
	 *								height: "100px"
	 *							};
	 * 				self.main = {
	 *								content: "left内容",
	 *								height: "320px",
	 *								width:"20%"
	 *							};
	 * 				self.center = {
	 *								height: "320px",
	 *								width:"58%",
	 *								visible:true,
	 *								templateOptions: {
	 *									name: "app_cubedemo.componentDemos.layoutDemos.borderlayout.tmp",
	 *									params: {}
	 *								}
	 *							};
	 * 				self.right = {
	 *									content: "right内容",
	 *									height: "320px",
	 *								width:"20%"
	 *							};
	 * 				self.bottom = {
	 *								content: "bottom内容"
	 *							};
	 * 				
	 * 				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**顶部设置
		 * @example
		 * <code language="JavaScript">
		 * {
		 * 		width:"100%",
		 * 		height:"100px",
		 * 		visible: true,
		 * 		isShowCloseBtn: true,
		 * 		border:"",
		 * 		content:"内容",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * }
		 * </code>
		 * 
		 * 其中content和templateOptions配置一个即可，若两个都配置，content优先级高。
		 * content表示内容，
		 * isShowCloseBtn表示是否显示页面可关闭按钮
		 * templateOptions表示加载其他页面，templateOptions中的name为页面路径，params为要传递给该页面对应ViewModel的参数
		 */
		self.top = null;
		
		/**左侧设置
		 * @example
		 * <code language="JavaScript">
		 * {
		 * 		width:"100%",
		 * 		height:"100px",
		 * 		visible: true,
		 * 		isShowCloseBtn: true,
		 * 		border:"",
		 * 		content:"内容",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * }
		 * </code>
		 * 
		 * 其中content和templateOptions配置一个即可，若两个都配置，content优先级高。
		 * content表示内容，
		 * isShowCloseBtn表示是否显示页面可关闭按钮
		 * templateOptions表示加载其他页面，templateOptions中的name为页面路径，params为要传递给该页面对应ViewModel的参数
		 */
		self.left = null;
		
		/**中央设置
		 * @example
		 * <code language="JavaScript">
		 * {
		 * 		width:"100%",
		 * 		height:"100px",
		 * 		visible: true,
		 * 		border:"",
		 * 		content:"内容",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * }
		 * </code>
		 * 
		 * 其中content和templateOptions配置一个即可，若两个都配置，content优先级高。
		 * content表示内容，
		 * templateOptions表示加载其他页面，templateOptions中的name为页面路径，params为要传递给该页面对应ViewModel的参数
		 */
		self.center = null;
		
		/**右侧设置
		 * @example
		 * <code language="JavaScript">
		 * {
		 * 		width:"33%",
		 * 		height:"300px",
		 * 		visible: true,
		 * 		isShowCloseBtn: true,
		 * 		border:"",
		 * 		content:"内容",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * }
		 * </code>
		 * 
		 * 其中content和templateOptions配置一个即可，若两个都配置，content优先级高。
		 * content表示内容，
		 * isShowCloseBtn表示是否显示页面可关闭按钮
		 * templateOptions表示加载其他页面，templateOptions中的name为页面路径，params为要传递给该页面对应ViewModel的参数
		 */
		self.right = null;
		
		/**底部设置
		 * @example
		 * <code language="JavaScript">
		 * {
		 * 		width:"100%",
		 * 		height:"100px",
		 * 		visible: true,
		 * 		isShowCloseBtn: true,
		 * 		border:"",
		 * 		content:"内容",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * }
		 * </code>
		 * 
		 * 其中content和templateOptions配置一个即可，若两个都配置，content优先级高。
		 * content表示内容，
		 * isShowCloseBtn表示是否显示页面可关闭按钮
		 * templateOptions表示加载其他页面，templateOptions中的name为页面路径，params为要传递给该页面对应ViewModel的参数
		 */
		self.bottom = null;
		
		/**
		 * 当中央区域没有设置高度时，是否自动设置其高度，超出出现滚动条
		 * @default false
		 */
		self.centerHeightAuto = false;
		
		/**
		 * @ignore
		 */
		self.$center = null;
		
		/**
		 * @ignore
		 */
		self.$left = null;
		
		/**
		 * @ignore
		 */
		self.$right = null;
		
		/**
         * 按钮颜色
         * @default
         * 		null
         */
        self.btnbgcolor = null;
        
		/**
		 * @ignore
		 */
        self._btnheight = null;
        
		/**
		 * @ignore
		 */
		self.topDisplay = true;
		
		/**
		 * @ignore
		 */
		self.bottomDisplay = true;	
		
		/**
		 * @ignore
		 */
		self.rightDisplay = true;	
		
		/**
		 * @ignore
		 */
		self.leftDisplay = true;
		
		/**
		 * @ignore
		 */
		self.centerWidth = null;
		
		var _topPage = false;
		var _leftPage = false;
		var _centerPage = false;
		var _rightPage = false;
		var _bottomPage = false;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			if(cube.notEmpty(self.center())){
				var centerWidth = self.center().width;
				self.centerWidth = cube.obj(centerWidth);
			}
		}
		
		/**
		 * 设置top面板显示隐藏
		 */
		self.setTopPanel = function() {
			if(self.topDisplay()){
				self.topDisplay(false)
			}else{
				self.topDisplay(true)
			}
		}
		
		/**
		 * 设置bottom面板显示隐藏
		 */
		self.setBottomPanel = function() {
			if(self.bottomDisplay()){
				self.bottomDisplay(false);
			}else{
				self.bottomDisplay(true);
			}
		}
		
		/**
		 * 设置right面板显示隐藏
		 */
		self.setRightPanel = function() {
			if(self.rightDisplay()){
				self.rightDisplay(false);
				if(cube.notEmpty(self.center())){
					self.centerWidth(parseInt(self.centerWidth().replace("%","")) + parseInt(self.right().width.replace("%","")) +'%');
					self.$center.css({"width": self.centerWidth()});
				}
			}else{
				self.rightDisplay(true);
				if(cube.notEmpty(self.center())){
					self.centerWidth(parseInt(self.centerWidth().replace("%","")) - parseInt(self.right().width.replace("%","")) +'%');
					self.$center.css({"width": self.centerWidth()});
				}
			}

		}
		
		/**
		 * 设置left面板显示隐藏
		 */
		self.setLeftPanel = function() {
			if(self.leftDisplay()){
				self.leftDisplay(false);
				if(cube.notEmpty(self.center())){
					self.centerWidth(parseInt(self.centerWidth().replace("%","")) + parseInt(self.left().width.replace("%","")) +'%');
					self.$center.css({"width": self.centerWidth()});
				}
			}else{
				self.leftDisplay(true);
				if(cube.notEmpty(self.center())){
					self.centerWidth(parseInt(self.centerWidth().replace("%","")) - parseInt(self.left().width.replace("%","")) +'%');
					self.$center.css({"width": self.centerWidth()});
				}
			}
			
		}
				
		/**
	     * @ignore
	     */
	    self.onloading = function(node, viewModel) {
	    	var $e = $(node);
	    	var pages = $e.children("section");
	    	var ieVersion = cube.getIEVersion();
	    	pages.each(function(index,element){
	    		var $element = $(element);	    		
	    		var type = $element.attr("type");
	    		var width = $element.attr("width");
	    		var height = $element.attr("height");
	    		var visible = $element.attr("visible");
	    		var border = $element.attr("border");
	    		var isShowCloseBtn = $element.attr("isShowCloseBtn");
	    		var content = $element.wrap("<div></div>").parent().html();
	    		if (ieVersion > -1 && ieVersion < 9) {
	    			content = content.replace(/<:/g, "<").replace(/<\/:/g, "</")
	    		}
	    		
	    		if (cube.notEmpty(visible)) {
	    			if (visible == "false") {
	    				visible = false;
	    			} else {
	    				visible = true;
	    			}
	    		}
	    		
	    		var obj = {
    				width: width,
    				height: height,
    				visible: cube.obj(visible),
    				border: border,
    				content: content,
    				isShowCloseBtn: isShowCloseBtn
	    		};
	    		
	    		if (type == "top") {
	    			self.top(obj);
	    			_topPage = true;
	    		} else if (type == "left") {
	    			self.left(obj);
	    			_leftPage = true;
	    		} else if (type == "center") {
	    			self.center(obj);
	    			_centerPage = true;
	    			var centerWidth = self.center().width;
					self.centerWidth = cube.obj(centerWidth);
	    			
	    		} else if (type == "right") {
	    			self.right(obj);
	    			_rightPage = true;
	    		} else if (type == "bottom") {
	    			self.bottom(obj);
	    			_bottomPage = true;
	    		}
	    		
	    		$element.remove();
	    	});
	    	
	    	var centerHeightAuto = $e.attr("centerHeightAuto");
	    	if (cube.notEmpty(centerHeightAuto)) {
	    		self.centerHeightAuto(Boolean(centerHeightAuto));
	    	}
	    }
	    
		/**
	     * @ignore
	     */
	    self.onload = function(node, viewModel) {
	    	var $e = $(node);
	    	if (_topPage) {
	    		cube.applyBindings($e.find(">.borderlayout>.row-fluid:nth-child(1)>.top>div").children(), viewModel);
	    	}
	    	
	    	if (_leftPage) {
	    		cube.applyBindings($e.find(">.borderlayout>.middle>.main>div").children(), viewModel);
	    	}
	    	
	    	if (_centerPage) {
	    		cube.applyBindings($e.find(">.borderlayout>.middle>.center>div").children(), viewModel);
	    	}
	    	
	    	if (_rightPage) {
	    		cube.applyBindings($e.find(">.borderlayout>.middle>.right>div").children(), viewModel);
	    	}
	    	
	    	if (_bottomPage) {
	    		cube.applyBindings($e.find(">.borderlayout>.row-fluid:nth-child(3)>.bottom>div").children(), viewModel);
	    	}
	    	
	    	self.$center = $e.children(".borderlayout").children(".middle").children(".center");
	    	self.$left = $e.children(".borderlayout").children(".middle").children(".left");
	    	self.$right = $e.children(".borderlayout").children(".middle").children(".right");
	    	self._btnheight( $e.children(".borderlayout").children(".middle").height()/2-48+'px');
	    	
	    	if (self.centerHeightAuto()) {
	    		var $top = $e.children(".borderlayout").children(".row-fluid").children(".top");
	    		var $bottom = $e.children(".borderlayout").children(".row-fluid").children(".bottom");
	    		var height = $(document).height() - $top.height() - $bottom.height();
	    		self.$center.height(height - 10);
	    		self.$center.css("overflow", "auto");
	    	}
	    }
	    
		cube.endViewModel(self, params);
	}
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 		
 	}
	return ViewModel;
});