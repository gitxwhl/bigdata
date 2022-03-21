<template>
    <div class='container'>
        <div class='meatitle'>菜品评价</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
        <div class="headbox">
            <div class="leftbox">
                <div class="ratebox">整体评价: <el-rate v-model="value1" class="rateclass"></el-rate></div>
                <div class="ratebox">用餐环境: <el-rate v-model="value2" class="rateclass"></el-rate></div>
                <div class="ratebox">食堂服务: <el-rate v-model="value3" class="rateclass"></el-rate></div>
            </div>
            <div class="textareabox">
                <el-form :model="queryform">
                    <el-form-item>
                        <el-input type="textarea" v-model="queryform.equipmentName"></el-input>
                    </el-form-item>
                </el-form>
            </div>
             <input style="display:none;" multiple="multiple" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel($event)">
            <div class='importimg' @click="leadingin">
                单击上传图片...
            </div>
            <div class='imgbox' v-for="item in img" :key="item.size">
                <img :src="item" alt="">
            </div>
            <div class='publishbt'>
                <button class='search' @click.prevent @click='userpublish'>发表</button>
            </div>
        </div>


        <div class='dishesassess'>
            <div style="margin-bottom:10px;">菜品评价:</div>
            <div class='assessdelite' v-for="item in assess" :key='item.id'>
                <div class='assessbox'><img :src="item.foodpictures" alt=""></div><el-rate v-model="item.score" class='ratetwo'></el-rate>
            </div>
        </div>


         <div>
             <el-form>
                 <el-row class="iptrow">
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="周期:">
                         <el-date-picker
                            v-model="strtime"
                            value-format="yyyy-MM-dd"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker><span class="fhbox">至</span>
                    </el-form-item>
                </el-col>
                <el-col :span='4' class='iptcol'>
                    <el-form-item>
                         <el-date-picker
                            v-model="endtime"
                            value-format="yyyy-MM-dd"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span='15'>
                <button class='search' @click.prevent @click="btnqb">全部</button>
                <button class='search' @click.prevent @click="btnst">晒图</button>
                <button class='search' @click.prevent @click="btngpf">高评分</button>
                <button class='search' @click.prevent @click="btnychjj">用餐环境佳</button>
                <button class='search' @click.prevent @click="btnctfwh">餐厅服务好</button>
                <button class='search' @click.prevent @click="btncpkwj">菜品口味佳</button>
                <button class='search' @click.prevent @click="btndf">低分</button>
                <button class='search' @click.prevent @click="btnychjc">用餐环境差</button>
                <button class='search' @click.prevent @click="btnctfwc">餐厅服务差</button>
                <button class='search' @click.prevent @click="btnkwbj">口味不佳</button>
                </el-col>
            </el-row>
             </el-form>
             <div class="userassess"  v-for='(item,index) in userassess' :key='index'>
                 <div>
                    <div class="allass">整体评价: <el-rate disabled :value="item.date1.overallevaluation" class="rateclass"></el-rate></div>
                    <div class="allass">食堂服务: <el-rate disabled :value="item.date1.canteenservice" class="rateclass"></el-rate></div>
                    <div class="allass">用餐环境: <el-rate disabled :value="item.date1.environment" class="rateclass"></el-rate></div>
                    <div class='userdate'>{{item.date1.begintime}}</div>
                 </div>
                <div>
                    <div class="foodass" v-for='i in userassess[index].date' :key='i.id'>{{i.disname}}: <el-rate disabled :value="i.score" class="rateclass"></el-rate></div>
                </div>
            <div class="font">
                评论: {{item.date1.content}}
            </div>
            <div class='userimg'>
                <img :src="img" alt="" v-for="img in item.uploadpictures" :key='img.id'>
            </div>
            <div class="reply" v-for='replay in item.replay' :key='replay.id'>
                <div style="float:left;">回复：</div>
                <div style="float:right;">{{replay.replaydate}}</div>
                <div style="float:left;">{{replay.replay}}</div>
            </div>
            </div>

         </div>



      <div style="text-align: right; margin-top: 10px;height:'15%'">
        <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageIndex"
      :page-sizes="pageSizes"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
      </div>
    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      value1:null,
      value2:null,
      value3:null,
      ttt:'',
      assess:[],
      foodreview:[],
      files:[],
      strtime:'',
      endtime:'',
      base:this.$axios.defaults.baseURL,
      filtercomments:'',
      userassess:[],//用户评价
      img:[],
      imgsrc:'',
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      restaurantCode:'',//树状图id
      queryform: {
        equipmentName: '',
        equipmentCode: ''
      },
      data: [],
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    this.treelist()
    this.querylist()
    this.foodassess()
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize=val
      this.querylist()
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.pageIndex=val
      this.querylist()
      console.log(`当前页: ${val}`)
    },
    handleNodeClick (data) {
      this.restaurantCode=data.restaurantCode
      this.querylist()
      console.log(data)
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    btnqb(){
        this.filtercomments=''
        this.querylist()
    },
    btnst(){
        this.filtercomments=1
     this.querylist()

    },
    btngpf(){
        this.filtercomments=2
      this.querylist()
    },
    btnychjj(){
        this.filtercomments=4
      this.querylist()
    },
    btnctfwh(){
        this.filtercomments=6
       this.querylist()
    },
    btncpkwj(){
        this.filtercomments=8
      this.querylist()
    },
    btndf(){
        this.filtercomments=3
        this.querylist()
    },
    btnychjc(){
        this.filtercomments=5
        this.querylist()
    },
    btnctfwc(){
        this.filtercomments=7
           this.querylist()
    },
    btnkwbj(){
        this.filtercomments=9
          this.querylist()
    },
    // 树状图接口
    treelist(){
      this.$axios({
        url:'StOperationrestaurant.do/getStOperationList',
        method:'get'
      }).then(res=>{
        this.data=res.data
      })
    },
    // 菜品评价查询
    querylist(){
        this.$axios({
            url:'StDishesAnalysis.do/selectUserComment',
            method:'post',
            data:{
                "restaurant":this.restaurantCode,
                "strDate":this.strtime,
                "endDate":this.endtime,
                "filtercomments":this.filtercomments,
                "pageNum": this.pageIndex,
                "pageSize":this.pageSize,
            }
        }).then(res=>{
            this.total=res.data.totalRecord
            // this.userassess=res.data.list
            console.log(this.userassess)
            for(var i=0;i<res.data.list.length;i++){
            res.data.list[i].date=res.data.list[i].date.filter(item=>{
                    return item.score>0
                })
            }
            this.userassess=res.data.list

        })
    },
    // 菜品评价接口
    foodassess(){
        this.$axios({
            url:'StDishesAnalysis.do/selectIntelligenceMeals',
            method:'post',
            data:{
                "restaurant":this.restaurantCode,
            }
        }).then(res=>{
            this.assess=res.data
        })
    },





    // 单击上传图片按钮
    importExcel(e){
        for(var i=0;i<this.$refs.articleImageFile.files.length;i++){
            this.img.push(URL.createObjectURL(this.$refs.articleImageFile.files[i]))
            this.files.push(this.$refs.articleImageFile.files[i])
            // var formData = new FormData();
            // formData.append("file",this.$refs.articleImageFile.files[0]);
            console.log(this.files,this.$refs.articleImageFile.files[0],e)
        }

    },
    // 用户发表formdata
    // userpublish(){

    //     console.log(this.files)
    //     var formData = new FormData();
    //     // for(var i=0;i<this.files.length;i++){
    //     // formData.append("files",this.files[i]);
    //     // }
    //     formData.append("multipartFile",this.files[0]);
    //     formData.append("content",this.queryform.equipmentName);
    //     formData.append("list",JSON.stringify(this.assess));
    //     formData.append("overallevaluation",this.value1);
    //     formData.append("environment",this.value2);
    //     formData.append("canteenservice",this.value3);
    //     formData.append("user",'22');
    //     formData.append("dishevaluation",'55');
    //     this.$axios({
    //         url:'StDishesAnalysis.do/postComment',
    //         method:'post',
    //         // data:{
    //         //     formData,
    //         //     "content":this.queryform.equipmentName,
    //         //     "list":this.assess,
    //         //     "overallevaluation":this.value1,
    //         //     "environment":this.value2,
    //         //     "canteenservice":this.value3
    //         // }
    //         data:formData,
    //         // headers: { 'Content-Type': 'application/x-www-form-urlencoded' }

    //     })
    // },



    // 用户发表base64
    // userpublish(){
    //     var reader = new FileReader();
    //     for(var i=0;i<this.files.length;i++){
    //         reader.readAsDataURL(this.files[i]);
    //     }
    //     var ttt;
    //     var that=this
    //     reader.onload = function (e) {
    //         ttt = e.target.result;
    //     console.log(ttt,1)
    //     that.$axios({
    //         url:'StDishesAnalysis.do/postComment',
    //         method:'post',
    //         data:{
    //             "files":ttt,
    //             "content":that.queryform.equipmentName,
    //             "list":that.assess,
    //             "overallevaluation":that.value1,
    //             "environment":that.value2,
    //             "canteenservice":that.value3
    //         }

    //     })

    //     }

    // },



    // 用户发表base64
    userpublish(){
        if(this.restaurantCode==''){
          this.$message('请先选择餐厅')
          return
        }
        if(this.files.length==0){
            this.$axios({
                url:'StDishesAnalysis.do/postComment',
                method:'post',
                data:{
                "files":this.files,
                "content":this.queryform.equipmentName,
                "list":this.assess,
                "user":'22',
                "dishevaluation":this.restaurantCode,
                "overallevaluation":this.value1,
                "environment":this.value2,
                "canteenservice":this.value3
            }
            }).then(res=>{
                if(res.data.msg=='评论成功'){
                    this.$message.success("发表评价成功")
                    window.setTimeout(function(){
                        location.reload()
                    },1500)
                }
            })
        }else {
            for(var i=0;i<this.files.length;i++){
        var reader = new FileReader();
            reader.readAsDataURL(this.files[i]);
            console.log(this.files[i])
        var ttt=[];
        var that=this
        reader.onload = function (e) {
            ttt.push(e.target.result);
        console.log(ttt)
        if(ttt.length==that.files.length){
            console.log(13)
            that.$axios({
            url:'StDishesAnalysis.do/postComment',
            method:'post',
            data:{
                "files":ttt,
                "content":that.queryform.equipmentName,
                "list":that.assess,
                "user":'22',
                "dishevaluation":that.restaurantCode,
                "overallevaluation":that.value1,
                "environment":that.value2,
                "canteenservice":that.value3
            }
        }).then(res=>{
            if(res.data.msg=='评论成功'){
                    that.$message.success("发表评价成功")
                    window.setTimeout(function(){
                        location.reload()
                    },1500)
                }
        })
        }
        }
        }
        }
    },

    // 调试图片接口
    // importExcel(){
    //         var formData = new FormData();
    //         formData.append("file",this.$refs.articleImageFile.files[0]);
    //               this.$axios({
    //                       url:'StfoodManagement.do/imgUploading',
    //                       method: 'post',
    //                       data:formData,
    //                       // headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    //                   }).then(res=>{
    //                     if(res){
    //                         this.imgsrc=this.$axios.defaults.baseURL+res.data
    //                           console.log(this.imgsrc)
    //                     }else{

    //                     }
    //                   })

    // },
    // 上传图片按钮
    leadingin(){
        this.$refs.articleImageFile.click()
      },
  }
}
</script>

