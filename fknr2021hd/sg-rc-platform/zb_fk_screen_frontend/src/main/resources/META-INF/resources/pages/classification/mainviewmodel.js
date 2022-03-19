define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
		self.Grade =[
			{id:'',text:'全部'},
			// {id:'01',text:'一级'},
			// {id:'02',text:'二级'},
			// {id:'03',text:'三级'},
			// {id:'04',text:'四级'},
			// {id:'05',text:'五级'},
			// {id:'06',text:'六级'},
			// {id:'07',text:'七级'},
			// {id:'08',text:'八级'},
		];
		self.accidentGrade=ko.observable('');  //等级
		self.voltageCapacity=ko.observable('');  //电压容量
		self.deviceClassConter =[  // 设备类别
			{id:'',text:'全部'},
			// {id:'01',text:'网络设备'},
			// {id:'02',text:'传输介质'},
			// {id:'03',text:'服务器'},
			// {id:'04',text:'存储设备'},
			// {id:'05',text:'安全设备'},
			// {id:'06',text:'UPS电源'},
			// {id:'07',text:'信息系统软件'},
			// {id:'99',text:'信息系统其他'},
		];
		self.wangluodeviceClassConter =[  //  网络信息设备类别
			{id:'',text:'全部'},
			// {id:'01',text:'网络设备'},
			// {id:'02',text:'传输介质'},
			// {id:'03',text:'服务器'},
			// {id:'04',text:'存储设备'},
			// {id:'05',text:'安全设备'},
			// {id:'06',text:'UPS电源'},
			// {id:'07',text:'信息系统软件'},
			// {id:'99',text:'信息系统其他'},
		];
		self.wangluodeviceClass=ko.observable(''); //设备类别
		self.injuryDegreeConter =[  //伤害程度
			{id:'',text:'全部'},
			// {id:'01',text:'死亡'},
			// {id:'02',text:'严重'},
			// {id:'03',text:'轻微'},
		];
		self.injuryDegree=ko.observable('');//伤害程度
		self.deviceClass=ko.observable(''); //设备类别
		self.gridcorp=ko.observable(''); //地区
		self.startTime=ko.observable('');  // 开始时间
		self.endTime=ko.observable('');   //结束时间

		self.accidentClassConter =[  //事故类别
			{id:'',text:'全部'},
			// {id:'01',text:' 触电'},
			// {id:'02',text:'高处坠落'},
			// {id:'03',text:'倒杆塔'},
			// {id:'04',text:'物体打击'},
			// {id:'05',text:'机械伤害'},
			// {id:'06',text:'起重伤害'},
			// {id:'07',text:'车辆伤害'},
			// {id:'08',text:'淹溺'},
			// {id:'09',text:'灼烫伤'},
			// {id:'10',text:'火灾'},
			// {id:'11',text:' 坍塌'},
			// {id:'12',text:'透水'},
			// {id:'13',text:'冒顶片帮'},
			// {id:'14',text:'放炮'},
			// {id:'15',text:'火药爆炸'},
			// {id:'16',text:'容器爆炸'},
			// {id:'17',text:'锅炉爆炸'},
			// {id:'18',text:'瓦斯爆炸'},
			// {id:'19',text:'其他爆炸'},
			// {id:'20',text:'中毒和窒息'},
			// {id:'21',text:'中暑'},
			// {id:'22',text:'职业病'},
			// {id:'23',text:' 动物伤害'},
			// {id:'24',text:'交通事故'},
			// {id:'99',text:'其他伤害'},

		];
		self.accidentClass=ko.observable('');   //事故类别
		self.accidentClassConters =[  //事件分类
			{id:'',text:'全部'},
			// {id:'01',text:'信息泄密'},
			// {id:'02',text:'数据遭恶意篡改'},
			// {id:'03',text:'数据丢失'},
			// {id:'04',text:'网络故障'},
			// {id:'05',text:'业务应用服务中断'},
			// {id:'99',text:'其他'},
		];
		self.accidentClasss=ko.observable('');   //事件分类
        self.selectList = [
            { id: '1', text: '人身事件' },
            { id: '3', text: '电网事件' },
            { id: '2', text: '设备事件' },
            { id: '4', text: '信息事件' }
			// { id: '1', text: '人身事件' },
			// { id: '2', text: '电网事件' },
			// { id: '0', text: '设备事件' },
			// { id: '3', text: '信息事件' }
		];
		self.selectIdByEventType = '3';
		self.eventChangeByEventType = function(e) {
			if (self.selectIdByEventType() == '1') {
				var params = {"type":1}
				axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentCountByMonth",params).then(function(res) {
					if (true) {
						var dataww=res.data.resultValue.thisYear
						var dataww2=res.data.resultValue.lastYear
						var option1 = self.option1();
						var thisYearNumber=Object.values(dataww)
						var thisYearNumber2=Object.values(dataww2)
						// console.log(arr[3])
						option1.series[0].data =thisYearNumber;
						option1.series[1].data =thisYearNumber2;
						self.option1(option1)
					} else {
						cube.indicate("数据加载失败");
					}

				})
			}
			if (self.selectIdByEventType() == '3') {

				var params = {"type":2}
				axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentCountByMonth",params).then(function(res) {
					if (true) {
						var dataww=res.data.resultValue.thisYear
						var dataww2=res.data.resultValue.lastYear
						// console.log(dataww)
						var option1 = self.option1();
						var thisYearNumber=Object.values(dataww)
						var thisYearNumber2=Object.values(dataww2)
						// console.log(arr[3])
						option1.series[0].data =thisYearNumber;
						option1.series[1].data =thisYearNumber2;
						self.option1(option1)
					} else {
						cube.indicate("数据加载失败");
					}

				})
			}
			if (self.selectIdByEventType() == '2') {

				var params = {"type":0}
				axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentCountByMonth",params).then(function(res) {
					if (true) {
						var dataww=res.data.resultValue.thisYear
						var dataww2=res.data.resultValue.lastYear
						// console.log(dataww)
						var option1 = self.option1();
						var thisYearNumber=Object.values(dataww)
						var thisYearNumber2=Object.values(dataww2)
						// console.log(arr[3])
						option1.series[0].data =thisYearNumber;
						option1.series[1].data =thisYearNumber2;
						self.option1(option1)
					} else {
						cube.indicate("数据加载失败");
					}

				})
			}
			if (self.selectIdByEventType() == '4') {

				var params = {"type":3}
				axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentCountByMonth",params).then(function(res) {
					if (true) {
						var dataww=res.data.resultValue.thisYear
						var dataww2=res.data.resultValue.lastYear
						// console.log(dataww)
						var option1 = self.option1();
						var thisYearNumber=Object.values(dataww)
						var thisYearNumber2=Object.values(dataww2)
						// console.log(arr[3])
						option1.series[0].data =thisYearNumber;
						option1.series[1].data =thisYearNumber2;
						self.option1(option1)
					} else {
						cube.indicate("数据加载失败");
					}

				})
			}
        };
		var  colorList=['#afa3f5', '#00d488', '#3feed4', '#3bafff', '#f1bb4c', "rgba(250,250,250,0.5)"];

		var Number = [
			{name:'人身事件',value:0},
			{name:'设备事件',value:0},
			{name:'电网事件',value:0},
			{name:'网络信息事件',value:0},
		]

		var thisYearNumber=[]
		var thisYearNumber2=[]
		var list=[]
		var list2=[]
		var list3=[]
		var list4=[]
		self.option = {
			grid: {
				bottom: 150,
				left: 0,
				right: '10%'
			},
			legend: {
				show:false,
				orient: 'vertical',
				top: "middle",
				right: "5%",
				textStyle: {
					color: '#f2f2f2',
					fontSize: 25,

				},
				icon: 'roundRect'
			},
			series: [
				// 主要展示层的
				{

					radius: ['25%', '51%'],
					center: ['50%', '50%'],
					type: 'pie',
					itemStyle: {
						normal: {
							color: function(params) {
								return colorList[params.dataIndex]
							}
						}
					},
					labelLine: {
						normal: {
							show: true,
							length: 15,
							length2: 120,
							lineStyle: {
								color: '#d3d3d3'
							},
							align: 'right'
						},
						color: "#000",
						emphasis: {
							show: true
						}
					},
					label:{

						normal:{

							formatter: function(params){
								// return '{nameStyle|' +params.name+'}'+'{rate|'+params.value+'%}'
								return '{nameStyle|' +params.name+'}'+'{rate|'+params.value+'}'
							},
							padding: [0, -110],
							height: 45,
							rich: {
								nameStyle: {
									fontSize: 16,
									color: "#fff",
									align: 'left'
								},
								rate: {
									fontSize: 20,
									color: "#1ab4b8",
									align: 'left'
								}
							}
						}
					},
					data:Number
				},
				// 边框的设置
				{
					radius: ['47%', '51%'],
					center: ['50%', '50%'],
					type: 'pie',
					label: {
						normal: {
							show: false
						},
						emphasis: {
							show: false
						}
					},
					labelLine: {
						normal: {
							show: false
						},
						emphasis: {
							show: false
						}
					},
					animation: false,
					tooltip: {
						show: false
					},
					itemStyle: {
						normal: {
							color:'rgba(250,250,250,0.5)'
						}
					},
					data: [{
						value: 1,
					}],
				}
			]
		};
		self.option1 = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    lineStyle: {
                        color: '#57617B'
                    }
                }
			},
			legend: {
				show:true,
				x:'center',
				icon: 'stack',
				itemWidth:10,
				itemHeight:10,
				textStyle:{
					color:'#1bb4f6'
				},


				data:[new Date().getFullYear() + "年",new Date().getFullYear()-1 + "年"]


			},
            grid: {
                left: '2%',
                right: '20',
                bottom: '3%',
                top: "30",
                containLabel: true
            },
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                axisLabel: {
                    margin: 10,
                    interval: 0,
                    textStyle: {
						fontSize: 12,
						color:'#4092A6'
					}
				},
				axisLine: { //坐标轴轴线相关设置。数学上的x轴
					show: true,
					lineStyle: {
						color: '#4092A6'
					},
				},
                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
            }],
            yAxis: [{
                type: 'value',
                minInterval: 1,
                axisLabel: {
                    textStyle: {
						fontSize: 12,
						color:'#4092A6'
                    }
				},
				splitLine: {
					show: true,
					lineStyle: {
						color: '#0a3256'
					}
				},
				axisLine: {
					lineStyle: {
						color: 'rgb(0,253,255,0.6)'
					}
				}
            }],
            color: ['#4ddce4', '#f99c56'],
            series: [


            	{
                name: new Date().getFullYear() + "年",
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 4,
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                            offset: 0,
                            color: 'rgba(16,97,204, 0.3)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(17,235,210, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                        shadowBlur: 10
                    }
                },
                data: thisYearNumber
            }

            ,{
                name:new Date().getFullYear()-1 +"年",
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 4,
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(205,52,42, 0.5)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(235,235,21, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                        shadowBlur: 10
                    }
                },
                data: thisYearNumber2
            }]
		};








		self.option2 ={
			grid: {
                left: '2%',
                right: '20',
                bottom: '3%',
                top: "30",
                containLabel: true
            },
			tooltip : {
				show: true,
				trigger: 'item'
			},
			legend: {
				show:true,
				x:'center',
				icon: 'stack',
				itemWidth:10,
				itemHeight:10,
				textStyle:{
					color:'#1bb4f6'
				},
				data:['人身事件','设备事件','电网事件','网络信息事件']
			},
			xAxis : [
				{
					type : 'category',
					boundaryGap : false,
					axisLabel:{
						color: '#30eee9'
					},
					axisLine:{
						   show:true,
						   lineStyle:{
							color:'#397cbc'
						}
					},  
					data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				}
			],
			yAxis : [
				{
					type : 'value',
					name : '单位(个)',
					minInterval:1,
					axisLabel : {
						formatter: '{value}',
						textStyle:{
							color:'#2ad1d2'
						}
					},
					axisLine:{
						interval: 0,
						minInterval:1,
						lineStyle:{
							color:'#27b4c2'
						}
					},
					axisTick:{
						show:false,
					},
					splitLine:{
						show:true,
						lineStyle:{
							color:'#11366e'
						}
					}
				},
			],
			series : [
				{
					name:'人身事件',
					type:'line',
					stack: '总量0',
					symbol:'circle',
					symbolSize: 8,
					itemStyle: {
						normal: {
							color:'#0092f6',
							lineStyle: {
								color: "#0092f6",
								width:1
							},
							areaStyle: { 
								//color: '#94C9EC'
								color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
									offset: 0,
									color: 'rgba(7,44,90,0.3)'
								}, {
									offset: 1,
									color: 'rgba(0,146,246,0.9)'
								}]),
							}
						}
					},
					markPoint:{
						itemStyle:{
							normal:{
								color:'red'
							}
						}
					},
					data:list3
				},
				{
					interval: 0,
					minInterval:1,
					name:'设备事件',
					type:'line',
					stack: '总量1',
					symbol:'circle',
					symbolSize: 8,
					
					itemStyle: {
						normal: {
							color:'#00d4c7',
							lineStyle: {
								color: "#00d4c7",
								width:1
							},
							areaStyle: { 
								//color: '#94C9EC'
								color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
									offset: 0,
									color: 'rgba(7,44,90,0.3)'
								}, {
									offset: 1,
									color: 'rgba(0,212,199,0.9)'
								}]),
							}
						}
					},
					data:list2
				},
				{
					interval: 0,
					minInterval:1,
					name:'电网事件',
					type:'line',
					stack: '总量2',
					symbol:'circle',
					symbolSize: 8,
					itemStyle: {
						normal: {
							color: '#aecb56',
							lineStyle: {
								color: "#aecb56",
								width:1
							},
							areaStyle: { 
								//color: '#94C9EC'
								color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
									offset: 0,
									color: 'rgba(7,44,90,0.3)'
								}, {
									offset: 1,
									color: 'rgba(114,144,89,0.9)'
								}]),
							}
						}
					},
					data:list4
				},
				{
					interval: 0,
					minInterval:1,
					name:'网络信息事件',
					type:'line',
					stack: '总量3',
					symbol:'circle',
					symbolSize: 8,
					itemStyle: {
						normal: {
							color: '#aecb56',
							lineStyle: {
								color: "#AA668E",
								width:1
							},
							areaStyle: { 
								//color: '#94C9EC'
								color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
									offset: 0,
									color: 'rgba(127,144,80,0.3)'
								}, {
									offset: 1,
									color: 'rgba(184,14,89,0.9)'
								}]),
							}
						}
					},
					data:list
				}
			]
		};

		var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
        // self.Name='';//标题
        // self.startTime = ko.observable(s2 + ' 00:00:00');//预警计划时间
        // self.endTime=ko.observable(s2 + ' 23:59:59');//预警截至时间
        // self.State = '';

        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
		self.pageTotalCount = '';
		self.pageIndex = ko.observable(1);;
		self.pageVisibleCount = 1;
		self.count2 ='';
		self.pageTotalCount2 = '';
		self.pageIndex2 = ko.observable(1);;
		self.pageVisibleCount2 = 1;
		self.count3 ='';
		self.pageTotalCount3 = '';
		self.pageIndex3 = ko.observable(1);;
		self.pageVisibleCount3 = 1;
		self.count4 ='';
		self.pageTotalCount4 =  ko.observable('');
		self.pageIndex4 = ko.observable(1);;
		self.pageVisibleCount4 = 1;
		self.onPageIndexChanged4 = function(p_pageIndex) {
			if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
				self.pageIndex4(p_pageIndex);
				information()
			}
		}
		self.onPageIndexChanged3 = function(p_pageIndex) {
			if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
				self.pageIndex(p_pageIndex);
				Grids()
			}
		}
        self.onPageIndexChanged = function(p_pageIndex) {
			if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
				self.pageIndex(p_pageIndex);
				RiskList()
			}
        }
		self.onPageIndexChanged2 = function(p_pageIndex) {
			if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
				self.pageIndex2(p_pageIndex);
				PersonalEvents()
			}
		}
        self.style ="background:#cccccc;";
        self.columns = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {
                name: "ACCIDENTGRADE", width: "10%", caption: "等级", editorType: "TextEditor",align : "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     return "<span style='padding:4px 16px; background:#6D000E;border-radius: 5px; font-size:16px'>" + cellValue + "</span>"
                // },
                onClick: function (pkValue, cellValue) {
                    //self.showDetail(true)
                }
            },
            {name: "TITLE", width:"30%", caption: "简题" , editorType: "TextEditor",align : "center"},
            {name: "VOLTAGE_CAPACITY", width:"10%", caption: "电压容量(KV)" , editorType: "TextEditor",align : "center" },
            {name: "GRIDCORP", width:"10%", caption: "地区" , editorType: "TextEditor",align : "center" },
            {name: "REASON", width:"10%", caption: "直接原因" , editorType: "TextEditor",align : "center" },
            {name: "DUTY", width:"10%", caption: "主要责任" , editorType: "TextEditor",align : "center" },
            {name: "DEVICECLASS", width:"10%", caption: "设备分类" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENTCORP", width:"20%", caption: "单位" , editorType: "TextEditor",align : "center" },
            {name: "BGNTIME", width:"10%", caption: "发生时间" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent = []
		self.columns2 = [
			// {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{
				name: "ACCIDENTGRADE", width: "10%", caption: "等级", editorType: "TextEditor",align : "center",
				// renderCell: function (cellValue, element, dictValue) {
				//     return "<span style='padding:4px 16px; background:#6D000E;border-radius: 5px; font-size:16px'>" + cellValue + "</span>"
				// },
				onClick: function (pkValue, cellValue) {
					//self.showDetail(true)
				}
			},
			{name: "TITLE", width:"30%", caption: "简题" , editorType: "TextEditor",align : "center"},
			{name: "ACCIDENTCLASS", width:"10%", caption: "事故类别" , editorType: "TextEditor",align : "center" },
			{name: "VICTIM", width:"10%", caption: "受害人" , editorType: "TextEditor",align : "center" },
			{name: "INJURY_DEGREE", width:"10%", caption: "受害程度" , editorType: "TextEditor",align : "center" },
			{name: "ACCIDENTCORP", width:"20%", caption: "单位" , editorType: "TextEditor",align : "center" },
			{name: "BGNTIME", width:"10%", caption: "发生时间" , editorType: "TextEditor",align : "center" }

		];
		self.cityContent2 = []
		self.GridEventscolumns = [
			// {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{
				name: "ACCIDENTGRADE", width: "10%", caption: "等级", editorType: "TextEditor",align : "center",
				// renderCell: function (cellValue, element, dictValue) {
				//     return "<span style='padding:4px 16px; background:#6D000E;border-radius: 5px; font-size:16px'>" + cellValue + "</span>"
				// },
				onClick: function (pkValue, cellValue) {
					//self.showDetail(true)
				}
			},
			{name: "TITLE", width:"30%", caption: "简题" , editorType: "TextEditor",align : "center"},
			{name: "VOLTAGE_CAPACITY", width:"10%", caption: "电压容量(KV)" , editorType: "TextEditor",align : "center" },
			// {name: "GRIDCORP", width:"10%", caption: "地区" , editorType: "TextEditor",align : "center" },
			{name: "REASON", width:"10%", caption: "直接原因" , editorType: "TextEditor",align : "center" },
			{name: "DUTY", width:"10%", caption: "主要责任" , editorType: "TextEditor",align : "center" },
			{name: "DEVICECLASS", width:"10%", caption: "设备分类" , editorType: "TextEditor",align : "center" },
			{name: "ACCIDENTCORP", width:"20%", caption: "单位" , editorType: "TextEditor",align : "center" },
			{name: "BGNTIME", width:"10%", caption: "发生时间" , editorType: "TextEditor",align : "center" }

		];
		self.GridEventsContent = []
		self.columns4 = [
			// {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{
				name: "ACCIDENTGRADE", width: "10%", caption: "等级", editorType: "TextEditor",align : "center",
				// renderCell: function (cellValue, element, dictValue) {
				//     return "<span style='padding:4px 16px; background:#6D000E;border-radius: 5px; font-size:16px'>" + cellValue + "</span>"
				// },
				onClick: function (pkValue, cellValue) {
					//self.showDetail(true)
				}
			},
			{name: "TITLE", width:"30%", caption: "简题" , editorType: "TextEditor",align : "center"},
			{name: "ACCIDENTCLASS", width:"10%", caption: "事件分类" , editorType: "TextEditor",align : "center" },
			{name: "DEVICECLASS", width:"10%", caption: "设备分类" , editorType: "TextEditor",align : "center" },
			{name: "ACCIDENTCORP", width:"20%", caption: "单位" , editorType: "TextEditor",align : "center" },
			{name: "BGNTIME", width:"10%", caption: "发生时间" , editorType: "TextEditor",align : "center" }

		];
		self.Content4= []
		todayList()
		function todayList() {
			var params = {"timeType":0}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentStatistics",params).then(function(res) {
				if (true) {
					var data=res.data.resultValue
					var option = self.option();
					for(var i=0;i<Number.length;i++){
						switch(Number[i]['name']){
							case '人身事件':
								Number[i]['value'] = data['personalAccident'];
								break;
							case '设备事件':

								Number[i]['value'] = data['equipAccident'];
								break;
							case '网络信息事件':

								Number[i]['value'] = data['informAccident'];
								break;
							case '电网事件':
								Number[i]['value'] = data['powerGridAccident'];
								break;
						}
					}
					option.series[0].data =Number;
					self.option(option)
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		//日
		self.today=function(){
			todayList()
		}
		//周
		self.week = function() {
			var params = {"timeType":1}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentStatistics",params).then(function(res) {
				if (true) {
					var data=res.data.resultValue
					var option = self.option();
					// var Number = [];
					for(var i=0;i<Number.length;i++){
						switch(Number[i]['name']){
							case '人身事件':
								Number[i]['value'] = data['personalAccident'];
								break;
							case '设备事件':

								Number[i]['value'] = data['equipAccident'];
								break;
							case '网络信息事件':

								Number[i]['value'] = data['informAccident'];
								break;
							case '电网事件':
								Number[i]['value'] = data['powerGridAccident'];
								break;
						}
					}
					option.series[0].data =Number;
					self.option(option)

				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		//月
		self.month = function() {
			var params = {"timeType":2}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentStatistics",params).then(function(res) {
				if (true) {
					var data=res.data.resultValue
					var option = self.option();
					// var Number = [];
					for(var i=0;i<Number.length;i++){
						switch(Number[i]['name']){
							case '人身事件':
								Number[i]['value'] = data['personalAccident'];
								break;
							case '设备事件':

								Number[i]['value'] = data['equipAccident'];
								break;
							case '网络信息事件':

								Number[i]['value'] = data['informAccident'];
								break;
							case '电网事件':
								Number[i]['value'] = data['powerGridAccident'];
								break;
						}
					}
					option.series[0].data =Number;
					self.option(option)

				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		//年
		self.year = function() {
			var params = {"timeType":3}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentStatistics",params).then(function(res) {
				if (true) {
					var data=res.data.resultValue
					var option = self.option();
					// var Number = [];
					for(var i=0;i<Number.length;i++){
						switch(Number[i]['name']){
							case '人身事件':
								Number[i]['value'] = data['personalAccident'];
								break;
							case '设备事件':

								Number[i]['value'] = data['equipAccident'];
								break;
							case '网络信息事件':

								Number[i]['value'] = data['informAccident'];
								break;
							case '电网事件':
								Number[i]['value'] = data['powerGridAccident'];
								break;
						}
					}
					option.series[0].data =Number;
					self.option(option)

				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		ComparedStatistical()
		MonthlyStatistics()
		//按类型同比统计
		function ComparedStatistical(){

			var params = {"type":0}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentCountByMonth",params).then(function(res) {
				if (true) {
					var dataww=res.data.resultValue.thisYear
					var dataww2=res.data.resultValue.lastYear
					// console.log(dataww)
					var option1 = self.option1();
					// var thisYearNumber=Object.values(dataww)
					var thisYearNumber=[]
					for(key in dataww){
						thisYearNumber.push(dataww[key])
					}
					var thisYearNumber2=[]
					for(key in dataww2){
						thisYearNumber2.push(dataww2[key])
					}
					// var thisYearNumber2=Object.values(dataww2)
					// console.log(arr[3])
					option1.series[0].data =thisYearNumber;
					option1.series[1].data =thisYearNumber2;
					self.option1(option1)
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		//按类型环比统计
		function MonthlyStatistics(){
			// var params = {"type":0}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentCount").then(function(res) {

				if (true) {
					var echartslist=res.data.resultValue.informAccident    //网络信息事件表总数
					var echartslist2=res.data.resultValue.equipAccident   //设备事件表总数
					var echartslist3=res.data.resultValue.personalAccident  //人身事件表总数
					var echartslist4=res.data.resultValue.powergridAccident  //电网事件表总数
					// console.log(dataww)
					var option2 = self.option2();
					// var list=Object.values(echartslist)
					var list=[]
					for(key in echartslist){
						list.push(echartslist[key])
					}
					var list2=[]
					var list3=[]
					var list4=[]
					for(key in echartslist2){
						list2.push(echartslist2[key])
					}
					for(key in echartslist3){
						list3.push(echartslist3[key])
					}
					for(key in echartslist4){
						list4.push(echartslist4[key])
					}
					// var list2=Object.values(echartslist2)
					// var list3=Object.values(echartslist3)
					// var list4=Object.values(echartslist4)
					// console.log(arr[3])
					option2.series[0].data =list3;
					option2.series[1].data =list2;
					option2.series[2].data =list4;
					option2.series[3].data =list;
					self.option2(option2)
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		//查询
		self.search = function() {
			RiskList()
		}
		self.search2 = function() {
			PersonalEvents()
		}
		self.search3 = function() {
			Grids()
		}
		self.search4 = function() {
			information()
		}
		RiskList()
		//设备事件
		self.equipment = function() {
			$(".aa").show();
			$(".region").show();
			$(".voltage").show();
			$(".classification").show();
			$(".extentInjury2").hide();
			$(".extentInjury").hide();
			$(".ren").hide();
			$(".shebei").show();
			$(".dianwang").hide();
			$(".incident").hide();
			$(".xinxiweihu").hide();
			$(".wangluoxinxi").hide();
			RiskList()
		}
		//设备事件
		function RiskList(searchParams) {
			var params = {
				"page":	self.pageIndex(),
				"size":10,
				"params": {
					"accidentGrade":self.accidentGrade(), //事故等级
					"voltageCapacity":self.voltageCapacity(), //电压容量
					"gridcorp":self.gridcorp(), //地区
					"deviceClass":self.deviceClass(), //设备分类
					"startTime":self.startTime(), //开始时间
					"endTime":self.endTime()  //结束时间
				}
			}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/equipAccidentList",params).then(function(res) {
				if (true) {
					var risklist=res.data.resultValue.content
					// console.log(risklist)
					self.count(res.data.resultValue.totalSize);
					self.pageTotalCount(Math.ceil(res.data.resultValue.totalPage));
					self.cityContent(risklist);
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}

		//电网事件
		self.GridEvent = function() {
			$(".region").hide();
			$(".extentInjury2").hide();
			$(".extentInjury").hide();
			$(".voltage").show();
			$(".classification").show();
			$(".ren").hide();
			$(".shebei").hide();
			$(".dianwang").show();
			$(".incident").hide();
			$(".xinxiweihu").hide();
			$(".wangluoxinxi").hide();
			Grids()
		}
		function Grids() {
			var params = {
				"page":	self.pageIndex3(),
				"size":10,
				"params": {
					"accidentGrade":self.accidentGrade(), //事故等级
					"voltageCapacity":self.voltageCapacity(), //电压容量
					// "accidentClass":self.gridcorp(),  //事故分类
					"deviceClass":self.deviceClass(),//设备分类
					"startTime":self.startTime(), //开始时间
					"endTime":self.endTime()  //结束时间
				}
			}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/powerGridAccidentList",params).then(function(res) {
				if (true) {
					var risklist3=res.data.resultValue.content
					// console.log(risklist3)
					self.count3(res.data.resultValue.totalSize);
					self.pageTotalCount3(Math.ceil(res.data.resultValue.totalPage));
					self.GridEventsContent(risklist3);
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		//人身事件
		self.Personal = function() {
			$(".aa").hide();
			$(".region").hide();
			$(".voltage").hide();
			$(".classification").hide();
			$(".extentInjury2").show();
			$(".extentInjury").show();
			$(".ren").show();
			$(".shebei").hide();
			$(".dianwang").hide();
			$(".incident").hide();
			$(".xinxiweihu").hide();
			$(".wangluoxinxi").hide();
			PersonalEvents()
		}
		function PersonalEvents(searchParams) {
			var params = {
				"page":	self.pageIndex2(),
				"size":10,
				"params": {
					"accidentGrade":self.accidentGrade(), //等级
					"accidentClass":self.accidentClass(),//事故分类
					"injuryDegree":self.injuryDegree(),//伤亡程度
					// "deviceClass":self.deviceClass(),
					"startTime":self.startTime(),
					"endTime":self.endTime()
				}
			}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/personalAccidentList",params).then(function(res) {
				if (true) {
					var risklist7=res.data.resultValue.content
					// console.log(risklist)
					self.count2(res.data.resultValue.totalSize);
					self.pageTotalCount2(Math.ceil(res.data.resultValue.totalPage));
					self.cityContent2(risklist7);
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}

		//信息维护
		self.informationMaintenance = function() {
			$(".region").hide();
			$(".voltage").hide();
			$(".incident").show();
			$(".aa").hide();
			$(".ren").hide();
			$(".shebei").hide();
			$(".dianwang").hide();
			$(".extentInjury").hide();
			$(".extentInjury2").hide();
			$(".xinxiweihu").show();
			$(".classification").hide();
			$(".wangluoxinxi").show();
			information()
		}
		function information(searchParams) {
			var params = {
				"page":	self.pageIndex4(),
				"size":10,
				"params": {
					"accidentGrade":self.accidentGrade(), //等级
					// "voltageCapacity":self.voltageCapacity(),
					"accidentClass":self.accidentClasss(),
					"deviceClass":self.wangluodeviceClass(),//网络信息设备分类
					"startTime":self.startTime(),
					"endTime":self.endTime()
				}
			}
			axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/informAccidentList",params).then(function(res) {
				if (true) {
					var risklist6=res.data.resultValue.content
					// console.log(risklist)
					self.count4(res.data.resultValue.totalSize);
					self.pageTotalCount4(Math.ceil(res.data.resultValue.totalPage));
					self.Content4(risklist6);
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		$('.ryn').click(function(){
			$(this).addClass('ryns')
			$('.ryn').not(this).removeClass('ryns')
		})
		$('.fr').click(function(){
			$(this).addClass('ifative')
			$('.fr').not(this).removeClass('ifative')
		})

       //分类统计所有下拉框选项值
		axios.get(cube.gatewayURL2 + "riskAccidentAnalysis/getSrpRiskSysTab").then(function(res) {

			if (true) {
				// var risklist3=res.data.resultValue
				// console.log(res.data)
				//等级
				for (var i=0;i< res.data.resultValue.accidentGrade.length;i++){
					self.Grade.push(res.data.resultValue.accidentGrade[i])
				}
                 //设备分类（设备事件，电网事件）
				for (var i=0;i< res.data.resultValue.deviceClass.length;i++){
					self.deviceClassConter.push(res.data.resultValue.deviceClass[i])
				}
				//事故类别（人身事件）
				for (var i=0;i< res.data.resultValue.accidentClass.length;i++){
					self.accidentClassConter.push(res.data.resultValue.accidentClass[i])
				}
				//伤害程度（人身事件）
				for (var i=0;i< res.data.resultValue.injuryDegree.length;i++){
					self.injuryDegreeConter.push(res.data.resultValue.injuryDegree[i])
				}
				//事件分类（网络信息事件）
				for (var i=0;i< res.data.resultValue.informAccidentClass.length;i++){
					self.accidentClassConters.push(res.data.resultValue.informAccidentClass[i])
				}

				// 设备分类（网络信息事件）
				for (var i=0;i< res.data.resultValue.informDeviceClass.length;i++){
					self.wangluodeviceClassConter.push(res.data.resultValue.informDeviceClass[i])
				}
			} else {
				cube.indicate("数据加载失败");
			}

		})
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});