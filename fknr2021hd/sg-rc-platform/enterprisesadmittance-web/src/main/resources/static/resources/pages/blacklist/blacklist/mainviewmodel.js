define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
        var self = this;
        var day2 = new Date();
        var date = new Date();
        var year = date.getFullYear() + "";
        var month = (date.getMonth() + 1) + "";
        // 本月第一天日期
        var begin = year + "-" + month + "-01 00:00:00"


        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
        self.pageSize = 10;
        self.name = '';
        self.startTime = begin//开始时间
        self.endTime = s2 + ' 23:59:59'//结束时间
        self.style ="background:#cccccc;";
        //所属省份
        self.provinceContent =[{id:'',text:'全部'},];
        //黑名单等级
        self.voltageCapacity = [
            { id: '', text: '全部' },
            { id: '01', text: 'I级' },
            { id: '02', text: 'II级' },
            { id: '03', text: 'III级' },
        ];
        self.selectVoltage = '0'
        self.issueContent=ko.observableArray(
            [{id:'',text:'全部'},]
        );//黑名单状态
        self.hmdContent=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'未解除'},
            {id:'02',text:'已解除'}
        ]);//省份
        self.issue='';
        self.hmd='';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;
        self.pageNo = ko.observable(0);
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
            self.pageNo(0)
            self.pageIndex(1)
            blackItems()
        }
        self.reset=function(){
            self.name('') //企业名称
            self.selectVoltage('');//黑名单等级
            self.issue('');//省份
            self.hmd('');//黑名单状态
            self.startTime('');//发布开始时间
            self.endTime('');//发布结束时间

        }
        self.pageNo = ko.observable(0);
        self.blacklistContent=[]
        var hmddj = {
            '01': 'Ⅰ级',
            '02': 'Ⅱ级',
            '03': 'Ⅲ级',
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
        //黑名单数据  siteinfo_id
        self.blacklistMeter = [
            {name: "siteinfo_id", width:"9%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {
                name: "basicinfo_contractor", width: "15%", readOnly: true, caption: "企业名称", editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                    element.style.textDecoration = "underline";
                    element.style.cursor = "pointer";

                },
                onClick: function (pkValue, cellValue) {
                    var list = self.blacklistContent();
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
            {name: "basicinfo_creditcode", width:"9%", readOnly:true, caption: "组织机构代码" , editorType: "TextEditor",align : "center"},
            {name: "datareport_org_id", width:"8%", caption: "省份" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    var data = self.issueContent();
                        for(var i = 0; i<data.length; i++){
                            if(cellValue == data[i]['id']){
                                return data[i]['text']
                            }
                        }
                }
            },
            {name: "blacklist_level", width:"8%", caption: "黑名单等级" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    return hmddj[cellValue]
                }
            },
            {name: "inclusion_condition", width:"20%", readOnly:true, caption: "纳入原因" , editorType: "TextEditor",align : "center"},
            {name: "report_date", width:"14%", readOnly:true, caption: "发布时间" , editorType: "TextEditor",align : "center"},
            {name: "isenableenter", width:"16%", caption: "黑名单状态" , editorType: "TextEditor",align : "center"},

        ];
        self.diaHeight = document.documentElement.clientHeight * 0.6 + 'px';
        self.customOperations = [
			{
				caption : "查看",
				click: function(item) {
                    cube.showDialog({
                        title : '黑名单详情',
                        width:"70%",
                        height:self.diaHeight,
                        submitFormOnConfirm : false,
                        isShowCloseBtn:false, // 是否显示取消按钮
                        isShowConfirmBtn:false, // 是否显示保存按钮
                        templateOptions : {
                            name : 'blacklist.detail.main',
                            params : {item:item,id:0}
                        }
                    });
				}
			}
        ];

        setTimeout(function(){
            blackItems();
        })

        function blackItems() {
            var jsonStr = {
                "pageSize":"10",
                "pageNo":self.pageNo(),
                "selType":'0',  //-----0代表黑名单查询；1代表负面清单查询
                "dataStr":{
                    "siteinfoName":self.name(),  //---企业名称：
                    "blacklistLevel":self.selectVoltage(),    //---黑名单等级：
                    "datareportOrgId":self.issue(), //-----省份：
                    "isEnableEnter":self.hmd(),   //----黑名单状态：
                    "beginTim":self.startTime(),
                    "endTim":self.endTime()
                }
            }
            axios.post(cube.safetygatewayURL + "negativeOrBlackList/getBlackInfosListOrNegativeInfosList",jsonStr).then(function(res) {

                if (res.data.successful) {
                    var list=res.data.resultValue.resultList
                    // console.log(list)
                    self.count(res.data.resultValue.listSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 10));
                    // console.log(res.data.resultValue)
                    self.blacklistContent(list);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        };
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
                ids.push(item['blacklistorg_id'])
            });
            // console.log(ids)
            var ids1={
                "selType":0,
                ids:ids
            }
            axios.post(cube.safetygatewayURL + "negativeOrBlackList/deleteBlacklistInventory", ids1).then(function (res) {
                blackItems()
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