<style lang="less" scoped>
.container{
   padding:15px 10px 0px 10px;
   box-sizing: border-box;
   color: rgb(255,255,255);
   font-size: 14px;
   height: 100%;
}
.meatitle{
    margin-bottom:6px;
}
.opmainrescard{
    background-color:rgba(1,5,22,.4);
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width: 16%;
    height: 860px;
    float: left;
}
.opmainreshead{
    height:44px;
    text-align: center;
    padding-top:6px;
    box-sizing: border-box;
    font-size: 16px;
    color:rgba(255,255,255);
    margin-bottom:26px ;
}
.el-tree{
  background:transparent;
  color: white;
}
/deep/.el-tree-node__label{
  font-size:14px;
}
/deep/.el-tree-node.is-current > .el-tree-node__content {
    background-color: transparent !important;
}
.tablecard{
    background-color:rgba(1,5,22,.4);
    padding:0 20px 0 30px;
    box-sizing: border-box;
    overflow: auto;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:83%;
    height: 860px;
    float: right;
}
/deep/.number,/deep/.btn-prev,/deep/.btn-next{
  background: transparent;
  color: white;
}
/deep/.el-pagination button:disabled{
  background: transparent;
}
.queryform{
    width:100%;
    margin-top:0px;
    margin-bottom:0px;
}
.iptrow{
   width:100%;
   height:56px;
   margin-bottom:0px;
   padding: 14px 0px 0px 10px;
   box-sizing: border-box;
}
.iptcol{
    height:40px;
    line-height: 40px;
    margin-bottom:0;
}
 /deep/.el-input__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    vertical-align: middle;
    background-color:black;
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:0px;
    padding-left:30px;
    height:32px;
}
/deep/.el-form-item__label{
  font-size: 12px;
  line-height: 28px;
  padding-right:12px;
  color: rgb(255,255,255);
}
.search,.increase{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 90px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.search,.increase:hover{
  cursor: pointer;
}
/deep/.el-tree-node:focus >.el-tree-node__content {
    background-color: transparent !important;
}
/deep/.el-tree-node__content{
background:transparent;
}
button:hover{
      background:url(../../assets/zy2.png) no-repeat;
      background-size:100% 100%;
}
.headbox{
    margin-top:40px;
    color:white;
    font-size: 12px;
    position: relative;
}
.leftbox{
    display: inline-block;
    width: 270px;
    height: 100px;
}
.ratebox{
    height: 33px;
    line-height: 33px;
}
.rateclass{
    display: inline-block;
    height:100%;
    vertical-align: middle;
}
.ratetwo{
    display: inline-block;
    position:absolute;
    bottom:10px;
}
/deep/.el-rate__icon{
    font-size: 26px;
    vertical-align: middle;
}
.textareabox{
    display: inline-block;
    width: 480px;
    height:100px;
    vertical-align: top;
}
.textareabox /deep/.el-textarea__inner{
    width: 470px;
    height: 100px;
    padding: 5px 15px;
    font-size: 12px;
    color:white;
    background: black;
}
/deep/.is-in-pagination /deep/.el-input__inner{
    padding:0;
}
.importimg{
    width: 145px;
    height:80px;
    background: url(../../assets/u839.svg);
    background-size:145px 80px;
    display: inline-block;
    vertical-align: top;
    line-height: 80px;
    text-align: center;
    margin-right: 10px;
    margin-top: 10px;
}
.imgbox{
    width: 150px;
    height:85px;
    background-size:150px 85px;
    display: inline-block;
    vertical-align: top;
    line-height: 80px;
    text-align: center;
    margin-right: 10px;
    margin-top: 10px;
}
.imgbox img{
    width: 150px;
    height: 85px;
}
.publishbt{
    position: absolute;
    top:70px;
    right:10px;
}
.dishesassess{
    color:white;
    font-size: 12px;
    margin-top: 10px;
    border-bottom: 1px solid #0098FF;
}
.assessdelite{
    display: inline-block;
    position: relative;
    width: 240px;
    height: 72px;
    margin:0 10px 20px 0;
}
.assessbox{
    float: left;
    width: 53px;
    height: 72px;
    border: 1px solid white;
    margin-right:5px;
}
.assessdelite img{
    width: 53px;
    height: 72px;
}
/deep/.el-date-editor{
    width: 245px;
}
/deep/.el-input__icon{
    line-height: 34px;
    width: 25px;
}
.fhbox{
    color: white;
    font-size: 12px;
    margin-left:5px;
}
.allass{
    display: inline-block;
    color: white;
    font-size: 12px;
    margin:10px 10px 10px 0;
    height: 30px;
    line-height:30px ;
}
.foodass{
    display: inline-block;
    color: white;
    font-size: 12px;
    margin:0 10px 10px 0;
    height: 30px;
    line-height:30px ;
}
.font{
    color:white;
    font-size: 12px;
    margin: 5px 0 20px 0;
}
.userimg img{
    width: 150px;
    height: 85px;
    margin:0 20px 10px 0;
}
.reply{
    color: white;
    font-size: 12px;
    overflow: hidden;
    padding:3px 20px ;
}
.userassess{
    margin-bottom:60px;
}
.userdate{
    float: right;
    font-size: 12px;
    color: white;
    margin-right: 20px;
}
</style>
