define(["../datagrid/datagrid.js"], function(datagrid) {
	
	/**
	 * 列锁定表格
	 * API可参看datagrid组件
	 * @example
	 * <code language="html">
	 *		<lockedgrid id="lockedgrid" params="
	 *						url: url,
	 *						columns: columns
	 *						"></lockedgrid>
	 * </code>
	 * <code language="JavaScript">
	 *		define([], function() {
	 *			var PageViewModel = function(params) {
	 *				var self = this;
	 * 
	 *				self.url = "";
	 * 
	 *				self.columns = [
	 *					{	
	 *						name: "depid", 
	 *						width:"90px",
	 *						locked:true, //使用locked标识列是否锁定
	 *						caption: "部门编号" , 
	 *						editorType: "TextEditor"	
	 *					},
	 *					{	name: "depname", width:"90px", locked:true, caption: "部门姓名" , editorType: "TextEditor"	},
	 *					{	name: "pardepid", width:"90px", caption: "上级部门" , editorType: "TextEditor"	},
	 *					{	name: "departdesc", width:"180px",caption: "部门描述" , editorType: "TextEditor"	},
	 *					{	name: "powerconsumption",width:"80px", caption: "用电量" , editorType: "TextEditor"	}
	 *				];
	 *				
	 *				cube.endViewModel(self, params);
	 *			};
	 * 
	 *			return PageViewModel;
	 *		});
	 * </code> 
	 */
	function ViewModel(params) {
		params._lazyInit = true;
		datagrid.call(this, params);
		
		var base = {};
		var self = this;
		
		/**
		 * @ignore
		 */
		self.lockedColumns = [];
		
		/**
		 * @ignore
		 */
		self._hasHScroll = false;
		
		/**
		 * @ignore
		 */
		self._emptyWidth = null;
		
		/**
		 * @ignore
		 */
		self._isSetHeight = false;
		
		/**
		 * @ignore
		 */
		self.$node = null;
		
		/**
		 * @ignore
		 */
		base._init = self._init;
		self._init = function() {
			self._hasVScrollSub = cube.subscribe(self._hasVScroll, self.adjustWidth);
			self._selectedItemSub = cube.subscribe(self._selectedItem, self.adjustHeight);
			self.isShowLoadingSub = cube.subscribe(self.isShowLoading, self.adjustHeight);
			
			base._init();
		};
		
		var autoPercentCount = 0;
		/**
		 * @ignore
		 */
		self.initColumn = function(args) {
			var columns = self.columns();
			if (columns && columns.length > 0 && self.lockedColumns().length == 0) {
				for (var i = 0; i < columns.length; i++) {
					var p_column = columns[i];
					if (cube.isEmpty(p_column)) {
						return true;
					}
					
					if (typeof(p_column.validStatus) == "undefined") {
						p_column.validStatus = cube.obj("");
					} else if (!cube.isObservable(p_column.validStatus)) {
						p_column.validStatus = cube.obj(p_column.validStatus);
					}
					
					if (typeof(p_column.validHint) == "undefined") {
						p_column.validHint = cube.obj("");
					} else if (!cube.isObservable(p_column.validHint)) {
						p_column.validHint = cube.obj(p_column.validHint);
					}
					
					if (typeof(p_column.align) == "undefined") {
						p_column.align = "";
					}
					
					if (typeof(p_column.width) == "undefined") {
						p_column.width = cube.obj(self.columnDefaultWidth());
						autoPercentCount++;
					} else if (!cube.isObservable(p_column.width)) {
						if (cube.notEmpty(p_column.width)) {
							if (p_column.width == "auto" || (cube.isString(p_column.width) && p_column.width.indexOf("%") != -1)) {
								autoPercentCount++;
							}
						}
						p_column.width = cube.obj(p_column.width);
					}
					
					if (!p_column.editorType) {
						p_column.editorType = "TextEditor";
					}
					
					if (p_column.editorType == "DropDownEditor" || p_column.editorType == "CheckListEditor" || p_column.editorType == "ListEditor" || p_column.editorType == "LabelEditor") {
						if (args && args.dicts && args.dicts[p_column.name]) {
							p_column.list = args.dicts[p_column.name];
						}
					} else if(p_column.editorType == "FileEditor") {
						if (!p_column.pkVal) {
							p_column.pkVal = cube.obj(null);
						} else if(!cube.isObservable(p_column.pkVal)) {
							p_column.pkVal = cube.obj(p_column.pkVal);
						}
					}
					
					if (p_column.locked === true) {
						self.lockedColumns.push(p_column);
					}
				}
				
				self.adjustWidth();
			}
		};
		
		/**
		 * 设置表格宽度
		 * @ignore
		 */
		self.adjustWidth = function() {
			if (cube.notEmpty(self.$node) && !cube.isObservable(self.$node)) {
				var tableLeftWidth = 0;
				var tableRightWidth = 0;
				
				var tableLeft = self.$node.find(".table-main");
				var tableRight = self.$node.find(".table-right");
				var $leftColumns = tableLeft.find(".tableHeader th");
				var $leftTableBody = tableLeft.find(".tableBody");
				var $rightTableBody = tableRight.find(".tableBody");
				var $operationsTableBody = self.$node.find(".table-operations .tableBody");
				for (var i = 0; i < $leftColumns.length; i++) {
					if (i != $leftColumns.length - 1) {
						tableLeftWidth += $leftColumns.eq(i).outerWidth();
					}
				}
				
				var operationsWidth = 2;
				if (self.isAllowOperations()) {
					operationsWidth = self.$node.find(".table-operations").outerWidth();
					
					if (cube.ieVersion > -1 && cube.ieVersion <= 8.0) {
						operationsWidth++;
					}
				}
				
				tableLeft.width(tableLeftWidth);
				tableRight.css("left", tableLeftWidth + 1);
				tableRight.width(self.$node.children(".lockedgrid").outerWidth() - tableLeftWidth - operationsWidth);
				self._emptyWidth(tableRight.find(".tableHeader .table").width() + "px");
				
				setBodyHeight();
			}
		};
		
		/**
		 * 调整三个表格的行高
		 * @ignore
		 */
		self.adjustHeight = function(p_value) {
			if (cube.isBoolean(p_value)) {
				if (!p_value) {
					setTimeout(function() {
						var leftTr = self.$node.find(".table-main .tableBody .table tr");
						var rightTr = self.$node.find(".table-right .tableBody .table tr");
						var operationsTr = self.$node.find(".table-operations .tableBody .table tr");
						for (var i = 0; i < leftTr.length; i++) {
							setHeight(leftTr.eq(i), rightTr.eq(i), operationsTr.eq(i));
						}
						
						setBodyHeight();
					}, 200);
				}
			} else { 
				var index = self._editedItemIndex;
				if (cube.isObservable(index)) {
					index = index();
				}
				var leftTr = self.$node.find(".table-main .tableBody .table tr:eq(" + index + ")");
				var rightTr = self.$node.find(".table-right .tableBody .table tr:eq(" + index + ")");
				var operationsTr = self.$node.find(".table-operations .tableBody .table tr:eq(" + index + ")");
				
				if (p_value != null) {
					setTimeout(function() {
						setHeight(leftTr, rightTr, operationsTr);
					}, 150);
				} else {
					leftTr.css("height", "");
					rightTr.css("height", "");
					operationsTr.css("height", "");
				}
			}
		};
		
		//设置行高
		function setHeight(leftTr, rightTr, operationsTr) {
			var leftHeight = leftTr.height();
			var rightHeight = rightTr.height();
			
			if (leftHeight > rightHeight) {
				rightTr.height(leftHeight);
				operationsTr.height(leftHeight);
			} else {
				leftTr.height(rightHeight);
				operationsTr.height(rightHeight);
			}
		}
		
		//设置锁定和操作表的高度
		function setBodyHeight() {
			if (cube.isObservable(self.$node)) {
				return;
			}
			
			var tableLeft = self.$node.find(".table-main");
			var tableRight = self.$node.find(".table-right");
			var $leftTableBody = tableLeft.find(".tableBody");
			var $rightTableBody = tableRight.find(".tableBody");
			var $operationsTableBody = self.$node.find(".table-operations .tableBody");
			if (tableRight[0].scrollWidth > tableRight[0].clientWidth) {
				self._hasHScroll(true);
				self.isShowTempColumn(false);
				$leftTableBody.height(parseInt($rightTableBody.height(),10) - 16);
				$operationsTableBody.height(parseInt($rightTableBody.height(),10) - 16);
			} else {
				self._hasHScroll(false);
				$leftTableBody.height(parseInt($rightTableBody.height(),10));
				$operationsTableBody.height(parseInt($rightTableBody.height(),10));
				
				if (autoPercentCount == 0) {
					self.isShowTempColumn(true);
				}
			}
			
			var gridHeight = self.height();
			if (isNaN(parseInt(gridHeight,10)) || (cube.isString(gridHeight) && gridHeight.indexOf("%") != -1)) {
				self.$node.children(".lockedgrid").height(parseInt(tableRight.height(),10));
			} else {
				self._isSetHeight(true);
			}
		}
		
		/**
		 * @ignore
		 */
		base.onload = self.onload;
		self.onload = function(node) {
			base.onload();
			
			self.$node = $(node);
			self.$tableBody = self.$node.find(".table-right .tableBody");
			self.$tableHeader = self.$node.find(".table-right .tableHeader .table");
			var $leftTableBody = self.$node.find(".table-main .tableBody");
			self.$tableBody.scroll(function(e) {
				self.$tableHeader.css("left", 0 - e.target.scrollLeft);
				
				if (!self.isAllowOperations()) {
					$leftTableBody.scrollTop(e.target.scrollTop);
				}
			});
			
			if (self.isAllowOperations()) {
				var $operationsTableBody = self.$node.find(".table-operations .tableBody");
				
				$operationsTableBody.scroll(function(e) {
					$leftTableBody.scrollTop(e.target.scrollTop);
					self.$tableBody.scrollTop(e.target.scrollTop);
				});
			}
			
			self.$node.children(".lockedgrid").resize(function(){ 
				self.adjustWidth();
			});
			
			self.adjustWidth();
		};
		
		cube.endViewModel(self, params);
	}
	
	//释放资源，包括compoted/subscrib资源等。
	ViewModel.prototype.dispose = function() {
		this._hasVScrollSub.dispose();
		this._selectedItemSub.dispose();
		
		if (this.isShowLoadingSub) {
			this.isShowLoadingSub.dispose();
		}
	};
	
	return ViewModel;
});