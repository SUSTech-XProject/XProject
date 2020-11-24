<template>
  <el-card  class="proj-card" shadow="hover">
    <div class="clearfix" >
      {{ teamName}}
    </div>
    <div style="text-align: center">
      <span v-for="i in Math.min(teamTag.length,5)" style="margin-left: 0.5rem">
          <el-tag :type="tagType[i-1]">{{teamTag[i-1]}}</el-tag>
        </span>
    </div>

    <div class="text item" slot="default">
      <div>ID: {{teamID}}</div>
      <div>Topic:  {{teamTopic}}</div>
      <div>Status: {{teamSta}}</div>
    </div>

    <div style="text-align: center">{{teamIntro}}</div>

  </el-card>
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
      tagType:['',"success","warning","danger","info"]
    }
  },
  props: {
    id:{type:Number, default:-1},
    topic:{type:String, default:'Topic 1'},
    status:{type:String, default: '1/5'},
    tags:{type:Array, default:()=>['tag1','tag2','tag3']},
    name:{type:String,default:'default name'},
    intro:{type:String,default:'details'}
  },

}
</script>

<style scoped>
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
  font-size: 22px;
  align-content: center;
  text-align: center;
  padding-bottom: 10px;


}

.proj-card {
  width: 300px;
  height: 240px;
  text-align: left;
}
</style>
