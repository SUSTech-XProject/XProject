import Vue from 'vue'

export function getGradeList (projId) {
  return Vue.prototype.$axios.get('/all/grade/list', {
    params: {projId: projId}
  })
}

export function postAddRecord (obj) {
  return Vue.prototype.$axios.post('/teacher/grade/new/record', obj)
}

export function postNewGrade (obj) {
  return Vue.prototype.$axios.post('/teacher/grade/new/grade', obj)
}
