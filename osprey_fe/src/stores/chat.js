import { reactive, ref } from 'vue'
import { defineStore } from 'pinia'

// 聊天室基础信息
export const userChatRoomInformation = defineStore('chat', () => {
  const name = ref('')
  const isSound = ref(false)
  const onlineCount = ref(0)
  return { name, isSound, onlineCount }
})

// 这个是行为队列
export const chatLineArray = defineStore('lineChat', () => {
  const chatArray = reactive({
    data: [] // 聊天消息队列
  })
  return { chatArray }
})
