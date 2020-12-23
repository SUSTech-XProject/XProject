import Vue from 'vue'

export function getSubmissionList (projId) {
  return Vue.prototype.$axios.get('/all/project/submission', {
    params: {projId: projId}
  })
}

export function getSbmInsList (sbmId, projId) {
  return Vue.prototype.$axios.get('/teacher/project/submission-ins', {
    params: {
      sbmId: sbmId,
      projId: projId
    }
  })
}

export function getSbmInstStd (sbmId) {
  return Vue.prototype.$axios.get('/student/project/submission/inst', {
    params: {sbmId: sbmId}
  })
}

export function postAddSubmission (projId, title, description, dueTime, finalTime, maxSubmission, status) {
  return Vue.prototype.$axios.post('/teacher/project/sub/add', {
    projId: projId,
    title: title,
    description: description,
    dueTime: dueTime,
    finalTime: finalTime,
    maxSubmission: maxSubmission,
    status: status
  })
}

export function postModifySubmission (projId, sbm_id, title, description, dueTime, finalTime, maxSubmission, status, resources) {
  return Vue.prototype.$axios.post('/teacher/project/sub/modify', {
    projId: projId,
    sbm_id: sbm_id,
    title: title,
    description: description,
    dueTime: dueTime,
    finalTime: finalTime,
    maxSubmission: maxSubmission,
    status: status
  })
}

export function getDeleteSubmission (sbmId) {
  return Vue.prototype.$axios.get('/teacher/project/sub/delete', {
    params: {
      sbmId: sbmId
    }
  })
}

export function getLeftSubmissionTime (projId, submId) {
  return Vue.prototype.$axios.get('/student/project/sub/sbm-left', {
    params: {
      projId: projId,
      submId: submId
    }
  })
}

export function postUploadSubmission (formData) {
  let options = {
    url: '/student/submission/upload',
    data: formData,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }
  return Vue.prototype.$axios(options)
}

export function postImportFromExcel (formData) {
  let options = {
    url: '/teacher/students/import',
    data: formData,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }
  return Vue.prototype.$axios(options)
}
