define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
		self.content = '数据加载中';
		self.isShow = ko.observable("")
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
        self.Name='';//标题
        self.time = ko.observable(s2 + ' 00:00:00');//预警计划时间
		self.endTime=ko.observable(s2 + ' 23:59:59');//预警截至时间
		//数据
		// var XName= ["day30", "day29", "day28", "day27", "day26", "day25", "day24", "day23", "day22", "day21", "day20", "day19", "day18", "day17", "day16", "day15", "day14", "day13", "day12", "day11", "day10", "day9", "day8", "day7", "day6", "day5", "day4", "day3", "day2", "day1", "day0"]
		var Xname=[]
		var data1 = []
		var Line = ["人身事件","设备事件","电网事件","网络信息事件"];
		var color =['#00f8ff','#00f15a','#0696f9','#dcf776','#00f8ff'];

        var list=[]
		var list2=[]
		var list3=[]
		var list4=[]
		var list5=[]
		//数据处理
		// 		var datas = [];
		// 		Line.map((item,index)=>{
		// 			datas.push(
		// 				{
		// 					symbolSize: 10,
		// 					name: item,
		// 					type: "line",
		// 					yAxisIndex: 1,
		// 					data: data1[index] ,
		// 					itemStyle: {
		// 						normal: {
		// 							borderWidth: 5,
		// 							color: color[index],
		// 						}
		// 					}
		// 				}
		// 			)
		// 		})
		//
		// self.option = {
		// 	tooltip:{
		// 		trigger: 'axis'
		// 	},
		// 	grid: {
		// 		left: '5%',
		// 		top: '5%',
		// 		bottom: '7%',
		// 		right: '5%',
		// 	},
		// 	legend: {
		// 		top:'10',
		// 		type: "scroll",
		// 		data:Line,
		// 		itemWidth:18,
		// 		itemHeight:12,
		// 		textStyle: {
		// 			color:"#00ffff",
		// 			fontSize:14
		// 		},
		// 	},
		// 	yAxis: [
		// 		{
		// 			type: 'value',
		// 			position: 'right',
		// 			splitLine: {
		// 				show: false
		// 			}
		// 			,
		// 			axisLine: {
		// 				show: false
		// 			},
		// 			axisTick: {
		// 				show: false
		// 			},
		// 			axisLabel: {
		// 				show: false
		// 			}
		// 		},
		// 		{
		// 			type: 'value',
		// 			position: 'left',
		// 			nameTextStyle: {
		// 				color: '#00FFFF'
		// 			},
		// 			splitLine: {
		// 				lineStyle: {
		// 					type: 'dashed',
		// 					color: 'rgba(135,140,147,0.8)'
		// 				}
		// 			},
		// 			axisLine: {
		// 				show: false
		// 			},
		// 			axisTick: {
		// 				show: false
		// 			},
		// 			axisLabel: {
		// 				formatter: '{value}',
		// 				color: '#fff',
		// 				fontSize: 14
		// 			}
		// 		},
		// 	],
		// 	xAxis: [
		// 		{
		// 			type: 'category',
		// 			axisTick: {
		// 				show: false
		// 			},
		//
		// 			axisLine: {
		// 				show: false,
		// 				lineStyle: {
		// 					color: '#6A989E',
		// 				},
		//
		// 			},
		// 			axisLabel: {
		// 				inside: false,
		// 				textStyle: {
		// 					color: '#fff',// x轴颜色
		// 					fontWeight: 'normal',
		// 					fontSize: '14',
		// 					lineHeight: 22
		// 				},
		// 				interval:0
		//
		// 			},
		// 			data: Xname,
		// 		}
		// 	],
		// 	series:datas
		//
		// };
		self.option = {
			color: [
				"#F367B9",
				"#755FD7",
				"#F99C56",
				'#4FDA75'
			],
			// backgroundColor: '#0A2E5D',
			title: {
				text: '',
				textStyle:{
					color:'white',  //坐标的字体颜色
				},
			},
			tooltip: {
				trigger: 'axis'
			},
				grid: {
					left: '5%',
					top: '5%',
					bottom: '7%',
					right: '5%',
				},
			legend: {
				data: ["人身事件","设备事件","电网事件","网络信息事件"],
				textStyle:{
					color:'white',  //坐标的字体颜色
				},
			},
			// toolbox: {
			// 	show: true,
			// 	feature: {
			// 		dataZoom: {
			// 			yAxisIndex: 'none'
			// 		},
			// 		dataView: {readOnly: false},
			// 		magicType: {type: ['line', 'bar']},
			// 		restore: {},
			// 		saveAsImage: {}
			// 	}
			// },
			xAxis: {
				type: 'category',
				data: Xname,
				axisTick: {           //去掉坐标轴刻线
					show: false
				},
				axisLine:{

					lineStyle:{
						color:'white',  //坐标轴的颜色
					},
				},
				axisLabel: {
					textStyle:{
						color:'white',  //坐标的字体颜色
					},


				},
			},
			yAxis: {
				// show:false,

				"axisTick":{       //y轴刻度线
					"show":false
				},
				"splitLine": {     //网格线
					"show": false
				},

				minInterval:1,
				type: 'value',
				name : '单位(个)',
				axisLabel: {textStyle:{
						color:'white',  //坐标的字体颜色
					},
					formatter: '{value}'
				},
				axisLine:{
					lineStyle:{
						color:'white',  //坐标轴的颜色

					},
				},
			},
			series: [
				{
					lineStyle:{
						normal:{
							width:2
						}
					},
					symbol: 'circle',     //设定为实心点
					symbolSize: 10,   //设定实心点的大小
					name: '人身事件',
					type: 'line',
					data: list

				},
				{
					symbol: 'circle',     //设定为实心点
					symbolSize: 10,   //设定实心点的大小
					name: '设备事件',
					type: 'line',
					data: list2

				},
				{
					symbol: 'circle',     //设定为实心点
					symbolSize: 10,   //设定实心点的大小
					name: '电网事件',
					type: 'line',
					data:list3

				},
				{
					symbol: 'circle',     //设定为实心点
					symbolSize: 10,   //设定实心点的大小
					name: '网络信息事件',
					type: 'line',
					data:list4

				},
				// {
				// 	symbol: 'circle',     //设定为实心点
				// 	symbolSize: 10,   //设定实心点的大小
				// 	name: '预测值',
				// 	type: 'line',
				// 	data:list5
				//
				// },

			]
		};
				// self.thisYearNumber=[]
		RiskList()
		function RiskList(searchParams) {
			self.isShow(true)
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/tendencyAnalyze").then(function(res) {
				if (res.data.successful) {
					console.log(res.data.resultValue)
					var risklist=res.data.resultValue.date
					var risklist2=res.data.resultValue.equipAccident     //设备
					var risklist3=res.data.resultValue.powergridaccident  //电网事故
					var risklist4=res.data.resultValue.informaccident     //信息
					var risklist5=res.data.resultValue.personalaccident
					var option = self.option();
					 // list=Object.values(risklist)
					 for(key in risklist2){
						 list2.push(risklist2[key])
					 }
					 for(key in risklist3){
						list3.push(risklist3[key])
					}
					for(key in risklist4){
						list4.push(risklist4[key])
					}
					for(key in risklist5){
						list5.push(risklist5[key])
					}
					//  list2=Object.values(risklist2)
					//  list3=Object.values(risklist3)
					//  list4=Object.values(risklist4)
					//  list5=Object.values(risklist5)
					// Xname=Object.keys(risklist)
					data1[0]=list
					data1[1]=list2
					data1[2]=list3
					data1[3]=list4
                    // console.log(data1)
					// console.log(Xname)
					option.xAxis['data'] =risklist;
					option.series[0]['data'] =list5;
					option.series[1]['data'] =list2;
					option.series[2]['data'] =list3;
					option.series[3]['data'] =list4;
					self.option(option);
					// console.log(option)
				} else {
					cube.indicate("数据加载失败");
				}
				self.isShow(false)
			})
		}
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});