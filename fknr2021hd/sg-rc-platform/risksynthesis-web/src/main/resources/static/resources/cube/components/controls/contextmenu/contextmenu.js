define([], function() {
	cube.importComponent("controls.menu");

	/**
	 * 弹出菜单组件
	 * @example
	 * <code language="html">
	 * 		<contextmenu params="contextMenuItems: contextMenuItems,
							isShowMenu: isShowMenu" ></contextmenu>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.contextMenuItems = [{
	 *	  										text: '操作', 
	 *										    route: "#cz",
	 *										    hasChildren: true,
	 *										    children:[{
	 *										    	text: '添加', 
	 *												route: "#add",
	 *								    			hasChildren: true,
	 *								    			children:[{
	 *								    					text: '添加', 
	 *								    					route: "#add1",
	 *								    	    			hasChildren: false
	 *								    	    			}]
	 *								    		}],
	 *							    			{text: '删除', 
	 *											route: "#delete",
	 *							    			hasChildren: false
	 *							    		}]
	 * 				self.isShowMenu = true;
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
		 * 是否显示弹出菜单
		 * @default 
		 * 		false
		 */
		self.isShowMenu = false;
		
		/**
		 * 菜单弹出的方向，包括：top\right\main\bottom
		 *  @default 
		 * 		right
		 * 
		 */
		self.popoverDirection = "right";
		
		/**
		 * 弹出菜单内容
		 * @example
		 * 
		 * <code language="JavaScript">
		 * [{
	 	 * 		text: '操作', 
		 *   	route: "#cz",
		 *   	hasChildren: true,
		 *   	children:[
		 *			{
		 *				text: '添加', 
		 *				route: "#add",
    	 *				hasChildren: false
    	 *			},
    	 *			{
    	 *				text: '删除', 
		 *				route: "#delete",
    	 *				hasChildren: false
    	 *			}
		 *		]
		 *	},
		 *  {
		 *   	text: '查询', 
		 *   	route: "#query",
		 *   	hasChildren: false
		 *  }]
		 * </code>
		 * 
		 *  @default 
		 * 		[]
		 */
		self.contextMenuItems = [];
		
		/**
		 * 窗口堆叠顺序，当使用cube.showContextmenu时，如果没有传入该属性值，该属性值会自动累加
		 * 
		 * @default 1060
		 */
		self.zIndex = 1060;
		
		/**
		 * 设置组件的left样式值，当使用cube.showContextmenu且传入srcDom参数时，该属性值会被重写计算
		 * @default null
		 */
		self.left = null;
		
		/**
		 * @ignore
		 */
		self.arrowLeft = null;
		
		/**
		 * 设置组件的right样式值，当使用cube.showContextmenu且传入srcDom参数时，该属性值会被重写计算
		 * @default null
		 */
		self.right = null;
		
		/**
		 * 设置组件的top样式值，当使用cube.showContextmenu且传入srcDom参数时，该属性值会被重写计算
		 * @default null
		 */
		self.top = null;
		
		/**
		 * 设置组件的bottom样式值，当使用cube.showContextmenu且传入srcDom参数时，该属性值会被重写计算
		 * @default null
		 */
		self.bottom = null;
		
		/**
		 * 当时使用indicate方法创建窗口时，会传入eleId（窗口节点的id）
		 * 
		 * @ignore
		 */
		self.eleId = null;
		
		/**
		 * 选中事件
		 */
		self.onSelectItemChanged = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 菜单选项改变
		 * @ignore
		 */
		self.ctxItemChanged = function(p_item) {
			self.isShowMenu(false);
			if (self.onSelectItemChanged!= null && !cube.isObservable(self.onSelectItemChanged)) {
				self.onSelectItemChanged(p_item);
			}
		};
		
		/**
		 * 隐藏菜单
		 * @ignore
		 */
		self.hideContextMenu = function(){
			self.isShowMenu(false);
		};
		
		/**
		 * @ignore
		 */
		self.mouseout = function($element, e) {
			var reltg = e.relatedTarget ? e.relatedTarget : e.toElement;  
			while( reltg && reltg != $element ) {
				reltg = reltg.parentNode;
			}
			
			if( reltg != $element ) {
				self.hideContextMenu();
				if (self.eleId() != null) {
					$("#"+self.eleId()).remove();
				}
			}
		};
		
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