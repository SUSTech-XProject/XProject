<template>
  <el-card id="base-card">
    <div slot="header" style="font-size: 20px;">
     Team Wanted !
    </div>
    <div style="margin-top: 0">
      <el-row class="selecting" :gutter="20">
        <el-col :span="7" style="padding-left: 50px" >Topics:
          <selector :in-list="op_topic" :index.sync="Topic_ind" ></selector>
        </el-col>
        <el-col :span="7">Target Mems:
          <selector :in-list="op_targetMem" :index.sync="TarMem_ind"></selector>
        </el-col>
        <el-col :span="7">Status:
          <selector :in-list="op_status" :index.sync="Status_ind"></selector>
        </el-col>
      </el-row>
    </div>
    <div v-if="useRecruit===false" class="no-data">Recruit System is Not Open :(</div>
    <div v-else-if="isDued" class="no-data">Recruit System is end :(</div>
    <div v-else-if="teams.length===0" class="no-data">No Teams Here :(</div>
    <div v-else class="board" v-loading="dataLoading">
      <div v-for="team in teams" class="cardList">
        <teamcard v-bind="team"
                  v-if="isVisible(team)"
                  @click.native="openDrawer(team)"></teamcard>

      </div>
    </div>
    <drawer :drawer.sync="drawerCtrl"
            :id="drawerId"
            @closeDrawer = "closeDrawer"></drawer>


  </el-card>
</template>

<script>
import single from '@/components/selector/single'
import searching from '@/components/searching/index'
import teamcard from '@/components/card/teamList/index'
import teamdrawer from '@/views/main/project/team/teamdrawer'
import double from '@/components/selector/double'
import {getTeamInfoList} from '@/api/team'
import {getProjOverview} from '@/api/proj_overview'

export default {
  name: "Forming",
  components:{
    searching,teamcard,drawer:teamdrawer,selector: single,multiSel:double
  },
  data(){
    return{
      isDued:false,
      useRecruit:'',
      dataLoading:false,
      op_topic: [],
      op_targetMem:[],
      op_status:[],
      Topic_ind: '',
      TarMem_ind:'',
      Status_ind:'',
      Tag_ind:[],
      teams:[],
      detailTeam:{},
      drawerCtrl:false,
      drawerId:1,
    }
  },
  mounted () {
    this.reLoad()
  },
  methods:{
    initTeams(){
      let id = this.$store.state.proj.projId
      console.log(id)
      getProjOverview(parseInt(id)).then(resp => {
        console.log(resp)
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.message)
          return false
        }
        let infoDict = resp.data.data
        console.log(infoDict)
        let settings = JSON.parse(infoDict.projSettings)
        console.log(settings)
        if(settings.due_time!==undefined){
          let dueTime = settings.due_time.replace("T"," ").substr(0,19)
          dueTime = dueTime.replace(/-/g,"/");
          var date = new Date(dueTime);
          console.log(date)
          var now = new Date()
          console.log(now)
          if(date.getTime()<now.getTime()){
            this.isDued = true
          }
        }


        this.useRecruit = settings.use_recruit
      }).catch(failResp => {
        this.$message.error(failResp.message)
        console.log(failResp)
      })


      getTeamInfoList(parseInt(id)).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }

        let teamList = resp.data.data
        console.log('===============')
        console.log(teamList)
        this.teams.splice(0,this.teams.length)
        this.op_topic.splice(0,this.op_topic.length)
        this.op_targetMem.splice(0,this.op_targetMem.length)
        this.op_status.splice(0,this.op_status.length)
        for (let i = 0; i < teamList.length; i++) {
          let team = teamList[i]
          if(!this.op_topic.includes(team.topic)){
            this.op_topic.push(team.topic)
          }
          this.teams.push({
            id:team.projInstId,
            name: team.teamName,
            topic: team.topic,
            tarMem:team.targetMemNum,
            tags: JSON.parse(team.tags),
            intro:team.descriptions,
            color_ind:this.getColorIndex(team.topic),
            curMem:team.curMemNum,
            status:team.status,
          })


          if(!this.op_targetMem.includes(team.targetMemNum)){
            this.op_targetMem.push(team.targetMemNum)
          }

          if(!this.op_status.includes(team.status)){
            this.op_status.push(team.status)
          }
        }

      }).catch(failResp=>{
        console.log('fail in getProjList. message=' + failResp.message)
      })
    },
    getColorIndex(topic){
      for (let i = 0; i <this.op_topic.length ; i++) {
        if(this.op_topic[i]==topic){
          return i;}
      }
      return -1;
    },
    reLoad(){
      this.dataLoading = true;
      this.initTeams()
      setTimeout(()=>{
        this.dataLoading = false
      },500)
    },
    openDrawer(val){
      this.drawerId = val.id
      this.drawerCtrl = true
    },
    closeDrawer(){
      this.drawerCtrl = false
      console.log(this.drawerCtrl)
    },
    isVisible(val){
      if(this.Topic_ind!==''){
        const topic = this.op_topic[this.Topic_ind]
        if(topic!==val.topic){return false}
      }
      if(this.TarMem_ind!==''){
        const targetMem = this.op_targetMem[this.TarMem_ind]
        if(targetMem!==val.tarMem){return false}
      }

      if(this.Status_ind!==''){
        const status = this.op_status[this.Status_ind]
        if(status!==val.status){return false}
      }
      return true
    }
  }
}
</script>

<style scoped>
#base-card{
  margin: 15px 10px;
  min-height: 95.2%;
}
.board{
  display: inline-block;
  padding-left: 30px;
  padding-top: 20px;
}
.cardList{
  display: inline-block;
}
.selecting{
  text-align: left;
  display: flex;
  width: 100%;
  padding-top: 10px;
}
.no-data{
  margin-top: 100px;
  margin-bottom: 100px;
  font-size: 20px;
  text-align: center;
  color: #6d7178;
}
</style>
