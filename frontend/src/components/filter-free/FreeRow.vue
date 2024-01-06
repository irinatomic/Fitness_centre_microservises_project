<template>
  <tr>
    <td> {{ gymName }}</td>
    <td> {{ timeSlot.date }} </td>
    <td> {{ timeSlot.startTime }} </td>
    <td>
      <router-link v-if="hasReserveOption && timeSlot && timeSlot.id" :to="{ name: 'reserve', params: { timeSlotId: timeSlot.id } }">
        Reserve
      </router-link>
    </td>
  </tr>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'FreeRow',
  data() {
    return {
      hasReserveOption: false,
      gymName: ''
    };
  },
  props: {
    timeSlot: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ...mapState(['token', 'gyms']),
  },
  mounted() {
    if (this.token === null) return;
    this.hasReserveOption = true;

    const gymId = this.timeSlot.gymId;
    this.gymName = this.gyms.find(gym => gym.id === gymId).name;
  }
};
</script>

<style scoped>
td {
  border: 1px solid #225E70;
  padding: 8px;
  background-color: white;
  color: #225E70;
}
</style>