<template>
  <el-card class="base-card">
    <el-col :span="4" :offset="3">
      <div v-if="isStudent() && haveTeam"
           style="margin-top: 10px">
        <el-avatar :src="team_avatar"
                   :size="205" :fit="'fill'"></el-avatar>

        <div style="font-size: 25px; font-weight:bold; margin-top: 20px">
          {{ teamName }}
        </div>

        <el-tag v-for="tag in tagList" :key="tag"
                class="el-tag" effect="plain">
          {{ tag }}
        </el-tag>

        <div style="width: 100%; margin-top: 20px" align="left" class="handle-overflow">
          {{ description }}
        </div>

        <div v-for="member in teamMemberList" :key="member.stdId"
             @click.native:="openDrawer(member.roleId)"
             style="margin-top: 15px;">
          <el-avatar :fit="'fill'" :src="member.iconUrl"
                     style="cursor: pointer; vertical-align: middle;"
          ></el-avatar>
          <span style="vertical-align: middle;">{{ member.stdName }}</span>
        </div>

        <div v-if="status==='Raw'" style="margin-top: 15px; align-items: center">
          <el-button @click="handleQuit()">Quit</el-button>
          <el-button @click="handleTeamConfirm()"
                     type="primary">Confirm
          </el-button>
        </div>
      </div>

      <div v-if="isStudent() && !haveTeam"
           style="margin-top: 10px;font-size: 30px;">
        <el-avatar src="https://github.com/Carl-Rabbit/my_sync_repo/blob/master/fig_bed/default_team.png?raw=true"
                   :size="205" :fit="'fill'"></el-avatar>

        <div style="font-size: 25px; font-weight:bold; margin-top: 20px">
          No team
        </div>

        <el-button @click="createMyTeam()"
                   type="primary"
                   style="margin-top: 15px; align-items: center">Create my team
        </el-button>
      </div>
    </el-col>

    <el-col :span="13" :offset="1">
      <el-tabs :tab-position="tabPosition"
               type="card" v-model="activeName"
               style="margin-top: 20px;">
        <el-tab-pane label="Notice list" name="notice">
          <div v-for="(notice, index) in noticeList" :key="index">
            <el-card class="box-card" shadow="never"
                     :class="notice.type"
                     style="width: 90%; margin-top: 20px;">
              <el-col :span="15">
                <div>
                  <span @click.native:="openDrawer(notice.creatorRoleId)">
                    <el-avatar :src="notice.crtIconUrl"
                               :fit="'fill'"
                               style="vertical-align:middle; cursor: pointer"
                    ></el-avatar>
                  </span>
                  <span style="vertical-align:middle;">{{ notice.crtUsername }}</span>

                  <span style="margin-left: 5px; font-size: 16px;vertical-align:middle;">
                    {{ notice.title }}
                  </span>
                </div>

                <div v-if="notice.type==='JoinSuccess'|| notice.type==='JoinFail'|| notice.type==='Invite'"
                     style="margin-top: 10px; margin-left: 10px;">
                  <span v-if="notice.type==='Invite'"
                        style="vertical-align:middle; font-size: 13px">
                    Inviter:
                  </span>
                  <span v-else style="vertical-align:middle; font-size: 13px">
                    Handler:
                  </span>

                  <span @click="openDrawer(notice.handlerRoleId)">
                    <el-avatar :src="notice.hdlIconUrl"
                               :fit="'fill'"
                               style="vertical-align:middle; margin-left: 8px; cursor: pointer"></el-avatar>
                  </span>
                  <span style="vertical-align:middle;">{{ notice.hdlUsername }}</span>
                </div>

                <div v-if="notice.type==='Apply'"
                     style="margin-top: 20px; margin-left: 10px; font-size: 13px">
                  ApplyInfo: {{ notice.content }}
                </div>

                <div style="margin-top: 20px; margin-left: 10px; font-size: 13px">
                  Date: {{ notice.createdTime }}
                </div>

                <div style="margin-bottom: 20px"></div>
              </el-col>

              <el-col :span="9">
                <div v-if="notice.type==='Apply'">

                  <div v-if="!notice.decided" style="margin-top: 35px">
                    <el-popover
                      placement="bottom"
                      width="340">
                      <div>
                        Reason:
                        <el-input v-model="rejectReason" placeholder="Reason here..."
                                  style="width: 200px;"></el-input>
                        <el-button @click="handleReject(notice)">Reply</el-button>
                      </div>
                      <el-button slot="reference">Reject</el-button>
                    </el-popover>

                    <el-button type="primary" @click="handleAccept(notice)">Accept</el-button>
                  </div>

                  <div v-else
                       style="margin-top: 40px; margin-left: 49px">
                    <div>{{ notice.result }}</div>
                  </div>
                </div>

                <div v-else-if="notice.type==='Quit'">
                  <el-button @click="handleMessageConfirm(notice)"
                             v-if="!notice.confirmed"
                             style="margin-top: 20px;  margin-left: 30px">
                    I know that
                  </el-button>

                  <div v-else
                       style="margin-top: 30px; margin-left: 20px">
                    Already confirmed
                  </div>
                </div>

                <div v-else>
                  <el-button @click="handleMessageConfirm(notice)"
                             v-if="!notice.confirmed"
                             style="margin-top: 40px; margin-left: 30px">
                    I know that
                  </el-button>

                  <div v-else
                       style="margin-top: 50px; margin-left: 20px">
                    Already confirmed
                  </div>
                </div>
              </el-col>
            </el-card>
          </div>
        </el-tab-pane>

        <el-tab-pane label="Invite teammates" name="invite"
                     v-if="haveTeam">
          <el-card class="box-card" style="min-height: 155px">
            <div slot="header" class="clearfix">
              <span>Available classmates</span>
            </div>

            <el-row>
              <el-col :span="6" v-for="(std, index) in ungroupList" :key="index">
                <div @click="handleInvite(index)" style="margin-top: 15px;">
                  <el-avatar :fit="'fill'"
                             :src="std.iconUrl"
                             style="margin-right: 10px; cursor: pointer; vertical-align: middle;"
                  ></el-avatar>
                  <span style="vertical-align: middle">{{ std.stdName }}</span>
                </div>
              </el-col>
            </el-row>

            <div style="margin-top: 10px"></div>
          </el-card>

          <el-card class="box-card" style="min-height: 155px; margin-top: 20px;">
            <div slot="header" class="clearfix">
              <span>Invite list</span>
            </div>
            <el-row>
              <el-col :span="8" v-for="(std, index) in inviteList" :key="index">
                <div @click="handleUnInvite(index)" style="margin-top: 15px;">
                  <el-avatar :fit="'fill'"
                             :src="std.iconUrl"
                             style="margin-right: 10px; cursor: pointer; vertical-align:middle;"
                  ></el-avatar>
                  <span style="vertical-align: middle">{{ std.stdName }}</span>
                </div>
              </el-col>
            </el-row>

            <div style="margin-top: 10px"></div>
          </el-card>

          <div align="center" style="margin-top: 20px;">
            <el-button type="primary" @click="handleUpdateInvite()">Invite</el-button>
            <el-button @click="handleCancelInvite()">Cancel</el-button>
          </div>
        </el-tab-pane>

        <el-tab-pane label="Settings" name="settings" v-if="haveTeam">
          <div class="personalInfoTitle">
            Team information
          </div>
          <el-form label-width="auto" style="margin-top: 20px;">
            <el-form-item label="Team name">
              <el-input v-model="newTeamName" style="width: 70%">
              </el-input>
            </el-form-item>

            <el-form-item label="Number of target members">
              <el-input v-model="newTargetMemNum" style="width: 70%">
              </el-input>
            </el-form-item>

            <el-form-item label="Topic">
              <el-select v-model="newTopic">
                <el-option
                  v-for="topic in topicList"
                  :key="topic.value"
                  :label="topic.label"
                  :value="topic.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="Description">
              <el-input type="textarea" :rows="4" v-model="newDescription"
                        style="width: 90%">
              </el-input>
            </el-form-item>
          </el-form>

          <div class="personalInfoTitle" style="margin-top: 20px; margin-bottom: 20px;">
            Team Tags
          </div>
          <div>
            <el-tag :key="tag" v-for="tag in newTagList" closable
                    :disable-transitions="false" effect="plain"
                    @close="handleTagClose(tag)" class="el-tag-dy">
              {{ tag }}
            </el-tag>
            <el-input class="input-new-tag" v-if="tagInputVisible" v-model="tagInputValue" ref="saveTagInput"
                      size="small"
                      @keyup.enter.native="handleTagInputConfirm" @blur="handleTagInputConfirm">
            </el-input>
            <el-button v-else class="input-new-tag" size="small" @click="showTagInput">+ New Tag</el-button>
          </div>

          <div style="margin-top: 10px;">
            <el-button type="primary" @click="handleEdit()">Update</el-button>
            <el-button @click="handleCancel()">Cancel</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
      <!--      <div style="font-size: 30px; font-weight:bold; margin-top: 10px">-->
      <!--        Notices-->
      <!--        <el-divider></el-divider>-->
      <!--      </div>-->
      <div style="margin-bottom: 40px"></div>
    </el-col>

    <drawer :drawer.sync="memDrawer"
            :id="memId"
            @closeDrawerStu="closeDrawerStu"></drawer>

    <CreateTeam :visible.sync="creatingTeam"
                @closeForm='closeAddDialog'></CreateTeam>

  </el-card>
