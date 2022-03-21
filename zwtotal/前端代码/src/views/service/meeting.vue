<template>
    <div class='container'>
        <div class='foodtitle'>会议用品</div>
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
          <span  class="assess"><span class="imgdescribe">评价</span><img src="../../assets/u105.svg" @click="lookevaluate" alt="" class='waterdrop' style="transform: rotate(180deg)"></span>

        </div>

        <!-- 发起申请 -->
        <div v-if='applyshow' class='Initiateapplication'>
            <div class='marginbtm'>申请人:</div>
            <div class='marginbtm'>需求部门:</div>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
              <el-row>
                  <el-col :span='6' class='iptcol'>
                  <el-form-item label="会议名称:">
                           <el-input class='inputwidth' v-model="queryform.meeting_name"></el-input>
                        </el-form-item>
                </el-col>
                <el-col :span='6' class='iptcol'>
                  <el-form-item label="参会人数:">
                           <el-input class='inputwidth' v-model="queryform.meeting_person_number"></el-input>
                        </el-form-item>
                </el-col>
                 <el-col :span='6' class='iptcol'>
                  <el-form-item label="联系方式:">
                           <el-input class='inputwidth' v-model="queryform.applyphone"></el-input>
                </el-form-item>
                </el-col>
                <el-col :span='5' class='iptcol'>
                  <el-form-item label="领用时间:">
                                      <el-date-picker
                                              v-model="queryform.receiptdate"
                                              format="yyyy-MM-dd"
                                              value-format="yyyy-MM-dd"
                                              type="date"
                                              placeholder="选择日期">
                                              </el-date-picker>
                        </el-form-item>
                </el-col>
              </el-row>
                <el-form-item label="申请原因:" >
                           <el-input type="textarea" class='textwidth' v-model="queryform.remark"></el-input>
                </el-form-item>

                        <div>
                            <div class='wpmx'>物品明细:</div>
                            <div class="artname" v-for="(item,index) in queryform.goods" :key='index'>
                              <p style="margin-top: -15px;margin-left: 97%;color: red" @click.prevent @click="cancel">x</p>

                              <el-form-item label="物品名称:">
                                    <el-select class="selectwidth" v-model="item.goodname" @change="getGoodInfo($event,index)">
                                    <el-option v-for="item in goods" :key="item.id" :label="item.goodname" :value="item.id">
                                    </el-option>
                                </el-select>
                              </el-form-item>
                              <el-row>
                                <el-col :span='8' class='iptcol'>
                                    <el-form-item label="物品数量:">
                                      <el-input class='amountwidth' v-model="item.goodamount"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span='8' class='iptcol'>
                                  <el-form-item label="物品单位:">
                                    <el-input class='amountwidth' readonly v-model="item.unit"></el-input>
                                  </el-form-item>
                                </el-col>
                                <el-col :span='8' class='iptcol'>
                                  <el-form-item label="物品规格:">
                                    <el-input  class='amountwidth' readonly v-model="item.specs"></el-input>
                                  </el-form-item>
                                </el-col>
                              </el-row>
                                <el-form-item label="备注:">
                                <el-input class="selectwidth" v-model="item.location"></el-input>
                                </el-form-item>
                            </div>
                            <div class="addart">
                                <span style="cursor: pointer" @click="addartname">添加物品明细...</span>
                            </div>
                        </div>
                        <div  class='btnbox'>
                            <button class='search' @click.prevent @click="reset">重置申请</button>
                            <button class='search' @click.prevent @click="TapplyOrder">提交申请</button>
                        </div>
            </el-form>
        </div>

        <!-- 处理中 -->
        <div v-if='approveshow' class='Initiateapplication'>
            <!-- <div  class="marginbtm">本人申请:处理</div>  -->
            <!-- <div  class="marginbtm">本人审批:</div>  -->
                        <div class='time'>
                            <span>待审批:{{dsp}}</span>
                        </div>
         <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" >
        <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
        <el-table-column align="center" min-width="12%" prop="state" label="类型" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center" min-width="8%"  prop="beginpersonname" label="申请人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begindate" label="提交时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="demanddep" label="需求部门" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="9%"  prop="receiptdate" label="期望交付时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="8%"  prop="attr2" label="联系方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="26%"  prop="remark" label="申请原因 " show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  label="详情" show-overflow-tooltip>
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
            <!-- <div  class="marginbtm">本人申请:审批</div>  -->
            <!-- <div  class="marginbtm">本人审批:</div>  -->
                        <div class='time'>
                            <span>待发放:{{dfftwo}}</span>
                        </div>
         <el-table stripe ref="multipleTable" :data="tableData2" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" >
        <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
        <el-table-column align="center" min-width="8%" prop="state" label="类型" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="beginpersonname" label="申请人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begindate" label="提交时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="demanddep" label="需求部门" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="9%"  prop="receiptdate" label="期望交付时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="8%"  prop="attr2" label="联系方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="26%"  prop="remark" label="申请原因 " show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%" label="详情" show-overflow-tooltip>
          <template slot-scope="scope">
            <div @click='lookdfftwo(scope.row)' v-show='scope.row.state=="待发放"'>
                查看详情
            </div>
            <div @click='lookyfftwo(scope.row)' v-show='scope.row.state=="已发放"'>
                查看详情
            </div>
            <div @click='lookdfftwo(scope.row)' v-show='scope.row.state=="待评价"'>
              查看详情
            </div>
            <div @click='lookyfftwo(scope.row)' v-show='scope.row.state=="已驳回"'>
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

        <!-- 评价 -->
        <div v-if='Evaluate' class='Initiateapplication'>
          <!-- <div  class="marginbtm">本人申请:审批</div>  -->
          <!-- <div  class="marginbtm">本人审批:</div>  -->
          <div class='time'>
            <span>待评价:{{dpj}}</span>
          </div>
          <el-table stripe ref="multipleTable" :data="tableData3" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                    size="small"  style="width: 100%;margin-top:0px;" >
            <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
            <el-table-column align="center" min-width="8%" prop="status" label="类型" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" min-width="8%"  prop="orderid" label="申请编号" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" min-width="8%"  prop="deal_user_name" label="审批人" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" min-width="8%"  prop="begindate" label="提交时间" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" min-width="9%"  prop="receiptdate" label="交付时间" show-overflow-tooltip></el-table-column>
