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

export function postEditSchool (schId, schName, location) {
  return Vue.prototype.$axios.post('/admin/school/add', {
    schId: schId,
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

export function postAllowTch (roleId) {
  return Vue.prototype.$axios.post('/admin/school/allow-tch', null, {
    params: {
      roleId: roleId
    }
  })
}

export function postAllowStu (roleId) {
  return Vue.prototype.$axios.post('/admin/school/allow-stu', null, {
    params: {
      roleId: roleId
    }
  })
}
