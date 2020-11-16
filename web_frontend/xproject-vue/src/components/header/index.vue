<template>
  <div id = "header">
    <el-header>
      <i v-bind:class="[{'el-icon-d-arrow-right':isFolded},'el-icon-d-arrow-left']"></i>
      <i style="margin-left: 80%" class="el-icon-help"></i>
      <i style="margin-left: 50px" class ="el-icon-user"></i>
    </el-header>
  </div>
</template>

<script>
export default {
  name: 'index',

  data () {
    return {
      isFolded: false
    }
  }
}
</script>

<style>
#header {
  background-color: #edf4ff;
  color: #333;
  line-height: 60px;
  width:100%;
  font-size: 20px;
  text-align: left;
}

</style>
