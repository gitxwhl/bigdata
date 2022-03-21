<template>
  <div class='container'>
    <div class='meatitle'>异地就餐申请</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
            <el-col :span='3' class='iptcol'>
            <el-form-item label="状态:">
              <el-select class="inputwidth" v-model="queryform.mealTypevalue">
                <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
           <el-col :span='3' class='iptcol'>
            <el-form-item label="餐厅:">
              <el-select class="inputwidth" v-model="queryform.mealTypevalue">
                <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span='4' class='iptcol'>
            <el-form-item label="申请人:">
              <el-input class="inputwidth"  v-model="queryform.jiliang"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='5' class='iptcol'>
            <el-form-item label="开始时间:">
              <el-date-picker
                v-model="queryform.startTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker><span class="fhbox">-</span>
            </el-form-item>
          </el-col>

          <el-col :span='5' class='iptcol'>
            <el-form-item label="结束时间:">
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
              <button class='search' @click.prevent @click="querylist">查询</button>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="dialogaddVisible=true">添加</button>
            </el-form-item>
          </el-col>
            <el-dialog class="adddialog"  title="预算设置" :visible.sync="dialogaddVisible">
                            <el-form :model="addform" ref="addform" class="addcategory">
                                <el-row class='addrow'>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item label="申请人:" prop="equipmentName">
                                            <el-input class="inputwidth2"  v-model="queryform.jiliang"></el-input>
                                            </el-form-item>
                                    </el-col>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item  label="联系方式:" prop="equipmentCode">
                                        <el-input  class='inputwidth2'  v-model="addform.maxmoney"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row class='addrow'>
                                    <el-col :span='12' class='iptcol'>
                                         <el-form-item label="申请公司:">
                                        <el-select class="inputwidth2" v-model="queryform.mealTypevalue">
                                            <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                                            </el-option>
                                        </el-select>
                                        </el-form-item>
                                    </el-col>
                                      <el-col :span='12' class='iptcol'>
                                    <el-form-item label="申请部门:">
                                    <el-select class="inputwidth2" v-model="queryform.mealTypevalue">
                                        <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                                        </el-option>
                                    </el-select>
                                    </el-form-item>
                                      </el-col>
                                </el-row>
                                <el-row class='addrow'>
                                    <el-col :span='12' class='iptcol'>
                                         <el-form-item label="目的公司:">
                                        <el-select class="inputwidth2" v-model="queryform.mealTypevalue">
                                            <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                                            </el-option>
                                        </el-select>
                                        </el-form-item>
                                    </el-col>
                                      <el-col :span='12' class='iptcol'>
                                    <el-form-item label="餐厅:">
                                    <el-select class="inputwidth2" v-model="queryform.mealTypevalue">
                                        <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                                        </el-option>
                                    </el-select>
                                    </el-form-item>
                                      </el-col>
                                </el-row>
                                <el-row class='addrow'>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item label="审批人:" prop="equipmentName">
                                            <el-input class="inputwidth2"  v-model="queryform.jiliang"></el-input>
                                            </el-form-item>
                                    </el-col>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item  label="联系方式:" prop="equipmentCode">
                                        <el-input  class='inputwidth2'  v-model="addform.maxmoney"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item label="开始时间:">
                                        <el-date-picker
                                            v-model="queryform.startTime"
                                            format="yyyy-MM-dd"
                                            value-format="yyyy-MM-dd"
                                            type="date"
                                            placeholder="请选择日期">
                                        </el-date-picker><span class="fhbox">-</span>
                                        </el-form-item>
                                    </el-col>

                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item label="结束时间:">
                                        <el-date-picker
                                            v-model="queryform.endTime"
                                            format="yyyy-MM-dd"
                                            value-format="yyyy-MM-dd"
                                            type="date"
                                            placeholder="请选择日期">
                                        </el-date-picker>
                                        </el-form-item>
                                    </el-col>
                                </el-row>

                                <el-form-item label="备注:" prop="remarks">
                                  <el-input type="textarea" v-model="addform.remark"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="search"  type="primary" @click="clear">确认</el-button>
                              </div>
                        </el-dialog>


        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="10%" prop="ingredientsName" label="序号"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="requirement" label="申请人"></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="demandUnit" label="申请时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="inventory" label="餐厅" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="inventoryUnit" label="申请人公司" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisedDemand" label="接待人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="开始时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="结束时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="revisionUnit" label="备注" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="25%"  prop="revisionUnit" label="状态" show-overflow-tooltip>
            <template>
                <div>
                    <span>通过 </span>
                    <span @click='dialogbohuiVisible=true'> 驳回</span>
                 </div>
            </template>
        </el-table-column>
      </el-table>
      <el-dialog class="adddialog"  title="驳回" :visible.sync="dialogbohuiVisible">
                            <el-form :model="addform" ref="addform" class="addcategory">
                                <el-form-item label="驳回原因:" prop="remarks">
                                  <el-input type="textarea" v-model="addform.remark"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="search"  type="primary" @click="clear">确认</el-button>
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
        dialogaddVisible:false,
        dialogbohuiVisible:false,
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
        addform:{

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
      this.selectlist()
      this.pagelist()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
        console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
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
      // 下拉框数据
      selectlist(){
        this.$axios({
          url:'StMenuPlanning.do/getRelevance',
          method:'get'
        }).then(res=>{
          this.mealType=res.data
          console.log(res.data)
        })
      },
      //点击已审批页面
      pagelist(){

        this.$axios({
          url:'TdeptBudget.do/getSituation',
          method:'post',
          data:{
            "pageNum": this.pageIndex2,
            "pageSize":this.pageSize2,

          }
        }).then(res=>{
          this.tableData=res.data.page.list
          this.total=res.data.page.totalRecord

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
    width: 140px;
  }
  .inputwidth2{
      width: 220px;
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
/deep/.el-dialog__body{
  font-size: 12px;
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
/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
    margin-top:-8px;
}
/deep/.el-textarea__inner{
    background-color: black;
    height: 100px;
    color: white;
}
</style>

