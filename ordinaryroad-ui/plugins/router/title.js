export default ({ app, store }) => {
  const router = app.router
  // 每次路由变更时更新app bar标题
  router.afterEach((to, from, next) => {
    const menuItems = store.getters['app/getMenuItems']
    const userMenuItems = store.getters['app/getUserMenuItems']
    const items = menuItems.concat(userMenuItems)
    let found = false
    items.forEach((item) => {
      if (!found && item.to === to.path) {
        store.commit('app/SET_TITLE_KEY', item.titleKey)
        found = true
      }
    })
    if (!found) {
      store.commit('app/SET_TITLE_KEY', 'ordinaryroad')
    }
  })
}
