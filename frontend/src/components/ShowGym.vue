<template>
    <div class="gym-card">
        <h2> {{ gym.name }} </h2>
        <p class="description"> {{ gym.description }} </p>
        <p> Number of coaches: {{ gym.coachesCount }} </p>
        <p> Opening time: {{ gym.openingTime }}h </p>
        <p> Closing time: {{ gym.closingTime }}h </p>
    </div>
</template>

<script>
export default {
  name: 'ShowGym',
  props: {
    gym: {
      type: Object,
      required: true
    }
  },
}
</script>

<style scoped>
.gym-card {
    background-color: #f7f7f7;
    border: 1px solid #cccccc;
    border-radius: 10px;
    padding: 20px;
    width: 250px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    text-align: left;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin: 5px;
    overflow: hidden;
    color: #008C8B;
}

.description {
    font-style: italic;
    overflow: hidden;
    /* Hide overflowing content */
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    /* Limit number of lines */
    line-height: 1.4;
    /* Line height for multiline truncation */
}
</style>