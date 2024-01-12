<template>
  <div class="gym-list">
    <ShowGym v-if="gyms" v-for="gym in gyms" :key="gym.id" :gym="gym" />
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';
import ShowGym from '@/components/ShowGym.vue';

export default {
  name: 'HomeView',
  components: {
    ShowGym
  },
  computed: {
    ...mapState(['gyms']),
  },
  methods: {
    ...mapActions(['fetchGyms']),
  },
  mounted() {
    this.fetchGyms();
  },
}
</script>

<style scoped>
.gym-list {
    display: flex;
    justify-content: center;
}
</style>