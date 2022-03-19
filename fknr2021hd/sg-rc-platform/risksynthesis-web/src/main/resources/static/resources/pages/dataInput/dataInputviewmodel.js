define(["RESTClient","echarts", "axios",], function(RestClient, echarts, axios) {
    var PageViewModel = function(params) {
        var self = this;
        importExcel = function (obj) {
            var formData = new FormData();
            var name = $("#articleImageFile").val();
            formData.append("file", $("#articleImageFile")[0].files[0]);
            formData.append("name", name);//可以使用formData.append()传递多个参数
            // $.ajax({
            //     url: cube.gatewayURL + "importFile/importExcel",
            //     type: 'POST',
            //     async: false,
            //     data: formData,
            //     // 告诉jQuery不要去处理发送的数据
            //     processData: false,
            //     // 告诉jQuery不要去设置Content-Type请求头
            //     contentType: false,
            //     // beforeSend: function () {
            //     //     console.log("正在进行，请稍候");
            //     // },
            //     success: function (e) {
            //         if (e.code == 200) {
            //             //这里登录成功后进行页面跳转
            //             // window.location.href = '/index';
            //             alert("导入成功");
            //         } else {
            //             alert("导入成功!");
            //         }
            //     },
            //     error: function () {
            //         alert("导入失败!");
            //     }
            //
            // });

            var params = formData
            axios.post(cube.gatewayURL + "importFile/importExcel",params).then(function(res) {
                if (res.data.successful) {
                    cube.indicate("导入成功");

                } else {
                    cube.indicate("导入失败");
                }

            })



        }

        cube.endViewModel(self, params);
    };



    return PageViewModel;
});