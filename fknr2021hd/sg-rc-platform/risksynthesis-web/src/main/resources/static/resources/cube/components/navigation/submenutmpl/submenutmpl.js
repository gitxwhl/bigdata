define([], function() {
	
	/**
	 * 工具条，配合menu和toolbar组件使用
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**
		 * 工具条内容
		 *  @ignore
		 * 
		 * @example
		 * <code language="JavaScript">
		 * [{
		 *		text:"新建",
		 *		icon:"plus",
		 *		visible:true,
		 *		enable:true,
		 *		func:"onNewClick"
		 *	},{
		 *		text:"保存",
		 *		icon:"folder-close",
		 *		visible:true,
		 *		enable:true,
		 *		func:"onSaveClick"
		 *	},{
		 *		text:"编辑",
		 *		icon:"edit",
		 *		visible:true,
		 *		enable:true,
		 *		func:"onEditClick"
		 *	}]
		 * </code>
		 * 
		 * @default
		 * 		[]
		 */
		self.children = [];
		
		/**
		 * 当前选中内容
		 *  @ignore
		 */
		self.selectMenuItem = null;
		
		/**
		 * 内部视图模型属性。鼠标经过菜单项，为hover样式。
		 *  @ignore
		 */
		self.hoverItem = null;
		
		/**
		 *  @ignore
		 */
		self.childrenHoverItem = null;
		
		/**
		 *  @ignore
		 */
		self.parentItem = null;
		
		/**
		 *  @ignore
		 */
		self.isList = false;
		
		/**
		 *  @ignore
		 */
		self.isHorizontalChildren = false;
		
		/**
		 * 如果有子菜单，显示子菜单
		 */
		self.openSubItems = function(viewModel, event) {
			if (!self.isList()) {
				self.childrenHoverItem(this);
				
				var element = $(event.currentTarget ? event.currentTarget : event.srcElement); 
				var ul = $(element).children("div").children("ul");
				
				var top = ul.outerHeight() + ul.offset().top - $(window).height();
				if ( top > 0 ){
					ul.css("top", -top-5 + "px");
				}
			}
		}
		 
		/**
		 * 如果没有子菜单，隐藏子菜单
		 */
		self.closeSubItems = function() {
			if (!self.isList() && !self.isHorizontalChildren()) {
				self.childrenHoverItem(null);
			}
		}
		
		cube.endViewModel(self, params);
	};
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 		    
 	};
 	
	return ViewModel;
});