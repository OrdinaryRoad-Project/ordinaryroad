import errorCode from './errorCode'

// Create a custom axios instance
export default function (context, inject) {
  // $dialog和$snackbar必须通过context.$xxx方式调用
  const { $axios, route, app } = context
  const { store, router } = app
  // 请求拦截器
  $axios.onRequest((config) => {
    // 将获取到token加入到请求头中
    const tokenInfo = store.getters['user/getTokenInfo']
    if (tokenInfo) {
      config.headers.common.satoken = tokenInfo.satoken
    }
  })

  // 响应拦截器
  $axios.interceptors.response.use((res) => {
    // 未设置状态码则默认成功状态
    const code = res.data.code || 200
    if (code === 200) {
      return res.data
    } else {
      // 获取错误信息
      const msg = errorCode[code] || res.data.msg || errorCode.default
      if (code === 2001) {
        store.commit('user/REMOVE_TOKEN_INFO')
        context.$dialog({
          persistent: true,
          title: '系统提示',
          content: '登录状态已过期，您可以继续留在该页面，或者重新登录。',
          confirmText: '重新登录'
        }).then((value) => {
          // 跳转登录页面
          router.push({ path: '/user/login', query: { redirect: route.fullPath } })
        })
      } else {
        context.$snackbar.error(msg)
      }
      return Promise.reject(msg)
    }
  },
  (error) => {
    console.log('err' + error)
    let { message } = error
    if (message === 'Network Error') {
      message = '后端接口连接异常'
    } else if (message.includes('timeout')) {
      message = '系统接口请求超时'
    } else if (message.includes('Request failed with status code')) {
      message = '系统接口' + message.substr(message.length - 3) + '异常'
    }
    context.$snackbar.error(message)
    return Promise.reject(error)
  }
  )
}
