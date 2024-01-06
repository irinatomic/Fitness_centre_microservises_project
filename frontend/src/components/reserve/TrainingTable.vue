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
    console.log(this.$store.state.trainings, this.$store.state.trainings[this.gymId] == undefined)    // check

    if (this.$store.state.trainings[this.gymId] == undefined) {
      // If it's an observed object, convert it to a plain JavaScript object
      const plainTrainings = JSON.parse(JSON.stringify(this.$store.state.trainings));
      this.trainingsForGym = plainTrainings[this.gymId]
    } else {
      this.trainingsForGym = this.$store.state.trainings[this.gymId]
    }


    console.log("trainingTable mounted: ", this.trainingsForGym)
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