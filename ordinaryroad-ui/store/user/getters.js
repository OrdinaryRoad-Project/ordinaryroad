export default {
  getTokenInfo: state => state.tokenInfo,
  getRememberMe: state => state.rememberMe,
  getUserInfo: state => state.userInfo,
  getUserRolesNameString: (state) => {
    let userRolesNameString = ''
    if (!state.userInfo.roles) {
      return userRolesNameString
    }
    state.userInfo.roles.forEach((role) => {
      userRolesNameString += role.roleName + '，'
    })
    return userRolesNameString.slice(0, userRolesNameString.length - 1)
  }
}
