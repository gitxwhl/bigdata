define(['treeEntityContainer'], function(entityContainerClass) {

	//注册模板组件
	cube.importComponent("datacontainer.treenodetmpl");
	cube.importComponent("editor.commoneditor");
	cube.importComponent("controls.loading");
	
	/**
	 * 树组件
	 * 如果配置url属性则不需要配置nodes属性。
	 * @example
	 * <code language="html">
	 *		<tree id="tree" params="nodes:  nodes,
	 *							isShowCheckbox: isShowCheckbox,
	 *							selectedNode:selectedNode,
	 *							checkedNode:checkedNode
	 *							"></tree>
	 * </code>
	 * <code language="JavaScript">
	 *		define([], function() {
	 *			var PageViewModel = function(params) {
	 *				var self = this;
	 *
	 *				self.nodes = [
	 *								{id:"a",text: '节点1',expanded:false,hasChildren:true, childNodes:[
	 *									{id:"a-1",text: '节点1-1',expanded: false,hasChildren:true, childNodes:[
	 *										{id:"a-1-1",text: '节点1-1-1',expanded: false,childNodes:[]}					 
	 *									]}
	 *								]},
	 *								{id:"b",text: '节点2',expanded: false,childNodes: []}
	 *							]
	 *				self.isShowCheckbox = false;
	 *				self.selectedNode = null;
	 *				self.checkedNode = [];
	 *
	 *				cube.endViewModel(self, params);
	 *			};
	 *			return PageViewModel;
	 *		});
	 *</code>
	 */
	function ViewModel(params) {
		var self = this;
		
		var entityContainer = new entityContainerClass();
		
		/**
		 * 设置组件宽度
		 * @default
		 *		auto
		 */
		self.width = 'auto';
		
		/**
		 * 设置组件高度
		 * @default
		 *		auto
		 */
		self.height = 'auto';
		
		/**
		 * 后台请求地址
		 * 
		 * @default 空
		 */
		self.url = "";
		
		/**
		 * 主键
		 * @default id
		 */
		self.primaryKey = "id";
		
		/**
		 * 获取或设置一个数字，表示请求的默认超时时间，以毫秒为单位，默认使用cube.timeout设置的时间。
		 * 
		 * @default null
		 */
		self.timeout = null;
		
		/**
		 * 加载数据时是否使用post方式提交参数，true：post方式 false：get方式
		 * 
		 * @default false
		 */
		self.loadByPost = false;
		
		/**
		 * text对应的属性名称，用于节点编辑保存时传递到后台的key名称
		 * 例：textColName的值为depname则保存时json为： {items: [{id: "123", depname: "节点新值"}]}
		 * @default text
		 */
		self.textColName = "text";
		
		/**
		 * 加载数据时传递到后端的参数，该属性时一个字符串或者一个JSON对象
		 * @default null
		 */
		self.args = null;
		
		/**
		 * 模糊查询关键字
		 * @default null
		 */
		self.keyword = null;
		
		/**
		 * 初始化时是否自动加载数据
		 *  @default true
		 */
		self.autoLoad = true;
		
		/**
		 * 样式风格，可选：default, menu
		 * 
		 * @default
		 *		default
		 */
		self.type = "default";
		
		/**
		 * 是否启用右击菜单功能
		 * @default
		 *		false
		 */
		self.isEnabledContextMenu = false;
		
		/**
		 * 设置超出层级之后右击菜单功能不再显示添加按钮
		 *  @default 0 不限制
		 */
		self.addLevel = 0;
		
		/**
		 * 是否显示选择框
		 * @default
		 *		false
		 */
		self.isShowCheckbox = false;
		
		/**
		 * 选择框类型，可选：checkbox、radio
		 * @default
		 *		checkbox
		 */
		self.checkboxType = "checkbox";
		
		/**
		 * 是否在整个树的单选中只能选择一个，默认为同一父节点的单选中只能选择一个，checkboxType属性为radio时生效
		 * @default false
		 */
		self.onlyCheckedOne = false;
		
		/**
		 * 是否显示边框
		 * @default
		 *		true
		 */
		self.isShowBorder = true;
		
		/**
		 * 是否显示边框阴影
		 * @default
		 *		false
		 */
		self.isShowBorderShadow = false;
		
		/**
		 * 是否允许编辑
		 * @default false
		 */
		self.isAllowEdit = false;
		
		/**
		 * 树节点选中时，是否级联选择其父子树节点。该属性在 isShowCheckbox 属性为 true 时有效。
		 * 
		 * @default true
		 */
		self.checkCascade = true;
		
		/**
		 * 是否只允许选择叶子节点
		 * @default false
		 */
		self.onlyCheckLeafNode = false;
		
		/**
		 * 鼠标右键上下文菜单内容，一个数组
		 *  @default 
		 *	[
		 *		{ text: "添加", route: "#add"}, 
		 *		{ text: "删除", route: "#delete"},
		 *	{ text: "编辑", route: "#edit" }
		 *	]
		 */
		self.contextMenuItems = [{
					text: cube.msg("EDIT"), 
					route: "#edit",
					icon: "edit"
				},{
					text: cube.msg("ADD"), 
					route: "#add",
					icon: "plus"
				}, {
					text: cube.msg("DELETE"), 
					route: "#delete",
					icon: "remove"
				}];
		
		/**
		 * 树节点
		 * @example
		 * 
		 * <code language="JavaScript">
		 * [
		 *	{
		 *		id: "a",
		 *		text: "节点1",
		 *		expanded: false, 
		 *		hasChildren: true,
		 *		childNodes: [{
		 *				id: "a-1",
		 *				text: "节点1-1",
		 *				hasChildren: false,
		 *			expanded: false, 
		 *		}]
		 *	},
		 *	{
		 *		id: "b",
		 *		text: "节点2",
		 *		hasChildren: false,
		 *		expanded: false
		 *	}
		 * ]
		 * </code>
		 * 
		 *  @default 
		 *		[]
		 */
		self.nodes = [];
		
		/**
		 * 获取当前已选中节点，该属性不用于设置选中节点
		 * @default
		 *		null
		 */
		self.selectedNode = null;
		
		/**
		 * 当前已选中节点Id，可设置该值选中节点
		 * @default
		 *		null
		 */
		self.selectedNodeId = null;
		
		/**
		 * 获取复选框已选中项，该属性不用于设置复选框选中节点
		 * 
		 * @default []
		 */
		self.checkedNode = [];
		
		/**
		 * 复选框已选中项Id，可设置该值选中节点
		 * 
		 * @default []
		 */
		self.checkedNodeId = [];
		
		/**
		 * 自定义REST请求的header
		 * @example
		 * <code language="JavaScript">
		 * { 
		 *		headRestType: "form", 
		 *		headMsg: "success"
		 * }
		 * </code>
		 * 
		 * @default null
		 */
		self.customHeaders = null;
		
		/**
		 * 是否启用手风琴模式，
		 * @default
		 *	false
		 */
		self.isEnabledAccordion = false;
		
		/**
		 *	加载效果的显示状态，true为显示，false为不显示
		 *	@default false
		 */
		self.isShowLoading = false;
		
		/**
		 * 设置只读节点的条件，可通过itemType或level设置，多个条件时使用数组
		 * 
		 * 示例：
		 * 
		 * self.readOnlyCondition = 2;//层级为2的设置为只读
		 * self.readOnlyCondition = [2, 3];//层级为2和3的设置为只读
		 * self.readOnlyCondition = "folder"; //itemType为folder的设置为只读
		 * self.readOnlyCondition = ["folder", "dept"];//itemType为folder和dept的设置为只读
		 * 
		 */
		self.readOnlyCondition = null;
		
		/**
		 * @ignore
		 */
		self.isNodeShowLoading = false;
		
		/**
		 * @ignore
		 */
		self._editedItem = null;
		
		/**
		 * @ignore
		 */
		self._showeditedItem = null;
		
		/**
		 * @ignore
		 */
		self.ctxMenuEdit = false;
		
		/**
		 * @ignore
		 */
		self.level = 1;
		
		/**
		 * 切换节点展开的状态事件。
		 * 
		 * @param node 节点对象
		 * @param level 节点所在层级
		 * @param expand Boolean值，true为展开，false为隐藏
		 */
		self.onToggleExpand = null;

		/**
		 * 选中变化处理事件。
		 * @param node 节点对象
		 * @param level 节点所在层级
		 */
		self.onSelectedChanged = null;
		
		/**
		 * 选中复选框变化处理事件。
		 * @param nodes 节点对象数组
		 */
		self.onCheckedChanged = null;
		
		/**
		 * 删除选中处理事件。
		 */
		self.onRemoveSelected = null;
		
		/**
		 * 双击树节点事件。
		 * @param node 节点对象
		 * @param level 节点所在层级
		 */
		self.onDblClickTreeNode = null;
		
		/**
		 * 选中右键菜单事件。
		 * @param node 节点对象
		 */
		self.onCtxMenuItemChanged = null;
		
		/**
		 * 树节点保存之前调用，取消保存返回false或者设置args.cancel = true
		 * @param args 该参数有三个属性
		 *  {
		 *		node: editedItem,//节点信息
		 *		cancel: false, //是否取消保存
		 *		params: null //为args.params赋值则会追加到向后台传递节点数据中，例：args.params = {title: "123"};
		 *	};
		 */
		self.onNodeSaving = null;
		
		/**
		 * 树节点保存成功事件
		 * @param node 节点对象
		 */
		self.onNodeSaved = null;
		
		/**
		 * 树加载完成事件。
		 * @param nodes 节点对象数组
		 */
		self.onLoaded = null;
		
		/**
		 * 树数据加载失败事件
		 * @params args 错误信息
		 */
		self.onLoadError = null;
		
		/**
		 * @ignore
		 */
		self.mouseLeft = cube.obj("");
		
		/**
		 * @ignore
		 */
		self.mouseTop = cube.obj("");
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			if (self.type() == "menu") {
				self.isAllowEdit(false);
				self.isShowCheckbox(false);
			}
			
			self.selectedNodeIdSub = cube.subscribe(self.selectedNodeId, function(newValue) {
				self.setExpandedById(newValue);			
			});
			
			self.urlSub = cube.subscribe(self.url, function(newValue) {
				entityContainer.baseUrl = newValue;
				self.load();
			});
			
			self.argsSub = cube.subscribe(self.args, function(newValue) {
				self.load();
			});
			
			self.checkNodeIdSub = cube.subscribe(self.checkedNodeId, function(p_ids) {
				var nodes = self.nodes();
				var checkedNode = self.checkedNode();
				//checkNodeId中存在，checkedNode中不存在的进行选中处理
				for (var i = 0; i < p_ids.length; i++) {
					var node = nodes["#" + p_ids[i] ];
					if (cube.notEmpty(node) && checkedNode.indexOf(node) == -1) {
						node.checked(true);
						self.checkedNode.push(node);
						
						if (self.checkCascade()) {
							self.checkCascadeNodes(node, true);
						}
					}
				}
				
				//checkedNode中存在，checkedNodeId中不存在的进行取消选中处理
				for (var i = checkedNode.length - 1; i >= 0; i--) {
					var node = checkedNode[i];
					if (p_ids.indexOf(node.id) == -1) {
						node.checked(false);
						if (self.checkCascade()) {
							self.checkCascadeNodes(node, false);
						}
						self.checkedNode.remove(node);
					}
				}
			});
			
			self.checkedNodeSub = cube.subscribe(self.checkedNode, function(p_nodes) {
				if (p_nodes.length == 0) {
					var nodes = self.nodes();
					for (var key in nodes) {
						if (nodes[key] && typeof(nodes[key].checked) == "function") {
							nodes[key].checked(false);
							nodes[key].indeterminate(false);
						}
					}
				}
			});
			
			self.keywordSub = cube.subscribe(self.keyword, self.filterNodes);
			
			entityContainer.baseUrl = self.url();
			entityContainer.type = "tree";
			entityContainer.primaryKey = self.primaryKey();
			entityContainer.textColName = self.textColName();
			entityContainer.timeout = self.timeout();
			entityContainer.data = self.nodes;
			entityContainer.customHeaders = self.customHeaders();
			entityContainer.loadByPost = self.loadByPost();
			entityContainer.onload = onload;
			entityContainer.onerror = _error;
			entityContainer.init();
			
			if (self.autoLoad()) {
				self.load();
			}
		};
		
		/**
		 * 通过关键字查找节点并显示
		 *  @ignore
		 */
		self.filterNodes = function(newValue, node) {
			var nodes = null;
			if (cube.notEmpty(node)) {
				nodes = node.childNodes();
			} else {
				nodes = self.nodes();
			}
			
			var filterCount = 0;
			if (cube.notEmpty(newValue)) {
				var textColName = self.textColName();
				for (var i = nodes.length-1; i >=0; i--) {
					var resultCount = 0;
					if (nodes[i].hasChildren()) {
						resultCount = self.filterNodes(newValue, nodes[i]);
					}
					
					if (nodes[i][textColName].indexOf(newValue) != -1 || resultCount > 0) {
						nodes[i].visible(true);
						filterCount++;
					} else {
						nodes[i].visible(false);
					}
				}
			} else {
				for (var i = nodes.length-1; i >=0; i--) {
					nodes[i].visible(true);
				}
			}
			
			return filterCount;
		};
		
		/**
		 * 通过Id获取节点
		 * @param p_id
		 */
		self.getNodeById = function(p_id) {
			var nodes = self.nodes();
			return nodes["#" + p_id ];
		};
		
		/**
		 * 复选框选中节点的所有子节点
		 *	@param
		 *		p_node 节点
		 *		p_checked，一个Boolean值，true为选中，false为未选中
		 */
		self.checkChildNodes = function(p_node, p_checked) {
			var nodes = p_node.childNodes;
			if (cube.isObservable(nodes)) {
				nodes = nodes();
			}
			
			$.each(nodes, function() {
				this.indeterminate(false);
				if (p_checked) {
					this.checked(true);
					self.checkedNode.push(this);
					self.checkedNodeId.push(String(this.id));
				} else {
					this.checked(false);
					self.checkedNode.remove(this);
					self.checkedNodeId.remove(String(this.id));
				}
				
				if((cube.isObservable(this.hasChildren) && this.hasChildren() == true) || this.hasChildren == true) {
					self.checkChildNodes(this, p_checked);
				} 
			});
		};
		
		/**
		 * @ignore
		 */
		self.checkParentNodes = function(node, checked) {
			var parentNode = null;
			var nodes = self.nodes();
			var childNodes = [];
			var checkedNode = self.checkedNode();
			if (node) {
				parentNode = nodes["#" + node.parentId];
			}
			
			if (!checked) {
				node.indeterminate(false);
				if (parentNode) {
					parentNode.indeterminate(false);
				}
			}
			
			if (parentNode) {
				while (parentNode) {
					childNodes = parentNode.childNodes;
					if (cube.isObservable(childNodes)) {
						childNodes = childNodes();
					}
					
					var checkedCount = 0;
					var indeterminateCount = 0;
					$.each(childNodes, function() {
						if (checkedNode.indexOf(this) != -1 || this.checked()) {
							checkedCount++;
						}
						
						if (this.indeterminate()) {
							indeterminateCount++;
						}
					});
					
					if (checkedCount != 0 && checkedCount == childNodes.length) {
						if (self.checkboxType() == "checkbox") {
							if (!self.onlyCheckLeafNode() || (self.onlyCheckLeafNode() && !parentNode.hasChildren())) {
								self.checkedNode.push(parentNode);
								self.checkedNodeId.push(String(parentNode.id));
							}
							
							parentNode.checked(true);
							parentNode.indeterminate(false);
						} else {
							parentNode.indeterminate(true);
						}
					} else {
						if (self.checkboxType() == "checkbox") {
							parentNode.checked(false);
							self.checkedNode.remove(parentNode);
							self.checkedNodeId.remove(String(parentNode.id));
						}
						
						if (checkedCount == 0 && indeterminateCount == 0) {
							parentNode.indeterminate(false);
						} else {
							parentNode.indeterminate(true);
						}
					}
					
					parentNode = nodes["#" + parentNode.parentId];		
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.expandNode = function(node, level) {
			node = node || this;
			level = cube.isNumber(level) ? level : self.level();
			
			var hasChildren = cube.isObservable(node.hasChildren) ? node.hasChildren() : node.hasChildren;
			var childNodes = cube.isObservable(node.childNodes) ? node.childNodes() : node.childNodes;
			if (cube.notEmpty(self.url()) && hasChildren && (cube.isEmpty(childNodes) || childNodes.length == 0)) {
				self.isNodeShowLoading(true);
				entityContainer.onload = null;
				entityContainer.load(node, function(args){
					node.childNodes(args.nodes);
					self.isNodeShowLoading(false);
					
					if (self.checkCascade()) {
						var checkedNode = self.checkedNode();
						if (checkedNode.indexOf(node) != -1) {
							self.checkChildNodes(node, true);
						}
					}
				});
			}
			
			node.expanded(!node.expanded());
			
			if(self.onToggleExpand !=null && !cube.isObservable(self.onToggleExpand)) {
				self.onToggleExpand(node, level, node.expanded());
			} 
		};
		
		/**
		 * @ignore
		 */
		self.checkNode = function(viewMode, event) {
			var element = $(event.currentTarget ? event.currentTarget : event.srcElement); 
			if (self.checkboxType() == "radio") {
				var nodes = self.nodes();
				var parentNode = null;
				if (viewMode) {
					parentNode = nodes["#" + viewMode.parentId];
				}
				
				var hasNode = false;
				if (self.checkedNode().indexOf(viewMode) != -1) {
					element.attr("checked", false);
					hasNode = true;
				}
				
				if (self.onlyCheckedOne()) {
					for (var key in nodes) {
						if (nodes[key] && typeof(nodes[key].checked) == "function") {
							nodes[key].checked(false);
							self.checkedNode.remove(nodes[key]);
							self.checkedNodeId.remove(nodes[key].id);
						}
					}
				} else {
					if (parentNode) {
						var childNodes = parentNode.childNodes;
						if (cube.isObservable(childNodes)) {
							childNodes = childNodes();
						}
						
						$.each(childNodes, function() {
							this.checked(false);
							self.checkedNode.remove(this);
							self.checkedNodeId.remove(this.id);
						});
					} else {
						$.each(nodes, function() {
							this.checked(false);
							self.checkedNode.remove(this);
							self.checkedNodeId.remove(this.id);
						});
					}
				}
				
				if (!hasNode) {
					self.checkedNode.push(viewMode);
				}
			}
			
			var checkedNode = self.checkedNode();
			if (checkedNode.indexOf(this) != -1) {
				this.checked(true);
				self.checkedNodeId.push(this.id);
			} else {
				this.checked(false);
				self.checkedNodeId.remove(this.id);
			}
			
			if (self.checkCascade()) {
				self.checkCascadeNodes(this, this.checked());
			}
			
			if (self.onCheckedChanged != null && !cube.isObservable(self.onCheckedChanged)) {
				self.onCheckedChanged(self.checkedNode());
			}
			
			return true;
		};
		
		/**
		 * @ignore
		 */
		self.checkCascadeNodes = function(node, checked) {
			if(self.checkboxType() == "checkbox" && ((cube.isObservable(node.hasChildren) && node.hasChildren() == true) || node.hasChildren == true)) {
				self.checkChildNodes(node, checked);
			}
			
			self.checkParentNodes(node, checked);
		};
		
		/**
		 * @ignore
		 */
		self.selectNode = function(node, level) {
			var nodes = self.nodes();			
			var parentNode = null;
			
			node = node || this;
			level = cube.isNumber(level) ? level : self.level();
			
			var hasChildren = cube.isObservable(node.hasChildren) ? node.hasChildren() : node.hasChildren;
			if (!self.onlyCheckLeafNode() || !hasChildren) {
				self.selectedNode(node);
				self.selectedNodeId(node.id);
				
				if(self.onSelectedChanged!=null && !cube.isObservable(self.onSelectedChanged)) {
					self.onSelectedChanged(node, level);
				}
			}
			
			if(self.isEnabledAccordion()){
				self.setExpandedById(node.id);
			} else {
				if (self.type() == "menu") {
					var childNodes = cube.isObservable(node.childNodes) ? node.childNodes() : node.childNodes;
					if (cube.notEmpty(self.url()) && hasChildren && (cube.isEmpty(childNodes) || childNodes.length == 0)) {
						self.isNodeShowLoading(true);
						entityContainer.onload = null;
						entityContainer.load(node, function(args){
							node.childNodes(args.nodes);
							self.isNodeShowLoading(false);
						});
					}
					
					node.expanded(!node.expanded());
					
					if(self.onToggleExpand !=null && !cube.isObservable(self.onToggleExpand)) {
						self.onToggleExpand(node, level,  node.expanded());
					}
				}
			}
		};
		
		/**
		 * 删除选中节点
		 */
		self.removeSelectNode = function() {
			var node = self.selectedNode();
			if(node){
				var callback = null;
				if(self.onRemoveSelected!=null && !cube.isObservable(self.onRemoveSelected)) {
					callback = self.onRemoveSelected;
				}
				entityContainer.remove(node, callback);
			}
		};
		
		/**
		 * 删除复选框选中节点
		 */
		self.removeCheckedNode = function() {
			var nodes = self.checkedNode();
			if (nodes && nodes.length > 0) {
				if (cube.notEmpty(self.url())) {
					entityContainer.remove(nodes);
				} else {
					for (var i = 0; i<nodes.length; i++) {
						entityContainer.remove(nodes[i]);
					}
				}
			}
		};
		
		/**
		 * 向节点下添加节点
		 * @param
		 *		p_node 节点，例：{text: "新节点"}
		 *		p_option 参数，{parentId: "a", order: false}, parentId为父节点Id，order为true时添加为最后一个，order为false是添加为第一个
		 *		p_save 是否保存到后台 一个boolean值，true保存到后台，false不保存到后台
		 */
		self.appendNode = function(p_node, p_option, p_save) {
			var node = entityContainer.create(p_node, p_option);
			var pk = node[self.primaryKey()];
			
			if(typeof(p_save) != "undefined" && p_save == true){
				entityContainer.save(function(arg){
					var data = null;
					if (arg && arg.resultValue && arg.resultValue.items.length > 0) {
						data = arg.resultValue.items[0];
					} else {
						data = node;
					}
					
					entityContainer.data()["#" + pk][self.primaryKey()] = data[self.primaryKey()];
					
					if (self.onNodeSaved != null && !cube.isObservable(self.onNodeSaved)) {
						self.onNodeSaved(data);
					}
				});
			} else {
				return node;
			}
		};
		
		/**
		 * 通过节点Id设置选中节点
		 * 
		 * @param p_id 一个字符串，节点ID
		 */
		self.setSelectedNodeById = function(p_id) {
			self.selectedNodeId(p_id);
			self.setExpandedById(p_id);
		};
		
		/**
		 * 通过节点Id设置复选框选中节点
		 * 
		 * @param p_ids 一个数组
		 */
		self.setCheckedNodeById = function(p_ids) {
			var nodes = self.nodes();
			for (var i = 0; i < p_ids.length; i++) {
				var node = nodes["#" + p_ids[i]];
				if (cube.notEmpty(node) && self.checkedNode().indexOf(node) == -1) {
					node.checked(true);
					self.checkedNode.push(node);
					self.checkedNodeId.push(String(p_ids[i]));
					
					if (self.checkCascade()) {
						self.checkCascadeNodes(node, true);
					}
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.setExpandedById = function(p_id) {
			var nodes = self.nodes();
			
			self.selectedNode(nodes["#" + p_id]);
			
			var parentNode = null;
			var node = nodes["#" + p_id ];
			if (node) {
				parentNode = nodes["#" + node.parentId];
			}
			if(self.isEnabledAccordion()){
				self.treeExpanded(nodes);
			}
			
			if (parentNode) {
				while (parentNode) {
					parentNode.expanded(true);
					parentNode = nodes["#" + parentNode.parentId];		
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.dblClickNode = function(node, level) {
			node = node || this;
			level = cube.isNumber(level) ? level : self.level();
			
			var hasChildren = cube.isObservable(node.hasChildren) ? node.hasChildren() : node.hasChildren;
			if (!self.onlyCheckLeafNode() || !hasChildren) {
				self.selectedNode(node);
				self.selectedNodeId(node.id);
				
				if(self.onDblClickTreeNode!=null && !cube.isObservable(self.onDblClickTreeNode)) {
					self.onDblClickTreeNode(node, level);
				}
			}
			
			var childNodes = cube.isObservable(node.childNodes) ? node.childNodes() : node.childNodes;
			if (cube.notEmpty(self.url()) && hasChildren && (cube.isEmpty(childNodes) || childNodes.length == 0)) {
				self.isNodeShowLoading(true);
				entityContainer.onload = null;
				entityContainer.load(node, function(args){
					node.childNodes(args.nodes);
					self.isNodeShowLoading(false);
					
					if (self.checkCascade()) {
						var checkedNode = self.checkedNode();
						if (checkedNode.indexOf(node) != -1) {
							self.checkChildNodes(node, true);
						}
					}
					
				});
			}
			
			node.expanded(!node.expanded());
			
			if(self.onToggleExpand !=null && !cube.isObservable(self.onToggleExpand)) {
				self.onToggleExpand(node, level, node.expanded());
			}
		};
		
		/**
		 *  @ignore
		 */
		self.ctxMenuItemChanged = function(p_item) {
			var selectedNode = self.selectedNode();
			
			if (self.onCtxMenuItemChanged != null && !cube.isObservable(self.onCtxMenuItemChanged)) {
				self.onCtxMenuItemChanged(p_item, selectedNode);
			} else {
				if (p_item.route == "#edit") {
					self.editItem(selectedNode);
					self.ctxMenuEdit(true);
				} else if(p_item.route == "#add") {
					var node = self.appendNode({text: ""}, {parentId: selectedNode.id, order: false}, false);
					self.editItem(node);
					self.ctxMenuEdit(true);
				} else if(p_item.route == "#delete") {
					cube.indicate("confirm", cube.msg("CONFIRM_DELETE"), function() {
						if (entityContainer.checkSaved(selectedNode[self.primaryKey()])) {
							self.removeSelectNode();
						} else{
							var nodes = self.nodes();
							var parentNode = nodes["#" + selectedNode.parentId];
							if (parentNode && cube.isObservable(parentNode.childNodes)) {
								parentNode.childNodes.remove(selectedNode);
								delete nodes["#" + selectedNode[self.primaryKey()]];
							}
						}
					});
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.ctxtmenu = function(viewModel, event) {
			if (self.isEnabledContextMenu()) {
				self.selectedNode(viewModel);
				
				var contextMenuItems = self.contextMenuItems();
				var addLevel = self.addLevel();
				if (addLevel > 0 && viewModel.level > addLevel) {
					contextMenuItems = cube.clone(contextMenuItems);
					contextMenuItems.splice(1, 1);
				}
				
				cube.showContextmenu(event, {
					contextMenuItems: contextMenuItems,
					onSelectItemChanged: self.ctxMenuItemChanged
				});
			}
		};
		
		/**
		 * @ignore
		 */
		self.treeExpanded = function(nodes){
			var nodes = self.nodes();		
			var node = nodes["#" + self.selectedNodeId()];
			
			var parentNode = null;
			var parentIds = [];
			if (node) {
				parentNode = nodes["#" + node.parentId];
			}
			
			if (parentNode) {
				while (parentNode) {
					parentIds.push(String(parentNode.id));
					parentNode = nodes["#" + parentNode.parentId];		
				}
			}
			
			for (var k in nodes) {
				if(k.indexOf('#') == 0){
					if(nodes[k] == node || parentIds.indexOf(String(nodes[k].id)) != -1) {
						nodes[k].expanded(true);
					} else {
						nodes[k].expanded(false);
					}
				}
			}
		};	
		
		/**
		 * 编辑项
		 * @ignore
		 */
		var _oldText = null;
		self.editItem = function(item) {
			if (typeof(item.text) != "undefined" && !cube.isObservable(item.text)) {
				item.text = cube.obj(item.text);
			}
			_oldText = item.text();
			
			self._editedItem(item);
		};
		
		/**
		 * 保存编辑项
		 * @ignore
		 */
		self.saveItem = function() {
			var editedItem = cube.clone(self._editedItem());
			if (typeof(editedItem.text) != "undefined" && cube.isObservable(editedItem.text)) {
				editedItem.text = editedItem.text();
			}
			
			entityContainer.setValue(editedItem, self.textColName(), editedItem.text);
			
			var result = true;
			var args = {
				node: editedItem,
				cancel: false,
				params: null
			};
			
			if (self.onNodeSaving != null && !cube.isObservable(self.onNodeSaving)) {
				result = self.onNodeSaving(args);
			}
			
			if (result === false || args.cancel) {
				return;
			}
			
			if (cube.notEmpty(args.params) && cube.isObject(args.params)) {
				for (var key in args.params) {
					entityContainer.setValue(editedItem, key, args.params[key]);
				}
			}
			
			var p_params = {parentID: editedItem.parentId};
			entityContainer.save(p_params, function(arg){
				var data = null;
				if (arg && arg.resultValue && arg.resultValue.items && arg.resultValue.items.length > 0) {
					data = arg.resultValue.items[0];
				} else {
					data = editedItem;
				}
				
				if (self.onNodeSaved != null && !cube.isObservable(self.onNodeSaved)) {
					self.onNodeSaved(data);
				}
				
				self._editedItem(null);
			});
		};
		
		/**
		 * 取消保存
		 * @ignore
		 */
		self.cancelSaveItem = function() {
			self._editedItem().text(_oldText);
			self._editedItem(null);
			_oldText = null;
		};
		
		/**
		 * 显示编辑按钮
		 * @ignore
		 */
		self.showEdit = function(item) {
			self._showeditedItem(item);
		};
		
		/**
		 * 隐藏编辑按钮
		 * @ignore
		 */
		self.hideEdit = function() {
			self._showeditedItem(null);
		};
		
		/**
		 * @ignore
		 */
		self.getEnable = function(node) {
			if (self.onlyCheckLeafNode() && node.hasChildren()) {
				return false;
			}
			
			var readOnlyCondition = self.readOnlyCondition();
			if(cube.isEmpty(readOnlyCondition)){
				return true;
			} else if (cube.isArray(readOnlyCondition)) {
				for (var i = 0; i < readOnlyCondition.length; i++) {
					if (cube.isNumber(readOnlyCondition[i])) {
						if (node.level == readOnlyCondition[i]) {
							return false;
						}
					} else {
						if (node.itemType == readOnlyCondition[i]) {
							return false;
						}
					}
				}
			} else if (cube.isNumber(readOnlyCondition)) {
				if (node.level == readOnlyCondition) {
					return false;
				}
			} else {
				if (node.itemType == readOnlyCondition) {
					return false;
				}
			}
			
			return true;
		};
		
		/**
		 * 保存选中节点Id，当isShowCheckbox属性为true时保存复选框选中项节点Id，否则保存点击选中的节点Id
		 *  @param p_callback 保存成功回调函数
		 */
		self.save = function(p_callback) {
			var items = [];
			if (self.isShowCheckbox()) {
				items = self.checkedNodeId();
			} else {
				items.push(self.selectedNodeId());
			}
			
			entityContainer.save({"items": items}, true, p_callback);
		};
		
		/**
		 * 加载数据
		 * 
		 */
		self.load = function(p_args) {
			self.isShowLoading(true);
			
			var args = self.args();
			if (cube.notEmpty(p_args)) {
				if (cube.isEmpty(args)) {
					args = p_args;
				} else {
					args = $.extend(args, p_args);
				}
			}
			entityContainer.onload = onload;
			entityContainer.data = self.nodes;
			entityContainer.init();
			entityContainer.load(args);
		};
		
		// 数据加载回调函数
		function onload(args) {
			var nodes = self.nodes();
			if (cube.notEmpty(self.selectedNodeId())) {
				self.selectedNode(nodes["#" + self.selectedNodeId()]);
				self.setExpandedById(self.selectedNodeId());
			}
			
			if (cube.notEmpty(self.checkedNodeId())) {
				var checkedNode = self.checkedNode();
				var p_ids = self.checkedNodeId();
				for (var i = 0; i < p_ids.length; i++) {
					var node = nodes["#" + p_ids[i] ];
					if (cube.notEmpty(node) && checkedNode.indexOf(node) == -1) {
						node.checked(true);
						self.checkedNode.push(node);
						
						if (self.checkCascade()) {
							self.checkCascadeNodes(node, true);
						}
					}
				}
			}
			
			self.isShowLoading(false);
			
			if (self.onLoaded != null && !cube.isObservable(self.onLoaded)) {
				self.onLoaded(self.nodes());
			}
		}
		
		function _error(args) {
			self.isShowLoading(false);
			self.isNodeShowLoading(false);
			
			if (self.onLoadError != null && !cube.isObservable(self.onLoadError)) {
				self.onLoadError(args);
			}
		}
		
		cube.endViewModel(self, params);
	}
	
	//释放资源，包括compoted/subscrib/setInterval资源等。
	ViewModel.prototype.dispose = function() {
		this.checkedNodeSub.dispose();
		this.selectedNodeIdSub.dispose();
		this.checkNodeIdSub.dispose();
		this.keywordSub.dispose();
		this.urlSub.dispose();
	};
	
	return ViewModel;
});