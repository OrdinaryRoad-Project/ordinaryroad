import roleApis from './role'
import userApis from './user'
import requestPathApis from './request_path'

export default {
  initAxios (axios) {
    userApis.initAxios(axios)
    roleApis.initAxios(axios)
    requestPathApis.initAxios(axios)
  },
  apis: {
    role: roleApis,
    user: userApis,
    request_path: requestPathApis
  }
}
