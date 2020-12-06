<template>
  <div id = "Testpage">
    <el-container>
      <el-aside style="width: 201px;height: 100%">
        <left-bar></left-bar>
      </el-aside>
        <el-container>
          <el-header>
            <Header>
            </Header>
          </el-header>
        <el-main>

          <el-upload
            class="avatar-uploader"
            action="http://localhost:8443/api/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>

<!--          <el-upload-->
<!--            ref="download"-->
<!--            action="http://localhost:8443/api/download">-->
<!--            <el-button style="margin-left: 10px;" size="small" type="success" @click="download" id="我就烂.jpg">上传到服务器</el-button>-->
<!--          </el-upload>-->

          <el-upload
            class="upload-demo"
            ref="upload"
            action="http://localhost:8443/api/upload"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
<!--          http://localhost:8080/api/download?path=&filename=-->

<!--            <el-button style="margin-left: 10px;" size="small" type="primary" @click="download">-->
<!--                downloadtest-->
<!--            </el-button>-->

          <el-row>
            <el-link href="http://localhost:8443/api/download?path=A.jpg&filename=A.jpg" target="_blank">downloadtest</el-link>
          </el-row>

          <DownDrop></DownDrop>
          <Table></Table>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Table from '@/components/table/index'
import DownDrop from '@/components/downdrop/index'
import Breadcrumb from '@/components/breadcrumb/index'
import {download} from "@/api/submit"

export default{
  name: 'Testpage',
  data () {
    return {
      fileList: [],
      test:"http://localhost:8443/api/download?path=A.jpg&filename=A.jpg",
      imageUrl: ''
    }
  },
  components: {
    LeftBar, Header, Table, DownDrop, Breadcrumb
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },
    download(){
      let defpath = "A.jpg"
      let file = "A.jpg"

      download(defpath, file).then(resp => {
        // if (resp.data.code !== 200) {
        //   this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
        //     confirmButtonText: 'OK'
        //   })
        //   return false
        // }
      }).catch(failResp => {
        console.log('fail in download. message=' + failResp.message)
      })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
  }
  // methods: {
  //   getHeight () {
  //     this.defaultHeight.height = window.innerHeight - 60 + 'px'
  //   }
  // },
  // getHeight () {
  //   this.defaultHeight.height = window.innerHeight - 60 + 'px'
  // }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
#Testpage{
  height: 100%;
}
.header{
  width : 100%;
  float:right;
  height: 60px;
}
.el-aside{
  position: relative;
  padding: 0px;
  margin: 0px;
  height: 100%;
  /*background: #42b983;*/
}
.el-header {
  background-color: #edf4ff;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.dropdown{
  width: 100px;
}
/*.el-aside {*/
/*  background-color: #D3DCE6;*/
/*  color: #333;*/
/*  text-align: center;*/
/*  !*line-height: 200px;*!*/
/*}*/
.el-container{
  padding: 0px;
  margin: 0px;
  height: 100%;
}
.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  /*line-height: 160px;*/
}
.table{
  /*position: absolute;left: 11.5% ;*/
  /*height: 200px;*/
  /*width: 200px;*/
}
.dropdown{
  /*position: absolute;left: 10%;*/
}
/*body > .el-container {*/
/*  margin-bottom: 40px;*/
/*}*/

/*.el-container:nth-child(5) .el-aside,*/
/*.el-container:nth-child(6) .el-aside {*/
/*  line-height: 260px;*/
/*}*/

/*.el-container:nth-child(7) .el-aside {*/
/*  line-height: 320px;*/
/*}*/
</style>
