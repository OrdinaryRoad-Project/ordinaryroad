export default {
  setLang ({ commit }, values) {
    commit('SET_LANG', values)
  },
  setLocaleOptions ({ commit }, localeOptions) {
    commit('SET_LOCALE_OPTIONS', localeOptions)
  }
}
