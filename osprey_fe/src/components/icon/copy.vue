<template>
    <div>
        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="none" version="1.1"
            :width="props.width" :height="props.height" viewBox="0 0 14 15">
            <g>
                <path
                    d="M3.11111,3L3.11111,0.749999C3.11111,0.335786,3.45933,0,3.88889,0L13.2222,0C13.6518,0,14,0.335786,14,0.749999L14,11.25C14,11.6642,13.6518,12,13.2222,12L10.8889,12L10.8889,14.25C10.8889,14.664,10.5389,15,10.1057,15L0.783222,15C0.351721,15.0025,0.000423908,14.6661,0,14.25L0.00233335,3.75C0.00233335,3.336,0.352333,3,0.785556,3L3.11111,3ZM4.66667,3L10.8889,3L10.8889,10.5L12.4444,10.5L12.4444,1.5L4.66667,1.5L4.66667,3Z"
                    fill="#92B8DC" fill-opacity="1" />
            </g>
        </svg>
    </div>
</template>
<script setup>
import { defineProps } from 'vue';
const props = defineProps({
    width: {
        type: Number,
        default: 14
    },
    height: {
        type: Number,
        default: 15
    }
})
</script>
<style></style>