<template>
  <div>
    <h3 class="login-title">Login to XProject</h3>
    <el-form class="login-container" :model="loginForm" :rules="rules" ref="loginForm">
      <el-form-item label="Username" prop="username">
        <el-input type="text" v-model="loginForm.username"
                  prefix-icon="el-icon-user" auto-complete="off"
                  placeholder="Please input username"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input type="password" v-model="loginForm.password"
                  prefix-icon="el-icon-lock" auto-complete="off"
                  placeholder="Please input password" show-password
                  @keyup.enter.native="login">
        </el-input>

      </el-form-item>
      <el-form-item>
        <el-checkbox label="Remember me" style=""
                     v-model="loginForm.rememberMe" name="type"></el-checkbox>
      </el-form-item>
      <el-form-item class="btm-item">
        <el-button class="sbm-btm" type="primary" v-on:click="login" :loading="btmLoading">Login</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {loginPost} from '@/api/role'

export default {
  name: 'Login',
  data () {
    const validateUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Please input username'));
      }
      setTimeout(() => {
        if (value.length < 8 || value.length > 24) {
          return callback(new Error('The username must have at least 8 characters and 24 at most.'));
        } else {
          return callback();
        }
      }, 1000);
    };
    const validatePwd = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Please input password'));
      }
      setTimeout(() => {
        if (value.length < 8 || value.length > 32) {
          return callback(new Error('The password must have at least 8 characters and 32 at most.'));
        } else {
          return callback();
        }
      }, 1000);
    };

    return {
      loginForm: {
        username: '',
        password: '',
        rememberMe: false
      },
      rules: {
        username: [
          { validator: validateUsername, trigger: 'blur' }
        ],
        password: [
          { validator: validatePwd, trigger: 'blur' }
        ]
      },
      btmLoading: false
    }
  },
  methods: {
    login () {
      this.btmLoading = true
      this.$refs['loginForm'].validate((valid) => {
        if (!valid) {
          this.$alert('You have not filled in all required fields correctly.', 'Tips', {
            confirmButtonText: 'OK'
          })
          this.btmLoading = false
          return false
        }

        console.log('send login data')
        loginPost(
          this.loginForm.username,
          this.loginForm.password,
          this.loginForm.rememberMe
        ).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.$store.commit('login', this.loginForm)
            this.$router.push({title:'MainPage'})
          } else if (resp.data.code === 400) {
            console.log(resp.data.message)
            this.$alert(resp.data.message, 'Tip', {
              confirmButtonText: 'OK'
            })
          }
          this.btmLoading = false
          return true
        }).catch(failResp => {
          this.$alert('Error' + failResp.message, 'Tips', {
            confirmButtonText: 'OK'
          })

          this.btmLoading = false
          return false
        })
      });
    },
  }
}
</script>

<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: auto;
    width: 300px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
  }

  .login-title {
    margin: 50px auto 40px auto;
    text-align: center;
    font-size: 30px;
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
