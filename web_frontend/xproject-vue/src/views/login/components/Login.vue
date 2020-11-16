<template>
  <el-form class="login-container" label-position="left"
           label-width="0px">
    <h3 class="login-title">Login to XProject</h3>
    <el-form-item label="Username">
      <el-input type="text" v-model="loginForm.username"
                prefix-icon="el-icon-user" auto-complete="off"
                placeholder="Please input username"></el-input>
    </el-form-item>
    <el-form-item label="Password">
      <el-input :type="pwdType" v-model="loginForm.password"
                prefix-icon="el-icon-lock" auto-complete="off"
                placeholder="Please input password"
                @keyup.enter.native="login">
        <i slot="suffix" :class="pwdIcon" @click="switchPwdType" style="margin-right: 5px"></i>
      </el-input>

    </el-form-item>
    <el-form-item>
      <el-checkbox label="Remember me" style=""
                   v-model="loginForm.rememberMe" name="type"></el-checkbox>
    </el-form-item>
    <el-form-item class="btm-item">
      <el-button class="sbm-btm" type="primary" v-on:click="login">Login</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {loginPost} from '@/api/role'

export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: '',
        rememberMe: false
      },
      pwdType: 'password',
      pwdIcon: 'el-icon-view',
      responseResult: [],
    }
  },
  methods: {
    login () {
      const _this = this
      console.log('send login data')
      loginPost(
        this.loginForm.username,
        this.loginForm.password,
        this.loginForm.rememberMe
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          _this.$store.commit('login', _this.loginForm)
          const path = this.$route.query.redirect
          this.$router.replace({path: path === '/' || path === undefined ? '/index' : path})
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResponse => {
        console.log(failResponse)
        this.$alert('Back-end no response', 'Tips', {
          confirmButtonText: 'OK'
        })
      })
    },
    switchPwdType () {
      if (this.pwdType === 'password') {
        this.pwdType = ''
        this.pwdIcon = 'iconfont el-icon-eye'
      } else {
        this.pwdType = 'password'
        this.pwdIcon = 'iconfont el-icon-eye-close'
      }
    }
  }
}
</script>

<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
  }

  .login-title {
    margin: 0 auto 40px auto;
    text-align: center;
    color: #505458;
  }
  .btm-item {
    display: flex;
    justify-content: center;
  }
  .sbm-btm {
    width: 100px;
  }
</style>
