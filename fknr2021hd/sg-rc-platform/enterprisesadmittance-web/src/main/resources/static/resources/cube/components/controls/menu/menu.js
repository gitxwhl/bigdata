define([], function() {

	//注册模板组件
	cube.importComponent("navigation.submenutmpl");
	
	/**
	 * 菜单组件
	 * menuItems控制是否垂直显示菜单，onSelectItemChanged控制当前菜单选中事件。
	 * @example
	 * <code language="html">
	 * 		<menu params="menuItems:  menuItems,
						isList: isList,
						onSelectItemChanged: onSelectItemChanged" style="padding-main:0" ></menu>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.menuItems = [
	 *								    {text: '菜单1', 
	 *							    	route: "#menu1",
	 *							    	hasChildren: true,
	 *							    	children: cube.array([
	 *							       		{text: '菜单1-1', 
	 *							    		hasChildren: true,
	 *							       		children:cube.array([
	 *												{text: '菜单1-1-1', 
	 *								    			hasChildren: true,
	 *								    			children:cube.array([
	 *													{text: '菜单1-1-1-1', 
	 *													route: "#menu1-1-1-1",
	 *									    			hasChildren: false}                     
	 *								    				])}                     
	 *								    			])},
	 *							       		{text:'菜单',
	 *							       		route:'#menu',
	 *							       		hasChildren:false},
	 *							       		{text:'-',
	 *							       		route:'-',
	 *							       		hasChildren:false},
	 *							       		{text: '菜单1-2', 
	 *							    		hasChildren: true,
	 *							       		children:cube.array([
	 *												{text: '菜单1-2-1', 
	 *								    			hasChildren: true,
	 *								    			children:cube.array([
	 *													{text: '菜单1-2-1-1', 
	 *													route: "#menu1-2-1-1",
	 *									    			hasChildren: false}                     
	 *								    				])}                     
	 *								    			])}
	 *								    ])},
	 *								    {text: '菜单2', 
	 *								    route: "#menu2",
	 *								    hasChildren: false}
	 *								];
	 * 				self.isList = false;
	 * 				self.onSelectItemChanged = function(p_value){
	 *																self.text(p_value.text);
	 *																self.route(p_value.route);
	 *																}
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
		 * 菜单内容
		 * @example
		 * 
		 * <code language="JavaScript">
		 * 	[{
		 *	   	text: '菜单1', 
		 *   	route: "#menu1",
		 *   	hasChildren: true,
		 *   	icon:"plus",
		 *   	expanded: true, //isList属性为true时生效
		 *   	children: [
		 *      		{
		 *      			text:'菜单',
		 *      			route:'#menu',
		 *      			hasChildren:false
		 *      		},
		 *      		{
		 *      			text:'-',
		 *      			route:'-',
		 *      			hasChildren:false
		 *      		}
		 *	    ]
	     *	 },
		 *	 {
		 *	    text: '菜单2', 
		 *    	route: "#menu2",
		 *    	hasChildren: false
		 *	 }]
		 *	</code>
		 *	其中：
	 	 *		icon：选取bootstrap v2（Glyphicons）中的图标，例如plus代表icon-plus，自动增加前缀icon-
	 	 *		expanded：菜单是否默认展开，isList属性为true时生效
		 */
		self.menuItems = [];
		
		/**
		 * 宽度
		 * @default 100
		 */
		self.width = "100%";
		
		/**
		 * 设置下拉菜单最大高度，内容高度超出会出现滚动条。
		 * @default
		 * 		auto
		 */
		self.dropdownMenuHeight = 'auto';
		
		/**
		 * 是否垂直显示主菜单
		 * @default false
		 */
		self.isList = false;
		
		/**
		 * 是否横向显示子菜单，当横向显示子菜单时，只支持一级子菜单的显示，此属性只有当isList属性值为false是才会生效。
		 * @default false
		 */
		self.isHorizontalChildren = false;
		
		/**
		 * 当前菜单选中事件
		 */
		self.onSelectItemChanged = null;
		
		/**
		 * 渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 内部视图模型属性。鼠标经过菜单项，为hover样式。
		 * @ignore
		 */
		self.hoverItem = null;
		
		self._init = function() {
			if (!self.isList() && self.isHorizontalChildren()) {
				var _menuItems = self.menuItems();
				if (_menuItems != null && _menuItems.length > 0 && typeof(_menuItems[0].children) != "undefined" && _menuItems[0].children != null) {
					self.hoverItem(_menuItems[0]);
				}
			}
			
			if (self.isList()) {
				var _menuItems = self.menuItems();
				for (var i = 0; i < _menuItems.length; i++) {
					if (typeof(_menuItems[i].expanded) == "undefined") {
						_menuItems[i].expanded = cube.obj(false);
					} else if(!cube.isObservable(_menuItems[i].expanded)) {
						_menuItems[i].expanded = cube.obj(_menuItems[i].expanded);
					}
				}
			}
		}
		
		/**
		 * 如果有子菜单，显示子菜单
		 */
		self.openSubItems = function() {
			if (!self.isList() || (self.isList() && self.isHorizontalChildren())) {
				self.hoverItem(this);
			}
			
		}
		
		/**
		 * 如果没有子菜单，隐藏子菜单
		 */
		self.closeSubItems = function() {
			if ((!self.isList() && !self.isHorizontalChildren()) || (self.isList() && self.isHorizontalChildren())) {
				self.hoverItem(null);
			}
		}

		/**
		 * 选中菜单项
		 */
		self.selectMenuItem = function() {
			if (self.onSelectItemChanged != null && !cube.isObservable(self.onSelectItemChanged)) {
				self.onSelectItemChanged(this);
			}
			
			if (self.isList() && typeof(this.expanded) == "function") {
				if(this.expanded()){
					this.expanded(false);
				}else{
					this.expanded(true);
				}
			}
		}
		
		/**
	     * @ignore
	     */
		self.onload = function(node) {
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		}
		
		cube.endViewModel(self, params);
	}
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 		    
 	}
 	
	return ViewModel;
});