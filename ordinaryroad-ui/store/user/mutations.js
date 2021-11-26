import Cookies from 'js-cookie'

export default {
  SET_TOKEN_INFO (state, tokenInfo) {
    if (tokenInfo) {
      Cookies.set('tokenInfo', JSON.stringify(tokenInfo))
    } else {
      Cookies.remove('tokenInfo')
    }
    state.tokenInfo = tokenInfo
  },
  SET_SATOKEN (state, satoken) {
    if (satoken) {
      Cookies.set('satoken', satoken)
    } else {
      Cookies.remove('satoken')
    }
    state.satoken = satoken
  }
}
