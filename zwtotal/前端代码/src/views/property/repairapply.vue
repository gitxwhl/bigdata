<template>
    <div class='container'>
        <div class='meatitle'>报修申请</div>
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
                             <el-option v-for="item in state" :key="item.id" :label="item.text" :value="item.id">
                             </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span='4' class='iptcol'>
                    <el-form-item label="订单编号:">
                           <el-input  v-model="queryform.num"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='8' class='iptcol'>
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
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click='dialogNutVisible=true'>我要报修</button>
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
                <el-table-column align="center"  min-width="7%"  prop="M_PJZT" label="评价" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <!-- <span @click="dialogyesVisible=true">{{scope.row.M_PJZT}}</span> -->
                        <span @click="appraise(scope.row)">{{scope.row.M_PJZT}}</span>
                    </template>
                </el-table-column>
        </el-table>

                    <!-- 我要报修弹框 -->
                    <el-dialog class='dialog'  title="报修单" :visible.sync="dialogNutVisible" @close='oppoadd'>
                        <div class='dialogttext'>
                            <div class='inforbox'>基本信息</div>
                            <span>报修人:</span>
                            <span>联系电话:
                              <el-input v-model="repairform.mLxdh"></el-input>
                            </span>
                            <span>所属部门:</span>
                            <br>
                            <span>详细位置:
                              <el-input class='lengthinput' v-model="repairform.mWxdz"></el-input>
                            </span>
                        </div>

                        <div class='dialogttext'>
                            <div class='inforbox'>报修信息</div>
                            <span>报修设备:
                              <el-select  v-model="repairform.swid" @change='getEquipmentById'>
                                  <el-option v-for="item in equipment" :key="item.ID" :label="item.SBMC" :value="item.ID">
                                  </el-option>
                              </el-select>
                              <!-- <el-input v-model="queryform.orderNum"></el-input> -->
                            </span>
                            <br>
                            <span>设备编码:{{this.xinxi.SBBH}}</span>
                            <span>设备型号:{{this.xinxi.SBXH}}</span>
                            <span>设备大类:{{this.xinxi.SBDL}}</span>
                            <span>设备小类:{{this.xinxi.SBXL}}</span>
                            <span>安装位置:{{this.xinxi.WEIZ}}</span>
                            <br>

                            <span>报修内容:
                              <el-input type="textarea" maxlength="300" show-word-limit v-model="repairform.mBxnr"></el-input>
                            </span>
                        </div>


                    <div slot="footer" class="dialog-footer">
                                <button class='search' @click.prevent @click='addUfrepair'>提交</button>
                                <button class='search' @click.prevent @click='oppoadd'>关闭</button>
                              </div>
                    </el-dialog>

            
                    <!-- 未评论弹框 -->
                    <el-dialog class='dialog'  title="报修单" :visible.sync="dialognoVisible" @close='opporepairEvaluate'>
                        <div class='dialogttext'>
                            <div class='inforbox'>基本信息</div>
                            <span>报修人:{{repairsingle.M_BXR}}</span>
                            <span>联系电话:{{repairsingle.M_LXDH}}
                            </span>
                            <span>所属部门:{{repairsingle.M_BXDW}}</span>
                            <br>
                            <span>详细位置:{{repairsingle.M_WXDZ}}
                            </span>
                        </div>

                        <div class='dialogttext'>
                            <div class='inforbox'>报修信息</div>
                            <span>报修时间:{{repairsingle.M_BXSJ}}</span><br>
                            <span>报修设备:{{repairsingle.SBMC}}</span><br>
                            <span>报修内容:{{repairsingle.M_BXNR}}</span><br>
                        </div>

                      
                        <div class='dialogttext'>
                            <div class='inforbox'>派单信息</div>
                            <span>维修人员:{{repairsingle.M_WXR}}</span>
                            <span>处理时长:{{repairsingle.M_WXSC}}</span>
                            <span>派单时间:{{repairsingle.M_WXSJ}}</span>
                            <span>完成时间:{{repairsingle.M_WCSJ}}</span>
                        </div>

                        <div class='dialogttext'>
                            <div class='inforbox'>维修评价</div>
                            <!-- <span>是否已维修完成:<el-switch
                                  v-model="value"
                                  active-color="#13ce66"
                                  inactive-color="#ff4949">
                                </el-switch>
                                </span> -->
                            <span><el-rate v-model="value1"></el-rate></span><br>
                            <span>报修内容:
                              <el-input type="textarea" v-model="mPjnr"></el-input>
                            </span>

                        </div>
                    <div slot="footer" class="dialog-footer">
                                <button class='search' @click.prevent @click='repairEvaluate'>提交</button>
                                <button class='search' @click.prevent @click='opporepairEvaluate'>关闭</button>
                              </div>
                    </el-dialog>



                     <!-- 已评论弹框 -->
                    <el-dialog class='dialog'  title="报修单" :visible.sync="dialogyesVisible">
                        <div class='dialogttext'>
                            <div class='inforbox'>基本信息</div>
                            <span>报修人:{{repairsingle.M_BXR}}</span>
                            <span>联系电话:{{repairsingle.M_LXDH}}
                            </span>
                            <span>所属部门:{{repairsingle.M_BXDW}}</span>
                            <br>
                            <span>详细位置:{{repairsingle.M_WXDZ}}
                            </span>
                        </div>

                        <div class='dialogttext'>
                            <div class='inforbox'>报修信息</div>
                            <span>报修时间:{{repairsingle.M_BXSJ}}</span><br>
                            <span>报修设备:{{repairsingle.SBMC}}</span><br>
                            <span>报修内容:{{repairsingle.M_BXNR}}</span><br>
                        </div>

                      
                        <div class='dialogttext'>
                            <div class='inforbox'>派单信息</div>
                            <span>维修人员:{{repairsingle.M_WXR}}</span>
                            <span>处理时长:{{repairsingle.M_WXSC}}</span>
                            <span>派单时间:{{repairsingle.M_WXSJ}}</span>
                            <span>完成时间:{{repairsingle.M_WCSJ}}</span>
                        </div>

                        <div class='dialogttext'>
                            <div class='inforbox'>维修评价</div>
                            <span>已维修</span>
                            <span><el-rate disabled v-model="repairsingle.M_PJXJ"></el-rate></span><br>
                            <span>报修内容:{{repairsingle.M_PJNR}}</span>

                        </div>
                    <div slot="footer" class="dialog-footer">
                            <button class='search' @click.prevent @click='dialogyesVisible=false'>关闭</button>
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
      dialogNutVisible:false,
      dialognoVisible:false,
      dialogyesVisible:false,
      value:0,
      value1:'',
      mPjnr:'',
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      restaurantCode:'',//树状图id
      queryform: {
        num:'',
        state:'',
        time:''
      },
      repairform:{
        mLxdh:'',
        mWxdz:'',
        mBxnr:'',
        swid:''
      },
      equipment:[],
      state:[],
      xinxi:{
        SBBH:"",
        SBXH:"",
        SBDL:"",
        SBXL:"",
        WEIZ:""
        },
      repairsingle:{},
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
    // this.treelist()
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
       this.equipment=res.data.equipment
       this.state=res.data.state
     })
    },
    // 报修申请列表
    pagelist(){
        this.$instance({
          url:'Ufrepair.do/selectRecord',
          method:'post',
          data:{
            "pageNum":this.pageIndex,
            "pageSize":this.pageSize,
            "mBxrgh": "E0015472212",
            "num":this.queryform.num,
            "state":this.queryform.state,
            "time":this.queryform.time,
          }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
    // 通过id查询设备信息
    getEquipmentById(id){
      this.$instance({
        url:'Ufrepair.do/getEquipmentById',
        method:'post',
        data:{
          "id":id
        }
      }).then(res=>{
        this.xinxi=res.data
      })
    },
    // 我要保修按钮
    addUfrepair(){
      if(this.repairform.mLxdh==''||this.repairform.mWxdz==''||this.repairform.mBxnr==''||this.repairform.swid==''){
        this.$message('请补全表单信息')
        return
      }
      this.$instance({
        url:'Ufrepair.do/addUfrepair',
        method:'post',
        data:{
          "mBxr": "小花生",
          "mBxrgh": "E0015472212",
          "mBxdw": "LGD-jungle",
          "mLxdh":this.repairform.mLxdh,
          "mWxdz":this.repairform.mWxdz,
          "mBxnr":this.repairform.mBxnr,
          "swid":this.repairform.swid
        }
      }).then(res=>{
        if(res.data=='申请成功'){
          this.$message.success('申请成功')
          this.pagelist()
        }else{
          this.$message.error('申请失败')
        }
        this.dialogNutVisible=false
        this.repairform.mLxdh=''
        this.repairform.mWxdz=''
        this.repairform.mBxnr=''
        this.repairform.swid=''
      })
    },
    // 我要报修关闭按钮
    oppoadd(){
        this.dialogNutVisible=false
        this.repairform.mLxdh=''
        this.repairform.mWxdz=''
        this.repairform.mBxnr=''
        this.repairform.swid=''
    },
    // 评价按钮
    appraise(row){
      if(row.M_PJZT=='不可评论'){
        return
      }else if(row.M_PJZT=='未评论'){
        this.dialognoVisible=true
        this.repairsingle=row
      }else if(row.M_PJZT=='已评论'){
        this.dialogyesVisible=true
        this.repairsingle=row
      }
    },
    // 报修评价
    repairEvaluate(){
      if(this.value1==''||this.mPjnr==''){
        this.$message('请输入评价')
        return
      }
      this.$instance({
        url:'Ufrepair.do/repairEvaluate',
        method:'post',
        data:{
          "mGh":this.repairsingle.M_GH,
          "mPjxj":this.value1,
          "mPjnr":this.mPjnr
        }
      }).then(res=>{
        if(res.data=='评价成功'){
          this.$message.success('评价成功')
        }else{
          this.$message.error('评价失败')
        }
        this.dialognoVisible=false
        this.pagelist()
        this.value1=''
        this.mPjnr=''
      })
    },
    // 未报修关闭按钮
    opporepairEvaluate(){
        this.dialognoVisible=false
        this.value1=''
        this.mPjnr=''
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
  // background: transparent;
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
.inforbox{
  position: absolute;
  top:-8px;
  left:10;
}
.dialogttext span{
    display: inline-block;
    font-size: 12px;
    margin: 0 50px 15px 0;
}
.lengthinput{
  width: 520px;
}
</style>
