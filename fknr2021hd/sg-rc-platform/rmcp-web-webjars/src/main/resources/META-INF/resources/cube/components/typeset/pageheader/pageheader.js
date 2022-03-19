define([], function() {

	/**
	 * 标题组件
	 * <code language="html">
	 * 		<pageheader params="
						style: style,
						title: title,
						subTitle: subTitle,
						titleSize: titleSize,
						isSubtitleWrap: isSubtitleWrap"></pageheader>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.style = 'border: 1px solid red;';
	 * 				self.title = '标题名称';
	 * 				self.subTitle = '副标题内容';
	 * 				self.titleSize = 'h5';
	 * 				self.isSubtitleWrap = false;
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
		 * 外观样式
		 * @default
		 * 		空
		 */
		self.style = "";
		
		/**
		 * 标题
		 * @default
		 * 		空
		 */
		self.title = "";
		
		/**
		 * 副标题
		 * @default
		 * 		空
		 */
		self.subTitle = "";
		
		/**
		 * 标题尺寸
		 * @default
		 * 		空
		 */
		self.titleSize = "h1";
		
		/**
		 * 副标题是否换行
		 * @default
		 * 		空
		 */
		self.isSubtitleWrap = false;
		
		cube.endViewModel(self, params);
	};
 	
 	//释放资源，包括compoted/subscrib/setInterval资源等。
 	ViewModel.prototype.dispose = function() {
 	};
 	
	return ViewModel;
});