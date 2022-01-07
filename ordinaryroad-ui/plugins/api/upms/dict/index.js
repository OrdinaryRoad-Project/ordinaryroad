let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  create: ({ dictName, dictCode, remark }) => {
    const data = { dictName, dictCode, remark }
    return $axios({ url: '/upms/dict/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/dict/delete', method: 'post', data })
  },
  update: ({ uuid, dictName, dictCode, remark }) => {
    const data = { uuid, dictName, dictCode, remark }
    return $axios({ url: '/upms/dict/update', method: 'post', data })
  },
  findByUniqueColumn: ({ dictName, dictCode }) => {
    const data = { dictName, dictCode }
    return $axios({ url: '/upms/dict/find/unique', method: 'post', data })
  },
  list: (offset, limit, { dictName, dictCode, remark }) => {
    const data = { limit, offset, dictName, dictCode, remark }
    return $axios({ url: '/upms/dict/list', method: 'post', data })
  },
  findAll: ({ dictName, dictCode, remark }) => {
    const data = { dictName, dictCode, remark }
    return $axios({ url: '/upms/dict/find_all', method: 'post', data })
  }
}
