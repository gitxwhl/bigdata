define(["echarts"], function(echarts) {

	/**
	 * 图表组件，基于echarts
	 * @example
	 * 
	 * 
	 * bar柱状图 line折现图
	 * <code language="html">
	 *		<chart style="width:1250px; height:400px;" 
	 *		params="
	 *			width:width,
	 *			height:height,
	 *			title:title,
	 *			tooltip:tooltip,
	 *			legend:legend,
	 *			grid:grid,
	 *			toolbox:toolbox,
	 *			xAxis:xAxis, 
	 *			yAxis:yAxis, 
	 *			series:series">
	 *		</chart>
	 * </code>
	 * 
	 * <code language="JavaScript">
	 *	define([], function() {
	 *		var PageViewModel = function(params) {
	 *			var self = this;
	 *			self.width = '1000px';
	 *			self.height = '400px';
	 *			self.title = {
	 *					text: '统计图'
	 *			};
	 *			self.tooltip = {
	 *					trigger: 'axis'
	 *			};
	 *			self.legend = {
	 *					data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
	 *			};
	 *			self.grid =  {
	 *					main: '3%',
	 *					right: '4%',
	 *					bottom: '3%',
	 *					containLabel: true
	 *			};
	 *			self.toolbox = {
	 *					feature: {
	 *						saveAsImage: {}
	 *					}
	 *			};
	 *	
	 *			self.xAxis =  {
	 *								type: 'category',
	 *								data: ['周一','周二','周三','周四','周五','周六','周日']
	 *							};
	 *			self.yAxis =  {
	 *				type: 'value'
	 *			};
	 *			self.series = [
	 *							{
	 *									name:'邮件营销',
	 *									type:'bar',
	 *						 
	 *									data:[120, 132, 101, 134, 90, 230, 210]
	 *								},
	 *								{
	 *									name:'联盟广告',
	 *									type:'bar',
	 *						 
	 *									data:[220, 182, 191, 234, 290, 330, 310]
	 *								},
	 *								{
	 *									name:'视频广告',
	 *									type:'bar',
	 *									data:[150, 232, 201, 154, 190, 330, 410]
	 *								},
	 *								{
	 *									name:'直接访问',
	 *									type:'bar',
	 *									data:[320, 332, 301, 334, 390, 330, 320]
	 *								},
	 *								{
	 *									name:'搜索引擎',
	 *									type:'bar',
	 *									data:[820, 932, 901, 934, 1290, 1330, 1320]
	 *								}
	 *							];
	 *			
	 *			cube.endViewModel(self, params);
	 *		};
	 *		return PageViewModel;
	 *	});
	 * </code>
	 * 
	 * 
	 * line折现图 bar柱状图
	 * <code language="html">
	 *		<chart style="width:1250px; height:400px;" 
	 *		params="
	 *			width:width,
	 *			height:height,
	 *			xAxis:xAxis, 
	 *			yAxis:yAxis, 
	 *			series:series">
	 *		</chart>
	 * </code>
	 * <code language="JavaScript">
	 *	define([], function() {
	 *		var PageViewModel = function(params) {
	 *			var self = this;
	 *			self.width = '1000px';
				self.height = '400px';
	 *	
	 *			self.xAxis = {
	 *					type: 'category',
	 *					data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
	 *			};
	 *			self.yAxis =  {
	 *				type: 'value'
	 *			};
	 *			self.series =  [{
	 *				data: [120, 200, 150, 80, 70, 110, 130],
	 *				type: 'bar'
	 *			}];
	 *			
	 *			cube.endViewModel(self, params);
	 *		};
	 *		return PageViewModel;
	 *	});
	 * </code>
	 * 
	 * pie饼型图	设置radius: ['50%', '70%'],为环形
	 * <code language="html">
	 *		<chart style="width:1250px; height:400px;" 
	 *		params="
	 *			width:width,
	 *			height:height,
	 *			title:title,
	 *			tooltip:tooltip, 
	 *			legend:legend, 
	 *			series:series">
	 *		</chart>
	 * </code>
	 * <code language="JavaScript">
	 *	define([], function() {
	 *		var PageViewModel = function(params) {
	 *			var self = this;
	 *			self.width = '1000px';
	 *			self.height = '400px';
	 *			self.title = {
	 *				text: '统计图',
	 *				subtext: '纯属虚构',
	 *				x:'center'
	 *			};
	 *			self.tooltip = {
	 *				trigger: 'item',
	 *				formatter: "{a} <br/>{b} : {c} ({d}%)"
	 *			};
	 *			self.legend = {
	 *				orient: 'vertical',
	 *				main: 'main',
	 *				data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
	 *			};
	 *			self.series = [
	 *				{
	 *					name: '访问来源',
	 *					type: 'pie',
	 *					radius : '55%',
	 *					center: ['50%', '50%'],
	 *					data:[
	 *						{value:335, name:'直接访问'},
	 *						{value:310, name:'邮件营销'},
	 *						{value:234, name:'联盟广告'},
	 *						{value:135, name:'视频广告'},
	 *						{value:1548, name:'搜索引擎'}
	 *					],
	 *					itemStyle: {
	 *						emphasis: {
	 *							shadowBlur: 10,
	 *							shadowOffsetX: 0,
	 *							shadowColor: 'rgba(0, 0, 0, 0.5)'
	 *						}
	 *					}
	 *				}
	 *			];
	 *			
	 *			cube.endViewModel(self, params);
	 *		};
	 *		return PageViewModel;
	 *	});
	 * </code>
	 * 
	 * 图表数据完整传入
	 * <code language="html">
	 *	<chart  style="width:1000px; height:400px;" 
	 *	params="
	 *			width:width,
	 *			height:height,
	 *			option:option">
	 *	</chart>
	 * </code>
	 * <code language="JavaScript">
	 *	define([], function() {
	 *		var PageViewModel = function(params) {
	 *			var self = this;
	 *			self.width = '1000px';
	 *			self.height = '400px';
	 *			self.option = {
	 *					title : {
	 *						text: '某站点用户访问来源',
	 *						subtext: '纯属虚构',
	 *						x:'center'
	 *					},
	 *					tooltip : {
	 *						trigger: 'item',
	 *						formatter: "{a} <br/>{b} : {c} ({d}%)"
	 *					},
	 *					legend: {
	 *						orient: 'vertical',
	 *						main: 'main',
	 *						data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
	 *					},
	 *					series : [
	 *						{
	 *							name: '访问来源',
	 *							type: 'pie',
	 *							radius : '55%',
	 *							center: ['50%', '60%'],
	 *							data:[
	 *								{value:335, name:'直接访问'},
	 *								{value:310, name:'邮件营销'},
	 *								{value:234, name:'联盟广告'},
	 *								{value:135, name:'视频广告'},
	 *								{value:1548, name:'搜索引擎'}
	 *							],
	 *							itemStyle: {
	 *								emphasis: {
	 *									shadowBlur: 10,
	 *									shadowOffsetX: 0,
	 *									shadowColor: 'rgba(0, 0, 0, 0.5)'
	 *								}
	 *							}
	 *						}
	 *					]
	 *				};
	 *			
	 *			cube.endViewModel(self, params);
	 *		};
	 *		return PageViewModel;
	 *	});
	 * </code>
	 */

	function ViewModel(params) {
		
			var self = this;
			/**
			 * 标题组件，包含主标题和副标题。
			 * 
			 * @default 空
			 */		
			self.title = {};
			
			/**
			 * 宽度 设置值为字符
			 * @default	auto
			 */
			self.width = 'auto';
			
			/**
			 * 高度 设置值为字符
			 * @default 400px
			 */
			self.height = '400px';
			
			/**
			 * 提示框组件。
			 * 
			 * @default 空
			 */
			self.tooltip = {};
			
			/**
			 * 图例组件展现了不同系列的标记(symbol)，颜色和名字。可以通过点击图例控制哪些系列不显示。
			 * 
			 * @default 空
			 */
			self.legend = {};
			
			/**
			 * 直角坐标系内绘图网格，单个 grid 内最多可以放置上下两个 X 轴，左右两个 Y 轴。
			 * 
			 * @default 空
			 */
			self.grid = {};
			
			/**
			 * 工具栏。
			 * 
			 * @default 空
			 */
			self.toolbox = {};
			
			/**
			 * 直角坐标系 grid 中的 x 轴，一般情况下单个 grid 组件最多只能放上下两个 x 轴，多于两个 x 轴需要通过配置 offset 属性防止同个位置多个 x 轴的重叠。
			 * 
			 * @default 空
			 */
			self.xAxis = null;
			
			/**
			 * 直角坐标系 grid 中的 y 轴，一般情况下单个 grid 组件最多只能放左右两个 y 轴，多于两个 y 轴需要通过配置 offset 属性防止同个位置多个 Y 轴的重叠。
			 * 
			 * @default 空
			 */
			self.yAxis = null;
			
			/**
			 * 系列列表。每个系列通过 type 决定自己的图表类型
			 * 
			 * @default 空
			 */
			self.series = [];
			
			/**
			 * @ignore
			 */
			self._width = "";
			
			/**
			 * @ignore
			 */
			self._height = "";
			
			/**
			 * 传入完整的图表数据
			 * 
			 * @default 空
			 */
			self.option = {};
			
			/**
			 * 图表对象
			 * @default null
			 */
			self.chart = null;
			
			/**
			 * 组件渲染完成事件
			 */
			self.onRendered = null;
			
			/**
			 * @ignore
			 */			
			self._init = function() {
				if (self.width()) {
					self._width(self.width());
				}
				
				if (self.height()) {
					self._height(self.height());
				}
				
				self.widthSub = cube.subscribe(self.width, function(p_width) {
						self._width(p_width);
				});
				
				self.heightSub = cube.subscribe(self.height, function(p_height) {
						self._height(p_height);
				});
				
				self.titleSub = cube.subscribe(self.title, function(p_title) {
					self.setOption({
						title: self.title()
					});
				});
				
				self.tooltipSub = cube.subscribe(self.tooltip, function(p_tooltip) {
					self.setOption({
						tooltip: self.tooltip()
					});
				});
				
				self.legendSub = cube.subscribe(self.legend, function(p_legend) {
					self.setOption({
						legend: self.legend()
					});
				});
				
				self.gridSub = cube.subscribe(self.grid, function(p_grid) {
					self.setOption({
						grid: self.grid()
					});
				});
				
				self.toolboxSub = cube.subscribe(self.toolbox, function(p_toolbox) {
					self.setOption({
						toolbox: self.toolbox()
					});
				});
				
				self.xAxisSub = cube.subscribe(self.xAxis, function(p_xAxis) {
					self.setOption({
						xAxis: self.xAxis()
					});
				});
				
				self.yAxisSub = cube.subscribe(self.yAxis, function(p_yAxis) {
					self.setOption({
						yAxis: self.yAxis()
					});
				});
				
				self.seriesSub = cube.subscribe(self.series, function(p_series) {
					self.setOption({
						series: self.series()
					});
				});
				
				self.optionSub = cube.subscribe(self.option, function(p_option) {
					self.setOption(self.option());
				});
			};
			
			/**
			 * 设置option，将会与图表已有option合并后设置，参考echarts的option
			 */
			self.setOption = function (p_option) {
				var option = self.chart.getOption();
				$.extend(option, p_option);
				self.chart.setOption(option);
			};
			
			/**
			 * @ignore
			 */
			self.onload = function(node) {
				if($.isEmptyObject(this.option())){
					self.chart = echarts.init($(node).children(":first").get(0));
					self.chart.setOption({
							title:self.title(),
							tooltip:self.tooltip(),
							legend:self.legend(),
							grid:self.grid(),
							toolbox:self.toolbox(),
							xAxis: self.xAxis(),
							yAxis: self.yAxis(),
							series: self.series()
						});
				} else {
					self.chart = echarts.init($(node).children(":first").get(0));
					self.chart.setOption(
								self.option()
						);
				}		
				
				if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
					self.onRendered(node);
				}
			};	
		cube.endViewModel(self, params);	
	}
	
	ViewModel.prototype.dispose = function() {
		this.widthSub.dispose();
		this.heightSub.dispose();
		
		this.titleSub.dispose();
		this.tooltipSub.dispose();
		this.legendSub.dispose();
		this.gridSub.dispose();
		this.toolboxSub.dispose();
		this.xAxisSub.dispose();
		this.yAxisSub.dispose();
		this.seriesSub.dispose();
		this.optionSub.dispose();
	};
	
	return ViewModel;
});