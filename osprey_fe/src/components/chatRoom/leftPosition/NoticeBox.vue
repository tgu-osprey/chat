<template>
    <div style="margin-top: 2rem;">
        <div class="noticeBox" v-for="(item, index) in notice">
            <div style="height: 2.5rem; width: 0.3rem;
             background-image: -webkit-linear-gradient(left, #A7B2F9, #ADE0FE);
              position: absolute;
              top: 0.8rem;
              "></div>
            <div class="title">
                <span>{{ item.title }}</span>
            </div>
            <div class="content">
                <span>{{ item.content }}</span>
                <span v-if="item.url !== undefined && item.url !== ''" @click="goPage(item.url)" class="url">点这里</span>
            </div>
        </div>
    </div>
</template>
<script setup>
import { ref, reactive, h, render, watch, onMounted } from 'vue';
import cache from '../../../utils/cache';
const notice = ref([
    {
        title: '欢迎来到鱼鹰聊天',
        content: '这是一个基于vue3 + springboot的聊天室，目前在测试中，如果你有什么建议或发现bug欢迎反馈给我们。'
    }
])
function goPage (url){
    window.open(url);
}
</script>
<style scoped lang="scss">
.noticeBox {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    padding: 1rem;
    position: relative;
    margin-bottom: 0.6rem;

    .title {
        width: 100%;
        display: flex;
        font-size: 1.35rem;
        font-weight: bold;
        padding-left: 1rem;
        margin-bottom: 0.8rem;
    }

    .content {
        width: 83%;
        font-size: 1.1rem;
        padding-left: 1rem;
        line-height: 1.3;
    }

    .url {
        color: #6e82ff;
        cursor: pointer;
        border-bottom: 1px solid #6e82ff;
    }
}
</style>