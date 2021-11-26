let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  list: (offset, limit, roleName, roleCode) => {
    const data = { limit, offset, roleName, roleCode }
    return $axios({ url: '/upms/role/list', method: 'post', data })
  }
}
