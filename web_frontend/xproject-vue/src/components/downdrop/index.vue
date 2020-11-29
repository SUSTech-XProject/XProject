<template>
  <div id="downdrop">
    <el-dropdown @command="handleCommand">
  <span class="el-dropdown-link">
    Test<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="T1">T1</el-dropdown-item>
        <el-dropdown-item command="T2">T2</el-dropdown-item>
        <el-dropdown-item command="T3">T3</el-dropdown-item>
        <el-dropdown-item command="Banned" disabled>Banned</el-dropdown-item>
        <el-dropdown-item command="Divided" divided>Divided</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  methods: {
    handleCommand (command) {
      this.$message('click on item ' + command)
    }
  }
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
