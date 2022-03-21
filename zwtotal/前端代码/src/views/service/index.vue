<template>
        <el-container class="home-container">
        <el-header>
            <span class='zmenu'>
                <span class='menu' @click="$router.push('/service/equipment')">
                办公设备
                </span>
            <span class='menu manage' @mouseenter="caldisblock" @mouseleave="caldisnone">
                办公用品
                <div class='mindfoodmanage' v-if="calshow">
                    <div class='selmanage' @click="$router.push('/service/stationery')">办公文具</div>
                    <div class='eqmanage' @click="$router.push('/service/paper')">打印纸</div>
                </div>
            </span>
            <span class='menu manage' @click="$router.push('/service/meeting')">
                会议用品
            </span>
            <span class='menu manage' @mouseenter="stockdisblock" @mouseleave="stockdisnone">
                库存管理
                <div class='mindfoodmanage' v-if="stockshow">
                    <div class='selmanage' @click="$router.push('/service/buyplan')">采购计划</div>
                    <div class='eqmanage' @click="$router.push('/service/stockquery')">库存查询</div>
                    <div class='eqmanage' @click="$router.push('/service/inbound')">入库管理</div>
                    <div class='eqmanage' @click="$router.push('/service/outbound')">出库管理</div>
                    <div class='eqmanage' @click="$router.push('/service/stockstatistics')">库存统计</div>
                </div>
            </span>
            <span class='menu manage' @mouseenter="budgetdisplay" @mouseleave="budgetdisnone">
                预算申请
                <div class='mindfoodmanage' v-if="budgetshow">
                    <div class='selmanage' @click="$router.push('/service/budgetsetting')">预算设置</div>
                    <div class='eqmanage' @click="$router.push('/service/budgetamendment')">预算追加</div>
                </div>
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
        calshow: false,
        stockshow:false,
        budgetshow:false,
    }
  },
  methods: {
     caldisblock () {
        this.calshow = true
      },
      caldisnone () {
        this.calshow = false
      },
      stockdisblock(){
        this.stockshow=true
      },
      stockdisnone(){
        this.stockshow=false
      },
      budgetdisplay(){
        this.budgetshow=true
      },
      budgetdisnone(){
        this.budgetshow=false
      }
  }
}
</script>

<style scoped lang='less'>
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
    border-bottom:1px solid rgb(97,206,249);
    height: 40px!important;
    line-height: 40px!important;
    box-sizing: border-box;
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

.el-main{
    padding:0px;
    width: 100%;
    height: 100%;
    background: url(../../assets/2222.png);
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
</style>
