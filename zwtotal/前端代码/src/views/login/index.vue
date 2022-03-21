<template>
  <div class='container'>
       <div class="login_back">
          <p class="pic"></p>
         <div class="type">
           <span>账号登陆</span>
           <span style="margin-left: 50px">短信登陆</span>
         </div>
         <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >

           <el-input  type="text" v-model="queryform.phone"  placeholder="手机号码" class="name"></el-input>
<!--           <el-input type="text" v-model="queryform.password"  placeholder="手机号码" class="password"></el-input>-->
           <el-input class="password" placeholder="请输入密码" v-model="queryform.password" ></el-input>
         </el-form>
             <div class="login" @click.prevent @click="dllogin"><span>登陆</span></div>
         <div class="bot" style="position: absolute;top: 440px;left: 50px">
           <el-radio v-model="radio" label="1">记住登陆状态</el-radio>
           <span>注册新用户</span>
           <span style="margin-left: 15px">忘记密码</span>
         </div>

       </div>

  </div>
</template>

<script>
    export default {
      data () {
        return {
          radio:'',
          queryform:{
            phone:'',
            password:''
          }
        }
      },
      methods: {
        dllogin(){
          var str2=this.queryform.phone
          // var str2 = JSON.stringify(arr2);
          window.sessionStorage.getItem('key', str2);
          this.$router.push({path:'/home'})
        }
      },
    }
</script>

<style scoped>
.container{

  background: url(../../assets/u0.svg)no-repeat;
  background-size:100% 100% ;
  line-height:55px;

  width: 100%;
  height:960px;
  /*background-size:cover;*/
  /*position: relative;*/
}
  .login_back{
    border-width: 0px;
    position: absolute;
    left: 300px;
    top: 200px;
    width: 460px;
    height: 592px;
    /*background: inherit;*/
    background-color:#343D5F;
    box-sizing: border-box;
    /*border-width: 1px;*/
    border-style: solid;
    border-color: rgba(233, 233, 233, 1);
    border-radius: 10px;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
    box-shadow: none;
    font-family: '微软雅黑';
    font-weight: 400;
    font-style: normal;
    text-align: left;
    line-height: 20px;
  }
  .pic{
    position: absolute;
    top: 20px;
    left: 150px;
    width: 140px;
    height: 140px;
    /*background: inherit;*/
    background-color: rgba(255, 255, 255, 1);
    border: none;
    border-radius: 284px;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
    box-shadow: none;
    font-family: '微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑';
    font-weight: 700;
    font-style: normal;
    font-size: 20px;
    color: #999999;
    /*line-height: 36px;*/
  }
.type{

  /*width: 175px;*/
  /*height: 50px;*/
  color: white;
  position: absolute;
  top: 210px;
  left: 120px;
  border-color: rgba(0, 121, 254, 1);
}
.name /deep/.el-input__inner{
  width: 260px;
  top: 236px;
  position: absolute;
  left: 100px;
  background:url(../../assets/u25.svg) no-repeat 0 0;
  background-size:15px 20px;
  background-position: 10px 10px;
  text-indent: 20px;
}
.password /deep/.el-input__inner{
  width: 260px;
  top: 270px;
  position: absolute;
  left: 100px;
  background:url(../../assets/u26.svg) no-repeat 0 0;
  background-size:15px 20px;
  background-position: 12px 10px;
  text-indent: 20px;
}
  .login{
    border-width: 0px;
    position: absolute;
    left: 50px;
    top: 380px;
    width: 350px;
    height: 50px;
    background: inherit;
    background-color: rgba(0, 121, 254, 0.847058823529412);
    border: none;
    border-radius: 3px;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
    box-shadow: none;

  }
  .login span{
    font-family: '微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑';
    font-weight: 700;
    font-style: normal;
    font-size: 18px;
    color: #FFFFFF;
    line-height: 50px;
    text-align: center;
    display:block;
  }
  .bot{
    font-style: normal;
    font-size: 14px;
    color: #FFFFFF;
  }
</style>
