<script setup lang="ts">
</script>

<template>
  <input type="text" placeholder="Search.." class="search">

  <div class="container">
    <div class="itemContainer">
      <img class="image"
           src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSsfW0S9NsXsSBxDdSgrJkxnjTR47XaBmphEg&usqp=CAU"
           alt="Red Bull Play Streets"/>
      <div class="textContainer">
        <h1>Red Bull Play Streets</h1>
        <p>Saturday, 01.01.2022</p>
        <div style="flex-grow: 1"/>
        <div class="badgesContainer">
          <p class="badge">Revenue: +30%</p>
          <p class="badge">Visitors: 7000</p>
          <p class="badge">Score: 90</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  gap: 32px;
  flex-wrap: wrap;
}

.itemContainer {
  position: relative;
  box-sizing: border-box;
  width: 48%;

  display: flex;
  flex-direction: row;

  border-radius: 32px;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 2px 6px 2px;
  transition: transform .5s ease;
}

.itemContainer:hover {
  transform: scale(1.02);
  cursor: pointer;
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

.badgesContainer {
  display: flex;
  gap: 8px;
  flex-flow: row wrap;
}

.badge {
  background: #BFDBFE;
  padding: 4px 8px;
  border-radius: 24px;
  color: #2563EB;
  font-weight: 700;
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
</style>