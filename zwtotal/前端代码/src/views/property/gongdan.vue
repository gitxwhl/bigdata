<template>
  <div class='container'>
    <div class='meatitle'>维护工单</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='5' class='iptcol'>
            <el-form-item label="策略名称:">
              <el-input v-model="queryform.equipmentName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='5' class='iptcol'>
            <el-form-item  label="策略分类:">
              <!--              <el-input v-model="queryform.equipmentCode"></el-input>-->
              <el-select v-model="queryform.equipmentCode">
                <el-option v-for="item in strategytype" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span='5' class='iptcol'>
            <el-form-item label="工单状态:">
              <el-select v-model="queryform.status">
                <el-option v-for="item in workingcondition " :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span='6'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click='querylist'>查询</button>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <span  class="pic" @click="dialogrevVisible4 = true"><span style="color: white;font-size: 14px;margin-left: 13px">{{this.totalnumber}}</span></span>
          </el-col>
        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center" min-width="5%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center" min-width="15%" prop="workno" label="工单编号"> </el-table-column>
        <el-table-column align="center" min-width="15%" prop="policyname" label="策略名称"> </el-table-column>
        <el-table-column align="center" min-width="15%" prop="policy" label="策略分类"></el-table-column>
        <el-table-column align="center" min-width="15%"  prop="planningtime" label="计划时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="state" label="工单状态" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="generationtime" label="生成时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="15%"  prop="forcetime" label="生效时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="personnel" label="指派人员" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="receiving" label="接单人员" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%" label="维护" >
          <template slot-scope="scope">
            <span  class="binicon" v-if='scope.row.state==="未派单"' @click="details(scope.row.id)"  style="color: #95F204">详情</span>
            <span  class="binicon" v-if='scope.row.state==="已派单"' @click="details2(scope.row.id)"  style="color: #95F204">详情</span>
            <span  class="binicon" v-if='scope.row.state==="已完结"' @click="details3(scope.row.id)"  style="color: #95F204">详情</span>
            <el-dialog class="adddialog"  title="详情" :visible.sync="dialogrevVisible" @close="closeaddDialog('addform')">
              <el-form :model="addform" ref='addform' label-width="80px" class="addcategory">
                <el-row class='addrow' style="margin-top: -50px">
              <el-col :span='14' style="margin-left: 15px">
                工单编号:<span style="margin-left: 10px">{{particulars.workno}}</span>
              </el-col>
              <el-col :span='9'>
                策略名称:<span style="margin-left: 10px">{{particulars.policyname}}</span>
              </el-col>
            </el-row>
              <el-row class='addrow'>
                <el-col :span='14' style="margin-left: 15px">
                  策略分类:<span style="margin-left: 10px">{{particulars.policy}}</span>
                </el-col>
                <el-col :span='9'>
                  生成时间:<span style="margin-left: 10px">{{particulars.forcetime}}</span>
                </el-col>
              </el-row>
              <el-row class='addrow'>
                <el-col :span='5' style="margin-left: 15px">
                  任务列表:
                </el-col>
              </el-row>
              <el-row>
                <el-col :span='25' style="margin-left: 15px">
                  <p  style="margin-left: 30px">{{particulars.content}}</p>
                </el-col>
              </el-row>
              <el-row class='addrow'>
                <el-col :span='5' style="margin-left: 15px">
                  匹配资源:
                </el-col>

              </el-row>
              <el-row>
                <el-col :span='25' style="margin-left: 15px">
                  <p  style="margin-left: 30px">{{particulars.resources}}</p>
                </el-col>
              </el-row>
                <el-row>
                <el-col :span='12'>
                  <el-form-item style="margin-bottom:15px;"  label="计划时间:" prop="equipmentstatus">
                    <el-input style="width:220px;" v-model="addform.jihuashijian"  ></el-input>
                    <!--                      <span class="tab">*</span>-->
                  </el-form-item>
                </el-col>


              </el-row>
                <el-row>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="接单人:" prop="equipmentstatus">
                      <el-input style="width:220px;" v-model="addform.jiedanren"  ></el-input>
                      <!--                      <span class="tab">*</span>-->
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="接单成员:" prop="equipmentstatus">
                      <el-input style="width:220px;"  ></el-input>
                      <!--                      <span class="tab">*</span>-->
                    </el-form-item>
                  </el-col>

                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button  class="btnaddchild"  type="primary" @click="Notasingle(particulars.id)">提交</el-button>
              </div>

            </el-dialog>
            <el-dialog class="adddialog"  title="详情" :visible.sync="dialogrevVisible2" @close="closeaddDialog('addform')">
              <el-form :model="sendorders" ref="sendorders"  class="addcategory">
              <el-row class='addrow' style="margin-top: -50px">
                <el-col :span='7' style="margin-left: 15px">
                  工单编号:<span style="margin-left: 10px">{{sendorders.workno}}</span>
                </el-col>
                <el-col :span='7'>
                  策略名称:<span style="margin-left: 10px">{{sendorders.policyname}}</span>
                </el-col>
                <el-col :span='7'>
                  策略分类:<span style="margin-left: 10px">{{sendorders.policy}}</span>
                </el-col>
              </el-row>
              <el-row class='addrow' style="margin-top: -50px">
                <el-col :span='7' style="margin-left: 15px">
                  生成时间:<span style="margin-left: 10px">{{sendorders.generationtime}}</span>
                </el-col>
                <el-col :span='7'>
                  计划时间:<span style="margin-left: 10px">{{sendorders.planningtime}}</span>
                </el-col>
                <el-col :span='7'>
                  派单人:<span style="margin-left: 10px">{{sendorders.personnel}}</span>
                </el-col>
              </el-row>
              <el-row class='addrow' style="margin-top: -50px">
                <el-col :span='7' style="margin-left: 15px">
                  生效时间:<span style="margin-left: 10px">{{sendorders.forcetime}}</span>
                </el-col>
                <el-col :span='7'>
                  接单人:<span style="margin-left: 10px">{{sendorders.receiving}}</span>
                </el-col>
                <el-col :span='7'>
                  接单成员:<span style="margin-left: 10px">{{sendorders.receiving}}</span>
                </el-col>
              </el-row>
              <el-row class='addrow'>
                <el-col :span='5' style="margin-left: 15px">
                  任务列表:
                </el-col>
              </el-row>
              <el-row>
                <el-col :span='25' style="margin-left: 15px">
                  <p style="margin-left: 30px">{{sendorders.content}}</p>
                </el-col>
              </el-row>
              <el-row class='addrow'>
                <el-col :span='5' style="margin-left: 15px">
                  匹配资源:
                </el-col>
              </el-row>
              <el-row>
                <el-col :span='25' style="margin-left: 15px">
                  <p style="margin-left: 30px">{{sendorders.resources}}</p>
                </el-col>
              </el-row>
                <el-row style="margin-left: 20px;">
                  <el-col :span='15' class='iptcol'>
                    <el-form-item label="完成时间:">
                      <el-date-picker
                        v-model="sendorders.completiontime"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        type="date"
                        placeholder="请选择日期">
                      </el-date-picker>

                    </el-form-item>
                  </el-col>
                </el-row>
              <el-row class='addrow'>
                <el-col :span='5' style="margin-left: 15px">
                  作业记录:
                </el-col>
              </el-row>
              <el-row  style="margin-left: 30px">
                <el-col :span='9'>
                  <el-form-item label="" prop="sendorders">
                    <el-input class='name' style="width: 560px;" v-model="sendorders.operationrecord"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
                <el-row class='addrow'>
                  <el-col :span='5' style="margin-left: 15px">
                    缺陷识别:
                  </el-col>
                </el-row>
                <el-row style="margin-left: 30px">
                  <el-col :span='9'>
                    <el-form-item label="" prop="sendorders">
                      <el-input class='name' style="width: 560px;" v-model="sendorders.defectidentification"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button  class="btnaddchild"  type="primary" @click="Notasingle2(sendorders.id)">提交</el-button>
              </div>
            </el-dialog>
            <el-dialog class="adddialog"  title="详情" :visible.sync="dialogrevVisible3" @close="closeaddDialog('addform')">
              <el-form :model="sendorders" ref="sendorders"  class="addcategory">
                <el-row class='addrow' style="margin-top: -50px">
                  <el-col :span='7' style="margin-left: 15px">
                    工单编号:<span style="margin-left: 10px">{{sendorders.workno}}</span>
                  </el-col>
                  <el-col :span='7'>
                    策略名称:<span style="margin-left: 10px">{{sendorders.policyname}}</span>
                  </el-col>
                  <el-col :span='7'>
                    策略分类:<span style="margin-left: 10px">{{sendorders.policy}}</span>
                  </el-col>
                </el-row>
                <el-row class='addrow' style="margin-top: -50px">
                  <el-col :span='7' style="margin-left: 15px">
                    生成时间:<span style="margin-left: 10px">{{sendorders.generationtime}}</span>
                  </el-col>
                  <el-col :span='7'>
                    计划时间:<span style="margin-left: 10px">{{sendorders.planningtime}}</span>
                  </el-col>
                  <el-col :span='7'>
                    派单人:<span style="margin-left: 10px">{{sendorders.personnel}}</span>
                  </el-col>
                </el-row>
                <el-row class='addrow' style="margin-top: -50px">
                  <el-col :span='7' style="margin-left: 15px">
                    生效时间:<span style="margin-left: 10px">{{sendorders.forcetime}}</span>
                  </el-col>
                  <el-col :span='7'>
                    接单人:<span style="margin-left: 10px">{{sendorders.receiving}}</span>
                  </el-col>
                  <el-col :span='7'>
                    接单成员:<span style="margin-left: 10px">{{sendorders.receiving}}</span>
                  </el-col>
                </el-row>
                <el-row class='addrow'>
                  <el-col :span='5' style="margin-left: 15px">
                    任务列表:
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='25' style="margin-left: 15px">
                    <p style="margin-left: 30px">{{sendorders.content}}</p>
                  </el-col>
                </el-row>
                <el-row class='addrow'>
                <el-col :span='5' style="margin-left: 15px">
                  匹配资源:
                </el-col>
              </el-row>
                <el-row>
                  <el-col :span='25' style="margin-left: 15px">
                    <p style="margin-left: 30px">{{sendorders.resources}}</p>
                  </el-col>
                </el-row>
                <el-row class='addrow'>
                  <el-col :span='5' style="margin-left: 15px">
                    完成情况:
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='25' style="margin-left: 15px">
                    <p style="margin-left: 30px">未有字段</p>
                  </el-col>
                </el-row>
                <el-row class='addrow'>
                  <el-col :span='5' style="margin-left: 15px">
                    缺陷识别:
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='25' style="margin-left: 15px">
                    <p style="margin-left: 30px">{{sendorders.defectidentification}}</p>
                  </el-col>
                </el-row>
