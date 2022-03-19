define([ 'text' ], function(text) {
	//网关路由地址
	//cube.gatewayURL = "http://172.16.100.250:10002/dip/";
	// cube.gatewayURL = "http://10.40.70.90/insight_backend/api/";
	cube.gatewayURL = "http://10.40.70.95:10010/risksynthesis-server/api/"; //电网风险管控
    cube.safetygatewayURL = "http://10.40.70.95:10020/enterprisesadmittance-server/api/"; //安全准入
    // cube.gatewayURL2 = "http://10.40.70.95:10040/risk-dataanalyze-server/api/";
	cube.PROJECT = "insight.";
	// 注册组件根据项目实际注册所用到的组件，组件名称可查看API手册
	// cube.loadWebpage({
	// 	components : [
	// 		'datacontainer.chart',
	// 		'editor.commoneditor',
	// 		'controls.cubelabel'
	// 	]
    // });
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
});
