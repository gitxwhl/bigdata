<template>
    <div class='container'>
        <div class='meatitle'>异地就餐申请</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
        <div class="bigbox">
            <div class="applybox" @click="applychange">申请(xx)</div>
            <div class="exambox" @click="applylist">审批(xx)</div>
            <div class="dealtbox" @click="dealtchange(null)">待办（xx）/全部（xxx）</div>
        </div>
        <div v-if="applyshow">
                <el-row class="iptrow" v-show='usersearchshow'>
                <el-col :span='8' class='iptcol'>
                        <el-input v-model="usersearch" placeholder="请输入审批人/申请日期/出差地点"></el-input>
                </el-col>
                <el-col :span='3' class='iptcol'>
                     <button class='search' @click.prevent @click="checkkeep">检索</button>
                </el-col>
            </el-row>

            <!-- 申请 -->
            <div class='applykeep' v-show='applykeepgage'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
            <div class="applypeople">申请人：{{yhname}} </div>            
            <div class="applypeople">申请公司：{{cname}} </div>
            <div class="applypeople">申请部门：{{dname}} </div>
            <div class="applypeople">
                   <el-form-item label="联系方式：">
                    <el-col :span="7">
                      <el-input  v-model="mobile" placeholder="mobile"></el-input>
                    </el-col>
                  </el-form-item>

                 </div>
            
            <el-row class="iptrow">
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="申请时间:" >
                         <el-date-picker
                         value-format="yyyy-MM-dd-hh-mm-ss"
                            v-model="queryform.beginTime"
                            type="datetime"
                            placeholder="请选择日期">
                            </el-date-picker>
                            <!-- <span class="fhbox">-</span> -->
                    </el-form-item>
                </el-col>
                <!-- <el-col :span='6' class='iptcol'>
                    <el-form-item>
                         <el-date-picker
                         value-format="yyyy-MM-dd-hh-mm-ss"
                            v-model="queryform.endTime"
                            type="datetime"
                            placeholder="请选择日期">
                            </el-date-picker>
                    </el-form-item>
                </el-col> -->
            </el-row>
          
            <el-row class='iptrow'>
              <el-col :span='5'>
                <el-form-item label="公司:">
                  <el-select  v-model="queryform.companyData" value-key="id" >
                    <el-option  @click.native="gongsiSelect(item.id)"  v-for="item in company" :key="item.id" :label="item.dname" :value="item">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span='5'>
                <el-form-item label="部门:">
                  <el-select v-model="queryform.deparmentName">
                    <el-option @click.native="gongsiperson(item.id)" v-for="item in department" :key="item.id" :label="item.dname" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

             <el-col :span='5'>
              <el-form-item label="选择餐厅:">
                <el-select v-model="queryform.restaurantObj" value-key="id">
                  <el-option v-for="item in stOperationList" :key="item.id" :label="item.name" :value="item">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

             </el-row>
             <el-row class='iptrow'>
             <el-col class="restaurant" :span='24'>
                <el-form-item label="说明:" prop="restaurant">
                   <el-input type="textarea" v-model="queryform.remarks"></el-input>
                 </el-form-item>
                 </el-col>
             </el-row>

         <el-row class='iptrow'>
               <el-col class="restaurant" :span='24'>
             <div class="approvepeople">
               <el-form-item label="接待人:">
                <el-select v-model="queryform.receptionistObj" value-key="ACCOUNT">
                     <el-option v-for="item in approvalBy" :key="item.ACCOUNT" :label="item.name" :value="item">
                    </el-option>
                </el-select>
                 </el-form-item>
               </div>
               </el-col>
             </el-row>
