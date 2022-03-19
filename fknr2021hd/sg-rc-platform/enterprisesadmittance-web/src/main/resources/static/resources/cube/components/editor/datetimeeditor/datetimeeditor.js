define(["CheckUtil", "Validator"], function(checkUtil, validator) {
	
	cube.importComponent("controls.calendar");
	
	/**
	 * 日期选择组件
	 * <code language="html">
	 * 		<datetimeeditor params="showTimePicker: showTimePicker,
	 *						showDayPicker: showDayPicker,
	 *						year: year,
	 *						month: month,
	 *						day: day,
	 *						hour: hour,
	 *						minute: minute,
	 *						second: second,
	 *						value: value"></datetimeeditor>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.showTimePicker = true;
	 * 				self.showDayPicker = true;
	 * 				self.year = '';
	 * 				self.month = '';
	 * 				self.day = '';
	 * 				self.minute = '';
	 * 				self.second = '';
	 * 				self.value = "2016-01-02";
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
		 * 编辑名称
		 * 
		 * @default 空
		 */
		self.name = '';
		
		/**
		 * 编辑器宽度
		 * 
		 * @default 100%
		 */
		self.width = "100%";
		
		/**
		 * 控制弹出方向，默认从下方弹出
		 * @default bottom
		 */
		self.popoverDirection = "bottom";
		
		/**
		 * @ignore
		 */
		self.msgType = "info";
		
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
		 *  是否显示时间选择框，默认显示
		 *  @default true
		 */
		self.showTimePicker = true;

		/**
		 * 是否显示日期选择框，默认显示
		 *  @default true
		 */
		self.showDayPicker = true;
		
		/**
		 * 是否显示月、日期选择框，默认显示
		 *  @default true
		 */
		self.showMonthPicker = true;
		
		/**
		 * 是否显示季度选择框，同时不显示月份、日期选择框，默认
		 *  @default false
		 */
		self.showQuarterPicker = false;
		
		/**
		 * 是否单击日期时，关闭时间选择框
		 * @default false
		 */
		self.isClickCloseDialog = false;
		
		/**
		 * 	时间日期格式
		 *  该属性可匹配的值类型，例：
     	 * <ul>
     	 *      <li><b>yyyy-MM-dd</b></li>
     	 *      <li><b>yyyy-MM</b></li>
     	 *      <li><b>yyyy-MM-dd HH:mm:ss</b></li>
         *      <li><b>yyyy-MM-dd HH:mm</b></li>
         *      <li><b>HH:mm:ss</b></li>
     	 * </ul>
     	 * 
		 *  @default yyyy-MM-dd
		 */
		self.format = "yyyy-MM-dd";
		
		/**
		 * 时间日期格式，该属性可匹配的值类型参考format属性
		 * 该格式用于向后台传递数据格式，当设置该属性时向后台传递数据时将使用该格式
		 * 
		 * @default null
		 */
		self.dataFormat = null;

		/**
		 * 选择框弹出left值
		 * @default 0px
		 */
		self.leftOffset = "0px";
		
		/**
		 * 弹出框是否获取焦点、
		 * @default false
		 */
		self.calendarFocus = false;
		
		/**
		 * 是否弹出日期选择框
		 * @default false
		 */
		self.isShowDialog = false;

		/**
		 * @ignore
		 */
		self.date = new Date();

		/**
		 * 年
		 * @default 当前年
		 */
		self.year = self.date.getFullYear();
		
		/**
		 * 季度
		 * @default 当前季度
		 */
		self.quarter = Math.ceil((self.date.getMonth()+1)/3);
		
		/**
		 * 月
		 * @default 当前月
		 */
		self.month = self.date.getMonth() + 1;

		/**
		 * 日
		 * @default 当前日
		 */
		self.day = self.date.getDate();

		/**
		 * 时
		 * @default 当前时
		 */
		self.hour = self.date.getHours();

		/**
		 * 分
		 * @default 当前分
		 */
		self.minute = self.date.getMinutes();

		/**
		 * 秒
		 * @default 当前秒
		 */
		self.second = self.date.getSeconds();
		
		/**
		 * 编辑器值，值格式为：2017-10-01 12:10:15或时间戳的秒数/毫秒数：151608643300
		 * @default 空
		 */
		self.value = "";
		
		/**
		 * 可选择的最小日期，仅支持yyyy-MM-dd格式
		 * @default null
		 */
		self.minValue = null;
		
		/**
		 * 可选择的最大日期，仅支持yyyy-MM-dd格式
		 * @default null
		 */
		self.maxValue = null;
		
		/**
		 * 是否为只读
		 * 
		 * @default false
		 */
		self.readOnly = false;
		
		/**
		 * 设置编辑器的字符最大长度，该属性值会设置input元素的maxlength属性。
		 * 
		 * @default null
		 */
		self.maxLength = null;
		
		/**
		 * 是否允许编辑
		 * @default true
		 */
		self.isAllowEdit = true;
		
		 /**
	     * 验证类型，多个验证类型用逗号分隔。
	     * 该字段可选的值包括：
	     * <ul>
	     * 	   <li><b>NOTNULL</b> 必填</li>
	     *     <li><b>DATE</b> 日期。例如：2007-07-01</li>
	     *     <li><b>DATETIME</b> 日期时间。例如：2007-07-01 14:28:32</li>
	     *     <li><b>TIME</b> 时间。例如：14:28:32</li>
	     * </ul>
	     * 
	     * @default DATE
	     */
		self.validType = "DATE";
	    
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
	     *         var begintime = Date.parse("2018-08-16 16:22:05");
	     *         var endtime = Date.parse("2018-08-26 16:22:05");
	     *         var nowtime = Date.parse(p_value);
	     * 
	     *   	   // TODO: 验证逻辑
	     *   	   if( nowtime > begintime && nowtime < endtime){
	     *         // 如果没有验证通过
	     *         return { successful:false, hint:"验证失败！" }
	     *     }
	     * </code>
	     */
	    self.customValidate = null;
	    
	    /**
	     * 当日期未定义时或时间设定未空时，是否允许时间设定为当前时间
	     * @default false
	     */
	    self.isShowValue = false;
	    
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
		 * @ignore
		 */
	    self.isShowPopDialog = false;
	    
	    /**
		 * @ignore
		 */
		self.validHintType = "inline";
		
		/**
		 * 当验证失败时，显示值处理方式，可选：empty（置空）、current（当前日期时间）、default（不做处理）
		 */
		self.validFailValue = "default";
		
		/**
		 * @ignore
		 * 是否进行校验
		 * @default true
		 */
		self.isValid = true;
		
		/**
		 * 是否外部显示
		 *  @default
		 * 		false
		 */
		self.isExtDisplay = false;
		
		/**
		 * 是否获取焦点
		 * @default false
		 */
		self.hasFocus = false;
		
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
		 * 编辑值改变之后回调事件
		 * 
		 * @default null
		 */
		self.onChanged = null;
		
		/**
		 * @ignore
		 */
		self.popNode = null;
		/**
		 * @ignore
		 */
		self._value = null;
		
		/**
		 * @ignore
		 */
		self.$scrollDiv = null;
		

		/**
		 * @ignore
		 */
		self.timeStart = 0;
		
		/**
		 * @ignore
		 */
		self._timeEditor = false;
		
		/**
		 * @ignore
		 */
		self.$e = null;
		
		var _valueChange = false;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			if (self.showQuarterPicker()) {
				self.showTimePicker(false);
				self.showMonthPicker(false);
			}

			if(self.format() == "HH:mm:ss" || self.format() == "HH:mm" || self.format() == "HH"){
				self._timeEditor(true);
			}
			
			self.validHint("");
			self.validStatus("");
						
			self.setValidType();
			setNullable(self.nullable());
			self.nullableSub = cube.subscribe(self.nullable, setNullable);
			self.formatSub = cube.subscribe(self.format, self.setValidType);
			
			self._valueSub = cube.subscribe(self._value, self._changed);
			self.valueSub = cube.subscribe(self.value, self._valuechanged);
			
			self._value(parseDate(self.value(), self.format(), self.isShowValue()));
			
			self.yearSub = cube.subscribe(self.year, self._calendarchanged);
			self.quarterSub = cube.subscribe(self.quarter, self._calendarchanged);
			self.monthSub = cube.subscribe(self.month, self._calendarchanged);
			self.daySub = cube.subscribe(self.day, self._calendarchanged);
			self.hourSub = cube.subscribe(self.hour, self._calendarchanged);
			self.minuteSub = cube.subscribe(self.minute, self._calendarchanged);
			self.secondSub = cube.subscribe(self.second, self._calendarchanged);
			
			self.hasFocusSub = cube.subscribe(self.hasFocus, function(value) {
				if (value && self.onFocus != null && !cube.isObservable(self.onFocus)) {
					self.onFocus(self.value(), self);
				}
			});
		}
		
		function setNullable(value) {
			var validType = self.validType();
			if (value == false) {
				if (validType == null) {
					validType = "NOTNULL";
				} else if (validType.indexOf("NOTNULL") == -1) {
					validType = validType + ", NOTNULL";
				}
			} else if (validType != null) {
				validType = validType.replace("NOTNULL", "");
			}
			
			self.validType(validType);
		}

		/**
		 * @ignore
		 */
		self.setValidType = function(p_format) {
			var format = p_format;
			if (cube.isEmpty(format)) {
				format = self.format();
			}
			
			var validType = self.validType();
			validType = "DATE";
			
			if(format == "HH:mm:ss"){
				validType = "TIME";
			} else if(validType == "DATE"){
				if (format == "yyyy") {
					self.showTimePicker(false);
					self.showMonthPicker(false);
					self.showDayPicker(false);
					self.showQuarterPicker(false);
				} else if (format.indexOf("dd") == -1 && format.indexOf("HH") == -1) {
					self.showTimePicker(false);
					self.showDayPicker(false);
				}
				
				if (format.indexOf("HH") != -1 && format.indexOf("dd") != -1) {
					validType = "DATETIME";
					if(self.validType().indexOf("NOTNULL") != -1){
						validType = validType + ", NOTNULL";
					}
				} else if(format.indexOf("HH") != -1 && format.indexOf("dd") == -1){
					validType = "TIME";
					if(self.validType().indexOf("NOTNULL") != -1){
						validType = validType + ", NOTNULL";
					}
				}
			}
						
			self.validType(validType);
			
			if (cube.notEmpty(p_format)) {
				self._valuechanged(self._value());
			}
		}
		
		/**
		 * @ignore
		 */
		self.setTimeUp = function() {
			self._setTimeChanged("up");
		}
		
		/**
		 * @ignore
		 */
		self.setTimeDown = function() {
			self._setTimeChanged("down");
		}
		
		/**
		 * @ignore
		 */
		self._input_keydown  = function(viewModel, event) {
			if ((self.readOnly() || !self.isAllowEdit()) && event.keyCode == 8) {
				event.keyCode = 0;
		        return false;
		    }
			
			if(self._timeEditor() && !self.isAllowEdit()){
				return false;
			} else {
				return true;
			}
		}
		
		/**
		 * @ignore
		 */
		self._setTimeChanged = function(btnStatus) {			
			if (self.readOnly()) {
				return;
			}
			var addNum = (btnStatus=="down")?-1:1;			
			var val = "";
			if(cube.isEmpty(self._value())){
				var myDate = new Date();
				var hours = parseInt(myDate.getHours());
				var minutes = parseInt(myDate.getMinutes());
				var seconds = parseInt(myDate.getSeconds());
			} else {
				var varArray = self._value().split(":");					
				var hours = parseInt(varArray[0],10);
				var minutes = parseInt(varArray[1],10);
				var seconds = parseInt(varArray[2],10);
			}
		
			if (self.timeStart() == 0){
				hours = hours + addNum;
				if(btnStatus=="down"){
					if(hours == -1){hours = 23}
				} else {
					if(hours == 24){hours = 0}
				}
			} else if (self.timeStart() == 1){
				minutes = minutes + addNum;
				if(btnStatus=="down"){
					if(minutes == -1){minutes = 59;hours -= 1;if(hours == -1){hours = 23}}
				} else {
					if(minutes == 60){minutes = 0;hours += 1;if(hours == 24){hours = 0}}
				}
			} else if (self.timeStart() == 2){
				seconds = seconds + addNum;
				if(btnStatus=="down"){
					if(seconds == -1){seconds = 59;minutes -= 1;if(minutes == -1){minutes = 59;hours -= 1;if(hours == -1){hours = 23}}}
				} else {
					if(seconds == 60){seconds = 0;minutes += 1;if(minutes == 60){minutes = 0;hours += 1;if(hours == 24){hours = 0}}}
				}
			}
			
			if(hours <= 9){hours = "0"+hours}
			if(minutes <= 9){minutes = "0"+minutes}
			if(seconds <= 9){seconds = "0"+seconds}
			
			if(self.format() == "HH:mm:ss"){
				val = hours+":"+minutes+":"+seconds;
			} else if (self.format() == "HH:mm"){
				val = hours+":"+minutes;
			} else if(self.format() == "HH"){
				val = hours;
			}
			self._value(val);
		}
		
		/**
		 * @ignore
		 */
		self._input_selectionchanged = function(element,event) {   
			var selectionStart = 0;
			var div = $(element);
			selectionStart = div.getSelection().start;
			if(selectionStart == 0 || selectionStart == 1 || selectionStart == 2 ){
				self.timeStart(0);
			} else if (selectionStart == 3 || selectionStart == 4 || selectionStart == 5){
				self.timeStart(1);
			} else if (selectionStart == 6 || selectionStart == 7 || selectionStart == 8){
				self.timeStart(2);
			} else {
				self.timeStart(0);
			}
		}
		
		/**
		 * 显示弹出框
		 */
		self.showPopoverDialog = function(element,event) {
			var div = $(element).parent().siblings('.par').children(".popover");
			if(self._timeEditor()){
				self._input_selectionchanged(element,event);
				return;
			}

			if (!self.readOnly()) {
				if(self.isExtDisplay()){
					self.showlog(element,div,event);	
				}
				
				self.isShowDialog(true);
				self.isShowPopDialog(false);
				self.calendarFocus(true);	
				computeLeftOffset();
			
			}
		}
		
		/**
		 * @ignore
		 */
		self.showlog = function(element,div,event) {
			var inputWid = null;
			var isShowRight = true;
			var scrollTop = 0;
			var scrollLeft = 0;
					
			if(element.className == 'btn btn-default'){
				inputWid = $(element).siblings('input')[0];
				isShowRight = ($(window).width() - event.clientX + event.offsetX - 40 > div.innerWidth())? true : false;
			} else {
				inputWid = $(element)[0];
				isShowRight = ($(window).width() - event.clientX - inputWid.offsetWidth + event.offsetX - 40 > div.innerWidth())? true : false;
			}
			
			var inputWidLeft = inputWid.offsetWidth;
			var inputWidHeight = inputWid.offsetHeight;
			
			div.css("position", "fixed");
			div.css("z-index", "5000");
			
			var target = (event.target) ? event.target : event.srcElement;
			var layerY  = (event.layerY) ? event.layerY : event.offsetY;
			var divOffsetX = event.clientX - event.offsetX + scrollLeft;
			
			if($(window).height() - event.clientY - inputWidHeight + event.offsetY > div.innerHeight()){
				if (target.className == 'btn btn-default'){
					div.css("left", divOffsetX - inputWidLeft);
				} else if (target.className == 'icon-calendar'){
					div.css("left", divOffsetX - inputWidLeft  -12);
				} else {
					div.css("left", divOffsetX);
				}
				div.css("top", event.clientY + inputWidHeight - layerY);
				div.removeClass('top');
				div.removeClass('right');
				div.addClass('bottom');
				$('body>.popover.arrow').css("top", "-11px");
			} else {
				if(event.clientY - event.offsetY - 10 > div.innerHeight()){
					if (target.className == 'btn btn-default'){
						div.css("left", divOffsetX - inputWidLeft);
					} else if (target.className == 'icon-calendar'){
						div.css("left", divOffsetX - inputWidLeft - 12);
					} else {
						div.css("left", divOffsetX);
					}
					div.css("top", event.clientY - div.innerHeight() - layerY);
					div.removeClass('bottom');
					div.removeClass('right');
					div.addClass('top');
					$('body>.popover.arrow').css("top", "100%");
				} else {
					if (target.className == 'btn btn-default'){
						if(isShowRight){
							div.css("left", divOffsetX + 40);
						} else {
							div.css("left", divOffsetX + 10 - inputWidLeft);
						}
					} else if (target.className == 'icon-calendar') {
						if(isShowRight){
							div.css("left", divOffsetX -12 + 40);
						} else {
							div.css("left", divOffsetX -12 + 10 - inputWidLeft);
						}
					} else {
						if(isShowRight){
							div.css("left", divOffsetX + 40 + inputWidLeft);
						} else {
							div.css("left", divOffsetX + 10);
						}

					}
					div.css("top", event.clientY - div.innerHeight()/2 - layerY + inputWidHeight/2);
					div.removeClass('bottom');
					div.removeClass('top');
					div.addClass('right');
					$('body>.popover.arrow').css("top", "100%");
				}
			}
		}
		
		
		/**
		 * 清空时间
		 */
		var isemptyTime = false;
		self.emptyTime = function() {
			var dt = new Date();
			isemptyTime = true;
			self._value("");
			self.year(dt.getFullYear());
			self.quarter(Math.ceil((dt.getMonth()+1)/3));
			self.month(dt.getMonth() + 1);
			self.day(dt.getDate());
			self.hour(dt.getHours());
			self.minute(dt.getMinutes());
			self.second(dt.getSeconds());
			isemptyTime = false;
		}
		
		/**
		 * 确定时间
		 */
		self.confirmTime = function() {
			self._calendarchanged();
			self.isShowDialog(false);
		}
		
		var isMousedownInner = false;
		var isMouseover = false;
		/**
		 * @ignore
		 */
		self.mouseoverPopoverDialog = function() {
			isMouseover = true;
		}
		
		/**
		 * @ignore
		 */
		self.mousedownInnerPopoverDialog = function() {
			isMousedownInner = true;
			isMouseover = false;
		}
		
		/**
		 * @ignore
		 */
		self.onSelectDate = function() {
			if (cube.isEmpty(self._value())) {
				self._calendarchanged();
			}
		}
		
		/**
		 * 隐藏弹出框
		 */
		self.hiddenPopoverDialog = function() {
			if(isMousedownInner==false || isMouseover== true && isMousedownInner==true) {
				self.isShowDialog(false);
				self.calendarFocus(false);
			} else {
				self.calendarFocus(true);
			}
			isMousedownInner = false;
		}
		
		/**
		 * @ignore
		 */
		self._calendarchanged = function() {
			if (!isemptyTime && self._checkDate()) {
				var fvalue = parseDate(null, self.format(), true);
				self._value(fvalue);
			}
		}
		
		/**
		 * 检查日期是否在最大值和最小值范围内
		 * @ignore
		 */
		self._checkDate = function(){
			var minDateTime = null;
			var minValue = self.minValue();
			if (cube.notEmpty(minValue) && checkUtil.isDate(minValue)) {
				minDateTime = new Date(minValue).getTime();
			}
			
			var maxDateTime = null;
			var maxValue = self.maxValue();
			if (cube.notEmpty(maxValue) && checkUtil.isDate(maxValue)) {
				maxDateTime = new Date(maxValue).getTime();
			}
			
			var curDateTime = new Date(self.year() + "-" + self.month() + "-" + self.day()).getTime();
			if (minDateTime != null) {
				if (curDateTime < minDateTime) {
					return false;
				} 
			}
			
			if (maxDateTime != null) {
				if (curDateTime > maxDateTime) {
					return false;
				}
			}
			
			return true;
		}
		
		/**
		 * @ignore
		 */
		self._valuechanged = function(value) {
			if (!_valueChange) {
				if (cube.notEmpty(value)) {
					var fvalue = parseDate(value, self.format(), true);
					if (value != fvalue && fvalue == self._value()) {
						self._changed(fvalue);
					}
					
					self._value(fvalue);
				} else {
					self.emptyTime();
				}
				
				if (self.onChanged != null && !cube.isObservable(self.onChanged)) {
					self.onChanged(self.name(), self.value());
				}
			}
		}
		
		/**
		 * @ignore
		 */
		self._changed = function(value) {
			_valueChange = true;
			self.isShowPopDialog(false);
			
			if (cube.notEmpty(value)) {
				var fvalue = parseDate(value, self.format(), true);
				if(!isemptyTime){
					var result = valid(fvalue);
					if (result == false) {
						return;
					}
				}
				
				if (self.dataFormat() != null) {
					var d_value = parseDate(fvalue, self.dataFormat(), true, true);
					self.value(d_value);
				} else {
					self.value(fvalue);
				}
			} else {
				self.value("");
			}
			
			
			if (self.onChanged != null && !cube.isObservable(self.onChanged)) {
				self.onChanged(self.name(), self.value());
			}
			_valueChange = false;
		}

		/**
		 * @ignore
		 */
		self.dateClick = function() {
//			console.log("click: "+self.calendarFocus());
//			self.dateInputFocus(true);
		}
		
		/**
		 * @ignore
		 */
		self._valid = function(viewModel, event) {        
			if (self.isValid()) {
				valid(event.target.value);
			}
		}
		
		function valid(p_value) {	
			var validateResult; 
			var validType = self.validType();
			// 校验先走自定义校验.
	        if (self.customValidate != null && cube.isFunction(self.customValidate) && !cube.isObservable(self.customValidate)) {
	             validateResult = self.customValidate(p_value);
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
				return false;
	        }
	        
	        if ((cube.isEmpty(validType) || validType.indexOf("NOTNULL") == -1 ) && cube.isEmpty(p_value)) {
				return;
			}
	        
			if (validType.indexOf("NOTNULL") != -1 || cube.notEmpty(p_value)) {
				if (validType != null) {
					var result = validator.validate(p_value, validType);
					if (!result.successful) {
						self.validStatus("error");
						self.validHint(result.hint);
						
						if (self.validHintType() == "pop") {
							self.isShowPopDialog(false);
							if(!cube.isObservable(self.popNode) && self.popNode != null){
								self.popNode.remove();
							}
							self.isShowPopDialog(true);
							
							self.popNode = cube.showPopDialog($(_node), {
								content: result.hint,
								popoverDirection: "bottom",
								msgType: "error",
								isShowDialog: self.isShowPopDialog
							});
							
						}
						
						var validFailValue = self.validFailValue();
						if (validFailValue == "empty") {
							self.value("");
							self._value("");
						} else if (validFailValue == "current") {
							self.value(parseDate(new Date(), self.format(), true));
						} else {
							self.value(p_value);
						}
						
						return false;
						
					} else {
						self.validStatus("");
						self.validHint("");
						self.isShowPopDialog(false);
					}
				}
			}
		}
		
		function computeLeftOffset() {
			var lef = self.$e.find(".popover.info").offset().left;
			var wid = self.$e.find(".popover.info").width();
			var windwid = $(window).width();
			if ((lef + wid) > windwid)
			{
				var osleft = lef + wid - windwid + 15;
				self.leftOffset("-" + osleft + "px");
			}
		};
		
		function parseDate(_value, _format, isShowValue, notSetDate) {
			if (cube.notEmpty(_value)) {
				var dt = null;
				
				if (checkUtil.isTime(_value)) {
					return _value;
				} else if (!cube.isDate(_value)) {
					if (!cube.isString(_value)) {
						_value = String(_value);
					}
					
					dt = new Date(_value.replace(/-/g,"/"));
					
					if (isNaN(dt.getMonth())) {
						_value = _value.replace(/-/g,"");
						_value = _value.replace(/\s*/g,"");
						_value = _value.replace(/:/g,"");
					}
					
					if (checkUtil.isDigit(_value)) {
						//无法判断_value为10位毫秒数还是2018110913的时间串，临时使用首位数字判断
						if (_value.length == 10) {
							if (String(_value).substring(0, 1) == "1") {
								_value = _value + "000";
							} else {
								_value = _value.substring(0, 4) + "/" +_value.substring(4, 6) + "/" +_value.substring(6, 8) + " " + _value.substring(8, 10) + ":00:00";
								dt = new Date(_value);
							}
						} else if (_value.length == 6) {
							_value = _value.substring(0, 4) + "/" +_value.substring(4, 6) + "/01";
							dt = new Date(_value);
						} else if (_value.length == 8) {
							_value = _value.substring(0, 4) + "/" +_value.substring(4, 6) + "/" +_value.substring(6, 8);
							dt = new Date(_value);
						} else if (_value.length == 12) {
							_value = _value.substring(0, 4) + "/" +_value.substring(4, 6) + "/" +_value.substring(6, 8) + " " + _value.substring(8, 10) + ":" + _value.substring(10, 12) + ":00";
							dt = new Date(_value);
						} else if (_value.length == 14) {
							_value = _value.substring(0, 4) + "/" +_value.substring(4, 6) + "/" +_value.substring(6, 8) + " " + _value.substring(8, 10) + ":" + _value.substring(10, 12) + ":" + _value.substring(12, 14);
							dt = new Date(_value);
						} 
						
						if (_value.length == 13) {
							dt = new Date(parseInt(_value));
						}
					}
				} else {
					dt = _value;
				}
				
				if (typeof(notSetDate) == "undefined") {
					if (isNaN(dt.getMonth())) {
						if (checkUtil.isDateTime(_value)) {
							var _time = "";
							var _valueAray = _value.split(" ");
							if (_valueAray[1].length == 2) {
								_time = _valueAray[1] + ":00:00";
							} else if(_valueAray[1].length == 4){
								_time = _valueAray[1] + ":00";
							}
							
							dt = new Date(_valueAray[0].replace(/-/g,"/") + " " +_time);
						} else {
							return _value;
						}
					}
					
					self.year(dt.getFullYear());
					self.quarter(Math.ceil((dt.getMonth()+1)/3));
					self.month(dt.getMonth() + 1);
					self.day(dt.getDate());
					self.hour(dt.getHours());
					self.minute(dt.getMinutes());
					self.second(dt.getSeconds());
				}
				
			} else if(!isShowValue) {
				return "";
			}
			
			_format = _format.replace(/yyyy/g, self.year());

			if(self.showQuarterPicker()){
				_format = _format.replace(/MM/g, numberFormat(self.quarter(), "01"));
			}else{
				_format = _format.replace(/MM/g, numberFormat(self.month(), "00"));
			}
			_format = _format.replace(/dd/g, numberFormat(self.day(), "00"));
			_format = _format.replace(/HH/g, numberFormat(self.hour(), "00"));
			_format = _format.replace(/mm/g, numberFormat(self.minute(), "00"));
			_format = _format.replace(/ss/g, numberFormat(self.second(), "00"));
			
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
	    	self.$e = $(node);
	    	
	    	if(self.isExtDisplay()){
	    		window.onmousewheel  = document.onmousewheel = function(){
	    			self.hiddenPopoverDialog();
	    		}
	    	}
	    	
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node, viewModel);
			}
	    }
		
		cube.endViewModel(self, params);

	}
	
	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 	   this.isShowPopDialog(false);
 	   this._valueSub.dispose();
 	   this.valueSub.dispose();
 	   this.nullableSub.dispose();
 	   this.yearSub.dispose();
 	   this.quarterSub.dispose();
 	   this.monthSub.dispose();
 	   this.daySub.dispose();
 	   this.hourSub.dispose();
 	   this.minuteSub.dispose();
 	   this.secondSub.dispose();
 	   this.formatSub.dispose();
 	   this.hasFocusSub.dispose();
 	}

	return ViewModel;
});