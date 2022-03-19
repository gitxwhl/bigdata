define(["RESTClient",'echarts',"axios"], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        $('.content-bg').scrollTop(0);
        self.moduleOptions = ko.observable({ name: 'blacklist.blacklist.main',params: {} });
        self.fmqdIndex = 0;
        self.page = 'list';
        self.oneblacklist='';//一级黑名单
        self.twoblacklist='';//二级黑名单
        self.threeblacklist='';//三级黑名单
        self.blacklist='';//本月纳入黑名单数
        self.RemoveBlacklist='';//本年纳入黑名单数
        self.TotalNegativeList='';//负面清单总数
        self.NegativeListNumber='';//本年纳入负面清单数
        self.ClearNegativeList='';//本月纳入负面清单数


        axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getBlackCntInfos').then(function(res){
            if(res.data.successful){
                var data = res.data.resultValue;
                // console.log(data)
                self.oneblacklist(data.blackCntInfos.blacklist_level1);
                self.twoblacklist(data.blackCntInfos.blacklist_level2);
                self.threeblacklist(data.blackCntInfos.blacklist_level3);
                self.blacklist(data.blackCntInfosByTimMonth.release_cnt_month);//本月纳入黑名单数
                self.RemoveBlacklist(data.blackCntInfosByTimYear.release_cnt_year);//本月解除黑名单数
            }
        })
        axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getNegativeCntInfos').then(function(res){
            if(res.data.successful){
                var data = res.data.resultValue;
                // console.log(data)
                self.TotalNegativeList(data.negativeCntInfos.negativelist_cnt);
                 self.NegativeListNumber(data.negativeCntInfosByAreaYear.release_cnt_year);//本年纳入负面清单数
                 self.ClearNegativeList(data.negativeCntInfosByAreaMonth.release_cnt_month);//本月纳入负面清单数

            }
        })


        self.width = '100%';
        self.height = '310px';
        self.heightMap = '420px';
        self.style ="background:#cccccc;";
        self.isShowRowNumber = true;
        self.isShowCheckBox = true;
        self.isShowCheckBoxs = true;
        self.isShowBorder = true;
        self.isShowStripe = true;
        self.isShowHover = true;
        self.isShowCondense = false;
        self.isAllowEdit = false;
        self.isAllowDelete = false;
        self.isAllowOperations = false;
        self.isAllowShift = false;
        self.isAllowSort = false;
        self.isAllowAppend = false;
        self.isAllowPaging = false;
        self.pageSize = 10;
        self.id='';
        self.enterpriseName='北京服贸有限责任公司';//企业名称
        self.establishedTime='2016-01-20';//成立时间
        self.licenseNumber='02021354';//执照编号
        self.enterpriseProperty='承包资质';//企业性质
        self.legalPerson='张某某';//法人代表
        self.region='北京市';//所在省市
        self.fitment = '';//承装修资质
        self.Unit = '';//联系单位
        self.subject = '承包许可证';//主项资质
        self.contactNumber='12345678911';//联系电话
        self.unitNumber='28';//单位人数
        self.shutDown='';//停工状态
        self.unitLocation='朝阳区';//单位地址
        self.registeredCapital='';//注册资本

        self.ViolationUnit='北京服贸有限责任公司';//违章单位
        self.ViolationTime='2019-1-19';//违章时间
        self.natureUnit='主业';//单位性质
        self.ViolationGrade='一般违章';//违章级别
        self.InspectionUnit='北京莱科有限责任公司';//巡检单位
        self.ISshutdown='';//停工状态
        self.ViolationPersonnel='张某某';//违章人员
        self.ViolationNature='';//违章性质
        self.ViolationContents='1、工作票5.1项“安全措施”栏已填写在10kV金融街1#分界室1-1、1-2、1-3开关处挂“禁止合闸，线路有人工作”标示牌，但现场1-1、1-3开关处安全措施未布置。2、工作票5.1项“安全措施”栏已填写在10kV金融街1#分界室1-1、1-2开关前两侧柜挂红布幔，但现场安全措施未布置。';//违章内容
        self.ViolationPlace='';//违章地点
        self.penalizeContent='1、扣除积分5分；2、交纳罚款200元整；';//处罚内容
        self.IntegralDetails='总分20分 1、因安全技术措施不规范属于一般性违章扣除5分，2、标志牌 漏挂、错挂、漏翻现场标示牌属于一般性违章扣除5分，现在剩余积分为10分；';//积分详情
        var selectedItems = []
        self.onSelectedItems = function(e) {
            selectedItems = e;
        };


        self.diaHeight = document.documentElement.clientHeight * 0.7 + 'px';
        self.customOperations = [
			{
				caption : "查看",
				click: function(item) {
                    // console.log(self.fmqdIndex())
                    var title = self.fmqdIndex() == 0?'黑名单详情':'负面清单详情'
                    cube.showDialog({
                        title : title,
                        width:"70%",
                        height:self.diaHeight,
                        submitFormOnConfirm : false,
                        isShowCloseBtn:false, // 是否显示取消按钮
                        isShowConfirmBtn:false, // 是否显示保存按钮
                        templateOptions : {
                            name : 'blacklist.detail.main',
                            params : {item:item}
                        }
                    });
				}
			}
		];
        self.blacklistContent=[]
        self.pageIndex = 0;

        //负面清单数据
        self.negativeMeter = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center"},
            {name: "matter", width:"10%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center"},
            {name: "zzjgdm", width:"10%", readOnly:true, caption: "组织机构代码" , editorType: "TextEditor",align : "center"},
            {name: "sf", width:"10%", caption: "省份" , editorType: "TextEditor",align : "center"},
            {name: "fqdw", width:"10%", caption: "发起单位" , editorType: "TextEditor",align : "center"},
            {name: "nryy", width:"30%", caption: "纳入原因" , editorType: "TextEditor",align : "center"},
            {name: "IllegalApprovalTime", width:"10%", readOnly:true, caption: "发布时间" , editorType: "TextEditor",align : "center"},
            // {name: "IllegalApprovalIdea", width:"90px", caption: "处理情况" , editorType: "TextEditor"},
        ];
        self.negativeContent=[
            {
                id:'',
                matter:'北京服贸有限责任公司',
                zzjgdm:'02321421',
                sf:'北京市',
                fqdw:'国网山东电力',
                nryy:'发生严重违章行为',
                IllegalApprovalPersonnel:"王某某",
                IllegalApprovalIdea:'同意',
                IllegalApprovalTime:'2019-1-1',

            }


        ]
        //各单位本月纳入黑名单数及本月解除黑名单数情况
        var option= {
            // title: {
            //     text: '各单位电网风险电压等级分布情况',
            //     textStyle: {
            //         align: 'center',
            //         color: '#fff',
            //         fontSize: 20,
            //     },
            //     top: '3%',
            //     left: '40%',
            // },
            // tooltip: {
            //     trigger: 'axis',
            //     axisPointer: {type: 'cross'}
            // },
            // tooltip: {
            //             //     // trigger: 'axis',
            //             //     axisPointer: {type: 'shadow'}
            //             // },

            "tooltip": {
                "trigger": "axis",
                "axisPointer": {
                    "type": "shadow"
                },
            },
            grid:{
                // top:'10%',
                // left: '3%',
                // right: '80'
                right: '3%',
                left:'4%'
            },
            legend: {
                data:['纳入黑名单数','解除黑名单数'],
                right:'80',
                // top:'5%',
                textStyle:{
                    color:'#fff',
                    fontSize:14,
                }
            },
            xAxis: {
                data:[],       //横坐标
                type: 'category',
                axisTick: {
                    show: false
                },
                // axisLine: {
                //     lineStyle: {
                //         color: '#5793f3',
                //         width: 0, //这里是为了突出显示加上的
                //     }
                // },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white" //X轴文字颜色
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#1B6B9D'
                    }
                },
            },
            yAxis: {
              minInterval:1,
                splitLine: {
                    show: false,
                    lineStyle:{
                        color: '#40A1EA',                                         //网格横线颜色
                        width: 1,
                        // type: 'solid'
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: '#fff',
                        fontSize:16,
                    }
                },
                axisLine: {
                    show:true,
                    lineStyle: {
                        color: '#1B6B9D'
                    }
                },
                axisTick: {
                    show: false
                }
            },
            series: [{
                name: '纳入黑名单数',
                type: 'bar',
                barWidth:15,
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: "#e17617"
                        },
                            {
                                offset: 1,
                                color: "#68523d"
                            }
                        ])
                    }
                },
                data:[],        //数据
            },{
                name: '解除黑名单数',
                type: 'bar',
                barWidth:15,
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: "#3acdd6"
                        },
                            {
                                offset: 1,
                                color: "#4f7c7f"
                            }
                        ])
                    }
                },
                data: [],        //数据
            }
            ]
        };
        self.option = option;
        axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getBlackCntInfosByArea/1').then(function(res){
            if(res.data.successful){
                var data = res.data.resultValue;
                var datareport_org = [],blacklistrel_cnt = [],release_cnt = [];
                for(var i = 0;i<data.length;i++){
                    datareport_org.push(data[i].datareport_org)
                    blacklistrel_cnt.push(data[i].release_cnt)
                    release_cnt.push(data[i].blacklistrel_cnt)
                }
                option.xAxis.data = datareport_org;
                option.series[0].data = blacklistrel_cnt;
                option.series[1].data = release_cnt;
                self.option(option);

            }
        })

        //各单位本月纳入负面清单数及本月解除负面清单数情况
        var option2= {
            // tooltip: {
            //     // trigger: 'axis',
            //     axisPointer: {type: 'shadow'}
            // },
            "tooltip": {
                "trigger": "axis",
                "axisPointer": {
                    "type": "shadow"
                },
            },
            grid:{
                // top:'10%',
                // left: '80',
                // right: '80'
                right: '3%',
                left:'4%'
            },
            legend: {
                data:['纳入负面清单数','解除负面清单数'],
                right:'80',
                // top:'5%',
                textStyle:{
                    color:'#fff',
                    fontSize:14,
                }
            },
            xAxis: {
                data:[],       //横坐标
                type: 'category',
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white" //X轴文字颜色
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#1B6B9D'
                    }
                },
            },
            yAxis: {
                minInterval:1,
                splitLine: {
                    show: false,
                    lineStyle:{
                        color: '#1B6B9D',                                         //网格横线颜色
                        width: 1,
                        // type: 'solid'
                    }
                },
                // axisLabel: {
                //     textStyle: {
                //         color: '#fff',
                //         fontSize:16,
                //     }
                // },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white" //X轴文字颜色
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#1B6B9D'
                    }
                },
            },
            series: [{
                name: '纳入负面清单数',
                type: 'bar',
                barWidth:15,
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: "#18b1ce"
                        },
                            {
                                offset: 1,
                                color: "#377fef"
                            }
                        ])
                    }
                },
                data:[],        //数据
            },{
                name: '解除负面清单数',
                type: 'bar',
                barWidth:15,
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: "#e6c91b"
                        },
                            {
                                offset: 1,
                                color: "#6b8fb5"
                            }
                        ])
                    }
                },
                data: [],        //数据
            }
            ]

        };
        self.option2 = option2;
        axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getNegativeCntInfosByArea/1').then(function(res){
            if(res.data.successful){
                var data = res.data.resultValue;
                var datareport_org = [],negativelistrel_cnt = [],release_cnt = [];
                for(var i = 0;i<data.length;i++){
                    datareport_org.push(data[i].datareport_org)
                    negativelistrel_cnt.push(data[i].negativelistrel_cnt)
                    release_cnt.push(data[i].release_cnt)
                }
                option2.xAxis.data = datareport_org;
                option2.series[1].data = negativelistrel_cnt;
                option2.series[0].data = release_cnt;
                self.option2(option2);
            }
        })
        //全部黑名单数
        self.whole_blacklist=function (){
            axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getBlackCntInfosByArea/1').then(function(res){
                if(res.data.successful){
                    var data = res.data.resultValue;
                    var option = self.option();
                    var datareport_org = [],blacklistrel_cnt = [],release_cnt = [];
                    for(var i = 0;i<data.length;i++){
                        datareport_org.push(data[i].datareport_org)
                        blacklistrel_cnt.push(data[i].release_cnt)
                        release_cnt.push(data[i].blacklistrel_cnt)
                    }
                    option.xAxis.data = datareport_org;
                    option.series[0].data = blacklistrel_cnt;
                    option.series[1].data = release_cnt;
                    self.option(option);

                }
            })
        },
        //本月黑名单数
        self.month_blacklist=function (){
            axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getBlackCntInfosByArea/3').then(function(res){
                if(res.data.successful){
                    var data = res.data.resultValue;
                    var option = self.option();
                    var datareport_org = [],blacklistrel_cnt = [],release_cnt = [];
                    for(var i = 0;i<data.length;i++){
                        datareport_org.push(data[i].datareport_org)
                        blacklistrel_cnt.push(data[i].release_cnt)
                        release_cnt.push(data[i].blacklistrel_cnt)
                    }
                    option.xAxis.data = datareport_org;
                    option.series[0].data = blacklistrel_cnt;
                    option.series[1].data = release_cnt;
                    self.option(option);

                }
            })
        },
            //本年纳入黑名单数
            self.year_blacklist=function (){
                axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getBlackCntInfosByArea/2').then(function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        var option = self.option();
                        var datareport_org = [],blacklistrel_cnt = [],release_cnt = [];
                        for(var i = 0;i<data.length;i++){
                            datareport_org.push(data[i].datareport_org)
                            blacklistrel_cnt.push(data[i].release_cnt)
                            release_cnt.push(data[i].blacklistrel_cnt)
                        }
                        option.xAxis.data = datareport_org;
                        option.series[0].data = blacklistrel_cnt;
                        option.series[1].data = release_cnt;
                        self.option(option);

                    }
                })
            },
            //全部纳入负面清单数
            self.whole_NegativeList=function (){
                axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getNegativeCntInfosByArea/1').then(function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        var option2 = self.option2();
                        var datareport_org = [],negativelistrel_cnt = [],release_cnt = [];
                        for(var i = 0;i<data.length;i++){
                            datareport_org.push(data[i].datareport_org)
                            negativelistrel_cnt.push(data[i].negativelistrel_cnt)
                            release_cnt.push(data[i].release_cnt)
                        }
                        option2.xAxis.data = datareport_org;
                        option2.series[1].data = negativelistrel_cnt;
                        option2.series[0].data = release_cnt;
                        self.option2(option2);
                    }
                })
            },
            //本月纳入负面清单数
            self.month_NegativeList=function (){
                axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getNegativeCntInfosByArea/3').then(function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        var option2 = self.option2();
                        var datareport_org = [],negativelistrel_cnt = [],release_cnt = [];
                        for(var i = 0;i<data.length;i++){
                            datareport_org.push(data[i].datareport_org)
                            negativelistrel_cnt.push(data[i].negativelistrel_cnt)
                            release_cnt.push(data[i].release_cnt)
                        }
                        option2.xAxis.data = datareport_org;
                        option2.series[1].data = negativelistrel_cnt;
                        option2.series[0].data = release_cnt;
                        self.option2(option2);
                    }
                })
            },
            //本年纳入负面清单数
            self.year_NegativeList=function (){
                axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getNegativeCntInfosByArea/2').then(function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        var option2 = self.option2();
                        var datareport_org = [],negativelistrel_cnt = [],release_cnt = [];
                        for(var i = 0;i<data.length;i++){
                            datareport_org.push(data[i].datareport_org)
                            negativelistrel_cnt.push(data[i].negativelistrel_cnt)
                            release_cnt.push(data[i].release_cnt)
                        }
                        option2.xAxis.data = datareport_org;
                        option2.series[1].data = negativelistrel_cnt;
                        option2.series[0].data = release_cnt;
                        self.option2(option2);
                    }
                })
            },
        //本年度违章情况
        self.Violation = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "personNames", width:"14.2%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center"},
            {name: "gender", width:"14.2%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    self.goViolationDetails(pkValue);
                }},
            {name: "age", width:"14.2%", caption: "违章单位" , editorType: "TextEditor",align : "center"},
            {name: "certificateName", width:"14.2%", caption: "违章时间" , editorType: "TextEditor",align : "center"},
            {name: "belongLevel", width:"14.2%", caption: "巡检单位" , editorType: "TextEditor"},
            {name: "Certificate_number", width:"14.2%", caption: "违章人员" , editorType: "TextEditor",align : "center"},
            {name: "belongUnit", width:"14.2%",caption: "违章人数" , editorType: "TextEditor",align : "center"},
            // {name: "major1", width:"120px", caption: "本年度准入" , editorType: "TextEditor"},
            // {name: "major", width:"107px", caption: "关联作业数" , editorType: "TextEditor"},
        ];
        self.ViolationContent=[]
        //注册信息
        self.message = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center"},
            {name: "personNames", width:"20%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center"},
            {name: "registerTime", width:"20%", readOnly:true, caption: "注册及变更时间" , editorType: "TextEditor",align : "center"},
            {name: "alterationTime", width:"20%", caption: "变更内容" , editorType: "TextEditor",align : "center"},
            {name: "AccessNature", width:"20%", caption: "准入性质" , editorType: "TextEditor",align : "center"},
            {name: "auditStatus", width:"20%", caption: "审核状态" , editorType: "TextEditor",align : "center"},
        ];
        self.messageContent=[
            {
                id:'',
                personNames:'北京服贸有限责任公司',
                registerTime:"2016-4-4",
                alterationTime:'注册',
                AccessNature:'主业',
                auditStatus:"已通过",

            },


        ]
        //各单位准入情况
        self.Admittance = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center"},
            {name: "personNames", width:"16.6%", readOnly:true, caption: "报备单位" , editorType: "TextEditor",align : "center",},
            {name: "Companyname", width:"16.6%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center"},
            {name: "legalRepresentative", width:"16.6%", caption: "法定代表人" , editorType: "TextEditor",align : "center"},
            {name: "projectLeader", width:"16.6%", caption: "项目负责人" , editorType: "TextEditor",align : "center"},
            {name: "AuditSituation", width:"16.6%", caption: "审核情况" , editorType: "TextEditor",align : "center"},
            {name: "safetyProduction", width:"16.6%", caption: "安全生产许可证" , editorType: "TextEditor",align : "center"},
        ];
        self.admittanceContent=[
            {
                id:'',
                personNames:'国网,天津市供电公司，河北省供电公司',
                Companyname:"北京服贸有限责任公司",
                legalRepresentative:'王某某',
                projectLeader:'主业',
                AuditSituation:"已通过",
                safetyProduction:'附件'

            },


        ]
        //工作负责人
        self.JobLeader = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "name", width:"14.2%", readOnly:true, caption: "姓名" , editorType: "TextEditor",align : "center",},
            {name: "IDnumber", width:"14.2%", readOnly:true, caption: "身份证号" , editorType: "TextEditor",align : "center"},
            {name: "affiliatedUnit", width:"14.2%", caption: "所属单位" , editorType: "TextEditor",align : "center"},
            {name: "major", width:"14.2%", caption: "专业" , editorType: "TextEditor",align : "center"},
            {name: "skill", width:"14.2%", caption: "当前技能" , editorType: "TextEditor",align : "center"},
            {name: "contactInformation", width:"14.2%", caption: "联系方式" , editorType: "TextEditor",align : "center"},
            {name: "SafetyTest", width:"14.2%", caption: "安规考试" , editorType: "TextEditor",align : "center"},
        ];
        self.JobLeaderContent=[
            {
                id:'',
                name:'张三',
                IDnumber:"110103458646745667",
                affiliatedUnit:'北京服贸有限责任公司',
                major:'农网工程',
                skill:"I",
                contactInformation:'17389129875',
                SafetyTest:'70.5'

            },


        ]
        //外包人员
        self.epibolyLeader = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "name", width:"14.2%", readOnly:true, caption: "姓名" , editorType: "TextEditor",align : "center",},
            {name: "IDnumber", width:"14.2%", readOnly:true, caption: "身份证号" , editorType: "TextEditor",align : "center"},
            {name: "affiliatedUnit", width:"14.2%", caption: "所属单位" , editorType: "TextEditor",align : "center"},
            {name: "major", width:"14.2%", caption: "专业" , editorType: "TextEditor",align : "center"},
            {name: "skill", width:"14.2%", caption: "当前技能" , editorType: "TextEditor",align : "center"},
            {name: "contactInformation", width:"14.2%", caption: "联系方式" , editorType: "TextEditor",align : "center"},
            {name: "SafetyTest", width:"14.2%", caption: "安规考试" , editorType: "TextEditor",align : "center"},
        ];
        self.epibolyLeaderContent=[
            {
                id:'',
                name:'张三',
                IDnumber:"110103458646745667",
                affiliatedUnit:'北京服贸有限责任公司',
                major:'农网工程',
                skill:"I",
                contactInformation:'17389129875',
                SafetyTest:'70.5'

            },


        ]
        self.IllegalApproval = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "matter", width:"10%", readOnly:true, caption: "事项" , editorType: "TextEditor",align : "center",},
            {name: "IllegalApprovalPersonnel", width:"10%", readOnly:true, caption: "违章审核人员" , editorType: "TextEditor",align : "center"},
            {name: "IllegalApprovalIdea", width:"10%", caption: "违章审核意见" , editorType: "TextEditor",align : "center"},
            {name: "IllegalApprovalTime", width:"10%", caption: "违章审核时间" , editorType: "TextEditor",align : "center"},

        ];
        self.IllegalapprovalContent=[
            {
                id:'',
                matter:'基层审核',
                IllegalApprovalPersonnel:"王某某",
                IllegalApprovalIdea:'同意',
                IllegalApprovalTime:'2019-1-1',

            },


        ]
        self.modify = function() {
            if (selectedItems.length !== 1) {
                cube.indicate("请勾选一条数据");
                return;
            }
            self.page('BlacklistDetails')

        },
            //黑名单企业查看
            self.goenterpriseInformation = function (id) {
                self.page('detail')
            }
        //黑名单企业详细信息查看
        self.goViolationDetails = function (id) {
            self.page('ViolationDetails')
        }
        //违章详情页面关闭
        self.refresh = function (e) {
            self.page('detail');
        }
        //黑名单详情页面关闭
        self.refreshClose = function (e) {
            self.page('list');
        }

        self.blacklist_click=function(){//content-bg

            self.moduleOptions({ name: 'blacklist.blacklist.main',params: {} });
            self.fmqdIndex(0)
            $(".blacklist").css("background", "#3363B3");
            $(".negative").css("background", "#1e3369");
        }
        self.negative_click=function(){
            self.moduleOptions({ name: 'blacklist.negativeList.main',params: {} });
            self.fmqdIndex(1)
            $(".blacklist").css("background", "#1e3369");
            $(".negative").css("background", "#3363B3");
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});
