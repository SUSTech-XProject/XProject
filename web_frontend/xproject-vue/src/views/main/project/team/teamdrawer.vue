<template>
  <div>
    <el-drawer
      :size = "size"
      :with-header = "false"
      :visible.sync="teamDrawer"
      :before-close="handleClose">
      <h2>{{teamID}}</h2>
      <!--      <h2>{{teamData.id}}</h2>-->
      <hr/>
      <h4>Author: {{teamTopic}}</h4>




      <div>
        <el-button type = "primary" @click="confirmed">confirm</el-button>
        <el-button>cancel</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>


export default {
  name: "teamdrawer",
  data() {
    return {
      size:'62%',
      // teamData:{}
      teamDrawer:'',
      teamID:'',
      teamTopic:'',
      teamSta:'',
      teamTags:[],
      teamIntro:'',
      isConfirmed:false
      //

    };
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.refresh()
          done();
        })
        .catch(_ => {});
    },
    refresh(){
      console.log("close")
      this.$emit('closeDrawer',this.isConfirmed)
    },
    confirmed(){
      this.isConfirmed = true
    }
  },

  created () {
    // this.teamData = this.data
    this.teamDrawer = this.drawer
    this.teamID = this.id
    this.teamTopic = this.topic
    this.teamSta = this.status
    this.teamTags = this.tags
    this.teamIntro = this.intro
  },
  watch:{
    // data(val){this.teamData = val}
    drawer(val){this.teamDrawer = val},
    id(val){this.teamID = val},
    topic(val){this.teamTopic = val},
    status(val){this.teamSta = val},
    tags(val){this.teamTags = val},
    intro(val){this.teamIntro = val}
  },
  props:{
    // data:{type:Object,default:()=>{}},
    drawer:{type:Boolean,default:false},
    id:{type:Number,default:-1},
    topic:{type:String, default:"Topic"},
    status:{type:String,default: "1/5"},
    tags:{type:Array,default:()=>['tag1','tag2','tag3']},
    intro:{type:String,default:"introduction here"},
  }
};
</script>

<style scoped>

</style>
