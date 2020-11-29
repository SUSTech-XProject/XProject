import Vue from 'vue'
export function getTeamInfo () {
  return Vue.prototype.$axios.get('/team-info')
}
