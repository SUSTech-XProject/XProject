<template>
  <div class="blank">
    <el-card  :class="cardStyle" shadow="hover">
      <div class="clearfix" >
        {{teamName}}
      </div>
      <div style="text-align: center">
      <span v-for="i in Math.min(teamTag.length,5)" style="margin-left: 0.5rem">
          <el-tag :type="tagType[teamTag[i-1].length%4]">{{teamTag[i-1]}}</el-tag>
        </span>
      </div>

      <div class="text item" slot="default">
        <div>ID: {{teamID}}</div>
        <div>Topic:  {{teamTopic}}</div>
        <div>Target Members: {{teamSta}}</div>
        <div>Current Members: {{teamNum}}</div>

      </div>

      <div style="text-align: center;padding-top: 5px">{{teamIntro}}</div>

    </el-card>
  </div>

</template>

<script>

export default {
  data() {
    return {
      teamID:this.id,
      teamTopic:this.topic,
      teamSta:this.status,
      teamTag:this.tags,
      teamName:this.name,
      teamIntro:this.intro,
      teamNum:this.curMem,
      tagType:['',"success","warning","danger"]
    }
  },
  props: {
    id:{type:Number, default:-1},
    topic:{type:String, default:'Topic 1'},
    status:{type:Number, default: 1},
    tags:{type:Array, default:()=>['tag1','tag2','tag3']},
    name:{type:String,default:'default name'},
    intro:{type:String,default:'details'},
    color_ind:{type:Number,default:-1},
    curMem:{type:Number,default:0},
  },
  computed:{
    cardStyle:function (){
      console.log(this.color_ind)
      if(this.color_ind%5===0){
        return 'proj-card0'
      }else if(this.color_ind%5===1){
        return 'proj-card1'
      }else if(this.color_ind%5===2){
        return 'proj-card2'
      }else if(this.color_ind%5===3){
        return 'proj-card3'
      }else{
        return 'proj-card4'
      }
    }
  }

}
</script>

<style scoped>
.blank{
  display: block;
  width: 360px;
  height: 280px;
}
.text {
  font-size: 14px;
  line-height: 1.5em;
}
.item {
  padding-top: 20px;
  padding-left: 15px;
  padding-bottom: 5px;
}
.clearfix{
  vertical-align: center;
  margin: 0;
  font-size: 25px;
  align-content: center;
  text-align: center;
  padding-bottom: 10px;


}
.proj-card0 {
  width: 300px;
  height: 240px;
  text-align: left;
  color: #6d7178;
  background-color: #f9fafc;
}
.proj-card1 {
  width: 300px;
  height: 240px;
  text-align: left;
  color: #6d7178;
  background-color: #e5e9f2;
}
.proj-card2 {
  width: 300px;
  height: 240px;
  text-align: left;
  color: #6d7178;
  background-color: #d3dce6;
}
.proj-card3 {
  width: 300px;
  height: 240px;
  text-align: left;
  color: #6d7178;
  background-color: #fff8f7;
}
.proj-card4 {
  width: 300px;
  height: 240px;
  text-align: left;
  color: #6d7178;
  background-color: #fef7ff;
}
</style>
