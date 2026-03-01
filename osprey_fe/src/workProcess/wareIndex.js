import { reactive, ref } from 'vue'
import pinia from '@/stores'
import { chatLineArray, userChatRoomInformation } from '@/stores/chat'
import { userInformation } from '@/stores/user'
import { closeSocket } from '@/utils/websocket'
import { skillIndexModule } from '@/workProcess/middleWare' // 技能索引文件
import router from '@/router'
import { stringToISOString } from '@/utils/timeFormat.js'
import cache from '../utils/cache'
import { message } from 'ant-design-vue'
const chatLine = chatLineArray(pinia)
const userInfStore = userInformation(pinia)
const chatInfo = userChatRoomInformation(pinia)

// 消息放入队列处理中间件,技能会在这里统一匹配后输出出去，调用即可，无感
const structInChatLine = (struct) => {
  chatLine.chatArray.data.push(skillIndexModule.normal(struct)) //  常规，不使用技能
}

// Websocket入口
export const msgTest = (chat) => {
  // 中间件入口,chat是原始消息对象,目前不会处理任何消息
  // console.log(chat)
  const data = JSON.parse(chat.data)
  const chatStruct = {
    msg: data.msg,
    userName: data.userName,
    time: stringToISOString(data.time),
    option: {}
  }
  if (data.userName.includes('@')) { // 常规心跳不会进入心跳队列内，独立的@用户会使用独立队列处理
    // console.log('心跳')  //特殊队列
    switch (data.userName) {
      case '@service':
        if (data.data.code === 600) {
          message.warning('账号已在其他位置登录')
          closeSocket()
          router.push({ path: '/' })
          cache.local.remove('token')
          cache.local.remove('username')
        } else {
          // 同步在线人数
          chatInfo.onlineCount = data.data.onlineCount
        }
        if (data.data.code === 700) {
          message.warning('您发送信息包含敏感词')
        }
    }
  } else {
    structInChatLine(chatStruct) // 普通消息常规处理
  }
}

// 将获取历史消息接口的数据转换为聊天框的数据结构，放入队列，
// 历史消息入口
export const historyMsgToChatStruct = (arr) => {
  arr.forEach(item => {
    const chatStruct = {
      msg: item.message,
      userName: item.userName,
      time: stringToISOString(item.time),
      option: {}
    }
    structInChatLine(chatStruct)
  })
}
