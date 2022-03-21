<template>
  <div class='container'>
    <div class='meatitle'>事故记录</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
    <div class='cardleft'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='5' class='iptcol'>
            <el-form-item >
              <el-date-picker
                v-model="queryform.beginTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker><span class="fhbox">-</span>
            </el-form-item>
          </el-col>
          <el-col :span='5' class='iptcol'>
            <el-form-item>
              <el-date-picker
                v-model="queryform.endTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="pagelist">搜索</button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center" min-width="10%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center" min-width="25%" prop="event" label="事件" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center" min-width="20%" prop="loss" label="人员伤亡、设备损坏或其他损失情况" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="timestamp" label="时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="place" label="地点" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="16%"  prop="involved" label="涉及人员" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="reporter" label="报告人" show-overflow-tooltip></el-table-column>
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
    <div class="cardright">
        <div style="text-align:center;" class='keep'>值班记录</div>
        <el-form :model="addform" ref="addform" size="mini" class='queryform' >
            <el-form-item  label="日期:">
              <el-date-picker
                v-model="addform.timestamp"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="地点:">
                <el-input class='inputwidth' v-model="addform.place"></el-input>
            </el-form-item>
            <el-form-item label="涉及人员:" class='casualties'>
              <el-select multiple v-model="addform.involved">
                <el-option v-for="item in person" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="人员伤亡、设备损坏或其他损失情况:" class='casualties'>
              <el-input  type="textarea"  v-model="addform.loss"></el-input>
            </el-form-item>
            <el-form-item label="事件详情:" class='details'>
              <el-input  type="textarea"  v-model="addform.event"></el-input>
            </el-form-item>
            <el-form-item label="报告人:">
              <el-select v-model="addform.reporter">
                <el-option v-for="item in person" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <button class='search' @click.prevent @click="addlist">提交</button>
            </el-form-item>
      </el-form>
    </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        person: [],
        queryform: {
          beginTime:'',
          endTime:''
        },
        addform:{
          timestamp:'',
          place:'',
          involved:[],
          loss:'',
          event:'',
          reporter:''
        },
        tableData: [],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      this.selectlist()
      this.pagelist()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
        this.pagelist()
        console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.pagelist()
        console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.pagelist()
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      
      // 树状图接口
      treelist(){
        
      },
      // 下拉框数据
      selectlist(){
        this.$instance({
          url:'Wyaccident.do/dropDownBox',
          method:'get'
        }).then(res=>{
          this.person=res.data.person
        })
      },
      // 查询事故记录
      pagelist(){
        this.$instance({
          url:'Wyaccident.do/getAccident',
          method:'post',
          data:{
            "pageNum":this.pageIndex,
            "pageSize":this.pageSize,
            "beginTime":this.queryform.beginTime,
            "endTime":this.queryform.endTime,
          }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 添加事故记录
      addlist(){
        if(this.addform.timestamp==''||this.addform.place==''||this.addform.involved.length==0||this.addform.loss==''||this.addform.event==''||this.addform.reporter==''){
          this.$message('请补全新增信息')
          return
        }
        this.$instance({
          url:'Wyaccident.do/addAccident',
          method:'post',
          data:this.addform
        }).then(res=>{
          if(res.data=='新增成功'){
            this.$message.success('新增成功')
            this.pagelist()
          }else{
            this.$message.error('新增失败')
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
    width: 190px;
    border:1px solid #5D5D64;
  }
  .tablecard{
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    overflow-y: auto;
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
  /deep/.el-form-item{
      margin-bottom: 18px;
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
    height: 700px!important;
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
  .cardleft{
      width: 78%;
      display: inline-block;
  }
  /deep/.el-input--suffix{
    width: 190px;
    border:1px solid #5D5D64;
  }
  .cardright{
      width: 20%;
      margin: 5px 0 0 20px;
      height:100%;
      padding:15px 10px 10px 20px;
      box-sizing: border-box;
      background-color: #34353D;
      display: inline-block;
      vertical-align: top;
      color: white;
      font-size: 13px;
  }
  .casualties /deep/ .el-textarea__inner{
      background: black;
        border:1px solid #5D5D64;
      color: white;
      width: 96%;
      height: 90px;
  }
  .details /deep/ .el-textarea__inner{
      background: black;
        border:1px solid #5D5D64;
      color: white;
      width: 96%;
      height: 270px;
  }
  .keep{
      text-align: center;
      margin:0 0 20px 0 ;
  }
</style>

