<template>
  <div style="height: 100%">
    <el-container id = "temp">
      <el-aside style="width: 201px">
        <left-bar></left-bar>
      </el-aside>
      <el-container>
        <el-header>
          <Header></Header>
        </el-header>

        <el-main>
          <!-- your code here-->
          <div></div>
          <div class="cardList">
            <Card></Card>
          </div>
          <div class="cardList">
            <Card></Card>
          </div>
          <div>
            <Card></Card>
          </div>




        </el-main>
      </el-container>

    </el-container>

  </div>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Card   from '@/components/card/projectList/index'
export default{
  name:"Forming",
  components:{
    LeftBar,Header,Card
  },
  data(){
    return{
      proNum:3,
    }
  }
}
</script>

<style >
html,body{
  /*设置内部填充为0，几个布局元素之间没有间距*/
  padding: 0;
  /*外部间距也是如此设置*/
  margin: 0;
  /*统一设置高度为100%*/
  height: 100%;
}

.cardList{
  height: 200px;
}

#temp{
  height: 100%;
}




</style>
