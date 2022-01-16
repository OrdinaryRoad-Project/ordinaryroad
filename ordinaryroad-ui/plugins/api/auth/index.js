import openidApis from './openid'

let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
    openidApis.initAxios(axios)
  },
  apis: {
    openid: openidApis
  }
}
