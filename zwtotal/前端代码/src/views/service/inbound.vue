<template>
    <div class='container'>
        <div class='foodtitle'>入库管理</div>
    <el-card class='genrecard'>
        <div class='genrehead'>部门</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
        <!-- 处理中 -->
        <div v-show="listdata" class='Initiateapplication'>
<!--            <div  class="marginbtm">待处理:XX条</div>-->
                        <div class='time'>
                            <span>待入库:{{ruku}}</span>
<!--                            <span>待上架:</span>-->
                        </div>
         <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" >
        <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
        <el-table-column align="center" min-width="16%" prop="title" label="采购事项" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center" min-width="8%" prop="begin_person_name" label="申请人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begin_person_phone" label="联系方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begin_dep_name" label="需求部门" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="plan_time" label="计划采购时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begin_date" label="提交计划时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="8%"  prop="end_date" label="审批计划时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="end_person_name" label="审批人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="apply_no" label="采购单号" show-overflow-tooltip></el-table-column>
<!--        <el-table-column align="center" min-width="8%"  prop="remark" label="状态" show-overflow-tooltip></el-table-column>-->
        <el-table-column align="center" min-width="12%"  prop="goodname" label="物品名称" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  label="详情" show-overflow-tooltip>
          <template slot-scope="scope">

            <div @click='lookdsp(scope.row)'>
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
        <!-- 详情页-->
        <div v-show="detailshow" class='detailbox'>
        <div class='clearfix colbox'>
          <div style="float:left;">采购名称:{{procurement.title}}</div>
          <div style="float:left; margin-left: 30px">计划采购日期:{{procurement.plan_time}}</div>
          <div style="float:right;"><button class='search' @click.prevent @click="goreturn">返回</button></div>
        </div>
        <div class='clearfix colbox'>
          <div style="float:left;">申请人:{{procurement.begin_person_name}}</div>
          <div style="float:left;margin-left: 100px" >电话:{{procurement.begin_person_phone}}</div>
          <div style="float:left;margin-left: 100px">部门:{{procurement.begin_dep_name}}</div>
          <div style="float:left;margin-left: 100px">采购理由:{{procurement.remark}}</div>
        </div>
        <div>
          <div>物品明细:</div>
                        <div class='definite' >
                          <div style="display: flex">
                          <div class='article'>物品名称:{{procurement.goodname}}</div>
                          <div class='article'>品牌:{{procurement.brand}}</div>
                          <div class='article'>规格型号:{{procurement.specs}}</div>
                          <div class='article'>单位:{{procurement.unit}}</div>
                          <div class='article'>均价:{{procurement.unitprice}}</div>
                          </div>
                          <div style="display: flex">
                          <div class='article'>采购单号:{{procurement.apply_no}}</div>
                          <div class='article'>计划采购数量:{{procurement.real_amount}}</div>
                          <div class='article'>计划采购单价:{{procurement.real_unitprice}}</div>
                          <div class='article'>采购计划备注:{{procurement.location}}</div>

                          </div>
                          <div style="display: flex" v-for="(item,index) in procurement2" :key="index">
                            <div class='article'>到货单号:{{item.arrival_order_id}}</div>
                            <div class='article'>入库数量:{{item.real_amount}}</div>
                            <div class='article'>入库单价:{{item.real_unitprice}}</div>
                            <div class='article'>入库备注: {{item.location}} </div>

                          </div>


                        </div>
        </div>
        <div class='timeleft'>
          实际入库数量:{{procurement.rkCnt}} 采购中数量:{{procurement.cgzCnt}}
        </div>
        <div>
          <div>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
              <el-row class="iptrow">
                <el-col :span='5' class='iptcol'>
                  <el-form-item label="入库到货单号:">
                    <el-input style="width: 150px" v-model="queryform.code"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span='17' class='iptcol'>
                  <el-form-item  label="本次入库数量:">
                    <el-input style="width: 150px" v-model="queryform.num"></el-input>
                  </el-form-item>
                </el-col>
<!--                <el-col :span='12' class='iptcol'>-->
<!--                  <el-form-item  label="本次入库单价:" >-->
<!--                    <el-input style="width: 150px" v-model="queryform.price"></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
                <el-col :span='2'  class='iptcol'>
                  <el-form-item>
                    <button class='search' @click.prevent  @click="storage(procurement)">入库</button>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span='12' class='iptcol aa'>
                  <el-form-item  label="备注:">
                    <el-input style="width: 700px;height: 100px" v-model="queryform.location"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div class='timeleft'>
            已上架数量{{procurement.ysjCnt}}  个
<!--            采购中数量：{{procurement.cgzCnt}}个 -->
            待上架{{procurement.dsjCnt}}个
          </div>
          <el-form :model="queryform2" ref="queryform2" size="mini" class='queryform' >
            <el-row class="iptrow">
              <el-col :span='5' class='iptcol'>
                <el-form-item label="预警数:">
                  <el-input style="width: 150px" v-model="queryform2.yujing"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span='17' class='iptcol'>
                <el-form-item  label="本次上架数量:">
                  <el-input style="width: 150px" v-model="queryform2.sjnum"></el-input>
                </el-form-item>
              </el-col>
