import Vue from 'vue'

export function getSubmissionList (projId) {
  return Vue.prototype.$axios.get('/teacher/project/submission', {
    params: {projId: projId}
  })
}

export function getAllSubmissionList (sbmId) {
  return Vue.prototype.$axios.get('/teacher/project/submission-ins', {
    params: {submId: sbmId}
  })
}

export function postAddSubmission (projId, title, description, dueTime, finalTime, maxSubmission, status, resources) {
    return Vue.prototype.$axios.post('/teacher/project/sub/add', {
    projId: projId,
    title: title,
    description: description,
    dueTime: dueTime,
    finalTime: finalTime,
    maxSubmission: maxSubmission,
    status: status,
    resources: resources
  })
}

export function postModifySubmission (projId, sbm_id, title, description, dueTime, finalTime, maxSubmission, status, resources) {
  return Vue.prototype.$axios.post('/teacher/project/sub/modify', {
    projId: projId,
    sbm_id:sbm_id,
    title: title,
    description: description,
    dueTime: dueTime,
    finalTime: finalTime,
    maxSubmission: maxSubmission,
    status: status,
    resources: resources
  })
}

export function getDeleteSubmission (sbmId) {
  return Vue.prototype.$axios.get('/teacher/project/sub/delete', {
    params: {
      sbmId: sbmId
    }
  })
}
