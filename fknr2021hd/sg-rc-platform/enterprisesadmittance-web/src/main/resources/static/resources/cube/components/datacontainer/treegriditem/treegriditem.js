define([], function() {
	
	/**
	 * 行纪录，与树表格组件配合使用
	 * @ignore
	 */
	function ViewModel(params) {
		var self = this;
		
		/**
		 * 行记录对象
		 * @ignore
		 */
		self.item = params.item;
		
		/**
		 * 父级对象
		 * @ignore
		 */
		self.parentItem= params.parentItem;
		
		/**
		 * 表格对象
		 * @ignore
		 */
		self.grid = params.grid;
		
		/**
		 * 层级
		 * @ignore
		 */
		self.level = params.level || 1;
		
		/**
		 * 行索引
		 * @ignore
		 */
		self.index = params.index || 0;
		
		/**
		 * 是否展开
		 * @ignore
		 */
		self.expanded = cube.obj(false);
		
		var hasLoad = false;
		
		/**
		 * 初始化
		 * @ignore
		 */
		self._init = function() {
			if (cube.isEmpty(self.item.items)) {
				self.item.items = cube.array();
			} else if (!cube.isObservable(self.item.items)) {
				self.item.items = cube.array(self.item.items);
			}
			
			if (self.grid.expanded() && !hasLoad) {
				self.expandItem();
			}
		}
		
		/**
		 * 展开行
		 * @ignore
		 */
		self.expandItem = function() {
			var expanded = self.expanded();
			self.expanded(!expanded);
			
			if (!hasLoad && self.expanded()) {
				self.grid.loadChildren(self.item);
				hasLoad = true;
			}
		}
		
		/**
		 * 加载完成
		 * @ignore
		 */
		self.onload = function() {
			self.grid.itemsChange();
		}
	}

	//释放资源，包括compoted/subscrib资源等。
 	ViewModel.prototype.dispose = function() {
 		
 	}

	return ViewModel;
});