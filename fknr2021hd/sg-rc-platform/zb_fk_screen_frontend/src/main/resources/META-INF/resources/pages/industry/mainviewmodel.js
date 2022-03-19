define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        self.time = ko.observable("");
        self.endTime =ko.observable("");
        self.isShowTime = false;
        self.RESERVOI_CNT=ko.observable("0");//当前水库共计
        self.WHETHER_TO_PASS_CNT=ko.observable("0");
        self.HYDROPOWERSTATION_CNT=ko.observable("0");
        self.INSTALLRD_CAPACITY_SUM=ko.observable("0");
        self.RESERVOI_CNT=ko.observable("0");
        self.RESERVOI_SUM=ko.observable("0");
        self.RESERVOI_CNTS=ko.observable("0");
        self.proportion=ko.observable("");//占比

        self.licheng=ko.observable("0");
        self.CAVERN_MILEAGE_SUM=ko.observable("0");
        self.EARTHWORK_VOLUME_SUM=ko.observable("0");

        self.BIOLOGICALMATER_CNT=ko.observable("0");
        self.STORAGE_NUM=ko.observable("0");

        self.COMPANY_CNT=ko.observable("0");
        self.PROVINCIAL_COMPANY_CNT=ko.observable("0");
        self.INDUSTRY_SL=ko.observable("0");
        self.INDUSTRY_XS=ko.observable("0");
        self.INDUSTRY_FL=ko.observable("0");
        self.INDUSTRY_TY=ko.observable("0");
        self.INDUSTRY_CN=ko.observable("0");

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
            {id:'01',text:'直属产业'},
            {id:'02',text:'水电厂'},
            {id:'03',text:'抽水蓄能'},
            {id:'04',text:'大坝'},
            {id:'05',text:'电工制造企业'},
            {id:'06',text:'风力发电'},
            {id:'07',text:'太阳能发电'},
            {id:'08',text:'储能发电'},
		]);
		self.dwfx =ko.observable("1");
       self.data = [ ];
        var geoCoordMap = {};
        initMap()
        safetyRisk()
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
        self.eventChange = function(e) {
            safetyRisk()
        }
        //各省市产业安全行风险数
        function safetyRisk() {
            var jsonStr = {
                "industryType":self.dwfx(),
                "startTime":self.time(),
                "endTime":self.endTime(),
            }
            self.isShow(true)
            axios.post(cube.gatewayURL + "riskIndusRiskWarn/srpRiskIndusRiskWarnAreaCNT",jsonStr).then(function(res) {
                if (res.data.successful) {
                    var data = res.data.resultValue;
                    self.data([]);
                    for(var i = 0;i<data.length;i++){
                        self.data.push({
                            name:data[i]['DATAREPORT_ORG'],
                            value:data[i]['GRIDWARN_CNT']
                        })
                    }
                    // console.log(self.data())
                    initMap()

                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }
        //水库、大坝、水电站情况统计数
        axios.get(cube.gatewayURL + "riskIndusRiskWarn/srpRiskStationInfo").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                // console.log(data)
                self.RESERVOI_CNT(data.reservoirStationInfo.RESERVOI_CNT);//当前水库共计
                self.WHETHER_TO_PASS_CNT(data.reservoirStationInfo.RESERVOI_SUM);//总库超一亿立方米
                self.HYDROPOWERSTATION_CNT(data.daminforStationInfo.RESERVOI_CNT);//当前大坝共计
                self.proportion(data.daminforStationInfo.proportion);//占比
                self.INSTALLRD_CAPACITY_SUM(data.daminforStationInfo.WHETHER_TO_PASS_CNT);//通过安全性注册数
                 self.RESERVOI_CNTS(data.hydroPowerStationInfo.HYDROPOWERSTATION_CNT);//当前水电站共计
                self.RESERVOI_SUM(data.hydroPowerStationInfo.INSTALLRD_CAPACITY_SUM);//装机容量
                // self.RESERVOI_CNT(data.daminforStationInfo.RESERVOI_CNT);

            } else {
                cube.indicate("数据加载失败");
            }

        })
        //抽水蓄能电站洞室施工建设情况汇总
        axios.get(cube.gatewayURL + "riskIndusRiskWarn/srpRiskHydroPowerStationInfo").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                // console.log(res.data.resultValue)
                self.licheng(data.INSTALLRD_CAPACITY_SUM);
                self.CAVERN_MILEAGE_SUM(data.CAVERN_MILEAGE_SUM);
                self.EARTHWORK_VOLUME_SUM(data.EARTHWORK_VOLUME_SUM);

            } else {
                cube.indicate("数据加载失败");
            }

        })
        //生物质料场情况汇总
        axios.get(cube.gatewayURL + "riskIndusRiskWarn/srpRiskBiologicalMaterInfo").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                // console.log(res.data.resultValue)
                self.BIOLOGICALMATER_CNT(data.BIOLOGICALMATER_CNT);
                self.STORAGE_NUM(data.STORAGE_NUM);


            } else {
                cube.indicate("数据加载失败");
            }

        })
        //企业分布情况汇总数
        axios.get(cube.gatewayURL + "riskIndusRiskWarn/srpRiskGridWarnCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                // console.log(res.data.resultValue)
                self.COMPANY_CNT(data.PROVINCIAL_COMPANY_CNT);
                self.PROVINCIAL_COMPANY_CNT(data.COMPANY_CNT);
                self.INDUSTRY_SL(data.INDUSTRY_SL);
                self.INDUSTRY_XS(data.INDUSTRY_XS);
                self.INDUSTRY_FL(data.INDUSTRY_FL);
                self.INDUSTRY_TY(data.INDUSTRY_TY);
                self.INDUSTRY_CN(data.INDUSTRY_CN);


            } else {
                cube.indicate("数据加载失败");
            }

        })
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});