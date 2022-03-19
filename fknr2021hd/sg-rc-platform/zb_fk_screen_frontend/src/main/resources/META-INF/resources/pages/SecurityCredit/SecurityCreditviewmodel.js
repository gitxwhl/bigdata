define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        var value = 7103;
        self.titleName=ko.observable("")//查询姓名
        self.Name=''//姓名
        self.phone=''//手机
        self.IDnumber=''//身份证号
        self.gender=''//性别
        self.TheirIdentity=''//所属身份
        self.PlaceUnit=''//所在单位
        self.age=''//年龄

        self.province =[
            {id:'',text:'全部'},
            {id:'1',text:'北京'},
            {id:'2',text:'天津'},
            {id:'3',text:'河北'},
            {id:'4',text:'冀北'},
            {id:'5',text:'山西'},
            {id:'6',text:'山东'},
            {id:'7',text:'上海'},
            {id:'8',text:'江苏'},
            {id:'9',text:'浙江'},
            {id:'10',text:'安徽'},
            {id:'11',text:'福建'},
            {id:'12',text:'湖北'},
            {id:'13',text:'湖南'},
            {id:'14',text:'河南'},
            {id:'15',text:'江西'},
            {id:'16',text:'四川'},
            {id:'17',text:'重庆'},
            {id:'18',text:'辽宁'},
            {id:'19',text:'吉林'},
            {id:'20',text:'黑龙江'},
            {id:'21',text:'蒙东'},
            {id:'22',text:'陕西'},
            {id:'23',text:'甘肃'},
            {id:'24',text:'青海'},
            {id:'25',text:'宁夏'},
            {id:'26',text:'新疆'},
            {id:'27',text:'西藏'},

        ];
        self.provinceContent = ko.observable("")
        self.releaseUnit =[
            {id:'',text:'全部'},
            {id:'1',text:'北京xxxxx有限公司'},
            {id:'2',text:'上海xxxxx有限公司'},
        ];
        self.Unit = ko.observable("")


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
        self.primaryKey='BASICFILEINFO_ID';
        self.primaryKey2='ids';
        self.pageSize = 1;
        self.id='';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='0';
        self.pageTotalCount = '1';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount =1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
            }
        }
        self.columns = [
            {name: "BASICFILEINFO_ID",  readOnly:true, caption: "" , editorType: "TextEditor",align : "center",},
            {name: "NAME",  width: "10%", readOnly:true, caption: "姓名" ,align : "center", editorType: "TextEditor",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (items) {
                    commonPageBridge({
                        name: 'SecurityCredit.peopleDetail.main',
                        params: {item:items}
                    });
                }
            },
            {name: "SEX", width: "10%", caption: "性别" , editorType: "TextEditor",align : "center",},
            {name: "ID_CARD",width: "15%", caption: "身份证号" , editorType: "TextEditor",align : "center",},
            {name: "DATAREPORT_ORG", width: "10%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
            {name: "ORG_NAME", width: "10%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "SAFELEARNING_CNT", width: "10%", caption: "培训次数" , editorType: "TextEditor",align : "center",},
            {name: "SAFETYTEST_CNT", width: "10%", caption: "考试次数" , editorType: "TextEditor",align : "center",},
            {name: "POINTS", width: "10%", caption: "个人积分" , editorType: "TextEditor",align : "center",},
            {name: "STAFFVIOLATIO_CNT", width: "10%", caption: "违章次数" , editorType: "TextEditor",align : "center",},
        ];
        self.items=[]
        //人员详情查看
        // self.goparticulars = function (items) {
        //     commonPageBridge({
        //         name: 'SecurityCredit.peopleDetail.main',
        //         params: {item:items}
        //     });
        //
        // }

        self.option = {
            // backgroundColor: '000',
            title: {
                text: '',
                subtext: '人员总数',
                left: 'center',
                top: 'center', //top待调整
                textStyle: {
                    color: '#fff',
                    fontSize: 40,
                    fontFamily: 'DINAlternate-Bold',
                },
                subtextStyle: {
                    color: '#ff',
                    fontSize: 20,
                    fontFamily: 'PingFangSC-Regular',
                    top: '20'
                },
                itemGap: -7 //主副标题间距
            },
            xAxis: {
                splitLine: {
                    show: false
                },
                axisLabel: {
                    show: false
                },
                axisLine: {
                    show: false
                }
                // data: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
            },
            yAxis: {
                splitLine: {
                    show: false
                },
                axisLabel: {
                    show: false
                },
                axisLine: {
                    show: false
                }
            },
            series: [
                // 内圆
                {
                    type: 'pie',
                    radius: ['0', '50%'],
                    center: ['50%', '50%'],
                    z: 0,
                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#1147A1'
                            },
                                {
                                    offset: 0.5,
                                    color: '#423DB3'
                                },
                                {
                                    offset: 1,
                                    color: '#1147A1'
                                }
                            ]),
                            label: {
                                show: false
                            },
                            labelLine: {
                                show: false
                            }
                        },
                    },
                    label: {
                        normal: {
                            position: "center",

                        }
                    },
                    data: [100],
                },
                // 进度圈
                {
                    type: 'pie',
                    clockWise: true,
                    radius: ["60%", "55%"],
                    data: [{
                        value: value,
                        itemStyle: {
                            normal: {
                                borderWidth: 10,
                                borderColor: {
                                    colorStops: [{
                                        offset: 0,
                                        color: '#7940FE' || '#00cefc' // 0% 处的颜色
                                    }, {
                                        offset: 1,
                                        color: '#2A69EC' || '#367bec' // 100% 处的颜色
                                    }]
                                },
                                color: { // 完成的圆环的颜色
                                    colorStops: [{
                                        offset: 0,
                                        color: '#7940FE' || '#00cefc' // 0% 处的颜色
                                    }, {
                                        offset: 1,
                                        color: '#2A69EC' || '#367bec' // 100% 处的颜色
                                    }]
                                },
                                label: {
                                    show: false
                                },
                                labelLine: {
                                    show: false
                                },
                            },
                        }
                    },
                        {
                            name: 'gap',
                            value: 100 - value,
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    },
                                    labelLine: {
                                        show: false
                                    },
                                    color: 'rgba(0, 0, 0, 0)',
                                    borderColor: 'rgba(0, 0, 0, 0)',
                                    borderWidth: 0,
                                }
                            },
                        }
                    ]
                },
                //刻度尺
                {
                    // name: "白色圈刻度",
                    type: "gauge",
                    radius: "67%",
                    startAngle: 225, //刻度起始
                    endAngle: -134.8, //刻度结束
                    z: 4,
                    axisTick: {
                        show: true,
                        lineStyle: {
                            width: 2,
                            color: 'rgba(1,244,255, 0.9)'
                        }
                    },
                    splitLine: {
                        length: 16, //刻度节点线长度
                        lineStyle: {
                            width: 2,
                            color: 'rgba(1,244,255, 0.9)'
                        } //刻度节点线
                    },
                    axisLabel: {
                        color: 'rgba(255,255,255,0)',
                        fontSize: 12,
                    }, //刻度节点文字颜色
                    pointer: {
                        show: false
                    },
                    axisLine: {
                        lineStyle: {
                            opacity: 0
                        }
                    },
                    detail: {
                        show: false
                    },
                    data: [{
                        value: 0,
                        name: ""
                    }]
                },
                // 刻度圈
                {
                    "type": "pie",
                    "radius": ["0%", "73%"],
                    "center": ["50%", "50%"],
                    "avoidLabelOverlap": false,
                    "label": {
                        "normal": {
                            "show": false,
                            "position": "center"
                        },
                        "emphasis": {
                            "show": false,
                            "textStyle": {
                                "fontWeight": "bold"
                            }
                        }
                    },
                    "itemStyle": {
                        "normal": {
                            "color": {
                                "type": "linear",
                                "x": 0,
                                "y": 0,
                                "x2": 0,
                                "y2": 1,
                                "colorStops": [{
                                    "offset": 0.05,
                                    "color": "rgba(32,36,107, 0.2)"
                                }, {
                                    "offset": 0.5,
                                    "color": "rgba(32,36,107,0.3)"
                                }, {
                                    "offset": 0.95,
                                    "color": "rgba(32,36,107, 0.2)"
                                }]
                            }
                        }
                    },
                    "labelLine": {
                        "normal": {
                            "show": false
                        }
                    },
                    "data": [{
                        "value": 3235
                    }]
                },
                //最外层圈
                {
                    "type": "pie",
                    "radius": ["0%", "85%"],
                    "center": ["50%", "50%"],
                    "avoidLabelOverlap": false,
                    hoverAnimation: false,
                    "label": {
                        "normal": {
                            "show": false,
                            "position": "center"
                        },
                        "emphasis": {
                            "show": false,
                            "textStyle": {
                                "fontWeight": "bold"
                            }
                        }
                    },
                    "itemStyle": {
                        "normal": {
                            "color": {
                                "type": "linear",
                                "x": 0,
                                "y": 0,
                                "x2": 0,
                                "y2": 1,
                                "colorStops": [{
                                    "offset": 0.05,
                                    "color": "rgba(21,24,65, 0.2)"
                                }, {
                                    "offset": 0.5,
                                    "color": "rgba(21,24,65, 0.3)"
                                }, {
                                    "offset": 0.95,
                                    "color": "rgba(21,24,65, 0.2)"
                                }]
                            }
                        }
                    },
                    "labelLine": {
                        "normal": {
                            "show": false
                        }
                    },
                    "data": [{
                        "value": 3235
                    }]
                }
            ]
        };
        self.list2=ko.observable('');
        self.list3=ko.observable('');
        self.list4=ko.observable('');
        self.option2 = {
            series: [
                {
                    name: '违章人数',
                    type: 'pie',
                    radius: ['55%', '65%'],
                    center: ['15%', '50%'],
                    startAngle: 225,
                    color: [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: '#FDFF5C'
                    }, {
                        offset: 1,
                        color: '#FFDB5C'
                    }]), "transparent"],
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    label: {
                        normal: {
                            position: 'center'
                        }
                    },
                    data: [{
                        value: 75,
                        name: '违章人数',
                        label: {
                            normal: {
                                formatter: '违章人数\n',
                                textStyle: {
                                    color: '#fff',
                                    fontSize: 16

                                }
                            }
                        }
                    }, {
                        value: 25,
                        name: '%',
                        label: {
                            normal: {
                                formatter: self.list2(),
                                textStyle: {
                                    color: '#007ac6',
                                    fontSize: 20

                                }
                            }
                        }
                    }]
                },
                {
                    name: '参加考试人数',
                    type: 'pie',
                    radius: ['55%', '65%'],
                    center: ['50%', '50%'],
                    startAngle: 225,
                    color: [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: '#9FE6B8'
                    }, {
                        offset: 1,
                        color: '#32C5E9'
                    }]), "transparent"],
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    label: {
                        normal: {
                            position: 'center'
                        }
                    },
                    data: [{
                        value: 75,
                        name: '参加考试人数',
                        label: {
                            normal: {
                                formatter: '参加考试人数\n',
                                textStyle: {
                                    color: '#fff',
                                    fontSize: 16

                                }
                            }
                        }
                    }, {
                        value: 25,
                        name: '%',
                        label: {
                            normal: {
                                formatter:  self.list3(),
                                textStyle: {
                                    color: '#007ac6',
                                    fontSize: 20

                                }
                            }
                        }
                    }]
                },
                {
                    name: '考试通过人数',
                    type: 'pie',
                    radius: ['55%', '65%'],
                    center: ['85%', '50%'],
                    startAngle: 225,
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    label: {
                        normal: {
                            position: 'center'
                        }
                    },
                    data: [
                        {
                            value: 75,
                            "itemStyle": {
                                "normal": {
                                    "color": new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                        "offset": 0,
                                        "color": '#FF9F7F'
                                    }, {
                                        "offset": 1,
                                        "color": '#FB7293'
                                    }]),
                                }
                            },
                            name: '考试通过人数',
                            label: {
                                normal: {
                                    formatter: '考试通过人数\n',
                                    textStyle: {
                                        color: '#fff',
                                        fontSize: 16

                                    }
                                }
                            }
                        },
                        {
                            value: 25,
                            name: '%',
                            label: {
                                normal: {
                                    formatter:  self.list4(),
                                    textStyle: {
                                        color: '#f125ff',
                                        fontSize: 20

                                    }
                                }
                            }
                        }]
                }
            ]
        };

        //查询
        self.search = function() {
            RiskList()
        }
        RiskList()
        function RiskList(searchParams) {
            var params = {
                "page":	 self.pageIndex(),
                "size":10,
                "params": {
                    "name":self.titleName(),
                    "orgName":self.Unit(),
                    "datareportOrgId":self.provinceContent()
                }
            }
            axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkerSafeInfoList",params).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue.content
                    // console.log(risklist)
                    self.count(res.data.resultValue.elementTotalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.elementTotalSize / 20));
                    self.items(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //人员总数
        axios.post(cube.gatewayURL2 + "personnelInformation/getSafeCount").then(function(res) {
            if (true) {
                var list=res.data.resultValue.staffCount
                var option = self.option();
                option.title.text= list;
                self.option(option);
                var option2 = self.option2();
                self.list2(res.data.resultValue.violationCount)
                self.list3(res.data.resultValue.examinationCount)
                self.list4(res.data.resultValue.passCount)

                option2.series[0].data[1].name= self.list2();
                option2.series[0].data[1].label.normal.formatter= self.list2();
                option2.series[1].data[1].name= self.list3();
                option2.series[1].data[1].label.normal.formatter= self.list3();
                option2.series[2].data[1].name= self.list2();
                option2.series[2].data[1].label.normal.formatter=self.list2();
                self.option2(option2);
            } else {
                cube.indicate("数据加载失败");
            }

        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});