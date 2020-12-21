<template>
  <div>
    <h3 class="reg-title">Sign up for an account</h3>
    <el-form class="reg-container" label-position="right" :model="registerForm"
             label-width="100px" status-icon :rules="rules" ref="registerForm">
      <el-form-item label="Username" prop="username" style="margin-bottom: 40px">
        <el-input type="text" v-model="registerForm.username"
                  prefix-icon="el-icon-user" auto-complete="off"
                  placeholder="Please input username"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password" style="margin-bottom: 40px">
        <el-input type="password" v-model="registerForm.password"
                  prefix-icon="el-icon-lock" auto-complete="off"
                  placeholder="Please input password" show-password></el-input>
      </el-form-item>
      <el-form-item label="Repeat" prop="passwordRepeat" style="margin-bottom: 40px">
        <el-input type="password" v-model="registerForm.passwordRepeat"
                  prefix-icon="el-icon-lock" auto-complete="off"
                  placeholder="Please input password again" show-password></el-input>
      </el-form-item>
      <el-form-item label="Role">
        <el-radio-group v-model="registerForm.roleType">
          <el-radio border label="Student">student</el-radio>
          <el-radio border label="Teacher">teacher</el-radio>
        </el-radio-group>
      </el-form-item>
      <div style="width: 100%; display: flex; align-content: center; justify-content: center;">
        <el-button class='reg-btm' type="primary" v-on:click="register" ref="reg-btm">Create</el-button>
      </div>
    </el-form>

  </div>
</template>
<script>
  import {postRegister} from '@/api/role'
import {validPassword, validUsername} from "@/utils/validate";

export default {
  name: 'Register',
  data () {
    const validateUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Please input username'));
      }
      setTimeout(() => {
        if (!validUsername(value)) {
          return callback(new Error('The username must have at least 8 characters and 24 at most. ' +
            'It should not start with a number.'));
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
        if (!validPassword(value)) {
          return callback(new Error('The password must have at least 8 characters and 32 at most. ' +
            'Must contain number and letter.'));
        } else {
          return callback();
        }
      }, 1000);
    };

    const validatePwd2 = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Please input password again'));
      }
      setTimeout(() => {
        if (value !== this.registerForm.password) {
          return callback(new Error('Two passwords are not matched.'));
        } else {
          return callback();
        }
      }, 1000);
    };

    return {
      registerForm: {
        username: '',
        password: '',
        passwordRepeat: '',
        roleType: 'Student'
      },
      rules: {
        username: [
          { validator: validateUsername, trigger: 'blur' }
        ],
        password: [
          { validator: validatePwd, trigger: 'blur' }
        ],
        passwordRepeat: [
          { validator: validatePwd2, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    register () {
      const _this = this
      this.$refs['reg-btm'].loading = true
      this.$refs['registerForm'].validate((valid) => {
        if (!valid) {
          this.$alert('You have not filled in all required fields correctly.', 'Tips', {
            confirmButtonText: 'OK'
          })
          _this.$refs['reg-btm'].loading = false
          return false
        }

        console.log('send account data')
        postRegister(
          this.registerForm.roleType,
          this.registerForm.username,
          this.registerForm.password
        ).then(resp => {
          if (resp.data.code === 200) {
            this.$alert('Register successfully', 'Tip', {
              confirmButtonText: 'OK'
            })
            _this.$router.push({name:'Login'})
          } else {
            this.$alert(resp.data.message, 'Tip', {
              confirmButtonText: 'OK'
            })
          }
          _this.$refs['reg-btm'].loading = false
          return true
        }).catch(failResponse => {
          this.$alert('Back-end no response', 'Tips', {
            confirmButtonText: 'OK'
          })

          _this.$refs['reg-btm'].loading = false
          return false
        })
      });
    }
  }
}
</script>
<style scoped>
  .reg-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: auto;
    width: 400px;
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
    width: 120px;
    margin: 20px auto 20px auto;
  }
</style>
