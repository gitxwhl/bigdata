define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        self.id=params.item
        self.data4=ko.observable('');
        self.data5=ko.observable('');
        self.data6=ko.observable('');

        var data = [
			{name:'一般违章',value:0},
			{name:'严重违章',value:0}
		]
        var aa=[]
        self.pieOption = {
            color: ['#125DED','#CB453F'],
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} ({d}%)"
            },
            title: {
                text: '违章总数(个)',
                subtext: "0",
                x: 'center',
                y: 'center',
                textStyle: {
                    color: '#fff',
                    fontSize: 14
                },
                subtextStyle: {
                    fontSize: 14,
                    color: ['#F99C56']
                }
            },
            legend: {
                orient: 'vertical',
				top: "10",
				right: "10",
				textStyle: {
					color: '#fff',
				},
                itemWidth: 12, // 设置宽度
                itemHeight: 10, // 设置高度
                data: data
            },
            series: [
                // 主要展示层的
                {
                    radius: ['45%', '65%'],
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
                            length: 6
                        }
                    },
                    hoverAnimation: false,
                    data: data

                },
                // 边框的设置
                {
                    radius: ['45%', '40%'],
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
                }, {
                    name: '外边框',
                    type: 'pie',
                    clockWise: false, //顺时加载
                    hoverAnimation: false, //鼠标移入变大
                    center: ['50%', '50%'],
                    radius: ['67%', '67%'],
                    label: {
                        normal: {
                            show: false
                        }
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
        self.option = {
            series: [
                {
                    name: '违章数',
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
                         name: '违章数',
                            label: {
                                normal: {
                                    formatter: '违章数(个)\n',
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
                                    formatter: self.data5(),
                                    textStyle: {
                                        color: '#007ac6',
                                        fontSize: 20
        
                                    }
                                }
                            }
                    }]
                },
                {
                    name: '负面清单数',
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
                         name: '负面清单数',
                            label: {
                                normal: {
                                    formatter: '负面清单数(个)\n',
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
                                    formatter: self.data6(),
                                    textStyle: {
                                        color: '#007ac6',
                                        fontSize: 20
        
                                    }
                                }
                            }
                    }]
                },
                {
                    name: '黑名单数',
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
                         name: '黑名单数',
                            label: {
                                normal: {
                                    formatter: '黑名单数(个)\n',
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
                                    formatter: self.data4(),
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
        
		self.style ="background:#cccccc;";
        self.columns = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "DATAREPORT_ORG", width:"10%", caption: "省份" , editorType: "TextEditor",align : "center"},
            {name: "PROJECT_NAME", width:"10%", caption: "所属项目" , editorType: "TextEditor",align : "center"},
            {name: "VIOLATION_LEVEL", width:"10%", caption: "违章级别" , editorType: "TextEditor",align : "center" },
            {name: "VIOLATION_CONTENT", width:"10%", caption: "违章详情" , editorType: "TextEditor",align : "center" },
            {name: "VIOLATION_ORG_POINTS", width:"10%", caption: "积分" , editorType: "TextEditor",align : "center" },
            {name: "VIOLATION_DATE", width:"10%", caption: "违章时间" , editorType: "TextEditor",align : "center" },
            {name: "CHECK_LEVEL", width:"10%", caption: "巡检单位" , editorType: "TextEditor",align : "center" },
            {name: "VIOLATION_STAFF", width:"10%", caption: "违章人员" , editorType: "TextEditor",align : "center" },
            {name: "PERSON_NUM", width:"10%", caption: "违章人数" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent = []
        self.columns1 = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "DATAREPORT_ORG", width:"10%", caption: "省份" , editorType: "TextEditor",align : "center"},
            {name: "ITEMS_UNDER", width:"10%", caption: "所属项目" , editorType: "TextEditor",align : "center"},
            {name: "PUBLISH_ORG", width:"10%", caption: "发起单位" , editorType: "TextEditor",align : "center" },
            {name: "INCLUSION_CONDITION", width:"10%", caption: "纳入原因" , editorType: "TextEditor",align : "center" },
            {name: "RELEASE_DATE", width:"10%", caption: "发布时间" , editorType: "TextEditor",align : "center" },
            {name: "NEGATIVELISTREL_DATE", width:"10%", caption: "解除时间" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent1 = []
        self.columns2 = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "DATAREPORT_ORG", width:"10%", caption: "省份" , editorType: "TextEditor",align : "center"},
            {name: "ITEMS_UNDER", width:"10%", caption: "所属项目" , editorType: "TextEditor",align : "center"},
            {name: "PUBLISH_ORG", width:"10%", caption: "发起单位" , editorType: "TextEditor",align : "center" },
            {name: "INCLUSION_CONDITION", width:"10%", caption: "纳入原因" , editorType: "TextEditor",align : "center" },
            {name: "BLACKLIST_LEVEL", width:"10%", caption: "黑名单等级" , editorType: "TextEditor",align : "center" },
            {name: "RELEASE_DATE", width:"10%", caption: "发布时间" , editorType: "TextEditor",align : "center" },
            {name: "BLACKLISTREL_DATE", width:"10%", caption: "解除时间" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent2 = []
        self.columns3 = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "PROJECT_NAME", width:"10%", caption: "项目名称" , editorType: "TextEditor",align : "center"},
            {name: "PROJECT_CODE", width:"10%", caption: "项目编号" , editorType: "TextEditor",align : "center"},
            {name: "ISSUING_ORG", width:"10%", caption: "发包单位" , editorType: "TextEditor",align : "center" },
            {name: "IMPLEMENTATION_SITE", width:"10%", caption: "项目地点" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENT_LEVEL", width:"10%", caption: "事故最高级别" , editorType: "TextEditor",align : "center" },
            {name: "cnt", width:"10%", caption: "事故数" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENT_HANDLING", width:"10%", caption: "事故处理情况" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent3 = []
        RiskList()
        function RiskList(searchParams) {
            var params = { "id":self.id}
            self.isShow(true)
            axios.post(cube.gatewayURL2 + "accidentInformation/securityInformation",params).then(function(res) {
                if (res.data.successful) {

                    var option = self.option();
                    var blackListCount=res.data.resultValue.count[0].blackListCount //黑名单
                    var violationCount=res.data.resultValue.count[0].violationCount //违章数
                    var negativeListCount=res.data.resultValue.count[0].negativeListCount //负面清单
                    self.data4(blackListCount)
                    self.data5(violationCount)
                    self.data6(negativeListCount)
                    option.series[0].data[1].name= self.data5();
                    option.series[0].data[1].label.normal.formatter= self.data5()+'';
                    option.series[1].data[1].name= self.data6();
                    option.series[1].data[1].label.normal.formatter= self.data6()+'';
                    option.series[2].data[1].name= self.data4();
                    option.series[2].data[1].label.normal.formatter= self.data4()+'';
                    self.option(option);


                    var yxzs = 0;
                    var general=res.data.resultValue.violationCountByLevel[0].general
                    var serious=res.data.resultValue.violationCountByLevel[0].serious

                    aa.push(general)
                    aa.push(serious)
                    for(var i in data) {
                        switch (data[i]['name']) {
                            case '一般违章':
                                yxzs += aa[0];
                                data[i]['value'] = aa[0];
                                break;
                            case '严重违章':
                                yxzs += aa[1];
                                data[i]['value'] = aa[1];
                                break;
                        }
                    }
                    var pieOption = self.pieOption();
                    pieOption.title.subtext= yxzs + '';
                    pieOption.series[0].data= data;
                    self.pieOption(pieOption);
                    var list=res.data.resultValue.violationThisYear
                    // console.log(list)
                    self.cityContent(list);
                    var list2=res.data.resultValue.negativeList
                    self.cityContent1(list2);
                    var list3=res.data.resultValue.blackList
                    self.cityContent2(list3);
                    var list4=res.data.resultValue.safetySituation
                    self.cityContent3(list4);

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