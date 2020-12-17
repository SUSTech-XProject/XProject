import Vue from 'vue'

export function getTeamInfoList (projId) {
  return Vue.prototype.$axios.get('/all/team/list', {
    params: {projId: projId}
  })
}

export function getTeamDetail (teamId) {
  return Vue.prototype.$axios.get('/all/team/info/detail', {
    params: {teamId: teamId}
  })
}

export function postApply (teamId) {
  return Vue.prototype.$axios.post('/student/team/apply', {
    teamId: teamId,
  })
}

export function postTeamCreation (form) {
  return Vue.prototype.$axios.post('/all/team/creation', {
    description: form.desc,
    generateNum: form.cnt===''?1:form.cnt,
    idealSize: form.member,
    projId: form.projId,
    teamName: form.name,
    topic: form.topic,
    useIncrNum: form.withRank,
  })
}

export function postTeamDeletion (teams) {
  return Vue.prototype.$axios.post('/team-deletion', {
    projInstIdList: teams
  })
}

export function postTeamConfirmation (teams) {
  return Vue.prototype.$axios.post('/team-confirm', {
    projInstIdList: teams
  })
}

export function getProjectTopics (projId) {
  return Vue.prototype.$axios.get('/all/project/topic', {
    params: {projId: projId}
  })
}

export function getStuInfo (roleId) {
  return Vue.prototype.$axios.get('/all/team/stu-info', {
    params: {roleId: roleId}
  })
}

export function postAutoForming (form) {
  return Vue.prototype.$axios.post('/teacher/team/auto-forming', {
    AutoFormingVO: form
  })
}

export function getMyTeamDetail (projId) {
  return Vue.prototype.$axios.get('/student/team/detail', {
    params: {projId: projId}
  })
}

export function getUngroupedStudents (projId) {
  return Vue.prototype.$axios.get('/student/team/ungrouped', {
    params: {projId: projId}
  })
}

export function postInviteStudents (roleIdList, projId) {
  return Vue.prototype.$axios.post('/student/team/invite', {
    projId: projId,
    stdRoleIdList: roleIdList
  })
}

export function postQuitTeam (projId) {
  return Vue.prototype.$axios.post('/student/team/quit', {
    params: {projId: projId}
  })
}

export function postReplyApplication (msgId, accepted, reason) {
  return Vue.prototype.$axios.post('/student/team/quit', {
    //todo
    msg_Id: msgId,
    accepted: accepted,
    reason: reason
  })
}

export function getTeamMessage (projInstId) {
  return Vue.prototype.$axios.get('/all/team/message', {
    params: {projInstId: projInstId}
  })
}

export function getPersonalMessage (projId) {
  return Vue.prototype.$axios.get('/all/project/message', {
    params: {projId: projId}
  })
}

export function postEditedTeamInfo (projId) {
  return Vue.prototype.$axios.post('/student/team/change/info', {
   // todo
  })
}
export function getStuProj (roleId) {
  return Vue.prototype.$axios.get('/all/team/stu-proj', {
    params: {roleId:roleId}
  })
}
