<template>
  <el-card class="base-card">
    <el-col :span="4" :offset="3">
      <div v-if="isStudent() && haveTeam()"
           style="margin-top: 20px">
        <el-avatar src="https://ww1.sinaimg.cn/thumb150/006z25tvly1genhm1qn9vj30tc0t9wi8.jpg"
                   :size="250" :fit="'fill'"></el-avatar>

        <div style="margin-top: 20px">team name</div>

        <el-tag v-for="tag in tagList" :key="tag"
                class="el-tag" effect="plain">
          {{ tag }}
        </el-tag>

        <div style="width: 100%; margin-top: 20px" align="left">
          {{ description }}
        </div>

        <el-avatar v-for="member in teamMemberList" :key="member"
                   :src=member :size="290" :fit="'fill'"
                   style="margin-top: 15px"></el-avatar>
      </div>

      <div v-if="isStudent() && !haveTeam()"
           style="margin-top: 20px">
        no team yet
      </div>
    </el-col>

    <el-col :span="13" :offset="1">
      <div style="font-size: 30px; font-weight:bold; margin-top: 10px">
        Notices
        <el-divider></el-divider>
      </div>
      <div v-for="(notice, index) in noticeList" :key="index">
        <el-card class="box-card" shadow="never"
                 :class="notice.type"
                 style="width: 90%; margin-top: 20px;">
          <el-col :span="17">
            <div>
              <el-popover
                placement="bottom"
                width="200"
                trigger="hover">
                <div>

                </div>
                <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"
                           :fit="'fill'"
                           style="vertical-align:middle; cursor: pointer"
                           @click="handleJump()"
                           slot="reference"></el-avatar>
              </el-popover>

              <!--              <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"-->
              <!--                         :fit="'fill'"-->
              <!--                         style="vertical-align:middle; cursor: pointer"-->
              <!--                         @click="handleJump()"></el-avatar>-->

              <span style="margin-left: 5px; font-size: 16px;vertical-align:middle;">
              {{ notice.info }}
            </span>
            </div>

            <div v-if="notice.type==='joinSuccess'|| notice.type==='joinFail'|| notice.type==='invite'"
                 style="margin-top: 10px; margin-left: 10px;">
            <span v-if="notice.type==='invite'"
                  style="vertical-align:middle; font-size: 13px">Inviter: </span>
              <span v-else
                    style="vertical-align:middle; font-size: 13px">Handler: </span>
              <el-popover
                placement="bottom"
                width="200"
                trigger="hover">
                <div>

                </div>
                <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"
                           :fit="'fill'"
                           style="vertical-align:middle; margin-left: 8px; cursor: pointer"
                           @click="handleJump()"
                           slot="reference"></el-avatar>
              </el-popover>
              <!--              <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"-->
              <!--                         :fit="'fill'"-->
              <!--                         style="vertical-align:middle; margin-left: 8px; cursor: pointer"-->
              <!--                         @click="handleJump()"></el-avatar>-->
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
                    <el-button @click="handleReject(notice)">Reply</el-button>
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

      <div style="margin-bottom: 40px"></div>
    </el-col>
  </el-card>
</template>

<script>
import {isStudent} from '@/utils/role'

export default {
  name: 'MyTeam',
  components: {},
  data () {
    return {
      tagList: [],
      description: '',
      teamMemberList: [],
      noticeList: [
        {
          type: 'quit',
          info: ' has quit from your team',
          applyInfo:'',
          handler: '',
          time: '',
          confirmed: false,
          decided: false,
          result: 'rejected'
        },
        {
          type: 'joinSuccess',
          info: ' successfully joined your team',
          applyInfo:'',
          handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',
          time: '',
          confirmed: false,
          decided: false,
          result: 'rejected'
        },
        {
          type: 'joinFail',
          info: ' has been refused to join your team',
          applyInfo:'',
          handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',
          time: '',
          confirmed: true,
          decided: false,
          result: 'rejected'
        },
        {
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
          type: 'invite',
          info: ' was invited to join your team',
          applyInfo:'',
          handler: 'https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg',
          time: '',
          confirmed: true,
          decided: false,
          result: 'rejected'
        }
      ],

      visible: false
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init team page')
      //TODO: init
    },
    isStudent () {
      return isStudent()
    },
    haveTeam () {
      return true
    },
    handleAccept (notice) {
      //TODO: logic

      notice.decided = true
      notice.result = 'accepted'
    },
    handleReject (notice) {
      this.visible = false

      //TODO: logic

      notice.decided = true
      notice.result = 'rejected'
    },
    handleConfirm (notice) {
      notice.confirmed = true
    },
    handleJump () {

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
