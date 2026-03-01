<template>
    <div class="content">
        <div class="dialogueOut" id="dialogueOut" ref="dialog">
            <div class="dialogueBubble" v-for="(index, key) in socketLineStore.data" :key="key">
                <div class="time-show" v-if="timeCompute(key)">{{timestampToString(index.time)}}</div>
                <MyBubble :userName="index.userName" :msg="index.msg" v-if="index.userName == cache.local.get('username')">
                </MyBubble>
                <OtherBubble :userName="index.userName" :msg="index.msg" :option="index.option"
                    v-if="index.userName != cache.local.get('username')">
                </OtherBubble>
            </div>
            <!-- <div style="height: 5rem"></div> -->
        </div>
        <DialogueInput @child-click="sendMyMessage" class="DialogueInput" :chatArray="chatLine.chatArray.data"></DialogueInput>
    </div>
</template>

<script setup>
// 下面这2行如果报错不用管，他抽风了
import MyBubble from './myBubble.vue'
import OtherBubble from './otherBubble.vue'
import DialogueInput from './input.vue'

// import router from '../router'
import { useRouter, useRoute } from 'vue-router'

import { createWs, sendSocket } from '@/utils/websocket'
import pinia from '@/stores'
import { chatLineArray } from '@/stores/chat'
import { message } from 'ant-design-vue'

import { ref, reactive, h, render, watch, onMounted } from 'vue'
import {timestampToString} from '@/utils/timeFormat'

import cache from '../../utils/cache'

const emit = defineEmits(['created'])

const route = useRoute()
const chatLine = chatLineArray(pinia) // 获取聊天队列，pinia必须写，使用外挂载pinia实例
onMounted(() => {
  console.log(chatLine.chatArray)
  // console.log(route)
  // 从cache中拿取uuid和姓名，接收路由过来的消息队列
  const uuid = cache.local.get('token')
  socketUser.data = createWs(uuid) // 建立socket消息，返回一个websocket对象，后续发送需要使用这个，也可以关闭 .close()
  emit('created', socketUser.data)

  // 进聊天室先置底
  // document.getElementById('dialogueOut').scrollTo(0, document.getElementById('dialogueOut').scrollHeight)
  setTimeout(scrollToBottom, 0)

  // 监听屏幕尺寸变化
  window.onresize = () => {
    return (() => {
      screenHeight.value = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
    })()
  }
})
const screenHeight = ref(0)
watch(() => screenHeight, (val) => {
  screenHeight.value = val
  console.log(screenHeight.value)
})

const socketLineStore = reactive({
  data: chatLine.chatArray.data // 获取消息队列
})
const socketUser = reactive({
  data: null,
  message: ''
})
const getSocket = () => {
  // console.log(searchWord(socketUser.message)) // 快速搜索，返回第一个敏感词的位置
  // console.log(hitWord(socketUser.message)) // 全量命中，返回一个对象
  const res = sendSocket(socketUser.data, socketUser.message) // 发送消息，需要传递一个socket实例和消息
  if (res === '502') {
    message.warning('非法输入')
  }
}

const dialog = ref(null)

watch(() => socketLineStore.data.length, (value, oldValue) => {
  // console.log('队列更新', socketLineStore.data.length)
  // 这个监听可以解决看不见的问题

  // 指定滚动条到底
  const anchorElement = document.getElementById('dialogueOut')
  const scrollIntoViewOptions = {
    block: 'end',
    behavior: 'smooth'
  }
  if (anchorElement) {
    anchorElement.scrollIntoView(scrollIntoViewOptions)
  }

  setTimeout(scrollToBottom, 0)
})

// 滚动条到底，有一定平滑效果用户可见
function scrollToBottom () {
  const domWrapper = document.getElementById('dialogueOut'); // 外层容器 出现滚动条的dom
  (function smoothscroll () {
    const currentScroll = domWrapper.scrollTop // 已经被卷掉的高度
    const clientHeight = domWrapper.offsetHeight // 容器高度
    const scrollHeight = domWrapper.scrollHeight // 内容总高度
    if (scrollHeight - 10 > currentScroll + clientHeight) {
      window.requestAnimationFrame(smoothscroll)
      domWrapper.scrollTo(0, currentScroll + (scrollHeight - currentScroll - clientHeight) / 2)
    }
  })()
}

const sendMyMessage = (value) => {
  socketUser.message = value
  getSocket()
  // document.getElementById('dialogueOut').scrollTop = document.getElementById('dialogueOut').scrollHeight + 50;
}

// 时间计算
const timeCompute = (index) => {
  if(index === 0) {
    return true
  }
  if (socketLineStore.data[index].time - socketLineStore.data[index - 1].time > 300000) {
    return true
  } else {
    return false
  }
}
</script>

<style scoped lang="scss">
.content{
    height: 100%;
}
.dialogueOut {
    box-sizing: border-box;
    width: 100%;
    height: calc(100% - 5rem);
    padding-top: 3.5rem;
    padding-bottom: 3.5rem;

    overflow: scroll;
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */

    background-color: #F9F9F9;

    border: 1px solid rgba(0, 0, 0, 0.0578);
    border-bottom: none;
    border-radius: 0.5rem 0px 0.5rem 0px;

    position: relative;

    .dialogueBubble {
        padding-left: 1rem;
        padding-right: 1rem;
    }
}

.DialogueInput {
    height: 5rem;
}
/*屏幕宽度小于768px */
@media screen and (max-width: 768px) {
    .dialogueOut {
      .dialogueBubble {
        font-size: 15px;
        padding-left: 0;
        padding-right: 0;
      }
    }
  .DialogueInput {
    padding: 0;
    position: absolute;
    bottom: 0;
    width: 100%;
  }
}

/* 隐藏 Chrome、Safari 和 Opera 的滚动条 */
.dialogueOut::-webkit-scrollbar {
    display: none;
}

// 时间显示
.time-show {
    text-align: center;
    font-size: 14px;
    color: #999999;
    margin-top: 1rem;
    // margin-bottom: 0.3rem;
}
</style>