<!--              <el-col :span='12' class='iptcol'>-->
<!--                <el-form-item  label="本次入库单价:" >-->
<!--                  <el-input style="width: 150px" v-model="queryform.price"></el-input>-->
<!--                </el-form-item>-->
<!--              </el-col>-->
              <el-col :span='2'  class='iptcol'>
                <el-form-item>
                  <button class='search' @click.prevent @click="putaway(procurement)">上架</button>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span='12' class='iptcol aa'>
                <el-form-item  label="备注:">
                  <el-input style="width: 700px;height: 100px" v-model="queryform2.beizhu2"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
<!--        <div  class='btnbox'>-->
<!--          <button class='search' @click.prevent >出库</button>-->
<!--        </div>-->
      </div>
    </el-card>

    </div>
</template>

<script>
export default {
  data () {
    return {
      ruku:'',
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
      detailshow:false,
      listdata:true,
      procurement:[],
      procurement2:[],
      queryform:{
             code:'',
        num:'',
        price:'',
        location:''
      },
      queryform2:{
        yujing:'',
        sjnum:'',
        beizhu2:''
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
        this.pageSize = val
        this.pagelist()
        // console.log(`每页 ${val} 条`)
      },
    handleCurrentChange (val) {
        this.pageIndex=val
        this.pagelist()
        // console.log(`当前页: ${val}`)
      },

    handleNodeClick (data) {
      console.log(data)
    },
    lookdsp(row){
      // console.log(row)
      this.detailshow=true
      this.listdata=false
      this.procurement=row
      this.procurement2=row.rc

      // this.queryform=row.rc
      // console.log( this.procurement2)
    },
    //返回
    goreturn(){
      this.detailshow=false
      this.listdata=true
},
    // 查询入库管理
    pagelist(){
      this.$office({
        url:'Tstock.do/getDrkList',
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
        this.ruku=res.data.totalRecord
      })
    },
    //入库
    storage(row) {
      console.log(row)
      if (this.queryform.num > this.procurement.cgzCnt) {
        this.$message.warning('入库数量不能超过采购中的数量')
        return
      }
      if (this.queryform.num=='' || this.queryform.code==''||this.queryform.location==''){
        this.$message.warning('请补全信息')
        return
      } else {
        // console.log(row)
        this.$office({
          url: 'Tstock.do/storeManagerInStore',
          method: 'post',
          data: {
            "apply_no": row.apply_no,			//采购单号，列表接口中获取
            "operate_person_id": "E0000000000000",		//操作人ID，暂时写死
            "operate_person_name": "管理员",			//操作人，暂时写死
            "remark": this.queryform.location,				//备注
            "goodid": row.goodid,				//物品id，列表接口中获取
            "real_unitprice": row.real_unitprice,						//本次入库单价
            "real_amount": this.queryform.num,					//本次入库数量
            "arrival_order_id": this.queryform.code,				//到货单号
            "cgzCnt": row.cgzCnt,					///采购中数量，从列表接口获取


          }
        }).then(res => {
          if (res.data == '入库成功') {
            this.$message.success('入库成功')

          } else {
            this.$message.error('入库失败')

          }
        })
        this.detailshow=false
        this.listdata=true
        this.pagelist()
        this.queryform.num=''
        this.queryform.location=''
        this.queryform.code=''
      }

    },
    //上架
    putaway(row) {
      if (this.queryform2.sjnum > this.procurement.dsjCnt) {
        this.$message.warning('上架数不能超过待上架数量')
        return
      } if (this.queryform2.yujing=='' || this.queryform2.sjnum==''||this.queryform2.beizhu2==''){
        this.$message.warning('请补全信息')
        return
      }else {
        // console.log(row)
        this.$office({
          url: 'Tstock.do/upToStock',
          method: 'post',
          data: {
            "apply_no": row.apply_no,			//采购单号，列表接口中获取
            "operate_person_id": "E0000000000000",		//操作人ID，暂时写死
            "operate_person_name": "管理员",			//操作人，暂时写死
            "goodid": row.goodid,				//物品id，列表接口中获取
            "real_unitprice": row.real_unitprice,						//本次入库单价
           "remark": this.queryform2.beizhu2,				//备注
           "dsjCnt":row.dsjCnt,					//待上架数量，从列表接口中获取
          "real_amount":  this.queryform2.sjnum,			//本次上架数量
          "expectcount": this.queryform2.yujing,			//预警值
          }
        }).then(res => {
          if (res.data == '上架成功') {
            this.$message.success('入库成功')

          } else {
            this.$message.error('入库失败')

          }
        })
        this.detailshow=false
        this.listdata=true
        this.pagelist()
        this.queryform2.yujing=''
        this.queryform2.sjnum=''
        this.queryform2.beizhu2=''
      }

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
    /*height: 860px;*/
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
.aa/deep/.el-input__inner{
  color:rgb(255,255,255) ;
  font-size: 10px;
  vertical-align: middle;
  background-color:black;
  border:1px solid rgb(52,57,82);
  box-sizing: border-box;
  border-radius:0;
  margin-top:0px;
  height:62px;
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
  width: 100%;
  height:100%;
  background: url(../../assets/u305.png)no-repeat;
  background-size: 100% 100%;
  margin:10px 40px 10px 0;
  /*display: flex;*/
  vertical-align: top;
  padding: 10px 20px;
  box-sizing: border-box;
}
.article{
  width: 300px;
  margin:35px 50px 10px 20px;
}
.explainbox{
  margin: 20px 0;
}
.timeleft{
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
.btnbox{
  text-align: right;
  margin:40px 0 10px 0;
}
</style>
