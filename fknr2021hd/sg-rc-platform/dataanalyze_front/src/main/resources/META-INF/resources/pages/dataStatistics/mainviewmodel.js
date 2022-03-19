define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        self.protectiveDevice='';//个体防护装备
        self.safetyTool='';//绝缘安全工器具
        self.similarTool='';//登高工器具
        var list=[]
        var list2=[]
        var list3=[]
        var list4=[]
        var list5=[]
        var list6=[]
        var list7=[]
        var list8=[]
        var list9=[]
        var list10=[]
        var list11=[]
        var list12=[]
        var list13=[]
        var list14=[]
        var list15=[]
        var list16=[]
        self.option1 ={
            grid: {
                left: '5%',
                right: '30',
                bottom: '5%',
                top: "30",
                containLabel: true
            },
            tooltip : {
                show: true,
                trigger: 'axis'
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
                data:['个体防护装备','绝缘安全工器具','登高工器具']
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
                    data : list
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
                    name:'个体防护装备',
                    type:'line',
                    // stack: '总量',
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
                    data:list2
                },
                {
                    name:'绝缘安全工器具',
                    type:'line',
                    // stack: '总量',
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
                    data:list3
                },
                {
                    name:'登高工器具',
                    type:'line',
                    // stack: '总量',
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
                // {
                //     name:'警示标识',
                //     type:'line',
                //     stack: '总量',
                //     symbol:'circle',
                //     symbolSize: 8,
                //     itemStyle: {
                //         normal: {
                //             color: '#aecb56',
                //             lineStyle: {
                //                 color: "#AA668E",
                //                 width:1
                //             },
                //             areaStyle: {
                //                 //color: '#94C9EC'
                //                 color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                //                     offset: 0,
                //                     color: 'rgba(127,144,80,0.3)'
                //                 }, {
                //                     offset: 1,
                //                     color: 'rgba(184,14,89,0.9)'
                //                 }]),
                //             }
                //         }
                //     },
                //     data:[150, 232, 201, 154, 190, 330, 410,150]
                // }
            ]
        };
        self.option2 ={
            grid: {
                left: '5%',
                right: '30',
                bottom: '5%',
                top: "30",
                containLabel: true
            },
            tooltip : {
                show: true,
                trigger: 'axis'
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
                data:['个体防护装备','绝缘安全工器具','登高工器具']
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
                    data :list5
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
                    name:'个体防护装备',
                    type:'line',
                    // stack: '总量',
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
                    data:list6
                },
                {
                    name:'绝缘安全工器具',
                    type:'line',
                    // stack: '总量',
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
                    data:list7
                },
                {
                    name:'登高工器具',
                    type:'line',
                    // stack: '总量',
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
                    data:list8
                },
                // {
                //     name:'警示标识',
                //     type:'line',
                //     stack: '总量',
                //     symbol:'circle',
                //     symbolSize: 8,
                //     itemStyle: {
                //         normal: {
                //             color: '#aecb56',
                //             lineStyle: {
                //                 color: "#AA668E",
                //                 width:1
                //             },
                //             areaStyle: {
                //                 //color: '#94C9EC'
                //                 color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                //                     offset: 0,
                //                     color: 'rgba(127,144,80,0.3)'
                //                 }, {
                //                     offset: 1,
                //                     color: 'rgba(184,14,89,0.9)'
                //                 }]),
                //             }
                //         }
                //     },
                //     data:[150, 232, 201, 154, 190, 330, 410,150]
                // }
            ]
        };
        self.option3 ={
            grid: {
                left: '5%',
                right: '30',
                bottom: '5%',
                top: "30",
                containLabel: true
            },
            tooltip : {
                show: true,
                trigger: 'axis'
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
                data:['个体防护装备','绝缘安全工器具','登高工器具']
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
                    data : list9
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
                    name:'个体防护装备',
                    type:'line',
                    // stack: '总量',
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
                    data:list10
                },
                {
                    name:'绝缘安全工器具',
                    type:'line',
                    // stack: '总量',
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
                    data:list11
                },
                {
                    name:'登高工器具',
                    type:'line',
                    // stack: '总量',
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
                    data:list12
                },
                // {
                //     name:'警示标识',
                //     type:'line',
                //     stack: '总量',
                //     symbol:'circle',
                //     symbolSize: 8,
                //     itemStyle: {
                //         normal: {
                //             color: '#aecb56',
                //             lineStyle: {
                //                 color: "#AA668E",
                //                 width:1
                //             },
                //             areaStyle: {
                //                 //color: '#94C9EC'
                //                 color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                //                     offset: 0,
                //                     color: 'rgba(127,144,80,0.3)'
                //                 }, {
                //                     offset: 1,
                //                     color: 'rgba(184,14,89,0.9)'
                //                 }]),
                //             }
                //         }
                //     },
                //     data:[150, 232, 201, 154, 190, 330, 410,150]
                // }
            ]
        };
        self.option4 ={
            grid: {
                left: '5%',
                right: '30',
                bottom: '5%',
                top: "30",
                containLabel: true
            },
            tooltip : {
                show: true,
                trigger: 'axis'
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
                data:['个体防护装备','绝缘安全工器具','登高工器具']
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
                    data : list13
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
                    name:'个体防护装备',
                    type:'line',
                    // stack: '总量',
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
                    data:list14
                },
                {
                    name:'绝缘安全工器具',
                    type:'line',
                    // stack: '总量',
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
                    data:list15
                },
                {
                    name:'登高工器具',
                    type:'line',
                    // stack: '总量',
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
                    data:list16
                },
                // {
                //     name:'警示标识',
                //     type:'line',
                //     stack: '总量',
                //     symbol:'circle',
                //     symbolSize: 8,
                //     itemStyle: {
                //         normal: {
                //             color: '#aecb56',
                //             lineStyle: {
                //                 color: "#AA668E",
                //                 width:1
                //             },
                //             areaStyle: {
                //                 //color: '#94C9EC'
                //                 color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                //                     offset: 0,
                //                     color: 'rgba(127,144,80,0.3)'
                //                 }, {
                //                     offset: 1,
                //                     color: 'rgba(184,14,89,0.9)'
                //                 }]),
                //             }
                //         }
                //     },
                //     data:[150, 232, 201, 154, 190, 330, 410,150]
                // }
            ]
        };

        RiskList()
        function RiskList(searchParams) {
            // var params = {
            //     "page":	self.pageIndex(),
            //     "size":10,
            //     "params": {
            //         "jobName":self.titleName(),
            //         "executionDateTime":self.time(),
            //         "endDateTime":self.endTime(),
            //         "processState":self.Unit()
            //     }
            // }
            self.isShow(true)
            axios.post(cube.gatewayURL2 + "toolInformation/dataStatistics").then(function(res) {
                if (res.data.successful) {
                    var risklist=res.data.resultValue
                    self.protectiveDevice(risklist.count[0].CNT);//个体防护装备
                    self.safetyTool(risklist.count[1].CNT);//绝缘安全工器具
                    self.similarTool(risklist.count[2].CNT);//登高工器具
                    //工器具采购曲线
                    var option = self.option1();
                    var risklist2=res.data.resultValue.PURPLAN
                    // console.log(risklist2)
                    for(var i=0;i<risklist2.length;i++){
                        list.unshift(risklist2[i].time)
                        list2.unshift(risklist2[i].protective)
                        list3.unshift(risklist2[i].insulation)
                        list4.unshift(risklist2[i].ascend)
                    }
                    // for(let key  in risklist2){
                    //     list.unshift(key)
                    //     list2.unshift(risklist2[key][0].protective)
                    //     list3.unshift(risklist2[key][0].insulation)
                    //     list4.unshift(risklist2[key][0].ascend)

                    // }
                    option.xAxis['data'] =list;
                    option.series[0]['data'] = list2;
                    option.series[1]['data'] = list3;
                    option.series[2]['data'] = list4;
                    self.option1(option);

                    //按类型统计检测工器具数量
                    var options = self.option2();
                    var risklist3=res.data.resultValue.TESTDETAIL
                    for(var i=0;i<risklist3.length;i++){
                        list5.unshift(risklist3[i].time)
                        list6.unshift(risklist3[i].protective)
                        list7.unshift(risklist3[i].insulation)
                        list8.unshift(risklist3[i].ascend)
                    }
                    // for(let key  in risklist3){
                    //     list5.unshift(key)
                    //     list6.unshift(risklist3[key][0].protective)
                    //     list7.unshift(risklist3[key][0].insulation)
                    //     list8.unshift(risklist3[key][0].ascend)
                    // }
                    options.xAxis['data'] =list5;
                    options.series[0]['data'] = list6;
                    options.series[1]['data'] = list7;
                    options.series[2]['data'] = list8;
                    self.option2(options);

                    //按类型同比统计库存工器具数量
                    var optionss = self.option3();
                    var risklist4=res.data.resultValue.STORAGE
                    for(var i=0;i<risklist4.length;i++){
                        list9.unshift(risklist4[i].time)
                        list10.unshift(risklist4[i].protective)
                        list11.unshift(risklist4[i].insulation)
                        list12.unshift(risklist4[i].ascend)
                    }
                    // for(let key  in risklist4){
                    //     list9.unshift(key)
                    //     list10.unshift(risklist4[key][0].protective)
                    //     list11.unshift(risklist4[key][0].insulation)
                    //     list12.unshift(risklist4[key][0].ascend)
                    // }
                    optionss.xAxis['data'] =list9;
                    optionss.series[0]['data'] = list10;
                    optionss.series[1]['data'] = list11;
                    optionss.series[2]['data'] = list12;
                    self.option3(optionss);

                    //按类型同比统计报废数量
                    var optionsss = self.option4();
                    var risklist5=res.data.resultValue.DISUSE
                    for(var i=0;i<risklist5.length;i++){
                        list13.unshift(risklist5[i].time)
                        list14.unshift(risklist5[i].protective)
                        list15.unshift(risklist5[i].insulation)
                        list16.unshift(risklist5[i].ascend)
                    }
                    // for(let key  in risklist5){
                    //     list13.unshift(key)
                    //     list14.unshift(risklist5[key][0].protective)
                    //     list15.unshift(risklist5[key][0].insulation)
                    //     list16.unshift(risklist5[key][0].ascend)
                    // }
                    optionsss.xAxis['data'] =list13;
                    optionsss.series[0]['data'] = list14;
                    optionsss.series[1]['data'] = list15;
                    optionsss.series[2]['data'] = list16;
                    self.option4(optionsss);
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