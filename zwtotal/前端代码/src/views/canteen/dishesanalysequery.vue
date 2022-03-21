<template>
    <div class='container'>
        <div class='meatitle'>菜品评价分析查询</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>

            <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
            <el-row class="iptrow">
                <el-col :span='8' class='iptcol'>
                    <el-form-item>
                        <el-input style="width:90%;" v-model="queryform.name" placeholder="请输入菜品名称"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='6' class='iptcol'>
                    <el-form-item label="统计周期:">
                         <el-date-picker
                            v-model="queryform.strtime"
                            value-format="yyyy-MM-dd"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker><span class="fhbox">至</span>
                    </el-form-item>
                </el-col>
                <el-col :span='6' class='iptcol'>
                    <el-form-item>
                         <el-date-picker
                            v-model="queryform.endtime"
                            value-format="yyyy-MM-dd"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            </el-form>



            <div class='clearfix tablebox'>
              <el-table stripe ref="multipleTable" :data="tableDatafood" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             size="small"  style="width: 45%;margin-right:30px;float:left;" @selection-change="handleSelectionChange">
         <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center" min-width="8%" prop="name" label="菜品" ></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="verysatisfied" label="非常满意(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="satisfied" label="满意(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="commonly" label="一般(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="dissatisfied" label="不满意(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="verydissatisfied" label="非常不满(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="totaltimes" label="评价总次数"></el-table-column>
      </el-table>

      <el-table stripe ref="multipleTable" :data="tableDataitem" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             size="small"  style="width: 45%;margin-top:0px;float:left" @selection-change="handleSelectionChange">
         <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center" min-width="8%" prop="name" label="项目" ></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="verysatisfied" label="非常满意(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="satisfied" label="满意(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="commonly" label="一般(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="dissatisfied" label="不满意(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="verydissatisfied" label="非常不满(次数)"></el-table-column>
        <el-table-column align="center"  min-width="10%" prop="total" label="评价总次数"></el-table-column>
      </el-table>
      </div>



      <div>
                 <el-row class="iptrow">
                <el-col :span='20'>
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
             <div class='userassess' v-for='(item,index) in userassess' :key='item.date1.id'>
                 <div class="clearfix">
                   <div style="float:left;" v-show='keepassess' ref='keepassess'>
                    <div class="allass">整体评价: <el-rate disabled :value="item.date1.overallevaluation" class="rateclass"></el-rate></div>
                    <div class="allass">食堂服务: <el-rate disabled :value="item.date1.canteenservice" class="rateclass"></el-rate></div>
                    <div class="allass">用餐环境: <el-rate disabled :value="item.date1.environment" class="rateclass"></el-rate></div>
                   </div>
                   <div style="float:left;" v-show='reviseassess' ref='reviseassess'>
                    <div class="allass">整体评价: <el-rate v-model="item.date1.overallevaluation" class="rateclass"></el-rate></div>
                    <div class="allass">食堂服务: <el-rate v-model="item.date1.canteenservice" class="rateclass"></el-rate></div>
                    <div class="allass">用餐环境: <el-rate v-model="item.date1.environment" class="rateclass"></el-rate></div>
                   </div>
                    <div style="float:right;">
                    <button class='search' @click.prevent @click="oppodia(item.date1.id)">回复</button>
                     <el-dialog  title="回复" :visible.sync="dialogFormVisible" @close='cancel'>
                              <el-form :model="addform" ref="addform" label-width="80px" class="addcategory">
                                <el-form-item>
                                  <el-input class='dltextarea'  type="textarea" v-model="addform.remark"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <button class='search' @click.prevent @click="reply">添加</button>
                                <button class='search' @click.prevent @click='cancel'>取消</button>
                              </div>
                        </el-dialog>
                    <button class='search' @click.prevent ref='keepbtn' v-show='keepbtn' @click='togglekeep(index,item.uploadpictures)'>修改</button>
                    <button class='search' @click.prevent ref='revisebtn' v-show='revisebtn'
                     @click='togglerevise(index,item.date1.id,item.date1.content,item.date1.overallevaluation,item.date1.canteenservice,item.date1.environment,item.date)'>保存</button>
                    <button class='search' @click.prevent @click="delassess(item.date1.id)">删除</button>
                    <span class="userdate">{{item.date1.begintime}}</span>
                    </div>
                    
                 </div>
                <div v-show='keepfoodass' ref='keepfoodass'>
                    <div class="foodass"  v-for='i in userassess[index].date' :key='i.id'>{{i.disname}}: <el-rate disabled :value="i.score" class="rateclass"></el-rate></div>
                </div>
                <div  v-show='revisefoodass' ref='revisefoodass' >
                    <div class="foodass" v-for='i in userassess[index].date' :key='i.id'>{{i.disname}}: <el-rate  v-model="i.score" class="rateclass"></el-rate></div>

                </div>

            <div class="font" v-show='keepfont' ref='keepfont'>
              评论: {{item.date1.content}}
            </div>
            <div class="font" v-show='revisefont' ref='revisefont'>
              评论: <el-input style="width:20%;" v-model="item.date1.content"></el-input> 
            </div>
            <div class='userimg' v-show="keepimg" ref='keepimg'>
                <img :src="img" alt="" v-for="img in item.uploadpictures" :key='img.id'>
            </div>
            <div class='userimg' v-show='reviseimg' ref='reviseimg'>
              <div style="display:inline-block;position:relative" v-show='displayX' ref='displayX' v-for="(img,index) in item.uploadpictures" :key='img.id'>
                <img :src="img" alt="">
                <div class='keepdel' @click="delimg(item.uploadpictures,img,index)">X</div>
              </div>
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
      dialogFormVisible:false,
      surplusimg:[],
      displayX:true,
      value1:0,
      keepimg:true,
      reviseimg:false,
      keepfont:true,
      revisefont:false,
      keepfoodass:true,
      revisefoodass:false,
      keepassess:true,
      reviseassess:false,
      revisebtn:false,
      keepbtn:true,
      userassess:[],
      replyid:'',
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      tableDatafood:[],
      tableDataitem:[],
      restaurantCode:'',//树状图id
      filtercomments:'',
      queryform: {
        name: '',
        strtime: '',
        endtime:''
      },
      addform:{
        remark:'',
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
    // 菜品整体查询
    querylist(){
        this.$axios({
            url:'StDishesAnalysis.do/selectAnalysisOfMyFood',
            method:'post',
            data:{
                "restaurant":this.restaurantCode,
                "strDate":this.queryform.strtime,
                "endDate":this.queryform.endtime,
                "filtercomments":this.filtercomments,
                "pageNum": this.pageIndex,
                "pageSize":this.pageSize,
                "name":this.queryform.name
            }
        }).then(res=>{
            this.tableDatafood=res.data.map.Foods
            this.tableDataitem=res.data.map.AnalysisOfRestaurant
            this.userassess=res.data.map.AnalysisOfMyFoods
            this.total=res.data.totalRecord
        })
    },
    // 回复弹框
    oppodia(id){
      this.dialogFormVisible = true
      this.replyid=id
    },
    // 删除用户评论
    delassess(id){
      this.$confirm('确认删除该条评论?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'StDishesAnalysis.do/deleteReplay',
              method:'post',
              data:{
                id:id
              }
            }).then(res=>{
              if(res.data.msg=='删除评论成功'){
              this.$message.success('删除成功')
              this.querylist()
          }
        
        })
      }).catch(()=>{

      })

    },
    // 点击修改按钮
    togglekeep(index,items){
      for(var i=0;i<this.$refs.keepbtn.length;i++){
        console.log(this.$refs.keepbtn[i].style.disabled)
        this.$refs.keepbtn[i].disabled=true

      }
      this.$refs.keepbtn[index].style.display='none'
      this.$refs.revisebtn[index].style.display='inline-block'
      this.$refs.keepassess[index].style.display='none'
      this.$refs.reviseassess[index].style.display='inline-block'
      this.$refs.keepfoodass[index].style.display='none'
      this.$refs.revisefoodass[index].style.display='inline-block'
      this.$refs.keepfont[index].style.display='none'
      this.$refs.revisefont[index].style.display='block'
      this.$refs.keepimg[index].style.display='none'
      this.$refs.reviseimg[index].style.display='inline-block'
      this.surplusimg=items
    },
    // 点击保存按钮
    togglerevise(index,id,replay,overallevaluation,canteenservice,environment,data){
      console.log(this.surplusimg)
      for(var i=0;i<this.$refs.keepbtn.length;i++){
        console.log(this.$refs.keepbtn[i].style.disabled)
        this.$refs.keepbtn[i].disabled=false

      }
      this.$refs.revisebtn[index].style.display='none'
      this.$refs.keepbtn[index].style.display='inline-block'
      this.$refs.keepassess[index].style.display='inline-block'
      this.$refs.reviseassess[index].style.display='none'
      this.$refs.keepfoodass[index].style.display='inline-block'
      this.$refs.revisefoodass[index].style.display='none'
      this.$refs.keepfont[index].style.display='block'
      this.$refs.revisefont[index].style.display='none'
      this.$refs.keepimg[index].style.display='inline-block'
      this.$refs.reviseimg[index].style.display='none'
      this.$axios({
        url:'StDishesAnalysis.do/updateReplay',
        method:'post',
        data:{
            "id":id,
            "replay":replay,
            "overallevaluation":overallevaluation,
            "environment":environment,
            "canteenservice":canteenservice,
            "list":data,
            "images":this.surplusimg
        }
      }).then(res=>{
        if(res.data.msg=='修改成功'){
          this.$message.success('修改成功')
          this.querylist()
        }else{
          this.$message.error('修改失败')

        }
      })
    },
    // 删除用户评论
      delimg(items,img,index){
        this.$confirm('确认删除该菜品?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        items.splice(index,1)
        this.surplusimg=items
      }).catch(()=>{

      })


        
      },
    // 回复用户评论
    reply(){
      if(this.addform.remark==''){
          this.$message('回复不能为空')
          return
      }
      this.dialogFormVisible=false
      this.$axios({
        url:'StDishesAnalysis.do/replay',
        method:'post',
        data:{
          "id":this.replyid,
          "replay":this.addform.remark
        }
      }).then(res=>{
        if(res.data.msg=='用户评价回复成功'){
          this.$message.success('回复成功')
        }else{
          this.$message.error('回复失败')
        }
      })
      this.addform.remark=''
    },
    cancel(){
      this.dialogFormVisible=false
      this.addform.remark=''
    }
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
    overflow: auto;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:83%;
    height: 860px;
    float: right;
}
/deep/.el-table{
    background-color:transparent;
    height: 200px!important;
}
/deep/.el-table__body-wrapper{
  height: 160px!important;
  overflow-y: auto;
}
/deep/.cell{
    line-height: 16px;
}
/deep/.el-table td,
/deep/.el-table th.is-leaf{
  border-top:none;
  border-bottom:1px solid rgb(121,121,121);
  border-collapse: collapse;

}
/deep/.el-table tr{
    background-color: transparent;
}
/deep/.el-table__row{
    background: transparent;
}
/deep/.el-table th, .el-table tr {
    background-color:transparent;
}

/deep/.el-table--striped .el-table__body tr.el-table__row--striped td{
    background-color:rgb(30,33,66);
}
/deep/.el-table tbody tr:nth-child(odd):hover>td {
    background-color:transparent!important;
}
/deep/.number,/deep/.btn-prev,/deep/.btn-next{
  background: transparent;
  color: white;
}
.el-table::before{
  display:none;
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
   padding: 20px 0px 0px 0px;
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
    margin-right: 10px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.search,.increase:hover{
  cursor: pointer;
}
/deep/.el-tree-node:focus > .el-tree-node__content {
    background-color: transparent !important;
}
/deep/.el-tree-node__content{
background:transparent;
}
button:hover{
      background:url(../../assets/zy2.png) no-repeat;
      background-size:100% 100%;
}
/deep/.el-date-editor{
    width: 290px;
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
.tablebox{
  border-bottom:1px solid rgb(0,152,255);
}
.allass{
    display: inline-block;
    color: white;
    font-size: 12px;
    margin:10px 10px 10px 0;
    height: 30px;
    line-height:30px ;
}
.rateclass{
    display: inline-block;
    height:100%;
    vertical-align: middle;
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
.keepdel{
  position: absolute;
  color:red;
  background-color: black;
  width: 20px;
  height: 20px;
  text-align: center;
  top:2px;
  right:21px;
  font-size: 14px;
}
.keepdel:hover{
  cursor: pointer;
}
.reply{
    color: white;
    font-size: 12px;
    overflow: hidden;
}
.foodass{
    display: inline-block;
    color: white;
    font-size: 12px;
    margin:0 10px 10px 0;
    height: 30px;
    line-height:30px ;
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
.userdate{
  color: white;
  font-size: 12px;
}
/deep/.el-dialog{
  // background: transparent;
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  margin-top:200px!important;
  width:700px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:rgba(0,0,0,.4);
  border-bottom:1px solid rgba(255,255,255,.5);
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:rgba(0,0,0,.4);
  padding-bottom:2px;
  padding:20px 20px 0 20px;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 20px;
  background:rgba(0,0,0,.4);
}
/deep/.el-dialog__title{
  font-size: 12px;
  color:rgb(255,255,255);

}
.addcategory /deep/.el-input__inner{
    background: black;
}
.addcategory /deep/.el-textarea__inner{
    background: black;
    border:1px solid rgb(52,57,82);
    color:white;
    font-size: 12px;
}
.addcategory /deep/.el-form-item{
  margin-bottom: 0px;
}
.btnaddchild,.btnedit{
  background-color:rgb(32,47,66);
  width: 60px;
  height:30px;
  line-height:0;
  padding:10px 0 10px 5px;
  font-size: 12px;
}
/deep/.btnaddchild span{
  line-height:0;
  margin-right:5px ;
}
/deep/.el-form-item__content{
  margin: 0px!important;
}
.dltextarea /deep/.el-textarea__inner{
  height:300px;
}
.userassess{
  margin-bottom: 60px;
}
</style>