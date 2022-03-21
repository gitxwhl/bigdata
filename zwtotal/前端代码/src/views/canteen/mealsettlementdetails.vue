<template>
  <div class='container'>
    <div class='meatitle'>就餐结算明细</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
    <div class='dataleftbox'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='5' class='iptcol'>
            <el-form-item >
              <el-date-picker
                v-model="queryform.startTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker><span class="fhbox">-</span>
            </el-form-item>
          </el-col>

          <el-col :span='5' class='iptcol'>
            <el-form-item>
              <el-date-picker
                v-model="queryform.endTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="pagelist">查询</button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="scheduled" label="日期"> </el-table-column>
        <el-table-column align="center"  min-width="10%" prop="mealType" label="餐别"></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="no" label="员工编号" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="name" label="姓名" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="dishname" label="菜品名称" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="category" label="菜品分类" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="taste" label="菜品口味" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="10%"  prop="dishNum" label="用餐数量" show-overflow-tooltip></el-table-column>
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
    <div class='datarightbox'>
        <div class='imgbox'>
            <div class='boxhead'>总销量Top5</div>
            <div class='boxbody' @mouseenter="clearinter" @mouseleave="setInter">
                <div class='fixedbox'>
                  <div class='setbox'>
                    <div class='staplefood'>
                        <div class='borderbox'></div>
                        <div class='leftlist'>
                            <div class='marbot'>主食销量排行榜</div>
                            <div class='marbot' v-for='(item,index) in allzs' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> 
                            </div>
                        </div>
                        <div class='rightlist'>
                            <div class='marbot'>汤类销量排行榜</div>
                            <div class='marbot'  v-for='(item,index) in alltl' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                    </div>

                    <div class='staplefood2'>
                        <div class='borderbox'></div>
                        <div class='leftlist'>
                            <div class='marbot'>凉菜销量排行榜</div>
                            <div class='marbot'  v-for='(item,index) in alllc' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                        <div class='rightlist'>
                            <div class='marbot'>热菜销量排行榜</div>
                            <div class='marbot'  v-for='(item,index) in allrc' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                    </div>


                    <div class='staplefood5'>
                      <div class='borderbox'></div>
                        <div class='leftlist'>
                            <div class='marbot'>水果销量排行榜</div>
                            <div class='marbot'  v-for='(item,index) in allsg' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                    </div>


                  </div>

                </div>
            </div>
        </div>





         <div class='imgbox'>
            <div class='boxhead'>线上销量Top5</div>
            <div class='boxbody' @mouseenter="clearinter2" @mouseleave="setInter2">
                <div class='fixedbox'>
                  <div class='setbox'>
                    <div class='staplefood3'>
                      <div class='borderbox'></div>
                        <div class='leftlist'>
                            <div class='marbot'>主食销量排行榜</div>
                            <div class='marbot' v-for='(item,index) in onlinezs' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                        <div class='rightlist'>
                            <div class='marbot'>汤类销量排行榜</div>
                            <div class='marbot' v-for='(item,index) in onlinetl' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                    </div>

                    <div class='staplefood4'>
                      <div class='borderbox'></div>
                        <div class='leftlist'>
                            <div class='marbot'>凉菜销量排行榜</div>
                            <div class='marbot' v-for='(item,index) in onlinelc' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                        <div class='rightlist'>
                            <div class='marbot'>热菜销量排行榜</div>
                            <div class='marbot' v-for='(item,index) in onlinerc' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                    </div>


                  <div class='staplefood6'>
                    <div class='borderbox'></div>
                        <div class='leftlist'>
                            <div class='marbot'>水果销量排行榜</div>
                            <div class='marbot'  v-for='(item,index) in onlinesg' :key='index'>
                              <div style="display:inline-block;vertical-align:top;"><div>Top {{index+1}} {{item.NAME}} {{item.num}}份</div><div style="text-indent:3em;">总销售额 {{item.price}}元</div></div> </div>
                        </div>
                </div>

                  </div>



                


                </div>
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
        alllc:[],
        allrc:[],
        allsg:[],
        alltl:[],
        allzs:[],
        onlinelc:[],
        onlinerc:[],
        onlinesg:[],
        onlinetl:[],
        onlinezs:[],
        pageIndex: 1,
        topjl:0,
        topjl2:350,
        topjl3:0,
        topjl4:350,
        topjl5:700,
        topjl6:700,
        pageSize:10,
        inter:'',
        inter2:'',
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        mealType: [],//餐别,
        queryform: {
          startTime:'',
          endTime:''
        },
        tableData: [],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    mounted(){
      this.setInter()
      this.setInter2()
    },
    created(){
      this.treelist()
      this.pagelist()
    },
    destroyed: function () {
      clearInterval(this.inter)
      clearInterval(this.inter2)
    },
    methods: {
      setInter(){
        this.inter=setInterval(this.set,30)
      },
      setInter2(){
        this.inter2=setInterval(this.set2,30)
      },
      set(){
        var staplefood=document.querySelector('.staplefood')
        var staplefood2=document.querySelector('.staplefood2')
        var staplefood5=document.querySelector('.staplefood5')
        
        if(this.topjl<-350){
          this.topjl=700
          this.topjl+=-1
          staplefood.style.top=this.topjl+'px'
        }else if(this.topjl2<-350){
          this.topjl2=700
          this.topjl2+=-1
        staplefood2.style.top=this.topjl2+'px'
        }else if(this.topjl5<-350){
          this.topjl5=700
          this.topjl5+=-1
        staplefood5.style.top=this.topjl5+'px'

        }
        else{
          this.topjl+=-1
          this.topjl2+=-1
          this.topjl5+=-1
          staplefood.style.top=this.topjl+'px'
          staplefood2.style.top=this.topjl2+'px'
          staplefood5.style.top=this.topjl5+'px'
        }
      },
      set2(){
        var staplefood3=document.querySelector('.staplefood3')
        var staplefood4=document.querySelector('.staplefood4')
        var staplefood6=document.querySelector('.staplefood6')
        
        if(this.topjl3<-350){
          this.topjl3=700
          this.topjl3+=-1
          staplefood3.style.top=this.topjl3+'px'
        }else if(this.topjl4<-350){
          this.topjl4=700
          this.topjl4+=-1
          staplefood4.style.top=this.topjl4+'px'
        }else if(this.topjl6<-350){
          this.topjl6=700
          this.topjl6+=-1
          staplefood6.style.top=this.topjl6+'px'
        }
        else{
          this.topjl3+=-1
          this.topjl4+=-1
          this.topjl6+=-1
          staplefood3.style.top=this.topjl3+'px'
          staplefood4.style.top=this.topjl4+'px'
          staplefood6.style.top=this.topjl6+'px'
        }
      },
      clearinter(){
        window.clearInterval(this.inter)
      },
      clearinter2(){
        window.clearInterval(this.inter2)
      },
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
        this.pagelist()
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
          console.log(res)
          this.data=res.data
        })
      },
      // 就餐结算明细列表
      pagelist(){
        this.$axios({
          url:'StFinanceManagement.do/detailList',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "restaurant":this.restaurantCode,
            "startTime":this.queryform.startTime,
            "endTime":this.queryform.endTime,
          }
        }).then(res=>{
          this.tableData=res.data.pageBean.list
          this.total=res.data.pageBean.totalRecord
          this.alllc=res.data.all.lc
          this.allrc=res.data.all.rc
          this.allsg=res.data.all.sg
          this.alltl=res.data.all.tl
          this.allzs=res.data.all.zs
          this.onlinelc=res.data.online.lc
          this.onlinerc=res.data.online.rc
          this.onlinesg=res.data.online.sg
          this.onlinetl=res.data.online.tl
          this.onlinezs=res.data.online.zs
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
    width: 160px;
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
    padding: 0 30px;
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
    height: 820px!important;
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
  .dataleftbox{
      display: inline-block;
      width: 76%;
  }
  .datarightbox{
      width: 22%;
      margin:60px 0 0 20px;
      display: inline-block;
      vertical-align: top;
  }
  /deep/.el-table__body-wrapper{
      height: 780px;
      overflow-y: auto;
  }
  .boxhead{
        font-size: 13px;
        text-align: left;
        padding-left:15px;
        box-sizing: border-box;
        background: url(../../assets/top.png)no-repeat;
        background-size:100% 100% ;
        line-height:36px;
        color:white;
        width: 100%;
        height: 36px;
  }
  .boxbody{
        font-size:12px;
        background: url(../../assets/body.png)no-repeat;
        background-size:100% 100%;
        color:white;
        width: 100%;
        height: 373px;
        padding:15px;
        box-sizing: border-box;
  }
  .imgbox{
      margin-bottom:30px;
      height: 400px;
  }
  .fixedbox{
      overflow:hidden;
      width: 100%;
      height: 100%;
      background: #003366;
      
  }
  .setbox{
    width: 100%;
    height: 100%;
    position: relative;
  }
  .staplefood{
      position: absolute;
      top:0px;
      width: 100%;
      // height: 100%;
      padding:20px 0;
      box-sizing:border-box;
      
  }
    .staplefood3{
      position: absolute;
      top:0px;
      width: 100%;
      // height: 100%;
      padding:20px 0;
      box-sizing:border-box;
      
  }
 .staplefood2{
      position: absolute;
      top:350px;
      width: 100%;
      // height: 100%;
      padding: 40px 0;
      box-sizing:border-box;
  }
  .staplefood5{
     position: absolute;
      top:700px;
      width: 100%;
      // height: 100%;
      padding: 40px 0;
      box-sizing:border-box;
  }
  .staplefood6{
     position: absolute;
      top:700px;
      width: 100%;
      // height: 100%;
      padding: 40px 0;
      box-sizing:border-box;
  }
  .staplefood4{
      position: absolute;
      top:350px;
      width: 100%;
      // height: 100%;  
      padding: 40px 0;
      box-sizing:border-box;
  }
  .borderbox{
    position: absolute;
    left:50%;
    top: 40px;
    height: 75%;
    border-left:1px dashed #797979;
  }
  .leftlist{
      width: 50%;
      // border-right:1px dashed #797979 ;
      display: inline-block;
      padding:10px 0 0 10px;
      box-sizing: border-box;
  }
  .rightlist{
      width: 50%;
      // border-left:1px dashed #797979 ;
      display: inline-block;
      padding:10px 0 0 10px;
      box-sizing: border-box;
      vertical-align:top;
  }
  .marbot{
      margin-bottom: 12px;
  }
</style>