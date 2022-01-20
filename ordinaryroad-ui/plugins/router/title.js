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

export default ({ app, store }) => {
  const router = app.router
  // 每次路由变更时更新app bar标题
  router.beforeEach((to, from, next) => {
    const menuItems = store.getters['app/getMenuItems']
    const userMenuItems = store.getters['app/getUserMenuItems']
    const items = menuItems.concat(userMenuItems)
    let found = false
    let item = items.shift()
    while (item) {
      if (!found && item.to && item.to === to.path) {
        store.commit('app/SET_TITLE_KEY', item.titleKey)
        found = true
        break
      }
      if (item.children && item.children.length > 0) {
        items.unshift(...item.children)
      }
      item = items.shift()
    }
    if (!found) {
      store.commit('app/SET_TITLE_KEY', 'ordinaryroad')
    }
    next()
  })
}
