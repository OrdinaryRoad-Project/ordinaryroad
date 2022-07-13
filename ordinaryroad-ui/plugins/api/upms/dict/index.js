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
  create: ({ dictName, dictCode, remark }) => {
    const data = { dictName, dictCode, remark }
    return $axios({ url: '/upms/dict/create', method: 'post', data })
  },
  delete: (uuid) => {
    const data = { uuid }
    return $axios({ url: '/upms/dict/delete', method: 'post', data })
  },
  update: ({ uuid, dictName, dictCode, remark }) => {
    const data = { uuid, dictName, dictCode, remark }
    return $axios({ url: '/upms/dict/update', method: 'post', data })
  },
  findByUniqueColumn: ({ dictName, dictCode }) => {
    const data = { dictName, dictCode }
    return $axios({ url: '/upms/dict/find/unique', method: 'post', data })
  },
  list: (offset, limit, sortBy, sortDesc, { dictName, dictCode, remark }) => {
    const data = { offset, limit, sortBy, sortDesc, dictName, dictCode, remark }
    return $axios({ url: '/upms/dict/list', method: 'post', data })
  },
  findAll: ({ dictName, dictCode, remark }) => {
    const data = { dictName, dictCode, remark }
    return $axios({ url: '/upms/dict/find_all', method: 'post', data })
  }
}
