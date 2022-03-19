define([ 'text' ], function(text) {

	//网关路由地址
	// cube.gatewayURL = "http://127.0.0.1:10036/risk-management-server/api/";
    // cube.gatewayURL2 = "http://127.0.0.1:10040/risk-dataanalyze-server/api/";
    // cube.gatewayURL = "http://172.28.1.189:10036/risk-management-server/api/";
    // cube.gatewayURL2 = "http://172.28.1.189:10040/risk-dataanalyze-server/api/";
    // cube.gatewayURL = "http://10.40.50.168:10036/risk-management-server/api/";
    // cube.gatewayURL2 = "http://10.40.50.168:10040/risk-dataanalyze-server/api/";
    // cube.gatewayURL = "http://10.40.70.245:10036/risk-management-server/api/";
    // cube.gatewayURL2 = "http://10.40.70.245:10040/risk-dataanalyze-server/api/";
    // cube.gatewayURL = "http://172.28.5.39:10036/risk-management-server/api/";
    // cube.gatewayURL2 = "http://172.28.5.39:10040/risk-dataanalyze-server/api/";
//网关地址
        cube.isLocal=false;
        /*cube.gateway = "http://172.28.1.225:80";
        cube.locPerception = "http://172.28.1.225:80";*/
        cube.gateway = "http://27.132.129.83:80";
        cube.locPerception = "http://27.132.129.83:80";
        /*cube.gateway = "http://127.0.0.1:10036";
        cube.locPerception = "http://127.0.0.1:10036";*/
        // cube.gateway = "http://172.28.1.189:80";
        // cube.locPerception = "http://172.28.1.189:80";



        /** 全景感知  **/
    // cube.LARGESCREEN = joinUrl(cube.locPerception, "/risk-management-server", "/api/");
    cube.gatewayURL= joinUrl(cube.locPerception,"/risk-management-server","/api/");
    cube.gatewayURL3 = joinUrl(cube.locPerception,"/portal-homepage-backend","/api/");
        /** 大数据分析  **/
    // cube.gatewayURL2 = joinUrl(cube.locPerception,"/risk-dataanalyze-server","/api/");

        /**
         * 如果是本地开发，使用传入的网关路径，不是本地开发，使用统一的网关
         */
    function joinUrl(gateway, context, basePath) {
        if (cube.isLocal) {
            return gateway + context + basePath;
        }
        return cube .gateway + context + basePath;
    }

    cube.componentDefaultValue = {
        "modaldialog":{
            width:"800px", // 弹窗 宽度
            height:"500px", // 高度
            confirmBtnText:"保存", // 保存
            closeBtnText:"取消", // 取消
            title:"详情", // 标题
            isShowDialog:true, // 是否显示对话框
            isShowCloseBtn:true, // 是否显示取消按钮
            isShowConfirmBtn:true, //  是否显示保存按钮
            submitFormOnConfirm: true // 是否点击确定按钮时提交窗口内表单（如果窗口内存在表单）
        }
    }
    cube.importComponent("container.accordion");
    cube.importComponent("container.tabcontainer");
    cube.importComponent("container.panelcontainer");
    cube.importComponent("container.hsplit");
    cube.importComponent("container.vsplit");
    cube.importComponent("controls.toolbar");
    cube.importComponent("controls.titlebar");
    cube.importComponent("controls.navbar");
    cube.importComponent("controls.loading");
    cube.importComponent("controls.cubebutton");
    cube.importComponent("controls.cubelabel");
    cube.importComponent("controls.menu");
    cube.importComponent("controls.contextmenu");
    cube.importComponent("datacontainer.tree");
    cube.importComponent("datacontainer.treegrid");
    cube.importComponent("datacontainer.treegriditem");
    cube.importComponent("datacontainer.datagrid");
    cube.importComponent("datacontainer.dataform");
    cube.importComponent("datacontainer.groupheadergrid");
    cube.importComponent("datacontainer.pagenavibar");
    cube.importComponent("datacontainer.searchbox");
    cube.importComponent("editor.commoneditor");
    cube.importComponent("editor.datetimeeditor");
    cube.importComponent("editor.dropdowneditor");
    cube.importComponent("editor.dropdowntreeeditor");
    cube.importComponent("editor.fileeditor");
    cube.importComponent("editor.imageeditor");
    cube.importComponent("editor.transfereditor");
    cube.importComponent("dialog.modaldialog");
    cube.importComponent("dialog.msgdialog");
    cube.importComponent("layout.include");
    cube.importComponent("layout.borderlayout");
    cube.importComponent("layout.fluidlayout");
    cube.importComponent("layout.gridlayout");
    cube.importComponent("html5.formeditor");
    cube.importComponent("html5.richeditor");
    cube.importComponent("datacontainer.chart");
    cube.importComponent("controls.progressbar");
},

);
