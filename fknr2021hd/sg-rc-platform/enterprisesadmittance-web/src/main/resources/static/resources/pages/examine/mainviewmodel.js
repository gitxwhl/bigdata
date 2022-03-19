define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
		self.page='list';
		self.professionalContent=ko.observableArray();
		self.professional = '';
		self.content = '';//考题内容
		self.titleTypeContent=ko.observableArray();
		self.titleType = '';
		self.newInformation = function(){//新建
			self.newEditor('')
		}
		self.msgType = 'confirm';
        self.isShowDialog = false;
        self.title = '温馨提示';
		self.content1 = '删除后将不能恢复，是否确定删除?';
		self.confirmDelete = function (e) {
            
            cube.getPageViewModelByNode($('#instanceTable')).load();//点击之后重新刷新表格
            // axios.post(cube.gatewayURL +  "expert/dropbyid",parmers).then(function (res) {
            //     // self.refresh();
            //     modification()
            // });
        };
		var ktzt = {
			'0': '未删除'
		}
		self.columns = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "ktzy", width:"15%", readOnly:true, caption: "考题专业" , editorType: "TextEditor",align : "center",},
            {name: "gender", width:"15%", readOnly:true, caption: "题目类型" , editorType: "TextEditor",align : "center",},
            {name: "ktnr", width:"50%", caption: "考题内容" , editorType: "TextEditor",align : "center",},
			{name: "ktzt", width:"10%", caption: "考题状态" , editorType: "TextEditor",align : "center",
				renderCell: function(item, element) {
					return ktzt[item];
				}
			}
        ];
        self.items=[
            {
                id:1,
                ktzy:'主要负责人',
                gender:'单选题',
                ktnr:'按照《国网北京市电力公司安全生产职责规范》规定：( )具有定期主持召开安全生产委员会议和安全生产月度例会，研究解决安全生产重大问题的安全职责。',
                ktzt:0
            },
            {
                id:2,
                ktzy:'主要负责人',
                gender:'单选题',
                ktnr:'按照《国网北京市电力公司安全生产职责规范》规定：( )具有定期主持召开安全生产委员会议和安全生产月度例会，研究解决安全生产重大问题的安全职责。',
                ktzt:0
            },
            {
                id:3,
                ktzy:'主要负责人',
                gender:'单选题',
                ktnr:'按照《国网北京市电力公司安全生产职责规范》规定：( )具有定期主持召开安全生产委员会议和安全生产月度例会，研究解决安全生产重大问题的安全职责。',
                ktzt:0
            },
            {
                id:4,
                ktzy:'主要负责人',
                gender:'单选题',
                ktnr:'按照《国网北京市电力公司安全生产职责规范》规定：( )具有定期主持召开安全生产委员会议和安全生产月度例会，研究解决安全生产重大问题的安全职责。',
                ktzt:0
            },


		];
		self.customOperations = [
			{
				caption : "修改",
				click: function(item) {
					//点击按钮时触发，item为行数据
					self.newEditor(item)
				}
			},
			{
				caption : "删除",
				click: function(item) {
					//点击按钮时触发，item为行数据
					self.isShowDialog(true)
				}
			},
			{
				caption : "查看",
				click: function(item) {
					self.newEditor(item)
				}
			}
		];
		self.diaHeight = document.documentElement.clientHeight * 0.7 + 'px';
		self.newEditor = function(item){
			cube.showDialog({
				title : '考题信息',
				width:"70%",
				height:self.diaHeight,
				submitFormOnConfirm : false,
				isShowCloseBtn:false, // 是否显示取消按钮
				isShowConfirmBtn:false, // 是否显示保存按钮
				templateOptions : {
					name : 'examine.newEditor.main',
					params : {item:item}
				}
			});
		}
		self.style ="background:#cccccc;";
		self.pageSize = 10;

		cube.endViewModel(self, params);
	};
	return PageViewModel;
});