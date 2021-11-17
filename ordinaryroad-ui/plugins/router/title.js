export default ({ app, store }) => {
  const router = app.router
  // 每次路由变更时更新app bar标题
  router.afterEach((to, from, next) => {
    store.getters['app/getMenuItems'].forEach((item) => {
      if (item.to === to.path) {
        store.commit('app/SET_TITLE_KEY', item.titleKey)
      }
    })
  })
}