<!--            <el-row class="iptrow">-->
<!--                <el-col :span='14' class='iptcol'>-->
<!--                    <el-form-item label="接待人:">-->
<!--                       <el-input class="place" v-model="queryform.receptionPersonName"></el-input>-->
<!--                    </el-form-item>-->
<!--                </el-col>-->
<!--            </el-row>-->

              </el-form>
            </div>




            <!-- 查看保存页面 -->
          <div class='applykeep' v-show="lookkeeppage" v-for='item in keeplist' :key='item.id'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
            <div class="applypeople">申请人:{{item.applicant}}</div>
            <el-row class="iptrow">
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="申请时间:">
                         <el-date-picker
                         value-format="yyyy-MM-dd"
                            v-model="item.beginTime"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker><span class="fhbox">-</span>
                    </el-form-item>
                </el-col>
                <el-col :span='6' class='iptcol'>
                    <el-form-item>
                         <el-date-picker
                         value-format="yyyy-MM-dd"
                            v-model="item.endTime"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row class="iptrow">
                <el-col :span='14' class='iptcol'>
                    <el-form-item label="出差地点:">
                       <el-input class="place" v-model="item.place"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <!-- <el-row class='iptrow'>
             <el-col :span='12'>
                <el-form-item label="选择餐厅:" prop="restaurant">
                <el-select v-model="item.delFlag">
                     <el-option v-for="i in stOperationList" :key="i.RestaurantCode" :label="i.RestaurantName" :value="i.RestaurantCode">
                    </el-option>
                </el-select>
                 </el-form-item>
                 </el-col>
             </el-row> -->
             <el-row class='iptrow'>
             <el-col class="restaurant" :span='24'>
                <el-form-item label="说明:" prop="restaurant">
                   <el-input type="textarea" v-model="item.remarks"></el-input>
                 </el-form-item>
                 </el-col>
             </el-row>
             <el-row class='iptrow'>
               <el-col class="restaurant" :span='24'>
             <div class="approvepeople">
               <el-form-item label="审批人:" prop="restaurant">
                <el-select v-model="item.state">
                     <el-option v-for="i in approvalBy" :key="i.id" :label="i.name" :value="i.id">
                    </el-option>
                </el-select>
                 </el-form-item>
               </div>
               </el-col>
               <el-col :span="5" :offset="18" style="margin-bottom:5px;">
                <button class='search' @click.prevent @click='keepbtnkeep(item.id,item.beginTime,item.endTime,
                item.place,item.delFlag,item.remarks,item.state)'>保存</button>
                <button class='search' v-show="returnshow" @click.prevent @click="retrunbtn">返回</button>
                <button class='search' @click.prevent @click="addApplicationkeep(item.id,item.beginTime,item.endTime,
                item.place,item.delFlag,item.remarks,item.state)">申请</button>
              </el-col>
             </el-row>
              </el-form>
        </div>
                  <div style="float: right; margin-top: 10px;height:'15%'">
              <el-pagination
              v-show="paginationshow"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageIndex"
              :page-sizes="pageSizes"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
          </div>
              <el-col :span="5" :offset="18" style="margin-bottom:20px;" v-show="checkkeepshow">
                <!-- <button class='search' @click.prevent @click='keepbtn'>保存</button>
                <button class='search'  @click.prevent @click="checkkeep">查看保存</button> -->
                <button class='search' @click.prevent @click="addApplication">申请</button>

              </el-col>
              <el-col :span="5" :offset="20" style="margin-bottom:20px;" v-show="uncheckkeepshow">
                  <button class='search' v-show="returnshow" @click.prevent @click="retrunbtn">返回</button>
              </el-col>
    </div>


    <!-- 审批 -->
    <div v-if="examshow">
                <el-row class="iptrow">
                <el-col :span='8' class='iptcol'>
                        <el-input v-model="applysearch" placeholder="请输入申请人/申请日期/出差地点"></el-input>
                </el-col>
                <el-col :span='3' class='iptcol'>
                     <button class='search' @click.prevent @click="applylist">检索</button>
                </el-col>
            </el-row>
          <div v-for='item in approvelist' :key='index'>

            <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
            <div class="applypeople">申请人:{{item.userName}}</div>
            <div class="applypeople">申请公司：{{item.orgName}}</div>
            <div class="applypeople">联系方式：{{item.userPhone}}</div>
            <div class="applypeople" >申请时间：{{timetmpAll}}</div>
            <div class="applypeople">选择餐厅</div>
            <div class="applypeople">说明：{{item.remark}}</div>
            <div class="applypeople">接待人:{{item.receptionPersonName}}</div>

             <el-row class='iptrow'>
             <el-col class="restaurant" :span='24'>
                <el-form-item label="审批说明:" prop="restaurant">
                   <el-input type="textarea" v-model="reason"></el-input>
                 </el-form-item>
                 </el-col>
             </el-row>
             <el-row class='iptrow'>
                <el-col :span="4" :offset="20">
              <button class='search' @click.prevent @click='approvecircs(item.id,item.staus)'>退回</button>
              <button class='search' @click.prevent @click='approvecircs(item.id,item.staus)'>同意</button>
                </el-col>
             </el-row>
            </el-form>
          </div>



      <div style="float: right; margin-top: 10px;height:15%">
        <el-pagination
      @size-change="handleSizeChangesp"
      @current-change="handleCurrentChangesp"
      :current-page="pageIndexsp"
      :page-sizes="pageSizessp"
      :page-size="pageSizesp"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalsp">
    </el-pagination>
      </div>
        </div>


        <!-- 待办 -->
        <div v-if="dealtshow">
          <el-row class="iptrow">
                <el-col :span='8' class='iptcol'>
                        <el-input v-model="backlog" placeholder="请输入申请人/申请日期/出差地点"></el-input>
                </el-col>
                <el-col :span='3' class='iptcol'>
                     <button class='search' @click.prevent v-model="keyWord" @click="dealtchange(state)">检索</button>
                </el-col>
          </el-row>
          <div class='text'>
            <!-- <div>本人已申请:{{pagexinxi.dsp}}条</div>
            <div>本人已审批:{{pagexinxi.sp}}条</div> -->
          </div>
          <div class='line'>
            <div v-show="wholeshow">
                <button class='searchtwo' @click.prevent @click="dealtchange(1)">待审批({{pagexinxi.dsp}})</button>
                <button class='searchtwo' @click.prevent @click="dealtchange(0)">待提交({{pagexinxi.dtj}})</button>
                <button class='searchtwo' @click.prevent @click="wholebtn(null)">全部({{pagexinxi.all}})</button>
            </div>
              <div v-show="dealtdoshow">
                <button class='searchtwo' @click.prevent @click="dealtchange(2)">驳回({{pagexinxi.bh}})</button>
                <button class='searchtwo' @click.prevent @click="dealtchange(3)">审批({{pagexinxi.sp}})</button>
                <button class='searchtwo' @click.prevent @click="dealtdobtn(4)">待办({{pagexinxi.db}})</button>
            </div>
          </div>
          <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">

                <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="10%" prop="user_name" label="申请人"></el-table-column>
                <el-table-column align="center"  min-width="10%" prop="endTime" label="申请公司"></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="dept_name" label="申请部门" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="place" label="联系方式" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="c_time" label="申请时间" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="reception_person_name" label="接待人" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="apply_org_name" label="接待人公司" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="place" label="接待人部门" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="AuditUserName" label="审核人" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="AuditStatus"  label="审核状态" show-overflow-tooltip> 
                     <template slot-scope="scope">
                       {{['未提交','待审','通过','驳回','撤销'][scope.row.AuditStatus]}}
                    </template>
                </el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="place" label="说明" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="restaurant" label="选择餐厅" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="state" label="当前状态" show-overflow-tooltip></el-table-column>
        <!-- <el-table-column align="center"  min-width="10%" label="详情" show-overflow-tooltip></el-table-column> -->
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
          <!-- 总条目数 -->
        </el-pagination>
      </div>
        </div>
    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {

      usersearchshow:false,
      paginationshow:false,
      applykeepgage:true,
      lookkeeppage:false,
      checkkeepshow:true,
      uncheckkeepshow:false,
      returnshow:false,
      applyshow:true,
      examshow:false,
      dealtshow:false,
      approvebox:false,
      wholeshow:true,
      dealtdoshow:false,
      clickstate:0,
      approveremark:'',
      yhname:'',
      dname:'',
      cname:'',
      cid:'',
      bmid:'',
      mobile:'',
      keyWord:'',//关键字搜索
      applysearch:'',
      usersearch:'',
      backlog:'',
      state:'',
      reason:'',
      pagexinxi:{},
      approvelist:[],
      keeplist:[],
      approvalBy:[],//接待人
      stOperationList:[],//餐厅
      company:[],//公司
      companyId:'',//公司id
      department:[],//部门
      tableData:[],//代办    
      value1:'',
      value2:'',
      value3:'',
      pageIndex: 1,
      pageSize:1,
      pageSizes:[ 1, 2 , 3, 5, 10],
      total:0,//总条目数
      pageIndex2: 1,//当前页数
      pageSize2:5,//每页显示条目个数，支持 .sync 修饰符
      pageSizes2:[ 10, 20 , 30, 50, 100],//每页显示个数选择器的选项设置
      total2:0,
      pageIndexsp: 1,
      pageSizesp:1,
      pageSizessp:[ 1, 2 , 3, 5, 10],
      totalsp:0,
      restaurantCode:'',//树状图id
      mealType:[],//餐别
      queryform: {    
        applicant:'',
        beginTime:'',
        endTime:'',
        restaurant:'',
        remarks:'',//说明
        // companyData:'',
        approvedBy:'',
        receptionPersonName:'',
        companyData:'',//接待人公司
        deparmentName:'',//接待人部门
        restaurantName:'',//选择餐厅
        timetmpAll:'',//时间
        receptionistObj:'' //接待人
      },
      data: [],
  
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    this.treelist()
    this.applicantbm()
    this.applicantgs()
    this.gongsi()
  },

