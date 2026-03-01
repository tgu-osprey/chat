<template>
    <div class="header">
        <Logo width="30" height="25"></Logo>
        <div>鱼鹰聊天</div>
    </div>
</template>
<script setup>
import Logo from './icon/Logo.vue'
</script>
<style scoped lang="scss">
.header {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 25px;
    font-weight: bold;
    gap: 10px;
    user-select: none;
}
@media screen and (max-width: 768px) {
    .header{
        div{
            display: none;
        }
    }
}
</style>