<!--            <el-table-column align="center"  min-width="8%"  prop="attr2" label="历史评分" show-overflow-tooltip></el-table-column>-->
<!--            <el-table-column align="center" min-width="26%"  prop="remark" label="物品评分 " show-overflow-tooltip></el-table-column>-->
            <el-table-column align="center" min-width="26%"  prop="remark" label="申请原因 " show-overflow-tooltip></el-table-column>

            <el-table-column align="center" min-width="10%" label="详情" show-overflow-tooltip>
              <template slot-scope="scope">
                <div @click='lookypj(scope.row)' v-show='scope.row.status=="待评价"'>
                  查看详情
                </div>
                <div @click='lookypj(scope.row)' v-show='scope.row.status=="已评价"'>
                  查看详情
                </div>

              </template>
            </el-table-column>
          </el-table>
          <div style="text-align: right; margin-top: 10px;height:'15%'">
            <el-pagination
              @size-change="handleSizeChange2"
              @current-change="handleCurrentChange2"
              :current-page="pageIndex3"
              :page-sizes="pageSizes3"
              :page-size="pageSize3"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total3">
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
         <div>
              <div>物品明细:</div>
              <div class='definite' v-for="(item,index) in dffrow.goods" :key="index">
                  <div class='article'>物品名称:{{item.goodname}}</div>
                  <div class='article'>物品数量:{{item.goodamount}}</div>
                  <div class='article'>物品规格:{{item.specs}}</div>
                  <div class='article'>存放地点:{{item.location}}</div>
              </div>
        </div>
      <div class='timeleft'>
          期望交付时间:{{dffrow.receiptdate}}
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
            <div>
                  <div>物品明细:</div>
                  <div class='definite' v-for="(item,index) in dsprow.goods" :key="index">
                    <div class='article'>物品名称:{{item.goodname}}</div>
                    <div class='article'>物品数量:{{item.goodamount}}</div>
                    <div class='article'>物品规格:{{item.specs}}</div>
                    <div class='article'>存放地点:{{item.location}}</div>
                  </div>
            </div>
          <div class='timeleft'>
              期望交付时间:{{dsprow.receiptdate}}
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
            <div>
                  <div>物品明细:</div>
                  <div class='definite' v-for='(item,index) in dfftworow.goods' :key="index">
                      <div class='article'>物品名称:{{item.goodname}}</div>
                      <div class='article'>物品数量:{{item.goodamount}}</div>
                      <div class='article'>物品规格:{{item.specs}}</div>
                      <div class='article'>存放地点:{{item.location}}</div>
                  </div>
            </div>
          <div class='timeleft'>
              期望交付时间:{{dfftworow.receiptdate}}
          </div>
          <div>
              <div>
                申请原因:{{dfftworow.remark}}
              </div>
              <!-- <div class='explainbox'>
                  审批说明:
                <el-input type="textarea" class='explainwidth' v-model="remarktwo"></el-input>
              </div> -->
          </div>
          <div  class='btnbox'>
                <!-- <button class='search' @click.prevent @click="bohui(dfftworow.orderid)">驳回</button> -->
