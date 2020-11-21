<template>
  <div>
    <div style="height: 100%">
      <!--            <sele v-bind = "selArr"></sele>-->
      <el-button type="primary" @click="selectStar"
                 round>only star <i :class="icn" ></i></el-button>
    </div>

    <div v-for="list in listArr">
        <div v-if="list.star||!star" class="proj">
          <card v-bind="list"
                @getStarChange = "getStarChange"></card>
        </div>

    </div>
  </div>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Card   from '@/components/card/projectList/index'
import Selector from '@/components/selector/index'
import {getProjList} from "@/api/home_page";
export default{
  name:'ProjectList',
  components:{
    card:Card,
    sele:Selector
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
      ],
      star:false,
      icn:"el-icon-star-off",
    }
  },
  mounted () {
    this.initProjList()
  },
  methods:{
    getStarChange (val) {
      console.log(val)
      //更改相应project star值
      for (let i = 0; i <this.listArr.length ; i++) {
        var va = this.listArr[i];
        if(va.id===val){
          console.log(this.listArr[i].star)
          var temp = va.star;
          temp = !temp;
          va.star = temp;
          console.log(this.listArr[i].star)
        }
      }
      console.log('testing')
    },
    selectStar (){
      let temp = this.star
      this.star = !temp;
      this.icn = !temp?'el-icon-star-on':'el-icon-star-off'
      console.log(this.star)
    },
    initProjList () {
      getProjList().then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        let projList = resp.data.data
        this.listArr = []
        for (let i = 0; i < projList.length; i++) {
          let proj = projList[i]
          this.listArr.push({
            id: i,
            name: proj.projName,
            course: 'UNKNOWN',
            star: false
          })
        }
        console.log(this.listArr)
      }).catch(failResp => {
        console.log('fail in getProjList. message=' + failResp.message)
      })
    }
  }
}
</script>

<style scoped>
html,body{
  /*设置内部填充为0，几个布局元素之间没有间距*/
  padding: 0;
  /*外部间距也是如此设置*/
  margin: 0;
  /*统一设置高度为100%*/
  height: 100%;
}

#temp{
  height: 100%;
}
.proj{
  height: 100px;
  text-align: center;
  padding-left: 10%;
}




</style>
