<template>
        <el-container class="home-container">
        <el-header>
            <div class='title'>
                智慧餐厅
<!--                <span class='back' @click="$router.push('/home')">返回首页</span>-->
            </div>
            <span class='zmenu'>
                <span class='menu' @mouseenter="resblock" @mouseleave="resnone" v-show="show1">
                餐厅管理
                <div class='calmanage' v-if="resshow">
                    <div class='selmanage' @click="$router.push('/canteen/dishesquery')">菜品查询</div>
                    <div class='selmanage' @click="$router.push('/canteen/dishesassess')">菜品评价</div>
                    <div class='eqmanage'  @click="$router.push('/canteen/dishesanalysequery')">菜品评价分析查询</div>
                    <div class='setmanage' @click="$router.push('/canteen/resdishesquery')">餐厅菜单计划查询</div>
                    <div class='query'  @click="$router.push('/canteen/rawplanquery')">原料需求计划查询</div>
                    <div class='setting' @click="$router.push('/canteen/dishespricequery')">菜品量价管理</div>
                    <div class='pnselect'  @click="$router.push('/canteen/placeeat')">异地就餐申请</div>
                    <div class='pnselect'  @click="$router.push('/canteen/packageservice')">打包服务</div>
                    <div class='pnselect'  @click="$router.push('/canteen/onlineordering')">线上订餐</div>
                    <div class='pnselect'  @click="$router.push('/canteen/foodindifferentplaces')">异地餐食配送</div>
                </div>
                </span>
                <span class='menu manage' @mouseenter="centerblock" @mouseleave="centernone" v-show="show2">
                辅助决策中心
                <div class='calmanage' v-if="centershow">
                    <div class='selmanage' @click="$router.push('/canteen/onlinedishesmanagement')">线上菜品管理</div>
                    <div class='eqmanage'  @click="$router.push('/canteen/ordermanagement')">订单管理</div>
                    <div class='setmanage' @click="$router.push('/canteen/productioncenter')">生产中心</div>
                    <div class='query'  @click="$router.push('/canteen/distributioncenter')">配送中心</div>
                    <div class='setting' @click="$router.push('/canteen/businessdata')">营业数据</div>
                    <div class='pnselect'  @click="$router.push('/canteen/restaurantinformation')">餐厅信息</div>
                </div>
            </span>
              <span class='menu manage' @mouseenter="takedisblock" @mouseleave="takedisnone" v-show="show3">
                取餐管理
                <div class='foodmanage' v-if="takeshow">
                    <div class='foodgenre' @click="$router.push('/canteen/cupboardmanagement')">餐柜管理</div>
                    <div class='dishgenre'  @click="$router.push('/canteen/takefood')">取餐管理</div>
                </div>
            </span>
            <span class='menu manage' @mouseenter="disblock" @mouseleave="disnone" v-show="show4">
                菜品管理
                <div class='foodmanage' v-if="show">
                    <div class='foodgenre' @click="$router.push('/canteen/foodcatemanage')">菜品类别管理</div>
                    <div class='dishgenre'  @click="$router.push('/canteen/foodmanage')">菜品管理</div>
                </div>
            </span>
            <span class='menu manage' @mouseenter="caldisblock" @mouseleave="caldisnone" v-show="show5">
                计量选餐管理
                <div class='calmanage' v-if="calshow">
                    <div class='selmanage' @click="$router.push('/canteen/measeldishes')">计量选餐排菜</div>
                    <div class='eqmanage'  @click="$router.push('/canteen/meaeqmanage')">计量设备管理</div>
                    <div class='setmanage' @click="$router.push('/canteen/measetstrate')">计量结算策略</div>
                    <div class='query'  @click="$router.push('/canteen/measetquery')">计量结算异常查询</div>
                    <div class='setting' @click="$router.push('/canteen/measystset')">计量系统设置</div>
                    <div class='pnselect'  @click="$router.push('/canteen/measelnutpack')">精准营养选餐</div>
                </div>
            </span>
            <span class='menu manage'  @mouseenter="minddisblock" @mouseleave="minddisnone" v-show="show6">
                智能排菜管理
                <div class='mindfoodmanage' v-if="mindshow">
                    <div class='mindarr' @click="$router.push('/canteen/mindmealarr')">智能排餐</div>
                    <div class='minddishes'  @click="$router.push('/canteen/mindmydishes')">我的菜品</div>
                    <div class='mindnut' @click="$router.push('/canteen/mindnut')">食谱营养分析</div>
                </div>
            </span>
            <span class='menu manage' @mouseenter="fooddisblock" @mouseleave="fooddisnone" v-show="show7">
                食品安全
                 <div class='foodtrace' v-if="foodshow" @click="$router.push('/canteen/foodtrace')">菜品溯源</div>
            </span>
            <span class='menu manage' @mouseenter="purchaseblock" @mouseleave="purchasesnone" v-show="show8">
                采购管理

               <div class='purchasetrace' v-if="purchaseshow">
                    <div class='selmanage' @click="$router.push('/canteen/suppliermanagement')">供应商管理</div>
                    <div class='eqmanage'  @click="$router.push('/canteen/purchaseorder')">采购订单查询</div>

                </div>
            </span>
            <span class='menu manage' @mouseenter="financeblock" @mouseleave="financenone" v-show="show9">
                财务管理

               <div class='finance' v-if="financeshow">
                    <div class='selmanage' @click="$router.push('/canteen/mealsettlementdetails')">就餐结算明细</div>
                    <div class='eqmanage'  @click="$router.push('/canteen/canteencoststatistics')">食堂经营成本统计</div>

                </div>
            </span>
            <span  class='menu manage' @mouseenter="supermarketblock" @mouseleave="supermarketnone" v-show="show10">
                网上超市

               <div class='purchasetrace' v-if="supermarketshow">
                    <div class='selmanage' @click="$router.push('/canteen/onlineshopping')">在线购物</div>
                    <div class='eqmanage'  @click="$router.push('/canteen/productmanagement')">售卖管理</div>
                   <div class='eqmanage'   @click="$router.push('/canteen/shoppingorder')">订单管理</div>
                     <!-- <div class='eqmanage'@mouseenter="goshopblock" @click="goshopblock"  >库存管理</div> -->
                  <!-- <div  style="position: absolute;left:110px;top: 79px" v-show="goshop">
                    <div style="width: 120px;border: 1px solid rgb(118,118,119);" class='selmanage' @click="rukugl" >入库管理</div>
                    <div style="width: 120px;border: 1px solid rgb(118,118,119);" class='eqmanage' @click="taizhang" >台账明细</div>
                   </div> -->

                </div>
            </span>
            <span class='menu manage' @click="$router.push('/canteen/healthmanagement')" v-show="show11">
                健康管理
            </span>
            <span class='icon'>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </span>
        </span>
        </el-header>
        <el-main>
            <router-view></router-view>
        </el-main>
        </el-container>

