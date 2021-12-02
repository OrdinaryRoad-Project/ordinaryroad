let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  register: ({ code, password, email, username }) => {
    const data = { code, password, email, username }
    return $axios({ url: '/upms/user/register', method: 'post', data })
  },
  list: (offset, limit, username) => {
    const data = { offset, limit, username }
    return $axios({ url: '/upms/user/list', method: 'post', data })
  },
  updateUsername: (username) => {
    const data = { username }
    return $axios({ url: '/upms/user/update/username', method: 'post', data })
  }
}
