<template>
  <div class="otherBubble">
    <div class="context">
      <div>
        <div class="username">
          {{ props.userName }}
        </div>
      </div>
      <div>
        <div class="bubble">
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
const feishuSound = ref("http://mc.haaland.top:5244/d/local/files_trusteeship/feishu.MP3")
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
</style>
