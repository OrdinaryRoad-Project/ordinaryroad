/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
  setDrawerMiniVariant ({ commit }, value) {
    commit('SET_DRAWER_MINI_VARIANT', value)
  },
  setDrawerClipped ({ commit }, value) {
    commit('SET_DRAWER_CLIPPED', value)
  },
  setSelectedThemeOption ({ commit }, values) {
    commit('SET_SELECTED_THEME_OPTION', values)
  },
  toggleDrawerMiniVariant ({ commit }) {
    commit('TOGGLE_DRAWER_MINI_VARIANT')
  },
  toggleDrawerClipped ({ commit }) {
    commit('TOGGLE_DRAWER_CLIPPED')
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
