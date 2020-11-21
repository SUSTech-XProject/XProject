<template>
  <el-card class="proj-card" shadow="hover">
    <div class="clearfix" >
      <span><slot>{{projName}}</slot></span>
      <span><slot>{{projCour}}</slot></span>
      <el-button :icon = "iconData"
                 style="color: orange;float: right" type="text"
                 @click="favour(projId)" circle></el-button>
      <el-button type="primary"
                 class='el-icon-d-arrow-right'
                 @click="gotoProjOverview"></el-button>
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
      projId:this.id,
      projName:this.name,
      projCour:this.course,
      isStar:this.star,
      iconData:this.star===true?'el-icon-star-on':'el-icon-star-off'
    }
  },
  props: {
    id:{
      type:Number,
      default:-1
    },
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
    favour: function (val) {
      this.isStar= !this.isStar;
      this.iconData = this.isStar===true?'el-icon-star-on':'el-icon-star-off';
      this.$emit('getStarChange', val)
    },
    gotoProjOverview: function () {
      this.$emit('gotoProjOverview', this.projId)
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
