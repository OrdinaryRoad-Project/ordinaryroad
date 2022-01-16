import openidApis from './openid'
import registeredClientApis from './registered_client'

let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
    openidApis.initAxios(axios)
    registeredClientApis.initAxios(axios)
  },
  apis: {
    openid: openidApis,
    registered_client: registeredClientApis
  }
}
