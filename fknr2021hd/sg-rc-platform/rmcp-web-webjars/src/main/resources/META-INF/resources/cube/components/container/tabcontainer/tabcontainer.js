define([], function() {

	/**
	 * 选项卡容器
	 * <code language="html">
	 * 		<tabcontainer params="
	 *					tabContents: tabContents,
	 *					selectedTabRoute: selectedTabRoute,
	 *					isHrefRoute: isHrefRoute
	 *					"></tabcontainer>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.tabContents = [
	 *   								{
	 *	   									title : '省级统计',
	 *	   									route : '#province_statistic',
	 *										isLocked: true, //当设置属性为锁定时，右击‘关闭所有’和‘关闭其他’时，该选项卡不会关闭。
	 *	   									content: '省级统计内容 '//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
	 *   								},
	 *   								{
	 *	  									 title : '市级统计',
	 *	   									 route : '#city_statistic',
	 *	   									 templateOptions: {
	 *   									 	name: 'app_cubedemo.componentDemos.containerDemos.tabcontainer.Templ',
	 *  									 	params: {title: 'tab2的模板'}
	 *   									}
	 *	 								}
	 *									];
	 *				self.selectedTabRoute = '#province_statistic';
	 *				self.isHrefRoute = true;
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
		 * 选项卡标签内容
		 * @example
		 * 
		 * <code language="JavaScript">
		 * [
		 *   {
		 *	   title : '省级统计',
		 *	   route : '#province_statistic',
		 *	   content: '省级统计内容 ',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 *	   templateOptions: {
		 *		   name: 'app_cubedemo.componentDemos.containerDemos.tabcontainer.Templ',
		 *		   params: {title: 'tab1的模板'}
		 *		   //type: "iframe"
		 *		   //iframeHeight: "500px"
		 *	   }
		 *   },
		 *   {
		 *	   title : '市级统计',
		 *	   route : '#city_statistic',
		 *	   content: '市统计内容 ',//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 *	   templateOptions: {
		 *   		name: 'app_cubedemo.componentDemos.containerDemos.tabcontainer.Templ',
		 *  		params: {title: 'tab2的模板'}
		 *  		//type: "iframe",
		 *  		//iframeHeight: "500px"
		 *   	}
		 *	 }
		 *	]
		 *	</code>
		 *
		 *  其中content和templateOptions配置一个即可，当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 *  content表示内容，
		 *  
		 *  templateOptions表示加载其他页面，templateOptions中的
		 *  name为页面路径，
		 *  params为要传递给该页面对应ViewModel的参数
		 *  type: 为页面加载方式，可选ajax、 iframe、viewModel，默认值为ajax，当页面为外部页面时生效（name以http开头）。
		 *  iframeHeight: iframe高度，type为iframe有效
		 *
		 *@default
		 *		[]
		 */
		self.tabContents = [];
		
		/**
		 * 选项卡风格，可选：default, tab
		 * 
		 * @default
		 * 		default
		 */
		self.type = "default";
		
		/**
		 * 选项卡内容是否重复使用，如果该值为 true，会调用方法隐藏选项卡；如果为 false，则会重新渲染选项卡。
		 * 
		 * @default false
		 */
		self.reusable = false;
		
		/**
		 * 选项卡显示位置，可选：top, main
		 * 
		 * @default
		 * 		top
		 */
		self.tabLayout = "top";
		
		/**
		 * 选项卡显示为左侧时，自定义高度，例如："300px"
		 * 
		 * @default
		 * 		auto
		 */
		self.tabLeftHeight = "auto";
		
		/**
		 * 关闭弹出框是否获得焦点
		 * @default
		 * 		false
		 */
		self.closeTabFocus = false;
		
		/**
		 * 是否启用右击显示关闭弹出框功能
		 * @default
		 * 		true
		 */
		self.isShowContextMenu = true;
		
		/**
		 * 关闭弹出框是否显示
		 * @ignore
		 * 		false
		 */
		self.isShowCloseTab = false;

		/**
		 * 当前选中tab项的路由
		 */
		self.selectedTabRoute = null;
		
		/**
		 * 选项卡超宽时，是否换行显示，
		 * @default
		 * 		false
		 */
		self.isTabLineFeed = false;
		
		/**
		 * 是否设置锚定值
		 * @default
		 * 		true
		 */
		self.isHrefRoute = true;
		
		/**
		 * 选中内容的变化处理事件
		 */
		self.onSelectedChanged = null;
		
		/**
		 * 内部属性，是否显示滑动按钮
		 * @ignore
		 */
		self._showBtn = false;
		
		/**
		 * @ignore
		 */
		self.$scrollDiv = null;
		
		/**
		 * @ignore
		 */
		self.$closeDialog = null;
		
		/**
		 * @ignore
		 */
		self._selectedTab = null;
		
		/**
		 * @ignore
		 */
		self._tabLockedText = null;
		
		/**
		 * @ignore
		 */
		self.ajaxHtml = "";
		
		var _node = null;
		var _viewModel = null;
		var _length = 0;
		var _setIframeHeight = true;
		
		/**
		 * 内部视图模型属性，通过selectedchanged传递。在tabContent的模板中，可以调用该项。但是外部不允许调用和改变。
		 * @ignore
		 */
		self.selectedTab = cube.comp(function(){
			var sel = null;
			$.each(self.tabContents(),function(){
				if(self.selectedTabRoute() == this.route) {
					sel= this;
					
					if (cube.notEmpty(sel.templateOptions)) {
						if(cube.notEmpty(sel.templateOptions.name) && sel.templateOptions.name.indexOf("http") == 0 && sel.templateOptions.type != "viewModel") {
							sel.isOut = true;
						} else {
							sel.isOut = false;
						}
						
						if (cube.isEmpty(sel.templateOptions.type)) {
							sel.templateOptions.type = "ajax";
						}
						
						if (cube.isEmpty(sel.templateOptions.iframeHeight)) {
							sel.templateOptions.iframeHeight = "auto";
							_setIframeHeight = false;
						} 
						
						if (sel.isOut && sel.templateOptions.type == "ajax") {
							if (self.reusable()) {
								self.getAjaxHtml(sel.templateOptions.name, sel);
							} else {
								self.getAjaxHtml(sel.templateOptions.name);
							}
							
						}
						
						if (sel.templateOptions.type == "iframe") {
							if (cube.notEmpty(sel.templateOptions.params)) {
								sel.templateOptions.params["dialog"] = {};
								sel.templateOptions.name += (sel.templateOptions.name.indexOf("?") > 0 ? "&" : "?") + "params=" + JSON.stringify(sel.templateOptions.params);
							}
				        }
					}
					
					return;
				}
			});
			
			self.selectedTabChange();
			
			return sel;
		},self);
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			if (self.reusable()) {
				$.each(self.tabContents(),function(){
					if (cube.notEmpty(this.templateOptions)) {
						if(cube.notEmpty(this.templateOptions.name) && this.templateOptions.name.indexOf("http") == 0 && this.templateOptions.type != "viewModel") {
							this.isOut = true;
						} else {
							this.isOut = false;
						}
						
						if (cube.isEmpty(this.templateOptions.type)) {
							this.templateOptions.type = "ajax";
						}
						
						if (this.templateOptions.type == "ajax" && cube.isEmpty(this.templateOptions.ajaxHtml)) {
							this.templateOptions.ajaxHtml = cube.obj("");
						}
						
						if (cube.isEmpty(this.templateOptions.iframeHeight)) {
							this.templateOptions.iframeHeight = "auto";
						} 
					}
					
					if (typeof(this.isLocked) == undefined) {		
						this.isLocked = cube.obj(false);
					} else if(!cube.isObservable(this.isLocked)) {
						this.isLocked = cube.obj(this.isLocked);
					}
				});
			} else {
				$.each(self.tabContents(),function(i){
					if (typeof(this.isLocked) == undefined) {		
						this.isLocked = cube.obj(false);
					} else if(!cube.isObservable(this.isLocked)) {
						this.isLocked = cube.obj(this.isLocked);
					}
				});
			}
			
			if (self.selectedTabRoute() != null) {
				self.selectedTabRoute(self.selectedTabRoute());
			} else if (self.tabContents() && self.tabContents().length > 0) {
				self.selectedTabRoute(self.tabContents()[0].route);
			}
			
			self.selctedTabRouteSub = cube.subscribe(self.selectedTabRoute, function(newValue) {
				if(self.onSelectedChanged!=null && !cube.isObservable(self.onSelectedChanged)) {
					var sel = null;
					$.each(self.tabContents(),function(){
						if(newValue == this.route) {
							sel = this;
							return;
						}
					});
					
					self.onSelectedChanged(sel);
				}
				
				if(self.isHrefRoute()) {
					window.location.hash = newValue;
				}
			});
			
			self.tabContentsSub = cube.subscribe(self.tabContents, self.itemsChange);
		};
		
		/**
		 * 切换选中tab的内容（根据route切换）
		 *  @ignore
		 */
		self.showTabContent = function(item) {
			self.selectedTabRoute(item.route);
			
			var $e = $(_node);
			var children = $e.children(".tab-content").children();
			if (children[0] && children[0].tagName && children[0].tagName.toLowerCase() == "section") {
				cube.applyBindings(children, _viewModel);
			}
		};
		
		/**
		 *  @ignore
		 */
		self.hiddenTab = function(item){
			if(self.selectedTab() ==  item){
				var _tabContents = self.tabContents();
				var index = _tabContents.indexOf(item);
				if (index == 0) {
					if (_tabContents.length > 1) {
						self.selectedTabRoute(_tabContents[1].route);
						self.tabContents.remove(item);
					}
				} else {
					self.selectedTabRoute(_tabContents[index-1].route);
					self.tabContents.remove(item);
				}
			}
		};
		
		/**
	     * @ignore
	     */
		self.next = function(element) {
			var div = $(element).siblings(".scrollborder");
			var ul =  div.children(".scroll").children("ul");
			var width = parseInt(div.width()) - parseInt(ul.width())
			var ulleft = ul.position().left;
			ul.stop(true, true);
			
			if (ulleft <= width) {
				return;
			}
			
			var left = parseInt(ulleft) - 100;
			if( left <= width) {
				left = width;
			}
			
			ul.animate({"left": left});
		};
		
		/**
	     * @ignore
	     */
		self.prev = function(element) {
			var div = $(element).siblings(".scrollborder");
			var ul = div.children(".scroll").children("ul");
			var ulleft = ul.position().left;
			ul.stop(true, true);
			if (ulleft >= 0) {
				return;
			}
			
			var left =  parseInt(ulleft) + 100;
			if( left > 0) {
				left = 0;
			}
			ul.animate({"left": left});
		};
		
		/**
	     * @ignore
	     */
		self.nextLeft = function(element) {
			var div = $(element).siblings(".scrollborder-main");
			var ul =  div.children(".scroll-main").children("ul");
			var height = parseInt(div.height()) - parseInt(ul.height())
			var ultop = ul.position().top;
			ul.stop(true, true);		
			if (ultop <= height) {
				return;
			}
			
			var top = parseInt(ultop) - 100;
			if( top <= height) {
				top = height;
			}
			
			ul.animate({"top": top});
		};
		
		/**
	     * @ignore
	     */
		self.prevLeft = function(element) {
			var div = $(element).siblings(".scrollborder-main");
			var ul = div.children(".scroll-main").children("ul");
			var ultop = ul.position().top;
			ul.stop(true, true);
			if (ultop >= 0) {
				return;
			}
			
			var top =  parseInt(ultop) + 100;
			if( top > 0) {
				top = 0;
			}
			ul.animate({"top": top});
		};
				
		/**
	     * 右击显示关闭弹出框
	     * @ignore
	     */
		self.showTabCloseDialog = function(index, data, event){
			if(!self.isShowContextMenu()){
				return;
			}
			
			var menu = self.$closeDialog[0];
			self._selectedTab(self.tabContents()[index()]);
			
			if(self._selectedTab().isLocked() == true){
				self._tabLockedText(cube.msg('DEBLOCKING'));
			} else {
				self._tabLockedText(cube.msg('LOCKED'));
			}
			self.isShowCloseTab(true);
			self.closeTabFocus(true);
			menu.style.top = event.clientY + 'px';
			menu.style.left = event.clientX + 'px';	
		};
		
		/**
	     * 锁定当选中的选项卡(方法调用时默认关闭选中的选项卡)
	     * @ignore
	     */	
		self.tabLockedThis = function(){
			var _selectedTab = self._selectedTab();
			if (_selectedTab != null) {
				if (_selectedTab.isLocked() == true) {
					_selectedTab.isLocked(false);
					self._tabLockedText(cube.msg('DEBLOCKING'));
				} else {
					_selectedTab.isLocked(true);
					self._tabLockedText(cube.msg('LOCKED'));
				}
			}
			
			self.isShowCloseTab(false);	
		};
		
		/**
	     * 关闭当前右击选中的选项卡(方法调用时默认关闭选中的选项卡)
	     */
		self.tabCloseThis = function(){
			var _tabContents = self.tabContents();
			var len = _tabContents.length-1;
			var route = self.selectedTabRoute();
			if (self._selectedTab() != null) {
				route = self._selectedTab().route;
			}
			
			if (len > 0 ){
				for (i = len; i >= 0; i--) {	
					if (_tabContents[i].route == route) {
						if (self.selectedTabRoute() == route) {
							if ( _tabContents[0].route == route) {
								self.selectedTabRoute(_tabContents[1].route);
							} else if ( _tabContents[len] == self._selectedTab()) {
								self.selectedTabRoute(_tabContents[len-1].route);		
							} else {
								self.selectedTabRoute(_tabContents[i-1].route);
							}
						}
						
						self.tabContents.remove(_tabContents[i]);
						break;
					}
				}
			}
			
			self.isShowCloseTab(false);			
		};
		
		/**
	     * 关闭其他选项卡（关闭除 当前点击的选项卡 和 isLocked属性设置为true之外  的所有选项卡）
	     */
		self.tabCloseOthers = function(){			
			var _tabContents = self.tabContents();
			var route = self.selectedTabRoute();
			if (self._selectedTab() != null) {
				route = self._selectedTab().route;
			}
			
			for(i=_tabContents.length-1;i>=0;i--) {
				self.selectedTabRoute(route);
				if(_tabContents[i].route != route){
					if( !_tabContents[i].isLocked()){
						self.tabContents.remove(_tabContents[i]);
					}
				}					
			}
			
			self.isShowCloseTab(false);			
		};
		
		/**
	     * 关闭所有选项卡（isLocked属性设置为true的除外）
	     */
		self.tabCloseAll = function(){
			var _tabContents = self.tabContents();
			var route = self.selectedTabRoute();
			for(i=_tabContents.length-1;i>=0;i--) {
					if( !_tabContents[i].isLocked()){
						self.tabContents.remove(_tabContents[i]);
					}
			}
			
			if (_tabContents.length > 0) {
				for(i=_tabContents.length-1;i>=0;i--) {
					if(_tabContents[i].route == route){
						self.selectedTabRoute(_tabContents[i].route);
						break;
					} else {
						self.selectedTabRoute(_tabContents[0].route);
					}
				}				
			}
			self.isShowCloseTab(false);
		};
		
		/**
		 * 刷新选项卡页面，暂时只支持刷新通过templateOptions配置的页面
		 * @param route 可选，选项卡的route，当不设置时刷新选中的当前选项卡，当参数值不为当前选项卡route时将会进行选项卡切换。
		 * 
		 */
		self.refreshTab = function(route) {
			var templateOptions = null;
			var index = 0;
			
			if (cube.isEmpty(route)) {
				route = self.selectedTabRoute();
			}
			
			var _tabContents = self.tabContents();
			var len = _tabContents.length;
			for (i = 0; i < len; i++) {
				if (_tabContents[i].route == route) {
					templateOptions = _tabContents[i].templateOptions;
					index = i;
					break;
				}
			}
			
			if (cube.notEmpty(templateOptions)) {
				cube.clearComponentCached(templateOptions.name);
				
				if (route != self.selectedTabRoute()) {
					self.selectedTabRoute(route);
				} else {
					var node = null;
					if (self.reusable()) {
						node = $(_node).find(".tab-content" + (self.tabLayout() == "left" ? "-main" : "") + ":eq(" + index + ")");
					} else {
						node = $(_node).find(".tab-content" + (self.tabLayout() == "left" ? "-main" : ""));
					}
					
					cube.bindComponentByNode(node, {component:templateOptions});
				}
			}
		};
		
		/**
		 * 关闭右击菜单弹窗
		 * @ignore
	     */
		self.tabCancelClose = function(){
			self.isShowCloseTab(false);
		};
		
		var isMousedownInner = false;
		var isMouseover = false;
		
		/**
		 * @ignore
		 */
		self.mouseoverPopoverDialog = function() {
			isMouseover = true;
		};
		
		/**
		 * @ignore
		 */
		self.mousedownInnerPopoverDialog = function() {
			isMousedownInner = true;
			isMouseover = false;
		};
		
		/**
		 * 隐藏弹出框
		 * @ignore
		 */
		self.hiddenPopoverDialog = function() {
			self.isShowCloseTab(false);
			self.closeTabFocus(false);
			isMousedownInner = false;
		};
		
		/**
		 * 选项卡数组发生变化时
		 * @ignore
		 */
		self.itemsChange = function(items) {
			var item = null;
			if (self.reusable() && items) {
				for (var i = 0; i < items.length; i++) {
					item = items[i];
					if (cube.notEmpty(item.templateOptions)) {
						if(cube.notEmpty(item.templateOptions.name) && item.templateOptions.name.indexOf("http") == 0 && item.templateOptions.type != "viewModel") {
							item.isOut = true;
						} else {
							item.isOut = false;
						}
						
						if (cube.isEmpty(item.templateOptions.type)) {
							item.templateOptions.type = "ajax";
						}
						
						if (item.templateOptions.type == "ajax" && cube.isEmpty(item.templateOptions.ajaxHtml)) {
							item.templateOptions.ajaxHtml = cube.obj("");
						}
						
						if (cube.isEmpty(item.templateOptions.iframeHeight)) {
							item.templateOptions.iframeHeight = "auto";
						} 
					}
					
					if (typeof(item.isLocked) == undefined) {		
						item.isLocked = cube.obj(false);
					} else if(!cube.isObservable(item.isLocked)) {
						item.isLocked = cube.obj(item.isLocked);
					}
				}
			} else if (items) {
				for (var i = 0; i < items.length; i++) {
					item = items[i];
					if (typeof(item.isLocked) == undefined) {		
						item.isLocked = cube.obj(false);
					} else if(!cube.isObservable(item.isLocked)) {
						item.isLocked = cube.obj(item.isLocked);
					}
				}
			}
			
			if(self.isTabLineFeed() != true && self.$scrollDiv != null && !cube.isObservable(self.$scrollDiv) && self.$scrollDiv.length > 0) {
				setTimeout(function(){
					var scrollWidth = self.$scrollDiv.width();
					var scrollLeftHeight = self.$scrollLeftDiv.height();					
					var ul = self.$scrollDiv.children("ul");
					var ulLeft = self.$scrollLeftDiv.children("ul");					
					var ulWidth = ul.width();
					var ulLeftHeight = ulLeft.height();
					var leftScroll = scrollWidth - ulWidth;
					if(self.tabLayout() =="top"){
						if (ulWidth >= scrollWidth) {
							self._showBtn(true);
						} else {
							self._showBtn(false);
						}
					} else {
						if (ulLeftHeight >= scrollLeftHeight) {
							self._showBtn(true);
						} else {
							self._showBtn(false);
						}
					}
				}, 300);
			}
		};
		
		/**
		 * 选项卡路由发生变化时
		 * @ignore
		 */
		self.selectedTabChange = function() {
			if(self.isTabLineFeed() != true && self.$scrollDiv != null && !cube.isObservable(self.$scrollDiv) && self.$scrollDiv.length > 0) {
				setTimeout(function(){
					var scrollWidth = self.$scrollDiv.width();
					var scrollLeftHeight = self.$scrollLeftDiv.height();					
					var ul = self.$scrollDiv.children("ul");
					var ulLeft = self.$scrollLeftDiv.children("ul");					
					var ulWidth = ul.width();
					var ulLeftHeight = ulLeft.height();
					
					var selWidth = 0;
					var topLeftScroll = 0;
					
					var _tabContents = self.tabContents();
					var len = _tabContents.length;
					for(i = 0; i<len; i++){
						selWidth += ul.children("li")[i].clientWidth + 4;
						if (_tabContents[i].route == self.selectedTabRoute()){
							topLeftScroll = scrollWidth - selWidth;
						}
					}

					if(self.tabLayout() =="top"){
						if (selWidth >= scrollWidth && topLeftScroll <= 0) {
							ul.animate({"left": topLeftScroll});
						} else {
							ul.animate({"left": 0});
						}
					} else {
						if (ulLeftHeight >= scrollLeftHeight) {
							self._showBtn(true);
						} else {
							self._showBtn(false);
							ul.animate({"top": 0});
						}
					}
				}, 300);
			}
		};
		
		/**
	     * @ignore
	     */
		self.getAjaxHtml = function(p_url, item) {
			$.ajax({
	    		url: p_url,
	    		crossDomain: cube.crosDomain(p_url),
	    		success: function(response,status,xhr){
	    			var ajaxHtml = response;
	    			if (typeof(item) != "undefined" && item.templateOptions && cube.isObservable(item.templateOptions.ajaxHtml)) {
	    				item.templateOptions.ajaxHtml(ajaxHtml);
	    			} else {
	    				self.ajaxHtml(ajaxHtml);
	    			}
	    		}
	    	});
		};
		
		/**
		 *  @ignore
		 */
		self.iframeLoad = function(viewMode, event) {
			var iframe = (event.currentTarget) ? event.currentTarget : event.srcElement;
			if (!_setIframeHeight && iframe) {
				var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
				try {
					if (iframeWin.document.body) {
						$(iframe).attr("scrolling", "no");
						$(iframeWin.document.body).resize(function(){
				    		$(iframe).height(this.scrollHeight + "px");
				    	}); 
					}
				} catch (e) {
				}
			}
		};
		
		/**
	     * @ignore
	     */
	    self.onloading = function(node, viewModel) {
	    	_node = node;
	    	_viewModel = viewModel;
	    	var $e = $(node);
	    	var tabPages = $e.children("section");
	    	_length = self.tabContents().length;
	    	var ieVersion = cube.getIEVersion();
	    	tabPages.each(function(index,element){
	    		var $element = $(element);
	    		var content = $element.wrap("<div></div>").parent().html();
	    		if (ieVersion > -1 && ieVersion < 9) {
	    			content = content.replace(/<:/g, "<").replace(/<\/:/g, "</")
	    		}
	    		
	    		var page = {
	    			   title : $element.attr("title"),
				 	   route : $element.attr("route"),
				 	   content: content
				};
	    		
    			self.tabContents.push(page);
	    		$element.remove();
	    		
	    	});
	    };
	    
		/**
	     * @ignore
	     */
	    self.onload = function(node, viewModel) {
	    	var $node = $(node);
      		self.$scrollDiv = $node.children(".tabsheader").children(".scrollborder").children(".scroll");
      		self.$scrollLeftDiv = $node.children(".tabsheader-main").children(".scrollborder-main").children(".scroll-main");
      		self.$closeDialog =  $node.children(".dropdown-menu");
      		
      		self.itemsChange();
      		   		
	    	if (_length == 0) {
	    		if(self.tabContents() && self.tabContents().length > 0) {
					self.selectedTabRoute(self.tabContents()[0].route);
					self.showTabContent(self.tabContents()[0]);
				}
	    	}
	    };
	    
		cube.endViewModel(self, params);
	}
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 		this.selectedTab.dispose();
 	    this.selctedTabRouteSub.dispose(); 	   
 	    this.tabContentsSub.dispose();
 	}
	return ViewModel;
});