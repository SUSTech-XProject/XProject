import Vue from 'vue'

export function getGradeList (projId) {
  return Vue.prototype.$axios.get('/student/grade/list', {
    params: {projId: projId}
  })
}

export function getAllRecord (projId) {
  return Vue.prototype.$axios.get('/all/record', {
    params: {projId: projId}
  })
}

export function getRecordInst (projInstId, rcdId) {
  return Vue.prototype.$axios.get('/teacher/record/inst', {
    params: {
      projInstId: projInstId,
      rcdId: rcdId
    }
  })
}

export function getRecordInstStudent (rcdId, stdRoleId) {
  return Vue.prototype.$axios.get('/teacher/record/inst/student', {
    params: {
      stdRoleId: stdRoleId,
      rcdId: rcdId
    }
  })
}

export function postNewRecord (recordCreationVO) {
  return Vue.prototype.$axios.post('/teacher/grade/new/record', recordCreationVO)
}

export function postDeleteRecord (recordDeletionVO) {
  return Vue.prototype.$axios.post('/teacher/grade/delete/record', recordDeletionVO)
}

export function postNewGrade (recordInstUpdateParamVO) {
  return Vue.prototype.$axios.post('/teacher/grade/new/grade', recordInstUpdateParamVO)
}

export function postCombineRecordInst (combineRcdInstParamVO) {
  return Vue.prototype.$axios.post('/teacher/combine/record/inst', combineRcdInstParamVO)
}
