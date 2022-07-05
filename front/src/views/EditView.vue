<script setup lang="ts">

import {useRouter} from "vue-router";
import {defineProps, ref} from "vue";
import axios from "axios";

const router = useRouter()

const goods = ref({
  id: 0,
  title: "",
  content: "",
})

const props = defineProps({
  goodsId: {
    type :[Number, String],
    require : true,
  },
});

axios.get(`/api/goods/${props.goodsId}`).then((response) => {
  goods.value = response.data;
});

const edit = () =>{
  axios.patch(`/api/goods/${props.goodsId}`, goods.value).then(() => {
    router.replace({name: "home"})
  });
}
</script>

<template>
  <div>
    <el-input v-model="goods.title"/>
  </div>

  <div class="mt-2">
    <el-input v-model="goods.content" type="textarea" rows="15"/>
  </div>

  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정 완료</el-button>
  </div>

</template>

<style>

</style>