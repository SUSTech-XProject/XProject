<template>
  <el-row type="flex" id="top-row" justify="space-between" align="middle" onselectstart="return false">
    <el-col :span="6" class="container">
      <div>
        <el-image class="img" :src="logo" @click="gotoWel"></el-image>
      </div>
      <div class="title" @click="gotoWel()">XProject</div>
    </el-col>
    <el-col :span="6" :offset="6" class="btm-group" v-if="this.$route.name === 'Login'">
      <div class="top-text">New to XProject ?</div>
      <el-button type="primary" @click="gotoRegister">Sign Up</el-button>
    </el-col>
    <el-col :span="6" :offset="6" class="btm-group" v-else-if="this.$route.name === 'Register'">
      <div class="top-text">Already have an account ?</div>
      <el-button plain @click="gotoLogin">Login</el-button>
    </el-col>
  </el-row>
</template>

<script>
import logo from '@/assets/xproject-icon.png'
export default {
  name: 'LRTop',
  props: {
    curStatus: String
  },
  data () {
    return {
      logo
    }
  },
  methods: {
    gotoWel() {
      this.$router.push({name: 'Welcome'})
    },
    gotoLogin() {
      this.$router.push({name: 'Login'})
    },
    gotoRegister() {
      this.$router.push({name: 'Register'})
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }

  .el-col, .el-row {
    font-size: 0;
  }

  #top-row {
    width: 100%;
    height: 100%;
    display: flex;
  }
  .container {
    display: inline-flex;
    text-align: center;
    flex-direction: row;
    align-items: center;
    width: 340px;
    margin-left: 20px;
  }
  .title {
    font-size: 34px;
    font-weight: bold;
    padding: 0;
    cursor: pointer;
  }
  .img {
    display: inline-block;
    vertical-align: middle;
    width: 40px;
    margin-right: 12px;
    cursor: pointer;
  }
  .btm-group {
    display: inline-flex;
    text-align: center;
    flex-direction: row;
    justify-content: flex-end;
    align-items: center;
    margin-right: 20px;
    width: 340px;
  }

  .top-text {
    font-size: 16px;
    margin-right: 10px
  }
</style>
