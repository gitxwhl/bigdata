<template>
    <div class='container'>
        <div class='meatitle'>在线购物</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-row class="iptrow">
                <el-col :offset="6" :span='12' class='iptcol'>
                        <el-input class="input" v-model="name" placeholder="请输入菜品名称/口味/品牌"></el-input>
                </el-col>
                <el-col :span='3' class='iptcol'>
                     <button class='search' @click.prevent @click="pagelist()">搜索</button>
                </el-col>
            </el-row>
            <div class='classify'>
                 <div class='fenlei'>分类:</div>
                 <div class='fenleiname'>
                     <div class='fenleifather'>
                         <div class='vforfather' ref='market' v-for="(item,index) in markettype" :key="index" @click="getinfor(item.id,index)">{{item.branchname}}</div>
                     </div>
                     <div class='fenleison'>
                         <div class='vforson' ref='page' v-for="(item,index) in twoinfor" :key="index" @click="page(item.id,index)">{{item.branchname}}</div>
                     </div>
                 </div>
            </div>


            <div class='shopbox' >
                <div class='shopsonbox'  v-for='(item,index) in shopsonbox' :key='index'>
                    <img src="../../assets/u16426.jpg" alt="">
                    <!-- <img :src="item.picture" alt=""> -->
                    <div class='shopname'>
                        <span>{{item.name}}</span>
                        <span>{{item.weight}}</span>
                    </div>
                    <div class='shopcontent'>{{item.introduction}}</div>
                    <div class='shopcontent'>{{item.price}}</div>
                    <div class='numberbox'>
                        <el-input-number size="mini" v-model="num[index]" @change="handleChange($event,index)" :min="0" label="描述文字"></el-input-number>
                    </div>
                </div>
            </div>


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
    </el-card>



    <el-card class='shoppingcard'>
        <div class='cartitle'>购物车</div>
        <div class='cartop'>
            <el-checkbox v-model="checked"></el-checkbox>
            <div class='selectmount'>共xx件，已选xx件</div>
        </div>
        <div class='carcontent' v-for='(item,index) in carshop' :key="index">
            <el-checkbox  v-model="checked"></el-checkbox>
            <div class='carimgname'>
                <img src="../../assets/u16607.svg" alt="">
                <div class='carname'>
                    <div class='carnametop'>苏打饼干</div>
                    <div style="text-align:center;">
                        <el-input-number size="mini" v-model="numtwo[index]" @change="handleChange" :min="0" :max="10" label="描述文字"></el-input-number>
                    </div>
                </div>
            </div>
            <div class='total'>
                18
            </div>
        </div>
        <div class='noshop'>
            没有更多商品啦
        </div>
        <div class='absolute'>
            总价:54.00
            <button class='order' style="vertical-align:top;" @click.prevent >下单结算</button>
        </div>
    </el-card>
    </div>


</template>

