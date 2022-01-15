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
        <v-tabs v-model="tab">
          <v-tab>{{ $t('basicInformation') }}</v-tab>
          <v-tab>{{ $t('changePassword') }}</v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab" class="mt-5">
          <v-tab-item>
            <v-text-field
              v-model="userInfo.user.orNumber"
              :disabled="true"
              type="text"
              :label="$t('orNumber')"
            />

            <v-form
              ref="usernameForm"
            >
              <div class="d-flex align-center">
                <v-text-field
                  v-model="usernameTextField.input"
                  :rules="[$rules.required,$rules.max10Chars]"
                  :loading="usernameTextField.loading"
                  :disabled="usernameTextField.disabled"
                  type="text"
                  :label="$t('username')"
                />
                <v-btn class="ms-3" icon @click="usernameClick">
                  <v-icon>
                    mdi-{{
                      usernameTextField.disabled ? 'pencil'
                      : usernameTextField.input === usernameTextField.value ? 'close'
                        : 'check'
                    }}
                  </v-icon>
                </v-btn>
              </div>
            </v-form>

            <v-form
              v-if="false"
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

            <!-- TODO 修改邮箱需要验证-->
            <v-text-field
              v-model="userInfo.user.email"
              :disabled="true"
              type="text"
              :label="$t('email')"
            />
          </v-tab-item>
          <v-tab-item>
            <v-form ref="passwordForm">
              <v-text-field
                v-model="passwordForm.oldPassword.value"
                clearable
                :append-icon="passwordForm.oldPassword.show ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[$rules.required,$rules.min6Chars,$rules.max16Chars,$rules.password]"
                :type="passwordForm.oldPassword.show ? 'text' : 'password'"
                :label="$t('oldPassword')"
                @click:append="passwordForm.oldPassword.show=!passwordForm.oldPassword.show"
              />
              <v-text-field
                v-model="passwordForm.newPassword.value"
                clearable
                :label="$t('newPassword')"
                :rules="[$rules.required,$rules.min6Chars,$rules.max16Chars,$rules.password]"
                :append-icon="passwordForm.newPassword.show ? 'mdi-eye' : 'mdi-eye-off'"
                :type="passwordForm.newPassword.show ? 'text' : 'password'"
                @click:append="passwordForm.newPassword.show = !passwordForm.newPassword.show"
              />
              <v-text-field
                v-model="passwordForm.confirmPassword.value"
                clearable
                :rules="[$rules.required,$rules.min6Chars,$rules.max16Chars,$rules.password]"
                :error-messages="passwordForm.confirmPassword.errorMessageKey?$t(passwordForm.confirmPassword.errorMessageKey):null"
                :label="$t('confirmPassword')"
                :append-icon="passwordForm.confirmPassword.show ? 'mdi-eye' : 'mdi-eye-off'"
                :type="passwordForm.confirmPassword.show ? 'text' : 'password'"
                @click:append="passwordForm.confirmPassword.show = !passwordForm.confirmPassword.show"
              />
              <v-row class="ma-0">
                <v-spacer />
                <v-btn
                  :loading="passwordForm.loading"
                  color="primary"
                  @click="updatePassword"
                >
                  {{ $t('update') }}
                </v-btn>
              </v-row>
            </v-form>
          </v-tab-item>
        </v-tabs-items>
      </base-material-card>
    </v-col>
    <v-col
      md="4"
      cols="12"
      order="1"
      order-md="2"
    >
      <base-material-card
        :avatar="avatarPath"
      >
        <template #avatar>
          <v-hover>
            <template #default="{ hover }">
              <v-avatar
                size="128"
                class="v-card--material__avatar elevation-6"
                color="grey"
              >
                <v-img :src="avatarPath">
                  <template #default>
                    <v-fade-transition>
                      <v-overlay
                        v-if="avatarOptions.uploading"
                        absolute
                      >
                        <v-progress-circular indeterminate />
                      </v-overlay>
                    </v-fade-transition>
                  </template>
                  <template #placeholder>
                    <v-skeleton-loader type="image" />
                  </template>
                </v-img>
                <v-fade-transition>
                  <v-overlay
                    v-if="!avatarOptions.uploading&&hover"
                    v-ripple
                    absolute
                    style="cursor: pointer"
                    @click="$refs.fileInput.$refs.input.click()"
                  >
                    <v-file-input
                      v-show="false"
                      ref="fileInput"
                      accept="image/*"
                      @change="onAvatarSelect"
                    />
                    <v-row
                      class="fill-height ma-0"
                      align="center"
                      justify="center"
                    >
                      <v-icon>mdi-pencil</v-icon>
                    </v-row>
                  </v-overlay>
                </v-fade-transition>
              </v-avatar>
            </template>
          </v-hover>
        </template>

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
    tab: null,
    avatarOptions: {
      uploading: false
    },
    usernameTextField: {
      value: '',
      input: '',
      disabled: true,
      loading: false
    },
    emailTextField: {
      value: '',
      disabled: true,
      loading: false
    },

    passwordForm: {
      loading: false,
      oldPassword: {
        value: null,
        show: false
      },
      newPassword: {
        value: null,
        show: false
      },
      confirmPassword: {
        value: null,
        show: false,
        errorMessageKey: null
      }
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
      userRolesNameString: 'getUserRolesNameString',
      avatarPath: 'getAvatarPath'
    })
  },
  mounted () {
    this.usernameTextField.value = this.userInfo.user.username
    this.usernameTextField.input = this.userInfo.user.username
    this.emailTextField.value = this.userInfo.user.email
  },
  methods: {
    ...mapActions('user', {
      updateAvatar: 'updateAvatar',
      updateUsername: 'updateUsername',
      updateEmail: 'updateEmail'
    }),

    usernameClick () {
      if (this.usernameTextField.disabled) {
        this.usernameTextField.disabled = false
      } else if (this.usernameTextField.input === this.usernameTextField.value) {
        this.usernameTextField.disabled = true
      } else if (this.$refs.usernameForm.validate()) {
        this.usernameTextField.loading = true
        this.updateUsername({
          username: this.usernameTextField.input,
          $apis: this.$apis
        }).then(() => {
          this.usernameTextField.loading = false
          this.$snackbar.success(this.$t('whatUpdateSuccessfully', [this.$t('username')]))
          this.usernameTextField.value = this.usernameTextField.input
          this.usernameTextField.disabled = true
        }).catch(() => {
          this.usernameTextField.loading = false
        })
      }
    },

    emailClick () {
      if (this.emailTextField.disabled) {
        this.emailTextField.disabled = false
      } else if (this.$refs.emailForm.validate()) {
        if (this.emailTextField.value === this.userInfo.user.email) {
          this.emailTextField.disabled = true
        } else {
          this.emailTextField.loading = true
          this.updateEmail({
            email: this.emailTextField.value,
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
    },

    updatePassword () {
      if (this.$refs.passwordForm.validate()) {
        if (this.passwordForm.newPassword.value === this.passwordForm.confirmPassword.value) {
          this.passwordForm.confirmPassword.errorMessageKey = null
          if (this.passwordForm.oldPassword.value === this.passwordForm.newPassword.value) {
            this.passwordForm.confirmPassword.errorMessageKey = 'oldAndNewPasswordsAreTheSame'
          } else {
            // 更新密码
            this.passwordForm.loading = true
            this.$apis.upms.user.updatePassword(this.passwordForm.oldPassword.value, this.passwordForm.confirmPassword.value)
              .then(() => {
                this.passwordForm.loading = false
                this.$snackbar.success(this.$t('whatUpdateSuccessfully', [this.$t('password')]))
              }).catch(() => {
                this.passwordForm.loading = false
              })
          }
        } else {
          this.passwordForm.confirmPassword.errorMessageKey = 'inconsistentPasswords'
        }
      }
    },

    onAvatarSelect (file) {
      if (file.size > 50 * 1024 * 1024) {
        this.$snackbar.error(this.$t('whatCannotGreaterThan', [this.$t('avatar'), '50MB']))
        return
      }
      this.avatarOptions.uploading = true
      this.$apis.upms.file.upload({ bucketName: 'avatar', file })
        .then(({ data }) => {
          this.updateAvatar({
            avatar: data,
            $apis: this.$apis
          })
            .then(() => {
              this.avatarOptions.uploading = false
              this.$snackbar.success(this.$t('whatUpdateSuccessfully', [this.$t('avatar')]))
            })
            .catch(() => {
              this.avatarOptions.uploading = false
            })
        })
        .catch(() => {
          this.avatarOptions.uploading = false
        })
    }
  }
}
</script>

<style scoped>

</style>
