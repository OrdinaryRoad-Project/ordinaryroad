<template>
  <v-app dark>
    <v-navigation-drawer
      v-model="drawerLeftModel"
      :mini-variant="miniVariant"
      :clipped="clipped"
      fixed
      app
    >
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
      :clipped-left="clipped"
      fixed
      app
    >
      <v-app-bar-nav-icon @click.stop="toggleDrawerLeft" />
      <v-btn
        icon
        @click.stop="miniVariant = !miniVariant"
      >
        <v-icon>mdi-{{ `chevron-${miniVariant ? 'right' : 'left'}` }}</v-icon>
      </v-btn>
      <v-btn
        icon
        @click.stop="clipped = !clipped"
      >
        <v-icon>mdi-application</v-icon>
      </v-btn>
      <v-toolbar-title>{{ $t(titleKey) }}</v-toolbar-title>
      <v-spacer />
      <!-- TODO 个人头像 -->
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
              $i18n, $vuetify
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
        <v-icon>mdi-menu</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <nuxt />
      </v-container>
    </v-main>
    <v-navigation-drawer
      v-model="drawerRightModel"
      width="300"
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
                  v-for="themeOption in themeOptions"
                  :key="themeOption.title"
                  cols="6"
                  class="pa-1"
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

export default {
  data () {
    return {
      clipped: false,
      miniVariant: false
    }
  },
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
      selectedThemeOption: 'getSelectedThemeOption',
      themeOptions: 'getThemeOptions',
      menuItems: 'getMenuItems',
      titleKey: 'getTitleKey'
    }),
    ...mapGetters('i18n', {
      localeOptions: 'getLocaleOptions',
      locales: 'getLocales'
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
    },

    selectedThemeOptionModel: {
      get () {
        return this.selectedThemeOption
      },
      set (val) {
        this.setSelectedThemeOption({ value: val, $vuetify: this.$vuetify })
      }
    }
  },
  methods: {
    ...mapActions('app', {
      setSelectedThemeOption: 'setSelectedThemeOption',
      setDrawerLeft: 'setDrawerLeft',
      setDrawerRight: 'setDrawerRight',
      toggleDrawerLeft: 'toggleDrawerLeft',
      toggleDrawerRight: 'toggleDrawerRight'
    }),
    ...mapActions('i18n', {
      setLang: 'setLang'
    })
  }
}
</script>
