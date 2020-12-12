import Vue from 'vue'
export function getTeamInfoList (projId) {
  return Vue.prototype.$axios.get('/team-info-list', {
    params: {projId: projId}
  })
}
export function getTeamDetail(teamId){
  return Vue.prototype.$axios.get('/team-detail',{
    params:{teamId:teamId}
  })
}
export function postApply(teamId){
  return Vue.prototype.$axios.post('/team-apply', {
    teamId:teamId,
  })
}
export function postTeamCreation(form){
  return Vue.prototype.$axios.post('/team-creation',{
    description:form.desc,
    generateNum:form.cnt,
    idealSize:form.member,
    projId:form.projId,
    teamName:form.name,
    topic:form.topic,
    useIncrNum:form.withRank,
  })
}
export function postTeamDeletion(teams){
  return Vue.prototype.$axios.post('/team-deletion',{
    projInstIdList: teams
  })
}
export function postTeamConfirmation(teams){
  return Vue.prototype.$axios.post('/team-confirm',null,{
    params:{teamIdList:teams}
  })
}
export function getProjectTopics(projId){
  return Vue.prototype.$axios.get('/proj-topic',{
    params:{projId:projId}
  })
}
