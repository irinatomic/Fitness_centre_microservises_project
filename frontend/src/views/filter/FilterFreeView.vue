<template>
  <div id="app">
    <div clas="filter-options">
      Gym:
      <select id="gym" class="gym" v-model="selectedGym">
        <option value="SELECT"> Select a gym </option>
        <option v-for="gym in gyms" :key="gym.id" :value="gym.id"> {{ gym.name }} </option>
      </select>
      Date:
      <input required type="date" v-model="selectedDate">
      <button @click="filter">Filter</button>
    </div>
    <div class="table-container">
      <FreeTable :key="tableKey" />
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import FreeTable from '@/components/filter-free/FreeTable.vue';

export default {
  name: 'FilterFreeView',
  components: {
    FreeTable
  },
  data() {
    return {
      selectedGym: 'SELECT',
      selectedDate: null,
      tableKey: 0
    };
  },
  computed: {
    ...mapState(['gyms']),
  },
  methods: {
    ...mapActions(['fetchGyms', 'fetchFreeTimeSlots']),

    filter() {
      if (this.selectedGym === 'SELECT' || this.selectedDate == null) {
        alert('Please select a gym and a date!');
        return;
      }

      this.$store.dispatch('fetchFreeTimeSlots', {
        gymId: this.selectedGym,
        date: this.selectedDate
      });

      this.tableKey++;
    }
  },
  mounted() {
    if (this.gyms.length === 0) this.fetchGyms();
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