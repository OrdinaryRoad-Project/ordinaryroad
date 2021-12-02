export default {
  SET_LANG (state, { value, $i18n, $vuetify, $dayjs }) {
    if (state.locales.includes(value)) {
      $i18n.locale = value
      $vuetify.lang.current = value
      $dayjs.locale(value === 'zh-Hans' ? 'zh-cn' : value)

      state.locale = value
    }
  },
  SET_LOCALE_OPTIONS (state, localeOptions) {
    state.localeOptions = localeOptions
  }
}
