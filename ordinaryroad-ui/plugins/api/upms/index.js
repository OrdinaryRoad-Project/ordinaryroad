import roleApis from './role'
import userApis from './user'
import requestPathApis from './request_path'
import permissionApis from './permission'
import dictApis from './dict'
import dictItemApis from './dict_item'
import fileApis from './file'

let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
    userApis.initAxios(axios)
    roleApis.initAxios(axios)
    requestPathApis.initAxios(axios)
    permissionApis.initAxios(axios)
    dictApis.initAxios(axios)
    dictItemApis.initAxios(axios)
    fileApis.initAxios(axios)
  },
  apis: {
    role: roleApis,
    user: userApis,
    request_path: requestPathApis,
    permission: permissionApis,
    dict: dictApis,
    dict_item: dictItemApis,
    file: fileApis,
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
