<template>
  <div class='container'>
    <div class='meatitle'>取餐管理</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">


          <el-col :span='5' class='iptcol'>
            <el-form-item label="取餐方式:">
              <el-select v-model="queryform.mealTypevalue">
                <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="personnel">人员</button>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="sideboard">餐柜</button>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol' v-if="yuangong">
            <el-form-item label="员工姓名:">
              <el-input class="inputwidth"  v-model="queryform.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol' v-if="cangui">
            <el-form-item label="餐柜名称:">
              <el-input class="inputwidth"  v-model="queryform.sideboardname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol' v-if="yuangong">
            <el-form-item>
            <button class='search' @click.prevent @click="list">查询</button>
          </el-form-item>

          </el-col>
          <el-col :span='2'  class='iptcol' v-if="cangui">
            <el-form-item>
              <button class='search' @click.prevent @click="list2">查询</button>
            </el-form-item>

          </el-col>
          

        </el-row>
      </el-form>
      <div v-if="yuangong">
        <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
              color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
              fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
              fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                  size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
          <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
          <el-table-column align="center"  min-width="15%" prop="name" label="员工姓名"> </el-table-column>
          <el-table-column align="center"  min-width="15%" prop="no" label="员工编号"></el-table-column>
          <el-table-column align="center"  min-width="15%"  prop="phone" label="联系电话" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="15%"  prop="rank" label="职级" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="15%"   label="刷脸取餐" show-overflow-tooltip>
            <template slot-scope='scope'>
                <span class='clickspan' @click="updateface(scope.row.id,scope.row.face)">{{scope.row.face}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center"  min-width="15%"   label="刷卡取餐" show-overflow-tooltip>
            <template slot-scope='scope'>
                <span class='clickspan' @click="updatecard(scope.row.id,scope.row.card)">{{scope.row.card}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center"  min-width="15%"  label="工号取餐" show-overflow-tooltip>
            <template slot-scope='scope'>
                <span class='clickspan' @click="updatenumber(scope.row.id,scope.row.number)">{{scope.row.number}}</span>
            </template>
          </el-table-column>
        </el-table>

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
      </div>
      <div v-if="cangui">
        <el-table stripe ref="multipleTable" :data="tableData2" :cell-style="{padding:'0',
              color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
              fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
              fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                  size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
          <el-table-column align="center" min-width="10%" type="selection" width="55"> </el-table-column>
          <el-table-column align="center" min-width="15%" prop="name" label="餐柜名称"> </el-table-column>
          <el-table-column align="center" min-width="15%" prop="no" label="餐柜编号"></el-table-column>
          <el-table-column align="center" min-width="15%"  prop="specification" label="餐柜规格" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" min-width="15%"  prop="addr" label="位置" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" min-width="15%"  label="刷脸取餐" show-overflow-tooltip>
              <template slot-scope='scope'>
                <span class='clickspan' @click="updateface(scope.row.id,scope.row.face)">{{scope.row.face}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" min-width="15%"  label="刷卡取餐" show-overflow-tooltip>
            <template slot-scope='scope'>
                <span class='clickspan' @click="updatecard(scope.row.id,scope.row.card)">{{scope.row.card}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" min-width="15%"  label="工号取餐" show-overflow-tooltip>
            <template slot-scope='scope'>
                <span class='clickspan' @click="updatenumber(scope.row.id,scope.row.number)">{{scope.row.number}}</span>
            </template>
          </el-table-column>
        </el-table>

        <div style="text-align: right; margin-top: 10px;height:'15%'">
          <el-pagination
            @size-change="handleSizeChange2"
            @current-change="handleCurrentChange2"
            :current-page="pageIndex2"
            :page-sizes="pageSizes2"
            :page-size="pageSize2"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total2">
          </el-pagination>
        </div>
      </div>

    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        yuangong:true,
        cangui:false,
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        pageIndex2: 1,
        pageSize2:10,
        pageSizes2:[ 10, 20 , 30, 50, 100],
        total2:0,
        restaurantCode:'',//树状图id
        mealType: [],//餐别,
        queryform: {
          mealTypevalue:'',
          name:'',//人员姓名
          sideboardname:''//餐柜名称
        },
        tableData: [],
        tableData2: [],
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
      this.list()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
        this.list()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.list()
        // console.log(`当前页: ${val}`)
      },
      handleSizeChange2 (val) {
        this.pageSize2 = val
        this.list2()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange2 (val) {
        this.pageIndex2=val
        this.list2()
        // console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        if(this.yuangong==true){
        this.list()
        }else{
        this.list2()
        }
        // console.log(data)
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      // 树状图接口
      treelist(){
        this.$axios({
          url:'StOperationrestaurant.do/getStOperationList',
          method:'get'
        }).then(res=>{
          // console.log(res)
          this.data=res.data
        })
      },
      // 下拉框数据
      selectlist(){
        this.$axios({
          url:'StMealmanagement.do/dropDownBox',
          method:'get'
        }).then(res=>{
          this.mealType=res.data.mode
          // console.log(res.data)
        })
      },
      // 取餐管理查询
      list(){
        this.$axios({
          url:'StMealmanagement.do/directList',
          method:'post',
          data:{
            "pageNum":this.pageIndex,
            "pageSize":this.pageSize,
            "mode":this.queryform.mealTypevalue,		//取餐方式，传下拉框对应id
            "name": this.queryform.name,		//员工姓名
            "restaurant":this.restaurantCode		,//餐厅，传导航栏对应id
          }
        }).then(res=>{
          // console.log(res.data)
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      list2(){
        this.$axios({
          url:'StMealmanagement.do/plateList',
          method:'post',
          data:{
            "pageNum":this.pageIndex2,
            "pageSize":this.pageSize2,
            "mode":this.queryform.mealTypevalue,		//取餐方式，传下拉框对应id
            "name": this.queryform.sideboardname,		//员工姓名
            "restaurant":this.restaurantCode		,//餐厅，传导航栏对应id
          }
        }).then(res=>{
          // console.log(res.data.plate.list)
          this.tableData2=res.data.list
          this.total2=res.data.totalRecord
        })
      },
      
      personnel(){
        this.yuangong=true
        this.cangui=false
        this.list()
      },
      sideboard(){
        this.cangui=true
        this.yuangong=false
        this.list2()
      },
      updateface(id,face){
        this.$axios({
          url:'StMealmanagement.do/updateMode',
          method:'post',
          data:{
            "id":id,
            "face":face
          }
        }).then(res=>{
          if(res.data=='更改成功'){
            this.$message.success('更改成功')
            this.list()
            this.list2()
          }else{
            this.$message.error('更改失败')
          }
        })
      },
      updatecard(id,card){
        this.$axios({
          url:'StMealmanagement.do/updateMode',
          method:'post',
          data:{
            "id":id,
            "card":card
          }
        }).then(res=>{
          if(res.data=='更改成功'){
            this.$message.success('更改成功')
            this.list()
            this.list2()
          }else{
            this.$message.error('更改失败')
          }
        })
      },
      updatenumber(id,number){
        this.$axios({
          url:'StMealmanagement.do/updateMode',
          method:'post',
          data:{
            "id":id,
            "number":number
          }
        }).then(res=>{
          if(res.data=='更改成功'){
            this.$message.success('更改成功')
            this.list()
            this.list2()
          }else{
            this.$message.error('更改失败')
          }
        })
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
  .inputwidth{
    width: 160px;
  }
  .tablecard{
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    box-sizing: border-box;
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
  .el-pagination /deep/.el-input__inner{
    padding:0px;
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
    padding: 14px 0px 0px 50px;
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
    padding: 0 30px;
  }

  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px ;
    color: rgb(255,255,255);
  }
  .search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
  }
  .search:hover{
    cursor: pointer;
  }
  /deep/.el-table{
    background-color:transparent;
    height: 620px!important;
  }
   /deep/.el-table__body-wrapper{
    height: 580px!important;
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
  /deep/.el-tree-node:focus > .el-tree-node__content {
    background-color: transparent !important;
  }
  /deep/.el-tree-node__content{
    background:transparent;
  }
  /deep/.el-range-input{
    background-color:transparent;
  }
  /deep/.el-range-separator{
    color:white;
  }
  button:hover{
    background:url(../../assets/zy2.png) no-repeat;
    background-size:100% 100%;
  }
  .bcol /deep/.el-form-item__content{
    width:70%;
  }
  /deep/.el-date-editor{
    width: 200px;
  }
  /deep/.el-range__icon{
    line-height: 20px;
    margin-left: -5px;
  }
  /deep/.el-input__icon{
    width: 25px;
  }
  /deep/.el-range-input{
    font-size: 12px;
  }
  /deep/.el-range-separator{
    font-size: 12px;
    line-height: 28px;
  }
  /deep/.el-input__icon{
    line-height: 28px;
    width:25px;
  }
  /deep/.el-input__suffix{
    right: 5px;
  }
  .fhbox{
    margin-left: 20px;
    font-size: 16px;
    color: white;
  }
  .clickspan:hover{
    cursor: pointer;
  }
</style>

