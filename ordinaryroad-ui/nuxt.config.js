import colors from 'vuetify/es5/util/colors'
import en from 'vuetify/lib/locale/en'
import zhHans from 'vuetify/lib/locale/zh-Hans'

export default {
  // Target: https://go.nuxtjs.dev/config-target
  target: 'static',

  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    titleTemplate: '%s - OrdinaryRoad',
    title: 'OrdinaryRoad',
    htmlAttrs: {
      lang: 'zh-Hans'
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    // api插件
    '@/plugins/api/index',
    // axios拦截器等
    '~/plugins/axios/index.js',
    // 国际化插件
    '~/plugins/i18n/index.js',
    // 自定义常量 工具类等
    '~/plugins/ordinaryroad/index.js',
    // 路由插件
    '~/plugins/router/statistics/index.js',
    '~/plugins/router/title.js',
    // vuetify client mode
    { src: '~/plugins/vuetify/index.js', mode: 'client' }
  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    // https://go.nuxtjs.dev/eslint
    '@nuxtjs/eslint-module',
    // https://go.nuxtjs.dev/vuetify
    '@nuxtjs/vuetify'
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    // https://go.nuxtjs.dev/pwa
    '@nuxtjs/pwa'
  ],

  // Axios module configuration: https://go.nuxtjs.dev/config-axios
  axios: {
    baseURL: 'http://localhost:9090',
    withCredentials: true,
    timeout: 10000
  },

  // PWA module configuration: https://go.nuxtjs.dev/pwa
  pwa: {
    manifest: {
      lang: 'zh-Hans'
    }
  },

  // Vuetify module configuration: https://go.nuxtjs.dev/config-vuetify
  vuetify: {
    lang: {
      locales: { en, 'zh-Hans': zhHans },
      current: 'zh-Hans'
    },
    customVariables: ['~/assets/variables.scss'],
    theme: {
      themes: {
        dark: {
          primary: colors.blue.darken2,
          accent:
          colors.grey.darken3,
          secondary:
          colors.amber.darken3,
          info:
          colors.teal.lighten1,
          warning:
          colors.amber.base,
          error:
          colors.deepOrange.accent4,
          success:
          colors.green.accent3
        }
      }
    }
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {}
}
