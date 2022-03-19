define(["RESTClient",'echarts', "axios",], function (RestClient,echarts, axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.params = ko.observable({});
        self.page = 'list';
        self.unitSum='';//单位总数
        self.Industry='';//产业单位
        self.epiboly='';//外包单位
        self.admittance='';//本月准入单位数
        self.blacklist='';//本月黑名单单位数
        self.NegativeList='';//本月负面清单数
        self.width = '100%';
        self.height = '310px';
        self.heightMap = '500px';
        self.nameFirm=ko.observable("");//企业名称
        //企业性质
        self.PropertyContent =[{id:'',text:'全部'},];
        self.Property = ko.observable("")
        //所属省份
        self.provinceContent =[{id:'',text:'全部'},];
        self.province=ko.observable("");
        //本年度准入
        self.annualAccessContent =[
            {id:'',text:'全部'},
            {id:'01',text:'有效'},
            {id:'02',text:'无效'},
        ];
        self.annualAccess=ko.observable("");
        //联系单位
        self.contactUnitContent =[{id:'',text:'全部'},];
        self.contactUnit=ko.observable("");

        cityNames=[];//各单位准入情况
        powerGridSums=[]
        self.enterpriseName='';//企业名称
        self.establishedTime='';//成立时间
        self.licenseNumber='';//执照编号
        self.enterpriseProperty='';//企业性质
        self.legalPerson='';//法人代表
        self.region='';//所在省市
        self.fitment = '';//承装修资质
        self.Unit = '';//联系单位
        self.subject = '';//主项资质
        self.contactNumber='';//联系电话
        self.unitNumber='';//单位人数
        self.shutDown='';//停工状态
        self.unitLocation='';//单位地址
        self.registeredCapital='';//注册资本
        //
        self.ViolationUnit='';//违章单位
        self.ViolationTime='';//违章时间
        self.natureUnit='';//单位性质
        self.ViolationGrade='';//违章级别
        self.InspectionUnit='';//巡检单位
        self.ISshutdown='';//停工状态
        self.ViolationPersonnel='';//违章人员
        self.ViolationNature='';//违章性质
        self.ViolationContents='';//违章内容
        self.ViolationPlace='';//违章地点
        self.penalizeContent='';//处罚内容
        self.IntegralDetails='';//积分详情
        // self.trBackground={column: "personName",backgroundColor:'#093f9a',value: '标题',};
        // self.trBackground={Violation: "personNames",colore:'red',value: '序号',};
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex =ko.observable(1);
        self.pageVisibleCount = 1;
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                self.inquire()
            }
        }
        self.query = function(){
            self.pageIndex(1);
            self.inquire()
        }

        self.style ="background:#cccccc;";
        self.isShowRowNumber = true;
        self.isShowCheckBox = false;
        self.isShowCheckBoxs = false;
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
        self.pageSize = 10;
        self.id='';
        self.primaryKey = 'siteinfo_id'
        self.primaryKeys = 'siteinfo_id'
        // self.onSelectedItems = function(e) {
        //     selectedItems = e;
        // };
        self.months = function() {
            // console.log(222222222)
            $(".month").css("background-image", "url('./resources/images/u1132.png')");
            $(".year").css("background-image", "url('./resources/images/u1134.png')");
        }
        self.columns = [
            // {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "basicinfo_contractor", width:"15%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {

                    var list = self.items();
                    var basicinfo_creditcode = '';
                    for(var i = 0; i<list.length;i++){
                        if(pkValue == list[i]['siteinfo_id']){
                            basicinfo_creditcode = list[i]['basicinfo_creditcode']
                        }
                    }
                    AccessIs(pkValue,basicinfo_creditcode)
                    subcontractingStaff(pkValue,basicinfo_creditcode)
                    EnterpriseAccess(pkValue,basicinfo_creditcode)
                    JobLeader(pkValue,basicinfo_creditcode)
                    getSrpWorkViolationfile (pkValue,basicinfo_creditcode)
                    self.goDetail(pkValue,basicinfo_creditcode);
                }},
            {name: "org_nature", width:"10%", readOnly:true, caption: "企业性质" , editorType: "TextEditor",align : "center",},
            {name: "basicinfo_legalrepresentative", width:"10%", caption: "法人代表" , editorType: "TextEditor",align : "center",},
            {name: "datareport_org", width:"10%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
            {name: "violation_cnt", width:"10%", caption: "违章" , editorType: "TextEditor",align : "center",},
            {name: "violation_org_points", width:"5%", caption: "积分" , editorType: "TextEditor",align : "center",},
            {name: "accept_org", width:"10%",caption: "联系单位" , editorType: "TextEditor",align : "center",},
            {name: "access_state", width:"5%", caption: "准入状态" , editorType: "TextEditor",align : "center",},
        ];
        //返回安全准入管理
        self.goBack = function() {
            self.page('list')
        }
        //返回安全准入管理
        self.goBack1 = function() {
            self.page('detail')
        }
        //返回企业信息查看
        self.goenterpriseInformation = function() {
            self.page('detail')
        }

        //违章详情查看
        self.goViolationDetails = function (siteinfo_id) {
            self.page('ViolationDetails')
            getSrpWorkViolationfile(siteinfo_id)
        }
        //违章详情
        function getSrpWorkViolationfile (siteinfo_id,basicinfo_creditcode) {
            // console.log(siteinfo_id,basicinfo_creditcode)
            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkViolationfile/'+siteinfo_id).then(function(res) {
                if (true) {
                    var data = res.data.resultValue[0];
                    if(self.page() != "ViolationDetails"){
                        self.ViolationContent(res.data.resultValue) //本年度违章情况
                    }
                    
                   self.ViolationUnit(data['violation_org']);//违章单位
                   self.ViolationTime(data['violation_date']);//违章时间
                   self.natureUnit(data['violation_nature']);//单位性质
                   self.ViolationGrade(data['violation_level']);//违章级别
                   self.InspectionUnit(data['inspection_org']);//巡检单位
                   self.ISshutdown(data['reform_states']);//停工状态
                   self.ViolationPersonnel(data['violation_staff']);//违章人员
                   self.ViolationNature(data['violation_nature']);//违章性质
                   self.ViolationContents(data['violation_content']);//违章内容
                   self.ViolationPlace(data['work_place']);//违章地点
                   self.penalizeContent(data['penalty_amount']);//处罚内容

                } else {
                    cube.indicate("数据加载失败");
                }

            })

        }
        self.items=[]
        //本年度违章情况
        self.Violation = [
            // {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "violation_level", width:"14.2%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center",},
            {name: "violation_content", width:"14.2%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                  self.goViolationDetails(pkValue);
        }},
            {name: "violation_org", width:"14.2%", caption: "违章单位" , editorType: "TextEditor",align : "center",},
            {name: "violation_date", width:"14.2%", caption: "违章时间" , editorType: "TextEditor",align : "center",},
            {name: "accept_org", width:"14.2%", caption: "巡检单位" , editorType: "TextEditor",align : "center",},
            {name: "violation_staff", width:"14.2%", caption: "违章人员" , editorType: "TextEditor",align : "center",},
            {name: "mumber_cnt", width:"14.2%",caption: "违章人数" , editorType: "TextEditor",align : "center",},
            // {name: "major1", width:"120px", caption: "本年度准入" , editorType: "TextEditor"},
            // {name: "major", width:"107px", caption: "关联作业数" , editorType: "TextEditor"},
        ];
        self.ViolationContent=[]
        //注册信息
        self.message = [
            // {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "name", width:"20%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center",},
            {name: "update_time", width:"20%", readOnly:true, caption: "注册及变更时间" , editorType: "TextEditor",align : "center",},
            {name: "content", width:"20%", caption: "变更内容" , editorType: "TextEditor",align : "center",},
            {name: "org_nature", width:"20%", caption: "准入性质" , editorType: "TextEditor",align : "center",},
            {name: "audit_status", width:"20%", caption: "审核状态" , editorType: "TextEditor",align : "center",},
        ];
        self.messageContent=[]
        //各单位准入情况
        self.Admittance = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "datareport_org", width:"16.6%", readOnly:true, caption: "所属省份" , editorType: "TextEditor",align : "center",},
            {name: "accept_org", width:"16.6%", readOnly:true, caption: "报备单位" , editorType: "TextEditor",align : "center",},
            {name: "basicinfo_contractor", width:"16.6%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center",},
            {name: "basicinfo_legalrepresentative", width:"16.6%", caption: "法定代表人" , editorType: "TextEditor",align : "center",},
            // {name: "projectLeader", width:"150px", caption: "项目负责人" , editorType: "TextEditor"},
            {name: "access_state", width:"16.6%", caption: "准入状态" , editorType: "TextEditor",align : "center",},
            {name: "annex", width:"16.6%", caption: "安全生产许可证" , editorType: "TextEditor",align : "center",},
        ];
        self.admittanceContent=[]

        //工作负责人
        self.JobLeader = [
            {name: "siteworkerinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "name", width:"14.2%", readOnly:true, caption: "姓名" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    self.params({
                        id:pkValue,
                        type: 'qiye'
                    })
                    self.page('renyuan')
                }
            },
            {name: "id_card", width:"14.2%", readOnly:true, caption: "身份证号" , editorType: "TextEditor",align : "center",},
            {name: "accept_org", width:"14.2%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "profession", width:"14.2%", caption: "专业" , editorType: "TextEditor",align : "center",},
            // {name: "skill", width:"150px", caption: "当前技能" , editorType: "TextEditor"},
            {name: "contact", width:"14.2%", caption: "联系方式" , editorType: "TextEditor",align : "center",},
            {name: "test_score", width:"14.2%", caption: "安规考试" , editorType: "TextEditor",align : "center",},
        ];
        self.JobLeaderContent=[]
        //外包人员
        self.epibolyLeader = [
            {name: "siteworkerinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "name", width:"14.2%", readOnly:true, caption: "姓名" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    self.params({
                        id:pkValue,
                        type: 'qiye'
                    })
                    self.page('renyuan')
                },
            },
            {name: "id_card", width:"14.2%", readOnly:true, caption: "身份证号" , editorType: "TextEditor",align : "center",},
            {name: "accept_org", width:"14.2%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "profession", width:"14.2%", caption: "专业" , editorType: "TextEditor",align : "center",},
            // {name: "skill", width:"150px", caption: "当前技能" , editorType: "TextEditor"},
            {name: "contact", width:"14.2%", caption: "联系方式" , editorType: "TextEditor",align : "center",},
            {name: "test_score", width:"14.2%", caption: "安规考试" , editorType: "TextEditor",align : "center",},
        ];
        self.epibolyLeaderContent=[]
        //违章审批意见
        self.IllegalApproval = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "matter", width:"10%", readOnly:true, caption: "事项" , editorType: "TextEditor",align : "center",},
            {name: "IllegalApprovalPersonnel", width:"10%", readOnly:true, caption: "违章审核人员" , editorType: "TextEditor",align : "center",},
            {name: "IllegalApprovalIdea", width:"10%", caption: "违章审核意见" , editorType: "TextEditor",align : "center",},
            {name: "IllegalApprovalTime", width:"10%", caption: "违章审核时间" , editorType: "TextEditor",align : "center",},

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
        self.option = {
            // title: {
            //     text: '各单位电网风险预警数量分布情况',
            //     textStyle: {
            //         align: 'center',
            //         color: '#fff',
            //         fontSize: 20,
            //     },
            //     top: '3%',
            //     left: '40%',
            // },
            // backgroundColor: '#0f375f',
            grid: {
                top: "15%",
                bottom: "5%",
                right: '1%',
                left:'2%'
            },
            tooltip: {
                trigger: "axis",
                axisPointer: {
                    type: "shadow",
                    label: {
                        show: true
                    }
                }
            },
            xAxis: {
                data:cityNames,
                axisLine: {
                    show: true //隐藏X轴轴线
                },
                axisTick: {
                    show: true //隐藏X轴刻度
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "#ebf8ac" //X轴文字颜色
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#01FCE3'
                    }
                },
            },
            yAxis: [{
                type: "value",
                name: "",
                nameTextStyle: {
                    color: "#ebf8ac"
                },
                splitLine: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: true
                },
                axisLine: {
                    show: true
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "#ebf8ac"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#FFFFFF'
                    }
                },
            },

                {
                    type: "value",
                    gridIndex: 0,
                    min: 50,
                    max: 100,
                    splitNumber: 8,
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    },
                    splitArea: {
                        show: true,
                        areaStyle: {
                            color: ["rgba(250,250,250,0.0)", "rgba(250,250,250,0.05)"]
                        }
                    }
                }
            ],
            series: [

                {
                    name: "",
                    type: "bar",
                    barWidth: 15,
                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: "#e47737"
                            },
                                {
                                    offset: 1,
                                    color: "#f9a574"
                                }
                            ])
                        }
                    },
                    data:powerGridSums
                }
            ]
        };
        //安全准入查询
        self.inquire = function() {
            var jsonStr = {
                'pageSize':10,
                'pageNo':self.pageIndex()-1,
                "dataStr":{
                    "basicinfoContractor":self.nameFirm(),
                    "orgNature":self.Property(),
                    "datareportOrgId":self.province(),
                    "isEnableEnter":self.annualAccess(),
                    "datafillOrgId":self.contactUnit()
                }
            }
            axios.post(cube.safetygatewayURL + "enterprisesAdmittance/getWorkSiteinfosList",jsonStr).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue.resultList
                    self.count(res.data.resultValue.listSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 10));
                    // console.log(res.data.resultValue)
                    self.items(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        },
         //违章详情页面关闭
            self.refresh = function (e) {
                self.page('detail');
            }

        enterpriseInformation()
        AccessUnit()
        unitList()
        dropDownBox()
        getDatafillOrg()
         //全网准入单位情况汇总
        function enterpriseInformation () {
            axios.get(cube.safetygatewayURL + "enterprisesAdmittance/getEnterpriseCnts").then(function(res) {
                if (true) {
                    var IndustryUnit=res.data.resultValue.cyEnterpriseCnt.enterprise_cnt //产业单位
                    var OutsourcingUnit=res.data.resultValue.wbEnterpriseCnt.enterprise_cnt //外包单位
                    var fmqdEnterpriseCnt=res.data.resultValue.fmqdEnterpriseCnt.enterprise_cnt //本月负面清单数
                    var zrEnterpriseCnt=res.data.resultValue.zrEnterpriseCnt.enterprise_cnt //本月准入单位数
                    var allEnterpriseCnt=res.data.resultValue.allEnterpriseCnt.enterprise_cnt //单位总数
                    var hmdEnterpriseCnt=res.data.resultValue.hmdEnterpriseCnt.enterprise_cnt //本月黑名单数

                    self.unitSum(allEnterpriseCnt);//单位总数
                    self.Industry(IndustryUnit);//产业单位
                    self.epiboly(OutsourcingUnit);//外包单位
                    self.admittance(zrEnterpriseCnt);//本月准入单位数
                    self.blacklist(hmdEnterpriseCnt);//本月黑名单单位数
                    self.NegativeList(fmqdEnterpriseCnt);//本月负面清单数

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
            //柱状图  各单位准入情况
        function AccessUnit () {
            axios.get(cube.safetygatewayURL + "enterprisesAdmittance/getEnterprisesAdmittanceInfoCnt/5").then(function(res) {
                if (true) {
                    var earlyNum=res.data.resultValue
                    // console.log(res.data.resultValue)
                    for (var i in earlyNum){
                        cityNames.push(earlyNum[i].datareport_org)
                        powerGridSums.push(earlyNum[i].enterprise_cnt)
                        var option = self.option();
                        option.xAxis.data = cityNames;
                        option.series.data = powerGridSums;

                        self.option(option)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }

        //企业信息列表
        function unitList() {
            var jsonStr = {
                "pageSize":"10",
                'pageNo':self.pageIndex()-1,
                "dataStr":{
                    "basicinfoContractor":'',
                    "orgNature":'',
                    "datareportOrgId":'',
                     "isEnableEnter":'',
                     "datafillOrgId":''
        }
        }
            axios.post(cube.safetygatewayURL + "enterprisesAdmittance/getWorkSiteinfosList",jsonStr).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue.resultList
                    self.count(res.data.resultValue.listSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 10));
                    // console.log(res.data.resultValue)
                    self.items(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //下拉框选项   企业信息列表查询下拉框选项
        function dropDownBox() {
            axios.get(cube.safetygatewayURL + "enterprisesAdmittance/getSrpRiskSysTab").then(function(res) {
                if (true) {
                    for (var i=0;i< res.data.resultValue.orgNature.length;i++){  //企业性质
                        self.PropertyContent.push(res.data.resultValue.orgNature[i])
                    }
                    for (var i=0;i< res.data.resultValue.voltageLevel.length;i++){  //所属省份
                        self.provinceContent.push(res.data.resultValue.voltageLevel[i])
                    }
                    // console.log(res.data.resultValue)
                    // for (var i=0;i< res.data.resultValue.voltageLevel.length;i++){  //联系单位
                    //     self.provinceContent.push(res.data.resultValue.voltageLevel[i])
                    // }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //下拉框选项   企业信息列表查询联系单位下拉框选项
        function getDatafillOrg() {
            axios.get(cube.safetygatewayURL + "enterprisesAdmittance/getDatafillOrg?datafillOrgId=1").then(function(res) {
                if (true) {
                    for (var i=0;i< res.data.resultValue.datafillOrg.length;i++){  //企业性质
                        self.contactUnitContent.push(res.data.resultValue.datafillOrg[i])
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //现场作业单位详细信息查询
        self.goDetail = function (siteinfo_id,basicinfo_creditcode) {
            self.page('detail')
            axios.get(cube.safetygatewayURL +'enterprisesAdmittance/getSrpWorkSiteinfo/'+siteinfo_id).then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
                    var data = res.data.resultValue[0];
                    self.enterpriseName(data['basicinfo_contractor']);//企业名称
                    self.establishedTime(data['basicinfo_establishdate']);//成立时间
                    self.licenseNumber(data['basicinfo_creditcode']);//执照编号
                    self.enterpriseProperty(data['org_nature']);//企业性质
                    self.legalPerson(data['basicinfo_legalrepresentative']);//法人代表
                    self.region(data['datafill_org']);//所在省市
                    self.fitment(data['certification_name']);//承装修资质
                    self.Unit(data['accept_org']);//联系单位
                    self.subject(data['certification_name']);//主项资质
                    self.contactNumber(data['basicinfo_contactnumber']);//联系电话
                    self.unitNumber(data['mumber_cnt']);//单位人数
                    self.shutDown();//停工状态
                    self.unitLocation(data['basicinfo_orgaddress']);//单位地址
                    self.registeredCapital(data['basicinfo_registeredcapital']);//注册资本
                    // self.ViolationContent(res.data.resultValue) //本年度违章情况
                    //

                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })

        }
        //准入情况
        function AccessIs(siteinfo_id,basicinfo_creditcode) {

        axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkSiteinfoAll?basicinfoCreditcode=' +basicinfo_creditcode).then(function(res) {
            if (true) {
                console.log(res.data.resultValue)
                self.admittanceContent(res.data.resultValue)

            } else {
                cube.indicate("数据加载失败");
            }

        })
    }
        //企业准入信息
        function EnterpriseAccess(siteinfo_id,basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkSiteinfochange?basicinfoCreditcode=' +basicinfo_creditcode).then(function(res) {
                if (true) {
                    self.messageContent(res.data.resultValue)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //工作负责人
        function JobLeader(siteinfo_id,basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?basicinfoCreditcode=' +basicinfo_creditcode+'&workerType=1').then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
                    self.JobLeaderContent(res.data.resultValue)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //外包人员
        function subcontractingStaff(siteinfo_id,basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?basicinfoCreditcode=' +basicinfo_creditcode+'&workerType=2').then(function(res) {
                if (true) {
                    console.log(res.data.resultValue)
                    self.epibolyLeaderContent(res.data.resultValue)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
    cube.endViewModel(self, params);


};

    return PageViewModel;
});