</template>

<script>
export default {
  data () {
    return {
      goshop:false,
      show: false,
      centershow:false,
      resshow:false,
      calshow: false,
      foodshow: false,
      mindshow: false,
      purchaseshow:false,
      takeshow:false,
      financeshow:false,
      supermarketshow:false,
      show1:true,
      show2:true,
      show3:true,
      show4:true,
      show5:true,
      show6:true,
      show7:true,
      show8:true,
      show9:true,
      show10:true,
      show11:true,
    }
  },
  created(){
    if(JSON.parse(window.sessionStorage.getItem('key'))==1){
      this.show1=true
      this.show2=false
      this.show3=false
      this.show4=false
      this.show5=false
      this.show6=false
      this.show7=false
      this.show8=false
      this.show9=false
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==2){
      this.show1=false
      this.show2=true
      this.show3=false
      this.show4=false
      this.show5=false
      this.show6=false
      this.show7=false
      this.show8=false
      this.show9=false
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==3){
      this.show1=false
      this.show2=false
      this.show3=true
      this.show4=false
      this.show5=false
      this.show6=false
      this.show7=false
      this.show8=false
      this.show9=false
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==4){
      this.show1=false
      this.show2=false
      this.show3=false
      this.show4=true
      this.show5=false
      this.show6=false
      this.show7=false
      this.show8=false
      this.show9=false
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==5){
      this.show1=false
      this.show2=false
      this.show3=false
      this.show4=false
      this.show5=true
      this.show6=false
      this.show7=false
      this.show8=false
      this.show9=false
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==6){
      this.show1=false
      this.show2=false
      this.show3=false
      this.show4=false
      this.show5=false
      this.show6=true
      this.show7=false
      this.show8=false
      this.show9=false
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==7){
      this.show1=false
      this.show2=false
      this.show3=false
      this.show4=false
      this.show5=false
      this.show6=false
      this.show7=true
      this.show8=false
      this.show9=false
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==8){
      this.show1=false
      this.show2=false
      this.show3=false
      this.show4=false
      this.show5=false
      this.show6=false
      this.show7=false
      this.show8=true
      this.show9=false
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==9){
      this.show1=false
      this.show2=false
      this.show3=false
      this.show4=false
      this.show5=false
      this.show6=false
      this.show7=false
      this.show8=false
      this.show9=true
      this.show10=false
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==10){
      this.show1=false
      this.show2=false
      this.show3=false
      this.show4=false
      this.show5=false
      this.show6=false
      this.show7=false
      this.show8=false
      this.show9=false
      this.show10=true
      this.show11=false
    }else if(JSON.parse(window.sessionStorage.getItem('key'))==11){
      this.show1=false
      this.show2=false
      this.show3=false
      this.show4=false
      this.show5=false
      this.show6=false
      this.show7=false
      this.show8=false
      this.show9=false
      this.show10=false
      this.show11=true
    }
  },
  methods: {
    takedisblock() {
      this.takeshow = true
    },
    takedisnone() {
      this.takeshow = false
    },
    disblock () {
      this.show = true
    },
    disnone () {
      this.show = false
    },
    resblock(){
      this.resshow=true
    },
    resnone(){
      this.resshow=false
    },
    centerblock(){
      this.centershow=true
    },
    centernone(){
      this.centershow=false
    },
    caldisblock () {
      this.calshow = true
    },
    caldisnone () {
      this.calshow = false
    },
    fooddisblock () {
      this.foodshow = true
    },
    fooddisnone () {
      this.foodshow = false
    },
    minddisblock () {
      this.mindshow = true
    },
    minddisnone () {
      this.mindshow = false
    },
    purchaseblock () {
      this.purchaseshow = true
    },
    purchasesnone(){
      this.purchaseshow = false
    },
    financeblock(){
      this.financeshow=true
    },
    financenone(){
      this.financeshow=false
    },
    supermarketblock(){
      this.supermarketshow=true
      this.goshop=false
    },
    supermarketnone(){
      this.supermarketshow=false
      this.goshop=false
    },
    goshopblock(){
      this.goshop=true
    },
    goshopnone(){
      this.goshop=true
      this.show10=true
    },
    rukugl(){

      this.$router.push('/canteen/stockmanagement')
      this.supermarketshow=false
    },
    taizhang(){

      this.$router.push('/canteen/standingBook')
      this.supermarketshow=false
    }
  }
}
</script>

