define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
        self.titleName=ko.observable("");//查询姓名
        // self.time = ko.observable(s2 + ' 00:00:00');//开始时间
        // self.endTime=ko.observable(s2 + ' 23:59:59');//结束时间
        self.time = ko.observable('');//开始时间
        self.endTime=ko.observable('');//结束时间
        self.releaseUnit =[
            {id:'',text:'全部'},
            {id:'01',text:'成功'},
            {id:'02',text:'失败'},
        ];
        self.Unit = ko.observable("")


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
        // self.primaryKey='id';
        self.pageSize = 1;
        // self.id='';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='2';
        self.pageTotalCount = '1';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount =1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
            }
        }
        self.customOperations = [
            {
                caption : "日志查看",
                click: function(items) {
                    cube.showDialog({
                        title : '日志查看',
                        width:"900px",
                        height:'500px',
                        submitFormOnConfirm : false,
                        isShowCloseBtn:false, // 是否显示取消按钮
                        isShowConfirmBtn:false, // 是否显示保存按钮
                        templateOptions : {
                            name : 'dataLoading.detail.main',
                            params : {items:items}
                        }
                    });
                }
            },

        ]
        self.columns = [
            // {name: "id",  readOnly:true, caption: "" , editorType: "TextEditor",align : "center",},
            {name: "JOBNAME",  width: "10%", readOnly:true, caption: "任务名称" ,align : "center", editorType: "TextEditor",
                // renderCell: function (cellValue, element, dictValue) {
                //     element.style.color = "#0ec4da";
                // },
                onClick: function (items) {
                    // self.goDetail();
                }
            },
            {name: "SERVER_IP", width: "10%", caption: "服务器IP" , editorType: "TextEditor",align : "center",},
            {name: "DATA_TYPE",width: "10%", caption: "数据类型" , editorType: "TextEditor",align : "center",},
            {name: "PROCESS_STATE", width: "10%", caption: "进程状态" , editorType: "TextEditor",align : "center",},
            {name: "OPERATION_INFO", width: "10%", caption: "运行信息" , editorType: "TextEditor",align : "center",},
            {name: "EXECUTION_DATETIME", width: "10%", caption: "执行时间" , editorType: "TextEditor",align : "center",},
            {name: "EXECUTION_TIME", width: "10%", caption: "执行时长" , editorType: "TextEditor",align : "center",},
        ];
        self.items=[]
        //查询
        self.search = function() {
            RiskList()
        }
        RiskList()
        function RiskList(searchParams) {
            var params = {
                "page":	self.pageIndex(),
                "size":10,
                "params": {
                    "jobName":self.titleName(),
                    "executionDateTime":self.time(),
                    "endDateTime":self.endTime(),
                    "processState":self.Unit()
                }
            }

            axios.post(cube.gatewayURL2 + "riskDataInfosController/manageList",params).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue.content
                    console.log(risklist)
                    self.count(res.data.resultValue.elementTotalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.elementTotalSize / 20));
                    self.items(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});