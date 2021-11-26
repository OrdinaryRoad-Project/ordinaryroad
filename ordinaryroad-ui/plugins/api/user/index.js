let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  apis: {
    login: ({ orNumber, username, email, password }) => {
      const data = { orNumber, username, email, password }
      return $axios({ url: '/login', method: 'post', data })
    },
    logout: () => {
      return $axios({ url: '/user/logout', method: 'post' })
    }
  }
}
