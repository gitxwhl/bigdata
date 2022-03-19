define(["RESTClient", "axios"], function(RestClient, axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = ko.observable(1);;
        self.pageVisibleCount = 1;
        self.count2 ='';
        self.pageTotalCount2 = '';
        self.pageIndex2 = ko.observable(1);;
        self.pageVisibleCount2 = 1;
        self.count3 ='';
        self.pageTotalCount3 = '';
        self.pageIndex3 = ko.observable(1);;
        self.pageVisibleCount3 = 1;
        self.Count4 ='';
        self.pageTotalCount4 = '';
        self.pageIndex4 = ko.observable(1);;
        self.pageVisibleCount4 = 1;
        self.onPageIndexChanged4 = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex4(p_pageIndex);
                information()
            }
        }
        self.onPageIndexChanged3 = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex3(p_pageIndex);
                Grids()
            }
        }
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                RiskList()
            }
        }
        self.onPageIndexChanged2 = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex2(p_pageIndex);
                PersonalEvents()
            }
        }


        self.columns = [
            // {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {
                name: "ACCIDENTGRADE", width: "10%", caption: "等级", editorType: "TextEditor",align : "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     return "<span style='padding:4px 16px; background:#6D000E;border-radius: 5px; font-size:16px'>" + cellValue + "</span>"
                // },
                onClick: function (pkValue, cellValue) {
                    //self.showDetail(true)
                }
            },
            {name: "TITLE", width:"30%", caption: "简题" , editorType: "TextEditor",align : "center"},
            {name: "VOLTAGE_CAPACITY", width:"10%", caption: "电压容量(KV)" , editorType: "TextEditor",align : "center" },
            {name: "GRIDCORP", width:"10%", caption: "地区" , editorType: "TextEditor",align : "center" },
            {name: "REASON", width:"10%", caption: "直接原因" , editorType: "TextEditor",align : "center" },
            {name: "DUTY", width:"10%", caption: "主要责任" , editorType: "TextEditor",align : "center" },
            {name: "DEVICECLASS", width:"10%", caption: "设备分类" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENTCORP", width:"20%", caption: "单位" , editorType: "TextEditor",align : "center" },
            {name: "BGNTIME", width:"10%", caption: "发生时间" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent = []
        self.GridEventscolumns = [
            // {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {
                name: "ACCIDENTGRADE", width: "10%", caption: "等级", editorType: "TextEditor",align : "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     return "<span style='padding:4px 16px; background:#6D000E;border-radius: 5px; font-size:16px'>" + cellValue + "</span>"
                // },
                onClick: function (pkValue, cellValue) {
                    //self.showDetail(true)
                }
            },
            {name: "TITLE", width:"30%", caption: "简题" , editorType: "TextEditor",align : "center"},
            {name: "VOLTAGE_CAPACITY", width:"10%", caption: "电压容量(KV)" , editorType: "TextEditor",align : "center" },
            // {name: "GRIDCORP", width:"10%", caption: "地区" , editorType: "TextEditor",align : "center" },
            {name: "REASON", width:"10%", caption: "直接原因" , editorType: "TextEditor",align : "center" },
            {name: "DUTY", width:"10%", caption: "主要责任" , editorType: "TextEditor",align : "center" },
            {name: "DEVICECLASS", width:"10%", caption: "设备分类" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENTCORP", width:"20%", caption: "单位" , editorType: "TextEditor",align : "center" },
            {name: "BGNTIME", width:"10%", caption: "发生时间" , editorType: "TextEditor",align : "center" }

        ];
        self.GridEventsContent = []

        self.columns2 = [
            // {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {
                name: "ACCIDENTGRADE", width: "10%", caption: "等级", editorType: "TextEditor",align : "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     return "<span style='padding:4px 16px; background:#6D000E;border-radius: 5px; font-size:16px'>" + cellValue + "</span>"
                // },
                onClick: function (pkValue, cellValue) {
                    //self.showDetail(true)
                }
            },
            {name: "TITLE", width:"30%", caption: "简题" , editorType: "TextEditor",align : "center"},
            {name: "ACCIDENTCLASS", width:"10%", caption: "事故类别" , editorType: "TextEditor",align : "center" },
            {name: "VICTIM", width:"10%", caption: "受害人" , editorType: "TextEditor",align : "center" },
            {name: "INJURY_DEGREE", width:"10%", caption: "受害程度" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENTCORP", width:"20%", caption: "单位" , editorType: "TextEditor",align : "center" },
            {name: "BGNTIME", width:"10%", caption: "发生时间" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent2 = []


        self.columns4 = [
            // {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {
                name: "ACCIDENTGRADE", width: "10%", caption: "等级", editorType: "TextEditor",align : "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     return "<span style='padding:4px 16px; background:#6D000E;border-radius: 5px; font-size:16px'>" + cellValue + "</span>"
                // },
                onClick: function (pkValue, cellValue) {
                    //self.showDetail(true)
                }
            },
            {name: "TITLE", width:"30%", caption: "简题" , editorType: "TextEditor",align : "center"},
            {name: "ACCIDENTCLASS", width:"10%", caption: "事件分类" , editorType: "TextEditor",align : "center" },
            {name: "DEVICECLASS", width:"10%", caption: "设备分类" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENTCORP", width:"20%", caption: "单位" , editorType: "TextEditor",align : "center" },
            {name: "BGNTIME", width:"10%", caption: "发生时间" , editorType: "TextEditor",align : "center" }

        ];
        self.Content4= []
        self.style=''
        self.isShowRowNumber = true;
        self.isShowCheckBox = false;
        self.isShowBorder = true;
        self.isShowStripe = true;
        self.isShowHover = false;
        self.isShowCondense = false;
        self.isAllowEdit = false;
        self.isAllowDelete = false;
        self.isAllowOperations = false;
        self.isAllowShift = false;
        self.isAllowSort = false;
        self.isAllowAppend = false;
        self.isAllowPaging = true;
        self.pageSize = 10;

        //设备事件
        self.equipment = function() {
            $(".aa").show();
            $(".region").show();
            $(".voltage").show();
            $(".classification").show();
            $(".extentInjury2").hide();
            $(".extentInjury").hide();
            $(".ren").hide();
            $(".shebei").show();
            $(".dianwang").hide();
            $(".incident").hide();
            $(".xinxiweihu").hide();
            RiskList()
        }
        RiskList()
        //设备事件
        function RiskList(searchParams) {
            self.isShow(true)
            var params = {
                "page":self.pageIndex(),
                "size":5,
                "params": {
                    "accidentGrade":'', //事故等级
                    "voltageCapacity":'', //电压容量
                    "gridcorp":'', //地区
                    "deviceClass":'', //设备分类
                    "startTime":'', //开始时间
                    "endTime":'' //结束时间
                }
            }
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/equipAccidentList",params).then(function(res) {
                if (res.data.successful) {
                    var risklist=res.data.resultValue.content
                    // console.log(risklist)
                    self.count(res.data.resultValue.totalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.totalPage));
                    self.cityContent(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }
        //电网事件
        self.GridEvent = function() {
            $(".region").hide();
            $(".extentInjury2").hide();
            $(".extentInjury").hide();
            $(".voltage").show();
            $(".classification").show();
            $(".ren").hide();
            $(".shebei").hide();
            $(".dianwang").show();
            $(".incident").hide();
            $(".xinxiweihu").hide();
            Grids()
        }
        function Grids() {
            self.isShow(true)
            var params = {
                "page":self.pageIndex3(),
                "size":5,
                "params": {
                    "accidentGrade":'', //事故等级
                    "voltageCapacity":'', //电压容量
                    // "accidentClass":self.gridcorp(),  //事故分类
                    "deviceClass":'',//设备分类
                    "startTime":'', //开始时间
                    "endTime":''  //结束时间
                }
            }
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/powerGridAccidentList",params).then(function(res) {
                if (res.data.successful) {
                    var risklist3=res.data.resultValue.content
                    // console.log(risklist3)
                    self.count3(res.data.resultValue.totalSize);
                    self.pageTotalCount3(Math.ceil(res.data.resultValue.totalPage));
                    self.GridEventsContent(risklist3);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }
        //人身事件
        self.Personal = function() {
            $(".aa").hide();
            $(".region").hide();
            $(".voltage").hide();
            $(".classification").hide();
            $(".extentInjury2").show();
            $(".extentInjury").show();
            $(".ren").show();
            $(".shebei").hide();
            $(".dianwang").hide();
            $(".incident").hide();
            $(".xinxiweihu").hide();
            PersonalEvents()
        }
        function PersonalEvents(searchParams) {
            self.isShow(true)
            var params = {
                "page":	self.pageIndex2(),
                "size":5,
                "params": {
                    "accidentGrade":'', //等级
                    "accidentClass":'',//事故分类
                    "injuryDegree":'',//伤亡程度
                    // "deviceClass":self.deviceClass(),
                    "startTime":'',
                    "endTime":''
                }
            }
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/personalAccidentList",params).then(function(res) {
                if (res.data.successful) {
                    var risklist=res.data.resultValue.content
                    // console.log(risklist)
                    self.count2(res.data.resultValue.totalSize);
                    self.pageTotalCount2(Math.ceil(res.data.resultValue.totalPage));
                    self.cityContent2(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }
       //信息维护
        self.informationMaintenance = function() {
            $(".region").hide();
            $(".voltage").hide();
            $(".incident").show();
            $(".aa").hide();
            $(".ren").hide();
            $(".shebei").hide();
            $(".dianwang").hide();
            $(".xinxiweihu").show();
            information()
        }
        function information(searchParams) {
            self.isShow(true)
            var params = {
                "page": self.pageIndex4(),
                "size":5,
                "params": {
                    "accidentGrade":'', //等级
                    // "voltageCapacity":self.voltageCapacity(),
                    "accidentClass":'',
                    "deviceClass":'',//设备分类
                    "startTime":'',
                    "endTime":''
                }
            }
            axios.post(cube.gatewayURL2 + "riskAccidentAnalysis/informAccidentList",params).then(function(res) {
                if (res.data.successful) {
                    var risklist=res.data.resultValue.content
                    // console.log(risklist)
                    self.Count4(res.data.resultValue.totalSize);
                    self.pageTotalCount4(Math.ceil(res.data.resultValue.totalPage));
                    self.Content4(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })
        }
        $('.fr').click(function(){
            $(this).addClass('ifative')
            $('.fr').not(this).removeClass('ifative')
        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});