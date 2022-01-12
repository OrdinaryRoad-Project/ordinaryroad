<template>
  <v-img
    class="align-center"
    height="100vh"
    src="https://api.dujin.org/bing/1920.php"
  >
    <base-material-card
      style="opacity:0.85"
      width="400"
    >
      <template #heading>
        <div class="text-center">
          <h2 class="font-weight-bold mb-2">
            {{ $t('login') }}
          </h2>
          <v-btn icon>
            <v-icon>
              mdi-github
            </v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon>
              mdi-qqchat
            </v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon>
              mdi-wechat
            </v-icon>
          </v-btn>
        </div>
      </template>

      <template #default>
        <div class="text-center">
          <div>{{ $t('orUseORAccount') }}{{ $t('login') }}</div>
          <div>
            还没有账号？
            <nuxt-link to="/user/register">
              立即创建
            </nuxt-link>
          </div>
        </div>

        <v-form class="mt-10">
          <v-text-field
            v-model="orNumber"
            messages="10001"
            :label="$t('orNumber')"
            prepend-icon="mdi-account"
            type="text"
          />
          <v-text-field
            v-model="password"
            messages="Abc123"
            :label="$t('password')"
            prepend-icon="mdi-lock"
            type="password"
          />
          <v-text-field
            v-model="code"
            hide-details
            maxlength="5"
            :label="$t('captcha')"
            prepend-icon="mdi-lock"
            @keydown="codeKeydown"
          >
            <template #append-outer>
              <v-sheet
                v-ripple
                outlined
                rounded
                style="width:90px;height:45px;cursor: pointer"
              >
                <v-img
                  class="mx-auto"
                  contain
                  :height="45"
                  :width="80"
                  :src="src"
                  @click="getCaptchaImage"
                >
                  <template #placeholder>
                    <v-row
                      class="fill-height ma-0"
                      align="center"
                      justify="center"
                    >
                      <v-progress-circular
                        indeterminate
                        color="primary"
                      />
                    </v-row>
                  </template>
                </v-img>
              </v-sheet>
            </template>
          </v-text-field>
          <v-checkbox
            v-model="rememberMeModel"
            label="记住我"
            hide-details="auto"
            :messages="rememberMeModel?'不要在非自己设备上勾选哦':null"
          />
          <div class="text-center mt-10">
            <v-btn
              x-large
              rounded
              :outlined="loading"
              :depressed="loading"
              :loading="loading"
              color="primary"
              @click="login"
            >
              {{ $t('login') }}
            </v-btn>
          </div>
        </v-form>
      </template>
    </base-material-card>
  </v-img>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  layout: 'empty',
  asyncData (context) {
    const { store, route, redirect } = context
    const redirectPath = route.query.redirect || '/'
    const userInfo = store.getters['user/getUserInfo']
    const tokenInfo = store.getters['user/getTokenInfo']
    if (tokenInfo && userInfo) {
      redirect(redirectPath)
    }
    return { redirect: redirectPath }
  },
  data () {
    return {
      loading: false,
      orNumber: '10001',
      username: '',
      password: 'Abc123',
      code: '',
      captchaId: '',
      src: '',

      redirect: undefined
    }
  },
  computed: {
    ...mapGetters('user', {
      rememberMe: 'getRememberMe'
    }),

    rememberMeModel: {
      get () {
        return this.rememberMe
      },
      set (val) {
        this.setRememberMe(val)
      }
    }
  },
  created () {
    this.getCaptchaImage()
  },
  mounted () {
  },
  methods: {
    ...mapActions('user', {
      setRememberMe: 'setRememberMe'
    }),
    login () {
      if (this.orNumber.length === 0 || this.password.length === 0) {
        alert('请补全输入')
      } else {
        this.loading = true
        this.$store.dispatch('user/login', {
          params: {
            username: this.username,
            orNumber: this.orNumber,
            password: this.password,
            captchaId: this.captchaId,
            code: this.code,
            rememberMe: this.rememberMe
          },
          $apis: this.$apis,
          $access: this.$access,
          $store: this.$store
        }).then(() => {
          this.loading = false
          this.$router.replace({ path: this.redirect })
        }).catch(() => {
          this.loading = false
        })
      }
    },
    getCaptchaImage () {
      this.$apis.user.getLoginCaptcha()
        .then((value) => {
          this.code = ''
          const data = value.data
          this.captchaId = data.captchaId
          this.src = 'data:image/jpeg;base64,' + data.img
        })
    },
    codeKeydown (res) {
      if (res.which === 13) {
        this.login()
      }
    }
  }
}
</script>

<style scoped>

</style>
