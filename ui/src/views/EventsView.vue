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
    case "fis-snowboard-wc-2022-01-11": return "https://www.gastein.com/fileadmin/userdaten/bilder/Events/Winter/Snowboard_Worldcup/fis-snowboard-worldcup-3-gastein-winter-urlaub-berge-therme-salzburger-land-ski-amade-c-bad-gastein_simon-hutter-171.jpg"
    case "ski-classics-2022-12-10": return "https://skiclassics.com/wp-content/uploads/2023/11/Stakston020423cm36259-1-scaled.jpg"
    case "fis-snowboard-wc-2023-01-11": return "https://www.gastein.com/uploads/tx_webxbookingemo/ImageStorage/SWC_a853ec2aceca7ebd59ddc51ec29ac435.jpg"
    case "fis-snowboard-wc-2023-01-10": return "https://www.gastein.com/uploads/tx_webxbookingemo/ImageStorage/SWC_a853ec2aceca7ebd59ddc51ec29ac435.jpg"
    case "red-bull-play-streets-2023-02-10": return "https://cdn.shopify.com/s/files/1/0666/7635/9400/t/3/assets/Red-Bull-Playstreets-Returns-1-1024x683.jpg?v=1678733489"
    case "ski-classics-2022-12-11": return "https://skiclassics.com/wp-content/uploads/2023/11/Stakston020423cm36259-1-scaled.jpg"
    case "fis-snowboard-wc-2022-01-12": return "https://www.gastein.com/fileadmin/userdaten/bilder/Events/Winter/Snowboard_Worldcup/fis-snowboard-worldcup-3-gastein-winter-urlaub-berge-therme-salzburger-land-ski-amade-c-bad-gastein_simon-hutter-171.jpg"
    case "adidas-infinite-trails-2022-09-10": return "https://infinite-trails.com/wp-content/uploads/2021/09/icorless_ait2021_-02312.jpg"
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
          <p :class="{ 'greenBadge': event.revenue >= 110, 'redBadge': event.revenue <= 90, 'orangeBadge': event.revenue > 90 && event.revenue < 110}">Spend Score: {{ event.revenue }}%</p>
          <p class="badge">Visitors: {{ event.visitors }}</p>
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