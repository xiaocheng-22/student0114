<template>
  <div id="main">
     <div id="title">学生信息管理系统</div>
     <div id="loginDiv">
         <el-form ref="form" label-width="70px" style="text-align: center">
           <el-form-item label="用户名：">
             <el-input v-model="user_for_login.username" class="input01"></el-input>
           </el-form-item>
           <el-form-item label="密码：" style="margin-top:-5px">
             <el-input v-model="user_for_login.password" class="input01" show-password></el-input>
           </el-form-item>
           <el-button type="primary" @click="login">登录</el-button>
         </el-form>
        <el-link @click="register" style="position:absolute;bottom:21px;right:10px;">注册新用户</el-link>
     </div>

    <el-dialog
        title="注册新用户"
        :visible.sync="dialogVisible"
        @closed="closeWin"
        width="30%">
      <el-form ref="form" label-width="85px">
        <el-form-item label="用户名：">
          <el-input v-model="user_for_register.username"></el-input>
        </el-form-item>
        <el-form-item label="密码：">
          <el-input v-model="user_for_register.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码：">
          <el-input v-model="user_for_register.confirmPassword" show-password></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="insertUser">注 册</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import axios from 'axios'
import { v4 as uuidv4 } from 'uuid';
export default {
  name: 'HomeMain',
  components: {

  },
  data(){
    return{
      user_for_login:{
        username:'',
        password:''
      },
      user_for_register:{
        id:'',
        username:'',
        password:'',
        confirmPassword:''
      },
      dialogVisible:false
    }
  },
  created() {

  },
  methods:{
    login(){
      //console.log(this.user_for_login);
      axios({
        url:'http://localhost:9090/login',
        data:this.user_for_login,
        method:'POST'
      }).then(res=>{
        //console.log(res.data);
        if(res.data=='1'){
          sessionStorage.setItem("username",this.user_for_login.username);
          // 登录成功，跳转到学生页面
          this.$router.push({ name: 'Student', params: {  } });
        }else{
          this.$alert('用户名或密码错误！', '提示', {
            confirmButtonText: '确定',
            callback: function (){}
          });
        }

      })
    },
    register(){
      this.dialogVisible=true;
    },
    insertUser(){
      let me=this;
      if(this.user_for_register.password==this.user_for_register.confirmPassword){
        this.user_for_register.id=uuidv4();
        axios({
          url:'http://localhost:9090/register',
          data:this.user_for_register,
          method:'POST'
        }).then(res=>{
          //console.log(res);
          if(res.data.code==200){
            this.$alert('注册成功！', '提示', {
              confirmButtonText: '确定',
              callback: function (){
                me.dialogVisible=false;
              }
            });
          }
        })
      }else{
        this.$alert('两次密码不一致！', '提示', {
          confirmButtonText: '确定',
          callback: function (){}
        });
      }

    },
    closeWin(){
      this.user_for_register={
        id:'',
        username:'',
        password:'',
        confirmPassword:''
      }
    },
  }
}
</script>

<style scoped lang="less">
  #main{
    width:100%;
    height:100vh;
    overflow: hidden;
  }

  #title{
    text-align: center;
    color:blue;
    font-weight: bold;
    font-size:30px;
    letter-spacing: 1px;
    height:calc(50vh - 101px);
    line-height:calc(50vh - 101px);
  }

  #loginDiv{
    width:500px;
    border:1px solid blue;
    border-radius: 10px;
    position: absolute;
    top:50%;
    left:50%;
    transform:translate(-50%,-50%);
    padding:20px 0;
    display: flex;                   /* 使用 Flexbox 布局 */
    justify-content: center;        /* 水平居中对齐 (可选) */
  }

  .input01 {
    width:200px;
  }

  .input01 ::v-deep .el-input__inner {
    height: 40px !important;  /* 使用 !important 强制覆盖 */
    line-height: 40px;  /* 确保内容垂直居中 */
  }


</style>
