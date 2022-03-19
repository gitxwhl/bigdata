define(["RESTClient",'echarts', "axios",], function (RestClient,echarts, axios) {
    var PageViewModel = function (params) {
        var self = this;
        // 0ad2d9ec0f5111ebb4cd02424fbc700b
        urlSite()
        function urlSite() {
             getHref = window.location.href;
            var array1 = getHref.split("?");
            var array2 = array1[1].split("&");
            var obj = {};
            for (var i = 0; i < array2.length; i++) {
                var obj2 = array2[i].split("=");
                obj[obj2[0]] = obj2[1];
            }
            return obj;
        }
        // function urlSite2() {
        //     const getHref = window.location.href;
        //     const array1 = getHref.split("?");
        //     const array2 = array1[1].split("&");
        //     const obj = {};
        //     for (let i = 0; i < array2.length; i++) {
        //         let obj2 = array2[i].split("=");
        //         obj[obj2[0]] = obj2[1];
        //     }
        //     return obj;
        // }

        // const urlObj2 = urlSite2();
        // console.log(urlObj2.code)
        var urlObj = urlSite();
        // self.basicinfo_creditcode=urlObj2.code
        self.basicinfo_creditcode=''
        self.ids=urlObj.id
        console.log(self.ids)
        self.page = 'detail';
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
        self.ViolationUnit=ko.observable();//违章单位
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
        self.size2 = 'middle';
        self.showArrow2 = true;
        self.showTotal2 = true;
        self.showGoto2 = true;
        self.showAllPage2 = false;
        self.count2 ='';
        self.pageTotalCount2 = '';
        self.pageIndex2 =ko.observable(1);
        self.pageVisibleCount2 = 1;
        self.onPageIndexChanged2 = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                self.onRendered()
            }
        }
        self.query = function(){
            $(".pageA").css({'display':'block'});
            $(".pageB").css({'display':'none'});
            self.pageIndex(1);
            self.inquire()
        }

        self.style ="background:#cccccc;";
        // self.isShowRowNumber = true;
        // self.isShowCheckBox = true;
        // self.isShowCheckBoxs = false;
        // self.isShowBorder = true;
        // self.isShowStripe = false;
        // self.isShowHover = true;
        // self.isShowCondense = false;
        // self.isAllowEdit = false;
        // self.isAllowDelete = false;
        // self.isAllowOperations = false;
        // self.isAllowShift = false;
        // self.isAllowSort = true;
        // self.isAllowAppend = false;
        // self.isAllowPaging = false;
        // self.pageSize = 10;
        // self.id='';

        self.isShowRowNumber = true;
        self.isShowCheckBox = true;
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
                    element.style.textDecoration = "underline";
                    element.style.cursor = "pointer";
                    // element.style='cursor:pointer';
                },
                // onClick: function (pkValue, cellValue) {
                //
                //     var list = self.items();
                //     var basicinfo_creditcode = '';
                //     for(var i = 0; i<list.length;i++){
                //         if(pkValue == list[i]['siteinfo_id']){
                //             basicinfo_creditcode = list[i]['basicinfo_creditcode']
                //         }
                //     }
                //     AccessIs(pkValue,basicinfo_creditcode)
                //     subcontractingStaff(pkValue,basicinfo_creditcode)
                //     EnterpriseAccess(pkValue,basicinfo_creditcode)
                //     JobLeader(pkValue,basicinfo_creditcode)
                //     // getSrpWorkViolationfile (pkValue,basicinfo_creditcode)
                //     // self.goDetail(pkValue,basicinfo_creditcode);
                // }
                },
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
            // console.log(siteinfo_id)
            self.page('ViolationDetails')
            getSrpWorkViolationfile2(siteinfo_id)
        }

        //本年度违章情况
        getSrpWorkViolationfile ()
        function getSrpWorkViolationfile (id) {
            // console.log(siteinfo_id,basicinfo_creditcode)
            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkViolationfile/'+self.ids).then(function(res) {
                if (true) {
                    var data = res.data.resultValue[0];
                    // console.log(res.data.resultValue)
                    if(self.page() != "ViolationDetails"){
                        self.ViolationContent(res.data.resultValue) //本年度违章情况
                        // console.log(res.data.resultValue)
                    }

                        // self.ViolationUnit(data['violation_org']);//违章单位
                        // self.ViolationTime(data['violation_date']);//违章时间
                        // self.natureUnit(data['violation_nature']);//单位性质
                        // self.ViolationGrade(data['violation_level']);//违章级别
                        // self.InspectionUnit(data['inspection_org']);//巡检单位
                        // self.ISshutdown(data['reform_states']);//停工状态
                        // self.ViolationPersonnel(data['violation_staff']);//违章人员
                        // self.ViolationNature(data['violation_nature']);//违章性质
                        // self.ViolationContents(data['violation_content']);//违章内容
                        // self.ViolationPlace(data['work_place']);//违章地点
                        // self.penalizeContent(data['penalty_amount']);//处罚内容

                } else {
                    cube.indicate("数据加载失败");
                }

            })

        }
        function getSrpWorkViolationfile2 (siteinfo_id) {
            // console.log(siteinfo_id)
            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkViolationfile/'+siteinfo_id).then(function(res) {
                if (true) {
                    var data = res.data.resultValue[0];
                    console.log(res.data)
                    // if(self.page() != "ViolationDetails"){
                    //     self.ViolationContent(res.data.resultValue) //本年度违章情况
                    //     // console.log(res.data.resultValue)
                    // }

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
                    element.style.cursor = "pointer";
                },
                onClick: function (pkValue, cellValue) {
                // console.log(pkValue)
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
                // onClick: function (pkValue, cellValue) {
                //     self.params({
                //         id:pkValue,
                //         type: 'qiye'
                //     })
                //     self.page('renyuan')
                // }
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
                // onClick: function (pkValue, cellValue) {
                //     self.params({
                //         id:pkValue,
                //         type: 'qiye'
                //     })
                //     // self.page('renyuan')
                // },
            },
            {name: "id_card", width:"14.2%", readOnly:true, caption: "身份证号" , editorType: "TextEditor",align : "center",},
            {name: "accept_org", width:"14.2%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "profession", width:"14.2%", caption: "专业" , editorType: "TextEditor",align : "center",},
            // {name: "skill", width:"150px", caption: "当前技能" , editorType: "TextEditor"},
            {name: "contact", width:"14.2%", caption: "联系方式" , editorType: "TextEditor",align : "center",},
            {name: "test_score", width:"14.2%", caption: "安规考试" , editorType: "TextEditor",align : "center",},
        ];
        self.epibolyLeaderContent=[]






            //违章详情页面关闭
            self.refresh = function (e) {
                self.page('detail');
            }





        //二级菜单返回
        self. goreturn=function(){
            self.page('list')
        }
        self. goreturn2=function(){
            self.page('detail')
        }
        goDetail()
        //现场作业单位详细信息查询
        function goDetail (id) {
        // self.goDetail = function (siteinfo_id,basicinfo_creditcode) {
            // self.page('detail')

            axios.get(cube.safetygatewayURL +'enterprisesAdmittance/getSrpWorkSiteinfo/'+self.ids).then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
                    var data = res.data.resultValue[0];
                    if (res.data.resultValue==0){
                        self.basicinfo_creditcode('');
                        self.enterpriseName('');//企业名称
                        self.establishedTime('');//成立时间
                        self.licenseNumber('');//执照编号
                        self.enterpriseProperty('');//企业性质
                        self.legalPerson('');//法人代表
                        self.region('');//所在省市
                        self.fitment('');//承装修资质
                        self.Unit('');//联系单位
                        self.subject('');//主项资质
                        self.contactNumber('');//联系电话
                        self.unitNumber('');//单位人数
                        self.shutDown();//停工状态
                        self.unitLocation('');//单位地址
                        self.registeredCapital('');//注册资本
                    }else {
                        self.basicinfo_creditcode(data['basicinfo_creditcode'])
                        self.enterpriseName(data['basicinfo_contractor']);//企业名称
                        self.establishedTime(data['basicinfo_establishdate']);//成立时间
                        self.licenseNumber(data['basicinfo_creditcode']);//执照编号
                        self.enterpriseProperty(data['org_nature']);//企业性质
                        self.legalPerson(data['basicinfo_legalrepresentative']);//法人代表
                        self.region(data['datareport_org']);//所在省市
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
                    }
                    EnterpriseAccess()
                    subcontractingStaff()
                    JobLeader()
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })

        }
        AccessIs()
        //准入情况
        function AccessIs(siteinfo_id,basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkSiteinfoAll?basicinfoCreditcode=' +self.ids).then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
                    self.admittanceContent(res.data.resultValue)

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //企业准入信息
        // EnterpriseAccess()
        function EnterpriseAccess(siteinfo_id,basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkSiteinfochange?basicinfoCreditcode=' +self.basicinfo_creditcode()).then(function(res) {
                if (true) {
                    self.messageContent(res.data.resultValue)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //工作负责人
        // JobLeader()
        function JobLeader(siteinfo_id,basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?basicinfoCreditcode=' +self.basicinfo_creditcode()+'&workerType=1').then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
                    self.JobLeaderContent(res.data.resultValue)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //外包人员
        // subcontractingStaff()
        function subcontractingStaff(siteinfo_id,basicinfo_creditcode) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?basicinfoCreditcode=' +self.basicinfo_creditcode()+'&workerType=2').then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
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
