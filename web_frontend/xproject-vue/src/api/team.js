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
export function postTeamCreation(projId,teams){
  return Vue.prototype.$axios.post('/team-creation',{
    params:{projId:projId}
  })
}
export function postTeamDeletion(teams){
  return Vue.prototype.$axios.post('/team-deletion',{
    teamIdList:teams
  })
}
export function postTeamConfirmation(teams){
  return Vue.prototype.$axios.post('/team-confirm',{
    teamIdList:teams
  })
}
export function getProjectTopics(projId){
  return Vue.prototype.$axios.get('/proj-topic',{
    params:{projId:projId}
  })
}
