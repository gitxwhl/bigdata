<template>
  <div class='container'>
    <div class='meatitle'>工作安排</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class="tablecard" >
    <div class='cardleft'>
      <div  class='clearfix'>
        <div class="space" style="width:90%;float:left;">
            <div class="top_pic1" v-for="(item,index) in space" :key="index" @mouseenter="mouseenter(space,index,item.id)" @mouseleave="mouseleave(space,index,item.id)">{{item.spacename}}
              <div v-show='dialogNutVisible[index]' class="enterbox" ref='nutdialog'>
                    <table border="1" class="nuttable">
                          <tr class='tablehead'>
                                <td rowspan="4">植被修剪</td>
                                <td rowspan="3">人天合计</td>
                                <td rowspan="2">本季度投入情况</td>
                                <td rowspan="2">去年同期</td>
                              </tr>
                            <tr>

                            </tr>
                             <tr>
                              <td>{{infobox.thisQuarterXj[0].peopleDay}}</td>
                              <td>{{infobox.lastQuarterXj[0].peopleDay}}</td>
                            </tr>
                            <tr>
                              <td>植被种类</td>
                              <td>{{infobox.thisQuarterXj[0].vegetationType}}</td>
                              <td>{{infobox.lastQuarterXj[0].vegetationType}}</td>
                            </tr>
                            <tr>
                              <td rowspan="3">植被种植</td>
                              <td>人天合计</td>
                              <td>{{infobox.thisQuarterZz[0].peopleDay}}</td>
                              <td>{{infobox.lastQuarterZz[0].peopleDay}}</td>
                            </tr>
                            <tr>
                              <td>植被种类</td>
                              <td>{{infobox.thisQuarterZz[0].vegetationType}}</td>
                              <td>{{infobox.lastQuarterZz[0].vegetationType}}</td>
                            </tr>
                            <tr>
                              <td>补植数量</td>
                              <td>{{infobox.thisQuarterZz[0].plantingNum}}</td>
                              <td>{{infobox.lastQuarterZz[0].plantingNum}}</td>
                            </tr>
                     </table>
              </div>
            </div>
        </div>

        <div style="float:right;">
          <div class="pic1" @click="dialogaddregister = true" @click.prevent>
            <p class="picture"><span class="words">登记植被<br>种植情况</span></p>
          </div>
          <div @click="dialogaddclip = true" @click.prevent>
            <p class="picture"><span class="words">登记植被<br>修剪情况</span></p>
          </div>
        </div>
      </div>


      <!-- 植被种植情况登记弹出框 -->
      <el-dialog class="adddialog"  title="植被种植情况登记" :visible.sync="dialogaddregister" @close="cancelplant">
        <el-form :model="plantform" ref='register' class="addcategory">
          <el-row>
            <el-col :span='9'>
              <el-form-item style="margin-bottom:15px;" label="种植时间:" prop="policyCode">
                <el-date-picker
                  v-model="plantform.plantingTime"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span='6'>
            <el-form-item label="种类:" style="margin-bottom:15px;">
              <el-select class='zhonglei' v-model="plantform.vegetationType">
                <el-option v-for="item in vegetationType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            </el-col>
            <el-col :span='8'>
              <el-form-item label="是否补植:" style="margin-bottom:15px">
                <template>
                  <el-radio v-model="plantform.isReplanting" label="是"></el-radio>
                  <el-radio v-model="plantform.isReplanting" label="否"></el-radio>
                </template>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span='8'>
              <el-form-item label="种植区域:" style="margin-bottom:15px;">
                <el-select class='quyu' v-model="plantform.plantingArea">
                  <el-option v-for="item in space" :key="item.id" :label="item.spacename" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span='7'>
            <el-form-item style="margin-bottom:15px;"  label="成员数量:" prop="numbermember">
              <el-input class='mount'  v-model="plantform.number"></el-input><div class='namebox'>人</div>
            </el-form-item>
          </el-col>
            <el-col :span='7'>
              <el-form-item style="margin-bottom:15px;"  label="工作时间:" prop="worktime">
                <el-input class='mount'   v-model="plantform.workingTime"></el-input><div class='namebox'>天</div>
              </el-form-item>
            </el-col>

          </el-row>

          <el-row v-for="(item,index) in plantform.msg" :key="index">
          <el-col :span='10'>
            <el-form-item style="margin-bottom:15px;"  label="植被名称:" prop="Namevegetation">
              <el-input class='zbname' v-model="item.vegetationName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='11'>
            <el-form-item style="margin-bottom:15px;"  label="种植数量:" prop="Growingnumber">
              <el-input class='zbname'  v-model="item.plantingNum"></el-input>
              </el-form-item>
          </el-col>
          <el-col :span='2'>
              <div class='imgbox' @click='vforplant' v-show="index==plantform.msg.length-1"></div>
          </el-col>
        </el-row>
          <el-row class='martop'>
            <el-col :span='12' :offset="12">
              <el-form-item  label="负责人:" prop="principal">
                <el-input class='zbname' v-model="plantform.person"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button style="" class="search"  type="primary" @click="resetplant">重置</el-button>
          <el-button style="" class="search"  type="primary" @click="addplant">保存</el-button>
          <el-button style="" class="search"  type="primary" @click="cancelplant">取消</el-button>
        </div>
      </el-dialog>





      <!-- 植被修剪情况登记弹出框 -->
      <el-dialog class="adddialog" title="植被修剪情况登记" :visible.sync="dialogaddclip" @close="canceclip">
        <el-form :model="clipform" ref='clip' class="addcategory">
          <el-row>
            <el-col :span='12'>
              <el-form-item style="margin-bottom:15px;" label="修剪时间:" prop="policyCode">
                <el-date-picker
                  v-model="clipform.plantingTime"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span='12'>
              <el-form-item label="种类:" style="margin-bottom:15px;">
                <el-select v-model="clipform.vegetationType">
                  <el-option v-for="item in vegetationType" :key="item.id" :label="item.text" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row v-for="(item,index) in clipform.msg" :key="index">
            <el-col :span='7'>
              <el-form-item label="修剪区域:" style="margin-bottom:15px;">
                <el-select class='quyu' v-model="item.plantingArea">
                  <el-option v-for="item in space" :key="item.id" :label="item.spacename" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span='7'>
              <el-form-item style="margin-bottom:15px;"  label="成员数量:" prop="numbermember">
                <el-input class='mount' v-model="item.number"></el-input><div class='namebox'>人</div>
              </el-form-item>
            </el-col>
            <el-col :span='7'>
              <el-form-item style="margin-bottom:15px;"  label="工作时间:" prop="worktime">
                <el-input class='mount' v-model="item.workingTime"></el-input><div class='namebox'>天</div>
              </el-form-item>
            </el-col>
              <el-col :span='2'>
              <div class='imgbox' @click='vforclip' v-show="index==clipform.msg.length-1"></div>
          </el-col>
          </el-row>
          <el-row class='martop'>
            <el-col :span='12' :offset="12">
              <el-form-item  label="负责人:" prop="principal">
                <el-input class='zbname' v-model="clipform.person"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
        <div slot="footer" class="dialog-footer">
                    <el-button style="" class="search"  type="primary" @click="resetclip">重置</el-button>
          <el-button style="" class="search"  type="primary" @click="addclip">保存</el-button>
          <el-button style="" class="search"  type="primary" @click="canceclip">取消</el-button>
        </div>
      </el-dialog>




        <el-row class="iptrow">
          <el-col :span='8' class='iptcol'>
              <el-input class="inputwidth"  v-model="searchname" placeholder="请输入您要查询的计划名称"></el-input>
          </el-col>
          <el-col :span='14'  class='iptcol'>
              <button class='search' @click.prevent @click="pagelist" >搜索</button>
          </el-col>
          <el-col :span='2'  class='iptcol'>
              <button class='increase' @click="dialogaddVisible = true" @click.prevent>新增工作安排</button>






              <!-- 新增弹框 -->
              <el-dialog class="adddialog" title="新增" :visible.sync="dialogaddVisible" @close="canceladd">
                <el-form :model="addform" ref='addform'  class="addcategory">
                  <el-row>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="名称:" prop="name">
                        <el-input class='zbname' v-model="addform.name"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;" label="时间:" prop="plantime">
                        <el-date-picker
                          v-model="addform.plantime"
                          format="yyyy-MM-dd"
                          value-format="yyyy-MM-dd"
                          type="date"
                          placeholder="">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                  </el-row>


                  <div class='vforbox' v-for="(item,index) in addform.msg" :key="index">
                    <el-row>
                    <el-col :span='18'>
                      <el-form-item label="工作类型:" style="margin-bottom:15px;" >
                        <el-select v-model="item.registrationtype">
                          <el-option v-for="item in jobType" :key="item.id" :label="item.text" :value="item.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span='2'>
                        <div class='imgbox' @click="addbox" v-show="index==addform.msg.length-1"></div>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='24'>
                      <el-form-item style="margin-bottom:15px;"  label="工作安排:" >
                        <el-input type="textarea" v-model="item.content"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  </div>


                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button style="" class="search"  type="primary" @click="resetregister">重置</el-button>
                  <el-button style="" class="search"  type="primary" @click="addlist">确定</el-button>
                  <el-button style="" class="search"  type="primary" @click="canceladd">取消</el-button>
                </div>
              </el-dialog>




          </el-col>
        </el-row>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center" min-width="5%" type="selection" :selectable="selectable" width="55"> </el-table-column>
        <el-table-column align="center" min-width="10%" prop="name" label="名称"> </el-table-column>
        <el-table-column align="center" min-width="10%" prop="plantime" label="时间"></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="content" label="内容" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%" label="操作" >
          <template slot-scope="scope">
            <span  class="binicon"><img src="../../assets/u349.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" @click="deletepolicy(scope.row.id)">删除</span>
            <span  class="editicon" @click="revisedialog(scope.row)"><img src="../../assets/u363.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" > 修改</span>
          </template>
        </el-table-column>
      </el-table>





      <!-- 修改弹框 -->
              <el-dialog class="adddialog" title="修改" :visible.sync="dialogchangeVisible" @close="closeaddDialog('addform')">
                <el-form :model="reviseform" ref='addform'  class="addcategory">
                  <el-row>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="名称:" prop="policyName">
                        <el-input class='zbname' v-model="reviseform.name"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;" label="时间:" prop="policyCode">
                        <el-date-picker
                          v-model="reviseform.plantime"
                          format="yyyy-MM-dd"
                          value-format="yyyy-MM-dd"
                          type="date"
                          placeholder="">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                  </el-row>


                  <div class='vforbox'>
                    <el-row>
                    <el-col :span='18'>
                      <el-form-item label="工作类型:" style="margin-bottom:15px;">
                        <el-select v-model="reviseform.registrationtype">
                          <el-option v-for="item in jobType" :key="item.id" :label="item.text" :value="item.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='24'>
                      <el-form-item style="margin-bottom:15px;"  label="工作安排:" prop="equipmentstatus">
                        <el-input type="textarea" v-model="reviseform.content"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  </div>


                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button style="" class="search"  type="primary" @click="resetrevise">重置</el-button>
                  <el-button style="" class="search"  type="primary" @click="revisebtn">确定</el-button>
                  <el-button style="" class="search"  type="primary" @click="cancelrevise">取消</el-button>
                </div>
              </el-dialog>





      <div class='pagbox'>
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





  <div class="cardright">
      <div class='routerbox clearfix'>
            <div class='smallbox'  @click="$router.push('/property/spacemanagement')">
            <div style="float:left;">空间管理</div><img style="float:right;" src="../../assets/gys1.svg" alt=""></div>
            <div class='smallbox' @click="$router.push('/property/slmanagement')">
            <div style="float:right;">供应商管理</div><img style="float:left;" src="../../assets/gys2.png" alt=""></div>
            <div class='smallbox' @click="$router.push('/property/irrigationmanagement')"><img style="float:right;" src="../../assets/gys3.png" alt=""><div style="float:left;">灌溉管理</div></div>
            <div class='smallbox' @click="$router.push('/property/jobplacement')">
            <img style="float:left;" src="../../assets/gys4.svg" alt=""><div style="float:right;">工作安排</div></div>
        </div>
        <div class='workreminder'>
            <div class='workminbox'>工作提醒</div>
        </div>
    </div>


    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dialogNutVisible:[],
        dialogaddregister:false,
        dialogaddclip:false,//修剪
        dialogchangeVisible:false,
        row:{},
        jobType:[],
        vegetationType:[],
        space:[],
        searchname:'',
        infobox:{
          thisQuarterZz:[
              {
              peopleDay: '',
              vegetationType: '',
              plantingNum: ''
            }
          ],
          lastQuarterZz: [
              {
                  peopleDay: '',
                  vegetationType: '',
                  plantingNum: ''
              }
          ],
          thisQuarterXj: [
              {
                  peopleDay: '',
                  vegetationType: ''
              }
          ],
          lastQuarterXj: [
              {
                  peopleDay: '',
                  vegetationType: ''
              }
          ]
        },
        register:{
          time:'',//种植时间
          type:'',//种类
          radio: '',
          areaconter:'',//种植区域
          numbermember:'',//成员数量
          worktime:'',//工作时间
          Namevegetation:'',//植被名称
          Growingnumber:'',//种植数量
          principal:'',//负责人
        },

        dialogaddVisible: false,

        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        settlementamount:[],//结算金额
        applicabletime:[],//适用时间
        equipment:[],//关联设备
        queryform: {
          settlementamount: '',
        },
        reviseform:{
          name:'',
          plantime:'',
          registrationtype:'',
          content:''
        },
        addform: {
          name:'',
          plantime:'',
          msg:[
            {
              registrationtype:'',
              content:''
            }
          ]
        },
        plantform:{
          plantingTime:'',
          vegetationType:'',
          isReplanting:'',
          person:'',
          plantingArea:'',
          number:'',
          workingTime:'',
          msg:[
            {
              vegetationName:'',
              plantingNum:''
            }
          ]
        },
        clipform:{
          plantingTime:'',
          vegetationType:'',
          person:'',
          msg:[
            {
              plantingArea:'',
              number:'',
              workingTime:''
            }
          ]
        },
        tableData: [],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    mounted () {
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
        console.log(data)
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      selectable(row,index){
        return 0
      },
      // 树状图接口
      treelist(){

      },
      // 获取下拉框数据-工作安排
      selectlist(){
        this.$instance({
          url:'Wyworkorganization.do/dropDownBox',
          method:'get'
        }).then(res=>{
          this.jobType=res.data.jobType
          this.space=res.data.space
          this.vegetationType=res.data.vegetationType
        })
      },
      // 列表
      pagelist(){
        this.$instance({
          url:'Wyworkorganization.do/getJobPlacement',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "name": this.searchname
          }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 新增加号
      addbox(){
        this.addform.msg.push({
              registrationtype:'',
              content:''
            })
      },
      // 种植加号
      vforplant(){
        this.plantform.msg.push({
          vegetationName:'',
          plantingNum:''
        })
      },
      // 修剪加号
      vforclip(){
        this.clipform.msg.push({
              plantingArea:'',
              number:'',
              workingTime:''
        })
      },
      //新增重置
      resetregister(){
        this.addform.name='',
        this.addform.plantime=''
         for(var i=0;i<this.addform.msg.length;i++){
         this.addform.msg[i].registrationtype=''
         this.addform.msg[i].content=''
       }
      },
    //新增接口
      addlist(){
        if(this.addform.name==''||this.addform.plantime==''){
          this.$message('请补全表单项')
          return
        }
        for(var i=0;i<this.addform.msg.length;i++){
          if(this.addform.msg[i].registrationtype==''||this.addform.msg[i].content==''){
            this.$message('请补全表单项')
            return
          }
        }
        this.$instance({
          url:'Wyworkorganization.do/addJobPlacement',
          method:'post',
          data:this.addform
        }).then(res=>{
            if(res.data=='新增成功'){
              this.$message.success('新增成功')
              this.pagelist()
            }else{
              this.$message.error('新增失败')
            }
        this.addform.name='',
        this.addform.plantime=''
        this.addform.msg=[{
          registrationtype:'',
          content:''
        }]
        this.dialogaddVisible=false
        })
      },
      // 新整取消按钮
    canceladd(){
      this.addform.name='',
        this.addform.plantime=''
        this.addform.msg=[{
          registrationtype:'',
          content:''
        }]
        this.dialogaddVisible=false
    },
    // 修改按钮
    revisedialog(row){
      this.dialogchangeVisible=true
      this.row=row
      this.reviseform.name=row.name
      this.reviseform.plantime=row.plantime
      this.reviseform.registrationtype=row.registrationtype
      this.reviseform.content=row.content
    },
    // 修改重置
    resetrevise(){
      this.reviseform.name=this.row.name
      this.reviseform.plantime=this.row.plantime
      this.reviseform.registrationtype=this.row.registrationtype
      this.reviseform.content=this.row.content
    },
    // 修改确定
    revisebtn(){
      this.$instance({
        url:'Wyworkorganization.do/updateJobPlacement',
        method:'post',
        data:{
          "id":this.row.id,
          "name":this.reviseform.name,
          "plantime":this.reviseform.plantime,
          "registrationtype":this.reviseform.registrationtype,
          "content":this.reviseform.content
        }
      }).then(res=>{
        if(res.data=='修改成功'){
          this.$message.success('修改成功')
          this.pagelist()
        }else{
          this.$message.error('修改失败')
        }
        this.dialogchangeVisible=false
      })
    },
    // 修改取消
    cancelrevise(){
      this.reviseform.name=this.row.name
      this.reviseform.plantime=this.row.plantime
      this.reviseform.registrationtype=this.row.registrationtype
      this.reviseform.content=this.row.content
      this.dialogchangeVisible=false
    },
      // 删除
      deletepolicy(id){
        this.$confirm('确认删除该菜品?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          this.$instance({
            url:'Wyworkorganization.do/deleteJobPlacement',
            method:'post',
            data:{
              id:id
            }
          }).then(res=>{
            if(res.data=='删除成功'){
              this.$message.success('删除成功')
              this.pagelist()
            }else{
              this.$message.error('删除失败')
            }
          })
        }).catch(()=>{

        })

      },
    //鼠标进入空间
    mouseenter(space,i,id){
      this.dialogNutVisible=space.map(item=>{
        return false
      })
     this.dialogNutVisible[i]=true
     this.$instance({
       url:'Wyworkorganization.do/getSpaceInfo',
       method:'post',
       data:{
         "id":id
       }
     }).then(res=>{
       this.infobox=res.data
     })
    },
    mouseleave(){
      this.dialogNutVisible=false
    },
    // 种植重置
    resetplant(){
        this.plantform.plantingTime=''
        this.plantform.vegetationType=''
        this.plantform.isReplanting=''
        this.plantform.person=''
        this.plantform.plantingArea=''
        this.plantform.number=''
        this.plantform.workingTime=''
        for(var i=0;i<this.plantform.msg.length;i++){
         this.plantform.msg[i].vegetationName=''
         this.plantform.msg[i].plantingNum=''
       }
    },
    // 种植接口
    addplant(){
      if(this.plantform.plantingTime==''||this.plantform.vegetationType==''||this.plantform.isReplanting==''||
      this.plantform.person==''||this.plantform.plantingArea==''||this.plantform.number==''||this.plantform.workingTime==''){
        this.$message('请补全表单项')
          return
      }
      for(var i=0;i<this.plantform.msg.length;i++){
        if(this.plantform.msg[i].vegetationNam==''||this.plantform.msg[i].plantingNum==''){
          this.$message('请补全表单项')
            return
        }
      }
      this.$instance({
        url:'Wyworkorganization.do/addRegisterZz',
        method:'post',
        data:this.plantform
      }).then(res=>{
        if(res.data=='添加成功'){
          this.$message.success('添加成功')
          this.pagelist()
        }else{
          this.$message.error('添加失败')
        }
        this.plantform.plantingTime=''
        this.plantform.vegetationType=''
        this.plantform.isReplanting=''
        this.plantform.person=''
        this.plantform.plantingArea=''
        this.plantform.number=''
        this.plantform.workingTime=''
        this.plantform.msg=[{
          vegetationName:'',
          plantingNum:''
        }]
        this.dialogaddregister=false
      })
    },
    //种植取消
    cancelplant(){
        this.plantform.plantingTime=''
        this.plantform.vegetationType=''
        this.plantform.isReplanting=''
        this.plantform.person=''
        this.plantform.plantingArea=''
        this.plantform.number=''
        this.plantform.workingTime=''
        this.plantform.msg=[{
          vegetationName:'',
          plantingNum:''
        }]
        this.dialogaddregister=false
    },
    //修剪重置按钮
    resetclip(){
        this.clipform.plantingTime=''
        this.clipform.vegetationType=''
        this.clipform.person=''
        for(var i=0;i<this.clipform.msg.length;i++){
         this.clipform.msg[i].plantingArea=''
         this.clipform.msg[i].number=''
         this.clipform.msg[i].workingTime=''
       }
    },
      // 修剪取消按钮
      canceclip(){
        this.clipform.plantingTime=''
        this.clipform.vegetationType=''
        this.clipform.person=''
        this.clipform.msg=[{
          plantingArea:'',
          number:'',
          workingTime:''
        }]
        this.dialogaddclip=false
      },
      // 修剪确定按钮
      addclip(){
        if(this.clipform.plantingTime==''||this.clipform.vegetationType==''||this.clipform.person==''){
        this.$message('请补全表单项')
          return
        }
        for(var i=0;i<this.clipform.msg.length;i++){
          if(this.clipform.msg[i].plantingArea==''||this.clipform.msg[i].number==''||this.clipform.msg[i].workingTime==''){
            this.$message('请补全表单项')
              return
          }
        }
        this.$instance({
          url:'Wyworkorganization.do/addRegisterXj',
          method:'post',
          data:this.clipform
        }).then(res=>{
            if(res.data=='添加成功'){
              this.$message.success('添加成功')
              this.pagelist()
            }else{
              this.$message.error('添加失败')
            }
            this.clipform.plantingTime=''
            this.clipform.vegetationType=''
            this.clipform.person=''
            this.clipform.msg=[{
              plantingArea:'',
              number:'',
              workingTime:''
            }]
            this.dialogaddclip=false
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
  .top{
    background-color:rgba(1,5,22,.4);
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
  }
  .bg_pic{
    width:500px;
    height: 38px;
    background: url(../../assets/u719.png) ;
    /*background-size: 683px 38px;*/
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
    overflow-y: auto;
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    box-sizing: border-box;
    border-radius: 0;
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
  .el-row /deep/.el-input__inner{
    width: 200px;
    padding:0 30px 0 15px;
  }
  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px ;
    color: rgb(255,255,255);
  }
  .search,.increase{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 90px;
    height:32px;
    padding:0;
    box-sizing: border-box;
    line-height: 0.8;
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
    height: 450px!important;
  }
   /deep/.el-table__body-wrapper{
    height: 410px!important;
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
    width:80px;
    height: 30px;
    line-height:30px;
    float: left;
  }
  .binicon img:hover{
    cursor: pointer;
  }
  .editicon{
    width:80px;
    height: 20px;
    line-height:30px;
    float: right;
    margin-right:50px ;
  }
  .editicon img{
    cursor: pointer;
  }
  /deep/.el-dialog{
  background: rgba(0,0,0,.8);
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
    padding:0 20px 0 20px;
    margin-top: 10px;
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
  .el-textarea{
    width: 560px;
    height: 50px;
  }
  .addcategory /deep/.el-textarea__inner{
    background: black;
    border:1px solid rgb(52,57,82);

  }
  /deep/.el-textarea__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 560px;
    height: 50px;
    padding: 5px;
    vertical-align: middle;
    background-color:black;
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:4px;
  }
  .adddialog /deep/.el-dialog{
    width: 700px!important;
  }
  .adddialog .point{
    width: 240px;
    height:100px;
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

  /deep/.el-input__icon{
    line-height: 28px;
    width:25px;
  }
  /deep/.el-input__suffix{
    right: 5px;
  }
  .pagbox{
    text-align: right;
    margin-top: 10px;
    height:'15%'
  }

  .words{
    font-size:14px;
    color: white;
    display: inline-block;
    /*width: 94px;*/
    margin-top: 50px;
    margin-left: 20px;

  }
  .pic1{
  margin-bottom: 55px
  }
  .picture{
    width: 100px;
    height: 100px;
    background: url("../../assets/u6897.png");
    background-size: 100% 100%;
  }
  .picture:hover{
    cursor: pointer;
  }
  .space{
    position: relative;
    text-align: center;
    margin-top: 10px;
    margin-left:10px;
  }
  .top_pic1{
    display: inline-block;
    vertical-align: middle;
    width: 140px;
    height: 140px;
    margin:10px 10px 0 0;
    box-sizing: border-box;
    background: url("../../assets/u7338.svg")no-repeat;
    background-size: content;
    color: white;
    font-size: 13px;
    text-align: center;
    position: relative;
  }
  .top_pic1:hover{
    background: url("../../assets/u7338_mouseOver.svg")no-repeat;
    cursor: pointer;
  }
  .el-radio {
    color: #606266;
    cursor: pointer;
    margin-right: 30px;
    margin-top: 5px;
  }
  .cardleft{
    width: 78%;
    display: inline-block;
  }
  /deep/.el-card__body{
    height: 100%;
  }
   .cardright{
    width: 20%;
    margin: 5px 0 0 20px;
    height:98%;
    padding:15px 0 0 20px;
    box-sizing: border-box;
    display: inline-block;
    vertical-align: top;
    color: white;
    font-size: 13px;
  }
.routerbox{
    background-color: #34353D;
    box-sizing: border-box;
    width: 100%;
    margin-bottom: 20px;
  }
  .smallbox{
      width: 50%;
      height:120px;
      float: left;
      box-sizing: border-box;
      padding:10px;
  }
  .smallbox:nth-child(1){
      border-right:2px solid #416A82;
      border-bottom:2px solid #416A82;
      height:122px;
  }
  .smallbox:nth-child(2){
      border-bottom:2px solid #416A82;
      height:122px;
  }
  .smallbox:nth-child(3){
      border-right:2px solid #416A82;
  }
.smallbox img{
    width: 80px;
    height: 80px;
}
.smallbox:hover{
    cursor: pointer;
}
.workreminder{
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
    background-color: #34353D;
    height: 550px;
}
.workminbox{
    width: 100%;
    text-align: center;
}
.inputwidth /deep/.el-input__inner{
  width: 360px;
}
.zhonglei /deep/.el-input__inner{
  width: 100px;
}
.quyu /deep/.el-input__inner{
  width: 120px;
}
.mount,.mount /deep/.el-input__inner{
  width: 80px;
  padding: 0 10px;
}
.imgbox{
  width: 26px;
  height: 26px;
  background:url(../../assets/u7434.svg)no-repeat;
  background-size: 100% 100%;
  cursor: pointer;
  margin:5px 0 0 0;
}
.el-date-editor,.el-date-editor /deep/.el-input__inner{
  width: 180px;
}
.el-date-editor /deep/.el-input__inner{
  padding: 0 32px 0 25px;
}
.zbname,.zbname /deep/.el-input__inner{
  width: 170px;
}
.martop{
  margin-top:80px;
}
.namebox{
  font-size: 12px;
  display: inline-block;
}
.vforbox{
  padding: 10px;
  margin:0 0 40px 0;
  box-sizing: border-box;
  background:url(../../assets/u1693.png)no-repeat;
  background-size: 100% 100%;
}
.nuttitle{
  height: 30px;
  color:white;
  line-height: 30px;
  padding-left: 10px;
  box-sizing: border-box;
}
.nuttable{
    color: white;
    width:80%;
    margin: 20px auto;
    table-layout:fixed;
    background:transparent;
    border-collapse: collapse;
}
.nuttable td{
    height:25px;
    padding:0 0 0 5px;
    font-size: 10px;
    box-sizing: border-box;
    background:transparent;
    border:1px solid rgb(128,128,128);
}
.tablehead{
    background:transparent;

}
.nuttable tr td{
    background:transparent!important;
}
.tablehead td{
    width: 30px;
    height:30px;
    text-align: center;
    font-size: 12px;
}
.enterbox{
  position: absolute;
  z-index: 10;
  left:90px;
  top: 90px;
  width: 300px;
  background:black;
  border: 1px solid white;
  font-size: 12px;
  box-sizing: border-box;
}
</style>
