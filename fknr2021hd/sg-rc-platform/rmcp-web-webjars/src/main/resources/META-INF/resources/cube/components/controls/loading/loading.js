define([], function() {

	/**
	 * 遮罩层组件
	 * @example
	 * <code language="html">
	 * 		<loading params="
	 *				isShow: isShow,
	 *				content: content"></loading>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.isShow = true;
	 * 				self.content = '加载中';
	 * 				
	 *				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
 	function ViewModel(params) {
		var self = this;
		
		/**
		 * 遮罩层显示的内容
		 * @default 
		 * 		null
		 */
		self.content = null;
		
		/**
		 * 遮罩层显示的图片url
		 * @default
		 * 	   window_loading.gif
		 */
		self.imgUrl = cube.cubepath("$/images/window_loading.gif");
		
		/**
		 * 遮罩层背景色
		 * @default 
		 * 		null
		 */
		self.backgroundColor = null;
		
		/**
		 * 是否fixed定位，默认为absolute定位
		 * @default 
		 * 		false
		 */
		self.fixed = false;
		
		/**
		 * 是否显示
		 * @default 
		 * 		true
		 */
		self.isShow = true;
		
		/**
		 * 遮罩层堆叠顺序，当使用cube.showLoading方法创建窗口时，如果没有传入该属性值，该属性值会自动累加
		 * 
		 * @default 1050
		 */
		self.zIndex = 9999;
		
		cube.endViewModel(self, params);
	}
	
	return ViewModel;
});