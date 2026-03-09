<template>
    <div class="myBubble" :class="{ 'banana-mode': props.reversed }">
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
    </div>
</template>

<script setup>

const props = defineProps({
    userName: String,
    msg: String,
    reversed: Boolean,
})

</script>

<style scoped lang="scss">
.myBubble {
    width: 100%;
    overflow: hidden;
    padding-top: 20px;
    // background-color: rgb(18, 35, 29);

    .context {
        float: right;
        width: auto;
        max-width: 60%;
        margin-right: 20px;

        .username {
            float: right;
            color: grey;

            word-break: break-all;
            white-space: pre-wrap;
            // background-color: rgb(200, 127, 255);
        }

        .bubble {
            float: right;
            margin-top: 20px;
            margin-right: 20px;
            padding: 10px;

            max-width: 100%;
            

            line-height: 28px;
            font-size: 18px;


            word-break: break-all;
            white-space: pre-wrap;
            

            border-radius: 10px;
            color: white;
            background: rgb(18,183,245);
            border: 1px solid rgba(0, 0, 0, 0.0578);

        }
    }

}
@media screen and (max-width: 768px) {
    .myBubble {
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
