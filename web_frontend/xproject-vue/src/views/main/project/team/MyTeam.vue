<template>
  <el-card class="base-card">
    <el-col :span="4" :offset="3">
      <div v-if="isStudent() && haveTeam"
           style="margin-top: 20px">
        <el-avatar :src="team_avatar"
                   :size="250" :fit="'fill'"></el-avatar>

        <div style="margin-top: 20px">{{ teamName }}</div>

        <!--        <el-tag v-for="tag in tagList" :key="tag"-->
        <!--                class="el-tag" effect="plain">-->
        <!--          {{ tag }}-->
        <!--        </el-tag>-->

        <div v-if="!editing"
             style="width: 100%; margin-top: 20px" align="left">
          {{ description }}
        </div>

        <div v-else
             style="width: 100%; margin-top: 20px" align="left">
          <el-input v-model="newDescription" placeholder="New description here..."></el-input>
        </div>

        <div v-for="member in teamMemberList" :key="member.stdId"
             @click="handleJump2(member)">
          <el-avatar :fit="'fill'" :src="member.iconUrl"
                     style="margin-top: 15px; margin-right: 10px; cursor: pointer"
          ></el-avatar>
        </div>

        <div>
          <el-button @click="handleEdit()"
                     style="margin-top: 15px;">
            <div v-if="!editing">Edit</div>
            <div v-else>Update</div>
          </el-button>
          <el-button @click="handleCancel()"
                     style="margin-top: 15px;">Cancel
          </el-button>
        </div>
        <el-button v-if="status==='Raw'"
                   @click="handleQuit()"
                   type="primary"
                   style="margin-top: 15px; align-items: center">Quit
        </el-button>
      </div>

      <div v-if="isStudent() && !haveTeam"
           style="margin-top: 20px">
        no team yet
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
              <el-col :span="17">
                <div>
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

                  <span @click="handleJump(notice,1)">
                    <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"
                               :fit="'fill'"
                               style="vertical-align:middle; cursor: pointer"></el-avatar>
                  </span>

                  <span style="margin-left: 5px; font-size: 16px;vertical-align:middle;">
                    {{ notice.info }}
                  </span>
                </div>

                <div v-if="notice.type==='joinSuccess'|| notice.type==='joinFail'|| notice.type==='invite'"
                     style="margin-top: 10px; margin-left: 10px;">
                  <span v-if="notice.type==='invite'"
                        style="vertical-align:middle; font-size: 13px">
                    Inviter:
                  </span>
                  <span v-else style="vertical-align:middle; font-size: 13px">
                    Handler:
                  </span>
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
                  <span @click="handleJump(notice,2)">
                    <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"
                               :fit="'fill'"
                               style="vertical-align:middle; margin-left: 8px; cursor: pointer"></el-avatar>
                  </span>
                </div>

                <div v-if="notice.type==='apply'"
                     style="margin-top: 20px; margin-left: 10px; font-size: 13px">
                  ApplyInfo: {{ notice.applyInfo }}
                </div>

                <div style="margin-top: 20px; margin-left: 10px; font-size: 13px">
                  Date: {{ notice.time }}
                </div>

                <div style="margin-bottom: 20px"></div>
              </el-col>

              <el-col :span="7">
                <div v-if="notice.type==='apply'">

                  <div v-if="!notice.decided"
                       style="margin-top: 35px">
                    <el-popover
                      placement="bottom"
                      width="340"
                      v-model="visible">
                      <div>
                        Reason:
                        <el-input style="width: 200px;"></el-input>
                        <el-button v-model="rejectReason" placeholder="Reason here..."
                                   @click="handleReject(notice)">
                          Reply
                        </el-button>
                      </div>
                      <el-button slot="reference">Reject</el-button>
                    </el-popover>

                    <el-button type="primary" @click="handleAccept(notice)">Accept</el-button>
                  </div>

                  <div v-else
                       style="margin-top: 45px; margin-left: 49px">
                    <div>{{ notice.result }}</div>
                  </div>
                </div>

                <div v-else-if="notice.type==='quit'">
                  <el-button @click="handleConfirm(notice)"
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
                  <el-button @click="handleConfirm(notice)"
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
          <el-row>
            <el-col style="width: 47%; margin-left: 2%">
              <el-card class="box-card" style="min-height: 155px">
                <div slot="header" class="clearfix">
                  <span>Available classmates</span>
                </div>
                <span v-for="(ava, index) in avaList" :key="index"
                      @click="handleInvite(index)">
                <el-avatar :fit="'fill'"
                           style="margin-top: 15px; margin-right: 10px; cursor: pointer"
                ></el-avatar>
              </span>
                <!--        :src=member-->
              </el-card>
            </el-col>

            <el-col style="width: 47%; margin-left: 2%">
              <el-card class="box-card" style="min-height: 155px">
                <div slot="header" class="clearfix">
                  <span>Invite list</span>
                </div>
                <span v-for="(unava, index) in unAvaList" :key="index"
                      @click="handleUnInvite(index)">
                <el-avatar :fit="'fill'"
                           style="margin-top: 15px; margin-right: 10px; cursor: pointer"
                ></el-avatar>
              </span>
                <!--        :src=member-->
              </el-card>
            </el-col>
          </el-row>

          <div align="center" style="margin-top: 20px;">
            <el-button type="primary" @click="handleUpdateInvite()">Invite</el-button>
            <el-button @click="handleCancelInvite()">Cancel</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
      <!--      <div style="font-size: 30px; font-weight:bold; margin-top: 10px">-->
      <!--        Notices-->
      <!--        <el-divider></el-divider>-->
      <!--      </div>-->
      <div style="margin-bottom: 40px"></div>
    </el-col>
  </el-card>
