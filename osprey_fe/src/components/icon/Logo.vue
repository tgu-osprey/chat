<template>
    <div style="display: flex; justify-content: center; align-items: center;">
        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="none" version="1.1" :width="props.width" :height="props.height" viewBox="0 0 372.1719970703125 326.6659851074219">
            <g>
                <path d="M160.083,217.778L103.201,160.895L268.972,160.895L212.089,217.777C212.089,256.782,221.841,294.162,239.718,326.666L372.172,203.963L372.172,0L346.981,0C258.408,0,186.086,71.509,186.086,160.895C186.086,71.509,113.764,0,25.1907,0L0,0L0,203.963L132.454,326.666C150.332,294.162,160.083,256.783,160.083,217.778Z" fill-rule="evenodd" fill="#009DFF" fill-opacity="1"/>
            </g>
        </svg>
    </div>
</template>
<script setup>
const props = defineProps({
    width: {
        type: String,
        default: '37.2'
    },
    height: {
        type: String,
        default: '32.7'
    }
})
</script>
<style lang="scss" scoped>

</style>