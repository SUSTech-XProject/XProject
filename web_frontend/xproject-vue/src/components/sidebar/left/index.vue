<template>
  <div id = "left-bar">
  <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
    <el-radio-button :label="false">展开</el-radio-button>
    <el-radio-button :label="true">收起</el-radio-button>
  </el-radio-group>
  <el-menu default-active="1-4-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse">
    <el-menu-item index="1">
      <i class="el-icon-menu"></i>
      <span slot="title">Announcement</span>
    </el-menu-item>

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span slot="title">Team</span>
      </template>
      <el-menu-item-group>

        <el-menu-item index="1-1">My Team</el-menu-item>
        <el-menu-item index="1-2">Forming</el-menu-item>
      </el-menu-item-group>

    </el-submenu>

    <el-menu-item index="3" >
      <i class="el-icon-document"></i>
      <span slot="title">Events</span>
    </el-menu-item>
    <el-menu-item index="4">
      <i class="el-icon-setting"></i>
      <span slot="title">Resources</span>
    </el-menu-item>
    <el-menu-item index="5">
      <i class="el-icon-setting"></i>
      <span slot="title">Submissions</span>
    </el-menu-item>
    <el-menu-item index="6">
      <i class="el-icon-setting"></i>
      <span slot="title">Gradebook</span>
    </el-menu-item>
  </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: true
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
