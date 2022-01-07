let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  create: ({ dictUuid, label, value, sort, remark }) => {
    const data = { dictUuid, label, value, sort, remark }
    return $axios({ url: '/upms/dict_item/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/dict_item/delete', method: 'post', data })
  },
  update: ({ uuid, dictUuid, label, value, sort, remark }) => {
    const data = { uuid, dictUuid, label, value, sort, remark }
    return $axios({ url: '/upms/dict_item/update', method: 'post', data })
  },
  list: (offset, limit, { dictUuid, label, value, remark }) => {
    const data = { limit, offset, dictUuid, label, value, remark }
    return $axios({ url: '/upms/dict_item/list', method: 'post', data })
  },
  findAllByForeignColumn: ({ dictUuid }) => {
    const data = { dictUuid }
    return $axios({ url: '/upms/dict_item/find_all/foreign', method: 'post', data })
  }
}
