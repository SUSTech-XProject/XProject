<template>
<!--  <div>-->
<!--    Do the main design here !-->
<!--    <br>-->
<!--    Use sub dir to create page for project/announcement ...-->
<!--    <br>-->
<!--    Hi, {{this.$store.state.role.username}}-->
<!--    <br>-->
<!--    Your roleType is {{this.$store.state.role.roleType}}-->
<!--  </div>-->
  <el-container>
    <left-bar/>
<!--    <el-aside style="width: 0">-->
<!--    </el-aside>-->
    <el-container>
      <el-header>
        <Header/>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
export default {
  name: 'MainPage',
  components: {
    Header, LeftBar
  }
}
</script>

<style scoped>
.el-header, .el-main, .el.container {
  margin: 0;
  padding: 0;
  width: 100%;
}
  .el-main {
    background: #ffffff;
  }
</style>
