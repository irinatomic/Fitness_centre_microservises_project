<template>
  <div id="app">
    <div class="card">
      <div class="card-header">
        <h3>My Profile</h3>
      </div>

      <form @submit.prevent="onSubmit" class="form">
        <div class="input-group">
          <label for="first-name">First name:</label>
          <input id="first-name" v-model="form.firstName" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="last-name">Last name:</label>
          <input id="last-name" v-model="form.lastName" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="email">Email address:</label>
          <input id="email" v-model="form.email" type="email" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="phone-number">Phone number:</label>
          <input id="phone-number" v-model="form.phoneNumber" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="username">Username:</label>
          <input id="username" v-model="form.username" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="password">Password:</label>
          <input id="password" v-model="form.password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Change profile info</button>
      </form>

      <div class="forbidden" v-if="role === 'ADMIN'">
        <ForbiddenComponent />
      </div>
    </div>
  </div>
</template>

<script>
import ForbiddenComponent from '@/components/ForbiddenComponent.vue';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'ProfileComponent',
  data() {
    return {
      role: '',
      form: {
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: ''
      }
    };
  },
  components: {
    ForbiddenComponent
  },
  computed: {
    ...mapState(['user'])
  },
  methods: {
    ...mapActions(['updateUser']),
    async onSubmit() {
      try {
        await this.updateUser(this.form);
        this.$router.push({ name: 'home' });
      }
      catch (error) {
        console.error('Update failed:', error);
      }
    }
  },
  mounted() {
    this.form.username = this.user.username;
    this.form.firstName = this.user.firstName;
    this.form.lastName = this.user.lastName;
    this.form.email = this.user.email;
    this.form.phoneNumber = this.user.phoneNumber;
    this.role = this.user.role;
  }
};
</script>

<style scoped>
#app {
  padding-top: 20px;
  display: flex;
  align-items: center;
  justify-content: center; /* Add this line to horizontally center the content */
  height: 100vh;
}

.card-header {
  color: #008C8B;
}

.card {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form {
  display: flex;
  flex-direction: column;
  align-items: center; /* Align the content of the form vertically centered */
}

.input-group {
  display: flex;
  align-items: center;
  justify-content: center; /* Add this line to horizontally center the row */
  margin-bottom: 15px;
}

label {
  color: #008C8B;
  margin-right: 10px;
  font-weight: bold;
  flex: 0 0 120px;
  /* Set a fixed width for labels */
}

input[type="text"],
input[type="password"],
input[type="email"],
input[type="tel"],
select {
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #ccc;
  flex: 1;
}

.btn {
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  color: white;
  background-color: #008C8B;
  font-weight: bold;
}

.btn:hover {
  background-color: #225E70;
}
</style>

