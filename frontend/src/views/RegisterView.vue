<template>
  <div id="app">
    <form @submit.prevent="onSubmit">
      <div class="role-select">
        <select class="role" v-model="role">
          <option value="client">client</option>
          <option value="manager">manager</option>
        </select>
      </div>
      <div class="form-group">
        <label for="first-name">First name:</label>
        <input id="first-name" v-model="form.firstName" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="last-name">Last name:</label>
        <input id="last-name" v-model="form.lastName" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="email">Email address:</label>
        <input id="email" v-model="form.email" type="email" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="phone-number">Phone number:</label>
        <input id="phone-number" v-model="form.phoneNumber" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="username">Username:</label>
        <input id="username" v-model="form.username" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input id="password" v-model="form.password" type="password" class="form-control" required>
      </div>
      <button type="submit" class="btn btn-primary">Register</button>
    </form>
  </div>
</template>
  
<script>
import { mapActions } from 'vuex';

export default {
  name: 'Register',
  data() {
    return {
      role: 'client',
      form: {
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        username: '',
        password: ''
      }
    };
  },
  methods: {
    ...mapActions(['register']),
    async onSubmit() {
      try {
        await this.register({
          role: this.role,
          obj: this.form
        });
        this.$router.push({ name: 'home' });
      } catch (error) {
        console.error('Registration failed:', error);
      }
    }
  }
};
</script>
  
<style scoped></style>
  