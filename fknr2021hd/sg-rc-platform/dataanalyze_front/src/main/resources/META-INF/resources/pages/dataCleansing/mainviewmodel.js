define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();
        self.titleName=ko.observable('')//查询姓名
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
            {name: "PROCESS_STATE", width: "10%", caption: "进程" , editorType: "TextEditor",align : "center",},
            {name: "OPERATION_INFO", width: "10%", caption: "运行信息" , editorType: "TextEditor",align : "center",},
            {name: "EXECUTION_DATETIME", width: "10%", caption: "执行时间" , editorType: "TextEditor",align : "center",},
            {name: "EXECUTION_TIME", width: "10%", caption: "执行时长" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    return cellValue + 's'
                }
            },
        ];
        self.items=[]
        //输入框onchange事件
        self.iptChange = function() {
            var ipt1 = document.querySelector("#ipt1")
            if(ipt1.value.length > 100) {
                ipt1.value = ipt1.value.substring(0,100)
                cube.indicate("输入框内容长度不能超过100！");
            }
        }
        //查询
        self.search = function() {
            self.pageIndex(1)
            RiskList()
        }
        RiskList()
        function RiskList(searchParams) {
            // 输入框内容校验
            var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'()?？·（）【】’#、]")
            if(iptRep.test(self.titleName().trim())) {
                cube.indicate("输入框含违法字符，请重新输入！");
                return;
            }
            // 输入框空格校验
            if(self.titleName().trim().length !== self.titleName().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            // 输入框长度校验
            if(self.titleName().trim().length > 100) {
                cube.indicate("输入框内容长度不能超过100！");
                return;
            }
            // 时间空格校验
            // if(self.time().trim().length !== self.time().length) {
            //     cube.indicate("时间选择框内容不能全空格或首尾有空格！");
            //     return;
            // }
            // if(self.endTime().trim().length !== self.endTime().length) {
            //     cube.indicate("时间选择框内容不能全空格或首尾有空格！");
            //     return;
            // }
            // 时间格式校验
            if(self.time().trim() || self.endTime().trim()) {
                var begin_r = self.time().trim() ? self.time().trim().match(/^(\d{4})(-)(\d{2})(-)(\d{2})\s(\d{2})(:)(\d{2})(:)(\d{2})*/) : 1;
                var end_r = self.endTime().trim() ? self.endTime().trim().match(/^(\d{4})(-)(\d{2})(-)(\d{2})\s(\d{2})(:)(\d{2})(:)(\d{2})*/) : 1;
                if(begin_r == null || end_r == null){
                    cube.indicate("请输入正确的时间格式");
                    return;
                }else if (self.time().trim() && self.endTime().trim()) {
                    if (self.time().trim() > self.endTime().trim()) {
                        cube.indicate("开始时间不能大于结束时间");
                        return;
                    }
                }
            }
            self.isShow(true)
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

            axios.post(cube.gatewayURL2 + "riskDataInfosController/purgeList",params).then(function(res) {
                if (res.data.successful) {
                    var risklist=res.data.resultValue.content
                    console.log(risklist)
                    self.count(res.data.resultValue.totalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.totalPage));
                    self.items(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});