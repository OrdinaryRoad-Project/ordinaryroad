export default function (context) {
  const { store, route, redirect } = context
  const userInfo = store.getters['user/getUserInfo']
  if (!userInfo) {
    redirect({ path: '/user/login', query: { redirect: route.fullPath } })
  }
}
