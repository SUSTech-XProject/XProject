<template>
  <el-card class="base-card">
    <el-col :span="14" :offset="2">
      <el-tabs :tab-position="tabPosition" type="card"
               style="height: 100%; margin-top: 10px" v-model="activeName">
        <el-tab-pane label="Account Information" name="accountInfo">
          <div style="font-size: 30px; font-weight:bold; margin-left: 20px">
            Account Information
            <el-divider></el-divider>
          </div>
          <el-container style="margin-left: 20px;">
            <el-form ref="form" label-width="140px">
              <el-form-item v-for="info in formInfoList" :key="info.label"
                            v-bind:label="info.label" class="form-item-height">
                {{ info.value }}
              </el-form-item>
            </el-form>
          </el-container>
        </el-tab-pane>

        <el-tab-pane label="Personal Information" name="personalInfo">
          <div style="font-size: 30px; font-weight:bold; margin-left: 20px">
            Personal Information
            <el-divider></el-divider>
          </div>

          <div v-if="this.roleType==='Student'">
            <!--            TODO: Indentation issues of add tag-->
            <!--            TODO: Multi-line tag line spacing problem-->
            <div class="personalInfoTitle" style="margin-left: 20px; margin-bottom: 20px">
              Impression Tags
            </div>
            <div class="personalInfoTypesetting">
              <el-tag :key="tag" v-for="tag in impressionTagList" closable
                      :disable-transitions="false" effect="plain"
                      @close="handleImpTagClose(tag)" class="el-tag">
                {{ tag }}
              </el-tag>
              <el-input class="input-new-tag" v-if="impTagInputVisible" v-model="impTagInputValue" ref="saveTagInput"
                        size="small"
                        @keyup.enter.native="handleImpTagInputConfirm" @blur="handleImpTagInputConfirm">
              </el-input>
              <el-button v-else class="input-new-tag" size="small" @click="showImpTagInput">+ New Tag</el-button>
            </div>

            <div class="personalInfoTitle personalInfoTypesetting" style="margin-left: 20px; margin-bottom: 20px;">
              Skill Tags
            </div>
            <div class="personalInfoTypesetting">
              <el-tag :key="tag" v-for="tag in skillTagList" closable
                      :disable-transitions="false" effect="plain"
                      @close="handleSkillTagClose(tag)" class="el-tag">
                {{ tag }}
              </el-tag>
              <el-input class="input-new-tag" v-if="skillTagInputVisible" v-model="skillTagInputValue"
                        ref="saveTagInput"
                        size="small"
                        @keyup.enter.native="handleSkillTagInputConfirm" @blur="handleSkillTagInputConfirm">
              </el-input>
              <el-button v-else class="input-new-tag" size="small" @click="showSkillTagInput">+ New Tag</el-button>
            </div>
          </div>

          <div class="personalInfoTypesetting personalInfoTitle">
            Bio
          </div>
          <el-input type="textarea" :rows="4" placeholder="" v-model="bio"
                    style="margin-left: 20px; margin-top: 20px; width: 90%">
          </el-input>

          <div class="personalInfoTypesetting" style="margin-bottom: 40px; margin-top: 30px">
            <el-button type="primary" @click="handleUpdate">update</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-col>

    <el-col :span="5" :offset="1">
      <div style="margin-top: 60px; margin-left: 5px; font-weight:bold;">Profile picture</div>
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
  </el-card>
</template>

<script>
import {getUserHomeInfo} from '@/api/home_page'
import {getAccountInfo, postSelfIntroduction} from '@/api/account'
import {isStudent, isTeacher} from '@/utils/role'

