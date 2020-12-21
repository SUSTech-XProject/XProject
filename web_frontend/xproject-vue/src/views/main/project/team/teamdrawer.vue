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
              <el-avatar :src="teamURL"
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
                    <el-col :span="3" class="left">
                      <span @click.native:="openDrawer(stu)">
                        <el-avatar :src="stu.iconUrl" :size="50"></el-avatar>
                      </span>
                    </el-col>
                    <el-col :span="12">
                      <div
                        style="font-size: 20px; padding:0; text-align:left; margin-top: 22px">
                        {{ stu.username }}</div>
                    </el-col>
                  </el-row>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>


      <drawer
        :drawer.sync="memDrawer"
        :id="memID"
        @closeDrawerStu = "closeDrawerStu"
      ></drawer>

      <footer>
        <div v-if="this.$store.state.role.roleType!=='Teacher'" style="margin-left: 80px">
          <span v-if="haveTeam">
            <el-button type = "info" disabled @click="confirmed">Apply</el-button>
          </span>
          <span v-else>
            <el-button type = "primary" @click="confirmed">Apply</el-button>
          </span>

        </div>
      </footer>


    </el-drawer>
  </div>
</template>

<script>
import {getMyTeamDetail, getTeamDetail} from '@/api/team'
import {postApply} from '@/api/team'
import stuInfoDrawer from '@/views/main/project/team/stuInfoDrawer'

export default {
  name: "teamdrawer",
  components:{
    drawer:stuInfoDrawer
  },
  data() {
    return {
      size:'62%',
      teamDrawer:'',
      teamID:'',
      haveTeam:false,
      teamName:'',
      teamTopic:'',
      teamSta:'',
      teamTags:[],
      teamIntro:'',
      teamURL:'',
      teamMembers:[],
      isConfirmed:false,
      tagType:['',"success","warning","danger","info"],
      memDrawer:false,
      memID:1

    };
  },
  methods: {
    refresh(){
      this.$emit('closeDrawer',this.isConfirmed)
      //清除数据？

    },
    confirmed(){
      console.log(this.teamID)

      this.$prompt('Please input application information:','Alert',{
        confirmButtonText:'Submit',
        cancelButtonText:'Cancel'
      }).then(({msg})=>{
        this.$confirm('Apply for this team?', 'Alert', {
          confirmButtonText: 'confirm',
          cancelButtonText: 'cancel',
          type: 'info'
        }).then(() => {
          postApply(msg, this.teamID
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
      }).catch(()=>{
        this.$message({
          type: 'info',
          message: 'Apply canceled'
        });
      })

    },
    initTeam(teamId){
      getMyTeamDetail(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code === 200) {
          let infoDict = resp.data.data
          this.haveTeam = infoDict != null;
        }
      })
      getTeamDetail(teamId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        let team = resp.data.data;
        console.log(team)
        //挂载
        this.teamIntro = team.descriptions
        this.teamTags = JSON.parse(team.tags)
        this.teamName = team.teamName
        this.teamMembers = team.teamMemberList
        this.teamSta = this.teamMembers.length+"/"+team.targetMemNum
        this.teamTopic = team.topic
        this.teamURL = team.iconUrl
        console.log(this.teamTags)

      })
    },
    openDrawer(val){
      // console.log("open stu drawer")
      // console.log(val)
      this.memID = val.roleId
      console.log(this.memID)
      this.memDrawer = true
    },
    closeDrawerStu(){
      this.memDrawer = false
    }
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

  },
  props:{
    drawer:{type:Boolean,default:false},
    id:{type:Number,default:-1},
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
