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

export default () => ({
  drawerLeft: true,
  drawerRight: false,
  // 设置初始值
  drawerMiniVariant: true,
  drawerClipped: false,
  selectedThemeOption: 1,
  userMenuItems: [
    {
      children: [
        {
          titleKey: 'userMenuTitles.userProfile',
          to: '/user/profile',
          icon: 'mdi-account'
        },
        {
          titleKey: 'logout',
          icon: 'mdi-logout'
        }
      ]
    }
  ],
  /**
   * 最多三层
   */
  menuItems: [
    {
      titleKey: 'menuTitles.accessControl',
      icon: 'mdi-key',
      children: [
        {
          titleKey: 'menuTitles.userManagement',
          to: '/upms/user',
          icon: 'mdi-account',
          meta: {
            permission: 'upms:user:list'
          }
        },
        {
          titleKey: 'menuTitles.roleManagement',
          to: '/upms/role',
          icon: 'mdi-account-multiple',
          meta: {
            permission: 'upms:role:list'
          }
        },
        {
          titleKey: 'menuTitles.permissionManagement',
          to: '/upms/permission',
          icon: 'mdi-lock',
          meta: {
            permission: 'upms:permission:list'
          }
        },
        {
          titleKey: 'menuTitles.requestPathManagement',
          to: '/upms/request_path',
          icon: 'mdi-menu',
          meta: {
            permission: 'upms:request_path:list'
          }
        }
      ]
    },
    {
      titleKey: 'menuTitles.systemManagement',
      icon: 'mdi-cog',
      children: [
        {
          titleKey: 'menuTitles.dictManagement',
          to: '/upms/dict',
          icon: 'mdi-book-cog',
          meta: {
            permission: 'upms:dict:list'
          }
        },
        {
          titleKey: 'menuTitles.fileManagement',
          to: '/upms/file',
          icon: 'mdi-file-multiple',
          meta: {
            permission: 'upms:file:list'
          }
        },
        {
          titleKey: 'menuTitles.registeredClientManagement',
          to: '/auth/registered_client',
          icon: 'mdi-web',
          meta: {
            permission: 'auth:registered_client:list'
          }
        },
        {
          titleKey: 'menuTitles.openidManagement',
          to: '/auth/openid',
          icon: 'mdi-identifier',
          meta: {
            permission: 'auth:openid:list'
          }
        }
      ]
    }
  ],
  accessibleMenuItems: [],
  titleKey: null,
  image: 'http://rekryt.ru/files/sidebar-2.32103624.jpg',
  themeOptions: [
    {
      titleKey: 'themeTitles.light',
      icon: 'mdi-white-balance-sunny'
    },
    {
      titleKey: 'themeTitles.dark',
      icon: 'mdi-weather-night'
    },
    {
      titleKey: 'themeTitles.hunk',
      icon: 'mdi-arm-flex'
    },
    {
      titleKey: 'themeTitles.system',
      // icon: 'mdi-desktop-tower-monitor'
      icon: 'mdi-arm-flex'
    }
  ]
})
