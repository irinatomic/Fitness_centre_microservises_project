<template>
  <div>
    <button @click="activate">Log Parameters</button>
  </div>
</template>

<script>


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
  },
};
</script>
