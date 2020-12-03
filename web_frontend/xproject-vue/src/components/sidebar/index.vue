<template>
  <el-menu :default-active="$route.path"
           class="el-menu-vertical-demo"
           id="left-bar"
           @open="handleOpen" @close="handleClose"
           @select="handleSelect"
           :collapse="isCollapse" style="height: 100%">
    <el-menu-item id="title" index="Welcome">
      <i class="el-icon-first-aid-kit" ></i>
      <span slot="title">XProject</span>
    </el-menu-item>

    <el-menu-item index="HomePage">
      <i class="el-icon-s-home"></i>
      <span slot="default">Home Page</span>
    </el-menu-item>

    <el-menu-item index="ProjectList">
      <i class="el-icon-notebook-1"></i>
      <span slot="default">Project List</span>
    </el-menu-item>

    <el-menu-item index="Account">
      <i class="el-icon-s-custom"></i>
      <span slot="title">Account</span>
    </el-menu-item>

    <el-menu-item-group v-if="this.$route.path.indexOf('project') !== -1" id="proj-items">
      <div id="proj-title">
        <span v-if="!isCollapse" style="margin-left: 22px">Project List</span>
        <span v-if="isCollapse" style="margin-left: 22px"></span>
<!--        <i class="el-icon-close" style="float: right; margin-top: 2px; font-size: 13px"/>-->
      </div>

      <el-menu-item index="ProjOverview" >
        <i class="el-icon-date"></i>
        <span slot="title">Overview</span>
      </el-menu-item>

      <el-submenu index="@3">
        <template slot="title">
          <i class="el-icon-connection"></i>
          <span v-if="!isCollapse" slot="title">Team</span>
          <span v-if="isCollapse" slot="title"></span>
        </template>

        <el-menu-item-group>
          <el-menu-item index="MyTeam">My Team</el-menu-item>
          <el-menu-item index="Forming">Forming</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-menu-item index="@4" >
        <i class="el-icon-date"></i>
        <span slot="title">Events</span>
      </el-menu-item>
      <el-menu-item index="@5">
        <i class="el-icon-folder-opened"></i>
        <span slot="title">Resources</span>
      </el-menu-item>
      <el-menu-item index="@6">
        <i class="el-icon-document"></i>
        <span slot="title">Submissions</span>
      </el-menu-item>
      <el-menu-item index="Gradebook">
        <i class="el-icon-collection"></i>
        <span slot="title">Gradebook</span>
      </el-menu-item>
    </el-menu-item-group>

    <el-radio-group v-model="isCollapse">
      <i v-show="!isCollapse" class="el-icon-s-fold expandBtn" @click="isCollapse=true"></i>
      <i v-show="isCollapse" class="el-icon-s-fold shrinkBtn" @click="isCollapse=false"></i>
    </el-radio-group>

  </el-menu>
  <!--    <el-radio-button :label="false">Open</el-radio-button>-->
  <!--    <el-radio-button :label="true">Close</el-radio-button>-->
  <!--  <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">-->
  <!--      <el-radio-button :label="false">展开</el-radio-button>-->
  <!--      <el-radio-button :label="true">收起</el-radio-button>-->
</template>

<script>
  import logo from '@/assets/xproject-icon.png'
  export default {
    data () {
      return {
        isCollapse: false,
        show: false,
        logo
      }
    },
    methods: {
      handleOpen (key, keyPath) {
        console.log(key, keyPath)
      },
      handleClose (key, keyPath) {
        console.log(key, keyPath)
      },
      handleSelect (key, keyPath) {
        console.log(key, keyPath)
        if (key.indexOf("@") !== -1) {
          console.log('Not finished')
        } else {
          this.$router.push({name: key})
        }
      }
    }
  }
</script>

<style scoped>
#left-bar{
  text-align: left;
  /*background: blueviolet;*/
}
.el-radio-group {
  /*position: center;*/
  position: absolute;
  bottom: 5%;
  font-size: 20px;
  /*line-height: 300%;*/
  line-height: 100%;
  /*margin-bottom: 10px;*/
  /*margin-top: 10%;*/
  /*margin-bottom: 10%;*/
  /*margin-top: 20px;*/
  /*margin-left: 0px;*/
  /*width: 200px;*/
}
.shrinkBtn {
  position: center;
  /*left: -18px;*/
  /*top: 38px;*/
  color: #151d41;
  /*margin-bottom: 10%;*/
  margin-left: 20px;
  transform: rotate(180deg);
}
.expandBtn {
  /*position: center;*/
  /*left: -18px;*/
  /*top: 38px;*/
  color: #151d41;
  margin-left: 160px;
  /*transform: rotate(0deg);*/
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 100%;
  margin: 0;
  padding: 0;
  text-align: left;
}
#title{
  background-color: #303f55;
  color: white;
  font-size: 20px;
  height: 60px;
}
#left-bar{
  height: 100%;
  background: #303f55;
}

  #proj-title {
    font-size: 17px;
    padding: 10px 0 10px 0;
    background-color: #f8f8f8;
  }
  #left-bar span, i {
    color: #becad8;
  }
  .img {
    display: inline-block;
    vertical-align: middle;
    width: 30px;
    margin-right: 12px;
    cursor: default;
  }
</style>
