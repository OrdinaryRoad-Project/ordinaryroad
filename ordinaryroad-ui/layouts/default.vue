<template>
  <v-app>
    <v-navigation-drawer
      v-model="drawerLeftModel"
      width="280"
      :temporary="$vuetify.breakpoint.smAndDown"
      mini-variant-width="90"
      :expand-on-hover="!$vuetify.breakpoint.smAndDown&&drawerMiniVariant"
      :mini-variant="!$vuetify.breakpoint.smAndDown&&drawerMiniVariant"
      :clipped="!$vuetify.breakpoint.smAndDown&&drawerClipped"
      fixed
      app
    >
      <v-list>
        <v-list-item>
          <v-list-item-avatar>OR</v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="text-h6">
              OrdinaryRoad
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <v-divider />
      <v-list nav>
        <v-list-group>
          <template #activator>
            <v-list-item-avatar>
              <v-img :src="require('static/vuetify-logo.svg')" />
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>
                {{ userInfo.user.username || '暂未设置用户名' }}
              </v-list-item-title>
              <v-list-item-subtitle class="text-caption">
                {{ userInfo.user.email }}
              </v-list-item-subtitle>
            </v-list-item-content>
          </template>

          <template #appendIcon>
            <v-list-item-action class="ms-0 me-0">
              <v-icon>mdi-chevron-down</v-icon>
            </v-list-item-action>
          </template>

          <v-list-item
            v-for="(item, i) in userMenuItems"
            :key="i"
            :to="item.to"
            router
            exact
            active-class="primary white--text"
          >
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>{{ $t(item.titleKey) }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item
            @click="logout"
          >
            <v-list-item-action>
              <v-icon>mdi-logout</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>{{ $t('logout') }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
      </v-list>
      <v-divider />
      <v-list nav>
        <v-list-item
          v-for="(item, i) in menuItems"
          :key="i"
          :to="item.to"
          router
          exact
          active-class="primary white--text elevation-4"
        >
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ $t(item.titleKey) }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
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
          <v-item-group v-model="selectedThemeOption" mandatory>
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
      menuItems: 'getMenuItems',
      titleKey: 'getTitleKey',
      userMenuItems: 'getUserMenuItems'
    }),
    ...mapGetters('i18n', {
      localeOptions: 'getLocaleOptions',
      locales: 'getLocales'
    }),
    ...mapGetters('user', {
      userInfo: 'getUserInfo'
    }),
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
      }).then((value) => {
        this.$store.dispatch('user/logout', {
          $apis: this.$apis,
          $router: this.$router,
          $route: this.$route
        }).then(() => value.cancel())
      })
    },

    click (index) {
      this.setSelectedThemeOption({ value: index, $vuetify: this.$vuetify })
    }
  }
}
</script>
