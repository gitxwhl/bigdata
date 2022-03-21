<template>
  <el-container class="home-container">
    <el-header>
      <div class='title'>
       物业管理
      </div>
      <span class='zmenu'>
                <span class='menu' @mouseenter="resblock" @mouseleave="resnone">
                  物业报修
                <div class='mindfoodmanage' v-if="resshow">
                    <div class='selmanage' @click="$router.push('/property/propertyrepair')">物业报修</div>
                    <div class='selmanage' @click="$router.push('/property/repairapply')">报修申请</div>
                    <div class='eqmanage' @click="$router.push('/property/repairauditing')">报修审核</div>
                </div>
                </span>
            <span class='menu manage' @mouseenter="disblock" @mouseleave="disnone">

              <div  @click="$router.push('/property/equipmentLedger')">设备台账</div>
            </span>
            <span class='menu manage' @mouseenter="caldisblock" @mouseleave="caldisnone">
                绿化管理
                <div class='mindfoodmanage' v-if="calshow">
                    <div class='selmanage' @click="$router.push('/property/spacemanagement')">空间管理</div>
                    <div class='eqmanage' @click="$router.push('/property/slmanagement')">供应商管理</div>
                    <div class='setmanage' @click="$router.push('/property/irrigationmanagement')">灌溉管理</div>
                    <div class='query'  @click="$router.push('/property/jobplacement')">工作安排</div>
                </div>
            </span>
            <span class='menu manage'  @mouseenter="minddisblock" @mouseleave="minddisnone">
               保洁管理
                <div class='clearing' v-if="mindshow">
                    <div class='mindarr' @click="$router.push('/property/districtmanagement')">保洁区域管理</div>
                    <div class='minddishes' @click="$router.push('/property/cleaningplanmanagement')">保洁计划管理</div>
                    <div class='mindnut' @click="$router.push('/property/inspectionandanalysis')">保洁执行情况检查分析</div>
                    <div class='minddishes'  @click="$router.push('/property/inspectionandmaintenance')">保洁执行情况检查维护</div>
                    <div class='mindnut' @click="$router.push('/property/materialmanagement')">保洁工具耗材管理</div>

                </div>
            </span>
        <span class='menu manage'  @mouseenter="Rankblock" @mouseleave="Ranknone">
              秩维管理
                <div class='mindfoodmanage' v-if="Rankshow">
                    <div class='mindarr' @click="$router.push('/property/rankplan')">秩维计划</div>
                    <div class='minddishes' @click="$router.push('/property/dutyrecord')" >值班记录</div>
                    <div class='mindnut' @click="$router.push('/property/accidentrecords')">事故记录</div>
                </div>
            </span>

             <span class='menu manage'  @mouseenter="equipmentblock" @mouseleave="equipmentnone">
              设备维护
                <div class='mindfoodmanage' v-if="equipmentshow">
                    <div class='mindarr' @click="$router.push('/property/koriyasu')">设备维保策略</div>
                    <div class='minddishes' @click="$router.push('/property/gongdan')"  >维护工单</div>
                    <div class='mindnut' @click="$router.push('/property/recordsquery')"  >历史维护记录</div>
                </div>
            </span>

         <span class='menu manage' @click="$router.push('/property/equipmentInspection')">
              设备巡检
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
        show: false,
        resshow:false,
        calshow: false,
        mindshow: false,
        Rankshow:false,
      equipmentshow:false
      }
    },
    methods: {
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
      caldisblock () {
        this.calshow = true
      },
      caldisnone () {
        this.calshow = false
      },
      minddisblock () {
        this.mindshow = true
      },
      minddisnone () {
        this.mindshow = false
      },
      Rankblock () {
        this.Rankshow = true
      },
      Ranknone () {
        this.Rankshow = false
      },
      equipmentblock () {
        this.equipmentshow = true
      },
      equipmentnone(){
        this.equipmentshow = false
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
    left:609px;
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
  .clearing{
    width:180px;
    text-indent:5px;
    opacity: 0.8;
    position: absolute;
    z-index: 1111;
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

</style>
