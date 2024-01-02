<template>
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Mail Type</th>
        <th>Subject</th>
        <th>Sent To</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="email in emails" :key="email.id">
        <td>{{ email.id }}</td>
        <td>{{ email.timestamp }}</td>
        <td>{{ email.mailType }}</td>
        <td>{{ email.subject }}</td>
        <td>{{ email.sentTo }}</td>
        <!-- Add more columns as needed -->
      </tr>
    </tbody>
  </table>
</template>
  
<script>
import { mapState, mapActions } from 'vuex';

export default {
  name: 'EmailsTable',
  computed: {
    ...mapState(['emails']),
  }
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

td {
  border: 1px solid #225E70;
  padding: 8px;
}
</style>
  