<template>
  <div id="app">
    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <label for="username">Username:</label>
        <input id="username" v-model="form.username" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input id="password" v-model="form.password" type="password" class="form-control" required>
      </div>
      <button type="submit" class="btn btn-primary">Login</button>
    </form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'Login',
  data() {
    return {
      form: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    ...mapActions(['login']),

    async onSubmit() {
      try {
        await this.login(this.form);
        this.$router.push({ name: 'home' });
      } catch (error) {
        console.error('Login failed:', error);
      }
    }
  }
}
</script>

<style scoped></style>