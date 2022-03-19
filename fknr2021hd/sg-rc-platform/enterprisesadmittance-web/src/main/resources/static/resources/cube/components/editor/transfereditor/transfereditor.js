define([], function() {

	/**
	 * 穿梭框组件
	 * 也可使用commoneditor组件通过设置editorType属性为TransferEditor使用
	 * <code language="html">
	 *	  		<transfereditor params="
	 *	  						list: list,
	 *	 						transferHeight:transferHeight,
	 *	 						transferWidth:transferWidth,
	 *	 						displayAddButton:displayAddButton,
	 *	 						displayDeleteButton:displayDeleteButton,
	 *	 						displayAddAllButton:displayAddAllButton,
	 *	 						displayDeleteAllButton:displayDeleteAllButton
	 *	 						"></transfereditor>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.list = [
	 *								{value:"bj",text:"北京"},
	 *								{value:"sh",text:"上海"},
	 *								{value:"tj",text:"天津"},
	 *								{value:"js",text:"江苏"}
	 *							 ];
	 * 				self.transferHeight = '300px';
	 * 				self.transferWidth = '100px';
	 * 				self.displayAddButton = true;
	 * 				self.displayDeleteButton = true;
	 * 				self.displayAddAllButton = true;
	 * 				self.displayDeleteAllButton = true;
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
		 * 设置组件的宽度
		 * @default
		 * 		auto
		 */
		self.width = 'auto';	
				
		/**
		 * 编辑内容，当编辑器多值时使用逗号分隔
		 * 例："1,2"
		 * 
		 * @default 空
		 */
		self.value = '';
				
		/**
		 * 编辑器选项列表
		 * 例：[{value:'1',text:'选择1'}]
		 * 
		 * @default null
		 */
		self.list = [];
		
		/**
		 * 设置显示复选框的高度 
		 * @default 200px
		 */
		self.transferHeight = "200px";
		
		/**
		 * 设置显示复选框的宽度 
		 * @default 100px
		 */
		self.transferWidth = "100px";
		
		/**
		 * 是否显示“添加”按钮
		 * @default
		 * 		true
		 */
		self.displayAddButton = true;
		
		/**
		 * 是否显示“删除”按钮
		 * @default
		 * 		true
		 */
		self.displayDeleteButton = true;
		
		/**
		 * 是否显示“添加全部”按钮
		 * @default
		 * 		true
		 */
		self.displayAddAllButton = true;
		
		/**
		 * 是否显示“删除全部”按钮
		 * @default
		 * 		true
		 */
		self.displayDeleteAllButton = true;
		
		/**
		 * @ignore
		 */
		self.listRight =  cube.array([]);
		
		/**
		 * @ignore
		 */
		self._addData = null;
		
		/**
		 * @ignore
		 */
		self._deleteData = null;
						
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * @ignore
		 */
		self.commonRendered = null;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			if (cube.notEmpty(self.value())) {
				var listValues =String(self.value()).split(",");
				for (var i = 0; i < listValues.length; i++) {
					var listValue = listValues[i];
					var list = self.list();
					for (var j = 0; j < list.length; j++) {
						var listItem = self.list()[j];			
						if(listValue == self.list()[j].value){
							self.listRight.push(listItem);
							self.list.remove(listItem);
						}				
					}
				}
				self.setTransferEditorValue();				
			}	
		}

		/**
		 * @ignore
		 */
		self._selectListItem = function($data) {		
			self._addData($data);
			self._deleteData($data);			
		}
		
		/**
		 * 双击添加
		 * @ignore
		 */
		self._abAddItem = function($data) {
			var data = $data;
			var list = self.list();
			for (var j = 0; j < list.length; j++) {
				var listItem = self.list()[j];			
				if(data.text == self.list()[j].text){
					self.list.remove(listItem);
					self.listRight.push(listItem);
				}				
			}
			self.setTransferEditorValue();		
		}
		
		/**
		 * 双击删除
		 * @ignore
		 */
		self._abRemoveItem = function($data) {
			var data = $data;
			var listRight = self.listRight();
			for (var j = 0; j < listRight.length; j++) {
				var listItem = self.listRight()[j];			
				if(data.text == listItem.text){
					self.list.push(listItem);
					self.listRight.remove(listItem);
				}				
			}
			self.setTransferEditorValue();	
		}
		
		/**
		 * 单击添加
		 * @ignore
		 */
		self._addItem = function() {			
			var listRight = self.listRight();
			for (var j = 0; j < listRight.length; j++) {
				var listItem = self.listRight()[j];			
				if(self._addData() == self.listRight()[j]){
					return;
				}				
			}
			
			if(cube.notEmpty(self._addData())){
				self.listRight.push(self._addData());
			}
						
			var list = self.list();
			for (var j = 0; j < list.length; j++) {
				var listItem = self.list()[j];			
				if(self._addData() == self.list()[j]){
					self.list.remove(listItem);
				}				
			}		
			self.setTransferEditorValue();			
		}
		
		/**
		 * 单击删除
		 * @ignore
		 */
		self._removeItem = function() {
			var list = self.list();
			for (var j = 0; j < list.length; j++) {
				var listItem = self.list()[j];			
				if(self._deleteData() == self.list()[j]){
					return;
				}				
			}
					
			if(cube.notEmpty(self._deleteData())){
				self.list.push(self._deleteData());
			}

			var listRight = self.listRight();
			for (var j = 0; j < listRight.length; j++) {
				var listItem = self.listRight()[j];			
				if(self._deleteData() == self.listRight()[j]){
					self.listRight.remove(listItem);
				}				
			}			
			self.setTransferEditorValue();
		}
		
		/**
		 * 添加全部
		 * @ignore
		 */
		self._addAllItems = function() {
			var list = self.list();
			for (var j = 0; j < list.length; j++) {
				var listItem = self.list()[j];			
				self.listRight.push(listItem);		
			}
			self.list.removeAll();			
			self.setTransferEditorValue();
		}
		
		/**
		 * 删除全部
		 * @ignore
		 */
		self._removeAllItems = function() {
			var listRight = self.listRight();
			for (var j = 0; j < listRight.length; j++) {
				var listItem = self.listRight()[j];			
				self.list.push(listItem);			
			}
			self.listRight.removeAll();
			self.setTransferEditorValue();
		}
			
		/**
		 * TransferEditor 的值
		 * @ignore
		 */
		self.setTransferEditorValue = function() {
			var listRight = self.listRight();
			var _listRight = "";			
			for (var j = 0; j < listRight.length; j++) {
				var listItem = self.listRight()[j].value;	
				if(_listRight == ''){
					_listRight = (listItem);
				} else {
					_listRight = (String(self.value())+','+listItem);
				}
				self.value(_listRight);
			}
			
			if (listRight.length == 0 ){
				self.value("");
			}
		}		
		
		/**
		 * @ignore
		 */
		self.onload = function(node, viewModel) {
			if (self.commonRendered != null && !cube.isObservable(self.commonRendered)) {
				self.commonRendered(node, viewModel);
			}
			
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node, viewModel);
			}
		}
		
		cube.endViewModel(self, params);
	}
		
	return ViewModel;
});