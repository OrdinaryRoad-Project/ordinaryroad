<template>
  <v-row>
    <v-col
      md="8"
      cols="12"
      order="2"
      order-md="1"
    >
      <base-material-card
        icon="mdi-account"
        :title="$t('userMenuTitles.userProfile')"
      >
        <v-form
          ref="usernameForm"
        >
          <div class="d-flex align-center">
            <v-text-field
              v-model="usernameTextField.value"
              :rules="[$rules.required,$rules.max10Chars]"
              :loading="usernameTextField.loading"
              :disabled="usernameTextField.disabled"
              type="text"
              :label="$t('username')"
            />
            <v-btn class="ms-3" icon @click="usernameClick">
              <v-icon>mdi-{{ usernameTextField.disabled ? 'pencil' : 'check' }}</v-icon>
            </v-btn>
          </div>
        </v-form>

        <v-form
          ref="emailForm"
        >
          <div class="d-flex align-center">
            <v-text-field
              v-model="emailTextField.value"
              :rules="[$rules.required,$rules.max100Chars,$rules.email]"
              :loading="emailTextField.loading"
              :disabled="emailTextField.disabled"
              type="text"
              :label="$t('email')"
            />
            <v-btn class="ms-3" icon @click="emailClick">
              <v-icon>mdi-{{ emailTextField.disabled ? 'pencil' : 'check' }}</v-icon>
            </v-btn>
          </div>
        </v-form>
      </base-material-card>
    </v-col>
    <v-col
      md="4"
      cols="12"
      order="1"
      order-md="2"
    >
      <base-material-card
        :avatar="require('static/vuetify-logo.svg')"
      >
        <v-list two-line>
          <v-list-item>
            <v-list-item-icon>
              <v-icon color="primary">
                mdi-account
              </v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ userInfo.user.username }}</v-list-item-title>
              <v-list-item-subtitle>{{ userInfo.user.orNumber }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>

        <v-divider inset />

        <v-list>
          <v-list-item>
            <v-list-item-icon>
              <v-icon color="primary">
                mdi-email
              </v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ userInfo.user.email }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="primary">
                mdi-account-multiple
              </v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ userRolesNameString }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="primary">
                mdi-calendar-month
              </v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ $dayjs(userInfo.user.createdTime).format('YYYY-MM-DD HH:mm:ss') }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </base-material-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  data: () => ({
    usernameTextField: {
      value: '',
      disabled: true,
      loading: false
    },
    emailTextField: {
      value: '',
      disabled: true,
      loading: false
    }
  }),
  head () {
    return {
      title: this.$t('userMenuTitles.userProfile')
    }
  },
  computed: {
    ...mapGetters('user', {
      userInfo: 'getUserInfo',
      userRolesNameString: 'getUserRolesNameString'
    })
  },
  mounted () {
    this.usernameTextField.value = this.userInfo.user.username
    this.emailTextField.value = this.userInfo.user.email
  },
  methods: {
    ...mapActions('user', {
      updateUsername: 'updateUsername',
      updateEmail: 'updateEmail'
    }),

    usernameClick () {
      if (this.usernameTextField.disabled) {
        this.usernameTextField.disabled = false
      } else if (this.$refs.usernameForm.validate()) {
        if (this.usernameTextField.value === this.userInfo.user.username) {
          this.usernameTextField.disabled = true
        } else {
          this.usernameTextField.loading = true
          this.updateUsername({
            username: this.usernameTextField.value,
            $apis: this.$apis
          }).then(() => {
            this.usernameTextField.loading = false
            this.$snackbar.success(this.$t('whatUpdateSuccessfully', [this.$t('username')]))
            this.usernameTextField.disabled = true
          }).catch(() => {
            this.usernameTextField.loading = false
          })
        }
      }
    },

    emailClick () {
      if (this.emailTextField.disabled) {
        this.emailTextField.disabled = false
      } else if (this.$refs.emailForm.validate()) {
        if (this.emailTextField.value === this.userInfo.user.username) {
          this.emailTextField.disabled = true
        } else {
          this.emailTextField.loading = true
          this.updateEmail({
            username: this.emailTextField.value,
            $apis: this.$apis
          }).then(() => {
            this.emailTextField.loading = false
            this.$snackbar.success(this.$t('whatUpdateSuccessfully', [this.$t('email')]))
            this.emailTextField.disabled = true
          }).catch(() => {
            this.emailTextField.loading = false
          })
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
