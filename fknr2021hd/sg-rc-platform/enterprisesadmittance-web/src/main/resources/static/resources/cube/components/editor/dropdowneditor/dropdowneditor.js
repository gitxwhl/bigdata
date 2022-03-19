define([], function() {

	/**
	 * 下拉框组件
	 * 也可使用commoneditor组件通过设置editorType属性为DropDownEditor使用
	 * <code language="html">
	 * 		<dropdowneditor id="drop" params="
	 *									items: items, 
	 *									selectedValue: selectedValue,
	 *									isSplit: isSplit,
	 *									disabled: disabled,
	 *									isHrefRoute: isHrefRoute"></dropdowneditor>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.items = [
	 *								{value:"bj",text:"北京"},
	 *								{value:"sh",text:"上海"},
	 *								{value:"tj",text:"天津"},
	 *								{value:"js",text:"江苏"}
	 *							 ];
	 * 				self.selectedValue = 'bj';
	 * 				self.isSplit = true;
	 * 				self.disabled = false;
	 * 				self.isHrefRoute = true;
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
		 * 下拉框的选项
		 * @example
		 * [
		 *		{ value: "bj", text: "北京" },
		 *  	{ value: "sh", text: "上海" },
		 *  	{ value: "tj", text: "天津" },
		 *  	{ value: "js", text: "江苏" }
		 *  ]
		 *  
		 *  @default
		 *  	[]
		 */
		self.items = [];
		
		/**
		 * @ignore
		 */
		self.t_items = [];
		
		/**
		 * 是否显示默认空白项
		 * @default true
		 */
		self.isShowNull = true;
		
		/**
		 * 当isShowNull设置为false时，value未设置时或设置value为空，默认选中第一项
		 *  @default
		 * 		false
		 */
		self.isSelectedFirst = false;
		
		/**
		 * 是否显示复选框
		 * @default false
		 */
		self.isShowCheckbox = false;
		
		/**
		 * 是否允许输入
		 * @default false
		 */
		self.isAllowEdit = false;
		
		/**
		 * 是否获取焦点
		 * @default false
		 */
		self.hasFocus = false;
		
		/**
		 * 是否根据输入自动匹配下拉项
		 * @default true
		 */
		self.autoMatching = true;
		
		/**
		 * 是否为只读
		 * 
		 * @default false
		 */
		self.readOnly = false;
		
		/**
		 * 当前选中项，当在表单中使用时通过value设置值
		 * @default
		 * 		null
		 */
		self.selectedValue = null;
		
		/**
		 * 是否单击按钮弹出选择框
		 * @default
		 * 	false
		 */		
		self.isClickShow = false;
		
		/**
		 * 内部存储CheckListEditor的值
		 * @ignore
		 */
		self._checkListvalue = [];
		
		/**
		 * @ignore
		 */
		self.dropdownFocus = false;
		
		/**
		 * @ignore
		 */
		self._checkText = "";
			
		/**
		 * 内部模型属性，下拉框选中项
		 * @ignore
		 */
		self.selectedItem = cube.comp(function(){
			return self.getSelectedItem();
		},self);
		
		/**
		 * 是否为分裂下拉框
		 * @default
		 * 		false
		 */
		self.isSplit = false;
		
		/**
		 * 设置选择框按钮宽度
		 * @default
		 * 		auto
		 */
		self.width = 'auto';
		
		/**
		 * 设置下拉菜单最大高度，内容高度超出会出现滚动条。
		 * @default
		 * 		auto
		 */
		self.dropdownMenuHeight = 'auto';
		
		/**
		 * 是否显示滚动条
		 * @default
		 * 		false
		 */
		self.isShowScroll = false;
		
		/**
		 * 内部属性是否显示下拉框
		 * @ignore
		 */
		self.isShowDropdownOptions = false;
		
		/**
		 * 下拉框是否可用
		 * @default
		 * 		false
		 */
		self.disabled = false;
		
		/**
		 * 是否设置锚定值
		 *  @default
		 * 		false
		 */
		self.isHrefRoute = false;
		
		/**
		 * 是否外部显示
		 *  @default
		 * 		false
		 */
		self.isExtDisplay = false;
		
		/**
		 * 下拉框内容宽度过长时是否省略
		 *  @default
		 * 		false
		 */
		self.isTextEllipsis = false;
		
		/**
		 * 是否添加下拉项
		 * @ignore
		 */
		self.addItem = false;
		
		 /**
		 * 设置编辑器文本对齐方式
		 * @default main
		 */
		self.textAlign = "left";
		
		 /**
		 * 中间变量，弹出框溢出模式下，弹窗初始化位置。
		 * @ignore
		 */
		self._ulHeight = null;
		
		/**
		 * @ignore
		 */
		self._isInputValueChange = true;
		
		/**
		 * 选中变化处理事件。
		 * @param item 选中项
		 */
		self.onSelectedChanged = null;
		
		/**
		 * 编辑器获取焦点事件
		 * 
		 * @param value 编辑器的值
		 * @param viewModel 编辑器的viewModel对象
		 * @default null
		 */
		self.onFocus = null;
		
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
			
			if (self.isAllowEdit()) {
				self.t_items(cube.clone(self.items()));
			} else {
				self.t_items = self.items;
			}
			
			if(self.dropdownMenuHeight != 'auto'){
				self.isShowScroll(true);
			}
			
			self.itemschanged();
			self.itemsSub = cube.subscribe(self.items, self.itemschanged);
			
			self.checkListvalueSub = cube.subscribe(self._checkListvalue, self._checkListValuechanged);
			if (self.isShowCheckbox() == true && cube.notEmpty(self.selectedValue())) {
				self._checkListvalue(self.selectedValue().split(","));
			}
			
			if (self.isAllowEdit() && self.autoMatching()) {
				self.checkTextSub = cube.subscribe(self._checkText, function(newValue) {
					if (self._isInputValueChange()) {
						if (cube.notEmpty(newValue)) {
							self.t_items.removeAll();
							$.each(self.items(),function(){
								if (this.text.indexOf(newValue) != -1) {
									self.t_items.push(this);
								}
							});
						} else {
							self.t_items(cube.clone(self.items()));
							self._checkListvalue([]);
						}
						
						if (self.t_items().length > 0) {
							self.doOpenDropdownOptions();
						} else {
							self.doCloseDropdownOptions();
						}
						
						self.selectedValue(newValue);
					}
				});
			}
			
			//调用外部的selectedChange事件
			self.selectedValueSub = cube.subscribe(self.selectedValue, function(newValue) {
				if (!self._isInputValueChange()) {
					if (cube.isEmpty(newValue)) {
						self._checkListvalue([]);
					}
					
					if (self.onSelectedChanged!=null && !cube.isObservable(self.onSelectedChanged)) {
						self.onSelectedChanged(self.getSelectedItem());
					}
					
					if (self.isHrefRoute()) {
						window.location.hash = newValue;
					}
				}
			});
			
			self.hasFocusSub = cube.subscribe(self.hasFocus, function(value) {
				if (value && self.onFocus != null && !cube.isObservable(self.onFocus)) {
					self.onFocus(self.selectedValue(), self);
				}
			});
		};
		
		/**
		 * 展开下拉框菜单
		 */
		self.doOpenDropdownOptions = function(element, event) {
			var div = $(element).siblings('.dropdown-menu');
			
			if(element.className == "btn dropdown-toggle" &&  event.target.className != "caret"){
				div.animate({scrollTop:0},100);
			}

			if (self.isClickShow() && typeof(event) != "undefined" && event.type == "mouseover" && element.tagName == "BUTTON") {
				return;
			}
			
			if(self.isExtDisplay() && element != undefined && element.className == "btn dropdown-toggle" &&  event.target.className != "caret"){		
				self.showlog(element,div, event);	
			}
			
			if (self.disabled()==false && self.readOnly() == false && !self.isShowDropdownOptions()) {
				self.isShowDropdownOptions(true);
				self.dropdownFocus(true);
			}
									
		};
		
		/**
		 * @ignore
		 */
		self.showlog = function(element,div,event) {			
			div.css("position", "fixed");
			div.css("z-index", "5000");
			
			var target = (event.target) ? event.target : event.srcElement;
			var layerY  = (event.layerY) ? event.layerY : event.offsetY;
			var layerX  = (event.layerX) ? event.layerX : event.offsetX;

			if ((target.className.indexOf("btn dropdown-toggle") != -1 || target.parentElement.className.indexOf("btn dropdown-toggle") != -1) && event.clientX != layerX){			
				if($(window).height() - event.clientY - element.offsetHeight > div.innerHeight()){
					div.css("left", (event.clientX - layerX) - target.offsetLeft);
					div.css("top", (event.clientY - layerY) + element.offsetHeight - target.offsetTop);
				} else {
					var ulHeight = (event.clientY - layerY) - div.innerHeight();
					self._ulHeight(ulHeight);
					div.css("left", (event.clientX - layerX) - target.offsetLeft);
					div.css("top", ulHeight - target.offsetTop);
				}
			}
			
		};
		
		/**
		 * 新增选项
		 */
		self.appendItems = function(){
			self.items.push({value:params.data.addItem.value(),text:params.data.addItem.value()});
		};
		
		/**
		 * 关闭下拉框菜单
		 */
		self.doCloseDropdownOptions = function(viewModel, event) {
			if (self.isClickShow() && event != undefined) {
				var toElement = event.toElement || event.relatedTarget;
				if (toElement && toElement.tagName == "SPAN") {
					var parent = $(toElement).parent().parent();
					if (parent.hasClass("dropdowneditor")) {
						return;
					}
				} else if(toElement && toElement.tagName == "BUTTON"){
					if ($(toElement).hasClass("dropdown-toggle")) {
						return;
					}
				} else if(toElement && toElement.tagName == "INPUT"){
					var parent = $(toElement).parent().parent();
					if (parent.hasClass("dropdowneditor")) {
						return;
					}
				}
			}
			
			if(self.disabled()==false && self.isShowDropdownOptions()){
				self.isShowDropdownOptions(false);
			}
				
		};
		
		/**
		 * 选择下拉框选项
		 */
		self.selectItem = function() {
			if (self.isShowCheckbox()) {
				var _checkListvalue = self._checkListvalue();
				var _has = false;
				for (var j = 0; j < _checkListvalue.length; j++) {
					if (_checkListvalue[j] == this.value) {
						_has = true;
						break;
					}
				}
				
				if (_has) {
					self._checkListvalue.remove(this.value);
				} else {
					self._checkListvalue.push(this.value);
				}
				
			} else {
				self._isInputValueChange(false);
				self.selectedValue(this.value);
				self.isShowDropdownOptions(false);
				self._isInputValueChange(true);
			}
			
			if (self.isAllowEdit()) {
				self.getSelectedItem();
			}
		};
		
		/**
		 * 添加下拉框选项
		 * @param
		 *		p_item 一个对象
		 */
		self.appendItem = function (p_item) {
			self.items.push(p_item);
		};
		
		/**
		 * @ignore
		 */
		self._checkListValuechanged = function() {
			self.selectedValue(self._checkListvalue().join(","));
			
			if (self.isAllowEdit()) {
				self.getSelectedItem();
			}
		};
		
		/**
		 * @ignore
		 */
		self.itemschanged = function(p_item) {
			if (cube.isEmpty(self.selectedValue()) || typeof(p_item) != "undefined") {
				if ((self.isShowNull() || self.isShowCheckbox()) && !self.isSelectedFirst()) {
					self.selectedValue("");
				} else if(!self.isShowNull() && cube.notEmpty(self.items()) && self.items().length > 0 && self.isSelectedFirst()){
					self.selectedValue(self.items()[0].value);
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.getSelectedItem = function() {
			var sel = null;
			if (self.isShowCheckbox()) {
				if (self.items() && self.items().length > 0) {
					sel = [];
					var text = [];
					var _checkListvalue = self._checkListvalue();
					$.each(self.items(),function(){
						for (var j = 0; j < _checkListvalue.length; j++) {
							if (_checkListvalue[j] == this.value) {
								sel.push(this);
								text.push(this.text);
							}
						}
					});
					self._isInputValueChange(false);
					self._checkText(text.join(","));
					if (text.length == 0) {
						sel = null;
					}
					self._isInputValueChange(true);
				}
			} else {
				if (self.items() && self.items().length > 0) {
					if (cube.isEmpty(self.selectedValue())) {
						self._isInputValueChange(false);
						self._checkText("");
						self._isInputValueChange(true);
					} else {
						$.each(self.items(),function(){
							if (self.selectedValue() == this.value) {
								sel= this;
								self._isInputValueChange(false);
								self._checkText(this.text);
								self._isInputValueChange(true);
								return;
							}
						});
					}
				}
			}
			
			return sel;
		};
		
		/**
		 * @ignore
		 */
		self.doClickDropdownOptions = function(element, event) {
			var div = $(element).parent().siblings('.dropdown-menu');
			
			if(element.className == "btn dropdown-toggle split-toggle"){	
				div.animate({scrollTop:0},100);
			}
			
			if(self.isExtDisplay() && element.className == "btn dropdown-toggle split-toggle"){	
				self.showlog(element,div, event);
			}
			
			if (!self.disabled() && !self.readOnly()) {
				var isShowDropdownOptions = self.isShowDropdownOptions();
				if (isShowDropdownOptions) {
					self.isShowDropdownOptions(false);
					self.dropdownFocus(true);
				} else {
					self.isShowDropdownOptions(true);
					self.dropdownFocus(true);
				}
			}		
		};
		
		var isMousedownInner = false;
		var isMouseover = false;
		
		/**
		 * 单击打开模式下鼠标进入
		 * @ignore
		 */
		self.mouseoverPopoverDialog = function() {
			isMouseover = true;
			self.dropdownFocus(true);
		};
		
		/**
		 * 单击打开模式下鼠标点击
		 * @ignore
		 */
		self.mousedownInnerPopoverDialog = function() {
			isMousedownInner = true;
			isMouseover = false;
			self.dropdownFocus(true);
		};
		
		
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
		};
		
		cube.endViewModel(self, params);
	}
	
	//释放资源，包括compoted/subscrib/setInterval资源等。
	ViewModel.prototype.dispose = function() {
		this.selectedItem.dispose();
		this.itemsSub.dispose();
		this.checkListvalueSub.dispose();
		this.selectedValueSub.dispose();
		this.hasFocusSub.dispose();
	};
	return ViewModel;
});