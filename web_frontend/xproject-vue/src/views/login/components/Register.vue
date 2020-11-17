<template>
  <div>
    <h3 class="reg-title">Sign up for an account</h3>
    <el-form class="reg-container" label-position="right"
             label-width="100px">
      <el-form-item label="Username">
        <el-input type="text" v-model="registerForm.username"
                  prefix-icon="el-icon-user" auto-complete="off"
                  placeholder="Please input username"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input type="password" v-model="registerForm.password"
                  prefix-icon="el-icon-lock" auto-complete="off"
                  placeholder="Please input password" show-password></el-input>
      </el-form-item>
      <el-form-item label="Repeat">
        <el-input type="password" v-model="registerForm.passwordRepeat"
                  prefix-icon="el-icon-lock" auto-complete="off"
                  placeholder="Please input password again" show-password></el-input>
      </el-form-item>
      <el-form-item label="Role">
        <el-radio-group v-model="registerForm.roleType" style="padding-left: 60px">
          <el-radio border label="S">student</el-radio>
          <el-radio border label="T">teacher</el-radio>
        </el-radio-group>
      </el-form-item>
      <div style="width: 100%; display: flex; align-content: center; justify-content: center;">
        <el-button class='reg-btm' type="primary" v-on:click="register">Create</el-button>
      </div>
    </el-form>

  </div>
</template>
<script>
import {registerPost} from '@/api/role'

export default {
  name: 'Register',
  data () {
    return {
      registerForm: {
        username: '',
        password: '',
        passwordRepeat: '',
        roleType: 'S'
      }
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
  .reg-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: auto;
    width: 500px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    /*box-shadow: 0 0 25px #cac6c6;*/
  }

  .reg-title {
    margin: 50px auto 40px auto;
    text-align: center;
    color: #505458;
    font-size: 30px;
  }

  .reg-btm {
    width: 200px;
    margin: 0 auto 20px auto;
  }
</style>