</template>

<script>
import {isStudent} from '@/utils/role'
import {
  getMyTeamDetail, getPersonalMessage, getProjectTopics,
  getTeamMessage,
  getUngroupedStudents, postInviteStudents,
  postQuitTeam,
  postReplyApplication,
  postEditedTeamInfo, postTeamConfirmation
} from '@/api/team'
import stuInfoDrawer from '@/views/main/project/team/stuInfoDrawer'
import {getDatetimeStr} from '@/utils/parse-day-time'
import CreateTeam from '@/views/main/project/team/CreateTeam'

export default {
  name: 'MyTeam',
  components: {
    drawer: stuInfoDrawer,
    CreateTeam,
  },
  data () {
    return {
      //
      memDrawer: false,
      memId: 1,
      //

      tabPosition: 'top',
      activeName: 'notice',

      //team info
      haveTeam: false,
      teamName: '',
      tagList: [],
      status: '',
      team_avatar: '',
      description: '',
      targetMemNum: '',
      topic: '',
      topicList: [],
      teamMemberList: [],
      noticeList: [],

      //edit team info
      newTeamName: '',
      newTagList: [],
      newDescription: '',
      newTargetMemNum: '',
      newTopic: '',
      tagInputVisible: false,
      tagInputValue: '',

      rejectReason: '',

      ungroupList: [],
      inviteList: [],

      projInstId: null,

      creatingTeam: false,
      newTeamInfo: {
        name: '',
        topic: '',
        member: '',
        desc: '',
        cnt: 1,
        projId: '',
        withRank: false,
      },
      formLabelWidth: '120px',
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    openDrawer (val) {
      console.log('pic clicked')
      console.log(val)
      this.memId = val
      this.memDrawer = true
      console.log('clicked')
    },
    closeDrawerStu () {
      this.memDrawer = false
    },

    init () {
      console.log('init team page')
      getMyTeamDetail(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code === 200) {
          let infoDict = resp.data.data

          if (infoDict == null) {
            this.haveTeam = false
          } else {
            this.haveTeam = true

            this.team_avatar = infoDict.iconUrl
            this.status = infoDict.status
            this.teamName = infoDict.teamName
            this.description = infoDict.descriptions
            this.tagList = JSON.parse(infoDict.tags)
            this.teamMemberList = infoDict.teamMemberList
            this.targetMemNum = infoDict.targetMemNum
            this.topic = infoDict.topic

            this.newDescription = infoDict.descriptions
            this.newTeamName = infoDict.teamName
            this.newTargetMemNum = infoDict.targetMemNum
            this.newTagList = JSON.parse(infoDict.tags)
            this.newTopic = infoDict.topic

            this.projInstId = infoDict.projInstId

            if (this.projInstId != null) {
              getTeamMessage(this.projInstId).then(resp => {
                if (resp.data.code === 200) {
                  let infoDict = resp.data.data

                  console.log(infoDict)

                  for (let i = 0; i < infoDict.length; ++i) {
                    infoDict[i].createdTime = getDatetimeStr(infoDict[i].createdTime)
                    this.noticeList.push(infoDict[i])
                  }

                  this.noticeList.sort(function (a, b) {
                    return Date.parse(b.createdTime) - Date.parse(a.createdTime)
                  })
                } else if (resp.data.code === 400) {
                  this.$message.error(resp.data.message)
                }
              }).catch(failResp => {
                this.$message.error(failResp.message)
              })
            }
          }
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          if (resp.data.message !== 'No team') {
            this.$message.error(resp.data.message)
          }
        }
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })

      getPersonalMessage(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code === 200) {
          let infoDict = resp.data.data
          console.log(infoDict)

          for (let i = 0; i < infoDict.length; ++i) {
            infoDict[i].createdTime = getDatetimeStr(infoDict[i].createdTime)
            this.noticeList.push(infoDict[i])
          }

          this.noticeList.sort(function (a, b) {
            return Date.parse(b.createdTime) - Date.parse(a.createdTime)
          })
        } else if (resp.data.code === 400) {
          this.$message.error(resp.data.message)
        }
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })

      getUngroupedStudents(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code === 200) {
          this.ungroupList = resp.data.data
          console.log('ungroup')
          console.log(this.ungroupList)
          this.avaStatus = new Array(this.ungroupList.length).fill(true)
        } else if (resp.data.code === 400) {
          this.$message.error(resp.data.message)
        }
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })

      getProjectTopics(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }

        let topics = resp.data.data
        this.topicList.splice(0, this.topicList.length)

        for (let i = 0; i < topics.length; i++) {
          this.topicList.push({label: topics[i].topicName, value: topics[i].topicName})
        }
      }).catch(failResp => {
        console.log('fail in getProjTitle. message=' + failResp.message)
      })
    },

    handleAccept (notice) {
      this.$confirm('Confirm to accept?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let applyReplyParamVO = {
          'accepted': true,
          'message': null,
          'msgId': notice.msgId
        }

        this.noticeList.splice(0, this.noticeList.length)

        postReplyApplication(applyReplyParamVO).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            // notice.decided = true
            // notice.result = 'accepted'
            this.noticeList.splice(this.noticeList.length)
            this.init()
            this.$message.success(resp.data.message)
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Accept canceled')
      })
    },
    handleReject (notice) {
      this.$confirm('Confirm to reject?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let applyReplyParamVO = {
          'accepted': false,
          'message': this.rejectReason,
          'msgId': notice.msgId
        }
        this.noticeList.splice(0, this.noticeList.length)

        postReplyApplication(applyReplyParamVO).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            // notice.decided = true
            // notice.result = 'rejected'
            this.noticeList.splice(this.noticeList.length)
            this.init()
            this.$message.success(resp.data.message)
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Reject canceled')
      })
    },
    handleMessageConfirm (notice) {
      notice.confirmed = true
    },
    handleEdit () {
      this.$confirm('Confirm to update?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let piuVO = {
          'description': this.newDescription,
          'projId': this.$store.state.proj.projId,
          'tags': JSON.stringify(this.newTagList),
          'targetMemNum': this.newTargetMemNum,
          'teamName': this.newTeamName,
          'topic': this.newTopic
        }

        postEditedTeamInfo(piuVO).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.description = this.newDescription
            this.topic = this.newTopic
            this.teamName = this.newTeamName
            this.targetMemNum = this.newTargetMemNum
            this.tagList = this.newTagList

            this.$message.success('Update success')
          } else if (resp.data.code === 400) {
            console.log(resp.data.message)
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Update canceled')
      })

    },
    handleCancel () {
      this.newDescription = this.description
      this.newTopic = this.topic
      this.newTeamName = this.teamName
      this.newTargetMemNum = this.targetMemNum
      this.newTagList = this.tagList
    },
    handleQuit () {
      this.$confirm('Confirm to quit?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        postQuitTeam(this.$store.state.proj.projId).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.noticeList.splice(0, this.noticeList.length)
            this.$message.success('Quit success')
            location.reload()
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Quit canceled')
      })
    },

    handleInvite (index) {
      this.inviteList.push(this.ungroupList[index])
      this.ungroupList.splice(index, 1)
    },
    handleUnInvite (index) {
      console.log(index)
      this.ungroupList.push(this.inviteList[index])
      this.inviteList.splice(index, 1)
    },
    handleUpdateInvite () {
      this.$confirm('Confirm to invite?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let roleIdList = []
        for (let i = 0; i < this.inviteList.length; ++i) {
          roleIdList.push(this.inviteList[i].roleId)
        }

        postInviteStudents(roleIdList, this.$store.state.proj.projId).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.inviteList = []
            this.$message.success('Change success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Invite canceled'
        })
      })
    },
    handleCancelInvite () {
      for (let i = 0; i < this.inviteList.length; ++i) {
        this.ungroupList.push(this.inviteList[i])
      }
      this.inviteList = []
    },

    isStudent () {
      return isStudent()
    },

    createMyTeam () {
      this.creatingTeam = true
    },
    closeAddDialog () {
      this.creatingTeam = false
      location.reload()
    },

    //impression tag list
    handleTagClose (tag) {
      this.newTagList.splice(this.newTagList.indexOf(tag), 1)
    },
    showTagInput () {
      this.tagInputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleTagInputConfirm () {
      let inputValue = this.tagInputValue
      if (inputValue) {
        this.newTagList.push(inputValue)
      }
      this.tagInputVisible = false
      this.tagInputValue = ''
    },

    handleTeamConfirm () {
      this.$confirm('Confirm to confirm?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let teamConfirmParamVO = {
          'force': true,
          'projInstIdList': [this.projInstId]
        }
        postTeamConfirmation(teamConfirmParamVO).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.$message.success('Confirm success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Confirm canceled'
        })
      })

    }
  }
}
</script>

