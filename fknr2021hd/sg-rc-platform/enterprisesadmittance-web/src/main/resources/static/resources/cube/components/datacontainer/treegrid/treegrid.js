define(["../datagrid/datagrid.js", 'entityContainer'], function(datagrid, entityContainerClass) {
	
	cube.importComponent("datacontainer.treegriditem");
	
	/**
	 * 树级表格
	 * 其他属性和方法可参看datagrid组件API（部分属性不支持）
	 * 注：加载子数据时请求后端url为: url属性值/{id}/children，如果使用items属性静态数据使用items嵌套。
	 * 
	 * @example
	 * <code language="html">
	 * 		<treegrid id="treegrid" params="
	 * 						url: url,
	 *						columns: columns
	 *						"></treegrid>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.url = "";
	 * 
	 * 				self.columns = [
	 *					 { name: "depid", width:"90px", caption: "部门编号" , editorType: "TextEditor" },
	 *					 {	name: "depname", width:"90px", caption: "部门姓名" , editorType: "TextEditor"	},
	 *					 {	name: "pardepid", width:"90px", caption: "上级部门" , editorType: "TextEditor"	},
	 *					 {	name: "departdesc", width:"180px",caption: "部门描述" , editorType: "TextEditor"	},
	 *					 {	name: "powerconsumption",width:"80px", caption: "用电量" , editorType: "TextEditor"	}
	 *				];
	 * 				
	 *				cube.endViewModel(self, params);
	 * 			};
	 * 
	 * 			return PageViewModel;
	 * 		});
	 * </code> 
	 */
	function ViewModel(params) {
		params._lazyInit = true;
		datagrid.call(this, params);
		
		var base = {};
		var self = this;
		
		/**
	     * 该属性将用作以树型结构显示的字段值。
	     * 
	     * @default null
	     */
		self.treeField = null;
		
		/**
	     * 设置表格是否默认展开
	     * 
	     * @default false
	     */
		self.expanded = false;
		
		/**
		 * 当前编辑的item的父级item
		 * @ignore
		 */
		self.parentItem = null;
		
		var entityContainer = new entityContainerClass();
		
		/**
		 * @ignore
		 */
		base._init = self._init;
		self._init = function() {
			base._init();
			
			entityContainer.primaryKey = self.primaryKey();
			entityContainer.actions = self.actions();
			entityContainer.type = "grid";
			entityContainer.timeout = self.timeout();
			entityContainer.dicts = self.dicts();
			entityContainer.loadMeta = false;
			entityContainer.customHeaders = self.customHeaders();
			entityContainer.loadByPost = self.loadByPost();
			entityContainer.init();
		}
		
		/** 编辑项
		 * @ignore
		 */
		base.editItem = self.editItem;
		self.editItem = function(item, index, parentItem) {
			base.editItem(item, index);
			self.parentItem = parentItem;
		}
		
		/**
		 * @ignore
		 * 设置选中项
		 */
		base.setSelectedItem = self.setSelectedItem;
		self.setSelectedItem = function(item, index, parentItem) {
			base.setSelectedItem(item, index);
			if (self._isEdited()) {
				self.parentItem = parentItem;
			}
		}
		
		/**
		 *  添加数据，如果主键通过服务器生成，则需要采用数据实体组件！
		 *  @param p_item 可选，行数据，一个json对象。
		 *  @param p_edit 可选，是否为编辑状态
		 *  
		 */
		self.appendItem = function(p_item, p_edit) {
			var checkedItem = null;
			var selectedItems = self._selectedItems();
			if (cube.notEmpty(selectedItems) && cube.isArray(selectedItems) && selectedItems.length > 0) {
				checkedItem = selectedItems[0];
			}
			
			if (self.isAllEdited() || self.validate(0)) {
				var obj = {};
				var isSet = false;
				var columns = self.columns();
				
				if (self.isAllEdited()) {
					var _editorValid = self._editorValid();
					var i = _editorValid.length;
					_editorValid[i] = {};
				}
				
				var _appendedItem = self._appendedItem;
				if (cube.notEmpty(p_item) && !cube.isBoolean(p_item)) {
					_appendedItem = p_item;
				}
				
				var attrCount = 0;
				for (var key in _appendedItem) {
					attrCount++;
					if (self.isAllEdited()) {
						obj[key] = cube.obj(cube.isObservable(_appendedItem[key])?_appendedItem[key]():_appendedItem[key]);
					} else {
						if (cube.isObservable(_appendedItem[key])) {
							obj[key] = _appendedItem[key]();
						} else {
							obj[key] = _appendedItem[key];
						}
					}
					
					if (self.isAllEdited()) {
						var valid = {
							validStatus: cube.obj(""),
							validHint: cube.obj("")
						};
							
						_editorValid[i][key] = valid;
					}
					
					
					for (var k = 0; k < columns.length; k++) {
						if (key == columns[k].name && typeof(columns[k].value) != "undefined") {
							if (cube.isObservable(columns[k].value)) {
								self._appendedItem[key] = columns[k].value;
								self._appendedItemObj[key] = columns[k].value();
							} else {
								self._appendedItem[key](columns[k].value);
								self._appendedItemObj[key] = columns[k].value;
							}
							isSet = true;
							break;
						}
					}
					
					if (!isSet) {
						if (cube.notEmpty(self._appendedItem[key]) && cube.isObservable(self._appendedItem[key])){
							self._appendedItem[key](null);
						}
						
						if (cube.notEmpty(self._appendedItemObj[key])){
							self._appendedItemObj[key] = null;
						}
						
						isSet = false;
					}
				}
				
				if (attrCount == 0) {
					var appendColumns = self._columns;
					if (appendColumns && appendColumns.length > 0) {
						$.each(appendColumns, function() {
							if (typeof(this.value) != "undefined") {
								if (cube.isObservable(this.value)) {
									obj[this.name] = this.value;
								} else {
									obj[this.name] = cube.obj(this.value);
								}
							} else {
								obj[this.name] =  cube.obj(null);
							}
							
							if (self.isAllEdited()) {
								var valid = {
									validStatus: cube.obj(""),
									validHint: cube.obj("")
								};
									
								_editorValid[i][this.name] = valid;
							}
							
						});
					}
				}
				
				if (cube.notEmpty(checkedItem)) {
					obj.parentId = checkedItem[self.primaryKey()];
					checkedItem.items.push(obj);
				} else {
					self.items.push(obj);
				}
				
				if (cube.notEmpty(p_edit) && p_edit === true) {
					var editObj = {};
					for (var key in obj) {
						editObj[key] = cube.obj(obj[key]);
					}
					self._isEdited(true);
					self._editedItem = editObj;
					self._selectedItem(obj);
				}
				
				self.itemsChange();
			}
		}
		
		/**
		 * 加载子数据
		 * @ignore
		 */
		self.loadChildren = function(p_item) {
			var id = p_item[self.primaryKey()];
			var url = self.url();
			if (url.lastIndexOf("/") != url.length - 1) {
				url += "/";
	        }
			entityContainer.setBaseUrl(url +  id + "/children");
			entityContainer.onload = function(args) {
				p_item.items(args.items);
			};
			
			entityContainer.load();
		}
		
		/**
		 * @ignore
		 */
		self.shiftupItem = function(p_item, parentItem) {
			var items = self.items;
			if (self.parentItem != null) {
				items = parentItem.items;
			}
			
			var i = items.indexOf(p_item);
			if (i > 0) {
				items.splice(i, 1);
				items.splice(i - 1, 0, p_item);
			}
		};

		/**
		 * @ignore
		 */
		self.shiftdownItem = function(p_item, parentItem) {
			var items = self.items;
			if (self.parentItem != null) {
				items = parentItem.items;
			}
			
			var i = items.indexOf(p_item);
			if (i < items().length - 1) {
				items.splice(i, 1);
				items.splice(i + 1, 0, p_item);
			}
		};
		
		/**
		 *  @ignore
		 */
		self.replaceItem = function(p_editedItemIndex, p_obj) {
			var items = self.items;
			if (self.parentItem != null) {
				items = self.parentItem.items;
			}
			
			if (items) {
				if (cube.isNumber(p_editedItemIndex)) {
					items.replace(items()[self._editedItemIndex], p_obj);
				} else {
					items.replace(self._editedItemObj, p_obj);
				}
			}
		}

		cube.endViewModel(self, params);
	}

	//释放资源，包括compoted/subscrib资源等。
 	ViewModel.prototype.dispose = function() {
 		
 	}

	return ViewModel;
});