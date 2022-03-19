define(["entityContainer", "JSONUtil", "PrintUtil", "FileUploader", "Validator"], function(entityContainerClass, jsonUtil, printUtil, FileUploader, validator) {
	//依赖组件注入
	cube.importComponent("editor.commoneditor");
	cube.importComponent("editor.datetimeeditor");
	cube.importComponent("editor.dropdowneditor");
	cube.importComponent("editor.dropdowntreeeditor");
	cube.importComponent("editor.fileeditor");
	cube.importComponent("datacontainer.datagrid");
	cube.importComponent("controls.loading");
	
	/**
	 * 基本表单
	 * fields中name属性必须与后台一致,url后台请求地址。
	 * @example
	 * <code language="html">
	 *		<dataform id="form" params="style: style,
	 *						url: url,
	 *						fields: fields,
	 *						data: data,
	 *						isEditState: isEditState,
	 *						submit:submit"></dataform>
	 * </code>
	 * <code language="JavaScript">
	 *		define([], function() {
	 *			var PageViewModel = function(params) {
	 *				var self = this;
	 * 
	 *				self.style = 'border: 1px solid red;';
	 *				self.fields = [
	 *								{ name: "compName", caption: "构件名称", prefixText: "前缀名", suffixText: "名", editorType:"TextEditor",readOnly: false,nullable:false,validType:"UNIQUE",validOptions:{url:"/component/isUnique",validateMessage:"构件名称已存在"},validHint:"",validStatus:'error'},
	 *								{ name: "version", caption: "构件版本", readOnly: false, editorType:"TextEditor",nullable:false,validType:"REGEXP",validOptions:{expression:/^[0-9]+\.[0-9]+$/,validateMessage :"请输入X.X形式的版本号"}},
	 *								{ name: "compType", caption: "构件类型", editorType: "DropDownEditor", allowEditing: true,nullable:false,
	 *									list:[{text:"a", value:"a"},{text:"c", value:"c"},
	 *										{text:"b", value:"b"}]
	 *								},
	 *								{ name: "businessType", caption: "构件业务类型", editorType: "DropDownEditor", allowEditing: true,nullable:false,
	 *									list:[{text:"a", value:"a"},{text:"c", value:"c"},
	 *										{text:"b", value:"b"}]
	 *								},
	 *								{ name: "logo", caption: "构件logo", readOnly: false,nullable:false, editorType:"FileEditor",url:cube.datafile,maxCount:1,allowTypes:"jpg,jpeg,gif,png,bmp,tiff",maxFileSize:self.maxLogoSize,tableName:"components",primaryKey:"id",colName:"logo",type:"form",tips:self.logoTips},
	 *								{ name: "screenshot", caption: "构件运行截图", readOnly: false,nullable:false, editorType:"FileEditor",url:cube.datafile,isShowPreview:true,allowTypes:"jpg,jpeg,gif,png,bmp,tiff",maxFileSize:self.maxPrintSize,tableName:"components",primaryKey:"id",colName:"screenshot",type:"form",tips:self.printTips},
	 *								{ name: "software", caption: "构件附件", readOnly: false,nullable:false, editorType:"FileEditor",url:cube.datafile,isShowPreview:true,allowTypes:self.fileType,maxFileSize:self.maxFileSize,tableName:"components",primaryKey:"id",colName:"software",type:"form",tips:self.fileTips,onItemUploading:function(args){
	 *									var result = self.checkFileMd5(args.item.file.md5);
	 *									if(!result){
	 *										cube.indicate("warning","该文件已存在，请重新选择新的构件附件上传，谢谢！");
	 *										args.cancel = true;
	 *									}
	 *								}},
	 *								{ name: "description", caption: "构件简介", readOnly: false,nullable:false, editorType:"TextEditor",textMode:'multiline'},
	 *								{ name: "versionDesc", caption: "版本介绍", readOnly: false,nullable:false, editorType:"TextEditor",textMode:'multiline'}
	 *					
	 *								];
	 *				self.data = { compName:"名称",version:"版本",compType:"a",businessType:"b", logo:'', screenshot:'',software:"",description:'介绍',versionDesc:'版本简介'}
	 *				self.isEditState = false;
	 *				self.url = '';
	 *				self.submit = function(p_value){
	 *					alert(p_value);
	 *				}
	 *
	 *				cube.endViewModel(self, params);
	 *			};
	 *			return PageViewModel;
	 *		});
	 * </code>
	 */
	function ViewModel(params) {
		var self = this;

		var entityContainer = new entityContainerClass();

		/**
		 * 后台请求地址
		 * 
		 * @default 空
		 */
		self.url = "";
		
		/**
		 * 获取或设置一个 JSON 对象，表示数据容器中各种操作的 path 集合。该字段的字段名表示操作类型，值表示操作路径。
		 * 用户可以根据需要改变改变相应的操作路径。
		 * <p>
		 * 该字段的字段名包括：
		 * <ul>
		 * <li><b>meta</b></li>
		 * <li><b>data</b></li>
		 * <li><b>save</b></li>
		 * <li><b>remove</b></li>
		 * </ul>
		 * 当actions中设置某一个属性则会使用actions属性的值作为本次请求的操作路径
		 * 即将actions属性的值作为请求的基准路径。如：actions:{remove:"~/casetest/rest/user/"},
		 * 则在remove时会使用~/casetest/rest/user/作为请求的路径，不再使用url属性。
		 * </p>
		 */
		self.actions = {};
		
		/**
		 * 获取或设置一个数字，表示请求的默认超时时间，以毫秒为单位，默认使用cube.timeout设置的时间。
		 * 
		 * @default null
		 */
		self.timeout = null;
		
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
		 * 加载数据时是否使用post方式提交参数，true：post方式 false：get方式
		 * 
		 * @default false
		 */
		self.loadByPost = false;
		
		/**
		 * 主键
		 * @default
		 *		id
		 */
		self.primaryKey = "id";
		
		/**
		 * 是否加载元数据
		 * @default
		 *		true
		 */
		self.loadMeta = true;
		
		/**
		 * 是否显示主键字段
		 * @default
		 *		false
		 */
		self.displayPrimaryKey = false;
		
		/**
		 * 是否显示提交按钮
		 * @default
		 *		true
		 */
		self.displaySubmitButton = true;
		
		/**
		 * 是否显示保存按钮
		 * @default
		 *		false
		 */
		self.displaySaveButton = false;
		
		/**
		 * 是否显示重置按钮
		 * @default
		 *		false
		 */
		self.displayResetButton = false;
		
		/**
		 * 保存、重置按钮的位置，可选：main、center、right
		 * @default
		 *		main
		 */
		self.buttonAlign = null;
		
		/** 
		 * 一个字符串，表示表单数据的唯一标识。
		 * @default null
		 */
		self.id = null;
		
		/**
		 * 一个 JSON 对象，表示查询参数。
		 * @default null
		 */
		self.param = null;
		
		/**
		 * 表单一行显示几列,可选0-4，当为0时自适应宽度，同时可通过fields属性中对某个编辑器设置colspan属性跨列。
		 *  @default 1
		 */
		self.cols = 1;

		/**
		 * 表格样式
		 * 
		 * @default 空
		 */
		self.style = "";

		/**
		 * 表单字段格式 
		 * @example
		 * <code language="JavaScript">
		 * [{ 
		 *		name: "id", 
		 *		caption: "标识", 
		 *		editorType:"TextEditor",
		 *		readOnly: true, 
		 *		value:"a1",
		 *		tips:"必填"
		 * }, 
		 *	{ 
		 *		name: "name", 
		 *		caption: "名称",
		 *		editorType:"TextEditor",
		 *		readOnly: false, 
		 *		nullable: true,
		 *		value:"小明",
		 *		tips:"*",
		 *		renderEditor: function(value) {
		 *		return "<a>"+value+"</a>";
		 *	},
		 *	onClick: function(value) {
		 *		//todo
		 *	}
		 *  }]
		 * </code>
		 * 
		 * @example
		 * 表单分组
		 * <code language="JavaScript">
		 * [
		 *	[
		 *  //分组信息必须设置isGroupInfo属性为true，且为数组第一个元素，可通过expanded设置默认是否展开。
		 *	{ caption: "基本信息", icon: "user", isGroupInfo: true},
		 *	{ 
		 *		name: "id", 
		 *		caption: "标识", 
		 *		editorType:"TextEditor",
		 *		readOnly: true, 
		 *		value:"a1",
		 *		tips:"必填"
		 *	}, 
		 *	{
		 *		name: "name", 
		 *		caption: "名称",
		 *		editorType:"TextEditor",
		 *		readOnly: false, 
		 *		nullable: true,
		 *		value:"小明",
		 *		tips:"*"
		 *	}
		 *	],
		 *	[
		 *  { caption: "其他信息", icon: "plus", isGroupInfo: true},//分组信息必须设置isGroupInfo属性为true，且为数组第一个元素
		 *  { 
		 *		name: "sex", 
		 *		caption: "性别", 
		 *		editorType:"DropDownEditor",
		 *		tips:"必填"
		 *	}, 
		 *	{ 
		 *		name: "address", 
		 *		caption: "地址",
		 *		editorType:"TextEditor",
		 *		readOnly: false, 
		 *		nullable: true,
		 *		tips:"*"
		 *}
		 *	]
		 *]
		 * </code>
		 * 
		 * 可使用width设置编辑器宽度。
		 * 可使用captionWidth设置caption宽度。
		 * 
		 * 可使用renderEditor自定义编辑器渲染内容，该方法需返回一个html字符串，同时可配合onClick点击事件。
		 * 可通过在适当位置放置 { caption: "基本信息", icon: "plus", isLine: true} 显示分割线。
		 * editorType编辑器类型可选：
		 * <ul>
		 * <li><b>TextEditor</b>：API查看editor/commoneditor</li>
		 * <li><b>LabelEditor</b>：API查看editor/commoneditor</li>
		 * <li><b>CheckEditor</b>：API查看editor/commoneditor</li>
		 * <li><b>CheckListEditor</b>：API查看editor/commoneditor</li>
		 * <li><b>ListEditor</b>：API查看editor/commoneditor</li>
		 * <li><b>NumberEditor</b>：API查看editor/commoneditor</li>
		 * <li><b>CustomEditor</b>：API查看editor/commoneditor</li>
		 * <li><b>DropDownEditor</b>：API查看editor/dropdowneditor</li>
		 * <li><b>DropDownTreeEditor</b>：API查看editor/dropdowntreeeditor</li>
		 * <li><b>DateTimeEditor</b>：API查看editor/datetimeeditor</li>
		 * <li><b>FileEditor</b>：API查看editor/fileeditor</li>
		 * <li><b>RichEditor</b>：API查看html5/richeditor</li>
		 * <li><b>GridEditor</b>：API查看datacontainer/datagrid</li>
		 * </ul>
		 * </p>
		 * 
		 * @default
		 *		[]
		 */
		self.fields = [];
		
		/**
		 * 表单字段编辑器，以key-value形式存储，通过编辑器name获取编辑器信息。
		 *  @example
		 * <code language="JavaScript">
		 *		var vm = cube.getPageViewModelByNode($("#form"));
		 *		var nameEditor = vm.editors["name"];
		 *	
		 *  </code>
		 * @default null
		 */
		self.editors = null;
		
		/**
		 * 本地表单字段值，当设置url属性时，优先通过url从后台读取数据。
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
		 * 数据字典，此属性为表单编辑器数据字典整体设置或者在编辑器中使用list属性设置，如果后台返回数据字典的话将会覆盖该设置，格式如下：
		 * @example
		 *  <code language="JavaScript">
		 * [{"name":"sex","values":[{"text":"男","value":"1"},{"text":"女","value":"0"}]}]
		 * </code>
		 */
		self.dicts = null;

		/**
		 * 是否在编辑状态。
		 * 
		 * @default true。
		 */
		self.isEditState = true;
		
		/**
		 * @ignore
		 */
		self.isShowPopDialog = false;
		
		/**
		 * 验证消息提示方式，可选：inline、pop
		 * @default inline
		 */
		self.validHintType = "inline";
		
		/**
		 * 标题和编辑器之间的分隔符，各个编辑器可使用showSeparator设置是否显示
		 * 
		 *  @default 空
		 */
		self.separator = "";
		
		/**
		 * 是否使用自定义模板
		 * 自定义模板放入dataform标签内。使用“编辑器名称.属性名称”显示fields设置的内容。
		 * @example
		 * <code language="html">
		 *		<link rel="stylesheet" type="text/css" href="./resources/css/CommonStyle_Skin.css"/>
		 *		<cubebutton params="text:'保存',onClick:save"></cubebutton>
		 *		<dataform id="form" params="
		 *						url: url,
		 *						fields: fields
		 *						isCustomTemplate:true ">
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
		 *		</dataform>
		 *</code>
		 *<code language="JavaScript">
		 *		define([], function() {
		 *			var PageViewModel = function(params) {
		 *				var self = this;
		 *
		 *				self.url = '';
		 *				self.fields = [
		 *						{	name: "mingcheng", caption: "名称", editorType: "TextEditor", nullable:false},
		 *						{	name: "type", caption: "类型", editorType: "DropDownEditor",list:[{value:'1',text:'类型1'},{value:'2',text:'类型2'}],},
		 *						{	name: "zhongda", caption: "重大", editorType: "CheckListEditor", list:[{value:'1',text:'是'},{value:'2',text:'否'}],type:"radio"},
		 *					
		 *					];
		 *				
		 *				self.save = function(){
		 *					cube.getPageViewModelByNode($("#form")).submitForm();
		 *				}
		 * 
		 *				cube.endViewModel(self, params);
		 *			};
		 *			return PageViewModel;
		 *		});
		 *</code>
		 *
		 *<code language="JavaScript">
		 *注：例fields中一项的name为“mingcheng”，则<br/>
		 *	显示该项caption：<span option="mingcheng.caption"><br/>
		 *	显示该项编辑器：<span option="mingcheng.editor"><br/>
		 *	显示该项验证提示信息：<span option="mingcheng.validHint"><br/>
		 *	可使用<div option="mingcheng.visible"></div>设置隐藏显示内容<br/>
		 *	如果嵌套其他组件，组件属性设置ViewModel中变量使用$parent.变量名<br/>
		 *</code>
		 *
		 * @default false
		 */
		self.isCustomTemplate = false;
		
		/**
		 * 一个Boolean值，表示是否提交表单所有数据，默认false，只提交修改过的值
		 * 
		 * @default false
		 */
		self.isSaveAll = false;
		
		/**
		 * 自定义按钮
		 * [{
		 *		text : "收藏",
		 *		type : "success",//type属性取值为default、primary、success、info、warning、danger、inverse、link
		 *		visible: true, //不设置时默认为true
		 *		valid: true,   //点击按钮时是否对编辑器进行校验
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
		 *	加载效果的显示状态，true为显示，false为不显示
		 *	@default false
		 */
		self.isShowLoading = false;
		
		/**
		 * 当表单保存完成之后是否更新附件表关联的业务表主键值。
		 * @default true
		 */
		self.isRefreshPk = true;

		/**
		 * 提交表单事件。 在点击提交按钮时且表单验证通过之后调用外部的submit事件。
		 * 回调函数接收参数为一个对象，例：
		 * function(args){
		 *		//args.data为表单数据
		 *		//args.cancel赋值为true是取消表单保存
		 * }
		 */
		self.onSubmit = null;
		
		/**
		 * 提交保存事件。表单保存后触发该事件。
		 * 接收参数为一个对象：表单保存后的数据值
		 */
		self.onSaved = null;
		
		/**
		 * 表单加载事件。表单数据加载后触发该事件。
		 * 接收参数为一个对象：表单加载后的数据值
		 */
		self.onLoad = null;
		
		/**
		 * 表单数据加载失败事件
		 * @params args 错误信息
		 */
		self.onLoadError = null;
		
		/**
		 * 表单渲染完成事件
		 * @param node
		 */
		self.onRendered = null;
		
		var _localFields = null;
		var _localdata = null;
		var _isDomLoad = false;
		var _node = null;
		var renderedEditorCount = 0;

		/**
		 * 初始化
		 * @ignore
		 */
		self._init = function() {
			self.editors = {};
			
			var _data = self.data()||{};
			
			$.each(self.fields(), function(index, p_field) {
				if (cube.isArray(p_field)) {
					for (var i = 0; i < p_field.length; i++) {
						self._initField(p_field[i], _data);
					}
				} else if(!cube.isString(p_field) && cube.isEmpty(p_field.isGroupInfo) && cube.isEmpty(p_field.isLine)) {
					self._initField(p_field, _data);
				}
			});
			
			self.data(_data);
			
			_localFields = cube.clone(self.fields());
			_localdata = cube.clone(_data);
			
			entityContainer.primaryKey = self.primaryKey();
			entityContainer.baseUrl = self.url();
			entityContainer.actions = self.actions();
			entityContainer.meta = _localFields;
			entityContainer.type = "form";
			entityContainer.loadMeta = self.loadMeta();
			entityContainer.timeout = self.timeout();
			entityContainer.onload = onload;
			entityContainer.onsaved = onsaved;
			entityContainer.onerror = _error;
			entityContainer.data = self.data();
			entityContainer.dicts = self.dicts();
			entityContainer.customHeaders = self.customHeaders();
			entityContainer.loadByPost = self.loadByPost();
			entityContainer.init();
			
			self.actionsSub = cube.subscribe(self.actions, function(p_actions) {
				entityContainer.actions = p_actions;
			});
			
			self.idSub = cube.subscribe(self.id, function(){
				self.load(self.id(), self.param());
			});
			
			
			if (cube.isEmpty(self.url())) {
				self.dataSub = cube.subscribe(self.data, function(p_data){
					entityContainer.data = p_data;
					self.load(self.id(), self.param());
				});
			}
			
			self.load(self.id(), self.param());
		};
		
		/**
		 * @ignore
		 */
		self._initField = function(p_field, _data) {
			if (cube.notEmpty(p_field.isLine)) {
				return;
			} else if (cube.notEmpty(p_field.isGroupInfo)) {
				if (typeof(p_field.expanded) == "undefined") {
					p_field.expanded = cube.obj(true);
				} else if (!cube.isObservable(p_field.expanded)) {
					p_field.expanded = cube.obj(p_field.expanded);
				}
				
				return;
			}
			
			if (typeof(p_field.validStatus) == "undefined") {
				p_field.validStatus = cube.obj("");
			} else if (!cube.isObservable(p_field.validStatus)) {
				p_field.validStatus = cube.obj(p_field.validStatus);
			}
			
			if (typeof(p_field.validHint) == "undefined") {
				p_field.validHint = cube.obj("");
			} else if (!cube.isObservable(p_field.validHint)) {
				p_field.validHint = cube.obj(p_field.validHint);
			}
			
			if (p_field.name == self.primaryKey()) {
				p_field.visible = self.displayPrimaryKey();
			}
			
			if (!p_field.editorType) {
				p_field.editorType = "TextEditor";
			}
				
			if (p_field.editorType == "DropDownEditor" || p_field.editorType == "CheckListEditor" || p_field.editorType == "ListEditor" || p_field.editorType == "LabelEditor") {
				if (!p_field.list) {
					p_field.list = cube.array([]);
				} else if(!cube.isObservable(p_field.list)) {
					p_field.list = cube.array(p_field.list);
				}
			} else if (p_field.editorType == "FileEditor") {
				if (!p_field.pkVal) {
					p_field.pkVal = cube.obj(null);
				} else if(!cube.isObservable(p_field.pkVal)) {
					p_field.pkVal = cube.obj(p_field.pkVal);
				}
			}
			
			if (cube.isEmpty(p_field.nullable)) {
				p_field.nullable = cube.obj(true);
			} else if (!cube.isObservable(p_field.nullable)) {
				p_field.nullable = cube.obj(p_field.nullable);
			}
			
			
			if (typeof(p_field.value) != "undefined" && typeof(_data[p_field.name]) == "undefined") {
				_data[p_field.name] = p_field.value;
			}
			
			self.editors[p_field.name] = p_field;
		};

		/**
		 * 提交表单
		 * 
		 * @param p_saveAll 一个Boolean值，表示是否提交表单所有数据，默认false，只提交修改过的值
		 */
		self.submitForm = function(p_saveAll) {
			if (!(arguments.length == 1 && cube.isBoolean(p_saveAll))) {
				p_saveAll = self.isSaveAll();
			}
				
			if (self.validate()) {
				if (self.onSubmit != null && !cube.isObservable(self.onSubmit)) {
					var args = {cancel: false, data : cube.isObservable(self.data) ? self.data() : self.data};
					self.onSubmit(args);
					if (args.cancel) {
						return false;
					}
				}
				
				entityContainer.save(p_saveAll);
			} else {
				return false;
			}
			
			return true;
		};
		
		/**
		 * 重置表单
		 */
		self.reset = function() {
			entityContainer.data = cube.clone(_localdata);
			entityContainer.clearChanges();
			
			self.load(self.id(), self.param());

			if (self.onReset != null && !cube.isObservable(self.onReset)) {
				self.onReset();
			}
		};
		
		/**
		 * 提交表单内FileEditor文件上传
		 */
		self.submitFiles = function() {
			var fields = self.fields();
			var editor = null;
			for (var i = 0; i <fields.length; i++) {
				if (cube.isArray(fields[i])) {
					for (var j = 0; j < fields[i].length; j++) {
						if (fields[i][j].editorType == "FileEditor") {
							editor = cube.getPageViewModelByNode($(_node).find("#"+fields[i][j].name));
							if (editor && editor.uploadFiles) {
								editor.uploadFiles();
							}
						}
					}
				} else if(!cube.isString(fields[i]) && cube.isEmpty(fields[i].isGroupInfo) && cube.isEmpty(fields[i].isLine)) {
					if (fields[i].editorType == "FileEditor") {
						editor = cube.getPageViewModelByNode($(_node).find("#"+fields[i].name));
						if (editor && editor.uploadFiles) {
							editor.uploadFiles();
						}
					}
				}
			}
		};

		/**
		 * 设置actions
		 * @param 一个对象，例：{remove:"~/casetest/rest/user/"}
		 */
		self.setActions = function(p_actions) {
			self.actions(cube.isObservable(p_actions) ? p_actions() : p_actions);
			entityContainer.actions = p_actions;
		};
		
		/**
		 * 加载数据
		 * @param
		 *		p_id   主键ID
		 *		p_data 参数数据
		 */
		self.load = function(p_id, p_data) {
			self.isShowLoading(true);
			
			var id = null;
			var data = null;
			if (cube.isEmpty(p_id) || cube.isString(p_id) || cube.isNumber(p_id)) {
				id = p_id;
				if (cube.isEmpty(p_data) || $.isPlainObject(p_data)) {
					data = p_data;
				}
			} else if ($.isPlainObject(p_id)) {
				data = p_id;
			}

			var filterColumns = _getQueryColumnsStr(self.fields());

			if (cube.notEmpty(data)) {
				if (cube.isEmpty(data.dataParams) && cube.isEmpty(data.metaParams)) {
					var data2 = {};
					data2.dataParams = data;
					data = data2;
					data2 = null;
				}
			}

			if (cube.notEmpty(filterColumns) && cube.isString(filterColumns)) {
				if (cube.isEmpty(data)) {
					data = {
						metaParams : {
							columns : filterColumns
						},
						dataParams : {
							columns : filterColumns
						}
					};
				} else {
					_setFilterColumns(data.metaParams, filterColumns, data, "metaParams");
					_setFilterColumns(data.dataParams, filterColumns, data, "dataParams");
				}
			}

			entityContainer.load(id, data);
		};

		/**
		 * 打印表单内容
		 */
		self.print = function() {
			printUtil.print(self.data, self.fields, self.dicts, "dataform");
		};
		
		// 数据加载回调函数
		function onload(args) {
			var fields = args.columns;
			var value = {};
			if (cube.isEmpty(args.items)) {
				value = {};
			} else if (cube.isArray(args.items)) {
				value = args.items[0];
			} else {
				value = args.items;
			}
			
			if (cube.isEmpty(value)){
				value = {};
			}
			
			renderedEditorCount = 0;
			self.fields([]);
			self.dicts(args.dicts);
			_fieldCells = [];
			
			_mergeFields(fields, value, args.dicts);
			
			if (self.isCustomTemplate() == true && _isDomLoad == true) {
				self.onload(_node);
			}
			
			self.isShowLoading(false);
			
			if (self.onLoad != null && !cube.isObservable(self.onLoad)) {
					self.onLoad(self.data);
			}
		}
		
		function _error(args) {
			self.isShowLoading(false);
			
			if (self.onLoadError != null && !cube.isObservable(self.onLoadError)) {
				self.onLoadError(args);
			}
		}

		function _mergeFields(p_fields, p_value, dicts) {
			var fields = cube.clone(_localFields);
				
			if (cube.isEmpty(p_fields) || p_fields.length <= 0) {
				var primaryField = jsonUtil.getItem(fields, "name", entityContainer.primaryKey, 3);
				if (cube.notEmpty(primaryField)) {
					primaryField.readOnly = true;
					primaryField.nullable = cube.obj(false);
					primaryField.visible = self.displayPrimaryKey();
				}
			} else {
				$.each(p_fields, function(index, p_field) {
					if (cube.isArray(p_field)) {
						for (var i = 0; i < p_field.length; i++) {
							if (cube.notEmpty(p_field[i].isGroupInfo) || cube.notEmpty(p_field[i].isLine)) {
								continue;
							}
							
							setFieldProp(p_field[i], fields, dicts, p_value);
						}
					} else if (cube.isEmpty(p_field.isGroupInfo) && cube.isEmpty(p_field.isLine)) {
						setFieldProp(p_field, fields, dicts, p_value);
					}
				});
			}
			
			self.data = p_value!=null?p_value:{};
			self.fields(fields);
			_localdata = cube.clone(self.data);
		}
		
		function setFieldProp(p_field, fields, dicts, p_value) {
			var field = _getLocalField(p_field.name, fields);
			if (cube.notEmpty(field)) {
				// 删除json对象中key为length的属性及值。
				for (i in p_field) {
					if (i == "length") {
						delete p_field[i];
					}
				}

				$.each(p_field, function(p_key, p_value) {
					if (cube.isEmpty(field[p_key]) || cube.isEqual(p_key, "readOnly") || cube.isEqual(p_key, "maxLength") || cube.isEqual(p_key, "nullable")) {
						field[p_key] = p_value;
					}
				});
				setField(field, dicts, p_value);
			} else {
				field = p_field;
				field.validStatus = cube.obj("");
				field.validHint = cube.obj("");
				setField(field, dicts, p_value);
				fields.push(field);
			}
		}
		
		function setField(field, dicts, p_value) {
			if (cube.isEqual(field.name, entityContainer.primaryKey)) {
				field.readOnly = true;
				field.nullable = cube.obj(false);
				field.visible = self.displayPrimaryKey();
			} else if (typeof(field.value) != "undefined" && typeof(p_value[field.name]) == "undefined") {
				entityContainer.setValue(p_value, field.name, cube.isObservable(field.value)?field.value():field.value, true);
			}
			
			if (field.editorType == "DropDownEditor" || field.editorType == "CheckListEditor" || field.editorType == "ListEditor" || field.editorType == "LabelEditor") {
				if (dicts && dicts[field.name]) {
					if (!cube.isObservable(field.list)) {
						field.list = dicts[field.name];
					} else {
						field.list(dicts[field.name]);
					}
				}
			} else if (field.editorType == "FileEditor") {
				if (field.pkVal && cube.isObservable(field.pkVal)) {
					if (cube.isEmpty(field.pkVal())) {
						field.pkVal(entityContainer.data[entityContainer.primaryKey]);
					}
				} else {
					if (cube.isEmpty(field.pkVal)) {
						field.pkVal = cube.obj(entityContainer.data[entityContainer.primaryKey]);
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
			} else {
				field.validStatus("");
			}
			
			if (cube.notEmpty(field.visible) && !cube.isObservable(field.visible)) {
				field.visible = cube.obj(field.visible);
			}
			
			if (cube.notEmpty(field.readOnly) && !cube.isObservable(field.readOnly)) {
				field.readOnly = cube.obj(field.readOnly);
			}
			
			self.editors[field.name] = field;
			
			return field;
		}
		

		// 获取本地定义的表单项
		function _getLocalField(p_fieldName, p_fields) {
			var field = null;
			var localFields = _localFields;
			if (typeof(p_fields) != null) {
				localFields = p_fields;
			}
			
			if (cube.notEmpty(p_fieldName) && cube.isArray(localFields) && localFields.length>0) {
					field = jsonUtil.getItem(localFields, "name", p_fieldName);
				if (cube.isEmpty(field)) {
					field = jsonUtil.getItem(localFields, "name", p_fieldName, 3);
				}
			}
			return field;
		}
		
		function _getQueryColumnsStr(p_fields) {
			var queryColumns = _getInitColumns(p_fields);
			if (queryColumns.length > 0) {
				return queryColumns.join(",");
			}

			return null;
		}

		function _setFilterColumns(p_params, p_filterColumns, p_data, p_type) {
			if (cube.isEmpty(p_params)) {
				p_data[p_type] = {
					columns : p_filterColumns
				};
			} else {
				if (cube.isEmpty(p_params.columns)) {
					p_params.columns = p_filterColumns;
				} else {
					if (cube.isString(p_params.columns)) {
						var filterCols = p_filterColumns.split(",");
						var cols = p_params.columns.split(",");

						var length = filterCols.length;
						for (var i = 0; i < length; i++) {
							if (cols.indexOf(filterCols[i]) == -1) {
								cols.push(filterCols[i]);
							}
						}
						p_params.columns = cols.join(",");

						filterCols.length = 0;
						cols.length = 0;
						filterCols = null;
						cols = null;
					} else {
						self.showError("ERR_UNSUPPORTED_FORMAT", [p_params.columns]);
					}
				}
			}
		}

		var _initColumns = null;
		function _getInitColumns(p_fields) {
			if (_initColumns == null) {
				_initColumns = _getColumnsFromFields(p_fields);
			}

			return _initColumns;
		}

		function _getColumnsFromFields(p_fields) {
			var columns = [];
			if (cube.notEmpty(p_fields)) {
				for (var i = 0; i < p_fields.length; i++) {
					var field = p_fields[i];
					if (cube.isArray(field)) {
						for (var j = 0; j < field.length; j++) {
							if ($.isPlainObject(field[j])) {
								columns.push(field[j].name);
							}
						}
					} else if ($.isPlainObject(field)) {
						columns.push(field.name);
					}
				}
			}
			return columns;
		}
		
		/**
		* 验证表单数据，验证通过返回true,否则返回false
		*/
		self.validate = function() {
			var fields = self.fields();
			var visible = null;
			var field = null;
			for (var i = 0; i < fields.length; i++) {
				if (cube.isArray(fields[i])) {
					for (var j = 0; j < fields[i].length; j++) {
						if (cube.notEmpty(fields[i][j].isGroupInfo) || cube.notEmpty(fields[i][j].isLine)) {
							continue;
						}
						
						visible = cube.isObservable(fields[i][j].visible) ? fields[i][j].visible() : fields[i][j].visible;
						if (cube.isEmpty(visible) || visible == true) {
							var field = self.valid(fields[i][j]);
							if (field.validStatus() != "") {
								return false;
							}
						}
					}
				} else if(!cube.isString(fields[i]) && cube.isEmpty(fields[i].isGroupInfo) && cube.isEmpty(fields[i].isLine)) {
					visible = cube.isObservable(fields[i].visible) ? fields[i].visible() : fields[i].visible;
					if (cube.isEmpty(visible) || visible == true) {
						field = self.valid(fields[i]);
						if (field.validStatus() != "") {
							return false;
						}
					}
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
			//隐藏的编辑器不再进行校验
			var visible = cube.isObservable(p_field.visible) ? p_field.visible() : p_field.visible;
			if (visible === false) {
				p_field.validStatus("");
				p_field.validHint("");
				self.isShowPopDialog(false);
				return p_field;
			}
			
			if (cube.notEmpty(p_field.validStatus())) {
				return p_field;
			}
			
			var value = entityContainer.data[p_field.name];
			if (cube.isObservable(value)) {
				value = value();
			}
			
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
			
			var validHintType = self.validHintType();
			if (cube.notEmpty(p_field.validHintType)) {
				if(cube.isObservable(p_field.validHintType)) {
					validHintType = p_field.validHintType();
				} else {
					validHintType = p_field.validHintType;
				}
			}
			
			// 自定义校验通过再走校验.
			if (validateResult === false || ($.isPlainObject(validateResult) && validateResult.successful === false)) {
				p_field.validStatus("error");
				if ($.isPlainObject(validateResult) && validateResult.hint) {
					if (validHintType == "pop") {
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
				return p_field;
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
			
			if ((cube.isEmpty(validType) || validType.indexOf("NOTNULL") == -1) && cube.isEmpty(value)) {
				return p_field;
			}
			
			if (validType != null) {
				var result = validator.validate(value, validType, p_field.validOptions);
				if (!result.successful) {
					p_field.validStatus("error");
					if (validHintType == "pop") {
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
			}
			
			return p_field;
		};
		
		function onsaved(args) {
			// 重置新增数据的主键值
			if (self.isRefreshPk()) {
				var fields = self.fields();
				for (var i = 0; i <fields.length; i++) {
					if (cube.isArray(fields[i])) {
						for (var j = 0; j < fields[i].length; j++) {
							if (fields[i][j].editorType == "FileEditor") {
								// 编辑器提供刷新主键的方法接口。
								if (cube.isArray(args.result) && args.result.length > 0) {
									//指定目录上传不需要刷新主键值
									if (fields[i][i].type != "path") {
										refreshPk(args.result, entityContainer.primaryKey, fields[i][j]);
									}
								}
							}
						}
					} else if(!cube.isString(fields[i]) && cube.isEmpty(fields[i].isGroupInfo) && cube.isEmpty(fields[i].isLine)) {
						if (fields[i].editorType == "FileEditor") {
							// 编辑器提供刷新主键的方法接口。
							if (cube.isArray(args.result) && args.result.length > 0) {
								//指定目录上传不需要刷新主键值
								if (fields[i].type != "path") {
									refreshPk(args.result, entityContainer.primaryKey, fields[i]);
								}
							}
						}
					}
				}
			}
			
			if (self.onSaved != null && !cube.isObservable(self.onSaved)) {
				self.onSaved(args);
			}
		}
		
		function refreshPk(p_items, p_pkColName, params) {
			var fileUploader = new  FileUploader();
			fileUploader.url = params.url;
			fileUploader.refreshPk(p_items, p_pkColName, params);
		}

		/**
		 * @ignore
		 */
		self._editor_changed = function(p_name, p_value) {
			if (entityContainer != null && cube.notEmpty(entityContainer.data[self.primaryKey()])) {
				entityContainer.setValue(entityContainer.data, p_name, p_value, true);
			}
			
			var field = jsonUtil.getItem(self.fields(), "name", p_name, 3);
			if (cube.notEmpty(field) && typeof(field.onChanged) == "function") {
				field.onChanged(p_value);
			}
		};
		
		/**
		 * @ignore
		 * 用于文件上传编辑器
		 */
		self._editor_upload = function(p_name, p_fileNames) {
			if (entityContainer != null && cube.notEmpty(entityContainer.data[self.primaryKey()])) {
				entityContainer.setValue(entityContainer.data, p_name, p_fileNames, true);
				if (self.editors != null && !(cube.isEmpty(entityContainer.data[p_name]) && cube.isEmpty(p_fileNames))) {
					var editor = self.editors[p_name];
					if (editor) {
						editor.validStatus("");
						self.valid(self.editors[p_name]);
					}
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.customBtnClick = function(click, valid) {
			var result = true;
			if (cube.notEmpty(valid) && valid == true) {
				result = self.validate();
			}
			
			if (cube.isFunction(click) && result) {
				click();
			}
		};
		
		/**
		 * @ignore
		 */
		self.expandGroup = function(viewModel, event){
			viewModel[0].expanded(!viewModel[0].expanded());
		};
		
		/**
		 * @ignore
		 */
		self.getEditorParam = function(p_data) {
			var param = cube.clone(p_data);
			var data = cube.isObservable(self.data) ? self.data() : self.data;
			if (cube.isObservable(param.value)) {
				var value = data[p_data.name];
				param.value(cube.isObservable(value) ? value._latestValue : value);
			} else {
				param.value = data[p_data.name];
			}
			
			param.readOnly = self.isEditState()?(typeof(p_data.readOnly)!='undefined'?p_data.readOnly:false):true;
			if (cube.isEmpty(param.validHintType)) {
				param.validHintType = self.validHintType;
			}
			
			param.isShowPopDialog = self.isShowPopDialog;
			param.onChanged = self._editor_changed;
			param.onRendered = self.editorRendered;
			
			if (p_data.editorType == "FileEditor") {
				param.onChanged = self._editor_upload;
			} 
			
			if (p_data.editorType == "DateTimeEditor" || p_data.editorType == "RichEditor") {
				param.width = typeof(p_data.width)!='undefined'?p_data.width:(self.cols()>1?null:'85%');
			} else if (p_data.editorType != "DateTimeEditor" && p_data.editorType != "FileEditor" && p_data.editorType != "RichEditor") {
				param.width = typeof(p_data.width)!='undefined'?p_data.width:(self.cols()>1?null:(p_data.editorType=='DropDownEditor'||p_data.editorType=='DropDownTreeEditor'?'auto':'85%'));
			}
			
			return param;
		};
		
		/**
		 * @ignore
		 */
		self.renderEditor = function(viewModel, value) {
			var result = viewModel.renderEditor(value);
			self.triggerRendered();
			return result;
		}
		
		/**
		 * @ignore
		 */
		self.editorRendered = function(node, viewModel) {
			if (cube.notEmpty(viewModel) && cube.isFunction(viewModel.onRendered)) {
				viewModel.onRendered(node, viewModel);
			}
			self.triggerRendered();
		};
		
		/**
		 * @ignore
		 */
		self.triggerRendered = function() {
			renderedEditorCount++;
			if (renderedEditorCount == self.fields().length) {
				if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
					self.onRendered(_node, this);
				}
			}
		}
		
		/**
		 * @ignore
		 */
		self.onload = function(node, viewModel) {
			_isDomLoad = true;
			_node = node;
			if (self.isCustomTemplate() == true) {
				var $e = $(node);
				var options = $e.find("*[option]");
				var bindings = {};
				for (var i = 0; i < options.length; i++) {
					var $option = $(options[i]);
					cube.cleanNode($option);
					var option = $option.attr("option");
					var optName = option.substring(0, option.lastIndexOf("."));
					var optType = option.substring(option.lastIndexOf(".") + 1);
					
					var editor = self.editors[optName];
					var viewModel =  {	
						editors : self.editors
					};
					var _visible = "visible: typeof(editors['" + editor.name + "'].visible) != 'undefined' ? editors['" + editor.name + "'].visible : true";
							
					if (optType == "editor") {
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
						
						$option.attr("data-bind", _visible + " , attr: {id:'"+editor.name+"', 'class' : 'form-inline control-group form-custom ' + editors['"+editor.name+"'].validStatus()+' " + editor.name + ($option.is("span")?" editor-option":"") + "',title:editors['"+editor.name+"'].validHint()}");  
						cube.bindComponentByNode($option, bindings);
						
					} else if(optType == "validHint"){
						$option.attr("data-bind", "css:'text-error', text: editors['" + editor.name + "'].validHint()");  
					} else if (optType == "visible") {
						$option.attr("data-bind", _visible);  
					} else {
						var _option = editor[optType];
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
		this.idSub.dispose();
		this.actionsSub.dispose();
		if (this.dataSub != null) {
			this.dataSub.dispose();
		}
	};

	return ViewModel;
});