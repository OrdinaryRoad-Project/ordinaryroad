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

import upmsApis from './upms/index'
import userApis from './user/index'
import authApis from './auth/index'

export default function ({ $axios, app }, inject) {
  // 初始化axios
  upmsApis.initAxios($axios)
  userApis.initAxios($axios)
  authApis.initAxios($axios)
  const $apis = {
    user: userApis.apis,
    upms: upmsApis.apis,
    auth: authApis.apis,
    statusColor (item) {
      const type = item.type
      if (type >= 100 && type <= 199) {
        return 'info'
      }
      if (type >= 200 && type <= 299) {
        return 'success'
      }
      if (type >= 300 && type <= 399) {
        return 'warning'
      }
      if (type >= 400 && type <= 499) {
        return 'error'
      }
      if (type >= 500 && type <= 599) {
        return 'error'
      }
      return ''
    }
  }
  // $apis
  inject('apis', $apis)
}
