define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
        self.totalNumber=''//事件总数
		self.informAccident=''//网络信息事件
        self.personalAccident='' //人身事件
        self.powerGridAccident=''  //电网事件
        self.equipAccident=''  //设备事件
        self.selectList = [
            { id: '1', text: '人身事件' },
            { id: '3', text: '电网事件' },
            { id: '2', text: '设备事件' },
            { id: '4', text: '网络信息事件' }
		];
		self.selectIdByEventType = '3';
        self.eventChangeByEventType = function(e){
            if (self.selectIdByEventType() == '1') {
                var params = {"type":2,"fldw":'dw'}
                axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentGrade",params).then(function(res) {
                    if (true) {
                        var list=res.data.resultValue[0]
                        // console.log(list)
                        var pieOption = self.pieOption();
                        var yxzs = 0;
                        for(var i=0;i<Number2.length;i++){
                            switch(Number2[i]['name']){
                                case '1-4级':
                                    yxzs += list['LEVEL4'];
                                    Number2[i]['value'] = list['LEVEL4'];
                                    break;
                                case '5级':
                                    yxzs += list['LEVEL5'];
                                    Number2[i]['value'] = list['LEVEL5'];
                                    break;
                                case '6级':
                                    yxzs += list['LEVEL6'];
                                    Number2[i]['value'] = list['LEVEL6'];
                                    break;
                                case '7级':
                                    yxzs += list['LEVEL7'];
                                    Number2[i]['value'] = list['LEVEL7'];
                                    break;
                                case '8级':
                                    yxzs += list['LEVEL8'];
                                    Number2[i]['value'] = list['LEVEL8'];
                                    break;
                            }
                        }
                        pieOption.title['subtext'] = yxzs + '';
                        pieOption.series[0]['data'] = Number2;
                        self.pieOption(pieOption);
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
            if (self.selectIdByEventType() == '2') {
                var params = {"type":0,"fldw":'dw'}
                axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentGrade",params).then(function(res) {
                    if (true) {
                        var list=res.data.resultValue[0]
                        // console.log(list)
                        var pieOption = self.pieOption();
                        var yxzs = 0;
                        for(var i=0;i<Number2.length;i++){
                            switch(Number2[i]['name']){
                                case '1-4级':
                                    yxzs += list['LEVEL4'];
                                    Number2[i]['value'] = list['LEVEL4'];
                                    break;
                                case '5级':
                                    yxzs += list['LEVEL5'];
                                    Number2[i]['value'] = list['LEVEL5'];
                                    break;
                                case '6级':
                                    yxzs += list['LEVEL6'];
                                    Number2[i]['value'] = list['LEVEL6'];
                                    break;
                                case '7级':
                                    yxzs += list['LEVEL7'];
                                    Number2[i]['value'] = list['LEVEL7'];
                                    break;
                                case '8级':
                                    yxzs += list['LEVEL8'];
                                    Number2[i]['value'] = list['LEVEL8'];
                                    break;
                            }
                        }
                        pieOption.title['subtext'] = yxzs + '';
                        pieOption.series[0]['data'] = Number2;
                        self.pieOption(pieOption);
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
            if (self.selectIdByEventType() == '3') {
                var params = {"type":1,"fldw":'dw'}
                axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentGrade",params).then(function(res) {
                    if (true) {
                        var list=res.data.resultValue[0]
                        // console.log(list)
                        var pieOption = self.pieOption();
                        var yxzs = 0;
                        for(var i=0;i<Number2.length;i++){
                            switch(Number2[i]['name']){
                                case '1-4级':
                                    yxzs += list['LEVEL4'];
                                    Number2[i]['value'] = list['LEVEL4'];
                                    break;
                                case '5级':
                                    yxzs += list['LEVEL5'];
                                    Number2[i]['value'] = list['LEVEL5'];
                                    break;
                                case '6级':
                                    yxzs += list['LEVEL6'];
                                    Number2[i]['value'] = list['LEVEL6'];
                                    break;
                                case '7级':
                                    yxzs += list['LEVEL7'];
                                    Number2[i]['value'] = list['LEVEL7'];
                                    break;
                                case '8级':
                                    yxzs += list['LEVEL8'];
                                    Number2[i]['value'] = list['LEVEL8'];
                                    break;
                            }
                        }
                        pieOption.title['subtext'] = yxzs + '';
                        pieOption.series[0]['data'] = Number2;
                        self.pieOption(pieOption);
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
            if (self.selectIdByEventType() == '4') {
                var params = {"type":3,"fldw":'dw'}
                axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentGrade",params).then(function(res) {
                    if (true) {
                        var list=res.data.resultValue[0]
                        // console.log(list)
                        var pieOption = self.pieOption();
                        var yxzs = 0;
                        for(var i=0;i<Number2.length;i++){
                            switch(Number2[i]['name']){
                                case '1-4级':
                                    yxzs += list['LEVEL4'];
                                    Number2[i]['value'] = list['LEVEL4'];
                                    break;
                                case '5级':
                                    yxzs += list['LEVEL5'];
                                    Number2[i]['value'] = list['LEVEL5'];
                                    break;
                                case '6级':
                                    yxzs += list['LEVEL6'];
                                    Number2[i]['value'] = list['LEVEL6'];
                                    break;
                                case '7级':
                                    yxzs += list['LEVEL7'];
                                    Number2[i]['value'] = list['LEVEL7'];
                                    break;
                                case '8级':
                                    yxzs += list['LEVEL8'];
                                    Number2[i]['value'] = list['LEVEL8'];
                                    break;
                            }
                        }
                        pieOption.title['subtext'] = yxzs + '';
                        pieOption.series[0]['data'] = Number2;
                        self.pieOption(pieOption);
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
        }
        self.selectList2 = [
            { id: '1', text: '人身事件' },
            { id: '3', text: '电网事件' },
            { id: '2', text: '设备事件' },
            { id: '4', text: '网络信息事件' }
        ];
        self.selectIdByEventType2 = '3';
        self.eventChangeByEventType2 = function(e) {

            if (self.selectIdByEventType2() == '1') {
                var params = {"type":1,"fldw":'dw'}
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
            if (self.selectIdByEventType2() == '3') {


                var params = {"type":2,"fldw":'dw'}
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
            if (self.selectIdByEventType2() == '2') {

                var params = {"type":0,"fldw":'dw'}
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
            if (self.selectIdByEventType2() == '4') {

                var params = {"type":3,"fldw":'dw'}
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
        self.selectList3 = [
            { id: '1', text: '人身事件' },
            { id: '3', text: '电网事件' },
            { id: '2', text: '设备事件' },
            { id: '4', text: '网络信息事件' }
        ];
        self.selectIdByEventType3 = '3';
        self.eventChangeByEventType3 = function(e){
            if (self.selectIdByEventType3() == '1') {
                var params = {"type":2,"fldw":'dw'}
                axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentDesc",params).then(function(res) {
                    if (true) {
                        var list=res.data.resultValue
                        // console.log(list)
                        var option4= self.option4();
                        var DATAREPORT_ORG = [],POWERGRIDACCIDENT_cnt = [];
                        for (var i in list){
                            DATAREPORT_ORG.push(list[i].DATAREPORT_ORG)
                            POWERGRIDACCIDENT_cnt.push(list[i].PERSONALACCIDENT_cnt)
                            option4.xAxis[0].data = DATAREPORT_ORG;
                            option4.series[0].data = POWERGRIDACCIDENT_cnt;
                            self.option4(option4)
                        }
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
            if (self.selectIdByEventType3() == '2') {
                var params = {"type":0,"fldw":'dw'}
                axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentDesc",params).then(function(res) {
                    if (true) {
                        var list=res.data.resultValue
                        // console.log(list)
                        var option4= self.option4();
                        var DATAREPORT_ORG = [],POWERGRIDACCIDENT_cnt = [];
                        for (var i in list){
                            DATAREPORT_ORG.push(list[i].DATAREPORT_ORG)
                            POWERGRIDACCIDENT_cnt.push(list[i].EQUIPACCIDENT_cnt)
                            option4.xAxis[0].data = DATAREPORT_ORG;
                            option4.series[0].data = POWERGRIDACCIDENT_cnt;
                            self.option4(option4)
                        }
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
            if (self.selectIdByEventType3() == '3') {
                var params = {"type":1,"fldw":'dw'}
                axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentDesc",params).then(function(res) {
                    if (true) {
                        var list=res.data.resultValue
                        // console.log(list)
                        var option4= self.option4();
                        var DATAREPORT_ORG = [],POWERGRIDACCIDENT_cnt = [];
                        for (var i in list){
                            DATAREPORT_ORG.push(list[i].DATAREPORT_ORG)
                            POWERGRIDACCIDENT_cnt.push(list[i].POWERGRIDACCIDENT_cnt)
                            option4.xAxis[0].data = DATAREPORT_ORG;
                            option4.series[0].data = POWERGRIDACCIDENT_cnt;
                            self.option4(option4)
                        }
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
            if (self.selectIdByEventType3() == '4') {
                var params = {"type":3,"fldw":'dw'}
                axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentDesc",params).then(function(res) {
                    if (true) {
                        var list=res.data.resultValue
                        console.log(list)
                        var option4= self.option4();
                        var DATAREPORT_ORG = [],POWERGRIDACCIDENT_cnt = [];
                        for (var i in list){
                            DATAREPORT_ORG.push(list[i].DATAREPORT_ORG)
                            POWERGRIDACCIDENT_cnt.push(list[i].INFORMACCIDENT_cnt)
                            option4.xAxis[0].data = DATAREPORT_ORG;
                            option4.series[0].data = POWERGRIDACCIDENT_cnt;
                            self.option4(option4)
                        }
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
        }
        // var data = [
		// 	{name:'1-4级',value:20},
		// 	{name:'5级',value:20},
		// 	{name:'6级',value:20},
		// 	{name:'7级',value:20},
		// 	{name:'8级',value:20}
		// ]
        // var Number = [
        //     {name:'人身事件',value:0},
        //     {name:'设备事件',value:0},
        //     {name:'电网事件',value:0},
        //     {name:'网络信息事件',value:0},
        // ]
        var Number2 = [
            {name:'1-4级',value:0},
            {name:'5级',value:0},
            {name:'6级',value:0},
            {name:'7级',value:0},
            {name:'8级',value:0}
        ]
        var thisYearNumber=[]
        var thisYearNumber2=[]
        var list=[]
        var list2=[]
        var list3=[]
        var list4=[]
		self.pieOption = {
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} ({d}%)"
            },
            title: {
                text: '事件总数',
                subtext: '0',
                left: 'center',
                top: 'center',
                textStyle: {
                    color: '#F99C56',
                    fontSize: 18
                },
                subtextStyle: {
                    fontSize: 14,
                    color: ['#F99C56']
                }
            },
            legend: {
                orient: 'vertical',
				top: "middle",
				right: "10",
				textStyle: {
					color: '#fff',
				},
                itemWidth: 12, // 设置宽度
                itemHeight: 10, // 设置高度
                data: Number2
            },
            series: [
                // 主要展示层的
                {
                    radius: ['45%', '65%'],
                    center: ['50%', '50%'],
                    type: 'pie',
                    label: {
                        normal: {
                            show: true,
                            formatter: "{d}%",
                            textStyle: {
                                fontSize: 14

                            },
                            position: 'outside'
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    labelLine: {
                        normal: {
                            length: 6
                        }
                    },
                    hoverAnimation: false,
                    data: Number2

                },
                // 边框的设置
                {
                    radius: ['45%', '40%'],
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
                    data: [{
                        value: 1,
                        itemStyle: {
                            color: "rgba(250,250,250,0.3)"
                        }
                    }]
                }, {
                    name: '外边框',
                    type: 'pie',
                    clockWise: false, //顺时加载
                    hoverAnimation: false, //鼠标移入变大
                    center: ['50%', '50%'],
                    radius: ['67%', '67%'],
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    data: [{
                        value: 9,
                        name: '',
                        itemStyle: {
                            normal: {
                                borderWidth: 2,
                                borderColor: '#4DDCE4'
                            }
                        }
                    }]
                }
            ]
		};
		self.homeworkOption = {
            color: [
                "#F367B9",
                "#F99C56",
                "#755FD7",
                '#4FDA75'
            ],
            legend: {
                show:true,
                right:15,
                icon: 'stack',
                itemWidth:10,
                itemHeight:10,
                textStyle:{
                    color:'#fff'
                },
                data:['人身事件','设备事件','电网事件','网络信息事件']
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '25',
                right: '10',
                bottom: '10',
                top: '40',
                containLabel: true,
            },
            xAxis: [{


                type: 'category',
                data: [],
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#FFFFFF'
                    }
                },
                axisLabel: {
                    interval:0,
                    show: true,
                    color: '#ffffff',
                    rotate:45,
                },
            }],
            yAxis: [{
                name: '单位(个)',
				splitLine: {
					show: false
				},
				axisTick: {
					show: false
				},
				axisLine: {
					show: true,
					lineStyle: {
						color: '#FFFFFF'
					}
				},
				axisLabel: {
					show: true,
					color: '#ffffff',

				}
            }],
            series: [
                {
                    name: '人身事件',
                    type: 'bar',
                    stack: 'level',
                    data: [],
                    barWidth: '10px'
                },
                {
                    name: '电网事件',
                    type: 'bar',
                    stack: 'level',
                    data: [],
                    barWidth: '10px'
                },
                {
                    name: '设备事件',
                    type: 'bar',
                    stack: 'level',
                    data: [],
                    barWidth: '10px'
                },
                {
                    name: '网络信息事件',
                    type: 'bar',
                    stack: 'level',
                    data: [],
                    barWidth: '10px'
                    // itemStyle: {
                    //     normal: {
                    //         barBorderRadius: [30, 30, 0, 0]
                    //     }
                    // }
                }
            ]
		};
		self.homeworkOption1 = {
            color: [
                "#F99C56",
                "#755FD7"
            ],
            legend: {
                data: [ '电网事件','设备事件'],
                right: 10,
                textStyle: {
                    color: "#fff"
                },
                itemWidth: 12,
                itemHeight: 10
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '25',
                right: '10',
                bottom: '10',
                top: '40',
                containLabel: true,
            },
            xAxis: [{
                type: 'category',
                data: [],
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#FFFFFF'
                    }
                },
                axisLabel: {
                    interval:1,
                    show: true,
                    color: '#ffffff',
                    // rotate:12,
                },
            }],
            yAxis: [{
                name: '单位(个)',
				splitLine: {
					show: false
				},
				axisTick: {
					show: false
				},
				axisLine: {
					show: true,
					lineStyle: {
						color: '#FFFFFF'
					}
				},
				axisLabel: {
					show: true,
					color: '#ffffff'
				}
            }],
            series: [
                // {
                //     name: '人身事件',
                //     type: 'bar',
                //     stack: 'level',
                //     data: [10,11,11,10,10],
                //     barWidth: '10px'
                // },
                {
                    name: '电网事件',
                    type: 'bar',
                    stack: 'level',
                    data: [],
                    barWidth: '10px'
                },
                {
                    name: '设备事件',
                    type: 'bar',
                    stack: 'level',
                    data: [],
                    barWidth: '10px'
                },
                // {
                //     name: '信息事件',
                //     type: 'bar',
                //     stack: 'level',
                //     data: [10,11,11,10,10],
                //     barWidth: '10px',
                //     itemStyle: {
                //         normal: {
                //             barBorderRadius: [30, 30, 0, 0]
                //         }
                //     }
                // }
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
                left: '5%',
                right: '20',
                bottom: '5%',
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
                name: '单位(个)',
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
            series: [{
                name:new Date().getFullYear() + "年",
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
            }, {
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
                left: '5%',
                right: '20',
                bottom: '5%',
                top: "30",
                containLabel: true
            },
            tooltip : {
                show: true,
                trigger: 'axis'
            },
            color: [
                "#F367B9",
                "#755FD7",
                "#F99C56",
                '#4FDA75'
            ],
            legend: {
                show:true,
                x:'center',
                icon: 'stack',
                itemWidth:10,
                itemHeight:10,
                textStyle:{
                    color:'#fff'
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
                    min:0,
                    axisLabel : {
                        formatter: '{value}',
                        textStyle:{
                            color:'#2ad1d2'
                        }
                    },
                    axisLine:{
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
                    // itemStyle: {
                    //     normal: {
                    //         color:'#0092f6',
                    //         lineStyle: {
                    //             color: "#0092f6",
                    //             width:1
                    //         },
                    //         areaStyle: {
                    //             //color: '#94C9EC'
                    //             color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                    //                 offset: 0,
                    //                 color: 'rgba(7,44,90,0.3)'
                    //             }, {
                    //                 offset: 1,
                    //                 color: 'rgba(0,146,246,0.9)'
                    //             }]),
                    //         }
                    //     }
                    // },
                    // markPoint:{
                    //     itemStyle:{
                    //         normal:{
                    //             color:'red'
                    //         }
                    //     }
                    // },
                    data:list3
                },
                {
                    name:'设备事件',
                    type:'line',
                    stack: '总量1',
                    symbol:'circle',
                    symbolSize: 8,

                    // itemStyle: {
                    //     normal: {
                    //         color:'#00d4c7',
                    //         lineStyle: {
                    //             color: "#00d4c7",
                    //             width:1
                    //         },
                    //         areaStyle: {
                    //             //color: '#94C9EC'
                    //             color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                    //                 offset: 0,
                    //                 color: 'rgba(7,44,90,0.3)'
                    //             }, {
                    //                 offset: 1,
                    //                 color: 'rgba(0,212,199,0.9)'
                    //             }]),
                    //         }
                    //     }
                    // },
                    data:list2
                },
                {
                    name:'电网事件',
                    type:'line',
                    stack: '总量2',
                    symbol:'circle',
                    symbolSize: 8,
                    // itemStyle: {
                    //     normal: {
                    //         color: '#aecb56',
                    //         lineStyle: {
                    //             color: "#aecb56",
                    //             width:1
                    //         },
                    //         areaStyle: {
                    //             //color: '#94C9EC'
                    //             color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                    //                 offset: 0,
                    //                 color: 'rgba(7,44,90,0.3)'
                    //             }, {
                    //                 offset: 1,
                    //                 color: 'rgba(114,144,89,0.9)'
                    //             }]),
                    //         }
                    //     }
                    // },
                    data:list4
                },
                {
                    name:'网络信息事件',
                    type:'line',
                    stack: '总量3',
                    symbol:'circle',
                    symbolSize: 8,
                    // itemStyle: {
                    //     normal: {
                    //         color: '#aecb56',
                    //         lineStyle: {
                    //             color: "#AA668E",
                    //             width:1
                    //         },
                    //         areaStyle: {
                    //             //color: '#94C9EC'
                    //             color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                    //                 offset: 0,
                    //                 color: 'rgba(127,144,80,0.3)'
                    //             }, {
                    //                 offset: 1,
                    //                 color: 'rgba(184,14,89,0.9)'
                    //             }]),
                    //         }
                    //     }
                    // },
                    data:list
                }
            ]
        };
		self.option4 = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '29',
                right: '10',
                bottom: '10',
                top: '40',
                containLabel: true,
            },
            xAxis: [{
                type: 'category',
                data: [],
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#FFFFFF'
                    }
                },
                axisLabel: {
                    interval:0,
                    show: true,
                    color: '#ffffff',
                    rotate:35,
                },
            }],
            yAxis: [{
                name: '单位(个)',
                splitLine: {
					show: false
				},
				axisTick: {
					show: false
				},
				axisLine: {
					show: true,
					lineStyle: {
						color: '#FFFFFF'
					}
				},
				axisLabel: {
					show: true,
					color: '#ffffff'
				}
            },{
				type: 'value',
				gridIndex: 0,
				min: 50,
				max: 100,
				splitNumber: 12,
				splitLine: {
					show: false
				},
				axisLine: {
					show: false
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					show: false
				},
				splitArea: {
					show: true,
					areaStyle: {
						color: ['rgba(250,250,250,0.0)', 'rgba(250,250,250,0.05)']
					}
				}
			}],
            series: [{
                type: 'bar',
                data: [],
                barWidth: '10px',
                color: [{
                    type: "linear",
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [
						{
							offset: 0,
							color: '#00feff'
						},
						{
							offset: 0.5,
							color: '#027eff'
						},
						{
							offset: 1,
							color: '#0286ff'
						}
                    ]
                    //   globalCoord: false // 缺省为 false
                }],
                itemStyle: {
                    normal: {
                        barBorderRadius: [30, 30, 0, 0]
                    }
                }
            }]
        };
        ComparedStatistical()
        MonthlyStatistics()
        //按类型同比统计
        function ComparedStatistical(){

            var params = {"type":2,"fldw":'dw'}
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentCountByMonth",params).then(function(res) {

                if (true) {

                    var dataww=res.data.resultValue.thisYear
                    var dataww2=res.data.resultValue.lastYear
                    // console.log(dataww)
                    var option1 = self.option1();
                    var thisYearNumber=[]
                    for(key in dataww){
                        thisYearNumber.push(dataww[key])
                    }
                    var thisYearNumber2=[]
                    for(key in dataww2){
                        thisYearNumber2.push(dataww2[key])
                    }
                    // var thisYearNumber=Object.values(dataww)
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
                    for(key in echartslist2){
                        list2.push(echartslist2[key])
                    }
                    var list3=[]
                    for(key in echartslist3){
                        list3.push(echartslist3[key])
                    }
                    var list4=[]
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
        //事件数量统计
        EventCount()
        function EventCount() {
            var params = {"timeType":0,"fldw":'dw'}
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentStatistics1",params).then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    // console.log(list)
                    self.totalNumber(list.informAccident+list.personalAccident+list.powerGridAccident+list.equipAccident)//事件总数
                    self.informAccident(list.informAccident)//网络信息事件
                    self.personalAccident(list.personalAccident) //人身事件
                    self.powerGridAccident(list.powerGridAccident)  //电网事件
                    self.equipAccident(list.equipAccident) //设备事件
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //事件等级统计
        EventLevel()
        function EventLevel() {
            var params = {"type":1,"fldw":'dw'}
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentGrade",params).then(function(res) {
                if (true) {
                    var data=res.data.resultValue[0]
                    // console.log(data)
                    var pieOption = self.pieOption();
                    var yxzs = 0;
                    for(var i=0;i<Number2.length;i++){
                        switch(Number2[i]['name']){
                            case '1-4级':
                                yxzs += data['LEVEL4'];
                                Number2[i]['value'] = data.LEVEL4;
                                break;
                            case '5级':
                                yxzs += data['LEVEL5'];
                                Number2[i]['value'] =data.LEVEL5;
                                break;
                            case '6级':
                                yxzs += data['LEVEL6'];
                                Number2[i]['value'] = data.LEVEL6;
                                break;
                            case '7级':
                                yxzs += data['LEVEL7'];
                                Number2[i]['value'] = data.LEVEL7;
                                break;
                            case '8级':
                                yxzs += data['LEVEL8'];
                                Number2[i]['value'] = data.LEVEL8;
                                break;
                        }
                    }
                    pieOption.title['subtext'] = yxzs + '';
                    pieOption.series[0]['data'] = Number2;
                    self.pieOption(pieOption);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //按单位统计
        UnitStatistics()
        function UnitStatistics() {
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/gridcrop").then(function(res) {
                if(res.data.successful){
                    var list=res.data.resultValue
                    var homeworkOption= self.homeworkOption();
                    var City = [],EQUIPACCIDENT_cnt = [],POWERGRIDACCIDENT_cnt = [],PERSONALACCIDENT_cnt = [],INFORMACCIDENT_cnt = [];
                    for (var i in list){
                        City.push(list[i].DATAREPORT_ORG)
                        EQUIPACCIDENT_cnt.push(list[i].EQUIPACCIDENT_cnt)       //设备事件
                        POWERGRIDACCIDENT_cnt.push(list[i].POWERGRIDACCIDENT_cnt)   //电网事件
                        PERSONALACCIDENT_cnt.push(list[i].PERSONALACCIDENT_cnt)  //人身事件
                        INFORMACCIDENT_cnt.push(list[i].INFORMACCIDENT_cnt)        //网络信息事件
                        homeworkOption.xAxis[0].data = City;
                        homeworkOption.series[0].data = PERSONALACCIDENT_cnt;
                        homeworkOption.series[1].data = POWERGRIDACCIDENT_cnt;
                        homeworkOption.series[2].data = EQUIPACCIDENT_cnt;
                        homeworkOption.series[3].data = INFORMACCIDENT_cnt;
                        // homeworkOption.series[4].data = MunicipalCooperate;

                        self.homeworkOption(homeworkOption)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //按各电压统计
        VoltageStatistics()
        function VoltageStatistics() {
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/voltageLevel").then(function(res) {
                if(res.data.successful){
                    var list=res.data.resultValue
                    var homeworkOption1= self.homeworkOption1();
                    var City = [],EQUIPACCIDENT_cnt = [],POWERGRIDACCIDENT_cnt = [];
                    for (var i in list){
                        City.push(list[i].DATAREPORT_ORG)
                        EQUIPACCIDENT_cnt.push(list[i].EQUIPACCIDENT_cnt)       //设备事件
                        POWERGRIDACCIDENT_cnt.push(list[i].POWERGRIDACCIDENT_cnt)   //电网事件

                        homeworkOption1.xAxis[0].data = City;
                        homeworkOption1.series[0].data = POWERGRIDACCIDENT_cnt;
                        homeworkOption1.series[1].data = EQUIPACCIDENT_cnt;


                        self.homeworkOption1(homeworkOption1)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //事故原因统计
        accidentCause()
        function accidentCause() {
            var params = {"type":1,"fldw":'dw'}
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/accidentDesc",params).then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    // console.log(list)
                    var option4= self.option4();
                    var DATAREPORT_ORG = [],POWERGRIDACCIDENT_cnt = [];
                    for (var i in list){
                        DATAREPORT_ORG.push(list[i].DATAREPORT_ORG)
                        POWERGRIDACCIDENT_cnt.push(list[i].POWERGRIDACCIDENT_cnt)
                        option4.xAxis[0].data = DATAREPORT_ORG;
                        option4.series[0].data = POWERGRIDACCIDENT_cnt;
                        self.option4(option4)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});