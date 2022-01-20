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
