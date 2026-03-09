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
  
  //🍌 香蕉皮模式状态
  const bananaMode = ref(false)
  const originalMsg = ref('') // 保存原文
  
  //🍌 检查消息是否包含"香蕉皮"
  const checkBanana = (msg) => {
    if (msg && msg.includes('香蕉皮')) {
      bananaMode.value = true
      return true
    }
    return false
  }
  
  //🍌 处理倒序消息
  const processBanana = (msg) => {
    if (bananaMode.value) {
      originalMsg.value = msg
      const reversed = msg.split('').reverse().join('')
      bananaMode.value = false // 触发后关闭
      return { text: reversed, isReversed: true, original: msg }
    }
    return { text: msg, isReversed: false, original: '' }
  }
  
  return { chatArray, bananaMode, originalMsg, checkBanana, processBanana }
})
