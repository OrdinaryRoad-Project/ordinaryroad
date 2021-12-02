export default {
  login ({ commit }, { params, $apis }) {
    // TODO 密码非对称加密
    return new Promise((resolve, reject) => {
      $apis.user.login(params)
        .then((value) => {
          commit('SET_TOKEN_INFO', value.data)
          commit('SET_USER_INFO', value.data.userInfo)
          resolve()
        }).catch((error) => {
          reject(error)
        })
    })
  },
  logout ({ commit }, { $apis, $router, $route }) {
    return new Promise((resolve, reject) => {
      $apis.user.logout().then(() => {
        commit('REMOVE_TOKEN_INFO')
        commit('REMOVE_USER_INFO')
        $router.push({ path: '/user/login', query: { redirect: $route.fullPath } })
        resolve()
      }).catch((error) => {
        reject(error)
      })
    })
  },
  updateUsername ({ commit }, { username, $apis }) {
    return new Promise((resolve, reject) => {
      $apis.upms.user.updateUsername(username).then(() => {
        commit('UPDATE_USER_INFO_USERNAME', username)
        resolve()
      }).catch((error) => {
        reject(error)
      })
    })
  },
  setRememberMe ({ commit }, rememberMe) {
    commit('SET_REMEMBER_ME', rememberMe)
  },
  setUserInfo ({ commit }, userInfo) {
    commit('SET_TOKEN_INFO', userInfo)
  }
}
