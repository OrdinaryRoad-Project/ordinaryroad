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
  },
  updateEmail: (email) => {
    const data = { email }
    return $axios({ url: '/upms/user/update/email', method: 'post', data })
  },
  updatePassword: (password, newPassword) => {
    const data = { password, newPassword }
    return $axios({ url: '/upms/user/update/password', method: 'post', data })
  }
}
