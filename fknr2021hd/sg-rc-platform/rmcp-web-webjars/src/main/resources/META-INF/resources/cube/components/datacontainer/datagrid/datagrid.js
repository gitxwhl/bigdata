define(['entityContainer', 'JSONUtil', "PrintUtil", "Validator", "CheckUtil", "ExcelUtil", "PinyinUtil"], 
		function(entityContainerClass, jsonUtil, printUtil, validator, checkUtil, excelUtil, pinyinUtil) {
	
	/**
	 * 基本表格
	 * 主键（primaryKey）是惟一的和后台主键一致。如果配置url属性，则items不需要配置。
	 * @example
	 * <code language="html">
	 *		<datagrid id="datagrid" params="
	 *						style: style,
	 *						items: items,
	 *						columns: columns,
	 *						isShowRowNumber: isShowRowNumber,
	 *						isShowCheckBox: isShowCheckBox,
	 *						isShowBorder: isShowBorder,
	 *						isShowStripe: isShowStripe,
	 *						isShowHover: isShowHover,
	 *						isShowCondense: isShowCondense,
	 *						isAllowEdit: isAllowEdit,
	 *						isAllowDelete: isAllowDelete,
	 *						isAllowOperations: isAllowOperations,
	 *						isAllowShift: isAllowShift,
	 *						isAllowAppend: isAllowAppend,
	 *						isAllowSort: isAllowSort,
	 *						pageSize: pageSize,
	 *						//url: url,
	 *						primaryKey: primaryKey
	 *						"></datagrid>
	 * </code>
	 * <code language="JavaScript">
	 *		define([], function() {
	 *			var PageViewModel = function(params) {
	 *				var self = this;
	 * 
	 *				self.style = '';
	 *				self.items = [
	 *								{
	 *									"depid" : "1",
	 *									"depname" : "cs",
	 *									"pardepid" : "cs",
	 *									"departdesc" : "cs",
	 *									"powerconsumption" : null
	 *								},
	 *								{
	 *									"depid" : "2",
	 *									"depname" : "测试",
	 *									"pardepid" : null,
	 *									"departdesc" : null,
	 *									"powerconsumption" : null
	 *								},
	 *								{
	 *									"depid" : "3",
	 *									"depname" : "测试",
	 *									"pardepid" : null,
	 *									"departdesc" : "测试",
	 *									"powerconsumption" : null
	 *								},
	 *								{
	 *									"depid" : "4",
	 *									"depname" : "新建文本文档.txt",
	 *									"pardepid" : null,
	 *									"departdesc" : "测试",
	 *									"powerconsumption" : null
	 *								},
	 *								{
	 *									"depid" : "5",
	 *									"depname" : "新建文本文档.txt",
	 *									"pardepid" : null,
	 *									"departdesc" : "测试测试测试",
	 *									"powerconsumption" : null
	 *								} 
	 *								];
	 *				self.columns = [
	 *										{	name: "depid", width:"90px",readOnly:true, caption: "部门编号" , editorType: "TextEditor"	},
	 *										{	name: "depname", width:"90px", caption: "部门姓名" , editorType: "TextEditor"	},
	 *										{	name: "pardepid", width:"90px", caption: "上级部门" , editorType: "TextEditor"	},
	 *												{	name: "departdesc",caption: "部门描述" , editorType: "TextEditor"	},
	 *												{	name: "powerconsumption",width:"80px", caption: "用电量" , editorType: "TextEditor"	}
	 *										];
	 *				self.isShowRowNumber = true;
	 *				self.isShowCheckBox = true;
	 *				self.isShowBorder = true;
	 *				self.isShowStripe = true;
	 *				self.isShowHover = false;
	 *				self.isShowCondense = false;
	 *				self.isAllowEdit = true;
	 *				self.isAllowDelete = true;
	 *				self.isAllowOperations = true;
	 *				self.isAllowShift = false;
	 *				self.isAllowSort = true;
	 *				self.isAllowAppend = true;
	 *				self.pageSize = 10;
	 *				//self.url = '';
	 *				self.primaryKey = 'depid';
	 * 
	 *				cube.endViewModel(self, params);
	 *			};
	 *			return PageViewModel;
	 *		});
	 * </code> 
	 */
	//依赖组件注册
	cube.importComponent("editor.commoneditor");
	cube.importComponent("editor.datetimeeditor");
	cube.importComponent("editor.dropdowneditor");
	cube.importComponent("datacontainer.pagenavibar");
	cube.importComponent("datacontainer.exceltmpl");
	cube.importComponent("controls.loading");
	
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
		 * 设置表格最大高度，当设置该属性时表格的高度会自适应，超出设定高度会出现滚动条。
		 * @default
		 *		auto
		 */
		self.maxHeight = 'auto';
		
		/**
		 * 编号列宽度
		 * @default 40px
		 */
		self.rowNumberWidth = "40px";
		
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

		/**
		 * 表格内容
		 * 格式如下：
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
		 * 表头配置
		 * 格式如下：
		 * <code language="JavaScript"> 
		 * [{
		 *		name : "depid",
		 *		width : "90px",
		 *		readOnly : true,
		 *		caption : "部门编号",
		 *		editorType : "TextEditor"
		 *	}, {
		 *		name : "depname",
		 *		width : "90px",
		 *		caption : "部门名称",
		 *		editorType : "TextEditor",
		 *		//可通过isAllowAutowrap设置单元格是否换行显示，该设置覆盖表格全局设置
		 *		isAllowAutowrap: true,
		 *		//可通过displayLength设置单元格显示字符长度
		 *		displayLength : 20,
		 *		//可通过displayRow设置单元格显示行数
		 *		//displayRow : 2,
		 *		//可通过align设置单元格居中方法，取值：main、center、right
		 *		align : "center",
		 *		//可通过renderCell自定义单元格样式，cellValue为单元格值，element为td的dom对象，dictValue为对应数据字典的值，item为行数据，column为列数据
		 *		//renderCell: function(cellValue, element, dictValue, item, column){
		 *		//	return "<span style='color:red;'>" + cellValue + "</span>";
		 *		//},
		 *		//可通过onClick设置该列单元格点击事件，pkValue：行记录主键值，cellValue：单元格值，column：列信息
		 *		//onClick: function(pkValue, cellValue, column){
		 *		//	
		 *		//},
		 *		//可通过onDblClick设置该列单元格双击事件，pkValue：行记录主键值，cellValue：单元格值，column：列信息
		 *		//onDblClick: function(pkValue, cellValue, column){
		 *		//	
		 *		//},
		 *		//可通过onMouseenter设置该列单元格鼠标进入事件，pkValue：行记录主键值，cellValue：单元格值
		 *		//onMouseenter: function(pkValue, cellValue){
		 *		//	
		 *		//},
		 *		//可通过onMouseleave设置该列单元格鼠标离开事件，pkValue：行记录主键值，cellValue：单元格值
		 *		//onMouseleave: function(pkValue, cellValue){
		 *		//	
		 *		//}
		 *	}]
		 * </code> 
		 */
		self.columns = [];
		
		/**
		 * 子表后台请求地址，当该值为空时将使用url属性值。
		 * 
		 * @default 空
		 */
		self.subUrl = "";
		
		/**
		 * 子表列信息，一个数组，格式参考columns属性，当该值不为null时在第一列将显示展开子表的图标，点击时显示子表
		 * @default null
		 */
		self.subColumns = null;
		
		/**
		 * 自定义操作按钮
		 * <code language="JavaScript"> 
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
		 * </code> 
		 */
		self.customOperations = [];
		
		/**
		 * 列默认宽度
		 * @default auto
		 */
		self.columnDefaultWidth = "auto";
		
		/**
		 * 标示列，如果设置该属性，则显示在表格的第一列。
		 * <code language="JavaScript"> 
		 * {
		 *		caption: "",
		 *		width : "90px",
		 *		value : "",//如果设置renderCell，则其返回值覆盖该设置
		 *		//可通过renderCell自定义单元格样式，item为行数据，element为td的dom对象
		 *		renderCell: function(item, element) {
		 *			var color = "red";
		 *			if(item["state"] == 0){
		 *				color = "green";
		 *			}
		 *			return "<span style='color:"+color+";'>●</span>";
		 *		}
		 * }
		 * </code> 
		 * @default null
		 */
		self.markColumn = null;
		
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
		 * 
		 * 其中column为列对应name，operation为比较运算符或"in"，value为具体值(操作符为in时该值为数组)
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
		 * 追加列信息
		 * @ignore
		 */
		self.appendColumns = null;
		
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
		 * 总记录数
		 * 
		 * @default 0
		 */
		self.itemCount = 0;
		
		/**
		 * 分页信息居左（总页数、总记录数）
		 * 
		 *  @default false
		 */
		self.pageInfoToLeft = false;
		
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
		 *  @default false
		 */
		self.isShowLoadTime = false;
		
		/**
		 * 分页显示的加载时间是否自动淡出，一个数字（毫秒），当值为0时不淡出，否则按设置时间淡出
		 * @default 0
		 * 
		 */
		self.loadTimeFadeout = 0;
		
		/**
		 * 是否失去焦点时跳转页面，默认输入即跳转
		 * @default
		 *		false
		 */
		self.blurGoto = false;
		
		/**
		 * 是否显示编辑按钮文字表述
		 * @default
		 *		false
		 */
		self.isShowEditBtnText = false;
		
		/**
		 * 分页可见页数
		 * @default
		 *		5
		 */
		self.pageVisibleCount = 5;

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
		 * @ignore
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
		 * @ignore
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
		 * @ignore
		 * 内部属性
		 * 
		 * @default false
		 */
		self._isEdited = false;
		
		/**
		 * 是否整个表格单元格显示为编辑状态
		 * 
		 * @default false
		 */
		self.isAllEdited = false;
		
		/**
		 * 是否显示主键列
		 * 
		 * @default false
		 */
		self.isShowPrimaryKey = false;
		
		/**
		 * 是否显示编号
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
		 * 是否允许调整列宽
		 * 
		 * @default true
		 */
		self.isAllowAdjustColumn = true;
		
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
		 * 当单元格数据被隐藏时，鼠标经过是否显示完整数据提示框
		 *  @default true
		 */
		self.isShowTips = true;
		
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
		 *  @default null
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
		 * 数据字典，如果后台返回数据字典的话将会覆盖该设置，格式如下：
		 * @example
		 *  <code language="JavaScript">
		 * [{"name":"sex","values":[{"text":"男","value":"1"},{"text":"女","value":"0"}]}]
		 * </code>
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
		 * @ignore
		 * 数据字典key-value暂存
		 */
		self._dictsKeyValue = null;
		
		/**
		 *  @ignore
		 */
		self._bodyHeight = "auto";
		
		/**
		 *  @ignore
		 */
		self._bodyMaxHeight = "auto";
		
		/**
		 * @ignore
		 */
		self._headerOperationsColumnWidth = null;
		
		/**
		 * @ignore
		 */
		self._hasVScroll = false;
		
		/**
		 * 是否显示临时列，用于调整列宽
		 * @ignore
		 */
		self.isShowTempColumn = false;
		
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
		 */
		self.$tableBody = null;
		
		/**
		 * @ignore
		 */
		self.$node = null;
		
		/**
		 * @ignore
		 */
		self.$tableHeader = null;
		
		/**
		 * @ignore
		 */
		self.showCellPopDialog = cube.obj(false);
		
		/**
		 * 当isAllEdited为true时记录编辑校验状态
		 * @ignore
		 */
		self._editorValid = [];
		
		/**
		 * @ignore
		 */
		self.subGridId = null;
		
		/**
		 * @ignore
		 */
		self.loadTime = 0;
		
		/**
		 * 选中记录回调函数，接收参数为一个对象：已选中记录数据
		 * 
		 * @param item 已选中记录数据
		 */
		self.onSelectedItem = null;
		
		/**
		 * 勾选记录回调函数，接收参数为一个数组对象：已勾选记录主键ID数组
		 * 
		 * @param ids 已勾选记录主键ID数组
		 */
		self.onSelectedIds = null;
		
		/**
		 * 勾选记录回调函数，接收参数为一个数组对象：已勾选记录数组
		 * @param items 已勾选记录数组
		 */
		self.onSelectedItems = null;
		
		/**
		 * 记录双击事件，接收参数为一个对象：双击记录数据
		 * @param item 行记录数据
		 */
		self.onDblClick = null;
		
		/**
		 * 所有数据保存之前回调事件，接收参数为一个对象
		 * @param args 
		 * 例：
		 * function(args){
		 *		//args.data为记录数据
		 *		//args.cancel赋值为true是取消保存
		 * }
		 */
		self.onSaving = null;
		
		/**
		 * 所有数据保存之后回调事件，接收参数为一个对象：保存记录数据
		 * @param item 保存数据
		 */
		self.onSaved = null;
		
		/**
		 * 行数据保存之前回调事件，
		 * @param args 
		 * 例：
		 * function(args){
		 *		//args.data为行数据
		 *		//args.cancel赋值为true是取消保存
		 * }
		 */
		self.onItemSaving = null;
		
		/**
		 * 行数据保存之后回调事件，接收参数为一个对象：保存记录数据
		 * @param item 保存记录数据
		 */
		self.onItemSaved = null;
		
		/**
		 * 行数据编辑之前回调事件
		 * @param item 行记录数据
		 * @param index 行索引值
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
		 * @param item 删除的行数据，通过复选框删除时为id数组
		 * @param p_result 后端返回数据
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
		
		var _originalOperationsColumnWidth = null;
		var _startLoadTime = 0;

		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			self._appendedItem = {};
			
			var height = self.height();
			var maxHeight = self.maxHeight();
			if( height != "auto" && height != null && height.indexOf("%") == -1) {
				self._bodyHeight((parseInt(height) - 41) + "px");
			}
			
			if( height == "auto" && maxHeight != "auto") {
				self._bodyMaxHeight((parseInt(maxHeight) - 41) + "px");
			}
			
			self._headerOperationsColumnWidth(self.operationsColumnWidth());
			if (self.isAllowOperations()) {
				_originalOperationsColumnWidth = self.operationsColumnWidth();
			} else {
				_originalOperationsColumnWidth = 0;
			}
			
			
			self._columns = self.columns();

			self.pageSizeSub = cube.subscribe(self.pageSize, function(){ self.load(); });
			self.argsSub = cube.subscribe(self.args, function() { 
				self.pageIndex.silentUpdate(1);
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
					self.initColumn({dicts: dicts});
				}
			});
			
			self.itemsChange();

			entityContainer.primaryKey = self.primaryKey();
			entityContainer.baseUrl = self.url();
			entityContainer.actions = self.actions();
			entityContainer.type = "grid";
			entityContainer.timeout = self.timeout();
			entityContainer.loadMeta = self.loadMeta();
			entityContainer.data = self.items();
			entityContainer.dicts = self.dicts();
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
				self.initColumn();
			}
			
			if (self.isAllowAppend()) {
				initAppendedItem();
			}
			
			if (self.isAllEdited()) {
				self._initItems(self.items());
				
				self.itemsSub = cube.subscribe(self.items, function(items){ 
					self._initItems(items);
					self.itemsChange();
				});
			} else{
				if (cube.isEmpty(self.url()) || !self.autoLoad()) {
					self.itemsSub = cube.subscribe(self.items, function(items){ 
						self.itemCount(items.length);
						self._pageCount(parseInt((items.length + parseInt(self.pageSize()) - 1) / parseInt(self.pageSize())));
						setTimeout(function(){
							self.itemsChange();
						}, 500);
					});
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self._initItems = function(p_items) {
			if (cube.notEmpty(p_items) && p_items.length > 0) {
				var columns = self.columns();
				var _editorValid = self._editorValid();
				if (columns && columns.length > 0) {
					for (var i = 0; i < p_items.length; i++) {
						_editorValid[i] = {};
						
						$.each(columns, function(index, p_column) {
							var value = p_items[i][p_column.name];
							if (typeof(value) == "undefined") {
								p_items[i][p_column.name] = cube.obj();
							} else if (!cube.isObservable(value)) {
								p_items[i][p_column.name] = cube.obj(value);
							}
							
							var valid = {
								validStatus: cube.obj(""),
								validHint: cube.obj("")
							};
							
							_editorValid[i][p_column.name] = valid;
						});
					}
				}
				self._editorValid(_editorValid);
			} else {
				self._editorValid([]);
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
				if (!self.validate(1)) {
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
				
				self.replaceItem(self._editedItemIndex, self._editedItemObj);
				
				
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
		 *  @ignore
		 */
		self.replaceItem = function(p_editedItemIndex, p_obj) {
			if (cube.isNumber(p_editedItemIndex)) {
				self.items.replace(self.items()[self._editedItemIndex], p_obj);
			} else {
				self.items.replace(p_editedItemIndex, p_obj);
			}
		}

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
		 * 删除行记录
		 * 
		 *  @param p_item 可选，行数据，一个json对象。
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
				if (cube.isObservable(primaryKeyValue)) {
					primaryKeyValue = primaryKeyValue();
				}
				
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
				self.itemsChange();
			});
		};
		
		/**
		 * 删除所选行
		 */
		self.delSelectedItem = function() {
			cube.indicate("confirm", cube.msg("CONFIRM_DELETE"), function() {
				var item = self._selectedItem();
				if (item == null) {
					return;
				}
				
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
				self.itemsChange();
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
				self.itemsChange();
			});
		};

		/**
		 * 编辑项
		 * @ignore
		 */
		self.editItem = function(item, index) {
			if (self._isEdited() && self._selectedItem() != item) {
				if (!self.validate(1)) {
					return;
				}
				
				for (var key in self._editedItem) {
					if (key == "mxVirtualId"){
						continue;
					}
					entityContainer.setValue(self._editedItemObj, key, self._editedItem[key]());
				}
				
				self.replaceItem(self._editedItemIndex, self._editedItemObj);
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
			
			var columns = self.columns();
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
		self.saveItem = function(p_item, event) {
			if (self.validate(1)) {
				
				if (self.autoSave()) {
					// 保存修改
					if (self.isAllEdited()) {
						for (var key in p_item) {
							if (key == "mxVirtualId"){
								continue;
							}
							entityContainer.setValue(p_item, key, cube.isObservable(p_item[key])?p_item[key]():p_item[key]);
						}
					} else {
						for (var key in self._editedItem) {
							if (key == "mxVirtualId"){
								continue;
							}
							entityContainer.setValue(self._editedItemObj, key, self._editedItem[key]());
						}
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
							if (!self.isAllEdited()) {
								self.replaceItem(self._editedItemIndex, args.result.newData[0]);
							}
							
							if (self.onItemSaved != null && !cube.isObservable(self.onItemSaved)) {
								self.onItemSaved(args.result.newData[0]);
							}
						} else {
							if (!self.isAllEdited()) {
								self.replaceItem(self._editedItemIndex, self._editedItemObj);
							}
							
							if (self.onItemSaved != null && !cube.isObservable(self.onItemSaved)) {
								self.onItemSaved(self._editedItemObj);
							}
						}
					}
					
					entityContainer.save();
				} else {
					var obj = {};
					if (self.isAllEdited()) {
						for (var key in p_item) {
							if (key == "mxVirtualId"){
								continue;
							}
							
							obj[key] = cube.isObservable(p_item[key])?p_item[key]():p_item[key];
						}
					} else {
						for (var key in self._editedItem) {
							if (key == "mxVirtualId"){
								continue;
							}
							
							obj[key] = self._editedItem[key]();
						}
					}
					
					if (self.onItemSaving != null && !cube.isObservable(self.onItemSaving)) {
						var args = {cancel: false, data : obj};
						self.onItemSaving(args);
						if (args.cancel) {
							return;
						}
					}
					
					if (!self.isAllEdited()) {
						self.replaceItem(self._editedItemIndex, obj);
					}
					
					if (self.onItemSaved != null && !cube.isObservable(self.onItemSaved)) {
						self.onItemSaved(obj);
					}
				}
	
				self._isEdited(false);
				self._editedItem = null;
				self._selectedItem(null);
			}
		}
		
		var _appendItem = null;
		/**
		 * 保存新增
		 * @ignore
		 */
		self.saveAppend = function(p_args, p_save) {
			if (self.validate(0)) {
				
				if (_appendItem == null) {
					_appendItem = entityContainer.create();
				}
				
				if (self.isAllEdited()) {
					var _editorValid = self._editorValid();
					var i = _editorValid.length;
					_editorValid[i] = {};
				}
				
				for (var key in self._appendedItem) {
					if (key == "mxVirtualId" || key == self.primaryKey()){
						continue;
					}
					_appendItem[key] = self._appendedItem[key]();
					
					if (self.isAllEdited()) {
						var valid = {
							validStatus: cube.obj(""),
							validHint: cube.obj("")
						};
							
						_editorValid[i][key] = valid;
					}
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
						var columns = self.columns();
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
					var columns = self.columns();
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
		}

		/**
		 * 取消保存
		 */
		self.cancelSaveItem = function() {
			self._isEdited(false);
			self._editedItem = null;
			self._selectedItem(null);
			
			var columns = self.columns();
			for (var i = 0; i < columns.length; i++) {
				columns[i].validStatus("");
			}
		}

		/**
		 *  添加数据，如果主键通过服务器生成，则需要采用数据实体组件！
		 *  @param p_item 可选，行数据，一个json对象。
		 */
		self.appendItem = function(p_item) {
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
				if (cube.notEmpty(p_item)) {
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
				
				self.items.push(obj);
				self.itemsChange();
			}
		}

		/**
		 * @ignore
		 */
		self.sortType = cube.obj(-1); // 1：当前为升序；-1：当前为降序
		
		/**
		 * @ignore
		 */
		self.sortName = cube.obj("");
		
		/**
		 * 按照某一列进行排序
		 * @param
		 * p_name 排序列名
		 * p_sortType 排序方式，true为升序， 
		 * 列：cube.getPageViewModelByNode($('#performancedata')).sortGrid('age',true);
		 */
		self.sortGrid = function(p_name,p_sortType) {
			var sortName = null;
			if (cube.notEmpty(p_name)) {
				 if (p_name.name != undefined) {
					 sortName = p_name.name;
				 } else {
					 sortName = p_name;
				 }				
			} else {
				sortName = self.sortName();
			}
			
			self.sortName(sortName);
			self.items.sort(function(left, right) {
				var leftVal = cube.isObservable(left[sortName])?left[sortName]():left[sortName];
				var rightVal = cube.isObservable(right[sortName])?right[sortName]():right[sortName];
				
				if (leftVal == null && rightVal == null) {
					return 0;
				} else if (leftVal==null && rightVal != null) {
					if (p_sortType == true) { 
						return 1;
					} else if (p_sortType == false) {
						return -1;
					} else {
						return self.sortType() == 1 ? -1 : 1;	
					}
				} else if (leftVal!=null && rightVal== null) {
					if (p_sortType == true) { 
						return -1;
					} else if (p_sortType == false) {
						return 1;
					} else {
						return self.sortType() == 1 ? 1 : -1;
					}
				}
				
				if (!isNaN(leftVal) && !isNaN(rightVal)) {
					leftVal = Number(leftVal);
					rightVal = Number(rightVal);
				} else {
					leftVal = pinyinUtil.convertPinyin(leftVal);
					rightVal = pinyinUtil.convertPinyin(rightVal);
				}
										
				var rel = null;
				if (p_sortType == true) { 	
					rel = leftVal == rightVal ? 0 : (leftVal < rightVal
							? -1
							: 1);
					return rel;
				} else if (p_sortType == false) {
					rel = leftVal == rightVal ? 0 : (leftVal < rightVal
							? 1
							: -1);
					return rel;
				} else {
					rel = leftVal == rightVal ? 0 : (leftVal < rightVal
							? self.sortType()
							: self.sortType() * -1);
					return rel;
				}
			});
			
			if (p_sortType == true) { 
				self.sortType(1);
			} else if (p_sortType == false) {
				self.sortType(-1);
			} else {
				self.sortType(self.sortType() * -1);
			}
		}

		var _canMove = false, _startMove = false, _mouseX = 0, _srcX = 0, _element, _index, _$td, _columnIndex;
		/**
		 * @ignore
		 */
		self.mousedown = function(element, data, event, columnIndex) {
			if (!self.isAllowAdjustColumn()) {
				return;
			}
			
			if (!_canMove) {
				return;
			}
			_startMove = true;
			_mouseX = event.clientX;
			_srcX = $(element).width();
			_element = $(element);
			
			_index = element.cellIndex;
			_columnIndex = columnIndex;
			_$td = self.$tableBody.find("table>tbody>tr>td:eq(" + _index + ")");
		}

		/**
		 * @ignore
		 */
		self.mouseup = function() {
			if (!self.isAllowAdjustColumn()) {
				return;
			}
			
			_startMove = false;
			self.itemsChange();
		}

		/**
		 * @ignore
		 */
		var oldHeadTdWidth = null;
		var oldBodyTdWidth = null;
		self.mousemove = function(element, event) {
			if (!self.isAllowAdjustColumn()) {
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
				var width = _srcX + offsetX;
				if (width < 20) {
					width = 20;
				}
				
				_element.width(width + "px");
				if (_columnIndex != -1) {
					var _column = self.columns()[_columnIndex];
					var _width = _column.width();
					
					if (!self.isShowTempColumn() && autoPercentCount == 0) {
						self.isShowTempColumn(true);
					}
					
					if (_width == "auto" || (cube.isString(_width) && _width.indexOf("%") != -1)) {
						autoPercentCount--;
					}
					
					
					_column.width(width + "px");
					
				}
			}
		}

		/**
		 * @ignore
		 */
		self.onCurrentPageIndexChanged = function(p_pageIndex) {
			self._selectedAll(false);
			self.sortName("");
			
			if (!self._indexNotLoad()) {
				self.load() 
			}
		}
		
		/**
		 * @ignore
		 */
		self.itemsChange = function() {
			if (self.$tableBody[0] && (self.$tableBody[0].scrollHeight > self.$tableBody[0].clientHeight)) {
				self._headerOperationsColumnWidth((parseInt(_originalOperationsColumnWidth) + (self.isAllowOperations() ? 17: 6)) + "px");
				self._hasVScroll(true);
			} else {
				self._headerOperationsColumnWidth(self.operationsColumnWidth());
				self._hasVScroll(false);
			}
		}

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
				data =  p_data;
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
			
			var page = {};
			if (self.isAllowPaging()) {
				page = {pageIndex:parseInt(self.pageIndex()), pageSize:parseInt(self.pageSize())};
			}
			
			args.dataParams = $.extend(page, args.dataParams);
			
			entityContainer.load(args);
		}
		
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
			printUtil.print(self.items, self.columns, self.dicts, "datagrid");
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
					
					var pkValue = cube.isObservable(items[i][primaryKey])?items[i][primaryKey]():items[i][primaryKey];
					if (!pkValue) {
						_appendItem = entityContainer.create();
					} else {
						_appendItem = {};
						_appendItem[primaryKey] = pkValue;
						if(self._editedItem && self._editedItem[primaryKey] && item[primaryKey] == self._editedItem[primaryKey]()){
							item = self._editedItem;
						}
					}
					
					if (self.isAllEdited()) {
						for (var key in item) {
							if (key == "mxVirtualId" || key == primaryKey){
								continue;
							}
							_appendItem[key] = cube.isObservable(item[key])?item[key]():item[key];
						}
					} else {
						//取列默认值
						if (!cube.isObservable(self._appendedItemObj)) {
							for (var key in self._appendedItemObj) {
								if (key == "mxVirtualId" || key == primaryKey){
									continue;
								}
								_appendItem[key] = self._appendedItemObj[key];
							}
						} else {
							for (var key in item) {
								if (key == "mxVirtualId" || key == primaryKey){
									continue;
								}
								_appendItem[key] = cube.isObservable(item[key])?item[key]():item[key];
							}
						}
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
				
					if (self.validate(_appendItem, i)) {
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
					} else {
						return;
					}
				}
				
				if (self.onSaving != null && !cube.isObservable(self.onSaving)) {
					var data = [];
					for (var key in entityContainer._changedItems) {
						data.push(entityContainer._changedItems[key]);
					}
					
					var args = {cancel: false, data : data};
					self.onSaving(args);
					if (args.cancel) {
						return;
					}
				}
				
				entityContainer.onsaved = function(args) {
					if (args.p_result.resultValue.items) {
						if (!self.isAllEdited()) {
							for (var i = 0; i < items.length; i++) {
								self.replaceItem(items[i], args.p_result.resultValue.items[i]);
							}
						}
						
						if (self.onSaved != null && !cube.isObservable(self.onSaved)) {
							self.onSaved(args.p_result.resultValue.items);
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
		 * 将 Excel 表格导入为表格数据。
		 * @param [p_url] 后台处理Excel导入的url。
		 * @param [p_confirmBtnClick] 可选，点击保存按钮的处理函数，当设置该参数时调用该函数进行保存处理，否则调用表格的保存。
		 * @param [p_showOption] 可选，一个Boolean值，设置是否显示弹出窗口中的配置项。
		 * @param [p_columnNames] 可选，一个列名数组，设置导入列的默认值。
		 * 
		 * @example
		 * <code language="JavaScript">
		 * dataGrid.importExcel(serverUrl);
		 * </code>
		 */
		self.importExcel = function(p_url, p_confirmBtnClick, p_showOption, p_columnNames) {
			var paramColumns = [];
			var paramValue = [];
			var columns = self.columns();
			for (var i = 0; i < columns.length; i++) {
				paramColumns.push({value: columns[i].name, text: columns[i].caption});
				paramValue.push(columns[i].name);
			}
			
			if (typeof(p_showOption) != "undefined" && cube.isArray(p_showOption)) {
				p_columnNames = p_showOption;
				p_showOption = true;
			}
			
			cube.showDialog({
						title: cube.msg("IMPORT_EXCEL"),
						templateOptions: {
							name: "exceltmpl",
							params: {
								columns: paramColumns,
								columnNames: p_columnNames,
								value: paramValue.join(),
								importExcelUrl: p_url,
								previewGridBaseUrl: self.url(),
								confirmBtnClick: p_confirmBtnClick,
								showOption: p_showOption
							}
						},
						confirmBtnText: cube.msg("SAVE"),
						closeBtnText: cube.msg("CANCEL"),
						onConfirmBtnClick: function(args) {
							if (!args.result) {
								args.cancel = true;
							}
						},
						customBtns:[{
								text : cube.msg("PREVIOUS"),
								visible: cube.obj(false),
								click: function(dialog) {
									dialog.step(1);
									this.visible(false);
									dialog.customBtns()[1].visible(true);
								}
							}, {
								text : cube.msg("NEXT"),
								visible: cube.obj(false),
								click: function(dialog) {
									dialog.step(2);
									this.visible(false);
									dialog.customBtns()[0].visible(true);
								}
							}]
			})
		};
			
		
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
				self.columns(args.columns);
			}
			
			self.initColumn(args);
			
			if (self.searchBoxId()) {
				var searchBox = cube.getPageViewModelByNode($("#" + self.searchBoxId()));
				if (searchBox) {
					_params = cube.isObservable(searchBox._params) ? searchBox._params() : searchBox._params;
					if (!_params && args.dicts && args.dicts.length > 0) {
						searchBox.setDicts(args.dicts);
					}
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
			self._pageCount(parseInt((args.itemCount + parseInt(self.pageSize()) - 1) / parseInt(self.pageSize())));
			self.itemsChange();
			
			if (self.isAllowAppend()) {
				initAppendedItem();
			}
			
			self.itemsSub = cube.subscribe(self.items, function(items){ 
				self.itemsChange();
			});
			
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
		
		var autoPercentCount = 0;
		/**
		 * @ignore
		 */
		self.initColumn = function(args) {
			var columns = self.columns();
			if (columns && columns.length > 0) {
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
				}
				
				if (autoPercentCount == 0) {
					self.isShowTempColumn(true);
				}
			}
		}

		// 初始化添加项
		function initAppendedItem() {
			var appendColumns = cube.clone(self._columns);
			var dicts = self.dicts();
			var obj = {};
			var oldObj = {};
			if (appendColumns && appendColumns.length > 0) {
				$.each(appendColumns, function() {
					if (typeof(this.value) != "undefined") {
						if (cube.isObservable(this.value)) {
							obj[this.name] = this.value;
							oldObj[this.name] = this.value();
						} else {
							obj[this.name] = cube.obj(this.value);
							oldObj[this.name] = this.value;
						}
					} else {
						obj[this.name] =  cube.obj(null);
						oldObj[this.name] = null;
					}
					if (!this.editorType) {
						this.editorType = "TextEditor";
					}
					
					if (this.editorType == "DropDownEditor" || this.editorType == "CheckListEditor" || this.editorType == "ListEditor" || this.editorType == "LabelEditor") {
						if (typeof(this.list) == "undefined") {
							if (dicts && dicts[this.name]) {
								this.list = dicts[this.name];
							} else {
								this.list = cube.array();
							}
						} else if (!cube.isObservable(this.list)) {
							if (cube.isArray(this.list)) {
								this.list = cube.array(this.list);
							} else {
								this.list = cube.obj(this.list);
							}
						}
					}
					
					this.validStatus = cube.obj("");
					this.validHint = cube.obj("");
				});
			}
			
			self._appendedItem = obj;
			self._appendedItemObj = oldObj;
			self.appendColumns(appendColumns);
		};
		
		/**
		 * 验证数据
		 * @ignore
		 */
		self.validate = function(type, index) {
			var editedItem = null;
			var columns = null;
			if (type == 1) {
				editedItem = self._editedItem;
				columns = self.columns();
			} else if(type == 0) {
				editedItem = self._appendedItem;
				columns = self.appendColumns;
			} else {
				editedItem = type;
				columns = self.columns();
			}
			
			for (var i = 0; i < columns.length; i++) {
				if (columns[i].validStatus && typeof(columns[i].validStatus) == "function" && columns[i].validStatus() != "") {
					return false;
				} else {
					if (editedItem!=null) {
						for(var key in editedItem) {
							if(columns[i].name == key && columns[i].nullable == false){
								var value = cube.isObservable(editedItem[key]) ? editedItem[key]() : editedItem[key];
								var result = validator.validate(value, "NOTNULL", {});
								if (!result.successful) {
									if (self.isAllEdited()) {
										var valid = self._editorValid()[index][key];
										valid.validStatus("error");
										valid.validHint(result.hint);
									} else {
										columns[i].validStatus("error");
										columns[i].validHint(result.hint);
									}
									return false;
								} else {
									if (self.isAllEdited()) {
										var valid = self._editorValid()[index][key];
										valid.validStatus("");
										valid.validHint("");
									} else {
										columns[i].validStatus("");
										columns[i].validHint("");
									}
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
		 */
		self._editor_changed = function(p_name, p_value, itemIndex) {
			var columns = self.columns();
			var item = self.items()[itemIndex];
			if (columns && columns.length > 0) {
				$.each(columns, function(index, p_column) {
					if (p_column.onChanged) {
						p_column.onChanged(p_name, p_value, item);
						return true;
					}
				});
			}
		};
		
		 /**
		 * @ignore
		 * 用于文件上传编辑器
		 */
		self._editor_upload = function(p_name, p_fileNames) {
			if (self._editedItem != null && !cube.isObservable(self._editedItem)) {
				  self._editedItem[p_name](p_fileNames);
			}
		};
		
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
			} else if (cube.isObservable(p_param) && typeof(p_param()) == "boolean") {
				return p_param();
			}else if(cube.isArray(p_param)) {
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
			
			var result = "";
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
			} else if (cube.notEmpty(column.list)) {
				 var list = [];
				 if(cube.isObservable(column.list)){
					 list = column.list();
				 } else {
					 list = column.list;
				 }
				
				 for (var i = 0; i < list.length; i++) {
					 if (list[i].value == p_value) {
						 result = list[i].text;
						 break;
					 }
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
		self.renderMarkCell = function(item, element) {
			var result = "";
			var markColumn = self.markColumn();
			if (markColumn.renderCell) {
				result = markColumn.renderCell(item, element);
			} else {
				result = markColumn.value;
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
				
				if(self.isShowTips()){
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
		 */
		self.getEditorParam = function(p_data, item, index) {
			var param = cube.clone(p_data);
			
			if (p_data.editorType == "FileEditor") {
				param.onChanged = self._editor_upload;
				param.type = "grid";
				param.previewSize = "small";
				if (item) {
					param.pkVal = item[self.primaryKey()];
				}
			} else {
				param.onChanged = self._editor_changed;
			}
			
			if (typeof(p_data.align) != "undefined") {
				param.textAlign =  p_data.align;
			}
			
			if (typeof(p_data.isExtDisplay) == "undefined") {
				param.isExtDisplay =  true;
			}
			
			if (typeof(p_data.appendEditorType) != "undefined") {
				param.editorType = p_data.appendEditorType;
			} else if (typeof(p_data.editorType) == "undefined") {
				param.editorType = "TextEditor";
			}
			
			if (p_data.editorType == "DateTimeEditor") {
				param.width = "65%";
			} else if(p_data.editorType == "DropDownEditor") {
				param.width = "auto";
			} else if(p_data.editorType == "NumberEditor") {
				param.width = "40%";
			} else {
				param.width = "85%";
			}
			
			
			if (cube.notEmpty(item)) {
				if (self.isAllEdited()) {
					if (self._editorValid().length > 0) {
						param.validStatus = self._editorValid()[index][p_data.name].validStatus;
						param.validHint = self._editorValid()[index][p_data.name].validHint;
					}
					
					param.value = item[p_data.name];
				} else {
					param.value = self._editedItem[p_data.name];
				}
			} else {
				param.value = self._appendedItem[p_data.name];
			}

			
			param.itemIndex = index;
			if (p_data.editorType == "CheckEditor" || p_data.editorType == "CheckListEditor") {
				param.name = p_data.name + index;
			}
			
			return param;
		}
		
		/**
		 * @ignore
		 */
		self.openSubGrid = function(viewModel, event) {
			if (self.subGridId() == viewModel[self.primaryKey()]) {
				self.subGridId(null);
			} else {
				self.subGridId(viewModel[self.primaryKey()]);
			}
			setTimeout(function(){
				self.itemsChange();
			}, 300);
		}
		
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
		self.customOperationsClick = function(item, viewModel, element) {
			viewModel.click(item, element);
		}
		
		/**
		 * @ignore
		 */
	  	self.onload = function(node) {
	  		var $node = $(node);
	  		self.$tableBody = $node.find(".tableBody");
	  		self.$tableHeader = $node.find(".tableHeader");
	  		
	  		self.$tableBody.scroll(function(e) {
	  			self.$tableHeader.css("left", 0 - e.target.scrollLeft);
	  		});
	  		
	  		self.itemsChange();
	  		
	  		var watermarkOptions = self.watermarkOptions();
			if(cube.notEmpty(watermarkOptions) && cube.notEmpty(watermarkOptions.text)){
				watermarkOptions = $.extend(self._watermarkOptionsDefaultValue(), watermarkOptions);
				
				watermarkOptions.indent = (parseInt(watermarkOptions.width) + parseInt(watermarkOptions.xSpace))/2;
				self.watermarkOptions(watermarkOptions);
				
				var rows = parseInt((self.$tableBody.height() - watermarkOptions.y)/(parseInt(watermarkOptions.height) + parseInt(watermarkOptions.ySpace))) + 1;
				for (var i = 0; i < rows; i++) {
					self.watermarkRows.push({
						y: watermarkOptions.y + (parseInt(watermarkOptions.height) + parseInt(watermarkOptions.ySpace)) * i
					});
				}

				var cols = parseInt((self.$tableBody.width() - watermarkOptions.x)/(parseInt(watermarkOptions.width) + parseInt(watermarkOptions.xSpace))) + 1;
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