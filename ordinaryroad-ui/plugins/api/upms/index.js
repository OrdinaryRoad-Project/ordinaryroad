import roleApis from './role'
import userApis from './user'

export default {
  initAxios (axios) {
    userApis.initAxios(axios)
    roleApis.initAxios(axios)
  },
  apis: {
    role: roleApis,
    user: userApis
  }
}
