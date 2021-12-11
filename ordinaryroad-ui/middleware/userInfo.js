export default async function (context) {
  const { store, app, route, redirect } = context
  const { $apis } = app
  const userInfo = store.getters['user/getUserInfo']
  if (!userInfo) {
    const tokenInfo = store.getters['user/getTokenInfo']
    if (tokenInfo) {
      const { data } = await $apis.upms.userInfo({ saToken: tokenInfo.satoken })
      store.commit('user/SET_USER_INFO', data)
    } else {
      redirect({ path: '/user/login', query: { redirect: route.fullPath } })
    }
  }
}
