<template>
  <div id="lr-page" style="width:100%; height:100%">
    <el-container style="height:100%">
      <el-header id="header">
        <LRTop/>
      </el-header>
      <el-main id="main">
        <router-view/>
      </el-main>
<!--      <el-footer id="footer">-->
<!--      </el-footer>-->
    </el-container>
  </div>
</template>

<script>
import LRTop from "@/views/visitor/components/LRTop";
import Login from "@/views/visitor/components/Login";
import Register from "@/views/visitor/components/Register";

export default {
  name: "LRPage",
  components: {
    LRTop, Login, Register
  },
  data () {
    return {
      curStatus: ''
    }
  },
  methods: {

  }
}
</script>

<style scoped>
  html,body,#app,.el-container, .el-header{
    padding: 0;
    margin: 0;
    height: 100%;
  }
  #header {
    align-content: center;
    height: 100%;
  }
  #main {
    background: #f6f6f6;
    height: 100%;
  }
</style>
