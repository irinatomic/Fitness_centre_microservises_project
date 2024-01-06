<template>
  <div>
    <button @click="activate"> Activate account </button>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'ActivateView',

  props: {
    role: String,
    id: String,
  },

  methods: {
    ...mapActions(['activateUser']),
    activate() {
      this.activateUser({ role: this.role, id: this.id });
    },
    mounted() {
      console.log("me")
    },
  },
};
</script>