<script>
export default {
  data () {
    return {
      num:[],
      numtwo:[],
      markettype:[],
      twoinfor:[],
      typeid:0,
      checked:false,
      shopsonbox:[{},{},{},{},{},{},{}],
      carshop:[],
      pageIndex: 1,
      pageSize:20,
      pageSizes:[ 20, 30 , 40, 50, 100],
      total:0,
      restaurantCode:'',//树状图id
      name:'',
      data: [],
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    this.treelist()
    this.pagelist()
    this.getStOnlineSupermarketType()
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
    handleChange(value,i) {
        console.log(value,i,this.num[i]);
            this.carshop=[]
            this.carshop=this.num.filter(item=>{
                return item>0
            })
            this.numtwo=this.carshop
            console.log(this.carshop)
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
    // 分类第一排名字
    getStOnlineSupermarketType(){
        this.$axios({
            url:'/StOnlineSupermarket.do/getStOnlineSupermarketType',
            method:'get'
        }).then(res=>{
            this.markettype=res.data
        })
    },
    // 二级分类
    getinfor(id,i){
        for(var o=0;o<this.$refs.market.length;o++){
            this.$refs.market[o].style.background='none'
        }
        this.$refs.market[i].style.background='red'
        this.typeid=id
        this.$axios({
            url:'StOnlineSupermarket.do/getStOnlineSupermarketTypeTwo',
            method:'post',
            data:{
                'id':id
            }
        }).then(res=>{
            this.twoinfor=res.data
            this.pagelist()
        })
    },
    // 点击二级类别
    page(id,i){
        for(var j=0;j<this.$refs.page.length;j++){
            this.$refs.page[j].style.background='none'
        }
        this.$refs.page[i].style.background='red'
        this.typeid=id
        this.pagelist()
    },
    // 查询列表
    pagelist(){
        this.$axios({
            url:'StOnlineSupermarket.do/getStOnlineSupermarket',
            method:'post',
            data:{
                'pageNum': this.pageIndex,
                'pageSize':this.pageSize,
                'id':this.restaurantCode,
                'typeid':this.typeid,
                'name':this.name,
            }
        }).then(res=>{
            this.total=res.data.data.totalRecord
            this.shopsonbox=res.data.data.list
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
    margin-right: 10px;
}
.shoppingcard{
    overflow-y: auto;
    background-color:rgba(1,5,22,.4);
    padding:20px;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width: 22%;
    height: 820px;
    float: right;
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
    padding:0 20px 0 30px;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:61%;
    height: 860px;
    float: left;
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
    padding-left:30px;
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
.order{
    color:rgb(255,255,255) ;
    float: right;
    font-size: 10px;
    width: 90px;
    height:31px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color:#01020E;
}
.search,.order:hover{
  cursor: pointer;
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
.input{
  width: 514px;
}
/deep/.el-input__icon{
  line-height: 28px;
  width: 25px;
}
/deep/.el-input__prefix{
  left:5px;
}
.el-pagination /deep/.el-input__inner{
    padding:0px;
}
.classify{
    width: 100%;
    height: 80px;
    box-sizing: border-box;
    background:url(../../assets/u8373.png)no-repeat;
    background-size:100% 100%;
    margin:10px;
}
.fenlei{
    color: #fff;
    font-size: 12px;
    width: 100px;
    height: 100%;
    float: left;
    padding:11px 0 0 30px;
    box-sizing: border-box;
}
.fenleiname{
    color: #fff;
    font-size: 12px;
    height: 100%;
    float: left;
    box-sizing: border-box;
}
.fenleifather{
    // height: 50%;
    // line-height: 44px;
    margin:10px 0 0 0;
}
.fenleison{
    margin:10px 0 0 0 ;
}
.vforfather{
    display: inline-block;
    margin-right: 40px;
    padding: 1px 6px;
    border: 1px solid rgba(0,0,0,0);
    box-sizing: border-box;
    border-radius: 5px;
}
.vforson{
    display: inline-block;
    margin-right: 30px;
    padding: 1px 6px;
    border: 1px solid rgba(0,0,0,0);
    border-radius: 5px;
    box-sizing: border-box;
}
.vforson:hover{
    cursor: pointer;
    background-color: #FE0099;
}
.vforfather:hover{
    cursor: pointer;
    background-color: #FE0099;
}
.shopbox{
    width: 100%;
}
.shopsonbox img{
    width: 200px;
    height: 155px;
}
.shopsonbox{
    display: inline-block;
    vertical-align: middle;
    font-size: 12px;
    color: white;
    width: 200px;
    height: 280px;
    margin:0 15px 20px 0;
    overflow-y: auto;
    overflow-x: hidden;
}
.shopname{
    margin:5px 0;
}
.shopname span{
    margin-right: 10px;
}
.shopcontent{
    margin:0 0 5px 0;
    // white-space:nowrap;
    // overflow:hidden;
    // text-overflow:ellipsis;
}
// .shopcontent:hover{
//     overflow: visible;
//     white-space: inherit;
// }
.numberbox{
    text-align: center;
    margin-left: 60px;
}
/deep/.el-input-number{
    width: 130px;
}
/deep/.el-input-number--mini{
    line-height: 26px;
}
/deep/.el-input-number /deep/.el-input__inner{
    height: 30px;
    width: 60px;
    padding: 0 10px;
}
/deep/.el-input-number__increase,/deep/.el-input-number__decrease{
    border-radius: 50%;
    font-size: 24px;
    font-weight: 900;
    width: 28px;
    height: 28px;
    line-height: 28px;
}
.cartitle{
    color: white;
    font-size: 12px;
}
.cartop{
    width: 100%;
    height: 33px;
    background: url(../../assets/u8373.png)no-repeat;
    background-size: 100% 100%;
    line-height: 33px;
    padding:0 10px;
    box-sizing: border-box;
}
.selectmount{
    font-size:13px;
    color: white;
    float: right;
}
.carcontent{
    width: 100%;
    border-top:1px solid #797979;
    line-height: 80px;
    padding: 10px;
    box-sizing: border-box;
}
/deep/.el-checkbox{
    vertical-align: middle;
}
.carimgname{
    display: inline-block;
    vertical-align: middle;
    line-height: 1;
    margin:0 10px 0 10px;
}
.carimgname img{
    width: 92px;
    height: 54px;
}
.carname{
    display: inline-block;
    height: 100%;
    vertical-align: top;
    font-size: 12px;
    color: white;
    margin:0 0 0 10px;
}
.carnametop{
    height: 30px;
}
.total{
    display: inline-block;
    font-size: 12px;
    color: white;
    margin-left: 30px;
}
.noshop{
    color: white;
    font-size: 12px;
    text-align: center;
}
.absolute{
    width: 100%;
    height: 33px;
    border: 1px solid white;
    background: url(../../assets/u8373.png)no-repeat;
    background-size: 100% 100%;
    line-height: 33px;
    font-size: 12px;
    color: white;
    box-sizing: border-box;
    margin-top: 100px;
}
</style>
