define([], function() {

	/**
	 * 流式布局，基于bootstrap的栅格布局。
	 * <code language="html">
	 * 		<fluidlayout params="items:items"></fluidlayout>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.items = [
	 *							    {
	 *									colspan : 4,
	 *									content : "单元格1",
	 *									textAlign : "center"
	 *								}, {
	 *									colspan : 6,
	 *									content : "单元格2"
	 *								}, {
	 *									colspan : 2,
	 *									content : "单元格3"
	 *								},
	 *								{
	 *									colspan : 2,
	 *									content : "单元格4"
	 *								}, {
	 *									colspan : 4,
	 *									content : "单元格5"
	 *								}, {
	 *									colspan : 6,
	 *									content : "单元格6"
	 *								}
	 *								]
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
		 * 宽度
		 * @default 
		 * 		100%
		 */
		self.width = "100%";
		
		/**
		 * 高度
		 * @default 
		 * 		100%
		 */
		self.height = "100%";
		
		/**内容设置
		 * @example
		 * <code language="JavaScript">
		 * [
		 * {
		 * 		colspan:4,
		 * 		content:"内容",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * },{
		 * 		colspan:8,
		 * 		content:"内容1",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * },{
		 * 		colspan:8,
		 * 		content:"内容3",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * },{
		 * 		colspan:4,
		 * 		content:"内容4",
		 * 		templateOptions:{
		 * 			name:"",
		 * 			params:{}
		 * 		}
		 * }
		 * ]
		 * </code>
		 * 
		 * 其中content和templateOptions配置一个即可，若两个都配置，content优先级高。
		 * content表示内容，
		 * templateOptions表示加载其他页面，templateOptions中的name为页面路径，params为要传递给该页面对应ViewModel的参数
		 */
		self.items = [];
		
		var _length = 0;
		
		/**
	     * @ignore
	     */
	    self.onloading = function(node, viewModel) {
	    	var $e = $(node);
	    	var pages = $e.children("section");
	    	_length = self.items().length;
	    	var ieVersion = cube.getIEVersion();
	    	pages.each(function(index,element){
	    		var $element = $(element);
	    		var content = $element.wrap("<div></div>").parent().html();
	    		if (ieVersion > -1 && ieVersion < 9) {
	    			content = content.replace(/<:/g, "<").replace(/<\/:/g, "</")
	    		}
	    		
	    		var page = {
	    			colspan : $element.attr("colspan"),
	    			textAlign :  $element.attr("textAlign"),
				 	content: content
				};
	    		
    			self.items.push(page);
	    		$element.remove();
	    	});
	    }
	    
		/**
	     * @ignore
	     */
	    self.onload = function(node, viewModel) {
	    	var $e = $(node);
	    	var items = self.items();
	    	for (var i = _length; i < items.length; i++) {
				cube.applyBindings($e.find(">.container>div:nth-child("+(i+1)+")>div").children(), viewModel);
	    	}
	    }
		
		cube.endViewModel(self, params);
	}
	
	return ViewModel;
});