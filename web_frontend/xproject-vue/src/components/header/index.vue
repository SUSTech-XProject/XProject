<template>
  <div id = "header">

<!--      <i v-bind:class="[{'el-icon-d-arrow-right':isFolded},'el-icon-d-arrow-left']"></i>-->
<!--      <div v-if="this.$route.path.indexOf('projectlist')!== -1">-->
<!--        <Breadcrumb></Breadcrumb>-->
<!--      </div>-->
<!--      <div v-else id="partname">{{part}}</div>-->
    <i style="margin: auto 20px auto 50px" class="el-icon-help"></i>
    <i style="margin: auto 50px auto 20px" class ="el-icon-user"></i>

  </div>
</template>

<script>
import Breadcrumb from '@/components/breadcrumb/index'

export default {
  name: 'index',

  data () {
    return {
      isFolded: false,
      part: this.$route.path.split('/')[1]
    }
  },
  components: {
    Breadcrumb
  }
}
</script>

<style>
#header {
  background-color: #edf4ff;
  color: #333;
  line-height: 60px;
  width:100%;
  height: 100%;
  font-size: 20px;
  text-align: left;
  display: flex;
  align-content: center;
  justify-content: flex-end;
}
.i {
  text-align: center;
}
#Breadcrumb {
  margin-top: 20px;
  position: center;
}
#partname{
  width: 20px;
  position: relative;
  height: 10%;
  bottom: 10%;
}

</style>
