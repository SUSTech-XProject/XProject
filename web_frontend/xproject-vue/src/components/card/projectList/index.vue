<template>
  <el-card class="proj-card" shadow="hover" :body-style="{ paddingTop: '10px'}">
    <el-row style="display: flex; justify-content: flex-start; align-items: center">
      <span style="font-size: 20px"><slot>{{proj.name}}</slot></span>
      <span style="font-size: 15px; margin-left: 20px; color: #aaaaaa"><slot>{{proj.course}}</slot></span>

      <el-button :icon = "iconData"
                 style="color: orange; font-size: 20px; margin-left: auto" type="text"
                 @click.stop="favour(proj.id)"  circle></el-button>
    </el-row>

    <el-row style="display: flex; justify-content: flex-start; align-items: center">
      <span style="font-size: 15px; color: #aaaaaa"><slot>Description: {{proj.description}}</slot></span>
    </el-row>



    <!--    <div v-for="o in 2" :key="o" class="text item">-->
    <!--      {{'列表内容 ' + o }}-->
    <!--    </div>-->

  </el-card>
</template>

<script>

export default {

  data() {
    return {
      iconData: this.proj.star===true?'el-icon-star-on':'el-icon-star-off'
    }
  },
  // use list for props
  // props: [
  //   'proj',
  //   'index',
  // ],
  // or an object
  props: {
    proj: Object,
    index: Number
  },
  methods:{
    favour:function (val) {
      this.proj.isStar= !this.proj.isStar;
      this.iconData = this.proj.isStar===true?'el-icon-star-on':'el-icon-star-off';
      this.$emit('getStarChange',val)
    }
  }
}
</script>

<style scoped>

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
  text-align: center;
  vertical-align: middle;
  height: 100%;

}
.clearfix:after {
  clear: both
}

.proj-card {
  width: 80%;
  height: 100px;
}
</style>
