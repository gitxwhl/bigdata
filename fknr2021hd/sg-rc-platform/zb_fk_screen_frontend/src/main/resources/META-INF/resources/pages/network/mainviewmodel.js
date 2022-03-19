define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {

        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        self.time = ko.observable("");
        self.endTime = ko.observable("");
        self.isShowTime = false;
        self.tanchu = function(){
            self.isShowTime(!self.isShowTime())
        }
        self.search = function(){
            // 时间空格校验
            // if(self.time().trim().length !== self.time().length) {
            //     cube.indicate("时间选择框内容不能全空格或首尾有空格！");
            //     return;
            // }
            // if(self.endTime().trim().length !== self.endTime().length) {
            //     cube.indicate("时间选择框内容不能全空格或首尾有空格！");
            //     return;
            // }
            // 时间格式校验
            if(self.time().trim() || self.endTime().trim()) {
                var begin_r = self.time().trim() ? self.time().trim().match(/^(\d{4})(-)(\d{2})(-)(\d{2})\s(\d{2})(:)(\d{2})(:)(\d{2})*/) : 1;
                var end_r = self.endTime().trim() ? self.endTime().trim().match(/^(\d{4})(-)(\d{2})(-)(\d{2})\s(\d{2})(:)(\d{2})(:)(\d{2})*/) : 1;
                if(begin_r == null || end_r == null){
                    cube.indicate("请输入正确的时间格式");
                    return;
                }else if (self.time().trim() && self.endTime().trim()) {
                    if (self.time().trim() > self.endTime().trim()) {
                        cube.indicate("开始时间不能大于结束时间");
                        return;
                    }
                }
            }
            safetyRisk()
        }
        var regions = [
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
		//电网风险
        self.dwfxContent=ko.observableArray([
            {id:'',text:'正常'},
            {id:'01',text:'一级'},
            {id:'02',text:'二级'},
            {id:'03',text:'三级'},
            {id:'04',text:'四级'},
            {id:'05',text:'五级'}
		]);
		self.dwfx =ko.observable("01");
        self.zyfxContent=ko.observableArray([
            // {id:'',text:'系统应用运行情况'},
            {id:'01',text:'ERP系统'},
            {id:'02',text:'生产管理'},
            {id:'03',text:'营销管理'},
            {id:'04',text:'协同办公'},
            {id:'05',text:'统计管理'}
		]);
		self.zyfx = ko.observable("");
        self.statusContent=ko.observableArray([
            {id:'01',text:'正常'},
            {id:'02',text:'预警'},
            {id:'03',text:'告警'},
            {id:'04',text:'停机检修'},
            {id:'05',text:'非停机检修'}
        ]);
        self.status = ko.observable("");
        self.eventChange = function(e) {
            safetyRisk()
        }
        self.eventChange2 = function(e) {
            safetyRisk()
        }
        self.eventChange3 = function(e) {
            safetyRisk()
        }
        self.data = [];
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
                regions: regions
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
        var myEcharts=""
        function initMap(name) {
            if (myEcharts !=null && myEcharts != "" && myEcharts !=undefined){
                myEcharts.dispose();//销毁
            }
			var mapUrl = 'resources/map/china.json';
			$.get(mapUrl, function (mapJson) {
				 myEcharts = echarts.init(document.getElementById('china_map'))
                echarts.registerMap('china', mapJson);
                var mapFeatures = echarts.getMap('china').geoJson.features;
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
        safetyRisk()
        //安全风险综合情况
        function safetyRisk() {
            var jsonStr = {
                "warningLevel":self.dwfx(), //风险类型
                "systremApplication":self.zyfx(), //输电线路
                "netoprationStatus":self.status(),    //风险等级
                "startTime":self.time(),      //开始时间
                "endTime":self.endTime(),      //截至时间
            }
            self.isShow(true)
            axios.post(cube.gatewayURL + "riskEventWarn/srpRiskEventWarnAreaCNT",jsonStr).then(function(res) {
                if (res.data.successful) {
                    var data = res.data.resultValue;
                    self.data([])
                    for(var i = 0;i<data.length;i++){
                        self.data.push({
                            name:data[i]['DATAREPORT_ORG'],
                            value:data[i]['GRIDWARN_CNT']
                        })
                    }
                    initMap()
                    // console.log(self.data())
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