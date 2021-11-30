export default {
  getImage (state) {
    return state.image
  },

  getTitleKey (state) {
    return state.titleKey
  },

  getDrawerLeft (state) {
    return state.drawerLeft
  },
  getDrawerRight (state) {
    return state.drawerRight
  },

  getDrawerMiniVariant (state) {
    return state.drawerMiniVariant
  },

  getDrawerClipped (state) {
    return state.drawerClipped
  },

  getSelectedThemeOption: (state) => {
    return state.selectedThemeOption
  },

  getThemeOptions (state) {
    return state.themeOptions
  },

  getMenuItems (state) {
    return state.menuItems
  }
}
