define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
		//电网风险
        self.dwfxContent=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'极高风险'},
            {id:'02',text:'高度风险'},
            {id:'03',text:'显著风险'},
            {id:'04',text:'一般风险'},
            {id:'05',text:'稍有风险'}
		]);
        self.dwfx = ko.observable('');
        self.cityName = '';
        self.zyfxContent=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'生产检修'},
            {id:'02',text:'大修挤改'},
            {id:'03',text:'基建工程'},
            {id:'04',text:'营销作业'},
            {id:'05',text:'配弄网工程'},
            {id:'06',text:'信息通信'},
            {id:'07',text:'外部施工'}
		]);
        self.zyfx = ko.observable('');;
        self.name = 'china';
        var myEcharts
        self.data = cube.array()
        self.isCity = false;
        self.time = ko.observable('');
        self.endTime = ko.observable('');
        self.isShowTime = false;
        self.test= ko.observable({});

        //人员详情
        self.infoContent = ko.observable('');
        self.infoTime = ko.observable('');
        self.infoType = ko.observable('');
        self.infoPlanUnit = ko.observable('');
        self.infoProjectName = ko.observable('');
        self.infoTdType = ko.observable('');
        self.infoWbUnit = ko.observable('');
        self.infoJlUnit = ko.observable('');
        self.infoName = ko.observable('');
        self.infoIpone = ko.observable('');
        self.infoNum = ko.observable('');
        self.infoZyfxdj = ko.observable('');
        self.infoDwfxdj = ko.observable('');
        self.infoDydj = ko.observable('');
        self.infoIsdd = ko.observable('');
        
        self.eventChange = function(){
            WorkWarnForAllArea()
        }
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
            WorkWarnForAllArea()
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
        }
        self.sqCode = ko.observable(1)
        self.cityCode = ko.observable('')
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
                // option.geo.zoom = name == 'china'?1.2:1;
                option.geo.zoom = 1.2;
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
                //         if(nosf.indexOf(e.name) == -1){
                //             self.name(e.name);
                //             self.cityCode(cityD[e.name])
                //             initMap(e.name)
                //             WorkWarnForAllArea()
                //         }
                //
                //     })
                // }else{
                //     myEcharts.on('click',function(e){
                //         self.cityName(e.name);
                //         self.sqCode(sqCodeObj[e.name])
                //         self.isCity(true);
                //         ConstWorkList()
                //     })
                // }
                
			})
        }
        WorkWarnForAllArea()
        function WorkWarnForAllArea(){
            var jsonStr = {
                "warningLevel":self.dwfx(),
                "areaId":self.cityCode(),
                "workType":self.zyfx(),
                "startTime":self.time(),
                "endTime":self.endTime(),
            }
            // var jsonStr = {
            //     "warningLevel":"",
            //     "areaId":self.cityCode(),
            //     "workType":"",
            //     "startTime":self.time(),
            //     "endTime":self.endTime(),
            // }
            self.isShow(true)
            self.test({'warningLevel':self.dwfx(),'workType':self.zyfx(),'startTime':self.time(),'endTime':self.endTime()})
            axios.post(cube.gatewayURL + "riskWorkWarnForAll/srpRiskWorkWarnForAllAreaCNT",jsonStr).then(function(res) {
                if (res.data.successful == true) {
                    var data = res.data.resultValue;
                    self.data([]);
                    for(var i = 0;i<data.length;i++){
                        self.data.push({
                            name:data[i]['DATAREPORT_ORG'],
                            value:data[i]['PLANWARN_CNT']
                        })
                    }
                    initMap(self.name())
                }else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }
        self.fanhui = function(){
            if(self.isCity()){
                self.isCity(false)
                self.showDetail(false)
                initMap(self.name());
            }else{
                self.name('china')
                self.cityCode('')
                initMap('china')
                WorkWarnForAllArea()
            }
            
        }
        function ConstWorkList() {

            var jsonStr = {
                    "page":self.pageIndex(),
                    "size":"10", 
                    "params":{
                        "cityID":self.sqCode()
                    }
                }
            axios.post(cube.gatewayURL + "riskWorkWarnForAll/srpRiskGridConstWorkList",jsonStr).then(function(res) {
                if (res.data.successful) {
                    var risklist = res.data.resultValue.content
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
            
            axios.get(cube.gatewayURL + "riskWorkWarnForAll/srpRiskWorkPlanInfo?workPlanId=" + id).then(function(res) {
                if (res.data.successful) {
                    var data = res.data.resultValue;
                    self.infoContent(data.WORKCONTENTS);
                    self.infoTime(data['BEGIN_TIME']);
                    self.infoType(data['WORK_TYPE']);
                    self.infoPlanUnit(data['WORK_PLACE']);
                    self.infoProjectName(data['PROJECT_NAME']);
                    self.infoTdType(data['POWER_CUT']);
                    self.infoWbUnit(data['SUBCONTRACT_ORG']);
                    self.infoJlUnit(data['SUPERVISORORG']);
                    self.infoName(data['WORK_MANAGER']);
                    self.infoIpone(data['WORK_MANAGER_CONTACT']);
                    self.infoNum(data['VIOLATION_CNT']);
                    self.infoZyfxdj(data['WORKRISK_LEVEL']);
                    self.infoDwfxdj(data['GRIDRISK_LEVEL']);
                    self.infoDydj(data['VOLTAGE_LEVEL']);
                    self.infoIsdd(data['LIVE_WORK_FLAG']);
    
                } else {
                    cube.indicate("数据加载失败");
                }
    
            })
        }
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});