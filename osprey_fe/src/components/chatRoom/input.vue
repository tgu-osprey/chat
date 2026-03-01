<template>
  <div class="chatRoomInput">
    <div class="left">
      <!-- <div class="chatRoomSkill" @mouseenter="showSkillBag = true" @mouseleave="showSkillBag = false"></div> -->
      <div class="chatRoomSkill" @click="goEmoji"></div>
      <SkillBag v-if="showSkillBag" style="position: absolute; bottom: 6rem"></SkillBag>
    </div>
    <div class="center">
      <a-mentions class="input" v-model:value="data.message" autofocus :options="mentionsOption(props.chatArray)"
        @select="onSelect" rows="2"></a-mentions>
      <!-- <textarea class="input" type="text" v-model="data.message" @keydown="keydown" placeholder="想说点什么"></textarea> -->
    </div>
    <div class="right">
      <div class="chatRoomSend" @click="sendMessage" :class="[grayFlag ? 'chatRoomSend-gray' : '']"></div>
    </div>
  </div>
</template>

<script setup>
import { defineEmits, onMounted, reactive, ref } from 'vue';
import SkillBag from './skill/SkillBag.vue';
import aMentions from 'ant-design-vue/es/mentions';

// 展示技能包
const showSkillBag = ref(false);

const emit = defineEmits(['child-click'])

onMounted(() => {
  document.onkeydown = function (e) {
    let ev = document.all ? window.event : e
    if (ev.keyCode === 13) {
      judgMention()

      // sendMessage()
    }
  }
})

// 标记选择mention项后回车的次数，第二次回车允许发送
let flag = 0;
function judgMention() {
  const regex = /@[^@\s]+ $/;

  function func() {
    if (regex.test(data.message)) {

      // 若flag为0，先记一次数
      if (!flag) {
        flag++;
        return
      }
      
      // 如果不为0，发送
      sendMessage()
    } else {
      sendMessage()
    }

  }
  func()
}

// 消息队列来自父组件
const props = defineProps({
  chatArray: {
    type: Array,
    default: () => []
  }
})

const data = reactive({
  message: ''
})

// 获取提及消息列表
function mentionsOption(val) {
  // 获取名称唯一值
  const set = new Set()
  val.forEach(item => {
    set.add(item.userName)
  })

  // Set类型转数组，才可以map
  return Array.from(set).map(item => {
    return {
      value: item,
      label: item
    }
  })
}

const grayFlag = ref(false)

const sendMessage = () => {
  // 置空@标记
  flag = 0;
  if (data.message == '') return

  console.log(data.message)

  emit('child-click', data.message)
  setTimeout(() => {
    data.message = ''
  }, 10)

  grayFlag.value = true
  setTimeout(() => {
    grayFlag.value = false
  }, 1000)
};
// const keydown = (e) => {

//   if (!e.shiftKey && e.keyCode == 13) {
//     e.cancelBubble = true; //ie阻止冒泡行为
//     e.stopPropagation();//Firefox阻止冒泡行为
//     e.preventDefault(); //取消事件的默认动作*换行
//     //以下处理发送消息代码
//     sendMessage()
//   }

// }

// 跳转emoji 
const goEmoji = () => {
  window.open('https://emojixd.com/', "_blank")
}
</script>

<style scoped lang="scss">
@import '@/font-style/font.css';

.chatRoomInput {
  padding-left: 0.5rem;
  padding-right: 0.5rem;
  display: flex;
  justify-content: space-around;
  align-items: center;

  background-color: #F9F9F9;

  .left {
    .chatRoomSkill {
      width: 50px;
      left: -50px;
      height: 50px;
      border-radius: 50%;


      background: #FFFFFF;
      box-shadow: 8.05px 24.15px 89.45px -11.63px rgba(22, 52, 80, 0.1);

      background-image: url(./skill.png);
      background-size: 100% 100%;
      background-repeat: no-repeat;
    }
  }

  .center {
    width: 90%;

    .input {
      font-family: PingFang;

      // font-size: 20px;

      width: 100%;
      height: 60px;

      background: none;
      background-color: rgba(255, 255, 255, 0.7);
      border-radius: 8px;
      border-image: linear-gradient(180deg, rgba(0, 0, 0, 0.0578) -3%, rgba(0, 0, 0, 0.0578) 99%, rgba(0, 0, 0, 0.4458) 100%) 1;
      resize: none;

      // box-sizing: border-box;
      // -webkit-box-sizing: border-box;
      // -moz-box-sizing: border-box;

      border: 1px solid #d2d2d2;
      background-color: #fff;
      outline: none;
      // padding: 10px;
      // padding-top: 8px;
    }

    .input::-webkit-scrollbar {
      width: 0;
      height: 0;
    }
  }

  .right {
    .chatRoomSend {
      width: 40px;
      height: 30px;

      background-image: url(./send.png);
      background-size: 100% 100%;
      background-repeat: no-repeat;
    }

    .chatRoomSend-gray {
      filter: grayscale(100%);
    }

    // }
  }
}

/* .chatRoomInput {
//     height: 85px;
//     // position: fixed;
//     padding-left: 6rem;
//     // padding-right: 6rem;
//     background-color: #F9F9F9;

//     .left {
//         float: left;

//         position: relative;
//         left: -100px;
//         width: 100px;
//         height: 100px;
//         margin-left: -100%;
//         user-select: none;
//     }

//     .center {
//         float: left;
//         width: 100%;
//         // height: 100px;
//         user-select: none;
//         margin-top: 10px;

//         // background: lightgray;
//     }

//     .right {
//         float: left;

//         position: relative;
//         left: -100px;
//         width: 100px;
//         height: 100px;
//         margin-left: -100px;
//         user-select: none;
//         // background: lightgreen;
//     }*/


@media screen and (max-width: 768px) {
  .chatRoomInput {
    display: flex;
    justify-content: space-around;
    align-items: center;
    gap: 10px;

    .left {
      margin-left: 10px;

      .chatRoomSkill {
        width: 40px;
        height: 40px;
      }
    }

    .center {
      .input {
        height: 50px;
        // width: 200px;
        font-size: 16px;
      }
    }

    .right {
      margin-right: 10px;

      .chatRoomSend {
        width: 40px;
        height: 30px;
        background-image: url(./send.png);
        background-size: 100% 100%;
        background-repeat: no-repeat;
      }
    }
  }
}
</style>
