export default {
  SET_LANG (state, { value, $i18n, $vuetify }) {
    if (state.locales.includes(value)) {
      $i18n.locale = value
      $vuetify.lang.current = value

      state.locale = value
      console.log($vuetify.lang)
    }
  },
  SET_LOCALE_OPTIONS (state, localeOptions) {
    state.localeOptions = localeOptions
  }
}
