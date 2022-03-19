define([], function() {

	/**
	 * 导入Excel，与表格组件配合使用
	 * @ignore
	 */
	function ViewModel(params) {
		var self = this;
		
		/**
		 * 导出Excel后台处理url
		 *  @ignore
		 */
		self.importExcelUrl = params.importExcelUrl;
		
		/**
		 * 导出Excel所在表格的url
		 *  @ignore
		 */
		self.previewGridBaseUrl = params.previewGridBaseUrl;
		
		/**
		 * 页面跳转标示
		 *  @ignore
		 */
		self.step = cube.obj(1);
		
		/**
		 * 导入的列得选择值
		 *  @ignore
		 */
		self.columnsValue = cube.obj(params.value);
		
		/**
		 * 预览表格列信息
		 *  @ignore
		 */
		self.columns =  cube.array([]);
		
		/**
		 * 导入列默认值
		 *  @ignore
		 */
		self.columnNames = params.columnNames;
		
		/**
		 * 预览表格行数据
		 *  @ignore
		 */
		self.items = cube.array([]);
		
		/**
		 * 是否显示配置项
		 */
		self.showOption = typeof(params.showOption) == "undefined"? true : params.showOption;
		
		/**
		 * 组件节点
		 *  @ignore
		 */
		self.$e = null;
		
		/**
		 * 导入Excel配置默认值
		 *  @ignore
		 */
		self.importExcelOptions = {
			horizontalBegin : cube.obj(1), 
			horizontalEnd : cube.obj(0), 
			verticalBegin : cube.obj(1), 
			verticalEnd : cube.obj(0), 
			sheetNumber : cube.obj(1), 
			separatorNum : cube.obj(0),
			saveByDefault: cube.obj(true)
		};
		
		/**
		 * 配置表单信息
		 *  @ignore
		 */
		self.columnFields = [
			{ 
				caption: cube.msg("IMPORT_OPTIONS"), 
				icon: "th-list", 
				isLine: true,
				visible: self.showOption
			}, {
				name : "column",
				caption : cube.msg("IMPORT_COLUMN"),
				editorType : "CheckListEditor",
				colspan: 3,
				width: "90%",
				captionWidth: "100px",
				list: params.columns,
				value: cube.isArray(self.columnNames) && self.columnNames.length > 0 ? self.columnNames : params.value,
				nullable: false,
				visible: self.showOption,
				onChanged: function(value){
					self.columnsValue(value);
				}
			}, { 
				caption: cube.msg("IMPORT_IMPORT_OPTIONS"),
				icon: "cog", 
				isLine: true,
				visible: self.showOption
			}, {
				name : "horizontalBegin",
				caption : cube.msg("IMPORT_EXCEL_HORIZONTALBEGIN"),
				width: "30px",
				captionWidth: "100px",
				nullable: false,
				visible: self.showOption,
				validType: "PLUSINTEGER",
				validOptions: {
					minValue: 1
				},
				value : self.importExcelOptions.horizontalBegin
			},{
				name : "horizontalEnd",
				caption : cube.msg("IMPORT_EXCEL_END"),
				width: "30px",
				captionWidth: "100px",
				nullable: false,
				visible: self.showOption,
				validType: "PLUSINTEGER",
				validOptions: {
					minValue: 0
				},
				value : self.importExcelOptions.horizontalEnd
			},{
				name : "verticalBegin",
				caption : cube.msg("IMPORT_EXCEL_VERTICALBEGIN"),
				width: "30px",
				captionWidth: "100px",
				nullable: false,
				visible: self.showOption,
				validType: "PLUSINTEGER",
				validOptions: {
					minValue: 1
				},
				value : self.importExcelOptions.verticalBegin
			},{
				name : "verticalEnd",
				caption : cube.msg("IMPORT_EXCEL_VERTICALEND"),
				width: "30px",
				captionWidth: "100px",
				nullable: false,
				visible: self.showOption,
				validType: "PLUSINTEGER",
				validOptions: {
					minValue: 0
				},
				value : self.importExcelOptions.verticalEnd
			},{
				name : "sheetNumber",
				caption : cube.msg("IMPORT_EXCEL_SHEETNUMBER"),
				width: "30px",
				captionWidth: "100px",
				nullable: false,
				visible: self.showOption,
				validType: "PLUSINTEGER",
				validOptions: {
					minValue: 1
				},
				value : self.importExcelOptions.sheetNumber
			},{
				name : "separatorNum",
				caption : cube.msg("IMPORT_EXCEL_SEPRATORNUM"),
				width: "30px",
				captionWidth: "100px",
				nullable: false,
				visible: self.showOption,
				value : self.importExcelOptions.separatorNum
			}, { 
				caption: cube.msg("IMPORT_EXCEL_WIZARD_UPLOAD_EXCEL"),
				icon: "file", 
				isLine: true
			}, {
				name : "excelFile",
				caption : cube.msg("IMPORT_EXCEL_SELECT_FILE"),
				editorType: "FileEditor",
				captionWidth: "100px",
				colspan: 3,
				previewSize: "mini",
				allowTypes: "xls, xlsx",
				maxCount: 1,
				nullable: false,
				autoLoadFiles: false,
				validOptions: {
					validateMessage: cube.msg("IMPORT_EXCEL_SELECT_AND_UPLOAD_FILE")
				},
				validHintType: "inline",
				url: self.importExcelUrl,
				onItemUploading: function(args) {
					var columnCaptions = [];
					var columnNames = self.columnsValue().split(",");
					var columns = params.columns;
					self.columns([]);
					for (var i = 0; i < columns.length; i++) {
						if (columnNames.indexOf(columns[i].value) != -1) {
							columnCaptions.push(columns[i].text);
							self.columns.push({name: columns[i].value, caption: columns[i].text});
						}
					}
					
					args.fileUploaderParams = {
							columnNames: columnNames,
							columnCaptions: columnCaptions.join(),
							impOptions: self.importExcelOptions.horizontalBegin() + "," 
										+ self.importExcelOptions.horizontalEnd() + "," 
										+ self.importExcelOptions.verticalBegin() + "," 
										+ self.importExcelOptions.verticalEnd() + "," 
										+ self.importExcelOptions.sheetNumber() + ","
										+ self.importExcelOptions.separatorNum()
					}
				},
				onAllItemUploaded: function(name, items, result) {
					if (cube.notEmpty(result)) {
						result = $.parseJSON(result);
						if (result.successful === true) {
							self.items(result.resultValue.items);
						}
					}
					
					self.step(2);
					params.dialog.customBtns()[0].visible(true);
				}
			}];
		
		/**
		 * 在表格的importExcel方法中传入的p_confirmBtnClick参数
		 * @ignore
		 */
		self.confirmBtnClick = params.confirmBtnClick;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			params.dialog.step = self.step;
			
			self.actionsSub = cube.subscribe(params.dialog.step, function(p_value) {
				self.step(p_value);
			});
			
			params.dialog.result = function() {
				var excelForm = cube.getPageViewModelByNode(self.$e.find("#exceltmpl_excelForm"));
				if (!excelForm.validate()) {
					return false;
				}
				
				var excelFormViewModel = cube.getPageViewModelByNode(self.$e.find("#exceltmpl_excelGrid"));
				if (self.confirmBtnClick != null && !cube.isObservable(self.confirmBtnClick)) {
					self.confirmBtnClick(excelFormViewModel.items());
				} else {
		       		excelFormViewModel.save();
				}
	       		
	       		return true;
			}
		}
		
		/**
		 * @ignore
		 */
		self.onload = function(node) {
			self.$e = $(node);
		}
	};

	return ViewModel;
});
