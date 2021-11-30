import { DRAWER_MINI_VARIANT_KEY, SELECTED_THEME_OPTION_KEY } from 'static/js/utils/cookie/vuex/app'

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

function getBooleanFromCookie (string, key) {
  return getFromCookie(string, key) === 'true'
}

function getNumberFromCookie (string, key) {
  const fromCookie = getFromCookie(string, key)
  return fromCookie ? Number(fromCookie) : null
}

export const actions = {
  nuxtServerInit ({ commit }, { $vuetify, req }) {
    // 初始化
    if (typeof req !== 'undefined' && req.headers && req.headers.cookie) {
      const cookieString = req.headers.cookie
      commit('app/SET_DRAWER_MINI_VARIANT', getBooleanFromCookie(cookieString, DRAWER_MINI_VARIANT_KEY))
      commit('app/SET_SELECTED_THEME_OPTION', {
        value: getNumberFromCookie(cookieString, SELECTED_THEME_OPTION_KEY),
        $vuetify
      })
    }
  }
}
