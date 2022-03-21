<template>
    <div class='container'>
        <div class='foodtitle'>采购计划</div>
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
        <div v-if='applyshow' class='Initiateapplication'>
          <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <div class='marginbtm'>申请人:王二</div>
          <div class='marginbtm'>电话:<el-input style="margin-left: 20px" class='inputwidth' v-model="queryform.beginPersonPhone"></el-input></div>
<!--          <div class='marginbtm'>状态:</div>-->
            <div class='marginbtm'>部门:办公室（党委办公室）</div>

              <el-row>
                <el-col :span='5' class='iptcol'>
                  <el-form-item label="采购日期:">
                                      <el-date-picker
                                              v-model="queryform.planTime"
                                              format="yyyy-MM-dd"
                                              value-format="yyyy-MM-dd"
                                              type="date"
                                              placeholder="选择日期">
                                              </el-date-picker>
                        </el-form-item>
                </el-col>
                <el-col :span='9' class='iptcol'>
                  <el-form-item label="采购申请:">
                           <el-input class='inputwidth' v-model="queryform.title"></el-input>
                        </el-form-item>
                </el-col>
              </el-row>
                <el-form-item label="采购理由:" >
                           <el-input type="textarea" class='textwidth' v-model="queryform.remark"></el-input>
                </el-form-item>

                        <div class='marbtm'>
                            <div class='wpmx'>物品明细:</div>
                            <div class="artname" v-for="(item,index) in queryform.goods" :key='index'>
                              <p style="margin-top: -15px;margin-left: 97%;color: red" @click.prevent @click="cancel">x</p>

                              <el-row>
                                <el-col :span="16">
                                  <el-form-item label="物品名称:">
                                    <el-select class="swidth" v-model="item.goodName" @change="getGoodInfo($event,index)">
                                    <el-option v-for="item in goods" :key="item.id" :label="item.goodname" :value="item.id">
                                    </el-option>
                                </el-select>
                              </el-form-item>
                              </el-col>
                              <el-col :span="8">
                                  <el-form-item label="物品状态:">
                                    <el-input class='amountwidth' readonly v-model="item.zhuangtai"></el-input>
                                  </el-form-item>
                                </el-col>
                              </el-row>

                              <el-row>
                                <el-col :span='8' class='iptcol'>
                                    <el-form-item label="采购数量:">
                                      <el-input class='amountwidth' v-model="item.realAmount"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span='8' class='iptcol'>
                                  <el-form-item label="采购单价:">
                                    <el-input class='amountwidth' v-model="item.realUnitprice"></el-input>
                                  </el-form-item>
                                </el-col>
                                <el-col :span='8' class='iptcol'>
                                  <el-form-item label="物品单位:">
                                    <el-input class='amountwidth' readonly v-model="item.unit"></el-input>
                                  </el-form-item>
                                </el-col>
                                <el-col :span='8' class='iptcol'>
                                  <el-form-item label="规格型号:">
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
                        <div class='time'>
                            <span>计划采购总数量:{{amount}}</span>
                            <span>计划采购总金额:{{money}}</span>

                        </div>
                        <div  class='btnbox'>
                            <button class='search' @click.prevent @click="reset">重置申请</button>
                            <button class='search' @click.prevent @click="TapplyOrder(queryform.goods)">提交申请</button>
                        </div>
            </el-form>
        </div>

        <!-- 处理中 -->
        <div v-if='approveshow' class='Initiateapplication'>
            <div  class="marginbtm">本人申请:处理</div>
            <div  class="marginbtm">本人审批:</div>
                        <div class='time'>
                            <span>待审批:{{dsp}}</span>
                            <span>待发放:{{dff}}</span>
                        </div>
         <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" >
        <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
