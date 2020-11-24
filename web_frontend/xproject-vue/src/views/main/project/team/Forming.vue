<template>
  <div >
    <div>
      <h2>testing</h2>
<!--      <selector v-bind="op_topic"></selector>-->
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
//import selector from '@/components/selector/index'
import searching from '@/components/searching/index'
import teamcard from '@/components/card/teamList/index'
import teamdrawer from '@/views/main/project/team/teamdrawer'


export default {
  name: "Forming",
  components:{
    searching,teamcard,drawer:teamdrawer
  },
  data(){
    return{
      op_topic: [
        {value: '1', label: 'Topic1'},
        {value: '2', label: 'Topic2'},
        {value: '3', label: 'Topic3'},
        {value: '4', label: 'Topic4'},
        {value: '5', label: 'Topic5'}],
      op_num:[
        {value: '1', label: '1'},
        {value: '2', label: '2'},
        {value: '3', label: '3'},
        {value: '4', label: '4'},
        {value: '5', label: '5'}],
      op_tag:[
        {value: '1', label: 'tag1'},
        {value: '2', label: 'tag2'},
        {value: '3', label: 'tag3'},
        {value: '4', label: 'tag4'},
        {value: '5', label: 'tag5'}],
      value: '',
      value1:[],
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
</style>
