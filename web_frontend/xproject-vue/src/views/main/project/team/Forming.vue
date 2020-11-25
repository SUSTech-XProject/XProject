<template>
  <div >
    <div class="selecting">
      <div>Topics:  <selector :in-list="op_topic"
                              ></selector></div>
      <div>Status:  <selector :in-list="op_sta"></selector></div>
      <div>Tags:    <multi-sel :in-list="op_tag"></multi-sel></div>
      <div><el-button @click="selection">selecting</el-button> </div>
    </div>

    <div style="display: inline-block">
      <div v-for="team in teams" class="cardList">
        <teamcard v-bind="team"
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
      op_topic: [
        {value: '0', label: 'Topic1'},
        {value: '1', label: 'Topic2'},
        {value: '2', label: 'Topic3'},
        {value: '3', label: 'Topic4'},
        {value: '4', label: 'Topic5'}],
      op_sta:[
        {value: '0', label: '1/5'},
        {value: '1', label: '2/5'},
        {value: '2', label: '3/5'},
        {value: '3', label: '4/5'}],
      op_tag:[
        {value: '1', label: 'tag1'},
        {value: '2', label: 'tag2'},
        {value: '3', label: 'tag3'},
        {value: '4', label: 'tag4'},
        {value: '5', label: 'tag5'}],
      selTopic_ind: '',
      selStatus:'',
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
    selection(){
      console.log(this.selTopic_ind)
    }
  }
}
</script>

<style scoped>
.cardList{
  display: inline-block;
  margin-left: 2rem;
  margin-bottom: 2rem;
}
.selecting{
  height: 100px;
  text-align: center;
  display: flex;
  width: 100%
}
</style>
