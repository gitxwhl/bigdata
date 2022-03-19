define([], function() {

	cube.importComponent("navigation.submenutmpl");
	
	/**
	 * 工具条组件
	 * @example
	 * <code language="html">
	 * 		<toolbar params="
	 *					toolbarContent: toolbarContent,
	 *					onItemClick: onItemClick,
	 *					showtext: showtext"></toolbar>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.toolbarContent = [
	 * 										{group:cube.array([{
	 *											text:"新建",
	 *											icon:"plus",
	 *											visible:true,
	 *											enable:true,
	 *											func:"onNewClick"
	 *										},{
	 *											text:"保存",
	 *											icon:"folder-close",
	 *											visible:true,
	 *											enable:false,
	 *											func:"onSaveClick"
	 *										},{
	 *											text:"编辑",
	 *											icon:"edit",
	 *											visible:false,
	 *											enable:true,
	 *											func:"onEditClick"
	 *										}]),
	 *										isFloatRight: true
	 *										},
	 *										{group:cube.array([{text:"放大",
	 *											icon:"zoom-in",
	 *											visible:true,
	 *											enable:true,
	 *											func:"onZoomInClick"
	 *										},{
	 *											text:"缩小",
	 *											icon:"zoom-out",
	 *											visible:true,
	 *											enable:true,
	 *											func:"onZoomOutClick"
	 *										}])}
	 *									  ];
	 *
     *  			self.onItemClick = function(e){
     *					if (e == "onNewClick") {
     *						//TOTO
     *					}
     *				}
	 * 				self.showtext = true;
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
		 * 工具条内容
		 * @example
		 * <code language="JavaScript">
		 * [{group:[{
		 * 		text:"新建",
		 *		icon:"plus",
		 *		visible:true,
		 *		enable:true,
		 *		func:"onNewClick"
		 *	},{
		 * 		text:"编辑",
		 *		icon:"edit",
		 *		visible:true,
		 *		enable:true,
		 *		func:"onUpdateClick"
		 *	}],
		 *	isFloatRight: true
		 *}]
		 * </code>
		 * 其中：group：对按钮进行分组，可以有多个group
		 * 		isFloatRight：按钮是否有浮动
		 * 		text：鼠标放在工具按钮上的提示信息
	 	 *		icon：选取bootstrap v2（Glyphicons）中的图标，例如plus代表icon-plus，自动增加前缀icon-
	 	 *		visible：按钮是否可见
		 *		enable：按钮是否可用
	 	 *		func：调用函数的名称
	 	 *		type: 按钮样式，可选的值包括：default、primary、success、info、warning、danger、inverse、link
	 	 * 
	 	 * @default
	 	 * 		[]
		 */
		self.toolbarContent = [];
		
		/**
		 * 是否显示文本
		 * @default
		 * 		false
		 */
		self.showtext = false;
		
		/**
		 * 是否显示背景颜色及边框
		 *  @default
		 * 		true
		 */
		self.isShowBackground = true;
		
		/**
		 * 单击按钮事件
		 */
		self.onItemClick = null;
		
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
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			addItemClick();
			cube.subscribe(self.toolbarContent, addItemClick);
		};
		
		/**
		 * 如果有子菜单，显示子菜单
		 */
		self.openSubItems = function() {
			self.hoverItem(this);
		};
		
		/**
		 * 如果没有子菜单，隐藏子菜单
		 */
		self.closeSubItems = function() {
			self.hoverItem(null);
		};
		
		/**
		 * 选中菜单项
		 */
		self.selectMenuItem = function() {
			if (self.onSelectItemChanged != null && !cube.isObservable(self.onSelectItemChanged)) {
				self.onSelectItemChanged(this);
			}
		};

		function addItemClick() {
			$.each(self.toolbarContent(), function(){
				if (this.onItemClick == undefined && !cube.isObservable(self.onItemClick)) {
					this.onItemClick = function() {
						if (cube.isEmpty(this.enable) || (cube.isObservable(this.enable) ? this.enable() : this.enable)) {
							self.onItemClick(this.func);
						}
					};
				}
				return; 
			});
		}
		
		/**
	     * @ignore
	     */
		self.onload = function(node) {
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		};
		
		cube.endViewModel(self, params);
	}
	
	return ViewModel;
});