define([], function() {

	/**
	 * 下拉树组件
	 * 也可使用commoneditor组件通过设置editorType属性为DropDownTreeEditor使用
	 * <code language="html">
	 * 		<dropdowntreeeditor params="
	 * 			treeOptions:treeOptions"
	 *  	></dropdowntreeeditor>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.treeOptions = {
	 * 					isShowCheckbox: true,
	 *					nodes:  [
	 *						{id:"a",text: '节点1',expanded:false,hasChildren:true, childNodes:[
	 *							{id:"a-1",text: '节点1-1',expanded: false,hasChildren:true, childNodes:[
	 *								{id:"a-1-1",text: '节点1-1-1'}
	 *							]}
	 *						]},
	 *						{id:"b",text: '节点2'}
	 *					]
	 *				}
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
		 * 树配置信息，参考树组件（tree）属性
		 */
		self.treeOptions = {};
		
		/**
		 * 当前选中值，节点ID值，多个使用逗号分割，当在表单中使用时通过value设置值
		 * @default
		 * 		null
		 */
		self.selectedValue = null;
		
		/**
		 * 是否为只读
		 * 
		 * @default false
		 */
		self.readOnly = false;
		
		/**
		 * 设置选择框按钮宽度
		 * @default
		 * 		auto
		 */
		self.width = 'auto';
		
		/**
		 * 是否显示滚动条
		 * @default
		 * 		false
		 */
		self.isShowScroll = false;
		
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
		 * 是否选中时关闭下拉框，该属性适用于节点选中不适用复选框选中
		 * @default
		 * 		false
		 */
		self.isSelectedClose = true;
		
		/**
		 * 是否外部显示
		 *  @default
		 * 		false
		 */
		self.isExtDisplay = false;
		
		/**
		 * 内部属性是否显示下拉框
		 * @ignore
		 */
		self.isShowDropdownOptions = false;
		
		/**
		 * 设置下拉菜单最大高度，内容高度超出会出现滚动条。
		 * @default
		 * 		auto
		 */
		self.dropdownMenuHeight = 'auto';
		
		/**
		 * 下拉框是否可用
		 * @default
		 * 		false
		 */
		self.disabled = false;
		
		 /**
		 * 设置编辑器文本对齐方式
		 * @default main
		 */
		self.textAlign = "left";
		
		/**
		 * @ignore
		 */
		self._checkText = null;
		
		 /**
		 * 中间变量，弹出框溢出模式下，弹窗初始化位置。
		 * @ignore
		 */
		self._ulHeight = null;
		
		/**
		 * @ignore
		 */
		self.dropdownFocus = false;
		
		/**
		 * 选中变化处理事件。
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
		
		var divMenu = null;
		//isExtDisplay为true时，点关闭下拉框会设置该变量为true，用于阻止修改下拉框left值。
		var isCloseDropdown = false;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			var treeOptions = self.treeOptions();
			var isShowCheckbox = typeof(treeOptions.isShowCheckbox) == "undefined"?false:treeOptions.isShowCheckbox;
			
			if (typeof(treeOptions.selectedNode) == "undefined") {
				treeOptions.selectedNode = cube.obj(null);
				if (!isShowCheckbox) {
					self.selectedNodeSub = cube.subscribe(treeOptions.selectedNode, self.selectedChanged);
				}
			}
			
			if (typeof(treeOptions.selectedNodeId) == "undefined") {
				treeOptions.selectedNodeId = cube.obj(null);
			} else if (!cube.isObservable(treeOptions.selectedNodeId)) {
				treeOptions.selectedNodeId = cube.obj(treeOptions.selectedNodeId);
			}
			
			if (typeof(treeOptions.checkedNode) == "undefined") {
				treeOptions.checkedNode = cube.array();
				if (isShowCheckbox) {
					self.checkedNodeSub = cube.subscribe(treeOptions.checkedNode, self.selectedChanged);
				}
			}
			
			if (self.isAllowEdit() && self.autoMatching()) {
				treeOptions.keyword = self._checkText;
			}
			
			treeOptions.onLoaded = self.nodesChanged;
			
			//调用外部的selectedChange事件
			self.selectedValueSub = cube.subscribe(self.selectedValue, function(newValue) {
				if (cube.isEmpty(newValue)) {
					if (cube.isObservable(treeOptions.selectedNodeId)) {
						treeOptions.selectedNodeId(null);
					}
					
					if (cube.isObservable(treeOptions.checkedNode)) {
						treeOptions.checkedNode([]);
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
		 * @ignore
		 */
		self.getCheckText = function(nodes, text, selectedValue, checkedNode) {
			if (cube.isObservable(nodes)) {
				nodes = nodes();
			}
			
			if (selectedValue) {
				$.each(nodes, function(){
					for (var j = 0; j < selectedValue.length; j++) {
						if (selectedValue[j] == this.id) {
							text.push(this.text);
							checkedNode.push(this);
						}
						 
						if((cube.isObservable(this.hasChildren) && this.hasChildren() == true) || this.hasChildren == true) {
							self.getCheckText(this.childNodes, text, selectedValue, checkedNode);
						} 
					}
				});
			}
			
			self._checkText(text.join(","));
		};
			
		/**
		 * @ignore
		 */
		self.nodesChanged = function(nodes) {
			var selectedValue = self.selectedValue();
			if (cube.isString(selectedValue)) {
				selectedValue = selectedValue.split(",");
			}
			
			var treeOptions = self.treeOptions();
			
			self.getCheckText(nodes, [], selectedValue, treeOptions.checkedNode);
		};
		
		/**
		 * @ignore
		 */
		self.selectedChanged = function(value) {
			var text = "";
			var ids = "";
			if (cube.isArray(value)) {
				for (var i = 0; i < value.length; i++) {
					text += value[i].text + ",";
					ids += value[i].id + ",";
				}
				
				if (cube.notEmpty(text)) {
					text = text.substring(0, text.length - 1);
				}
				
				if (cube.notEmpty(ids)) {
					ids = ids.substring(0, ids.length - 1);
				}
			} else if (cube.notEmpty(value)) {
				text += value.text;
				ids += value.id;
			}
			
			self._checkText(text);
			self.selectedValue(ids);
			
			if (self.onSelectedChanged != null && !cube.isObservable(self.onSelectedChanged)) {
				self.onSelectedChanged(ids);
			}
			
			if (self.isSelectedClose() && !cube.isArray(value)) {
				self.isShowDropdownOptions(false);
			}
		};
		
		/**
		 * @ignore
		 */
		self.doClickDropdownOptions = function(element, event) {
			var div = $(element).parents('.input-append').siblings('.dropdown-menu');
			divMenu = div;
			isCloseDropdown = false;
			if (self.disabled()==false && self.readOnly() == false) {
				var isShowDropdownOptions = self.isShowDropdownOptions();
				
				if (!isShowDropdownOptions) {
					if(self.isExtDisplay() && element != undefined){		
						self.showlog(element,event);	
					}
					
					if (!self.isAllowEdit()) {
						if (isShowDropdownOptions) {
							self.isShowDropdownOptions(false);
						} else {
							self.isShowDropdownOptions(true);
						}
						
						self.dropdownFocus(true);
					} else {
						self.isShowDropdownOptions(true);
					}
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.showlog = function(element,event) {	
			var div = $(element).parents('.input-append').siblings('.dropdown-menu');
			div.css("position", "fixed");
			div.css("z-index", "5000");
			div.css("min-width", div.innerWidth()+"px");			
			if(self.width() == "auto" || cube.isEmpty(self.width())){
				div.css("width", "auto");
			} else {
				div.css("width", self.width());
			}

			var target = (event.target) ? event.target : event.srcElement;
			var layerY  = (event.layerY) ? event.layerY : event.offsetY;
			var layerX  = (event.layerX) ? event.layerX : event.offsetX;

			self._resize(element,div,event,target,layerY,layerX);			
			div.resize(function(){
				self._resize(element,div,event,target,layerY,layerX);
			});		
		};
		
		/**
		 * @ignore
		 */
		self._resize = function(element,div,event,target,layerY,layerX) {
			if(event.target.className == "caret"){
				var positionX = event.clientX - layerX - target.offsetLeft - 4;
				var positionY = event.clientY - layerY - target.offsetTop - 3;
			} else {
				var positionX = event.clientX - layerX - target.offsetLeft;
				var positionY = event.clientY - layerY - target.offsetTop;
			}
		
			if(isCloseDropdown){
				return;
			}
			
			div.css("left", positionX);
			
			if($(window).height() - event.clientY - element.offsetHeight > div.innerHeight()){
				div.css("top", positionY + element.offsetHeight);
			} else {
				div.css("top", positionY - div.innerHeight());
			}
		};
		
		var isMousedownInner = false;
		var isMouseover = false;
		/**
		 * 关闭下拉框菜单
		 */
		self.doCloseDropdownOptions = function() {
			if(self.disabled()==false && self.isShowDropdownOptions()) {
				if (isMousedownInner==false || isMouseover== true && isMousedownInner==true) {
					self.isShowDropdownOptions(false);
					self.dropdownFocus(false);
					isMousedownInner = false;
					if(self.isExtDisplay()){
						divMenu.css("position", "relative");
						divMenu.css("width", "0px");
						divMenu.css('left','0px');
						isCloseDropdown = true;
					}
				} else {
					self.dropdownFocus(true);
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.mouseoverPopoverDialog = function() {
			isMouseover = true;
			self.dropdownFocus(true);
		};
		
		/**
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
		if (this.selectedNodeSub) {
			this.selectedNodeSub.dispose();
		}
		
		if (this.checkedNodeSub) {
			this.checkedNodeSub.dispose();
		}
		
		this.hasFocusSub.dispose();
	};
	return ViewModel;
});