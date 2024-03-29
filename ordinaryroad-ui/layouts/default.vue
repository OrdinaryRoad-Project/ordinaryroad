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
  <v-app>
    <v-navigation-drawer
      v-model="drawerLeftModel"
      width="280"
      :temporary="$vuetify.breakpoint.smAndDown"
      :expand-on-hover="!$vuetify.breakpoint.smAndDown&&drawerMiniVariant"
      :mini-variant="!$vuetify.breakpoint.smAndDown&&drawerMiniVariant"
      :clipped="!$vuetify.breakpoint.smAndDown&&drawerClipped"
      fixed
      app
    >
      <OrBaseTreeList
        :nav="false"
        :items="[{}]"
        @clickListItem="$router.push('/')"
      >
        <template #item_0>
          <v-list-item-avatar>OR</v-list-item-avatar>
          <v-list-item-title class="text-h6" style="height:24px;line-height:24px">
            OrdinaryRoad
          </v-list-item-title>
        </template>
      </OrBaseTreeList>
      <v-divider />
      <OrBaseTreeList
        :items="userMenuItems"
        @clickListItem="logout"
      >
        <template #prependIcon_0>
          <v-list-item-avatar class="pa-0 ma-0" size="26">
            <v-img :src="avatarPath" style="border: 1px solid">
              <template #placeholder>
                <v-skeleton-loader type="image" />
              </template>
            </v-img>
          </v-list-item-avatar>
        </template>
        <template #activator_0>
          <v-list-item-content class="pa-0">
            <v-list-item-title>
              {{ userInfo.user.username || '暂未设置用户名' }}
            </v-list-item-title>
            <v-list-item-subtitle>
              {{ userInfo.user.email }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </template>
        <template #appendIcon_0>
          <v-list-item-action class="ms-0 me-0">
            <v-icon>mdi-chevron-down</v-icon>
          </v-list-item-action>
        </template>
      </OrBaseTreeList>
      <v-divider />
      <OrBaseTreeList :items="menuItems" />
    </v-navigation-drawer>
    <v-app-bar
      :clipped-left="drawerClipped"
      fixed
      app
    >
      <v-app-bar-nav-icon @click.stop="toggleDrawerLeft" />
      <v-btn
        v-if="!$vuetify.breakpoint.smAndDown"
        icon
        @click.stop="toggleDrawerMiniVariant"
      >
        <v-icon>mdi-{{ `chevron-${drawerMiniVariant ? 'right' : 'left'}` }}</v-icon>
      </v-btn>
      <v-btn
        v-if="!$vuetify.breakpoint.smAndDown"
        icon
        @click.stop="toggleDrawerClipped"
      >
        <v-icon>mdi-application</v-icon>
      </v-btn>
      <v-toolbar-title>{{ $t(titleKey) }}</v-toolbar-title>
      <v-spacer />
      <v-btn
        icon
        href="https://github.com/1962247851/ordinaryroad"
        target="_blank"
      >
        <v-icon>mdi-github</v-icon>
      </v-btn>
      <v-tooltip bottom>
        {{ $t('document') }}
        <template #activator="{ on, attrs }">
          <v-btn
            icon
            href="https://ordinaryroad.tech"
            target="_blank"
            v-bind="attrs"
            v-on="on"
          >
            <v-icon>mdi-help-circle</v-icon>
          </v-btn>
        </template>
      </v-tooltip>
      <v-menu offset-y open-on-hover>
        <template #activator="{ on, attrs }">
          <v-btn
            depressed
            v-bind="attrs"
            v-on="on"
          >
            <v-icon>mdi-translate</v-icon>
            <v-icon>mdi-chevron-down</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(localeOption, index) in localeOptions"
            :key="index"
            @click.stop="setLang({
              value: locales[localeOptions.indexOf(localeOption)],
              $i18n, $vuetify, $dayjs
            })"
          >
            <v-list-item-title>{{ localeOption }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn
        icon
        @click.stop="toggleDrawerRight"
      >
        <v-icon>mdi-cog</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <nuxt />
      </v-container>
    </v-main>
    <v-navigation-drawer
      v-model="drawerRightModel"
      width="310"
      right
      temporary
      fixed
    >
      <v-toolbar>
        <v-toolbar-title>{{ $t('settings') }}</v-toolbar-title>
        <v-spacer />
        <v-btn
          icon
          @click.stop="toggleDrawerRight"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-toolbar>
      <v-container>
        <div>
          <div class="text-subtitle-2 font-weight-black">
            {{ $t('theme') }}
          </div>
          <v-item-group v-model="selectedThemeOptionModel" mandatory>
            <v-container>
              <v-row>
                <v-col
                  v-for="(themeOption,index) in themeOptions"
                  :key="themeOption.title"
                  cols="6"
                  class="pa-1"
                  @click="click(index)"
                >
                  <v-item v-slot="{ active, toggle }">
                    <v-card
                      flat
                      :color="active ? 'primary' : $vuetify.theme.dark? 'grey darken-3':'grey lighten-3'"
                      :dark="active"
                      class="py-3 px-4 d-flex align-center justify-space-between"
                      height="50"
                      @click="toggle"
                    >
                      <span>{{ $t(themeOption.titleKey) }}</span>
                      <v-icon>{{ themeOption.icon }}</v-icon>
                    </v-card>
                  </v-item>
                </v-col>
              </v-row>
            </v-container>
          </v-item-group>
        </div>
      </v-container>
    </v-navigation-drawer>
    <v-footer
      app
    >
      <span>&copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>

<script>
// Utilities
import { mapActions, mapGetters } from 'vuex'
import { updateTheme } from 'static/js/utils/vuetify'

export default {
  middleware: ['userInfo'],
  head () {
    return {
      htmlAttrs: {
        lang: this.$i18n.locale
      }
    }
  },
  computed: {
    ...mapGetters('app', {
      drawerLeft: 'getDrawerLeft',
      drawerRight: 'getDrawerRight',
      drawerMiniVariant: 'getDrawerMiniVariant',
      drawerClipped: 'getDrawerClipped',
      selectedThemeOption: 'getSelectedThemeOption',
      themeOptions: 'getThemeOptions',
      menuItems: 'getAccessibleMenuItems',
      titleKey: 'getTitleKey',
      userMenuItems: 'getUserMenuItems'
    }),
    ...mapGetters('i18n', {
      localeOptions: 'getLocaleOptions',
      locales: 'getLocales'
    }),
    ...mapGetters('user', {
      userInfo: 'getUserInfo',
      avatarPath: 'getAvatarPath'
    }),
    selectedThemeOptionModel: {
      get () {
        return this.selectedThemeOption
      },
      set (val) {
        // ignore
      }
    },

    drawerLeftModel: {
      get () {
        return this.drawerLeft
      },
      set (val) {
        this.setDrawerLeft(val)
      }
    },
    drawerRightModel: {
      get () {
        return this.drawerRight
      },
      set (val) {
        this.setDrawerRight(val)
      }
    }
  },
  created () {
    this.setDrawerLeft(!this.$vuetify.breakpoint.mdAndDown)
  },
  mounted () {
    // dom初始化完成再初始化主题
    this.$nextTick(() => {
      updateTheme(this.selectedThemeOption, this.$vuetify)
    })
  },
  methods: {
    ...mapActions('app', {
      setSelectedThemeOption: 'setSelectedThemeOption',
      toggleDrawerMiniVariant: 'toggleDrawerMiniVariant',
      toggleDrawerClipped: 'toggleDrawerClipped',
      setDrawerLeft: 'setDrawerLeft',
      setDrawerRight: 'setDrawerRight',
      toggleDrawerLeft: 'toggleDrawerLeft',
      toggleDrawerRight: 'toggleDrawerRight'
    }),
    ...mapActions('i18n', {
      setLang: 'setLang'
    }),

    logout () {
      this.$dialog({
        content: this.$i18n.t('confirmLogout'),
        loading: true
      }).then((dialog) => {
        if (dialog.isConfirm) {
          this.$store.dispatch('user/logout', {
            $apis: this.$apis,
            $router: this.$router,
            $route: this.$route
          }).then(() => dialog.cancel())
        }
      })
    },

    click (index) {
      this.setSelectedThemeOption({ value: index, $vuetify: this.$vuetify })
    }
  }
}
</script>
