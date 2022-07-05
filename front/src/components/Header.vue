<template>
  <el-header class="header">
  <!--        <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/create">상품 등록</RouterLink> -->
  <el-menu mode="horizontal" router>
    <el-menu-item index="/">Home</el-menu-item>
    <el-menu-item index="/create">상품등록</el-menu-item>
  </el-menu>
  </el-header>
</template>

<script setup lang="ts"></script>

<style scoped>
.header{
  padding: 0;
  height: 60px;
}
</style>