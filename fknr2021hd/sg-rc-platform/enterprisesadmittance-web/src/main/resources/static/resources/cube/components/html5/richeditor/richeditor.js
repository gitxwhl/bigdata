define(["Validator", "JSONUtil"], function(validator, jsonUtil) {
	
	/**
	 * 富文本编辑器组件
	 * <code language="html">
	 * 		<richeditor params="
						style: style,
						contentHtml: contentHtml"></richeditor>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.style = 'border:1px solid #eee';
	 * 				self.contentHtml = "<div>请输入内容...</div>";
	 * 				
	 * 				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**
		 * 编辑器名称
		 * 
		 * @default 空
		 */
		self.name = '';
		
		/**
		 * 编辑内容，当编辑器类型为CheckListEditor时此值为数组
		 * 例：["1","2"]
		 * 
		 * @default 空
		 */
		self.value = '';
		
		/**
		 * 编辑器宽度
		 * 
		 * @default 空
		 */
		self.width = "auto";
		
		/**
		 * 编辑器高度
		 * 
		 * @default 空
		 */
		self.height = "auto";
		
		
		/**
		 * @ignore
		 */
		self.textareaHeight = "200px";
		
		/**
		 * 是否为只读
		 * 
		 * @default false
		 */
		self.readOnly = false;
		
		/**
		 * 是否显示编辑源码按钮
		 * 
		 * @default false
		 */
		self.isShowCodeBtn = false;
		
		/**
	     * 验证类型，多个验证类型用逗号分隔。
	     * 该字段可选的值包括：
	     * <ul>
	     * 	   <li><b>NOTNULL</b> 必填</li>
	     *     <li><b>DIGIT</b> 数字</li>
	     *     <li><b>INTEGER</b> 整数</li>
	     *     <li><b>FLOAT</b> 浮点数</li>
	     *     <li><b>MINUSINTEGER</b> 负整数</li>
	     *     <li><b>MINUSFLOAT</b> 负浮点数</li>
	     *     <li><b>PLUSINTEGER</b> 正整数</li>
	     *     <li><b>PLUSFLOAT</b> 正浮点数</li>
	     *     <li><b>CHINESE</b> 中文</li>
	     *     <li><b>LETTER</b> 字母</li>
	     *     <li><b>ASCII</b> ASCII 码</li>
	     *     <li><b>DATE</b> 日期。例如：2007-07-01</li>
	     *     <li><b>DATETIME</b> 日期时间。例如：2007-07-01 14:28:32</li>
	     *     <li><b>TIME</b> 时间。例如：14:28:32</li>
	     *     <li><b>EMAIL</b> 邮箱地址</li>
	     *     <li><b>IP</b>  IP 地址</li>
	     *     <li><b>MOBILE</b> 手机号码</li>
	     *     <li><b>PHONE</b> 电话号码</li>
	     *     <li><b>URL</b> 统一资源地址</li>
	     *     <li><b>ZIPCODE</b> 邮政编码</li>
	     *     <li><b>SQL</b> 是否包含 SQL 关键字</li>
	     *     <li><b>IDCARD</b> 身份证号码</li>
	     *     <li><b>REGEXP</b> 正则表达式，支持通过指定正则表达式进行校验。</li>
	     *     <li><b>UNIQUE</b> 唯一性验证。</li>
	     * </ul>
	     * 
	     * @default null
	     */
	    self.validType = null;
	    
	    /**
	     * 一个json对象，设校验参数。该属性与 {@link validType}配合使用。
	     * 常用校验参数有：
	     * maxValue, minValue, maxLength, minLength, expression。
    	 * maxValue 表示数字的最大值。该参数在 validType 为 DIGIT、INTEGER、MINUSINTEGER、PLUSINTEGER、FLOAT、MINUSFLOAT、PLUSFLOAT 时使用。
     	 * minValue 表示数字的最小值。该参数在 validType 为 DIGIT、INTEGER、MINUSINTEGER、PLUSINTEGER、FLOAT、MINUSFLOAT、PLUSFLOAT 时使用。
     	 * expression 表示正则表达式。该参数在 validType 为 REGEXP 时使用。
     	 * maxLength 表示字符串的最大长度。
     	 * minLength 表示字符串的最小长度。
     	 * validateMessage 表示验证无效时的提示信息。
     	 * 
     	 *  @default null
	     */
	    self.validOptions = null;
		
	    /**
	     * 验证状态，在表单和表格中起作用。
	     * 取值：（错误）error、（警告）warning、（通知）info和（成功）success
	     * @default null
	     */
	    self.validStatus = null;
	    
	    /**
	     * 验证错误提示信息，在表单和表格中起作用。
	     * @default null
	     */
	    self.validHint = null;
		
		/**
		 * 编辑器样式
		 * @default
		 * 		空
		 */
		self.style = "";
		
		/**
		 * 当前是否为代码编辑模式。
		 * @default
		 * 		false
		 */
		self.isCodeMode = false;
		
		/**
		 * 内部属性，命令里诶包
		 * @ignore
		 */
		self.cmdLists = [
		     { 
		    	 type:"list",
		    	 cmdKey:"FontName",
		    	 desc: "字体",
		    	 options:[
		    	    {name:'宋体',value:'宋体'},
		    	    {name:'微软雅黑',value:'微软雅黑'},
		    	    {name:'楷体',value:'楷体'},
		    	    {name:'Serif',value:'Serif'},
		    	    {name:'Sans',value:'Sans'},
		    	    {name:'Arial',value:'Arial'},
		    	    {name:'Arial Black',value:'Arial Black'},
		    	    {name:'Courier',value:'Courier'},
		    	    {name:'Courier New',value:'Courier New'},
		    	    {name:'Comic Sans MS',value:'Comic Sans MS'},
		    	    {name:'Helvetica',value:'Helvetica'},
		    	    {name:'Times',value:'Times'},
		    	    {name:'Times New Roman',value:'Times New Roman'}
		    	 ],
		    	 isShowOptions:cube.obj(false),
		    	 value:cube.obj("宋体"),
		    	 icon: "icon-font"
		     },
		    { 
		    	 type:"list",
		    	 cmdKey:"FontSize",
		    	 desc: "字体大小",
		    	 options:[
		    	     {name:'大号',value:5},
		    	     {name:'中号',value:3},
		    	     {name:'小号',value:1}
		    	 ],
		    	 isShowOptions:cube.obj(false),
		    	 value:cube.obj("大号"),
		    	 icon: "icon-text-height"
		     },
		     { 
		    	 type:"group",
		    	 items:[
		    	      {
		    	    	 type: 'complex',
		    	    	 cmdKey:"ForeColor",
		 		    	 desc: "字体颜色",
				    	 icon: "icon-heart",
				    	 value: cube.obj(''),
				    	 options: {editorType:'color'},
				    	 isShowOptions:cube.obj(false)
		    	      },
		    	      {
			    	    	 type: 'complex',
			    	    	 cmdKey:"BackColor",
			 		    	 desc: "文字背景",
					    	 icon: "icon-tags",
					    	 value: cube.obj(''),
					    	 options: {editorType:'color'},
					    	 isShowOptions:cube.obj(false)
			    	      }
		    	 ]
		     },
		     { 
		    	 type:"group",
		    	 items:[
		    	      {
		    	    	 type: 'single',
		    	    	 cmdKey:"Bold",
		 		    	 desc: "粗体",
				    	 icon: "icon-bold",
				    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Italic",
			 		    	 desc: "斜体",
					    	 icon: "icon-italic",
					    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Strikethrough",
			 		    	 desc: "删除",
					    	 icon: "icon-strikethrough",
					    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Underline",
			 		    	 desc: "下划线",
					    	 icon: "icon-underline",
					    	 value: cube.obj('')
		    	      }
		    	 ]
		     },
		     { 
		    	 type:"group",
		    	 items:[
		    	      {
		    	    	 type: 'single',
		    	    	 cmdKey:"InsertUnorderedList",
		 		    	 desc: "项目编号",
				    	 icon: "icon-list-ul",
				    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Insertorderedlist",
			 		    	 desc: "数字编号",
					    	 icon: "icon-list-ol",
					    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Outdent",
			 		    	 desc: "减少缩进",
					    	 icon: "icon-indent-main",
					    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Indent",
			 		    	 desc: "缩进",
					    	 icon: "icon-indent-right",
					    	 value: cube.obj('')
		    	      }
		    	 ]
		     },
		     { 
		    	 type:"group",
		    	 items:[
		    	      {
		    	    	 type: 'single',
		    	    	 cmdKey:"Justifyleft",
		 		    	 desc: "居左",
				    	 icon: "icon-align-main",
				    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Justifycenter",
			 		    	 desc: "居中",
					    	 icon: "icon-align-center",
					    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Justifyright",
			 		    	 desc: "居右",
					    	 icon: "icon-align-right",
					    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	 type: 'single',
			    	    	 cmdKey:"Justifyfull",
			 		    	 desc: "两端对齐",
					    	 icon: "icon-align-justify",
					    	 value: cube.obj('')
		    	      }
		    	 ]
		     },
		     { 
		    	 type:"group",
		    	 items:[
		    	      {
		    	    	 type: 'complex',
		    	    	 cmdKey:"createLink",
		 		    	 desc: "超级链接",
				    	 icon: "icon-link",
				    	 value: cube.obj(''),
				    	 options: {editorType:'text'},
				    	 isShowOptions:cube.obj(false)
		    	      },
		    	      {
		    	    	  type: 'single',
		    	    	  cmdKey:"Unlink",
			 		      desc: "取消超级链接",
			 		      icon: "icon-cut",
			 		      value: cube.obj('')
		    	      }
		    	 ]
		     },
		     { 
		    	 type:"group",
		    	 items:[
		    	      {
		    	    	 type: 'complex',
		    	    	 cmdKey:"InsertImg",
		 		    	 desc: "插入图片",
				    	 icon: "icon-picture",
				    	 value: cube.obj(''),
				    	 options: {editorType: 'file'},
				    	 isShowOptions:cube.obj(false)
		    	      }
		    	 ]
		     },
		     { 
		    	 type:"group",
		    	 items:[
		    	      {
		    	    	 type: 'single',
		    	    	 cmdKey:"Undo",
		 		    	 desc: "撤销",
				    	 icon: "icon-undo",
				    	 value: cube.obj('')
		    	      },
		    	      {
		    	    	  	type: 'single',
			    	    	 cmdKey:"Redo",
			 		    	 desc: "重新键入",
					    	 icon: "icon-repeat",
					    	 value: cube.obj('')
		    	      }
		    	 ]
		     },
		     { 
		    	 type:"group",
		    	 items:[
//		    	      {
//		    	    	 type: 'single',
//		    	    	 cmdKey:"CUBE_SAVE",
//		 		    	 desc: "保存",
//				    	 icon: "icon-file",
//				    	 value: cube.obj('')
//		    	      },
		    	      {
			    	    	 type: 'single',
			    	    	 cmdKey:"CUBE_CODE",
			 		    	 desc: "编辑源码",
					    	 icon: "icon-edit",
					    	 value: cube.obj('')
			    	   }
		    	 ]
		     }
		];
		
		/**
		 * @ignore
		 */
		self.curCmd = null;
		
		/**
		 * @ignore
		 */
		self.fontName = null;
		
		/**
		 * @ignore
		 */
		self.fontSize = null;
		
		/**
		 * @ignore
		 */
		self.selectionRange =  null;
		
		var isChangeeditableHtml = true;
		
		/**
		 * 编辑值改变之后回调事件
		 * 
		 * @default null
		 */
		self.onChanged = null;
		
		/**
		 *  @ignore
		 */
		self._init = function(){
			
			var style = self.style();
			var width = self.width();
			var height = self.height();
			if (style.indexOf("width") == -1) {
				style = style + ";width:" + width + ";";
			}
			
			if (style.indexOf("height") == -1) {
				style = style + ";height:" + height + ";";
			}
			
			self.style(style);
			
			self.valueSub = cube.subscribe(self.value, self._changed);
			
			if (typeof(params.maxLength) != "undefined") {
				var validOptions = self.validOptions();
				if (validOptions == null) {
					validOptions = {};
				}
				
				validOptions.maxLength = params.maxLength;
				self.validOptions(validOptions);
			}
			
			if (typeof(params.nullable) != "undefined" && (!params.nullable || !params.nullable())){
				var validType = self.validType();
				if (validType == null) {
					validType = "NOTNULL";
				}else{
					validType = validType + ",NOTNULL";
				}
				
				self.validType(validType);
			}
		}

		/**
		 * 鼠标经过按钮
		 * @ignore
		 */
		self.getEditorSelection = function() {
			if(self.isCodeMode()) return;
			
			self.saveSelection();
		}
		
		/**
		 * 显示设置选项
		 * @ignore
		 */
		self.showOptions = function() {
			if(self.isCodeMode() || self.readOnly())
				return;
			
			self.curCmd(this);
			this.isShowOptions(true);
			
			self.restoreSelection();
		}
		
		/**
		 * 隐藏设置选项
		 * @ignore
		 */
		self.hiddenOptions = function() {
			if(self.isCodeMode()) return;
			
			this.isShowOptions(false);
		}
		
		/**
		 * 执行操作
		 * @ignore
		 */
		self.execCommand = function(viewModel, event) {
			if (self.readOnly()) {
				return;
			}
			
			if (self.curCmd() != null) {
				if (self.curCmd().cmdKey == "FontName") {
					self.fontName(viewModel.name);
				} else if (self.curCmd().cmdKey == "FontSize") {
					self.fontSize(viewModel.name);
				}
			}
			
			if(self.isCodeMode()) {
				if(self.curCmd().cmdKey == 'CUBE_CODE') {
					self.isCodeMode(!self.isCodeMode());
				}
				return;
			}
			
			self.restoreSelection();
			
			if(this.cmdKey != null)
				self.curCmd(this);
			
			if(self.curCmd().type == 'list')
				self.curCmd().value(this.value);
				
			if(self.curCmd().cmdKey == 'CUBE_SAVE'){
				self.value($('div.richeditor').html());
			} else if(self.curCmd().cmdKey == 'CUBE_CODE'){
				self.value($('div.richeditor').html());
				self.isCodeMode(!self.isCodeMode());
			} else {
				if(this.options!=null &&this.options.editorType =='file') {
					if (/^image\//.test(this.value().type)) {
						$.when(readFileIntoDataUrl(this.value())).done(function (dataUrl) {
							document.execCommand('insertimage',"false", dataUrl);
						});
					}
				} else {
					document.execCommand(self.curCmd().cmdKey,0,self.curCmd().value());
				}
			}
	
			if(self.curCmd().type == 'list' || self.curCmd().type == 'complex')
				self.curCmd().isShowOptions(false);
			
			self.saveSelection();
		}
		
		/**
		 * @ignore
		 */
		self._changed = function() {
			if (self.onChanged != null && !cube.isObservable(self.onChanged)) {
				self.onChanged(self.name(), self.value());
			}
		}
		
		/**
		 * @ignore
		 */
		self._valid = function(viewModel, event) {
			
			var textValue = event.target.innerText.replace(/(\n)/g, "");
			var value = event.target.innerHTML;
			isChangeeditableHtml = false;
			self.value(value);
			isChangeeditableHtml = true;
			
			var validType = self.validType();
			
			if (validType != null) {
				var result = validator.validate(textValue, validType, self.validOptions());
				if (!result.successful) {
					self.validHint(result.hint);
					self.validStatus("error");
				} else {
					self.validHint("");
					self.validStatus("");
				}
			}
		}
		
		self._click = function(viewModel, event){
			var target = event.target;
			if (target) {
				target = $(target);
				var fontName = target.css("font-family");
				var fontSize = target.attr("size");
				var result = jsonUtil.getItem(self.cmdLists(), "name", fontName, 3);
				if (result){
					self.fontName(fontName);
				} else {
					self.fontName(null);
				}
				
				if (fontSize == 5) {
					self.fontSize("大号");
				} else if (fontSize == 3) {
					self.fontSize("中号");
				} else if (fontSize == 1) {
					self.fontSize("小号");
				} else {
					self.fontSize(null);
				}
			}
		}
		
		function readFileIntoDataUrl(fileInfo) {
			var loader = $.Deferred(),
				fReader = new FileReader();
			fReader.onload = function (e) {
				loader.resolve(e.target.result);
			};
			fReader.onerror = loader.reject;
			fReader.onprogress = loader.notify;
			fReader.readAsDataURL(fileInfo);
			return loader.promise();
		};
		
		self.saveSelection = function() {
			var selRange = null;
			
			if(window.getSelection) {
				var sel = window.getSelection($('div.richeditor'));//$('div.richeditor')
				
				if (sel.getRangeAt && sel.rangeCount) {
					selRange = sel.getRangeAt(0);
				} 
			} else if(document.selection) {
				selRange = document.selection.createRange();
			}
				
			if (selRange != null) {
				self.selectionRange = selRange;
			}
		}
		
		//由于点击按钮后，window.selection()会变为按钮的字符的内容。因此每次执行操作前，需要将selection重置为richeditor的选中内容。
		self.restoreSelection = function() {
			var selection;
			if (self.selectionRange && !cube.isObservable(self.selectionRange)) {
				if (window.getSelection) {
					selection = window.getSelection($('div.richeditor'));
					try {
						selection.removeAllRanges();
					} catch (ex) {
						var textRange = document.body.createTextRange();
						textRange.select();
						document.selection.empty();
					}
					
					selection.addRange(self.selectionRange);
				} else if(document.selection) {
					selrange = document.selection.createRange(self.selectionRange);
				}
				
			}
		}

		//可编辑html
		ko.bindingHandlers.editableHtml = {
				init: function(element, valueAccessor) {

			    },
			    update: function(element, valueAccessor) {
			    	if (isChangeeditableHtml) {
			    		var value = ko.utils.unwrapObservable(valueAccessor());	        
				        $(element).html(value);
			    	}
			    }
		};
		
		self.onload = function(node, viewModel) {
			var $node = $(node);
			if (self.height() != "auto" || self.style().indexOf("height") != -1) {
				var toolbarHeight = $node.find('.btn-toolbar').outerHeight();
				self.textareaHeight((parseInt($node.height()) - parseInt(toolbarHeight)) - 10 + "px");
			}
		}
		
		cube.endViewModel(self, params);
	}
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 	      this.valueSub.dispose();
 	}
 	
	return ViewModel;
});