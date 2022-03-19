define(["RESTClient","axios",], function (RestClient,axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.content = '数据加载中';
        self.isShow = ko.observable("")
        self.id=params.item
        // console.log( self.id)
       self.title=ko.observable('');//单位名称
        self.time=ko.observable('');//成立时间
        self.bianhao=ko.observable('');//执照编号
        self.num=ko.observable(''); //单位人数
        self.faren=ko.observable('');//法人代表
        self.shengfen=ko.observable('');//所在省市
        self.ziben=ko.observable('');//注册资本
        self.zizhi=ko.observable('');//相关资质
        self.phone=ko.observable('');//联系电话
        self.address=ko.observable('');//单位地址
        self.size = 'middle';
        self.count = '';
        self.pageTotalCount = '';
        self.pageIndex = ko.observable('1');
        self.pageVisibleCount = 1;
        self.onPageIndexChanged = function (p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                RiskList()
            }
        }
        self.style = "background:#cccccc;";
        self.columns = [
            // { name: "", width: "30%", readOnly: true, caption: "ID", editorType: "TextEditor", align: "center" },
            { name: "PROJECT_NAME", width: "10%", caption: "项目名称", editorType: "TextEditor", align: "center" },
            { name: "PROJECT_CODE", width: "10%", caption: "项目编号", editorType: "TextEditor", align: "center" },
            { name: "ISSUING_ORG", width: "10%", caption: "发包单位", editorType: "TextEditor", align: "center" },
            { name: "IMPLEMENTATION_SITE", width: "10%", caption: "项目地点", editorType: "TextEditor", align: "center" },
            { name: "CREATE_TIME", width: "10%", caption: "开始时间", editorType: "TextEditor", align: "center" },
            { name: "END_TIME", width: "10%", caption: "结束时间", editorType: "TextEditor", align: "center" },
            { name: "STATE_PROJECT", width: "10%", caption: "当前状态", editorType: "TextEditor", align: "center" }

        ];
        self.cityContent = []
        RiskList()
        function RiskList(searchParams) {
            self.isShow(true)
            var params = {
                "page":self.pageIndex(),
                "size":"10",
                "id":self.id
            }

            axios.post(cube.gatewayURL2 + "accidentInformation/basicInformation",params).then(function(res) {
                if (res.data.successful) {
                    var list=res.data.resultValue.OUTSOURCINGUNIT[0]
                    // console.log(list)
                    if(list){
                    self.title(list.BASICINFO_CONTRACTOR);//单位名称
                    self.time(list.BASICINFO_ESTABLISHDATE);//成立时间
                    self.bianhao(list.BASICINFO_BUSINESSLICENSE);//执照编号
                    self.num(list.PERSONNEL_NUMBER); //单位人数
                    self.faren(list.BASICINFO_LEGALREPRESENTATIVE);//法人代表
                    self.shengfen(list.DATAREPORT_ORG);//所在省市
                    self.ziben(list.BASICINFO_REGISTEREDCAPITAL);//注册资本
                    self.zizhi(list.CERTIFICATION_NAME);//相关资质
                    self.phone(list.BASICINFO_CONTACTNUMBER);//联系电话
                    self.address(list.BASICINFO_ORGADDRESS);//单位地址
                    }
                    
                    var list2=res.data.resultValue.PROJECTMANAGEMENT[0].content
                    // console.log(list2)
                    self.count(res.data.resultValue.PROJECTMANAGEMENT[0].totalSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.PROJECTMANAGEMENT[0].totalPage));
                    self.cityContent(list2);
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