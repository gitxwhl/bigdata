define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
        var self = this;
        $('.popover').hide();
        setTimeout(function(){
            if(params.siteinfo_id){
                self.siteinfo_id = params.siteinfo_id;
                self.goDetail(params.siteinfo_id)
                getSrpWorkViolationfile(params.siteinfo_id)
            }
            if(params.basicinfo_creditcode){
                self.basicinfo_creditcode = params.basicinfo_creditcode;
                EnterpriseAccess(params.basicinfo_creditcode)
                AccessIs(params.basicinfo_creditcode)
                JobLeader(params.basicinfo_creditcode)
                subcontractingStaff(params.basicinfo_creditcode)
            }
        })
        $('.content-bg').scrollTop(0);
		self.returnBack = function(){
			commonPageBridge({ 
				name: 'blacklist.blacklist', 
				params: {} 
			});
        }
        self.siteinfo_id = ''
        self.basicinfo_creditcode = ''
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
        self.goDetail = function (siteinfo_id) {
            
            axios.get(cube.safetygatewayURL +'enterprisesAdmittance/getSrpWorkSiteinfo/'+siteinfo_id).then(function(res) {
                
                if (res.data.successful && res.data.resultValue.length>0) {
                    // console.log(res.data.resultValue)
                    var data = res.data.resultValue[0];
                    self.enterpriseName(data['basicinfo_contractor']);//企业名称
                    self.establishedTime(data['basicinfo_establishdate']);//成立时间
                    self.licenseNumber(data['basicinfo_creditcode']);//执照编号
                    self.enterpriseProperty(data['org_nature']);//企业性质
                    self.legalPerson(data['basicinfo_legalrepresentative']);//法人代表
                    self.region(data['datareport_org']);//所在省市
                    self.fitment(data['certification_name']);//承装修资质
                    self.Unit(data['basicinfo_contractor']);//联系单位
                    self.subject(data['certification_name']);//主项资质
                    self.contactNumber(data['basicinfo_contactnumber']);//联系电话
                    self.unitNumber(data['mumber_cnt']);//单位人数
                    self.shutDown();//停工状态
                    self.unitLocation(data['basicinfo_orgaddress']);//单位地址
                    self.registeredCapital(data['basicinfo_registeredcapital']);//注册资本
                    // self.ViolationContent(res.data.resultValue) //本年度违章情况
                    //

                }

            })

        }
        self.wzxqData = {};
        function getSrpWorkViolationfile (siteinfo_id) {
            // console.log(siteinfo_id,basicinfo_creditcode)
            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkViolationfile/'+siteinfo_id).then(function(res) {
                if (res.data.resultValue.length>0) {
                    self.wzxqData = res.data.resultValue[0];
                    var list = res.data.resultValue;
                    for(var i=0;i<list.length;i++){
                        list[i]['id'] = i+1;
                    }
                    self.ViolationContent(list) //本年度违章情况
                //    self.ViolationUnit(data['violation_org']);//违章单位
                //    self.ViolationTime(data['violation_date']);//违章时间
                //    self.natureUnit(data['violation_nature']);//单位性质
                //    self.ViolationGrade(data['violation_level']);//违章级别
                //    self.InspectionUnit(data['inspection_org']);//巡检单位
                //    self.ISshutdown(data['reform_states']);//停工状态
                //    self.ViolationPersonnel(data['violation_staff']);//违章人员
                //    self.ViolationNature(data['violation_nature']);//违章性质
                //    self.ViolationContents(data['violation_content']);//违章内容
                //    self.ViolationPlace(data['work_place']);//违章地点
                //    self.penalizeContent(data['penalty_amount']);//处罚内容

                }

            })
        }
        var fzjb = {
            "01":'一般违章',
            "02":'严重违章',
            "03":'红线禁令',
        }
		//本年度违章情况
        self.Violation = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "violation_level", width:"14.2%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center" },
            {name: "violation_content", width:"35.2%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    commonPageBridge({ 
                        name: 'blacklist.illegal.main', 
                        params: {
                            siteinfo_id:self.siteinfo_id,
                            basicinfo_creditcode:self.basicinfo_creditcode,
                            wzxqData:self.wzxqData
                        } 
                    });
                }},
            {name: "violation_org", width:"14.2%", caption: "违章单位" , editorType: "TextEditor",align : "center"},
            {name: "violation_date", width:"14.2%", caption: "违章时间" , editorType: "TextEditor",align : "center"},
            {name: "accept_org", width:"14.2%", caption: "巡检单位" , editorType: "TextEditor"},
            {name: "violation_staff", width:"11.2%", caption: "违章人员" , editorType: "TextEditor",align : "center"},
            {name: "mumber_cnt", width:"11.2%",caption: "违章人数" , editorType: "TextEditor",align : "center"},
            // {name: "major1", width:"120px", caption: "本年度准入" , editorType: "TextEditor"},
            // {name: "major", width:"107px", caption: "关联作业数" , editorType: "TextEditor"},
        ];
        self.ViolationContent=[
            // {
            //     id:1,
            //     personNames:'一般违章',
            //     gender:"违章详情",
            //     age:'北京服贸有限责任公司',
            //     certificateName:'2019-1-1',
            //     belongLevel:"",
            //     Certificate_number:'张某',
            //     belongUnit:1,
            // },


        ]
        //注册信息
        self.message = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center"},
            {name: "name", width:"20%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center"},
            {name: "update_time", width:"20%", readOnly:true, caption: "注册及变更时间" , editorType: "TextEditor",align : "center"},
            {name: "content", width:"20%", caption: "变更内容" , editorType: "TextEditor",align : "center"},
            {name: "content", width:"20%", caption: "准入性质" , editorType: "TextEditor",align : "center"},
            {name: "audit_status", width:"20%", caption: "审核状态" , editorType: "TextEditor",align : "center"},
        ];
        function EnterpriseAccess(basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkSiteinfochange?basicinfoCreditcode=' +basicinfo_creditcode).then(function(res) {
                if (res.data.resultValue.length>0) {
                    var list = res.data.resultValue;
                    for(var i=0;i<list.length;i++){
                        list[i]['id'] = i+1;
                    }
                    self.messageContent(list)
                }
                // else {
                //     cube.indicate("数据加载失败");
                // }

            })
        }
        self.messageContent=[
            // {
            //     id:'',
            //     personNames:'北京服贸有限责任公司',
            //     registerTime:"2016-4-4",
            //     alterationTime:'注册',
            //     AccessNature:'主业',
            //     auditStatus:"已通过",

            // },


        ]
        //各单位准入情况
        self.Admittance = [
            {name: "datareport_org", width:"20%", readOnly:true, caption: "报备单位" , editorType: "TextEditor",align : "center",},
            {name: "accept_org", width:"20%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center"},
            {name: "basicinfo_contractor", width:"20%", caption: "法定代表人" , editorType: "TextEditor",align : "center"},
            // {name: "basicinfo_legalrepresentative", width:"16.6%", caption: "项目负责人" , editorType: "TextEditor",align : "center"},
            {name: "access_state", width:"20%", caption: "审核情况" , editorType: "TextEditor",align : "center"},
            {name: "annex", width:"20%", caption: "安全生产许可证" , editorType: "TextEditor",align : "center"},
        ];
         //准入情况
         function AccessIs(basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkSiteinfoAll?basicinfoCreditcode=' +basicinfo_creditcode).then(function(res) {
                if (res.data.resultValue.length>0) {
                    var list = res.data.resultValue;
                    for(var i=0;i<list.length;i++){
                        list[i]['id'] = i+1;
                    }
                    self.admittanceContent(list)
    
                }
                // else {
                //     cube.indicate("数据加载失败");
                // }
                //
            })
        }
        self.admittanceContent=[
            // {
            //     id:'',
            //     personNames:'国网,天津市供电公司，河北省供电公司',
            //     Companyname:"北京服贸有限责任公司",
            //     legalRepresentative:'王某某',
            //     projectLeader:'主业',
            //     AuditSituation:"已通过",
            //     safetyProduction:'附件'

            // },


        ]
        //工作负责人
        self.JobLeader = [
            {name: "siteworkerinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "name", width:"16.66%", readOnly:true, caption: "姓名" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    commonPageBridge({ 
                        name: 'riskAnalysis.peopleDetail.main', 
                        params: {
                            id:pkValue,
                            type:'hmd',
                            siteinfo_id:self.siteinfo_id,
                            basicinfo_creditcode:self.basicinfo_creditcode
                        } 
                    });
                },
            },
            {name: "id_card", width:"16.66%", readOnly:true, caption: "身份证号" , editorType: "TextEditor",align : "center"},
            {name: "accept_org", width:"16.66%", caption: "所属单位" , editorType: "TextEditor",align : "center"},
            {name: "profession", width:"16.66%", caption: "专业" , editorType: "TextEditor",align : "center"},
            // {name: "skill", width:"16.66%", caption: "当前技能" , editorType: "TextEditor",align : "center"},
            {name: "contact", width:"16.66%", caption: "联系方式" , editorType: "TextEditor",align : "center"},
            {name: "test_score", width:"16.66%", caption: "安规考试" , editorType: "TextEditor",align : "center"},
        ];
        //工作负责人
        function JobLeader(basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?basicinfoCreditcode=' +basicinfo_creditcode+'&workerType=1').then(function(res) {
                if (true) {
                    self.JobLeaderContent(res.data.resultValue)
                }
                // else {
                //     cube.indicate("数据加载失败");
                // }

            })
        }
        self.JobLeaderContent=[
            // {
            //     id:'',
            //     name:'张三',
            //     IDnumber:"110103458646745667",
            //     affiliatedUnit:'北京服贸有限责任公司',
            //     major:'农网工程',
            //     skill:"I",
            //     contactInformation:'17389129875',
            //     SafetyTest:'70.5'

            // },


        ]
        //外包人员
        self.epibolyLeader = [
            {name: "siteworkerinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "name", width:"16.66%", readOnly:true, caption: "姓名" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    commonPageBridge({ 
                        name: 'riskAnalysis.peopleDetail.main', 
                        params: {
                            id:pkValue,
                            type:'hmd',
                            siteinfo_id:self.siteinfo_id,
                            basicinfo_creditcode:self.basicinfo_creditcode
                        } 
                    });
                },
            },
            {name: "id_card", width:"16.66%", readOnly:true, caption: "身份证号" , editorType: "TextEditor",align : "center",},
            {name: "accept_org", width:"16.66%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "profession", width:"16.66%", caption: "专业" , editorType: "TextEditor",align : "center",},
            // {name: "skill", width:"150px", caption: "当前技能" , editorType: "TextEditor"},
            {name: "contact", width:"16.66%", caption: "联系方式" , editorType: "TextEditor",align : "center",},
            {name: "test_score", width:"16.66%", caption: "安规考试" , editorType: "TextEditor",align : "center",},
        ];
        //外包人员
        function subcontractingStaff(basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?basicinfoCreditcode=' +basicinfo_creditcode+'&workerType=2').then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
                    self.epibolyLeaderContent(res.data.resultValue)
                }
                // else {
                //     cube.indicate("数据加载失败");
                // }

            })
        }
        self.epibolyLeaderContent=[
            // {
            //     id:'',
            //     name:'张三',
            //     IDnumber:"110103458646745667",
            //     affiliatedUnit:'北京服贸有限责任公司',
            //     major:'农网工程',
            //     skill:"I",
            //     contactInformation:'17389129875',
            //     SafetyTest:'70.5'

            // },


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
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});