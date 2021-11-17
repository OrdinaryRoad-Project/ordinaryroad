import roleApis from './upms/role'
import userApis from './upms/user'

export default function ({ $axios, app }, inject) {
  // 初始化axios
  userApis.initAxios($axios)
  roleApis.initAxios($axios)
  // $apis
  inject('apis', {
    role: roleApis,
    user: userApis
  })
}
