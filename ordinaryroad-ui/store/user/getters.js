import Cookies from 'js-cookie'

export default {
  getTokenInfo: state => ($store) => {
    if (!state.tokenInfo) {
      $store.commit('user/SET_TOKEN_INFO', Cookies.get('tokenInfo') ? JSON.parse(Cookies.get('tokenInfo')) : null)
    }
    return state.tokenInfo
  },
  getSatoken: state => ($store) => {
    if (!state.satoken) {
      $store.commit('user/SET_SATOKEN', Cookies.get('satoken'))
    }
    return state.satoken
  }
}
