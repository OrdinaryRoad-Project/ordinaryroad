export default ({ app, store }) => {
  const { router, $access } = app
  // 每次路由变更前判断是否有访问权限
  router.beforeEach((to, from, next) => {
    const menuItems = store.getters['app/getMenuItems']
    const userMenuItems = store.getters['app/getUserMenuItems']
    const items = menuItems.concat(userMenuItems)

    let item
    for (let i = 0; i < items.length; i++) {
      const itemTemp = items[i]
      if (itemTemp.to === to.path) {
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
