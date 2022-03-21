<template>
  <div class='container'>
    <div class='meatitle'>灌溉管理</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
    <div class='cardleft'>
        <el-row class="iptrow">
            <el-form :model="queryform">
              <el-col :span='6' class='iptcol'>
                    <el-form-item label="统计周期:">
                         <el-date-picker
                            v-model="queryform.starttime"
                            value-format="yyyy-MM-dd"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker><span class="fhbox">至</span>
                    </el-form-item>
                </el-col>
                <el-col :span='5' class='iptcol'>
                    <el-form-item>
                         <el-date-picker
                            v-model="queryform.endtime"
                            value-format="yyyy-MM-dd"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker>
                    </el-form-item>
                </el-col>
            <el-col :span='10'  class='iptcol'>
              <button class='search' style="vertical-align:top;" @click.prevent @click="getIrrigateExamine">查看</button>
          </el-col>
          <el-col :span='2'  class='iptcol'>
              <button class='search' style="vertical-align:top;" @click.prevent @click="dialogaddVisible=true">新增</button>
          </el-col>
          </el-form>
        </el-row>
        <div class='echartsbox'>
            <div class='echartsleft'>
                <div id="myChart1" class='myChart1'></div>
            </div>
            <div class='echartsright'>
                <div id="myChart2" class='myChart2'></div>
            </div>
        </div>
        <el-dialog title="新增" :visible.sync="dialogaddVisible" @close='closedialog'>
                    <el-form :model="addform" ref="addform"  class="addcategory">
                        <el-row class="iptrow">
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item  label="计划名称:" prop="meteringEquipment">
                                    <el-input  v-model="addform.programname" class='gysname'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row class="iptrow">
                            <el-col :span='12'  class='iptcol'>
                                <el-form-item   label="空间名称:" prop="meteringEquipment">
                                    <el-input  v-model="addform.spacename" class='code'></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span='12'  class='iptcol'>
                                <el-form-item   label="植被名称:" prop="meteringEquipment">
                                    <el-input  v-model="addform.vegetationname" class='code'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row class="iptrow">
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item   label="灌溉时间:" prop="meteringEquipment">
                                    <el-date-picker
                                        v-model="addform.irrigationtime"
                                        value-format="yyyy-MM-dd"
                                        type="date"
                                        placeholder="请选择日期">
                                        </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row class="iptrow">
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item   label="灌溉方式:" prop="meteringEquipment">
                                    <el-select v-model="addform.irrigationmethod" style="width: 158px">
                                        <el-option v-for="item in irrigation" :key="item.parakey" :label="item.paravalue" :value="item.parakey">
                                        </el-option>
                                        </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button style="margin-top:30px;" class="search"  type="primary" @click="addbtn">确定</el-button>
                    </div>
                </el-dialog>
            <el-row class="iptrow">
                <el-col :span='10'  class='iptcol'>
                    <el-input v-model="programname" placeholder="请输入您要查询的计划名称"></el-input>
                </el-col>
                <el-col :span='2'  class='iptcol'>
                    <button class='search' @click.prevent @click="pagelist">搜索</button>
                </el-col>
            </el-row>
      <el-table stripe ref="multipleTable"  :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="20%" prop="programname" label="计划名称" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center"  min-width="20%" prop="spacename" label="空间名称" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="20%"  prop="vegetationname" label="植被名称" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="irrigationtime" label="灌溉时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="irrigationmethod" label="灌溉方式" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="evaluation" label="评价系数" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="30%"  label="操作" show-overflow-tooltip>
            <template slot-scope='scope'>
                <div  class='scopebox'><img @click='revise(scope.row.id)' src="../../assets/u6438.svg" alt="">灌溉计划修改</div>
                <div  class='scopebox'><img @click='assess(scope.row.id)' src="../../assets/u6439.svg" alt="">评价</div>
            </template>
        </el-table-column>
      </el-table>




            <!--灌溉计划修改-->
          <el-dialog class="lookdialog"  title="修改" :visible.sync="dialoglookVisible">
           <el-form :model="reviseform" ref="reviseform"  class="addcategory">
                        <el-row class="iptrow">
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item  label="计划名称:" prop="meteringEquipment">
                                    <el-input  v-model="reviseform.programname" class='gysname'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row class="iptrow">
                            <el-col :span='12'  class='iptcol'>
                                <el-form-item   label="空间名称:" prop="meteringEquipment">
                                    <el-input  v-model="reviseform.spacename" class='code'></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span='12'  class='iptcol'>
                                <el-form-item   label="植被名称:" prop="meteringEquipment">
                                    <el-input  v-model="reviseform.vegetationname" class='code'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row class="iptrow">
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item   label="灌溉时间:" prop="meteringEquipment">
                                    <el-date-picker
                                        v-model="reviseform.irrigationtime"
                                        value-format="yyyy-MM-dd"
                                        type="date"
                                        placeholder="请选择日期">
                                        </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row class="iptrow">
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item   label="灌溉方式:" prop="meteringEquipment">
                                    <el-select v-model="reviseform.irrigationmethod" style="width: 158px">
                                        <el-option v-for="item in irrigation" :key="item.parakey" :label="item.paravalue" :value="item.parakey">
                                        </el-option>
                                        </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button style="margin-top:30px;" class="search"  type="primary" @click='getamendplan'>确定</el-button>
                    </div>
          </el-dialog>




        <!-- 评价 -->
        <el-dialog class='asessdialog'  title="灌溉情况评价" :visible.sync="dialogassesVisible" @close='qxscoring'>
                    <div class='projectdetails'>
                        <div class='absolute'>计划详情</div>
                        <div class='detailsbox'>计划名称:{{assessdetails.programname}}</div>
                        <div class='detailsbox'>区域名称:{{assessdetails.spacename}}</div>
                        <div class='detailsbox'>植被名称:{{assessdetails.vegetationname}}</div>
                        <div class='detailsbox' style="margin-bottom:0px;">灌溉时间:{{assessdetails.irrigationtime}}</div>
                        <div class='detailsbox' style="margin-bottom:0px;">灌溉方式:{{assessdetails.irrigationmethod}}</div>
                    </div>
                    <div class='projectdetails'>
                        <div class='absolute'>评估打分</div>
                        <el-row class="iptrow">
                            <el-col :span='12'>
                                <el-select v-model="formcarry" style="width: 158px">
                                    <el-option v-for="item in carryout" :key="item.id" :label="item.text" :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-col>
                            <el-col :span='12'>
                                综合评分:<el-rate v-model="value1" class="rateclass"></el-rate>
                            </el-col>
                        </el-row>
                        评价详情:<el-input type="textarea" v-model="textarea" class='textarea'></el-input>
                        <input style="display:none;" multiple="multiple" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel($event)">
                        <div @click="leadingin" style="display:inline-block;">附件：点击上传附件...</div>
                    </div>
                    <div slot="footer" class="dialog-footer">
                        <el-button style="margin-top:30px;" class="define"  type="primary" @click='scoring'>确定</el-button>
                        <el-button style="margin-top:30px;" class="define"  type="primary" @click="qxscoring">取消</el-button>
                    </div>
          </el-dialog>





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
    <div class="cardright">
        <div class='routerbox clearfix'>
            <div class='smallbox'  @click="$router.push('/property/spacemanagement')">
            <div style="float:left;">空间管理</div><img style="float:right;" src="../../assets/gys1.svg" alt=""></div>
            <div class='smallbox' @click="$router.push('/property/slmanagement')">
            <div style="float:right;">供应商管理</div><img style="float:left;" src="../../assets/gys2.png" alt=""></div>
            <div class='smallbox' ><img style="float:right;" src="../../assets/gys3.png" alt=""><div style="float:left;">灌溉管理</div></div>
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
        search:'',
        file:'',
        reviseid:'',
        assessid:'',
        formcarry:'',
        textarea:'',
        value1:0,
        assessdetails:{},
        echartsdata:{},
        irrigation:[],//灌溉方式
        carryout:[
            {id:1,text:'按计划时间进行执行'},
            {id:2,text:'未按计划时间进行执行'}
        ],
        programname:'',
        dialogaddVisible:false,
        dialoglookVisible:false,
        dialogassesVisible:false,
        addform:{
          programname:'',
          spacename:'',
          vegetationname:'',
          irrigationtime:'',
          irrigationmethod:''
        },
        reviseform:{
          programname:'',
          spacename:'',
          vegetationname:'',
          irrigationtime:'',
          irrigationmethod:''
        },
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        mealType: [],//餐别,
        queryform: {
          starttime:'',
          endtime:''
        },
        tableData: [{},{},{},{},{},{},{},{},{},{}],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
        this.pagelist()
        this.getIrrigationMethods()
        this.getIrrigateExamine()
    },
    mounted(){
        // this.echarts1()
        // this.echarts2()
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
        this.restaurantCode=data.id
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      // 树状图接口
      treelist(){
        
      },
    // 打开灌溉计划修改按钮
      revise(id){
          this.reviseid=id
          this.dialoglookVisible=true
          this.$instance({
              url:'Wyirrigationmanagement.do/getPlanDetails',
              method:'post',
              data:{
                  "id":id
              }
          }).then(res=>{
              this.reviseform.programname=res.data.programname
              this.reviseform.spacename=res.data.spacename
              this.reviseform.vegetationname=res.data.vegetationname
              this.reviseform.irrigationtime=res.data.irrigationtime
              this.reviseform.irrigationmethod=res.data.irrigationmethod
          })
      },
    //评估打分
      scoring(){
          if(this.formcarry==''||this.value1==0||this.textarea==''){
              this.$message('请补全表单项')
              return
          }
        this.dialogassesVisible=false
        this.$instance({
            url:'Wyirrigationmanagement.do/getAssessRedact',
            method:'post',
            data:{
                "id":this.assessid,
                "isplan":this.formcarry,
                "rategradestar":this.value1,
                "details":this.textarea
            }
        }).then(res=>{
            if(res.data=='评估打分成功'){
                this.$message.success('评估打分成功')
            }else{
                this.$message.error('评估打分失败')
            }
            this.getIrrigateExamine()
            this.pagelist()
            this.formcarry=''
            this.value1=0
            this.textarea=''
        })
      },
    // 取消打分按钮
        qxscoring(){
        this.dialogassesVisible=false
        this.formcarry=''
        this.value1=0
        this.textarea=''
        },
    //打开评价按钮
      assess(id){
          this.assessid=id
          this.dialogassesVisible=true
          this.$instance({
              url:'Wyirrigationmanagement.do/getPlanDetails',
              method:'post',
              data:{
                  'id':id
              }
          }).then(res=>{
              this.assessdetails=res.data
          })
      },
    //返回灌溉方式
    getIrrigationMethods(){
        this.$instance({
            url:'Wyirrigationmanagement.do/getIrrigationMethods',
            method:'get'
        }).then(res=>{
            this.irrigation=res.data
        })
    },
    // 列表接口
    pagelist(){
        this.$instance({
            url:'Wyirrigationmanagement.do/WyirrigationmanagementList',
            method:'post',
            data:{
                "pageNum":this.pageIndex,
                "pageSize":this.pageSize,
                "param":{
                    "programname":this.programname
                }
            }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
    },
    // 新增确定
    addbtn(){
        if(this.addform.programname==''||this.addform.spacename==''||this.addform.vegetationname==''||this.addform.irrigationtime==''||this.addform.irrigationmethod==''){
            this.$message('表单项不能为空')
            return
        }
        this.$instance({
            url:'Wyirrigationmanagement.do/getNewlyIncreasedPlan',
            method:'post',
            data:{
                "param":this.addform
            }
        }).then(res=>{
            if(res.data=='新增成功'){
                this.$message.success('新增成功')
                this.pagelist()
            }else{
                this.$message.error('新增失败')
            }
            this.addform.programname='',
            this.addform.spacename='',
            this.addform.vegetationname='',
            this.addform.irrigationtime='',
            this.addform.irrigationmethod=''
        })
        this.dialogaddVisible=false
    },
    // 关闭新增
    closedialog(){
            this.addform.programname='',
            this.addform.spacename='',
            this.addform.vegetationname='',
            this.addform.irrigationtime='',
            this.addform.irrigationmethod=''
    },
    //灌溉计划修改
    getamendplan(){
        this.dialoglookVisible=false
        this.$instance({
            url:'Wyirrigationmanagement.do/getamendPlan',
            method:'post',
            data:{
                "param":{
                    "id":this.reviseid,
                    "programname":this.reviseform.programname,
                    "spacename":this.reviseform.spacename,
                    "vegetationname":this.reviseform.vegetationname,
                    "irrigationtime":this.reviseform.irrigationtime,
                    "irrigationmethod":this.reviseform.irrigationmethod
                }
            }
        }).then(res=>{
            if(res.data=='修改成功'){
                this.$message.success('修改成功')
                this.pagelist()
            }else{
                this.$message.error('修改失败')
            }
        })
    },
    importExcel(){
        this.file=this.$refs.articleImageFile.files[0]
    },
    // 上传图片按钮
    leadingin(){
        this.$refs.articleImageFile.click()
      },
    //图形接口
    getIrrigateExamine(){
        this.$instance({
            url:'Wyirrigationmanagement.do/getIrrigateExamine',
            method:'post',
            data:{
                "param":{
                    "startTime":this.queryform.starttime,
                    "finishTime":this.queryform.endtime
                }
            }
        }).then(res=>{
            this.echartsdata=res.data
            this.echarts1()
            this.echarts2()
        })
    },
    //灌溉计划执行情况分析
    echarts1(){
        var myChart1 = this.$echarts.init(document.getElementById('myChart1'));
        myChart1.setOption({
                    title:{
                        text:'灌溉计划执行情况分析',
                        left:'center',
                        top:'10',
                        textStyle:{
                            color:'white',
                            fontSize:'14',
                            fontWeight:'100'
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        right: 50,
                        bottom:50,
                        data: ['按时执行', '未按时执行'],
                                textStyle:{
                        color:'white',
                        fontSize:12,
                        },
                    },
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: ['55%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data: [
                                {value: this.echartsdata.getexecute,
                                 name: '按时执行',
                                 itemStyle:{
                                    color:'#D0E1FF'
                                    },
                                    label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }
                                    },
                                {value:  this.echartsdata.getNotexecute,
                                 name: '未按时执行',
                                 itemStyle:{
                                color:'#FFD2D1'
                                },
                                label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }
                                    },
                            ]
                        }
                    ]
                })
    },
    // 灌溉计划检查情况分析
    echarts2(){
        var myChart2 = this.$echarts.init(document.getElementById('myChart2'));
        myChart2.setOption({
                    title:{
                            text:'灌溉计划检查情况分析',
                            left:'center',
                            top:'10',
                            textStyle:{
                                color:'white',
                                fontSize:'14',
                                fontWeight:'100'
                            }
                        },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        right: '10%',
                        bottom:'center',
                        data: ['1星', '2星', '3星', '4星', '5星'],
                        textStyle:{
                          color:'#fff',
                          fontSize:'12'
                        }
                    },
                    series: [
                        {
                            type: 'pie',
                            radius: '75%',
                            center: ['50%', '50%'],
                            clockwise:true,
                            // selectedMode: 'single',
                            data: [
                                {value: this.echartsdata.OneStar, name: '1星',itemStyle:{color:'#B3CCFB'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }},
                                {value: this.echartsdata.TwoStar, name: '2星',itemStyle:{color:'#FBB1B0'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }},
                                {value: this.echartsdata.ThreeStar, name: '3星',itemStyle:{color:'#F0FCE0'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }},
                                {value: this.echartsdata.FourStar, name: '4星',itemStyle:{color:'#D5C7E9'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }},
                                {
                                  value: this.echartsdata.FiveStar,
                                  name: '5星',
                                  itemStyle:{color:'#BEECFB'},
                                  label: {
                                        position: 'inner',
                                        show:true,
                                        formatter:'{c} ({d}%)',
                                    }}
                            ],
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        },
                        
                    ]
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
  .inputwidth{
    width: 120px;
    border:1px solid #5D5D64;
  }
  .tablecard{
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    overflow-y: auto;
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
    height:56px;
    margin-bottom:0px;
    padding: 14px 0px 0px 0;
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
  /deep/.el-form-item{
      margin-bottom: 18px;
  }
  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px ;
    color: rgb(255,255,255);
  }
  .search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
  }
  .search:hover{
    cursor: pointer;
  }
  /deep/.el-table{
    background-color:transparent;
    height: 420px!important;
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
    color: white;
    font-size: 12px;
    margin-left:5px;
}
  .cardleft{
      width: 78%;
      display: inline-block;
  }
  /deep/.el-input--suffix{
    width: 210px;
    border:1px solid #5D5D64;
  }
  .cardright{
      width: 20%;
      margin: 5px 0 0 20px;
      height:100%;
      padding:15px 0 10px 20px;
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
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  width: 600px;
}
/deep/.el-dialog__header{
  box-sizing: border-box;
  text-align: center;
  background:transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
}
.asessdialog /deep/.el-dialog__header{
  padding:20px;
  box-sizing: border-box;
  text-align: left;
  background:transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
}
/deep/.el-dialog__title{
    font-size: 18px!important;
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:20px 20px 0 20px;
  margin-bottom: 20px;
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
.gysname{
    width: 440px;
}
.code{
    width: 190px;
}
.textarea{
    margin:10px 0;
}
.textarea /deep/.el-textarea__inner{
    background-color: black;
    color: white;
    height: 100px;
    border: 1px solid #626468;
}
 /deep/.el-table__body-wrapper{
    height: 380px!important;
    overflow-y:auto;
}
.echartsbox{
    width: 100%;
    height: 260px;
}
.echartsleft,.echartsright{
    width: 50%;
    height: 100%;
    display: inline-block;
}
.myChart1,.myChart2{
    width: 100%;
    height: 100%;
}
.scopebox{
    display: inline-block;
    margin:0 20px;
}
.scopebox img{
    vertical-align: middle;
    width: 30px;
    height: 24px;
}
.scopebox img:hover{
    cursor: pointer;
}
.asessdialog /deep/.el-dialog{
  background: #343645;
  border:1px solid rgba(255,255,255,.5);
  width: 900px;
}
.projectdetails{
    position: relative;
    width: 100%;
    margin:10px 0 30px 0;
    padding:30px 40px;
    border: 1px solid #444654;
    box-sizing: border-box;
    font-size: 12px;
    background-color:#303240;
}
.absolute{
    position: absolute;
    font-size: 18px;
    top:-10px;
    left:10px;
}
.detailsbox{
    display: inline-block;
    margin:10px 150px 20px 0;
}
.rateclass{
    display: inline-block;
    height:100%;
}
/deep/.el-rate__icon{
    font-size: 50px;
}
.define{
    width: 100px;
}
</style>

