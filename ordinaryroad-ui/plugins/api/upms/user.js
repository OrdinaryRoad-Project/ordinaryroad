let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  list: (offset, limit, username) => {
    const data = { offset, limit, username }
    return $axios({ url: '/upms/user/list', method: 'post', data })
  },
  login: (username, password, code, uuid) => {
    const data = { username, password, code, uuid }
    return $axios({ url: '/user/login', method: 'post', params: data })
  },
  logout: () => {
    return $axios({ url: '/user/logout', method: 'get' })
  }
}
