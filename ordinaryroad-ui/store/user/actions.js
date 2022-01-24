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

export default {
  login ({ commit }, { params, $apis, $access, $store }) {
    // TODO 密码非对称加密
    return new Promise((resolve, reject) => {
      $apis.user.login(params)
        .then(({ data }) => {
          const tokenInfo = Object.assign({}, data)
          delete tokenInfo.userInfo
          commit('SET_TOKEN_INFO', tokenInfo)
          commit('SET_USER_INFO', data.userInfo)
          $store.commit('app/UPDATE_ACCESSIBLE_MENU_ITEMS', $access)
          resolve()
        }).catch((error) => {
          reject(error)
        })
    })
  },
  logout ({ commit }, { $apis, $router, $route }) {
    return new Promise((resolve, reject) => {
      $apis.user.logout().then(() => {
        commit('REMOVE_TOKEN_INFO')
        $router.push({ path: '/user/login' })
        resolve()
      }).catch((error) => {
        reject(error)
      })
    })
  },
  updateAvatar ({ commit }, { avatar, $apis }) {
    return new Promise((resolve, reject) => {
      $apis.upms.user.updateAvatar(avatar).then(() => {
        commit('UPDATE_USER_INFO_AVATAR', avatar)
        resolve()
      }).catch((error) => {
        reject(error)
      })
    })
  },
  updateUsername ({ commit }, { username, $apis }) {
    return new Promise((resolve, reject) => {
      $apis.upms.user.updateUsername(username).then(() => {
        commit('UPDATE_USER_INFO_USERNAME', username)
        resolve()
      }).catch((error) => {
        reject(error)
      })
    })
  },
  updateEmail ({ commit }, { email, $apis }) {
    return new Promise((resolve, reject) => {
      $apis.upms.user.updateEmail(email).then(() => {
        commit('UPDATE_USER_INFO_EMAIL', email)
        resolve()
      }).catch((error) => {
        reject(error)
      })
    })
  },
  setRememberMe ({ commit }, rememberMe) {
    commit('SET_REMEMBER_ME', rememberMe)
  },
  setUserInfo ({ commit }, userInfo) {
    commit('SET_TOKEN_INFO', userInfo)
  }
}
