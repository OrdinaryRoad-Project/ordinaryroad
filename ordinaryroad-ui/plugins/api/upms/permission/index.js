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
  create: ({ permissionCode, description }) => {
    const data = { permissionCode, description }
    return $axios({ url: '/upms/permission/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/permission/delete', method: 'post', data })
  },
  update: ({ uuid, permissionCode, description }) => {
    const data = { uuid, permissionCode, description }
    return $axios({ url: '/upms/permission/update', method: 'post', data })
  },
  list: (offset, limit, orderBy, orderByDesc, { permissionCode, description }) => {
    const data = { offset, limit, orderBy, orderByDesc, permissionCode, description }
    return $axios({ url: '/upms/permission/list', method: 'post', data })
  },
  findAll: ({ permissionCode, description }) => {
    const data = { permissionCode, description }
    return $axios({ url: '/upms/permission/find_all', method: 'post', data })
  },
  findAllByForeignColumn: ({ roleUuid, userUuid }) => {
    const data = { roleUuid, userUuid }
    return $axios({ url: '/upms/permission/find_all/foreign', method: 'post', data })
  }
}
