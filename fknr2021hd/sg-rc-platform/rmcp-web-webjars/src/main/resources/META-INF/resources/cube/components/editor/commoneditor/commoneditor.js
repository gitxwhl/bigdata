define(["Validator", "CheckUtil"], function(validator, checkUtil) {

	/**
	 * 编辑器组件，通过editorType属性设置编辑器类型（TextEditor、LabelEditor、DropDownEditor、CheckEditor、CheckListEditor、ListEditor、NumberEditor、CustomEditor）
	 * <code language="html">
	 * 		<commoneditor params="
	 *					    name:'test',
	 *					    value: value,
	 *						readOnly: readOnly,
	 *						editorType: editorType,
	 *						textMode: textMode,
	 *						type: type,
	 *						list: list,
	 *						width: width"></commoneditor>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.value = ['1'];
	 * 				self.readOnly = false;
	 * 				self.editorType = 'TextEditor';
	 * 				self.textMode = 'normal';
	 * 				self.type = 'checkbox';
	 * 				self.list = [
	 *	    					    {value:'1',text:'选择1'},
	 *	    		    			{value:'2',text:'选择2'},
	 *	    	 		    		{value:'3',text:'选择3'},
	 *	    	 		    		{value:'4',text:'选择4'},
	 *	    	 		    		{value:'5',text:'选择5'},
	 *	    	 		    		{value:'6',text:'选择6'},
	 *	    	 		    		{value:'7',text:'选择7'},
	 *	    	 		    		{value:'8',text:'选择8'}
	 *		    	 		    ];
	 * 				self.width = '90%';			
	 * 
	 * 				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 * 
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**
		 * 编辑器名称
		 * 
		 * @default 空
		 */
		self.name = '';
		
		/**
		 * 编辑内容，当编辑器类型为CheckListEditor多值时使用逗号分隔
		 * 例："1,2"
		 * 
		 * @default 空
		 */
		self.value = '';
		
		/**
		 * 下拉框是否显示默认空白项
		 * @default true
		 */
		self.isShowNull = true;
		
		/**
		 * 当isShowNull设置为false时，value未设置时，默认选中第一项,适用与DropDownEditor
		 *  @default
		 * 		false
		 */
		self.isSelectedFirst = false;
		
		/**
		 * 是否单项选择按钮垂直排列, 适用与CheckListEditor
		 * @default
		 * 	false
		 */	
		self.isCheckListVer = false;
		
		/**
		 * 是否弹出选择框在弹窗外部显示, 适用与DropDownEditor, DropDownTreeEditor
		 * @default
		 * 	false
		 */		
		self.isExtDisplay = false;
		
		/**
		 * 下拉框内容宽度过长时是否省略, 适用与DropDownEditor
		 * @default
		 * 	false
		 */		
		self.isTextEllipsis = false;
		
		/**
		 * 是否启用输入限制功能, true为启用,启用后当初始值不为空时不可输入编辑；为空时可输入编辑。适用与NumberEditor.
		 * @default
		 * 	false
		 */
		self.isValueIsEmptyEnter = false;
		
		/**
		 * 设置下拉菜单最大高度, 内容高度超出会出现滚动条, 适用与DropDownEditor、DropDownTreeEditor
		 * @default
		 * 		auto
		 */
		self.dropdownMenuHeight = 'auto';
		
		/**
		 * 下拉框是否显示滚动条
		 * @default
		 * 	false
		 */
		self.isShowScroll = false;
		
		/**
		 * 下拉框是否显示复选框
		 * @default false
		 */
		self.isShowCheckbox = false;
		
		/**
		 * 是否单击按钮弹出选择框, 适用与DropDownEditor
		 * @default
		 * 	false
		 */		
		self.isClickShow = false;
		
		/**
		 * 是否允许输入，适用与DropDownEditor、DropDownTreeEditor、CustomEditor
		 * @default 
		 * DropDownEditor、DropDownTreeEditor 默认为不可输入，false
		 * CustomEditor 默认为可以输入。 true
		 */
		self.isAllowEdit = false;
		
		/**
		 * 是否失去焦点时进行校验，默认输入即校验 适用与TextEditor
		 * @default
		 * 		false
		 */
		self.blurValid = false;
		
		/**
		 * 是否根据输入自动匹配下拉项，适用与DropDownEditor、DropDownTreeEditor
		 * @default true
		 */
		self.autoMatching = true;
		
		/**
		 * 内部存储CheckListEditor的值
		 * @ignore
		 */
		self._checkListvalue = [];
		
		/**
		 * 是否为只读
		 * 
		 * @default false
		 */
		self.readOnly = false;
		
		/**
		 * 编辑器类型
		 * <p>
	     * 该字段可选的值包括：
	     * <ul>
	     * <li><b>TextEditor</b></li>
	     * <li><b>LabelEditor</b></li>
	     * <li><b>DropDownEditor</b></li>
	     * <li><b>CheckEditor</b></li>
	     * <li><b>CheckListEditor</b></li>
	     * <li><b>ListEditor</b></li>
	     * <li><b>NumberEditor</b></li>
	     * <li><b>CustomEditor</b></li>
	     * <li><b>DropDownTreeEditor</b></li>
	     * <li><b>TransferEditor</b></li>
	     * </ul>
	     * </p>
		 * 
		 * 当该属性值为：CheckListEditor时需同时设置 <strong>type</strong> 属性。<br/>
		 * 当该属性值为：DropDownEditor、CheckListEditor、ListEditor时需同时设置 <strong>list</strong>  属性。<br/>
		 * 当该属性值为：CustomEditor时可设置 <strong>customBtnText</strong> 属性或<strong>customBtnIcon</strong> 属性。按钮点击事件时有<strong>onClick</strong>事件设置。
		 *
		 * @default TextEditor
		 */
		self.editorType = 'TextEditor';
		
	    /**
	     * 设置编辑器前缀文本描述
	     * @default 空
	     */
		self.prefixText = '';
		
	    /**
	     * 设置编辑器后缀文本描述
	     * @default 空
	     */
		self.suffixText = '';
		
		/**
		 * 设置编辑器的字符最大长度，该属性与validOptions中maxLength的区别为：该属性值会设置input元素的maxlength属性。
		 * 
		 * @default null
		 */
		self.maxLength = null;
		
		/**
	     * 文本框的类型。 当editorType为TextEditor时有效。
	     * <p>
	     * 该字段可选的值包括：
	     * <ul>
	     * <li><b>normal</b></li>
	     * <li><b>password</b></li>
	     * <li><b>multiline</b></li>
	     * </ul>
	     * </p>
	     * 
	     * @default normal
	     */
	    self.textMode = "normal";
	    
	    /**
	     * 设置编辑器文本对齐方式
	     * @default main
	     */
	    self.textAlign = "left";
	    
	    /**
	     * 编辑器是否允许为空
	     * 
	     * @default true
	     */
	    self.nullable = true;
	    
	    /**
	     * 验证类型，多个验证类型用逗号分隔。
	     * 该字段可选的值包括：
	     * <ul>
	     * 	   <li><b>NOTNULL</b> 必填</li>
	     *     <li><b>DIGIT</b> 数字</li>
	     *     <li><b>INTEGER</b> 整数</li>
	     *     <li><b>FLOAT</b> 浮点数</li>
	     *     <li><b>MINUSINTEGER</b> 负整数</li>
	     *     <li><b>MINUSFLOAT</b> 负浮点数</li>
	     *     <li><b>PLUSINTEGER</b> 正整数</li>
	     *     <li><b>PLUSFLOAT</b> 正浮点数</li>
	     *     <li><b>CHINESE</b> 中文</li>
	     *     <li><b>LETTER</b> 字母</li>
	     *     <li><b>ASCII</b> ASCII 码</li>
	     *     <li><b>DATE</b> 日期。例如：2007-07-01</li>
	     *     <li><b>DATETIME</b> 日期时间。例如：2007-07-01 14:28:32</li>
	     *     <li><b>TIME</b> 时间。例如：14:28:32</li>
	     *     <li><b>EMAIL</b> 邮箱地址</li>
	     *     <li><b>IP</b>  IP 地址</li>
	     *     <li><b>MOBILE</b> 手机号码</li>
	     *     <li><b>PHONE</b> 电话号码</li>
	     *     <li><b>URL</b> 统一资源地址</li>
	     *     <li><b>ZIPCODE</b> 邮政编码</li>
	     *     <li><b>SQL</b> 是否包含 SQL 关键字</li>
	     *     <li><b>IDCARD</b> 身份证号码</li>
	     *     <li><b>REGEXP</b> 正则表达式，支持通过指定正则表达式进行校验。</li>
	     *     <li><b>UNIQUE</b> 唯一性验证。</li>
	     * </ul>
	     * 
	     * @default null
	     */
	    self.validType = null;
	    
	    /**
	     * 一个json对象，设校验参数。该属性与 {@link validType}配合使用。
	     * 常用校验参数有：
	     * maxValue, minValue, maxLength, minLength, expression。
    	 * maxValue 表示数字的最大值。该参数在 validType 为 DIGIT、INTEGER、MINUSINTEGER、PLUSINTEGER、FLOAT、MINUSFLOAT、PLUSFLOAT 时使用。
     	 * minValue 表示数字的最小值。该参数在 validType 为 DIGIT、INTEGER、MINUSINTEGER、PLUSINTEGER、FLOAT、MINUSFLOAT、PLUSFLOAT 时使用。
     	 * expression 表示正则表达式。该参数在 validType 为 REGEXP 时使用。
     	 * maxLength 表示字符串的最大长度。
     	 * minLength 表示字符串的最小长度。
     	 * validateMessage 表示验证无效时的提示信息。
     	 * 
     	 *  @default null
	     */
	    self.validOptions = null;
	    
	    /**
	     * 验证状态，在表单和表格中起作用。
	     * 取值：（错误）error、（警告）warning、（通知）info和（成功）success
	     * @default null
	     */
	    self.validStatus = null;
	    
	    /**
	     * 验证错误提示信息，在表单和表格中起作用。
	     * @default null
	     */
	    self.validHint = null;
	    
	    /**
	     * 自定义验证规则。
	     * 返回的结果为一个 Boolean 值或者一个 JSON 对象。如“true”、“false”或者“{ successful: false, hint: "验证失败！" }”。
	     * 如果返回的是 JSON 对象，“successful”表示是否成功，“hint”表示显示值。
	     * 
	     * @example
	     * <code language="JavaScript">
	     *     customValidate:function(p_value){
	     *         // TODO: 验证逻辑
	     *         // 如果没有验证通过
	     *         return { successful:false, hint:"验证失败！" }
	     *     }
	     * </code>
	     */
	    self.customValidate = null;
	    
	    /**
		 * @ignore
		 * 
		 * 是否进行校验
		 * @default true
		 */
		self.isValid = true;
		
		/**
		 * 是否获取焦点
		 * @default false
		 */
		self.hasFocus = false;
	    
	    /**
	     * CheckListEditor类型（radio 或者 checkbox）,当editorType为CheckListEditor时有效。
	     * <p>
	     * 该属性可选的值包括：
	     * <ul>
	     * <li><b>checkbox</b></li>
	     * <li><b>radio</b></li>
	     * </ul>
	     * </p>
	     * 
	     * @default checkbox
	     */
	    self.type = "checkbox";
		
		/**
		 * 编辑器选项列表，当编辑器类型为DropDownEditor、CheckListEditor、ListEditor、LabelEditor、TransferEditor时使用
		 * 例：[{value:'1',text:'选择1'}]
		 * 
		 * @default null
		 */
		self.list = [];
		
		/**
		 * 下拉树配置项，参考树组件（tree）属性，当编辑器类型为DropDownTreeEditor时使用
		 */
		self.treeOptions = {};
		
		/**
		 * 是否选中时关闭下拉框，该属性适用于节点选中不适用复选框选中，当编辑器类型为DropDownTreeEditor时使用
		 * @default
		 * 		true
		 */
		self.isSelectedClose = true;
		
		/**
		 * 编辑器宽度
		 * 
		 * @default 100%
		 */
		self.width = "100%";
		
		/**
		 * 编辑器高度，当textMode为multiline时有效
		 * 
		 * @default 100px
		 */
		self.height = "100px";
		
		/**
		 * 编辑器类型为NumberEditor时，数字显示框递增或递减的值
		 * 
		 * @default 1
		 */
		self.increment = 1;
		
		/**
		 * 	时间日期格式，当该值不为null时，会将value值进行时间格式化处理
		 *  该属性可匹配的值类型，例：
     	 * <ul>
     	 *      <li><b>yyyy-MM-dd</b></li>
     	 *      <li><b>yyyy-MM</b></li>
     	 *      <li><b>yyyy-MM-dd HH:mm:ss</b></li>
         *      <li><b>yyyy-MM-dd HH:mm</b></li>
     	 * </ul>
     	 * 
		 *  @default null
		 */
		self.format = null;
		
		/**
		 * 编辑器类型为CustomEditor时，按钮文字
		 * @default null
		 */
		self.customBtnText = null;
		
		/**
		 * Label编辑器是否换行显示
		 * @default true
		 */
		self.labelAutowrap = true;
		
		/**
		 * @ignore
		 */
		self.labelValue = "";
		
		/**
		 * @ignore
		 */
		self.labelTitle = "";
		
		/**
		 * 编辑器类型为CustomEditor时，按钮图标
		 * 选取bootstrap v2（Glyphicons）中的图标，例如plus代表icon-plus，自动增加前缀icon-
		 * @default folder-open
		 */
		self.customBtnIcon = "folder-open";
		
		/**
		 * 编辑器类型为CustomEditor时，按钮是否可点击
		 * @default false
		 */
		self.customBtnEnabled = true;
		
		/**
		 * 编辑器类型为TransferEditor时，设置显示复选框的高度 
		 * @default 200px
		 */
		self.transferHeight = "200px";
		
		/**
		 * 编辑器类型为TransferEditor时，设置显示复选框的宽度 
		 * @default 100px
		 */
		self.transferWidth = "100px";
		
		/**
		 * 编辑器类型为CheckListEditor时，设置显示复选框每行列数 
		 * @default null
		 */
		self.checkListCols = null;
		
		/**
		 * 是否显示添加按钮，编辑器类型为TransferEditor时使用。
		 * @default
		 * 		true
		 */
		self.displayAddButton = true;
		
		/**
		 * 是否显示删除按钮，编辑器类型为TransferEditor时使用。
		 * @default
		 * 		true
		 */
		self.displayDeleteButton = true;
		
		/**
		 * 是否显示添加全部按钮，编辑器类型为TransferEditor时使用。
		 * @default
		 * 		true
		 */
		self.displayAddAllButton = true;
		
		/**
		 * 是否显示删除全部按钮，编辑器类型为TransferEditor时使用。
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
		 * @ignore
		 */
		self._isValueIsEmptyEnter = false;
		
		/**
		 * @ignore
		 */
		self._value = null;
		
		/**
		 * @ignore
		 */
		self._isValided = null;
		
		/**
		 * @ignore
		 */
		self.isShowPopDialog = false;
		
		/**
		 * @ignore
		 */
		self.validHintType = "inline";
		
		/**
		 * @ignore
		 * 编辑器所在表格行索引，表格编辑时使用
		 */
		self.itemIndex = null;
		
		/**
		 * 编辑值改变之后回调事件
		 * @param name 编辑器的名称
         * @param value 编辑器的值
		 * 
		 * @default null
		 */
		self.onChanged = null;
		
		/**
		 * 选中变化处理事件。
		 * @param item 选中项
		 */
		self.onSelectedChanged = null;
		
		/**
		 * 编辑器文本框点击回调事件，回调函数可设置一个参数接收文本框的输入值，支持TextEditor、CustomEditor，<br/>
		 * 注：当为CustomEditor时点击文本框后面按钮触发该事件，该回调事件的返回值将赋值给编辑器
		 * 
         * @param value 编辑器的值
         * @param viewModel 编辑器的viewModel对象
	     *
		 * @default null
		 */
		self.onClick = null;
		
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
		
		var _node = null;

		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			var list = self.list();
			if (cube.notEmpty(list) && !cube.isArray(list) && self.itemIndex() != null) {
				if (cube.notEmpty(list[self.itemIndex()])) {
					self.list(list[self.itemIndex()]);
				}
			}
			
			if (self.customBtnIcon() == null) {
				self.customBtnIcon("folder-open");
			}
			
			var editorType = self.editorType();
			if (editorType == "TextEditor" && self.textMode() == "multiline" && cube.notEmpty(self.value()) && cube.isString(self.value())) {
				 self.value(self.value().replace(/\\r\\n/gi, "\n").replace(/\\n/gi, "\r\n"));
				 self.onChanged(self.name(), self.value());
			} 
			
			if (self.format() != null) {
				self.value(parseDate(self.value(), self.format()));
			}
			 
			self.valueSub = cube.subscribe(self.value, self._changed);
			self.checkListvalueSub = cube.subscribe(self._checkListvalue, self._checkListValuechanged);
			self.hasFocusSub = cube.subscribe(self.hasFocus, function(value) {
				if (value && self.onFocus != null && !cube.isObservable(self.onFocus)) {
					self.onFocus(self.value(), self);
				}
			});
			
			if (editorType == "NumberEditor" && cube.isEmpty(self.validType())) {
				self.validType("DIGIT");
				self._value(self.value());
			}
			
			self._changeCheckListvalue(editorType);
			
			if (editorType == "LabelEditor") {
				self.labelValue(cube.escapeString(self.value()));
				self.labelTitle(self.value());
				var list = self.list();
				var value = self.value();
				for (var i = 0; i < list.length; i++) {
					if (list[i].value == value) {
						self.labelValue(cube.escapeString(list[i].text));
						self.labelTitle(list[i].text);
						break;
					}
				}
			}

			if (typeof(params.maxLength) != "undefined" && params.maxLength != null) {
				var validOptions = self.validOptions();
				if (validOptions == null) {
					validOptions = {};
				}
				
				validOptions.maxLength = params.maxLength;
				self.validOptions(validOptions);
			}
			
			if(cube.notEmpty(self.checkListCols())){
				self.initList();
			}
			
			self.validHint("");
			self.validStatus("");
			
			setNullable(self.nullable(), params);
			self.nullableSub = cube.subscribe(self.nullable, setNullable);
		}
		
		function setNullable(value, params) {
			var validType = self.validType();
			if (value == false) {
				if (validType == null) {
					validType = "NOTNULL";
				} else if (validType.indexOf("NOTNULL") == -1) {
					validType = validType + ",NOTNULL";
				}
			} else if (validType != null && cube.notEmpty(params) && typeof(params.validType) != "undefined" && params.validType.indexOf("NOTNULL") == -1) {
				validType = validType.replace("NOTNULL", "");
			}
			
			self.validType(validType);
		}
		
		/**
		 * @ignore
		 */
		self._changeCheckListvalue = function(editorType, value) {
			if (typeof(value) != "undefined" && cube.isEmpty(value)) {
				self._checkListvalue([]);
			} else if ((editorType == "CheckListEditor" || (editorType == "DropDownEditor" && self.isShowCheckbox() == true) ) && cube.notEmpty(self.value())) {
				if (editorType == "CheckListEditor" && self.type() == "radio") {
					self._checkListvalue(self.value());
				} else {
					self._checkListvalue(String(self.value()).split(","));
				}
			} 
		}
		
		
		/**
		 * 初始化显示内容：QuarterItems
		 * @ignore
		 */
		self._list = cube.array([]);
		
		/**
		 * @ignore
		 */
		self.initList = function() {
			var listLength = self.list().length;
			var checkListCols = self.checkListCols();
			var lineNum = listLength / checkListCols;
			for(var i=0;i<lineNum;i++) {
				var items = cube.array([]);
				for(var j=0;j<checkListCols;j++) {
					var addItem  = self.list()[i*checkListCols+j];
					if((i*checkListCols+j)<listLength){
						items().push(addItem);
					}
				}
				self._list().push(items);
			}
		}
		
		/**
		 * @ignore
		 */
		self._selectListItem = function($data) {
			if (typeof(this.value) != "undefined") {
				self.value(this.value);
			} else {
				self.value(this);
			}			
		}
		
		/**
		 * @ignore
		 */
		self.setNumberDown = function() {
			if (self.readOnly()) {
				return;
			}

			var _value = parseFloat(self.value());
			if(isNaN(_value)){
				_value = 0;
				if(self.isValueIsEmptyEnter()){
					self._isValided(true);
					self._isValueIsEmptyEnter(true);
				}
			}
			
			var validOptions = self.validOptions();
			if (validOptions) {
				var maxValue = validOptions.maxValue;
				if(typeof(maxValue) != "undefined" && _value >= maxValue) {
					return;
				}
			}
			
			self.value(_value + self.increment());
		}
		
		/**
		 * @ignore
		 */
		self.setNumberUp = function() {
			if (self.readOnly()) {
				return;
			}

			var _value = parseFloat(self.value());
			if(isNaN(_value)) {
				_value = 0;
				if(self.isValueIsEmptyEnter()){
					self._isValided(true);
					self._isValueIsEmptyEnter(true);
				}
			}
			
			var validOptions = self.validOptions();
			if (validOptions) {
				var minValue = validOptions.minValue;
				if(typeof(minValue) != "undefined" && _value <= minValue) {
					return;
				}
			}
			
			self.value(_value - self.increment());
		}
		
		/**
		 * 键盘输入前验证
		 * @ignore
		 */ 
		self._input_keydown = function(viewMode, event) {   
			if (self.readOnly() && event.keyCode == 8) {
				event.keyCode = 0;
		        return false;
		    }
			
			var numDisplay = null;
			if(self.isValueIsEmptyEnter()){
				if(cube.notEmpty(self._value())){
					numDisplay =  false;
				} else if(cube.isEmpty(self._value()) && self._isValided() == false ){
					numDisplay = true;
				} else {
					if(self._isValueIsEmptyEnter()){
						numDisplay  = false;
					} else {
						numDisplay = true;
					}
				}
				
				return numDisplay;				
			} else {
				return true;
			}	
		}
		
		/**
		 * 键盘输入后验证
		 * @ignore
		 */ 
		self._input_keyup = function() {   
			_check_data();		
		}
		
		//检验input中数字是否标准
		function _check_data() {
			setTimeout(function() {
				var valStr = self.value() + "";
		    	var regxp1 = /\-*(\d+(\.\d+)*)e([\+\-])(\d+)/;
		    	var regxp2 = /^[+|-]?\d*[\.]?\d*$/;
		    	if (!(regxp1.test(valStr) || regxp2.test(valStr) || /^[+|-]?\d/.test(valStr))||/[a-zA-Z]+/.test(valStr)) {
		    		self.value("");
		    	}
			}, 100);
		}
		
		/**
		 * 不接受非法字符，对很多输入法无法拦截
		 * @ignore
		 */
		self._input_keypress = function(viewMode, event) {
			if (self.value() == null) {
				return true;
			}
			if (self.value().toString().indexOf("-") ==-1 && event.which == 45) {
				return true;
			}
			if (self.value().toString().indexOf(".") ==-1 && event.which == 46) {
				return true;
			} else {
				if ((event.which >= 48 && event.which <= 57 && event.ctrlKey == false && event.shiftKey == false)
						|| event.which == 0 || event.which == 8) {
					return true;
				} else {
					if (event.ctrlKey == true && (event.which == 99 || event.which == 118)) {
						return true;
					} else {
						return false;
					}
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self._changed = function(value, flag) {
			if (flag != false && self.isValid()) {
				self._valid();
			}
			
			var editorType = self.editorType();
			
			if (editorType == "LabelEditor") {
				self.labelValue(cube.escapeString(self.value()));
				self.labelTitle(self.value());
				var list = self.list();
				var value = self.value();
				if (cube.isArray(list)) {
					for (var i = 0; i < list.length; i++) {
						if (list[i].value == value) {
							self.labelValue(cube.escapeString(list[i].text));
							self.labelTitle(list[i].text);
							break;
						}
					}
				}
			} else if (editorType == "NumberEditor" && self.isValueIsEmptyEnter() && cube.isEmpty(self._value())) {
				if(event.srcElement.className == "btn btn-success"){
					self._isValided(false);
					self._isValueIsEmptyEnter(false);
				}	
				if(event.srcElement.className == "caret-down" || event.srcElement.className == "caret-up"){
					self._isValided(true);
				}
			} 
			
			self._changeCheckListvalue(editorType, value);
			
			if (self.onChanged != null && !cube.isObservable(self.onChanged)) {
				var itemIndex = self.itemIndex();
				if (itemIndex != null) {
					self.onChanged(self.name(), self.value(), itemIndex);
				} else {
					self.onChanged(self.name(), self.value());
				}
			}
		}
		
		/**
		 * @ignore
		 */
		self._onclick = function(viewModel) {
			if (self.onClick!=null && !cube.isObservable(self.onClick)) {
				if ((self.editorType() != "CustomEditor" && !self.readOnly()) || (self.editorType() == "CustomEditor" && self.customBtnEnabled())) {
					var result = self.onClick(self.value(), viewModel);
					if (result) {
						self.value(result)
					}
				}
			}
		}
		
		/**
		 * @ignore
		 */
		self._onkeydown = function(viewModel, event) {
			 if (viewModel.readOnly() && event.keyCode == 8) {
				 event.keyCode = 0;
		         return false;
		     }
			 
			 return true;
		}
		
		/**
		 * @ignore
		 */
		self._checkListValuechanged = function() {
			var editorType = self.editorType();
			if (editorType == "CheckListEditor" || editorType == "DropDownEditor") {
				var _checkListvalue = self._checkListvalue();
				
				self.value(cube.isArray(_checkListvalue) ? _checkListvalue.join(",") : _checkListvalue);
			}
		}
		
		/**
		 * @ignore
		 */
		self._valid = function(viewModel, event) {
			
			if(self.isValueIsEmptyEnter()){
				self._isValided(true);
				if(cube.notEmpty(self.value())){
					self._isValueIsEmptyEnter(true);
				} else {
					self._isValueIsEmptyEnter(false);
				}
			}
			
			if (!self.isValid() || self.readOnly()) {
				return;
			}
					
			var validType = self.validType();
			
			if(self.editorType() == "NumberEditor"){
				var value = self.value();
				
				if (cube.isEmpty(value) && validType.indexOf("NOTNULL") ==-1) {
					return;
				} else if (value != "-") {
					var result = validator.validate(self.value(), "DIGIT", null);
					if (!result.successful) {
						self.value("");
					}
				}
			}
				        
			var validateResult; 
			// 校验先走自定义校验.
	        if (self.customValidate != null && cube.isFunction(self.customValidate) && !cube.isObservable(self.customValidate)) {
	             validateResult = self.customValidate(self.value());
	        }
	        
	        // 自定义校验通过再走校验.
	        if (validateResult == false || ($.isPlainObject(validateResult) && validateResult.successful == false)) {
	        	self.validStatus("error");
	        	if ($.isPlainObject(validateResult) && validateResult.hint) {
	        		if (self.validHintType() == "pop") {
						self.isShowPopDialog(false);
						self.isShowPopDialog(true);
						cube.showPopDialog($(_node), {
							content: validateResult.hint,
							popoverDirection: "bottom",
							msgType: "error",
							isShowDialog: self.isShowPopDialog
						});
					} else {
						self.validHint(validateResult.hint);
					}
	        	}
				return;
	        }
			
	        if ((cube.isEmpty(validType) || validType.indexOf("NOTNULL") == -1 ) && cube.isEmpty(self.value())) {
	        	self.validStatus("");
				self.validHint("");
				self.isShowPopDialog(false);
				return;
			}
			
			var result = validator.validate(self.value(), validType, self.validOptions());
			if (!result.successful) {
				self.validStatus("error");
				
				if (self.validHintType() == "pop") {
					self.isShowPopDialog(false);
					self.isShowPopDialog(true);
					cube.showPopDialog($(_node), {
						content: result.hint,
						popoverDirection: "bottom",
						msgType: "error",
						isShowDialog: self.isShowPopDialog
					});
				} else {
					self.validHint(result.hint);
				}
			} else {
				self.validStatus("");
				self.validHint("");
				self.isShowPopDialog(false);
				
			}
		}
		
		function parseDate(_value, _format) {
			if (cube.notEmpty(_value)) {
				if (!cube.isString(_value)) {
					_value = String(_value);
				}
				
				var dt = new Date(_value.replace(/-/g,"/"));
				
				if (checkUtil.isDigit(_value)) {
					if(_value.length == 10){
						_value = _value + "000";
					}
					
					if (_value.length == 13) {
						dt = new Date(parseInt(_value));
					}
				}
				
				_format = _format.replace(/yyyy/g, dt.getFullYear());
				_format = _format.replace(/MM/g, numberFormat(dt.getMonth() + 1, "00"));
				_format = _format.replace(/dd/g, numberFormat(dt.getDate(), "00"));
				_format = _format.replace(/HH/g, numberFormat(dt.getHours(), "00"));
				_format = _format.replace(/mm/g, numberFormat(dt.getMinutes(), "00"));
				_format = _format.replace(/ss/g, numberFormat(dt.getSeconds(), "00"));
			} else {
				return _value;
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
	    self.onload = function(node, viewModel) {
	    	_node = node;
	    	
	    	var editorType = self.editorType();
	    	if (editorType != "DropDownEditor" && editorType != "DropDownTreeEditor") {
	    		if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
					self.onRendered(node, viewModel);
				}
	    	}
			
	    }
	    
		cube.endViewModel(self, params);
	}
	
	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 		this.isShowPopDialog(false);
 	    this.valueSub.dispose();
 	    this.checkListvalueSub.dispose();
 	    this.nullableSub.dispose();
 	    this.hasFocusSub.dispose();
 	}
 	
	return ViewModel;
});