export default {
  name: 'Account',
  mounted () {
    this.init()
  },
  data () {
    return {
      //tab
      tabPosition: 'left',
      activeName: 'accountInfo',

      roleType: '',

      //el-form
      formInfoList: [
        {label: 'ID', value: ''},
        {label: 'First name', value: ''},
        {label: 'Last name', value: ''},
        {label: 'Email', value: ''},
        {label: 'Type', value: ''},
        {label: 'Status', value: ''},
        {label: 'Register Time', value: ''}
      ],

      //avatar uploader
      imageUrl: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',

      //impression tag list
      impressionTagList: ['a', 'b'],
      impTagInputVisible: false,
      impTagInputValue: '',

      //skill tag list
      skillTagList: [],
      skillTagInputVisible: false,
      skillTagInputValue: '',

      //textarea
      bio: '',
    }
  },
  methods: {
    init () {
      console.log('init account page')

      this.roleType = this.$store.state.role.roleType

      getAccountInfo().then(resp => {
        if (resp.data.code === 200) {
          let roleDict = resp.data.data.role

          this.formInfoList[4].value = roleDict.roleType
          this.formInfoList[5].value = roleDict.status
          this.formInfoList[6].value = roleDict.registerTime.substring(0, roleDict.registerTime.indexOf('T'))

          if (isStudent()) {
            let infoDict = resp.data.data.student

            this.bio = infoDict.bio

            this.formInfoList[0].value = infoDict.stdNo
            let name = infoDict.stdName.split(' ')
            this.formInfoList[1].value = name[0]
            this.formInfoList[2].value = name[1]
            this.formInfoList[3].value = infoDict.email

            if (infoDict.flags != null) {
              this.impressionTagList = JSON.parse(infoDict.flags)
            }
            if (infoDict.skills != null) {
              this.skillTagList = JSON.parse(infoDict.skills)
            }
          } else if (isTeacher()) {
            let infoDict = resp.data.data.teacher

            this.bio = infoDict.bio

            this.formInfoList[0].value = infoDict.tchNo
            let name = infoDict.tchName.split(' ')
            this.formInfoList[1].value = name[0]
            this.formInfoList[2].value = name[1]
            this.formInfoList[3].value = infoDict.email
          }

        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResp => {
        this.$alert('Error: ' + failResp.message, 'Tips', {
          confirmButtonText: 'OK'
        })
      })
    },

    //avatar uploader
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.$message.error('Can only upload .jpg')
      }
      if (!isLt2M) {
        this.$message.error('Size of picture can not exceed 2MB')
      }
      return isJPG && isLt2M
    },

    //impression tag list
    handleImpTagClose (tag) {
      this.impressionTagList.splice(this.impressionTagList.indexOf(tag), 1)
    },
    showImpTagInput () {
      this.impTagInputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleImpTagInputConfirm () {
      let inputValue = this.impTagInputValue
      if (inputValue) {
        this.impressionTagList.push(inputValue)
      }
      this.impTagInputVisible = false
      this.impTagInputValue = ''
    },

    //skill tag list
    handleSkillTagClose (tag) {
      this.skillTagList.splice(this.skillTagList.indexOf(tag), 1)
    },
    showSkillTagInput () {
      this.skillTagInputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleSkillTagInputConfirm () {
      let inputValue = this.skillTagInputValue
      if (inputValue) {
        this.skillTagList.push(inputValue)
      }
      this.skillTagInputVisible = false
      this.skillTagInputValue = ''
    },

    //update personal information
    handleUpdate () {
      let impTagList = null, skillTagList = null, bio = null
      if (isTeacher()) {
        bio = this.bio
      } else if (isStudent()) {
        impTagList = this.impressionTagList
        skillTagList = this.skillTagList
        bio = this.bio
      }

      //TODO: Update avatar
      postSelfIntroduction(
        impTagList, skillTagList, bio
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.$alert('Changed successfully', 'Tip', {
            confirmButtonText: 'OK'
          })
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResp => {
        this.$alert('Error ' + failResp.message, 'Tip', {
          confirmButtonText: 'OK'
        })
      })
    }
  }
}
</script>

<style scoped>
.personalInfoTypesetting {
  margin-left: 20px;
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
  border-radius: 290px;
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
  width: 290px;
  height: 290px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 290px;
  height: 290px;
  display: block;
}

/*dynamic tags*/
.el-tag {
  margin-right: 10px;
  /*margin-top: 20px;*/
  margin-bottom: 20px;
}

.input-new-tag {
  width: 90px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
  vertical-align: bottom;
  margin-bottom: 20px;
}

.base-card {
  margin: 15px 10px;
  min-height: 95.7%;
}
</style>


<!--          <div class="personalInfoTypesetting personalInfoTitle">-->
<!--            Email-->
<!--          </div>-->
<!--          <div class="personalInfoTypesetting" style="width:90%">-->
<!--            <el-input v-model="email" placeholder=""></el-input>-->
<!--          </div>-->

<!--          <div class="personalInfoTypesetting personalInfoTitle">-->
<!--            College-->
<!--          </div>-->
<!--          <div class="personalInfoTypesetting" style="width:90%">-->
<!--            <el-input v-model="college" placeholder=""></el-input>-->
<!--          </div>-->

<!--          <div class="personalInfoTypesetting personalInfoTitle">-->
<!--            Location-->
<!--          </div>-->
<!--          <div class="personalInfoTypesetting" style="width:90%">-->
<!--            <el-input v-model="location" placeholder=""></el-input>-->
<!--          </div>-->
