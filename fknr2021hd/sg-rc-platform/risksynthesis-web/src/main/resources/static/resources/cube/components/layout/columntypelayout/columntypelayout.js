define([], function() {

	/**
	 * 列式布局
	 * <code language="html">
	 * 		<columntypelayout params="
	 *							columns:columns
	 *						"></columntypelayout>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.columns = [{name:"1",columnborder:'20px',columnWidth:"200px",
	 * 								 component:{
	 *								    		name:"app_cubedemo.componentDemos.layoutDemos.columntypelayout.tmp",
	 *								    		params: {windowHeight: 200, footHeight: 300}
	 *								    		}
	 *								 },
	 *	                			 {name:"2",columnborder:'50px',columnWidth:"300px",
	 *	                			  component:{
	 *	                						name:"commoneditor",params:{editorType:"NumberEditor"}
	 *	                						}
	 *	                			 }];
	 *	
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
		 * 列式布局，定义布局的每一列以及添加的组件
		 * @example
		 * <code language="JavaScript">
		 *  [{
		 *  	name: "1",
		 *  	columnborder: '20px',
		 *  	component: "app_cubedemo.componentDemos.dialogDemos.modaldialog.main"
		 * 	},
		 *  {
		 *   	name: "2",
		 *   	columnWidth: '200px',
		 *   	component: "app_cubedemo.componentDemos.dialogDemos.modaldialog.main"
		 *  },
		 *  {
		 *  	name: "3",
		 *  	content: "内容"
		 *  }
		 *  ];
		 *  </code>
		 */
		self.columns = [];
		
		var _length = 0;
		
		/**
	     * @ignore
	     */
	    self.onloading = function(node, viewModel) {
	    	var $e = $(node);
	    	var pages = $e.children("section");
	    	_length = self.columns().length;
	    	var ieVersion = cube.getIEVersion();
	    	pages.each(function(index,element){
	    		var $element = $(element);
	    		var content = $element.wrap("<div></div>").parent().html();
	    		if (ieVersion > -1 && ieVersion < 9) {
	    			content = content.replace(/<:/g, "<").replace(/<\/:/g, "</")
	    		}
	    		
	    		var page = {
	    			   columnWidth : $element.attr("columnWidth"),
	    			   columnborder : $element.attr("columnborder"),
	    			   name : $element.attr("name"),
				 	   content: content
				};
	    		
    			self.columns.push(page);
	    		$element.remove();
	    	});
	    }
	    
		/**
	     * @ignore
	     */
	    self.onload = function(node, viewModel) {
	    	var $e = $(node);
	    	var columns = self.columns();
	    	for (var i = _length; i < columns.length; i++) {
				cube.applyBindings($e.find(">.col-layout>tbody>tr>td:nth-child("+(i+1)+")").children("div").eq(0).children(), viewModel);
	    	}
	    }
		
		cube.endViewModel(self,params);
		
	}
	return ViewModel;
});