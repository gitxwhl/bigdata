<template>
  <div class='container'>
    <div class='meatitle'>配送中心</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='3' class='iptcol'>
            <el-form-item label="预定菜品:">
              <el-input class="inputwidth"  v-model="queryform.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='3' class='iptcol'>
            <el-form-item label="订单编号:">
              <el-input class="inputwidth"  v-model="queryform.number"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='3' class='iptcol'>
            <el-form-item label="预定人员:">
              <el-input class="inputwidth"  v-model="queryform.personnel"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="用餐日期:">
              <el-date-picker
                v-model="queryform.Time"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker><span class="fhbox"></span>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
          <el-form-item label="餐别:">
            <el-select v-model="queryform.mealTypevalue" class='selectbox'>
              <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="扣费情况:">
              <el-select v-model="queryform.debit" class='selectbox'>
                <el-option v-for="item in Deduction" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="querylist">查询</button>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="ingredientsName" label="订单编号"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="requirement" label="预定人员"></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="demandUnit" label="联系电话" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="inventory" label="下单餐厅" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="inventoryUnit" label="下单时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisedDemand" label="预定日期" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="餐别/类别" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisedDemand" label="费用合计" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="送餐时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="inventory" label="扣费情况" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="inventoryUnit" label="配送方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisedDemand" label="配送人员" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="送餐员联系电话" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisedDemand" label="订单状态" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="订单详情" show-overflow-tooltip>
          <template slot-scope="scope">
                        <span  class="editicon"><img src="../../assets/u3963.svg" alt=""  @click="getorder(scope.row.ordernumber)"> 查看</span>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog class='dialog'  title="预定信息" :visible.sync="dialogNutVisible">
                        <div class='dialogttext'>
                            <span>预定单号:</span>
                            <span>下单时间:</span>
                            <span>下单餐厅:</span>
                            <span>预定人员:</span>
                            <span>联系电话:</span>
                            <span>扣费情况:</span>
                            <span>预定日期:</span>
                            <span>餐别类型:</span>
                            <span>取餐时间:</span>
                        </div>
                        <el-table  stripe ref="multipleTable" :data="tableDataname" :cell-style="{padding:'0',
                                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                                height="300px" size="small"  style="width: 100%;margin-top:10px;" @selection-change="handleSelectionChange">
                                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                                    <el-table-column align="center"  min-width="16%" prop="name" label="菜品名称"> </el-table-column>
                                    <el-table-column align="center"  min-width="10%" prop="dishescategory" label="分类"></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="weight" label="重量(g)/份" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="energy" label="能量/份" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="dishNum" label="数量" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="referenceprice" label="单价" show-overflow-tooltip></el-table-column>
                                </el-table>
                <div class='amount'>合计:￥</div>
                <div class='dialogttext' style="margin-top:20px;">
                    <span>配送人员:</span>
                    <span>送餐员联系方式:</span>
                    <span>配送方式:</span>
                    <span>配送地址:</span>
                </div>
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
        dialogNutVisible:false,
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        mealType: [],//餐别,
        Deduction:[
          {
            id: '1',
            text: '扣费失败'
          },{
            id: '2',
            text: '已扣费'
          },{
            id: '3',
            text: '待扣费'
          },
        ],//扣费情况
        queryform: {
          name:'',//预定菜品
          number:'',//订单编号
          personnel:'',//预定人员
          mealTypevalue:'',//餐别
          Time:'',   //用餐日期
          debit:'' //扣费情况
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
      this.treelist()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        // console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        // console.log(data)
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      getorder(){
        this.dialogNutVisible=true
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
    width: 100px;
  }
  .selectbox{
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
    padding: 0 32px;
  }
.inputwidth /deep/.el-input__inner{
    padding: 0 10px;
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
    width: 160px;
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
  .editicon img{
  width:22px;
  height:26px;
  vertical-align: middle;
}
.editicon img:hover{
  cursor: pointer;
}
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  width: 700px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
  margin-bottom:20px;
}
/deep/.el-dialog__title{
  line-height: 24px;
  font-size: 14px;
  color:rgb(255,255,255);
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:0 20px 0 10px;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;
  background:transparent;
}
/deep/.el-dialog__title{
  font-size: 14px;
  color:rgb(255,255,255);
}
/deep/.el-dialog__headerbtn{
    top:20px;
    right:20px;
    height: 18px;
    line-height: 18px;
}
/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
    margin-top:-8px;
}
.dialogttext span{
    display: inline-block;
    width: 220px;
    font-size: 12px;
    margin: 0 0 15px 0;
}
.dialog /deep/.el-table{
    background-color:transparent;
    height: 200px!important;
    margin-bottom: 20px;
}
.dialog /deep/.el-table__body-wrapper{
    height: 200px!important;
}
.amount{
    height: 40px;
    background: url(../../assets/1_u3579.png) no-repeat;
    background-size: 100% 100%;
    line-height: 40px;
    text-align: right;
    padding-right:30px ;
    font-size: 14px;
}
</style>

