define(["FileUploader", "cryptocore", "md5", "JSONUtil"], function(FileUploader, CryptoJS, md5, jsonUtil) {

	/**
	 * 文件组件
	 * <code language="html">
	 *		<fileeditor params="
	 *						allowTypes: allowTypes,
	 *						url: url,
	 *						isShowPreview: isShowPreview,
	 *						maxCount: maxCount,
	 *						maxFileSize: maxFileSize
	 *					"></fileeditor>
	 * </code>
	 * <code language="JavaScript">
	 *		define([], function() {
	 *			var PageViewModel = function(params) {
	 *				var self = this;
	 *				
	 *				self.allowTypes = "jpg,jpeg,gif,png,bmp,tiff,txt";
	 *				self.url = '';
	 *				self.isShowPreview = false;
	 *				self.maxCount = 0;
	 *				self.maxFileSize = 0; 
	 * 
	 *				cube.endViewModel(self, params);
	 *			};
	 *			return PageViewModel;
	 *		});
	 * </code>
	 * 
	 */
	function ViewModel(params) {
		var self = this;

		/**
		 * 表单ID
		 * @ignore
		 */
		self.id = "";
		
		/**
		 * 编辑器名称
		 * 
		 * @default 空
		 */
		self.name = '';
		
		/**
		 * 文件上传URL
		 * @default 空
		 */
		self.url = "";
		
		/**
		 * 文件删除URL，该属性一般情况加不用设置，删除操作和上传操作同用url属性，当该属性不为空时则删除操作使用该地址
		 * 
		 *  @default 空
		 */
		self.removeUrl = "";
		
		/**
		 * 是否为只读
		 * 
		 * @default false
		 */
		self.readOnly = false;
		
		/**
		 * 上传文件时是否显示文件预览
		 * @default true
		 */
		self.isShowPreview = true;
		
		/**
		 * 预览图大小，可选big、small、mini
		 * @default big
		 */
		self.previewSize = "big";
		
		/**
		 * 是否为预览模式，预览模式只可查看文件，不能编辑
		 * @default false
		 */
		self.previewModel = false;
		
		/**
		 * 选择文件后是否自动上传文件。
		 * @default false
		 */
		self.autoUpload = false;
		
		/**
		 * 是否显示上传按钮
		 * @default true
		 */
		self.isShowUploadButton = true;
		
		/**
		 * 是否显示删除按钮
		 * @default true
		 */
		self.isShowRemoveButton = true;
		
		/**
		 * 是否显示下载按钮
		 * @default true
		 */
		self.isShowDownloadButton = true;
		
		/**
		 * 是否显示文件缩略图
		 * @default true
		 */
		self.isShowThumbnail = true;
		
		/**
		 * 是否显示输入框和上传按钮，当为false时需通过自定义按钮调用组件showDialog方法打开文件上传窗口，上传完成之后显示已上传文件名称
		 * @default true
		 */
		self.isShowControl = true;
		
		/**
		 * 最大上传文件个数
		 * @default 0 不限制
		 */
		self.maxCount = 0;
		
		/**
		 * 最大上传文件大小，单位：KB
		 * @default 0 不限制
		 */
		self.maxFileSize = 0;
		
		/**
		 * 允许上传文件类型
		 * @default jpg,jpeg,gif,png,bmp,tiff,txt
		 */
		self.allowTypes = "jpg,jpeg,gif,png,bmp,tiff,txt";
		
		/**
		 * 设置或获取一个字符串，表示当前文件编辑器的类型。 当类型为 path 时，表示管理指定目录下的文件； 当类型为 form 或 grid
		 * 时，表示管理表单、表格对应的数据的附件。
		 * 该属性必须设置
		 * <p>
		 * 该字段可选的值包括：
		 * <ul>
		 * <li><b>path</b></li>
		 * <li><b>form</b></li>
		 * <li><b>grid</b></li>
		 * </ul>
		 * </p>
		 * @default path
		 */
		self.type = "path";

		/**
		 * 设置或获取一个字符串，表示上传文件在服务器上的保存路径。
		 * 如果{@link type}属性设置为path，则需要设置该属性
		 * 
		 *  @default null
		 */
		self.filePath = null;

		/**
		 * 设置或获取一个字符串，表示上传控件所属数据的表名。 当控件类型为 form 时，此属性有值。
		 * 当{@link type}属性的值设置为form或者grid时，需要设置该属性
		 * 
		 * @default 空
		 */
		self.tableName = "";

		/**
		 * 设置或获取一个字符串，表示上传控件所属数据表的主键列名。 当控件类型为 form 时，此属性有值。
		 * 当{@link type}属性的值设置为form或者grid时，需要设置该属性
		 * 
		 * @default 空
		 */
		self.primaryKey = "";

		/**
		 * 设置或获取一个字符串，表示上传控件所属数据的主键值。 当控件类型为 form 时，此属性有意义；若此时此属性没有值，控件不会响应点击事件。
		 * 当{@link type}属性的值设置为form或者grid时，需要设置该属性
		 * 
		 * @default 空
		 */
		self.pkVal = "";

		/**
		 * 设置或获取一个字符串，表示上传控件在存储表单数据的表中对应的列名。 当控件类型为 form 时，此属性有值。
		 * 当{@link type}属性的值设置为form或者grid时，需要设置该属性
		 * 
		 * @default 空
		 */
		self.colName = "";

		/**
		 * 设置或获取一个字符串，表示保存文件的方式。 当控件类型为 form 时，此属性有意义。
		 * <p>
		 * 该字段可选的值包括：
		 * <ul>
		 * <li><b>file</b>表示文件以磁盘文件方式存储</li>
		 * <li><b>blob</b>表示文件以blob 类型存储在系统表的附件表中</li>
		 * <li><b>uds</b>表示文件存在非结构化系统中</li>
		 * <li><b>ftp</b>表示文件存在ftp中，ftp账号等信息需在平台platformconfig中进行配置，添加键值对："MX_FTP_SECURITY = 主机;端口;用户名;密码"，匿名时用户名、密码可以为空，但是分号不能省略；必须使用{@link filePath}属性设置上传目录。
		 * </li>
		 * </ul>
		 * </p>
		 * 
		 * @default file
		 */
		self.uploadMode = "file";
		
		/**
		 * 传递到后台的其他参数，设置该参数将会在url后拼接key为params的参数值
		 * @default 空
		 */
		self.params = "";
		
		/**
		 * 上传文件时params参数时是否使用post方式提交，否则参数将会在url后拼接key为params的参数值
		 * 
		 * @default false
		 */
		self.paramsByPost = false;
		
		/**
		 * 获取或设置input是否虚拟，
		 * 如果值是 true ，则不会提交编辑器的值。
		 * 
		 * @default false
		 */
		self.isVirtual = false;
		
		/**
		 * 初始化时是否自动加载文件
		 * @default true
		 */
		self.autoLoadFiles = true;
		
		/**
		 * 是否获取文件的md5值，如果为true，则将获取的文件md5值赋值给file对象的md5属性，同时上传时将文件md5值放置到request Header的fileMD5中，注意该属性只支持html5。
		 * 例：可以在onItemUploading事件中获取文件md5值进行前期验证，示例中的self.checkFileMd5方法需自定义
		 *	onItemUploading:function(args){
		 *		var result = self.checkFileMd5(args.item.file.md5);
		 *		if(!result){
		 *			cube.indicate("warning","该文件已存在，请重新选择新的附件上传！");
		 *			args.cancel = true;//当args.cancel赋值为true时将取消上传
		 *		}
		 *	}
		 *	
		 *	后台java中可通过request.getHeader("fileMD5")方法获取文件的md5值。
		 *	
		 * @default false
		 * 
		 */
		self.isGetMd5 = false;
		
		/**
		 * @ignore
		 */
		self.isIE = false;
		
		/**
		 * 上传项删除之前触发该事件。
		 * @param args 该参数有两个属性，cancel：当赋值为true时将取消删除操作，item：删除项信息
		 */
		self.onItemRemoving = null;
		
		/**
		 * 上传项删除之后触发该事件。
		 * @param item 文件信息
		 * @param result 后台返回信息
		 */
		self.onItemRemoved = null;
		
		/**
		 * 清除上传项之前触发该事件。
		 * @param args 该参数有两个属性，cancel：当赋值为true时将取消删除操作，items：上传项信息
		 */
		self.onAllItemRemoving = null;
		
		/**
		 * 清除上传项之后触发该事件。
		 */
		self.onAllItemRemoved = null;
		
		/**
		 * 上传项上传之前触发该事件。
		 * @param args 该参数有4个属性：
		 *	cancel：当赋值为true时将取消上传操作，
		 *	item：上传项信息，
		 *	params：设置该值时将覆盖组件的params属性值（即拼接在url中key为params的参数值），
		 *	fileUploaderParams：设置该值时将重置上传时的所有参数（即url中所有的参数）
		 */
		self.onItemUploading = null;
		
		/**
		 * 上传项上传之后触发该事件。
		 * @param item 文件信息
		 * @param result 后台返回信息
		 * @param args 该参数有一个cancel属性，当设置args.cancel = true时则取消已上传状态
		 */
		self.onItemUploaded = null;
		
		/**
		 * 上传项上传失败之后触发该事件。
		 * @param item 文件信息
		 * @param result 后台返回信息
		 */
		self.onItemUploadFail = null;
		
		/**
		 * 所有附件上传完毕后触发该事件
		 * @param name 编辑器名称
		 * @param items 文件列表
		 * @param result 后台返回信息
		 */
		self.onAllItemUploaded = null;
		
		/**
		 * 下载文件之前触发该事件，回调事件返回false时终止下载操作。
		 * @param item 文件信息
		 */
		self.onDownloading = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * @ignore
		 */
		self.onChanged = null;

		/**
		 * 待上传文件
		 * @ignore
		 */
		self._items = [];
		
		/**
		 * 无效文件
		 * @ignore
		 */
		self._validitems = [];
		
		/**
		 * @ignore
		 */
		self._files = [];
		
		/**
		 * 是否显示错误信息
		 * @ignore
		 */
		self._showErrorMessage = false;
		
		/**
		 * 已上传文件数
		 * @ignore
		 */
		self._uploadedCount = 0;
		
		/**
		 * 未上传文件数
		 * @ignore
		 */
		self._unuploadCount = 0;
		
		/**
		 * @ignore
		 */
		self._fileAccept = "";
		
		/**
		 *  @ignore 
		 *  存储ie下已经选择的文件，如果已经选择之后将不能选择相同文件
		 */
		self.ieHasFile = [];
		
		/**
		 * @ignore
		 */
		self.animatShowDialog = false;
		
		/**
		 * 整体上传进度
		 * @ignore
		 */
		self.progress = "0%";
		
		/**
		 * 要上传的文件数
		 */
		var uploadCount = 0;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			
			if (typeof FileReader == "function") {
				self.isIE(false);
			} else {
				self.isIE(true);
			}
			
			self._fileAccept("." + self.allowTypes().replace(/\,/g, ",."));
			
			if(cube.isEmpty(self.id())){
				self.id("fileForm_" + cube.random().toString().split("&")[0]);
			}
			
			self._validitemsSub = cube.subscribe(self._validitems, function(){
				if(self._validitems().length > 0){
					self._showErrorMessage(true);
				}else{
					self._showErrorMessage(false);
				}
			});
			
			if (cube.notEmpty(self.url())) {
				var fileUploader = new FileUploader();
				
				fileUploader.url = self.url();
				fileUploader.params = getParam();
				
				var fileList = [];
				if (self.autoLoadFiles()) {
					fileList = fileUploader.getUploadedFiles();
				}
				
				for(var i =0; i < fileList.length; i++){
					var css = getFileIcon(fileList[i].attName);
					self._items.push({
						index: i+1, 
						name: fileList[i].attName,
						css: css,
						isImage: css=="icon-picture"?true:false, 
						isIE: false, 
						src: css=="icon-picture"?_getUri(fileList[i].attName):"",
						status: cube.obj(cube.msg("UPLOADED")),
						progress: cube.obj("100%")
					});
				}
				
				self._onChanged();
			}
		};

		/**
		 * 添加文件显示预览框
		 * @ignore
		 */
		var fileId = 1;
		self.fileChange = function(viewModel, event) {
			self._validitems.removeAll();
			if (typeof FileReader == "function") {
				var files = event.target.files;
				if (files && files.length > 0) {
					for ( var i = 0; i < files.length; i++) {
						_preview(files[i]);
					}
				}
				
				//清空file控件的值
				var file = $(event.target);
				var fileClone = file.clone().val("");
				file.after(fileClone);
				file.remove();
				
				fileClone.attr("data-bind", "event: {change: fileChange}, attr:{disabled: readOnly, accept: _fileAccept}");  
				cube.bindTemlate(fileClone, {fileChange: self.fileChange, readOnly: self.readOnly, _fileAccept: self._fileAccept});
			} else {
				_previewIE(event.target, fileId);
				
				event.target.disabled = true;
				event.target.style.display = "none";
				
				fileId = cube.random().toString().split("&")[0];
				var id = "file_" + fileId;
				var fileInput = "<input name='file' id='" + id + "' type='file'>";
				self._files.push(fileInput);
				
				var $fileInput = $("#"+id);
				$fileInput.attr("data-bind", "event: {change: fileChange}, attr:{disabled: readOnly, accept: _fileAccept}");  
				cube.bindTemlate($fileInput, {fileChange: self.fileChange, readOnly: self.readOnly, _fileAccept: self._fileAccept});
			}
		};

		/**
		 * 删除文件
		 * @ignore
		 */
		self.removeFile = function() {
			var item = this;
			cube.indicate("confirm", cube.msg("CONFIRM_DELETE"), function() {
				var success = true;
				if (self.onItemRemoving != null && !cube.isObservable(self.onItemRemoving)) {
					var args = {cancel: false, item : item};
					self.onItemRemoving(args);
					if(args.cancel) {
						return;
					}
				}
				
				var vitem = jsonUtil.getItem(self._validitems(), "name", item.name);
				if (vitem) {
					self._validitems.remove(vitem);
				}
				
				var _index = item.index;
				if (item.status() == cube.msg("UPLOADED") || item.status() == cube.msg("DELETEFAIL")) {
					var fileUploader = new FileUploader();
					
					var url = self.url();
					var removeUrl = self.removeUrl();
					if (cube.notEmpty(removeUrl)) {
						url = removeUrl;
					}
					
					fileUploader.url = url;
					fileUploader.params = getParam();
					
					var result = fileUploader.removeFile(item.name);
					if (result) {
						self._items.remove(item);
						if (cube.notEmpty(item.path)) {
							self.ieHasFile.remove(item.path);
						}
						self._uploadedCount(self._uploadedCount()-1);
						
						if (self.onItemRemoved != null && !cube.isObservable(self.onItemRemoved)) {
							self.onItemRemoved(item, result);
						}
						self._onChanged();
						
					} else {
						item.status(cube.msg("DELETEFAIL"));
						success = false;
					}
				} else {
					self._items.remove(item);
					if (cube.notEmpty(item.path)) {
						self.ieHasFile.remove(item.path);
					}
					self._unuploadCount(self._unuploadCount()-1);
					
					if (self.onItemRemoved != null && !cube.isObservable(self.onItemRemoved)) {
						self.onItemRemoved(item);
					}
				}
				
				if (success) {
					var input = $("#" + self.id()).find("input");
					if (input) {
						var index = 1;
						input.each(function(){
							if (self.isIE()) {
								if (index == _index) {
									$(item).remove();
									self._items.remove(index);
								}
							} else {
								$(item).val("");  
							}  
							index++;
						});
						
						var _input = $("#" + self.id()).find("input");
						if (_input && _input.length == 0) {
							fileId = cube.random().toString().split("&")[0];
							var id = "file_" + fileId;
							var fileInput = "<input name='file' id='" + id + "' type='file'>";
							self._files.push(fileInput);
							
							var $fileInput = $("#"+id);
							$fileInput.attr("data-bind", "event: {change: fileChange}, attr:{disabled: readOnly, accept: _fileAccept}");  
							cube.bindTemlate($fileInput, {fileChange: self.fileChange, readOnly: self.readOnly, _fileAccept: self._fileAccept});
						}
					}
				}
			});
		};

		/**
		 * 清空文件
		 * @param p_deletedata 是否删除数据库数据，默认true
		 */
		self.clearFiles = function(p_deletedata) {
			if (self._items().length > 0) {
				cube.indicate("confirm", cube.msg("CONFIRM_DELETEALL"), function() {
					self._clearFiles(p_deletedata);
				});
			}
		};
		
		/**
		 * 清空文件，不弹出确认窗口
		 * @param p_deletedata 是否删除数据库数据，默认true
		 */
		self.clearFilesWithoutConfirm = function(p_deletedata) {
			if (self._items().length > 0) {
				self._clearFiles(p_deletedata);
			}
		};
		
		/**
		 *  @ignore
		 */
		self._clearFiles = function(p_deletedata) {
			if (self.onAllItemRemoving != null && !cube.isObservable(self.onAllItemRemoving)) {
				var args = {cancel: false, items : self._items()};
				self.onAllItemRemoving(args);
				if(args.cancel) {
					return;
				}
			}
			
			self._validitems.removeAll();
			
			if (p_deletedata != false) {
				for(var i = self._items().length -1; i >= 0; i--){
					var success = true;
					if (self._items()[i].status() == cube.msg("UPLOADED") || self._items()[i].status() == cube.msg("DELETEFAIL")) {
						var fileUploader = new FileUploader();
						
						var url = self.url();
						var removeUrl = self.removeUrl();
						if (cube.notEmpty(removeUrl)) {
							url = removeUrl;
						}
						
						fileUploader.url = url;
						fileUploader.params = getParam();
						var result = fileUploader.removeFile(self._items()[i].name);
						if (result) {
							if (cube.notEmpty(self._items()[i].path)) {
								self.ieHasFile.remove(self._items()[i].path);
							}
							self._items.remove(self._items()[i]);
							self._uploadedCount(self._uploadedCount()-1);
						} else {
							self._items()[i].status(cube.msg("DELETEFAIL"));
							success = false;
						}
					} else {
						if (cube.notEmpty(self._items()[i].path)) {
							self.ieHasFile.remove(self._items()[i].path);
						}
						self._items.remove(self._items()[i]);
						self._unuploadCount(self._unuploadCount()-1);
					}
					
					if (success) {
						var input = $("#" + self.id()).find("input");
						if (input && input.eq(i)) {
							if (self.isIE()) {
								input.eq(i).remove();
							} else {
								input.eq(i).val("");  
							} 
							
							var _input = $("#" + self.id()).find("input");
							if (_input && _input.length == 0) {
								fileId = cube.random().toString().split("&")[0];
								var id = "file_" + fileId;
								var fileInput = "<input name='file' id='" + id + "' type='file'>";
								self._files.push(fileInput);
								
								var $fileInput = $("#"+id);
								$fileInput.attr("data-bind", "event: {change: fileChange}, attr:{disabled: readOnly, accept: _fileAccept}");  
								cube.bindTemlate($fileInput, {fileChange: self.fileChange, readOnly: self.readOnly, _fileAccept: self._fileAccept});
							}
						}
					}
				}
			}
			
			if (self._items().length == 0) {
				self._unuploadCount(0);
				self._uploadedCount(0);
				
				if (self.onAllItemRemoved != null && !cube.isObservable(self.onAllItemRemoved)) {
					self.onAllItemRemoved();
				}
				self._onChanged();
			}
		};

		/**
		 * 上传文件
		 * @ignore
		 */
		self.uploadFile = function(item) {
			if (self.isIE()) {
				ieUploadFile(item);
			} else {
				upload(item);
			}
		};
		
		/**
		 * 上传所有文件
		 */
		self.uploadFiles = function() {
			self._validitems.removeAll();
			uploadCount = self._unuploadCount();
			if (!self.isShowControl()) {
				self.closeDialog();
			}
			
			for(var i = 0; i < self._items().length; i++){
				if(self._items()[i].status() != cube.msg("UPLOADED")){
					if (self.isIE()) {
						ieUploadFile(self._items()[i]);
					} else {
						upload(self._items()[i]);
					}
				}
			}			
		};
		
		/**
		 * 下载文件
		 * @ignore
		 */
		self.downloadFile = function() {
			
			if (self.onDownloading != null && !cube.isObservable(self.onDownloading)) {
				var ret = self.onDownloading(this);
				if (ret == false) {
					return;
				}
			}
			
			var fileUploader = new  FileUploader();
			fileUploader.url = self.url();
			fileUploader.params = getParam();
			
			fileUploader.onprogresschange = function(progress){
				item.progress(progress + "%");
			};
			
			fileUploader.onprogresscomplete = function(){
				item.status(cube.msg("DOWNLOADED"));
			};
			
			fileUploader.onprogresserror = function(){
				item.status(cube.msg("DOWNLOADFAIL"));
			};
			
			fileUploader.oncanceled = function(){
				item.status(cube.msg("DOWNLOADCANCEL"));
			};
			
			fileUploader.download(this.name);
		};
		
		/**
		 * 隐藏预览框
		 * @ignore
		 */
		self.hidePreview = function() {
			self.isShowPreview(false);
		};
		
		/**
		 * 渲染出预览的图片
		 * @ignore 
		 */
		var chunkSize = 204800;  
		var pos = 0; 
		function _preview(file) {
			if (typeof FileReader == "function") {
				if (_allowUpload(file.name) && _allowMaxFileSize(file.name, file.size)) {
					if (file.type.indexOf("image/") != -1) {
						var reader = new FileReader();
						reader.onload = function(e) {
							if(_allowMaxCount(file.name)){
								self._unuploadCount(self._unuploadCount()+1);
								var item = {
									index: self._items().length+1, 
									name: file.name, 
									src: e.target.result, 
									isImage: true, 
									isIE: false, 
									file: file,
									status: cube.obj(cube.msg("NOTUPLOAD")),
									progress: cube.obj("0%")
								};
								self._items.push(item);
								
								if (self.autoUpload()) {
									self.uploadFile(item);
								}
							}
						};
						reader.readAsDataURL(file);
					} else {
						var css = getFileIcon(file.name);
						if(_allowMaxCount(file.name)){
							self._unuploadCount(self._unuploadCount()+1);
							var item = {
								index: self._items().length+1, 
								name: file.name, 
								css: css, 
								isImage: false, 
								file: file,
								status:cube.obj(cube.msg("NOTUPLOAD")),
								progress: cube.obj("0%")
							};
							
							self._items.push(item);
							
							if (self.autoUpload()) {
								self.uploadFile(item);
							}
						}
					}
					
					if (self.isGetMd5()) {
						chunkSize = 204800;  
						pos = 0; 
						var filereader = new FileReader();
						getMd5(file, filereader);
					}
				}
			}
		}
		
		/**
		 * @ignore 
		 */
		self._onChanged = function() {
			if(self.onChanged != null && !cube.isObservable(self.onChanged)){
				var fileNames = "";
				var p_items = self._items();
				for (var i = 0; i < p_items.length; i++){
					fileNames += p_items[i].name + ";";
				}
				
				if (fileNames!="") {
					fileNames = fileNames.substring(0, fileNames.length-1);
				}
				self.onChanged(self.name(), fileNames);
			}
		};
		
		/**
		 * 打开上传文件窗口
		 */
		self.showDialog = function() {
			self.animatShowDialog(true);
		}
		
		/**
		 * 关闭上传文件窗口
		 */
		self.closeDialog = function() {
			self.animatShowDialog(false);
		}
		
		/**
		 * 渲染出预览的图片
		 * @ignore 
		 */
		function _previewIE(target, fileId) {
			var path = "";
			if (window.navigator.userAgent.indexOf("MSIE") > -1) {
				target.select(); 
				$("#" + self.id()).find("#_fileSelectionHiddenArea").focus();
				path = document.selection.createRange().text; 
			}
			
			var ieHasFile = self.ieHasFile();
			if (ieHasFile.indexOf(path) ==-1) {
				self.ieHasFile.push(path);
			} else {
				return;
			}
			
			var filename = getFileName(path);
			
			if (_allowUpload(path) && _allowMaxCount(filename)) {
				if (_isImage(path)) {//图片文件
					var size = self.previewSize() == "big"? 160 : 100;
					var p_scale = { width: size , height:  size};
					var p_$view = $("<div></div>");
					var $img = $("<div style='margin:0px auto;'>");
					$img.css("filter", _imgloader(path, "image"));
				
					if(navigator.userAgent.indexOf("MSIE 9.0")>-1){//ie9需延迟一会才能获取到图片尺寸
						var temp = document.createElement("div");
						(document.body || document.documentElement).appendChild(temp);
						with(temp.runtimeStyle) {
							filter = _imgloader(path,"image");
							zoom = width = height = 1;
							overflow="scroll";
							position = "absolute";
							right = "9999em";
							top = "-9999em";
							border = 0;
						}
						setTimeout(function(){
							var info =  {
								height: temp.offsetHeight,
								width: temp.offsetWidth
							};
							
							temp.parentNode.removeChild(temp);
							var _sizeInfo = _getSizeInfo(p_scale, info);
							$img.appendTo(p_$view);
							$img[0].style.filter = _imgloader(path, "scale");
							$img.width(_sizeInfo.width).height(_sizeInfo.height);
							
							self._unuploadCount(self._unuploadCount()+1);
							var item = {
								path: path,
								index: self._items().length+1, 
								name: filename, 
								view: p_$view.html(),
								isImage: true, 
								isIE: true, 
								fileId: fileId,
								status: cube.obj(cube.msg("NOTUPLOAD")),
								progress: cube.obj("0%")
							};
							
							self._items.push(item);
							
							if (self.autoUpload()) {
								self.uploadFile(item);
							}
							
						},500); 
					} else {
						var _size = _getImgSize(path);
						var _sizeInfo = _getSizeInfo(p_scale, _size);
						$img.appendTo(p_$view);
						$img[0].style.filter = _imgloader(path, "scale");
						$img.width(_sizeInfo.width).height(_sizeInfo.height);
						
						self._unuploadCount(self._unuploadCount()+1);
						var item = {
							path: path,
							index: self._items().length+1, 
							name: filename, 
							view: p_$view.html(),
							isImage: true, 
							isIE: true, 
							fileId: fileId,
							status: cube.obj(cube.msg("NOTUPLOAD")),
							progress: cube.obj("0%")
						};
						self._items.push(item);
						
						if (self.autoUpload()) {
							self.uploadFile(item);
						}
					}
				} else {
					var css = getFileIcon(filename);
					self._unuploadCount(self._unuploadCount()+1);
					var item = {
						path: path,
						index: self._items().length+1, 
						name: filename, 
						css: css, 
						isImage: false, 
						fileId: fileId,
						status:cube.obj(cube.msg("NOTUPLOAD")),
						progress: cube.obj("0%")
					};
					self._items.push(item);
					
					if (self.autoUpload()) {
						self.uploadFile(item);
					}
				}
			}
			
		}
		
		var md5Instance = CryptoJS.algo.MD5.create();  
		function getMd5(file, filereader) {
			var end = Math.min(pos + chunkSize, file.size);  
			filereader.onload = function(e) {  
				pos = end;  
				if (filereader.readAsBinaryString) {
				 	md5Instance.update(CryptoJS.enc.Latin1.parse(e.target.result));  
				} else {
					md5Instance.update(arrayBufferToWordArray(e.target.result));  
				}
				
				var present = (parseFloat(pos)/parseFloat(file.size))*10000/100;  
				if (pos < file.size) {  
					getMd5(file, filereader);  
				} else {  
					var md5Value = md5Instance.finalize();  
					file.md5 = md5Value.toString();
				}  
			}; 
  
			if (file.slice) {  
				var blob = file.slice(pos, end);  
			} else if (file.webkitSlice) {  
				var blob = file.webkitSlice(pos, end);  
			} else if (File.prototype.mozSlice) {  
				var blob = file.mozSlice(pos, end);  
			}  
			
			if (filereader.readAsBinaryString) {
				filereader.readAsBinaryString(blob);  
			} else {
				filereader.readAsArrayBuffer(blob);  
			}
			
		}

		function arrayBufferToWordArray(ab) {
			var i8a = new Uint8Array(ab);
			var a = [];
			for (var i = 0; i < i8a.length; i += 4) {
				a.push(i8a[i] << 24 | i8a[i + 1] << 16 | i8a[i + 2] << 8 | i8a[i + 3]);
			}
			return CryptoJS.lib.WordArray.create(a, i8a.length);
		}

		function _allowMaxCount(filename){
			if(self.maxCount() > 0 && self._items().length >= self.maxCount()){
				self._validitems.push({name: filename, msg: cube.msg("UPLOAD_MAXALLOWED", self.maxCount())});
				return false;
			}
			return true;
		}
		
		function _allowMaxFileSize(filename, filesize){
			filesize = filesize/1024;
			if(self.maxFileSize() > 0 && filesize >= self.maxFileSize()){
				self._validitems.push({name: filename, msg: cube.msg("UPLOAD_SIZE_LIMIT", _getSize(self.maxFileSize()*1024))});
				return false;
			}
			return true;
		}
		
		function _getSize(p_size){
			var result = null;
			if (p_size < 1024){
				result = p_size + "B";
			} else if (p_size >= 1024 && p_size < 1024*1024){
				result = _toDecimal(p_size/1024) + "KB";
			} else {			
				result = _toDecimal(p_size/(1024 * 1024)) + "MB";
			}
			return result;
		}
		
		function _toDecimal(p_value){
			var result = parseFloat(p_value);
			result = isNaN(result) ? 0 : Math.round(p_value*100)/100; 
			return result;
		}
		
		/**
		 * @ignore 文件是否符合格式
		 */
		function _allowUpload(p_name) {
			if (cube.isEmpty(p_name)) {
				return false;
			}
			var arr = p_name.split(".");
			var _fileType = arr[arr.length - 1];
			if (("," + self.allowTypes()).indexOf(_fileType) == -1){
				self._validitems.push({name: p_name, msg: cube.msg("FILE_TYPE_LIMITED", _fileType)});
				return false;
			}
			return true;
		}
		
		/**
		 * @ignore 判断是否为图片
		 */
		function _isImage(p_name) {
			if (cube.isEmpty(p_name)) {
				return false;
			}
			var arr = p_name.split(".");
			var _fileType = arr[arr.length - 1];
			if ((",jpg,jpeg,gif,png,bmp").indexOf(_fileType) == -1){
				return false;
			}
			return true;
		}

		/**
		 * @ignore ie 专用
		 */
		function _imgloader(p_path, p_mode) {
			return "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"
					+ p_path + "', sizingMethod='" + p_mode + "')";
		}
		
		/**
		 * @ignore
		 * 获取图片的大小 ie 专用
		 */
		function _getImgSize(p_path)
		{
			var temp = document.createElement("div");
			(document.body || document.documentElement).appendChild(temp);
			with(temp.runtimeStyle)
			{
				filter = _imgloader(p_path,"image");
				zoom = width = height = 1;
				position = "absolute";
				right = "9999em";
				top = "-9999em";
				border = 0;
			}
			var info = 
			{
				height: temp.offsetHeight,
				width: temp.offsetWidth
			};
			temp.parentNode.removeChild(temp);
			return info;
		}
		
		/**
		 * @ignore
		 * 获取展示图片的大小
		 */ 
		function _getSizeInfo(p_sizeInfo, p_imageSize) {
			var info = {};
			if(p_sizeInfo.width < p_imageSize.width || p_sizeInfo.height < p_imageSize.height) {
				var scale = p_sizeInfo.width / p_imageSize.width < p_sizeInfo.height / p_imageSize.height ? p_sizeInfo.width / p_imageSize.width : p_sizeInfo.height / p_imageSize.height;
				info = {width:p_imageSize.width * scale, height:p_imageSize.height * scale};
			} else {
				info = p_imageSize;
			}
			
			if(info.width==1 && info.height==1) {
				var previewSize = self.previewSize();
				info.width = previewSize=="big"?160:90;
				info.height= previewSize=="big"?160:90;
			}
			
			return info;
		}
		
		function getFileName(path) {
			var arr = path.split("\\");
			path = arr[arr.length - 1];
			arr = path.split("/");
			return arr[arr.length - 1];
		}

		function getFileIcon(fileName) {
			var fileImages = {
				"icon-word": "doc,docx",
				"icon-excel": "xls,xlsx",
				"icon-ppt": "ppt,pptx",
				"icon-pdf": "pdf",
				"icon-txt": "txt",
				"icon-rar": "zip,rar,tar,tgz,arj",
				"icon-music": "wav,mp3,ogg",
				"icon-film": "avi,mpg,mpeg,rm,move",
				"icon-picture": "jpg,jpeg,gif,png,bmp"
			};
			var ext = "";
			var arr = fileName.split(".");
			if (arr.length > 1){
				ext = arr[arr.length - 1].toLowerCase();
			}
			
			for(var key in fileImages){
				if(fileImages[key].indexOf(ext) != -1){
					return key;
				}
			}
				
			return "icon-file";
		}
		
		function upload(item){
			
			var args = {cancel: false, item : item, params: self.params(), fileUploaderParams: null};
			if (self.onItemUploading != null && !cube.isObservable(self.onItemUploading)) {
				self.onItemUploading(args);
				if(args.cancel) {
					item.status(cube.msg("UPLOADCACEL"));
					return;
				}
			}
			
			item.status(cube.msg("UPLOADING"));
			
			var fileUploader = new  FileUploader();
			fileUploader.url = self.url();
			fileUploader.file = item.file;
			fileUploader.paramsByPost = self.paramsByPost();
			fileUploader.params = getParam(args.params);
			
			if (cube.notEmpty(args.fileUploaderParams)) {
				fileUploader.params = args.fileUploaderParams;
			}
			
			fileUploader.onprogresschange = function(progress){
				item.progress(progress + "%");
				
				if (cube.isNumber(progress)) {
					self.progress((parseFloat(self.progress()) + progress/uploadCount) + "%");
				}
				
				if (item.status() == cube.msg("UPLOADFAIL")) {
					item.progress("0%");
				}
			};
			
			fileUploader.onprogresscomplete = function(){
				
			};
			
			fileUploader.onprogresserror = function(){
				item.status(cube.msg("UPLOADFAIL"));
			};
			
			fileUploader.oncanceled = function(){
				item.status(cube.msg("UPLOADCACEL"));
			};
			
			fileUploader.onuploadcomplete = function(e){
				var resultValue = e.resultValue;
				var p_name = item.name;
				var ret = e.resultValue;
				var _fileExt = null;
				if(ret.charAt(2) == ":"){
					_fileExt = ret.substring(3,ret.length);
					ret = parseInt(ret.substring(0,2));
				}
				
				var vitem = jsonUtil.getItem(self._validitems(), "name", p_name);
				if (vitem) {
					self._validitems.remove(vitem);
				}
				
				// 上传失败 -2
				if (ret == -2 || ret == -3 || ret == -4 || ret == -5) {
					item.status(cube.msg("UPLOADFAIL"));
					item.progress("0%");
					// 上传失败 -3 -4 文件类型非法
					if (ret == -3) {
						self._validitems.push({name: p_name, msg: cube.msg("FILE_TYPE_LIMITED",[_fileExt])});
					} else if (ret == -4){
						self._validitems.push({name: p_name, msg: cube.msg("FILE_TYPE_ALLOWED",[_fileExt])});
					}else if(ret == -5 ){
						self._validitems.push({name: p_name, msg: cube.msg("UPLOAD_SIZE_LIMIT",[(self.maxFileSize() + "KB")])});
					} else {
						if(_fileExt) {
							self._validitems.push({name: p_name, msg: _fileExt});
						} else {
							self._validitems.push({name: p_name, msg: cube.msg("UPLOADFAIL")});
						}
					}
					
					if (self.onItemUploadFail != null && !cube.isObservable(self.onItemUploadFail)) {
						self.onItemUploadFail(item, resultValue);
					}
					
				} else {
					if (item.status() != cube.msg("UPLOADED")) {
						self._unuploadCount(self._unuploadCount()-1);
						self._uploadedCount(self._uploadedCount()+1);
					
						item.status(cube.msg("UPLOADED"));
						
						if (self.onItemUploaded != null && !cube.isObservable(self.onItemUploaded)) {
							var args = {cancel: false};
							self.onItemUploaded(item, resultValue, args);
							if (args.cancel == true) {
								self._unuploadCount(self._unuploadCount()+1);
								self._uploadedCount(self._uploadedCount()-1);
								item.status(cube.msg("UPLOADFAIL"));
								item.progress("0%");
								return;
							}
						}
						
						self._onChanged();
						
						var notUploaded = 0;
						for (var i = 0; i < self._items().length; i++){
							if(self._items()[i].status() != cube.msg("UPLOADED")){
								notUploaded++;
							}
						}
						
						if (notUploaded == 0) {
							if (self.onAllItemUploaded != null
								&& !cube.isObservable(self.onAllItemUploaded)) {
								self.onAllItemUploaded(self.name(), self._items(), resultValue);
							}
							self._onChanged();
						}
					}
				}				
			};
			
			fileUploader.upload();
		}

		
		function getParam(p_params) {
			var _params = {};
			var type = self.type();
			
			if (self.filePath()) {
				_params.filePath = self.filePath();
			}
			
			if (type == "form" || type == "grid") {
				_params.tableName = self.tableName();
				_params.primaryKey = self.primaryKey();
				_params.pkVal = self.pkVal();
				_params.colName = self.colName();
			}
		
			_params.uploadMode = self.uploadMode();
			_params.isVirtual = self.isVirtual();
			
			if (cube.notEmpty(p_params)) {
				_params.params = p_params;
			} else {
				_params.params = self.params();
			}
		
			
			return _params;
		}
		
		/**
		 * @ignore
		 * 根据文件名找路径
		 */
		function _getUri(p_name) {
			var uri = null;
			var type = self.type();
			var codedName = p_name.replace(/\+/g,"%2B");
			var codedPath = self.filePath() ? self.filePath().replace(/\+/g,"%2B") : "";
			var pathParam = codedPath ? ("&filePath=" + codedPath) : "";
			if (type == "path") {
				uri = encodeURI(encodeURI(self.url() + "?option=download&filePath=" + codedPath + "&fileName=" + codedName + "&uploadMode="
						+ self.uploadMode() + "&rnd=" + cube.random()));
			} else if (type == "form" || type == "grid") {
				uri = encodeURI(encodeURI(self.url() + "?option=download&tableName=" + self.tableName() + "&pkVal=" 
						+ self.pkVal() + "&colName=" + self.colName() + "&fileName=" + codedName + "&uploadMode="
						+ self.uploadMode() + pathParam  + "&rnd=" + cube.random()));   
			}
			
			return uri;
		}
		
		/**
		 * IE上传
		 * @ignore
		 */
		var sessionId = "";
		function ieUploadFile(item) {
			if (item.status() != cube.msg("UPLOADED")) {
				var args = {cancel: false, item : item, params: self.params(), fileUploaderParams: null};
				
				if (self.onItemUploading != null && !cube.isObservable(self.onItemUploading)) {
					if (self.isGetMd5() && !item.file) {
						item.file = {"md5": ""};
					}
					self.onItemUploading(args);
					if(args.cancel) {
						item.status(cube.msg("UPLOADCACEL"));
						return;
					}
				}
				
				item.status(cube.msg("UPLOADING"));
				
				var uploadForm = document.getElementById(self.id());
				
				var fileInput = $("#" + self.id()).find("#file_" + item.fileId);
				fileInput.get(0).disabled = false;
				
				startRequest(item);
				sendIdSession(item);
				
				var params = cube.notEmpty(args.params) ? args.params : self.params();
				if (cube.isObject(params)) {
					params = JSON.stringify(params);
				}
				
				if (cube.notEmpty(args.fileUploaderParams)) {
					uploadForm.action = _getUrl(self.url(), args.fileUploaderParams);
				} else {
					if (self.type() == "path") {
						uploadForm.action = encodeURI(self.url() + "?option=upload&maxFileSize="+self.maxFileSize()+"&sessionId="
							+ sessionId + "&fileName=" + self.name()+ "&filePath=" + self.filePath() + "&uploadMode=" + self.uploadMode()
							+ "&params=" + params
							+ "&isVirtual=" + self.isVirtual() + "&rnd=" + cube.random());
					} else if (self.type() == "form" || self.type() == "grid") {
						uploadForm.action = encodeURI(self.url() + "?option=upload&maxFileSize="+ self.maxFileSize()+"&sessionId="
							+ sessionId + "&tableName="
							+ self.tableName() + "&primaryKey="
							+ self.primaryKey() + "&pkVal="
							+ self.pkVal() + "&colName="
							+ self.colName() 
							+ (self.filePath() ? ("&filePath="+ self.filePath()) : "")
							+ "&uploadMode="
							+ self.uploadMode() + "&params="
							+ params
							+"&isVirtual="+self.isVirtual() + "&rnd=" + cube.random());
					}
				}
				
				uploadForm.submit();
				fileInput.get(0).disabled = true;
			}
		}

		/**
		 * @ignore
		 */
		function startRequest(item) {
			var xmlHttp = createXMLHttpRequest();
			open(xmlHttp);
			xmlHttp.send("option=startUpload&rnd=" + cube.random());
			if (xmlHttp.status == 200) {
				if (!xmlHttp.responseText) {
					item.status(cube.msg("UPLOADFAIL"));
					throw "error";
				}
				sessionId = xmlHttp.responseText.replace(/^\s*/, "").replace(/\s*$/, "");
			} else {
				item.status(cube.msg("UPLOADFAIL"));
				throw "error"
			}
		}
		
		function sendIdSession(item) {
			var xmlHttp = createXMLHttpRequest();
			open(xmlHttp);
			xmlHttp.send("option=getProgress&sessionId=" + sessionId + "&rnd=" + cube.random());
			if (xmlHttp.status == 200) {
				var res = xmlHttp.responseText.replace("&","&amp;").replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#039;").replace(/script/gi, "").replace(/%/g, "&#37").replace(/\r/g,"").replace(/\n/g,"");
				if (res) {
					var _fileExt = null;
					var err = res.split(":");
					if (err[0] == -2) {
						if (err.length > 1 && err[1]) {
							self._validitems.push({name: item.name, msg: err[1]});
						}
						item.status(cube.msg("UPLOADFAIL"));
						throw "error"
					}else if (err[0] == -3) {
						item.status(cube.msg("UPLOADFAIL"));
						self._validitems.push({name: item.name, msg: cube.msg("FILE_TYPE_LIMITED",[err[1]])});
						throw "error"
					}else if (err[0] == -4) {
						item.status(cube.msg("UPLOADFAIL"));
						self._validitems.push({name: item.name, msg: cube.msg("FILE_TYPE_ALLOWED",[err[1]])});
						throw "error"
					}else if (err[0] == -5) {
						item.status(cube.msg("UPLOADFAIL"));
						self._validitems.push({name: item.name, msg: cube.msg("UPLOAD_SIZE_LIMIT",[(self.maxFileSize() + "KB")])});
						throw "error"
					}
					
					res = parseInt(res, 10);
					if (isNaN(res)) {
						item.status(cube.msg("UPLOADFAIL"));
						throw "error"
					}
					
					if (res != -1) {
						try {
							window.setTimeout(function() {
								sendIdSession(item);
							}, 500)
						} catch (e) {
						}
					} else if (res == -1) {
						item.status(cube.msg("UPLOADED"));
						item.progress("100%");
						
						self._unuploadCount(self._unuploadCount()-1);
						self._uploadedCount(self._uploadedCount()+1);
						
						if (self.onItemUploaded != null && !cube.isObservable(self.onItemUploaded)) {
							var args = {cancel: false};
							self.onItemUploaded(item, res, args);
							
							if (args.cancel == true) {
								self._unuploadCount(self._unuploadCount()+1);
								self._uploadedCount(self._uploadedCount()-1);
								item.status(cube.msg("UPLOADFAIL"));
								item.progress("0%");
								return;
							}
						}
						self._onChanged();
						
						var notUploaded = 0;
						for (var i = 0; i < self._items().length; i++){
							if(self._items()[i].status() != cube.msg("UPLOADED")){
								notUploaded++;
							}
						}
						
						if (notUploaded == 0) {
							if (self.onAllItemUploaded != null
								&& !cube.isObservable(self.onAllItemUploaded)) {
								self.onAllItemUploaded(self.name(), self._items());
							}
							self._onChanged();
						}
					}
				}
			}
		}
		
		function createXMLHttpRequest() {
			var xmlHttp = null;
			if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			}
			return xmlHttp
		}
		
		function open(xmlHttp) {
			var version = cube.getIEVersion();
			if (cube.ieCorsProxy && version > -1 && version <= 9.0 && cube.crosDomain(self.url())) {
				xmlHttp.open("POST", cube.mappath(cube.ieCorsProxyHost), false);
				xmlHttp.setRequestHeader("X-CorsProxy-Url", self.url());
			} else {
				xmlHttp.open("POST", self.url(), false);
			}
		 	
		 	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		}
		
		function _getUrl(p_url, p_params)
		{
			var params = "";
			var url = "";
			for (var key in p_params)
			{
				if (params != "")
				{
					params+="&";
				}
				
				if (typeof(p_params[key]) == "string" && p_params[key].replace(/^\s*/, "").replace(/\s*$/, "") == "") {
			   		params += key + "=";
				} else {
					if (key == "fileName") {
						params += key + "=" + p_params[key];
					} else if(key == "params"){
						params += key + "=" + JSON.stringify(p_params[key]);
					} else {
						params += key + "=" + encodeURIComponent(encodeURIComponent(p_params[key]));
					}
				}
			}
			
			url = p_url + (cube.isEmpty(params) ? "" : (cube.isEmpty(p_url) ? "" : "?") + params);
			return url + "&rnd=" + cube.random();
		}
		
		/**
		 * @ignore
		 */
		self.onload = function(node) {
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		}
		
		cube.endViewModel(self, params);
	}
	
	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 	   this._validitemsSub.dispose();
 	}
 	
	return ViewModel;
});