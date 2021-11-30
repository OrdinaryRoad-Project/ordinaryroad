import { DRAWER_CLIPPED_KEY, DRAWER_MINI_VARIANT_KEY, SELECTED_THEME_OPTION_KEY } from 'static/js/utils/cookie/vuex/app'

function parseCookieString (string) {
  const cookie = {}
  const cookies = string.split('; ')
  // 遍历Cookie，取得需要的值
  cookies.forEach((e) => {
    const split = e.split('=')
    cookie[split[0]] = split[1]
  })
  return cookie
}

function getFromCookie (string, key) {
  return parseCookieString(string)[key]
}

function getBooleanFromCookie (string, key, defaultValue) {
  const fromCookie = getFromCookie(string, key)
  return fromCookie ? fromCookie === 'true' : defaultValue
}

function getNumberFromCookie (string, key, defaultValue) {
  const fromCookie = getFromCookie(string, key)
  return fromCookie ? Number(fromCookie) : defaultValue
}

export const actions = {
  nuxtServerInit ({ commit }, { $vuetify, req, app }) {
    const store = app.store
    // 初始化，可以获取初始值
    if (typeof req !== 'undefined' && req.headers && req.headers.cookie) {
      const cookieString = req.headers.cookie
      commit('app/SET_DRAWER_MINI_VARIANT', getBooleanFromCookie(cookieString, DRAWER_MINI_VARIANT_KEY, store.getters['app/getDrawerMiniVariant']))
      commit('app/SET_DRAWER_CLIPPED', getBooleanFromCookie(cookieString, DRAWER_CLIPPED_KEY, store.getters['app/getDrawerClipped']))
      commit('app/SET_SELECTED_THEME_OPTION', {
        value: getNumberFromCookie(cookieString, SELECTED_THEME_OPTION_KEY, store.getters['app/getSelectedThemeOption']),
        $vuetify
      })
    }
  }
}
