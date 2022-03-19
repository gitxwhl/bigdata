define([], function() {

	/**
	 * HTML5表单编辑器
	 * <code language="html">
	 * 		<formeditor params="
	 *				        type:type
	 *				"></formeditor>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.type = 'date';
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
         * 编辑器类型
         * @default
         * 		空
         */
        self.type = "";
      
		cube.endViewModel(self, params);
    };

    return ViewModel;
});