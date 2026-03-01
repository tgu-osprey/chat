import router from '@/router'

// 路由守卫，作为聊天室鉴权用
router.beforeEach((to, from, next) => {
  next()
})
router.afterEach((to, from, next) => {
})
