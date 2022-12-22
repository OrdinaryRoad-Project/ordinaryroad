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
  create: ({ email, username, password }) => {
    const data = { email, username, password }
    return $axios({ url: '/upms/user/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/user/delete', method: 'post', data })
  },
  update: ({ uuid, password }) => {
    const data = { uuid, password }
    return $axios({ url: '/upms/user/update', method: 'post', data })
  },
  register: ({ code, password, email, username }) => {
    const data = { code, password, email, username }
    return $axios({ url: '/upms/user/register', method: 'post', data })
  },
  findByUniqueColumn: ({ orNumber, email, username }) => {
    const data = { orNumber, email, username }
    return $axios({ url: '/upms/user/find/unique', method: 'post', data })
  },
  findAllByForeignColumn: ({ roleUuid }) => {
    const data = { roleUuid }
    return $axios({ url: '/upms/user/find_all/foreign', method: 'post', data })
  },
  list: (offset, limit, sortBy, sortDesc, { email, orNumber, username }) => {
    const data = { offset, limit, sortBy, sortDesc, email, orNumber, username }
    return $axios({ url: '/upms/user/list', method: 'post', data })
  },
  updateAvatar: (avatar) => {
    const data = { avatar }
    return $axios({ url: '/upms/user/update/avatar', method: 'post', data })
  },
  updateUsername: (username) => {
    const data = { username }
    return $axios({ url: '/upms/user/update/username', method: 'post', data })
  },
  updateEmail: (email) => {
    const data = { email }
    return $axios({ url: '/upms/user/update/email', method: 'post', data })
  },
  updatePassword: (password, newPassword) => {
    const data = { password, newPassword }
    return $axios({ url: '/upms/user/update/password', method: 'post', data })
  },
  resetPassword: (uuid, password) => {
    const data = { uuid, password }
    return $axios({ url: '/upms/user/reset/password', method: 'post', data })
  },
  updateEnabled: (uuid, enabled) => {
    const data = { uuid, enabled }
    return $axios({ url: '/upms/user/update/enabled', method: 'post', data })
  },
  updateUserRoles: ({ uuid, roleUuids }) => {
    const data = { uuid, roleUuids }
    return $axios({ url: '/upms/user/update/user_roles', method: 'post', data })
  }
}
