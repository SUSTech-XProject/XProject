<template xmlns="http://www.w3.org/1999/html">
    <el-container>
      <el-header>
        <Top></Top>
      </el-header>
      <el-main>
        Welcome to XProject !
      </el-main>
      <el-form>
      <el-form-item style="width: 100%">
        <el-button type="login" plain v-on:click="login">Login</el-button>
      </el-form-item>
      </el-form>
<!--      <el-button type="login" plain style="display: block;margin-left:0 auto">Login</el-button>-->
    </el-container>
</template>

<script>
import Top from '@/views/welcome/Top'
// import WelcomeImg from '@/assets/WelcomePageBackgroundTest.jpg'

export default {
  name: 'WelcomePage',
  components: {
    Top
  },
  data () {
    return {
      msg: 'Welcome to XProject !'
      // styles: {
      //   backgroundImage: 'url(${WelcomeImg})'
      // }
    }
  },
  methods: {
    login () {
      this.$router.replace({path: '/login'})
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-main{
  /*background-color: #edf4ff;*/
  color: #42b983;
  text-align: center;
  line-height: 400px;
  /*width: 200px;*/
  font-size: 70px;
}
.el-button{
  background: #B3C0D1;
  color: black;
  text-align: center;
  /*position: page;*/
  line-height: 20px;
  font-size: large;
  width: 200px;
}
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
</style>
