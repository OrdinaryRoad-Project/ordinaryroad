/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
  updateRoleUsers: ({ roleUuid, userUuids }) => {
    const data = { roleUuid, userUuids }
    return $axios({ url: '/upms/role/update/role_users', method: 'post', data })
  },
  updateRolePermissions: ({ roleUuid, permissionUuids }) => {
    const data = { roleUuid, permissionUuids }
    return $axios({ url: '/upms/role/update/role_permissions', method: 'post', data })
  }
}
