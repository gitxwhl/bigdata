define([ "entityContainer", 'JSONUtil', "PrintUtil", "Validator", "CheckUtil", "ExcelUtil", "PinyinUtil"], 
		function(entityContainerClass, jsonUtil, printUtil, validator, checkUtil, excelUtil, pinyinUtil) {
	cube.importComponent("editor.commoneditor");
	cube.importComponent("editor.datetimeeditor");
	cube.importComponent("editor.dropdowneditor");
	cube.importComponent("datacontainer.pagenavibar");
	cube.importComponent("controls.loading");
	
	/**
	 * 多表头表格
	 * 主键（primaryKey）是惟一的和后台主键一致。如果配置url属性，则items不需要配置。
	 * @example
	 * <code language="html">
	 *		<groupheadergrid id="datagrid" params="
	 *						style: style,
	 *						items: items,
	 *						columns: columns,
	 *						pageSize: pageSize,
	 *						url: url,
	 *						primaryKey: primaryKey
	 *						"></groupheadergrid>
	 * </code>
	 * <code language="JavaScript">
	 *		define([], function() {
	 *			var PageViewModel = function(params) {
	 *				var self = this;
	 * 
	 *				self.style = 'border: 1px solid red;';
	 *				self.items = [
	 *								{
	 *									"mxVirtualId" : null,
	 *									"depid" : "1",
	 *									"depname" : "cs",
	 *									"pardepid" : "cs",
	 *									"departdesc" : "cs",
	 *									"powerconsumption" : null,
	 *									"rowChecked":true
	 *								},
	 *								{
	 *									"mxVirtualId" : null,
	 *									"depid" : "2",
	 *									"depname" : "测试",
	 *									"pardepid" : null,
	 *									"departdesc" : null,
	 *									"powerconsumption" : null
	 *								},
	 *								{
	 *									"mxVirtualId" : null,
	 *									"depid" : "3",
	 *									"depname" : "测试",
	 *									"pardepid" : null,
	 *									"departdesc" : "测试",
	 *									"powerconsumption" : null
	 *								},
	 *								{
	 *									"mxVirtualId" : null,
	 *									"depid" : "4",
	 *									"depname" : "新建文本文档.txt",
	 *									"pardepid" : null,
	 *									"departdesc" : "测试",
	 *									"powerconsumption" : null
	 *								},
	 *								{
	 *									"mxVirtualId" : null,
	 *									"depid" : "5",
	 *									"depname" : "新建文本文档.txt",
	 *									"pardepid" : null,
	 *									"departdesc" : "测试测试测试",
	 *									"powerconsumption" : null
	 *								} ];
	 *				self.columns = [
	 *								{
	 *									name:"ceshi",
	 *									caption: "部门信息",
	 *									columns:[
	 *										{	
	 *											name: "depid", 
	 *											width:"90px",
	 *											readOnly:true, 
	 *											caption: "部门编号" , 
	 *											editorType: "TextEditor"	
	 *										},
	 *										{	name: "depname", 
	 *											width:"90px", 
	 *											caption: "部门姓名" , 
	 *											editorType: "TextEditor"
	 *										},
	 *										{	name: "departdesc",caption: "部门描述" , editorType: "TextEditor"	}
	 *									]
	 *								},
	 *								{	name: "pardepid", 
	 *									width:"90px", 
	 *									caption: "上级部门" , 
	 *									editorType: "TextEditor"},
	 *								{	name: "powerconsumption",width:"80px", caption: "用电量" , editorType: "TextEditor"	}
	 *							];
	 *				self.pageSize = 10;
	 *				self.url = '';
	 *				self.primaryKey = 'depid';
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
		 * 表格样式
		 * @default 空
		 */
		self.style = "";
		
		/**
		 * 表格高度
		 * 
		 * @default auto
		 */
		self.height = "auto";
		
		/**
		 * 操作列宽度
		 * 
		 * @default 40px
		 */
		self.operationsColumnWidth = "40px";
		
		/**
		 * 操作列对齐方式
		 * 
		 * @default main
		 */
		self.operationsColumnAlign = "left";
		
		/** 格式如下：
		 * <code language="JavaScript">
		 * [{
		 *		"depid" : "1",
		 *		"depname" : "测试"
		 *	}, {
		 *		"depid" : "2",
		 *		"depname" : "测试"
		 *	}
		 * </code> 
		 */ 
		self.items = [];

		/**
		 * 表头配置, 格式如下：
		 * <code language="JavaScript"> 
		 * [
		 *		{
		 *			name:"department",
		 *			caption: "部门信息",
		 *			columns:[
		 *				{	
		 *					name: "depid", 
		 *					width:"90px",
		 *					readOnly:true, 
		 *					caption: "部门编号" , 
		 *					editorType: "TextEditor"
		 *				},
		 *				{	name: "depname", 
		 *					width:"90px", 
		 *					caption: "部门姓名" , 
		 *					editorType: "TextEditor",
		 *					nullable:false,
		 *					//可通过isAllowAutowrap设置单元格是否换行显示，该设置覆盖表格全局设置
		 *				isAllowAutowrap: true,
		 *				//可通过displayLength设置单元格显示字符长度
		 *					displayLength : 20,
		 *					//可通过displayRow设置单元格显示行数
		 *					//displayRow : 2,
		 *					//可通过align设置单元格居中方法，取值：main、center、right
		 *					align : "center",
		 *					//可通过renderCell自定义单元格样式，cellValue为单元格值，element为td的dom对象，dictValue为对应数据字典的值，item为行数据，column为列数据
		 *					//renderCell: function(cellValue, element, dictValue, item, column){
		 *					//	return "<span style='color:red;'>" + cellValue + "</span>";
		 *					//},
		 *					//可通过onClick设置该列单元格点击事件，pkValue：行记录主键值，cellValue：单元格值，column：列信息
		 *					//onClick: function(pkValue, cellValue, column){
		 *					//	
		 *				//},
		 *				//可通过onDblClick设置该列单元格双击事件，pkValue：行记录主键值，cellValue：单元格值，column：列信息
		 *					//onDblClick: function(pkValue, cellValue, column){
		 *					//	
		 *				//},
		 *				//可通过onMouseenter设置该列单元格鼠标进入事件，pkValue：行记录主键值，cellValue：单元格值
		 *					//onMouseenter: function(pkValue, cellValue){
		 *					//	
		 *				//},
		 *				//可通过onMouseleave设置该列单元格鼠标离开事件，pkValue：行记录主键值，cellValue：单元格值
		 *					//onMouseleave: function(pkValue, cellValue){
		 *					//	
		 *				//}
		 *				},
		 *				{	name: "departdesc",caption: "部门描述" , editorType: "TextEditor"	}
		 *			]
		 *		},
		 *		{	name: "pardepid", 
		 *			width:"90px", 
		 *			caption: "上级部门" , 
		 *			editorType: "TextEditor"},
		 *		{	name: "powerconsumption",width:"80px", caption: "用电量" , editorType: "DateTimeEditor"	}
			 * ]
			 * </code> 
		 */
		self.columns = [];
		
		/**
		 * 自定义操作按钮
		 * [{
		 *		caption : "收藏",
		 *		icon : "icon-star-empty",//Font Awesome 3.0.2中的图标样式，当icon没有设置时显示caption
		 *		visible: true, //一个Boolean值,或者一个对象或对象数组，例：{column: "state", operation: "==",value: "完成"}，其中column为列对应name、operation为比较运算符或"in"，value为具体值(操作符为in时该值为数组)。不设置时默认为true
		 *		click: function(item) {
		 *		//点击按钮时触发，item为行数据
		 *		}
		 *	}, {
		 *		caption : "查看",
		 *		click: function(item) {
		 *		//点击按钮时触发，item为行数据
		 *		}
		 *	}]
		 */
		self.customOperations = [];
		
		/**
		 * 列默认宽度
		 * @default auto
		 */
		self.columnDefaultWidth = "auto";
		
		/**
		 * 表格布局，可选：auto、fixed
		 * @default auto
		 */
		self.tableLayout = "auto";
		
		/**
		 * 根据单元格值设置行背景色，一个json对象
		 * <code language="JavaScript">
		 *  {
		 *		column: "state",
		 *		value: 1,
		 *		operation: "==",
		 *		background: "#cc0000"
		 *	}
		 * </code>
		 * 其中column为列对应name，operation为比较运算符或"in"，value为具体值(操作符为in时该值为数组)
		 * 
		 * @default null
		 */
		self.trBackground = null;
		
		/**
		 * 根据单元格值设置行复选框是否显示，一个json对象或对象数组
		 * <code language="JavaScript">
		 *  {
		 *		column: "state",
		 *		value: 1,
		 *		operation: "=="
		 *	}
		 * </code>
		 * 
		 * 其中column为列对应name，operation为比较运算符或"in"，value为具体值(操作符为in时该值为数组)
		 * @default null
		 */
		self.checkboxHideCondition = null;

		/**
		 * @ignore
		 */
		self._columns = [];

		/**
		 * @ignore
		 */
		self._levelColumns = [];
		
		/**
		 * @ignore
		 */
		self._showItemColumns = [];
		
		/**
		 * 追加列信息
		 * @ignore
		 */
		self.appendColumns = null;

		/**
		 * 后台请求地址
		 * 
		 * @default null
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
		 * 当actions中设置某一个属性则会使用action属性的值作为本次请求的操作路径
		 * 即将action属性的值作为请求的基准路径。如：actions:{remove:"~/casetest/rest/user/"},
		 * 则在remove时会使用~/casetest/rest/user/作为请求的路径，不再使用url属性。
		 * </p>
		 */
		self.actions = {};
		
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
		 * 获取或设置一个数字，表示请求的默认超时时间，以毫秒为单位，默认使用cube.timeout设置的时间。
		 * 
		 * @default null
		 */
		self.timeout = null;

		/**
		 * 每页显示记录数
		 * 
		 * @default 20
		 */
		self.pageSize = 20;

		/**
		 * 当前页码
		 * 
		 * @default 1
		 */
		self.pageIndex = 1;

		/**
		 * 每页显示记录数
		 * 
		 * @default 0
		 */
		self.itemCount = 0;
		
		/**
		 * 是否显示翻页按钮文字表述
		 * @default
		 *		false
		 */
		self.isShowBtnText = false;
		
		/**
		 * 设置“首页”按钮图标（参考Font Awesome 图标库）， 例如：“icon-step-backward”。
		 * @default 空
		 */
		self.firstIcon = "";
		
		/**
		 * 设置“上一页”按钮图标（参考Font Awesome 图标库）， 例如：“icon-caret-main”。
		 * @default 空
		 */
		self.previousIcon = "";
		
		/**
		 * 设置“下一页”按钮图标（参考Font Awesome 图标库），例如：“icon-caret-right”。
		 * @default  空
		 */
		self.nextIcon = "";
		
		/**
		 * 设置“末页”按钮图标（参考Font Awesome 图标库）， 例如：“icon-step-forward”。
		 * @default 空
		 */
		self.endIcon = "";
		
		/**
		 * 是否在分页条显示数据加载时间
		 * @default false
		 */
		self.isShowLoadTime = false;
		
		/**
		 * 分页显示的加载时间是否自动淡出，一个数字（毫秒），当值为0时不淡出，否则按设置时间淡出
		 * @default 0
		 * 
		 */
		self.loadTimeFadeout = 0;
		
		/**
		 * 分页信息居左（总页数、总记录数）
		 * 
		 *  @default false
		 */
		self.pageInfoToLeft = false;

		/**
		 * 分页可见页数
		 * @default
		 *		5
		 */
		self.pageVisibleCount = 5;
		
		/**
		 * 是否失去焦点时跳转页面，默认输入即跳转
		 * @default
		 *		false
		 */
		self.blurGoto = false;
		
		/**
		 * @ignore
		 */
		self._pageCount = 0;

		/**
		 * 获取或设置一个属性，该属性对应的值将用作数据项的 id 值，表示主键唯一值。 此属性必须在初始化控件时设置
		 * 
		 * @default id
		 */
		self.primaryKey = "id";
		
		/**
		 * 是否加载元数据
		 * @default
		 *		true
		 */
		self.loadMeta = true;
		
		/**
		 * 设置复选框选中，一个主键值得数组
		 *  @default []
		 */
		self.selectedIds = [];

		/**
		 * 内部属性，选中项
		 * 
		 * @default []
		 * @ignore
		 */
		self._selectedIds = [];
		
		/**
		 * 内部属性，选中项
		 * 
		 * @default []
		 * @ignore
		 */
		self._selectedItems = [];
		
		/**
		 * 内部属性，是否全选
		 * 
		 * @default null
		 */
		self._selectedAll = false;

		/**
		 * 内部属性，选中项
		 * 
		 * @default null
		 * @ignore
		 */
		self._selectedItem = null;

		/**
		 * 内部属性，当前被编辑的项
		 * 
		 * @default null
		 * @ignore
		 */
		self._editedItem = null;
		
		/**
		 * 内部属性，当前被编辑的项(原生)
		* @ignore
		*/
		self._editedItemObj = null;
		
		/**
		 * 内部属性，当前被编辑的项索引
		 * 
		 * @default null
		 * @ignore
		 */
		self._editedItemIndex = null;

		/**
		 * 内部属性，当前添加项
		 * 
		 * @default {}
		 */
		self._appendedItem = {};
		
		/**
		 * @ignore
		 * 内部属性，当前添加项原始数据
		 * 
		 * @default {}
		 */
		self._appendedItemObj = {};
		
		/**
		 * 内部属性
		 * 
		 * @default false
		 */
		self._isEdited = false;
		
		/**
		 * 是否显示主键列
		 * 
		 * @default false
		 */
		self.isShowPrimaryKey = false;

		/**
		 * 是否显示标号
		 * 
		 * @default true
		 */
		self.isShowRowNumber = true;
		
		/**
		 * 设置编号列显示列名
		 * 
		 * @default 编号
		 */
		self.rowNumberColumnName = null;
		
		/**
		 * 显示编号分页之后是否连续
		 * 
		 * @default false
		 */
		self.isRowNumberSerial = false;

		/**
		 * 是否显示复选框
		 * 
		 * @default true
		 */
		self.isShowCheckBox = false;
		
		/**
		 * 该值表示数据项之前选择框是否启用单选效果。如果该值为 true，则表示启用；反之则不启用。
		 * 
		 * @default false
		 */
		self.isRadio = false;

		/**
		 * 是否显示边框
		 * 
		 * @default true
		 */
		self.isShowBorder = true;

		/**
		 * 是否显示斑马线格式
		 * 
		 * @default false
		 */
		self.isShowStripe = false;

		/**
		 * 是否显示悬浮格式
		 * 
		 * @default false
		 */
		self.isShowHover = false;

		/**
		 * 是否紧凑显示
		 * 
		 * @default false
		 */
		self.isShowCondense = false;

		/**
		 * 是否显示操作列
		 * 
		 * @default false
		 */
		self.isAllowOperations = false;
		
		/**
		 * 是否显示保存按钮
		 * 
		 * @default true
		 */
		self.isShowSave = true;
		
		/**
		 * 是否显示新增按钮
		 * 
		 * @default true
		 */
		self.isShowAdd = true;

		/**
		 * 是否显示编辑按钮
		 * 
		 * @default false
		 */
		self.isAllowEdit = false;

		/**
		 * 是否显示删除按钮
		 * 
		 * @default false
		 */
		self.isAllowDelete = false;

		/**
		 * 是否显示移动按钮
		 * 
		 * @default false
		 */
		self.isAllowShift = false;

		/**
		 * 是否允许添加
		 * 
		 * @default false
		 */
		self.isAllowAppend = false;

		/**
		 * 是否允许排序
		 * 
		 * @default true
		 */
		self.isAllowSort = true;

		/**
		 * 是否分页显示
		 * 
		 * @default true
		 */
		self.isAllowPaging = true;
		
		/**
		 * 是否自动换行
		 * 
		 * @default false
		 */
		self.isAllowAutowrap = false;
		
		/**
		 * 设置表格的过滤条件。该属性是一个字符串或者是一个 JSON 对象，当表格与搜索框组件关联时，该属性与搜索框组件的searchParamType属性有关。
		 * 当 搜索框组件的 searchParamType 属性为 string 时，该属性是字符串或者 JSON 对象，例如："name='jacky'&age=23" 或者 {name: "jacky", age: 23}, 此时执行简单查询；
		 * 当 搜索框组件的 searchParamType 属性为 json 时，该属性是 JSON 对象， 该 JSON 对象必须遵循高级查询格式规范, 例如： 
		 * {criterions: [
		 *	{fieldName: "age", value: 18, operator: ">"},
		 *	{fieldName: "age", value: 60, operator: "<"}
		 * ], junction： "and", columnJunction: "and"} 表示对属性名为 “age” 的列设置过滤条件： 18 < age < 60。
		 *
		 * 
		 * @default null
		 */
		self.args = null;
		
		/**
		 * 查询组件标签的Id，用于表格查询
		 * @default null
		 */
		self.searchBoxId = null;
		
		/**
		 * 初始化表格时是否自动加载数据
		 *  @default true
		 */
		self.autoLoad = true;
		
		/**
		 * 点击保存按钮时是否自动保存数据到后台，当该属性设置为false时需通过手动调用save方法进行保存。
		 * @default true
		 */
		self.autoSave = true;
		
		/**
		 * 调用删除时是否调用后台删除，当该属性设置为false时为本地删除，不操作后台。
		 * @default true
		 */
		self.autoDelete = true;
		
		/**
		 * 删除之后是否重新加载数据、
		 * @default true
		 */
		self.refreshDeleted = true;
		
		/**
		 * 数据字典
		 */
		self.dicts = null;
		
		/**
		 * 水印配置，一个json对象，以下示例中除了text均为默认值，可不用设置。
		 * 水印透明度和水印倾斜角度两个属性在IE9及以下浏览器中为固定值，此处设置无效。
		 *  @example
		 *  <code language="JavaScript">
		 *	{
		 *		text: "国家电网",//水印文字内容
		 *		x: 40, //水印起始位置x轴坐标
		 *		y: 40, //水印起始位置y轴坐标
		 *		xSpace: 150, //水印x轴间隔
		 *		ySpace: 60, //水印y轴间隔
		 *		color: "#278874", //水印字体颜色
		 *		fontSize: "16px", //水印字体大小
		 *		width: 100, //水印宽度
		 *		height: 20, //水印高度
		 *		rotate: 25, //水印倾斜角度
		 *		alpha: 0.2 //水印透明度
		 *	}
		 * </code>
		 * 
		 */
		self.watermarkOptions = null;
		
		/**
		 *  @ignore
		 */
		self._watermarkOptionsDefaultValue = {
			text: "",
			x: 40,
			y: 40,
			xSpace: 150,
			ySpace: 60,
			color: "#278874",
			alpha: 0.2,
			fontSize: "16px",
			width: 100,
			height: 20,
			rotate: 25
		};
		
		/**
		 *  @ignore
		 */
		self.watermarkRows = [];
		
		/**
		 *  @ignore
		 */
		self.watermarkCols = [];
		
		/**
		 * 对应html方式显示的内容中的脚本处理方式，可选：escape（转义）、clean（清除）
		 * 注意：对于renderCell自定义单元格内容中脚本将进行clean。
		 * 
		 * @default escape
		 */
		self.scriptProcessing = "escape";
		
		/**
		 *	加载效果的显示状态，true为显示，false为不显示
		 *	@default false
		 */
		self.isShowLoading = false;
		
		/**
		 * @ignore
		 */
		self._indexNotLoad = false;
		
		/**
		 * @ignore
		 * 数据字典key-value暂存
		 */
		self._dictsKeyValue = null;
		
		/**
		 * @ignore
		 */
		self.showCellPopDialog = cube.obj(false);
		
		/**
		 * @ignore
		 */
		self.loadTime = 0;

		/**
		 * 选中记录回调函数，接收参数为一个对象：已选中记录数据
		 * 
		 * @default null
		 */
		self.onSelectedItem = null;
		
		/**
		 * 勾选记录回调函数，接收参数为一个数组对象：已勾选记录主键ID数组
		 * 
		 * @default null
		 */
		self.onSelectedIds = null;

		/**
		 * 勾选记录回调函数，接收参数为一个数组对象：已勾选记录数组
		 * 
		 * @default null
		 */
		self.onSelectedItems = null;

		/**
		 *  是否首次加载
		 *  @ignore
		 */
		self.loadCompleted = false;
		
		/**
		 * 记录双击事件，接收参数为一个对象：双击记录数据
		 */
		self.onDblClick = null;
		
		/**
		 * 行数据保存之前回调事件，
		 * 回调函数接收参数为一个对象，例：
		 * function(args){
		 *		//args.data为行数据
		 *		//args.cancel赋值为true是取消保存
		 * }
		 */
		self.onItemSaving = null;
		
		/**
		 * 行数据保存之后回调事件，接收参数为一个对象：保存记录数据
		 */
		self.onItemSaved = null;
		
		/**
		 * 行数据编辑之前回调事件
		 */
		self.onItemEditing = null;
		
		/**
		 * 行数据删除之前回调事件
		 * @param args 行记录数据
		 * 例：
		 * function(args){
		 *		//args.data为行数据
		 *		//args.cancel赋值为true是取消删除
		 * }
		 */
		self.onItemDeleting = null;
		
		/**
		 * 行数据删除之后回调事件
		 *  @param item 删除的行数据，通过复选框删除时为id数组
		 *  @param p_result 后端返回数据
		 */
		self.onItemDeleted = null;
		
		/**
		 * 表格数据加载后回调事件
		 * @param items 表格数据
		 */
		self.onLoaded = null;
		
		/**
		 * 表格数据加载失败事件
		 * @params args 错误信息
		 */
		self.onLoadError = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			self._columns = self.columns();
			
			parseColumns(self._columns,null, 0, 0);

			self.pageSizeSub = cube.subscribe(self.pageSize, self.load);
			self.argsSub = cube.subscribe(self.args, function(){
				self.load();
			});
			self._selectedIdsSub = cube.subscribe(self._selectedIds, self.chkboxClick);
			self._selectedAllSub = cube.subscribe(self._selectedAll, self.chkboxAllClick);
			self.selectedIdsSub = cube.subscribe(self.selectedIds, function(value) {
				self._selectedIds(value);
			});
			
			self.actionsSub = cube.subscribe(self.actions, function(p_actions) {
				entityContainer.actions = p_actions;
			});
			
			self.dictsSub = cube.subscribe(self.dicts, function(dicts){
				if (cube.isArray(dicts) && dicts.length > 0 && (self._dictsKeyValue() != null || self.autoLoad() == false)) {
					entityContainer._parseDicts({dicts: dicts});
					self._dictsKeyValue(entityContainer.dictsKeyValue);
					initColumn({dicts: dicts});
				}
			});

			entityContainer.primaryKey = self.primaryKey();
			entityContainer.baseUrl = self.url();
			entityContainer.actions = self.actions();
			entityContainer.type = "grid";
			entityContainer.timeout = self.timeout();
			entityContainer.loadMeta = self.loadMeta();
			entityContainer.data = self.items();
			entityContainer.onload = onload;
			entityContainer.onerror = _error;
			entityContainer.customHeaders = self.customHeaders();
			entityContainer.loadByPost = self.loadByPost();
			entityContainer.init();
			
			if (self.autoLoad()) {
				var searchBox = cube.getPageViewModelByNode($("#" + self.searchBoxId()));
				if (searchBox && !searchBox._isload() && (cube.notEmpty(searchBox.url()) || searchBox._lazyload())) {
					cube.subscribe(searchBox._isload, function() {
						self.load();
					});
				} else {
					self.load();
				}
			} else {
				initColumn();
			}
			
			if (self.isAllowAppend()) {
				initAppendedItem(self._showItemColumns());
			}
		};
		
		/**
		 * 设置actions
		 * @param 一个对象，例：{remove:"~/casetest/rest/user/"}
		 */
		self.setActions = function(p_actions) {
			self.actions = p_actions;
		};

		/**
		 * @ignore
		 */
		self.chkboxClick = function(p_value) {
			var items = self.items();
			var primaryKey = self.primaryKey();
			for ( var i = 0, j = items.length; i < j; i++) {
				if (typeof(items[i][primaryKey]) == "undefined") {
					cube.indicate("warning", cube.msg("SET_PRIMARYKEY"));
					break;
				}
				
				if (self.isRadio() && cube.notEmpty(p_value) && p_value == String(items[i][primaryKey])) {
					if (self._selectedItems.indexOf(items[i]) == -1) {
						self._selectedItems.push(items[i]);
					}
				} else if (cube.notEmpty(p_value) && cube.isArray(p_value) && p_value.indexOf(String(items[i][primaryKey])) != -1) {
					if (self._selectedItems.indexOf(items[i]) == -1) {
						self._selectedItems.push(items[i]);
					}
				} else {
					self._selectedItems.remove(items[i]);
				}
			}		
			
			if (items.length > 0) {
				self._selectedAllSub.dispose();
				if (self._selectedItems().length == items.length) {
					self._selectedAll(true);
				} else {
					self._selectedAll(false);
				}
				self._selectedAllSub = cube.subscribe(self._selectedAll, self.chkboxAllClick);
				
				if (self.onSelectedItems != null && !cube.isObservable(self.onSelectedItems)) {
					self.onSelectedItems(self._selectedItems());
				}
				
				if (self.onSelectedIds != null && !cube.isObservable(self.onSelectedIds)) {
					self.onSelectedIds(self._selectedIds());
				}
			}
		};

		/**
		 * 全选
		 */
		self.chkboxAllClick = function() {
			self._selectedIdsSub.dispose();
			
			var selectedIds = self._selectedIds();
			var items = self.items();
			var primaryKey = self.primaryKey();
			for ( var i = 0, j = items.length; i < j; i++) {
				if (typeof(items[i][primaryKey]) == "undefined") {
					cube.indicate("warning", cube.msg("SET_PRIMARYKEY"));
					break;
				}
				
				if (self._selectedAll()) {
					if (selectedIds.indexOf(String(items[i][primaryKey])) == -1 && self.checkboxVisible(items[i])) {
						self._selectedIds.push(String(items[i][primaryKey]));
						self._selectedItems.push(items[i]);
					}
				} else {
					self._selectedIds.shift();
					self._selectedItems.shift();
				}
			}

			if (self.onSelectedItems != null
					&& !cube.isObservable(self.onSelectedItems)) {
				self.onSelectedItems(self._selectedItems());
			}
			
			if (self.onSelectedIds != null
					&& !cube.isObservable(self.onSelectedIds)) {
				self.onSelectedIds(self._selectedIds());
			}
			
			self._selectedIdsSub = cube.subscribe(self._selectedIds, self.chkboxClick);
		};

		/**
		 * @ignore
		 * 设置选中项
		 */
		self.setSelectedItem = function(item, index) {
			if (self._isEdited() && self._selectedItem() != item) {
				if (!validate(1)) {
					return;
				}
				
				if (self.onItemEditing != null && !cube.isObservable(self.onItemEditing)) {
					self.onItemEditing(item, index);
				}
				
				for (var key in self._editedItem) {
					if (key == "mxVirtualId"){
						continue;
					}
					entityContainer.setValue(self._editedItemObj, key, self._editedItem[key]());
				}
				
				self.items.replace(self.items()[self._editedItemIndex], self._editedItemObj);
				
				
				var obj = {};
				var itemObj = {};
				for (var key in item) {
					obj[key] = cube.obj(item[key]);
					itemObj[key] = item[key];
				}
				self._editedItem = obj;
				self._editedItemObj = itemObj;
				self._editedItemIndex = index;
			}
			
			self._selectedItem(item);
			if (self.onSelectedItem != null && !cube.isObservable(self.onSelectedItem)) {
				self.onSelectedItem(item);
			}
		};
		
		/**
		 * 取消行选中
		 */
		self.cancelSelectedItem = function() {
			self._selectedItem(null);
		};
		
		/**
		 * @ignore
		 */
		self.dblclick = function(item) {
			if (self.onDblClick != null && !cube.isObservable(self.onDblClick)) {
				self.onDblClick(item);
			}
		};

		/**
		 * @ignore
		 */
		self.delItem = function(p_item) {
			var item = p_item;
			cube.indicate("confirm", cube.msg("CONFIRM_DELETE"), function() {
				
				if (self.onItemDeleting != null && !cube.isObservable(self.onItemDeleting)) {
					var args = {cancel: false, data : item};
					self.onItemDeleting(args);
					if (args.cancel) {
						return;
					}
				}
				
				var primaryKeyValue = item[self.primaryKey()];
				if (primaryKeyValue && self.autoDelete()) {
					entityContainer.ondeleted = function(args, p_result) {
						self.items.remove(item);
						self._selectedIds.remove(primaryKeyValue);
						
						if (self.onItemDeleted != null && !cube.isObservable(self.onItemDeleted)) {
							self.onItemDeleted(item, p_result);
						}
						
						if (self.refreshDeleted()) {
							self.load();
						}
					};
					entityContainer.remove(primaryKeyValue, item);
				} else {
					self.items.remove(item);
					
					if (self.onItemDeleted != null && !cube.isObservable(self.onItemDeleted)) {
						self.onItemDeleted(item);
					}
				}
			});
		};
		
		/**
		 * 删除所选行
		 */
		self.delSelectedItem = function() {
			cube.indicate("confirm", cube.msg("CONFIRM_DELETE"), function() {
				var item = self._selectedItem();
				
				if (self.onItemDeleting != null && !cube.isObservable(self.onItemDeleting)) {
					var args = {cancel: false, data : item};
					self.onItemDeleting(args);
					if (args.cancel) {
						return;
					}
				}
				
				var primaryKeyValue = item[self.primaryKey()];
				if (primaryKeyValue && self.autoDelete()) {
					entityContainer.ondeleted = function(args, p_result) {
						self.items.remove(item);
						self._selectedIds.remove(primaryKeyValue);
						
						if (self.onItemDeleted != null && !cube.isObservable(self.onItemDeleted)) {
							self.onItemDeleted(item, p_result);
						}
						
						if (self.refreshDeleted()) {
							self.load();
						}
					};
					entityContainer.remove(primaryKeyValue);
				} else {
					self.items.remove(item);
					
					if (self.onItemDeleted != null && !cube.isObservable(self.onItemDeleted)) {
						self.onItemDeleted(item);
					}
				}
			});
		};
		
		/**
		 * 删除复选框所选行
		 */
		self.delCheckedItem = function() {
			cube.indicate("confirm", cube.msg("CONFIRM_DELETE"), function() {
				var selectedIds = self._selectedIds();
				
				if (self.onItemDeleting != null && !cube.isObservable(self.onItemDeleting)) {
					var args = {cancel: false, data : selectedIds};
					self.onItemDeleting(args);
					if (args.cancel) {
						return;
					}
				}
				
				if (self.autoDelete()) {
					entityContainer.ondeleted = function(args, p_result) {
						for(var i = 0; i < selectedIds.length; i++){
							self.items.remove(jsonUtil.getItem(self.items(), self.primaryKey(), selectedIds[i]));
						}
						
						if (self.onItemDeleted != null && !cube.isObservable(self.onItemDeleted)) {
							self.onItemDeleted(selectedIds, p_result);
						}
						
						if (self.refreshDeleted()) {
							self.load();
						}
					};
					entityContainer.remove(selectedIds);
				} else {
					for(var i = 0; i < selectedIds.length; i++){
						self.items.remove(jsonUtil.getItem(self.items(), self.primaryKey(), selectedIds[i]));
					}
					
					if (self.onItemDeleted != null && !cube.isObservable(self.onItemDeleted)) {
						self.onItemDeleted(selectedIds);
					}
				}
				
				self._selectedIds([]);
			});
		};

		/**
		 * 编辑项
		 * @ignore
		 */
		self.editItem = function(item, index) {
			if (self._isEdited() && self._selectedItem() != item) {
				if (!validate(1)) {
					return;
				}
				
				for (var key in self._editedItem) {
					if (key == "mxVirtualId"){
						continue;
					}
					entityContainer.setValue(self._editedItemObj, key, self._editedItem[key]());
				}
				
				self.items.replace(self.items()[self._editedItemIndex], self._editedItemObj);
			}
			
			if (self.onItemEditing != null && !cube.isObservable(self.onItemEditing)) {
				self.onItemEditing(item, index);
			}
			
			var obj = {};
			var itemObj = {};
			for (var key in item) {
				if(key == self.primaryKey() && item[key] == null) {
					continue;
				}
				
				obj[key] = cube.obj(item[key]);
				itemObj[key] = item[key];
			}
			
			var columns = self._showItemColumns();
			if (columns && columns.length > 0) {
				$.each(columns, function(index, p_column) {
					if (p_column.editorType == "FileEditor") {
						if (p_column.pkVal && cube.isObservable(p_column.pkVal)) {
							p_column.pkVal(item[self.primaryKey()]);
						} else {
							p_column.pkVal = cube.obj(item[self.primaryKey()]);
						}
					}
				});
			}
			
			self._editedItemIndex = index;
			self._editedItem = obj;
			self._editedItemObj = itemObj;
			self._isEdited(true);
			
			self._selectedItem(item);
		};

		/**
		 * @ignore
		 */
		self.shiftupItem = function() {
			var i = self.items.indexOf(this);
			if (i > 0) {
				self.items.splice(i, 1);
				self.items.splice(i - 1, 0, this);
			}
		};
		
		/**
		 * @ignore
		 */
		self.shiftdownItem = function() {
			var i = self.items.indexOf(this);
			if (i < self.items().length - 1) {
				self.items.splice(i, 1);
				self.items.splice(i + 1, 0, this);
			}
		};

		/**
		 * 保存修改
		 */
		self.saveItem = function() {
			if (validate(1)) {
				
				if (self.autoSave()) {
					// 保存修改
					for (var key in self._editedItem) {
						if (key == "mxVirtualId"){
							continue;
						}
						entityContainer.setValue(self._editedItemObj, key, self._editedItem[key]());
					}
					
					if (self.onItemSaving != null && !cube.isObservable(self.onItemSaving)) {
						var args = {cancel: false, data : self._editedItemObj};
						self.onItemSaving(args);
						if (args.cancel) {
							return;
						}
					}
		
					entityContainer.onsaved = function(args) {
						if (typeof(self._editedItemObj[self.primaryKey()]) == "undefined" && args.result.newData.length > 0) {
							self.items.replace(self.items()[self._editedItemIndex], args.result.newData[0]);
							if (self.onItemSaved != null && !cube.isObservable(self.onItemSaved)) {
								self.onItemSaved(args.result.newData[0]);
							}
						} else {
							self.items.replace(self.items()[self._editedItemIndex], self._editedItemObj);
							if (self.onItemSaved != null && !cube.isObservable(self.onItemSaved)) {
								self.onItemSaved(self._editedItemObj);
							}
						}
						
					};
					
					entityContainer.save();
				} else {
					var obj = {};
					for (var key in self._editedItem) {
						if (key == "mxVirtualId"){
							continue;
						}
						
						obj[key] = self._editedItem[key]();
					}
					
					if (self.onItemSaving != null && !cube.isObservable(self.onItemSaving)) {
						var args = {cancel: false, data : obj};
						self.onItemSaving(args);
						if (args.cancel) {
							return;
						}
					}
					
					self.items.replace(self.items()[self._editedItemIndex], obj);
					
					if (self.onItemSaved != null && !cube.isObservable(self.onItemSaved)) {
						self.onItemSaved(obj);
					}
				}
				
				self._isEdited(false);
				self._editedItem = null;
				self._selectedItem(null);
			}
		};
		
		var _appendItem = null;
		/**
		 * 保存新增
		 * @ignore
		 */
		self.saveAppend = function(p_args, p_save) {
			if (validate(0)) {
				if (_appendItem == null) {
					_appendItem = entityContainer.create();
				}
				for (var key in self._appendedItem) {
					if (key == "mxVirtualId" || key == self.primaryKey()){
						continue;
					}
					_appendItem[key] = self._appendedItem[key]();
				}
				
				if (p_args && cube.isArray(p_args)) {
					for (var j = 0; j< p_args.length; j++) {
						for (var key in p_args[j]) {
							if (key == "mxVirtualId" || key == self.primaryKey()){
								continue;
							}
							_appendItem[key] =  p_args[j][key];
						}
					}
				}
				
				if (self.autoSave() || p_save === true) {
					if (self.onItemSaving != null && !cube.isObservable(self.onItemSaving)) {
						var args = {cancel: false, data : _appendItem};
						self.onItemSaving(args);
						if (args.cancel) {
							return;
						}
					}
					
					// 保存修改
					for (var key in _appendItem) {
						if (key == self.primaryKey()){
							continue;
						}
						entityContainer.setValue(_appendItem, key, _appendItem[key]);
					}
		
					entityContainer.onsaved = function(args) {
						for (var i=0;i<args.result.newData.length;i++) {
							self.items.push(args.result.newData[i]);
						}
						
						var isSet = false;
						var columns = self._showItemColumns();
						for (var key in self._appendedItem) {
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
							
							if(!isSet){
								self._appendedItem[key](null);
								self._appendedItemObj[key] = null;
								isSet = false;
							}
							delete self._appendedItem["mxVirtualId"];
							delete self._appendedItem[self.primaryKey()];
						}
						
						if (self.onItemSaved != null && !cube.isObservable(self.onItemSaved)) {
							self.onItemSaved(_appendItem);
						}
						
						_appendItem = null;
					};
					
					entityContainer.save();
				
				} else {
					if (self.onItemSaving != null && !cube.isObservable(self.onItemSaving)) {
						var args = {cancel: false, data : _appendItem};
						self.onItemSaving(args);
						if (args.cancel) {
							return;
						}
					}
					
					self.items.push(_appendItem);
					var isSet = false;
					var columns = self._showItemColumns();
					for (var key in self._appendedItem) {
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
						
						if(!isSet){
							self._appendedItem[key](null);
							self._appendedItemObj[key] = null;
							isSet = false;
						}
						delete self._appendedItem["mxVirtualId"];
						delete self._appendedItem[self.primaryKey()];
					}
					
					if (self.onItemSaved != null && !cube.isObservable(self.onItemSaved)) {
						self.onItemSaved(_appendItem);
					}
					
					_appendItem = null;
				}
				
				self._isEdited(false);
				self._editedItem = null;
				self._selectedItem(null);
			}
		};

		/**
		 * 取消保存
		 */
		self.cancelSaveItem = function() {
			self._isEdited(false);
			self._editedItem = null;
			self._selectedItem(null);
			
			var columns = self._showItemColumns();
			for (var i = 0; i < columns.length; i++) {
				columns[i].validStatus("");
			}
		};

		/**
		 *  添加数据，如果主键通过服务器生成，则需要采用数据实体组件！
		 */
		self.appendItem = function() {
			if (validate(0)) {
				var obj = {};
				var isSet = false;
				var columns = self._showItemColumns();
				for (var key in self._appendedItem) {
					obj[key] = self._appendedItem[key]();
					
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
					
					if(!isSet){
						self._appendedItem[key](null);
						self._appendedItemObj[key] = null;
						isSet = false;
					}
				}
				
				self.items.push(obj);
			}
		};

		/**
		 * @ignore
		 */
		self.sortType = cube.obj(-1); // 1：当前为升序；-1：当前为降序
		
		/**
		 * @ignore
		 */
		self.sortName = cube.obj("");
		
		/**
		 * @ignore
		 */
		self.sortGrid = function() {
			var sortName = this.name;
			self.sortName(sortName);
			// 如何实现按照拼音排序？
			self.items.sort(function(left, right) {
				var leftVal = left[sortName];
				var rightVal = right[sortName];
				
				if (leftVal == null && rightVal == null) {
					return 0;
				} else if (leftVal==null && rightVal != null) {
					return self.sortType() == 1 ? -1 : 1;
				} else if (leftVal!=null && rightVal== null) {
					return self.sortType() == 1 ? 1 : -1;
				}
				
				if(!isNaN(leftVal) && !isNaN(rightVal)) {
					leftVal = Number(leftVal);
					rightVal = Number(rightVal);
				} else {
					leftVal = pinyinUtil.convertPinyin(leftVal);
					rightVal = pinyinUtil.convertPinyin(rightVal);
				}
				
				var rel = leftVal == rightVal ? 0 : (leftVal < rightVal
						? self.sortType()
						: self.sortType() * -1);
				return rel;
			});
			self.sortType(self.sortType() * -1);
		};
		
		var _canMove = false, _startMove = false, _mouseX = 0, _srcX = 0, _element;
		/**
		 * @ignore
		 */
		self.mousedown = function(element, data, event) {
			if (!_canMove) {
				return;
			}
			_startMove = true;
			_mouseX = event.clientX;
			_srcX = $(element).width();
			_element = $(element);
		};

		/**
		 * @ignore
		 */
		self.mouseup = function() {
			_startMove = false;
		};

		/**
		 * @ignore
		 */
		self.mousemove = function(element, event) {
			if(element.colSpan!=1){
				return;
			}
			
			if (event.offsetX > $(element).width() - 10) {
				element.style.cursor = 'e-resize';
				_canMove = true;
			} else {
				element.style.cursor = 'default';
				_canMove = false;
			}

			if (_startMove) {
				var offsetX = event.clientX - _mouseX;
				_element.width((_srcX + offsetX) + "px");
			}
		};

		/**
		 * @ignore
		 */
		self.onCurrentPageIndexChanged = function(p_pageIndex) {
			self._selectedAll(false);
			
			if (!self._indexNotLoad()) {
				self.load();
			}
		};

		/**
		 * 加载数据
		 */
		self.load = function(p_data) {
			
			self.isShowLoading(true);
			_startLoadTime = new Date().getTime();
			
			self._selectedItem(null);
			self._selectedIds([]);
			self._selectedItems.removeAll();
			
			var data = null;
			if (cube.notEmpty(p_data) && $.isPlainObject(p_data)) {
				data = p_data;
			}
			
			var args = {metaParams:null, dataParams:null};
			if (cube.notEmpty(data)) {
				if (cube.isEmpty(data.metaParams) && cube.isEmpty(data.dataParams)) {
					args.dataParams = data;
				} else {
					args = $.extend(args, data);
				}
			}
			
			var filterParam = _getFilterParam(p_data);
			if (cube.notEmpty(filterParam)) {
				var filter = { filter: filterParam};
				args.dataParams = $.extend(filter, args.dataParams);
			}
			
			if (self.loadMeta() && cube.notEmpty(self._columns)) {
				var columnsFilter = "";
				var columnNames = self._getColunmsNames();
				columnsFilter = columnNames.join(",");
				var columns1 = {columns:columnsFilter};
				var columns2 = {columns:columnsFilter};
				args.metaParams = $.extend(columns1, args.metaParams);
				args.dataParams = $.extend(columns2, args.dataParams);
			}
			
			if (args.dataParams && cube.notEmpty(args.dataParams.pageIndex) && args.dataParams.pageIndex != self.pageIndex()) {
				self._indexNotLoad(true);
				self.pageIndex(args.dataParams.pageIndex);
				self._indexNotLoad(false);
			}
			
			var page = {pageIndex:parseInt(self.pageIndex()), pageSize:parseInt(self.pageSize())};
			args.dataParams = $.extend(page, args.dataParams);
			
			entityContainer.load(args);
		};
		
		/**
		 * @ignore
		 * 获取表格的列名集合 
		 */
		self._getColunmsNames = function() {
			var columnNames = []
			for (var i = 0; i < self._columns.length; i++) {
				var column = self._columns[i];
				processColumn(columnNames, column);
			}
			return columnNames;
		}
		
		/**
		 * 递归方法，用来获取多表头最后一级的列名
		 * @param p_columnNames 一个数组，用来保存列名
		 * @param p_column 多表头的列。
		 */
		function processColumn(p_columnNames, p_column) {
			if(!cube.isArray(p_columnNames)|| cube.isEmpty(p_column)) {
				return;
			}
			
			if (p_column.columns != undefined && cube.isArray(p_column.columns)) {
				for (var i=0; i<p_column.columns.length; i++) {
					processColumn(p_columnNames, p_column.columns[i]);
				}
			} else {
				p_columnNames.push(p_column.name);
			}
		}
		
		/**
		 * 获取查询参数。
		 * @ignore
		 */
		function _getFilterParam(p_data)
		{
			var isSearchLoad = false;
			if (cube.notEmpty(p_data) && cube.notEmpty(p_data.isSearchLoad)) {
				isSearchLoad = p_data.isSearchLoad;
				delete p_data.isSearchLoad;
			}
			
			var filterParam = "";
			if (cube.notEmpty(p_data) && cube.notEmpty(p_data.filter)) {
				filterParam = p_data.filter;
			} else {
				filterParam = cube.clone(p_data);
				if (filterParam) {
					delete filterParam.pageIndex;
					delete filterParam.pageSize;
				}
			}
			
			var _args = null;
			if (cube.isObservable(self.args)) {
				_args = self.args();
			} else {
				_args = self.args;
			}
			
			var _params = null;
			var searchBox = null;
			if (self.searchBoxId()) {
 				searchBox = cube.getPageViewModelByNode($("#" + self.searchBoxId()));
 				if (searchBox) {
 					_params = cube.isObservable(searchBox._params) ? searchBox._params() : searchBox._params;
 					if (!isSearchLoad || !_params) {
 						if (searchBox.searchParamType() == "json") {
							_params = searchBox.getJsonSearchParam();
						} else {
							_params = searchBox.getSearchParam();
						}
 					}
 				}
 			}
			
			if (searchBox && searchBox.searchParamType() == "json") {
				if (_params.length > 0) {
					if (cube.isArray(_args)) {
						for (var i = 0; i < _args.length; i++) { 
							_params.push(_args[i]);
						}
					} else {
						_params.push(_args);
					}
					
					if (cube.isArray(filterParam)) {
						for (var i = 0; i < filterParam.length; i++) { 
							_params.push(filterParam[i]);
						}
					} else {
						_params.push(filterParam);
					}
				} else {
					_params = _args;
				}
				
				filterParam = _params;
			} else {
				var myFilterParam = "";
				var filterParamString = "";
				if ($.isPlainObject(filterParam)) {
					for ( var key in filterParam) {
						var value = filterParam[key];
						filterParamString = filterParamString + "&" + key + "=" + (cube.isObservable(value)?value():value);
					}
					filterParam = filterParamString;
				}
				
				if ($.isPlainObject(_args)) {
					for ( var key in _args) {
						var value = _args[key];
						myFilterParam = myFilterParam + "&" + key + "=" + (cube.isObservable(value)?value():value);
					}
					
					if (cube.notEmpty(filterParam)) {
						filterParam = filterParam + myFilterParam;
					} else {
						filterParam = myFilterParam.substring(1, myFilterParam.length);
					}
				}  else if (cube.isString(_args)) {
					if (cube.notEmpty(filterParam)) {
						filterParam = filterParam + "&" +_args;
					} else {
						filterParam = _args;
					}
				}
				
				if ($.isPlainObject(_params)) {
					for ( var key in _params) {
						var value = _params[key];
						myFilterParam = myFilterParam + "&" + key + "=" + (cube.isObservable(value)?value():value);
					}
					
					if (cube.notEmpty(filterParam)) {
						filterParam = filterParam + myFilterParam;
					} else {
						filterParam = myFilterParam.substring(1, myFilterParam.length);
					}
				}  else if (cube.isString(_params)) {
					if (cube.notEmpty(filterParam)) {
						filterParam = filterParam + "&" +_params;
					} else {
						filterParam = _params;
					}
				}
			}
			
			return filterParam;
		}
	
		
		/**
		 * 打印表格内容
		 */
		self.print = function() {
			printUtil.print(self.items, self.columns, self.dicts, "groupheadergrid");
		}

		/**
		 * 保存表格所有项
		 * @params 
		 * 		p_args 一个数组，表格项中要追加的列信息，例：[{depId:'123456'}],
		 *	  列信息及值将追加到所有项中。
		 * 		
		 */
		self.save = function(p_args) {
			var _appendItem = null;
			var items = self.items();
			var primaryKey = self.primaryKey();
			if (items.length > 0) {
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					if (item == null) {
						continue;
					}
					
					if(!items[i][primaryKey]){
						_appendItem = entityContainer.create();
					}else{
						_appendItem = {};
						_appendItem[primaryKey] = items[i][primaryKey];
						if(self._editedItem && self._editedItem[primaryKey] && item[primaryKey] == self._editedItem[primaryKey]()){
							item = self._editedItem;
						}
					}
					
					for (var key in self._appendedItemObj) {
						if (key == "mxVirtualId" || key == primaryKey){
							continue;
						}
						_appendItem[key] = self._appendedItemObj[key]();
					}
					
					if (p_args && cube.isArray(p_args)) {
						for (var j = 0; j< p_args.length; j++) {
							for (var key in p_args[j]) {
								if (key == "mxVirtualId" || key == primaryKey){
									continue;
								}
								_appendItem[key] =  p_args[j][key];
							}
						}
					}
				
					if (validate()) {
						// 保存修改
						for (var key in _appendItem) {
							if (key == "mxVirtualId" || key == primaryKey){
								continue;
							}
							
							if(cube.isFunction(item[key])){
								entityContainer.setValue(_appendItem, key,_appendItem[key]!=null?_appendItem[key]:item[key]());
							}else{
								entityContainer.setValue(_appendItem, key, _appendItem[key]!=null?_appendItem[key]:item[key]);
							}
							
						}
					}
				}
				
				entityContainer.onsaved = function(args) {
					if (args.p_result.resultValue.items) {
						for (var i = 0; i < items.length; i++) {
							self.items.replace(items[i], args.p_result.resultValue.items[i]);
						}
					}
				}
				
				entityContainer.save();
			} else {
				self.saveAppend(p_args, true);
			}
			
			self._isEdited(false);
			self._editedItem = null;
			self._selectedItem(null);
		}
		
		/**
		 * 将表格数据导出为 Excel 表格。
		 * 
		 * @param [p_url] 后台处理Excel导出的url。
		 * @param [p_params] 一个 JSON 对象。
		 * @param [p_checked] 一个 Boolean 值。当为true时将会向后台传递表格已选中记录的id，向后台传递的参数名为ids，多个id参数值以逗号分隔。
		 * @param [p_type] 一个 Boolean 值。是否使用post请求，当p_checked为true时默认为post，否则默认为false
		 * 
		 * @example
		 * <code language="JavaScript">
		 * dataGrid.exportExcel(serverUrl, {tableName:"uap_bm", columnNames:"BMMC,BMJL", columnCaptions:"部门名称,部门经理", filter:"BMMC='研发中心'", fileName:"部门人员表格"});
		 * </code>
		 * 
		 * @example
		 * <code language="JavaScript">
		 * dataGrid.exportExcel(serverUrl, null, true);
		 * </code>
		 */
		self.exportExcel = function(p_url, p_params, p_checked, p_type) {
			excelUtil.setBaseUrl(p_url);
			excelUtil.setParams(p_params);
			
			if (p_checked === true) {
				excelUtil.setIds(self._selectedIds());
			}
			
			if (p_type == true) {
				excelUtil.setType("post");
			}
			
			excelUtil.exportExcel();
		};
		
		// 数据加载回调函数
		function onload(args) {
			// 如果没有配置列信息，使用元数据信息
			if (cube.notEmpty(self.url()) && cube.notEmpty(self._columns) && self._columns.length == 0) {
				if (self.isAllowAppend()) {
					initAppendedItem(args.columns);
				}
				self.columns(args.columns);
				self._columns = self.columns();
				parseColumns(self._columns,null, 0, 0);
			}
			
			initColumn(args);
			
			if (self.searchBoxId()) {
				var searchBox = cube.getPageViewModelByNode($("#" + self.searchBoxId()));
				if (searchBox && args.dicts && args.dicts.length > 0) {
					searchBox.setDicts(args.dicts);
				}
			
			}
			
			self.dicts(args.dicts);
			self._dictsKeyValue(args.dictsKeyValue);
			
			if (cube.notEmpty(args.items) && !(args.items.length == 1 && args.items[0] == null)) {
				self.items(args.items);
			} else {
				self.items([]);
			}
			
			self.itemCount(args.itemCount);
			self._pageCount(parseInt((args.itemCount
					+ parseInt(self.pageSize()) - 1)
					/ parseInt(self.pageSize())));
					
			var selectedIds = self.selectedIds();
			if (selectedIds.length > 0) {
				self._selectedIds(selectedIds);
			}
			
			self.isShowLoading(false);
			var loadTime = new Date().getTime() - _startLoadTime;
			self.loadTime(loadTime > 1000 ? (loadTime / 1000 + "s") : loadTime + "ms");
			
			if (self.onLoaded != null && !cube.isObservable(self.onLoaded)) {
				self.onLoaded(self.items());
			}
		}
		
		function _error(args) {
			self.isShowLoading(false);
			
			if (self.onLoadError != null && !cube.isObservable(self.onLoadError)) {
				self.onLoadError(args);
			}
		}

		function initColumn(args) {
			var columns = self._showItemColumns();
			if (columns && columns.length > 0) {
				$.each(columns, function(index, p_column) {
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
					
					if (!p_column.editorType) {
						p_column.editorType = "TextEditor";
					}
					
					if (p_column.editorType == "DropDownEditor" || p_column.editorType == "CheckListEditor" || p_column.editorType == "ListEditor") {
						if (args.dicts && args.dicts[p_column.name]) {
							p_column.list(args.dicts[p_column.name]);
						}
					} else if(p_column.editorType == "FileEditor") {
						if (!p_column.pkVal) {
							p_column.pkVal = cube.obj(null);
						} else if(!cube.isObservable(p_field.pkVal)) {
							p_column.pkVal = cube.obj(p_field.pkVal);
						}
					}
				});
			}
			
		}
		
		// 初始化添加项
		function initAppendedItem(columns) {
			self.appendColumns = cube.clone(columns);
			var obj = {};
			var oldObj = {};
			$.each(self.appendColumns, function() {
				if (typeof(this.value) != "undefined") {
					if (cube.isObservable(this.value)) {
						obj[this.name] = this.value;
						oldObj[this.name] = this.value();
					} else {
						obj[this.name] = cube.obj(this.value);
						oldObj[this.name] =  this.value;
					}
				} else {
					obj[this.name] = cube.obj(null);
					oldObj[this.name] = null;
				}
				if (!this.editorType) {
					this.editorType = "TextEditor";
				}
				
				this.validStatus = cube.obj("");
				this.validHint = cube.obj("");
			});

			self._appendedItem = obj;
			self._appendedItemObj = oldObj;
		};

		//解析列头的层级关系以便模板中遍历输出
		var col;
		function parseColumns(p_columns, p_column, p_level, p_colspan) {
			p_level++;
			p_colspan = 0;
			for ( var i = 0; i < p_columns.length; i++) {
				p_columns[i].validStatus = cube.obj("");
				p_columns[i].validHint = cube.obj("");
				
				if (p_columns[i].editorType == "DropDownEditor" || p_columns[i].editorType == "CheckListEditor" || p_columns[i].editorType == "ListEditor" || p_columns[i].editorType == "LabelEditor") {
					if (typeof(p_columns[i].list) == "undefined") {
						p_columns[i].list = cube.array([]);
					} else if (!cube.isObservable(p_columns[i].list)) {
						p_columns[i].list = cube.array(p_columns[i].list);
					}
				}
				
				if (p_columns[i].columns && p_columns[i].columns.length > 0) {
					var o = $.extend({}, p_columns[i]);
					_pushCol(o, p_level, 0);
					p_colspan = parseColumns(p_columns[i].columns, o, p_level, p_colspan);
					if(p_column != null){
						p_column.colspan += p_colspan;
					}
				} else {
					var o = $.extend({}, p_columns[i]);
					_pushCol(o, p_level, 1);
					self._showItemColumns.push(o);
					var visible = p_columns[i].visible;
					if (cube.isObservable(visible)) {
						visible = visible();
					}
					
					if(p_column != null && ((p_columns[i].name == self.primaryKey() && self.isShowPrimaryKey()) || (p_columns[i].name != self.primaryKey() && visible !== false))){
						p_column.colspan++;
					}
					
					if(p_columns[i] != null && ((p_columns[i].name == self.primaryKey() && self.isShowPrimaryKey()) || (p_columns[i].name != self.primaryKey() && visible !== false))){
						p_colspan++;
					}
				}
			}
			return p_column ? p_column.colspan : p_colspan;
		}

		function _pushCol(o, _level, _colspan) {
			delete o.columns;
			o.colspan = _colspan;
			if (self._levelColumns().length >= _level) {
				col = self._levelColumns()[_level - 1];
				if (typeof col == "undefined") {
					col = new Array();
					self._levelColumns()[_level - 1] = col;
				}
			} else {
				col = new Array();
				self._levelColumns()[_level - 1] = col;
			}
			col.push(o);
		}
		
		// 验证数据
		function validate(type) {
			var editedItem = null;
			var columns = null;
			if (type == 1) {
				editedItem = self._editedItem;
				columns = self._showItemColumns();
			} else if(type == 0) {
				editedItem = self._appendedItem;
				columns = self.appendColumns;
			} else {
				columns = self._showItemColumns();
			}
			
			for (var i = 0; i < columns.length; i++) {
				if (columns[i].validStatus && typeof(columns[i].validStatus) == "function" && columns[i].validStatus() != "") {
					return false;
				} else {
					if (editedItem!=null) {
						for(var key in editedItem) {
							if(columns[i].name == key && columns[i].nullable == false){
								var result = validator.validate(editedItem[key](), "NOTNULL", {});
								if (!result.successful) {
									columns[i].validStatus("error");
									columns[i].validHint(result.hint);
									return false;
								} else {
									columns[i].validStatus("");
									columns[i].validHint("");
								}
								break;
							}
						}
					}
					
				}
			}
			return true;
		};
		
		 /**
		 * @ignore
		 * 用于文件上传编辑器
		 */
		self._editor_upload = function(p_name, p_fileNames) {
			self._editedItem[p_name](p_fileNames);
		};
		
		/**
		 * @ignore
		 */
		self.checkboxVisible = function(item) {
			var p_param = self.checkboxHideCondition();
			var result = true;
			if (cube.isEmpty(p_param)){
				return true;
			} else if (typeof(p_param) == "boolean"){
				return p_param;
			} else if(cube.isArray(p_param)) {
				for (var i = 0; i < p_param.length; i++) {
					result = self._checkCondition(item, p_param[i]);
					if (!result) {
						break;
					}
				}
			} else {
				result = self._checkCondition(item, p_param);
			}
			
			return !result;
		}

		/**
		 * @ignore
		 */
		self.customOperationsVisible = function(item, p_param) {
			var result = true;
			if (cube.isEmpty(p_param)){
				result = true;
			} else if (typeof(p_param) == "boolean"){
				return p_param;
			} else if(cube.isArray(p_param)) {
				for (var i = 0; i < p_param.length; i++) {
					result = self._checkCondition(item, p_param[i]);
					if (!result) {
						break;
					}
				}
			} else {
				result = self._checkCondition(item, p_param);
			}
			
			return result;
		}
		
		/**
		 *  @ignore
		 */
		self._checkCondition = function(item, p_param) {
			var result = false;
			switch (p_param.operation) {
			case "==":
				result = item[p_param.column] == p_param.value;
				break;
			case "!=":
				result = item[p_param.column] != p_param.value;
				break;
			case ">":
				result = item[p_param.column] > p_param.value;
				break;
			case "<":
				result = item[p_param.column] < p_param.value;
				break;
			case ">=":
				result = item[p_param.column] >= p_param.value;
				break;
			case "<=":
				result = item[p_param.column] <= p_param.value;
				break;
			case "in":
				if (cube.isArray(p_param.value)) {
					if (p_param.value.indexOf(item[p_param.column]) != -1) {
						result = true;
					}
				}
				break;
			default:
				break;
			}
			
			return result;
		}
		
		/**
		 * @ignore
		 */
		self.renderCell = function(p_value, element, column, item) {
			if (cube.isObservable(p_value)) {
				p_value = p_value();
			}
			
			var result = null;
			var renderCellResult = false;
			var dictValue = "";
			var _dictsKeyValue = self._dictsKeyValue();
			if (column.renderCell) {
				if (cube.notEmpty(_dictsKeyValue)) {
					 if (cube.notEmpty(_dictsKeyValue[column.name])) {
						 dictValue = _dictsKeyValue[column.name][p_value];
						 if (typeof(dictValue) == "undefined") {
							 dictValue = "";
						 }
					 }
				}
				result =  column.renderCell(p_value, element, dictValue, item, column);
				
				if (result == null) {
					result = cube.notEmpty(dictValue) ? dictValue : p_value;
				} else {
					renderCellResult = true;
				}
			} else if (cube.notEmpty(_dictsKeyValue) && cube.notEmpty(_dictsKeyValue[column.name])) {
				 result = _dictsKeyValue[column.name][p_value];
				 if (typeof(result) == "undefined") {
					 result = p_value;
				 }
			} else if (typeof(column.editorType) != "undefined" && column.editorType == "DateTimeEditor") {
				result = self._parseDate(p_value, column.format);
			} else {
				result = p_value;
				var $element = $(element);
				
				if (typeof(column.displayLength) != "undefined" && cube.notEmpty(result)) {
					result = String(result);
					if (result.length > column.displayLength) {
						result = result.substring(0, column.displayLength) + "...";
					}
				} else if (typeof(column.displayRow) != "undefined" && cube.notEmpty(result)) {
					result = String(result);
					var width = $element.width();
					var fontSize = parseInt($element.css("fontSize"));
					var length = parseInt(width/fontSize*column.displayRow) - 3;
					if (result.length > length) {
						result = result.substring(0, length) + "...";
					}
				}
				
				if (typeof(column.displayRow) != "undefined" || typeof(column.displayLength) != "undefined") {
					$element.css("white-space", "normal");
					$element.css("word-break", "break-all");
				}
			}
			
			var trBackground = self.trBackground();
			if (trBackground != null) {
				if(column.name == trBackground.column) {
					var res = false;
					switch (trBackground.operation) {
					case "==":
						res = p_value == trBackground.value;
						break;
					case "!=":
						res = p_value != trBackground.value;
						break;
					case ">":
						res = p_value > trBackground.value;
						break;
					case "<":
						res = p_value < trBackground.value;
						break;
					case ">=":
						res = p_value >= trBackground.value;
						break;
					case "<=":
						res = p_value <= trBackground.value;
						break;
					case "in":
						if (cube.isArray(trBackground.value)) {
							if (trBackground.value.indexOf(p_value) != -1) {
								res = true;
							}
						}
						break;
					default:
						break;
					}
					
					if (res) {
						$(element).parent().css("background", trBackground.background);
					}
				}
			}
			
			if (result) {
				result = String(result);
				if (self.scriptProcessing() == "clean" || renderCellResult) {
					result = cube.cleanScript(result);
				} else {
					result = cube.escapeString(result, typeof(column.isAllowAutowrap) != "undefined" ? column.isAllowAutowrap : self.isAllowAutowrap());
				}
			}
			
			return result;
		}
		
		/**
		 * @ignore
		 */
		self.mouseenter = function(id_value, p_value, column, element) {
			if (typeof(column.onMouseenter) == "function") {
				column.onMouseenter(id_value, p_value);
			}
			
			p_value = String(p_value);
			
			var dictValue = "";
			var _dictsKeyValue = self._dictsKeyValue();
			if (cube.notEmpty(_dictsKeyValue)) {
				 if (cube.notEmpty(_dictsKeyValue[column.name])) {
					 dictValue = _dictsKeyValue[column.name][p_value];
					 if (typeof(dictValue) == "undefined") {
						 dictValue = "";
					 }
				 }
			}
			
			p_value = cube.notEmpty(dictValue) ? dictValue : p_value;
			
			if (typeof(column.editorType) != "undefined" && column.editorType == "DateTimeEditor") {
				p_value = self._parseDate(p_value, column.format);
			}
			
			var o_value = p_value;
			if (p_value) {
				p_value = String(p_value);
				if (self.scriptProcessing() == "clean") {
					p_value = cube.cleanScript(p_value);
					o_value = p_value;
				} else {
					p_value = cube.escapeString(p_value, typeof(column.isAllowAutowrap) != "undefined" ? column.isAllowAutowrap : self.isAllowAutowrap());
				}
			}
			
			var noAutowrap = (self.isAllowAutowrap() == false || column.isAllowAutowrap == false) && element.scrollWidth > element.clientWidth;
			if ((typeof(column.displayLength) != "undefined" 
				|| typeof(column.displayRow) != "undefined"
				|| noAutowrap) 
				&& cube.notEmpty(p_value)) {
				
				if (self.isShowTips()) {
					var length = column.displayLength;
					
					if (typeof(column.displayRow) != "undefined") {
						var width = $(element).width();
						var fontSize = parseInt($(element).css("fontSize"));
						length = parseInt(width/fontSize*column.displayRow) - 3;
					}
					
					if (p_value.length > length || noAutowrap) {
						self.showCellPopDialog(true);
						
						cube.showPopDialog(element, {
							popoverDirection: "top",
							content: p_value.replace(/\n/g, "<br/>"),
							isShowDialog: self.showCellPopDialog
						});
					}
				} else {
					$(element).attr("title", o_value);
				}
			}
		}
		
		/**
		 * @ignore
		 */
		self.mouseleave = function(id_value, p_value, column) {
			if (typeof(column.onMouseleave) == "function") {
				column.onMouseleave(id_value, p_value);
			}
			
			self.showCellPopDialog(false);
		}
		
		 /**
		 * @ignore
		 * 解析日期格式
		 */
		self._parseDate = function(_value, _format) {
			if (cube.notEmpty(_value)) {
				if (!cube.isString(_value)) {
					_value = String(_value);
				}
				
				var dt = new Date(_value.replace(/-/g,"/"));
				
				if (checkUtil.isDigit(_value)) {
					if (cube.isEmpty(_format)) {
						_format = "yyyy-MM-dd HH:mm:ss";
					}
					
					if(_value.length == 10){
						_value = _value + "000";
					}
					
					if (_value.length == 13) {
						dt = new Date(parseInt(_value));
					}
				}
				
				if (cube.isEmpty(_format)) {
					_format = _value;
				} else {
					_format = _format.replace(/yyyy/g, dt.getFullYear());
					_format = _format.replace(/MM/g, numberFormat(dt.getMonth() + 1, "00"));
					_format = _format.replace(/dd/g, numberFormat(dt.getDate(), "00"));
					_format = _format.replace(/HH/g, numberFormat(dt.getHours(), "00"));
					_format = _format.replace(/mm/g, numberFormat(dt.getMinutes(), "00"));
					_format = _format.replace(/ss/g, numberFormat(dt.getSeconds(), "00"));
				}
			} else {
				_format = _value;
			}
			
			return _format;
		}
		
		function numberFormat(p_value, p_formatString) {
			if (p_value == null) {
				return "";
			}
			if (typeof(p_formatString) == "undefined") {
				return p_value + "";
			}
			if (typeof(p_formatString) == "number") {
				return p_value + "";
			}
			var string = p_value + "";
			if (p_formatString != null && p_formatString != "") {
				var stringParts = string.split('.');
				var formatParts = p_formatString.split('.');
		
				if (stringParts[0].length < formatParts[0].length) {
					stringParts[0] =
						formatParts[0].substring(0, formatParts[0].length - stringParts[0].length) +
						stringParts[0];
				}
				if (formatParts.length == 1) {
					return stringParts[0];
				} else {
					if (stringParts.length > 1) {
						while (stringParts[1].length < formatParts[1].length) {
							stringParts[1] += "0";
						}
					} else {
						stringParts[1] = formatParts[1];
					}
					
					var value = new Number(stringParts[0] + "." + stringParts[1]);
					return value.toFixed(formatParts[1].length);
				}
			} else {
				return string;
			}
		};
		
		
		/**
		 * @ignore
		 */
		self.appendCustomClick = function(viewModel) {
			if (cube.isFunction(viewModel.click)) {
				var _appendItem = {};
				for (var key in self._appendedItem) {
					if (key == "mxVirtualId" || key == self.primaryKey()){
						continue;
					}
					_appendItem[key] = self._appendedItem[key]();
				}
				viewModel.click(_appendItem);
			}
		}
		
		/**
		 * @ignore
		 */
	  	self.onload = function(node) {
	  		var $node = $(node);
	  		var $tableBody = $node.find(".tableBody");
	  		
	  		var watermarkOptions = self.watermarkOptions();
			if(cube.notEmpty(watermarkOptions) && cube.notEmpty(watermarkOptions.text)){
				watermarkOptions = $.extend(self._watermarkOptionsDefaultValue(), watermarkOptions);
				
				watermarkOptions.indent = (parseInt(watermarkOptions.width) + parseInt(watermarkOptions.xSpace))/2;
				self.watermarkOptions(watermarkOptions);
				
				var rows = parseInt(($tableBody.height() - watermarkOptions.y)/(parseInt(watermarkOptions.height) + parseInt(watermarkOptions.ySpace))) + 1;
				for (var i = 0; i < rows; i++) {
					self.watermarkRows.push({
						y: watermarkOptions.y + (parseInt(watermarkOptions.height) + parseInt(watermarkOptions.ySpace)) * i
					});
				}

				var cols = parseInt(($tableBody.width() - watermarkOptions.x)/(parseInt(watermarkOptions.width) + parseInt(watermarkOptions.xSpace))) + 1;
				for (var i = 0; i < cols; i++) {
					self.watermarkCols.push({
						x: watermarkOptions.x + (parseInt(watermarkOptions.width) + parseInt(watermarkOptions.xSpace)) * i
					});
				}
			}
			
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
	  	}
		
		cube.endViewModel(self, params);
	}

	//释放资源，包括compoted/subscrib资源等。
 	ViewModel.prototype.dispose = function() {
 		this.pageSizeSub.dispose();
 		this.argsSub.dispose();
		this._selectedIdsSub.dispose();
		this._selectedAllSub.dispose();
		this.actionsSub.dispose();
		this.dictsSub.dispose();
		this.selectedIdsSub.dispose();
 	}

	return ViewModel;
});