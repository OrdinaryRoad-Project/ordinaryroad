let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  create: ({ clientId, clientSecret, clientName, redirectUris, scopes }) => {
    const data = { clientId, clientSecret, clientName, redirectUris, scopes }
    return $axios({ url: '/auth/registered_client/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/auth/registered_client/delete', method: 'post', data })
  },
  update: ({ uuid, clientId, clientSecret, clientName, redirectUris, scopes }) => {
    const data = { uuid, clientId, clientSecret, clientName, redirectUris, scopes }
    return $axios({ url: '/auth/registered_client/update', method: 'post', data })
  },
  findByUniqueColumn: ({ clientId, clientName }) => {
    const data = { clientId, clientName }
    return $axios({ url: '/auth/registered_client/find/unique', method: 'post', data })
  },
  list: (offset, limit, { clientId, clientName, redirectUris, scopes }) => {
    const data = { limit, offset, clientId, clientName, redirectUris, scopes }
    return $axios({ url: '/auth/registered_client/list', method: 'post', data })
  },
  findAll: ({ clientId, clientName, redirectUris, scopes }) => {
    const data = { clientId, clientName, redirectUris, scopes }
    return $axios({ url: '/auth/registered_client/find_all', method: 'post', data })
  }
}
