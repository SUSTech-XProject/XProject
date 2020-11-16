<template>
  <el-form class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 class="login-title">Register - Test</h3>
    <el-form-item>
      <el-input type="text" v-model="registerForm.username"
                auto-complete="off" placeholder="Username"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="registerForm.password"
                auto-complete="off" placeholder="Password"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="registerForm.passwordRepeat"
                auto-complete="off" placeholder="Password again"></el-input>
    </el-form-item>

    <el-radio-group v-model="radio">
      <el-radio label="S">Student</el-radio>
      <el-radio label="T">Teacher</el-radio>
    </el-radio-group>

    <el-form-item style="width: 100%; margin-top: 30px">
      <el-button type="primary" v-on:click="register">Submit</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import {registerPost} from '@/api/role'

export default {
  name: 'Register',
  data () {
    return {
      checked: true,
      radio: 'S',
      registerForm: {
        username: '',
        password: '',
        passwordRepeat: ''
      },
      loading: false
    }
  },
  methods: {
    register () {
      if (this.password !== this.passwordRepeat) {
        this.$alert('Two passwords are not matched', 'Tip', {
          confirmButtonText: 'OK'
        })
        return
      }

      const _this = this
      registerPost(
        this.radio,
        this.registerForm.username,
        this.registerForm.password
      ).then(resp => {
        if (resp.data.code === 200) {
          this.$alert('Register successfully', 'Tip', {
            confirmButtonText: 'OK'
          })
          _this.$router.replace('/login')
        } else {
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResponse => {
        this.$alert('Back-end no response', 'Tips', {
          confirmButtonText: 'OK'
        })
      })
    }
  }
}
</script>
<style scoped>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    /*box-shadow: 0 0 25px #cac6c6;*/
  }

  .login-title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
