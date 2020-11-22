<template>
  <div style="width:100%; height:100%">
    <el-row style="margin-top: 20px">
      <el-col :span="6" :offset="1">
        <div>
<!--                <el-avatar :size="200"><div style="font-size: 80px; text-align:center; margin-top: -5px">n</div></el-avatar>-->
          <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" :size="200"></el-avatar>
        </div>
      </el-col>

      <el-col :span="16" :offset="1">
        <div style="font-size: 80px; padding: 0; text-align:left; margin-top: 20px">{{this.$store.state.role.username}}</div>
        <div style="padding: 0; text-align:left; margin-top: 20px">
          <el-tag>标签一</el-tag>
          <el-tag type="success">标签二</el-tag>
          <el-tag type="info">标签三</el-tag>
          <el-tag type="warning">标签四</el-tag>
          <el-tag type="danger">标签五</el-tag>
        </div>
      </el-col>
    </el-row>

    <el-row style="margin-top: 40px">
      <el-col :span="19" :offset="1">
        <el-input
          type="textarea"
          :rows="5"
          placeholder=""
          v-model="description">
        </el-input>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getUserHomeInfo} from "@/api/home_page";

export default {
  name: "HomePage",
  components: {

  },
  data () {
    return {
      description: 'XXXX'
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init home page')
      getUserHomeInfo().then(resp => {
        this.description = resp.data.data;
      }).catch(failResp => {
        console.log("fail to get response in getUserHomeInfo")
      })
    }
  }
}
</script>

<style scoped>

</style>
