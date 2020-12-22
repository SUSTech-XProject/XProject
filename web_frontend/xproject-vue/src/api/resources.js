import Vue from 'vue'

export function getResourcesList (projId) {
  return Vue.prototype.$axios.get('/all/resource/list', {
    params: {projId: projId}
  })
}

export function postAddResources (files) {
  return Vue.prototype.$axios.post('/teacher/project/resource/add', {
    files: files
  })
}

export function getDeleteResources (srcId) {
  return Vue.prototype.$axios.get('/teacher/project/resource/del', {
    params: {srcId: srcId}
  })
}

export function getDownload (srcId) {
  return Vue.prototype.$axios.get('/all/resource/download', {
    params: {srcId: srcId}
  })
}

// export function postUpload (files) {
//   return Vue.prototype.$axios.post('/all/project/resource/download', {
//     files: fki
//   })
// }

// teacher
// postAddResources
// getDeleteResources
// getResourcesList
// postUpload
//
// student
// postDownload
