import Vue from 'vue'

export function getTeamInfo (projId) {
  return Vue.prototype.$axios.get('/team-info', {
    params: {
      projId: projId,
    }
  })
}

export function getTeamDetail(teamId){
  return Vue.prototype.$axios.get('/team-detail', {
    params: {
      teamId: teamId,
    }
  })
}

export function postApply(teamId){
  return Vue.prototype.$axios.post('/team-apply', {
    teamId:teamId,
  })
}
