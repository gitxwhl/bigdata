define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
		self.StudyTitle='';//学习标题
		//学习类型
		self.StudyTypeContent=ko.observableArray();
		self.StudyType='';
		self.width = '100%';
		self.height = '310px';
		self.heightMap = '620px';
		self.style ="background:#cccccc;";
		self.isShowRowNumber = true;
		self.isShowCheckBox = false;
		self.isShowCheckBoxs = false;
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
		self.onSelectedItems = function(e) {
			selectedItems = e;
		};
		self.columns = [
			{name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
			{name: "personName", width:"150px", readOnly:true, caption: "学习编号" , editorType: "TextEditor",
				renderCell: function (cellValue, element, dictValue) {
					element.style.color = "#0ec4da";
				},
				// onClick: function (pkValue, cellValue) {
				// 	self.goDetail(pkValue);
				// }
				},
			{name: "gender", width:"280px", readOnly:true, caption: "学习标题" , editorType: "TextEditor",align : "center",},
			{name: "age", width:"150px", caption: "学习类型" , editorType: "TextEditor",align : "center",},
			{name: "certificateName", width:"150px", caption: "发布时间" , editorType: "TextEditor",align : "center",},
			// {name: "belongLevel", width:"150px", caption: "违章" , editorType: "TextEditor",align : "center",},
			// {name: "Certificate_number", width:"150px", caption: "积分" , editorType: "TextEditor",align : "center",},
			// {name: "belongUnit", width:"120px",caption: "联系单位" , editorType: "TextEditor",align : "center",},
			// {name: "major1", width:"120px", caption: "本年度准入" , editorType: "TextEditor",align : "center",},
		];
		self.items=[
			{
				id:1,
				personName:'XXXXXXX001',
				gender:"电力安全事故应急处置和调查处置条例",
				age:'文档',
				certificateName:'2019-12-21 16:29:32',
			},
			{
				id:2,
				personName:'XXXXXXX002',
				gender:"电力安全事故应急处置和调查处置条例",
				age:'文档',
				certificateName:'2019-12-21 16:29:32',
			},
			{
				id:3,
				personName:'XXXXXXX003',
				gender: "电力安全事故应急处置和调查处置条例",
				age:"视频",
				certificateName:'2019-12-21 16:29:32',
			},


		]





















		cube.endViewModel(self, params);
	};
	return PageViewModel;
});
