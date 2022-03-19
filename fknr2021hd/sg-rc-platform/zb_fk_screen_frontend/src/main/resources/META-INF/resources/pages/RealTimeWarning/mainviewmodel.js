define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.instrumentName=ko.observable("")//工器具名称
        self.province =[
            {id:'',text:'全部'},
            {id:'1',text:'北京'},
            {id:'2',text:'天津'},
            {id:'3',text:'河北'},
            {id:'4',text:'冀北'},
            {id:'5',text:'山西'},
            {id:'6',text:'山东'},
            {id:'7',text:'上海'},
            {id:'8',text:'江苏'},
            {id:'9',text:'浙江'},
            {id:'10',text:'安徽'},
            {id:'11',text:'福建'},
            {id:'12',text:'湖北'},
            {id:'13',text:'湖南'},
            {id:'14',text:'河南'},
            {id:'15',text:'江西'},
            {id:'16',text:'四川'},
            {id:'17',text:'重庆'},
            {id:'18',text:'辽宁'},
            {id:'19',text:'吉林'},
            {id:'20',text:'黑龙江'},
            {id:'21',text:'蒙东'},
            {id:'22',text:'陕西'},
            {id:'23',text:'甘肃'},
            {id:'24',text:'青海'},
            {id:'25',text:'宁夏'},
            {id:'26',text:'新疆'},
            {id:'27',text:'西藏'},
        ];
        self.provinceContent = ko.observable("")
        self.releaseUnit =[
            {id:'',text:'全部'},
            {id:'0',text:'个人防护装备'},
            {id:'1',text:'登高工器具'},
            {id:'2',text:'绝缘安全工器具'},
        ];
        self.type = ko.observable("") //工器具类型
        self.Unit = ko.observable("") //单位
        self.currentStatus =[
            {id:'',text:'全部'},
            {id:'1',text:'采购'},
            {id:'2',text:'验收'},
            {id:'3',text:'入库'},
            {id:'4',text:'检测'},
            {id:'5',text:'报废'},
            {id:'6',text:'领用'},
            {id:'7',text:'检查不合格'},
        ];
        self.status = ko.observable("") //当前状态

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
        self.primaryKey='id';
        self.pageSize = 1;
        self.id='';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='1';
        self.pageTotalCount = '1';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount =1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                RiskList()
            }
        }


        self.columns = [
            {name: "id",  readOnly:true, caption: "" , editorType: "TextEditor",align : "center",},
            {name: "WARING_LEVEL",  width: "10%", readOnly:true, caption: "预警级别" ,align : "center", editorType: "TextEditor",
                // renderCell: function (cellValue, element, dictValue) {
                //     element.style.color = "#0ec4da";
                // },
                onClick: function (items) {

                }
            },
            {name: "DATAREPORT_ORG", width: "10%", caption: "省份" , editorType: "TextEditor",align : "center",},
            {name: "MANAGEMENT_CODE",width: "15%", caption: "单位" , editorType: "TextEditor",align : "center",},
            {name: "TOOL_TYPE", width: "10%", caption: "工器具类型" , editorType: "TextEditor",align : "center",},
            {name: "NAME", width: "10%", caption: "工器具名称" , editorType: "TextEditor",align : "center",},
            {name: "involvedProject", width: "10%", caption: "数量" , editorType: "TextEditor",align : "center",}, //接口未返回对应字段
            {name: "expirationTime", width: "10%", caption: "到期时间" , editorType: "TextEditor",align : "center",},
            {name: "timeRemaining", width: "10%", caption: "剩余时间" , editorType: "TextEditor",align : "center",},
            {name: "INS_STATE", width: "10%", caption: "当前状态" , editorType: "TextEditor",align : "center",},
        ];
        self.items=[]
        //查询
        self.search = function() {
            RiskList()
        }
        RiskList()
        function RiskList(searchParams) {
            var params = {
                "pageNo":	self.pageIndex(),
                "pageSize":10,
                "params": {
                    "dataReportOrg":self.provinceContent(), //省份
                    "managementCode":self.Unit(), //单位
                    "toolType":self.type(),//工器具类型
                    "name":self.instrumentName(),//工器具名称（可不传）
                    "insState":self.status()//当前状态
                }
            }

            axios.post(cube.gatewayURL2 + "toolInformation/realWarning",params).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue.content
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