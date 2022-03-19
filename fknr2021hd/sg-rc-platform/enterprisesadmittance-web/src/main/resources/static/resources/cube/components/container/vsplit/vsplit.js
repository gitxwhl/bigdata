define([], function() {

	/**
	 * 垂直分割容器
	 * <code language="html">
	 * 		<vsplit params="
	 *					height:'500px',
	 *					resizable: resizable,
	 *					contents: contents"></vsplit>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.resizable = true;
	 * 				self.contents = [{
	 * 									width:"200px",
	 *  								content: '第一列',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
	 *  								templateOptions: {
	 *		    										   name: 'app_cubedemo.componentDemos.containerDemos.vsplit.Templ',
	 *		    										   params: {title: '第一列的模板'}
	 *		    									   }
	 *  							},
	 *  							{
	 *  								width:"200px",
	 *  								content: '第二列',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
	 *  								templateOptions: {
	 *		    										   name: 'app_cubedemo.componentDemos.containerDemos.vsplit.Templ',
	 *		    										   params: {title: '第一列的模板'}
	 *		    									   }
	 *  							},
	 *  							{
	 *  								width:"200px",
	 *  								content: '第三列',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
	 *  								templateOptions: {
	 *		    										   name: 'app_cubedemo.componentDemos.containerDemos.vsplit.Templ',
	 *		    										   params: {title: '第一列的模板'}
	 *		    									   }
	 *  							}];  			
	 *  			cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**
		 * 高度
		 * @default
		 * 		100%
		 */
		self.height = "100%";
		
		/**
		 * 宽度
		 * @default
		 * 		100%
		 */
		self.width = "100%";
		
		/**
         * 按钮背景颜色
         * @default
         * 		null
         */
        self.btnbgcolor = null;
		
		/**
	     * 设置一个 Boolean 值，表示是否可以由用户以拖动方式改变容器面板大小。值为true时允许拖动。
	     * 
	     * @default true
	     */
		self.resizable = true;
		
		/** 容器内容
		 * @example
		 * <code language="JavaScript">
		 *  [
		 *   {
		 *     width:"200px",
		 *	   content: '第一列',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 *	   templateOptions: {
		 *		   name: 'app_cubedemo.componentDemos.containerDemos.hsplit.Templ',
		 *		   params: {title: '第一列的模板'}
		 *		   //type: "iframe",
		 *  	   //iframeHeight: "500px"
		 *	   }
		 *   },
		 *   {
		 *   	width:"200px",
		 *      content: '第二列',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 *	    templateOptions: {
		 *   		name: 'app_cubedemo.componentDemos.containerDemos.hsplit.Templ',
		 *   		params: {title: '第二列的模板'}
		 *   	}
		 *	 },
		 *	 {
		 *		 width:"200px",
		 *		 content: '第三列',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 *		 templateOptions: {
		 *			 name: 'app_cubedemo.componentDemos.containerDemos.hsplit.Templ',
		 *	    	 params: {title: '第三列的模板'}
		 *	    }
		 *	 }
		 *	]
		 *
		 *	</code>
		 *
		 *  其中content和templateOptions配置一个即可，当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 *  content表示内容，
		 *  templateOptions表示加载其他页面，templateOptions中的name为页面路径，params为要传递给该页面对应ViewModel的参数
		 *	type: 为页面加载方式，可选ajax、 iframe、viewModel，默认值为ajax，当页面为外部页面时生效（name以http开头）。
		 *  iframeHeight: iframe高度，type为iframe有效
		 *  
		 *	@default
		 *		[]
		 */
		self.contents = [];
		
		var _length = 0;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			var contents = self.contents();
			var length = contents.length;
			for(var i = 0; i < length; i++){
				if(contents[i]){
					contents[i].display = cube.obj("block");
					contents[i].width = cube.initComponentProperty(contents[i].width, "auto", 'obj');
					contents[i].splitClass = cube.obj("top");
					
					if (cube.notEmpty(contents[i].templateOptions)) {
						if(cube.notEmpty(contents[i].templateOptions.name) && contents[i].templateOptions.name.indexOf("http") == 0 && contents[i].templateOptions.type != "viewModel") {
							contents[i].isOut = true;
						} else {
							contents[i].isOut = false;
						}
						
						if (cube.isEmpty(contents[i].templateOptions.type)) {
							contents[i].templateOptions.type = "ajax";
						}
						
						if (cube.isEmpty(contents[i].templateOptions.iframeHeight)) {
							contents[i].templateOptions.iframeHeight = "100%";
						}
						
						if (contents[i].isOut && contents[i].templateOptions.type == "ajax") {
							contents[i].ajaxHtml = cube.obj("");
							self.getAjaxHtml(contents[i]);
						}
					}
				}
			}
		};
		
		/**
		 * 设置面板显示隐藏
		 */
		self.setPanel = function(index) {
			var display = self.contents()[index()].display;
			var splitClass = self.contents()[index()].splitClass;
			if(display() == "block"){
				display("none");
				splitClass("collapsed");
			}else{
				display("block");
				splitClass("top");
			}
		};
		
		/**
		 * @ignore
		 */
		var _mouseX = 0, _srcX = 0, contentWidth;
		self.mousedown = function(index, data, event) {
			_mouseX = event.clientX;
			contentWidth = self.contents()[index()].width;
			var _width = contentWidth();
			if(_width == "auto" || _width.indexOf("%") != -1){
				var target = (event.currentTarget) ? event.currentTarget : event.srcElement;
				_srcX = $(target).prev().width();
			}else{
				_srcX = parseInt(_width);
			}
			$(document).unbind('mousemove').bind('mousemove',self.mousemove);
		};
		
		/**
		 * @ignore
		 */
		self.mouseup = function() {
			$(document).unbind('mousemove');
		};
		
		/**
		 * @ignore
		 */
		self.mousemove = function() {
			 var offsetX = event.clientX - _mouseX;
			 contentWidth((_srcX + offsetX) + "px");
		};
		
		/**
	     * @ignore
	     */
		self.getAjaxHtml = function(content) {
			$.ajax({
	    		url: content.templateOptions.name,
	    		crossDomain: cube.crosDomain(content.templateOptions.name),
	    		success: function(response,status,xhr){
	    			var ajaxHtml = response;
	    			content.ajaxHtml(ajaxHtml);
	    		}
	    	});
		};
		
		/**
	     * @ignore
	     */
	    self.onloading = function(node, viewModel) {
	    	var $e = $(node);
	    	var pages = $e.children("section");
	    	_length = self.contents().length;
	    	var ieVersion = cube.getIEVersion();
	    	pages.each(function(index,element){
	    		var $element = $(element);
	    		var content = $element.wrap("<div></div>").parent().html();
	    		if (ieVersion > -1 && ieVersion < 9) {
	    			content = content.replace(/<:/g, "<").replace(/<\/:/g, "</")
	    		}
	    		var page = {
	    			   width : cube.obj($element.attr("width")),
	    			   display: cube.obj($element.attr("display") || "block"),
	    			   splitClass: cube.obj("top"),
				 	   content: content
				};
	    		
    			self.contents.push(page);
	    		$element.remove();
	    	});
	    };
	    
		/**
	     * @ignore
	     */
	    self.onload = function(node, viewModel) {
	    	var $e = $(node);
	    	var panels = self.contents();
	    	for (var i = _length; i < panels.length; i++) {
				cube.applyBindings($e.find(">.row-fluid>div.split-body").eq(i).children().eq(0).children(), viewModel);
	    	}
	    };
	    
		cube.endViewModel(self, params);
	}
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 		
 	}
	return ViewModel;
});