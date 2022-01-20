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
  const { router, $access } = app
  // 每次路由变更前判断是否有访问权限
  router.beforeEach((to, from, next) => {
    const menuItems = store.getters['app/getMenuItems']
    const userMenuItems = store.getters['app/getUserMenuItems']
    // 不在侧边栏展示的menuItems
    const itemsNotInDrawer = [
      {
        to: '/upms/user/roles',
        name: 'upms-user-roles-orNumber',
        meta: {
          permission: 'upms:user:update:user_roles'
        }
      },
      {
        to: '/upms/role/users',
        name: 'upms-role-users-roleCode',
        meta: {
          permission: 'upms:role:update:role_users'
        }
      },
      {
        to: '/upms/role/permissions',
        name: 'upms-role-permissions-roleCode',
        meta: {
          permission: 'upms:role:update:role_permissions'
        }
      }
    ]
    const items = menuItems.concat(userMenuItems).concat(itemsNotInDrawer)
    let item
    for (let i = 0; i < items.length; i++) {
      const itemTemp = items[i]
      if (itemTemp.to === to.path || itemTemp.name === to.name) {
        item = itemTemp
        break
      }
    }
    if (item && item.meta && item.meta.permission) {
      // 判断权限
      if (!$access.has(item.meta.permission)) {
        if (from.path === '/user/login') {
          next('/')
        } else {
          next(false)
        }
      } else {
        next()
      }
    } else {
      next()
    }
  })
}
