<template>
    <div class='container'>
        <div class='meatitle'>餐柜管理</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
            <el-row class="iptrow">
                <el-col :span='4' class='iptcol'>
                    <el-form-item label="餐柜编号:">
                           <el-input class='name' v-model="queryform.number"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='4' class='iptcol'>
                    <el-form-item label="餐柜名称:">
                           <el-input class='name' v-model="queryform.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='9' class='iptcol'>
                    <el-form-item  label="餐柜状态:">
                        <el-select  v-model="queryform.state">
                             <el-option v-for="item in sideboardState" :key="item.id" :label="item.text" :value="item.id">
                             </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click='pagelist'>查询</button>
                    </el-form-item>
                </el-col>
                <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click='information'>餐柜上货</button>
                    </el-form-item>
                </el-col>
                <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click='changeinfor'>更换订单</button>
                    </el-form-item>
                </el-col>
                <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click='emptycabinet'>清空餐柜</button>
                    </el-form-item>
                </el-col>
            </el-row>
            </el-form>
        <el-table id='tableid' @change='console.log(1)' @selection-change='selectevent' stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             height="740px" size="small"  style="width: 100%;margin-top:0px;">
                <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="6%" prop="number" label="餐柜编号"> </el-table-column>
                <el-table-column align="center"  min-width="6%" prop="name" label="餐柜名称"></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="addr" label="具体地点" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="state" label="餐柜状态" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="reservepersonnel" label="订餐人员" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="telephone" label="联系电话" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="picktime" label="取餐时间" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="launchtime" label="上架时间" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="shelf" label="上架人员" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="mode" label="取餐方式" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="18%"  prop="details" label="菜品明细" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%" label="操作" show-overflow-tooltip></el-table-column>
        </el-table>
                <!-- 餐柜上货弹框 -->
                <el-dialog class='dialog'  title="上柜信息" :visible.sync="dialoginfoVisible" @close='emptyclose'>
                            <el-form :model="addform" ref="addform" size="mini" class='queryform'>
                                <el-row class="iptrow">
                                    <el-col :span='8' class='iptcol'>
                                        <el-form-item label="上架人员:">
                                            <el-select  v-model="addform.shelf">
                                                <el-option v-for="item in person" :key="item.id" :label="item.name" :value="item.id">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item label="上架时间:">
                                            <el-date-picker
                                                v-model="addform.launchTime"
                                                value-format="yyyy-MM-dd"
                                                type="date"
                                                placeholder="请选择日期">
                                            </el-date-picker>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <div>
                                    <div class='vforinfo' v-for="(item,index) in addform.param" :key="index">
                                      <el-col :span='24' class='iptcol'>
                                        <el-form-item label="餐柜名称:">
                                            <el-select  v-model="item.id">
                                                <el-option v-for="item in emptyCabinet" :key="item.id" :label="item.text" :value="item.id">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                      </el-col>
                                      <el-col :span='24' class='iptcol'>
                                        <el-form-item label="订单编号:">
                                            <el-select  v-model="item.orderNo" @change='getOrderInfo(item.orderNo,index)'>
                                                <el-option v-for="item in cabinOrder" :key="item.orderNum" :label="item.orderNum" :value="item.orderNum">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                      </el-col>
                                      <div class='details'>
                                        订单详情:
                                        <div style='text-indent:2em;'>
                                          {{item.details}}
                                        </div> 
                                      </div>
                                    </div>

                                </div>
                        </el-form>
                            <div slot="footer" class="dialog-footer">
                                <button class='search' @click.prevent @click="confirmbtn">确认</button>
                                <button class='search' @click.prevent @click="empty">清空</button>
                              </div>
                    </el-dialog>




          <!-- 更换订单弹框 -->
                <el-dialog class='dialog'  title="更换上柜信息" :visible.sync="dialogchangeVisible" @close='emptyclosetwo'>
                            <el-form :model="addform" ref="addform" size="mini" class='queryform'>
                                <el-row class="iptrow">
                                    <el-col :span='8' class='iptcol'>
                                        <el-form-item label="上架人员:">
                                            <el-select  v-model="changeform.shelf">
                                                <el-option v-for="item in person" :key="item.id" :label="item.name" :value="item.id">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item label="上架时间:">
                                            <el-date-picker
                                                v-model="changeform.launchTime"
                                                value-format="yyyy-MM-dd"
                                                type="date"
                                                placeholder="请选择日期">
                                            </el-date-picker>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <div>
                                    <div class='vforinfo' v-for='(item,index) in changeform.selectdata' :key='index'>
                                      <el-col :span='24' class='iptcol'>
                                            <div class='itemid'>
                                              餐柜名称:<span>{{item.name}}</span>
                                            </div>
                                      </el-col>
                                      <el-col :span='24' class='iptcol'>
                                        <el-form-item label="订单编号:">
                                            <el-select  v-model="item.orderNo"  @change="getOrderInfotwo(item.orderNo,index)">
                                                <el-option v-for="item in cabinOrder" :key="item.orderNum" :label="item.orderNum" :value="item.orderNum">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                      </el-col>
                                      <div class='details'>
                                        订单详情:
                                        <div style='text-indent:2em;'>
                                          {{item.details}}
                                        </div> 
                                      </div>
                                    </div>

                                </div>
                        </el-form>
                            <div slot="footer" class="dialog-footer">
                                <button class='search' @click.prevent @click="confirmbtntwo">确认</button>
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
      dialoginfoVisible:false,
      dialogchangeVisible:false,
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      restaurantCode:'',//树状图id
      queryform: {
        number: '',
        name: '',
        state:'',
      },
      addform:{
        shelf:'',
        launchTime:'',
        param:[
          {
            id:'',
            orderNo:'',
            details:''
          },
          {
            id:'',
            orderNo:'',
            details:''
          },
          {
            id:'',
            orderNo:'',
            details:''
          },
          {
            id:'',
            orderNo:'',
            details:''
          },
          {
            id:'',
            orderNo:'',
            details:''
          },
          {
            id:'',
            orderNo:'',
            details:''
          },
          {
            id:'',
            orderNo:'',
            details:''
          },
          {
            id:'',
            orderNo:'',
            details:''
          },
          {
            id:'',
            orderNo:'',
            details:''
          },
          
        ]
      },
      changeform:{
        shelf:'',
        launchTime:'',
        selectdata:[]
      },
      addformtj:{
        shelf:'',
        launchTime:'',
        param:[]
      },
      changeformtj:{
        shelf:'',
        launchTime:'',
        param:[]
      },
      sideboardState:[],
      person:[],
      emptyCabinet:[],
      cabinOrder:[],
      tableData: [],
      data: [],
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    this.treelist()
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
    information(){
        this.dialoginfoVisible=true
    },
    changeinfor(){
      if(this.changeform.selectdata.length==0){
        this.$message('请选择需要更换的餐柜')
        return
      }
        this.dialogchangeVisible=true
    },
    selectevent(selection){
        this.changeform.selectdata=selection.map(item=>{
          return {
            name:item.name,
            id:item.id,
            orderNo:item.orderNo,
            details:item.details
          }
        })
      console.log(selection,this.changeform.selectdata)

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
      this.$axios({
        url:'StCabinet.do/dropDownBox',
        method:'get'
      }).then(res=>{
        this.sideboardState=res.data.sideboardState
        this.person=res.data.person
        this.emptyCabinet=res.data.emptyCabinet
        this.cabinOrder=res.data.cabinOrder
      })
    },
    //餐柜管理列表
    pagelist(){
        this.$axios({
            url:'StCabinet.do/getCabinet',
            method:'post',
            data:{
                "pageNum": this.pageIndex,
                "pageSize":this.pageSize,
                "restaurant":this.restaurantCode,
                "number":this.queryform.number,
                "name":this.queryform.name,
                "state":this.queryform.state,
            }
        }).then(res=>{
            this.tableData=res.data.list
            this.total=res.data.totalRecord
            this.selectlist()
        })
    },
    // 查看详情
    getOrderInfo(number,index){
      this.$axios({
        url:'StCabinet.do/getOrderInfo',
        method:'post',
        data:{
          "orderNumber":number
        }
      }).then(res=>{
        this.addform.param[index].details=res.data.details
      })
    },
    getOrderInfotwo(number,index){
      this.$axios({
        url:'StCabinet.do/getOrderInfo',
        method:'post',
        data:{
          "orderNumber":number
        }
      }).then(res=>{
        this.changeform.selectdata[index].details=res.data.details
      })
    },
    // 确认
    confirmbtn(){
      this.addformtj.launchTime=this.addform.launchTime,
      this.addformtj.shelf=this.addform.shelf,
      this.addformtj.param=this.addform.param.map(item=>{
        return {
          id:item.id,
          orderNo:item.orderNo
        }
      })
      if(this.addformtj.launchTime==''||this.addformtj.shelf==''){
        this.$message('请选择上架人员和上架时间')
        return
      }
      console.log(this.addformtj)
      this.$axios({
          url:'StCabinet.do/addOrder',
          method:'post',
          data:this.addformtj
      }).then(res=>{
        if(res.data='操作成功'){
          this.$message.success('操作成功')
          this.pagelist()
        }else{
          this.$message.error('操作失败')
        }
      })
      this.addform.launchTime=''
      this.addform.shelf=''
      for(var i=0;i<this.addform.param.length;i++){
        this.addform.param[i].id=''
        this.addform.param[i].orderNo=''
        this.addform.param[i].details=''

      }
      console.log(this.addform)
      this.dialoginfoVisible=false
    },
    // 确认
    confirmbtntwo(){
      this.changeformtj.launchTime=this.changeform.launchTime
      this.changeformtj.shelf=this.changeform.shelf
      this.changeformtj.param=this.changeform.selectdata.map(item=>{
        return {
          id:item.id,
          orderNo:item.orderNo
        }
      })
      if(this.changeformtj.launchTime==''||this.changeformtj.shelf==''){
        this.$message('请选择上架人员和上架时间')
        return
      }
        this.$axios({
          url:'StCabinet.do/updateOrder',
          method:'post',
          data:this.changeformtj,
        }).then(res=>{
          if(res.data=='操作成功'){
            this.$message.success('更换订单成功')
            this.pagelist()
          }else{
            this.$message.error('更换订单失败')
          }
        })
        this.dialogchangeVisible=false
    },
    // 清空
    empty(){
      this.addform.launchTime=''
      this.addform.shelf=''
      for(var i=0;i<this.addform.param.length;i++){
        this.addform.param[i].id=''
        this.addform.param[i].orderNo=''
        this.addform.param[i].details=''
      }
    },
    // 关闭按钮
    emptyclose(){
      this.addform.launchTime=''
      this.addform.shelf=''
      for(var i=0;i<this.addform.param.length;i++){
        this.addform.param[i].id=''
        this.addform.param[i].orderNo=''
        this.addform.param[i].details=''
      }
      this.dialoginfoVisible=false
    },
    // 修改关闭按钮
    emptyclosetwo(){
      this.dialogchangeVisible=false
      this.pagelist()
    },
    // 清空餐柜
    emptycabinet(){
      this.$confirm('确认清空货柜?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        this.$axios({
        url:'StCabinet.do/emptyCabinet',
        method:'post',
      }).then(res=>{
        if(res.data=='清空餐柜成功'){
          this.$message.success('清空餐柜成功')
          this.pagelist()
        }
      })
      }).catch(()=>{

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
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 90px;
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
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  width: 800px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
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
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;
  background:transparent;
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
    width: 140px;
}
.el-select /deep/.el-input__inner{
    padding: 0 30px 0 15px;
}
.vforinfo{
    width: 233px;
    height: 188px;
    padding: 10px;
    box-sizing: border-box;
    background: url(../../assets/1_u3667.png)no-repeat;
    background-size: 100% 100%;
    display: inline-block;
    margin: 0 20px 15px 0;
    vertical-align: middle;
}
.details{
  font-size: 12px;
}
.itemid{
  width: 100%;
  // height: 27px;
  font-size: 12px;
}
.itemid span{
  margin-left:15px ;
}
</style>