</template>

<script>
import {isStudent} from '@/utils/role'
import {
  getMyTeamDetail,
  getTeamMessage,
  getUngroupedStudents, postInviteStudents,
  postQuitTeam,
  postReplyApplication,
  postTeamDescription
} from '@/api/team'

export default {
  name: 'MyTeam',
  components: {},
  data () {
    return {
      status: '',
      team_avatar: '',
      haveTeam: false,

      tabPosition: 'top',
      activeName: 'invite',

      teamName: '',
      tagList: [],

      avaList: [],
      unAvaList: [],

      description: '',
      newDescription: '',
      editing: false,

      teamMemberList: [],
      noticeList: [
        {
          member: '',
          type: 'quit',
          info: ' has quit from your team',
          applyInfo: '',
          handler: '',
          time: '',
          confirmed: false,
          decided: false,
          result: 'rejected'
        },
        {
          member: '',
          type: 'joinSuccess',
          info: ' successfully joined your team',
          applyInfo: '',
          handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',
          time: '',
          confirmed: false,
          decided: false,
          result: 'rejected'
        },
        {
          member: '',
          type: 'joinFail',
          info: ' has been refused to join your team',
          applyInfo: '',
          handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',
          time: '',
          confirmed: true,
          decided: false,
          result: 'rejected'
        },
        {
          member: '',
          type: 'apply',
          info: ' apply to join your team',
          applyInfo: 'Please accept me!',
          handler: '',
          time: '',
          confirmed: true,
          decided: false,
          result: 'rejected',
        },
        {
          member: '',
          type: 'invite',
          info: ' was invited to join your team',
          applyInfo: '',
          handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',
          time: '',
          confirmed: true,
          decided: false,
          result: 'rejected'
        }
      ],
      visible: false,

      rejectReason: ''
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init team page')
      getMyTeamDetail(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code === 200) {
          let infoDict = resp.data.data
          console.log(resp.data)

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

      // getUngroupedStudents(this.$store.state.proj.projId).then(resp => {
      //   if (resp.data.code === 200) {
      //     let infoDict = resp.data.data
      //
      //     this.avaList = infoDict.stuList
      //     this.avaStatus = new Array(len(this.avaList)).fill(true)
      //   } else if (resp.data.code === 400) {
      //     console.log(resp.data.message)
      //     this.$alert(resp.data.message, 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   }
      // }).catch(failResp => {
      //   this.$alert('Error: ' + failResp.message, 'Tips', {
      //     confirmButtonText: 'OK'
      //   })
      // })

      // getTeamMessage(this.$store.state.proj.projId).then(resp => {
      //   if (resp.data.code === 200) {
      //     let infoDict = resp.data.data
      //
      //     this.noticeList=infoDict.message
      //   } else if (resp.data.code === 400) {
      //     console.log(resp.data.message)
      //     this.$alert(resp.data.message, 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   }
      // }).catch(failResp => {
      //   this.$alert('Error: ' + failResp.message, 'Tips', {
      //     confirmButtonText: 'OK'
      //   })
      // })
    },
    isStudent () {
      return isStudent()
    },
    handleAccept (notice) {
      //TODO: logic
      // postReplyApplication(notice.msgId, true, null).then(resp => {
      //   console.log('get response : ' + resp)
      //   if (resp.data.code === 200) {
      //     this.$alert('Quit success', 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   } else if (resp.data.code === 400) {
      //     console.log(resp.data.message)
      //     this.$alert(resp.data.message, 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   }
      // }).catch(failResp => {
      //   this.$alert('Error ' + failResp.message, 'Tip', {
      //     confirmButtonText: 'OK'
      //   })
      // })
      notice.decided = true
      notice.result = 'accepted'
    },
    handleReject (notice) {
      this.visible = false

      //TODO: upload reject and reason
      // postReplyApplication(notice.msgId, true, this.rejectReason).then(resp => {
      //   console.log('get response : ' + resp)
      //   if (resp.data.code === 200) {
      //     this.$alert('Quit success', 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   } else if (resp.data.code === 400) {
      //     console.log(resp.data.message)
      //     this.$alert(resp.data.message, 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   }
      // }).catch(failResp => {
      //   this.$alert('Error ' + failResp.message, 'Tip', {
      //     confirmButtonText: 'OK'
      //   })
      // })
      notice.decided = true
      notice.result = 'rejected'
    },
    handleConfirm (notice) {
      notice.confirmed = true
    },
    handleJump (notice, i) {
      if (i === 1) {
        //notice.member
      } else if (i === 2) {
        //notice.handler
      }
    },
    handleJump2 (member) {
      // member in teamMemberList
    },
    handleEdit () {
      if (this.editing) {
        // todo:upload newDescription
        // postTeamDescription(this.$store.state.proj.projId, this.description).then(resp => {
        //   console.log('get response : ' + resp)
        //   if (resp.data.code === 200) {
        //     this.$alert('Quit success', 'Tip', {
        //       confirmButtonText: 'OK'
        //     })
        //   } else if (resp.data.code === 400) {
        //     console.log(resp.data.message)
        //     this.$alert(resp.data.message, 'Tip', {
        //       confirmButtonText: 'OK'
        //     })
        //   }
        // }).catch(failResp => {
        //   this.$alert('Error ' + failResp.message, 'Tip', {
        //     confirmButtonText: 'OK'
        //   })
        // })
        this.description = this.newDescription
        this.newDescription = ''
      }
      this.editing = !this.editing
    },
    handleCancel () {
      this.newDescription = ''
      this.editing = false
    },
    handleQuit () {
      this.haveTeam = false
      // todo
      // postQuitTeam(this.$store.state.proj.projId).then(resp => {
      //   console.log('get response : ' + resp)
      //   if (resp.data.code === 200) {
      //     this.$alert('Quit success', 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   } else if (resp.data.code === 400) {
      //     console.log(resp.data.message)
      //     this.$alert(resp.data.message, 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   }
      // }).catch(failResp => {
      //   this.$alert('Error ' + failResp.message, 'Tip', {
      //     confirmButtonText: 'OK'
      //   })
      // })
    },
    handleInvite (index) {
      this.unAvaList.push(this.avaList[index])
      this.avaList.splice(index, 1)
    },
    handleUnInvite (index) {
      console.log(index)
      this.avaList.push(this.unAvaList[index])
      this.unAvaList.splice(index, 1)
    },
    handleUpdateInvite () {
      // todo: push unavalist
      // postInviteStudents(this.$store.state.proj.projId, this.unAvaList).then(resp => {
      //   console.log('get response : ' + resp)
      //   if (resp.data.code === 200) {
      //     this.$alert('Quit success', 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   } else if (resp.data.code === 400) {
      //     console.log(resp.data.message)
      //     this.$alert(resp.data.message, 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   }
      // }).catch(failResp => {
      //   this.$alert('Error ' + failResp.message, 'Tip', {
      //     confirmButtonText: 'OK'
      //   })
      // })
    },
    handleCancelInvite () {
      for (let unAva in this.unAvaList) {
        this.avaList.push(unAva)
        this.unAvaList = []
      }
    }
  }
}
</script>

<style scoped>
.base-card {
  margin: 15px 10px;
  min-height: 95.7%;
}

.quit {
  /*red*/
  background: #fef0f0;
}

.joinSuccess {
  /*green*/
  background: #f0f9eb;
}

.joinFail {
  /*grey*/
  background: #f4f4f5;
}

.apply {
  /*yellow*/
  background: #fdf6ec;
}

.invite {
  /*blue*/
  background: #ecf5ff;
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
