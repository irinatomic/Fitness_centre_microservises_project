<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/filter-free">Filter free</router-link> |
      <router-link to="/filter-reserved">Filter reserved</router-link> |
      <router-link v-if="isRoleClientOrManager" to="/emails-user">User emails</router-link>
      <router-link v-if="isRoleAdmin" to="emails-admin">Admin emails</router-link>
      <router-link v-if="!token" to="/login">Login</router-link> |
      <router-link v-if="!token" to="/register">Register</router-link>
      <router-link v-if="token" to="/profile">Profile</router-link> |
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
      return this.user ? this.user.role === 'ADMIN' : false;
    },
    isRoleClientOrManager() {
      return this.user ? ['CLIENT', 'MANAGER'].includes(this.user.role) : false;
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
body,
#app {
  margin: 0;
  padding: 0;
}

/* Apply gradient to the whole page */
#app {
  font-family: 'Gloock', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #ffffff;
  background: linear-gradient(#42B983, #0070BA);
  height: 100vh; /* Set a fixed height */
  min-height: 600px; /* Set a minimum height */
  display: flex;
  flex-direction: column;
}

nav {
  padding-top: 20px;
  padding-bottom: 20px;
  background: linear-gradient(#225E70, #008C8B);
  display: flex;
  justify-content: center;
  align-items: center;
}

nav a {
  font-weight: bold;
  color: #ffffff;
  margin: 0 10px;
  text-decoration: none; 
}

nav a.router-link-exact-active {
  color: #ffffff; 
}
</style>