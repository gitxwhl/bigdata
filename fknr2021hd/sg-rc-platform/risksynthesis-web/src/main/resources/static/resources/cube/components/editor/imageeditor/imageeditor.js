define(["cropper", "FileUploader", "text!croppercss"], function(cropperClass, FileUploader) {

	/**
	 * 图片编辑器
	 * 
	 * 图片旋转与翻转的保存需后台对图片处理，暂未处理
	 * <code language="html">
	 *		<imageeditor params="
	 *						width: width,
	 *						height: height,
	 *						showToolbar: showToolbar,
	 *						url: url,
	 *						maxFileSize: maxFileSize
	 *					"></imageeditor>
	 * </code>
	 * <code language="JavaScript">
	 *		define([], function() {
	 *			var PageViewModel = function(params) {
	 *				var self = this;
	 *				
	 *				self.width = '600px';
	 *				self.height '400px';
	 *				self.showToolbar = true;
	 *				self.url = '';
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
		 * 宽度
		 *		@default 600
		 */
		self.width = 600;

		/**
		 * 高度
		 *		@default 400
		 */
		self.height = 400;
		
		/**
		 * 是否显示图片操作按钮
		 *		@default true
		 */
		self.showToolbar = true,
		
		/**
		 * 获取或设置一个字符串，表示后台服务所在路径。
		 * 用户可以根据需要使用自己的服务路径。
		 */
		self.url = null;
		
		/**
		 * 最大上传文件大小，单位：KB
		 * @default 0 不限制
		 */
		self.maxFileSize = 0;
		
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
		 */
		self.filePath = null;

		/**
		 * 设置或获取一个字符串，表示上传控件所属数据的表名。 当控件类型为 form 时，此属性有值。
		 * 当{@link type}属性的值设置为form或者grid时，需要设置该属性
		 */
		self.tableName = "";

		/**
		 * 设置或获取一个字符串，表示上传控件所属数据表的主键列名。 当控件类型为 form 时，此属性有值。
		 * 当{@link type}属性的值设置为form或者grid时，需要设置该属性
		 */
		self.primaryKey = "";

		/**
		 * 设置或获取一个字符串，表示上传控件所属数据的主键值。 当控件类型为 form 时，此属性有意义；若此时此属性没有值，控件不会响应点击事件。
		 * 当{@link type}属性的值设置为form或者grid时，需要设置该属性
		 */
		self.pkVal = "";

		/**
		 * 设置或获取一个字符串，表示上传控件在存储表单数据的表中对应的列名。 当控件类型为 form 时，此属性有值。
		 * 当{@link type}属性的值设置为form或者grid时，需要设置该属性
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
		 * 获取或设置input是否虚拟，
		 * 如果值是 true ，则不会提交编辑器的值。
		 * 
		 * @default false
		 */
		self.isVirtual = false;
		
		/**
		 * 文件上传控件udsParam, 默认为null；
		 * 例如：{comp:"公司1", des:"公司描述"};
		 * @default null
		 */
		self.udsParam = null;

		/**
		 * @ignore
		 */
		self.image = null;

		/**
		 * @ignore
		 */
		self.hasImage = false;
		
		/**
		 * 是否已上传
		 * @ignore
		 */
		self.uploaded = false;
		
		/**
		 * 上传状态
		 * @ignore
		 */
		self.status = "";
		
		/**
		 * 上传进度
		 * @ignore
		 */
		self.progress = "0%";

		/**
		 * @ignore
		 */
		self.editable = false;

		/**
		 * @ignore
		 */
		self.imageType = "";

		/**
		 * @ignore
		 */
		self.name = "";

		/**
		 * @ignore
		 */
		self.imageUrl = "";

		/**
		 * @ignore
		 */
		self.originalImageUrl = "";

		/**
		 * @ignore
		 */
		self.cropper = null;

		/**
		 * @ignore
		 */
		self.cropping = false;
		
//		/**
//		 * 放大或缩小单位
//		 * @ignore
//		 */
//		self.zoom = 0;
//		
//		/**
//		 * 旋转度数
//		 * @ignore
//		 */
//		self.rotate = 0;
//		
//		/**
//		 * 翻转
//		 * @ignore
//		 */
//		self.scale = null;

		/**
		 * @ignore
		 */
		self.cropped = false;

		/**
		 * @ignore
		 */
		self.data = null;

		/**
		 * @ignore
		 */
		self.canvasData = null;

		/**
		 * @ignore
		 */
		self.cropBoxData = null;
		
		/**
		 * @ignore
		 */
		self._allowTypes = "jpg,jpeg,gif,png,bmp";
		
		/**
		 * @ignore
		 */
		self.isIE = false;
		
		/**
		 * @ignore
		 */
		self.isOperate = false;
 
		/**
		 * @ignore
		 */
		self.isServerImage = false;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
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
				
			self.data = null;
			self.canvasData = null;
			self.cropBoxData = null;
			self.cropper = null;
			
			if(cube.isEmpty(self.id())){
				self.id("imageForm_" + cube.random().toString().split("&")[0]);
			}
			
			var fileUploader = new FileUploader();
	
			fileUploader.url = self.url();
			fileUploader.params = getParam();
			
			var fileList = fileUploader.getUploadedFiles();
			if(fileList && fileList.length > 0){
				self.name(fileList[fileList.length - 1].attName);
			}
			
			_initDefaultValue();
		};

		/**
		 * 选择图片事件
		 * @ignore
		 */
		self.imageInputChange = function(viewModel, event) {
			read(event.target, function() {
						event.target.value = "";
					});
			self.uploaded(false); 
		};

		/**
		 * @ignore
		 */
		self.dragover = function(viewModel, event) {
			event.preventDefault();
		};

		/**
		 * 图片拖放事件
		 * @ignore
		 */
		self.drop = function(viewModel, event) {
			var dataTransfer = event.dataTransfer ? event.dataTransfer : event.originalEvent.dataTransfer;
			event.preventDefault();
			read(dataTransfer, function(){
				
			});
		};

		/**
		 * 图片加载完成事件
		 * @ignore
		 */
		self.imageLoad = function(viewModel, event) {
			self.image = event.target;
			self.hasImage(true);
			self.start();
		};

		/**
		 * 删除
		 * @ignore
		 */
		self.remove = function() {
			// Disallow to delete image when cropping
			if (self.cropping()) {
				return;
			}

			if(self.uploaded()){
				var fileUploader = new FileUploader();
				
				fileUploader.url = self.url();
				fileUploader.params = getParam();
				
				var result = fileUploader.removeFile(self.name());
				if(!result){
					cube.indicate("info", cube.msg("DELETEFAIL"));
					return;
				}
			}
			
			self.stop();
			self.editable(false);
			self.data = null;
			self.image = null;
			self.imageType("");
			self.name("");
			self.imageUrl("");
			self.originalImageUrl("");
			self.hasImage(false);
			self.cropped(false);
			self.status("");
			self.isServerImage(false);
		};

		/**
		 * @ignore
		 */
		self.stop = function() {
			if (self.cropper) {
				self.cropper.destroy();
				self.cropper = null;
				self.cropping(false);
			}
		};

		/**
		 * 取消
		 * @ignore
		 */
		self.clear = function() {
			if (self.cropping()) {
				self.cropper.clear();
				self.cropping(false);
			}
		};

		/**
		 * 撤销
		 * @ignore
		 */
		self.restore = function() {
			self.imageUrl(self.originalImageUrl());
			self.cropper.replace(self.originalImageUrl());
			self.data = self.cropper.getData();
			self.canvasData = self.cropper.getCanvasData();
			self.cropBoxData = self.cropper.getCropBoxData();
			self.cropper.clear();
			
			self.originalImageUrl("");
			self.isOperate(true);
		};

		/**
		 * 移动
		 * @ignore
		 */
		self.move = function() {
			self.cropper.setDragMode("move");
			self.isOperate(true);
		};

		/**
		 * 确定裁剪 
		 * @ignore
		 */
		self.cropOk = function() {
			var cropper = self.cropper;
			var type = self.imageType();

			if (self.cropping()) {
				self.originalImageUrl(self.imageUrl());
				self.data = cropper.getData();
				self.canvasData = cropper.getCanvasData();
				self.cropBoxData = cropper.getCropBoxData();
				self.imageUrl(cropper.getCroppedCanvas(type == 'image/png' ? null : {
					fillColor : '#fff'
				}).toDataURL(type));

				cropper.replace(self.imageUrl());
				cropper.clear();

				self.cropped(true);
				self.cropping(false);
				self.isOperate(true);
			}
		};

		/**
		 * 裁剪 
		 * @ignore
		 */
		self.crop = function() {
			self.cropper.setDragMode("crop");
			self.isOperate(true);
		};

		/**
		 * 缩小
		 * @ignore
		 */
		self.zoomin = function() {
			self.cropper.zoom(0.1);
			self.isOperate(true);
		};

		/**
		 * 放大
		 * @ignore
		 */
		self.zoomout = function() {
			self.cropper.zoom(-0.1);
			self.isOperate(true);
		};

		/**
		 * 逆时针旋转
		 * @ignore
		 */
		self.rolateleft = function() {
			self.cropper.rotate(-90);
			self.isOperate(true);
		};

		/**
		 * 顺时针旋转
		 * @ignore
		 */
		self.rolateright = function() {
			self.cropper.rotate(90);
			self.isOperate(true);
		};

		/**
		 * 水平翻转
		 * @ignore
		 */
		self.horizontal = function() {
			self.cropper.scaleX(-self.cropper.getData().scaleX || -1);
			self.isOperate(true);
		};

		/**
		 * 垂直翻转
		 * @ignore
		 */
		self.vertical = function() {
			self.cropper.scaleY(-self.cropper.getData().scaleY || -1);
			self.isOperate(true);
		};

		/**
		 * 初始化cropper
		 * @ignore
		 */
		self.start = function() {
			if(self.cropped()){
				return;
			}
			
			self.cropper = new cropperClass(self.image, {
						autoCrop : false,
						checkOrientation:false,
						checkCrossOrigin:false,
						dragMode : 'move',
						background : false,
						built : function() {
							if (self.data) {
								self.cropper.crop().setData(self.data).setCanvasData(self.canvasData).setCropBoxData(self.cropBoxData);
								self.data = null;
								self.canvasData = null;
								self.cropBoxData = null;
							}
						},
						crop : function(data) {
							if (data.width > 0 && data.height > 0 && !self.cropping()) {
								self.cropping(true);
							}
						}
					});
		};
		
		/**
		 * 上传
		 * @ignore
		 */
		self.upload = function() {
			self.status(cube.msg("UPLOADING"));
	
			if (self.isIE()) {
				self.ieUploadFile();
			} else {
				var fileUploader = new FileUploader();
				fileUploader.url = self.url();
				
				if (self.isOperate()== true) {
					self.cropper.cropped = true;
					var type = self.imageType();
					self.imageUrl(self.cropper.getCroppedCanvas(type == 'image/png' ? null : {
						fillColor : '#fff'
					}).toDataURL(type));
				}
				
				fileUploader.file = convertBase64UrlToBlob(self.imageUrl());
				fileUploader.file.name = self.name();
				fileUploader.params = getParam();
				
				fileUploader.onprogresschange = function(progress){
					self.progress(progress + "%");
				};
				
				fileUploader.onprogresscomplete = function(result){
					var res = result.event.target.responseText.replace(/\s*/g, "");
					if(res == "-1") {
						self.status(cube.msg("UPLOADED"));
						self.uploaded(true);
					} else {
						var err = res.split(":");
						if(err[0] == "-5"){
							self.status(cube.msg("UPLOADFAIL") + "," + cube.msg("UPLOAD_SIZE_LIMIT", [(self.maxFileSize() + "KB")]));
						}
					}
				};
				
				fileUploader.onprogresserror = function(result){
					self.status(cube.msg("UPLOADFAIL"));
				};
				
				fileUploader.oncanceled = function(result){
					self.status(cube.msg("UPLOADCACEL"));
				};
				
				fileUploader.upload();
			}
		};
		
		/**
		 * 下载
		 * @ignore
		 */
		self.download = function() {
			window.open(_getUri(self.name()));
		};
		
		/**
		 * IE上传
		 * @ignore
		 */
		var sessionId = "";
		self.ieUploadFile = function() {
				var uploadForm = document.getElementById(self.id());
				startRequest();
				sendIdSession();
				
				if (self.type() == "path") {
					uploadForm.action = encodeURI(self.url() + "?option=upload&maxFileSize="+self.maxFileSize()+"&sessionId="
						+ sessionId + "&fileName=" + self.name()+ "&filePath=" + self.filePath() + "&uploadMode=" + self.uploadMode() 
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
						+ self.uploadMode() + "&udsParam="
						+ self.udsParam()
						+"&isVirtual="+self.isVirtual() + "&rnd=" + cube.random());
				}
				
				uploadForm.submit();
		};

		/**
		 * @ignore
		 */
		function startRequest() {
			var xmlHttp = createXMLHttpRequest();
			xmlHttp.open("POST", self.url(), false);
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			xmlHttp.send("option=startUpload&rnd=" + cube.random());
			if (xmlHttp.status == 200) {
				if (!xmlHttp.responseText) {
					throw "error";
				}
				sessionId = xmlHttp.responseText;
			} else {
				throw "error";
			}
		}
		
		function sendIdSession() {
			var xmlHttp = createXMLHttpRequest();
			xmlHttp.open("post", self.url(), false);
			xmlHttp.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			xmlHttp.send("option=getProgress&sessionId=" + sessionId + "&rnd=" + cube.random());
			if (xmlHttp.status == 200) {
				var res = xmlHttp.responseText.replace("&","&amp;").replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#039;").replace(/script/gi, "").replace(/%/g, "&#37").replace(/\r/g,"").replace(/\n/g,"");
				if (res) {
					var err = res.split(":");
					if (err[0] == -2) {
						self.status(cube.msg("UPLOADFAIL"));
						throw "error";
					}else if (err[0] == -3) {
						self.status(cube.msg("UPLOADFAIL"));
						throw "error";
					}else if (err[0] == -4) {
						self.status(cube.msg("UPLOADFAIL"));
						throw "error";
					}else if (err[0] == -5) {
						self.status(cube.msg("UPLOADFAIL"));
						throw "error";
					}
						
					res = parseInt(res, 10);
					if (isNaN(res)) {
						throw "error";
					}
					
					if (res != -1) {
						try {
							window.setTimeout(function() {
								sendIdSession();
							}, 500);
						} catch (e) {
						}
					} else if (res == -1) {
						self.status(cube.msg("UPLOADED"));
						self.uploaded(true);
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
			return xmlHttp;
		}

		function _initDefaultValue() {
			var name = self.name();
			if (self.type() != "grid" && cube.isString(name) && cube.notEmpty(name)){
				self.imageUrl(_getUri(name));
				self.hasImage(true);
				self.uploaded(true);
				self.isServerImage(true);
			}
		}
		
		function read(target, callback) {
			if (typeof FileReader == "function") {
				self.isIE(false);
				var reader = null;
				var files = target.files;
				var file = null;
				if (files) {
					file = files[0];
				}
				if (file) {
					if (/^image\/\w+$/.test(file.type)) {
						reader = new FileReader();
						reader.onload = function() {
							self.hasImage(true);
							self.editable(true);
							self.imageType(file.type);
							self.name(file.name);
							self.imageUrl(reader.result);
	
							callback();
						};
	
						reader.readAsDataURL(file);
					} else {
						self.status(cube.msg("SELECTPICTURE"));
						callback();
					}
				} else {
					callback();
				}
			} else {
				self.isIE(true);
				self.editable(true);
				
				var path = "";
				if (window.navigator.userAgent.indexOf("MSIE") > -1){ 
					target.select(); 
					$("#_fileSelectionHiddenArea").focus();
					path = document.selection.createRange().text; 
				} 
				
				var p_scale = { width: parseInt(self.width()) , height:  parseInt(self.height())};
				
				var p_$view = $("#_show_area");
				var $img = $("<div style='margin:0px auto;'>");
				$img.css("filter",self._imgloader(path, "image"));
			   
				if(_allowUpload(path)) {
					if(navigator.userAgent.indexOf("MSIE 9.0")>-1){//ie9需延迟一会才能获取到图片尺寸
						var temp = document.createElement("div");
						(document.body || document.documentElement).appendChild(temp);
						with(temp.runtimeStyle) {
							filter = self._imgloader(path,"image");
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
							p_$view.html("");
							$img.appendTo(p_$view);
							$img[0].style.filter=self._imgloader(path, "scale");
							$img.width(_sizeInfo.width).height(_sizeInfo.height);
							
							self.hasImage(true);
							self.name(getFileName(path));
//							self.image = document.getElementById("_show_area");
//							self.start();
							
						},500); 
					} else {
						var _size = me._getImgSize(path);
						var _sizeInfo = _getSizeInfo(p_scale, _size);
						p_$view.html("");
						$img.appendTo(p_$view);
						$img[0].style.filter=self._imgloader(path, "scale");
						$img.width(_sizeInfo.width).height(_sizeInfo.height);
						
						self.hasImage(true);
						self.name(getFileName(path));
//						self.image = document.getElementById("_show_area");
//	 					self.start();
					}
				} else {
					self.status(cube.msg("SELECTPICTURE"));
					callback();
				}
			}
		}
		
		/**
		 * @ignore
		 * ie 专用
		 */
		self._imgloader=function(p_path, p_mode) {
			return "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + p_path + "', sizingMethod='" + p_mode + "')";
		}
		
		/**
		 * @ignore
		 * 文件是否符合格式
		 */ 
		function _allowUpload(p_name) {
			if(cube.isEmpty(p_name)) {
				return false;
			}
			var arr = p_name.split(".");
			var _fileType = arr[arr.length - 1];
			return (","+self._allowTypes()+",").indexOf(","+_fileType+",") > -1;
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
				info.width = parseInt(self.width());
				info.height= parseInt(self.height());
			}
			
			return info;
		}
		
		function getFileName(path) {
			var arr = path.split("\\");
			path = arr[arr.length - 1];
			arr = path.split("/");
			return arr[arr.length - 1]
		};
		
		function getParam(){
			var params = {};
			
			if (self.filePath()){
				params.filePath = self.filePath();
			}
			
			params.tableName = self.tableName();
			params.primaryKey = self.primaryKey();
			params.pkVal = self.pkVal();
			params.colName = self.colName();
			params.uploadMode = self.uploadMode();
			params.isVirtual = self.isVirtual();
			params.maxFileSize = self.maxFileSize()*1024;
			
			return params;
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
		
		function convertBase64UrlToBlob(urlData) {
			var bytes=window.atob(urlData.split(',')[1]); 
			var ab = new ArrayBuffer(bytes.length);
			var ia = new Uint8Array(ab);
			for (var i = 0; i < bytes.length; i++) {
				ia[i] = bytes.charCodeAt(i);
			}
		
			return new Blob( [ab] , {type : 'image/png'});
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
	return ViewModel;
});