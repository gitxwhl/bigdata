define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        var value = '';
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
        self.page='list'
        self.titleName=ko.observable('')//查询姓名
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
        self.Unit = ko.observable('')


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
        self.primaryKey2='BASICFILEINFO_ID';
        self.pageSize = 1;

        self.id='';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '1';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount =ko.observable(1);
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                RiskList()
            }
        }
        self.size2 = 'middle';
        self.showArrow2 = true;
        self.showTotal2 = true;
        self.showGoto2 = true;
        self.showAllPage2 = false;
        self.count2 ='0';//总记录数
        self.pageTotalCount2 = '1';//总页数
        self.pageIndex = ko.observable(1);//当前页数
        self.pageVisibleCount2 =ko.observable(1);;  //可见页数
        self.pageNo2 = ko.observable(0);
        self.list2=ko.observable('');
        self.list3=ko.observable('');
        self.list4=ko.observable('');
        self.option2 = {
            series: [
                {
                    name: '主业人员数',
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
                    hoverAnimation: false,
                    data: [{
                        value: 75,
                        name: '主业人员数',
                        label: {
                            normal: {
                                formatter: '主业人员数\n',
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
                    name: '外包人员数',
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
                    hoverAnimation: false,
                    data: [{
                        value: 75,
                        name: '外包人员数',
                        label: {
                            normal: {
                                formatter: '外包人员数\n',
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
                                formatter: self.list3(),
                                textStyle: {
                                    color: '#007ac6',
                                    fontSize: 20

                                }
                            }
                        }
                    }]
                },
                // {
                //     name: '集体人员数',
                //     type: 'pie',
                //     radius: ['55%', '65%'],
                //     center: ['85%', '50%'],
                //     startAngle: 225,
                //     // "color": [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                //     //                             "offset": 0,
                //     //                             "color": '#FF9F7F'
                //     //                         }, {
                //     //                             "offset": 1,
                //     //                             "color": '#FB7293'
                //     //                         }]),"transparent"],
                //     color: [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                //         offset: 0,
                //         color: '#9FE6B8'
                //     }, {
                //         offset: 1,
                //         color: '#32C5E9'
                //     }]), "transparent"],
                //     labelLine: {
                //         normal: {
                //             show: false
                //         }
                //     },
                //     label: {
                //         normal: {
                //             position: 'center'
                //         }
                //     },
                //     hoverAnimation: false,
                //     data: [{
                //         value: 75,
                //         name: '集体人员数',
                //         label: {
                //             normal: {
                //                 formatter: '集体人员数\n',
                //                 textStyle: {
                //                     color: '#fff',
                //                     fontSize: 16

                //                 }
                //             }
                //         }
                //     }, {
                //         value: 25,
                //         name: '%',
                //         label: {
                //             normal: {
                //                 formatter: self.list2(),
                //                 textStyle: {
                //                     color: '#007ac6',
                //                     fontSize: 20

                //                 }
                //             }
                //         }
                //     }]
                // },
                {
                    name: '集体人员数',
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
                    hoverAnimation: false,
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
                            name: '集体人员数',
                            label: {
                                normal: {
                                    formatter: '集体人员数\n',
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
                                    formatter:self.list4(),
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
        RiskList()
        self.columns = [
            {name: "BASICFILEINFO_ID",  readOnly:true, caption: "" , editorType: "TextEditor",align : "center",},
            {name: "NAME",  width: "10%", readOnly:true, caption: "姓名" ,align : "center", editorType: "TextEditor",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                    element.style.cursor = "pointer";
                },
                onClick: function (pkValue, cellValue) {
                    self.goDetail(pkValue);
                    // console.log(pkValue)

                }
            },
            {name: "SEX", width: "10%", caption: "性别" , editorType: "TextEditor",align : "center",},
            {name: "ID_CARD",width: "15%", caption: "人员编号" , editorType: "TextEditor",align : "center",},
            {name: "DATAREPORT_ORG", width: "10%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
            {name: "ORG_NAME", width: "10%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "PROJECT", width: "10%", caption: "参与项目" , editorType: "TextEditor",align : "center",},
            // {name: "CONTACT", width: "10%", caption: "手机号" , editorType: "TextEditor",align : "center",},
        ];
        self.items=[]
        self.columns2 = [
            {name: "BASICFILEINFO_ID",  readOnly:true, caption: "" , editorType: "TextEditor",align : "center",},
            {name: "PROJECT_CODE",  width: "10%", readOnly:true, caption: "项目编号" ,align : "center", editorType: "TextEditor",
                // renderCell: function (cellValue, element, dictValue) {
                //     element.style.color = "#0ec4da";
                // },
                onClick: function (items) {
                }
            },
            {name: "PROJECT_NAME", width: "10%", caption: "项目名称" , editorType: "TextEditor",align : "center",},
            {name: "CONTRACTOR_MANAGER",width: "15%", caption: "项目负责人" , editorType: "TextEditor",align : "center",},
            {name: "STATE_PROJECT", width: "10%", caption: "项目状态" , editorType: "TextEditor",align : "center",},
            {name: "CREATE_TIME", width: "10%", caption: "项目开工日期" , editorType: "TextEditor",align : "center",},
            {name: "END_TIME", width: "10%", caption: "项目结束日期" , editorType: "TextEditor",align : "center",},
            // {name: "phone", width: "10%", caption: "手机号" , editorType: "TextEditor",align : "center",},
        ];
        self.items2=[]
        //输入框onchange事件
        self.iptChange = function() {
            var ipt1 = document.querySelector("#ipt1")
            if(ipt1.value.length > 100) {
                ipt1.value = ipt1.value.substring(0,100)
                cube.indicate("输入框内容长度不能超过100！");
            }
        }
        //输入框onchange事件
        self.iptChange2 = function() {
            var ipt1 = document.querySelector("#ipt2")
            if(ipt1.value.length > 100) {
                ipt1.value = ipt1.value.substring(0,100)
                cube.indicate("输入框内容长度不能超过100！");
            }
        }
        //查询
        self.search = function() {
            RiskList()
        }
        self.goDetail = function (pkValue) {
            var params = {
                "workerId":pkValue
            }
            axios.post(cube.gatewayURL2 + "personnelInformation/getWorkerInfoTwo",params).then(function(res) {
                if (res.data.successful) {
                    var risklist=res.data.resultValue
                    // console.log(risklist)
                    self.Name(risklist[0].NAME)//姓名
                    self.phone(risklist[0].CONTACT)//手机
                    self.IDnumber(risklist[0].ID_CARD)//身份证号
                    self.gender(risklist[0].SEX)//性别
                    self.TheirIdentity(risklist[0].DATAREPORT_ORG)//所属身份
                    self.PlaceUnit(risklist[0].ORG_NAME)//所在单位
                    self.age(risklist[0].AGE)//年龄
                } else {
                    cube.indicate("数据加载失败");
                }
            })
            self.isShow(true);
            var params = {
                "page": self.pageIndex(),
                "size": 10,
                "params": {
                    // "name": "",
                    // "orgName": "",
                    // "datareportOrgId": "",
                    "workerId":pkValue
                }
            }
            //项目列表
            axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkertoProjectList",params).then(function(res) {
                if (res.data.successful) {
                    var risklist=res.data.resultValue.content
                    // console.log(risklist)
                    self.count2(res.data.resultValue.elementTotalSize);
                    self.pageTotalCount2(Math.ceil(res.data.resultValue.elementTotalSize / 20));
                    self.items2(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
            self.page('details')
        }
        $('.goblack').click(function(){
            self.page('list')
        })
        function RiskList(searchParams) {
            // 输入框内容校验
            var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
            if(iptRep.test(self.titleName().trim()) || iptRep.test(self.Unit().trim())) {
                cube.indicate("输入框含违法字符，请重新输入！");
                return;
            }
            // 输入框空格校验
            if(self.titleName().trim().length !== self.titleName().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            if(self.Unit().trim().length !== self.Unit().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            // 输入框长度校验
            if(self.titleName().trim().length > 100 || self.Unit().trim().length > 100) {
                cube.indicate("输入框内容长度不能超过100！");
                return;
            }
            self.isShow(true)
            var params = {
                "page":	self.pageIndex(),
                "size":10,
                "params": {
                    "name":self.titleName(),
                    "orgName":self.Unit(),
                    "datareportOrgId":self.provinceContent()
                }
            }

            axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkerInfosList",params).then(function(res) {
                if (res.data.successful) {
                    var risklist=res.data.resultValue.content
                    console.log(risklist)
                    self.count(res.data.resultValue.elementTotalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.elementTotalSize / 20));
                    self.items(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }

        //人员总数
        axios.post(cube.gatewayURL2 + "personnelInformation/getStaffCount").then(function(res) {
            if (true) {
                var list=res.data.resultValue.staffCount
                var option = self.option();
                option.title.text= list;
                self.option(option);
                var option2 = self.option2();
                self.list2(res.data.resultValue.majorCount)
                self.list3(res.data.resultValue.outsourcingCount)
                self.list4(res.data.resultValue.collectiveCount)

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