define(["CheckUtil"], function(checkUtil) {

	/**
	 * 导航条组件
	 * selectedRoute配置路由的值，如果外部需要该值，则该属性必须配置。
	 * @example
	 * <code language="html">
	 * 		<navbar params="
	 *				navTitle: navTitle,
	 *				items: items,
	 *				selectedRoute: selectedRoute,
	 *				isFixTop: isFixTop,
	 *				isInverse: isInverse,
	 *				isShowDividerVertical: isShowDividerVertical,
	 *				isHrefRoute: isHrefRoute"></navbar>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.navTitle = '测试导航条';
	 * 				self.items = [{
	 *								text : '首页',
	 *								route : '#home',
	 *								hasChildren : false
	 *							  }, {
	 *								text : '线损菜单',
	 *								route : '#linelossmenu',
	 *								hasChildren : true,
	 *								children : [
	 *								   {
	 *									   text : '省级统计',
	 *									   route : '#linelossmenu/province_statistic'
	 *								   },
	 *								   {
	 *									   text : '市级统计',
	 *									   route : '#linelossmenu/city_statistic'
	 *								   },
	 *								   {
	 *									   text : '县级统计',
	 *									   route : '#linelossmenu/county_statistic'
	 *								   }
	 *								]
	 *							  }, {
	 *									text : '统计售电',
	 *									route : '#salestatistic',
	 *									hasChildren : false
	 *							  }, {
	 *									text : '降损措施上报',
	 *									route : '#actionupload',
	 *									hasChildren : false
	 *							}]
	 * 				self.selectedRoute = '';
	 * 				self.isFixTop = false;
	 * 				self.isInverse = false;
	 * 				self.isShowDividerVertical = false;
	 * 				self.isHrefRoute = true;
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
		 * 导航logo，在导航条最左侧显示。
		 * @default
		 * 		空
		 */
		self.navLogo = "";
		
		/**
		 * 导航的标题，在导航条左侧显示，如果navLogo不为空显示在navLogo右侧。
		 * @default
		 * 		空
		 */
		self.navTitle = "";
		
		/**
         * 导航背景色，当设置该属性时将覆盖css中的背景色设置。
         * @default
         * 		空
         */
        self.bgcolor = "";
        
		/**
         * 导航菜单栏宽度，菜单项较多并超出默认宽度时会显示滚动按钮，设置菜单栏宽度。
         * @default
         * 		null
         */
        self.navWidth = null;
        
        /**
         * 鼠标经过和当前菜单背景色，当设置该属性时将覆盖css中的背景色设置，下拉子菜单等样式需通过自定义css修改。
         * @default
         * 		空
         */
        self.activeBgColor = "";
        
        /**
         * 字体颜色，当设置该属性时将覆盖css中的字体颜色色设置。
         * @default
         * 		空
         */
        self.fontColor = "";
        
        /**
		 * 高度
		 * @default 40px
		 */
		self.height = "40px";
		
		/**
		 * 导航条内容
		 * 
		 * @example
		 * 
		 * <code language="JavaScript">
		 *  [ {
		 *		text : '首页',
		 *		route : '#home',
		 *		hasChildren : false
		 *	}, {
		 *		text : '线损菜单',
		 *		route : '#linelossmenu',
		 *		hasChildren : true,
		 *		children : [
		 *		   {
		 *			   text : '省级统计',
		 *			   route : '#linelossmenu/province_statistic'
		 *		   },
		 *		   {
		 *			   text : '市级统计',
		 *			   route : '#linelossmenu/city_statistic'
		 *		   }
		 *		]
		 *	}, {
		 *		text : '统计售电',
		 *		route : '#salestatistic',
		 *		hasChildren : false
		 *	}]
		 *	</code>
		 *
		 */
		self.items = [];
		
		/**
		 * 操作菜单
		 * @example
		 * <code language="JavaScript">
		 * [{
		 *		text : "消息",
		 *      icon: "envelope",//icon：选取bootstrap v2（Glyphicons）中的图标，例如plus代表icon-plus，自动增加前缀icon-
		 *      //route: "#envelope",
		 *		click: function() {//或者通过设置route值和组件属性onSelectedChanged触发事件
		 *          //点击按钮时触发
		 *      }
		 *	}, {
		 *		text : "退出",
		 *		icon: "signout",
		 *		click: function() {
		 *          //点击按钮时触发
		 *      }
		 *	}]
		 * </code>
		 *
		 * @default
		 * 		[]
		 */
		self.operations = [];
		

		/**
		 * 图标显示位置，需先设置icon图标。可选位置top、main
		 * @default
		 * 		main
		 */
		self.iconAlign = 'left';
		
		/**
		 * 是否显示搜索框
		 * @default
		 * 		false
		 */
		self.isShowSearchbox = false;
		
		/**
		 * 搜索框是否居右显示
		 * @default
		 * 		false
		 */
		self.isSearchboxRight = false;
		
		/**
		 * 是否显示导航条分隔符
		 * @default
		 * 		false
		 */
		self.isShowDividerVertical = false;
		
		/**
		 * 菜单是否换行显示，即不和logo在同一行
		 */
		self.isMenuWrap = false;
		
		/**
		 * 当前选中的导航条。
		 * 注意：如果外部希望得到该值，则该参数为必须。
		 * 	
		 */
		self.selectedRoute = null;
		
		/**
		 * 是否在上方悬浮
		 * @default
		 * 		false
		 */
		self.isFixTop = false;
		
		/**
		 * 是否反向显示
		 * @default
		 * 		false
		 */
		self.isInverse = false;
		
		/**
		 * 是否设置锚定值
		 * @default
		 * 		true
		 */
		self.isHrefRoute = true;
		
		/**
		 * 子集弹出框是否显示
		 * @default
		 * 		false
		 */
		self.isShowChildren = false;
		
		/**
		 * 内部属性，是否显示滑动按钮
		 * @ignore
		 */
		self._showBtn = false;
		
		self._showBtnTop = '20px';
		
		/**
		 * @ignore
		 */
		self.$scrollDiv = null;
		
		/**
		 * @ignore
		 */
		self._children = null;
		
		/**
		 * @ignore
		 */
		self._menuHeight = null;
		
		/**
		 * @ignore
		 */
		self.$node = null;
		
		/**
		 * @ignore
		 */
		self.$childrenDialog = null;
		
		/**
		 * @ignore
		 */
		self.$navsHeader = null;
		
		/**
		 * 选中变化处理事件。
		 * 
		 */
		self.onSelectedChanged = null;
		
		/**
		 * 搜索按钮点击事件，接收参数为搜索框的值。
		 * 
		 */
		self.onSearchBtnClick = null;
		
		/**
		 * 渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 内部视图模型属性，当前选中项
		 * 然后由于navbar有多层，通过compute方法，然后遍历获取，会影响效率。
		 * 但是如果把selectedItem作为外部的属性，在外部通过route来定位选中的bar，还是需要索引，所以不如在这里完成。
		 * 当selectedroute改变时，knockout的执行顺序为先执行computed，然后执行subscribe。符合要求。
		 * @ignore
		 */
		self.selectedItem = cube.comp(function(){
			var sel = null;
			$.each(self.items(),function(){
				if(this.hasChildren==true) {
					$.each(this.children,function(){
						if(self.selectedRoute() == this.route) {
							sel= this;
							return;
						}
					});
				}
				if(sel != null) {
					return sel;
				}
					
				if(sel == null &&  self.selectedRoute() == this.route) {
					sel= this;
					return;
				}
			});
			return sel;
		},self);

		/**
		 * 内部视图模型属性。鼠标经过菜单项，为hover样式。 
		 * @ignore
		 */
		self.hoverItem = cube.obj();
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			//调用外部的selectedChange事件

			self.selectedRouteSub = cube.subscribe(self.selectedRoute, function(newValue) {
				if (self.onSelectedChanged!=null && !cube.isObservable(self.onSelectedChanged)) {
					var item = self.selectedItem();
					if (item == null) {
						item = newValue;
					}
					self.onSelectedChanged(item);
				}
				
				if(self.isHrefRoute()) {
					window.location.hash = newValue;
				}
			});
			
			if (self.items() && self.items().length > 0) {
				self.selectedRoute(self.items()[0].route);
			}
			
			self._showBtnTop(parseInt((self.height()).replace("px","") / 2)  + "px");
		}
		
		/**
		 * 设置选中导航项
		 * @ignore
		 */
		self.setSelectRoute = function() {
			if (cube.notEmpty(this.route)) {
				self.selectedRoute(this.route);
			}
			
			if (cube.isFunction(this.click) && !cube.isObservable(this.click)) {
				this.click();
			}
			self.closeChildrenDialog();
		}
		
		/**
		 * 如果有子菜单，显示子菜单
		 * @ignore
		 */
		self.openSubItems = function(element, data, event) {
			var menu = self.$childrenDialog[0];
			var menuTop = null;
			var ul = self.$scrollDiv.children("ul").position().left;
			var ultop = self.$node.children(".cubenavbar")[0].offsetTop;
			var nh = self.$navsHeader.position().left;
			var li = element.offsetLeft;
			
			if (!checkUtil.isDigit(self.height())) {
				menuTop = parseInt(self.height().replace("px",""));
			}
			
			self.hoverItem(this);
			if(data.hasChildren && !self.isShowChildren()){
					self.isShowChildren(true);
					menu.style.left = nh + ul + li + 15 + 'px';	
					menu.style.top = ultop + menuTop + 'px';
					self._menuHeight( ultop + menuTop + 'px');
					self._children(data.children);
			}
		}
		
		/**
		 * 如果没有子菜单，隐藏子菜单
		 * @ignore
		 */
		self.closeSubItems = function() {
			self.hoverItem(null);
			self.isShowChildren(false);
		}
		
		/**
		 * 如果鼠标移除或点击菜单列，显示子菜单
		 * @ignore
		 */
		self.openChildrenDialog= function() {
			self.isShowChildren(true);
		}
		
		/**
		 * 如果鼠标移除或点击菜单列，隐藏子菜单
		 * @ignore
		 */
		self.closeChildrenDialog= function() {
			self.isShowChildren(false);
		}
		
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
		}
		
		/**
	     * @ignore
	     */
		self.next = function(element) {
			var div = $(element).siblings(".scrollborder");
			var ul =  div.children(".scroll").children("ul");
			var width = parseInt(div.width()) - parseInt(ul.width());
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
		}
		
		/**
		 * @ignore
		 */
		self.itemsChange = function() {
			if(self.$scrollDiv != null && !cube.isObservable(self.$scrollDiv)) {
				setTimeout(function(){
					var scrollWidth = self.$scrollDiv.width();					
					var ul = self.$scrollDiv.children("ul");					
					var ulWidth = ul.width();
					if (ulWidth >= scrollWidth) {
						self._showBtn(true);
					} else {
						self._showBtn(false);
					}
				}, 300);
			}
		}
		
		/**
		 * @ignore
		 */
		self._onSearchBtnClick = function(element) {
			if (self.onSearchBtnClick != null && !cube.isObservable(self.onSearchBtnClick)) {
				var input = $(element).siblings(".search-query");
				self.onSearchBtnClick(input.val());
			}
		}
		
		/**
	     * @ignore
	     */
		self.onload = function(node, viewModel) {
			var $node = $(node);
			self.$node = $node;
			self.$childrenDialog =  $node.children(".cubenavbar").children(".dropdown-menu");
			self.$navsHeader = $node.children(".cubenavbar").children(".navbar-inner").children(".navsheader");
      		self.$scrollDiv = $node.children(".cubenavbar").children(".navbar-inner").children(".navsheader").children(".scrollborder").children(".scroll");
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
			self.itemsChange();
		}
		
		cube.endViewModel(self, params);
	}
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 		this.selectedItem.dispose();
 	    this.selectedRouteSub.dispose(); 	    
 	}
  	
	return ViewModel;
});