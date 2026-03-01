<template>
  <div>
    <div class="background"></div>
    <div class="header">
      <LogoTitle class="logo-title"></LogoTitle>
      <div style="display: flex; gap: 1rem; align-items: center;">
        <div style="color: #616161">在线人数: {{chatInfo.onlineCount}}</div>
        <Sound class="sound" :width="25" :height="25" color="#009DFF" :style="[chatInfo.isSound?'':soundStyle]" @click="chatInfo.isSound = !chatInfo.isSound" />
      </div>
    </div>
    <div class="chatRoomOut">
      <div class="left">
        <!-- 左侧其他区域 -->
        <ChatBox :socketData="socketData"></ChatBox>
      </div>
      <div class="right">
        <!-- 右侧聊天组件区域 -->
        <Dialogue @created="socketCreated"></Dialogue>
      </div>
    </div>
  </div>
</template>

<script setup>
import LogoTitle from '../../components/LogoTitle.vue';
import Dialogue from '../../components/chatRoom/dialogue.vue'
import ChatBox from '../../components/chatRoom/leftPosition/chatBox.vue'
import Sound from '../../components/icon/Sound.vue'
import {ref} from 'vue';
import pinia from '@/stores'
import { userChatRoomInformation } from '@/stores/chat'
const chatInfo = userChatRoomInformation(pinia)
console.log(chatInfo)
const socketData = ref(null)
const soundStyle = ref({
  filter: 'grayscale(100%)'
})


function socketCreated(value) {
  socketData.value = value
  console.log(value)
}

</script>

<style scoped lang="scss">
.background {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: #F3F3F3;
}

.header{
  position: fixed;
  top: 0;
  height: 3.5rem;
  user-select: none;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .logo-title {
    // position: fixed;
    position: static;
    height: 3.5rem;
    width: 9.3rem;
    display: flex;
    margin-left: 1.5rem;
  }
  .sound{
    margin-right: 1.5rem;
    cursor: pointer;
    user-select: none;
  }
}

.chatRoomOut {
  display: flex;
  position: fixed;
  height: calc(100% - 3.5rem);
  width: 100%;
  top: 3.5rem;

  .left {
    width: 20rem;
    min-width: 100px;
    height: 100%;
  }

  .right {
    width: calc(100% - 20rem);
    height: 100%;
    // height: 100vh;
    // background-color: red;
  }
}

@media screen and (max-width: 768px) {
  .logo-title {
    position: fixed;
    height: 55px;
    width: 80px;
    display: flex;
  }
  .chatRoomOut {
    position: fixed;
    display: flex;
    flex-direction: column;
    width: 100%;

    .left {
      width: auto;
      height: 50px;
    }

    .right {
      width: auto;
      height: calc(100% - 45px);
    }
  }

}
</style>
