<template>
  <div>
<!--  <el-button @click="drawer = true" type="primary" style="margin-left: 16px;">-->
<!--    点我打开-->
<!--  </el-button>-->

  <el-drawer
    title="我是标题"
    direction="rtl"
    :with-header = "false"
    :visible.sync="annoDrawer"
    :before-close="handleClose">
    <h2>{{annoTitle}}</h2>
    <hr/>
    <h4>Author: {{annoAuthor}}</h4>
  </el-drawer>
  </div>
</template>

<script>

export default {
  name: "index",
  data() {
    return {
      annoDrawer: this.drawer,
      annoTitle:this.title,
      annoAuthor:this.author,
      annoTime:this.time,
      annoMessage:this.message,

    };
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.refresh()
          done();
        })
        .catch(_ => {});
    },
    refresh(){
      console.log("close")
      this.$emit('closeDrawer',"msg")
    }
  },
  props:{
    drawer:{type:Boolean,default:false},
    title:{type:String, default:"title"},
    author:{type:String,default: "admin"},
    time:{type:String,default:"2020-01-01 9:34"},
    message:{type:String,default:"message content"},
  }
};
</script>

<style>

</style>
