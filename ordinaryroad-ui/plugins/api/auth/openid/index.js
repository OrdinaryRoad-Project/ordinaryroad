let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  create: ({ orNumber, clientId, openid }) => {
    const data = { orNumber, clientId, openid }
    return $axios({ url: '/auth/openid/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/auth/openid/delete', method: 'post', data })
  },
  update: ({ uuid, orNumber, clientId, openid }) => {
    const data = { uuid, orNumber, clientId, openid }
    return $axios({ url: '/auth/openid/update', method: 'post', data })
  },
  findByClientIdAndOrNumber: ({ clientId, orNumber }) => {
    const data = { clientId, orNumber }
    return $axios({ url: '/auth/openid/find/clientIdAndOrNumber', method: 'post', data })
  },
  findByClientIdAndOpenid: ({ clientId, openid }) => {
    const data = { clientId, openid }
    return $axios({ url: '/auth/openid/find/clientIdAndOpenid', method: 'post', data })
  },
  list: (offset, limit, { orNumber, clientId, openid }) => {
    const data = { limit, offset, orNumber, clientId, openid }
    return $axios({ url: '/auth/openid/list', method: 'post', data })
  },
  findAll: ({ orNumber, clientId, openid }) => {
    const data = { orNumber, clientId, openid }
    return $axios({ url: '/auth/openid/find_all', method: 'post', data })
  }
}
