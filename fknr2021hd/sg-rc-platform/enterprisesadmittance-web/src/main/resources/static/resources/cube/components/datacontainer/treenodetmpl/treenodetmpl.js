define([], function() {

	//注册模板组件
	cube.importComponent("datacontainer.treenodeTmpl")
	
	/**
	 * 树节点，与树组件配合使用
	 * @ignore
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**
		 * 是否显示复选框
		 * @default
		 * 		false
		 */
		self.isShowCheckbox = false;
		
		/**
		 * 选择框类型，可选：checkbox、radio
		 * @default
		 * 		checkbox
		 */
		self.checkboxType = "checkbox";
		
		/**
		 * 样式风格，可选：default, menu
		 * 
		 * @default
		 * 		default
		 */
		self.type = "default";
		
		/**
		 * 树节点
		 * @example
		 * 
		 * <code language="JavaScript">
		 * [
		 *  {
		 *   	text: "节点1",
		 *   	isExpanded: cube.obj(false), 
		 *   	childNodes: cube.array([{
		 *   			text: "节点1-1",
		 *      		isExpanded: cube.obj(false), 
		 *      }])
		 *  },
		 *  {
		 *   	text: "节点2",
		 *   	isExpanded: cube.obj(false)
		 *  }
		 * ]
		 * </code>
		 * 
		 *  @default 
		 * 		[]
		 */
		self.nodes = [];
		
		/**
		 * 当前选中节点
		 * @default
		 * 		null
		 */
		self.selectedNode = null;
		
		/** 当前选中节点
		 * @default
		 * 		null
		 */
		self.selectedNodeId = null;
		
		/**
		 * 内部属性，选中项
		 * 
		 * @default []
		 * @ignore
		 */
		self.checkedNode = [];
		
		/**
		 * @ignore
		 */
		self.level = 1;
		
		/**
		 *  @ignore
		 */
		self.selectNode = null;
		
		/**
		 *  @ignore
		 */
		self.checkNode = null;
		
		/**
		 *  @ignore
		 */
		self.dblClickNode =null;
		
		/**
		 *  @ignore
		 */
		self.expandNode =null;
		
		/**
		 *  @ignore
		 */
		self.ctxtmenu = null;
		
		/**
		 * @ignore
		 */
		self._editedItem = null;
		
		/**
		 * @ignore
		 */
		self.saveItem = null;
		
		/**
		 * @ignore
		 */
		self.cancelSaveItem = null;
		
		/**
		 * @ignore
		 */
		self.editItem = null;
		
		/**
		 * @ignore
		 */
		self._showeditedItem = null;
		
		/**
		 * 是否允许编辑
		 * @default false
		 */
		self.isAllowEdit = false;
		
		/**
		 * @ignore
		 */
		self.isNodeShowLoading = false;
		
		/**
		 * @ignore
		 */
		self.ctxMenuEdit = false;
		
		/**
		 * 切换节点展开的状态事件。
		 */
		self.onToggleExpand = null;

		/**
		 * 选中变化处理事件。
		 */
		self.onSelectedChanged = null;
		
		/**
		 * 双击树节点事件。
		 */
		self.onDblClickTreeNode = null;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			self.level(self.level() + 1 );
		}
		
		/**
		 * 显示编辑按钮
		 * @ignore
		 */
		self.showEdit = function(item) {
			self._showeditedItem(item);
		}
		
		/**
		 * 隐藏编辑按钮
		 * @ignore
		 */
		self.hideEdit = function() {
			self._showeditedItem(null);
		}
		
		/**
		 * @ignore
		 */
		self._selectNode = function() {
			self.selectNode(this, self.level());
		}
		
		/**
		 * @ignore
		 */
		self._dblClickNode = function() {
			self.dblClickNode(this, self.level());
		}
		
		/**
		 * @ignore
		 */
		self._expandNode = function() {
			self.expandNode(this, self.level());
		}
		
		/**
		 * @ignore
		 */
		self.getEnable = null;
		
		cube.endViewModel(self, params);
	}
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 		    
 	}
 	
	return ViewModel;
});