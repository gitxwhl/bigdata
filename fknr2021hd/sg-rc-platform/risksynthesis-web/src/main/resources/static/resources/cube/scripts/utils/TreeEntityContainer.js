/**
 * 提供一个供 {@link cube.datacontrols.DataTree} 使用的数据容器的类。
 */
define(["entityContainer", "GUIDUtil"], function(entityContainer, guidUtil) {
	return function(){
	    var me = new entityContainer();
	    var base = {};
	
	    /**
	     * 以下参数说明仅包含当前子类的扩展参数，基础参数和其他信息请参照
	     * {@link cube.datacontainers.EntityContainer.onload} 事件。
	     * 
	     * @param parentNode
	     *            一个对象，表示此次加载子节点的节点。
	     * @param nodes
	     *            一个数组，包含本次加载结果中的节点信息。
	     */
	    me.onload = null;
	
	    /**
	     * 当数据容器中有数据对象被删除时，将触发该事件。
	     * 
	     * @param cancel
	     *            一个 Boolean 值，表示是否中止事件。
	     * @param node
	     *            一个 对象，表示要删除的节点。
	     */
	    me.ondeleting = null;
	
	    /**
	     * 当数据容器中有数据对象被删除后，将触发该事件。
	     * 
	     * @param node
	     *            一个 对象，表示要删除的节点。
	     */
	    me.ondeleted = null;
		
	    /**
	     * 当数据容器中新增数据对象时，将触发该事件。
	     * 
	     * @param cancel
	     *            一个 Boolean 值，表示是否中止事件。
	     * @param entity
	     *            一个 JSON 对象，表示新增的数据对象。
	     */
	    me.oncreating = null;
	    
	    /**
	     * 当数据容器中新增数据对象后，将触发该事件。
	     * 
	     * @param entity
	     *            一个 JSON 对象，表示新增的数据对象。
	     */
	    me.oncreated = null;
	    
	    me.primaryKey = "id";
	    
	    me.textColName = "text";
	    
	    me.loadMeta = false;
	    
	    me.canCreate = true;
		
	    
	    me._removeCallBack = null;
	    
	    
	    var _loadingNode = null;
	
	    var _deletingNode = null;
	    
	    var _loadNode = [];
	    
	    base.init = me.init;
	    me.init = function()
	    {
	    	base.init();
    		var data = me.data() || [];
    		me.data([]);
    		if (cube.notEmpty(me.baseUrl)) {
    			data = [];
    		}
    		_populateNodes(data);
	    };
	    
	    /**
	     * 提供一个方法，为数据容器加载数据对象。
	     * 
	     * @overload function()
	     * @overload function(p_node)
	     * @overload function(p_node, p_callBack)
	     * 
	     * @param [p_node]
	     *            一个对象，表示要加载子节点的节点。
	     * @param [p_callBack]
	     *            一个方法，表示加载完成后的回调函数。
	     */
	    base.load = me.load;
	    me.load = function(p_node, p_callBack)
	    {
	        _loadingNode = null;
	        if (typeof (p_callBack) != "undefined" && typeof (p_node) != "undefined")
	        {
	        	if (cube.notEmpty(p_node) && cube.notEmpty(p_node.id)) {
	        		_loadingNode = p_node;
	            	_loadNode.push(p_node.id);
	                var params = p_node.queryParams != null ? p_node.queryParams : {};
	                params.itemType = p_node.itemType;
	                base.load(p_node.id, params, p_callBack);
	        	} else {
	        		 base.load(p_node, p_callBack);
	        	}
            	
	        }
	        else if (typeof (p_node) != "undefined")
	        {
	        	if (cube.notEmpty(p_node) && cube.notEmpty(p_node.id)) {
	        		_loadingNode = p_node;
	            	_loadNode.push(p_node.id);
	                var params = p_node.queryParams != null ? p_node.queryParams : {};
	                params.itemType = p_node.itemType;
	                base.load(p_node.id, params);
	        	} else {
	        		 base.load(p_node);
	        	}
	        }
	        else
	        {
	            base.load();
	        }
	    };
	
	    /**
	     * 提供一个方法，根据主键获取对应的数据对象。
	     * 
	     * @param p_nodeID
	     *            一个字符串或者数字，表示要查找的数据对象的主键值。
	     */
	    me.getNodeEntity = function(p_nodeID, p_itemType)
	    {
	    	if (cube.notEmpty(p_nodeID) && (cube.isString(p_nodeID) || cube.isNumber(p_nodeID)))
	     	{
	    		var node = null;
	            $.each(me.data(), function(i, p_node){
	               if (p_node.id  === p_nodeID && p_node.itemType  === p_itemType)
	               {
	                   node = p_node;
	               } 
	            });
	            
	            if (node != null)
	            {
	            	return node;
	            }
	
	     		return me.data()["#" + p_nodeID];
	     	}
	    };
		
	    /**
	     * 创建节点
	     */
	    me.create = function(p_node, p_options)
	    {
	    	p_node = $.extend(true, {}, p_node);
	    	var args = {};
	    	if (!me.canCreate)
	    	{
	    		me._notAllowedTip();
	    		return;
			}
			if ($.isPlainObject(p_options))
	        {
	        	args.parentNode = me.getNodeEntity(p_options.parentId, p_options.itemType);
	        	args.nextSibling = me.getNodeEntity(p_options.nextId, p_options.itemType);
	        	args.prevSibling = me.getNodeEntity(p_options.prevId, p_options.itemType);
	        	args.order = cube.isEmpty(p_options.order) ? true : p_options.order; 
	        }else{
	        	args.order = true;
	        }
	        
			args.node = p_node || {};
		
	        var event_args = $.extend(true, {cancel: false}, args)
	
//	        me.trigger("creating", event_args);
	        if (event_args.cancel)
	        {
	            return;
	        }
	        
	      	var node = _create(args.node, args);
	      	args.node = node;
	        event_args = $.extend(true, {}, args);
//	        me.trigger("created", event_args);
	     	return node;
	    };
	    
	    
	    function _create(p_node, p_args)
	    {
	    	var node = p_node, 
	    	    nextSibling = p_args.nextSibling,
	    	    prevSibling = p_args.prevSibling,
	    	    parentNode = p_args.parentNode, 
	    	    order = p_args.order,
	    	    pk = node[me.primaryKey],
	 			data = [];
	 			
			node.hasChildren =  cube.obj(cube.isEmpty(node.hasChildren) ? false: node.hasChildren);
			node.expanded =  cube.obj(cube.isEmpty(node.expanded) ? false: node.expanded);
			node.childNodes =  cube.array(cube.isEmpty(node.childNodes) ? []: node.childNodes);
			node.visible =  cube.obj(cube.isEmpty(node.visible) ? true: node.visible);
			node.checked =  cube.obj(cube.isEmpty(node.checked) ? false: node.checked);
			node.indeterminate =  cube.obj(cube.isEmpty(node.indeterminate) ? false: node.indeterminate);
			node.imageUrl =  cube.obj(cube.isEmpty(node.imageUrl) ? null: node.imageUrl);
			
	 		if (cube.isEmpty(parentNode))
	 		{
	 			if (cube.notEmpty(nextSibling))
	 			{
	 				parentNode = me.data()["#" + nextSibling.parentId];
	 			}
	 			else if (cube.notEmpty(prevSibling))
	 			{
	 				parentNode = me.data()["#" + prevSibling.parentId];
	 			}
	 		}
	 		
	    	if (cube.isEmpty(pk))
	    	{
	    		pk = guidUtil.newGUID();
	    		node[me.primaryKey] = pk;
	    	}
	    	
	    	node.parentId = cube.isEmpty(parentNode) ? null: parentNode[me.primaryKey];
	    	
	    	 // 为新增的数据准备存放变更信息的对象。
	        me._changedItems[pk] = new Object();
	        
	        for ( var p in node) {
	        	if (p != me.primaryKey && !cube.isFunction(node[p])) {
	        		if (p == "text") {
	        			me._changedItems[pk][me.textColName] = node[p];
	        		} else {
	        			me._changedItems[pk][p] = node[p];
	        		}
	        	}
	        }
	        
	        me.hasChanged(true);
	    	
	    	if (parentNode && cube.notEmpty(parentNode.level)) {
				node.level = parentNode.level + 1;
			} else {
				node.level = 1;
			}
			
	    	if (cube.notEmpty(me.data()["#" + pk]))
	    	{
	    		cube.indicate("warning", cube.msg("ERR_DUPLICATED_ITEM", [ pk ]));
	    		return;
	    	}
	    	
	    	if (cube.isEmpty(parentNode))
	    	{
	    		data = me.data || [];
//	    		me.data = data;
	    	}
	    	else
	    	{
	    		data = parentNode.childNodes || [];
//	    		parentNode.childNodes = data;
	    	}
	    	
	    	if (data.indexOf(node) == -1)
	    	{
	    		if (cube.notEmpty(data["#" + pk]))
	        	{
	        		cube.indicate("warning", cube.err("DUPLICATED_ITEM", [ pk ]));
	        		return;
	        	}
	    		if (cube.notEmpty(nextSibling))
	    		{
	    			var index = data.indexOf(nextSibling);
				    if (index != -1)
				    {
				        data.splice(index, 0, node);
				    }
	    		}
	    		else if (cube.notEmpty(prevSibling))
	    		{
	    			var index = data.indexOf(prevSibling);
				    if (index != -1)
				    {
				        if (index == data.length)
					    {
					        data.push(node);
					    }
					    else
					    {
					        data.splice(index + 1, 0, node);
					    }
				    }
				   
	    		}
	    		else 
	    		{
	    			order ? data.push(node): data.splice(0, 0, node);
	    		}
	    		
	    		
	    	}
	    	else
	    	{
	    		cube.indicate("warning", cube.err("DUPLICATED_ITEM", [ pk ]));
	    		return;
	    	}
	    	
	    	if (cube.isEmpty(data["#" + pk]))
	    	{
	    		data["#" + pk] = node;
	    	}
	    	
	    	if (cube.notEmpty(parentNode)){
//	    		parentNode.childNodes(data);
	    		parentNode.hasChildren(true);
	    	}
	    	
	    	if (cube.isEmpty(me.data()["#" + pk]))
	    	{
//	    		me.data.push(node);
	    		me.data()["#" + pk] = node;
	    	}
	    		
			if (cube.isArray(node.childNodes()))
			{
				$.each(node.childNodes(), function(index, child){
					_create(child, { parentNode: node});
				});
			}
			
	    	return node;
	    }
	    
	    /**
	     * 提供一个方法，删除一个树节点。
	     * 
	     * @overload function(p_node)
	     * @overload function(p_node, p_delete)
	     * 
	     * @param p_node
	     *            一个对象，表示要删除的节点。
	     * @param [p_callback] 一个方法，表示删除之后的回调函数。
	     * @param [p_path] 一个字符串，表示自定义删除服务的路径。
	     */
	    me.remove = function(p_node, p_callback, p_path)
	    {
	        if (me._submitting)
	        {
	            return;
	        }
	        me._submitting = true;
	        
	        me._removeCallBack = p_callback
	        var args =
	        {
	            cancel : false,
	            node : p_node
	        };
	
//	        me.trigger("deleting", args);
	        if (args.cancel)
	        {
	            return;
	        }
	
	        _deletingNode = p_node;
	
	        var IDs = [];
	        if (cube.isArray(p_node))
	        {
	        	for (var i = 0; i < p_node.length; i++) 
	        	{
	        		IDs.push(p_node[i].id);
				}
	        }
	        else
	        {
	        	IDs.push(p_node.id);
	        }
	       
	
	   		if (cube.notEmpty(me.baseUrl)) 
	   		{
	   			//var delUrl = ("remove" in me.actions) ? me.actions["remove"] : "delete";
	        	//delUrl = p_path || delUrl;
	        	var delUrl = me._makePath("remove","delete",p_path);
	        	var _post = me._getClientPostMode("remove");
	        	_post(delUrl, JSON.stringify({"ids": IDs}), _delete_callback);
	   		}
	        else
	        {
	        	me._submitting = false;
	        	_delete_success();
	        }
	        
	    };
	
	    /**
	     * @ignore
	     */
	    base._prepareLocalData = me._prepareLocalData;
	    me._prepareLocalData = function(p_data)
	    {
	    	me.data = cube.array(p_data);
	        var localResult = {}, resultValue = {nodes: p_data};
	       	localResult.resultValue = resultValue;
	        return localResult;
	    };
	
	    /**
	     * @ignore
	     */
	    me._parseData = function(p_result, p_parentId)
	    {
	    	var nodes = null, parentNode = _loadingNode;
	    	if(cube.isObservable(p_result.nodes)){
	    		nodes = p_result.nodes();
	    	}else{
	    		nodes = p_result.nodes;
	    	}
	    	
	    	if (cube.isEmpty(me.data()))
	    	{
	    		me.data([]);
	    	}
	        if (cube.isArray(nodes))
	        { 
	        	if (cube.notEmpty(p_parentId)){
	        		parentNode = me.data()["#" + p_parentId];
		    	}
	        	
	        	if (cube.notEmpty(me.baseUrl)) 
	        	{
	        		_populateNodes(nodes, parentNode);
	        	}
	        	else
	        	{
	        		if (cube.notEmpty(_loadingNode))
	        		{
	        			p_result.nodes = _filterNodes();
	        			
	        			if (p_result.nodes == 0 && nodes["#" + _loadingNode.id])
	        			{
	            			p_result.nodes = nodes["#" + _loadingNode.id].childNodes;
//	            			if(typeof(p_result.nodes) == "function")
//	            			{
//	            				p_result.nodes = p_result.nodes();
//	            			}
	        			}
	        		}
	        	}
	        }
	    };
	    
	    
	    function _filterNodes()
	    {
	    	if (cube.notEmpty(_loadingNode))
	    	{
	    		if(typeof(_loadingNode.childNodes) == "function" && cube.isArray(_loadingNode.childNodes()))
	    		{
	    			return _loadingNode.childNodes;
	    		}
	    		else if(cube.isArray(_loadingNode.childNodes))
	    		{
	    			return _loadingNode.childNodes;
	    		}
	    	}
	    	return cube.array([]);
	    }
	    
	    function _clearChildren(p_node)
	    {
	    	var children = cube.isEmpty(p_node) ? me.data: p_node.childNodes
	    	if (cube.isArray(children))
	    	{
	    		while (children.length > 0)
	    		{
	    			_remove(children()[0], p_node);
	    		}
	    	}
	    }
	    
	    function _populateNodes(p_nodes, p_parentNode)
	    {
			var data, pk;
			
			if (! cube.isArray(p_nodes))
				return;
			
			if (cube.isEmpty(p_parentNode))
			{
				me.data = me.data ||　cube.array([]);
				data = me.data;
			}
			else
			{
				p_parentNode.childNodes = p_parentNode.childNodes || cube.array([]);
				data = p_parentNode.childNodes;
			}
			
			_clearChildren(p_parentNode);
			
			$.each(p_nodes, function(index, node){
				 me._parseNode(node, data, pk, p_parentNode);
			});
	    }
	    
	    /**
	     * @ignore
	     */
	    me._parseNode = function(node, data, pk, p_parentNode) {
	    	pk = node[me.primaryKey];
			node.parentId = cube.isEmpty(p_parentNode) ? null: p_parentNode[me.primaryKey];
			node.hasChildren = cube.obj(cube.isEmpty(node.hasChildren) ? false: node.hasChildren);
			node.expanded =  cube.obj(cube.isEmpty(node.expanded) ? false: node.expanded);
			node.childNodes =  cube.array(cube.isEmpty(node.childNodes) ? []: node.childNodes);
			node.visible =  cube.obj(cube.isEmpty(node.visible) ? true: node.visible);
			node.checked =  cube.obj(cube.isEmpty(node.checked) ? false: node.checked);
			node.indeterminate =  cube.obj(cube.isEmpty(node.indeterminate) ? false: node.indeterminate);
			node.imageUrl =  cube.obj(cube.isEmpty(node.imageUrl) ? null: node.imageUrl);
			
			if (p_parentNode && cube.notEmpty(p_parentNode.level)) {
				node.level = p_parentNode.level + 1;
			} else {
				node.level = 1;
			}
			
			if (data.indexOf(node) == -1) {
				data.push(node);
			}
			data()["#" + pk] = node;
			me.data()["#" + pk] = node;
		
			var childNodes = cube.isObservable(node.childNodes) ? node.childNodes() : node.childNodes;
			if (cube.isArray(childNodes)) {
				_populateNodes(childNodes, node)
			} else {
				node.childNodes = cube.array([]);
			}
			
			if (node.expanded() && node.hasChildren() && cube.notEmpty(me.baseUrl) && childNodes.length == 0) {
				me._submitting = false;
				me.load(node);
			}
	    };
	
	    /**
	     * @ignore
	     */
	    base._setLoadArgs = me._setLoadArgs;
	    me._setLoadArgs = function(p_result, p_parentId)
	    {
	    	for (var i = 0; i < _loadNode.length; i++) {
				if (_loadNode[i] == p_parentId) {
					_loadNode.splice(i, 1);
				}
			}
	    	
	        var args = new Object();
	        if (cube.notEmpty(p_parentId)) {
	        	 args.parentNode = me.data()["#" + p_parentId];
	    	}
	        
	        if (_loadNode.length == 0) {
	        	 args.triggerLoad = true;
	        } else {
	        	 args.triggerLoad = false;
	        }
	        
	        args.nodes = p_result.nodes;
	        return args;
	    };
	
	    /**
	     * @ignore
	     */
	    base._setLoadingArgs = me._setLoadingArgs;
	    me._setLoadingArgs = function()
	    {
	        var newArgs = {};
	        newArgs.childItemTyps = "";
	        newArgs.itemType = "";
	        newArgs.filter = "";
	        return newArgs;
	    };
	
	    function _delete_callback(p_context)
	    {
	        me._submitting = false;
	        if (p_context.successful)
	        {
	            _delete_success(p_context);
	        }
	        else
	        {
	            _delete_error(p_context);
	        }
	    }
		
	    
	    function _remove(p_node, p_parentNode)
	    {
	    	var parent = p_parentNode; 
	    	
	    	if(typeof(p_node.childNodes) == "function" && cube.isArray(p_node.childNodes()) && p_node.childNodes().length > 0)
	    	{
	    		var childNodes = p_node.childNodes();
	    		while (childNodes.length > 0)
	    		{
	    			_remove(childNodes[0], p_node);	
	    		}
	    	}
	    	else if (cube.isArray(p_node.childNodes) && p_node.childNodes.length > 0)
	    	{
	    		var childNodes = p_node.childNodes;
	    		while (childNodes.length > 0)
	    		{
	    			_remove(childNodes[0], p_node);	
	    		}
	    	}
	
	    	if (cube.isEmpty(parent))
			{
				var index = me.data.indexOf(p_node);
				if (index >= 0 && index < me.data().length)
			    {
			        me.data.splice(index, 1);
			    }
			}
			else if (typeof(parent.childNodes) == "function" && cube.isArray(parent.childNodes()))
			{
				parent.childNodes.remove(p_node);
			}
			else if (cube.isArray(parent.childNodes))
			{
				parent.childNodes.remove(p_node);
			}
			delete me.data()["#" + p_node[me.primaryKey]];
	    }
	    
	    function _delete_success(p_result)
	    {
	    	if (cube.isArray(_deletingNode)) {
	    		for (var i = 0; i < _deletingNode.length; i++) {
	    			var removeNode = me.getNodeEntity(_deletingNode[i].id, _deletingNode[i].itemType);
			        if (removeNode) {
			         	_remove(removeNode, me.data()["#"+_deletingNode[i].parentId]);
			        }
	    		}
	    	} else {
	    		var removeNode = me.getNodeEntity(_deletingNode.id, _deletingNode.itemType);
		        if (removeNode) {
		         	_remove(removeNode, me.data()["#"+_deletingNode.parentId]);
		        }
	    	}
			
	        var args =
	        {
	            node : _deletingNode
	        };
//	        me.trigger("deleted", args);
	        
	        if (cube.notEmpty(me._removeCallBack))
	        {
	            me._removeCallBack(args);
	        }
	    }
	
	    function _delete_error(p_context)
	    {
	        me._showError("DELETING", p_context);
	    }
	    
	    return me;
	}
});