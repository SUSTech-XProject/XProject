<template>
  <el-card  class="proj-card" shadow="hover">
    <div class="clearfix" >
      {{teamTopic}}

    </div>

    <div class="text item" slot="default">
      <div>ID: {{teamID}}</div>
      <div>Status: {{teamSta}}</div>
      <div>Tags:
        <span v-for="i in Math.min(teamTag.length,5)" style="margin-left: 0.5rem">
          <el-tag :type="tagType[i-1]">{{teamTag[i-1]}}</el-tag>
        </span>
      </div>
    </div>


  </el-card>
</template>

<script>

export default {
  data() {
    return {
      teamID:this.id,
      teamTopic:this.topic,
      teamSta:this.status,
      teamTag:this.tag,
      tagType:['',"success","warning","danger","info"]
    }
  },
  props: {
    id:{type:Number, default:-1},
    topic:{type:String, default:'Topic 1'},
    status:{type:String, default: '1/5'},
    tag:{type:Array, default:()=>['tag1','tag2','tag3']},
  },

}
</script>

<style scoped>
.text {
  font-size: 14px;
  line-height: 1.5em;
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
  text-align: center;
  height: 30px;

}

.proj-card {
  width: 300px;
  height: 200px;
  text-align: left;
}
</style>
