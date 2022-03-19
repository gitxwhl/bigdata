define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
        var self = this;
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
        self.style ="background:#cccccc;";
        self.name = '';
        self.issueContent=ko.observableArray([{id:'',text:'全部'},]);//黑名单状态
        self.issue='';
        self.startTime = s2 + ' 00:00:00'//开始时间
        self.endTime = s2 + ' 23:59:59'//结束时间
        self.pageNo = 1;
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;
        self.onPageIndexChanged = function(p_pageIndex) {
            self.pageNo(p_pageIndex-1);
            blackItems();
        }
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
            blackItems()
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
         //负面清单数据
         self.negativeMeter = [
            {name: "siteinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center"},
            {name: "basicinfo_contractor", width:"10%", readOnly:true, caption: "企业名称" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                    
                },
                onClick: function (pkValue, cellValue) {
                    var list = self.negativeContent();
                    var basicinfo_creditcode = '';
                    for(var i = 0; i<list.length;i++){
                        if(pkValue == list[i]['siteinfo_id']){
                            basicinfo_creditcode = list[i]['basicinfo_creditcode']
                        }
                    }
                    commonPageBridge({ 
                        name: 'blacklist.information.main', 
                        params: {siteinfo_id:pkValue,basicinfo_creditcode:basicinfo_creditcode} 
                    });
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
        setTimeout(function(){
            blackItems();
        })
        
        function blackItems() {
            var jsonStr = {
                "pageSize":"10",
                "pageNo":self.pageNo()-1,
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
            axios.post(cube.safetygatewayURL + "negativeOrBlackList/getBlackInfosListOrNegativeInfosList",jsonStr).then(function(res) {
                
                if (res.data.successful) {
                    var list=res.data.resultValue.resultList
                    self.count(res.data.resultValue.listSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 10));
                    // console.log(res.data.resultValue)
                    self.negativeContent(list);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        };
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});