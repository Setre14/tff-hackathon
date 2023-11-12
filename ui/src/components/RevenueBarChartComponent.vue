<script setup lang="ts">
import {Bar} from 'vue-chartjs'
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from 'chart.js'
import {ref} from 'vue'

const props = defineProps(['from', 'to'])

const revenue = ref([])

const getData = () => {
  console.log("request data")
  console.log(new Date(props.from).toISOString())
  console.log(new Date(props.to).toISOString())
  fetch(`http://localhost:8080/revenue?from=${new Date(props.from).toISOString()}&to=${new Date(props.to).toISOString()}`)
      .then(res => res.json()).then((response) => {
    console.log(response)
    revenue.value = response
  })
      .catch((error) => alert(error));
}

getData();

const data = {
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
  datasets: [
    {
      label: 'Red Bull Play Streets',
      backgroundColor: '#BFDBFE',
      borderColor: '#2563EB',
      borderWidth: 2,
      borderSkipped: false,
      borderRadius: 2,
      data: [40, 20, 12, 39, 10, 40, 39, 80, 40, 20, 12, 11]
    },
      {
        label: 'Other',
        backgroundColor: '#f8e1b1',
        borderColor: '#eb8c25',
        borderWidth: 2,
        borderSkipped: false,
        borderRadius: 2,
        data: [40, 20, 12, 39, 10, 40, 39, 80, 40, 20, 12, 11]
      }
  ]
}

const options = {
  responsive: true,
  maintainAspectRatio: true,
}


ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)
</script>

<template>
  <Bar :data="data" :options="options"/>
</template>
