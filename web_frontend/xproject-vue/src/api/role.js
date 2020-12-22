import Vue from 'vue'

export function postLogin (username, password, rememberMe) {
  return Vue.prototype.$axios.post('/visitor/login', {
    username: username,
    password: password,
    rememberMe: rememberMe
  })
}

export function postRegister (type, username, password) {
  return Vue.prototype.$axios.post('/visitor/register', {
    type: type,
    username: username,
    password: password
  })
}

export function getLogout () {
  return Vue.prototype.$axios.get('/logout')
}

export function getAuth () {
  return Vue.prototype.$axios.get('/authentication')
}

export function getRoleList () {
  return Vue.prototype.$axios.get('/admin/role/list', {
  })
}

export function postChangeStatus (roleId) {
  return Vue.prototype.$axios.post('/admin/role/change', null, {
    params: {
      roleId: roleId
    }
  })
}
