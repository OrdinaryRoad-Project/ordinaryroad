let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  create: ({ permissionCode, description }) => {
    const data = { permissionCode, description }
    return $axios({ url: '/upms/permission/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/permission/delete', method: 'post', data })
  },
  update: ({ uuid, permissionCode, description }) => {
    const data = { uuid, permissionCode, description }
    return $axios({ url: '/upms/permission/update', method: 'post', data })
  },
  list: (offset, limit, { permissionCode, description }) => {
    const data = { limit, offset, permissionCode, description }
    return $axios({ url: '/upms/permission/list', method: 'post', data })
  },
  findAll: ({ permissionCode, description }) => {
    const data = { permissionCode, description }
    return $axios({ url: '/upms/permission/find_all', method: 'post', data })
  },
  findAllByForeignColumn: ({ roleUuid, userUuid }) => {
    const data = { roleUuid, userUuid }
    return $axios({ url: '/upms/permission/find_all/foreign', method: 'post', data })
  }
}
