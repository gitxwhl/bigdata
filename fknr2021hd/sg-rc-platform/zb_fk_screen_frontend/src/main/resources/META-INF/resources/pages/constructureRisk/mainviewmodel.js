define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
		//电网风险
        self.dwfxContent=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'一级'},
            {id:'02',text:'二级'},
            {id:'03',text:'三级'},
            {id:'04',text:'四级'}
		]);
		self.dwfx = ko.observable('');
        // self.zyfxContent=ko.observableArray([
        //     {id:'',text:'专业类型'},
        //     {id:'1',text:'生产检修'},
        //     {id:'2',text:'大修挤改'},
        //     {id:'3',text:'基建工程'},
        //     {id:'4',text:'营销作业'},
        //     {id:'5',text:'配弄网工程'},
        //     {id:'6',text:'信息通信'},
        //     {id:'7',text:'外部施工'}
		// ]);
        // self.zyfx = '';
        self.name = 'china';
        self.eventChange = function(){
            srpRiskGrid()
        }
        var cityD = {
            // "china":"",
            // "北京":"1",
            // "天津":"2",
            // "河北":"3",
            // "冀北":"4",
            // "山西":"5",
            // "山东":"6",
            // "上海":"7",
            // "江苏":"8",
            // "浙江":"9",
            // "安徽":"10",
            // "福建":"11",
            // "湖北":"12",
            // "湖南":"13",
            // "河南":"14",
            // "江西":"15",
            // "四川":"16",
            // "重庆":"17",
            // "辽宁":"18",
            // "吉林":"19",
            // "黑龙江":"20",
            // "蒙东":"21",
            // "陕西":"22",
            // "甘肃":"23",
            // "青海":"24",
            // "宁夏":"25",
            // "新疆":"26",
            // "西藏":"27",
        }
        function getProvince() {
            axios.get(cube.gatewayURL + "riskEventWarn/getProvinceList").then(function (res) {
                for (let i = 0; i < res.data.resultValue.length; i++) {
                    cityD[res.data.resultValue[i].column_type_name] = res.data.resultValue[i].column_type_code
                }
            })
        }
        getProvince()
        var sqCodeObj = {
            "西安市":1,
            "咸阳市":2,
            "渭南市":3,
            "宝鸡市":4,
            "商洛市":5,
            "汉中市":6,
            "延安市":7,
            "榆林市":8,
            "安康市":9,
            "铜川市":10,

            "朝阳区":100,
            "怀柔区":101,
            "延庆区":102,
            "密云区":103,
            "平谷区":104,
            "顺义区":105,
            "昌平区":106,
            "房山区":107,
            "大兴区":108,
            "通州区":109,
            "丰台区":110,
            "西城区":111,
            "门头沟":112,
            "石景山":113,
            "海淀区":114,

        }
        self.sqCode = ko.observable(1)
        self.cityCode = ko.observable('')

        //人员详情
        self.infoName = ko.observable('');
        self.infoIpone = ko.observable('');
        self.infoProjectName = ko.observable('');
        self.infoNumber = ko.observable('');
        self.infoPlanNumber = ko.observable('');
        self.infoPeople = ko.observable('');
        self.infoPlanUnit = ko.observable('');
        self.infoType = ko.observable('');
        self.infoContent = ko.observable('');

        self.name = 'china';
        var myEcharts
        self.data = ko.observableArray([]);
        self.isCity = false;
        self.cityName = '';
        var geoCoordMap = {};
		initMap(self.name)
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
				zoom: 1,
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
                        // return 0<v<10?10:v>30?30:v;
                        
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
        }
        var nosf = ['蒙西','澳门','香港','广东','广西','海南','云南','台湾','贵州','南海诸岛']
		function initMap(name) {
            if (myEcharts != null && myEcharts != "" && myEcharts != undefined) {
                myEcharts.dispose();//销毁
            }
			// var mapUrl='resources/map/'+name+'.json';
            var mapUrl = 'resources/map/china.json';
			$.get(mapUrl, function (mapJson) {
				myEcharts = echarts.init(document.getElementById('china_map'))
                // echarts.registerMap(name, mapJson);
                echarts.registerMap('china', mapJson);
                // option.geo.map = name;
                option.geo.map = 'china';
                // var mapFeatures = echarts.getMap(name).geoJson.features;
                var mapFeatures = echarts.getMap('china').geoJson.features;
                geoCoordMap = {};
                mapFeatures.forEach(function(v) {
                    // 地区名称
                    var name = v.properties.name;
                    // 地区经纬度
                    geoCoordMap[name] = v.properties.cp;
                
                });
                option.series[0].data = convertData(self.data())
                myEcharts.setOption(option);
                // if(name == 'china'){
                //     myEcharts.on('click',function(e){
                //         $('.select').hide()
                //         if(nosf.indexOf(e.name) == -1){
                //             self.name(e.name);
                //             self.cityCode(cityD[e.name])
                //             initMap(e.name)
                //             srpRiskGrid()
                //         }
                //
                //     })
                // }else{
                //     myEcharts.on('click',function(e){
                //         $('.select').hide()
                //         self.cityName(e.name);
                //         self.sqCode(sqCodeObj[e.name])
                //         self.isCity(true);
                //         ConstWorkList()
                //     })
                // }
                
			})
		}
        self.fanhui = function(){
            if(self.isCity()){
                $('.select').show()
                self.isCity(false)
                self.showDetail(false)
                initMap(self.name());
            }else{
                $('.select').show()
                self.name('china')
                self.cityCode('')
                initMap('china')
                srpRiskGrid()
            }
            
        }
        srpRiskGrid()
        function srpRiskGrid() {
            var jsonStr = {
                "areaId":self.cityCode(),
                "warningLevel":self.dwfx()
            }
            self.isShow(true)
            axios.post(cube.gatewayURL + "riskGridConstWarnn/srpRiskGridConstWarnAreaCNT",jsonStr).then(function(res) {
                if (res.data.successful) {
                    var data = res.data.resultValue;
                    self.data([]);
                    for(var i = 0;i<data.length;i++){
                        self.data.push({
                            name:data[i]['DATAREPORT_ORG'],
                            value:data[i]['GRIDWARN_CNT']
                        })
                    }
                    initMap(self.name())
    
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }
        
        function ConstWorkList() {
		    console.log(self.sqCode())
            var jsonStr = {
                    "page":self.pageIndex(),
                    "size":"10", 
                    "params":{
                        "cityID":self.sqCode()
                    }
                }
            axios.post(cube.gatewayURL + "riskGridConstWarnn/srpRiskGridConstWorkList",jsonStr).then(function(res) {
                if (res.data.successful) {
                    var risklist = res.data.resultValue.content
                    console.log(risklist)
                    self.count(res.data.resultValue.totalSize);
                    self.pageTotalCount(res.data.resultValue.totalPage);
                    self.cityContent(risklist);
                    if(risklist.length>0){
                        ConstWorkPlanInfo(risklist[0]['WORK_PLAN_ID'])
                    }
                } else {
                    cube.indicate("数据加载失败");
                }
    
            })
        }
        self.showDetail = false;
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;
        self.onPageIndexChanged = function(p_pageIndex) {
            
        }
        self.shutDown = function(){
            self.isCity(false)
        }
        self.style ="background:#cccccc;";
        self.columns = [
            {name: "WORK_PLAN_ID", width:"9%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {
                name: "WORK_PLAN_NAME", width: "15%", readOnly: true, caption: "作业名称", editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                    
                },
                onClick: function (pkValue, cellValue) {
                    ConstWorkPlanInfo(pkValue)
                }
            },
            {name: "WORK_PLACE", width:"9%", readOnly:true, caption: "作业地点" , editorType: "TextEditor",align : "center"},
            {name: "isEnd", width:"8%", caption: "作业状态" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    
                }
            },

        ];
        self.cityContent = []
        function ConstWorkPlanInfo(id) {
            
            axios.get(cube.gatewayURL + "riskGridConstWarnn/srpRiskGridConstWorkPlanInfo?workPlanId=" + id).then(function(res) {
                if (res.data.successful) {
                    var data = res.data.resultValue;
                    self.infoName(data['WORK_MANAGER']);
                    self.infoIpone(data['WORK_MANAGER_CONTACT']);
                    self.infoProjectName(data['PROJECT_NAME']);
                    self.infoNumber(data['PROJECT_ID']);
                    self.infoPlanNumber(data['WORK_PLAN_CODE_DAY']);
                    self.infoPeople(data['staff_cnt']);
                    self.infoPlanUnit(data['WORK_ORG']);
                    self.infoType(data['column_type_name']);
                    self.infoContent(data['WORKCONTENTS']);
    
                } else {
                    cube.indicate("数据加载失败");
                }
    
            })
        }
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});