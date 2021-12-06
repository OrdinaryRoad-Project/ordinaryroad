import roleApis from './role'
import userApis from './user'
import requestPathApis from './request_path'
import permissionApis from './permission'

export default {
  initAxios (axios) {
    userApis.initAxios(axios)
    roleApis.initAxios(axios)
    requestPathApis.initAxios(axios)
    permissionApis.initAxios(axios)
  },
  apis: {
    role: roleApis,
    user: userApis,
    request_path: requestPathApis,
    permission: permissionApis
  }
}
