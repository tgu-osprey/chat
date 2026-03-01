<template>
  <div class="box zoom">
    <div class="title">
      <div class="chatRoomSkill"></div>
      <div>技能包</div>
    </div>
    <div class="all-skill">
      <div class="skill" v-for="(item, index) in list">
        <div class="skill-name">{{ item.name }}</div>
        <div class="skill-des">{{ item.description }}</div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue'
let style = document.createElement('style');
style.setAttribute('type', 'text/css');
document.head.appendChild(style);
let sheet = style.sheet;

const list = ref([
  {
    id: 1,
    name: '技能1',
    description: '这里是这个技能的描述。各种各样的描述，中文的英文的一大堆的描述描述一下这个技能。'
  },
  {
    id: 2,
    name: '技能2',
    description: '这里是这个技能的描述。各种各样的描述，中文的英文的一大堆的描述描述一下这个技能。'
  },
  {
    id: 3,
    name: '技能2',
    description: '这里是这个技能的描述。各种各样的描述，中文的英文的一大堆的描述描述一下这个技能。这里是这个技能的描述。各种各样的描述，中文的英文的一大堆的描述描述一下这个技能。这里是这个技能的描述。各种各样的描述，中文的英文的一大堆的描述描述一下这个技能。'
  }
])
//根据技能数量添加不同的keyframes
sheet.insertRule(
  `@keyframes zoom-in {
    0% {
      width: 0;
      height: 0;
      margin-top: ` + (3.5 + list.value.length * 7) + `rem;
    }

    100% {
      width: 30rem;
      height: ` + (3.5 + list.value.length * 7) + `rem;
      
    }
  }`
);
</script>
<style lang="scss" scoped>
.box {
  background-color: #fff;
  box-shadow: 8.05px 24.15px 89.45px -11.63px rgba(22, 52, 80, 0.1);
  border-radius: 30px;
  overflow: hidden;
  // width: 30rem;

  padding: 0.5rem 1.4rem 1.7rem 1.4rem;

  .title {
    height: 3rem;
    display: flex;
    align-items: center;
    font-size: 1.3rem;
    font-weight: bold;
    overflow: hidden;

    .chatRoomSkill {
      width: 50px;
      height: 50px;
      background-image: url(../skill.png);
      background-size: 100% 100%;
      background-repeat: no-repeat;
    }
  }

  .all-skill {
    margin-top: 0.5rem;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    gap: 1.5rem;
    width: 100%;
  }

  .skill {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-image: -webkit-linear-gradient(left, #ECB1F6, #CBE4F6);
    padding: 1rem 1.5rem 1rem 1.5rem;
    gap: 1rem;
    width: 90%;
    border-radius: 16px;
    cursor: pointer;
    box-shadow: 2px 5px 15px 2px rgba(0, 0, 0, 0.082);

    .skill-name {
      color: #fff;
      font-size: 1.4rem;
      font-weight: bold;

      width: 18rem;
      text-align: center;
      height: 3.3rem;
      line-height: 3.3rem;
      background-color: #E461C9;
      border-radius: 12px;
      overflow: hidden;
    }

    .skill-des {
      width: 60rem;
      line-height: 1.2;
      font-size: 1.1rem;
      color: #E461C9;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
    }
  }
}

.zoom {
  animation: zoom-in ease 0.5s;
  animation-iteration-count: 1;
  animation-fill-mode: forwards;
}
@media screen and (max-width: 768px){

  //不显示技能包的详细信息
  .box{
    display: none;
  }
}
</style>
