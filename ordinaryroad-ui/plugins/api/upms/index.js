import roleApis from './role'
import userApis from './user'
import requestPathApis from './request_path'
import permissionApis from './permission'

let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
    userApis.initAxios(axios)
    roleApis.initAxios(axios)
    requestPathApis.initAxios(axios)
    permissionApis.initAxios(axios)
  },
  apis: {
    role: roleApis,
    user: userApis,
    request_path: requestPathApis,
    permission: permissionApis,
    userInfo: ({ saToken }) => {
      const data = { saToken }
      return $axios({
        url: '/upms/userinfo',
        method: 'post',
        data,
        // 在server端调用的方法必须手动设置header，因为获取不到client的cookie
        headers: {
          satoken: saToken,
          or_api_name: 'userInfo'
        }
      })
    }
  }
}
