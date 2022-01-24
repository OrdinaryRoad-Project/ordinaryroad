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
  create: ({ clientId, clientSecret, clientName, redirectUris, scopes }) => {
    const data = { clientId, clientSecret, clientName, redirectUris, scopes }
    return $axios({ url: '/auth/registered_client/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/auth/registered_client/delete', method: 'post', data })
  },
  update: ({ uuid, clientId, clientSecret, clientName, redirectUris, scopes }) => {
    const data = { uuid, clientId, clientSecret, clientName, redirectUris, scopes }
    return $axios({ url: '/auth/registered_client/update', method: 'post', data })
  },
  findByUniqueColumn: ({ clientId, clientName }) => {
    const data = { clientId, clientName }
    return $axios({ url: '/auth/registered_client/find/unique', method: 'post', data })
  },
  list: (offset, limit, orderBy, orderByDesc, { clientId, clientName, redirectUris, scopes }) => {
    const data = { offset, limit, orderBy, orderByDesc, clientId, clientName, redirectUris, scopes }
    return $axios({ url: '/auth/registered_client/list', method: 'post', data })
  },
  findAll: ({ clientId, clientName, redirectUris, scopes }) => {
    const data = { clientId, clientName, redirectUris, scopes }
    return $axios({ url: '/auth/registered_client/find_all', method: 'post', data })
  }
}
