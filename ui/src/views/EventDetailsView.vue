<script setup lang="ts">
import RevenueBarChartComponent from "@/components/RevenueBarChartComponent.vue";

import {ref} from 'vue'
import {useRoute} from "vue-router";
import MasterCardIcon from "@/components/MasterCardIcon.vue";

const route = useRoute();

const event = ref(null)
const comparisonEvent = ref(null)
const otherEvents = ref([])
const showSelect = ref(false)
const industries = ref([])
const selectedIndustry = ref(null)

const getData = async () => {
  await fetch(`http://localhost:8080/events/${route.params.name}`)
      .then(res => res.json()).then((response) => event.value = response)
      .catch((error) => alert(error));

  await fetch(`http://localhost:8080/events`)
      .then(res => res.json()).then((response) => otherEvents.value = response.filter(e => e.name != event.value.name))
      .catch((error) => alert(error));

  await fetch(`http://localhost:8080/revenue/industries`)
      .then(res => res.json()).then((response) => {
        industries.value = response
        selectedIndustry.value = response[0]
        console.log('set industries', industries.value)
      })
      .catch((error) => alert(error));
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

const setComparison = (event: any) => {
  comparisonEvent.value = event
  showSelect.value = false
}
</script>

<template>
  <div v-if="event">
    <button class="backButton" @click="$router.back">Back</button>
    <button class="compareButton" @click="showSelect = !showSelect">Compare</button>
    <button v-if="comparisonEvent" class="clearButton" @click="comparisonEvent = null">Clear selection</button>
    <div class="compareSelect" v-if="showSelect">
      <p v-for="event in otherEvents" class="compareSelectItem" @click="setComparison(event)">{{event.displayName}}, {{new Date(event.eventDate).toDateString()}}</p>
    </div>

    <img
        class="image"
         :src="getImage(event.name)"
         alt="Event Image"/>
    <div class="content">
      <h1>{{event.displayName}}, {{new Date(event.eventDate).toDateString()}}{{comparisonEvent ? ` ⇔ ${comparisonEvent.displayName}, ${new Date(comparisonEvent.eventDate).toDateString()}`  : ''}}</h1>
      <div class="badgesContainer">
        <p class="badge">Spend Score: {{event.revenue}}%</p>
        <p class="badge">Visitors: {{event.visitors}}</p>
        <span v-if="comparisonEvent" style="width: 50px"/>
        <p v-if="comparisonEvent" class="badge otherBadge">Spend Score: {{comparisonEvent.revenue}}%</p>
        <p v-if="comparisonEvent" class="badge otherBadge">Visitors: {{comparisonEvent.visitors}}</p>
      </div>

      <div class="stats" >
        <div class="card">
          <h2 style="text-align: center; margin-bottom: 12px">Daily Spend Score</h2>
          <MasterCardIcon style="position: absolute; right: 24px; width: 48px; height: 48px"/>
          <RevenueBarChartComponent :event="event" :comparison-event="comparisonEvent" style="width: 500px; height: 300px"/>
        </div>
        <div class="card">
          <h2 style="text-align: center; margin-bottom: 12px">Daily Spend Score</h2>
          <MasterCardIcon style="position: absolute; right: 24px; width: 48px; height: 48px"/>
          <RevenueBarChartComponent :event="event" :comparison-event="comparisonEvent" :industry="selectedIndustry" style="width: 500px; height: 300px"/>
          <div class="industrySelect">
            <select @change="(e) => selectedIndustry = e.target.value">
              <option v-for="industry in industries" :value="industry">{{industry}}</option>
            </select>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.industrySelect {
  position: absolute;
  margin-top: 12px;
  width: 200px;
}

.industrySelect > select {
  width: 130px;
}

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