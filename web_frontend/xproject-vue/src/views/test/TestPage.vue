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
html,body{
  /*设置内部填充为0，几个布局元素之间没有间距*/
  padding: 0;
  /*外部间距也是如此设置*/
  margin: 0;
  /*统一设置高度为100%*/
  height: 100%;
}

.header{
  width : 100%;
  float:right;
  height: 60px;
}
</style>
