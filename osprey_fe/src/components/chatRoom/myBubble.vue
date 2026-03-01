<template>
    <div class="myBubble">
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
    </div>
</template>

<script setup>

const props = defineProps({
    userName: String,
    msg: String,
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
</style>
