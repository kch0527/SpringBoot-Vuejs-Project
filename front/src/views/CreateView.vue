<script setup lang="ts">
import {ref} from "vue";

import axios from 'axios';

const title = ref("")
const content = ref("")

const create = function (){
  axios.post("/api/goods",{
    title : title.value,
    content : content.value
  });
}
</script>

<template>
  <div>
   <el-input v-model="title" type="text" placeholder="상품명을 입력해주세요."/>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15" placeholder="상품정보를 입력해주세요"/>
  </div>

  <div>
    <el-button type="primary" @click="create()">상품 등록</el-button>
  </div>

</template>

<style>

</style>