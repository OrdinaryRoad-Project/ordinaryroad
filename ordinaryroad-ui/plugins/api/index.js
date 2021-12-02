import upmsApis from './upms/index'
import userApis from './user/index'

export default function ({ $axios, app }, inject) {
  // 初始化axios
  upmsApis.initAxios($axios)
  userApis.initAxios($axios)
  const $apis = {
    user: userApis.apis,
    upms: upmsApis.apis
  }
  // $apis
  inject('apis', $apis)
}
