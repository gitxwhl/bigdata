<template>
    <div class='container'>
        <div class='foodtitle'>预算追加</div>
    <el-card class='genrecard'>
        <div class='genrehead'>部门</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <div v-show="applyforshow">
        <div class='imgbox'>
            <img class="line" src="../../assets/u500.svg" alt="">
            <span  class="apply"><img src="../../assets/u501.svg" class='waterdrop' alt="" @click='applychange'><span class="imgdescribe" >发起申请</span></span>
            <span  class="approve"><span class="imgdescribe" >处理中</span><img src="../../assets/u502.svg" alt="" class='waterdrop' style="transform: rotate(180deg)" @click="approvechange"></span>
            <span  class="neck"><img src="../../assets/u507.svg" alt="" class='waterdrop' @click="neckchange"><span class="imgdescribe">已审批</span></span>
        </div>

        <!-- 发起申请 -->
        <div v-if='applyshow' class='Initiateapplicationone'>
            <div class='marginbtm'>申请日期: {{nowDate}}</div>
            <div class='marginbtm'>申请人: </div>
            <div class='marginbtm'>联系方式: </div>
            <div class='marginbtm'>所属部门: </div>
            <div class='marginbtm'>申请年度: {{thisYear}}</div>
            <div class='marginbtm'>年度预算: {{depmoney}}</div>
            <div class='marginbtm'>已执行数: {{allmoney}}</div>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
              <el-row>
                <el-col :span='24' class='iptcol'>
                    <el-form-item label="预算类型:">
                                    <el-select class="selectwidth" v-model="queryform.meeting_name" @change="getBudgetInfo($event)">
                                    <el-option v-for="item in budgetTypes" :key="item.id" :label="item.text" :value="item.id">
                                    </el-option>
                                </el-select>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row>
                <el-col :span='24' class='iptcol'>
                  <el-form-item label="追加额度:">
                           <el-input class='inputwidth' v-model="queryform.amountPrice"></el-input>
                        </el-form-item>
                </el-col>
              </el-row>
                <el-form-item label="申请原因:" >
                           <el-input type="textarea" class='textwidth' v-model="queryform.remark"></el-input>
                </el-form-item>
                        <div  class='btnbox'>
                            <button class='search' @click.prevent @click="reset">清空</button>
                            <button class='search' @click.prevent @click="TapplyOrder">提交</button>
                        </div>
            </el-form>
        </div>

        <!-- 处理中 -->
        <div v-if='approveshow' class='Initiateapplication'>
                        <div class='time'>
                            <span>待审批:{{dsp}}</span>
                        </div>
         <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" >
        <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
        <el-table-column align="center" min-width="20%" prop="demanddep" label="部门名称" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].IS_PAPER" label="预算类型" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].THEYEAR" label="预算年度" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].BEGIN_DATE" label="预算开始时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].END_DATE" label="预算结束时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="8%"  prop="budgetInfo[0].depmoney" label="预算金额" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].leftmoney" label="剩余金额" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begindate" label="申请日期" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="beginpersonname" label="申请人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="attr2" label="联系方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="amountprice" label="追加额度" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  label="详情" show-overflow-tooltip>
          <template slot-scope="scope">
            <div @click='lookdff(scope.row)' v-show='scope.row.state=="已驳回"'>
                查看详情
            </div>
            <div @click='lookdff(scope.row)' v-show='scope.row.state=="已完成"'>
                查看详情
            </div>
            <div @click='lookdsp(scope.row)' v-show='scope.row.state=="待审批"'>
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


        <!-- 已审批 -->
        <div v-if='neckshow' class='Initiateapplication'>
                        <div class='time'>
