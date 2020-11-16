<template>
  <div id = "temp">
    <el-container>
      <el-aside style="width: 201px">
        <left-bar></left-bar>
      </el-aside>
        <el-container>
          <el-header>
            <Header></Header>
          </el-header>
        <el-main>
          <DownDrop></DownDrop>
          <Table></Table>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Table from '@/components/table/index'
import DownDrop from '@/components/downdrop/index'
export default{
  name: 'Testpage',
  components: {
    LeftBar, Header, Table, DownDrop
  }
}
</script>

<style>
.header{
  width : 100%;
  float:right;
  height: 60px;
}
.el-header {
  background-color: #edf4ff;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.dropdown{
  width: 100px;
}
/*.el-aside {*/
/*  background-color: #D3DCE6;*/
/*  color: #333;*/
/*  text-align: center;*/
/*  !*line-height: 200px;*!*/
/*}*/

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  /*line-height: 160px;*/
}
.table{
  /*position: absolute;left: 11.5% ;*/
  /*height: 200px;*/
  /*width: 200px;*/
}
.dropdown{
  /*position: absolute;left: 10%;*/
}
/*body > .el-container {*/
/*  margin-bottom: 40px;*/
/*}*/

/*.el-container:nth-child(5) .el-aside,*/
/*.el-container:nth-child(6) .el-aside {*/
/*  line-height: 260px;*/
/*}*/

/*.el-container:nth-child(7) .el-aside {*/
/*  line-height: 320px;*/
/*}*/
</style>
