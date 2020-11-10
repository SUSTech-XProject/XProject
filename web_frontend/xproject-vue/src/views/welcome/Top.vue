<template>
  <div>
    <el-menu
      router
      mode="horizontal"
      background-color="white"
      text-color="#222"
      active-text-color="red"
      style="min-width: 1300px">

      <span style="position: absolute; left: 10%">
        <el-container>
          <el-image
            style="width: 30px; height: 30px; padding-top: 20px"
            :src="logo"
            :fit="fit"></el-image>
          <p style="font-size: 34px;font-weight: bold">XProject</p>
        </el-container>
      </span>

    </el-menu>
  </div>
</template>

<script>
import logo from '@/assets/logo.png'
export default {
  name: 'Top',
  data () {
    return {
      msg: 'XProject welcome page',
      activeIndex: '1',
      logo
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
