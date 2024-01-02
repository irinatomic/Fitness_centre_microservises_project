<template>
  <div>
    <div clas="filter-options">
      <select class="mail-type" v-model="selectedMailType">
        <option value="ALL">ALL</option>
        <option v-for="tip in mailTypes" :key="tip.id" :value="tip.name"> {{ tip.name }}</option>
      </select>
      <input type="date" v-model="dateFrom">
      <input type="date" v-model="dateTo">
      <button @click="filterEmails">Filter</button>
    </div>

    <div class="table-container">
      <EmailsTable :key="tableKey" />
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import EmailsTable from '../components/EmailsTable.vue';

export default {
  name: 'EmailsAdminView',
  components: {
    EmailsTable
  },
  data() {
    return {
      selectedMailType: 'ALL',
      dateFrom: null,
      dateTo: null,
      tableKey: 0         // Key to force component refresh
    };
  },
  computed: {
    ...mapState(['mailTypes']),
  },
  methods: {
    ...mapActions(['fetchMailTypes', 'fetchEmailsAdmin']),
    filterEmails() {
      this.fetchEmailsAdmin({
        selectedMailType: this.selectedMailType,
        dateFrom: this.dateFrom,
        dateTo: this.dateTo
      });
      this.tableKey++;
    },

  },
  mounted() {
    if (this.mailTypes.length === 0) {
      this.fetchMailTypes();
      this.fetchEmailsAdmin({
        selectedMailType: this.selectedMailType,
        dateFrom: this.dateFrom,
        dateTo: this.dateTo
      });
      this.tableKey++;
    }
  }
};
</script>

<style scoped>
.filter-options {
  margin-bottom: 20px;
}

.table-container {
  margin-top: 20px;
}

.mail-type {
  background-color: #42B983;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  font-size: 14px;
  margin-right: 10px;
}

input[type="date"] {
  padding: 8px 12px;
  border-radius: 4px;
  border: 1px solid #00A38B;
  margin-right: 10px;
}

button {
  background-color: #008C8B;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}
</style>
