<template>
  <div style="height: 100%">
    <el-container id = "temp">
      <el-aside style="width: fit-content">
        <left-bar></left-bar>
      </el-aside>
      <el-container>
        <el-header>
          <Header></Header>
        </el-header>

        <el-main>
          <div>
            <sele v-bind = "selArr"></sele>
            <sea></sea>
          </div>
          <div v-for="list in listArr">
            <card v-bind="list" @getStarChange = "getStarChange"></card>
          </div>
        </el-main>

      </el-container>
    </el-container>

  </div>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Card   from '@/components/card/projectList/index'
import Searching from '@/components/searching/index'
import Selector from '@/components/selector/index'
export default{
  name:'Forming',
  components:{
    LeftBar,Header,card:Card,sea:Searching,sele:Selector
  },
  data(){
    return{
      listArr:[
        {id:1,name:"PROJECT",course:"COURSE",star:true},
        {id:2,name:"PROJECT2",course:"COURSE2",star:false},
        {id:3,name:"PROJECT3",course:"COURSE3",star:true}
      ],
      selArr:[
        {value: '选项1', label: '黄金糕'},
        {value: '选项2',label: '双皮奶'},
        {value: '选项3',label: '蚵仔煎'},
        {value: '选项4',label: '龙须面'},
        {value: '选项5',label: '北京烤鸭'}
      ]
    }
  },
  methods:{
    getStarChange(val){
      console.log(val)
      console.log('testing')
    }
  }
}
</script>

<style >
html,body{
  /*设置内部填充为0，几个布局元素之间没有间距*/
  padding: 0;
  /*外部间距也是如此设置*/
  margin: 0;
  /*统一设置高度为100%*/
  height: 100%;
}

.cardList{
  height: 200px;
}

#temp{
  height: 100%;
}




</style>
