define(["RESTClient","echarts", "axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
        var self = this;
       

        function RiskList(searchParams) {
            var params={
                "page":searchParams,
                "size":10,
           }
            axios.post(cube.gatewayURL2 + "fileLead/knowledge",params).then(function(res) {
                if (res.data.successful==true) {   
                    self.count(res.data.resultValue.totalSize)
                    self.pageIndex(res.data.resultValue.page)
                    self.pageTotalCount(res.data.resultValue.totalPage)
                    var risklist=res.data.resultValue.content
                    // self.count(res.data.resultValue.totalSize)
                    // self.pageTotalCount(res.data.resultValue.totalPage)


                    self.cityContent(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        self.diaHeight = document.documentElement.clientHeight * 0.8 + 'px';
        var selectedItems = []
        self.searchKeywords = ko.observable('');
        $('.cate-list li a span').on('click',function(){
            $(this).addClass('modelActive')
            $('.cate-list li a span').not(this).removeClass('modelActive')
        })
          
        //总页数
        self.pageTotalCount=ko.observable('1'),
        //总记录数
        self.count =ko.observable('1'),
        //当前页数
        self.pageIndex=ko.observable('1'),
        //可见页数
        self.pageVisibleCount=ko.observable('1'),
        self.onPageIndexChanged = function(p_pageIndex) {
            self.pageIndex(p_pageIndex);
            // console.log(self.pageIndex())

            RiskList(self.pageIndex())
        }
        RiskList(self.pageIndex())
        
        self.onSelectedItems = function(e) {
            selectedItems = e;
        };
        self.unit = '';
        self.size = 'middle';
       
		
        self.style ="background:#cccccc;";
        self.columns = [
            // {name: "INSTANCE_RELATION_ID", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {name: "TYPE", width: "10%", caption: "类型", editorType: "TextEditor",align : "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     element.style.color = "#0ec4da";                
                // },
                // onClick: function (pkValue, cellValue) {
                //     commonPageBridge({ 
                //         name: 'hapMap.knowledge.main', 
                //         params: {} 
                //     });
                // }
            },
            {name: "TYPE_NAME", width:"10%", caption: "类别名称" , editorType: "TextEditor",align : "center"},
            {name: "UPDATE_TIME", width:"10%", caption: "更新时间" , editorType: "TextEditor",align : "center" },
            {name: "NUMBER_DATA_UPDATE", width:"10%", caption: "需更新数据条数" , editorType: "TextEditor",align : "center" },
            {name: "UPDATE_PREVIOUS", width:"10%", caption: "更新前数据条数" , editorType: "TextEditor",align : "center" },
            {name: "UPDATE_AFTER", width:"10%", caption: "更新后数据条数" , editorType: "TextEditor",align : "center" },
            {name: "STORAGE_LOCATION", width:"20%", caption: "存储位置" , editorType: "TextEditor",align : "center" },

        ];
        self.cityContent = []
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});