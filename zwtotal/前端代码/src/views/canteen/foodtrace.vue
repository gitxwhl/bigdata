<template>
    <div class='container'>
        <div class='managetitle'>菜品溯源</div>
        <el-card class='managecard'>
            <el-form :model="queryform" ref="queryform"  size="mini" class='queryform' >
                <el-row class="iptrow" :gutter="0">
                  <el-col :span='4' >
                    <el-form-item label="供应餐厅:">
                      <el-input class='name'  v-model="queryform.Supplyrestaurant"></el-input>
                    </el-form-item>
                  </el-col>
                    <el-col :span='4' >
                        <el-form-item label="菜品名称:">
                           <el-input class='name'  v-model="queryform.name"></el-input>
                        </el-form-item>
                    </el-col>
                  <el-col :span='4'>
                    <el-form-item label="供应时间段:">
                      <el-input class='name' v-model="queryform.supplytime"></el-input>
                    </el-form-item>
                  </el-col>
                    <el-col :span='4'>
                        <el-form-item label="用餐人员:">
                        <el-input class='name' v-model="queryform.person"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='4'>
                        <el-form-item>
                            <button class='search' @click.prevent @click="pagelist">查询</button>
                           <button style="margin-left: 10px" class='search' @click.prevent @click="reset">重置</button>
                          <button style="margin-left: 10px" class='search' @click.prevent >导出</button>
                        </el-form-item>
                    </el-col>

                </el-row>
            </el-form>
            <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             height="730px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="10%" prop="dishName" label="菜品名称"> </el-table-column>
                <el-table-column align="center"  min-width="15%" prop="category" label="供应餐厅"></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="supplyPeriod" label="供应时间段" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="diningStaff" label="用餐人员" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="40%"  prop="ingredient" label="食材" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="20%" label="食材溯源" >
                    <template slot-scope="scope">
                            <button class='detail' @click='lookdff(scope.row)' @click.prevent>食材查看详情</button>
                            <el-dialog class="detaildialog"  title="食材溯源" :visible.sync="dialogdetailVisible">
                              <el-table stripe ref="multipleTable" :data="tableData2" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                                        height="730px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
<!--                                <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>-->
                                <el-table-column align="center"  min-width="10%" prop="dishName" label="食材名称"> </el-table-column>
                                <el-table-column align="center"  min-width="15%" prop="category" label="供应商"></el-table-column>
                                <el-table-column align="center"  min-width="10%"  prop="supplyPeriod" label="入库时间" show-overflow-tooltip></el-table-column>
                                <el-table-column align="center"  min-width="10%"  prop="diningStaff" label="入库经办人" show-overflow-tooltip></el-table-column>

                              </el-table>
                        </el-dialog>
                    </template>
                </el-table-column>
            </el-table>
                <el-pagination
                        style="height:100px;float:right;"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="pageIndex"
                        :page-sizes="pageSizes"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
        </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      dialogdetailVisible: false,
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      tableData: [],
      tableData2: [],
      queryform: {
        name: '',
        person: '',
        Supplyrestaurant:'',
        supplytime:''
      }

    }
  },
  computed: {

  },
  created () {
    this.pagelist()
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize=val
      this.pagelist()
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.pageIndex=val
      this.pagelist()
      console.log(`当前页: ${val}`)
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 菜品查询
    pagelist(){
      this.$axios({
        url:'StFoodtraceability.do/getList',
        method:'post',
        data:{
        "pageNum": this.pageIndex,
        "pageSize":this.pageSize,
        "dishName":this.queryform.name,
        "personName":this.queryform.person
         }
      }).then(res=>{
        this.tableData=res.data.data.list
        this.total=res.data.data.totalRecord
      })
    },
    //重置
    reset(){
      this.queryform={
          name: '',
          person: '',
          Supplyrestaurant:'',
          supplytime:''
      }
    },
    lookdff(row){
   this.dialogdetailVisible = true
      // console.log(row)
    }
  }
}
</script>

<style lang="less" scoped>
.container{
   padding:10px 10px 5px 10px;
   box-sizing: border-box;
   color: rgb(255,255,255);
   font-size: 10px;
   height: 100%;
}
.managetitle{
    box-sizing: border-box;
    padding-top: 5px;
    font-size: 14px;
    padding-bottom:5px ;
    border-bottom:1px solid rgb(37,81,108);
}
.managecard{
    background-color:rgba(1,5,22,.4);
    box-sizing: border-box;
    margin-right: 10px;
    border-radius: 0;
    border:none;
    width: 100%;
    height: 860px;
}
.iptrow{
    width:100%;
    height:50px;
}
.queryform{
    color: rgb(255,255,255);
    font-size: 12px;
    width:100%;
    padding-left:50px;
    margin-top:16px;
}
 /deep/.el-input__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    vertical-align: middle;
    background-color:rgb(0,2,9);
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:0px;
    height:32px;
    padding:0 5px;
}
.search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    padding: 0;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.detail{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 90px;
    padding: 0;
    height:24px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color:rgb(64,158,255);
}
.search,.detail:hover{
    cursor: pointer;
}
/deep/.el-form-item{
  margin-bottom:18px;
}
/deep/.el-form-item__label{
    font-size: 10px;
    line-height: 28px;
    padding-right: 12px;
    color:rgb(255,255,255) ;
}
/deep/.el-card__body{
  overflow-y: auto;
  overflow-x: hidden;;
  height:100%;
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
/deep/.number,/deep/.btn-prev,/deep/.btn-next{
  background: transparent;
  color: white;
}
/deep/.el-pagination button:disabled{
  background: transparent;
}
/deep/.el-pagination{
  background-color: transparent;
}
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  width: 880px;
  height: 60%;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:rgba(0,0,0,.4);
  border-bottom:1px solid rgba(255,255,255,.5);
  margin-bottom:20px;
}
.editdialog .point{
    width: 240px;
    height:100px;
    line-height:30px;
    position: absolute;
    top:100px;
    right:40px;
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:0 20px 0 0;
  margin-bottom: 80px;
  margin-top: 40px;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;
  background:rgba(0,0,0,.4);
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
.detailbody{
    padding-left:40px;
}
.detailbody div{
  font-size: 14px;
  margin-bottom: 30px;
}
button:hover{
      background:url(../../assets/zy2.png) no-repeat;
      background-size:100% 100%;
}
.name{
  width: 220px;
}
</style>
