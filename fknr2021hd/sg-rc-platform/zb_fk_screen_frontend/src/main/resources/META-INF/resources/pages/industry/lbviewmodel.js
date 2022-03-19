define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1).toString().padStart(2, "0") + "-" + day2.getDate().toString().padStart(2, "0");
        self.title=ko.observable('');//标题
        self.number=ko.observable('');//编号
        self.time = ko.observable(s2 + ' 00:00:00');//预警计划时间
        self.endTime=ko.observable(s2 + ' 23:59:59');//预警截至时间
        // self.time = ko.observable('');//预警计划时间
        // self.endTime=ko.observable('');//预警截至时间
        self.State = '';
        self.riskGrade=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'一般'},
            {id:'02',text:'重大'},
            {id:'03',text:'特别重大'},
            // {id:'04',text:'四级'},
        ]);
        self.Grade = ko.observable("");
        self.warningState=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'已发布'},
            {id:'02',text:'告知'},
            {id:'03',text:'实施'},
            {id:'04',text:'解除'},
            {id:'05',text:'延期'},
            {id:'06',text:'作废'},

        ]);
        self.State = ko.observable("");
        self.warning =ko.observable("");
        self.releaseUnit =[{id:'',text:'全部'},];
        self.Unit = ko.observable("")
        // self.primaryKey='INDUSRISKNOTICE_ID';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount = 1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                IndustrialRisk()
            }
        }
        self.shutDown = function(){
            self.isCity(false)
        }
        self.style ="background:#cccccc;";
        self.columns = [
            // {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {
                name: "TITLE", width: "40%", caption: "标题", editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                    
                },
                onClick: function (pkValue, cellValue) {
                    //self.showDetail(true)
                }
            },
            {name: "WARNINGLEVEL", width:"15%", caption: "预警级别" , editorType: "TextEditor",align : "center"},
            {name: "WARNBEGINTIME", width:"20%", caption: "预警计划开始时间" , editorType: "TextEditor",align : "center" },
            {name: "PUBLISH_ORG", width:"20%", caption: "发布单位" , editorType: "TextEditor",align : "center" },
            {name: "WARNSTATUS", width:"15%", caption: "状态" , editorType: "TextEditor",align : "center" },
            {name: "WARNNUM", width:"15%", caption: "预警编号" , editorType: "TextEditor",align : "center" },

        ];
        self.cityContent = []
        //输入框onchange事件
        self.iptChange = function() {
            var ipt1 = document.querySelector("#ipt1")
            if(ipt1.value.length > 100) {
                ipt1.value = ipt1.value.substring(0,100)
                cube.indicate("输入框内容长度不能超过100！");
            }
        }
        self.iptChange2 = function() {
            var ipt1 = document.querySelector("#ipt2")
            if(ipt1.value.length > 100) {
                ipt1.value = ipt1.value.substring(0,100)
                cube.indicate("输入框内容长度不能超过100！");
            }
        }
        self.iptChange3 = function() {
            var ipt1 = document.querySelector("#ipt3")
            if(ipt1.value.length > 100) {
                ipt1.value = ipt1.value.substring(0,100)
                cube.indicate("输入框内容长度不能超过100！");
            }
        }
        //查询
        self.search = function() {
            self.pageIndex(1)
            IndustrialRisk()
        }
        IndustrialRisk()
        //产业风险业预警单分页查询
        function IndustrialRisk() {
            // 输入框内容校验
            var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
            if(iptRep.test(self.title().trim()) || iptRep.test(self.number().trim()) || iptRep.test(self.Unit().trim())) {
                cube.indicate("输入框含违法字符，请重新输入！");
                return;
            }
            // 输入框空格校验
            if(self.title().trim().length !== self.title().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            if(self.number().trim().length !== self.number().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            if(self.Unit().trim().length !== self.Unit().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            // 输入框长度校验
            if(self.title().trim().length > 100 || self.number().trim().length > 100 || self.Unit().trim().length > 100) {
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

            var jsonStr2 = {
                "page": self.pageIndex(),
                "size": "10",
                "params": {
                    "warningLevel": self.Grade(),//预警等级
                    "warnNum": self.number(),//预警编号
                    "startTime": self.time().trim(),//开始时间
                    "endTime": self.endTime().trim(),//结束时间
                    "publishOrg": self.Unit(),//发布单位
                    "warnStatus": self.State(),//预警状态
                    "title": self.title()//标题
                }
            }
            self.isShow(true)
            axios.post(cube.gatewayURL + "riskIndusRiskWarn/risRiskIndusRiskWarnList", jsonStr2).then(function (res) {
                if (res.data.successful) {
                    var risklist = res.data.resultValue.content
                    // console.log(res.data)
                    self.count(res.data.resultValue.totalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.totalPage));
                    self.cityContent(risklist);

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