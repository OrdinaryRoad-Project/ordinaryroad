import en from 'vuetify/lib/locale/en'
import zhHans from 'vuetify/lib/locale/zh-Hans'

export default {
  // Target: https://go.nuxtjs.dev/config-target
  target: 'server',

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
  css: [
    '@/assets/css/ordinaryroad.css'
  ],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    // 权限插件
    '~/plugins/access/index.js',
    // api插件
    '@/plugins/api/index',
    // axios拦截器等
    '~/plugins/axios/index.js',
    // dayjs
    '~/plugins/dayjs/index.js',
    // 国际化插件
    '~/plugins/i18n/index.js',
    // 自定义常量 工具类等
    '~/plugins/ordinaryroad/index.js',
    // 路由插件
    '~/plugins/router/statistics/index.js',
    '~/plugins/router/title.js',
    { src: '~/plugins/router/access.js', mode: 'client' },
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
    '@nuxtjs/pwa',
    '@nuxtjs/dayjs'
  ],

  // Axios module configuration: https://go.nuxtjs.dev/config-axios
  axios: {
    prefix: '/api',
    // https://axios.nuxtjs.org/options/#proxy
    proxy: true // Can be also an object with default options
  },

  proxy: {
    '/api': {
      target: process.env.BASE_URL,
      pathRewrite: {
        '^/api': '/'
      }
    }
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
    customVariables: ['~/assets/variables.scss']
  },

  dayjs: {
    locales: ['en', 'zh-cn'],
    defaultLocale: 'zh-cn'
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {},

  // https://nuxtjs.org/docs/configuration-glossary/configuration-router
  router: {},

  // https://www.nuxtjs.cn/guide/runtime-config#runtime-config-213
  publicRuntimeConfig: {
    BASE_URL: process.env.BASE_URL
  },
  privateRuntimeConfig: {}
}