<style scoped lang='less'>
.title{
    // margin-right: 250px;
    font-size: 16px;
    float: left;
}
.home-container{
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: 0;
//   background-color: pink;
}
.el-header{
    position: relative;
    background-color: rgb(1,7,36);
    border-bottom:1px solid rgb(97,206,249) ;
    line-height: 40px;
    padding-left: 20px;
    height: 40px!important;
    box-sizing: border-box;
    color:rgb(255,255,255);
}
.el-dropdown{
    color:rgb(255,255,255);
}
.zmenu{
    position: absolute;
    top:0;
    right: 10px;
}
.menu{
    float: left;
    border-left: 1px solid rgb(90,93,104);
    padding: 0 22px;
    box-sizing: border-box;
    height: 40px;
    font-size: 16px;
}
.menu:hover{
    background-color: rgb(0,26,137);
    cursor: pointer;
}
.icon{
    border-left: 1px solid rgb(90,93,104);
    display: inline-block;
    width: 280px;
    height: 40px;
}
.icon span{
    display: inline-block;
    width:40px ;
    height:38px;
}
.icon span:hover{
    cursor: pointer;
}
.icon span:nth-child(1){
    background: url(../../assets/u46.png) no-repeat -4px -2px;
    background-size: 340px 46px;
}
.icon span:nth-child(2){
    background: url(../../assets/u46.png) -50px -2px;
    background-size: 340px 46px;

}
.icon span:nth-child(3){
    background: url(../../assets/u46.png) -96px -2px;
    background-size: 340px 46px;

}
.icon span:nth-child(4){
    background: url(../../assets/u46.png) -144px -2px;
    background-size: 340px 46px;
}
.icon span:nth-child(5){
    background: url(../../assets/u46.png) -192px -2px;
    background-size: 340px 46px;
}
.icon span:nth-child(6){
    background: url(../../assets/u46.png) -238px -2px;
    background-size: 340px 46px;
}
.icon span:nth-child(7){
    background: url(../../assets/u46.png) -284px -2px;
    background-size: 340px 46px;

}
.manage{
    position: relative;
}
.foodmanage{
    width:110px;
    text-indent:5px;
    opacity: 0.8;
    position: absolute;
    z-index: 4;
    left: 0;
    bottom: -28;
    box-sizing: border-box;
    background-color: rgb(1,7,36);
    border: 1px solid rgb(118,118,119);
}
.calmanage{
    width:160px;
    opacity: 0.8;
    text-indent:5px;
    position: absolute;
    z-index: 4;
    left: 0;
    bottom: -28;
    box-sizing: border-box;
    background-color: rgb(1,7,36);
    border: 1px solid rgb(118,118,119);
}
.foodtrace{
    width:110px;
    text-indent:5px;
    opacity: 0.8;
    position: absolute;
    z-index: 4;
    left: 0;
    bottom: -28;
    box-sizing: border-box;
    background-color: rgb(1,7,36);
    border: 1px solid rgb(118,118,119);
}
.purchasetrace{
  width:110px;
  text-indent:5px;
  opacity: 0.8;
  position: absolute;
  z-index: 4;
  left:0;
  bottom: -28;
  box-sizing: border-box;
  background-color: rgb(1,7,36);
  border: 1px solid rgb(118,118,119);
}
.finance{
    width:145px;
    text-indent:5px;
    opacity: 0.8;
    position: absolute;
    z-index: 4;
    left: 0;
    bottom: -28;
    box-sizing: border-box;
    background-color: rgb(1,7,36);
    border: 1px solid rgb(118,118,119);
}
.mindfoodmanage{
    width:110px;
    text-indent:5px;
    opacity: 0.8;
    position: absolute;
    z-index: 4;
    left: 0;
    bottom: -28;
    box-sizing: border-box;
    background-color: rgb(1,7,36);
    border: 1px solid rgb(118,118,119);
}
.foodgenre,.selmanage,.eqmanage,.setmanage,.query,.setting,.pnselect,.foodtrace,.mindarr,.minddishes,.mindnut{
    border-bottom: 1px solid rgb(118,118,119);
}
.foodgenre,.dishgenre,.selmanage,.eqmanage,.setmanage,.query,.setting,.pnselect,.foodtrace,.mindarr,.minddishes,.mindnut:hover{
    cursor: pointer;
}
.foodgenre:hover,.dishgenre:hover,.selmanage:hover,.eqmanage:hover,.setmanage:hover,.query:hover,.setting:hover,.pnselect:hover,.foodtrace:hover,.mindarr:hover,.minddishes:hover,.mindnut:hover{
    background-color:rgb(0,26,137);
}
.el-main{
    padding:0px;
    width: 100%;
    height: 100%;
    background: url(../../assets/2222.png);
}
.back{
  font-size: 14px;
  border: 1px solid white;
  padding:2px 10px;
  border-radius: 5px;
  box-sizing: border-box;
  margin-left: 10px;
  cursor: pointer;
}
</style>
