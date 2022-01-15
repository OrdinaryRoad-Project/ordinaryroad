import { removeTokenInfo, setRememberMe, setTokenInfo } from 'static/js/utils/cookie/vuex/user'

export default {
  REMOVE_TOKEN_INFO (state) {
    state.tokenInfo = null
    removeTokenInfo()
  },
  SET_TOKEN_INFO (state, tokenInfo) {
    state.tokenInfo = tokenInfo
    setTokenInfo(tokenInfo)
  },
  SET_REMEMBER_ME (state, rememberMe) {
    state.rememberMe = rememberMe
    setRememberMe(rememberMe)
  },
  REMOVE_USER_INFO (state) {
    state.userInfo = null
  },
  SET_USER_INFO (state, userInfo) {
    state.userInfo = userInfo
  },
  UPDATE_USER_INFO_AVATAR (state, avatar) {
    state.userInfo.user.avatar = avatar
  },
  UPDATE_USER_INFO_USERNAME (state, username) {
    state.userInfo.user.username = username
  },
  UPDATE_USER_INFO_EMAIL (state, email) {
    state.userInfo.user.email = email
  }
}
