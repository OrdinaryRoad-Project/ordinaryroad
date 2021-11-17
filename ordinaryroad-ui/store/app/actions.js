export default {
  setDrawerLeft ({ commit }, drawerLeft) {
    commit('SET_DRAWER_LEFT', drawerLeft)
  },
  setDrawerRight ({ commit }, drawerRight) {
    commit('SET_DRAWER_RIGHT', drawerRight)
  },
  setImage ({ commit }, image) {
    commit('SET_IMAGE', image)
  },
  setTitleKey ({ commit }, value) {
    commit('SET_TITLE_KEY', value)
  },
  setSelectedThemeOption ({ commit }, values) {
    commit('SET_SELECTED_THEME_OPTION', values)
  },
  toggleDrawerLeft ({ commit }) {
    commit('TOGGLE_DRAWER_LEFT')
  },
  toggleDrawerRight ({ commit }) {
    commit('TOGGLE_DRAWER_RIGHT')
  },
  setMenuItems ({ commit }, menuItems) {
    commit('SET_MENU_ITEMS', menuItems)
  }
}
