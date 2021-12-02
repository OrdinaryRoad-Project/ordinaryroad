export default {
  setLang ({ commit }, { value, $i18n, $vuetify, $dayjs }) {
    commit('SET_LANG', { value, $i18n, $vuetify, $dayjs })
  },
  setLocaleOptions ({ commit }, localeOptions) {
    commit('SET_LOCALE_OPTIONS', localeOptions)
  }
}
