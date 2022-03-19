define(["CheckUtil"], function(checkUtil) {

	/**
	 * 日期时间选择组件
	 * 说明：本控件通过输入年月日时分秒实现对日历当前日期的修改，此为可选输入项，若不输入则默认显示当前日期。
	 *      本控件不对输入的年月日时分秒进行数值合理范围校验，由控件调用方负责保证输入参数的正确性。
	 *      
	 * @example
	 * <code language="html">
	 * 		<calendar params="showTimePicker: showTimePicker,
	 *						showDayPicker: showDayPicker,
	 *						year: year,
	 *						month: month,
	 *						day: day,
	 *						hour: hour,
	 *						minute: minute,
	 *						second: second"></calendar>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 						
	 * 				self.showTimePicker = true;
	 *  			self.showDayPicker = true;
	 *   			self.year = "";
	 *    			self.month = "";
	 *     			self.day = "";
	 *      		self.hour = "";	
	 *      		self.minute = "";	
	 *      		self.second = "";
	 *      
	 *				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 * 
	 */
 	function ViewModel(params) {
		var self = this;
				
		/**
		 * @ignore
		 */
		self.date = new Date();

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
		 * 是否显示月份和日期选择框，默认显示
		 *  @default true
		 */
		self.showMonthPicker = true;
		
		/**
		 * 是否显示季度选择框，同时月份和日期选择框不显示，默认显示
		 *  @default false
		 */
		self.showQuarterPicker = false;
		
		/**
		 * 年
		 * @default 当前年
		 */
		self.year = self.date.getFullYear();

		/**
		 * 季
		 * @default 当前季
		 */
		self.quarter = Math.ceil((self.date.getMonth()+1)/3);
		
		/**
		 * 月
		 * @default 当前月
		 */
		self.month = self.date.getMonth()+1;

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
		 * 是否单击日期时，关闭时间选择框
		 * @default false
		 */
		self.isClickCloseDialog = false;
		
		/**
		 * 可选择的最小日期
		 * @default null
		 */
		self.minValue = null;
		
		/**
		 * 可选择的最大日期
		 * @default null
		 */
		self.maxValue = null;
		
		/**
		 * 显示星期，暂时不考虑false的情况
		 * @ignore
		 */
		self.isWest = true; 
		
		/**
		 * @ignore
		 */
		self.weekDays = ["一","二","三","四","五","六","日"]; 
		
		/**
		 * @ignore
		 */
		self.westWeekDays = ["日","一","二","三","四","五","六"];
		
		/**
		 * 季度
		 * @ignore
		 */
		self.yearQuarters = ["第一季度","第二季度","第三季度","第四季度"];
	
		/**
		 * 月份
		 * @ignore
		 */
		self.yearMonths = ["01月","02月","03月","04月","05月","06月","07月","08月","09月","10月","11月","12月"];
		
		/**
		 * 小时
		 * @ignore
		 */
		self.Hours = ["00","02","04","06","08","10","12","14","16","18","20","22"];

		/**
		 * 分、秒
		 * @ignore
		 */
		self.Minutes = ["00","05","10","15","20","25","30","35","40","45","50","55"];
		
		/**
		 * 从上一年的12月，1-12月，到次年的1月份，每个月的天数
		 *  @ignore
		 */
		self.monthTotalDays = [31,31,28,31,30,31,30,31,31,30,31,30,31,31];
		
		/**
		 * 本月日期
		 * @ignore
		 */
		self.thisMonthDay = cube.obj({year:1, month:1, dayNums: 1});

		/**
		 * 上个月日期
		 * @ignore
		 */
		self.preMonthDay = cube.obj({year:1, month:1, dayNums: 1});
		
		/**
		 * 本月日期
		 * @ignore
		 */
		self.nextMonthDay = cube.obj({year:1, month:1, dayNums: 1});

		/**
		 * 日期和时间的切换
		 * @ignore
		 */
		self.showCalendar = cube.obj(1);

		/**
		 * 点选时分秒的切换
		 *  @ignore
		 */
		self.timePickType = cube.obj(0);
		
		/**
		 * 设置title显示内容
		 * @ignore
		 */
		self.showState = cube.obj(-1);//0:月，1：选择月，2：选择年
		
		/**
		 * 是否显示，从datetimeeditor传递
		 * @ignore
		 */
		self.isShowDialog = null;
		
		/**
		 * @ignore
		 */
		self.titleText = cube.comp(function(){
			if(self.showState()==0) {
				return  self.year()+'年'+ self.month()+'月';
			} else if(self.showState()==1 || self.showState()==3) {
				return self.year()+'年';
			}
			else if(self.showState()==2) {
				return (self.year()-6)+'年-'+(parseInt(self.year(), 10)+5)+'年';
			}
		},self);
		
		/**
		 * @ignore
		 */
		self.onSelectDate = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			self.date = new Date();
			
			self.validYear();
			self.validQuarter();
			self.validMonth();
			self.validDay();
			self.validHour();
			self.validMinute();
			self.validSecond();
			
			if (self.showTimePicker() == false && self.showDayPicker() == false && 
					self.showMonthPicker() == false && self.showQuarterPicker() == false) {
				self.showState(2);
			}
			
			self.changeShowState();
			
			self.yearSub = cube.subscribe(self.year, function(p_year) {
				self.validYear();
				calculateShowDateItems();
				calculateShowYearItems();
			});

			self.quarterSub = cube.subscribe(self.quarter, function(p_quarter) {
				self.validQuarter();
				if (isNormalize(p_quarter)) {
//					calculateShowDateItems();
//					calculateShowMonthItems();
					calculateShowQuarterItems();
				}
			});
	
			self.monthSub = cube.subscribe(self.month, function(p_month) {
				self.validMonth();
				if (isNormalize(p_month)) {
					calculateShowDateItems();
					calculateShowMonthItems();
				}
			});
	
			self.daySub = cube.subscribe(self.day, function(p_day) {
				self.validDay();
				calculateShowDateItems();
			});
			
			self.hourSub = cube.subscribe(self.hour, function(p_hour) {
				self.validHour();
			});
			
			self.minuteSub = cube.subscribe(self.minute, function(p_minute) {
				self.validMinute();
			});
			
			self.secondSub = cube.subscribe(self.second, function(p_second) {
				self.validSecond();
			});
			
			self.minValueSub = cube.subscribe(self.minValue, calculateShowDateItems);
			
			self.maxValueSub = cube.subscribe(self.maxValue, calculateShowDateItems);
			
			self.initDateItems();
			self.initMonthItems();
			self.initQuarterItems();
			self.initYearItems();
			self.initHourItems();
			self.initMinuteItems();
			
			calculateShowDateItems();
			
			if (self.showState() == 0) {
				if (self.showDayPicker() && self.showMonthPicker() && !self.showQuarterPicker()){
					self.showState(0);
				}else if(self.showMonthPicker() && !self.showQuarterPicker()){
					self.showState(1);
				}else if(!self.showMonthPicker() && !self.showQuarterPicker()){
					self.showState(2);
				}else if(self.showQuarterPicker()){
					self.showState(2);
				}else{
					self.showState(0);
				}					
			}
		};
		
		/**
		 * 改变title显示状态。年/月/日选择
		 * @ignore
		 */
		self.changeShowState = function() {
			self.showState(self.showState()+1);
			if (self.showState() == 1)
				calculateShowMonthItems();		
			else if (self.showState() == 2)
				calculateShowYearItems();
			else if(self.showState() == 3) {
				if (self.showDayPicker() && self.showMonthPicker() && !self.showQuarterPicker()){
					self.showState(0);
				}else if(self.showMonthPicker() && !self.showQuarterPicker()){
					self.showState(1);
				}else if(self.showQuarterPicker()){
					self.showState(3);
					calculateShowQuarterItems();
				}else{
					self.showState(2);
				}	
			}else if(self.showQuarterPicker()){
				self.showState(2);
			}else{
				self.showState(0);
			}	
		};

		/**
		 * 显示日历
		 */
		self.changeShowCalendar = function() {
			if (self.showCalendar() == 1)
				self.showCalendar(0);
			else
				self.showCalendar(1);
		};

		/**
		 * @ignore
		 */
		self.hourUp = function() {
			if (self.hour() > 22)
				self.hour(0);
			else
				self.hour(parseInt(self.hour(), 10)+1);
		};

		/**
		 * @ignore
		 */
		self.hourDown = function() {
			if (self.hour() < 1)
				self.hour(23);
			else
				self.hour(parseInt(self.hour(), 10)-1);
		};

		/**
		 * @ignore
		 */
		self.minuteUp = function() {
			if (self.minute() > 58) {
				self.minute(0);
				self.hourUp();
			} else
				self.minute(parseInt(self.minute(), 10)+1);
		};

		/**
		 * @ignore
		 */
		self.minuteDown = function() {
			if (self.minute() < 1) {
				self.minute(59);
				self.hourDown();
			} else
				self.minute(parseInt(self.minute(), 10)-1);
		};

		/**
		 * @ignore
		 */
		self.secondUp = function() {
			if (self.second() > 58) {
				self.second(0);
				self.minuteUp();
			} else
				self.second(parseInt(self.second(), 10)+1);
		};

		/**
		 * @ignore
		 */
		self.secondDown = function() {
			if (self.second() == 0) {
				self.second(59);
				self.minuteDown();
			} else {
				self.second(parseInt(self.second(), 10)-1);
			}		
		};

		/**
		 * @ignore
		 */
		self.onHourClick = function() {
			self.showCalendar(0);
			self.timePickType(1);
		};

		/**
		 * @ignore
		 */
		self.onMinuteClick = function() {
			self.showCalendar(0);
			self.timePickType(2);
		};

		/**
		 * @ignore
		 */
		self.onSecondClick = function() {
			self.showCalendar(0);
			self.timePickType(3);
		};

		var fillDate = function(year, month, day, quarter) {
			if (year != null) {
				self.year(year);
			}				
			if (month != null) {
				self.month(month);
			}
			if (day != null) {	
				self.day(day);
			}
			
			if (quarter != null) {	
				self.quarter(quarter);
			}
		};

		//根据年份、月份获取天数
		var getMonthTotalDays = function(year, month) {
			if(month == 2) {
				if((year % 4 == 0 && year % 100!= 0) || (year % 4 == 0 && year % 100== 0 && year % 400 == 0))
					return 29;
				else return 28;
			} else {
				return self.monthTotalDays()[month];
			}
				
		};
		
		//初始化上个月、这个月和下个月的全月信息
		var calculateMonthDays = function() {
			var cur_year = self.year();
			var cur_month = parseInt(self.month(), 10);
			var year, month, weekDay;
			
			self.date.setFullYear(cur_year);
			self.date.setMonth(cur_month-1);
			self.date.setDate(1);
			year = self.date.getFullYear();
			month = self.date.getMonth()+1;
			weekDay = self.date.getDay();
			self.thisMonthDay({year:year,month:month,dayNums:getMonthTotalDays(year,month),firstWeekDay: weekDay});
			
			self.date.setFullYear(cur_year);
			self.date.setMonth(cur_month-2);
			year = self.date.getFullYear();
			month = self.date.getMonth()+1;
			weekDay = self.date.getDay();
			self.preMonthDay({year:year,month:month,dayNums:getMonthTotalDays(year,month),firstWeekDay: weekDay});

			self.date.setFullYear(cur_year);
			self.date.setMonth(cur_month);
			year = self.date.getFullYear();
			month = self.date.getMonth()+1;
			weekDay = self.date.getDay();
			self.nextMonthDay({year:year,month:month,dayNums:getMonthTotalDays(year,month),firstWeekDay: weekDay});
		};
		
		//计算显示的内容
		var calculateShowDateItems = function() {
			
			calculateMonthDays();
			
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
			
			var weekInd = 0;
			var firstWeekDay = self.thisMonthDay().firstWeekDay;
			if(firstWeekDay == 0) firstWeekDay = 7;
			for(i=0;i<42;i++) {
				var curDate;
				if(i<firstWeekDay) {
					curDate = self.DateItems()[weekInd]()[i%7];
					curDate.monthInd(-1);
					curDate.year(self.preMonthDay().year);
					curDate.month(self.preMonthDay().month);
					curDate.day(self.preMonthDay().dayNums-(firstWeekDay-i)+1);
				} else if(i<firstWeekDay+self.thisMonthDay().dayNums){
					if( i % 7 == 0) {
						weekInd++;
					}
					curDate = self.DateItems()[weekInd]()[i%7];
					curDate.monthInd(0);
					curDate.year(self.thisMonthDay().year);
					curDate.month(self.thisMonthDay().month);
					curDate.day(i-firstWeekDay+1);
				} else {
					if( i % 7 == 0) {
						weekInd++;
					}
					curDate = self.DateItems()[weekInd]()[i%7];
					curDate.monthInd(1);
					curDate.year(self.nextMonthDay().year);
					curDate.month(self.nextMonthDay().month);
					curDate.day(i-firstWeekDay-self.thisMonthDay().dayNums+1);
				}
				
				//选择一个当前日期
				if((curDate.day() == self.day()) && 
					(curDate.month() == self.month()) &&
					(curDate.year() == self.year()) ) {
						self.selectDate(curDate);
				}
				
				curDate.disable(false);
				
				var curDateTime = new Date(curDate.year() + "-" + curDate.month() + "-" + curDate.day()).getTime();
				if (minDateTime != null) {
					if (curDateTime < minDateTime) {
						curDate.disable(true);
					} 
				}
				
				if (maxDateTime != null) {
					if (curDateTime > maxDateTime) {
						curDate.disable(true);
					}
				}
			}
		};
		
		//计算年份选择显示的内容
		var calculateShowYearItems = function() {
			for(var i=0;i<3;i++) {
				for(var j=0;j<4;j++)
				{
					var year = self.YearItems()[i]()[j];
					year.yearName(self.year()-6+(4*i+j));
					
					if (year.yearName() == self.year())
						year.isSelect(true);
					else
						year.isSelect(false);
				}
			}
		};

		//计算季度选择显示的内容
		var calculateShowQuarterItems = function() {
			for(var i=0;i<2;i++) {
				for(var j=0;j<2;j++) {
					var quarter = self.QuarterItems()[i]()[j];
					
					if (quarter.quarterInd == (self.quarter()-1))
						quarter.isSelect(true);
					else
						quarter.isSelect(false);
				}
			}
		};
		
		//计算月份选择显示的内容
		var calculateShowMonthItems = function() {
			for(var i=0;i<3;i++) {
				for(var j=0;j<4;j++) {
					var month = self.MonthItems()[i]()[j];
					
					if (month.monthInd == (self.month()-1))
						month.isSelect(true);
					else
						month.isSelect(false);
				}
			}
		};
		
		/**
		 * 初始化显示内容：DateItems
		 * @ignore
		 */
		self.DateItems = cube.array([]);
		/**
		 * @ignore
		 */
		self.selectDate = cube.obj();
		/**
		 * @ignore
		 */
		self.initDateItems = function() {
			for(var i=0;i<6;i++) {
				var item = cube.array([]);
				for(var j=0;j<7;j++) {
					var day = {
							year : cube.obj(1),
							month: cube.obj(1),
							day: cube.obj(1),
							weekInd: i,
							weekDay: j,
							monthInd: cube.obj(0), //-1:表示上个月，0表示这个月，1表示下个月
							
							isHover: cube.obj(false),
							disable: cube.obj(false),
							showHover: function() {
								if (!this.disable()) {
									this.isHover(true);
								}
							},
							hiddenHover: function() {
								this.isHover(false);
							},
							selectDay: function(d) {
								if (this.disable()) {
									return;
								}
								
								fillDate(this.year(),this.month(),this.day());
								if(this.monthInd()!=0) {
									calculateShowDateItems();
								}else {
									self.selectDate(this);
								}
								
								if (self.onSelectDate != null && !cube.isObservable(self.onSelectDate)) {
									self.onSelectDate(this);
								}
								
								if(self.isClickCloseDialog()){
									self.isShowDialog(false);
								}
							},
							selectDayAndClose: function(d) {
								if (this.disable()) {
									return;
								}
								
								fillDate(this.year(),this.month(),this.day());
								if(this.monthInd()!=0) {
									calculateShowDateItems();
								}else {
									self.selectDate(this);
								}
								
								self.isShowDialog(false);
							}
					};
					day.isSelect=cube.comp(function(){
						if (this.disable()) {
							return;
						}
						
						return (this.year() == self.year()) &&
						(this.month() == (self.month()))&&
						(this.day() == self.day());
					},day);
					item().push(day);
				}
				self.DateItems().push(item);
			}
		};
		
		/**
		 * 初始化显示内容：MonthItems
		 * @ignore
		 */
		self.MonthItems = cube.array([]);
		/**
		 * @ignore
		 */
		self.selectMonth = cube.obj();
		/**
		 * @ignore
		 */
		self.initMonthItems = function() {
			for(var i=0;i<3;i++) {
				var item = cube.array([]);
				for(var j=0;j<4;j++) {
					var month = {
							monthInd: i*4+j,
							monthName: self.yearMonths()[i*4+j],
							isSelect:cube.obj(false),
							isHover: cube.obj(false),
							showHover: function() {
								this.isHover(true);
							},
							hiddenHover: function() {
								this.isHover(false);
							},
							selectMonth: function(d) {
								if(self.selectMonth() != null)
									self.selectMonth().isSelect(false);
								this.isSelect(true);
								self.selectMonth(this);
								if (self.showDayPicker()){
									self.showState(0);
								}
								
								if (!self.showDayPicker() && self.isClickCloseDialog()){
									self.isShowDialog(false);
								}
								self.validMaxDay(this.monthInd+1);	
								fillDate(null,this.monthInd+1,null);
								calculateShowDateItems();
								this.isHover(false);
							}
					};
					item().push(month);
				}
				self.MonthItems().push(item);
			}
		}
		
		/**
		 * 初始化显示内容：QuarterItems
		 * @ignore
		 */
		self.QuarterItems = cube.array([]);
		/**
		 * @ignore
		 */
		self.selectQuarter = cube.obj();
		/**
		 * @ignore
		 */
		self.initQuarterItems = function() {
			for(var i=0;i<2;i++) {
				var item = cube.array([]);
				for(var j=0;j<2;j++) {
					var quarter = {
							quarterInd: i*2+j,
							quarterName: self.yearQuarters()[i*2+j],
							isSelect:cube.obj(false),
							isHover: cube.obj(false),
							showHover: function() {
								this.isHover(true);
							},
							hiddenHover: function() {
								this.isHover(false);
							},
							selectQuarter: function(d) {
								if(self.selectQuarter() != null)
									self.selectQuarter().isSelect(false);
								this.isSelect(true);
								self.selectQuarter(this);
								if (self.showDayPicker())
									self.showState(2);
								if (self.isClickCloseDialog())
									self.isShowDialog(false);
								fillDate(null,null,null, this.quarterInd+1);
								calculateShowDateItems();
								this.isHover(false);
							}
					};
					item().push(quarter);
				}
				self.QuarterItems().push(item);
			}
		}
		
		/**
		 * 初始化显示内容：YearItems
		 * @ignore
		 */
		self.YearItems = cube.array([]);
		/**
		 * 初始化显示内容：MonthItems
		 * @ignore
		 */
		self.selectYear = cube.obj();
		/**
		 * @ignore
		 */
		self.initYearItems = function() {
			for(var i=0;i<3;i++) {
				var item = cube.array([]);
				for(var j=0;j<4;j++) {
					var year = {
							yearName: cube.obj(self.year()-6+(4*i+j)),
							isSelect:cube.obj(false),
							isHover: cube.obj(false),
							showHover: function() {
								this.isHover(true);
							},
							hiddenHover: function() {
								this.isHover(false);
							},
							selectYear: function(d) {
								if(self.selectYear() != null)
									self.selectYear().isSelect(false);
								this.isSelect(true);
								self.selectYear(this);
								if (!self.showMonthPicker() && self.isClickCloseDialog() && !self.showQuarterPicker())
								self.isShowDialog(false);
								if (self.showMonthPicker() && !self.showQuarterPicker())
								self.showState(1);
								if(self.showQuarterPicker())
								self.showState(3);	
								this.isHover(false);
								fillDate(this.yearName(),null,null);
							}
					};
					item().push(year);
				}
				self.YearItems().push(item);
			}
		}

		/**
		 * 初始化显示内容：HourItems
		 * @ignore
		 */
		self.HourItems = cube.array([]);
		/**
		 * @ignore
		 */
		self.selectHour = cube.obj();
		/**
		 * @ignore
		 */
		self.initHourItems = function() {
			for(var i=0;i<3;i++) {
				var item = cube.array([]);
				for(var j=0;j<4;j++) {
					var hour = {
							hourInd: (i*4+j)*2,
							hourName: self.Hours()[i*4+j],
							isSelect:cube.obj(false),
							isHover: cube.obj(false),
							showHover: function() {
								this.isHover(true);
							},
							hiddenHover: function() {
								this.isHover(false);
							},
							selectHour: function(d) {
								if(self.selectHour() != null)
									self.selectHour().isSelect(false);
								this.isSelect(true);
								self.selectHour(this);
								self.timePickType(0);
								self.hour(this.hourInd);
								this.isHover(false);
								self.showCalendar(1);
							}
					};
					item().push(hour);
				}
				self.HourItems().push(item);
			}
		}
		
		/**
		 * 初始化显示内容：MinuteItems
		 * @ignore
		 */
		self.MinuteItems = cube.array([]);
		/**
		 * @ignore
		 */
		self.selectMinute = cube.obj();
		/**
		 * @ignore
		 */
		self.initMinuteItems = function() {
			for(var i=0;i<3;i++) {
				var item = cube.array([]);
				for(var j=0;j<4;j++) {
					var minute = {
							minuteInd: (i*4+j)*5,
							minuteName: self.Minutes()[i*4+j],
							isSelect:cube.obj(false),
							isHover: cube.obj(false),
							showHover: function() {
								this.isHover(true);
							},
							hiddenHover: function() {
								this.isHover(false);
							},
							selectMinute: function(d) {
								if(self.selectMinute() != null)
									self.selectMinute().isSelect(false);
								this.isSelect(true);
								self.selectMinute(this);
								self.timePickType(0);
								self.minute(this.minuteInd);
								this.isHover(false);
								self.showCalendar(1);
							},
							selectSecond: function(d) {
								if(self.selectMinute() != null)
									self.selectMinute().isSelect(false);
								this.isSelect(true);
								self.selectMinute(this);
								self.timePickType(0);
								self.second(this.minuteInd);
								this.isHover(false);
								self.showCalendar(1);
							}
					};
					item().push(minute);
				}
				self.MinuteItems().push(item);
			}
		}
		
		/**
		 * 显示上个月/上年/上个年份范围
		 * @ignore
		 */
		self.showPre = function() {
			if(self.showState()==0) {
				if (self.month() == 1) {
					self.month(12);
					self.year(self.year()-1);
				} else {
					var _month = parseInt(self.month())-1;
					self.validMaxDay(_month);
					self.month(self.month()-1);
				}
				calculateShowDateItems();
			} else if(self.showState()==1 || self.showState()==3) {
				self.year(self.year()-1);
			} else if(self.showState()==2) {
				self.year(self.year()-12);
				
				calculateShowYearItems();
			}
		};
		
		/**
		 * 显示下个月/下年/下个年份范围
		 * @ignore
		 */
		self.showNext = function() {
			if(self.showState()==0) {
				if (self.month() == 12) {
					self.month(1);
					self.year(parseInt(self.year())+1);
				} else {
					var _month = parseInt(self.month())+1;
					self.validMaxDay(_month);					
					self.month(parseInt(self.month())+1);
				}	
				calculateShowDateItems();
			} else if(self.showState()==1 || self.showState()==3) {
				self.year(parseInt(self.year())+1);
			}else if(self.showState()==2) {
				self.year(parseInt(self.year())+12);

				calculateShowYearItems();
			}
		};
		
		/**
		 * @ignore
		 */
		self.validYear = function() {
			if (!checkUtil.isPlusInteger(self.year())) {
				self.year(self.date.getFullYear());
			}
		}

		/**
		 * @ignore
		 */
		var isQuarterNull = false;
		self.validQuarter = function() {
			var _quarter = self.quarter();
			if (cube.isEmpty(_quarter)) {
				isQuarterNull = true;
			}
			if (isQuarterNull && cube.notEmpty(_quarter) && (_quarter < 1 || _quarter > 4)) {
				_quarter = _quarter.substring(0, _quarter.length-1);
				self.quarter(_quarter);
				isQuarterNull = false;
			} else if (!checkUtil.isPlusInteger(_quarter) || _quarter < 1 || _quarter > 4) {
				self.quarter(self.date.getQuarter());
			}
		}
		
		/**
		 * @ignore
		 */
		var isMonthNull = false;
		self.validMonth = function() {
			var _month = self.month();
			if (cube.isEmpty(_month)) {
				isMonthNull = true;
			}
			if (isMonthNull && cube.notEmpty(_month) && (_month < 1 || _month > 12)) {
				_month = _month.substring(0, _month.length-1);
				self.month(_month);
				isMonthNull = false;
			} else if (!checkUtil.isPlusInteger(_month) || _month < 1 || _month > 12) {
				self.month(self.date.getMonth());
			}
		}
		
		/**
		 * @ignore
		 */
		var isDayNull = false;
		self.validDay = function() {
			var _day = self.day();
			var _month = self.month();
			var _maxDay = 30;
			if (_month == 2) {
				_maxDay = 28;
			} else if (_month == 1 || _month == 3 || _month == 5 || _month == 7 || _month == 8 || _month == 10 || _month == 12) {
				_maxDay = 31;
			}
			
			if (cube.isEmpty(_day)) {
				isDayNull = true;
			}
			if (isDayNull && cube.notEmpty(_day) && (_day < 1 || _day > _maxDay)) {
				_day = _day.substring(0, _day.length-1);
				isDayNull = false;
			} else if (!checkUtil.isPlusInteger(_day) || _day < 1 || _day > _maxDay) {
				self.day(self.date.getDate());
			}
		}
		
		/**
		 * @ignore
		 */
		var isHourNull = false;
		self.validHour = function() {
			var _hour = self.hour();
			if (cube.isEmpty(_hour)) {
				isHourNull = true;
			}
			if (isHourNull && cube.notEmpty(_hour) && (_hour < 0 || _hour > 23)) {
				_hour = _hour.substring(0, _hour.length-1);
				self.hour(_hour);
				isHourNull = false;
			} else if (!checkUtil.isPlusInteger(_hour) || _hour < 0 || _hour > 23) {
				self.hour(self.date.getHours());
			}
		}
		
		/**
		 * @ignore
		 */
		var isMinuteNull = false;
		self.validMinute = function() {
			var _minute = self.minute();
			if (cube.isEmpty(_minute)) {
				isMinuteNull = true;
			}
			if (isMinuteNull &&  cube.notEmpty(_minute) && (_minute < 0 || _minute > 59)) {
				_minute = _minute.substring(0, _minute.length-1);
				self.minute(_minute);
				isMinuteNull = false;
			} else if (!checkUtil.isPlusInteger(_minute) || _minute < 0 || _minute > 59) {
				self.minute(self.date.getMinutes());
			}
		}
		
		/**
		 * @ignore
		 */
		var isSecondNull = false;
		self.validSecond = function() {
			var _second = self.second();
			if (cube.isEmpty(_second)) {
				isSecondNull = true;
			}
			if (isSecondNull && cube.notEmpty(_second) && (_second < 0 || _second > 59)) {
				_second = _second.substring(0, _second.length-1);
				self.second(_second);
				isSecondNull = false;
			} else if (!checkUtil.isPlusInteger(_second) || _second < 0 || _second > 59){
				self.second(self.date.getSeconds());
			}
		}
		
		/**
		 * @ignore
		 */
		self.validMaxDay = function(p_month) {
			var _day = self.day();
			var _month = p_month;
			var _maxDay = 30;
			var _year = self.year();														
			if (_month == 2) {
				if((_year % 4 == 0 && _year % 100!= 0) || (_year % 4 == 0 && _year % 100== 0 && _year % 400 == 0)){
					_maxDay = 29;
				} else {
					_maxDay = 28;		
				}
			} else if (_month == 1 || _month == 3 || _month == 5 || _month == 7 || _month == 8 || _month == 10 || _month == 12) {
				_maxDay = 31;
			}					
			if( _day > _maxDay) {
				self.day(_maxDay);
			}
		}
		
		function isNormalize(p_int) {
			var integer = parseInt(p_int);
			
			if (isNaN(integer) ||  (integer < 0) || 
				(integer > 12))
				return false;
			else 
				return true;
		}
		
		/**
	     * @ignore
	     */
		self.onload = function(node) {
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		}
		
		cube.endViewModel(self, params);
	}
	
	ViewModel.prototype.dispose = function() {
		this.yearSub.dispose();
		this.quarterSub.dispose();
		this.monthSub.dispose();
		this.daySub.dispose();
		this.hourSub.dispose();
		this.minuteSub.dispose();
		this.secondSub.dispose();
		this.minValueSub.dispose();
		this.maxValueSub.dispose();
	};
	
	return ViewModel;
});