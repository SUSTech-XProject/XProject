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

          <div style="height: 80px">
            <!--            <sele v-bind = "selArr"></sele>-->
            <el-button type="primary" @click="selectStar"
                       round>only star <i :class="icn" ></i></el-button>
          </div>

          <div v-for="list in listArr">
            <div v-if="list.star||!star" class="proj">
              <card @getStarChange = "getStarChange"
                    @click.native="openDrawer(list)"
                    v-bind="list"
              ></card>
            </div>
          </div>
          <drawer @closeDrawer = "closeDrawer"
            v-bind="detailAnn"></drawer>

        </el-main>

      </el-container>
    </el-container>

  </div>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Card   from '@/components/card/announceList/index'
import Selector from '@/components/selector/index'
import Drawer from '@/components/drawer/announcement/index'
export default{
  name:'Forming',
  components:{
    LeftBar,Header,card:Card,sele:Selector,drawer:Drawer
  },
  data(){
    return{
      listArr:[
        {id:1,title:"PROJECT",author:"COURSE",time:"2020-01-02",star:true,message:"message1"},
        {id:2,title:"PROJECT2",author:"COURSE2",time:"2020-01-02",star:false,message:"message2"},
        {id:3,title:"PROJECT3",author:"COURSE3",time:"2020-01-02",star:true,message:"message3"}
      ],
      star:false,
      icn:"el-icon-star-off",
      detailAnn:{},
      showDrawer:false
    }
  },
  methods:{
    getStarChange(val){
      //更改相应project star值
      for (let i = 0; i <this.listArr.length ; i++) {
        const va = this.listArr[i]
        if(va.id===val){
          let temp = va.star
          temp = !temp;
          va.star = temp;
        }
      }
    },
    selectStar(){
      //选择是否显示星标
      let temp = this.star
      this.star = !temp;
      this.icn = !temp?'el-icon-star-on':'el-icon-star-off'
    },
    openDrawer(val){
      //打开相应drawer
      console.log("testing"+val.id)

      val.drawer = true;

      this.detailAnn = val;
      console.log(this.detailAnn.id)

    },
    closeDrawer(){
      this.showDrawer = false
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

#temp{
  height: 100%;
}
.proj{
  height: 100px;
  text-align: center;
  padding-left: 10%;
}




</style>