<!--        <el-table-column align="center" min-width="12%" prop="orderid" label="项目" show-overflow-tooltip> </el-table-column>-->
        <el-table-column align="center" min-width="8%" prop="status" label="状态" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begin_date" label="提交时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begin_person_name" label="申请人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begin_dep_name" label="需求部门" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="9%"  prop="plan_time" label="期望交付时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="8%"  prop="begin_person_phone" label="联系方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="26%"  prop="remark" label="申请原因 " show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  label="详情" show-overflow-tooltip>
          <template slot-scope="scope">
            <div @click='lookdff(scope.row)' v-show='scope.row.status=="待发放"'>
                查看详情
            </div>
            <div @click='lookdsp(scope.row)' v-show='scope.row.status=="待审批"'>
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
            <div  class="marginbtm">本人申请:审批</div>
            <div  class="marginbtm">本人审批:</div>
                        <div class='time'>
                            <span>待发放:{{dfftwo}}</span>
<!--                            <span>已发放:{{yfftwo}}</span>-->
                        </div>
         <el-table stripe ref="multipleTable" :data="tableData2" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" >
        <el-table-column align="center" min-width="5%" type="selection"  width="55"> </el-table-column>
<!--        <el-table-column align="center" min-width="12%" prop="orderid" label="项目" show-overflow-tooltip> </el-table-column>-->
        <el-table-column align="center" min-width="8%" prop="status" label="状态" show-overflow-tooltip></el-table-column>
           <el-table-column align="center" min-width="8%"  prop="begin_person_name" label="申请人" show-overflow-tooltip></el-table-column>

           <el-table-column align="center" min-width="8%"  prop="begin_date" label="提交时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="begin_dep_name" label="需求部门" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="9%"  prop="plan_time" label="期望交付时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="8%"  prop="begin_person_phone" label="联系方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="26%"  prop="remark" label="申请原因 " show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%" label="详情" show-overflow-tooltip>
          <template slot-scope="scope">
            <div @click='lookdfftwo(scope.row)' v-show='scope.row.status=="已驳回"'>
                查看详情
            </div>
            <div @click='lookyfftwo(scope.row)' v-show='scope.row.status=="已审批"'>
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
          <div style="float:left;">申请人:{{dffrow.begin_person_name}}</div>
          <div style="float:right;"><button class='search' @click.prevent @click="oppodetail">返回</button></div>
        </div>
        <div class='clearfix colbox'>
          <div style="float:left;">
            <span>所在部门:{{dffrow.begin_dep_name}}</span>
<!--            <span>部门预算余额:{{dffrow.MAX_MONEY}}</span>-->
<!--            <span>申请总金额:{{dffrow.totalPrice}}</span>-->
          </div>
          <div style="float:right;">申请编号:{{dffrow.apply_no}}</div>
        </div>
         <div>
              <div>物品明细:</div>
              <div class='definite' v-for="(item,index) in dffrow.goods" :key="index">
                  <div class='article'>物品名称:{{item.goodname}}</div>
                  <div class='article'>物品数量:{{item.real_amount}}</div>
                  <div class='article'>物品规格:{{item.specs}}</div>
<!--                  <div class='article'>物品单价:{{item.unitprice}}</div>-->
                  <div class='article'>存放地点:{{item.location}}</div>
              </div>
        </div>
      <div class='timeleft'>
          期望交付时间:{{dffrow.plan_time}}
      </div>
      <div>
          <div>
            申请原因:{{dffrow.remark}}
          </div>
      </div>
      </div>

    <!--  处理中待审批页面 -->
    <div v-show="detailtwoshow" class='detailbox'>
            <div class='clearfix colbox'>
              <div style="float:left;">申请人:{{dsprow.begin_person_name}}</div>
              <div style="float:right;"><button class='search' @click.prevent @click="oppodetailtwo">返回</button></div>
            </div>
            <div class='clearfix colbox'>
              <div style="float:left;">
                <span>所在部门:{{dsprow.begin_dep_name}}</span>
