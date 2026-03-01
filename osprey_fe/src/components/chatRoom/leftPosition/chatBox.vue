<template>
    <div class="chatBox">
        <div>
            <NameBox class="notice" />
            <NoticeBox class="notice"></NoticeBox>
        </div>
        <div class="leave-box">
            <div class="options">
                <div class="text-change">
                    <div>本次身份记忆码</div>
                    <Copy @click="copyToken" style="cursor: pointer;"></Copy>
                </div>
                <div class="token">{{ token }}</div>
            </div>
            <div class="go-run"  @mouseenter="showGoRun = true" @mouseleave="mouseLeave">
                <GoRun></GoRun>
                <div>开润!</div>
            </div>
            <div class="btns" v-if="showGoRun" @mouseenter="mouseInBtns = true" @mouseleave="mouseInBtns = false; showGoRun = false">
                <div class="btn" @click="quitAndClear">留存账号</div>
                <div class="btn" @click="quitAndAbandon">弃用昵称</div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, h, render, watch, onMounted } from 'vue';
import cache from '../../../utils/cache';
import router from '../../../router';
import { abandonName } from '../../../api/login';
import Copy from '../../icon/copy.vue'
import GoRun from '../../icon/GoRun.vue';
import NoticeBox from './NoticeBox.vue'
import NameBox from './NameBox.vue';
import { chatLineArray } from '@/stores/chat'
import pinia from '@/stores'
import { message } from 'ant-design-vue';
const chatLine = chatLineArray(pinia) // 获取聊天队列，pinia必须写，使用外挂载pinia实例

const props = defineProps({
    userName: String,
    msg: String,
    socketData: {
        type: Object,
        default: null
    }
})
const showGoRun = ref(false)
const mouseInBtns = ref(false)
function mouseLeave(){
    setTimeout(()=>{
        if(!mouseInBtns.value) {
            showGoRun.value = false
        }
    }, 300)
}

const token = ref(cache.local.get('token'))

const copyToken = () => {
    if (window.clipboardData) {
        window.clipboardData.setData('text', token.value);
    } else {
        (function () {
            document.oncopy = function (e) {
                e.clipboardData.setData('text', token.value);
                e.preventDefault();
                document.oncopy = null;
            }
        })(token.value);
        document.execCommand('Copy');
    }
    message.success('复制成功')
}
function quitAndClear() {
    // 关闭socket，防止队列重复
    
    router.push({ path: '/' })
    cache.local.remove('token')
    cache.local.remove('username')
    props.socketData.close()

    // 清空目前的聊天记录
    chatLine.chatArray.data = []
}

// 弃用昵称并退出
function quitAndAbandon() {
    abandonName(cache.local.get('token'))
        .then(res => {
            console.log(res)
            quitAndClear()
        })
}

</script>

<style scoped lang="scss">
.chatBox {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    
    .leave-box {
        justify-self: flex-end;
        // position: absolute;
        // bottom: 3.5rem;
        // left: 0.5rem;
        // right: 0.5rem;
        padding: 1rem 0.5rem 0.5rem 0.5rem;
        height: 3.5rem;
        display: flex;
        gap: 10px;
        justify-content: space-around;
        border-top: solid 1px #E0E0E0;
        .options{
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            width: 50%;
            .text-change {
                background-image: -webkit-linear-gradient(left, #EB68E9, #91A59A);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                overflow: hidden;
    
                display: flex;
                justify-content: space-between;
            }
            .token {
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                font-size: 25px;
            }
        }
        .go-run{
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;

            padding: 0rem 1rem 0rem 1rem;
            border-radius: 10px;
            height: 3rem;
            font-size: 23px;
            font-weight: bold;
            text-align: center;
            background-image: -webkit-linear-gradient(left, #A7B2F9, #ADE0FE);
            cursor: pointer;
        }
        .btns{
            position: absolute;
            bottom: 4.7rem;
            margin-left: 11rem;
            display: flex;
            gap: 1rem;
            padding: 1rem 1rem 1rem 1rem;
            // width: 10rem;
            // height: 10rem;
            background-color: #fff;
            box-shadow: 8.05px 24.15px 89.45px -11.63px rgba(22, 52, 80, 0.1);
            border-radius: 10px;
            flex-direction: column;
            .btn{
                background-image: -webkit-linear-gradient(left, #A7B2F9, #ADE0FE);
                border-radius: 10px;
                // width: 6rem;
                // height: 3rem;
                text-align: center;
                line-height: 2.5rem;
                font-size: 20px;
                font-weight: bold;
                padding: 3px 8px 3px 8px;
                cursor: pointer;
            }
        }
    }
}
@media screen and (max-width: 768px) {
    .chatBox {
        width: 100%;
        height: 50px;
        display: flex;
        align-items: center;
        justify-content: center;
        .notice{
            display: none;
        }
        .leave-box {
            width: 100%;
            height: 40px;
            padding: 6px;
            border-top: none;
            .options{
                display: flex;
                flex-direction: column;
                align-content: space-between;
                .text-change{
                    font-size: 16px;
                }
                .token{
                    font-size: 14px;
                }
            }
            .go-run{
                width: 80px;
                height: 40px;
                font-size: 14px;
            }
            .btns{
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: .5rem;
            position: absolute;
            top: 50px;
            right: 25px;
            width: 100px;
            height: 100px;
            z-index: 99;
            .btn{
                background-image: -webkit-linear-gradient(left, #A7B2F9, #ADE0FE);
                border-radius: 10px;
                text-align: center;
                line-height: 2.5rem;
                padding: 0;
                cursor: pointer;
                width: 80px;
                height: 40px;
                font-weight: bold;
                font-size: 16px;
            }
        }
        }
    }
    
}
</style>
