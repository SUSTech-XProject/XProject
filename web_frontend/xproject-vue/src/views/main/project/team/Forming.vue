<template>
  <div>
    <div style="background-color: #f7fcff;margin-top: 0">
      <h2 style="padding-left: 40px;">Team Wanted !</h2>
      <el-row class="selecting" :gutter="20">
        <el-col :span="5" style="padding-left: 50px" >Topics:
          <selector :in-list="op_topic" :index.sync="Topic_ind"></selector>
        </el-col>
        <el-col :span="4">Status:
          <selector :in-list="op_sta" :index.sync="Status_ind"></selector>
        </el-col>
        <el-col :span="7">Tags:
          <multi-sel :in-list="op_tag" :index.sync="Tag_ind"></multi-sel>
        </el-col>
      </el-row>
    </div>

    <div class="board">
      <div v-for="team in teams" class="cardList">
        <teamcard v-bind="team"
                  v-if="isVisible(team)"
                  @click.native="openDrawer(team)"></teamcard>

      </div>
    </div>
    <drawer v-bind="detailTeam"
            @closeDrawer = "closeDrawer"></drawer>


  </div>
</template>

<script>
import single from '@/components/selector/single'
import searching from '@/components/searching/index'
import teamcard from '@/components/card/teamList/index'
import teamdrawer from '@/views/main/project/team/teamdrawer'
import double from '@/components/selector/double'


export default {
  name: "Forming",
  components:{
    searching,teamcard,drawer:teamdrawer,selector: single,multiSel:double
  },
  data(){
    return{
      op_topic: ['Topic 0','Topic 1','Topic 2','Topic 3','Topic 4'],
      op_sta:['1/5','2/5','3/5','4/5'],
      op_tag:['tag0','tag1','tag2','tag3','tag4','tag5'],
      Topic_ind: '',
      Status_ind:'',
      Tag_ind:[],
      teams:[
        {id:1,name:'Team 1',topic:'Topic 1', status:'1/5', tags:['tag11','tag12','tag13'],intro:'introduction1'},
        {id:2,name:'Team 2',topic:'Topic 2', status:'2/5', tags:['tag21','tag22','tag23'],intro:'introduction2'},
        {id:3,name:'Team 3',topic:'Topic 3', status:'3/5', tags:['tag31','tag32','tag33'],intro:'introduction3'},
        {id:4,name:'Team 4',topic:'Topic 4', status:'4/5', tags:['tag41','tag42','tag43'],intro:'introduction4'},
      ],
      detailTeam:{},
    }
  },
  methods:{
    openDrawer(val){
      val.drawer = true;
      this.detailTeam = val;
    },
    closeDrawer(){
      this.detailTeam= {}
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
        for (const i in this.Tag_ind) {
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
.board{
  display: inline-block;
  padding-left: 30px;
  padding-top: 20px;
}
.cardList{
  display: inline-block;
  margin-left: 2rem;
  margin-bottom: 2rem;
}
.selecting{
  height: 60px;
  text-align: left;
  display: flex;
  width: 100%;
  padding-top: 10px;
}
</style>
