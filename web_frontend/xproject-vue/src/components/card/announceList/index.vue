<template>
  <el-card class="proj-card" shadow="hover">
    <div class="clearfix" >
      <span><slot>{{annoTitle}}</slot></span>
      <el-button :icon = "iconData"
                 style="color: orange;float: right" type="text"
                 @click="favour(annoID)"  circle></el-button>
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
      annoID:this.id,
      annoTitle:this.title,
      annoAuthor:this.author,
      annoDate:this.date,
      isStar:this.star,
      iconData:this.star===true?'el-icon-star-on':'el-icon-star-off'
    }
  },
  props: {
    id:{
      type:Number,
      default:-1
    },
    title:{
      type:String,
      default:'Announce 1'
    },
    author:{
      type:String,
      default: 'xxx'
    },
    date:{
      type:String,
      default:'2020-01-01'
    },
    star:{
      type:Boolean,
      default:false
    }
  },

  methods:{
    favour:function (val){
      this.isStar= !this.isStar;
      this.iconData = this.isStar===true?'el-icon-star-on':'el-icon-star-off';
      this.$emit('getStarChange',val)
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
