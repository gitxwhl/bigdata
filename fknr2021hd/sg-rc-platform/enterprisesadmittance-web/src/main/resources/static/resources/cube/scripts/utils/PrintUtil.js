/**
 * 提供局部元素打印功能。 
 * 
 * @example
 * <code language="JavaScript">
 * define(["PrintUtil"], function(printUtil) {
 * 		printUtil.print($("#divId"));
 * })
 * </code>
 */
define(["jqprint"], function(jqprint) {
    var me = {};

    /**
     * 提供一个方法，打印内容。
     * 
     * 当打印页面时只需提供一个参数，即dom节点
     * 当打印表单和表格时需提供三个参数，第一个：表格/表单内容数据、第二个参数：表格列信息/表单字段信息，第三个参数：组件类型（datagrid、groupheadergrid、form）	
     */
    me.print = function() {
    	if (arguments.length == 4) {
    		var type = arguments[3];
    		var id = new Date().getTime();
    		var dom = "";
    		
	    	var node = document.createElement(type); 
			node = $(node);
			node.attr("id", id);
			
			if (type == "datagrid" || type == "groupheadergrid") {
				dom = "table";
    			node.attr("params", ko.toJSON({items: arguments[0], columns: arguments[1], dicts: arguments[2], isAllowAppend: false, isShowCondense: true}));
    		} else if(type == "dataform") {
    			dom = "form";
    			node.attr("params", ko.toJSON({data: arguments[0], fields:arguments[1], dicts: arguments[2],  displaySubmitButton: false}));
    		}
			
			node.css("display", "none");
			ko.applyBindingsToNode(node[0]);
			$("body").append(node);
			
			setTimeout(function() {
				node.find(dom).jqprint();
				node.remove();
			}, 300);
    		
    	} else if (arguments.length == 1) {
    		var dom = arguments[0];
    		if(! (dom instanceof jQuery)){
				dom = $(dom);
			}
    		dom.jqprint();
    	}
    };
    
    return me;
});
