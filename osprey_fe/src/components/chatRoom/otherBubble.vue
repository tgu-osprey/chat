<template>
  <div class="otherBubble" :class="{ 'banana-mode': props.reversed }">
    <div class="context">
      <div>
        <div class="username">
          {{ props.userName }}
          <span v-if="props.reversed" class="banana-badge">🍌</span>
        </div>
      </div>
      <div>
        <div class="bubble" :class="{ 'reversed-bubble': props.reversed }">
          {{ props.msg }}
        </div>
      </div>
    </div>
    <div v-if="props.option.eggshell !== undefined">
      <audio :controls="false" class="aud" ref="audio" v-if="props.option.eggshell.type === 'zx'">
        <source :src="props.option.eggshell.content"/>
      </audio>
    </div>
    <audio :controls="false" class="aud" ref="feishu" v-if="chatInfo.isSound">
      <source :src="feishuSound" />
    </audio>
  </div>
</template>

<script setup>
import {defineProps, ref, onMounted} from 'vue'
import pinia from '@/stores'
import { userChatRoomInformation } from '@/stores/chat'
const chatInfo = userChatRoomInformation(pinia)
const props = defineProps({
  userName: String,
  msg: String,
  reversed: Boolean,
  option: {
    type: Object,
    default: {}
  }
})

const audio = ref(null)
const playAudio = () => {
  audio.value.play()
}

const feishu = ref(null)
const feishuSound = ref("/feishu.MP3")
const playFeishu = () => {
  feishu.value.play()
}
onMounted(() => {
  if (props.option.eggshell !== undefined) {
    if (props.option.eggshell.type === 'zx') {
      playAudio()
    }
  }
  if(chatInfo.isSound === true) {
    playFeishu()
  }

})
</script>

<style scoped lang="scss">
.otherBubble {
  width: 100%;
  overflow: hidden;
  padding-top: 20px;

  .context {
    float: left;
    width: auto;
    max-width: 60%;
    margin-left: 20px;

    // background-color: bisque;
    .username {
      float: left;
      color: grey;

      word-break: break-all;
      white-space: pre-wrap;
    }

    .bubble {
      float: left;
      margin-top: 20px;
      margin-left: 20px;
      padding: 10px;

      max-width: 100%;

      line-height: 28px;
      font-size: 18px;


      word-break: break-all;
      white-space: pre-wrap;

      border-radius: 10px;
      background: rgb(229, 229, 229);
      border: 1px solid rgba(0, 0, 0, 0.0578);

    }
  }

}

@media screen and (max-width: 768px) {
  .otherBubble {
    .context {
      .bubble {
        font-size: 15px;
      }
    }
  }
}

//🍌 香蕉皮特效
.banana-mode {
  animation: bananaShake 0.5s ease-in-out;
}

.banana-badge {
  font-size: 14px;
  margin-left: 5px;
}

.reversed-bubble {
  background: linear-gradient(135deg, #ffeaa7 0%, #fab1a0 100%) !important;
  color: #2d3436 !important;
  border: 2px solid #e17055 !important;
  font-style: italic;
}

@keyframes bananaShake {
  0%, 100% { transform: translateX(0) rotate(0deg); }
  25% { transform: translateX(-3px) rotate(-2deg); }
  50% { transform: translateX(3px) rotate(2deg); }
  75% { transform: translateX(-3px) rotate(-1deg); }
}
</style>
