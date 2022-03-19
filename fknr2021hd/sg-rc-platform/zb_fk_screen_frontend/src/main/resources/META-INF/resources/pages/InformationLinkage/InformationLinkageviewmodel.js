define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.titleName=ko.observable("")//查询姓名
        self.Name=''//姓名
        self.phone=''//手机
        self.IDnumber=''//身份证号
        self.gender=''//性别
        self.TheirIdentity=''//所属身份
        self.PlaceUnit=''//所在单位
        self.age=''//年龄

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
            {id:'1',text:'北京xxxxx有限公司'},
            {id:'2',text:'上海xxxxx有限公司'},
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
        //操作
        self.isAllowOperations = false;

        self.isAllowShift = false;
        self.isAllowSort = false;
        self.isAllowAppend = false;
        self.isAllowPaging = false;
        self.primaryKey='BASICFILEINFO_ID';
        self.primaryKey2='ids';
        self.pageSize = 1;
        self.id='';
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='6';
        self.pageTotalCount = '1';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount =1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
            }
        }

        self.columns = [
            {name: "BASICFILEINFO_ID",  readOnly:true, caption: "" , editorType: "TextEditor",align : "center",},
            {name: "NAME",  width: "10%", readOnly:true, caption: "姓名" ,align : "center", editorType: "TextEditor",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (items) {
                    commonPageBridge({
                        name: 'InformationLinkage.peopleDetail.main',
                        params: {item:items}
                    });
                }
            },
            {name: "SEX", width: "10%", caption: "性别" , editorType: "TextEditor",align : "center",},
            {name: "ID_CARD",width: "15%", caption: "身份证号" , editorType: "TextEditor",align : "center",},
            {name: "DATAREPORT_ORG", width: "10%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
            {name: "ORG_NAME", width: "10%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "ACCESS_STATE", width: "10%", caption: "当前准入状态" , editorType: "TextEditor",align : "center",},
            {name: "ACCESS_DATE", width: "10%", caption: "准入期限" , editorType: "TextEditor",align : "center",},
            {name: "VIOLATION_CNT", width: "10%", caption: "违章次数" , editorType: "TextEditor",align : "center",},
        ];
        self.items=[]
        //查询
        self.search = function() {
            RiskList()
        }

        // //人员详情查看
        // self.goparticulars = function (id) {
        //     commonPageBridge({
        //         name: 'InformationLinkage.peopleDetail.main',
        //         params: {}
        //     });
        //
        // }
        RiskList()
        function RiskList(searchParams) {
            var params = {
                "page":	self.pageIndex(),
                "size":10,
                "params": {
                    "name":self.titleName(),
                    "orgName":self.Unit(),
                    "datareportOrgId":self.provinceContent()
                }
            }

            axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkerLinkageInfosList",params).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue.content
                    // console.log(risklist)
                    self.count(res.data.resultValue.totalSize);
                    self.pageTotalCount(res.data.resultValue.totalPage);
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