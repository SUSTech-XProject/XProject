<template>
  <el-card class="box-card" shadow="hover">
    <div class="clearfix" >
      <span><slot>Name</slot></span>
      <span><slot>Course</slot></span>
      <el-button :icon = "iconData"
                 style="color: orange;float: right" type="text"
                 @click="favour"  circle></el-button>
    </div>
    <!--    <div v-for="o in 2" :key="o" class="text item">-->
    <!--      {{'列表内容 ' + o }}-->
    <!--    </div>-->

  </el-card>
</template>

<script>

export default {
  data() {
    return {
      Name: 'Project Name',
      course: 'course1',
      isFavour:false,
      iconData:'el-icon-star-off'

    }
  },
  props:{
    testName:String,
    testNum:Number,
  },
  methods:{
    favour:function (){
      if(this.isFavour===false){
        this.iconData = 'el-icon-star-on'
      }else{
        this.iconData = 'el-icon-star-off'
      }
      this.isFavour = !this.isFavour;
    }
  }
}
</script>

<style>

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

.box-card {
  width: 60%;
  height: 80px;
}
</style>
