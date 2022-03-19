define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        var fxObj = {
            "01":[
                // {id:'',text:'等级'},
                {id:'1',text:'一级'},
                {id:'2',text:'二级'},
                {id:'3',text:'三级'},
                {id:'4',text:'四级'},
                {id:'5',text:'五级'},
                {id:'6',text:'六级'},
                {id:'7',text:'七级'},
                {id:'8',text:'八级'}
            ],
            "02":[
                {id:'',text:'等级'},
                {id:'1',text:'极高风险'},
                {id:'2',text:'高度风险'},
                {id:'3',text:'显著风险'},
                {id:'4',text:'一般风险'},
                {id:'5',text:'稍有风险'}
            ],
            "03":[
                {id:'',text:'等级'},
                {id:'1',text:'一级'},
                {id:'2',text:'二级'},
                {id:'3',text:'三级'},
                {id:'4',text:'四级'},
                {id:'5',text:'五级'},
            ],
            "04":[
                {id:'',text:'等级'},
                {id:'1',text:'一般'},
                {id:'2',text:'重大'},
                {id:'3',text:'特别重大'}
            ],
            "05":[
                {id:'',text:'等级'},
                {id:'1',text:'一级'},
                {id:'2',text:'二级'},
                {id:'3',text:'三级'},
                {id:'4',text:'四级'},
                {id:'5',text:'五级'},
            ],
        }
		//电网风险
        self.dwfxContent=ko.observableArray([
            {id:'1',text:'电网风险'},
            {id:'2',text:'作业风险'},
            // {id:'3',text:'电网建设'},
            {id:'4',text:'产业风险'},
            {id:'5',text:'网络风险'}
		]);
		self.dwfx = ko.observable('01');
        self.zyfxContent=ko.observableArray([
            {id:'',text:'全部'},
            {id:'1',text:'一级'},
            {id:'2',text:'二级'},
            {id:'3',text:'三级'},
            {id:'4',text:'四级'},
            {id:'5',text:'五级'},
            {id:'6',text:'六级'},
            {id:'7',text:'七级'},
            {id:'8',text:'八级'}
		]);
        self.zyfx = ko.observable('');
        self.eventChange = function(){
            // self.zyfx('')
            // self.zyfxContent(fxObj[self.dwfx()])
            WorkWarnArea()
        }
        self.eventChange1 = function(){
            WorkWarnArea()
        }
        
        self.data = cube.array()
    
        var geoCoordMap = {};
        initMap()
		var option = {
            tooltip: {
                textStyle: {
                    color: '#fff'
                },
                backgroundColor: '#1F4FAF',
                formatter: function(params) {
                    var name = params.name;
                    var val = params.data.value[2]
                    return name+'：'+val;
                }
                
            },
            geo: {
                show: true,
				map: "china",
				label: {
					normal: {
						show: true,
						color: "#fff"
					},
					emphasis: {
                        show: false,
                        color: "#1DE9B6"
					}
				},
				roam: false,
				zoom: 1.2,
				itemStyle: {
					normal: {
                        borderColor: "#87B9EE", //每个区域的边框色
                        areaColor: {//区域背景色
                            type: 'radial',
                            x: 0.5,
                            y: 0.5,
                            r: 0.8,
                            colorStops: [{
                                offset: 0,
                                color: 'rgba(147, 235, 248, 0)' // 0% 处的颜色
                            }, {
                                offset: 1,
                                color: 'rgba(147, 235, 248, .2)' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        },
                        borderWidth: 2
					},
					emphasis: {
					    areaColor: "#4499d0"
					}
                },
                regions: [
                    {
                        name: '广东',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '云南',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '广西',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '贵州',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '台湾',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '海南',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '香港',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '澳门',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '蒙西',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    }
                ]
            },
            series: [
                {
                    type: 'effectScatter',
                    coordinateSystem: 'geo',
                    data: [],
                    symbolSize: function(val) {
                        // var v = val[2] / 10;
                        var v = val[2];
                        // var a = v==0?0:v<10?10:v>30?30:v;
                        var a = v==0?0:5;
                        return a;
                    },
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    hoverAnimation: false,
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'left',
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: 'rgba(0, 255, 255, .3)',
                            shadowBlur: 10,
                            shadowColor: '#1DE9B6'
                        }
                    },
                    zlevel: 1
                },
        
            ]
        }
        
        function convertData(data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var geoCoord = geoCoordMap[data[i].name];
                if (geoCoord) {
                    res.push({
                        name: data[i].name,
                        value: geoCoord.concat(data[i].value),
                    });
                }
            }
            return res;
        };
		function initMap() {
			var mapUrl = 'resources/map/china.json';
			$.get(mapUrl, function (mapJson) {
				var myEcharts = echarts.init(document.getElementById('china_map'))
                echarts.registerMap('china', mapJson);
                var mapFeatures = echarts.getMap('china').geoJson.features;
                geoCoordMap = {}
                mapFeatures.forEach(function(v) {
                    // 地区名称
                    var name = v.properties.name;
                    // 地区经纬度
                    geoCoordMap[name] = v.properties.cp;
                
                });
                option.series[0].data = convertData(self.data())
				myEcharts.setOption(option);
			})
        }
        WorkWarnArea()
        function WorkWarnArea(){
            var jsonStr = {
                "warningLevel":self.zyfx(),
                "warnType":self.dwfx()
            }
            self.isShow(true)
            axios.post(cube.gatewayURL + "riskWorkWarn/srpRiskWorkWarnAreaCNT",jsonStr).then(function(res) {
                if (res.data.successful == true) {
                    var data = res.data.resultValue;
                    self.data([]);
                    for(var i = 0;i<data.length;i++){
                        self.data.push({
                            name:data[i]['DATAREPORT_ORG'],
                            value:data[i]['GRIDWARN_CNT']
                        })
                    }
                    initMap()
                }else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }

		self.pieOption = {
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} ({d}%)"
            },
            title: {
                text: '事件总数',
                subtext: "0",
                left: 'center',
                top: 'center',
                textStyle: {
                    color: '#F99C56',
                    fontSize: 14
                },
                subtextStyle: {
                    fontSize: 14,
                    color: ['#F99C56']
                }
            },
            legend: {
                orient: 'vertical',
                top: 'center',
                right: "6",
                itemWidth: 12, // 设置宽度
				itemHeight: 10, // 设置高度
				textStyle: {
					color: '#A3E2F4',
					fontSize: 12,
					fontWeight: 0
				},
                data: ['1-4级','5级','6级','7级','8级']
            },
            series: [
                // 主要展示层的
                {
                    radius: ['50%', '70%'],
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
                            length: 15
                        }
                    },
                    data: [
                        {value:0,name:'1-4级'},
                        {value:0, name:'5级'},
                        {value:0, name:'6级'},
                        {value:0, name:'7级'},
                        {value:0, name:'8级'}]

                },
                // 边框的设置
                {
                    radius: ['50%', '45%'],
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
				}, 
				{
                    name: '外边框',
                    type: 'pie',
                    clockWise: false, //顺时加载
                    hoverAnimation: false, //鼠标移入变大
                    center: ['50%', '50%'],
                    radius: ['70%', '72%'],
                    label: {
                        normal: {
                            show: false
                        }
					},
					tooltip: {
                        show: false
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
		self.pieOption1 = {
			tooltip: {
				trigger: 'item',
				formatter: "{b} : {c} ({d}%)"
			},
			series: [{
				type: 'pie',
				radius: '80%',
				center: ['50%', '50%'],
				color: ['rgb(131,249,103)', '#FBFE27', '#FE5050', '#1DB7E5'], //'#FBFE27','rgb(11,228,96)','#FE5050'
				data: [{
						value: 0,
						name: '1级'
					},
					{
						value: 0,
						name: '2级'
					},
					{
						value: 0,
						name: '3级'
					},
					{
						value: 0,
						name: '4级'
					}
				].sort(function(a, b) {
					return a.value - b.value
				}),
				roseType: 'radius',
		
				label: {
					normal: {
						formatter: ['{c|{c}}', '{b|{b}}'].join('\n'),
						rich: {
							c: {
								color: 'rgb(241,246,104)',
								fontSize: 14,
								lineHeight: 2
							},
							b: {
								color: 'rgb(98,137,169)',
								fontSize: 12,
								height: 30
							},
						},
					}
				},
				labelLine: {
					normal: {
						lineStyle: {
							color: 'rgb(98,137,169)',
						},
						smooth: 0.2,
						length: 10,
						length2: 20,
		
					}
				}
			}]
        };
        
        //电网运行风险等级
        axios.get(cube.gatewayURL + "riskWorkWarn/srpRiskGridWarnLevelCNT").then(function(res) {
            if (res.data.successful == true) {
                var data = res.data.resultValue;
                var option = self.pieOption();
                option.series[0].data[0].value = data[0]['WARNINGLEVEL1'] + data[0]['WARNINGLEVEL2'] + data[0]['WARNINGLEVEL3'] + data[0]['WARNINGLEVEL4']
                option.series[0].data[1].value = data[0]['WARNINGLEVEL5'];
                option.series[0].data[2].value = data[0]['WARNINGLEVEL6'];
                option.series[0].data[3].value = data[0]['WARNINGLEVEL7'];
                option.series[0].data[4].value = data[0]['WARNINGLEVEL8'];
                option.title.subtext = data[0]['WARNINGLEVEL'];
                self.pieOption(option)
            } else {
                cube.indicate("数据加载失败");
            }

        })
        //电网建设风险等级
        axios.get(cube.gatewayURL + "riskWorkWarn/srpSrpRiskGridConstWarnLevelCNT").then(function(res) {
            if (res.data.successful == true) {
                var data = res.data.resultValue;
                var option = self.pieOption1();
                option.series[0].data[0].value = data['LEVEL1'];
                option.series[0].data[1].value = data['LEVEL2'];
                option.series[0].data[2].value = data['LEVEL3'];
                option.series[0].data[3].value = data['LEVEL4'];
                self.pieOption1(option)
            } else {
                cube.indicate("数据加载失败");
            }

        })
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});