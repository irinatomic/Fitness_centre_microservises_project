<template>
  <div id="app">
    <div v-if="timeSlot" class="header">Reserve a training session for {{timeSlot.date}} {{timeSlot.startTime}}</div>
    <div class="table-container">
      <TrainingTable v-if="timeSlot && timeSlot.gymId" :key="tableKey" :gymId="timeSlot.gymId"/>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import TrainingTable from '@/components/reserve/TrainingTable.vue';

export default {
  name: 'ReserveView',
  data() {
    return {
      timeSlot: null,
      tableKey: 0
    };
  },
  props: {
    timeSlotId: {
      type: Number,
      required: true,
    },
  },
  components: {
    TrainingTable,
  },
  computed: {
    ...mapState(['token', 'trainingTypes', 'timeSlots']),
  },
  methods: {
    ...mapActions(['fetchTrainingsForGym']),
  },
  async mounted() {
    // fetch the timeSlot for our id
    this.timeSlot = this.timeSlots.find(timeSlot => timeSlot.id === this.timeSlotId);
    this.$store.dispatch('setCurrentTimeSlot', this.timeSlot);
    // fetch the trainings for the selected gym
    // gymId is in this.timeSlot.gymId
    await this.$store.dispatch('fetchTrainingsForGym', this.timeSlot.gymId);
  }
};
</script>

<style scoped>
.header {
  font-size: 24px;
  font-weight: bold;
  color: #225E70;
  margin: 20px;
}

.table-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 150px;
  overflow-y: auto;
}
</style>