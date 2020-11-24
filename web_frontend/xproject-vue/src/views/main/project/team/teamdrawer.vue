<template>
  <div>
    <el-drawer
      :destroy-on-close = "true"
      :size = "size"
      :with-header = "false"
      :visible.sync="teamDrawer"
      :before-close="refresh">

      <div>
        <el-row style="margin-top: 20px">
          <el-col :span="4" :offset="2">
            <div>
              <!--                <el-avatar :size="200"><div style="font-size: 80px; text-align:center; margin-top: -5px">n</div></el-avatar>-->
              <el-avatar src="http://wx1.sinaimg.cn/large/0072GgNply1g5takxmar5j309q09w3yl.jpg"
                         :size="150"></el-avatar>
            </div>
          </el-col>

          <el-col :span="16">
            <div class="team-name">{{teamName}}</div>
            <div style="padding: 0; text-align:left; margin-top: 20px">
              <span v-for="i in Math.min(teamTags.length,5)" style="margin-left: 0.5rem">
                <el-tag :type="tagType[i-1]">{{teamTags[i-1]}}</el-tag>
              </span>
            </div>




          </el-col>
        </el-row>

        <div style="margin-top: 40px; margin-left:40px">
          <el-tabs value="intro" type="card">
            <el-tab-pane label="Introduction" name="intro">
              <div class="tab-box">
                {{teamIntro}}
              </div>

            </el-tab-pane>

            <el-tab-pane label="Members" name="member">
              <div class="tab-box">
                <div style="font-size: 20px;">
                  Status:  {{teamSta}}
                </div>
                <el-row>
                  <el-col :span="2" class="left">
                    <el-avatar src="https://ww1.sinaimg.cn/thumb150/006z25tvly1genhm1qn9vj30tc0t9wi8.jpg"
                               :size="50"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                    <div style="font-size: 20px; padding:0; text-align:left; margin-top: 22px">name</div>
                  </el-col>
                </el-row>

                <el-row style="margin-top: 10px" class="left">
                  <el-col :span="2">
                    <el-avatar src="https://ww4.sinaimg.cn/orj360/0064eUp2ly1ge61i3malnj3073077746.jpg"
                               :size="50"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                    <div style="font-size: 20px; padding:0; text-align:left; margin-top: 15px">name</div>
                  </el-col>
                </el-row>
              </div>

            </el-tab-pane>

          </el-tabs>
        </div>
      </div>



      <footer>
        <div style="text-align: center">
        <el-button type = "primary" @click="confirmed">Apply</el-button>
      </div>
      </footer>

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
      teamName:'',
      teamTopic:'',
      teamSta:'',
      teamTags:[],
      teamIntro:'',
      isConfirmed:false,
      tagType:['',"success","warning","danger","info"]
    };
  },
  methods: {
    refresh(){
      console.log("close")
      this.$emit('closeDrawer',this.isConfirmed)
    },
    confirmed(){
      this.$confirm('Apply for this team?', 'Alert', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'info'
      }).then(() => {
        this.isConfirmed = true
        //后台发送！！！

        this.$message({
          type: 'success',
          message: 'Applied successfully'
        });
        this.refresh()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'canceled'
        });
      });
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
    this.teamName = this.name
  },
  watch:{
    // data(val){this.teamData = val}
    drawer(val){this.teamDrawer = val},
    id(val){this.teamID = val},
    topic(val){this.teamTopic = val},
    status(val){this.teamSta = val},
    tags(val){this.teamTags = val},
    intro(val){this.teamIntro = val},
    name(val){this.teamName = val}
  },
  props:{
    // data:{type:Object,default:()=>{}},
    drawer:{type:Boolean,default:false},
    id:{type:Number,default:-1},
    name:{type:String,default:"Default Team"},
    topic:{type:String, default:"Topic"},
    status:{type:String,default: "1/5"},
    tags:{type:Array,default:()=>['tag1','tag2','tag3']},
    intro:{type:String,default:"introduction here"},
  }
};
</script>

<style scoped>
.tab-box{
  font-size: 15px;
  padding:0;
  text-align:left;
  margin-top: 15px;
  margin-left: 20px;
}
footer{
  bottom: 30px;
  position: fixed;
  width: 62%;
  text-align: center;
}
.team-name{
  font-size: 40px;
  padding:0;
  text-align:left;
  margin-top: 20px
}
</style>