<!--                            <span>待审批:{{dff}}</span>-->
                        </div>
         <el-table stripe ref="multipleTable" :data="tableData2" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" >
        <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
         <el-table-column align="center" min-width="20%" prop="demanddep" label="部门名称" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].IS_PAPER" label="预算类型" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].THEYEAR" label="预算年度" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].BEGIN_DATE" label="预算开始时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].END_DATE" label="预算结束时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="8%"  prop="budgetInfo[0].depmoney" label="预算金额" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="budgetInfo[0].leftmoney" label="剩余金额" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begindate" label="申请日期" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="beginpersonname" label="申请人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="attr2" label="联系方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="amountprice" label="追加额度" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%" label="详情" show-overflow-tooltip>
          <template slot-scope="scope">
            <!-- <div @click='lookdfftwo(scope.row)' v-show='scope.row.status=="待发放"'>
                查看详情
            </div>
            <div @click='lookyfftwo(scope.row)' v-show='scope.row.status=="已发放"'>
                查看详情
            </div>
             -->
             <div @click='lookdfftwo(scope.row)'>
                查看详情
             </div>
          </template>
        </el-table-column>
      </el-table>
       <div style="text-align: right; margin-top: 10px;height:'15%'">
                    <el-pagination
          @size-change="handleSizeChange2"
          @current-change="handleCurrentChange2"
          :current-page="pageIndex2"
          :page-sizes="pageSizes2"
          :page-size="pageSize2"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total2">
        </el-pagination>
              </div>
        </div>



      </div>

      <!-- 处理中待发放页面 -->
      <div v-show="detailshow" class='detailbox'>
        <div class='clearfix colbox'>
          <div style="float:left;">申请人:{{dffrow.beginpersonname}}</div>
          <div style="float:right;"><button class='search' @click.prevent @click="oppodetail">返回</button></div>
        </div>
        <div class='clearfix colbox'>
          <div style="float:left;">所在部门:{{dffrow.demanddep}}</div>
          <div style="float:right;">申请编号:{{dffrow.orderid}}</div>
        </div>
        <div class='colbox'>申请日期:{{dffrow.begindate}}</div>
        <div class='colbox'>联系方式:{{dffrow.attr2}}</div>
        <div class='colbox'>申请年度:{{budgetInfo.THEYEAR}}</div>
        <div class='colbox'>年度预算:{{budgetInfo.depmoney}}</div>
        <div class='colbox'>已执行数:{{budgetInfo.allmoney}}</div>
        <div class='colbox'>剩余额度:{{budgetInfo.leftmoney}}</div>
        <div class='colbox'>追加额度:{{dffrow.amountprice}}</div>
      <div class='timeleft'>
          预算类型:{{budgetInfo.IS_PAPER}}
      </div>
      <div>
          <div>
            申请原因:{{dffrow.remark}}
          </div>
      </div>
      </div>

    <!-- 处理中待审批页面 -->
    <div v-show="detailtwoshow" class='detailbox'>
            <div class='clearfix colbox'>
              <div style="float:left;">申请人:{{dsprow.beginpersonname}}</div>
              <div style="float:right;"><button class='search' @click.prevent @click="oppodetailtwo">返回</button></div>
            </div>
            <div class='clearfix colbox'>
              <div style="float:left;">所在部门:{{dsprow.demanddep}}</div>
              <div style="float:right;">申请编号:{{dsprow.orderid}}</div>
            </div>
           <div class='colbox'>申请日期:{{dsprow.begindate}}</div>
            <div class='colbox'>联系方式:{{dsprow.attr2}}</div>
            <div class='colbox'>申请年度:{{dspbudgetInfo.THEYEAR}}</div>
            <div class='colbox'>年度预算:{{dspbudgetInfo.depmoney}}</div>
            <div class='colbox'>已执行数:{{dspbudgetInfo.allmoney}}</div>
            <div class='colbox'>剩余额度:{{dspbudgetInfo.leftmoney}}</div>
            <div class='colbox'>追加额度:{{dsprow.amountprice}}</div>
          <div class='timeleft'>
             预算类型:{{dspbudgetInfo.IS_PAPER}}
          </div>
          <div>
              <div>
                申请原因:{{dsprow.remark}}
              </div>
              <div class='explainbox'>
                  审批说明:
                <el-input type="textarea" class='explainwidth' v-model="remark"></el-input>
              </div>
          </div>
          <div  class='btnbox'>
                <button class='search' @click.prevent @click="reject(dsprow)">拒绝</button>
                <button class='search' @click.prevent @click="pass(dsprow)">通过</button>
          </div>
      </div>

      <!-- 已审批待发放页面 -->
      <div v-show="grantshow" class='detailbox'>
            <div class='clearfix colbox'>
              <div style="float:left;">申请人:{{dfftworow.beginpersonname}}</div>
              <div style="float:right;"><button class='search' @click.prevent @click="oppogrant">返回</button></div>
            </div>
            <div class='clearfix colbox'>
              <div style="float:left;">所在部门:{{dfftworow.demanddep}}</div>
              <div style="float:right;">申请编号:{{dfftworow.orderid}}</div>
            </div>
            <div class='colbox'>申请日期:{{dfftworow.begindate}}</div>
            <div class='colbox'>联系方式:{{dfftworow.attr2}}</div>
            <div class='colbox'>申请年度:{{dffbudgetInfo.THEYEAR}}</div>
            <div class='colbox'>年度预算:{{dffbudgetInfo.depmoney}}</div>
            <div class='colbox'>已执行数:{{dffbudgetInfo.allmoney}}</div>
            <div class='colbox'>剩余额度:{{dffbudgetInfo.leftmoney}}</div>
            <div class='colbox'>追加额度:{{dfftworow.amountprice}}</div>
          <div class='timeleft'>
              预算类型:{{dffbudgetInfo.IS_PAPER}}
          </div>
          <div>
              <div class='colbox'>
                申请原因:{{dfftworow.remark}}
              </div>
              <div>
                驳回原因:{{dfftworow.bhyy}}
              </div>
          </div>
      </div>


      <!-- 已审批已发放页面
      <div v-show="granttwoshow" class='detailbox'>
        <div class='clearfix colbox'>
          <div style="float:left;">申请人:{{yfftworow.beginpersonname}}</div>
          <div style="float:right;"><button class='search' @click.prevent @click="oppogranttwo">返回</button></div>
        </div>
        <div class='clearfix colbox'>
          <div style="float:left;">所在部门:{{yfftworow.demanddep}}</div>
          <div style="float:right;">申请编号:{{yfftworow.orderid}}</div>
        </div>
         <div>
              <div>物品明细:</div>
              <div class='definite'  v-for='(item,index) in yfftworow.goods' :key="index">
                  <div class='article'>物品名称:{{item.goodname}}</div>
                  <div class='article'>物品数量:{{item.goodamount}}</div>
                  <div class='article'>物品规格:{{item.specs}}</div>
                  <div class='article'>存放地点:{{item.location}}</div>
              </div>
        </div>
      <div class='timeleft'>
          期望交付时间:{{yfftworow.receiptdate}}
      </div>
      <div>
          <div>
            申请原因:{{yfftworow.remark}}
          </div>
      </div>
      </div> -->



    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
    dialogChangeVisible:false,
    applyforshow:true,
    detailshow:false,
    detailtwoshow:false,
    grantshow:false,
    granttwoshow:false,
    applyshow:true,
    approveshow:false,
    neckshow:false,
    budgetTypes:[],
    nowDate:'',
    thisYear:'',
    allmoney:'',
    depmoney:'',
    remark:'',
    remarktwo:'',
    dffrow:{},
    budgetInfo:{},
    dspbudgetInfo:{},
    dffbudgetInfo:{},
    dsprow:{},
    dfftworow:{},
    yfftworow:{},
    dsp:'',
    dff:'',
    goods:[],
    pageIndex: 1,
    pageSize:10,
    pageSizes:[ 10, 20 , 30, 50, 100],
    total:0,
    pageIndex2: 1,
    pageSize2:10,
    pageSizes2:[ 10, 20 , 30, 50, 100],
    total2:0,
    queryform: {
      beginpersonid:'zs19990205',
      beginpersonname:'张三',
      demanddep:'办公室（党委办公室）',
      demanddepid:'A000000000000003',
      receiptdate:'',
      applyphone:'153XXXXXXX',
      remark:'',
      typeid:'006',
      meeting_name:'',
      meeting_person_numbe:'',
      amountPrice:'',
      goods:[]
    },
    tableData:[],
    tableData2:[],
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
    this.selectlist()
  },
  methods: {
    applychange(){
      this.applyshow=true
      this.approveshow=false
      this.neckshow=false
    },
    // 点击处理中页面
    approvechange(){
      this.neckshow=false
      this.applyshow=false
      this.approveshow=true
      this.$office({
        url:'TapplyOrder.do/getApprovalList',
        method:'post',
        data:{
          "pageNum": this.pageIndex,
          "pageSize":this.pageSize,
          "dealUserId":'E0311010542',
          "typeid":'006',
          "depId":""
        }
      }).then(res=>{
        this.tableData=res.data.page.list
        this.total=res.data.page.totalRecord
        this.dsp=res.data.dsp
      })
    },
    //点击已审批页面
    neckchange(){
      this.applyshow=false
      this.approveshow=false
      this.neckshow=true
      this.$office({
        url:'TdeptBudget.do/getSituation',
        method:'post',
        data:{
          "pageNum": this.pageIndex2,
          "pageSize":this.pageSize2,
          "beginPersonId": "zs19990205",	//申请人id
          "typeid": "006",			//固定传006
          "depId": ""	,		//传空
          "status":"1"			//固定传1
        }
      }).then(res=>{
        // console.log(res)
        this.tableData2=res.data.page.list
        this.total2=res.data.page.totalRecord
        // this.dff=res.data.dff
      })
    },
    oppodetail(){
      this.detailshow=false
      this.applyforshow=true
      this.approveshow=true
    },
    oppodetailtwo(){
      this.detailtwoshow=false
      this.applyforshow=true
      this.approveshow=true
    },
    oppogrant(){
      this.grantshow=false
      this.applyforshow=true
      this.neckshow=true
    },
    oppogranttwo(){
      this.granttwoshow=false
      this.applyforshow=true
      this.neckshow=true
    },
    handleSizeChange (val) {
        this.pageSize = val
        this.approvechange()
        console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.approvechange()
        console.log(`当前页: ${val}`)
      },
      handleSizeChange2 (val) {
        this.pageSize2 = val
        this.neckchange()
        console.log(`每页 ${val} 条`)
      },
      handleCurrentChange2 (val) {
        this.pageIndex2=val
        this.neckchange()
        console.log(`当前页: ${val}`)
      },
    handleNodeClick (data) {
      console.log(data)
    },
    // 查看详情待发放按钮
    lookdff(row){
      this.applyforshow=false
      this.detailshow=true
      this.dffrow=row
      this.budgetInfo=row.budgetInfo.length>0?row.budgetInfo[0]:{}
    },
    lookdsp(row){
      this.applyforshow=false
      this.detailtwoshow=true
      this.dsprow=row
      this.dspbudgetInfo=row.budgetInfo.length>0?row.budgetInfo[0]:{}
    },
    lookdfftwo(row){
      this.applyforshow=false
      this.grantshow=true
      this.dfftworow=row
      this.dffbudgetInfo=row.budgetInfo.length>0?row.budgetInfo[0]:{}
    },
    // lookyfftwo(row){
    //   this.applyforshow=false
    //   this.granttwoshow=true
    //   this.yfftworow=row
    // },
    // 下拉框数据
    selectlist(){
      this.$office({
        url:'TdeptBudget.do/dropDownBox',
        method:'get',
      }).then(res=>{
        this.budgetTypes=res.data.budgetTypes
        this.nowDate=res.data.nowDate
        this.thisYear=res.data.thisYear
      })
    },
    // 查询申请信息
    getBudgetInfo(id){
        console.log(id)
      this.$office({
        url:'TdeptBudget.do/getBudgetInfo',
        method:'post',
        data:{
          "orgId":"A000000000000003",
          "isPaper":id,
        }
      }).then(res=>{
          this.allmoney=res.data[0].allmoney
          this.depmoney=res.data[0].depmoney
      })
    },
  // 提交预算追加申请
    TapplyOrder(){
      if(this.queryform.remark==''||this.queryform.meeting_name==''||this.queryform.amountPrice==''){
        this.$message('请补全信息')
        return
      }
      this.$office({
        url:'TapplyOrder.do/TapplyOrder',
        method:'post',
        data:this.queryform
      }).then(res=>{
        if(res.data=='操作成功！'){
          this.$message.success('提交申请成功')
          this.allmoney=''
          this.depmoney=''
          this.queryform={
                beginpersonid:'zs19990205',
                beginpersonname:'张三',
                demanddep:'办公室（党委办公室）',
                demanddepid:'A000000000000003',
                receiptdate:'',
                applyphone:'153XXXXXXX',
                remark:'',
                typeid:'006',
                meeting_name:'',
                meeting_person_numbe:'',
                amountPrice:'',
                goods:[]
            }
        }else{
          this.$message.error('提交申请失败')
        }
      })
    },
    // 重置申请
    reset(){
      this.allmoney=''
      this.depmoney=''
      this.queryform={
                beginpersonid:'zs19990205',
                beginpersonname:'张三',
                demanddep:'办公室（党委办公室）',
                demanddepid:'A000000000000003',
                receiptdate:'',
                applyphone:'153XXXXXXX',
                remark:'',
                typeid:'006',
                meeting_name:'',
                meeting_person_numbe:'',
                amountPrice:'',
                goods:[]
            }
    },
    // 审批拒绝
    reject(row){
      console.log(row)
      this.$office({
        url:'TapplyOrder.do/approveApply',
        method:'post',
        data:{
          "orderid":row.orderid,
          "typeid":'006',
          "status":row.status,
          "step":row.step,
          "index":'0',
          "demanddepid":row.demanddepid,
          "remark":this.remark,
        }
      }).then(res=>{
        if(res.data=='操作成功'){
          this.$message.success('审批已拒绝')
          this.detailtwoshow=false
          this.applyforshow=true
          this.approveshow=true
          this.approvechange()
        }else{
          this.$message.error('审批拒绝失败')
        }
        this.remark=''
      })
    },
    // 审批同意
    pass(row){
      this.$office({
        url:'TapplyOrder.do/approveApply',
        method:'post',
        data:{
          "orderid":row.orderid,
          "typeid":'006',
          "status":row.status,
          "step":row.step,
          "index":'1',
          "demanddepid":row.demanddepid,
          "remark":this.remark,
        }
      }).then(res=>{
        if(res.data=='操作成功'){
          this.$message.success('审批已通过')
          this.detailtwoshow=false
          this.applyforshow=true
          this.approveshow=true
          this.approvechange()
        }else{
          this.$message.error('审批通过失败')
        }
        this.remark=''
      })
    },
    // // 驳回
    // bohui(id){
    //   this.$office({
    //     url:'TapplyOrder.do/grantGoods',
    //     method:'post',
    //     data:{
    //       "orderId":id,
    //       "status":'2',
    //       "remark":this.remarktwo,
    //     }
    //   }).then(res=>{
    //     if(res.data=='驳回成功'){
    //       this.$message.success('驳回成功')
    //       this.grantshow=false
    //       this.applyforshow=true
    //       this.neckshow=true
    //       this.neckchange()
    //     }else{
    //       this.$message.error('驳回失败')
    //     }
    //     this.remarktwo=''
    //   })
    // },
    // 发放
    fafang(id){
      this.$office({
        url:'TapplyOrder.do/grantGoods',
        method:'post',
        data:{
          "orderid":id,
        }
      }).then(res=>{
        if(res.data=='发放成功'){
          this.$message.success('发放成功')
          this.grantshow=false
          this.applyforshow=true
          this.neckshow=true
          this.neckchange()
        }else{
          this.$message.error('发放失败')
        }
        // this.remarktwo=''
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
.tab{
    font-size: 18px;
    color:red;
    padding-left:16px;
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
.add{
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
img{
  width: 12px;
  vertical-align: middle;
}
img:hover{
  cursor: pointer;
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
.apply{
    position: absolute;
    bottom:-30px;
    left: 170px;
}
.approve{
    position: absolute;
    bottom: -40px;
    left: 670px;
}
.neck{
    position: absolute;
    bottom: -30px;
    left: 1170px;
}
.imgdescribe{
    display:block;
    color:rgb(149,242,4);
    text-align: center;
}
.wpmx{
  margin: 0 0 10px 0;
}
.artname{
    background: url(../../assets/u305.png)no-repeat;
    background-size: 100% 100%;
    display: inline-block;
    vertical-align: top;
    width:470px;
    height:150px;
    box-sizing: border-box;
    padding:18px 10px 0px 10px;
    margin:0 20px 10px 0px;
    border:1px solid rgb(52,57,77);
}
.addart{
    // background: url(../../assets/u305.png)no-repeat;
    background-size: 100% 100%;
    display: inline-block;
    width:335px;
    height:150px;
    vertical-align: top;
    text-align: center;
    line-height: 150px;
    color: white;
    margin-bottom: 20px;
    background:url(../../assets/u171.svg) center center no-repeat;
    border:1px solid rgb(52,57,77);
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
.time span{
  margin:0 30px 0 0;
}

.imgbox{
  margin-top:80px;
  position:relative;
}
.line{
  width: 1530px;
}
.waterdrop{
  width: 70px;
}
.Initiateapplication{
  margin:120px 0 0 0;
  padding:0 20px 0 20px;
  color: white;
  font-size: 12px;
}
.Initiateapplicationone{
  width: 600px;
  margin:80px auto 0;
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
  .inputwidth{
    width: 400px;
  }
  .textwidth{
    width: 400px;
    height: 300px;
  }
  .textwidth /deep/.el-textarea__inner{
      height: 300px;
  }
  .explainwidth{
    width: 1400px;
    vertical-align: middle;
  }
  .selectwidth{
    width: 400px;
  }
  .amountwidth{
    width: 80px;
  }
  .iptcol{
    height:40px;
    line-height: 40px;
    margin-bottom:0;
}
.btnbox{
  text-align: right;
  margin:40px 0 10px 0;
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
</style>
