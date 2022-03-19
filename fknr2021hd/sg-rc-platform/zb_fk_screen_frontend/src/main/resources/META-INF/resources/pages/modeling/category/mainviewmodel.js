define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
        var self = this;
        var selectedItems = []
        $('.cate-list li a span').on('click',function(){
            $(this).addClass('modelActive')
            $('.cate-list li a span').not(this).removeClass('modelActive')
        })
        self.add = function(){
            cube.showDialog({
                title : '新增',
                width:"800px",
                height:'400px',
                submitFormOnConfirm : false,
                isShowCloseBtn:false, // 是否显示取消按钮
                isShowConfirmBtn:false, // 是否显示保存按钮
                templateOptions : {
                    name : 'modeling.category.detail',
                    params : {}
                }
            });
        }
        self.modify = function(){
            if(selectedItems.length == 1){
                cube.showDialog({
                    title : '新增',
                    width:"800px",
                    height:'400px',
                    submitFormOnConfirm : false,
                    isShowCloseBtn:false, // 是否显示取消按钮
                    isShowConfirmBtn:false, // 是否显示保存按钮
                    templateOptions : {
                        name : 'modeling.category.detail',
                        params : {item:selectedItems[0]}
                    }
                });
            }else{
                cube.indicate("请勾选一条数据！");
            }
            
        }
        self.deleteInstance = function(){
            if(selectedItems.length==0){
                cube.indicate("请至少选中一条数据！");
                return;
            }
            cube.confirm('温馨提示','删除后将不能恢复，是否确定删除?',function(){

            })
        }
        self.isReturn = function(){
            commonPageBridge({ 
                name: 'modeling.home.main', 
                params: {} 
            });
        }
        self.onPageIndexChanged = function(p_pageIndex) {
            
        }
        
        self.onSelectedItems = function(e) {
            selectedItems = e;
        };
        self.unit = '';
		self.size = 'middle';
		self.count ='20';
        self.pageTotalCount = '2';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;
        self.style ="background:#cccccc;";
        self.columns = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "name", width: "15%", caption: "名称", editorType: "TextEditor",align : "center"},
            {name: "nodeName", width:"10%", caption: "节点名称" , editorType: "TextEditor",align : "center"},
            {name: "attribute", width:"10%", caption: "属性" , editorType: "TextEditor",align : "center" },
            {name: "time", width:"10%", caption: "更新时间" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent = [
            {
                'siteinfo_id':1,
				'name':'作业计划',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':2,
				'name':'外包单位',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':3,
				'name':'人员',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':4,
				'name':'风险',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':5,
				'name':'隐患',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':6,
				'name':'事件',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            },
            {
                'siteinfo_id':7,
				'name':'违章信息',
                'nodeName':'LB00001',
                'attribute':'xxxxx',
                'time':'2020/03/09',
            }
        ]
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});