export default {
  login ({ commit }, { params, $apis }) {
    return new Promise((resolve, reject) => {
      $apis.user.login(params)
        .then((value) => {
          commit('SET_TOKEN_INFO', value.data.data)
          commit('SET_SATOKEN', value.data.data.satoken)
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
