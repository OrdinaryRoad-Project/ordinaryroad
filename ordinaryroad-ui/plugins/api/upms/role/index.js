let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  create: ({ roleName, roleCode }) => {
    const data = { roleName, roleCode }
    return $axios({ url: '/upms/role/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/role/delete', method: 'post', data })
  },
  update: ({ uuid, roleName, roleCode }) => {
    const data = { uuid, roleName, roleCode }
    return $axios({ url: '/upms/role/update', method: 'post', data })
  },
  findByUniqueColumn: ({ roleCode, roleName }) => {
    const data = { roleCode, roleName }
    return $axios({ url: '/upms/role/find/unique', method: 'post', data })
  },
  list: (offset, limit, { roleName, roleCode }) => {
    const data = { limit, offset, roleName, roleCode }
    return $axios({ url: '/upms/role/list', method: 'post', data })
  },
  findAll: ({ roleCode, roleName }) => {
    const data = { roleCode, roleName }
    return $axios({ url: '/upms/role/find_all', method: 'post', data })
  },
  findAllByUserUuid: (userUuid) => {
    const data = { userUuid }
    return $axios({ url: '/upms/role/find_all/user_uuid', method: 'post', data })
  },
  updateUserRoles: ({ userUuid, roleUuids }) => {
    const data = { userUuid, roleUuids }
    return $axios({ url: '/upms/role/update/user_roles', method: 'post', data })
  },
  updateRoleUsers: ({ roleUuid, userUuids }) => {
    const data = { roleUuid, userUuids }
    return $axios({ url: '/upms/role/update/role_users', method: 'post', data })
  }
}
