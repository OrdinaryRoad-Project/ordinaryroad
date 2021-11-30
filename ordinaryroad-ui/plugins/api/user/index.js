let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  apis: {
    login: ({ rememberMe, captchaId, code, orNumber, username, email, password }) => {
      const data = { rememberMe, captchaId, code, orNumber, username, email, password }
      return $axios({ url: '/login', method: 'post', data })
    },
    logout: () => {
      return $axios({ url: '/logout', method: 'get' })
    },
    getLoginCaptcha: (orNumber) => {
      const params = { orNumber }
      return $axios({ url: '/captcha/login', method: 'get', params })
    },
    getRegisterCaptcha: (email) => {
      const params = { email }
      return $axios({ url: '/captcha/register', method: 'get', params })
    }
  }
}
