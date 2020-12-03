import Vue from 'vue'

export function getGradeList (projId) {
  return Vue.prototype.$axios.get('/grade-list', {
    params: {projId: projId}
  })
}
