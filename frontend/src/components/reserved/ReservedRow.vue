<!-- ReservedRow.vue -->
<template>
  <tr>
    <td>{{ session.id }}</td>
    <td>{{ session.date }}</td>
    <td>{{ session.startTime }}</td>
    <td>{{ session.trainingName }}</td>
    <td>{{ session.signedUpCount }}</td>
    <td>{{ session.capacity }}</td>
    <td>
      <button v-if="hasSignedUpOption" @click="signUp(session.id)">Sign Up</button>
      <button v-if="hasCancelAsUserOption" @click="cancelAsUser(session.id)">Cancel</button>
      <button v-if="hasCancelAsManagerOption" @click="cancelAsManager(session.id)">Cancel</button>
    </td>
  </tr>
</template>
  
<script>
import { mapActions, mapState } from 'vuex';

export default {
  name: 'ReservedRow',
  data() {
    return {
      hasSignedUpOption: false,
      hasCancelAsUserOption: false,
      hasCancelAsManagerOption: false,
    };
  },
  props: {
    session: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ...mapState(['token', 'user']),
  },
  methods: {
    ...mapActions(['signUpForSession', 'cancelSessionAsUser', 'cancelSessionAsManager']),
    signUp(sessiondId) {
      console.log(sessiondId)
      this.$store.dispatch('signUpForSession', sessiondId);
      // this.$router.go(0);
    },

    cancelAsUser(sessiondId) {
      this.$store.dispatch('cancelSessionAsUser', sessiondId);
      // this.$router.go(0);
    },

    cancelAsManager(sessiondId) {
      this.$store.dispatch('cancelSessionAsManager', sessiondId);
      // this.$router.go(0);
    },

  },
  mounted() {
    if (this.user === null) return;

    const userEmail = this.user.email;
    const userId = this.user.id;
    const role = this.user.role;

    this.sessiondId = this.session.id;
    this.hasSignedUpOption = role === 'CLIENT' && !this.session.signedUpUsersEmails.includes(userEmail);
    this.hasCancelAsUserOption = role === 'CLIENT' && !this.hasSignedUpOption;
    this.hasCancelAsManagerOption = role === 'MANAGER' && this.session.gymManagerId === userId;
  },
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