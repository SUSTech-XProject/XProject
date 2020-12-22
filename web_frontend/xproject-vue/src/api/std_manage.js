import Vue from 'vue'

export function postGetStudentAcPage (page, pageSize, orderBy, orderType, stdClass) {
  return Vue.prototype.$axios.post('/teacher/students', {
    page: page,
    pageSize: pageSize,
    orderBy: orderBy,
    orderType: orderType,
    stdClass: stdClass
  })
}

export function postDeleteStudentAc (deleteSltList) {
  return Vue.prototype.$axios.delete('/teacher/students', {
    params: {
      deleteSltList: deleteSltList
    }
  })
}

export function postImportFromExcel (formData) {
  let options = {
    url: '/teacher/students/excel',
    data: formData,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }
  return Vue.prototype.$axios(options)
}

export function getProjectListBySch () {
  return Vue.prototype.$axios.get('/teacher/students')
}

export function postAddStdIntoProj (pasVO) {
  return Vue.prototype.$axios.post('/teacher/project/add/std', pasVO)
}

export function getProjStdList (projId) {
  return Vue.prototype.$axios.get('/teacher/project/students', {
    params: {projId: projId},
  })
}

export function postClearStdTeam (quitProjParamVO) {
  return Vue.prototype.$axios.post('/teacher/clear/std/team', quitProjParamVO)
}
