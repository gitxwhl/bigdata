(function (factory) {
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
		factory(require("knockout"), exports);
	} else if (typeof define === "function" && define["amd"]) {
		define(["knockout", "exports"], factory);
	} else {
		factory(ko, ko.mapping = {});
	}
}(function (ko, exports) {
	var base = {};
	
    var commentNodesHaveTextProperty = document && document.createComment("test").text === "<!--test-->";

    var startCommentRegex = commentNodesHaveTextProperty ? /^<!--\s*ko(?:\s+([\s\S]+))?\s*-->$/ : /^\s*ko(?:\s+([\s\S]+))?\s*$/;
    var endCommentRegex =   commentNodesHaveTextProperty ? /^<!--\s*\/ko\s*-->$/ : /^\s*\/ko\s*$/;
    var htmlTagsWithOptionallyClosingChildren = { 'ul': true, 'ol': true };
    
	function isStartComment(node) {
        return (node.nodeType == 8) && startCommentRegex.test(commentNodesHaveTextProperty ? node.text : node.nodeValue);
    }

	//组件嵌套    
	ko.virtualElements.setDomNodeChildren = function(node, childNodes){
		if (cube.importedComponents.indexOf(node.nodeName.toLocaleLowerCase())!=-1) {
			//判断表单组件是否使用自定义模板
			if ((node.nodeName.toLocaleLowerCase() == "dataform" || node.nodeName.toLocaleLowerCase() == "searchbox") && node.childNodes.length > 0 && !(node.childNodes.length == 1 && node.childNodes[0].nodeType == 3)) {
				childNodes = ko.utils.cloneNodes(node.childNodes);
			} else {
				childNodes = ko.utils.cloneNodes(node.childNodes).concat(childNodes);
			}
        }
		
		if (!isStartComment(node))
            ko.utils.setDomNodeChildren(node, childNodes);
        else {
            ko.virtualElements.emptyNode(node);
            var endCommentNode = node.nextSibling; // Must be the next sibling, as we just emptied the children
            for (var i = 0, j = childNodes.length; i < j; i++)
                endCommentNode.parentNode.insertBefore(childNodes[i], endCommentNode);
        }
	}
	
	//自动注册页面vvm
	var cubeLoader = {
        'getConfig': function(componentName, callback) {
        	var result = cube.loadSubWebPageVVM(componentName);
            callback(result);
        }
    };
    
    //压缩模式加载模板
    var cubeTemplateLoader = {
        'loadTemplate': function(componentName, templateConfig, callback) {
        	if (cube.importedComponents.indexOf(componentName) != -1 && templateConfig['id']) {
          		var element = templateConfig['id'];
          		if (typeof element === 'string') {
          			var elemInstance = cube.template.siblings("div#" + element).get(0);
          			if (!elemInstance && cube.templateext) {
          				elemInstance = cube.templateext.siblings("div#" + element).get(0);
          			}
          			
	                if (elemInstance) {
	                    callback(ko.utils.cloneNodes(elemInstance.childNodes));
	                } else {
	                    throw new Error('Component \'' + componentName + '\': ' + 'Cannot find element with ID ' + element);
	                }
          		} else {
          			throw new Error('Component \'' + componentName + '\': ' + 'Cannot find element with ID ' + element);
          		}
        	} else {
        		callback(null);
        	}
        },
        'loadViewModel': function(componentName, viewModelConfig, callback) {
            resolveViewModel(makeErrorCallback(componentName), viewModelConfig, callback);
        }
    }; 
    
    ko.components.loaders.splice(0, 0, cubeTemplateLoader);
    ko.components.loaders.push(cubeLoader);
    
    function resolveViewModel(errorCallback, viewModelConfig, callback) {
    	if (typeof viewModelConfig === 'undefined') {
    		errorCallback('Unknown viewModel value: ' + viewModelConfig);
    	} else if (typeof viewModelConfig === 'function') {
            // Constructor - convert to standard factory function format
            // By design, this does *not* supply componentInfo to the constructor, as the intent is that
            // componentInfo contains non-viewmodel data (e.g., the component's element) that should only
            // be used in factory functions, not viewmodel constructors.
            callback(function (params , componentInfo ) {
            	if (params && componentInfo && componentInfo.element && !isStartComment(componentInfo.element)) {
            		var tagName = componentInfo.element.tagName.toLowerCase();
            		var comtype = params.__comtype;
            		if (comtype) {
            			tagName = comtype;
            		}
            		
            		if (cube.importedComponents.indexOf(tagName) < 0) {
            			var dataBind = componentInfo.element.getAttribute("data-bind");
                		if (dataBind && dataBind.indexOf("component") != -1 && dataBind.indexOf("params") != -1) {
                			dataBind = dataBind.substring(dataBind.indexOf("component"), dataBind.indexOf("params"));
                			var bind = dataBind.split(":");
                			if (bind.length >= 3 && bind[bind.length-1]) {
                				tagName = bind[bind.length-1].replace(/"/g, "").replace(/'/g, "").replace(/,/g, "").replace(/\s/g, "");
                			}
                		}
            		}
            		
            		params.__tagName = tagName;
            		
            		if (params.__iframe == true) {
            			cube.__params = params;
            		}
            	}
            	
            	if (typeof(params) == "undefined") {
            		params = {};
            	}
            	
            	var viewModel = new viewModelConfig(params);
            	if (typeof viewModel._init === "function" && viewModel._initialized !== true) {
            		viewModel._init();
            		viewModel._initialized = true;
            	}
            	
                return viewModel;
            });
        } else if (typeof viewModelConfig[createViewModelKey] === 'function') {
            // Already a factory function - use it as-is
            callback(viewModelConfig[createViewModelKey]);
        } else if ('instance' in viewModelConfig) {
            // Fixed object instance - promote to createViewModel format for API consistency
            var fixedInstance = viewModelConfig['instance'];
            callback(function (params, componentInfo) {
                return fixedInstance;
            });
        } else if ('viewModel' in viewModelConfig) {
            // Resolved AMD module whose value is of the form { viewModel: ... }
            resolveViewModel(errorCallback, viewModelConfig['viewModel'], callback);
        } else {
            errorCallback('Unknown viewModel value: ' + viewModelConfig);
        }
    }
    
    function makeErrorCallback(componentName) {
        return function (message) {
            throw new Error('Component \'' + componentName + '\': ' + message);
        };
    }
    
    //调用组件onload方法
    base.applyBindingsToDescendants = ko.applyBindingsToDescendants;
    ko.applyBindingsToDescendants = function(viewModelOrBindingContext, rootNode) {
       if ((!isStartComment(rootNode) || isStartCommentDropdowneditor(rootNode)) && viewModelOrBindingContext['$data'] 
    	   && typeof(viewModelOrBindingContext['$data'].onloading) == "function" 
    	   && !cube.isObservable(viewModelOrBindingContext['$data'].onloading)) {
			viewModelOrBindingContext['$data'].onloading(rootNode, isStartCommentDropdowneditor(rootNode) ? viewModelOrBindingContext.$data : viewModelOrBindingContext.$parent);
	   }
    	
       base.applyBindingsToDescendants(viewModelOrBindingContext, rootNode);
       
       if ((!isStartComment(rootNode) || isStartCommentDropdowneditor(rootNode)) && viewModelOrBindingContext['$data'] 
       	   && typeof(viewModelOrBindingContext['$data'].onload) == "function" 
       	   && !cube.isObservable(viewModelOrBindingContext['$data'].onload)) {
   			viewModelOrBindingContext['$data'].onload(rootNode, isStartCommentDropdowneditor(rootNode) ? viewModelOrBindingContext.$data : viewModelOrBindingContext.$parent);
       }
    };
    
    function isStartCommentDropdowneditor(node) {
    	if (isStartComment(node)) {
    		var value = commentNodesHaveTextProperty ? node.text : node.nodeValue;
    		if (value && value.indexOf("component") != -1 && (value.indexOf("dropdowneditor") != -1 || value.indexOf("dropdowntreeeditor") != -1 || value.indexOf("treegriditem") != -1)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    //重写样式绑定
    ko.bindingHandlers['style'] = {
	    'update': function (element, valueAccessor) {
	        var value = ko.utils.unwrapObservable(valueAccessor() || {});
	        ko.utils.objectForEach(value, function(styleName, styleValue) {
	            styleValue = ko.utils.unwrapObservable(styleValue);
	
	            if (styleValue === null || styleValue === undefined || styleValue === false) {
	                // Empty string removes the value, whereas null/undefined have no effect
	                styleValue = "";
	            }
	            
	            if(!(element.style[styleName] == "" && styleValue == "")){
	            	element.style[styleName] = styleValue;
	            }
	           
	        });
	    }
	};
	
	//报错提示扩展
	ko.expressionRewriting.preProcessBindings = function (bindingsStringOrKeyValueArray, bindingOptions) {
        bindingOptions = bindingOptions || {};

        function processKeyValue(key, val) {
            var writableVal;
            function callPreprocessHook(obj) {
                return (obj && obj['preprocess']) ? (val = obj['preprocess'](val, key, processKeyValue)) : true;
            }
            if (!bindingParams) {
                if (!callPreprocessHook(ko['getBindingHandler'](key)))
                    return;

                if (ko.expressionRewriting.twoWayBindings[key] && (writableVal = getWriteableValue(val))) {
                    // For two-way bindings, provide a write method in case the value
                    // isn't a writable observable.
                    propertyAccessorResultStrings.push("'" + key + "':function(_z){" + writableVal + "=_z}");
                }
            }
            // Values are wrapped in a function so that each value can be accessed independently
            if (makeValueAccessors) {
                val = 'function(){try{return ' + val + '}catch(e){ e.message+=" \\nat "+$element.outerHTML;throw e;} }';
            }
            resultStrings.push("'" + key + "':" + val);
        }

        var resultStrings = [],
            propertyAccessorResultStrings = [],
            makeValueAccessors = bindingOptions['valueAccessors'],
            bindingParams = bindingOptions['bindingParams'],
            keyValueArray = typeof bindingsStringOrKeyValueArray === "string" ?
                ko.expressionRewriting.parseObjectLiteral(bindingsStringOrKeyValueArray) : bindingsStringOrKeyValueArray;

        ko.utils.arrayForEach(keyValueArray, function(keyValue) {
            processKeyValue(keyValue.key || keyValue['unknown'], keyValue.value);
        });

        if (propertyAccessorResultStrings.length)
            processKeyValue('_ko_property_writers', "{" + propertyAccessorResultStrings.join(",") + " }");

        return resultStrings.join(",");
    }
    
    var javaScriptReservedWords = ["true", "false", "null", "undefined"];
    var javaScriptAssignmentTarget = /^(?:[$_a-z][$\w]*|(.+)(\.\s*[$_a-z][$\w]*|\[.+\]))$/i;
    function getWriteableValue(expression) {
        if (ko.utils.arrayIndexOf(javaScriptReservedWords, expression) >= 0)
            return false;
        var match = expression.match(javaScriptAssignmentTarget);
        return match === null ? false : match[1] ? ('Object(' + match[1] + ')' + match[2]) : expression;
    }
    
    //扩展改变值不通知订阅方法，
    //value为新值
    //allSilent为Boolean值，当为true时则本次赋值不会通知所有订阅，当不设置或false时则会给订阅回调函数传递值为false的第二个参数
    ko.observable.fn.silentUpdate = function(value, allSilent) {
    	if (typeof(callbackName) != "undefined" && callbackName == true) {
    		this.notifySubscribers = function(){};
    	} else {
    		this.notifySubscribers = function (valueToNotify, event) {
    	        event = event || "change";
    	        if (this.hasSubscriptionsForEvent(event)) {
    	            try {
    	                ko.dependencyDetection.begin(); 
    	                for (var a = this._subscriptions[event].slice(0), i = 0, subscription; subscription = a[i]; ++i) {
    	                    if (!subscription.isDisposed)
    	                        subscription.callback(valueToNotify, false);
    	                }
    	            } finally {
    	                ko.dependencyDetection.end();
    	            }
    	        }
    	    }
    	}
    	
    	this(value);
    	this.notifySubscribers = function(){
    		ko.subscribable.fn.notifySubscribers.apply(this, arguments);
    	}
    }
}));

(function($) {

// jQuery on an empty object, we are going to use this as our Queue
var ajaxQueue = $({});

$.ajaxQueue = function( ajaxOpts ) {
    var jqXHR,
        dfd = $.Deferred(),
        promise = dfd.promise();

    // run the actual query
    function doRequest( next ) {
        jqXHR = $.ajax( ajaxOpts )
            .done( dfd.resolve )
            .fail( dfd.reject )
            .then( next, next );
    }

    // queue our ajax request
    ajaxQueue.queue( doRequest );

    // add the abort method
    promise.abort = function( statusText ) {

        // proxy abort to the jqXHR if it is active
        if ( jqXHR ) {
            return jqXHR.abort( statusText );
        }

        // if there wasn't already a jqXHR we need to remove from queue
        var queue = ajaxQueue.queue(),
            index = $.inArray( doRequest, queue );

        if ( index > -1 ) {
            queue.splice( index, 1 );
        }

        // and then reject the deferred
        dfd.rejectWith( ajaxOpts.context || ajaxOpts, [ promise, statusText, "" ] );
        return promise;
    };

    return promise;
};

})(jQuery);


//div,span等控件resize监听事件 
//******************************************************************
(function($, h, c)
    {
        var a = $([]), e = $.resize = $.extend($.resize, {}), i, k = "setTimeout", j = "resize", d = j
                + "-special-event", b = "delay", f = "throttleWindow";
        e[b] = 250;
        e[f] = true;
        $.event.special[j] =
        {
            setup : function()
            {
                if (!e[f] && this[k])
                {
                    return false
                }
                var l = $(this);
                a = a.add(l);
                $.data(this, d,
                {
                    w : l.width(),
                    h : l.height()
                });
                if (a.length === 1)
                {
                    g()
                }
            },
            teardown : function()
            {
                if (!e[f] && this[k])
                {
                    return false
                }
                var l = $(this);
                a = a.not(l);
                l.removeData(d);
                if (!a.length)
                {
                    clearTimeout(i)
                }
            },
            add : function(l)
            {
                if (!e[f] && this[k])
                {
                    return false
                }
                var n;
                function m(s, o, p)
                {
                    var q = $(this), r = $.data(this, d);
                    r.w = o !== c ? o : q.width();
                    r.h = p !== c ? p : q.height();
                    n.apply(this, arguments)
                }
                if ($.isFunction(l))
                {
                    n = l;
                    return m
                }
                else
                {
                    n = l.handler;
                    l.handler = m
                }
            }
        };
        function g()
        {
            i = h[k](function()
            {
                a.each(function()
                {
                    var n = $(this), m = n.width(), l = n.height(), o = $.data(
                            this, d);
                    if (m !== o.w || l !== o.h)
                    {
                        n.trigger(j,
                        [ o.w = m, o.h = l ])
                    }
                });
                g()
            }, e[b])
        }
    })(jQuery, this);

//******************************************************************
//* 选择区域                                                       *
//******************************************************************

(function() {
var fieldSelection = {
   getSelection: function() {
       var e = this.jquery ? this[0] : this;
       return (
           /* mozilla / dom 3.0 */
           ('selectionStart' in e && function() {
               var l = e.selectionEnd - e.selectionStart;
               return { start: e.selectionStart, end: e.selectionEnd, length: l, text: e.value.substr(e.selectionStart, l) };
           }) ||

           /* exploder */
           (document.selection && function() {

               e.focus();

               var r = document.selection.createRange();
               if (r == null) {
                   return { start: 0, end: e.value.length, length: 0 }
               }

               var re = e.createTextRange();
               var rc = re.duplicate();
               re.moveToBookmark(r.getBookmark());
               rc.setEndPoint('EndToStart', re);

               return { start: rc.text.length, end: rc.text.length + r.text.length, length: r.text.length, text: r.text };
           }) ||

           /* browser not supported */
           function() {
               return { start: 0, end: e.value.length, length: 0 };
           }
       )();
   },

   replaceSelection: function() {
       var e = this.jquery ? this[0] : this;
       var text = arguments[0] || '';

       return (
           /* mozilla / dom 3.0 */
           ('selectionStart' in e && function() {
               e.value = e.value.substr(0, e.selectionStart) + text + e.value.substr(e.selectionEnd, e.value.length);
               return this;
           }) ||

           /* exploder */
           (document.selection && function() {
               e.focus();
               document.selection.createRange().text = text;
               return this;
           }) ||

           /* browser not supported */
           function() {
               e.value += text;
               return this;
           }
       )();
   }
};

jQuery.each(fieldSelection, function(i) { jQuery.fn[i] = this; });
})();

