<script setup lang="ts">
import RevenueBarChartComponent from "@/components/RevenueBarChartComponent.vue";

import {ref} from 'vue'
import {useRoute} from "vue-router";

const route = useRoute();

const event = ref(null)
const comparisonEvent = ref(null)
const otherEvents = ref([])
const showSelect = ref(false)

const getData = async () => {
  await fetch(`http://localhost:8080/events/${route.params.name}`)
      .then(res => res.json()).then((response) => event.value = response)
      .catch((error) => alert(error));

  await fetch(`http://localhost:8080/events`)
      .then(res => res.json()).then((response) => otherEvents.value = response.filter(e => e.name != event.value.name))
      .catch((error) => alert(error));
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

const setComparison = (event: any) => {
  comparisonEvent.value = event
  showSelect.value = false
}
</script>

<template>
  <div>
    <button class="backButton" @click="$router.back">Back</button>
    <button class="compareButton" @click="showSelect = !showSelect">Compare</button>
    <button v-if="comparisonEvent" class="clearButton" @click="comparisonEvent = null">Clear selection</button>
    <div class="compareSelect" v-if="showSelect">
      <p v-for="event in otherEvents" class="compareSelectItem" @click="setComparison(event)">{{event.displayName}}, {{new Date(event.eventDate).toDateString()}}</p>
    </div>

    <img class="image"
         :src="getImage(event.name)"
         alt="Event Image"/>
    <div class="content">
      <h1>{{event.displayName}}, {{new Date(event.eventDate).toDateString()}}{{comparisonEvent ? ` ⇔ ${comparisonEvent.displayName}, ${new Date(comparisonEvent.eventDate).toDateString()}`  : ''}}</h1>
      <div class="badgesContainer">
        <p class="badge">Revenue: {{event.revenue}}</p>
        <p class="badge">Visitors: {{event.visitors}}</p>
        <p class="badge">Score: {{event.score}}</p>
        <span v-if="comparisonEvent" style="width: 50px"/>
        <p v-if="comparisonEvent" class="badge otherBadge">Revenue: {{event.revenue}}</p>
        <p v-if="comparisonEvent" class="badge otherBadge">Visitors: {{event.visitors}}</p>
        <p v-if="comparisonEvent" class="badge otherBadge">Score: {{event.score}}</p>
      </div>

      <div class="stats" >
        <div class="card">
          <h2 style="text-align: center; margin-bottom: 12px">Revenue</h2>
          <RevenueBarChartComponent :from="event.start" :to="event.end" style="width: 500px; height: 300px"/>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.image {
  width: 100%;
  height: 300px;
  object-fit: cover;
}

.content {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 24px;
}

h1 {
  margin-bottom: 8px;
}

.badgesContainer {
  margin-top: 24px;
  align-items: center;
  margin-bottom: 48px;
}

.stats {
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  gap: 24px;
}

.compareButton {
  position: fixed;
  bottom: 24px;
  right: 24px;

  background: #2563EB;
  border-radius: 32px;
  color: white;
  padding: 8px 24px;
  border: none;
  font-size: 18px;
  transition: transform .5s ease;
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 2px 0, rgba(60, 64, 67, 0.15) 0 2px 6px 2px;
}

.clearButton {
  position: fixed;
  bottom: 24px;
  right: 165px;

  background: white;
  border-radius: 32px;
  color: #2563EB;
  padding: 8px 24px;
  border: none;
  font-size: 18px;
  transition: transform .5s ease;
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 2px 0, rgba(60, 64, 67, 0.15) 0 2px 6px 2px;
}

.backButton {
  position: absolute;
  top: 24px;
  left: 24px;

  background: white;
  border-radius: 32px;
  color: #2563EB;
  padding: 8px 24px;
  border: none;
  font-size: 18px;
  transition: transform .5s ease;
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 2px 0, rgba(60, 64, 67, 0.15) 0 2px 6px 2px;
}


.compareButton:hover, .clearButton:hover, .compareSelect:hover, .backButton:hover {
  transform: scale(1.05);
  cursor: pointer;
}

.compareSelect {
  position: fixed;
  bottom: 80px;
  right: 24px;
  transition: transform .5s ease;
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 2px 0, rgba(60, 64, 67, 0.15) 0 2px 6px 2px;
  border-radius: 12px;
}

.compareSelectItem {
  padding: 12px 24px;
  text-align: right;
}

.compareSelectItem:hover {
  font-weight: 700;
}

.otherBadge {
  background: #f8e1b1;
  color: #eb8c25;
}
</style>