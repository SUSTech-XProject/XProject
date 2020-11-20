<template>
  <el-container>
    <el-aside style="width: fit-content">
      <left-bar></left-bar>
    </el-aside>

    <el-container>
      <el-header>
        <Header></Header>
      </el-header>

      <el-main>

        <el-row style="margin-top: 20px">
          <el-col :span="6" :offset="1">
            <div>
              <!--                <el-avatar :size="200"><div style="font-size: 80px; text-align:center; margin-top: -5px">n</div></el-avatar>-->
              <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" :size="200"></el-avatar>
            </div>
          </el-col>

          <el-col :span="16" :offset="1">
            <div style="font-size: 80px; padding: 0; text-align:left; margin-top: 20px">name</div>
            <div style="padding: 0; text-align:left; margin-top: 20px">
              <el-tag>标签一</el-tag>
              <el-tag type="success">标签二</el-tag>
              <el-tag type="info">标签三</el-tag>
              <el-tag type="warning">标签四</el-tag>
              <el-tag type="danger">标签五</el-tag>
            </div>
          </el-col>
        </el-row>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'

export default {
name: "myTeam",
  components: {
    LeftBar, Header
  }
}
</script>

<style scoped>

</style>
