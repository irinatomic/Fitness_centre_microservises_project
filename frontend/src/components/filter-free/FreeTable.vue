<template>
  <div class="table-container">
    <table class="table">
      <thead>
        <tr>
          <th>Gym</th>
          <th>Date</th>
          <th>Time</th>
          <th>Reserve</th>
        </tr>
      </thead>
      <tbody>
        <FreeRow v-for="timeSlot in timeSlots" :key="timeSlot.id" :timeSlot="timeSlot" v-if="timeSlot" />
      </tbody>
    </table>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import FreeRow from './FreeRow.vue';

export default {
  name: 'FreeTable',
  components: {
    FreeRow,
  },
  computed: {
    ...mapState(['timeSlots']),
  },
};
</script>

<style scoped>
.table-container {
  min-height: 150px;
  height: 20%;
  overflow-y: auto;
}

table {
  width: 500px;
  border-collapse: collapse;
}

th {
  background-color: #007582;
  color: white;
  padding: 8px;
}
</style>