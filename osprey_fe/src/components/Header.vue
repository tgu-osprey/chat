<template>
  <div>
    <LogoTitle class="logo-title"></LogoTitle>
    <Cat class="cat" :width="144" :height="120" @click="isCatRun = !isCatRun" :class="[isCatRun ? 'cat-run' : '']" />
  </div>
</template>
<script setup>
import { ref } from 'vue'
import LogoTitle from './LogoTitle.vue';
import Cat from './icon/Cat.vue'
const isCatRun = ref(false)
</script>
<style scoped lang="scss">
.logo-title {
  position: fixed;
  width: 100%;
  bottom: 6%;
}
.cat {
  position: fixed;
  bottom: -20px;
  left: 5%;
  width: 100px;
  height: 100px;
  cursor: pointer;
}

.cat-run {
  animation: run ease 3s;
  animation-iteration-count: 1;
  animation-fill-mode: forwards;
}

@keyframes run {
  0% {
    left: 5%;
    /*初始状态 透明度为0*/
  }

  100% {
    left: 70%;
    /*结尾状态 透明度为1*/
  }
}
</style>