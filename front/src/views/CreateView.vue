<script setup lang="ts">
import {ref} from "vue";

import axios from 'axios';
import {useRouter} from "vue-router";

const title = ref("")
const content = ref("")

const router = useRouter()

const create = function (){
  axios.post("/api/goods",{
    title : title.value,
    content : content.value,
  })
  .then(() => {
    router.replace({name: "home"});
  });
};
</script>

<template>
  <div>
   <el-input v-model="title" type="text" placeholder="상품명을 입력해주세요."/>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15" placeholder="상품정보를 입력해주세요"/>
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="create()">상품 등록</el-button>
    </div>
  </div>

</template>

<style>

</style>