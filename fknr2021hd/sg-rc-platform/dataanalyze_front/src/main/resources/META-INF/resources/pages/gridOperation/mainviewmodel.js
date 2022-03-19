define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
        self.WARNINGLEVEL1='';//一级
        self.WARNINGLEVEL2='';//二级
        self.WARNINGLEVEL3='';//三级
        self.WARNINGLEVEL4='';//四级
        self.WARNINGLEVEL5='';//五级
        self.WARNINGLEVEL6='';//六级
        self.WARNINGLEVEL7='';//七级
        self.WARNINGLEVEL8='';//八级
        self.WARNINGLEVEL='';//电网风险总数
        self.WORK_PLAN_WARN_CNT='';//关联作业总数
        self.data = [];
        self.Name=ko.observable("");//标题
        self.number=ko.observable("");//编号
        self.time = ko.observable(s2 + ' 00:00:00');//预警计划时间
        self.endTime=ko.observable(s2 + ' 23:59:59');//预警截至时间
        self.style = '';
        self.isShowRowNumber = true;
        self.isShowCheckBox = false;
        self.isShowBorder = true;
        self.isShowStripe = false;
        self.isShowHover = true;
        self.isShowCondense = false;
        self.isAllowEdit = false;
        self.isAllowDelete = false;
        self.isAllowOperations = false;
        self.isAllowShift = false;
        self.isAllowSort = false;
        self.isAllowAppend = false;
        self.isAllowPaging = false;
        self.primaryKey='GRIDWARNNOTICE_ID';
        self.pageSize = 1;
        self.id='';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount =1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                riskGridWarnList()
            }
        }
        //各省市电网运行风险数
        self.eventChange = function(e) {
            safetyRisk()
        }
        //当日
        self.day=function (flag) {
            axios.get(cube.gatewayURL + "riskGridWarn/riskGridWarnTabCNT/1").then(function(res) {
                if (true) {
                    // var data = res.data.resultValue;
                    // console.log(res.data.resultValue[1].WORK_PLAN_WARN_CNT)
                    self.WARNINGLEVEL1(res.data.resultValue[0].WARNINGLEVEL1);//一级
                    self.WARNINGLEVEL2(res.data.resultValue[0].WARNINGLEVEL2);//二级
                    self.WARNINGLEVEL3(res.data.resultValue[0].WARNINGLEVEL3);//三级
                    self.WARNINGLEVEL4(res.data.resultValue[0].WARNINGLEVEL4);//四级
                    self.WARNINGLEVEL5(res.data.resultValue[0].WARNINGLEVEL5);//五级
                    self.WARNINGLEVEL6(res.data.resultValue[0].WARNINGLEVEL6);//六级
                    self.WARNINGLEVEL7(res.data.resultValue[0].WARNINGLEVEL7);//七级
                    self.WARNINGLEVEL8(res.data.resultValue[0].WARNINGLEVEL8);//八级
                    self.WARNINGLEVEL(res.data.resultValue[0].WARNINGLEVEL);//电网风险总数
                    self.WORK_PLAN_WARN_CNT(res.data.resultValue[1].WORK_PLAN_WARN_CNT);//关联作业总数
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            $(".day").css("background-image", "url('./resources/images/gridOperation/u1132.png')");
            $(".month").css("background-image", "url('./resources/images/gridOperation/u1134.png')");
            $(".year").css("background-image", "url('./resources/images/gridOperation/u1134.png')");
        }
        //月
        self.month=function (flag) {
            axios.get(cube.gatewayURL + "riskGridWarn/riskGridWarnTabCNT/2").then(function(res) {
                if (true) {
                    // var data = res.data.resultValue;
                    // console.log(res.data.resultValue[1].WORK_PLAN_WARN_CNT)
                    self.WARNINGLEVEL1(res.data.resultValue[0].WARNINGLEVEL1);//一级
                    self.WARNINGLEVEL2(res.data.resultValue[0].WARNINGLEVEL2);//二级
                    self.WARNINGLEVEL3(res.data.resultValue[0].WARNINGLEVEL3);//三级
                    self.WARNINGLEVEL4(res.data.resultValue[0].WARNINGLEVEL4);//四级
                    self.WARNINGLEVEL5(res.data.resultValue[0].WARNINGLEVEL5);//五级
                    self.WARNINGLEVEL6(res.data.resultValue[0].WARNINGLEVEL6);//六级
                    self.WARNINGLEVEL7(res.data.resultValue[0].WARNINGLEVEL7);//七级
                    self.WARNINGLEVEL8(res.data.resultValue[0].WARNINGLEVEL8);//八级
                    self.WARNINGLEVEL(res.data.resultValue[0].WARNINGLEVEL);//电网风险总数
                    self.WORK_PLAN_WARN_CNT(res.data.resultValue[1].WORK_PLAN_WARN_CNT);//关联作业总数
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            $(".day").css("background-image", "url('./resources/images/gridOperation/u1134.png')");
            $(".month").css("background-image", "url('./resources/images/gridOperation/u1132.png')");
            $(".year").css("background-image", "url('./resources/images/gridOperation/u1134.png')");
        }
        //年
        self.year=function (flag) {
            axios.get(cube.gatewayURL + "riskGridWarn/riskGridWarnTabCNT/3").then(function(res) {
                if (true) {
                    // var data = res.data.resultValue;
                    // console.log(res.data.resultValue[1].WORK_PLAN_WARN_CNT)
                    self.WARNINGLEVEL1(res.data.resultValue[0].WARNINGLEVEL1);//一级
                    self.WARNINGLEVEL2(res.data.resultValue[0].WARNINGLEVEL2);//二级
                    self.WARNINGLEVEL3(res.data.resultValue[0].WARNINGLEVEL3);//三级
                    self.WARNINGLEVEL4(res.data.resultValue[0].WARNINGLEVEL4);//四级
                    self.WARNINGLEVEL5(res.data.resultValue[0].WARNINGLEVEL5);//五级
                    self.WARNINGLEVEL6(res.data.resultValue[0].WARNINGLEVEL6);//六级
                    self.WARNINGLEVEL7(res.data.resultValue[0].WARNINGLEVEL7);//七级
                    self.WARNINGLEVEL8(res.data.resultValue[0].WARNINGLEVEL8);//八级
                    self.WARNINGLEVEL(res.data.resultValue[0].WARNINGLEVEL);//电网风险总数
                    self.WORK_PLAN_WARN_CNT(res.data.resultValue[1].WORK_PLAN_WARN_CNT);//关联作业总数
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            $(".day").css("background-image", "url('./resources/images/gridOperation/u1134.png')");
            $(".month").css("background-image", "url('./resources/images/gridOperation/u1134.png')");
            $(".year").css("background-image", "url('./resources/images/gridOperation/u1132.png')");
        }
        self.warningSituation=ko.observableArray([
            {id:null,text:'全部'},
            {id:'01',text:'一级'},
            {id:'02',text:'二级'},
            {id:'03',text:'三级'},
            {id:'04',text:'四级'},
            {id:'05',text:'五级'},
            {id:'06',text:'六级'},
            {id:'07',text:'七级'},
            {id:'08',text:'八级'},
        ]);
        self.warning =ko.observable(null);
        self.riskGrade=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'一级'},
            {id:'02',text:'二级'},
            {id:'03',text:'三级'},
            {id:'04',text:'四级'},
            {id:'05',text:'五级'},
            {id:'06',text:'六级'},
            {id:'07',text:'七级'},
            {id:'08',text:'八级'},
        ]);
        self.Grade = ko.observable("");
        self.warningState=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'已发布'},
            {id:'02',text:'告知'},
            {id:'03',text:'实施'},
            {id:'04',text:'解除'},
            {id:'05',text:'延期'},
            {id:'06',text:'作废'},

        ]);
        self.State = ko.observable("");
        // self.releaseUnit =[{id:'',text:'全部'},];
        self.Unit = ko.observable("")
        self.diaHeight = document.documentElement.clientHeight *0.9+ 'px';
        self.columns = [
            // {name: "id",  readOnly:true, caption: "" , editorType: "TextEditor",align : "center",},
            {name: "TITLE",  width: "20%", readOnly:true, caption: "标题" ,align : "center", editorType: "TextEditor",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue) {
                    var title = '电网风险详情'
                        cube.showDialog({
                            title: title,
                            width: "90%",
                            height: self.diaHeight,
                            submitFormOnConfirm: false,
                            isShowCloseBtn: false, // 是否显示取消按钮
                            isShowConfirmBtn: false, // 是否显示保存按钮
                            templateOptions: {
                                name: 'gridOperation.detail.main',
                                params: {items: pkValue}
                            },
                        });
                }
                },
            // {name: "warnnum", width: "20%", readOnly:true, caption: "预警编号" , editorType: "TextEditor",align : "center",},
            {name: "warnnum", width: "20%",  caption: "预警编号" , editorType: "TextEditor",align : "center",},
            {name: "WARNINGLEVEL", width: "10%", caption: "预警等级" , editorType: "TextEditor",align : "center",},
            {name: "WARNBEGINTIME",width: "15%", caption: "预警计划开始时间" , editorType: "TextEditor",align : "center",},
            {name: "PUBLISH_ORG", width: "10%", caption: "发布单位" , editorType: "TextEditor",align : "center",},
            {name: "WARNSTATUS", width: "10%", caption: "预警状态" , editorType: "TextEditor",align : "center",},
        ];
        self.items=[]

        var geoCoordMap = {};
        initMap()
        safetyRisk()
        riskGridWarnList()
        //查询
        self.search = function() {
            riskGridWarnList()
        }
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
                        color: "#1DE9B6"
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
                        var v = val[2] / 30;
                        var a = v==0?0:v<10?10:v>30?30:v;
                        return a;
                        // return v<10?10:v>30?30:v;
                    },
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    hoverAnimation: true,
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'left',
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#1DE9B6',
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
                // console.log(option)
                myEcharts.setOption(option);
            })
        }
        //各省市电网运行风险数
        function safetyRisk() {
            // var jsonStr = {
            //     "warningLevel":self.warning(),
            // }
            axios.get(cube.gatewayURL + "riskGridWarn/srpRiskGridWarnCNT/" + self.warning()).then(function(res) {
                if (res.data.successful) {
                    var data = res.data.resultValue;
                    // console.log(data)
                    self.data([]);
                    for(var i = 0;i<data.length;i++){
                        self.data.push({
                            name:data[i]['DATAREPORT_ORG'],
                            value:data[i]['GRIDWARN_CNT']
                        })
                    }
                    initMap()

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //电网运行风险预警分页查询
        function riskGridWarnList() {
            var jsonStr2 = {
                "page":self.pageIndex(),
                "size": 10,
                "params": {
                    "title": self.Name(),//标题
                    "warningLevel": self.Grade(),//预警等级
                    "warnNum": self.number()+'',//预警编号
                    "startTime": self.time(),//开始时间
                    "endTime": self.endTime(),//结束时间
                    "publishOrg": self.Unit(),//发布单位
                    "warnStatus": self.State()//预警状态
                }
            }
            axios.post(cube.gatewayURL + "riskGridWarn/riskGridWarnList", jsonStr2).then(function (res) {
                if (true) {
                    var risklist = res.data.resultValue.content
                    self.count(res.data.resultValue.totalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.totalPage));
                    self.items(risklist);

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
              //电网运行风险等级汇总统计
        axios.get(cube.gatewayURL + "riskGridWarn/riskGridWarnTabCNT/1").then(function(res) {
            if (true) {
                // var data = res.data.resultValue;
                // console.log(res.data.resultValue[1].WORK_PLAN_WARN_CNT)
                self.WARNINGLEVEL1(res.data.resultValue[0].WARNINGLEVEL1);//一级
                self.WARNINGLEVEL2(res.data.resultValue[0].WARNINGLEVEL2);//二级
                self.WARNINGLEVEL3(res.data.resultValue[0].WARNINGLEVEL3);//三级
                self.WARNINGLEVEL4(res.data.resultValue[0].WARNINGLEVEL4);//四级
                self.WARNINGLEVEL5(res.data.resultValue[0].WARNINGLEVEL5);//五级
                self.WARNINGLEVEL6(res.data.resultValue[0].WARNINGLEVEL6);//六级
                self.WARNINGLEVEL7(res.data.resultValue[0].WARNINGLEVEL7);//七级
                self.WARNINGLEVEL8(res.data.resultValue[0].WARNINGLEVEL8);//八级
                self.WARNINGLEVEL(res.data.resultValue[0].WARNINGLEVEL);//电网风险总数
                self.WORK_PLAN_WARN_CNT(res.data.resultValue[1].WORK_PLAN_WARN_CNT);//关联作业总数
            } else {
                cube.indicate("数据加载失败");
            }

        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});