export default {
  getTokenInfo: state => state.tokenInfo,
  getRememberMe: state => state.rememberMe,
  getUserInfo: state => state.userInfo,
  getUserRolesNameString: (state) => {
    if (!state.userInfo.roles) {
      return ''
    }
    return state.userInfo.roles.map(role => role.roleName).join('，')
  }
}
