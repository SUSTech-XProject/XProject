<template>
  <div style="height: 100%">
    <el-container style="height: 100%">
      <el-aside style="width: fit-content">
        <left-bar></left-bar>
      </el-aside>
      <el-container>
        <el-header>
          <Header></Header>
        </el-header>
        <el-main>
          <div>
            <span>Topic:
               <el-select v-model="value" placeholder="请选择">
                  <el-option
                    v-for="item in op_topic"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
               </el-select>
            </span>
            <span>Number:
               <el-select v-model="value" placeholder="请选择">
                  <el-option
                    v-for="item in op_num"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
               </el-select>
            </span>
            <span>Tags:
                <el-select v-model="value1" multiple placeholder="请选择">
                  <el-option
                    v-for="item in op_tag"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
            </span>
            <el-button type="primary" icon="el-icon-finished">Filter</el-button>
          </div>





        </el-main>

      </el-container>
    </el-container>

  </div>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Card   from '@/components/card/announceList/index'

export default {
  name: "Forming",
  components:{
    LeftBar,Header,card:Card
  },
  data(){
    return{
      op_topic:
        [{value: '1', label: 'Topic1'},
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
      value1:[]
    }
  }
}
</script>

<style scoped>

</style>
