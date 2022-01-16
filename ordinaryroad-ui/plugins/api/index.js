import upmsApis from './upms/index'
import userApis from './user/index'
import authApis from './auth/index'

export default function ({ $axios, app }, inject) {
  // 初始化axios
  upmsApis.initAxios($axios)
  userApis.initAxios($axios)
  authApis.initAxios($axios)
  const $apis = {
    user: userApis.apis,
    upms: upmsApis.apis,
    auth: authApis.apis
  }
  // $apis
  inject('apis', $apis)
}
