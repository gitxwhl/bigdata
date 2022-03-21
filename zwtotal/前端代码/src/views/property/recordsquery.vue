<template>
  <div class='container'>
    <div class='meatitle'>历史维护记录查询</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='4' class='iptcol'>
            <el-form-item label="">
              <el-input  placeholder="请输入设备名称或设备编码"  v-model="queryform.name" @blur="pagelist"></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div class="box">
        <span class="box_text">基本信息</span>
        <div  class="pic">
          <div>
            <div class='pictopbox'>设备名称:{{information.name}}</div>
            <div class='pictopbox'>设备编码:{{information.code}}</div>
            <div class='pictopbox'>规格:{{information.model}}</div>
            <div class='pictopbox'>参数:{{information.parameter}}</div>
            <div class='pictopbox'>出厂日期:{{information.production}}</div>
          </div>
          <div>
            <div class='pictopboxbt'>设备状态:{{information.status}}</div>
            <div class='pictopboxbt'>当前位置:{{information.location}}</div>
            <div class='pictopboxbt'>生产厂家:{{information.manufacturer}}</div>
            <div class='pictopboxbt'>质保期:{{information.period}}</div>
            <div class='pictopboxbt'>使用年限:{{information.servicelife}}</div>
          </div>
        </div>
      </div>

      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center" min-width="5%" type="selection" :selectable="selectable" width="55"> </el-table-column>
        <el-table-column align="center" min-width="8%" prop="workno" label="工单编号" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center" min-width="15%" prop="policyname" label="策略名称" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  prop="policy" label="策略分类" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="personnel" label="派单人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  prop="workstate" label="工单状态" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="receiving" label="接单人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="12%"  prop="" label="接单成员" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="operationrecord" label="作业记录 " show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="defectidentification" label="缺陷识别" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  prop="completiontime" label="完成时间" show-overflow-tooltip></el-table-column>

      </el-table>
      <div class='pagbox'>
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
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        id:'',
        information:{
          name:'',
          code:'',
          model:'',
          parameter:'',
          production:'',
          status:'',
          location:'',
          manufacturer:'',
          period:'',
          servicelife:''
        },
        restaurantCode:'',//树状图id
        settlementamount:[],//结算金额
        applicabletime:[],//适用时间
        equipment:[],//关联设备
        restaurant:[],//运维餐厅
        queryform:{
          name:''
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
      // this.treelist()
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
        this.restaurantCode=data.id
            this.querylist()
        console.log(data)
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      selectable(row,index){
        return 0
      },
      // 树状图接口
      treelist(){
        this.$axios({
          url:'StOperationrestaurant.do/getStOperationLists',
          method:'get'
        }).then(res=>{
          console.log(res)
          this.data=res.data
        })
      },

      // 基本信息
      pagelist(){
        if(this.queryform.name==''){
          this.$message('请输入设备名称或设备编码')
          return
        }
        this.$instance({
          url:'StEquipment.do/WySpacemanagementList',
          method:'post',
          data:{
            "param":{
              "name":this.queryform.name
            }
          }
        }).then(res=>{
          if(res.data.length==0){
            this.$message.error('名称或编码不正确')
          }else{
            this.information=res.data[0]
            this.id=res.data[0].id
            this.querylist()
          }
        })
      },
      // 列表
      querylist(){
        this.$instance({
          url:'StEquipment.do/WySpacemanagementListDis',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "param":{
              "id":this.id
            }
          }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
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
  .queryform{
    width:100%;
    margin-top:0px;
    margin-bottom:0px;
  }
  .iptrow{
    width:100%;
    height:56px;
    margin-bottom:0px;
    padding: 14px 0px 0px 0;
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
  .el-row /deep/.el-input__inner{
    width: 240px;
    padding:0 30px 0 15px;
  }
  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px ;
    color: rgb(255,255,255);
  }
    /deep/.el-table{
    background-color:transparent;
    height: 600px!important;
  }
   /deep/.el-table__body-wrapper{
    height: 560px!important;
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

 
  /deep/.el-input__suffix{
    right: 5px;
  }
  .pagbox{
    text-align: right;
    margin-top: 10px;
    height:'15%'
  }
  .box{
    margin-top: 30px;
    margin-bottom: 10px;
    position: relative;
  }
  .box_text{
    position: absolute;
    top: -10px;
    left:15px;
    color: white;
    font-size: 13px;
  }
  .pic{
    width:1542px;
    background: url("../../assets/u4594.svg");
    background-size: 100% 100%;

  }
  .pictopbox{
    color: white;
    display: inline-block;
    font-size: 12px;
    margin: 20px 100px 0 30px;
  }
  .pictopboxbt{
    color: white;
    display: inline-block;
    font-size: 12px;
    margin: 20px 100px 20px 30px;
  }
</style>

