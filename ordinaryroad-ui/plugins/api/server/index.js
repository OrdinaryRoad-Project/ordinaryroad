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

export default function ({ $axios, app }, inject) {
  // $apisServer
  inject('apisServer', {
    upms: {
      userInfo: (satoken) => {
        return $axios({
          url: '/upms/userinfo',
          method: 'post',
          // 默认是application/x-www-form-urlencoded;charset=UTF-8，接口不支持
          data: {},
          // 在server端调用的方法必须手动设置header，因为获取不到client的cookie
          headers: { satoken }
        })
      },
      user: {
        findByUniqueColumn: (satoken, { orNumber, email, username }) => {
          const data = { orNumber, email, username }
          return $axios({
            url: '/upms/user/find/unique',
            method: 'post',
            data,
            // 在server端调用的方法必须手动设置header，因为获取不到client的cookie
            headers: { satoken }
          })
        }
      },
      role: {
        findByUniqueColumn: (satoken, { roleCode, roleName }) => {
          const data = { roleCode, roleName }
          return $axios({
            url: '/upms/role/find/unique',
            method: 'post',
            data,
            // 在server端调用的方法必须手动设置header，因为获取不到client的cookie
            headers: { satoken }
          })
        }
      },
      dict: {
        findByUniqueColumn: (satoken, { dictName, dictCode }) => {
          const data = { dictName, dictCode }
          return $axios({
            url: '/upms/dict/find/unique',
            method: 'post',
            data,
            // 在server端调用的方法必须手动设置header，因为获取不到client的cookie
            headers: { satoken }
          })
        }
      }
    }
  })
}