<!--                <el-row style="margin-left: 20px;">-->
<!--                  <el-col :span='15' class='iptcol'>-->
<!--                    <el-form-item label="完成时间:">-->
<!--                      <el-date-picker-->
<!--                        v-model="sendorders.aa"-->
<!--                        format="yyyy-MM-dd"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                        type="date"-->
<!--                        placeholder="请选择日期">-->
<!--                      </el-date-picker>-->

<!--                    </el-form-item>-->
<!--                  </el-col>-->
<!--                </el-row>-->
<!--                <el-row class='addrow'>-->
<!--                  <el-col :span='5'style="margin-left: 15px">-->
<!--                    作业记录:-->
<!--                  </el-col>-->
<!--                </el-row>-->
<!--                <el-row  style="margin-left: 30px">-->
<!--                  <el-col :span='9'>-->
<!--                    <el-form-item label="" prop="sendorders">-->
<!--                      <el-input class='name' style="width: 560px;" v-model="sendorders.operationrecord"></el-input>-->
<!--                    </el-form-item>-->
<!--                  </el-col>-->
<!--                </el-row>-->
                <el-row class='addrow' >
                  <el-col :span='7' style="margin-left: 15px">
                    完成时间:<span style="margin-left: 10px">未有字段</span>
                  </el-col>
                  <el-col :span='7'>
                    提交人:<span style="margin-left: 10px">未有字段</span>
                  </el-col>
                  <!--                  <el-col :span='7'>-->
                  <!--                    接单成员:<span style="margin-left: 10px">{{sendorders.receiving}}</span>-->
                  <!--                  </el-col>-->
                </el-row>
              </el-form>
            </el-dialog>

          </template>

        </el-table-column>
      </el-table>
      <el-dialog class="adddialog"   title="超期订单" :visible.sync="dialogrevVisible4" @close="closeaddDialog('addform')">
        <el-table stripe ref="multipleTable" :data="tableData2" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                  height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
          <el-table-column  min-width="5%" type="selection" width="55"> </el-table-column>
          <el-table-column  min-width="15%" prop="workno" label="工单编号" align="center"> </el-table-column>
          <el-table-column  min-width="15%" prop="policyname" label="策略名称" align="center"> </el-table-column>
          <el-table-column  min-width="15%" prop="policy" label="策略分类" align="center"></el-table-column>
          <el-table-column  min-width="15%"  prop="planningtime" label="计划时间" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column  min-width="20%"  prop="content" label="任务内容" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column  min-width="25%" label="操作" align="center" >
            <template slot-scope="scope">
              <span  class="binicon" v-if="scope.row.armstatus == '未取消'"  style="color:#6E9602" @click="regen(scope.row.id)">重新生成</span>
              <span  class="editicon" v-if="scope.row.armstatus == '未取消'" style="color:#F59A23" @click="Cancelalarm(scope.row.id)"> 取消告警</span>
              <span  class="binicon" v-if="scope.row.armstatus == '已取消'"  style="color:white">告警已取消</span>
            </template>
          </el-table-column>
        </el-table>
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

        dialogrevVisible: false,//未派单
        dialogrevVisible2: false,//已派单
        dialogrevVisible3: false,//已完结
        dialogrevVisible4:false,
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        totalnumber:'',
        restaurantCode:'',//树状图id
        mealType:[],
        queryform: {
          equipmentName: '',
          equipmentCode: '',
          status:''
        },
        strategytype:[
          // {
          //   "text": "临时",
          //   "id": "1"
          // },
          // {
          //   "text": "专项",
          //   "id": "2"
          // }

        ],//策略分类
        addform:{
          jihuashijian:'',
          jiedanren:'',
          chengyuan:'',
        },
        workingcondition:[],//工单状态
        particulars:[],//未派单
        sendorders:{},//已派单

        restaurant:[],//运维餐厅
        // foodcategory: [
        //   { value: '猪肉', text: '猪肉' },
        //   { value: '牛肉', text: '牛肉' },
        //   { value: '鸡肉', text: '鸡肉' }
        // ],
        tableData: [],
        tableData2: [],//超期订单列表
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
      this.getBeyondOrder()//超期订单列表
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize=val
        this.pagelist()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.pagelist()
        // console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.pagelist()
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
          // console.log(res)
          this.data=res.data
        })
      },
      // 下拉框数据
      selectlist(){
        this.$instance({
          url:'StWymaintainorder.do/getWorkState',
          method:'post'
        }).then(res=>{
          // console.log(res)
          this.workingcondition=res.data.policyAndWorkState
          // this.restaurant=res.data.restaurant
        })
        this.$instance({
          url:'StEquipmentstrategy.do/getPolicy',
          method:'post'
        }).then(res=>{
          // console.log(res)
          this.strategytype=res.data.policy
          // this.restaurant=res.data.restaurant
        })
      },
      // 设备列表
      pagelist(){
        this.$instance({
          url:'StWymaintainorder.do/getMaintainorder',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "policyname":this.queryform.equipmentName, //策略名称
            "policy":this.queryform.equipmentCode, //策略分类
            "state":this.queryform.status, //工单状态
            "company":this.restaurantCode,// 公司外键
          }
        }).then(res=>{
          // console.log(res)
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 查询列表
      querylist(){
        this.pagelist()
        // this.queryform.equipmentName=''
        // this.queryform.equipmentCode=''
      },
      // 详情
      details(id){
        this.dialogrevVisible = true
        this.$instance({
          url:'StWymaintainorder.do/getMaintainorderDetails',
          method:'post',
          data:{
            id:id
          }
        }).then(res=>{
          this.particulars=res.data[0]
          // console.log(this.particulars)

        })
        // console.log("未派单")
      },
      details2(id){
        this.dialogrevVisible2 = true
        this.$instance({
          url:'StWymaintainorder.do/getMaintainorderDetails',
          method:'post',
          data:{
            id:id
          }
        }).then(res=>{
          this.sendorders=res.data[0]
          // console.log(this.particulars)

        })
      },
      details3(id){
        this.dialogrevVisible3 = true
        this.$instance({
          url:'StWymaintainorder.do/getMaintainorderDetails',
          method:'post',
          data:{
            id:id
          }
        }).then(res=>{
          this.sendorders=res.data[0]
          // console.log(res.data)

        })
        // console.log("已完结")
      },
      //超期订单查询
      getBeyondOrder(){
        this.$instance({
          url:'StWymaintainorder.do/getBeyondOrder',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
          }
        }).then(res=>{
          // console.log(res.data.totalRecord)
          this.totalnumber=res.data.totalRecord
          this.tableData2=res.data.list
        })
      },
      //取消告警
      Cancelalarm(id){
        this.$instance({
          url:'StWymaintainorder.do/updateOrderAlarm',
          method:'post',
          data:{
            id:id
          }
        }).then(res=>{
          this.$message.success('取消成功')
          this.getBeyondOrder()

        })
      },
      //重新生成
      regen(id){
       this.$router.push({path: '/property/koriyasu', query: { id: 10 }})
      },
      //未派单提交
      Notasingle(id){
        this.$instance({
          url:'StWymaintainorder.do/updateSingle',
          method:'post',
          data:{
            "planningtime":this.addform.jihuashijian,//计划时间
            "receiving":this.addform.jiedanren,//接单人
            "personnel":'',//接单成员
            "id":id

          }
        }).then(res=>{
          // this.sendorders=res.data[0]
          // console.log(this.particulars)
          this.$message.success('提交成功')
          this.dialogrevVisible = false
          this.pagelist()

        })
        // console.log(id)
      },
      //已派单提交
      Notasingle2(id){
        // console.log(id)
        this.$instance({
          url:'StWymaintainorder.do/updateSingle',
          method:'post',
          data:{
            "operationrecord":this.sendorders.operationrecord,//作业记录
            "defectidentification":this.sendorders.defectidentification,//缺陷识别
            "completiontime":this.sendorders.completiontime,//完成时间
            "id":id

          }
        }).then(res=>{
          // this.sendorders=res.data[0]
          // console.log(this.particulars)
          this.$message.success('提交成功')
          this.dialogrevVisible2 = false
          this.pagelist()

        })
        // console.log(id)
      },
      //关闭事件
      closeaddDialog(queryform){
        // this.$refs[queryform].resetFields()
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
  }
  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px;
    color: rgb(255,255,255);
  }
  .search,.increase{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 90px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
  }
  .search,.increase:hover{
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
  .binicon{
    width:50px;
    height: 30px;
    line-height:30px;
    /*float: left;*/
  }
  .binicon img{
    width:18px;
    height:16px;
    vertical-align: middle;
  }
  .editicon{
    width:80px;
    height: 20px;
    line-height:30px;
    float: right;
    /*margin-right:50px ;*/
  }
  .pic{
    display: inline-block;
    width: 40px;
    height: 40px;
    background: url("../../assets/u4141.svg");
    background-size: 100% 100%;
  }
  .editicon img{
    width:18px;
    height:16px;
    vertical-align: middle;
  }
  .binicon img:hover{
    cursor: pointer;
  }
  .editicon img:hover{
    cursor: pointer;
  }
  /deep/.el-dialog{
    background: transparent;
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
    color:rgb(255,255,255);
    background:transparent;
    padding-bottom:2px;
    padding:0 20px 0 10px;
    margin-bottom: 80px;
    margin-top: 60px;
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
  .btnaddchild,.btnedit{
    background-color:rgb(32,47,66);
    width: 60px;
    height:30px;
    line-height:0;
    padding:10px 0 10px 5px;
    font-size: 12px;
  }
  /deep/.btnaddchild span{
    line-height:0;
    margin-right:5px ;
  }
  .addcategory /deep/.el-textarea__inner{
    background: black;
    border:1px solid rgb(52,57,82);

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
  .adddialog .point{
    width: 240px;
    height:100px;
    position: absolute;
    top:100px;
    right:40px;
  }
  .revdialog .point{
    width: 240px;
    height:100px;
    line-height:30px;
    position: absolute;
    top:100px;
    right:40px;
  }
  .tab{
    font-size: 18px;
    color:red;
    padding-left:16px;
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
    width: 200px;
  }
  .name{
    width: 160px;
  }
  .code{
    width: 90px;
  }
  .number{
    width: 90px;
  }
  .restaurant{
    width: 200px;
  }
  .count{
    width: 100px;
  }
  // .addrow{
  //   /*margin-bottom:20px ;*/
  // }
  /deep/.el-input__icon{
    width: 25px;
    line-height: 40px;
  }
</style>
