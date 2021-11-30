export default {
  login ({ commit }, { params, $apis }) {
    // TODO 密码非对称加密
    return new Promise((resolve, reject) => {
      $apis.user.login(params)
        .then((value) => {
          commit('SET_TOKEN_INFO', value.data)
          commit('SET_SATOKEN', value.data.satoken)
          resolve()
        }).catch((error) => {
          reject(error)
        })
    })
  },
  logout ({ commit }, { $apis, $router, $route }) {
    return new Promise((resolve, reject) => {
      $apis.user.logout().then(() => {
        commit('SET_TOKEN_INFO', null)
        commit('SET_SATOKEN', null)
        $router.push({ path: '/user/login', query: { redirect: $route.fullPath } })
        resolve()
      }).catch((error) => {
        reject(error)
      })
    })
  }
}
