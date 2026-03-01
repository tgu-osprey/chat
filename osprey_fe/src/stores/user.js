import { ref } from 'vue'
import { defineStore } from 'pinia'

export const userInformation = defineStore('counter', () => {
  const username = ref('zhaoxiang') // 用户名
  const userKey = ref('') // 用户身份码

  return { username, userKey }
})
