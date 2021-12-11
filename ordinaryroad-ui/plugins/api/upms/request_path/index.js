let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  create: ({ permissionUuid, path, pathName }) => {
    const data = { permissionUuid, path, pathName }
    return $axios({ url: '/upms/request_path/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/request_path/delete', method: 'post', data })
  },
  update: ({ uuid, permissionUuid, path, pathName }) => {
    const data = { uuid, permissionUuid: permissionUuid || '', path, pathName }
    return $axios({ url: '/upms/request_path/update', method: 'post', data })
  },
  list: (offset, limit, { path, pathName }) => {
    const data = { limit, offset, path, pathName }
    return $axios({ url: '/upms/request_path/list', method: 'post', data })
  }
}
