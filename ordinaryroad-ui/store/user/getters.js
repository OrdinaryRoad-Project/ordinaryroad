export default {
  getTokenInfo: state => state.tokenInfo,
  getRememberMe: state => state.rememberMe,
  getUserInfo: state => state.userInfo,
  getUserRolesNameString: (state) => {
    if (!state.userInfo.roles) {
      return ''
    }
    return state.userInfo.roles.map(role => role.roleName).join('，')
  },
  getAvatarPath: (state) => {
    if (!state.userInfo.user.avatar) {
      return require('static/vuetify-logo.svg')
    }
    return '/api/upms/file/download' + state.userInfo.user.avatar
  }
}
