define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {

		var self = this;
		self.id=params.item
        var color = ["#FFC2C2", "#FF8C8C", "#FFD4AB", "#FFB46E", "#FFEE99", "#FFE048", ];
		self.option = cube.obj ();

		RiskList()
		// var data = ko.observableArray([]);
		var data =	[
			{
				"name": "故障",
				"percent": '',
				"value":''
			},
			{
				"name": "违章",
				"percent":0,
				"value": 0
			},
			{
				"name": "负面清单",
				"percent":0,
				"value": 0
			},
			{
				"name": "黑名单",
				"percent": 0,
				"value": 0
			}
		];
		init(data)
		function init(data){
				var max = data[0].value;
				data.forEach(function(d) {
					max = d.value > max ? d.value : max;
				});

				var renderData = [{
					value: [],
					name: [],
					symbol: 'none',
					lineStyle: {
						normal: {
							color: '#ecc03e',
							width: 2
						}
					}
				}];


				data.forEach(function(d, i) {
					// var value = data.map(d => '');
					var value = data.map(function(item) {
						return ''
					});
					value[i] = d.value;
					renderData[0].value[i] = d.value;
					renderData[0].name[i] = d.name;
					renderData.push({
						value: value,
						symbol: 'circle',
						symbolSize: d.percent * 50,
						lineStyle: {
							normal: {
								color: 'transparent'
							}
						},
						itemStyle: {
							normal: {
								color: color[i],
							}
						}
					})
				})
				var indicator = [];

				data.forEach(function(d) {
					indicator.push({
						name: d.name,
						max: max + 10,
						color: '#fff'
					})
				})

				option1 = {
					tooltip: {
						show: true,
						trigger: "item",
						formatter: function(p){
							// var contentArr = data.map((d) => `${d.name}: ${d.value}, 占比：${d.percent*100}%`);
							var contentArr = data.map(function(d)  {
								return `${d.name}: ${d.value}, 占比：${d.percent*100}%`
							});
							contentArr.unshift('分析');
							return contentArr.join('<br />')
						}
					},
					radar: {
						center: ["50%", "50%"],
						radius: "70%",
						startAngle: 90, // 起始角度
						splitNumber: 4,
						shape: "circle",
						splitArea: {
							areaStyle: {
								color: 'transparent'
							}
						},
						axisLabel: {
							show: false,
							fontSize: 20,
							color: "#000",
							fontStyle: "normal",
							fontWeight: "normal"
						},
						axisLine: {
							show: true,
							lineStyle: {
								color: "rgba(255, 255, 255, 0.5)"
							}
						},
						splitLine: {
							show: true,
							lineStyle: {
								color: "rgba(255, 255, 255, 0.5)"
							}
						},
						indicator: indicator
					},
					series: [{
						type: "radar",
						data: renderData
					}]
				}
				self.option(option1)
		}
		self.style ="background:#cccccc;";
        self.columns = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "type", width:"10%", caption: "类型" , editorType: "TextEditor",align : "center",
				// renderCell: function (cellValue, element, dictValue) {
				// 	return '故障'
				// },
			},
            {name: "number", width:"10%", caption: "数量" , editorType: "TextEditor",align : "center"},
            {name: "proportion", width:"10%", caption: "占比" , editorType: "TextEditor",align : "center" },
            {name: "score", width:"10%", caption: "评分" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent =ko.observableArray([]);


		function RiskList(searchParams) {
			var params = { "id":self.id}
			axios.post(cube.gatewayURL2 + "accidentInformation/safetyAnalysis",params).then(function(res) {

				if (true) {
					var list=res.data.resultValue[0]
					// console.log(list)
					if(list){
						for(var i=0;i<list.length;i++){
							// data.push(list[i])
							// 	data.push({
							// 		name:list[i]['name'],
							// 		percent:list[i]['percent'],
							// 		value:list[i]['value'],
							// 	})
							switch(data[i]['name']){
								case '故障':
									data[i]['value'] = list[0].value;
									data[i]['percent'] =  list[0].percent;
									break;
								case '违章':
	
									data[i]['value'] = list[1].value;
									data[i]['percent'] = list[1].percent;
									break;
								case '负面清单':
	
									data[i]['value'] = list[2].value;
									data[i]['percent'] = list[2].percent;
									break;
								case '黑名单':
									data[i]['value'] = list[3].value;
									data[i]['percent'] = list[3].percent;
									break;
							}
						}

						// console.log(data)


					var list4=res.data.resultValue[1]
					// console.log(list4)
					// for(var i = 0;i<list4.length;i++){
					// 	self.cityContent.push({
					// 		type:list4[i]['type'],
					// 		number:list4[i]['number'],
					// 		proportion:list4[i]['proportion'],
					// 		score:list4[i]['score'],
					// 	})
					// 	console.log(self.cityContent())
					// }

					self.cityContent(list4);
					init(data)


					}
					
					
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});