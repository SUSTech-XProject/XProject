<template>
  <el-form class="login-container" label-position="left"
           label-width="0px">
    <h3 class="login_title">Login - Test</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="Username"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="Password"></el-input>
    </el-form-item>
    <p>{{ msg }}</p>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">Login</el-button>
    </el-form-item>
  </el-form>
</template>

<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login_title {
    margin: 0 auto 40px auto;
    text-align: center;
    color: #505458;
  }

</style>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: [],
      msg: ''
    }
  },
  methods: {
    login () {
      this.msg = 'Wait ...'
      console.log('send login data')
      this.$axios
        .post('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.$router.replace({path: '/index'})
          } else if (resp.data.code === 400) {
            console.log(resp.data.message)
            this.msg = resp.data.message
          }
        })
        .catch(failResponse => {
          console.log(failResponse)
          this.msg = 'Back-end no response'
        })
    }
  }
}
</script>
