define(["RESTClient"], function(RestClient) {
    var PageViewModel = function(params) {
        var self = this;
        self.page='list';
        //通报类型
        self.NotificationTypeContent=ko.observableArray();
        self.NotificationType='';
        self.releaseTime='';//发布时间
        //下发状态
        self.issueContent=ko.observableArray();
        self.issue='';
        //通报对象
        self.NotificationObjectContent=ko.observableArray();
        self.NotificationObject='';
        self.time=''//截至时间
        self.notificationTitle=''//通报标题
        self.style ="background:#cccccc;";
        self.isShowRowNumber = true;
        self.isShowCheckBox = true;
        self.isShowBorder = true;
        self.isShowStripe = true;
        self.isShowHover = true;
        self.isShowCondense = false;
        self.isAllowEdit = false;
        self.isAllowDelete = false;
        self.isAllowOperations = false;
        self.isAllowShift = false;
        self.isAllowSort = false;
        self.isAllowAppend = false;
        self.isAllowPaging = false;
        self.pageSize = 10;
        self.id='';
        self.allowTypes = "jpg,jpeg,gif,png,bmp,tiff,txt";
        self.url = '';
        self.isShowPreview = false;
        self.maxCount = 0;
        self.maxFileSize = 0;
        self.msgType = 'confirm';
        self.isShowDialog = false;
        self.title = '温馨提示';
        self.content = '删除后将不能恢复，是否确定删除?';
        self.confirmDelete = function (e) {
            var parmers = []
            selectedItems.forEach(function (item) {
                parmers.push(item['id'])
            });
            cube.getPageViewModelByNode($('#instanceTable')).load();//点击之后重新刷新表格
            // axios.post(cube.gatewayURL +  "expert/dropbyid",parmers).then(function (res) {
            //     // self.refresh();
            //     modification()
            // });
        };
        var selectedItems = []
        self.onSelectedItems = function(e) {
            selectedItems = e;
        };
        self.columns = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "personName", width:"280px", readOnly:true, caption: "通报编号" , editorType: "TextEditor",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    self.goDetail(pkValue);
                }},
            {name: "gender", width:"150px", readOnly:true, caption: "通报类型" , editorType: "TextEditor",align : "center",},
            {name: "age", width:"150px", caption: "通报对象" , editorType: "TextEditor",align : "center",},
            {name: "certificateName", width:"150px", caption: "通报标题" , editorType: "TextEditor",align : "center",},
            {name: "belongLevel", width:"150px", caption: "发布时间" , editorType: "TextEditor",align : "center",},
            {name: "Certificate_number", width:"120px", caption: "截至时间" , editorType: "TextEditor",align : "center",},
            {name: "belongUnit", width:"90px",caption: "下发状态" , editorType: "TextEditor",align : "center",},
        ];
        self.items=[
            {
                id:1,
                personName:'111111',
                gender:111,
                age:111,
                certificateName:111,
                belongLevel:111,
                Certificate_number:111,
                belongUnit:11,
                major1:111,
            },
            {
                id:2,
                personName:'张三',
                gender:111,
                age:111,
                certificateName:111,
                belongLevel:111,
                Certificate_number:111,
                belongUnit:11,
                major1:111,
            },
            {
                id:3,
                personName:'张三',
                gender:111,
                age:111,
                certificateName:111,
                belongLevel:111,
                Certificate_number:111,
                belongUnit:11,
                major1:111,
            },


        ]
        //已读人员信息表
        self.ReadInformation = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "personName", width:"150px", readOnly:true, caption: "序号" , editorType: "TextEditor",align : "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     element.style.color = "#0ec4da";
                // },
                // onClick: function (pkValue, cellValue) {
                //     self.goDetail(pkValue);
                // }
                },
            {name: "gender", width:"150px", readOnly:true, caption: "已读人员" , editorType: "TextEditor",align : "center",},
            {name: "age", width:"150px", caption: "所属单位" , editorType: "TextEditor",align : "center",},
            {name: "certificateName", width:"150px", caption: "手机号" , editorType: "TextEditor",align : "center",},
            // {name: "belongLevel", width:"150px", caption: "发布时间" , editorType: "TextEditor",align : "center",},
            // {name: "Certificate_number", width:"120px", caption: "截至时间" , editorType: "TextEditor",align : "center",},
            // {name: "belongUnit", width:"90px",caption: "下发状态" , editorType: "TextEditor",align : "center",},
        ];
        self.ReadInformationContent=[
            {
                id:1,
                personName:'111111',
                gender:111,
                age:111,
                certificateName:111,
                belongLevel:111,
                Certificate_number:111,
                belongUnit:11,
                major1:111,
            },
            {
                id:2,
                personName:'张三',
                gender:111,
                age:111,
                certificateName:111,
                belongLevel:111,
                Certificate_number:111,
                belongUnit:11,
                major1:111,
            },
            {
                id:3,
                personName:'张三',
                gender:111,
                age:111,
                certificateName:111,
                belongLevel:111,
                Certificate_number:111,
                belongUnit:11,
                major1:111,
            },


        ]
        self.goDetail = function (id) {
            self.page('safetyInformation')
        }
        //返回安全考场
        self.goBack = function() {
                    self.page('list')
                }
        //新建通报信息
        self.newInformation= function() {
            self.page('OntheNew')
        }
        //新建通报信息返回
        self.newInformationReturn= function() {
            self.page('list')
        }
        //删除
        self.deleteInstance = function (e) {
            if (selectedItems.length === 0) {
                cube.indicate("请至少勾选一条数据");
                return;
            }
            self.isShowDialog(true)
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});
