define(["RESTClient","axios"], function (RestClient,axios) {
    var PageViewModel = function (params) {
        var self = this;
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();

        self.page = 'list';
        self.Name=ko.observable("");//姓名

        self.time = ko.observable(s2 + ' 00:00:00');//预警计划时间
        // console.log(self.time())
        self.endTime=ko.observable(s2 + ' 23:59:59');//预警截至时间
        self.num=ko.observable("");//预警编号

        self.releaseUnit=ko.observable("");//发布单位
        // self.risktype='';//风险后果类型
        self.title=''//预警标题
        self.number=''//预警编号
        self.hierarchy=''//预警等级
        self.startTime=''//开始时间
        self.endtTime=''//结束时间
        self.monad=''//发布单位
        self.staff=''//发布人员
        self.status=''//状态

        //预警通知单
        self.informNum=''//预警编号
        self.informUnit=''//发布单位
        self.informData=''//预警日期
        self.informPowercut=''//停电设备
        self.informMatter=''//预警事由
        self.informTimeframe=''//预警时段
        self.informgrade=''//风险等级
        self.informbranch=''//主送部门
        self.informaccountability=''//责任单位
        self.informanalyze=''//风险分析
        self.informrequire=''//管控措施及要求
        self.informdepartment=''//签收部门
        self.informauthorizedStrength=''//编制
        self.annex=''//附件
        //预警反馈单
        self.feedbackNum=''//预警编号
        self.feedbackUnit=''//发布单位
        self.feedbackTime=''//反馈时间
        self.feedbackoddNum=''//预警单编号
        self.feedbackaudit=''//审核
        self.feedbackratify=''//批准
        self.feedbackTimeframe=''//预警时段
        self.feedbacksent=''//主送单位
        self.feedbackrequire=''//管控措施落实情况
        self.feedbackauthorizedStrength=''//编制
        //预警报告单
        self.reportNum=''//预警编号
        self.reportTime=''//报送日期
        self.reportunit=''//主送单位
        self.reportTimeframe=''//预警时段
        self.reportcondition=''//预警情况
        self.reportscope=''//涉及区域和影响范围
        self.reportsuggest=''//需政府协调的事项建议
        self.reportmonad=''//报告单位
        self.reportName=''//联系人
        self.reportPhone=''//联系电话
        //预警告知单
        self.notifysNum=''//预警编号
        self.notifysTime=''//报送日期
        self.notifysmonad=''//送达单位
        self.notifysMatter=''//预警事由
        self.notifysTimeframe=''//预警时段
        self.notifysanalyze=''//风险分析
        self.notifysmeasure=''//电网风险管控措施
        self.notifysunit=''//告知单位
        self.notifysName=''//联系人
        self.notifysPhone=''//联系电话
        //预警实施单
        self.exploitingsNum=''//预警编号
        self.exploitingsPowercut=''//停电设备
        self.exploitingsMatter=''//预警事由
        self.exploitingsclass=''//预警等级
        self.exploitingsTimeframe=''//预警时段
        self.exploitingsunit=''//主送部门
        self.exploitingsmonad=''//责任单位
        //预警解除
        self.relievesmessage=''//解除信息
        self.relievesyardman=''//调度员
        self.relievesphone=''//联系方式
        self.relievesTime=''//解除时间

        //预警等级
        self.GradeContent =[{id:'',text:'全部'},];
        self.Grade = ko.observable("")
        //电压等级
        self.voltageClassesContent =[{id:'',text:'全部'},];
        self.voltageClasses = ko.observable("")
        //停电事由
        self.voltageCapacity =[{id:'',text:'全部'},];
        self.selectVoltage=ko.observable("")
        //停电设备类型
        self.PowerEquipmenttype = [{id:'',text:'全部'},];
        self.PowerEquipment = ko.observable("")
        //预警状态
        self.stateContent = [{id:'',text:'全部'},];
        self.state = ko.observable("")
        //预警设备类型
        self.facilitytypeContent = [{id:'',text:'全部'},];
        self.facilitytype = ko.observable("")
        //风险后果类型
        self.risktypeContent = [{id:'',text:'全部'},];
        self.risktype = ''
        self.items=[];
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
        self.primaryKey = 'gridwarnnotice_id'
        self.pageSize = 1;
        self.id='';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount =1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                RiskList()
            }
        }
        self.diaHeight = document.documentElement.clientHeight *1+ 'px';

        self.columns = [
            {name: "title",  width: "20%", readOnly:true, caption: "标题" , editorType: "TextEditor",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    self.goDetail(pkValue);
                }},
            {name: "warnnum", width: "20%", readOnly:true, caption: "预警编号" , editorType: "TextEditor",align : "center",},
            {name: "warninglevel", width: "5%", caption: "预警等级" , editorType: "TextEditor",align : "center",},
            {name: "warnbegintime",width: "10%", caption: "预警计划开始时间" , editorType: "TextEditor",align : "center",},
            {name: "warnendtime", width: "10%", caption: "预警计划解除时间" , editorType: "TextEditor",align : "center",},
            {name: "publish_org", width: "10%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
            {name: "publish_staff",width: "5%",caption: "发布人员" , editorType: "TextEditor",align : "center",},
            {name: "warnstatus", width: "5%", caption: "状态" , editorType: "TextEditor",align : "center",},
            {name: "work_plan_code_day_cnt", width: "5%", caption: "关联作业数" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (id,cellValue) {
                   console.log(cellValue)
                    if (cellValue!==0){


                    var arr = self.items(), item = [];
                        var title = '作业计划信息'
                        for (var i = 0; i < arr.length; i++) {
                            if (arr[i]['gridwarnnotice_id'] == id) {
                                item = arr[i];
                            }
                        }
                        cube.showDialog({
                            title: title,
                            width: "90%",
                            height: self.diaHeight,
                            submitFormOnConfirm: false,
                            isShowCloseBtn: false, // 是否显示取消按钮
                            isShowConfirmBtn: false, // 是否显示保存按钮
                            templateOptions: {
                                name: 'operationRisk.detail.main',
                                params: {item: item}
                            },
                        });

                    }else{
                        cube.indicate("未有相关作业计划");
                    }
                }
                },
        ];
        //标题查看
        self.goDetail = function (gridwarnnotice_id) {
            console.log(gridwarnnotice_id)
            $(".requisition").css("background", "#1b6b9d");
            axios.get(cube.gatewayURL +'riskInfos/gridWarnContent?str=' +gridwarnnotice_id).then(function(res) {
                if (true) {
                    var requisition=res.data.resultValue.srpRiskGridwarnnotice[0] //预警通知单
                    var feedback=res.data.resultValue.srpRiskGridwarnfeedback[0]//反馈单
                    var report=res.data.resultValue.srpRiskGridwarnreport[0]//报告单
                    var notifys=res.data.resultValue.srpRiskGridwarninform[0]//告知单
                    var relieve=res.data.resultValue.srpRiskGridwarnrelinfo[0]//预警解除
                    self.title(requisition.title)
                    self.number(requisition.warnnum)
                    self.hierarchy(requisition.warninglevel)
                    self.startTime(requisition.warnbegintime)
                    self.endtTime(requisition.warnendtime)
                    self.monad(requisition.publish_org)
                    self.staff(requisition.publish_staff)
                    self.status(requisition.warnstatus)
                    //预警实施单
                    self.exploitingsNum(requisition.warnnum)//预警编号
                    self.exploitingsPowercut(requisition.riskdev)//停电设备
                    self.exploitingsMatter(requisition.warncontents)//预警事由
                    self.exploitingsclass(requisition.warninglevel)//预警等级
                    self.exploitingsTimeframe(requisition.warnbegintime+'至'+requisition.warnendtime)//预警时段
                    // self.exploitingsunit(feedback.mainsend)//主送部门
                    // self.exploitingsmonad(report.up_dept)//责任单位

                    self.informNum(requisition.warnnum)//预警编号
                    self.informUnit(requisition.publish_org);//发布单位
                    self.informData(requisition.warnbegintime)//预警日期
                    self.informPowercut(requisition.riskdev)//停电设备
                    self.informMatter(requisition.warncontents)//预警事由
                    self.informTimeframe(requisition.warnbegintime+'至'+requisition.warnendtime)//预警时段
                    self.informgrade(requisition.warninglevel)//风险等级
                    self.informbranch(requisition.mainsend)//主送部门
                    self.informaccountability(requisition.responsible_org)//责任单位
                    self.informanalyze(requisition.riskanalysis)//风险分析
                    self.informrequire(requisition.riskmeasure)//管控措施及要求
                    self.informdepartment(requisition.countersign_dep)//签收部门
                    self.informauthorizedStrength(requisition.editor)//编制
                    self.annex(requisition.annex)//附件

                    //预警反馈单
                    self.feedbackNum(feedback.warnnum)//预警编号
                    self.feedbackUnit(requisition.feedback_org)//反馈单位
                    self.feedbackTime(feedback.feedback_time)//反馈日期
                    self.feedbackoddNum(feedback.feedbacknum)//预警单编号
                    self.feedbackaudit(feedback.auditor)//审核
                    self.feedbackratify(feedback.appro_staff)//批准
                    self.feedbackTimeframe(feedback.warnbegintime+'至'+feedback.warnendtime)//预警时段
                    self.feedbacksent(feedback.mainsend)//主送单位
                    self.feedbackrequire(feedback.implementation)//管控措施落实情况
                    self.feedbackauthorizedStrength(feedback.editor)//编制
                    self.annex(feedback.annex)//附件
                    //预警报告单
                    self.reportNum(report.warnreportnum)//预警编号
                    self.reportTime(report.submit_time)//报送日期
                    self.reportunit(report.mainsend)//主送单位
                    self.reportTimeframe(report.warnbegintime+'至'+report.warnendtime)//预警时段
                    self.reportcondition(report.warncontents)//预警情况
                    self.reportscope(report.area)//涉及区域和影响范围
                    self.reportsuggest(report.forgovercoordiation)//需政府协调的事项建议
                    self.reportmonad(report.up_dept)//报告单位
                    self.reportName(report.phoneuser)//联系人
                    self.reportPhone(report.phonenumber)//联系电话
                    self.annex(report.annex)//附件

                    //预警告知单
                    self.notifysNum(notifys.informnum)//预警编号
                    self.notifysTime(notifys.submit_time)//报送日期
                    self.notifysmonad(notifys.delivery_dept)//送达单位
                    self.notifysMatter(notifys.warncontents)//预警事由
                    self.notifysTimeframe(notifys.warnbegintime+'至'+notifys.warnendtime)//预警时段
                    self.notifysanalyze(notifys.riskanalysis)//风险分析
                    self.notifysmeasure(notifys.riskmeasure)//电网风险管控措施
                    self.notifysunit(notifys.inform_dep)//告知单位
                    self.notifysName(notifys.phoneuser)//联系人
                    self.notifysPhone(notifys.phonenumber)//联系电话
                    self.annex(notifys.annex)//附件
                    //预警解除
                    self.relievesmessage(relieve.relinfonum)//解除信息
                    self.relievesyardman(relieve.warnremove_staff)//调度员
                    self.relievesphone(notifys.phonenumber)//联系方式
                    self.relievesTime(relieve.removedate)//解除时间
                    // console.log(res.data.resultValue.srpRiskGridwarnfeedback[0])
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            self.page('edit')
        }
        RiskList()
        dropDownbox()
        self.goBack = function() {
            self.page('list')
        }
        self.search = function() {
            self.pageIndex(1);
            RiskList()
        }
        function RiskList(searchParams) {
            var params = {
                "pageSize":	20,
                "pageNo":self.pageIndex()-1,
                "dataStr": {
                    "title":self.Name(),
                    "warningLevel":self.Grade(), //预警等级
                    "voltageLevel":self.voltageClasses(),//电压等级
                    "warnbegintime":self.time(),
                    "warnendtime":self.endTime(),
                    "failureReason":self.selectVoltage(),//停电事由
                    "warnnum":self.num(),//预警编号
                    "riskdev":self.PowerEquipment(),//停电设备
                    "warnstatus":self.state(),//预警状态
                    "warndevtype":self.facilitytype(),//停电设备类型
                    "datareport_org":self.releaseUnit()//所属省份
                }
            }

            axios.post(cube.gatewayURL + "riskInfos/allRiskWarnInfosList",params).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue.resultList
                    console.log(risklist)
                    self.count(res.data.resultValue.listSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 20));
                    self.items(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        function dropDownbox(){
            axios.get(cube.gatewayURL + "riskInfos/getSrpRiskSysTab").then(function(res) {
                if (true) {
                    var list=res.data.resultValue.warningLevel
                        // console.log(list)
                    //预警等级
                    for (var i=0;i< res.data.resultValue.warningLevel.length;i++){
                        self.GradeContent.push(res.data.resultValue.warningLevel[i])
                    }
                     //电压等级
                    for (var i=0;i< res.data.resultValue.voltageLevel.length;i++){
                        self.voltageClassesContent.push(res.data.resultValue.voltageLevel[i])
                    }
                    //停电事由
                    for (var i=0;i< res.data.resultValue.failureReason.length;i++){
                        self.voltageCapacity.push(res.data.resultValue.failureReason[i])
                    }
                    //停电设备类型
                    for (var i=0;i< res.data.resultValue.riskDev.length;i++){
                        self.PowerEquipmenttype.push(res.data.resultValue.riskDev[i])
                    }
                    //预警状态
                    for (var i=0;i< res.data.resultValue.warnStatus.length;i++){
                        self.stateContent.push(res.data.resultValue.warnStatus[i])
                    }
                    // 预警设备类型
                    for (var i=0;i< res.data.resultValue.warndevType.length;i++){
                        self.facilitytypeContent.push(res.data.resultValue.warndevType[i])
                    }

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //预警通知单
        self.requisition_click=function(){
            $(".requisition").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".report").css("background", "");
            $(".notify").css("background", "");
            $(".relieve").css("background", "");
            $(".exploiting").css("background", "");
            $(".requisitions").show();
            $(".feedbacks").hide();
            $(".reports").hide();
            $(".notifys").hide();
            $(".exploitings").hide();
            $(".relieves").hide();
        }
        //预警反馈单
        self.feedback_click=function(){
            $(".requisition").css("background", "");
            $(".report").css("background", "");
            $(".notify").css("background", "");
            $(".relieve").css("background", "");
            $(".exploiting").css("background", "");
            $(".feedback").css("background", "#1b6b9d");
            $(".feedbacks").show();
            $(".requisitions").hide();
            $(".reports").hide();
            $(".notifys").hide();
            $(".exploitings").hide();
            $(".relieves").hide();

        }
        //预警报告单
        self.report_click=function(){
            $(".report").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".requisition").css("background", "");
            $(".notify").css("background", "");
            $(".relieve").css("background", "");
            $(".exploiting").css("background", "");
            $(".reports").show();
            $(".requisitions").hide();
            $(".feedbacks").hide();
            $(".notifys").hide();
            $(".exploitings").hide();
            $(".relieves").hide();
        }
        //预警告知单
        self.notify_click=function(){
            $(".notify").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".requisition").css("background", "");
            $(".report").css("background", "");
            $(".exploiting").css("background", "");
            $(".relieve").css("background", "");
            $(".notifys").show();
            $(".requisitions").hide();
            $(".feedbacks").hide();
            $(".reports").hide();
            $(".exploitings").hide();
            $(".relieves").hide();
        }
        //预警实施单
        self.exploiting_click=function(){
            $(".exploiting").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".requisition").css("background", "");
            $(".report").css("background", "");
            $(".notify").css("background", "");
            $(".relieve").css("background", "");
            $(".exploitings").show();
            $(".requisitions").hide();
            $(".feedbacks").hide();
            $(".reports").hide();
            $(".notifys").hide();
            $(".relieves").hide();
        }
        //预警解除
        self.relieve_click=function(){
            $(".relieve").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".requisition").css("background", "");
            $(".report").css("background", "");
            $(".notify").css("background", "");
            $(".exploiting").css("background", "");
            $(".relieves").show();
            $(".requisitions").hide();
            $(".feedbacks").hide();
            $(".reports").hide();
            $(".notifys").hide();
            $(".exploitings").hide();
        }
        //电网
        self.power=function () {
            $(".dianwang").attr("src","../resources/resources/images/u1863.png");
            $(".crew").attr("src","../resources/resources/images/u1839.png");
            $(".devicename").attr("src","../resources/resources/images/u1843.png");
            $(".atmosphere").attr("src","../resources/resources/images/u1847.png");
            $(".advocate").attr("src","../resources/resources/images/u1849.png");
            $(".powers").show();
            $(".personnels").hide();
            $(".facilitys").hide();
            $(".surroundings").hide();
            $(".useNames").hide();
        }
        //人员
        self.personnel=function () {
            $(".crew").attr("src","../resources/resources/images/u1901.png");
            $(".dianwang").attr("src","../resources/resources/images/u1883.png");
            $(".devicename").attr("src","../resources/resources/images/u1843.png");
            $(".atmosphere").attr("src","../resources/resources/images/u1847.png");
            $(".advocate").attr("src","../resources/resources/images/u1849.png");
            $(".personnels").show();
            $(".powers").hide();
            $(".facilitys").hide();
            $(".surroundings").hide();
            $(".useNames").hide();
        }
        //设备
        self.facility=function () {
            $(".devicename").attr("src","../resources/resources/images/u1933.png");
            $(".crew").attr("src","../resources/resources/images/u1839.png");
            $(".dianwang").attr("src","../resources/resources/images/u1883.png");
            $(".atmosphere").attr("src","../resources/resources/images/u1847.png");
            // $(".advocate").attr("src","../resources/resources/images/u1849.png");
            $(".facilitys").show();
            $(".powers").hide();
            $(".personnels").hide();
            $(".surroundings").hide();
            $(".useNames").hide();
        }
        //环境
        self.surrounding=function () {
            $(".atmosphere").attr("src","../resources/resources/images/u1965.png");
            $(".devicename").attr("src","../resources/resources/images/u1843.png");
            $(".crew").attr("src","../resources/resources/images/u1839.png");
            $(".dianwang").attr("src","../resources/resources/images/u1883.png");
            $(".advocate").attr("src","../resources/resources/images/u1849.png");
            $(".surroundings").show();
            $(".powers").hide();
            $(".personnels").hide();
            $(".facilitys").hide();
            $(".useNames").hide();
        }
        //用户
        self.useName=function () {
            $(".advocate").attr("src","../resources/resources/images/u2001.png");
            $(".atmosphere").attr("src","../resources/resources/images/u1847.png");
            $(".devicename").attr("src","../resources/resources/images/u1843.png");
            $(".crew").attr("src","../resources/resources/images/u1839.png");
            $(".dianwang").attr("src","../resources/resources/images/u1883.png");
            $(".useNames").show();
            $(".personnels").hide();
            $(".facilitys").hide();
            $(".surroundings").hide();
            $(".powers").hide();
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});