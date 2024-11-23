<template>
  <div id="app">
    <!-- 布局 上面header 下面main -->
    <container-view></container-view>
     <!-- <test-view></test-view> -->
  </div>
</template>

<script>
import ContainerView from './views/ContainerView.vue';
//import TestView from './views/TestView.vue';

export default{
  components:{ ContainerView/*TestView*/ },
  data () {
    return {
      
    }
  }
}
</script>

<style>

</style>