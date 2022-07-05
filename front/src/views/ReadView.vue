<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";

const props = defineProps({
  goodsId: {
    type :[Number, String],
    require : true,
  },
});

//추후 수정
const goods = ref({
  id: 0,
  title: "",
  content: "",
});

//추후 수정
onMounted(() => {
  axios.get(`/api/goods/${props.goodsId}`).then((response) => {
   goods.value = response.data;
  });
})
</script>

<template>
  <h2>{{ goods.title }}</h2>
  <div>{{ goods.content }}</div>
</template>