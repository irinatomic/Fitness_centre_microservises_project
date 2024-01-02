<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/filter-free">Filter free</router-link> |
      <router-link to="/filter-reserved">Filter reserved</router-link> |
      <router-link to="/emails-user">User emails</router-link> |
      <router-link to="emails-admin">Admin emails</router-link> |
      <router-link v-if="!token" to="/login">Login</router-link> |
      <router-link v-if="!token" to="/register">Register</router-link> |
      <a v-if="token" href="#" @click="logout">Logout</a>
    </nav>
    <router-view />
  </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';

export default {
  name: 'App',
  computed: {
    ...mapState(['token']),
  },
  methods: {
    ...mapMutations(['REMOVE_TOKEN', 'SET_TOKEN']), 
    logout() {
      this.REMOVE_TOKEN();
    },
  },
  mounted() {
    if (localStorage.token) {
      this.SET_TOKEN(localStorage.token);
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