// filters:{
//       toFixed:function(data){
//         var arr=[];
//         data.split(',').map(
//               function(item){
//                 if(item=='0'){
//                     arr.push('未提交')
//                 }else if(item=='1'){
//                     arr.push('待审')
//                 }else if(item=='2'){
//                     arr.push('通过')
//                 }else if(item=='3'){
//                     arr.push('驳回')
//                 }else if(item=='4'){
//                     arr.push('撤销')
//                 }
//               }  
//         )
//         return arr.join();
//       }
//     },

  methods: {
    applychange(){
        this.applyshow=true
        this.examshow=false
        this.dealtshow=false
        this.approvebox=false
    },
    // examchange(){
    //     this.applyshow=false
    //     this.examshow=true
    //     this.dealtshow=false
    // },

  

    // 获取申请人信息及公司
    applicantgs(){
          this.$axios({
              url:'common.do/findCompanyId',
              method:'post',
              data:{
              "account":'chenl3',
              }
            }).then(res=>{
              console.log(res)
              let arr =res.data.data
              let  ar=arr.map(item=>item.name) 
              this.name= ar.join() 
              let  ar1=arr.map(item=>item.department)
              let  ar2=ar1.map(item=>item.dname) 
              this.cname=ar2.join()
              let ar3 = ar1.map(item=>item.id) 
              //公司id
              this.cid = ar3.join()

            })
        },
        //获取申请人信息及部门
    applicantbm(){
      this.$axios({
          url:'common.do/findDeparmentId',
          method:'post',
          data:{
          "account":'chenl3',
          }
        }).then(res=>{
          let arr =res.data.data
          let  mobile=arr.map(item=>item.mobile) 
          this.mobile = mobile.join()
          let  ar=arr.map(item=>item.name) 
          this.yhname= ar.join()   
          let  ar1=arr.map(item=>item.department)
          let  ar2=ar1.map(item=>item.dname) 
          this.dname = ar2.join()   
          let ar3 = ar1.map(item=>item.id) 
              //部门id
              this.bmid = ar3.join()
          
        })
    },

    //邀访人公司
        gongsi(){
          this.$axios({
            url:'common.do/findCompany',
            method:'post', 
            data:{}    
          }).then(res=>{
            this.company = res.data.data 

          })
        },

        // 选择公司获取餐厅新增
        gongsiSelect(ss){
          this.$axios({
              url:'common.do/getRestaurantList/' + ss,
              method:'get',       
          }).then(res=>{
            //   this.department ='',
            // this.department = res.data.data
            this.stOperationList = res.data.data
           
          })
          this.$axios({
              url:'common.do/finddeparmentIds/' + ss,
              method:'get',       
          }).then(res1=>{
           
              this.department ='',
            this.department = res1.data.data  
          })
        },
        //获取根据部门id获取人员信息
        gongsiperson(pp){
           this.$axios({
              url:'common.do/getPersonList/' + pp,
              method:'get',       
          }).then(res2=>{
            this.approvalBy= res2.data.data
            console.log(approvalBy)

          })
        },

// 添加申请
    addApplication(){
      // if(this.queryform.beginTime==''||this.queryform.endTime==''||this.queryform.place==''||
      // this.queryform.restaurant==''||this.queryform.remarks==''|| this.queryform.approvedBy==this.queryform.receptionPersonName){
      //   this.$message('请完整输入申请信息')
      //   return
      // }
      console.log(this.queryform.receptionistObj.ACCOUNT)
          alert(this.cid)
          console.log(this.yhname)//申请人
          console.log(this.cname)//申请公司
          console.log(this.cid)//申请公司
          console.log(this.dname)//申请部门
          console.log(this.mobile)//联系方式
          console.log(this.queryform.beginTime)//申请时间
          console.log(this.queryform.companyData.dname)//邀访人公司
          console.log(this.queryform.companyData.id)//邀访人公司id
          console.log(this.queryform.deparmentName)//邀访人部门
          console.log(this.queryform.restaurantName)//邀访人餐厅
          console.log(this.queryform.receptionPersonId)//接待人
          console.log(this.queryform.remarks)//说明
          this.$axios({
                  url:'appTApplyCanteen.do/applyRemoteDining',
                  method:'post',
                  data:{
                    "userId":'chenl3',//申请人账号
                    "userName":this.yhname,//申请人姓名
                    "userPhone":this.mobile,//联系方式
                    "orgName":this.cname,//申请公司
                    "orgId":this.cid,//用户所在公司id
                    "deptName":this.dname,//申请部门
                    "deptId":this.bmid,           //用户所在部门
                    "applyOrgId":this.queryform.companyData.id,//邀访人公司id
                    "applyOrgName":this.queryform.companyData.dname,
                    "receptionPersonName":this.queryform.receptionistObj.name,//邀访人名称
                    "receptionPersonId":this.queryform.receptionistObj.ACCOUNT,//接待人
                    // "receptionPersonPhone":13111980625,     
                    "applyRestaurantId":"this.queryform.restaurantName",//邀访人餐厅
                    "applyRestaurantName":"applyRestaurantName1",
                    "remark":this.queryform.remarks//说明
                    // "startTime":"2020-11-12 14:36:21" 
                  }
                }).then(res=>{
                    console.log(res)
                  if(res.data.data=="申请成功"){
                    this.$message.success('申请成功')
                    window.setTimeout(function(){
                        // location.reload()
                      },1500)
                  }else{
                    this.$message.error('申请失败')
                    window.setTimeout(function(){
                      // location.reload()
                      },1500)
                  }
                })
    },


    dealtchange(state){
        this.approvebox=false
        this.applyshow=false
        this.examshow=false
        this.dealtshow=true
        this.state=state
        this.$axios({
          url:'appTApplyCanteen.do/getAllInfo',
          method:'post',
          data: {
          "userId": "E222222222229",  
          "status":3,		
          "keyWord":'',
          "index":this.pageIndex2,  
          "pageSize":this.pageSize2
          }
          // data:{
          // "pageNum":this.pageIndex2,
          // "pageSize":this.pageSize2,
          // // "applicant":'1',
          // // "restaurant":this.restaurantCode,
          // // "msg":this.backlog,
          // // "state":this.state
          //   "userId":"E222222222227",
          //   // "pageIndex":"1",
          //   // "pageSize":"10",
          //   "status":"1"
          // }
        }).then(res=>{
        console.log(res)
          this.tableData = res.data.data.list
          this.total2=res.data.data.totalRecord
        })
    },
    handleSizeChange (val) {
      this.pageSize=val
      this.checkkeep()
      // console.log(`每页 ${val} 条`)
    },
    handleSizeChangesp (val) {
      this.pageSizesp=val
      this.applylist()
      // console.log(`每页 ${val} 条`)
    },
   
    handleCurrentChange (val) {
      this.pageIndex=val
      this.checkkeep()
      // console.log(`当前页: ${val}`)
    },
    handleCurrentChangesp (val) {
      this.pageIndexsp=val
      this.applylist()
      // console.log(`当前页数: ${val}`)
    },

     //代办 currentPage 改变时会触发
    handleCurrentChange2 (val) {
      this.pageIndex2=val
      this.dealtchange(this.state)
      // console.log(`当前页: ${val}`)
    },
     //代办 pageSize 改变时会触发
    handleSizeChange2 (val) {
      this.pageSize2=val
      this.dealtchange(this.state)
      // console.log(`每页 ${val} 条`)
    },
    handleNodeClick (data) {
      this.restaurantCode=data.id
      if(this.clickstate==1){
        this.checkkeep()
      }else if(this.approvebox==true){
        this.applylist()
      }else  if(this.dealtshow==true){
        this.dealtchange(this.state)
      }

    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    
    // 代办：全部按钮
    wholebtn(state){
      this.wholeshow=false
      this.dealtdoshow=true
      this.dealtchange(this.state)
    },
    dealtdobtn(state){
      this.wholeshow=true
      this.dealtdoshow=false
      this.dealtchange(this.state)
    },
// 申请人查询保存的申请
    checkkeep(){
      if(this.restaurantCode==''){
        this.$message('请选择餐厅')
        return
      }
      this.$axios({
        url:'StLocaleat.do/getSave',
        method:'post',
        data:{
          "pageNum":this.pageIndex,
          "pageSize":this.pageSize,
          "applicant":1,
          "restaurant":this.restaurantCode,
          "msg":this.usersearch
        }
      }).then(res=>{
        this.keeplist=res.data.list
        this.clickstate=1
        if(this.keeplist.length==0){
          this.uncheckkeepshow=true
        }else{
          this.uncheckkeepshow=false
        }
          this.total=res.data.totalRecord
          this.usersearchshow=true,
          this.applykeepgage=false,
          this.lookkeeppage=true,
          this.checkkeepshow=false,
          this.returnshow=true,
          this.paginationshow=true

      })



    },
    // 返回按钮
    retrunbtn(){
      this.clickstate=0
      this.usersearchshow=false,
      this.applykeepgage=true,
      this.lookkeeppage=false,
      this.checkkeepshow=true,
      this.returnshow=false,
      this.paginationshow=false
    },


     // 树状图接口
    treelist(){
      this.$axios({
        url:'StOperationrestaurant.do/getStOperationList',
        method:'get'
      }).then(res=>{
        this.data=res.data
      })
    },
    


    // 保存的申请
    addApplicationkeep(id,beginTime,endTime,place,restaurant,remarks,approvedBy){
          this.$axios({
                  url:'StLocaleat.do/updateSave',
                  method:'post',
                  data:{
                    "id":id,
                    "beginTime":beginTime,
                    "endTime":endTime,
                    "place":place,
                    "restaurant":restaurant,
                    "remarks":remarks,
                    "approvedBy":approvedBy,
                    "state":1,
                  }
                }).then(res=>{
                  if(res.data=="申请成功"){
                    this.$message.success('申请成功')
                    window.setTimeout(function(){
                        location.reload()
                      },1500)
                  }else{
                    this.$message.error('申请失败')
                    window.setTimeout(function(){
                      location.reload()
                      },1500)
                  }
                })
    },





    //保存的修改
    keepbtnkeep(id,beginTime,endTime,place,restaurant,remarks,approvedBy){
          this.$axios({
                  url:'StLocaleat.do/updateSave',
                  method:'post',
                  data:{
                    "id":id,
                    "beginTime":beginTime,
                    "endTime":endTime,
                    "place":place,
                    "restaurant":restaurant,
                    "remarks":remarks,
                    "approvedBy":approvedBy,
                    "state":0,
                  }
                }).then(res=>{
                  if(res.data=="保存成功"){
                    this.$message.success('保存成功')
                    window.setTimeout(function(){
                        location.reload()
                      },1500)
                  }else{
                    this.$message.error('保存失败')
                    window.setTimeout(function(){
                      location.reload()
                      },1500)
                  }
                })
    },
    // 保存按钮

    keepbtn(){
      if(this.queryform.beginTime==''||this.queryform.endTime==''||this.queryform.place==''||
      this.queryform.restaurant==''||this.queryform.remarks==''||this.queryform.approvedBy==''){
        this.$message('请完整输入申请信息')
        return
      }
          this.$axios({
                  url:'StLocaleat.do/addApplication',
                  method:'post',
                  data:{
                    "applicant":1,
                    "beginTime":this.queryform.beginTime,
                    "endTime":this.queryform.endTime,
                    "place":this.queryform.place,
                    "restaurant":this.queryform.restaurant,
                    "remarks":this.queryform.remarks,
                    "approvedBy":this.queryform.approvedBy,
                    "state":0,
                  }
                }).then(res=>{
                  if(res.data=="保存成功"){
                    this.$message.success('保存成功')
                    window.setTimeout(function(){
                        location.reload()
                      },1500)
                  }else{
                    this.$message.error('保存失败')
                    window.setTimeout(function(){
                      location.reload()
                      },1500)
                  }
                })
    },
    // 审批人查询已提交的申请
    applylist(){
        this.applyshow=false
        this.examshow=true
        this.dealtshow=false
        this.approvebox=true
      this.$axios({
        url:'appTApplyCanteen.do/AppTFlowdetail',
        method:'post',
        data:{
          "pageIndex":this.pageIndexsp,
          "pageSize":this.pageSizesp,
          // "approvedBy":2,
          // "restaurant":this.restaurantCode,
          // "msg":this.applysearch
          "userId":"cz_gaoy",
          "status":"1"
        }
      }).then(res=>{

        this.approvelist=res.data.msg.list

          let arr =res.data.msg.list
          let  startTime=arr.map(item=>item.startTime) 
        //  console.log(startTime)
         let data = startTime.map(item=>item.date)
         let year = data.map(item=>item.year)
         let month = data.map(item=>item.month)
         let day = data.map(item=>item.day)
         let timetmp = startTime.map(item=>item.time)
         let hour = timetmp.map(item=>item.hour)
         let minute = timetmp.map(item=>item.minute)
         let nano = timetmp.map(item=>item.nano)
         let second = timetmp.map(item=>item.second)
         this.timetmpAll=year + '-'+month+'-' + day + ':'+hour+':'+minute+':'+nano+second
        
        //  console.log(data)
          // this.startTime = startTime.join()
        this.totalsp=res.data.msg.totalRecord

      })
    },
    // 审批
    approvecircs(id,state){
      this.$axios({
        url:'appTApplyCanteen.do/approvelRemoteDining',
        method:'post',
        data:{
          "flowId":'',
          "status":state,
          "userID":"cz_gaoy",
          "id":id,
          "remark":this.remark,
          "reason":this.reason
        }
      }).then(res=>{
        if(res.data=='已同意'){
            this.$message.success('已经同意申请')
            this.applylist()
        }else if(res.data=='已退回'){
            this.$message.success('已经退回申请')
            this.applylist()
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
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:83%;
    height: 860px;
    float: right;
    overflow-y: auto;
}
  /deep/.el-table{
    background-color:transparent;
    height: 520px!important;
  }
    /deep/.el-table__body-wrapper{
      height: 480px;
      overflow-y: auto;
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
   margin-bottom:0px;
   padding: 14px 0px 0px 30px;
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
    color:rgb(255,255,255);
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.searchtwo{
    color:rgb(255,255,255);
    font-size: 10px;
    width: 100px;
    height:36px;
    border:1px solid rgb(52,57,77);
    border-radius: 10px;
    vertical-align: middle;
    background-color: rgba(1,7,34,.1);
}
.search,.searchtwo:hover{
  cursor: pointer;
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
.bigbox{
    width:100%;
    margin-top:30px;
    padding-left:30px;
    color: white;
    font-size: 12px;
}
.applybox{
    display: inline-block;
    background-color:rgb(0, 51, 255, 1);
    margin-right: 150px;
    width:398px;
    height: 170px;
    line-height: 240px;
    text-align: center;
}
.exambox{
    display: inline-block;
    margin-right: 150px;
    text-align: center;
    line-height: 240px;
    background-color:rgb(127, 0, 255, 1);
    width:398px;
    height: 170px;
}
.dealtbox{
    display: inline-block;
    text-align: center;
    line-height: 240px;
    background-color:rgb(254, 0, 153, 1);
    width:398px;
    height: 170px;
}
.applybox,.exambox,.dealtbox:hover{
    cursor: pointer;
}
.applypeople{
    padding-left: 30px;
    margin-bottom: 20px;
    margin-top: 10px;
    box-sizing: border-box;
    color: white;
    font-size: 12px;
}
.approvepeople{
  margin-bottom: 20px;
    margin-top: 10px;
    box-sizing: border-box;
    color: white;
    font-size: 12px;
}
.place{
    width: 300px;
}
.restaurant /deep/.el-textarea{
    width: 90%;
    height: 200px;
}
.restaurant /deep/.el-textarea__inner{
    height:200px;
    color:white;
    background-color: black;
}
.text{
  color: #fff;
  font-size: 12px;
  margin: 10px 0 10px 0;
  padding: 10px 50px 10px 10px;
  box-sizing: border-box;
  text-align: right;
  line-height: 2;
}
.line{
  width: 100%;
  height: 40px;
  line-height: 40px;
  margin: 0 0 20px 0;
  background: url(../../assets/1_u3579.png) no-repeat;
  background-size: 100% 100%;
}

</style>
