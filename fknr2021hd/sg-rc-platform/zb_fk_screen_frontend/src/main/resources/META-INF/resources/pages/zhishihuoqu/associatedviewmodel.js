define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
		self.add = function(){
			cube.showDialog({
                title : '新增',
                width:"800px",
                height:'520px',
                submitFormOnConfirm : false,
                isShowCloseBtn:false, // 是否显示取消按钮
                isShowConfirmBtn:false, // 是否显示保存按钮
                templateOptions : {
                    name : 'fusion.detail',
                    params : {}
                }
            });
		}
		self.theImport = function(){

		}
		self.bulkImport = function(){

		}
		self.onPageIndexChanged = function(p_pageIndex) {
            
        }
		self.size = 'middle';
		self.count ='20';
        self.pageTotalCount = '2';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;
        self.style ="background:#cccccc;";
        self.columns = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {name: "name", width: "10%", caption: "名称", editorType: "TextEditor",align : "center"},
            {name: "nodeName", width:"10%", caption: "编码" , editorType: "TextEditor",align : "center"},
            {name: "ylb", width:"10%", caption: "源类别" , editorType: "TextEditor",align : "center" },
            {name: "mblb", width:"10%", caption: "目标类别" , editorType: "TextEditor",align : "center" },
            {name: "glgxlx", width:"10%", caption: "关联关系类型" , editorType: "TextEditor",align : "center" },
            {name: "attribute", width:"10%", caption: "描述" , editorType: "TextEditor",align : "center" },
            {name: "time", width:"10%", caption: "更新时间" , editorType: "TextEditor",align : "center" },

        ];
        self.cityContent = [
            {
                'siteinfo_id':1,
				'name':'下发',
				'nodeName':'GX000001',
                'ylb':'作业计划',
                'mblb':'外包单位',
                'glgxlx':'一对一',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':2,
				'name':'下发',
				'nodeName':'GX000001',
                'ylb':'作业计划',
                'mblb':'外包单位',
                'glgxlx':'一对一',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':3,
				'name':'下发',
				'nodeName':'GX000001',
                'ylb':'作业计划',
                'mblb':'外包单位',
                'glgxlx':'一对一',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':4,
				'name':'下发',
				'nodeName':'GX000001',
                'ylb':'作业计划',
                'mblb':'外包单位',
                'glgxlx':'一对一',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':5,
				'name':'下发',
				'nodeName':'GX000001',
                'ylb':'作业计划',
                'mblb':'外包单位',
                'glgxlx':'一对一',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':6,
				'name':'下发',
				'nodeName':'GX000001',
                'ylb':'作业计划',
                'mblb':'外包单位',
                'glgxlx':'一对一',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':7,
				'name':'下发',
				'nodeName':'GX000001',
                'ylb':'作业计划',
                'mblb':'外包单位',
                'glgxlx':'一对一',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':8,
				'name':'下发',
				'nodeName':'GX000001',
                'ylb':'作业计划',
                'mblb':'外包单位',
                'glgxlx':'一对一',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            }
        ]
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});