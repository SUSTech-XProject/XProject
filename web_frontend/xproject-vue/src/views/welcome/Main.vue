<template>
  <div id="main-root">
    <div style="font-size: 54px;font-weight: bold; padding: 0; text-align:center;">Welcome to XProject</div>
    <div style="margin-left: auto; margin-right: auto">
      <div style="font-size: 20px; padding: 0; text-align:left; margin-top: 20px">
        Makes your course project management easier <br/>
        whether you are a student or a teacher
      </div>
    </div>
    <div style="margin-left: auto; margin-right: auto; margin-top: 40px;">
      <el-button id="start-btm" type="primary" @click="gotoLogin">Start Right Now</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Main',
  props: {
    curStatus: String
  },
  data () {
    return {
    }
  },
  methods: {
    gotoLogin() {
      this.$router.push({name: 'Login'})
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

  .el-col, .el-row {
    font-size: 0;
  }

  #main-root {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
  }
  #start-btm {
    font-size: 20px;
  }
</style>
