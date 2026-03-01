import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login.vue'
import ChatRoom from '@/views/chatroom/ChatRoom.vue'
import Example from '@/views/example.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/chatroom',
      name: 'chatroom',
      component: ChatRoom
    }
    // {
    //   path: '/chatroom',
    //   name: 'chatroom',
    //   component: Example
    // }
  ]
})

export default router
