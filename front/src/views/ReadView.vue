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
  <el-row>
    <el-col>
      <h2 class="title">{{ goods.title }}</h2>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2022-07-05</div>
      </div>

    </el-col>
  </el-row>


  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ goods.content }}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
  .title {
    font-size: 1.6rem;
    font-weight: 600;
    color: #383838;
    margin: 0;
  }

  .sub {
    margin-top: 4px;
    font-size: 0.78rem;

    .regDate {
      margin-left: 10px;
      color: #0000bf;
    }
  }

  .content {
    font-size: 0.95rem;
    margin-top: 8px;
    color: #7e7e7e;
    white-space: break-spaces;
    line-height: 1.5;
  }
</style>