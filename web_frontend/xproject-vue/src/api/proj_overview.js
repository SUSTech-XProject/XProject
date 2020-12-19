import Vue from 'vue'

export function getProjOverview (projId) {
  return Vue.prototype.$axios.get('/all/project/overview', {
    params: {projId: projId}
  })
}

export function postProjectOverview (projectVO) {
  return Vue.prototype.$axios.post('/teacher/update-overview', projectVO)
}
