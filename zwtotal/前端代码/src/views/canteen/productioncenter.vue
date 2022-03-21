<template>
  <div class='container'>
    <div class='meatitle'>生产中心</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='5' class='iptcol'>
            <el-form-item label="生产日期:">
              <el-date-picker
                v-model="queryform.startTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker><span class="fhbox">-</span>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
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
          <el-col :span='12' class='iptcol'>
            <el-form-item label="选择餐别:">
              <el-select v-model="queryform.mealTypevalue">
                <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
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
        <el-table-column align="center"  min-width="15%" prop="ingredientsName" label="餐厅"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="requirement" label="生产日期"></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="demandUnit" label="餐别" show-overflow-tooltip>
          <template>
              <!-- <div  slot-scope="scope"> -->
              <div @click="dialogNutVisible=true">
                    <!-- {{scope.row.demandUnit}} -->
                    10
              </div>
          </template>
        </el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="inventory" label="主食数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="inventoryUnit" label="凉菜数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisedDemand" label="热菜数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="汤类数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisedDemand" label="水果数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="生产总量" show-overflow-tooltip></el-table-column>
      </el-table>


              <el-dialog class='dialog'  title="菜品信息" :visible.sync="dialogNutVisible">
                        <div class='dialogtop'>
                          <div>生产餐厅:</div>
                          <span class='spanbox'>生产日期:</span>
                          <span class='spanbox'>餐别类型:</span>
                          <span>生产总数:</span>
                        </div>
                        
                        <el-table  stripe ref="multipleTable" :data="tableDatatwo" :cell-style="{padding:'0',
                                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                                height="300px" size="small"  style="width: 100%;margin-top:10px;" @selection-change="handleSelectionChange">
                                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                                    <el-table-column align="center"  min-width="16%" prop="name" label="菜品名称"> </el-table-column>
                                    <el-table-column align="center"  min-width="10%" prop="dishescategory" label="菜品分类"></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="weight" label="菜品口味" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="energy" label="菜品类别" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="dishNum" label="生产数量" show-overflow-tooltip></el-table-column>
                          </el-table>
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
        queryform: {
          mealTypevalue:'',
          jiliang:'',
          startTime:'',
          endTime:''
        },
        tableData: [],
        tableDatatwo:[],
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
        this.List()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.list()
        // console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.list()
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
          console.log(res)
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
.dialog  /deep/.el-table{
    background-color:transparent;
    height: 320px!important;
  }
.dialog /deep/.el-table__body-wrapper{
    height: 280px!important;
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
  /deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  width: 500px;
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
  margin-bottom: 20px;
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
.dialogtop{
  padding: 0 0 0 20px;
  font-size: 12px;
  line-height: 2;
}
.spanbox{
  margin-right: 40px;
}
</style>

