<template>
  <div>
    <el-row style="margin-top: 20px">
      <el-col :span="4" :offset="2">
        <div align="left">
          <!--                <el-avatar :size="200"><div style="font-size: 80px; text-align:center; margin-top: -5px">n</div></el-avatar>-->
          <el-avatar src="http://wx1.sinaimg.cn/large/0072GgNply1g5takxmar5j309q09w3yl.jpg"
                     :size="150"></el-avatar>
        </div>
      </el-col>

      <el-col :span="16">
        <div style="font-size: 50px; padding:0; text-align:left; margin-top: 20px">Team</div>
        <div style="padding: 0; text-align:left; margin-top: 20px">
          <el-tag>标签一</el-tag>
          <el-tag type="success">标签二</el-tag>
          <el-tag type="info">标签三</el-tag>
          <el-tag type="warning">标签四</el-tag>
          <el-tag type="danger">标签五</el-tag>
        </div>
      </el-col>
    </el-row>

    <div style="margin-top: 40px; margin-left:40px">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="Introduction" name="intro">
          <div style="font-size: 15px; padding:0; text-align:left; margin-top: 15px;margin-left: 20px">
            Team for project {}
          </div>
          <div style="margin-top: 20px; margin-left: 20px">
            <el-input
              type="textarea"
              :rows="5"
              placeholder=""
              v-model="textarea">
            </el-input>
          </div>
        </el-tab-pane>

        <el-tab-pane label="Member" name="member">
          <el-row>
            <el-col :span="2" class="left">
              <el-avatar src="https://ww1.sinaimg.cn/thumb150/006z25tvly1genhm1qn9vj30tc0t9wi8.jpg"
                         :size="50"></el-avatar>
            </el-col>
            <el-col :span="12">
              <div style="font-size: 20px; padding:0; text-align:left; margin-top: 22px">name</div>
            </el-col>
          </el-row>

          <el-row style="margin-top: 10px" class="left">
            <el-col :span="2">
              <el-avatar src="https://ww4.sinaimg.cn/orj360/0064eUp2ly1ge61i3malnj3073077746.jpg"
                         :size="50"></el-avatar>
            </el-col>
            <el-col :span="12">
              <div style="font-size: 20px; padding:0; text-align:left; margin-top: 15px">name</div>
            </el-col>
          </el-row>
        </el-tab-pane>

        <el-tab-pane label="Message" name="message">
          message
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyTeam",
  components: {},
  data() {
    return {
      activeName: 'intro'
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
}
</script>

<style scoped>
.left {
  padding: 10px 0;
}
</style>
