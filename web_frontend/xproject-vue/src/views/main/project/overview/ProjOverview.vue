<!--YZX-->
<!--Overview page for proj-->

<template>
  <div style="margin: 20px 20px 20px 20px">
    <el-row style="display: flex; margin: 0 10px 10px 0">
      <div style="font-size: 30px; font-weight: bold; text-align:left">{{this.$store.state.proj.projName}}</div>
    </el-row>
  </div>
</template>

<script>
import {getUserHomeInfo} from "@/api/home_page";

export default {
  name: "ProjOverview",
  components: {

  },
  data () {
    return {

    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init proj overview page')
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
