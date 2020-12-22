import Vue from 'vue'

export function getSchoolList () {
  return Vue.prototype.$axios.get('/visitor/role/school', {
  })
}

export function postAddSchool (schName, location) {
  return Vue.prototype.$axios.post('/admin/school/add', {
    schName: schName,
    location: location
  })
}

export function getDeleteSchool (schId) {
  return Vue.prototype.$axios.get('/admin/school/delete', {
    params: {
      schId: schId
    }
  })
}
