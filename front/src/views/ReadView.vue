<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

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

const router = useRouter();

const moveToEdit = () => {
  router.push({name: "edit", params: {goodsId: props.goodsId}});
}

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

  <el-button type="warning" @click="moveToEdit()">수정</el-button>
</template>