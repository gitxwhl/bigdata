<template>
    <div class='container'>
        <div class='meatitle'>我的菜品</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <el-row class="iptrow">
                 <el-col :span='5' class='iptcol'>
                    <el-form-item label="菜品名称:" prop="name">
                           <el-input class='name' v-model="queryform.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='13' class='iptcol'>
                    <el-form-item label="菜品类别:" prop="metereq">
                        <el-select class='type' v-model="queryform.metereq">
                                    <el-option v-for="item in foodcategory" :key="item.parakey" :label="item.paravalue" :value="item.parakey">
                                  </el-option>
                              </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click="querybt">查询</button>
                    </el-form-item>
                </el-col>
                 <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='zsearch' @click.prevent @click="querylistbt">全量库查询</button>
                    </el-form-item>
                </el-col>
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='reset' @click.prevent @click="resetform('queryform')">一键重置</button>
                    </el-form-item>
                </el-col>
            </el-row>
            </el-form>
        <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="20%" prop="name" label="名称"> </el-table-column>
                <el-table-column align="center"  min-width="15%" prop="category" label="类别"></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="referenceprice" label="参考价格(元)" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="weight" label="重量" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="energy" label="能量" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="remarks" label="备注" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="20%" label="操作" >
                    <template slot-scope="scope">
                            <span  class="binicon" v-if="delbt"><img src="../../assets/u349.svg" alt="" @click="deletefood(scope.row.id)"> 删除</span>
                            <span  class="binicon" v-if='addbt'><img src="../../assets/u363.svg" alt="" @click='addfood(scope.row.dishcode)'> 添加</span>
                            <span  class="editicon"><img src="../../assets/u8075.svg" alt=""  @click="nutanaly(scope.row.dishcode)"> 营养成分详情表</span>
                            
                    </template>
                </el-table-column>
        </el-table>
        <el-dialog  title="营养成分表" :visible.sync="dialogNutVisible">
                               <table border="1" class="nuttable">
                                    <tr class='tablehead'>
                                        <td>能量</td>
                                        <td>每100克(g)</td>
                                        <td>营养素参考值%</td>
                                    </tr>
                                    <tr v-for="item in nuttable" :key="item.name">
                                        <td>{{item.name}}</td>
                                        <td>{{item.energy}}</td>
                                        <td>{{item.energyPro}}%</td>
                                    </tr>
                                </table>
                            </el-dialog>
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
      delbt:true,
      addbt:false,
      dialogNutVisible: false,
      nuttable:[],
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      restaurantCode:'',//树状图code码
      queryform: {
        metereq: '',
        name: ''
      },
      foodcategory: [],
      tableData: [],
      data: [],
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    this.treelist()
    this.selectlist()
    this.pagelist()
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize=val
      if(this.delbt==true){
      this.pagelist()
      }else if(this.addbt==true){
        this.querylist()
      }
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.pageIndex=val
      if(this.delbt==true){
      this.pagelist()
      }else if(this.addbt==true){
        this.querylist()
      }
      console.log(`当前页: ${val}`)
    },
    handleNodeClick (data) {
     this.restaurantCode=data.restaurantCode
     this.pagelist()
      console.log(data)
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    //树状图接口
    treelist(){
      this.$axios({
        url: 'StOperationrestaurant.do/getStOperationList',
        method: 'post',
      }).then(res=>{
          this.data=res.data
      })
    },
    // 获取下拉框数据
    selectlist(){
      this.$axios({
        url:'/StMyfoodController.do/selectdish',
        method:'get',
      }).then(res=>{
          console.log(res)
          this.foodcategory=res.data    
      })
    },
    // 查询列表
    pagelist(){
      
      this.$axios({
        url:'StMyfoodController.do/selectByRestaurant',
        method:'post',
        data:{
        "pageNum": this.pageIndex,
        "pageSize":this.pageSize,
        "restaurant": this.restaurantCode,
        "name":this.queryform.name,
        "category":this.queryform.metereq,
         }
      }).then(res=>{
        this.addbt=false
        this.delbt=true
        console.log(res)
        this.tableData=res.data.list
        this.total=res.data.totalRecord
      })
    },
    // 查询按钮
    querybt(){
      this.pageIndex=1
      this.pagelist()
    },
    // 全量库查询
    querylist(){
      this.$axios({
        url:'StMyfoodController.do/selectNameByFoodManage',
        method:'post',
        data:{
          "pageNum": this.pageIndex,
          "pageSize":this.pageSize,
          "name":this.queryform.name,
          "category":this.queryform.metereq,
        }
      }).then(res=>{
        this.addbt=true
        this.delbt=false
         console.log(res)
        this.tableData=res.data.list
        this.total=res.data.totalRecord
      })
    },
    // 全量库查询按钮
    querylistbt(){
      this.pageIndex=1
      this.querylist()
    },
    // 删除
      deletefood(id){
    this.$confirm('确认删除该菜品?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'StMyfoodController.do/deleteMyFoodById',
              method:'post',
              data:{
              "id":id
      }
            }).then(res=>{
              if(res.data.message=='删除成功！'){
                this.$message.success('删除成功!')
                this.pagelist()
              }else{
                this.$message('删除失败!')
                this.pagelist()
              }
        })
      }).catch(()=>{

      })
  },
  // 菜品添加按钮
      addfood(id){
        this.$confirm('是否将次菜品添加到查询列表?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'StMyfoodController.do/insertMyFood',
              method:'post',
              data:{
              "restaurant": this.restaurantCode,
              "dishcode":id
      }
            }).then(res=>{
              if(res.data.message=='添加菜品成功'){
                  this.$message.success('菜品添加成功')
              }else {
                this.$message('菜品添加失败')
              }
        })
      }).catch(()=>{

      })
      },
    // 营养成分分析
    nutanaly(id){
      this.dialogNutVisible = true
      this.$axios({
        url:'StMyfoodController.do/CalculationNutritional',
        method:'post',
        data:{
          "dishcode":id
        }
      }).then(res=>{
        this.nuttable=res.data.data
        console.log(this.nuttable)
      })
    },
    // 一键重置
    resetform(queryform){
    this.$refs[queryform].resetFields()
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
    margin-right: 20px;
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
    padding:0 15px;
    box-sizing: border-box;
    border-radius: 0;
    overflow: auto;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:82%;
    height: 860px;
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
   padding: 14px 0px 0px 0px;
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
    height:32px;
}
/deep/.el-form-item__label{
  font-size: 12px;
  line-height: 28px;
  padding-right: 12px;
  color: rgb(255,255,255);
}
.search,.zsearch,.reset{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    padding: 0;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.search,.zsearch,.reset:hover{
    cursor: pointer;
}
/deep/.el-table{
    background-color:transparent;
}
 /deep/.el-table__body-wrapper{
    height: 660px!important;
    overflow-y:auto;
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
.binicon{
  width:80px;
  height: 30px;
  line-height:30px;
  float: left;
}
.binicon img{
  width:18px;
  height:16px;
  vertical-align: middle;
}
.binicon img:hover{
  cursor: pointer;
}
.editicon{
  width:120px;
  height: 20px;
  line-height:30px;
  float: right;
  margin-right:50px ;
}
.editicon img{
  width:18px;
  height:16px;
  vertical-align: middle;
}
.editicon img:hover{
  cursor: pointer;
}
/deep/.el-textarea__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 600px;
    height: 140px;
    vertical-align: middle;
    background-color:black;
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:4px;
}
.tab{
    font-size: 18px;
    color:red;
    padding-left:16px;
}

/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  width: 800px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background: transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
}
/deep/.el-dialog__body{
    color:rgb(255,255,255);
    background: transparent;
    padding: 30px 20px;
    box-sizing: border-box;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;
  background: transparent;
}
/deep/.el-dialog__title{
  line-height: 24px;
  font-size: 14px;
  color:rgb(255,255,255);
}
/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
    margin-top:-8px;

}
.nuttable{
    width:100%;
    table-layout:fixed;
    background:transparent;
    border-collapse: collapse;
}
.nuttable td{
    height:30px;
    padding:0 0 0 5px;
    font-size: 10px;
    box-sizing: border-box;
    background:transparent;
    border:1px solid rgb(128,128,128);
}
.tablehead{
    background:transparent;

}
.nuttable tr td{
    background:transparent!important;
}

.tablehead td{
    width: 120px;
    height:30px;
    text-align: center;
    font-size: 12px;
}

/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
}
tr:hover > td{
    background-color:transparent!important;
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
.name{
  width: 200px;
}
.type{
  width: 230px;
}
/deep/.el-input__suffix{
  right: 5px;
  text-align: center;
}
/deep/.el-input__icon{
  line-height: 28px;
  width: 25px;
}
/deep/.el-dialog__headerbtn{
    top:20px;
    right: 20px;
}
</style>
