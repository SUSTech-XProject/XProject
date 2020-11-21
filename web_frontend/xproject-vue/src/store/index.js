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
    },
    proj: {
      projId: null,
      projName: null
    }
  },
  mutations: {
    login (state, loginForm) {
      state.role.username = loginForm.username
      window.localStorage.setItem('role', JSON.stringify(state.role))

      state.proj.projId = null
      state.proj.projName = null
    },
    logout (state) {
      state.role.username = ''
      state.role.roleType = ''
      window.localStorage.removeItem('role')

      state.proj.projId = null
      state.proj.projName = null
    },
    updateRoleType (state, roleType) {
      state.role.roleType = roleType
      window.localStorage.setItem('role', JSON.stringify(state.role))
    },

    setProj(state, proj) {
      state.proj = proj
    },
    removeProj(state) {
      state.proj.projId = null
      state.proj.projName = null
    }
  }
})
