<template>
  <div class="table-container">
    <table class="table">
      <thead>
        <tr>
          <th>Gym</th>
          <th>Name</th>
          <th>Training type</th>
          <th>Duration</th>
          <th>Minimum people</th>
          <th>Maxim people</th>
          <th>Reserve</th>
        </tr>
      </thead>
      <tbody>
        <TrainingRow v-for="training in trainingsForGym" :key="training.id" :training="training" />
      </tbody>
    </table>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import TrainingRow from './TrainingRow.vue';

export default {
  name: 'TrainingTable',
  props: {
    gymId: {
      type: Number,
      required: true,
    }
  },
  data() {
    return {
      trainingsForGym: [],
    };
  },
  components: {
    TrainingRow,
  },
  computed: {
    ...mapState(['trainings']),
  },
  mounted() {
    // set the trainings for the selected gym
    console.log(this.trainings)
    this.trainingsForGym = this.trainings[this.gymId];
    console.log("training table mounted", this.trainingsForGym)

    // filter based on trainingTypeId
    // trainingsForGym = trainingsForGym.filter(training => training.trainingTypeId === trainingTypeId)
  }
};
</script>

<style scoped>
table {
  border-collapse: collapse;
}

th {
  background-color: #007582;
  color: white;
  padding: 8px;
}
</style>