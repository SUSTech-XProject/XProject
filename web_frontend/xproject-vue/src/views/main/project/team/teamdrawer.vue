<template>
  <div>
    <el-drawer
      :destroy-on-close = "true"
      :size = "size"
      :with-header = "false"
      :visible.sync="teamDrawer"
      :before-close="refresh"
    >

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
            <div style="margin-left: 15px">{{teamTopic}}</div>
            <div style="padding: 0; text-align:left; margin-top: 20px">
              <span v-for="i in Math.min(teamTags.length,5)" style="margin-left: 0.5rem">
                <el-tag :type="tagType[teamTags[i-1].length%4]">{{teamTags[i-1]}}</el-tag>
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

                <div v-for="stu in teamMembers">
                  <el-row>
                    <el-col :span="2" class="left">
                      <el-avatar src="https://ww1.sinaimg.cn/thumb150/006z25tvly1genhm1qn9vj30tc0t9wi8.jpg"
                                 :size="50"></el-avatar>
                    </el-col>
                    <el-col :span="12">
                      <div style="font-size: 20px; padding:0; text-align:left; margin-top: 22px">{{ stu.username }}</div>
                    </el-col>
                  </el-row>
                </div>
              </div>

            </el-tab-pane>
          </el-tabs>
        </div>


      </div>



      <footer>
        <div style="margin-left: 80px">
          <el-button type = "primary" @click="confirmed">Apply</el-button>
        </div>
      </footer>

    </el-drawer>
  </div>
</template>

<script>
import {getTeamDetail} from '@/api/team'
import {postApply} from '@/api/team'

export default {
  name: "teamdrawer",
  data() {
    return {
      size:'62%',
      teamDrawer:'',
      teamID:'',
      teamName:'',
      teamTopic:'',
      teamSta:'',
      teamTags:[],
      teamIntro:'',
      teamMembers:[],
      isConfirmed:false,
      tagType:['',"success","warning","danger","info"]
    };
  },
  methods: {
    refresh(){
      console.log("close")
      this.$emit('closeDrawer',this.isConfirmed)
      //清除数据？

    },
    confirmed(){
      this.$confirm('Apply for this team?', 'Alert', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'info'
      }).then(() => {
        //后台发送！！！
        postApply(
          //this.$store.state.role.username,
          this.teamID
        ).then(resp =>{
          if(resp.data.code===200){
            this.$message({
              type: 'success',
              message: 'Applied successfully'
            });
            this.refresh()
          }else{
            this.$message.error(resp.data.message)
          }
        }).catch(failResponse=>{
          this.$message.error('Back-end no response')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'canceled'
        });
      });
    },
    initTeam(teamId){
      getTeamDetail(teamId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        let team = resp.data.data;
        //挂载
        this.teamIntro = team.descriptions
        this.teamTags = team.tags
        this.teamName = team.teamName
        this.teamMembers = team.teamMemberList
        this.teamSta = this.teamMembers.length+"/"+team.targetMemNum
        this.teamTopic = team.topic
        console.log(this.teamMembers.length)

      })
    },
  },
  created () {
    this.teamDrawer = this.drawer
    this.teamID = this.id
  },
  watch:{
    // data(val){this.teamData = val}
    id(val){
      this.teamID = val
    },
    drawer(val){
      this.teamDrawer = val
      if(this.teamDrawer === true){
        this.initTeam(this.teamID)
      }
    },
    // id(val){this.teamID = val},
    // topic(val){this.teamTopic = val},
    // status(val){this.teamSta = val},
    // tags(val){this.teamTags = val},
    // intro(val){this.teamIntro = val},
    // name(val){this.teamName = val}
  },
  props:{
    // data:{type:Object,default:()=>{}},
    drawer:{type:Boolean,default:false},
    id:{type:Number,default:-1},
    // name:{type:String,default:"Default Team"},
    // topic:{type:String, default:"Topic"},
    // status:{type:String,default: "1/5"},
    // tags:{type:Array,default:()=>['tag1','tag2','tag3']},
    // intro:{type:String,default:"introduction here"},
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
footer {
  bottom: 30px;
  position: fixed;
  width: 62%;
}
.team-name{
  font-size: 40px;
  padding:0;
  text-align:left;
  margin-top: 20px;
  margin-bottom: 10px;
  margin-left:10px;
}
</style>
