export default ({ app, store }, inject) => {
  inject('access', {
    has: (permissionCode) => {
      const userInfo = store.getters['user/getUserInfo']
      let permissions = []
      if (userInfo && userInfo.permissions) {
        permissions = userInfo.permissions
      }
      return permissions.includes(permissionCode)
    }
  })
}
