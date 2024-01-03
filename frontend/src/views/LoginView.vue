<template>
  <div id="app">
    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <div class="role-select">
          <select class="role" v-model="role">
            <option value="client">client</option>
            <option value="manager">manager</option>
            <option value="admin">admin</option>
          </select>
        </div>
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
      role: 'client',
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
        await this.login({
          role: this.role,
          obj: this.form
        });
        this.$router.push({ name: 'home' });
      } catch (error) {
        console.error('Login failed:', error);
      }
    }
  }
}
</script>

<style scoped></style>