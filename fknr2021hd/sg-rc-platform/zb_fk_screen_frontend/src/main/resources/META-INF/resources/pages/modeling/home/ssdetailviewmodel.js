define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
		self.id=params.item.TYPE_ID
		// console.log(self.id)
		self.isEditor = false;
		self.releaseUnit =[
			{id:'',text:'全部'},
			{id:'01',text:'作业计划'},
			{id:'02',text:'外包单位'},
			{id:'03',text:'人员'},
			{id:'04',text:'风险'},
			{id:'05',text:'隐患'},
			{id:'06',text:'事件'},
			{id:'07',text:'违章'},
		];
		self.name = '';
		self.code = '';
		self.content = '';
        setTimeout(function(){
        	//查看
			var params = {"typeId": self.id()}
			axios.post(cube.gatewayURL2 + "atlasInformation/getModenlingDetails",params).then(function(res) {
				if (res.data.successful==true) {
					var risklist=res.data.resultValue
					  console.log(risklist)
						self.name(risklist.TYPE_NAME);
						self.code(risklist.TYPE_CODE);
						self.content(risklist.TYPE_DESCRIBE);
						var img=document.querySelector('#img')
						if(risklist.TYPE_ICON==='u12012'){
							 img.src='pages/hapMap/icon/u12012.png'
						}else if(risklist.TYPE_ICON==='u12014'){
							img.src='pages/hapMap/icon/u12014.png'
						}else if(risklist.TYPE_ICON==='u12016'){
							img.src='pages/hapMap/icon/u12016.png'
						}else if(risklist.TYPE_ICON==='u12018'){
							img.src='pages/hapMap/icon/u12018.png'
						}else if(risklist.TYPE_ICON==='u12020'){
							img.src='pages/hapMap/icon/u12020.png'
						}else if(risklist.TYPE_ICON==='u12022'){
							img.src='pages/hapMap/icon/u12022.png'
						}else if(risklist.TYPE_ICON==='u12024'){
							img.src='pages/hapMap/icon/u12024.png'
						}

				} else {
					cube.indicate("数据加载失败");
				}

			})


			// self.isEditor(params.isEditor)
			// if(params.item){
			// 	var data = params.item;
			// 	self.name(data.name);
			// 	self.code(data.code);
			// 	self.content(data.content);
            // }

		})
		//保存
		self.save = function(){
			//编辑
			var params ={
				params:{
				"typeId":self.id(),
				"typeName":self.name(),
				"typeCode":self.code(),
				"typeDescribe":self.content(),
				"typeIcon":"XX"
			}
			}
			axios.post(cube.gatewayURL2 + "atlasInformation/updateModeling",params).then(function(res) {
				if (res.data.successful==true) {
				} else {
					cube.indicate("数据加载失败");
				}

			})

		}
		self.gbClick = function(){
            params.dialog.closeDialog();
		}

		cube.endViewModel(self, params);
	};
	return PageViewModel;
});