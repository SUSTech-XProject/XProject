import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    role: {
      // this is for init
      username: window.localStorage.getItem('role') == null ?
        '' : JSON.parse(window.localStorage.getItem('role' || '[]')).username,
      // '' for visitor, 'S' for student, 'T' for teacher, 'A' for admin
      // this will be updated before visiting each jump
      roleType: '',
    }
  },
  mutations: {
    login (state, loginForm) {
      state.role.username = loginForm.username
      window.localStorage.setItem('role', JSON.stringify(state.role))
    },
    logout (state) {
      state.role.username = ''
      state.role.roleType = ''
      window.localStorage.removeItem('role')
    },
    updateRoleType (state, roleType) {
      state.role.roleType = roleType
      window.localStorage.setItem('role', JSON.stringify(state.role))
    }
  }
})
