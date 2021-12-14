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
