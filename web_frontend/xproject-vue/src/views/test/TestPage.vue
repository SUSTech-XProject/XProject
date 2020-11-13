<template>
  <div id = "temp">
    <el-container>
      <el-aside style="width: 201px">
        <left-bar></left-bar>
      </el-aside>
      <el-container class = 'header'>
        <Header></Header>
      </el-container>
    </el-container>

  </div>
</template>

<script>
  import LeftBar from '@/components/sidebar/index'
  import Header from '@/components/header/index'
  export default{
    name:"Testpage",
    components:{
      LeftBar,Header
    }
  }
</script>

<style>
.header{
  width : 100%;
  float:right;
  height: 60px;
}
</style>
