define(["entityContainer", "JSONUtil", "CheckUtil", "Validator" ],
		function(entityContainerClass, jsonUtil, checkUtil, validator) {
			// 依赖组件注入
			cube.importComponent("editor.commoneditor");
			cube.importComponent("editor.datetimeeditor");
			cube.importComponent("editor.dropdowneditor");
			cube.importComponent("editor.dropdowntreeeditor");
			cube.importComponent("controls.loading");
	
			/**
			 * 查询框组件
			 * 
			 * @example <code language="html">
			 *		<searchbox id="form" params="style: style,
			 *								fields: fields
			 *								"></searchbox>
			 * </code>
			 * <code language="JavaScript">
			 *		define([], function() {
			 *			var PageViewModel = function(params) {
			 *				var self = this;
			 * 
			 *				self.style = '';
			 *				self.fields = [
			 *								{ name: "name", caption: "名称", editorType:"TextEditor",readOnly: false,nullable:false},
			 *								{ name: "level", caption: "级别", editorType: "DropDownEditor", allowEditing: true,
			 *									list:[{text:"a", value:"a"},{text:"c", value:"c"},
			 *											{text:"b", value:"b"}]
			 *								}
			 *							];
			 * 
			 *				cube.endViewModel(self, params);
			 *			};
			 *			return PageViewModel;
			 *		});
			 * </code>
			 */
			
			/**
			 * 查询框组件
			 * 
			 * @example <code language="html">
			 *		<searchbox id="form" params="style: style,
			 *								fields: fields,
			 *								colNum:colNum,
			 *								isLineFeed:isLineFeed,
			 *								isDefaultLayout:isDefaultLayout
			 *								"></searchbox>
			 * </code>
			 * <code language="JavaScript">
			 *define([], function() {
			 *	var PageViewModel = function(params) {
			 *	var self = this;
			 *	
			 *	self.style = '';
			 *	self.colNum = 2;
			 *	self.isLineFeed = false;
			 *	self.isDefaultLayout = false;
			 *	self.fields = [							
			 *					{ name: "level2", caption: "工作大类:", editorType: "DropDownEditor", width:"80px",captionWidth:"100px",
			 *						list:[{text:"a市", value:"a"},
			 *							{text:"b市", value:"b"},
			 *							{text:"c市", value:"c"}
			 *						]
			 *					},
			 *					{ name: "level3", caption: "工作小类:", editorType: "DropDownEditor", width:"250px",captionWidth:"150px",
			 *						list:[{text:"a县", value:"a"},
			 *							{text:"b县", value:"b"},
			 *							{text:"c县", value:"c"}
			 *						]
			 *					}, 
			 *					{ name: "birthday", caption: "工作时间", editorType:"DateTimeEditor",width:"200px",captionWidth:"150px"},
			 *					{ name: "name8", caption: "工作内容:", editorType:"TextEditor", width:"475px",captionWidth:"100px"}, 
			 *					{ name: "leve", caption: "启用标识:", editorType: "CheckListEditor", type: "checkbox", width:"250px",captionWidth:"155px",
			 *						list:[{text:"", value:"yes"}]
			 *					}
			 *					];
			 *	
			 *					cube.endViewModel(self, params);
			 *	};
			 *				
			 *	return PageViewModel;
			 *});
			 * </code>
			 */
			function ViewModel(params) {
				var self = this;

				var entityContainer = new entityContainerClass();
				
				/**
				 * 后台请求地址， 可通过设置该属性从后台获取查询框默认值
				 * 
				 * @default 空
				 */
				self.url = "";
				
				/** 
				 * 一个字符串，表示请求数据的唯一标识。
				 * @default null
				 */
				self.id = null;
				
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
				 * 是否显示提交按钮
				 * 
				 * @default true
				 */
				self.displaySearchButton = true;

				/**
				 * 是否显示重置按钮
				 * 
				 * @default true
				 */
				self.displayResetBtns = true;
				
				/**
				 * 设置按钮对齐位置, main、center、right
				 * 
				 * @default null
				 */
				self.buttonAlign = null;
				
				/**
				 * 标题和编辑器之间的分隔符，各个编辑器可使用showSeparator设置是否显示
				 * 
				 *  @default 空
				 */
				self.separator = "";
				
				/**
				 * 一个 JSON 对象，表示查询参数。
				 * 
				 * @default null
				 */
				self.param = null;

				/**
				 * 表格样式
				 * 
				 * @default 空
				 */
				self.style = "";

				/**
				 * 表单字段格式
				 * 
				 * @example <code language="JavaScript">
				 * [
				 *	{ 
				 *		name: "name",
				 *		caption: "名称",
				 *		editorType:"TextEditor",
				 *		//spanNum：6,
				 *		//captionSpanNum: 4,
				 *		//editorSpanNum: 8,
				 *		//是否根据后台返回设置数据字典，默认值true
				 *		//autoDict: false
				 *  },
				 *  { 
				 *		name: "age", 
				 *		caption: "年龄",
				 *		editorType:"NumberEditor"
				 *  }
				 *  ]
				 * </code>
				 * 可通过以下属性组合调整field宽度，field包括标题和编辑器，spanNum指bootstrap中的栅格，取值范围1-12。
				 * spanNum：field的spanNum
				 * captionSpanNum: 标题的spanNum
				 * editorSpanNum: 编辑器所在的spanNum
				 * captionWidth: 标题的宽度
				 * width: 编辑器宽度
				 * colspan: field跨列数
				 * 
				 * @default []
				 */
				self.fields = [];

				/**
				 * 本地字段默认值，当设置url属性时，优先通过url从后台读取数据。
				 * 
				 * @example
				 *  <code language="JavaScript">
				 * {"id":"a1","name":"小明","label":"小名的label","level":"b", "boy":true,"girl":false,"language":"English"}
				 * </code>
				 * 
				 * @default
				 *		null
				 */
				self.data = null;
				
				/**
				 * 字段编辑器，以key-value形式存储，通过编辑器name获取编辑器信息。
				 *  @example
				 * <code language="JavaScript">
				 *		var vm = cube.getPageViewModelByNode($("#search"));
				 *		var nameEditor = vm.editors["name"];
				 *	
				 *  </code>
				 * @default null
				 */
				self.editors = null;
				
				/**
				 * 获取或设置一个 Boolean 值，表示是否对查询框中输入的文本做sql注入检查。默认检查
				 * 
				 * @default true
				 */
				self.sqlCheck = true;

				/**
				 * 获取或设置所有编辑器宽, 默认宽度136px
				 * 
				 * @default 136px
				 */
				self.editorWidth = cube.theme == "small"?"98px":(cube.theme == "middle"?"116px":"136px");
				
				/**
				 * 标题的默认spanNum，spanNum指bootstrap中的栅格，取值范围1-12。
				 * @default 5
				 */
				self.captionSpanNum = 5;
				
				/**
				 * 编辑器的默认spanNum，spanNum指bootstrap中的栅格，取值范围1-12。
				 * @default 7
				 */
				self.editorSpanNum = 7;
				
				/**
				 * 获取或设置"查询"按钮是否换行
				 * 
				 * @default true
				 */
				self.isLineFeed = true;
				
				/**
				 * 获取或设置是否查询框默认对齐排列布局
				 * 
				 * @default true
				 */
				self.isDefaultLayout = true;
				
				/**
				 * 获取或设置每行显示列数，当isDefaultLayout属性为true时生效。
				 * 
				 * @default 3
				 */
				self.colNum = 3;
				
				/**
				 * 重置时是否将默认值重置为空
				 * 
				 * @default false
				 */
				self.emptyDefaultValue = false;
				
				/**
				 * 获取或设置包含sql关键字的查询条件是否提交后台查询
				 * 
				 * @default true
				 */
				self.allowSqlKeyQuery = true;

				/**
				 * 获取或设置表格的查询条件类型。json 表示查询参数filter 的值采用 Json 对象的形式传递。string
				 * 表示查询参数 filter 的值采用字符串的形式传递。 该字段必须在初始化前设置。
				 * <p>
				 * 该字段可选的值包括：
				 * <ul>
				 * <li><b>json</b></li>
				 * <li><b>string</b></li>
				 * </ul>
				 * </p>
				 */
				self.searchParamType = "string";

				/**
				 * 获取或初始化设置查询框中输入的值是否需要编码。
				 * 
				 * @default false
				 */
				self.encodeValue = false;

				/**
				 * 要查询表格组件标签的Id
				 */
				self.gridId = null;
				
				/**
				 * 自定义按钮
				 * [{
				 *		text : "收藏",
				 *		type : "success",//type属性取值为default、primary、success、info、warning、danger、inverse、link
				 *		visible: true, //不设置时默认为true
				 *		valid: true,	//点击按钮时是否对编辑器进行校验
				 *		click: function() {
				 *		//点击按钮时触发
				 *		}
				 *	}, {
				 *		text : "查看",
				 *		click: function() {
				 *		//点击按钮时触发
				 *		}
				 *	}]
				 */
				self.customBtns = [];
				
				/**
				 * 验证消息提示方式，可选：inline、pop
				 * @default pop
				 */
				self.validHintType = "pop";
				
				/**
				 * 是否进行校验
				 * @default true
				 */
				self.isValid = true;
				
				/**
				 * 是否使用自定义模板
				 * 自定义模板放入dataform标签内。使用“编辑器名称.属性名称”显示fields设置的内容。
				 * @example
				 * <code language="html">
				 *		<search id="sear" params="
				 *						fields: fields
				 *						isCustomTemplate:true">
				 *			<table cellspacing="0" cellpadding="0">
				 *				<tr height="30px">
				 *					<td class="caption"><span option="mingcheng.caption"></span>：</td>
				 *					<td class="caption" ><span option="mingcheng.editor"></span><span option="mingcheng.validHint"></span></td>
				 *					<td class="caption">类型：</td>
				 *					<td class="caption"><span option="type.editor"></span></td>
				 *					<td class="caption">重大：</td>
				 *					<td class="caption"><span option="zhongda.editor"></span></td>
				 *				</tr>
				 *			</table>
				 *
				 *		</search>
				 *		<cubebutton params="text:'查询',onClick:searchClick"></cubebutton>
				 *		<cubebutton params="text:'重置',onClick:resetClick"></cubebutton>
				 * </code>
				 * <code language="JavaScript">
				 *		define([], function() {
				 *			var PageViewModel = function(params) {
				 *				var self = this;
				 * 
				 *				self.fields = [
				 *						{	name: "mingcheng", caption: "名称", editorType: "TextEditor", nullable:false},
				 *						{	name: "type", caption: "类型", editorType: "DropDownEditor",list:[{value:'1',text:'类型1'},{value:'2',text:'类型2'}],},
				 *						{	name: "zhongda", caption: "重大", editorType: "CheckListEditor", list:[{value:'1',text:'是'},{value:'2',text:'否'}],type:"radio"},
				 *					
				 *					];
				 *
				 *				self.searchClick = function(){
				 *					cube.getPageViewModelByNode($("#sear")).search();
				 *				}
				 *				
				 *				self.resetClick = function(){
				 *					cube.getPageViewModelByNode($("#sear")).reset();
				 *				}
				 * 
				 *				cube.endViewModel(self, params);
				 *			};
				 *			return PageViewModel;
				 *		});
				 * </code>
				 * 
				 * <code language="JavaScript">
				 * 注：例fields中一项的name为“mingcheng”，则<br/>
				 *	显示该项caption：<span option="mingcheng.caption"><br/>
				 *	显示该项编辑器：<span option="mingcheng.editor"><br/>
				 *	显示该项验证提示信息：<span option="mingcheng.validHint"><br/>
				 *	如果嵌套其他组件，组件属性设置ViewModel中变量使用$parent.变量名<br/>
				 * </code>
				 * 
				 * @default false
				 */
				self.isCustomTemplate = true;

				/**
				 * @ignore 暂存查询条件
				 */
				self._params = null;

				/**
				 * @ignore
				 */
				self._dicts = null;
				
				/**
				 * @ignore
				 */
				self._editorWidth1 = "";
				
				/**
				 * @ignore
				 */
				self._editorWidth2 = "";
				
				/**
				 * @ignore
				 */
				self._colnum = "three";
				
				/**
				 * @ignore
				 */
				self.isShowPopDialog = false;

				/**
				 * 查询回调事件。 在点击查询按钮时调用外部的onSearch事件，同时传递查询条件参数。
				 * @param l_params 查询条件参数
				 * @param args	args.cancel赋值为true时取消后续加载表格数据操作
				 */
				self.onSearch = null;
				
				/**
				 * 重置回调事件。 在点击重置按钮时调用外部的onReset事件。
				 */
				self.onReset = null;
				
				/**
				 * 组件渲染完成事件
				 */
				self.onRendered = null;
				
				/**
				 * 通过url属性数据加载后回调事件
				 * @param val 返回数据
				 */
				self.onLoaded = null;
				
				/**
				 * @ignore
				 */
				self._isload = false;
				/**
				 * @ignore
				 */
				self._lazyload = false;
				var _localFields = null;
				var _node = null;
				var renderedEditorCount = 0;
				var _isCustomTemplateRender = false;
				var _isDomLoad = false;

				/**
				 * 初始化
				 * 
				 * @ignore
				 */
				self._init = function() {
					self.editors = {};
					
					self._editorWidth1(parseInt((self.editorWidth()).replace("px",""),10) + 38  + "px");
					self._editorWidth2(parseInt((self.editorWidth()).replace("px",""),10) + 52  + "px");

					self.dictsSub = cube.subscribe(self._dicts, self.setFiledDicts);
					_localFields = self.fields();
					self._mergeFields();
					
					if (cube.notEmpty(self.url()) || self.data() != null) {
						entityContainer.baseUrl = self.url();
						entityContainer.meta = _localFields;
						entityContainer.type = "form";
						entityContainer.timeout = self.timeout();
						entityContainer.loadMeta = false;
						entityContainer.onload = onload;
						entityContainer.onerror = onerror;
						entityContainer.data = self.data();
						entityContainer.dicts = self._dicts();
						entityContainer.loadByPost = self.loadByPost();
						entityContainer.init();
						self.load();
					}
				};
				
				// 数据加载回调函数
				function onload(args) {
					var value = {};
					if (cube.isEmpty(args.items)) {
						value = {};
					} else if (cube.isArray(args.items)) {
						value = args.items[0];
					} else {
						value = args.items;
					}
					
					if (cube.notEmpty(args.dicts) && cube.isArray(args.dicts) && args.dicts.length > 0) {
						self._dicts(args.dicts);
					}
					
					if (cube.notEmpty(value)) {
						var fields = self.fields();
						for (var i = 0; i < fields.length; i++) {
							var val = value[fields[i].name];
							if (cube.notEmpty(val)) {
								fields[i].value(val);
								_localFields[i].value = val;
							}
						}
						
					}
					self._isload(true);
					
					if (self.isCustomTemplate() == true && _isDomLoad == true && _isCustomTemplateRender == false) {
						self.onload(_node);
					}
					
					if (self.onLoaded != null && !cube.isObservable(self.onLoaded)) {
						self.onLoaded(value);
					}
				}
				
				// 数据加载失败回调函数
				function onerror(args) {
					self._isload(true);
				}
				
				/**
				 * 通过url从后台加载编辑器默认值，组件初始化时会自动调用，重新获取数据时可调用该方法。
				 */
				self.load = function() {
					entityContainer.load(self.id(), self.param());
				};

				/**
				 * 查询
				 */
				self.search = function() {
					
					if (self.isValid() && !self.validate()) {
						return;
					}
					
					if (self.searchParamType() == "json") {
						l_params = self.getJsonSearchParam();
					} else {
						l_params = self.getSearchParam();
					}

					// 发现sql注入，并且不允许sql关键字查询
					if (self.allowSqlKeyQuery() == false && self._isSqlInject) {
						return;
					}

					self._params = l_params;

					if (self.onSearch != null
							&& !cube.isObservable(self.onSearch)) {
						
						var pageIndex = 1;
						var grid = null;
						if (self.gridId() != null) {
							grid = cube.getPageViewModelByNode($("#" + self.gridId()));
						}
						
						//查询时将页码设置为1
						if (grid) {
							pageIndex = grid.pageIndex();
							grid._indexNotLoad(true);
							grid.pageIndex(1);
							grid._indexNotLoad(false);
						}
						
						var args = {cancel: false};
						var result = self.onSearch(l_params, args);
						if (args.cancel || result === false) {
							
							//如果取消查询操作，将页码设置为之前值
							if (grid) {
								grid._indexNotLoad(true);
								grid.pageIndex(pageIndex);
								grid._indexNotLoad(false);
							}
							
							return;
						}
					}

					self.loadGrid(true);
				};
				
				/**
				 * 重置
				 */
				self.reset = function() {
					self.isShowPopDialog(false);
					var fields = self.fields();
					for (var i = 0; i < fields.length; i++) {
						var val = null;
						if (!self.emptyDefaultValue()) {
							for (var j = 0; j < _localFields.length; j++) {
								if (_localFields[j].name == fields[i].name){
									if (_localFields[j].editorType == "DropDownEditor" && _localFields[j].isSelectedFirst && cube.notEmpty(_localFields[j].list[0].value)){
										val = cube.isObservable(_localFields[j].list[0].value)?_localFields[j].list[0].value():_localFields[j].list[0].value;
									} else if (_localFields[j].editorType == "DateTimeEditor" && _localFields[j].isShowValue){
										val = new Date().getTime();
									} else {
										val = cube.isObservable(_localFields[j].value)?_localFields[j].value():_localFields[j].value;										
									}
									break;
								}
							}
						}
						
						fields[i].value.silentUpdate(typeof(val)=="undefined"?"":val);
					}
					
					if (self.onReset != null && !cube.isObservable(self.onReset)) {
						self.onReset();
					}
				};

				/**
				 * 以 JSON 对象数组的形式返回查询参数。例如： [ //属性age的查询条件 { name: "age", //
				 * 属性名称 criterions: [ { fieldName: "age", value: 21, operator:
				 * ">"}, { fieldName: "age", value: 48, operator: "<"} ], //
				 * 查询条件子项集合 junction: "and", //查询条件子项的连接关系 columnJunction: "and"
				 * //属性age与相邻属性之间的连接关系 }, { name: "gender", criterions: [ {
				 * fieldName: "gender", value: "1", operator: "="} ], junction:
				 * "and", columnJunction: "and" } ]
				 */
				self.getJsonSearchParam = function() {
					self._isSqlInject = false;
					var params = [];
					var fields = self.fields();
					var sqlIn = [];
					for (var i = 0; i < fields.length; i++) {
						var name = fields[i].name;
						var value = fields[i].value();
						if (cube.notEmpty(value)) {
							var jsonValue = {};
							var criterions = [];
							if ((fields[i].editorType == "TextEditor" || fields[i].editorType == "NumberEditor")
									&& self.sqlCheck()) {
								var res = checkUtil.isSqlInjection(value);
								if (!res.successful) {
									if (sqlIn.indexOf(res.hint) == -1) {
										sqlIn.push(res.hint);
										continue;
									}
								}
							}
							var ele = {};
							ele.fieldName = name;
							ele.operator = "=";
							ele.value = value;
							criterions.push(ele);
							jsonValue.criterions = criterions;
							jsonValue.junction = "and";
							jsonValue.columnJunction = "and";
							params.push(jsonValue);
						}
					}

					if (!self.allowSqlKeyQuery() && sqlIn.length > 0) {
						self._isSqlInject = true;
						var sqls = sqlIn.join(",");
						cube.indicate("error", cube.msg("SQL_INJECTION", {
							sql : sqls
						}));
					}

					return params;
				};

				/**
				 * 获取查询参数，是由需要查询的参数拼成的字符串，如 key1=value1&key2=value2。
				 */
				self.getSearchParam = function() {
					self._isSqlInject = false;
					var params = "";
					var fields = self.fields();
					var sqlIn = [];
					for (var i = 0; i < fields.length; i++) {
						var name = fields[i].name;
						var value = fields[i].value();
						if (cube.notEmpty(value)) {
							if (fields[i].editorType == "TextEditor"
									|| fields[i].editorType == "NumberEditor") {
								if (self.sqlCheck()) {
									var res = checkUtil.isSqlInjection(value);
									if (!res.successful) {
										if (sqlIn.indexOf(res.hint) == -1) {
											sqlIn.push(res.hint);
											continue;
										}
									} else {
										params = params + name + "=" + _getValue(value) + "&";
									}
								} else {
									params = params + name + "=" + _getValue(value) + "&";
								}
							} else {
								params = params + name + "=" + _getValue(value) + "&";
							}
						}
					}

					if (!self.allowSqlKeyQuery() && sqlIn.length > 0) {
						self._isSqlInject = true;
						var sqls = sqlIn.join(",");
						cube.indicate("error", cube.msg("SQL_INJECTION", {
							sql : sqls
						}));
					}

					if (params.length > 0) {
						params = params.substring(0, params.length - 1);
					}

					return params;
				};

				/**
				 * @ignore
				 */
				var _isSetDicts = false;
				self.setDicts = function(dicts) {
					if(!_isSetDicts){
						self._dicts(dicts);
						_isSetDicts = true;
					}
				};
				
				/**
				 * @ignore
				 */
				self.setFiledDicts = function(dicts) {
					var s_fields = self.fields();
					$.each(s_fields, function(index, field) {
						if (cube.notEmpty(field)) {
							if (field.editorType == "DropDownEditor" || field.editorType == "CheckListEditor" || field.editorType == "ListEditor") {
								if (dicts && dicts[field.name] && (cube.isEmpty(field.autoDict) || field.autoDict == true)) {
									if (!cube.isObservable(field.list)) {
										field.list = dicts[field.name];
									} else {
										field.list(dicts[field.name]);
									}
								} else {
									if (field.list) {
										field.list = cube.array(field.list);
									} else if (!field.list) {
										field.list = cube.array([]);
									} else if (!cube.isObservable(field.list)) {
										field.list = cube.array(field.list);
									}
								}
							}
						}
					});
				};
				
				/**
				 * 加载对应表格
				 */
				self.loadGrid = function(isSearchLoad){
					if (self.gridId() != null) {
						var grid = cube.getPageViewModelByNode($("#" + self.gridId()));
						if (grid) {
							grid.load({
								pageIndex: 1,
								isSearchLoad: isSearchLoad
							});
						}
					}
				};

				/**
				 * @ignore
				 */
				self._mergeFields = function() {
					var p_fields = cube.clone(_localFields);
					var s_fields = self.fields();
					var dicts = self._dicts();
					$.each(p_fields, function(index, field) {
						if (cube.notEmpty(field)) {
							// 删除json对象中key为length的属性及值。
							for (i in field) {
								if (i == "length") {
									delete p_field[i];
								}
							}

							$.each(field, function(p_key, p_value) {
								if (cube.isEmpty(field[p_key]) || cube.isEqual(p_key, "readOnly") || cube.isEqual(p_key, "maxLength") || cube.isEqual(p_key, "nullable")) {
									field[p_key] = p_value;
								}
							});

							var sfield = null;
							if (s_fields) {
								sfield = s_fields[index];
							}
							setField(field, sfield, dicts);
						}
					});

					self.fields(p_fields);
				};

				function setField(field, sfield, dicts) {
					//由于时间编辑器有值时需要做格式化处理，所以需要延迟表格的加载
					if (field.editorType == "DateTimeEditor" && cube.notEmpty(field.value)) {
						self._lazyload(true);
					}
					
					if (cube.notEmpty(sfield.value)) {
						field.value = cube.obj(sfield.value);
					} else if (!field.value) {
						field.value = cube.obj("");
					} else if (!cube.isObservable(field.value)) {
						field.value = cube.obj(field.value);
					}
					
					if (field.editorType == "DropDownEditor" || field.editorType == "CheckListEditor" || field.editorType == "ListEditor") {
						if (dicts && dicts[field.name] && (cube.isEmpty(field.autoDict) || field.autoDict == true)) {
							if (!cube.isObservable(field.list)) {
								field.list = dicts[field.name];
							} else {
								field.list(dicts[field.name]);
							}
						} else {
							if (sfield.list) {
								field.list = cube.array(sfield.list);
							} else if (!field.list) {
								field.list = cube.array([]);
							} else if (!cube.isObservable(field.list)) {
								field.list = cube.array(field.list);
							}
						}
					}
					
					if (cube.isEmpty(field.nullable)) {
						field.nullable = cube.obj(true);
					} else if (!cube.isObservable(field.nullable)) {
						field.nullable = cube.obj(field.nullable);
					}
					
					if (typeof(field.validStatus) == "undefined") {
						field.validStatus = cube.obj("");
					} else if (!cube.isObservable(field.validStatus)) {
						field.validStatus = cube.obj(field.validStatus);
					}
					
					if (typeof(field.validHint) == "undefined") {
						field.validHint = cube.obj("");
					} else if (!cube.isObservable(field.validHint)) {
						field.validHint = cube.obj(field.validHint);
					}

					if (self.isCustomTemplate() == true) {
						self.editors[field.name] = field;
					}
					
					return field;
				}

				/**
				 * 根据 {@link encodeValue}属性设置是否编码输入值。
				 * 
				 * @param p_value
				 * @returns
				 * @ignore
				 */
				function _getValue(p_value) {
					if (self.encodeValue() == true) {
						return encodeURIComponent(p_value);
					} else {
						return p_value;
					}
				}
				
				/**
				 * 验证表单数据，验证通过返回true,否则返回false
				 */
				self.validate = function() {
					var fields = self.fields();
					for (var i = 0; i < fields.length; i++) {
						var result = self.valid(fields[i]);
						if (!result || (cube.isObservable(fields[i].validStatus) && fields[i].validStatus() != "")) {
							return false;
						}
					}
					return true;
				};
				
				/**
				 * 验证表单某一个field，验证结果将存在field的validStatus和validHint属性中，当验证通过时属性值为空否则为错误信息。
				 * 
				 * @param field
				 */
				self.valid = function(p_field) {
					var success = true;
					
					//隐藏的编辑器不再进行校验
					var visible = cube.isObservable(p_field.visible) ? p_field.visible() : p_field.visible;
					if (visible === false) {
						p_field.validStatus("");
						p_field.validHint("");
						self.isShowPopDialog(false);
						return success;
					}
					
					var value = p_field.value();
					if(p_field.editorType == "NumberEditor"){
						var result = validator.validate(value, "DIGIT", null);
						if (!result.successful) {
							value = "";
						}
					}
					
					var validateResult; 
					// 校验先走自定义校验.
					if (p_field.customValidate != null && cube.isFunction(p_field.customValidate) && !cube.isObservable(p_field.customValidate)) {
						validateResult = p_field.customValidate(value);
					}
					
					// 自定义校验通过再走校验.
					if (validateResult === false || ($.isPlainObject(validateResult) && validateResult.successful === false)) {
						if ($.isPlainObject(validateResult) && validateResult.hint) {
							success = false;
							p_field.validStatus("error");
							
							if (self.validHintType() == "pop") {
								self.isShowPopDialog(false);
								self.isShowPopDialog(true);
								cube.showPopDialog($(_node).find("#"+p_field.name), {
									content: validateResult.hint,
									popoverDirection: "bottom",
									msgType: "error",
									isShowDialog: self.isShowPopDialog
								});
							} else {
								p_field.validHint(validateResult.hint);
							}
						}
						return success;
					}
					
					var validType = p_field.validType;
					
					var nullable = cube.isObservable(p_field.nullable) ? p_field.nullable() : p_field.nullable;
					if (typeof(nullable) != "undefined" && nullable === false ) {
						if (validType == null) {
							validType = "NOTNULL";
						} else if (validType.indexOf("NOTNULL") == -1) {
							validType = validType + ", NOTNULL";
						}
					}
					
					if ((validType && validType.indexOf("NOTNULL") == -1 && cube.isEmpty(value))) {
						return success;
					}
					
					if (validType != null) {
						var result = validator.validate(value, validType, p_field.validOptions);
						if (!result.successful) {
							p_field.validStatus("error");
							success = false;
							
							if (self.validHintType() == "pop") {
								self.isShowPopDialog(false);
								self.isShowPopDialog(true);
								cube.showPopDialog($(_node).find("#"+p_field.name), {
									content: result.hint,
									popoverDirection: "bottom",
									msgType: "error",
									isShowDialog: self.isShowPopDialog
								});
							} else {
								p_field.validHint(result.hint);
							}
						} else {
							p_field.validStatus("");
							p_field.validHint("");
							self.isShowPopDialog(false);
						}
					} else {
						p_field.validStatus("");
						p_field.validHint("");
						self.isShowPopDialog(false);
					}
					
					return success;
				};
				
				/**
				 * @ignore
				 */
				self.customBtnClick = function(click, valid) {
					var result = true;
					if (self.isValid() && cube.notEmpty(valid) && valid == true) {
						result = self.validate();
					}
					
					if (cube.isFunction(click) && result) {
						click();
					}
				};
				
				/**
				 * @ignore
				 */
				self._getSpanNum = function(item, type) {
					if (type == 0) {//field
						if (item && typeof(item.spanNum) != 'undefined') {
							return 'span'+ item.spanNum;
						} else if (self.isDefaultLayout()) {
							if (item && typeof(item.colspan) != 'undefined') {
								if(self.colNum() == 5){
									return 'span50';
								} else {
									return 'span'+ item.colspan*Math.floor(12/self.colNum());
								}
							} else {
								if(self.colNum() == 5){
									return 'span50';
								} else {
									return 'span'+ Math.floor(12/self.colNum());
								}
							}
						} else {
							return 'fluid';
						}
					} else if (type == 1) {//标题
						if (self.isDefaultLayout()) {
							if (item && typeof(item.captionSpanNum) != 'undefined') {
								if(self.colNum() == 5){
									return 'span51';
								} else {
									return 'span'+ item.captionSpanNum;
								}
							} else {
								if(self.colNum() == 5){
									return 'span51';
								} else {
									return 'span' + self.captionSpanNum();
								}
							}
						}
					} else if (type == 2) {//编辑器
						if (self.isDefaultLayout()) {
							if (item && typeof(item.editorSpanNum) != 'undefined') {
								if(self.colNum() == 5){
									return 'span52';
								} else {
									return 'span'+ item.editorSpanNum;
								}
							} else {
								if(self.colNum() == 5){
									return 'span52';
								} else {
									return 'span' + self.editorSpanNum();
								}
							}
						} else {
							return 'fluid';
						}
					} else if (type == 3) {//按钮
						if (self.isDefaultLayout()) {
							var buttonAlign = self.buttonAlign();
							var fields = self.fields();
							if (buttonAlign == null || buttonAlign == "left") {
								if(self.colNum() == 5){
									return 'span50';
								} else {
									return 'span'+ Math.floor(12/self.colNum());
								}
							} else if(self.isLineFeed() == false && cube.isArray(fields)) {
								if(self.colNum() == 5){
									return 'span50';
								} else {
									return 'span'+ ((self.colNum() - fields.length % self.colNum()) *  Math.floor(12/self.colNum()));
								}
							}
						} else {
							return '';
						}
					} else if (type == 4) {//按钮左侧站位
						var buttonAlign = self.buttonAlign();
						if (buttonAlign == null || buttonAlign == "left") {
							if(self.colNum() == 5){
								return 'span51';
							} else {
								return 'span'+ self.captionSpanNum();
							}
						}
						
					} else if (type == 5) {//按钮位置
						var buttonAlign = self.buttonAlign();
						if (buttonAlign == null || buttonAlign == "left") {
							if(self.colNum() == 5){
								return 'span52';
							} else {
								return 'span'+ self.editorSpanNum();
							}
						}
					}
				};
				
				/**
				 * @ignore
				 */
				self.getEditorParam = function(p_data) {
					var param = cube.clone(p_data);
					
					param.validHintType = self.validHintType;
					param.isValid = self.isValid;
					param.isShowPopDialog = self.isShowPopDialog;
					param.onRendered = self.editorRendered;
					
					if (p_data.editorType == "DateTimeEditor") {
						param.width = typeof(p_data.width)!='undefined'?p_data.width: self.editorWidth;
					} else if (p_data.editorType == "DropDownTreeEditor"){
						param.width = typeof(p_data.width)!='undefined'?p_data.width: self.editorWidth;
						param.isExtDisplay = typeof(p_data.isExtDisplay)!='undefined'?p_data.isExtDisplay: true;
					} else if (p_data.editorType != "DateTimeEditor" && p_data.editorType != "FileEditor" && p_data.editorType != "RichEditor") {
						param.width = typeof(p_data.width)!='undefined'?p_data.width:(typeof(p_data.editorType)!='undefined' && (p_data.editorType!='DropDownEditor' || (p_data.editorType=='DropDownEditor' && typeof(p_data.isAllowEdit)!='undefined' && p_data.isAllowEdit == true)) && p_data.editorType!='TextEditor'?self.editorWidth:(typeof(p_data.editorType)!='undefined' && p_data.editorType =='DropDownEditor'?self._editorWidth2():self._editorWidth1()));
					}
					
					return param;
				};
				
				/**
				 * @ignore
				 */
				self.editorRendered = function(node, viewModel) {
					if (cube.notEmpty(viewModel) && cube.isFunction(viewModel.onRendered)) {
						viewModel.onRendered(node, viewModel);
					}
					
					renderedEditorCount++;
					
					if (renderedEditorCount == self.fields().length) {
						if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
							self.onRendered(_node, this);
						}
						
						if (self._lazyload() && cube.isEmpty(self.url())) {
							self._isload(true);
						}
					}
				};
				
				/**
				 * @ignore
				 */
				self.onload = function(node, viewModel) {
					_node = node;
					_isDomLoad = true;
					if (self.isCustomTemplate() == true && (cube.isEmpty(self.url()) || self._isload() == true)) {
						_isCustomTemplateRender = true;
						var $e = $(node);
						var options = $e.find("*[option]");
						var bindings = {};
						for (var i = 0; i < options.length; i++) {
							var $option = $(options[i]);
							cube.cleanNode($option);
							
							var opt = $option.attr("option").split(".");
							
							var editor = self.editors[opt[0]];
							var viewModel =  {	
								editors : self.editors
							};
							var _visible = "visible: typeof(editors['" + editor.name + "'].visible) != 'undefined' ? editors['" + editor.name + "'].visible : true";
							
							if (opt[1] == "editor") {
								var params = self.getEditorParam(editor);
								
								var componentName = "commoneditor";
								if (editor.editorType == "DateTimeEditor" || editor.editorType == "FileEditor" || editor.editorType == "RichEditor") {
									componentName = editor.editorType.toLowerCase();
								} 
								
								bindings = {
									component:{
										name: componentName,
										params: params
									}
								};
								
								$option.attr("data-bind", _visible + ", attr: {id:'"+editor.name+"', 'class' : 'form-inline ' + editors['"+editor.name+"'].validStatus()+' " + editor.name + "',title:editors['"+editor.name+"'].validHint()}");  
								cube.bindComponentByNode($option, bindings);
								
							} else if(opt[1] == "validHint"){
								$option.attr("data-bind", "text: editors['"+editor.name+"'].validHint()");  
							} else if (opt[1] == "visible") {
								$option.attr("data-bind", _visible);  
							} else {
								var _option = editor[opt[1]];
								$option.attr("data-bind", "text: '" + _option + "', " + _visible);
							}
							
							cube.bindTemlate($option, viewModel);
							if ($option.is("span")) {
								$option.addClass("editor-option");
							}
						}
					}
				};

				cube.endViewModel(self, params);
			}

			ViewModel.prototype.dispose = function() {
				this.isShowPopDialog(false);
				this.dictsSub.dispose();
			};

			return ViewModel;
		});