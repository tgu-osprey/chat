import pinia from '../stores'
import { msgTest } from '@/workProcess/wareIndex'
import { userInformation } from '@/stores/user'
import cache from '@/utils/cache'
import { searchWord } from './clashword'
import { Modal, message } from 'ant-design-vue'
import router from '@/router'
const userStore = userInformation(pinia)
const wsBaseUrl = window.location.host
let socket = null // 持久保存本地状态化
let sendCount = 0
// 这里是webstrom工厂类，用于快速创建webstorm,调用这个库然后返回一个带有
export const createWs = (userID) => {
  userStore.username = userID
  socket = new WebSocket(`ws://${wsBaseUrl}/ws/` + userID)
  socket.onopen = (data) => { // 建立成功回调
    console.log('Socket挂载成功')
  }
  socket.onmessage = (data) => { // 获取到消息的回调
    msgTest(data)
  }
  socket.onclose = (data) => { // 获取到close的回调
    if(router.currentRoute.value.fullPath === '/chatroom') {
      createWs(userID)
    } else {
      console.log('Socket连接已断开')
    }
    // Modal.warning({
    //   title: '提示',
    //   content: '连接已断开',
    //   onOk: () => {
    //     router.push('/')
    //   }
    // })
    
  }
  return socket
}

// 发送方法
export const sendSocket = (socketInstance, msg) => {
  if (new Date().getTime() - sendCount <= 1100) {
    message.warning('消息过于频繁')
    return '503' // 操作过于频繁
  }
  // 只有不超时的时候才会更新时间
  sendCount = new Date().getTime()
  if (!searchWord(msg).length) {
    if (cache.local.get('token')) { // 验证是否有token
      socketInstance.send(JSON.stringify({
        code: 1,
        uuid: cache.local.get('token'),
        msg
      }))
      return '200'
    } else {
      message.error('全局Token丢失')
      return '501' // 无token
    }
  } else {
    return '502' // 涉及敏感词
  }
}
export const closeSocket = () => {
  socket.close()
}
