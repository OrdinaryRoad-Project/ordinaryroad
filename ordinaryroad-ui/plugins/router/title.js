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
