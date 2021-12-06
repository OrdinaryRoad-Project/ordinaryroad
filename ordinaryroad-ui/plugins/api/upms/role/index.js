let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  create: ({ roleName, roleCode }) => {
    const data = { roleName, roleCode }
    return $axios({ url: '/upms/role/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/role/delete', method: 'post', data })
  },
  update: ({ uuid, roleName, roleCode }) => {
    const data = { uuid, roleName, roleCode }
    return $axios({ url: '/upms/role/update', method: 'post', data })
  },
  list: (offset, limit, { roleName, roleCode }) => {
    const data = { limit, offset, roleName, roleCode }
    return $axios({ url: '/upms/role/list', method: 'post', data })
  }
}