<style scoped>
.base-card {
  margin: 15px 10px;
  min-height: 95.2%;
}

.Quit {
  /*red*/
  background: #fef0f0;
}

.JoinSuccess {
  /*green*/
  background: #f0f9eb;
}

.JoinFail {
  /*grey*/
  background: #f4f4f5;
}

.Apply {
  /*yellow*/
  background: #fdf6ec;
}

.Invite {
  /*blue*/
  background: #ecf5ff;
}

.personalInfoTitle {
  font-size: 22px;
  font-weight: bold;
}

.el-tag {
  margin-right: 10px;
  margin-top: 15px;
}

/*dynamic tags*/
.el-tag-dy {
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

.handle-overflow {
  overflow: hidden;
  white-space: nowrap;
  /*text-overflow: ellipsis;*/
  width: 100%;
}
</style>


<!--  <div>-->
<!--    <el-row style="margin-top: 20px">-->
<!--      <el-col :span="4" :offset="2">-->
<!--        <div align="left">-->
<!--          &lt;!&ndash;                <el-avatar :size="200"><div style="font-size: 80px; text-align:center; margin-top: -5px">n</div></el-avatar>&ndash;&gt;-->
<!--          <el-avatar src="http://wx1.sinaimg.cn/large/0072GgNply1g5takxmar5j309q09w3yl.jpg"-->
<!--                     :size="150"></el-avatar>-->
<!--        </div>-->
<!--      </el-col>-->

<!--      <el-col :span="16">-->
<!--        <div style="font-size: 50px; padding:0; text-align:left; margin-top: 20px">Team</div>-->
<!--        <div style="padding: 0; text-align:left; margin-top: 20px">-->
<!--          <el-tag>标签一</el-tag>-->
<!--          <el-tag type="success">标签二</el-tag>-->
<!--          <el-tag type="info">标签三</el-tag>-->
<!--          <el-tag type="warning">标签四</el-tag>-->
<!--          <el-tag type="danger">标签五</el-tag>-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->

<!--    <div style="margin-top: 40px; margin-left:40px">-->
<!--      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">-->
<!--        <el-tab-pane label="Introduction" name="intro">-->
<!--          <div style="font-size: 15px; padding:0; text-align:left; margin-top: 15px;margin-left: 20px">-->
<!--            Team for project {}-->
<!--          </div>-->
<!--          <div style="margin-top: 20px; margin-left: 20px">-->
<!--            <el-input-->
<!--              type="textarea"-->
<!--              :rows="5"-->
<!--              placeholder=""-->
<!--              v-model="textarea">-->
<!--            </el-input>-->
<!--          </div>-->
<!--        </el-tab-pane>-->

<!--        <el-tab-pane label="Member" name="member">-->
<!--          <el-row>-->
<!--            <el-col :span="2" class="left">-->
<!--              <el-avatar src="https://ww1.sinaimg.cn/thumb150/006z25tvly1genhm1qn9vj30tc0t9wi8.jpg"-->
<!--                         :size="50"></el-avatar>-->
<!--            </el-col>-->
<!--            <el-col :span="12">-->
<!--              <div style="font-size: 20px; padding:0; text-align:left; margin-top: 22px">name</div>-->
<!--            </el-col>-->
<!--          </el-row>-->

<!--          <el-row style="margin-top: 10px" class="left">-->
<!--            <el-col :span="2">-->
<!--              <el-avatar src="https://ww4.sinaimg.cn/orj360/0064eUp2ly1ge61i3malnj3073077746.jpg"-->
<!--                         :size="50"></el-avatar>-->
<!--            </el-col>-->
<!--            <el-col :span="12">-->
<!--              <div style="font-size: 20px; padding:0; text-align:left; margin-top: 15px">name</div>-->
<!--            </el-col>-->
<!--          </el-row>-->
<!--        </el-tab-pane>-->

<!--        <el-tab-pane label="Message" name="message">-->
<!--          message-->
<!--        </el-tab-pane>-->
<!--      </el-tabs>-->
<!--    </div>-->
<!--  </div>-->

<!--{-->
<!--member: '',-->
<!--type: 'quit',-->
<!--info: ' has quit from your team',-->
<!--applyInfo: '',-->
<!--handler: '',-->
<!--time: '',-->
<!--confirmed: false,-->
<!--decided: false,-->
<!--result: 'rejected'-->
<!--}-->
<!--{-->
<!--member: '',-->
<!--type: 'joinSuccess',-->
<!--info: ' successfully joined your team',-->
<!--applyInfo: '',-->
<!--handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',-->
<!--time: '',-->
<!--confirmed: false,-->
<!--decided: false,-->
<!--result: 'rejected'-->
<!--},-->
<!--{-->
<!--member: '',-->
<!--type: 'joinFail',-->
<!--info: ' has been refused to join your team',-->
<!--applyInfo: '',-->
<!--handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',-->
<!--time: '',-->
<!--confirmed: true,-->
<!--decided: false,-->
<!--result: 'rejected'-->
<!--},-->
<!--{-->
<!--member: '',-->
<!--type: 'apply',-->
<!--info: ' apply to join your team',-->
<!--applyInfo: 'Please accept me!',-->
<!--handler: '',-->
<!--time: '',-->
<!--confirmed: true,-->
<!--decided: false,-->
<!--result: 'rejected',-->
<!--},-->
<!--{-->
<!--member: '',-->
<!--type: 'invite',-->
<!--info: ' was invited to join your team',-->
<!--applyInfo: '',-->
<!--handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',-->
<!--time: '',-->
<!--confirmed: true,-->
<!--decided: false,-->
<!--result: 'rejected'-->
<!--}-->
<!--              <el-popover-->
<!--                placement="bottom"-->
<!--                width="200"-->
<!--                trigger="hover">-->
<!--                <div>-->

<!--                </div>-->
<!--                <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"-->
<!--                           :fit="'fill'"-->
<!--                           style="vertical-align:middle; cursor: pointer"-->
<!--                           @click="handleJump()"-->
<!--                           slot="reference"></el-avatar>-->
<!--              </el-popover>-->
<!--              <el-popover-->
<!--                placement="bottom"-->
<!--                width="200"-->
<!--                trigger="hover">-->
<!--                <div>-->

<!--                </div>-->
<!--                <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"-->
<!--                           :fit="'fill'"-->
<!--                           style="vertical-align:middle; margin-left: 8px; cursor: pointer"-->
<!--                           @click="handleJump()"-->
<!--                           slot="reference"></el-avatar>-->
<!--              </el-popover>-->


<!--<div class="personalInfoTitle">-->
<!--Description-->
<!--</div>-->
<!--<el-input type="textarea" :rows="4" :placeholder="description" v-model="newDescription"-->
<!--          style="margin-top: 20px; width: 70%">-->
<!--</el-input>-->
