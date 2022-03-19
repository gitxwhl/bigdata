define(["RESTClient", "axios",], function (RestClient,axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.province = [
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
        self.provinceContent = ko.observable('');
        self.unitContent = [
            { id: '', text: '全部' }
        ];
        self.unit = ko.observable('');
        self.size = 'middle';
        self.count = '';
        self.pageTotalCount = '';
        self.pageIndex = ko.observable(1);
        self.pageVisibleCount = 1;
        self.pageSize = 1;
        self.onPageIndexChanged = function (p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                RiskList()
            }
        }
        self.style = "background:#cccccc;";
        self.columns = [
            { name: "CONSTRUCTION_ORG_ID", width: "30%", readOnly: true, caption: "ID", editorType: "TextEditor", align: "center" },
            { name: "DATAREPORT_ORG", width: "10%", caption: "省份", editorType: "TextEditor", align: "center" },
            {
                name: "CONSTRUCTION_ORG", width: "30%", caption: "单位名称", editorType: "TextEditor", align: "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (items) {
                    commonPageBridge({
                        name: 'outsourcing.details.main',
                        params: {items:items}
                    });
                }
            },
            { name: "WORK_MANAGER", width: "10%", caption: "负责人", editorType: "TextEditor", align: "center" },
            { name: "OUTWORKER_NUM", width: "10%", caption: "人数", editorType: "TextEditor", align: "center" },
            { name: "accidentCount", width: "10%", caption: "作业风险数", editorType: "TextEditor", align: "center" },
            { name: "violationCount", width: "10%", caption: "违章次数", editorType: "TextEditor", align: "center" },
            { name: "negativeListCount", width: "10%", caption: "进入负面清单次数", editorType: "TextEditor", align: "center" },
            { name: "blackListCount", width: "10%", caption: "进入黑名单次数", editorType: "TextEditor", align: "center" }

        ];
        self.item = []
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
                    "datareportOrg":self.provinceContent(),
                    "constructionOrg":self.unit()
                }
            }

            axios.post(cube.gatewayURL2 + "accidentInformation/getRiskPortrait",params).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue.content
                    // console.log(risklist)
                    self.count(res.data.resultValue.totalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.totalPage));
                    self.item(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});