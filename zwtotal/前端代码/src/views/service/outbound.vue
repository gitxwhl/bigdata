<template>
    <div class='container'>
        <div class='foodtitle'>出库管理</div>
    <el-card class='genrecard'>
        <div class='genrehead'>部门</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
        <div class='Initiateapplication' v-show="inforshow">
                        <div class='time'>
                            <span>待发放:{{total}}</span>
                        </div>
         <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" >
        <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
        <el-table-column align="center" min-width="8%" prop="typeid" label="类型" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center" min-width="10%" prop="beginpersonname" label="申请人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begindate" label="提交时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="demanddep" label="需求部门" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="receiptdate" label="期望交付时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="attr2" label="联系方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="30%"  prop="remark" label="申请原因" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  label="详情" show-overflow-tooltip>
          <template slot-scope="scope">
            <div @click='lookinfor(scope.row)'>
                查看详情
            </div>
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



      <div v-show="detailshow" class='detailbox'>
        <div class='clearfix colbox'>
          <div style="float:left;">申请人:{{inforrow.beginpersonname}}</div>
          <div style="float:right;"><button class='search' @click.prevent @click="oppodetail">返回</button></div>
        </div>
        <div class='clearfix colbox'>
          <div style="float:left;">所在部门:{{inforrow.demanddep}}</div>
          <div style="float:right;">申请编号:{{inforrow.orderid}}</div>
        </div>
         <div>
              <div>物品明细:</div>
              <div class='definite' v-for="(item,index) in inforrow.goods" :key="index">
                  <div class='article'>物品名称:{{item.goodname}}</div>
                  <div class='article'>物品数量:{{item.goodamount}}</div>
                  <div class='article'>物品规格:{{item.specs}}</div>
                  <div class='article'>存放地点:{{item.location}}</div>
              </div>
        </div>
      <div class='timeleft'>
          期望交付时间:{{inforrow.receiptdate}}
      </div>
      <div>
          <div>
            申请原因:{{inforrow.remark}}
          </div>
      </div>
      <div  class='btnbox'>
                <button class='search' @click.prevent @click='grantGoods'>出库</button>
          </div>
      </div>



    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
    inforshow:true,
    detailshow:false,
    inforrow:{},
    pageIndex: 1,
    pageSize:10,
    pageSizes:[ 10, 20 , 30, 50, 100],
    total:0,
    tableData:[],
    data: [],
    defaultProps: {
        children: 'children',
        label: 'label'
      },
    }
  },
  computed: {

  },
  created () {
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
      console.log(data)
    },
    // 查询出库列表
    pagelist(){
      this.$office({
        url:'Tstock.do/getCkList',
        method:'post',
        data:{
          "pageNum": this.pageIndex,
          "pageSize":this.pageSize,
          "depId": ""
        }
      }).then(res=>{
        // console.log(res)
        this.total=res.data.totalRecord
        this.tableData=res.data.list
      })
    },
    // 查看详情
    lookinfor(row){
      this.inforshow=false
      this.detailshow=true
      this.inforrow=row
    },
    // 返回按钮
    oppodetail(){
      this.inforshow=true
      this.detailshow=false
    },
    // 出库
    grantGoods(){
      this.$office({
        url:'TapplyOrder.do/grantGoods',
        method:'post',
        data:{
          "orderid":this.inforrow.orderid,
          "deal_user_id": "E0311010002",	//管理员id，先写死
          "deal_user_name": "陈虹"		//管理员名称，先写死
        }
      }).then(res=>{
        if(res.data=='发放成功'){
          this.$message.success('出库成功')
          this.inforshow=true
          this.detailshow=false
          this.pagelist()
        }else{
          this.$message.error('出库失败')
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
.foodtitle{
    margin-bottom:6px;
}
.genrecard{
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
.genrehead{
    height:44px;
    text-align: center;
    padding-top:6px;
    box-sizing: border-box;
    font-size: 16px;
    color:rgba(255,255,255);
    margin-bottom:26px ;
}
.tablecard{
    overflow-y: auto;
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:82%;
    height: 860px;
}
    /deep/.el-table{
    background-color:transparent;
    height: 680px!important;
  }
   /deep/.el-table__body-wrapper{
    height: 640px!important;
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

.queryform{
    width:100%;
    margin-top:20px;
    margin-bottom:0px;
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
/deep/.el-textarea__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    vertical-align: middle;
    background-color:black;
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:4px;
    height:140px;
}
.search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}

.search,.add:hover{
    cursor: pointer;
}
/deep/.el-dialog{
  background: transparent;
  border:1px solid rgba(255,255,255,.5);
  margin-top:300px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:rgba(0,0,0,.4);
  border-bottom:1px solid rgba(255,255,255,.5);
  margin-bottom:20px;
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:rgba(0,0,0,.4);
  padding-bottom:2px;
  padding:0 20px 0 0;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;

  background:rgba(0,0,0,.4);
}
/deep/.el-dialog__title{
  font-size: 12px;
  color:rgb(255,255,255);

}
/deep/.el-form-item__label{
  font-size: 12px;
  color: rgb(255,255,255);
}
.line:hover{
  cursor:auto;
}
/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
    margin-top:-8px;

}
/deep/.number,/deep/.btn-prev,/deep/.btn-next{
  background: transparent;
  color: white;
}
/deep/.el-pagination button:disabled{
  background: transparent;
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

.time{
    text-align: right;
    height:36px;
    color: white;
    line-height: 36px;
    font-size: 13px;
    padding-right:10px;
    background:url(../../assets/u441.png) no-repeat;
    background-size:100% 100%;
    margin-bottom: 10px;
}
.time span{
  margin:0 30px 0 0;
}

.Initiateapplication{
  margin:20px 0 0 0;
  padding:0 20px 0 20px;
  color: white;
  font-size: 12px;
}
.marginbtm{
  margin: 0 0 10px 0;
}
/deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px ;
    color: rgb(255,255,255);
  }
  .el-date-editor,.el-date-editor /deep/.el-input__inner{
  width: 180px;
}
.el-date-editor /deep/.el-input__inner{
  padding: 0 32px 0 25px;
}
/deep/.el-form-item{
      margin-bottom: 18px;
  }
/deep/.el-input__icon{
    line-height: 28px;
    width:25px;
  }
  .detailbox{
  width: 100%;
  font-size: 12px;
  color: white;
  padding: 30px 10px 10px 40px;
  box-sizing: border-box;
}
.colbox{
  margin:0 0 20px 0;
}
.definite{
  width: 250px;
  height: 120px;
  background: url(../../assets/u305.png)no-repeat;
  background-size: 100% 100%;
  margin:10px 40px 10px 0;
  display: inline-block;
  vertical-align: top;
  padding: 10px 20px;
  box-sizing: border-box;
}
.article{
  margin:0 0 10px 0;
}
.explainbox{
  margin: 20px 0;
}
.timeleft{
    text-align: left;
    height:36px;
    color: white;
    line-height: 36px;
    font-size: 13px;
    padding-right:10px;
    background:url(../../assets/u441.png) no-repeat;
    background-size:100% 100%;
    margin-bottom: 10px;
}
.btnbox{
  text-align: right;
  margin:40px 0 10px 0;
}
</style>
