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
