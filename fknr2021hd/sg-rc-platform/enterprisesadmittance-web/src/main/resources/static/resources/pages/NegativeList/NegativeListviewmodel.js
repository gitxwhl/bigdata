define(["RESTClient",'echarts',"axios"], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        $('.content-bg').scrollTop(0);
        self.moduleOptions = ko.observable({ name: 'blacklist.blacklist.main',params: {} });
        self.fmqdIndex = 0;
        self.page = 'list';
        self.params = ko.observable({});

        var day2 = new Date();
        var date = new Date();
        var year = date.getFullYear() + "";
        var month = (date.getMonth() + 1) + "";
        // 本月第一天日期
        var begin = year + "-" + month + "-01 00:00:00"

        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
        self.TotalNegativeList=ko.observable('');;//负面清单总数
        self.NegativeListNumber=ko.observable('');//本年纳入负面清单数
        self.ClearNegativeList=ko.observable('');//本月纳入负面清单数

        self.name = ko.observable('');
        self.issueContent=ko.observableArray([{id:'',text:'全部'},]);
        self.issue=ko.observable('');
        self.startTime =ko.observable(begin); //开始时间
        self.endTime =ko.observable(s2 + ' 23:59:59'); //结束时间

        axios.get(cube.safetygatewayURL + 'negativeOrBlackList/getNegativeCntInfos').then(function(res){
            if(res.data.successful){
                var data = res.data.resultValue;
                // console.log(data)
                self.TotalNegativeList(data.negativeCntInfos.negativelist_cnt);
                self.NegativeListNumber(data.negativeCntInfosByAreaYear.release_cnt_year);//本年纳入负面清单数
                self.ClearNegativeList(data.negativeCntInfosByAreaMonth.release_cnt_month);//本月纳入负面清单数

            }
        })
        self.query = function(){
            if(self.startTime()>self.endTime()){
                cube.indicate({
                    msgType:'error',
                    content:'开始时间不能大于结束时间！！',
                    isShowCancelBtn:false,
                    showTime: '2000'
                });
                return
            }
            self.pageNo(0)
            self.pageIndex(1)
            Negativelist()
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
                ids.push(item['negativelistorg_id'])
            });
            // console.log(ids)
            var ids1={
                "selType":1,
                ids:ids
            }
            axios.post(cube.safetygatewayURL + "negativeOrBlackList/deleteBlacklistInventory", ids1).then(function (res) {
                Negativelist()
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
        //重置
        self.reset=function(){
            self.name('');//企业名称
            self.issue('');//省份
            self.startTime('');//发布开始时间
            self.endTime('');//发布结束时间

        }
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count =ko.observable('');
        self.pageTotalCount =ko.observable('');
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount = 10;
        self.pageNo= ko.observable(0)
        self.onPageIndexChanged = function(p_pageIndex) {
            self.pageNo(p_pageIndex-1);
            Negativelist()
        }
        dropDownBox();
        //下拉框选项
        function dropDownBox() {
            axios.get(cube.safetygatewayURL + "enterprisesAdmittance/getSrpRiskSysTab").then(function(res) {
                if (true) {
                    for (var i=0;i< res.data.resultValue.voltageLevel.length;i++){  //所属省份
                        self.issueContent.push(res.data.resultValue.voltageLevel[i])
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }

        self.width = '100%';
        self.height = '310px';
        self.heightMap = '520px';
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


        self.primaryKeys = 'siteinfo_id'
        var siteinfo_id='';
        var province_code='';
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
        
        
        var selectedItems = []
        self.onSelectedItems = function(e) {
            selectedItems = e;
        };
        //负面清单数据
        self.negativeMeter = [
            // {name: "siteinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center"},
            {name: "org_name", width:"10%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                    element.style.textDecoration = "underline";
                    element.style.cursor = "pointer";

                },
                onClick: function (pkValue, cellValue) {
                    console.log(pkValue)
                    var list = self.negativeContent();
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
            {name: "basicinfo_creditcode", width:"10%", readOnly:true, caption: "组织机构代码" , editorType: "TextEditor",align : "center"},
            {name: "datareport_org_id", width:"10%", caption: "省份" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    var data = self.issueContent();
                    for(var i = 0; i<data.length; i++){
                        if(cellValue == data[i]['id']){
                            return data[i]['text']
                        }
                    }
                }
            },
            {name: "datafill_org", width:"10%", caption: "发起单位" , editorType: "TextEditor",align : "center"},
            {name: "inclusion_condition", width:"30%", caption: "纳入原因" , editorType: "TextEditor",align : "center"},
            {name: "report_date", width:"10%", readOnly:true, caption: "发布时间" , editorType: "TextEditor",align : "center"},
            // {name: "IllegalApprovalIdea", width:"90px", caption: "处理情况" , editorType: "TextEditor"},
        ];
        self.diaHeight = document.documentElement.clientHeight * 0.6 + 'px';
        self.customOperations = [
            {
                caption : "查看",
                click: function(item) {
                    cube.showDialog({
                        title : '负面清单详情',
                        width:"70%",
                        height:self.diaHeight,
                        submitFormOnConfirm : false,
                        isShowCloseBtn:false, // 是否显示取消按钮
                        isShowConfirmBtn:false, // 是否显示保存按钮
                        templateOptions : {
                            name : 'blacklist.detail.main',
                            params : {item:item,id:1}
                        }
                    });
                }
            }
        ];
        self.negativeContent=[]
        //负面清单列表查询
        Negativelist()
        function Negativelist() {
            var jsonStr = {
                "pageSize":"10",
                "pageNo":self.pageNo(),
                "selType":'1',  //-----0代表黑名单查询；1代表负面清单查询
                "dataStr":{
                    "siteinfoName":self.name(),  //---企业名称：
                    "blacklistLevel":"",    //---黑名单等级：
                    "datareportOrgId":self.issue(), //-----省份：
                    "isEnableEnter":"",   //----黑名单状态：
                    "beginTim":self.startTime(),
                    "endTim":self.endTime()
                }
            }
            // cube.indicate("加载中，请稍后...");
            axios.post(cube.safetygatewayURL + "negativeOrBlackList/getBlackInfosListOrNegativeInfosList",jsonStr).then(function(res) {

                if (res.data.successful) {
                    // cube.indicate("数据加载成功!");

                    var list=res.data.resultValue.resultList
                    // console.log(list)
                    self.count(res.data.resultValue.listSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 10));
                    // console.log(res.data.resultValue)
                    self.negativeContent(list);
                } else {
                    cube.indicate("数据加载失败");
                }

            }).catch(function(res){
                cube.indicate("数据加载失败!");

            })
        };
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
        
        self.IllegalApproval = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "matter", width:"10%", readOnly:true, caption: "事项" , editorType: "TextEditor",align : "center",},
            {name: "IllegalApprovalPersonnel", width:"10%", readOnly:true, caption: "违章审核人员" , editorType: "TextEditor",align : "center"},
            {name: "IllegalApprovalIdea", width:"10%", caption: "违章审核意见" , editorType: "TextEditor",align : "center"},
            {name: "IllegalApprovalTime", width:"10%", caption: "违章审核时间" , editorType: "TextEditor",align : "center"},

        ];
        self.IllegalapprovalContent=[  ]






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
        //返回安全准入管理
        self.goBack = function() {
            self.page('list')
        }
         //返回安全准入管理
         self.goBack1 = function() {
            self.page('detail')
        }
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
            {name: "siteworkerinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
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
         //现场作业单位详细信息查询
         self.goDetail= function (siteinfo_id,basicinfo_creditcode) {
            self.page('detail')
            axios.get(cube.safetygatewayURL +'enterprisesAdmittance/getSrpWorkSiteinfo/'+siteinfo_id).then(function(res) {
                if (res.data.resultValue.length>0) {
                    console.log(res.data.resultValue)
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
                    // cube.indicate("数据加载失败");
                }

            })

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
                    }

                        
                } else {
                    cube.indicate("数据加载失败");
                }

            })

        }
        //违章详情查看
        self.goViolationDetails = function (siteinfo_id,xsz) {
            // console.log(siteinfo_id)
            self.page('ViolationDetails')
            getSrpWorkViolationfile2(siteinfo_id,xsz)
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
        //二级菜单返回
        self. goreturn=function(){
            self.page('list')
        }
        self. goreturn2=function(){
            self.page('detail')
        }
        // self.modify = function() {
        //     if (selectedItems.length !== 1) {
        //         cube.indicate("请勾选一条数据");
        //         return;
        //     }
        //     self.page('BlacklistDetails')
        //
        // },
            //黑名单企业查看
            // self.goenterpriseInformation = function (id) {
            //     self.page('detail')
            // }
        //黑名单企业详细信息查看
        // self.goViolationDetails = function (id) {
        //     self.page('ViolationDetails')
        // }
        //违章详情页面关闭
        // self.refresh = function (e) {
        //     self.page('detail');
        // }
        //黑名单详情页面关闭
        // self.refreshClose = function (e) {
        //     self.page('list');
        // }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});
