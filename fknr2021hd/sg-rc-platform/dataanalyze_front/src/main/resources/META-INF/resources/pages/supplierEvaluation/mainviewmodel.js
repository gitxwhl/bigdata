define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        var myColor = ['#5fccf9',];
        var name=[]
        var DataList=[]
        var name2=[]
        var DataList2=[]
        self.option = {
            // backgroundColor: '#0e2147',
            grid: {
                left: '11%',
                top: '12%',
                right: '0%',
                bottom: '8%',
                containLabel: true
            },
            xAxis: [{
                show: false,
            }],
            yAxis: [{
                axisTick: 'none',
                axisLine: 'none',
                offset: '27',
                axisLabel: {
                    textStyle: {
                        color: '#ffffff',
                        fontSize: '16',
                    }
                },
                data:name
            },
                {
                    axisLabel:'false',
                axisTick: 'none',
                axisLine: 'none',
                // axisLabel: {
                //     textStyle: {
                //         color: '#ffffff',
                //         fontSize: '16',
                //     }
                // },
                data: ['10', '9', '8', '7', '6', '5', '4', '3', '2', '1']
            },
                {
                name: '分拨延误TOP 10',
                nameGap: '50',
                nameTextStyle: {
                    color: '#ffffff',
                    fontSize: '16',
                },
                axisLine: {
                    lineStyle: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: [],
            }
            ],
            series: [{
                name: '条',
                type: 'bar',
                yAxisIndex: 0,
                data: DataList,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        textStyle: {
                            color: '#ffffff',
                            fontSize: '16',
                        }
                    }
                },
                barWidth: 13,
                itemStyle: {
                    normal: {
                        color: function(params) {
                            var num = myColor.length;
                            return myColor[params.dataIndex % num]
                        },
                    }
                },
                z:3
            }, {
                name: '白框',
                type: 'bar',
                yAxisIndex: 1,
                barGap: '-100%',
                data: [99, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5],
                barWidth: 20,
                itemStyle: {
                    normal: {
                        color: '#0e2147',
                        barBorderRadius: 5,
                    }
                },
                z: 1
            }, {
                name: '外框',
                type: 'bar',
                yAxisIndex: 2,
                barGap: '-100%',
                data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
                barWidth: 24,
                itemStyle: {
                    normal: {
                        color: function(params) {
                            var num = myColor.length;
                            return myColor[params.dataIndex % num]
                        },
                        barBorderRadius: 5,
                    }
                },
                z: 0
            }
            // ,
            //     {
            //         name: '外圆',
            //         type: 'scatter',
            //         hoverAnimation: false,
            //         data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            //         yAxisIndex: 2,
            //         symbolSize: 35,
            //         itemStyle: {
            //             normal: {
            //                 color: function(params) {
            //                     var num = myColor.length;
            //                     return myColor[params.dataIndex % num]
            //                 },
            //                 opacity: 1,
            //             }
            //         },
            //         z: 2
            //     }
            ]
        };
        self.option2 = {
            // backgroundColor: '#0e2147',
            grid: {
                left: '11%',
                top: '12%',
                right: '0%',
                bottom: '8%',
                containLabel: true
            },
            xAxis: [{
                show: false,
            }],
            yAxis: [{
                axisTick: 'none',
                axisLine: 'none',
                offset: '27',
                axisLabel: {
                    textStyle: {
                        color: '#ffffff',
                        fontSize: '16',
                    }
                },
                data:name2
            },
                {
                    axisLabel:'false',
                    axisTick: 'none',
                    axisLine: 'none',
                    // axisLabel: {
                    //     textStyle: {
                    //         color: '#ffffff',
                    //         fontSize: '16',
                    //     }
                    // },
                    data: ['10', '9', '8', '7', '6', '5', '4', '3', '2', '1']
                },
                {
                    name: '分拨延误TOP 10',
                    nameGap: '50',
                    nameTextStyle: {
                        color: '#ffffff',
                        fontSize: '16',
                    },
                    axisLine: {
                        lineStyle: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    data: [],
                }
            ],
            series: [{
                name: '条',
                type: 'bar',
                yAxisIndex: 0,
                data: DataList2,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        textStyle: {
                            color: '#ffffff',
                            fontSize: '16',
                        }
                    }
                },
                barWidth: 13,
                itemStyle: {
                    normal: {
                        color: function(params) {
                            var num = myColor.length;
                            return myColor[params.dataIndex % num]
                        },
                    }
                },
                z:3
            }, {
                name: '白框',
                type: 'bar',
                yAxisIndex: 1,
                barGap: '-100%',
                data: [99, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5],
                barWidth: 20,
                itemStyle: {
                    normal: {
                        color: '#0e2147',
                        barBorderRadius: 5,
                    }
                },
                z: 1
            }, {
                name: '外框',
                type: 'bar',
                yAxisIndex: 2,
                barGap: '-100%',
                data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
                barWidth: 24,
                itemStyle: {
                    normal: {
                        color: function(params) {
                            var num = myColor.length;
                            return myColor[params.dataIndex % num]
                        },
                        barBorderRadius: 5,
                    }
                },
                z: 0
            }
            // ,
            //     {
            //         name: '外圆',
            //         type: 'scatter',
            //         hoverAnimation: false,
            //         data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            //         yAxisIndex: 2,
            //         symbolSize: 35,
            //         itemStyle: {
            //             normal: {
            //                 color: function(params) {
            //                     var num = myColor.length;
            //                     return myColor[params.dataIndex % num]
            //                 },
            //                 opacity: 1,
            //             }
            //         },
            //         z: 2
            //     }
            ]
        };
        // self.Name=''//姓名
        // self.phone=''//手机
        // self.IDnumber=''//身份证号
        // self.gender=''//性别
        // self.TheirIdentity=''//所属身份
        // self.PlaceUnit=''//所在单位
        // self.age=''//年龄

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
        self.Unit = ko.observable("") //供应商名称


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
        self.primaryKey='id';
        self.pageSize = 10;
        // self.id='';

        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = ko.observable('1');
        self.pageVisibleCount =1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                RiskList()
            }
        }


        self.columns = [
            {name: "id",  readOnly:true, caption: "" , editorType: "TextEditor",align : "center",},
            {name: "DATAREPORT_ORG",  width: "10%", readOnly:true, caption: "省份" ,align : "center", editorType: "TextEditor",
                // renderCell: function (cellValue, element, dictValue) {
                //     element.style.color = "#0ec4da";
                // },
                onClick: function (items) {

                }
            },
            {name: "MAN_NAME", width: "10%", caption: "供应商名称" , editorType: "TextEditor",align : "center",},
            {name: "MAN_CODE",width: "15%", caption: "供应商编码" , editorType: "TextEditor",align : "center",},
            {name: "percent", width: "10%", caption: "供应商综合评分" , editorType: "TextEditor",align : "center",},
            {name: "ranking ", width: "10%", caption: "排名" , editorType: "TextEditor",align : "center",},
        ];
        self.items=[]
        //输入框onchange事件
        self.iptChange = function() {
            var ipt1 = document.querySelector("#ipt1")
            if(ipt1.value.length > 100) {
                ipt1.value = ipt1.value.substring(0,100)
                cube.indicate("输入框内容长度不能超过100！");
            }
        }
        //查询
        self.search = function() {
            self.pageIndex(1)
            RiskList()
        }
        RiskList()
        function RiskList(searchParams) {
            // 输入框内容校验
            var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
            if(iptRep.test(self.Unit().trim())) {
                cube.indicate("输入框含违法字符，请重新输入！");
                return;
            }
            // 输入框空格校验
            if(self.Unit().trim().length !== self.Unit().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            // 输入框长度校验
            if(self.Unit().trim().length > 100) {
                cube.indicate("输入框内容长度不能超过100！");
                return;
            }
            self.isShow(true)
            var params = {
                "pageNo":	self.pageIndex(),
                "pageSize":10,
                "params": {
                    "dataReportOrg":self.provinceContent(), //省份
                    "manName":self.Unit(), //单位
                }
            }

            axios.post(cube.gatewayURL2 + "toolInformation/getGradeList",params).then(function(res) {
                name = [];
                DataList = [];
                name2 = [];
                DataList2 = [];
                if (res.data.successful) {
                    var risklist=res.data.resultValue.LIST[0].content
                    self.count(res.data.resultValue.LIST[0].totalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.LIST[0].totalPage));
                    self.items(risklist);
                    // const newChannels =self.channels.map((items,i)=>({
                    //     id:items.id,
                    //     name:items.name,
                    //     seq:i
                    // }))
                    // console.log(items)
                    var option = self.option();
                    var risklist2=res.data.resultValue.TOP
                    // console.log(risklist2)
                    risklist2.forEach(ele=>{
                        name.unshift(ele.MAN_NAME)
                        DataList.unshift(ele.percent)
                    })
                    // for (let i  in risklist2){
                    //
                    // }
                    console.log(DataList)
                    option.yAxis[0]['data'] =name;
                    option.series[0]['data'] = DataList;
                    self.option(option);
                    // console.log(name)
                    // console.log(DataList)

                    var option2 = self.option2();
                    var risklist3=res.data.resultValue.BOTTOM
                    // console.log(risklist2)
                    // for (var i  in risklist3){
                    //     name2.unshift(risklist3[i].MAN_NAME)
                    //     DataList2.unshift(risklist3[i].percent)
                    // }
                    risklist3.forEach(ele=>{
                        name2.unshift(ele.MAN_NAME)
                        DataList2.unshift(ele.percent)
                    })
                    // for (let i  in risklist3){
                    //
                    // }
                    option2.yAxis[0]['data'] =name2;
                    option2.series[0]['data'] = DataList2;

                    self.option2(option2);
                    // console.log(name2)
                    // console.log(DataList2)
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