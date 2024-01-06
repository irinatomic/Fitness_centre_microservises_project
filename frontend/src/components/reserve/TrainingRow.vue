<template>
  <tr>
    <td>{{ gymName }}</td>
    <td>{{ training.name }}</td>
    <td>{{ training.trainingType }}</td>
    <td>{{ training.duration }}</td>
    <td>{{ training.minPeopleNo }}</td>
    <td>{{ training.capacity }}</td>
    <td>
      <button v-if="hasReserveOption" @click="reserve(training)">Reserve</button>
    </td>
  </tr> 
</template>

<script>
import { log } from 'util';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'TrainingRow',
  data() {
    return {
      hasReserveOption: false,
      gymName: '',
    }
  },
  props: {
    training: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ...mapState(['token', 'gyms']),
  },
  methods: {
    ...mapActions(['createTrainingSession']),

    reserve(training) {
      this.$store.dispatch('createTrainingSession', training)
    },
  },
  mounted() {
    if (this.token === null) return;
    this.hasReserveOption = true;

    console.log("trainingRow mounted")

    const gymId = this.training.gymId;
    this.gymName = this.gyms.find(gym => gym.id === gymId).name;
  },
}
</script>

<style scoped>
td {
  border: 1px solid #225E70;
  padding: 8px;
  background-color: white;
  color: #225E70;
}
</style>