<!--                <span>部门预算余额:{{dsprow.MAX_MONEY}}</span>-->
<!--                <span>申请总金额:{{dsprow.totalPrice}}</span>-->
              </div>
              <div style="float:right;">申请编号:{{dsprow.apply_no}}</div>
            </div>
            <div>
                  <div>物品明细:</div>
                  <div class='definite' v-for="(item,index) in dsprow.goods" :key="index">
                      <div class='article'>物品名称:{{item.goodname}}</div>
                      <div class='article'>物品数量:{{item.real_amount}}</div>
                      <div class='article'>物品规格:{{item.specs}}</div>
                      <div class='article'>存放地点:{{item.location}}</div>
                  </div>
            </div>
          <div class='timeleft'>
              期望交付时间:{{dsprow.plan_time}}
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
              <div style="float:left;">申请人:{{dfftworow.begin_person_name}}</div>
              <div style="float:right;"><button class='search' @click.prevent @click="oppogrant">返回</button></div>
            </div>
            <div class='clearfix colbox'>
              <div style="float:left;">
                <span>所在部门:{{dfftworow.begin_dep_name}}</span>
<!--                <span>部门预算余额:{{dfftworow.MAX_MONEY}}</span>-->
<!--                <span>申请总金额:{{dfftworow.totalPrice}}</span>-->
              </div>
              <div style="float:right;">申请编号:{{dfftworow.apply_no}}</div>
            </div>
            <div>
                  <div>物品明细:</div>
                  <div class='definite' v-for='(item,index) in dfftworow.goods' :key="index">
                      <div class='article'>物品名称:{{item.goodname}}</div>
                      <div class='article'>物品数量:{{item.real_amount}}</div>
                      <div class='article'>物品规格:{{item.specs}}</div>
<!--                      <div class='article'>物品单价:{{item.unitprice}}</div>-->
                      <div class='article'>存放地点:{{item.location}}</div>
                  </div>
            </div>
          <div class='timeleft'>
              期望交付时间:{{dfftworow.plan_time}}
          </div>
          <div>
              <div>
                申请原因:{{dfftworow.remark}}
              </div>
              <div class='explainbox'>
                  审批说明:
                <el-input type="textarea" class='explainwidth' v-model="remarktwo"></el-input>
              </div>
          </div>
<!--          <div  class='btnbox'>-->
<!--                <button class='search' @click.prevent @click="bohui(dfftworow.orderid)">驳回</button>-->
<!--                <button class='search' @click.prevent @click="fafang(dfftworow.orderid)">发放</button>-->
<!--          </div>-->
      </div>


      <!-- 已审批已发放页面 -->
      <div v-show="granttwoshow" class='detailbox'>
        <div class='clearfix colbox'>
          <div style="float:left;">申请人:{{yfftworow.beginpersonname}}</div>
          <div style="float:right;"><button class='search' @click.prevent @click="oppogranttwo">返回</button></div>
        </div>
        <div class='clearfix colbox'>
          <div style="float:left;">
            <span>所在部门:{{yfftworow.begin_dep_name}}</span>
<!--            <span>部门预算余额:{{yfftworow.MAX_MONEY}}</span>-->
<!--            <span>申请总金额:{{yfftworow.totalPrice}}</span>-->
          </div>
          <div style="float:right;">申请编号:{{yfftworow.apply_no}}</div>
        </div>
         <div>
              <div>物品明细:</div>
              <div class='definite' v-for='(item,index) in yfftworow.goods' :key="index">
                  <div class='article'>物品名称:{{item.goodname}}</div>
                  <div class='article'>物品数量:{{item.goodamount}}</div>
                  <div class='article'>物品规格:{{item.specs}}</div>