<!--                <button class='search' @click.prevent @click="fafang(dfftworow.orderid)">发放</button>-->
          </div>
      </div>


      <!-- 已审批已发放页面 -->
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
      </div>

      <!-- 评价已评价页面 -->
      <div v-show="ypjpage" class='detailbox'>
        <div class='clearfix colbox'>
          <div style="float:left;">申请人:{{ypjrow.beginpersonname}}</div>
          <div style="float:right;"><button class='search' @click.prevent @click="ypjreturn">返回</button></div>
        </div>
        <div class='clearfix colbox'>
          <div style="float:left;">所在部门:{{ypjrow.demanddep}}</div>
          <div style="float:right;">申请编号:{{ypjrow.orderid}}</div>
        </div>
        <div class='clearfix colbox'>
          <div style="float:left;">申请原因:{{ypjrow.remark}}</div>
        </div>
        <div class='timeleft'style="font-size: 16px">评价详情</div>
        <div style="margin-top: 10px;font-size: 16px">物品明细:</div>
        <div class='pjdefinite' v-for="(item,index) in ypjrow.goods" :key="index">
          <div style="width: 100px;height: 100px"><img :src="item.pictureurl" alt=""></div>
          <div style="display: flex" >
            <div style="margin-left: 15px">
              <div style="margin-top: 8px">物品名称:<span>{{item.goodname}}</span></div>
              <div style="margin-top: 8px">物品数量:<span>{{item.goodamount}}</span></div>
              <div style="margin-top: 8px">物品规格:<span>{{item.specs}}</span></div>
              <div style="margin-top: 8px">存放地点:<span>{{item.location}}</span></div>
            </div>
            <div style="width: 750px">
              <el-form :model="item" ref="queryform" size="mini" >
                <div style="display: flex;margin-left: 15px">

                  <p> <el-rate v-model="item.number"></el-rate></p>

                  <p style="font-size: 14px;color: white;margin-left: 15px;margin-top: 12px;margin-right: 15px">历史评分:</p>
                  <p> <el-rate v-model="item.history"></el-rate></p>
                </div>
                <div style="margin-top: -9px;margin-left: 15px">
                  <el-input
                    type="textarea"

                    :autosize="{ minRows:3, maxRows: 5}"
                    placeholder="请输入内容"
                    v-model="item.content">
                  </el-input>
                </div>
              </el-form>
            </div>

          </div>
        </div>
        <div style="float:right;margin-right: 80px"><button class='search' @click.prevent @click="publish(ypjrow)" >发表</button></div>
      </div>

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
    Evaluate:false,
    ypjpage:false,
    remark:'',
    remarktwo:'',
    dffrow:{},
    dsprow:{},
    dfftworow:{},
    yfftworow:{},
      ypjrow:{},
    dsp:'',
    dfftwo:'',
      dpj:'',
    goods:[],
      item:{
      number:"",
        history:"",
      },
    pageIndex: 1,
    pageSize:10,
    pageSizes:[ 10, 20 , 30, 50, 100],
    total:0,
    pageIndex2: 1,
      pageSize2:10,
      pageSizes2:[ 10, 20 , 30, 50, 100],
      total2:0,
      pageIndex3: 1,
      pageSize3:10,
      pageSizes3:[ 10, 20 , 30, 50, 100],
      total3:0,
    queryform: {
      beginpersonid:'zs19990205',
      beginpersonname:'张三',
      demanddep:'办公室（党委办公室）',
      demanddepid:'A000000000000003',
      receiptdate:'',
      applyphone:'',
      remark:'',
      typeid:'004',
      meeting_name:'',
      meeting_person_numbe:'',
      amountPrice:'',
      goods:[{
        goodid:'',
        goodno:'',
        goodname:'',
        goodamount:'',
        specs:'',
        brand:'',
        storeno:'',
        unitprice:'',
        unit:'',
        pictureurl:'',
        location:'',
      }]
    },
    tableData:[],
    tableData2:[],
      tableData3:[],
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
  //添加物品明细按钮
    addartname(){
      this.queryform.goods.push({
        goodid:'',
        goodno:'',
        goodname:'',
        goodamount:'',
        specs:'',
        brand:'',
        storeno:'',
        unitprice:'',
        unit:'',
        pictureurl:'',
        location:'',
      })
    },
    //物品明细删除
    cancel(){
      this.queryform.goods.splice(0,1);
    },
    applychange(){
      this.applyshow=true
      this.approveshow=false
      this.neckshow=false
      this.Evaluate=false
    },
    // 点击处理中页面
    approvechange(){
      this.neckshow=false
      this.applyshow=false
      this.approveshow=true
      this.Evaluate=false
      this.$office({
        url:'TapplyOrder.do/getApprovalList',
        method:'post',
        data:{
          "pageNum": this.pageIndex,
          "pageSize":this.pageSize,
          "dealUserId":'E0311010542',
          "typeid":'004',
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
      this.Evaluate=false
      this.$office({
        url:'TdeptBudget.do/getSituation',
        method:'post',
        data:{
          "pageNum": this.pageIndex,
          "pageSize":this.pageSize,
          "beginPersonId": "zs19990205",	//申请人id
          "typeid": "004",			//类型
          "depId": "",
          "status":"1"			//0：处理中界面	1：已审批界面       这里固定传1
        }
      }).then(res=>{
        // console.log(res)
         this.tableData2=res.data.page.list
      this.total2=res.data.page.totalRecord
          this.dfftwo=res.data.dff
      })
    },
    //点击图片评价
    lookevaluate(){
      this.applyshow=false
      this.approveshow=false
      this.neckshow=false
      this.Evaluate=true
      this.$office({
        url:'TdeptBudget.do/getPjList',
        method:'post',
        data:{
          "pageNum": this.pageIndex3,//页码
          "pageSize":this.pageSize3,//每页数量
          "beginPersonId": "zs19990205",	//申请人id
          "typeid": "004"


        }
      }).then(res=>{
        // console.log(res.data)
        this.tableData3=res.data.page.list
        this.total3=res.data.page.totalRecord
        this.dpj=res.data.dpj
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
    ypjreturn(){  //已评价返回

      this.applyforshow=true

      // this.estimate=false

      this.ypjpage=false
      this.lookevaluate()
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
    },
    lookdsp(row){
      this.applyforshow=false
      this.detailtwoshow=true
      this.dsprow=row
    },
    lookdfftwo(row){
      this.applyforshow=false
      this.grantshow=true
      this.dfftworow=row
    },
    lookyfftwo(row){
      this.applyforshow=false
      this.granttwoshow=true
      this.yfftworow=row
    },
    //查看待评价
    lookypj(row){
      // this.estimate=false
      this.applyforshow=false
      this.ypjpage=true
      this.ypjrow=row

    },
    publish(row){
      console.log(row)
      this.$office({
        url:'TdeptBudget.do/evaluate',
        method:'post',
        data:[
          {
            "orderId": this.ypjrow.orderid,		//编号，从列表中获取
            "content": this.ypjrow.goods[0].content,		//评价内容
            "number":this.ypjrow.goods[0].number,				//评价星级
            "pictureUrl": "",			//图片
            "goodNo":this.ypjrow.goods[0].goodno	//物品编号，从列表中获取
          }
        ],
      }).then(res=>{
        // console.log(res.data)
        if(res.data=='评价成功'){
          this.$message.success('发表成功')

        }else{
          this.$message.error('发表失败')
        }
      })
    },
    // 下拉框数据
    selectlist(){
      this.$office({
        url:'TapplyOrder.do/dropDownBox',
        method:'post',
        data:{
          "typeid": "004"
        }
      }).then(res=>{
        // console.log(res)
        this.goods=res.data.goods
      })
    },
    // 通过i物品详情
    getGoodInfo(id,index){
      this.$office({
        url:'TapplyOrder.do/getGoodInfo',
        method:'post',
        data:{
          'id':id
        }
      }).then(res=>{
        this.queryform.goods[index].goodid=res.data.id
        this.queryform.goods[index].goodno=res.data.goodno
        this.queryform.goods[index].goodname=res.data.goodname
        this.queryform.goods[index].specs=res.data.specs
        this.queryform.goods[index].brand=res.data.brand
        this.queryform.goods[index].storeno=res.data.storeno
        this.queryform.goods[index].unitprice=res.data.unitprice
        this.queryform.goods[index].unit=res.data.unit
        this.queryform.goods[index].pictureurl=res.data.pictureurl
      })
    },
  // 提交办公服务用品申请
    TapplyOrder(){
      if(this.queryform.receiptdate==''||this.queryform.applyphone==''||this.queryform.remark==''||this.queryform.meeting_name==''||this.queryform.meeting_person_number==''){
        this.$message('请补全信息')
        return
      }
      for(var i=0;i<this.queryform.goods.length;i++){
        if(this.queryform.goods[i].goodname==''||this.queryform.goods[i].goodamount==''||this.queryform.goods[i].location==''){
          this.$message('请补全信息')
          return
        }
      }
      this.$office({
        url:'TapplyOrder.do/TapplyOrder',
        method:'post',
        data:this.queryform
      }).then(res=>{
        if(res.data=='操作成功！'){
          this.$message.success('提交申请成功')
          this.queryform={
              beginpersonid:'zs19990205',
              beginpersonname:'张三',
              demanddep:'办公室（党委办公室）',
              demanddepid:'A000000000000003',
              receiptdate:'',
              applyphone:'',
              remark:'',
              typeid:'004',
              meeting_name:'',
              meeting_person_numbe:'',
              amountPrice:'',
              goods:[{
                goodid:'',
                goodno:'',
                goodname:'',
                goodamount:'',
                specs:'',
                brand:'',
                storeno:'',
                unitprice:'',
                unit:'',
                pictureurl:'',
                location:'',
              }]
            }
        }else{
          this.$message.error('提交申请失败')
        }
      })
    },
    // 重置申请
    reset(){
      this.queryform={
        beginpersonid:'zs19990205',
        beginpersonname:'张三',
        demanddep:'办公室（党委办公室）',
        demanddepid:'A000000000000003',
        receiptdate:'',
        applyphone:'',
        remark:'',
        typeid:'004',
        meeting_name:'',
        meeting_person_numbe:'',
        amountPrice:'',
        goods:[{
          goodid:'',
          goodno:'',
          goodname:'',
          goodamount:'',
          specs:'',
          brand:'',
          storeno:'',
          unitprice:'',
          unit:'',
          pictureurl:'',
          location:'',
        }]
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
          "typeid":'004',
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
          "typeid":'004',
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

    //审批界面用户查询申请情况
    ApprovalQuery(){
      this.$office({
        url:'TdeptBudget.do/getSituation',
        method:'post',
        data:{
          "pageNum": this.pageIndex,
          "pageSize":this.pageSize,
          "beginPersonId": "zs19990205",	//申请人id
          "typeid": "004",			//类型
          "depId": "",
          "status":"1"			//0：处理中界面	1：已审批界面       这里固定传1
        }
      }).then(res=>{
        console.log(res)
        // this.tableData=res.data.page.list
        // this.total=res.data.page.totalRecord
        // this.dsp=res.data.dsp
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
    left: 570px;
}
.neck{
    position: absolute;
    bottom: -30px;
    left: 970px;
}
.assess{
  position: absolute;
  bottom: -40px;
  left: 1300px;
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
    height:170px;
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
    width: 200px;
  }
  .textwidth{
    width: 1400px;
  }
  .explainwidth{
    width: 1400px;
    vertical-align: middle;
  }
  .selectwidth{
    width: 370px;
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
.pjdefinite{
  width: 1350px;
  height: 120px;
  background: url(../../assets/u305.png)no-repeat;
  background-size: 100% 100%;
  margin:10px 40px 10px 0;
  display: flex;
  vertical-align: top;
  padding: 10px 20px;
  box-sizing: border-box;
  margin-left: 50px;
}
.article{
  margin:0 0 10px 0;
}
.explainbox{
  margin: 20px 0;
}
</style>
