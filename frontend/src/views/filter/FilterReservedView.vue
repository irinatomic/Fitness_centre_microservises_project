<template>
  <div id="app">
    <div clas="filter-options">
      Gym:
      <select required=true id="gym" class="gym" v-model="selectedGym">
        <option value="SELECT"> Select a gym </option>
        <option v-for="gym in gyms" :key="gym.id" :value="gym.id"> {{ gym.name }} </option>
      </select>
      Training type:
      <select id="training-type" class="training-type" v-model="selectedTrainingType">
        <option value="ALL"> ALL </option>
        <option v-for="trainingType in trainingTypes" :key="trainingType.id" :value="trainingType.id"> {{
          trainingType.name }} </option>
      </select>
      Date:
      <input type="date" v-model="selectedDate">
      <button @click="filter">Filter</button>
    </div>
    <div class="table-container">
      <ReservedTable :key="tableKey" />
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import ReservedTable from '@/components/filter-reserved/ReservedTable.vue';

export default {
  name: 'FilterReservedView',
  components: {
    ReservedTable
  },
  data() {
    return {
      selectedGym: 'SELECT',
      selectedTrainingType: 'ALL',
      selectedDate: null,
      tableKey: 0
    };
  },
  computed: {
    ...mapState(['gyms', 'trainingTypes']),
  },
  methods: {
    ...mapActions(['fetchGyms', 'fetchTrainingTypes', 'fetchSessions']),

    filter() {
      if (this.selectedGym === 'SELECT') {
        alert('Please select a gym!');
        return;
      }

      this.$store.dispatch('fetchSessions', {
        gymId: this.selectedGym,
        trainingTypeId: this.selectedTrainingType,
        date: this.selectedDate
      });
      this.tableKey++;
    },
  },
  mounted() {
    if (this.gyms.length === 0) this.fetchGyms();
    if (this.trainingTypes.length === 0) this.fetchTrainingTypes();
  }
}
</script>

<style scoped>
#app {
  padding-top: 20px;
  display: flex;
  align-items: center;
  height: 100vh;
}

.filter-options {
  margin-bottom: 20px;
}

.table-container {
  margin-top: 20px;
}

.gym,
.training-type {
  background-color: white;
  color: black;
  border: none;
  padding: 4px 6px;
  border-radius: 4px;
  font-size: 14px;
  margin-right: 10px;
}

input[type="date"] {
  padding: 4px 6px;
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