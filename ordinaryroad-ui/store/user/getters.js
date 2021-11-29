import Cookies from 'js-cookie'

export default {
  getTokenInfo: state => ($store) => {
    if (!state.tokenInfo) {
      $store.commit('user/SET_TOKEN_INFO', Cookies.get('tokenInfo') ? JSON.parse(Cookies.get('tokenInfo')) : null)
      console.log('从cookie获取tokenInfo', state.tokenInfo)
    }
    return state.tokenInfo
  },
  getSatoken: state => ($store) => {
    if (!state.satoken) {
      $store.commit('user/SET_SATOKEN', Cookies.get('satoken'))
      console.log('从cookie获取satoken', state.satoken)
    }
    return state.satoken
  }
}
