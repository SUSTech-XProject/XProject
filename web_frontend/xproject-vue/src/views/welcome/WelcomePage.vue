<template>
  <div id="welcome-page" style="width:100%; height:100%">
    <el-container style="height:100%">
      <el-header id="header">
        <Top/>
      </el-header>
      <el-main id="main">
        <Main/>
      </el-main>
      <el-footer id="footer">
        <el-row type="flex" align="middle">
          <p>SUSTech OOAD Course Project</p>
        </el-row>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import Top from '@/views/welcome/components/Top'
import Main from "@/views/welcome/components/Main";
export default {
  name: 'WelcomePage',
  components: {
    Top, Main
  },
  data () {
    return {
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  html,body,#app,.el-container, .el-header{
    padding: 0;
    margin: 0;
    height: 100%;
  }
  #header {
    /*box-shadow: 0 0 12px 0 rgba(0, 0, 0, 0.8);*/
    align-content: center;
    height: 100%;
  }
  #main {
    background: #f6f6f6;
    height: 100%;
    display: flex;
    justify-content: center;
    align-content: center;
  }
  #footer {
    /*box-shadow: 0 0 12px 0 rgba(0, 0, 0, 0.15);*/
    display: flex;
    justify-content: center;
    align-content: center;
  }
</style>
