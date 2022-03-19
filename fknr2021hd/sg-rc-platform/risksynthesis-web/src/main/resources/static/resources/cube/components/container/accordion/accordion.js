define([], function() {

	/**
	 * 手风琴组件
	 * @example
	 * <code language="html">
	 * 		<accordion params="
	 * 		panels: panels,
	 * 		selectedPanelRoute: selectedPanelRoute">
	 * 		</accordion>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.panels = [
	 *	  							{
	 *   	 							route:'panel1',
	 *  	 							title: '面板1',
     *	  								content: '<b>这是面板1的内容</b>',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
	 *	   	 							templateOptions: {
	 *	   									name: 'app_cubedemo.componentDemos.containerDemos.accordion.panel1Templ',
	 *	   									params: {title: '面板1的模板'}
	 *	   	 							}
	 *	  							},
	 *	 		 					{
	 *	   		 						route:'panel2',
	 *	   	 							title: '面板2',
	 *	   	 							content: '<b>这是面板2的内容</b>',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
	 *	   	 							templateOptions: {
	 *	   									name: 'app_cubedemo.componentDemos.containerDemos.accordion.panel2Templ',
	 *	   									params: {title: '面板2的模板'}
	 *	   	 							}
	 *	  							}
	 *							];
	 *				self.selectedPanelRoute= "panel1";
	 *				
	 *				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**
		 * 所有面板
		 * @example
		 * 
		 * <code language="JavaScript">
		 * [
		 *	  {
		 *   	 route:'panel1',
		 *  	 title: '面板1',
	     *	  	 content: '<b>这是面板1的内容</b>',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
    	 *	   	 templateOptions: {
    	 *	   		name: 'app_cubedemo.componentDemos.containerDemos.accordion.panel1Templ',
    	 *	   		params: {title: '面板1的模板'}
    	 *	   	 }
    	 *	  },
    	 *	  {
    	 *	   	 route:'panel2',
    	 *	   	 title: '面板2',
    	 *	   	 content: '<b>这是面板2的内容</b>',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
    	 *	   	 templateOptions: {
    	 *	   		name: 'app_cubedemo.componentDemos.containerDemos.accordion.panel2Templ',
    	 *	   		params: {title: '面板2的模板'}
    	 *	   	 }
    	 *	  }
    	 *	]
    	 *	
    	 *	</code>
    	 *
    	 *  其中content和templateOptions配置一个即可，当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 *  content表示内容，
		 *  templateOptions表示加载其他页面，templateOptions中的name为页面路径，params为要传递给该页面对应ViewModel的参数
    	 *
    	 *@default
    	 *		[]
		 */
		self.panels = [];
		
		/**
		 * 当前选中面板的路由
		 * @default
		 * 	null
		 */
		self.selectedPanelRoute = null;
		
		/**
		 * 是否启用抽屉模式，当启用抽屉模式时初始化时是否展开，可通过为panel设置expanded
		 * @example
		 * 
		 * <code language="JavaScript">
		 * [
		 *	  {
		 *   	 route:'panel1',
		 *  	 title: '面板1',
		 *       expanded: true,
	     *	  	 content: '<b>这是面板1的内容</b>'
    	 *	  }
    	 *	]
    	 *	
    	 *	</code>
		 * 
		 * @default
		 * 	false
		 */
		self.isEnabledDrawer = false;
		
		/**
		 * 是否延迟渲染, 当isEnabledDrawer设置为true，该属性无效
		 * 
		 * @default
		 * 	false
		 */
		self.isLazyRender = false;
		
		var _length = 0;
		var _index = 0;
		
		/**
		 * 内部属性
		 * @ignore
		 */
		self.selectedPanel = cube.comp(function(){
			var sel = null;
			$.each(self.panels(),function(index, panel){
				
				if(sel != null) {
					return sel;
				}
					
				if(sel == null &&  self.selectedPanelRoute() == panel.route) {
					sel= panel;
					_index = index;
					return;
				}
			});
			return sel;
		},self);
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			if (self.isEnabledDrawer()) {
				$.each(self.panels(), function(index, p_panel) {
					if (typeof(p_panel.expanded) == "undefined") {
						p_panel.expanded = cube.obj(false);
					} else if (!cube.isObservable(p_panel.expanded)) {
						p_panel.expanded = cube.obj(p_panel.expanded);
					}
				});
			}
			
			if (self.panels() && self.panels().length > 0) {
				if (self.selectedPanelRoute() != "undefined" && self.selectedPanelRoute() != null) {
					self.selectedPanelRoute(self.selectedPanelRoute());
				} else {
					self.selectedPanelRoute(self.panels()[0].route);
				}
			}
		};

		/**
		 * 选择面板
		 * @ignore
		 */
		self.setSelectedPanel = function(viewModel, event) {
			if (self.isLazyRender() && !self.isEnabledDrawer()) {
				self.renderPanel(this, event.currentTarget ? event.currentTarget : event.srcElement);
			}
			
			self.selectedPanelRoute(this.route);
			
			if (self.isEnabledDrawer()) {
				this.expanded(!this.expanded());
			}
		};
		
		/**
		 * @ignore
		 */
		self.renderPanel = function(panel, target) {
			var bindings = null;
			var innerDiv = $(target).parent().parent().find(".accordion-inner");
			if (typeof(panel.content)!="undefined") {
				bindings = {
						html: panel.content
					}
				cube.bindComponentByNode(innerDiv, bindings);
			} else if (typeof(panel.content)=="undefined" && typeof(panel.templateOptions) != "undefined") {
				bindings = {
					component: {
						name: panel.templateOptions.name,
						params: panel.templateOptions.params
					}
				}
				
				cube.bindComponentByNode(innerDiv, bindings);
			}
		};
		
		/**
	     * @ignore
	     */
	    self.onloading = function(node, viewModel) {
	    	var $e = $(node);
	    	var pages = $e.children("section");
	    	_length = self.panels().length;
	    	var ieVersion = cube.getIEVersion();
	    	pages.each(function(index,element){
	    		var $element = $(element);
	    		var content = $element.wrap("<div></div>").parent().html();
	    		if (ieVersion > -1 && ieVersion < 9) {
	    			content = content.replace(/<:/g, "<").replace(/<\/:/g, "</")
	    		}
	    		
	    		var expanded = $element.attr("expanded");
	    		var page = {
	    			   title : $element.attr("title"),
				 	   route : $element.attr("route"),
				 	   expanded : cube.obj(typeof(expanded)=="undefined"?false:expanded),
				 	   content: content
				};
	    		
    			self.panels.push(page);
	    		$element.remove();
	    	});
	    };
	    
		/**
	     * @ignore
	     */
	    self.onload = function(node, viewModel) {
	    	var $e = $(node);
	    	var panels = self.panels();
	    	for (var i = _length; i < panels.length; i++) {
				cube.applyBindings($e.find(">.accordion>.accordion-group:nth-child("+(i+1)+")>.accordion-body").children(".accordion-inner").children(), viewModel);
	    	}
	    	
	    	if (_length == 0) {
	    		if(self.panels() && self.panels().length > 0) {
	    			if (cube.isEmpty(self.selectedPanelRoute())) {
						self.selectedPanelRoute(self.panels()[0].route);
					}
				}
	    	}
	    	
	    	if (self.isLazyRender() && !self.isEnabledDrawer()) {
				self.renderPanel(self.selectedPanel(), $e.find(".accordion-inner:eq("+_index+")"));
			}
	    };
		
		cube.endViewModel(self, params);
	}
 	
 	//释放资源，包括compoted/subscrib资源等。
 	ViewModel.prototype.dispose = function() {
 	    this.selectedPanel.dispose(); 	    
 	}
	return ViewModel;
});