import Vue from 'vue'

export function getProjOverview (projId) {
  return Vue.prototype.$axios.get('/project/overview', {
    params: {projId: projId}
  })
}

export function postProjectOverview (
  siteInfo,
  topicBO,
  projSettingsBO) {
  return Vue.prototype.$axios.post('teacher/update-overview', {
    siteInfo: siteInfo,
    TopicBO: topicBO,
    ProjSettingsBO: projSettingsBO
  })
}
