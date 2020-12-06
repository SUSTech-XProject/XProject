import Vue from 'vue'

export function hasProjState() {
  return Vue.prototype.$store.state.proj.projId != null;
}
