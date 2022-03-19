define(["RESTClient"], function (RestClient) {
    var PageViewModel = function (params) {
        var self = this;
        self.size = 'middle';
        self.count = '20';
        self.pageTotalCount = '2';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;
        self.style = "background:#cccccc;";
        self.onPageIndexChanged = function (p_pageIndex) {

        }
        self.columns = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {name: "name", width: "15%", caption: "名称", editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    commonPageBridge({
                        name: 'zhishihuoqu.knowledge.main',
                        params: {}
                    });
                }
            },
            {name: "nodeName", width:"10%", caption: "编号" , editorType: "TextEditor",align : "center"},
            {name: "attribute", width:"10%", caption: "描述" , editorType: "TextEditor",align : "center" },
            {name: "time", width:"10%", caption: "更新时间" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent = [
            {
                'siteinfo_id':1,
                'name':'作业计划1',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':2,
                'name':'作业计划1',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':3,
                'name':'作业计划1',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':4,
                'name':'作业计划1',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':5,
                'name':'作业计划1',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':6,
                'name':'作业计划1',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':7,
                'name':'作业计划1',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            }
        ]
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});