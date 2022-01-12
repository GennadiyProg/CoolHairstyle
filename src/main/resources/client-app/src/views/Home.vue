<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import { onMounted } from "@vue/runtime-core";

export default {
  name: "Home",
  components: {
    HelloWorld,
  },
  setup() {
    onMounted(async () => {
      console.log("ПРОСТО ФЕТЧУ");
      const res = await fetch("/test");
      const result = await res.json();
      console.log(result);
    });
  },
};
</script>
