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
  logout ({ commit }) {

  }
}
