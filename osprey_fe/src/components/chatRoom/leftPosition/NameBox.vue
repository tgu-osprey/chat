<template>
    <div style="">
        <div class="title">{{randomWelcome()}}：</div>
        <div class="state">{{ getTimeState()}}{{ name }}</div>
        <div ></div>
    </div>
</template>
<script setup>
import { ref, reactive, h, render, watch, onMounted } from 'vue';
import cache from '@/utils/cache';
import {getTimeState} from '@/utils/timeFormat';
const name = ref('')
const welcome = ref([
    '哦哈哟',
    '鉴定为',
    '欢迎回来',
    '这里是鱼鹰',
    '嘘~~'
])
// 随机抽取欢迎语
function randomWelcome(){
    const index = Math.floor(Math.random() * welcome.value.length)
    return welcome.value[index]
}
onMounted(() => {
    name.value = cache.local.get('username')
});
</script>
<style scoped lang="scss">
.state {
    margin-left: 1.5rem;
    font-size: 1.5rem;
    color: #000;
    font-weight: bold;
}
.title{
    margin-top: 1rem;
    margin-left: 1.5rem;
    font-size: 1.5rem;
    color: #000;
    margin-bottom: 10px;
}
</style>