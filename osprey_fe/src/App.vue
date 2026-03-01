<template>
  <div class="body">
    <RouterView />
  </div>
</template>
<script setup>
import { onMounted } from 'vue';
import './access/reset.css'
import { RouterView, useRouter } from 'vue-router'
import { getUserName } from '@/api/login'
import cache from '@/utils/cache'
import router from '@/router'
import {historyMsgToChatStruct} from '@/workProcess/wareIndex.js'

onMounted(() => {
  // 若检测到有token且处于生产环境，则验证账号后，直接进入聊天室
  if (cache.local.get('token') && import.meta.env.PROD) {
    useCode(cache.local.get('token'))
  }
  
})
function useCode(newVal) {
  getUserName(newVal).then(res => {
    if (res.code === 1) {
      cache.local.set('token', newVal)
      cache.local.set('username', res.data.userName)
      enterChat(res.data)
      console.log('登录成功')
    } else {
      cache.local.remove('token')
      cache.local.remove('username')

    }
  })
}
// 进入聊天室
function enterChat(data) {
  if(router.currentRoute.value.fullPath !== '/chatroom') {
    router.push({
      name: 'chatroom'
    })
  }
  historyMsgToChatStruct(data.talkingMessages)
}
</script>
<style scoped lang="scss">
@import './font-style/font.css';

.body {
  font-family: PingFang;
}
</style>
