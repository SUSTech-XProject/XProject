import Vue from 'vue'

export function getProjOverview (projId) {
  return Vue.prototype.$axios.get('/all/project/overview', {
    params: {projId: projId}
  })
}

export function postProjectOverview (
  projId,
  siteInfo,
  topicBO,
  projSettingsBO) {
  return Vue.prototype.$axios.post('/teacher/update-overview', {
    projId: projId,
    description: siteInfo,
    topics: topicBO,
    projSettings: projSettingsBO
  })
}
