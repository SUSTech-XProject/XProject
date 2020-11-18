<template>
  <el-container>
    <el-aside style="width: 201px">
      <left-bar></left-bar>
    </el-aside>
    <el-container class = 'header'>
      <Header></Header>
    </el-container>
  </el-container>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
export default {
  name: 'Overview',
  components: {
    Header, LeftBar
  }
}
</script>

<style scoped>
.header{
  width : 100%;
  float:right;
  height: 60px;
}
</style>
