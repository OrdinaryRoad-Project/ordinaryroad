export default () => ({
  drawerLeft: true,
  drawerRight: false,
  // 设置初始值
  drawerMiniVariant: true,
  drawerClipped: false,
  selectedThemeOption: 1,
  userMenuItems: [
    {
      titleKey: 'userMenuTitles.userProfile',
      to: '/user/profile',
      icon: 'mdi-account'
    }
  ],
  menuItems: [
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
