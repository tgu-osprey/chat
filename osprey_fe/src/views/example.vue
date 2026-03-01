<template>
  <div class="test">
    <input v-model="socketUser.message">
    <button @click="getSocket">发送</button>
    <div v-for="(index,key) in socketLineStore.data" :key="key">
     <p :style="index.option.style">{{ index.userName }}:{{ index.msg }}</p>
    </div>
  </div>
</template>
<script setup>
import { reactive, onMounted, watch } from 'vue'
import { createWs, sendSocket } from '@/utils/websocket'
import pinia from '@/stores'
import { chatLineArray } from '@/stores/chat'
import router from '../router'
import { useRouter, useRoute } from 'vue-router'
import { searchWord, hitWord } from '@/utils/clashword'

const route = useRoute()
const chatLine = chatLineArray(pinia) // 获取聊天队列，pinia必须写，使用外挂载pinia实例
onMounted(() => {
  console.log(route)
  const name = route.query.name
  socketUser.data = createWs(name) // 建立socket消息，返回一个websocket对象，后续发送需要使用这个，也可以关闭 .close()
})
const socketLineStore = reactive({
  data: chatLine.chatArray.data // 获取消息队列
})
const socketUser = reactive({
  data: null,
  message: ''
})
const getSocket = () => {
  console.log(searchWord(socketUser.message)) // 快速搜索，返回第一个敏感词的位置
  console.log(hitWord(socketUser.message)) // 全量命中，返回一个对象
  sendSocket(socketUser.data, socketUser.message) // 发送消息，需要传递一个socket实例和消息
  socketUser.message = '' // 重置输入框
}
watch(() => socketLineStore.data.length, (value, oldValue) => {
  console.log('队列更新') // 这个监听可以解决看不见的问题
  console.log(value)
})
</script>
<style scoped lang="scss">
.test {
  color: red;
 .test123 {
    color: blue;
  }
}
</style>
