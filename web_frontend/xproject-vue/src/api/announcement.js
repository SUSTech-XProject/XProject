import Vue from 'vue'

export function getAnnouncementList (projId) {
  return Vue.prototype.$axios.get('/all/project/ann', {
    params: {projId: projId}
  })
}

export function postModifyAnnouncement (annId, title, message) {
  return Vue.prototype.$axios.post('/teacher/project/ann/modify', {
    annId: annId,
    title: title,
    message: message,
  })
}

export function postAddAnnouncement (projId, title, message) {
  return Vue.prototype.$axios.post('/teacher/project/ann/add', {
    projId: projId,
    title: title,
    message: message
  })
}

export function getDeleteAnnouncement (annId) {
  return Vue.prototype.$axios.get('/teacher/project/ann/delete', {
    params: {
      annId: annId
    }
  })
}
