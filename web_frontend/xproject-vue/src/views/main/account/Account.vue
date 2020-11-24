<template>
  <div style="margin-top: 20px; width:100%">
    <el-col :span="14" :offset="1">
      <el-tabs :tab-position="tabPosition" type="card" style="height: 100%;" v-model="activeName">
        <el-tab-pane label="Account Information" name="accountInfo">
          <div style="font-size: 30px; font-weight:bold; margin-left: 20px">
            Account Information
            <el-divider></el-divider>
          </div>
          <el-container style="margin-left: 20px;">
            <el-form ref="form" :model="form" label-width="110px">
              <el-form-item label="Student ID" class="form-item-height">
                11111111
              </el-form-item>
              <el-form-item label="First name" class="form-item-height">

              </el-form-item>
              <el-form-item label="Last name" class="form-item-height">
                {{ this.$store.state.role.username }}
              </el-form-item>
              <el-form-item label="Email" class="form-item-height">
                11111111@mail.sustech.edu.cn
              </el-form-item>
              <el-form-item label="Type" class="form-item-height">
                registered
              </el-form-item>
              <el-form-item label="disabled" class="form-item-height">
                False
              </el-form-item>
              <el-form-item label="Creator" class="form-item-height">
                Admin SUPPER
              </el-form-item>
              <el-form-item label="Creation time" class="form-item-height">
                2020/11/10
              </el-form-item>
            </el-form>
          </el-container>
        </el-tab-pane>

        <el-tab-pane label="Personal Information" name="personalInfo">
          <div style="font-size: 30px; font-weight:bold; margin-left: 20px">
            Personal Information
            <el-divider></el-divider>
          </div>

          <div class="personalInfoTitle" style="margin-left: 20px;">
            My Tags
          </div>
          <div class="personalInfoTypesetting">
            <el-tag :key="tag" v-for="tag in dynamicTags" closable :disable-transitions="false"
                    @close="handleClose(tag)">
              {{ tag }}
            </el-tag>
            <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" size="small"
                      @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm">
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          </div>

          <div class="personalInfoTypesetting personalInfoTitle">
            Email
          </div>
          <div class="personalInfoTypesetting" style="width:90%">
            <el-input v-model="email" placeholder=""></el-input>
          </div>

          <div class="personalInfoTypesetting personalInfoTitle">
            College
          </div>
          <div class="personalInfoTypesetting" style="width:90%">
            <el-input v-model="college" placeholder=""></el-input>
          </div>

          <div class="personalInfoTypesetting personalInfoTitle">
            Location
          </div>
          <div class="personalInfoTypesetting" style="width:90%">
            <el-input v-model="location" placeholder=""></el-input>
          </div>

          <div class="personalInfoTypesetting personalInfoTitle">
            Bio
          </div>
          <el-input type="textarea" :rows="4" placeholder="" v-model="description"
                    style="margin-left: 20px; margin-top: 20px; width: 90%">
          </el-input>

          <div class="personalInfoTypesetting" style="margin-bottom: 40px">
            <el-button type="primary" @click="handleUpdate">update</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-col>

    <el-col :span="5" :offset="1">
      <div style="margin-top: 40px; margin-left: 5px; font-weight:bold;">Profile picture</div>
      <div style="margin-top: 5px" align="left">
        <el-upload
          class="avatar-uploader"
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
    </el-col>
  </div>
</template>

<script>
import {getUserHomeInfo} from "@/api/home_page";

export default {
  name: 'Account',
  mounted() {
    this.getBio()
  },
  data() {
    return {
      //el-form
      form: {
        studentID: ''
      },

      //tab
      tabPosition: 'left',
      activeName: 'personalInfo',

      //avatar uploader
      imageUrl: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',

      //dynamic tag
      dynamicTags: ['hardworking', 'efficient', 'earnest'],
      inputVisible: false,
      inputValue: '',

      //textarea
      description: '',

      email: '',
      college: '',
      location: ''
    }
  },
  methods: {
    //avatar uploader
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
    },

    //dynamic tag
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },
    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    },

    //bio text area
    getBio() {
      getUserHomeInfo().then(resp => {
        this.description = resp.data.data;
      }).catch(failResp => {
        console.log("fail to get response in getUserHomeInfo")
      })
    },

    //update personal information
    handleUpdate() {

    }
  }
}
</script>

<style scoped>
.personalInfoTypesetting {
  margin-left: 20px;
  margin-top: 20px;
}

