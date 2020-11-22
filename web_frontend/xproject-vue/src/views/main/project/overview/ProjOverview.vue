<!--YZX-->
<!--Overview page for proj-->

<template>
  <div style="width:100%; height:100%">
    Hello, here is proj {{this.$route.params.proj_id}}
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
