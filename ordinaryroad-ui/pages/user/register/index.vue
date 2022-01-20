<!--
  - MIT License
  -
  - Copyright (c) 2021 苗锦洲
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy
  - of this software and associated documentation files (the "Software"), to deal
  - in the Software without restriction, including without limitation the rights
  - to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  - copies of the Software, and to permit persons to whom the Software is
  - furnished to do so, subject to the following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all
  - copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  - IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  - FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  - AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  - LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  - OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  - SOFTWARE.
  -->

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
            {{ $t('register') }}
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
          {{ $t('orUseORAccount') }}
          <nuxt-link to="/user/login">
            {{ $t('login') }}
          </nuxt-link>
        </div>

        <v-form ref="form" class="mt-10">
          <v-text-field
            v-model="email"
            :counter="email&&email.length>80"
            :rules="[$rules.required,$rules.max100Chars,$rules.email]"
            :label="$t('email')"
            prepend-icon="mdi-email"
            type="text"
          />
          <v-text-field
            v-model="password"
            :counter="password&&password.length>10"
            :rules="[$rules.required,$rules.min6Chars,$rules.max16Chars,$rules.password]"
            :label="$t('password')"
            prepend-icon="mdi-lock"
            type="password"
          />
          <v-text-field
            v-model="code"
            :label="$t('captcha')"
            maxlength="6"
            :error-messages="codeErrorMsg"
            prepend-icon="mdi-lock"
            @change="codeChange"
            @keydown="codeKeydown"
          >
            <template #append-outer>
              <v-btn
                rounded
                :loading="codeLoading"
                depressed
                :height="40"
                :disabled="codeWaitingSeconds !== 0"
                @click="getCaptchaCode"
              >
                {{ codeWaitingSeconds === 0 ? $t('getCaptcha') : codeWaitingSeconds }}
              </v-btn>
            </template>
          </v-text-field>
          <div class="text-center mt-10">
            <v-btn
              :outlined="loading"
              :depressed="loading"
              x-large
              rounded
              :loading="loading"
              color="primary"
              @click="register"
            >
              {{ $t('register') }}
            </v-btn>
          </div>
        </v-form>
      </template>
    </base-material-card>
  </v-img>
</template>

<script>
export default {
  layout: 'empty',
  data () {
    return {
      loading: false,
      code: '',
      password: '',
      email: '',

      codeErrorMsg: null,
      codeLoading: false,
      codeWaitingSeconds: 0,
      codeCountDownTimer: null
    }
  },
  mounted () {
  },
  beforeDestroy () {
    clearInterval(this.codeCountDownTimer)
  },
  methods: {
    register () {
      if (this.$refs.form.validate()) {
        if (this.code && this.code.length === 6) {
          this.loading = true
          this.$apis.upms.user.register({
            code: this.code,
            password: this.password,
            email: this.email
          }).then((value) => {
            this.loading = false
            this.$dialog({
              persistent: true,
              title: this.$t('registrationSuccessDialog.title'),
              content: this.$t('registrationSuccessDialog.content', [value.data.orNumber]),
              confirmText: this.$t('registrationSuccessDialog.confirmText')
            }).then((dialog) => {
              dialog.cancel()
              this.$router.push({ path: '/user/login' })
            })
          }).catch(() => {
            this.loading = false
          })
        } else {
          this.codeErrorMsg = this.$t('captchaNotValidMsg')
        }
      }
    },
    getCaptchaCode () {
      if (this.$refs.form.validate()) {
        this.codeLoading = true
        this.$apis.user.getRegisterCaptcha(this.email)
          .then(() => {
            this.codeLoading = false
            this.codeCountDownTimer = setInterval(this.countDown, 1000)
            this.codeWaitingSeconds = 60
            this.code = ''
            this.$snackbar.success(this.$t('captchaSendSuccess', [this.email]))
          })
          .catch(() => {
            this.codeLoading = false
          })
      }
    },
    codeKeydown (res) {
      if (res.which === 13) {
        this.login()
      }
    },
    codeChange () {
      this.codeErrorMsg = this.code && this.code.length === 6 ? null : this.$t('captchaNotValidMsg')
    },
    countDown () {
      if (this.codeWaitingSeconds === 0) {
        clearInterval(this.codeCountDownTimer)
      } else {
        this.codeWaitingSeconds--
      }
    }
  }
}
</script>

<style scoped>

</style>
