import Vue from 'vue'
export function getTeamInfo (projId) {
  return Vue.prototype.$axios.get('/team-info')
}
export function getTeamDetail(teamId){
  return Vue.prototype.$axios.get('/team-detail')
}
export function postApply(teamId){
  return Vue.prototype.$axios.post('/team-apply', {
    teamId:teamId,
  })

}
