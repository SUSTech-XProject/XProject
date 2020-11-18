<template>
  <el-card class="proj-card" shadow="hover">
    <div class="clearfix" >
      <span><slot>{{projName}}</slot></span>
      <span><slot>{{projCour}}</slot></span>
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
      projName:this.name,
      projCour:this.course,
      isStar:this.star,
      iconData:'el-icon-star-off'
    }
  },
  props: {
    name:{
      type:String,
      default:'Project1'
    },
    course:{
      type:String,
      default: 'Course1'
    },
    star:{
      type:Boolean,
      default:false
    }
  },

  methods:{
    passData(val){
      this.$emit();
    },
    favour:function (){
      if(this.star===false){
        this.iconData = 'el-icon-star-on'
      }else{
        this.iconData = 'el-icon-star-off'
      }
      this.star = !this.star;
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

.proj-card {
  width: 60%;
  height: 80px;
}
</style>
