let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  list: (offset, limit, username) => {
    const data = { offset, limit, username }
    return $axios({ url: '/upms/user/list', method: 'post', data })
  }
}
