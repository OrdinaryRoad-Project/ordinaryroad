import errorCode from './errorCode'

// Create a custom axios instance
export default function ({ $axios, route, app }, inject) {
  const token = 'token'
  const { store, router } = app
  // 请求拦截器
  $axios.onRequest((config) => {
    // 将获取到token加入到请求头中
    config.headers.common.Authorization = token
  })

  // 响应拦截器
  $axios.interceptors.response.use((res) => {
    // 未设置状态码则默认成功状态
    const code = res.data.errorCode || 200
    // 获取错误信息
    const msg = errorCode[code] || res.data.errorMsg || errorCode.default
    if (code === 2001) {
      alert('登录状态已过期，即将跳转登录页面')
      store.dispatch('user/LogOut').then(() => {
        router.push({ path: '/dashboard/user/login', query: { redirect: route.fullPath } })
      })
      /**
         MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }
         ).then(() => {
        store.dispatch('LogOut').then(() => {
          location.href = '/index';
        })
      })
         **/
      return Promise.reject(new Error(msg))
    } else if (code === 500) {
      alert(msg)
      /**
         Message({
        message: msg,
        type: 'error'
      })
         **/
      return Promise.reject(new Error(msg))
    } else if (code !== 200) {
      alert(msg)
      /**
         Notification.error({
        title: msg
      })
         **/
      // return Promise.reject('error')
      return res.data
    } else {
      return res.data
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
    alert(message)
    /**
       Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
       **/
    return Promise.reject(error)
  })
}