.personalInfoTitle {
  font-size: 22px;
  font-weight: bold;
}

/*el-form*/
.form-item-height >>> .el-form-item__label {
  height: 20px;
  font-weight: bold;
  text-align: left;
}

.form-item-height >>> .el-form-item__content {
  height: 20px;
}

/*avatar uploader*/
.avatar-uploader >>> .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 100px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader >>> .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 200px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 200px;
  height: 200px;
  display: block;
}

/*dynamic tags*/
.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>

<!--  <el-container style="height: 500px; border: 1px solid #eee">-->
<!--    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">-->
<!--      <el-menu :default-openeds="['1', '3']">-->
<!--        <el-submenu index="1">-->
<!--          <template slot="title"><i class="el-icon-message"></i>导航一</template>-->
<!--          <el-menu-item-group>-->
<!--            <template slot="title">分组一</template>-->
<!--            <el-menu-item index="1-1">选项1</el-menu-item>-->
<!--            <el-menu-item index="1-2">选项2</el-menu-item>-->
<!--          </el-menu-item-group>-->
<!--          <el-menu-item-group title="分组2">-->
<!--            <el-menu-item index="1-3">选项3</el-menu-item>-->
<!--          </el-menu-item-group>-->
<!--          <el-submenu index="1-4">-->
<!--            <template slot="title">选项4</template>-->
<!--            <el-menu-item index="1-4-1">选项4-1</el-menu-item>-->
<!--          </el-submenu>-->
<!--        </el-submenu>-->
<!--        <el-submenu index="2">-->
<!--          <template slot="title"><i class="el-icon-menu"></i>导航二</template>-->
<!--          <el-menu-item-group>-->
<!--            <template slot="title">分组一</template>-->
<!--            <el-menu-item index="2-1">选项1</el-menu-item>-->
<!--            <el-menu-item index="2-2">选项2</el-menu-item>-->
<!--          </el-menu-item-group>-->
<!--          <el-menu-item-group title="分组2">-->
<!--            <el-menu-item index="2-3">选项3</el-menu-item>-->
<!--          </el-menu-item-group>-->
<!--          <el-submenu index="2-4">-->
<!--            <template slot="title">选项4</template>-->
<!--            <el-menu-item index="2-4-1">选项4-1</el-menu-item>-->
<!--          </el-submenu>-->
<!--        </el-submenu>-->
<!--        <el-submenu index="3">-->
<!--          <template slot="title"><i class="el-icon-setting"></i>导航三</template>-->
<!--          <el-menu-item-group>-->
<!--            <template slot="title">分组一</template>-->
<!--            <el-menu-item index="3-1">选项1</el-menu-item>-->
<!--            <el-menu-item index="3-2">选项2</el-menu-item>-->
<!--          </el-menu-item-group>-->
<!--          <el-menu-item-group title="分组2">-->
<!--            <el-menu-item index="3-3">选项3</el-menu-item>-->
<!--          </el-menu-item-group>-->
<!--          <el-submenu index="3-4">-->
<!--            <template slot="title">选项4</template>-->
<!--            <el-menu-item index="3-4-1">选项4-1</el-menu-item>-->
<!--          </el-submenu>-->
<!--        </el-submenu>-->
<!--      </el-menu>-->
<!--    </el-aside>-->

<!--    <el-container>-->
<!--      <el-header style="text-align: right; font-size: 12px">-->
<!--        <el-dropdown>-->
<!--          <i class="el-icon-setting" style="margin-right: 15px"></i>-->
<!--          <el-dropdown-menu slot="dropdown">-->
<!--            <el-dropdown-item>查看</el-dropdown-item>-->
<!--            <el-dropdown-item>新增</el-dropdown-item>-->
<!--            <el-dropdown-item>删除</el-dropdown-item>-->
<!--          </el-dropdown-menu>-->
<!--        </el-dropdown>-->
<!--        <span>王小虎</span>-->
<!--      </el-header>-->

<!--      <el-main>-->
<!--        <el-table :data="tableData">-->
<!--          <el-table-column prop="date" label="日期" width="140">-->
<!--          </el-table-column>-->
<!--          <el-table-column prop="name" label="姓名" width="120">-->
<!--          </el-table-column>-->
<!--          <el-table-column prop="address" label="地址">-->
<!--          </el-table-column>-->
<!--        </el-table>-->
<!--      </el-main>-->
<!--    </el-container>-->
<!--  </el-container>-->
