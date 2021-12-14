export default {
  login ({ commit }, { params, $apis, $access, $store }) {
    // TODO 密码非对称加密
    return new Promise((resolve, reject) => {
      $apis.user.login(params)
        .then(({ data }) => {
          const tokenInfo = Object.assign({}, data)
          delete tokenInfo.userInfo
          commit('SET_TOKEN_INFO', tokenInfo)
          commit('SET_USER_INFO', data.userInfo)
          $store.commit('app/UPDATE_ACCESSIBLE_MENU_ITEMS', $access)
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
        $router.push({ path: '/user/login' })
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
  updateEmail ({ commit }, { email, $apis }) {
    return new Promise((resolve, reject) => {
      $apis.upms.user.updateEmail(email).then(() => {
        commit('UPDATE_USER_INFO_EMAIL', email)
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
