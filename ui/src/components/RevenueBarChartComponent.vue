<script setup lang="ts">
import {Bar} from 'vue-chartjs'
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from 'chart.js'
import {ref, watch} from 'vue'

const props = defineProps(['event', 'comparisonEvent', 'industry'])

watch(() => props.industry, () => {
  loading.value = true;
  const requestUrl = props.industry
      ? `http://localhost:8080/revenue-score/industry?from=${new Date(props.event.start).toISOString()}&to=${new Date(props.event.end).toISOString()}&industry=${encodeURIComponent(props.industry)}`
      : `http://localhost:8080/revenue-score?from=${new Date(props.event.start).toISOString()}&to=${new Date(props.event.end).toISOString()}`

  fetch(requestUrl)
      .then(res => res.json()).then((response) => {
    const datasets = []
    datasets.push(
        {
          label: props.industry ? `${props.event.displayName} (${props.industry})` : props.comparisonEvent.displayName,
          backgroundColor: '#BFDBFE',
          borderColor: '#2563EB',
          borderWidth: 2,
          borderSkipped: false,
          borderRadius: 2,
          data: response.map(it => it.revenue)
        });
    chart.value.datasets = datasets
    loading.value = false;
    loadComparision()
  })
      .catch((error) => alert(error));

});

watch(() => props.comparisonEvent, () => {
  loadComparision()
});

const loadComparision = () => {
  loading.value = true;
  if (!props.comparisonEvent) {
    const datasets = chart.value.datasets
    if (datasets.length > 1)
      datasets.pop()
    chart.value.datasets = datasets
    new Promise( resolve => setTimeout(resolve, 100)).then(() => loading.value = false)
    return;
  }
  const requestUrl = props.industry
      ? `http://localhost:8080/revenue-score/industry?from=${new Date(props.comparisonEvent.start).toISOString()}&to=${new Date(props.comparisonEvent.end).toISOString()}&industry=${encodeURIComponent(props.industry)}`
      : `http://localhost:8080/revenue-score?from=${new Date(props.comparisonEvent.start).toISOString()}&to=${new Date(props.comparisonEvent.end).toISOString()}`

  fetch(requestUrl)
      .then(res => res.json()).then((response) => {
    const datasets = chart.value.datasets
    if (datasets.length > 1)
      datasets.pop()
    datasets.push(
        {
          label: props.industry ? `${props.comparisonEvent.displayName} (${props.industry})` : props.comparisonEvent.displayName,
          backgroundColor: '#f8e1b1',
          borderColor: '#eb8c25',
          borderWidth: 2,
          borderSkipped: false,
          borderRadius: 2,
          data: response.map(it => it.revenue)
        });
    chart.value.datasets = datasets
    loading.value = false;
    console.log('loaded comparison event data', datasets)
  })
      .catch((error) => alert(error));
}

const loading = ref(true)
const chart = ref({
  labels: [
    '-5 Days',
    '-4 Days',
    '-3 Days',
    '-2 Days',
    '-1 Days',
    'Event',
    '+1 Days',
    '+2 Days',
    '+3 Days',
    '+4 Days',
    '+5 Days',
  ],
  datasets: []
})

const getData = () => {
  const requestUrl = props.industry
      ? `http://localhost:8080/revenue-score/industry?from=${new Date(props.event.start).toISOString()}&to=${new Date(props.event.end).toISOString()}&industry=${encodeURIComponent(props.industry)}`
      : `http://localhost:8080/revenue-score?from=${new Date(props.event.start).toISOString()}&to=${new Date(props.event.end).toISOString()}`
  fetch(requestUrl)
      .then(res => res.json()).then((response) => {
    loading.value = false;
    chart.value.datasets = [
      {
        label: props.industry ? `${props.event.displayName} (${props.industry}` : props.event.displayName,
        backgroundColor: '#BFDBFE',
        borderColor: '#2563EB',
        borderWidth: 2,
        borderSkipped: false,
        borderRadius: 2,
        data: response.map(it => it.revenue)
      }
    ]
  })
      .catch((error) => alert(error));
}

getData();

if (props.comparisonEvent) {
  console.log("now?")
}


const options = {
  responsive: true,
  maintainAspectRatio: true,
}


ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)
</script>

<template>
  <Bar v-if="!loading" :data="chart" :options="options"/>
</template>
