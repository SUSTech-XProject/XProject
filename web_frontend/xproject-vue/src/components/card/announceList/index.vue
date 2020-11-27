<template>
  <el-card  class="proj-card" shadow="hover">
    <div class="clearfix">
      <span>{{annoTitle}}</span>
      <el-button :icon = "iconData"
                style="color: orange;float: right" type="text"
                @click.stop="favour(annoID)"  circle></el-button>

    </div>

    <div class="text item" slot="default">
      {{'Saved by: '+annoAuthor}}
      <span style="padding-left: 25px">{{'Modified Date: '+annoDate}}</span>
    </div>

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
    id:{type:Number, default:-1},
    title:{type:String, default:'Announce 1'},
    author:{type:String, default: 'xxx'},
    date:{type:String, default:'2020-01-01'},
    star:{type:Boolean, default:false}
  },

  methods:{
    favour:function (val){
      this.isStar= !this.isStar;
      this.iconData = this.isStar===true?'el-icon-star-on':'el-icon-star-off';
      this.$emit('getStarChange',val)
    },

  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
  padding-top: 20px;
}
.clearfix{
  vertical-align: center;
  margin: 0;
  padding: 0;
  font-size: 22px;
  align-content: center;

}
/*.clearfix:before,*/
/*.clearfix:after {*/
/*  display: table;*/
/*  content: "";*/
/*  height: 60px;*/

/*}*/
/*.clearfix:after {*/
/*  clear: both*/
/*}*/

.proj-card {
  width: 600px;
  height: 100px;
  text-align: left;
}
</style>
