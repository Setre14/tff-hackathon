<script setup lang="ts">
import {ref} from 'vue'

const events = ref([])

const getData = () => {
  fetch('http://localhost:8080/events')
      .then(res => res.json()).then((response) => {
    events.value = response
  })
      .catch((error) => {
        alert(error)
      });
}

getData();

const getImage = (name: string) => {
  switch (name) {
    case "adidas-infinite-trails": return "https://infinite-trails.com/wp-content/uploads/2021/09/icorless_ait2021_-02312.jpg"
    case "fis-snowboard-weltcup": return "https://www.gastein.com/uploads/tx_webxbookingemo/ImageStorage/SWC_a853ec2aceca7ebd59ddc51ec29ac435.jpg"
    case "fis-snowboard-wc": return "https://www.gastein.com/uploads/tx_webxbookingemo/ImageStorage/SWC_a853ec2aceca7ebd59ddc51ec29ac435.jpg"
    case "red-bull-playstreets": return "https://cdn.shopify.com/s/files/1/0666/7635/9400/t/3/assets/Red-Bull-Playstreets-Returns-1-1024x683.jpg?v=1678733489"
    case "ski-classics": return "https://skiclassics.com/wp-content/uploads/2023/11/Stakston020423cm36259-1-scaled.jpg"
  }
}
</script>

<template>
  <input type="text" placeholder="Search.." class="search">
  <div class="container">
    <router-link v-for="event in events" class="card" :to="{ name: 'event', params: { name: event.name }}">
      <img class="image"
           :src="getImage(event.name)"
           alt="Event Image"/>
      <div class="textContainer">
        <h1>{{ event.displayName }}</h1>
        <p>{{ new Date(event.eventDate).toDateString() }}</p>
        <div style="flex-grow: 1"/>
        <div class="badgesContainer">
          <p class="badge">Revenue: {{ event.revenue }}</p>
          <p class="badge">Visitors: {{ event.visitors }}</p>
          <p class="badge">Score: {{ event.score }}</p>
        </div>
      </div>
    </router-link>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  gap: 32px;
  flex-wrap: wrap;
  margin: 0 64px;
}

.card {
  width: 48%;
  flex-direction: row;
  padding: 0;
}


.image {
  width: 200px;
  height: 200px;
  border-radius: 32px;
  object-fit: cover;
}

.textContainer {
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  padding: 24px;
  height: 100%;
}


.search {
  width: 70%;
  margin: 24px 15% 64px 15%;
  background: #dfdfdf;
  border: none;
  border-radius: 32px;
  height: 48px;
  text-align: center;
  font-size: large;
}

a, a:visited {
  text-decoration: none;
  color: var(--color-text);
}
</style>