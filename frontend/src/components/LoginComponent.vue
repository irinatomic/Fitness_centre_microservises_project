<template>
  <div id="app">
    <div class="card">
      <form @submit.prevent="onSubmit">
        <div class="form-group">
          <div class="role-select">
            <label for="role">Role:</label>
            <select class="role" v-model="role">
              <option value="client">client</option>
              <option value="manager">manager</option>
              <option value="admin">admin</option>
            </select>
          </div>
          <div class="input-group">
            <label for="username">Username:</label>
            <input id="username" v-model="form.username" class="form-control" required>
          </div>
        </div>
        <div class="form-group">
          <div class="input-group">
            <label for="password">Password:</label>
            <input id="password" v-model="form.password" type="password" class="form-control" required>
          </div>
        </div>
        <div class="form-group">
          <button type="submit" class="btn btn-primary">Login</button>
        </div>
      </form>
    </div>
  </div>
</template>
  
<script>
import { mapActions } from 'vuex';

export default {
  name: 'LoginComponent',
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
  
<style scoped>
#app {
  padding-top: 20px;
  display: flex;
  align-items: center;
  height: 100vh;
}

.card {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 20px;
}

.role-select {
  margin-bottom: 20px;
}

.input-group {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

label {
  color: #008C8B;
  margin-right: 10px;
  font-weight: bold;
}

input[type="text"],
input[type="password"],
select {
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #ccc;
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