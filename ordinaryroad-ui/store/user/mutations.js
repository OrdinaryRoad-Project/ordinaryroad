import Cookies from 'js-cookie'

export default {
  SET_TOKEN_INFO (state, tokenInfo) {
    Cookies.set('tokenInfo', JSON.stringify(tokenInfo))
    state.tokenInfo = tokenInfo
  },
  SET_SATOKEN (state, satoken) {
    Cookies.set('satoken', satoken)
    state.satoken = satoken
  }
}
