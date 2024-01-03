import Vue from 'vue'
import Vuex from 'vuex'
import jwt from 'jsonwebtoken'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    user: {},                              // user object
    mailTypes: [],                         // all mail types
    emails: [],                            // current emails being shown
  },

  mutations: {
    SET_TOKEN(state, token) {
      state.token = token;
      localStorage.token = token;
    },
    REMOVE_TOKEN(state) {
      state.token = '';
      localStorage.token = '';
    },
    SET_USER(state, user) {
      state.user = user;
    },
    REMOVE_USER(state) {
      state.user = {};
    },
    SET_MAIL_TYPES(state, mailTypes) {
      state.mailTypes = mailTypes;
    },
    SET_EMAILS(state, emails) {
      state.emails = emails;
    }
  },

  actions: {

    // REGISTER
    async register({ commit }, { role, obj }) {
      role = role.toLowerCase();
      const response = await fetch(`http://localhost:8081/user-service/${role}/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(obj)
      });
    },

    // LOGIN
    async login(context, { role, obj }) {
      role = role.toLowerCase();
      const response = await fetch(`http://localhost:8081/user-service/${role}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(obj)
      })

      const json = await response.json();
      context.commit('SET_TOKEN', json.token);

      const decodedToken = jwt.decode(json.token, { complete: true });
      const id = decodedToken.payload.id || '';
      await context.dispatch('getUserById', { role, id });
    },

    // LOGOUT
    async logoutUser({ commit }) {
      const role = this.state.user.role.toString().toLowerCase();

      let url = new URL(`http://localhost:8081/user-service/${role}/logout`);
      url.searchParams.append('id', this.state.user.id);

      const response = await fetch(url.toString(), { method: 'POST' });

      if (response.status === 200) {
        commit('REMOVE_TOKEN');
        commit('REMOVE_USER');
      }
    },

    // GET USER BY ID
    async getUserById({ commit }, { role, id }) {
      role = role.toLowerCase();
      const response = await fetch(`http://localhost:8081/user-service/${role}/${id}`, {
        method: 'GET'
      });

      const json = await response.json();
      console.log('user: ', json)
      commit('SET_USER', json);
    },

    // MAIL TYPES
    async fetchMailTypes({ commit }) {
      const response = await fetch('http://localhost:8083/notif-service/mail-type', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
      });

      const json = await response.json();
      commit('SET_MAIL_TYPES', json.content);
    },

    // EMAILS - ADMIN
    async fetchEmailsAdmin({ commit }, { selectedMailType, dateFrom, dateTo }) {
      if (selectedMailType === 'ALL') selectedMailType = null;

      let url = new URL('http://localhost:8083/notif-service/mail/all');

      if (dateFrom) url.searchParams.append('timestampFrom', dateFrom)
      if (dateTo) url.searchParams.append('timestampTo', dateTo);
      if (selectedMailType) url.searchParams.append('mailType', selectedMailType);

      console.log(url.toString());
      console.log(this.state.token)

      const response = await fetch(url.toString(), {
        headers: {
          'Authorization': 'Bearer ' + this.state.token
        }
      });

      const json = await response.json();
      commit('SET_EMAILS', json.content);
    },

    // EMAILS: USER + MANAGER
    async fetchEmailsUser({ commit }, { selectedMailType, dateFrom, dateTo }) {
      if (selectedMailType === 'ALL') selectedMailType = null;

      let url = new URL('http://localhost:8083/notif-service/mail/specific');

      if (dateFrom) url.searchParams.append('timestampFrom', dateFrom)
      if (dateTo) url.searchParams.append('timestampTo', dateTo);
      if (selectedMailType) url.searchParams.append('mailType', selectedMailType);
      url.searchParams.append('sentTo', this.state.user.email);

      const response = await fetch(url.toString(), {
        headers: {
          'Authorization': 'Bearer ' + this.state.token
        }
      });

      const json = await response.json();
      commit('SET_EMAILS', json.content);
    },

  },

  getters: {
  },

  modules: {
  }
})
