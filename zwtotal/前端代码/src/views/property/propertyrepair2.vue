<template>
  <div class='container'>
    <div class='meatitle'>物业报修</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
        
      <div class='titletop'>
          <div class='imgbox'>
              <div class="imgboxtop" >报修总量</div>
              <div class="imgboxleft">{{getCount.bx}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >派单总量</div>
              <div class="imgboxleft">{{getCount.pd}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >维修总量</div>
              <div class="imgboxleft">{{getCount.wx}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >评价总数</div>
              <div class="imgboxleft">{{getCount.pj}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >好评总数</div>
              <div class="imgboxleft">{{getCount.hp}}</div>
          </div>
          <div class='applyfor' @click='changeapplyfor'>报修申请</div>
          <div class='appraise' @click="changeappraise">报修评价</div>
      </div>
      

      <div class='contentbox' v-show='echartsshow'>
          <div class='contentleft'>
              <div class='repairtype'>报修类型分析</div>
                <div id="myChart1" class='myChart1'></div>
              <div class='bottomline'>
              </div>
      </div>
          <div class='contentright'>
              <div class='distribution'>
                  <div class='repairtype'>派单情况分析</div>
                  <div id="myChart2" class='myChart2'></div>
                    <div class='bottomline'></div>
              </div>
              <div class='evaluation'>
                <div class='repairtype'>报修评价分析</div>
                  <div id="myChart3" class='myChart3'></div>
                    <div class='bottomline'></div>
              </div>
          </div>
      </div>


      <div v-show="applyforshow" class='application'>
          <div class='marbot'>报修人:XX</div>
          <div class='marbot'>单位:公司/部门/部门</div>
          <el-form :model="queryform" size="mini" class='queryform'>
            <el-form-item label="联系电话:" class='marbot'>
                    <el-input  v-model="queryform.mLxdh"></el-input>
            </el-form-item>
            <el-form-item label="报修类型:" class='marbot'>
                    <el-select v-model="queryform.mBxlx" style="width:180px;">
                                    <el-option v-for="item in type" :key="item.id" :label="item.text" :value="item.id">
                                  </el-option>
                    </el-select>
            </el-form-item >
            <el-form-item label="选择园区:" class='marbot'>
                    <el-input  v-model="queryform.mZone"></el-input>
            </el-form-item>
            <el-form-item label="详细地点:" class='marbot'>
                    <el-input class='address' v-model="queryform.mWxdz"></el-input>
            </el-form-item>
            <el-form-item label="报修内容:" class='marbot'>
                    <el-input type="textarea" v-model="queryform.mBxnr"></el-input>
            </el-form-item>
          </el-form>
          <div class='marbot'>报修现场照片：
            <input style="display:none;" multiple="multiple" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel($event)">
            <div class='importimg' @click="leadingin">
                点此上传照片...
            </div>
            <div class='name'>
              <div v-for='item in name' :key="item.id" class='itemname'>{{item}},</div>
            </div>
          </div>
          <div  class='btnbox'>
                <button class="search" @click.prevent @click="submit">提交</button>
                <button class="search" @click.prevent @click="closeapplyfor">关闭</button>
          </div>
      </div>


        <div v-show="appraiseshow" class='appraisebox'>
          <div class='vforbox' v-for='item in listdata' :key="item.id">
              <div class='marbot'>报修类型:{{item.M_BXLX}}</div>
                <div class='marbot'>
                    <span>报修人:{{item.M_BXR}}</span>
                    <span>单位:{{item.M_BXDW}}</span>
                </div>
                <div class='marbot'>
                    <span>园区:{{item.M_ZONE}}</span>
                    <span>详细地点:{{item.M_WXDZ}}</span>
                </div>
                <div class='marbot'>报修内容:{{item.M_BXNR}}
                </div>
                <div class='marbot'>报修现场照片:
                      <div class='mbxnrfj' v-for="(i,index) in item.M_BXNRFJ" :key='index'>
                        <img :src="i" alt="">
                      </div>
                </div>
                <div class='backgroundbox'>维修结果</div>
                <div class='marbot'>
                    <span>维修人员:{{item.M_WXR}}</span>
                    <span>维修后设备状态:{{item.M_WXJL}}</span>
                </div>
                <div class='marbot'>报修内容:{{item.M_WXNR}}
                </div>
                <div class='marbot'>维修现场照片:
                      <div class='mbxnrfj' v-for="(o,index) in item.M_WXNRFJ" :key='index'>
                        <img :src="o" alt="">
                      </div>
                </div>
          
                <div class='marbot'>维修评价:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                <div class='marbot'>评价详情: <el-input type="textarea" v-model="wxdetail" class='details'></el-input></div>
                <div class='marbot'>维修后现场照片:
                    <input style="display:none;" multiple="multiple" ref="articleImageFiletwo" id="articleImageFiletwo" type="file" @change="importExceltwo($event)">
                    <div class='importimg' @click="leadingintwo">
                        点此上传照片...
                    </div>
                    <div class='name'>
                      <div v-for='item in nametwo' :key="item.id" class='itemname'>{{item}},</div>
                    </div>
                </div>
                <div  class='btnbox'>
                    <button class="search" @click.prevent @click="publish(item.M_GH)">发表</button>
                    <button class="search" @click.prevent @click="closeappraise">关闭</button>
                </div>
          </div>
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
        </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        value1:0,
        sendOrders:{},
        statistics:[],
        TYPE_NAME:'',
        typecnt:'',
        type:[],
        files:[],
        filestwo:[],
        name:[],
        nametwo:[],
        listdata:[],
        getCount:[],
        typename:[],
        monthctn:[],
        yearctn:[],
        quarterctn:[],
        pageIndex: 1,
        pageSize:1,
        pageSizes:[ 1, 2 , 3, 5, 10],
        total:0,
        echartsshow:true,
        applyforshow:false,
        appraiseshow:false,
        restaurantCode:'',//树状图id
        queryform: {
          mLxdh:'',
          mBxlx:'',
          mZone:'',
          mWxdz:'',
          mBxnr:''
        },
        wxdetail:'',
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      this.treelist()
      this.select()
      this.getData()
    },
    mounted(){
      // this.trigonometry()
      // this.distribution()
      // this.evaluation()
    },
    methods: {
        handleSizeChange (val) {
        this.pageSize=val
        this.selectRepair()
        console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.selectRepair()
        console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
      },
      changeapplyfor(){
          this.echartsshow=false
          this.appraiseshow=false
          this.applyforshow=true
      },
      changeappraise(){
          this.echartsshow=false
          this.appraiseshow=true
          this.applyforshow=false
          this.selectRepair()
      },
      closeapplyfor(){
          this.applyforshow=false
          this.echartsshow=true
      },
      closeappraise(){
          this.appraiseshow=false
          this.echartsshow=true
      },
      // 树状图接口
      treelist(){
        this.$axios({
          url:'StOperationrestaurant.do/getStOperationList',
          method:'get'
        }).then(res=>{
          console.log(res.data)
          this.data=res.data
        })
      },
      // 获取下拉框数据
      select(){
        this.$instance({
          url:'Ufrepair.do/dropDownBox',
          method:'get'
        }).then(res=>{
          this.type=res.data.type
        })
      },
      // 单击上传图片按钮
    importExcel(e){
            this.name.push(this.$refs.articleImageFile.files[0].name)
            this.files.push(this.$refs.articleImageFile.files[0])
    },
    importExceltwo(e){
          this.nametwo.push(document.getElementById('articleImageFiletwo').files[0].name)
          this.filestwo.push(document.getElementById('articleImageFiletwo').files[0])
    },
    // 上传图片按钮
    leadingin(){
        this.$refs.articleImageFile.click()
      },
    leadingintwo(){
        document.getElementById('articleImageFiletwo').click()
      },
  // 提交报修申请
      submit(){
        if(this.files.length==0){
          if(this.queryform.mLxdh==''||this.queryform.mBxlx==''||this.queryform.mZone==''||this.queryform.mWxdz==''||this.queryform.mBxnr==''){
            this.$message('请补全输入框信息')
            return
          }
          this.$instance({
                  url:'/Ufrepair.do/addUfrepair',
                  method:'post',
                  data:{
                    'mBxfj':this.files,
                    "mBxr":"张三",
                    "mBxrgh":"002",
                    "mBxdw":"XXX单位",
                    "mLxdh":this.queryform.mLxdh,
                    "mBxlx":this.queryform.mBxlx,
                    "mZone":this.queryform.mZone,
                    "mWxdz":this.queryform.mWxdz,
                    "mBxnr":this.queryform.mBxnr,
                  }
                }).then(res=>{
                  if(res.data=='提交申请成功'){
                    this.$message.success('提交申请成功')
                    window.setTimeout(function(){
                        location.reload()
                    },1500)
                  }
                })
        }else{
          if(this.queryform.mLxdh==''||this.queryform.mBxlx==''||this.queryform.mZone==''||this.queryform.mWxdz==''||this.queryform.mBxnr==''){
            this.$message('请补全输入框信息')
            return
          }
            for(var i=0;i<this.files.length;i++){
            var reader = new FileReader();
                    reader.readAsDataURL(this.files[i]);
                    var ttt=[]
                    reader.onload = e=>{
                      ttt.push(e.target.result)
                      console.log(e.target.result)
                if(ttt.length==this.files.length){
                    this.$instance({
                      url:'/Ufrepair.do/addUfrepair',
                      method:'post',
                      data:{
                        'mBxfj':ttt,
                        "mBxr":"张三",
                        "mBxrgh":"002",
                        "mBxdw":"XXX单位",
                        "mLxdh":this.queryform.mLxdh,
                        "mBxlx":this.queryform.mBxlx,
                        "mZone":this.queryform.mZone,
                        "mWxdz":this.queryform.mWxdz,
                        "mBxnr":this.queryform.mBxnr,
                      }
                    }).then(res=>{
                      if(res.data=='提交申请成功'){
                        this.$message.success('提交申请成功')
                        window.setTimeout(function(){
                        location.reload()
                        },1500)
                      }
                    })
                }
              }
        }
      }
    },
    // 报修评价
    publish(id){
      if(this.filestwo.length==0){
          if(this.wxdetail==''){
            this.$message('请补全输入框信息')
            return
          }
          this.$instance({
                  url:'Ufrepair.do/repairEvaluate',
                  method:'post',
                  data:{
                    "mWxhfjs":this.filestwo,
                    "mGh":id,
                    "mPjxj":this.value1,
                    "mPjnr":this.wxdetail
                  }
                }).then(res=>{
                  if(res.data=='评价成功'){
                    this.$message.success('评价成功')
                    window.setTimeout(function(){
                        location.reload()
                    },1500)
                  }
                })
        }else{
          if(this.wxdetail==''){
            this.$message('请补全输入框信息')
            return
          }
            for(var i=0;i<this.filestwo.length;i++){
            var reader = new FileReader();
                    reader.readAsDataURL(this.filestwo[i]);
                    var bbb=[]
                    reader.onload = e=>{
                      bbb.push(e.target.result)
                      console.log(e.target.result)
                if(bbb.length==this.filestwo.length){
                    this.$instance({
                      url:'Ufrepair.do/repairEvaluate',
                      method:'post',
                      data:{
                        "mWxhfjs":bbb,
                        "mGh":id,
                        "mPjxj":this.value1,
                        "mPjnr":this.wxdetail
                      }
                    }).then(res=>{
                      if(res.data=='评价成功'){
                        this.$message.success('评价成功')
                        window.setTimeout(function(){
                        location.reload()
                        },1500)
                      }
                    })
                }
              }
        }
      }
    },
    // 查询未评价的报修单
      selectRepair(){
        this.$instance({
          url:'Ufrepair.do/selectRepair',
          method:'post',
          data:{
            "pageNum":this.pageIndex,
            "pageSize":this.pageSize,
            "mBxrgh":"002"
          }
        }).then(res=>{
          this.listdata=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 获取页面数据
      getData(){
        this.$instance({
          url:'Ufrepair.do/getData',
          method:'post',
        }).then(res=>{
          this.getCount=res.data.getCount
          this.repairType=res.data.repairType
          this.typename=res.data.repairType.thisMonth.map(item=>{return {name:item.TYPE_NAME}})
          this.monthctn=res.data.repairType.thisMonth.map(item=>{return item.cnt})
          this.quarterctn=res.data.repairType.thisQuarter.map(item=>{return item.cnt})
          this.yearctn=res.data.repairType.thisYear.map(item=>{return item.cnt})
          this.sendOrders=res.data.sendOrders
          this.statistics=res.data.evaluationAnalysis.statistics
          this.TYPE_NAME=res.data.evaluationAnalysis.good.map(item=>{return '{weatherHead|'+item.TYPE_NAME+'}'}).join("")
          this.typecnt=res.data.evaluationAnalysis.good.map(item=>{return '{weatherHead|'+item.cnt+'}'}).join("")
          this.trigonometry()
          this.distribution()
          this.evaluation()
        })
      },
      // 报修类型分析图
      trigonometry(){
        var myChart = this.$echarts.init(document.getElementById('myChart1'));
         myChart.setOption({
            tooltip: {},
            legend: {
                data: ['本月', '本季度','本年'],
                x:'center',
                y:'bottom',
                padding:[0,0,40,0,],
                textStyle:{
                  color:'white',
                  fontSize:22,
                },
            },
            radar: {
                // shape: 'circle',
                name: {
                    textStyle: {
                        color: '#fff',
                        borderRadius: 3,
                        padding: [3, 5],
                        fontSize:30,
                    },
                },
                splitArea : {
                            show : true,   
                            areaStyle : {
                                color: ["transparent"]  // 图表背景网格的颜色
                            }
                        },
                indicator: this.typename
            },
            series: [{
                name: '',
                type: 'radar',
                itemStyle: {
                      color:'red',
                  },
                data: [
                    {
                        value: this.monthctn,
                        name: '本月',
                        lineStyle:{
                          color:'#5C9DEA',
                          width:'8'
                        },
                        symbolSize:'22',
                        symbol:'circle',
                        itemStyle:{
                          color:'#5C9DEA'
                        },
                        label:{
                          normal:{
                            show:true,
                            color:'white',
                            fontSize:'20'
                          }
                        }
                    },
                    {
                        value:this.quarterctn,
                        name: '本季度',
                        lineStyle:{
                          color:'#C0504D',
                          width:'8'
                        },
                        symbolSize:'22',
                        symbol:'circle',
                        itemStyle:{
                          color:'#C0504D'
                        },
                        label:{
                          normal:{
                            show:true,
                            color:'white',
                            fontSize:'20'
                          }
                        }
                    },
                    {
                        value: this.yearctn,
                        name: '本年',
                        lineStyle:{
                          color:'#9BBB59',
                          width:'8'
                        },
                        symbolSize:'22',
                        symbol:'circle',
                        itemStyle:{
                          color:'#9BBB59'
                        },
                        label:{
                          normal:{
                            show:true,
                            color:'white',
                            fontSize:'20'
                          }
                        }
                    }
                ]
            }]
        });
      },
      // 派单情况分析图
      distribution(){
                var myChart2 = this.$echarts.init(document.getElementById('myChart2'));
                myChart2.setOption({
                legend: {
                data: ['派单量', '保修量','派单率'],
                x:'center',
                y:'bottom',
                textStyle:{
                  color:'white',
                  fontSize:12,
                },
              },
                xAxis:{
                 type: 'category',
                  data:[{
                    value: '本月',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '上月',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '本季度',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '上季度',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '本年',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '上年',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  ],
                  nameLocation:{
                    color:'red'
                  },
                  axisTick: {
                        alignWithLabel: true
                    }
                },
                tooltip: {},
                dataset: {
                
            },
            yAxis: [{
              type:'value',
              splitNumber: 7,
              splitLine:{show: true},//去除网格线
               axisLabel: {
                   textStyle: {
                       color: '#fff'
                   }
               }
            },
                  {
                  type: 'value',
                  position: 'right',
                  splitLine:{show: false},//去除网格线
                  axisLabel: {
                      show:true,
                      interval: 'right',
                      formatter: '{value}%',
                      textStyle: {
                          color: '#fff'
                      }
                },
              }
            ],
            series: [
                {
                  type: 'bar',
                  name:'派单量',
                  color:'#C2D7FF',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:true,
                    position: 'top',
                    color:'#fff'
                  },
                  data: [this.sendOrders.thisMonthPd,this.sendOrders.lastMonthPd, this.sendOrders.thisQuarterPd,this.sendOrders.lastQuarterPd, this.sendOrders.thisYearPd, this.sendOrders.lastYearPd]
                },
                {
                  type: 'bar',
                  name:'保修量',
                  color:'#FFC4C3',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:true,
                    position: 'top',
                    color:'#fff'
                  },
                  data: [this.sendOrders.thisMonthBx, this.sendOrders.lastMonthBx, this.sendOrders.thisQuarterBx,this.sendOrders.lastQuarterBx, this.sendOrders.thisYearBx, this.sendOrders.lastYearBx]
                },
                {
                  type: 'line',
                  name:'派单率',
                  color:'#9BBB59',
                  yAxisIndex: 1,
                  label:{
                      show:true,
                      position:'inside',
                      color:'#fff',
                      formatter:'{c}%'
                  },
                  data: [this.sendOrders.thisMonthRate,this.sendOrders.lastMonthRate, this.sendOrders.thisQuarterRate,this.sendOrders.lastQuarterRate, this.sendOrders.thisYearRate, this.sendOrders.lastYearRate]
                }
            ]
        })
      },
    //报修评价分析图
      evaluation(){
        var myChart3 = this.$echarts.init(document.getElementById('myChart3'));
        myChart3.setOption({
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        top: 10,
                        left: 100,
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
                            center: ['30%', '55%'],
                            
                            clockwise:false,
                            // selectedMode: 'single',
                            data: [
                                {value: this.statistics[0].oneStar, name: '1星',itemStyle:{color:'#F79646'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c}',
                                    }},
                                {value: this.statistics[0].twoStar, name: '2星',itemStyle:{color:'#4BACC6'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c}',
                                    }},
                                {value: this.statistics[0].threeStar, name: '3星',itemStyle:{color:'#8064A2'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c}',
                                    }},
                                {value: this.statistics[0].fourStar, name: '4星',itemStyle:{color:'#B65708'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c}',
                                    }},
                                {
                                  value: this.statistics[0].fiveStar,
                                  name: '5星',
                                  itemStyle:{color:'#6FBDD1'},
                                  label: {
                                        position: 'outside',
                                        show:true,
                                        formatter: [
                                          '{title|{b}}{abg|}',
                                          ''+this.TYPE_NAME+'',
                                          '{hr|}',
                                          ' '+ this.typecnt+'',
                                      ].join('\n'),
                                        borderWidth: 1,
                                        borderRadius: 4,
                                        rich: {
                            title: {
                                color: '#eee',
                                align: 'center'
                            },
                            abg: {
                                // backgroundColor: 'black',
                                width: '100%',
                                align: 'right',
                                height: 25,
                                color:'white',
                                borderRadius: [4, 4, 0, 0]
                            },
                            weatherHead: {
                                color: 'white',
                                width:26,
                                height: 24,
                                align: 'left',
                                padding: [0, 20, 0,5 ],

                            },
                            hr: {
                                borderColor: '#777',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            },
                            value: {
                                width: 20,
                                padding: [10, 20, 10, 25],
                                color:'white',
                                align: 'left'
                            },
                            }
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
                }

        )
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
    overflow-y:auto ;
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
  /deep/.el-input{
      width: 200px;
  }
  .address{
      width:800px;
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
  /deep/.el-textarea {
    width: 1300px;
    height: 200px;
}
/deep/.el-textarea__inner {
    width: 1300px;
    height: 200px;
    background-color: black;
    color: white;
    font-size: 12px;
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
  .titletop{
      margin:20px 0;
  }
  .imgbox{
      width: 180px;
      height: 102px;
      background:url(../../assets/u239.svg);
      background-size: 100% 100%;
      display: inline-block;
      margin-right:30px;
      color: white;
  }
  .imgboxtop{
      width: 180px;
      height: 40px;
      line-height:50px;
      text-align: center;
      font-size: 12px;
  }
  .imgboxleft{
      width: 180px;
      height: 62px;
      line-height: 62px;
      font-size: 32px;
      font-weight: 500;
      text-align: center;
  }
  .applyfor{
      width: 180px;
      height: 102px;
      line-height: 102px;
      text-align: center;
      font-size: 13px;
      display: inline-block;
      margin: 0 30px 0 50px;
      background-color: #029214;
      vertical-align: top;
      color: white;
  }
  .appraise{
      width: 180px;
      height: 102px;
      line-height: 102px;
      text-align: center;
      font-size: 13px;
      display: inline-block;
      background-color: #BFBF00;
      vertical-align: top;
      color: white;
  }
  .applyfor,.appraise:hover{
      cursor: pointer;
  }
  .contentbox{
      width: 100%;
      height: 700px;
  }
  .contentleft{
      width: 58%;
      height: 100%;
    //   background-color: red;
      display: inline-block;
      margin-right: 20px;
      vertical-align: top;
      position: relative;
  }
  .contentright{
      width: 40%;
      height: 100%;
      display: inline-block;
      vertical-align: top;
  }
  .repairtype{
      width: 100%;
      height: 40px;
      line-height: 40px;
      padding:0 0 0 5px;
      box-sizing: border-box;
      color: white;
      background: url(../../assets/topline.png)no-repeat;
      background-size: 100% 100%;
      font-size: 12px;
  }
  .bottomline{
      width: 100%;
      height: 5px;
      background: url(../../assets/botline.png)no-repeat;
      background-size:100% 100%;
      position: absolute;
      bottom:0;
  }
  .distribution{
      width: 100%;
      height: 340px;
      margin-bottom: 20px;
      position: relative;
  }
  .evaluation{
      width: 100%;
      height: 340px;
      position: relative;
  }
  .application{
      width: 100%;
      padding:70px 0 20px 70px;
      box-sizing: border-box;
      color: white;
      font-size: 12px;
  }
  .appraisebox{
      width: 100%;
      padding:30px 0 20px 70px;
      box-sizing: border-box;
      color: white;
      font-size: 12px;
  }
  .marbot{
      margin-bottom: 20px;
  }
  .btnbox{
      text-align: right;
      padding:20px 100px 0 0;
      box-sizing: border-box;
  }
  .backgroundbox{
      width: 100%;
      height: 40px;
      line-height: 40px;
      font-size: 13px;
      background: url(../../assets/1_u3579.png) no-repeat;
      background-size: 100% 100%;
      margin-bottom: 20px;
  }
  .marbot span{
      margin-right: 60px;
  }
/deep/.el-rate{
    display: inline-block;
    vertical-align: middle;
}
  .rateclass{
    display: inline-block;
    height:100%;
}
/deep/.el-rate__icon{
    font-size: 32px;
}
.details{
    height: 100px;
    vertical-align: top;
}
.details /deep/.el-textarea__inner{
    height: 100px;
}
.myChart1{
  width: 100%;
  height: 660px;
}
.myChart2{
  width: 100%;
  height: 290px;
}
.myChart3{
  width: 100%;
  height: 290px;
}
.importimg{
  display: inline-block;
}
.importimg:hover{
  cursor: pointer;
}
.name{
  display: inline-block;
  margin: 0 20px;
}
.itemname{
  display: inline-block;
  margin:0 5px;
}
/deep/.number,/deep/.btn-prev,/deep/.btn-next{
  background: transparent;
  color: white;
}
/deep/.el-pagination button:disabled{
  background: transparent;
}
.vforbox{
  margin-bottom: 80px;
}
.mbxnrfj{
  display: inline-block;
  vertical-align: top;
  width: 100px;
  height: 57px;
  margin:0 10px;
}
.mbxnrfj img{
  width: 100px;
  height: 57px;
}
</style>

