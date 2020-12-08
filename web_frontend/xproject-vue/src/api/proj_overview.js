import Vue from 'vue'

export function getProjOverviewStudent () {
  return Vue.prototype.$axios.get('student/proj-overview')
}
export function getProjOverviewTeacher () {
  return Vue.prototype.$axios.get('student/proj-overview')
}

export function postProjectOverview (
  useRecruitSystem,
  minSize,
  maxSize,
  dueTime,
  allowCrossShiftTeaming) {
  return Vue.prototype.$axios.post('teacher/update-overview', {})
}