<!--                  <div class='article'>物品单价:{{item.unitprice}}</div>-->
                  <div class='article'>存放地点:{{item.location}}</div>
              </div>
        </div>
      <div class='timeleft'>
          期望交付时间:{{yfftworow.plan_time}}
      </div>
      <div>
          <div>
            申请原因:{{yfftworow.remark}}
          </div>
      </div>
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
    artname:[{}],
    applyshow:true,
    approveshow:false,
    neckshow:false,
    dsp:'',
    dff:'',
    dfftwo:'',
    yfftwo:'',
    dffrow:{},
    dsprow:{},
    dfftworow:{},
    yfftworow:{},
    goods:[],
    remark:'',
    remarktwo:'',
    pageIndex: 1,
    pageSize:10,
    pageSizes:[ 10, 20 , 30, 50, 100],
    total:0,
    pageIndex2: 1,
    pageSize2:10,
    pageSizes2:[ 10, 20 , 30, 50, 100],
    total2:0,
      queryform:{
      "beginPersonId": "E0311010002",		//申请人id
      "beginPersonName": "王二",			//申请人
      "beginPersonPhone": "",		//电话
      "beginDepId": "A000000000000003",		//部门编码
      "beginDepName": "办公室（党委办公室）",	//部门
      "planTime": "",			//采购时间
      "endPersonId": "E0311010267",		//审批人id
      "endPersonName": "王博",			//审批人
      "remark": "",			//采购理由
      "status": "1",				//提交计划，固定传1
      // "allMoney": "",			//计划采购总金额
      // "allaMount": "",				//计划采购总数量
      "title": "",			//采购申请
      "goods": [
      {
        // "expectCount": "10",
        "goodId": "",			//物品ID
        "goodNo": "",			//物品编号
        "goodName": "",		//物品名称
        "specs": "",			//规格
        "brand": "",			//品牌
        // "storeNo": "",			//仓库编号
        "realUnitprice": "",		//采购单价
        "realAmount": "",			//采购数量
        // "unitPrice":"",				//库存平均单价
        // "unit": "个",				//单位
        // "pictureUrl": "",	//图片地址
        // "expectCount": "10",
        "location": ""			//备注
      }
    ]
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
    amount(){
      var o=Number(0)
      for(var i=0;i<this.queryform.goods.length;i++){
        o+=Number(this.queryform.goods[i].realAmount)
      }
      return o
    },
    money(){
      var b=Number(0)
      for(var i=0;i<this.queryform.goods.length;i++){
        b+=Number(this.queryform.goods[i].realAmount*this.queryform.goods[i].realUnitprice)
      }
      return b
    }
  },
  created () {
    this.selectlist()
  },
  methods: {
    addartname(){
      this.queryform.goods.push({
        // goodid:'',
        // goodno:'',
        // goodname:'',
        // goodamount:'',
        specs:'',
        // real_amount:'',
        brand:'',
        storeno:'',
        realAmount:'',
        // price:'',
        // unitprice:'',
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
    },
    // 点击处理中页面
    approvechange(){
      this.neckshow=false
      this.applyshow=false
      this.approveshow=true
      this.$office({
        url:'Tstock.do/getListBySp',
        method:'post',
        data:{
          "pageNum": this.pageIndex,
          "pageSize":this.pageSize,
          "endPersonId": "E0311010267",	//审批人id
          "depId": ""			//部门，暂时传空
        }
      }).then(res=>{
        this.tableData=res.data.page.list
        this.total=res.data.page.totalRecord
        this.dsp=res.data.dsp
        // this.dff=res.data.dff
      })
    },
    //点击已审批页面
    neckchange(){
      this.applyshow=false
      this.approveshow=false
      this.neckshow=true
      this.$office({
        url:'Tstock.do/getSave',
        method:'post',
        data:{
          "pageNum": this.pageIndex2,
          "pageSize":this.pageSize2,			//每页数量
          "beginPersonId": "E0311010002",		//申请人id
          "status": "2"				//固定传2
        }
      }).then(res=>{
        // console.log(res)
        this.tableData2=res.data.list
        this.total2=res.data.totalRecord
        // this.yfftwo=res.data.yff
        // this.dfftwo=res.data.dff
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
        // console.log(`每页 ${val} 条`)
      },
    handleCurrentChange (val) {
        this.pageIndex=val
        this.approvechange()
        // console.log(`当前页: ${val}`)
      },
      handleSizeChange2 (val) {
        this.pageSize2 = val
        this.neckchange()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange2 (val) {
        this.pageIndex2=val
        this.neckchange()
        // console.log(`当前页: ${val}`)
      },
    handleNodeClick (data) {
      // console.log(data)
    },
    // 查看详情待发放按钮
    lookdff(row){
      this.applyforshow=false
      this.detailshow=true
      this.dffrow=row
    },
    lookdsp(row){
      // console.log(row)
      this.applyforshow=false
      this.detailtwoshow=true
      this.dsprow=row
    },
    lookdfftwo(row){
      // console.log(row)
      this.applyforshow=false
      this.grantshow=true
      this.dfftworow=row
    },
    lookyfftwo(row){
      // console.log(row)
      this.applyforshow=false
      this.granttwoshow=true
      this.yfftworow=row
    },
    // 下拉框数据
    selectlist(){
      this.$office({
        url:'Tstock.do/getData',
        method:'get',
      }).then(res=>{
        // console.log(res)
        this.goods=res.data.goods
      })
    },
    // 通过id查询物品详情
    getGoodInfo(id,index){
      this.$office({
        url:'TapplyOrder.do/getGoodInfo',
        method:'post',
        data:{
          'id':id
        }
      }).then(res=>{
        console.log(res)
        this.queryform.goods[index].goodId=res.data.id
        this.queryform.goods[index].goodNo=res.data.goodno
        this.queryform.goods[index].goodName=res.data.goodname
        this.queryform.goods[index].specs=res.data.specs
        this.queryform.goods[index].brand=res.data.brand
        this.queryform.goods[index].storeno=res.data.storeno
        this.queryform.goods[index].unitPrice=res.data.unitprice
        this.queryform.goods[index].unit=res.data.unit
        this.queryform.goods[index].pictureurl=res.data.pictureurl
        this.queryform.goods[index].expectcount=res.data.expectcount
        this.queryform.goods[index].zhuangtai=res.data.iswarn
      })
    },
    // 提交办公服务用品申请
    TapplyOrder(row){
      // console.log(row)
      var form={"allMoney":this.money,"allaMount":this.amount,...this.queryform}
      if(this.queryform.remark==''){
        this.$message('请补全信息')
        return
      }
      for(var i=0;i<this.queryform.goods.length;i++){
        if(this.queryform.goods[i].goodname==''){
          this.$message('请补全信息')
          return
        }
      }
      this.$office({
        url:'Tstock.do/purchaseApply',
        method:'post',
        data:form,
      }).then(res=>{
        if(res.data=='操作成功'){
          this.$message.success('提交申请成功')
          this.queryform={
          beginpersonid:'zs19990205',
          beginpersonname:'张三',
          demanddep:'办公室（党委办公室）',
          demanddepid:'A000000000000003',
          receiptdate:'',
          applyphone:'',
          remark:'',
          typeid:'003',
          dealUserId:'ls19920815',
          dealUserName:'李四',
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
          typeid:'003',
          dealUserId:'ls19920815',
          dealUserName:'李四',
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
      this.$office({
        url:'Tstock.do/updateBySp',
        method:'post',
        data:{
          "applyNo": row.apply_no,	//申请编号
          "approvelRemark": this.remark,		//驳回原因
          "status": "3"				//通过：2      //驳回：3
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
      // console.log(row)
      this.$office({
        url:'Tstock.do/updateBySp',
        method:'post',
        data:{
          "applyNo": row.apply_no,	//申请编号
          "approvelRemark": this.remark,		//驳回原因
          "status": "2"				//通过：2      //驳回：3
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
    // 驳回
    bohui(id){
      this.$office({
        url:'TapplyOrder.do/grantGoods',
        method:'post',
        data:{
          "orderId":id,
          "status":'2',
          "remark":this.remarktwo,
        }
      }).then(res=>{
        if(res.data=='驳回成功'){
          this.$message.success('驳回成功')
          this.grantshow=false
          this.applyforshow=true
          this.neckshow=true
          this.neckchange()
        }else{
          this.$message.error('驳回失败')
        }
        this.remarktwo=''
      })
    },
    // 发放
    fafang(id){
      this.$office({
        url:'TapplyOrder.do/grantGoods',
        method:'post',
        data:{
          "orderId":id,
          "status":'3',
          "remark":this.remarktwo,
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
        this.remarktwo=''
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
    height:200px;
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
    height:200px;
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
  .swidth{
    width: 200px;
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
.colbox span{
  margin: 0 40px 0 0;
}
.definite{
  width: 250px;
  height: 140px;
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
.marbtm{
  margin: 0 0 40px 0;
}
</style>
