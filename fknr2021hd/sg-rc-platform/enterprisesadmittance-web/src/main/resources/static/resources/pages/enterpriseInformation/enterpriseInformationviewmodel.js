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
            {id:'03',text:'异常'},
        ];
        self.annualAccess=ko.observable("");
        //联系单位
        // self.contactUnitContent =[{id:'',text:'全部'},];
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
        self.pageVisibleCount = 10;
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
        self.pageVisibleCount2 = 10;
        var s=false
        self.onPageIndexChanged2 = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex2(p_pageIndex);
// console.log(self.a())
//                 self.onRendered()
            //    console.log(state.a)


              
                if(s==true){
                    return
                }
                
                var jsonStr = {
                    'pageSize':10,
                    'pageNo':self.pageIndex2()-1,
                    "dataStr":{
                        "basicinfoContractor":self.nameFirm(),
                        "orgNature":self.Property(), 
                        "datareportOrgId":self.a(),
                        "isEnableEnter":self.annualAccess(),
                        "datafillOrgId":self.contactUnit()
                    }
                }
                axios.post(cube.safetygatewayURL + "enterprisesAdmittance/getWorkSiteinfosList",jsonStr).then(function(res) {
                    if (true) {
                        var risklist=res.data.resultValue.resultList
                        self.count2(res.data.resultValue.listSize);
                        self.pageTotalCount2(Math.ceil(res.data.resultValue.listSize / 10));
                        self.items(risklist);
                    } else {
                        cube.indicate("数据加载失败");
                    }

                })
            }
        }
        // 本年度违章情况
        self.size3 = 'middle';
        self.showArrow3 = true;
        self.showTotal3 = true;
        self.showGoto3 = true;
        self.showAllPage3 = false;
        self.count3 ='';
        self.pageTotalCount3 = '';
        self.pageIndex3 =ko.observable(1);
        self.pageVisibleCount3 = 10;
        self.onPageIndexChanged3 = function(p_pageIndex) {
            self.pageIndex3(p_pageIndex)
            getSrpWorkViolationfile(siteinfo_id)
        }
        // 工作负责人
        self.size4 = 'middle';
        self.showArrow4 = true;
        self.showTotal4 = true;
        self.showGoto4 = true;
        self.showAllPage4 = false;
        self.count4 ='';
        self.pageTotalCount4 = '';
        self.pageIndex4 =ko.observable(1);
        self.pageVisibleCount4 = 10;
        self.onPageIndexChanged4 = function(p_pageIndex) {
            self.pageIndex4(p_pageIndex)
            JobLeader(province_code,siteinfo_id)
            
        }
        // 作业人员
        self.size5 = 'middle';
        self.showArrow5 = true;
        self.showTotal5 = true;
        self.showGoto5 = true;
        self.showAllPage5 = false;
        self.count5 ='';
        self.pageTotalCount5 = '';
        self.pageIndex5 =ko.observable(1);
        self.pageVisibleCount5 = 10;
        self.onPageIndexChanged5 = function(p_pageIndex) {
            self.pageIndex5(p_pageIndex)
            subcontractingStaff(province_code,siteinfo_id)
        }
        // 作业计划
        self.size6 = 'middle';
        self.showArrow6 = true;
        self.showTotal6 = true;
        self.showGoto6 = true;
        self.showAllPage6 = false;
        self.count6 ='';
        self.pageTotalCount6 = '';
        self.pageIndex6 =ko.observable(1);
        self.pageVisibleCount6 = 10;
        self.onPageIndexChanged6 = function(p_pageIndex) {
            self.pageIndex6(p_pageIndex)
            subcontractingplan(siteinfo_id)
        }
        //导出
        self.download=function() {
            // window.open(cube.safetygatewayURL +'enterprisesAdmittance/downloadEnterExcel')

            axios({
                method: 'post',
                url: cube.safetygatewayURL +'enterprisesAdmittance/downloadEnterExcel',
                responseType: 'arraybuffer'
            }).then(function(res) {
                // console.log(res)
                var blob = new Blob( [res.data], {type: 'application/vnd.ms-excel'} )
                // 兼容不同浏览器的URL对象
                var url = window.URL || window.webkitURL || window.moxURL
                // 创建下载链接
                var downloadHref = url.createObjectURL(blob)
                // 创建a标签并为其添加属性
                var downloadLink = document.createElement('a')
                downloadLink.href = downloadHref
                downloadLink.download = '数据导出.xlsx'
                // 触发点击事件执行下载
                downloadLink.click()
            })
        }
        self.reset=function(){
            self.nameFirm('') //企业名称
            self.Property('');//企业性质
            self.province('');//所属省份
            self.annualAccess('');//准入状态
            self.contactUnit('');//联系单位

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
        self.primaryKeys = 'violationfile_id'
        // self.onSelectedItems = function(e) {
        //     selectedItems = e;
        // };
        self.months = function() {
            // console.log(222222222)
            $(".month").css("background-image", "url('./resources/images/u1132.png')");
            $(".year").css("background-image", "url('./resources/images/u1134.png')");
        }
        var siteinfo_id='';
        var province_code='';
        self.columns = [
            // {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "basicinfo_contractor", width:"15%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                    element.style.textDecoration = "underline";
                    element.style.cursor = "pointer";
                    // element.style='cursor:pointer';
                },
                onClick: function (pkValue, cellValue) {
                   console.log(pkValue,cellValue)
                    var list = self.items();
                    console.log(list)
                    self.pageIndex3(1)
                    self.pageIndex4(1)
                    self.pageIndex5(1)
                    self.pageIndex6(1)
                    var basicinfo_creditcode= '';
                    var basicinfo_contractor='';
                    console.log(list,pkValue)
                    for(var i = 0; i<list.length;i++){
                        if(pkValue == list[i]['siteinfo_id']){
                            basicinfo_creditcode= list[i]['basicinfo_creditcode']
                            province_code=list[i]['province_code']
                            basicinfo_contractor=list[i]['basicinfo_contractor']
                            siteinfo_id=list[i]['siteinfo_id']
                        }
                    }
                    AccessIs(pkValue,basicinfo_contractor,siteinfo_id)
                    subcontractingStaff(province_code,siteinfo_id)
                    subcontractingplan(siteinfo_id)
                    EnterpriseAccess(pkValue,province_code,basicinfo_contractor)
                    JobLeader(province_code,siteinfo_id)
                    getSrpWorkViolationfile (siteinfo_id)
                    self.goDetail(pkValue,basicinfo_creditcode);
                }
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
        self.goViolationDetails = function (siteinfo_id,xsz) {
            // console.log(siteinfo_id)
            self.page('ViolationDetails')
            getSrpWorkViolationfile2(siteinfo_id,xsz)
        }
        var wzstate=true
        //违章详情
        function getSrpWorkViolationfile (siteinfo_id) {
            // console.log(siteinfo_id,basicinfo_creditcode)
            axios({
                url:cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkViolationfile',
                method:'post',
                data:{
                    'dataStr':{'siteinfoId':siteinfo_id},
                    'pageNo':self.pageIndex3()-1,
                    'pageSize':10
                }
            }).then(function(res){
            // axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkViolationfile?siteinfoId='+siteinfo_id).then(function(res) {
                if (res.data.successful==true) {
                    var data = res.data.resultValue[0];
                    self.pageTotalCount3(res.data.resultValue.pageCount)
                    self.count3(res.data.resultValue.listSize)
                    // console.log(res.data.resultValue)
                    if(self.page() != "ViolationDetails"){

                        var nulldata=[]
                        var data=res.data.resultValue.resultList
                        if( data.length==0){
                            self.ViolationContent(nulldata)
                            if($('#zhankai1').css('display')=='block'){
                            document.getElementById("weizhang").style.display="none";
                            }else{
                            document.getElementById("weizhang").style.display="block";
                            }


                        }else  if( data.length!==0){
                            document.getElementById("weizhang").style.display="none";
                            wzstate=false
                            
                            self.ViolationContent(res.data.resultValue.resultList)
                        }
                        // self.ViolationContent(res.data.resultValue) //本年度违章情况
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
        function getSrpWorkViolationfile2 (siteinfo_id,xsz) {
            console.log(xsz)
                if (true) {
                    var data = xsz[0];
                    // console.log(res.data.resultValue)
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
                onClick: function (pkValue, cellValue,index) {
                    var xsz=self.ViolationContent().filter(function(item){
                        return item.violationfile_id==pkValue
                    })
                    self.goViolationDetails(pkValue,xsz);
                    console.log(pkValue,xsz)

                },
            },
                
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
            {name: "org_name", width:"14.2%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
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
            {name: "org_name", width:"14.2%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "profession", width:"14.2%", caption: "专业" , editorType: "TextEditor",align : "center",},
            // {name: "skill", width:"150px", caption: "当前技能" , editorType: "TextEditor"},
            {name: "contact", width:"14.2%", caption: "联系方式" , editorType: "TextEditor",align : "center",},
            {name: "test_score", width:"14.2%", caption: "安规考试" , editorType: "TextEditor",align : "center",},
        ];
        self.epibolyplanContent=[]
        // 作业计划
        self.iframesrc=ko.observable('');
        self.epibolyplan=[
            {name: "work_plan_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "work_plan_name", width:"14.2%", readOnly:true, caption: "作业计划名称" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    self.page('jump')
                    self.iframesrc(`http://20.1.60.108:18080/rmcp-web-workrisk/pages/dist/zyxq2.html?id=${pkValue}`)
                },
            },
            {name: "work_state", width:"14.2%", readOnly:true, caption: "执行状态" , editorType: "TextEditor",align : "center",
             renderCell: function (cellValue, element, dictValue) {
                 if(cellValue=='01'){
                    return '未开工' 
                 }else if(cellValue=='02'){
                    return '已开工' 
                 }else if(cellValue=='03'){
                    return '未开工' 
                 }else if(cellValue=='04'){
                    return '计划延期' 
                 }else if(cellValue=='05'){
                    return '已完工' 
                 }else if(cellValue=='06'){
                    return '计划取消' 
                 }
                },
        },
            {name: "substation_name", width:"14.2%", caption: "变电站/线路名称" , editorType: "TextEditor",align : "center",},
            {name: "work_type", width:"14.2%", caption: "专业类型" , editorType: "TextEditor",align : "center",
            renderCell: function (cellValue, element, dictValue) {
                if(cellValue=='01'){
                   return '生产检修改造' 
                }else if(cellValue=='02'){
                   return '输变电工程' 
                }else if(cellValue=='03'){
                   return '配农网工程' 
                }else if(cellValue=='04'){
                   return '装表接点' 
                }else if(cellValue=='05'){
                   return '业扩工程' 
                }else if(cellValue=='06'){
                   return '迁改工程' 
                }else if(cellValue=='07'){
                    return '网络信息作业' 
                 }else if(cellValue=='08'){
                    return '通信检修施工' 
                 }else if(cellValue=='09'){
                    return '外部工程' 
                 }else if(cellValue=='10'){
                    return '发电检修改造' 
                 }else if(cellValue=='11'){
                    return '发电基建工程' 
                 }else if(cellValue=='12'){
                    return '综合能源项目' 
                 }else if(cellValue=='13'){
                    return '设备租赁项目' 
                 }else if(cellValue=='14'){
                    return '电工制造施工总承包项目' 
                 }else if(cellValue=='15'){
                    return '充电桩工程' 
                 }else if(cellValue=='16'){
                    return '小型基建工程' 
                 }else if(cellValue=='17'){
                    return '其他' 
                 }
               },
        },
            // {name: "skill", width:"150px", caption: "当前技能" , editorType: "TextEditor"},
            {name: "workrisk_level", width:"14.2%", caption: "作业风险等级" , editorType: "TextEditor",align : "center",
            renderCell: function (cellValue, element, dictValue) {
                if(cellValue=='01'){
                   return '一' 
                }else if(cellValue=='02'){
                   return '二' 
                }else if(cellValue=='03'){
                   return '三' 
                }else if(cellValue=='04'){
                   return '四' 
                }else if(cellValue=='05'){
                   return '五' 
                }else if(cellValue=='06'){
                   return '无' 
                }
               },
        },
            {name: "begin_time", width:"14.2%", caption: "计划开始时间" , editorType: "TextEditor",align : "center",},
            {name: "end_time", width:"14.2%", caption: "计划结束时间" , editorType: "TextEditor",align : "center",},
        ]
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


        self.option = ko.observable({

            "tooltip": {
                "trigger": "axis",
                // formatter:'{b}<br />{a0}:{c0}<br/>{a1}:{c1}',
                // formatter: function(params) {
                //     // console.log(params)
                //     var str = 0
                //     var strArr = ''
                //     var name = ''
                //     params.forEach((item, index) => {
                //         if (index === 0) {
                //             name = item.name
                //         }
                //         str += item.value
                //         strArr += `<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${item.color.colorStops[0].color};"></span>` + item.seriesName + ':' + item.value + '<br />'
                //     })
                //     return name + ':' + str + '<br />' + strArr
                // },
                formatter: function(params) {
                    var clickIndex = params.name
                    var str = 0
                    var strArr = ''
                    var name = ''
                    for (var i=0; i<params.length; i++) {
                        if (i == 0) {
                            name = params[i].name
                        }
                        str += params[i].value
                        strArr += `<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${params[i].color.colorStops[0].color};"></span>` + params[i].seriesName + ':' + params[i].value + '<br />'
                    }
                    return name + ':' + str + '<br />' + strArr
                },
                "axisPointer": {
                    "type": "shadow",
                },

            },

            grid: {
                top:'15%',
                right: '3%',
                left:'4%'
            },

            legend: {
                data:['有效','无效','异常'],
                right:"66",
                top:'50',
                textStyle:{
                    color:'#fff',
                    fontSize:14,
                }
            },

            "calculable": true,
            "xAxis": [
                {
                    "type": "category",

                    "axisTick": {
                        "show": false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: '#1B6B9D'
                        }
                    },
                    // "splitArea": {
                    //     "show": true
                    // },
                    "axisLabel": {
                        "show": true,
                        "splitNumber": 15,
                        textStyle: {
                            color: "white" //X轴文字颜色
                        }
                    },
                    "data": [],
                    // formatter : function(p) {
                    //                    return p.value + p.name;
                    //                }
                }
            ],
            "yAxis": [


                {
                    minInterval:1,
                    "type": "value",

                    "splitLine": {
                        "show": false,
                    },
                    "axisLabel": {
                        "show": true,
                        "splitNumber": 15,
                        textStyle: {
                            color: "white" //X轴文字颜色
                        },
                        // formatter: function (value) {
                        //     var texts = [];
                        //     if(value>10000){
                        //         texts.push('1000');
                        //     }
                        //     else if (value <=10000) {
                        //         texts.push('好');
                        //     }
                        //     // else if (value<= 2) {
                        //     //     texts.push('很好');
                        //     // }
                        //     // else if(value<= 3){
                        //     //     texts.push('非常好');
                        //     // }
                        //     // else{
                        //     //     texts.push('完美');
                        //     // }
                        //     return texts;
                        //
                        // }

                    },
                    // max: 5000,

                    // scale: true,

                    "axisTick": {
                        "show": false
                    },
                    "splitArea": {
                        "show": false
                    },
                    "axisLine": {
                        show:true,
                        lineStyle: {
                            color: '#1B6B9D'
                        }
                    },

                }
            ],
            "series": [

                {
                    "name": "有效",
                    "type": "bar",
                    "stack": "总量",
                    label: {
                        show: true,
                        position: 'insideRight'
                    },
                    "barMaxWidth": 15,
                    "barGap": "10%",
                    "itemStyle": {
                        "normal": {
                            "barBorderRadius": 0,
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#0B7468'},                   //柱图渐变色
                                    // {offset: 1, color: 'white'},                 //柱图渐变色
                                    //柱图渐变色
                                ]
                            ),
                            "label": {
                                // "show": true,
                                "textStyle": {
                                    "color": "rgba(0,0,0,1)"
                                },
                                "position": "insideTop",
                                // formatter : function(p) {
                                //                         return p.value > 0 ? (p.value ): '';
                                //                     }
                            }
                        }
                    },
                    "data": [],
                },
                {
                    "name": "无效",
                    "type": "bar",
                    "stack": "总量",
                    "barMaxWidth": 15,
                    "barGap": "10%",
                    "itemStyle": {
                        "normal": {
                            "barBorderRadius": 0,
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#FFC000'},                   //柱图渐变色
                                    // {offset: 1, color: '#0B7468'},                 //柱图渐变色
                                    //柱图渐变色
                                ]
                            ),
                            "label": {
                                // "show": true,
                                "textStyle": {
                                    "color": "rgba(0,0,0,1)"
                                },
                                "position": "insideTop",
                                // formatter : function(p) {
                                //                         return p.value > 0 ? (p.value ): '';
                                //                     }
                            }
                        }
                    },
                    "data": [],
                },
                {
                    "name": "异常",
                    "type": "bar",
                    "stack": "总量",
                    "barMaxWidth": 15,
                    "barGap": "10%",
                    "itemStyle": {
                        "normal": {
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#E4177D'},                   //柱图渐变色
                                    // {offset: 1, color: '#FFC000'},                 //柱图渐变色
                                    //柱图渐变色
                                ]
                            ),
                            "barBorderRadius": 0,
                            "label": {
                                // "show": true,
                                // "position": "top",
                                // formatter : function(p) {
                                //                         return p.value > 0 ? ('▼'
                                //                                 + p.value + '')
                                //                                 : '';
                                //                     }
                            }
                        }
                    },
                    "data": []
                },

                ]

        })
        // var myChart = echarts.init(document.getElementById('stop'));
        //
        // myChart.setOption(self.option);
        // myChart.getZr().on('click', function(params) {
        //     //得到准确索引值，不会随着位置的变化而变化！
        //     alert(clickIndex);
        // })
        var state={a:''}
       
        self.onRendered = function(node) {
            
            // cube.getPageViewModelByNode($("#stop")).chart.on('legendselectchanged', function(params) {
            //     console.log(params)
            // })
            // console.log(cube.getPageViewModelByNode($("#stop")).chart)
            cube.getPageViewModelByNode($("#stop")).chart.getZr().on("click",function (param) {
                var pointInPixel = [param.offsetX, param.offsetY]
          if (cube.getPageViewModelByNode($("#stop")).chart.containPixel('grid', pointInPixel)) {
            var xIndex = cube.getPageViewModelByNode($("#stop")).chart.convertFromPixel({ seriesIndex: 0 }, [param.offsetX, param.offsetY])[0]
          }
           $(".pageA").css({'display':'none'});
           $(".pageB").css({'display':'block'});
            self.nameFirm("")
            self.Property("")
            self.annualAccess("")
            self.contactUnit("")
            self.a=ko.observable("");
           if (param.name=="北京"||xIndex==0){
               self.a(110000);
           }if (param.name=="天津"||xIndex==1){
               self.a(120000);
           }if (param.name=="河北"||xIndex==2){
               self.a(130000);
           }if (param.name=="冀北"||xIndex==3){
               self.a(130100);
           }if (param.name=="山西"||xIndex==4){
               self.a(140000);
           }if (param.name=="蒙东"||xIndex==20){
               self.a(150000);
           }if (param.name=="辽宁"||xIndex==17){
               self.a(210000);
           }if (param.name=="吉林"||xIndex==18){
               self.a(220000);
           }if(param.name=="黑龙江"||xIndex==19){
               self.a(230000);
           }if (param.name=="上海"||xIndex==6){
               self.a(310000);
           }if (param.name=="江苏"||xIndex==7){
               self.a(320000);
           }if (param.name=="浙江"||xIndex==8){
               self.a(330000);
           }if (param.name=="安徽"||xIndex==9){
               self.a(340000);
           }if (param.name=="福建"||xIndex==10){
               self.a(350000);
           }if (param.name=="山东"||xIndex==5){
               self.a(370000);
           }if (param.name=="河南"||xIndex==13){
               self.a(410000);
           }if (param.name=="江西"||xIndex==14){
               self.a(360000);
           }if (param.name=="湖北"||xIndex==11){
               self.a(420000);
           }if (param.name=="湖南"||xIndex==12){
               self.a(430000);
           }if (param.name=="重庆"||xIndex==16){
               self.a(500000);
           }if (param.name=="四川"||xIndex==15){
               self.a(510000);
           }if (param.name=="西藏"||xIndex==26){
               self.a(540000);
           }if (param.name=="陕西"||xIndex==21){
               self.a(610000);
           }if (param.name=="甘肃"||xIndex==22){
               self.a(620000);
           }if (param.name=="青海"||xIndex==23){
               self.a(630000);
           }if (param.name=="宁夏"||xIndex==24){
                self.a(640000);
           }if (param.name=="新疆"||xIndex==25){
                self.a(650000);
            }
            state.a=self.a()
            Object.defineProperty(state,'a', {
                set: function (newValue) {
                    // console.log(newValue);//成功触发方法打印出设置的值
                    s=true
                }
            });
           self.pageIndex2(1);
           s=false
           // console.log(param)
           var jsonStr = {
               'pageSize':10,
               'pageNo':self.pageIndex2()-1,
               "dataStr":{
                   "basicinfoContractor":self.nameFirm(),
                   "orgNature":self.Property(),
                   "datareportOrgId":self.a(),
                   "isEnableEnter":self.annualAccess(),
                   "datafillOrgId":self.contactUnit()
               }
           }
                self.province(self.a());
           // console.log(self.province())
           axios.post(cube.safetygatewayURL + "enterprisesAdmittance/getWorkSiteinfosList",jsonStr).then(function(res) {
               if (true) {

                   var risklist=res.data.resultValue.resultList
                   self.count2(res.data.resultValue.listSize);
                   self.pageTotalCount2(Math.ceil(res.data.resultValue.listSize / 10));
                   self.items(risklist);
               } else {
                   cube.indicate("数据加载失败");
               }

           })
   })
        }

        
        
        // document.onkeydown = function (e) { // 回车查询列表
        //  // 兼容FF和IE和Opera
        //     var theEvent = window.event || e;
        //     var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        //     if (code == 13) {
        //         self.inquire()
        //     }
        // }
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
            axios({
                url:cube.safetygatewayURL + "enterprisesAdmittance/getWorkSiteinfosList",
                method:'post',
                data:jsonStr,
                
                onUploadProgress: function (progressEvent) {
                //    cube.indicate("加载中，请稍后...");

                }
            }).then(function(res){
                if (res.data.successful==true) {
                    // cube.indicate("数据加载成功!");
                    var risklist=res.data.resultValue.resultList
                    self.count(res.data.resultValue.listSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 10));
                    // console.log(res.data.resultValue)
                    self.items(risklist);
                } else {
                    // cube.indicate("数据加载失败!");
                }

            }).catch(function(res){
                // cube.indicate("数据加载失败!");

            })
            // axios.post(cube.safetygatewayURL + "enterprisesAdmittance/getWorkSiteinfosList",jsonStr).then(function(res) {
            //     cube.showLoading(false)

            //     if (true) {
            //         cube.showLoading(false)
            //         var risklist=res.data.resultValue.resultList
            //         self.count(res.data.resultValue.listSize);
            //         self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 10));
            //         // console.log(res.data.resultValue)
            //         self.items(risklist);
            //     } else {
            //         cube.indicate("数据加载失败");
            //     }

            // })
        },
        self.inquire()
            //违章详情页面关闭
            self.refresh = function (e) {
                self.page('detail');
            }

        enterpriseInformation()
        AccessUnit()
        // unitList()
        dropDownBox()
        // getDatafillOrg()
        //全网准入单位情况汇总
        function enterpriseInformation () {
            axios.get(cube.safetygatewayURL + "enterprisesAdmittance/getEnterpriseCnts").then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
                    var IndustryUnit=res.data.resultValue.cyEnterpriseCnt.enterprise_cnt //产业单位
                    var OutsourcingUnit=res.data.resultValue.wbEnterpriseCnt.enterprise_cnt //外包单位
                    var fmqdEnterpriseCnt=res.data.resultValue.fmqdEnterpriseCnt.fmqd //本月负面清单数
                    var zrEnterpriseCnt=res.data.resultValue.zrEnterpriseCnt.enterprise_cnt //本月准入单位数
                    var allEnterpriseCnt=res.data.resultValue.allEnterpriseCnt.enterprise_cnt //单位总数
                    var hmdEnterpriseCnt=res.data.resultValue.hmdEnterpriseCnt.hmd //本月黑名单数

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
                if (res.data.successful) {
                    var data = res.data.resultValue;

                    var sf = [],
                        youxiao = [],wuxiao = [],yichang = [],code = [];
                    for(var i = 0;i<data.length;i++) {
                        sf.push(data[i]['datareport_org'])  //省份
                        youxiao.push(data[i]['enterprise_cnt_y'])
                        wuxiao.push(data[i]['enterprise_cnt_n'])
                        yichang.push(data[i]['enterprise_cnt_w'])
                        // code.push(data[i]['column_type_code'])
                    }
                    // console.log(code)
                    var option = self.option()
                    option.xAxis[0].data = sf;  //省份
                    option.series[0].data = youxiao;
                    option.series[1].data = wuxiao;
                    option.series[2].data = yichang;
                    // option.series[3].data = yichang;

                    self.option(option);
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
                    // console.log(res.data.resultValue)
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
                    // console.log(res.data.resultValue.voltageLevel)
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
        // function getDatafillOrg() {
        //     axios.get(cube.safetygatewayURL + "enterprisesAdmittance/getDatafillOrg?datafillOrgId=1").then(function(res) {
        //         if (true) {
        //             for (var i=0;i< res.data.resultValue.datafillOrg.length;i++){  //联系单位
        //                 self.contactUnitContent.push(res.data.resultValue.datafillOrg[i])
        //             }
        //         } else {
        //             cube.indicate("数据加载失败");
        //         }
        //
        //     })
        // }
        //二级菜单返回
        self. goreturn=function(){
            self.page('list')
        }
        self. goreturn2=function(){
            self.page('detail')
        }
        //现场作业单位详细信息查询
        self.goDetail= function (siteinfo_id,basicinfo_creditcode) {
            self.page('detail')
            axios.get(cube.safetygatewayURL +'enterprisesAdmittance/getSrpWorkSiteinfo/'+siteinfo_id).then(function(res) {
                if (true) {
                    // console.log(res.data.resultValue)
                    var data = res.data.resultValue[0];
                    self.enterpriseName(data['basicinfo_contractor']);//企业名称
                    self.establishedTime(data['basicinfo_establishdate']==null?'':data['basicinfo_establishdate'].substring(0,data['basicinfo_establishdate'].indexOf(' ')));//成立时间
                    // console.log(data['basicinfo_establishdate'].substring(0,data['basicinfo_establishdate'].indexOf(' ')))
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

                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })

        }
        
        // 本年度违章情况
        $('#shouqi1').on('click',function(){
            $('#instanceTable1').css('display','none')
            $('#zhankai1').css('display','block')
            $('#shouqi1').css('display','none')
            $('#weizhang').css('display','none')
            $('#line1').css('display','block')
            $('#fenye3').css('display','none')
        })
        $('#zhankai1').on('click',function(){
            $('#instanceTable1').css('display','block')
            $('#shouqi1').css('display','block')
            $('#zhankai1').css('display','none')
            if(wzstate==true){
            $('#weizhang').css('display','block')
            }else{
            $('#weizhang').css('display','none')
            }
            $('#line1').css('display','none')
            $('#fenye3').css('display','block')
        })
        // 企业准入注册信息收起 
        $('#shouqi2').on('click',function(){
            $('#instanceTable2').css('display','none')
            $('#zhankai2').css('display','block')
            $('#shouqi2').css('display','none')
            $('#xinxi').css('display','none')
            $('#line2').css('display','block')
        })
        $('#zhankai2').on('click',function(){
            $('#instanceTable2').css('display','block')
            $('#shouqi2').css('display','block')
            $('#zhankai2').css('display','none')
            if(xxstate==true){
            $('#xinxi').css('display','block')
            }else{
            $('#xinxi').css('display','none')
            }
            $('#line2').css('display','none')
        })
        // 准入情况
        $('#shouqi3').on('click',function(){
            $('#instanceTable3').css('display','none')
            $('#zhankai3').css('display','block')
            $('#shouqi3').css('display','none')
            $('#zhunru').css('display','none')
            $('#line3').css('display','block')
        })
        $('#zhankai3').on('click',function(){
            $('#instanceTable3').css('display','block')
            $('#shouqi3').css('display','block')
            $('#zhankai3').css('display','none')
            if(zrstate==true){
            $('#zhunru').css('display','block')

            }else{
            $('#zhunru').css('display','none')

            }
            $('#line3').css('display','none')
        })
        // 工作负责人
        $('#shouqi4').on('click',function(){
            $('#instanceTable4').css('display','none')
            $('#zhankai4').css('display','block')
            $('#shouqi4').css('display','none')
            $('#fuze').css('display','none')
            $('#line4').css('display','block')
            $('#fenye4').css('display','none')

        })
        $('#zhankai4').on('click',function(){
            $('#instanceTable4').css('display','block')
            $('#shouqi4').css('display','block')
            $('#zhankai4').css('display','none')
            if(fzstate==true){
                $('#fuze').css('display','block')

            }else{
            $('#fuze').css('display','none')

            }
            $('#line4').css('display','none')
            $('#fenye4').css('display','block')

        })
        // 作业人员
        $('#shouqi5').on('click',function(){
            $('#instanceTable5').css('display','none')
            $('#zhankai5').css('display','block')
            $('#shouqi5').css('display','none')
            $('#epibolyLeader').css('display','none')
            $('#line5').css('display','block')
            $('#fenye5').css('display','none')

        })
        $('#zhankai5').on('click',function(){
            $('#instanceTable5').css('display','block')
            $('#shouqi5').css('display','block')
            $('#zhankai5').css('display','none')
            if(elstate==true){
                $('#epibolyLeader').css('display','block')

            }else{
            $('#epibolyLeader').css('display','none')

            }
            $('#line5').css('display','none')
            $('#fenye5').css('display','block')

        })
        // 作业计划
        $('#shouqi6').on('click',function(){
            $('#instanceTable6').css('display','none')
            $('#zhankai6').css('display','block')
            $('#shouqi6').css('display','none')
            $('#epibolyplan').css('display','none')
            $('#line6').css('display','block')
            $('#fenye6').css('display','none')

        })
        $('#zhankai6').on('click',function(){
            $('#instanceTable6').css('display','block')
            $('#shouqi6').css('display','block')
            $('#zhankai6').css('display','none')
            if(elplan==true){
                $('#epibolyplan').css('display','block')

            }else{
            $('#epibolyplan').css('display','none')

            }
            $('#line6').css('display','none')
            $('#fenye6').css('display','block')

        })



        var zrstate=true
        //准入情况
        function AccessIs(pkValue,basicinfo_contractor,siteinfo_id) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkSiteinfoAll?siteinfoId=' +siteinfo_id+'&basicinfoContractor='+basicinfo_contractor).then(function(res) {
                if (true) {
                    var nulldata=[]
                    var data=res.data.resultValue
                    if( data.length==0){
                        self.admittanceContent(nulldata)
                        if($('#zhankai3').css('display')=='block'){
                            document.getElementById("zhunru").style.display="none";

                        }else{
                            document.getElementById("zhunru").style.display="block";

                        }


                    }else  if( data.length!==0){
                        document.getElementById("zhunru").style.display="none";
                        zrstate=false
                        self.admittanceContent(res.data.resultValue)
                    }
                    // console.log(res.data.resultValue)
                    // self.admittanceContent(res.data.resultValue)

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        var xxstate=true
        //企业准入信息
        function EnterpriseAccess(siteinfo_id,province_code,basicinfo_contractor) {

            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkSiteinfochange?basicinfoContractor=' +basicinfo_contractor+'&provinceCode='+province_code).then(function(res) {

                if (true) {
                     var nulldata=[]
                     var data=res.data.resultValue
                    if( data.length==0){
                        self.messageContent(nulldata)
                        if($('#zhankai2').css('display')=='block'){
                        document.getElementById("xinxi").style.display="none";

                        }else{
                            document.getElementById("xinxi").style.display="block";

                        }

                    }else  if( data.length!==0){
                        document.getElementById("xinxi").style.display="none";
                        xxstate=false
                        self.messageContent(res.data.resultValue)
                    }
                    // self.messageContent(data)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        var fzstate=true
        //工作负责人
        function JobLeader(province_code,siteinfo_id) {
            axios({
                url:cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker',
                method:'post',
                data:{
                    'dataStr':{
                        'provinceCode':province_code,
                        'siteinfoId':siteinfo_id,
                        'workerType':1
                    },
                    'pageSize':10,
                    'pageNo':self.pageIndex4()-1,
                }
            }).then(function(res){
            // axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?provinceCode=' +province_code+'&siteinfoId='+siteinfo_id+'&workerType=1').then(function(res) {
                if (res.data.successful==true) {
                    var nulldata=[]
                    var data=res.data.resultValue.resultList
                    self.pageTotalCount4(res.data.resultValue.pageCount)
                    self.count4(res.data.resultValue.listSize)
                    if( data.length==0){
                        self.JobLeaderContent(nulldata)
                        if($('#zhankai4').css('display')=='block'){
                            document.getElementById("fuze").style.display="none";

                        }else{
                            document.getElementById("fuze").style.display="block";

                        }


                    }else  if( data.length!==0){
                        document.getElementById("fuze").style.display="none";
                       fzstate=false
                        
                        self.JobLeaderContent(res.data.resultValue.resultList)
                    }
                        // self.JobLeaderContent(res.data.resultValue)
                } else {
                    cube.indicate("工作负责人数据加载失败");
                }

            })
        }
        var elstate=true
        //作业人员
        function subcontractingStaff(province_code,siteinfo_id) {
            axios({
                url:cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker',
                method:'post',
                data:{
                    'dataStr':{
                        'provinceCode':province_code,
                        'siteinfoId':siteinfo_id,
                        'workerType':2
                    },
                    'pageSize':10,
                    'pageNo':self.pageIndex5()-1,
                }
            }).then(function(res){
            // axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?provinceCode=' +province_code+'&siteinfoId='+siteinfo_id+'&workerType=2').then(function(res) {
                if (res.data.successful==true) {
                    var nulldata=[]
                    var data=res.data.resultValue.resultList
                    self.pageTotalCount5(res.data.resultValue.pageCount)
                    self.count5(res.data.resultValue.listSize)
                    if( data.length==0){
                        self.epibolyLeaderContent(nulldata)
                        if($('#zhankai5').css('display')=='block'){
                            document.getElementById("epibolyLeader").style.display="none";

                        }else{
                            document.getElementById("epibolyLeader").style.display="block";

                        }


                    }else  if( data.length!==0){
                        document.getElementById("epibolyLeader").style.display="none";
                        elstate=false
                        self.epibolyLeaderContent(res.data.resultValue.resultList)
                        
                    }

                        // self.epibolyLeaderContent(res.data.resultValue)
                } else {
                    cube.indicate("作业人员数据加载失败");
                }

            })
        }
        var elplan=true

        //作业计划
        function subcontractingplan(siteinfo_id) {
            axios({
                url:cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkePlanDay',
                method:'post',
                data:{
                    'dataStr':{
                        'siteinfoId':siteinfo_id,
                    },
                    'pageSize':10,
                    'pageNo':self.pageIndex6()-1,
                }
            }).then(function(res){
            // axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorksiteworker?provinceCode=' +province_code+'&siteinfoId='+siteinfo_id+'&workerType=2').then(function(res) {
                if (res.data.successful==true) {
                    var nulldata=[]
                    var data=res.data.resultValue.resultList
                    self.pageTotalCount6(res.data.resultValue.pageCount)
                    self.count6(res.data.resultValue.listSize)
                    if( data.length==0){
                        self.epibolyplanContent(nulldata)
                        if($('#zhankai6').css('display')=='block'){
                            document.getElementById("epibolyplan").style.display="none";

                        }else{
                            document.getElementById("epibolyplan").style.display="block";

                        }


                    }else  if( data.length!==0){
                        document.getElementById("epibolyplan").style.display="none";
                        elplan=false
                        self.epibolyplanContent(res.data.resultValue.resultList)
                        
                    }

                        // self.epibolyLeaderContent(res.data.resultValue)
                } else {
                    cube.indicate("作业计划数据加载失败");
                }

            })
        }
        //删除
        self.onSelectedItems = function (e) {
            selectedItems = e;
        };
        var selectedItems = [];
        var selectedDelIds = [];
        self.msgType = 'confirm';
        self.isShowDialog = false;
        self.Modaltitle = '事件处理工作评价列表';
        self.deleteTitle = '温馨提示';
        self.content = '删除后将不能恢复，是否确定删除?';
        self.confirmDelete = function (e) {
            var ids = []
                selectedDelIds.forEach(function (item) {
                ids.push(item['siteinfo_id'])
            });
            // console.log(ids)
            var ids1={
                ids:ids
            }
            axios.post(cube.safetygatewayURL + "enterprisesAdmittance/deleteEnterprise", ids1).then(function (res) {
                if (res.data.successful) {
                    enterpriseInformation()
                    unitList()
                    AccessUnit ()
                    axios.get(cube.safetygatewayURL + "enterprisesAdmittance/getEnterprisesAdmittanceInfoCnt/5").then(function(res) {
                        if (true) {
                            var earlyNum=res.data.resultValue
                            console.log(earlyNum)
                            var option= self.option();
                            var cityNames=[],powerGridSums=[];
                            for (var i in earlyNum){
                                cityNames.push(earlyNum[i].datareport_org)
                                powerGridSums.push(earlyNum[i].enterprise_cnt)
                                option.xAxis.data = cityNames;
                                option.series.data = powerGridSums;
                                self.option(option)
                            }
                        } else {
                            cube.indicate("数据加载失败");
                        }

                    })
                }



                // document.location.reload();//重新加载当前页面
            });
        };
        self.deleteInstance = function (e) {
            if (selectedItems.length === 0) {
                cube.indicate("请至少勾选一条数据");
                return;
            }
            selectedDelIds = [];
            for (var i = 0, len = selectedItems.length; i < len; i++) {
                selectedDelIds.push(selectedItems[i]);
            }
            self.isShowDialog(true)
        }
        cube.endViewModel(self, params);


    };

    return PageViewModel;
});
