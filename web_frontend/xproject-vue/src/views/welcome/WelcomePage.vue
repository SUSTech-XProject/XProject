<template>
  <el-container>
    <p>XProject</p>
  </el-container>
</template>

<script>
import Top from '@/views/welcome/Top'
export default {
  name: 'WelcomePage',
  components: {
    Top
  },
  data () {
    return {
      msg: 'XProject welcome page'
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
