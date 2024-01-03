<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/filter-free">Filter free</router-link> |
      <router-link to="/filter-reserved">Filter reserved</router-link> |
      <router-link v-if="isRoleClientOrManager" to="/emails-user">User emails</router-link> |
      <router-link v-if="isRoleAdmin" to="emails-admin">Admin emails</router-link> |
      <router-link v-if="!token" to="/login">Login</router-link> |
      <router-link v-if="!token" to="/register">Register</router-link> |
      <a v-if="token" href="#" @click="logout">Logout</a>
    </nav>
    <router-view />
  </div>
</template>

<script>
import { mapMutations, mapState, mapActions } from 'vuex';
import jwt from 'jsonwebtoken';

export default {
  name: 'App',
  computed: {
    ...mapState(['token', 'user']),
    isRoleAdmin() {
      if (this.user)
        return this.user.role == 'ADMIN';
      return false;
    },
    isRoleClientOrManager() {
      if (this.user)
        return this.user.role == 'CLIENT' || this.user.role == 'MANAGER';
      return false;
    },
  },
  methods: {
    ...mapActions(['logoutUser', 'getUserById']),
    ...mapMutations(['REMOVE_TOKEN', 'SET_TOKEN']), 
    logout() {
      this.logoutUser();
      this.$router.push('/');
    },
  },
  mounted() {
    if (localStorage.token) {
      this.SET_TOKEN(localStorage.token);

      const decodedToken = jwt.decode(localStorage.token, { complete: true });
      const role = decodedToken.payload.role;
      const id = decodedToken.payload.id;
      this.getUserById({ role, id });
    }
  },
}
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
