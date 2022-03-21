<template>
    <div class='container'>
        <div class='meatitle'>报修审核</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
            <el-row class="iptrow">
                 <el-col :span='5' class='iptcol'>
                    <el-form-item  label="订单状态:">
                        <el-select  v-model="queryform.state">
                             <el-option v-for="item in adminState" :key="item.id" :label="item.text" :value="item.id">
                             </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="订单编号:">
                           <el-input class='name' v-model="queryform.num"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='6' class='iptcol'>
                    <el-form-item label="报修人员:">
                           <el-input class='name' v-model="queryform.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="提交时间:">
                        <el-date-picker
                            v-model="queryform.time"
                            value-format="yyyy-MM-dd"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker>
                    </el-form-item>
                </el-col>
               
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click='pagelist'>查询</button>
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
                <el-table-column align="center"  min-width="8%" prop="M_GH" label="订单编号" show-overflow-tooltip> </el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="M_BXR" label="报修人" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="M_LXDH" label="联系电话" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="M_BXDW" label="所属部门" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="M_WXDZ" label="维修地址" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="SBBH" label="设备编码" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="SBMC" label="设备名称" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="M_BXNR" label="报修内容" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="7%"  prop="M_BXSJ" label="提交时间" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="7%"  prop="M_ZT" label="订单状态" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="7%"  prop="dishId" label="详细信息" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span  @click="inforbtn(scope.row)">查看更多</span>
                    </template>
                </el-table-column>
        </el-table>



                     <!-- 未配单弹框 -->
                    <el-dialog class='dialog'  title="报修单" :visible.sync="dialognoVisible" @close='opposendOrders'>
                        <div class='dialogttext'>
                            <div class='inforbox'>基本信息</div>
                            <span>报修人:{{xinxi.M_BXR}}</span>
                            <span>联系电话:{{xinxi.M_LXDH}}
                            </span>
                            <span>所属部门:{{xinxi.M_BXDW}}</span>
                            <br>
                            <span>详细位置:{{xinxi.M_WXDZ}}
                            </span>
                        </div>

                        <div class='dialogttext'>
                            <div class='inforbox'>报修信息</div>
                            <span>报修设备:{{xinxi.SBMC}}</span><br>
                            <span>报修内容:{{xinxi.M_BXNR}}</span><br>
                        </div>

                      
                        <div class='dialogttext'>
                            <div class='inforbox'>派单信息</div>
                            <span>维修人员:
                              <el-input v-model="mWxr"></el-input>
                            </span>
                            <span>联系电话:
                              <el-input v-model="mWxlxfs"></el-input>
                            </span>
                            <span>
                            <button class='search' @click.prevent>通讯录添加</button>
                            </span>
                        </div>
                    <div slot="footer" class="dialog-footer">
                            <button class='search' @click.prevent @click='sendOrders'>派单</button>
                            <button class='search' @click.prevent @click='opposendOrders'>关闭</button>
                    </div>
                    </el-dialog>





                    <!-- 已配单弹框 -->
                    <el-dialog class='dialog'  title="报修单" :visible.sync="dialogyesVisible" @close='oppofinishWx'>
                        <div class='dialogttext'>
                            <div class='inforbox'>基本信息</div>
                            <span>报修人:{{xinxi.M_BXR}}</span>
                            <span>联系电话:{{xinxi.M_LXDH}}
                            </span>
                            <span>所属部门:{{xinxi.M_BXDW}}</span>
                            <br>
                            <span>详细位置:{{xinxi.M_WXDZ}}
                            </span>
                        </div>

                        <div class='dialogttext'>
                            <div class='inforbox'>报修信息</div>
                            <span>报修时间:{{xinxi.M_BXSJ}}</span><br>
                            <span>报修设备:{{xinxi.SBMC}}</span><br>
                            <span>报修内容:{{xinxi.M_BXNR}}</span><br>
                        </div>

                      
                        <div class='dialogttext'>
                            <div class='inforbox'>派单信息</div>
                            <span>维修人员:{{xinxi.M_WXR}}
                            </span>
                            <span>联系电话:{{xinxi.M_WXLXFS}}
                            </span>
                           <span>派单时间:{{xinxi.M_WXSJ}}
                            </span>
                        </div>
                    <div slot="footer" class="dialog-footer">
                            <button class='search' @click.prevent @click='finishWx'>完成维修</button>
                            <button class='search' @click.prevent @click='oppofinishWx'>关闭</button>
                    </div>
                    </el-dialog>





                    <!-- 已处理弹框 -->
                    <el-dialog class='dialog'  title="报修单" :visible.sync="dialogchuliVisible">
                        <div class='dialogttext'>
                            <div class='inforbox'>基本信息</div>
                            <span>报修人:{{xinxi.M_BXR}}</span>
                            <span>联系电话:{{xinxi.M_LXDH}}
                            </span>
                            <span>所属部门:{{xinxi.M_BXDW}}</span>
                            <br>
                            <span>详细位置:{{xinxi.M_WXDZ}}
                            </span>
                        </div>

                        <div class='dialogttext'>
                            <div class='inforbox'>报修信息</div>
                            <span>报修时间:{{xinxi.M_BXSJ}}</span><br>
                            <span>报修设备:{{xinxi.SBMC}}</span><br>
                            <span>报修内容:{{xinxi.M_BXNR}}</span><br>
                        </div>

                      
                        <div class='dialogttext'>
                            <div class='inforbox'>派单信息</div>
                            <span>维修人员:{{xinxi.M_WXR}}
                            </span>
                            <span>联系电话:{{xinxi.M_WXLXFS}}
                            </span>
                           <span>派单时间:{{xinxi.M_WXSJ}}
                            </span>
                            <span>完成时间:{{xinxi.M_WCSJ}}
                            </span>
                        </div>
                        <div class='dialogttext'>
                            <div class='inforbox'>维修评价</div>
                            <span>已维修</span>
                            <span><el-rate disabled v-model="xinxi.M_PJXJ"></el-rate></span><br>
                            <span>{{xinxi.M_PJNR}}</span>

                        </div>
                    <div slot="footer" class="dialog-footer">
                            <button class='search' @click.prevent @click='dialogchuliVisible=false'>关闭</button>
                    </div>
                    </el-dialog>




                    
      <div style="text-align: right; margin-top: 10px;height:'15%'">
        <el-pagination
        style="height:100px"
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
      dialognoVisible:false,
      dialogyesVisible:false,
      dialogchuliVisible:false,
      value:'',
      xinxi:{},
      mWxr:'',
      mWxlxfs:'',
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      adminState:[],
      order:{},
      restaurantCode:'',//树状图id
      queryform: {
        state: '',
        num: '',
        name:'',
        time:'',
      },
      mealType:[],
      deduction:[],
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
      console.log(data)
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
       url:'Ufrepair.do/dropDownBox',
       method:'get',
     }).then(res=>{
       this.adminState=res.data.adminState
     })
    },
    // 报修审核列表
    pagelist(){
      this.$instance({
        url:'Ufrepair.do/management',
        method:'post',
        data:{
            "pageNum":this.pageIndex,
            "pageSize":this.pageSize,
            "state":this.queryform.state ,
            "num":this.queryform.num,
            "name":this.queryform.name,
            "time":this.queryform.time,
          }
      }).then(res=>{
        this.tableData=res.data.list
        this.total=res.data.totalRecord
      })
    },
    // 详细信息按钮
    inforbtn(row){
      if(row.M_ZT=='未派单'){
        this.dialognoVisible=true
        this.xinxi=row
      }else if(row.M_ZT=='已派单'){
        this.dialogyesVisible=true
        this.xinxi=row
      }else if(row.M_ZT=='已处理'){
        this.dialogchuliVisible=true
        this.xinxi=row
      }
    },
    // 管理员派单
    sendOrders(){
      if(this.mWxr==''||this.mWxlxfs==''){
        this.$message('请补全派单信息')
        return
      }
      this.$instance({
        url:'Ufrepair.do/sendOrders',
        method:'post',
        data:{
          "mGh":this.xinxi.M_GH,
          "mWxr":this.mWxr,
          "mWxlxfs":this.mWxlxfs
        }
      }).then(res=>{
        if(res.data=='派单成功'){
          this.$message.success('派单成功')
          this.pagelist()
        }else{
          this.$message.error('派单失败')
        }
        this.dialognoVisible=false
        this.mWxr=''
        this.mWxlxfs=''
      })
    },
    // 关闭派单
    opposendOrders(){
      this.dialognoVisible=false
        this.mWxr=''
        this.mWxlxfs=''
    },
    // 完成维修
    finishWx(){
      this.$instance({
        url:'Ufrepair.do/finishWx',
        method:'post',
        data:{
          "mGh":this.xinxi.M_GH,
          "mWxsj":this.xinxi.M_WXSJ,
        }
      }).then(res=>{
        if(res.data=='维修完成'){
          this.$message.success('维修完成')
          this.pagelist()
        }else{
          this.$message.error('维修失败')
        }
        this.dialogyesVisible=false
      })
    },
    // 维修关闭
    oppofinishWx(){
        this.dialogyesVisible=false
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
    overflow: auto;
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
   padding: 14px 0px 0px 10px;
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
  padding-right:12px;
  color: rgb(255,255,255);
}
.search{
    color:rgb(255,255,255);
    font-size: 10px;
    width: 90px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.search,.import:hover{
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
/deep/.el-dialog{
  background-color: rgba(0,0,0,.8);
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
  margin-bottom: 20px;
  margin-top: 20px;
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
.el-input{
  width: 140px;
}
/deep/.el-input__icon{
  width: 25px;
  line-height: 28px;
}
/deep/.el-date-editor{
    width: 160px;
}
/deep/.el-date-editor /deep/.el-input__inner{
    padding: 0 30px;
}
.el-select /deep/.el-input{
    width: 200px;
}
.el-select /deep/.el-input__inner{
    padding: 0 30px 0 15px;
}
.dialogttext{
     padding:20px 0 0 20px;
    box-sizing: border-box;
    margin: 0 0 30px 0;
    font-size: 12px;
    border: 1px dashed #797979;
    position: relative;
}
.dialogttext span{
     display: inline-block;
    font-size: 12px;
    margin: 0 50px 15px 0;
}
.inforbox{
  position: absolute;
  top:-8px;
  left:10;
}
</style>
