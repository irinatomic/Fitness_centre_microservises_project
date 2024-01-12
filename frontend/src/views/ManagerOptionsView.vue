<template>
    <EditGym/>
</template>

<script>
import EditGym from '@/components/manager-options/EditGym.vue';

export default {
  name: 'ManagersGymView',
  data() {
    return {
      managerHasAGym: false,
    };
  },
  components: {
    EditGym
  }
}
</script>

