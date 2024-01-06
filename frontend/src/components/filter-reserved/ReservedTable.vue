<template>
  <table class="table">
    <thead>
      <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Time</th>
        <th>Training</th>
        <th>SignedUp</th>
        <th>Capacity</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      <ReservedRow v-for="session in sessions" :key="session.id" :session="session" />
    </tbody>
  </table>
</template>
  
<script>
import { mapState } from 'vuex';
import ReservedRow from './ReservedRow.vue';

export default {
  name: 'ReservedTable',
  components: {
    ReservedRow,
  },
  computed: {
    ...mapState(['sessions']),
  },
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th {
  background-color: #007582;
  color: white;
  padding: 8px;
}
</style>
  