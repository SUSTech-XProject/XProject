<!--YZX-->
<!-- Student management page for teacher -->

<template>
  <el-card id="base-card">
    <el-row style="display: flex; margin: 0 10px 10px 0">
      <div style="font-size: 30px; font-weight: bold; text-align:left">{{this.$store.state.proj.projName}}</div>
    </el-row>
  </el-card>
</template>

<script>
  import {getUserHomeInfo} from "@/api/home_page";

  export default {
    name: "StdManage",
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
  #base-card {
    margin: 15px 10px
  }
</style>
