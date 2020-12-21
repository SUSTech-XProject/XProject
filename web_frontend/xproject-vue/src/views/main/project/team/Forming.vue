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
          <selector :in-list="op_sta" :index.sync="Status_ind"></selector>
        </el-col>
        <el-col :span="7">Tags:
          <multi-sel :in-list="op_tag" :index.sync="Tag_ind"></multi-sel>
        </el-col>
      </el-row>
    </div>

    <div class="board" v-loading="dataLoading">
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

export default {
  name: "Forming",
  components:{
    searching,teamcard,drawer:teamdrawer,selector: single,multiSel:double
  },
  data(){
    return{
      dataLoading:false,
      op_topic: [],
      op_sta:[],
      op_tag:[],
      Topic_ind: '',
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
        this.op_sta.splice(0,this.op_sta.length)
        this.op_tag.splice(0,this.op_tag.length)
        for (let i = 0; i < teamList.length; i++) {
          let team = teamList[i]
          if(!this.op_topic.includes(team.topic)){
            this.op_topic.push(team.topic)
          }
          this.teams.push({
            id:team.projInstId,
            name: team.teamName,
            topic: team.topic,
            status:team.targetMemNum,
            tags: JSON.parse(team.tags),
            intro:team.descriptions,
            color_ind:this.getColorIndex(team.topic),
            curMem:team.curMemNum
          })


          if(!this.op_sta.includes(team.targetMemNum)){
            this.op_sta.push(team.targetMemNum)
          }

          for (let j = 0; j <JSON.parse(team.tags).length ; j++) {
            let tag = JSON.parse(team.tags)[j]
            if(!this.op_tag.includes(tag)){
              this.op_tag.push(tag)
            }

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
      if(this.Status_ind!==''){
        const status = this.op_sta[this.Status_ind]
        if(status!==val.status){return false}
      }

      if(this.Tag_ind.length!==0){
        console.log(this.Tag_ind)
        console.log(this.op_tag)
        for (const i in this.Tag_ind) {
          console.log(this.op_tag[i])
          if(val.tags.indexOf(this.op_tag[i])!==-1){
            return true
          }
        }
        return false
      }
      return true
    }
  }
}
</script>

<style scoped>
#base-card{
  margin: 15px 10px
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
</style>
