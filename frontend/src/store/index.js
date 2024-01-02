import Vue from 'vue'
import Vuex from 'vuex'
import jwt_decode from 'jsonwebtoken'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // client token
    token: 'eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwicm9sZSI6IkNMSUVOVCIsImVtYWlsIjoiY2xpZW50X29uZUBlbWFpbC5jb20ifQ.Da4FR7tf57VvxGK6eUonrEhiBiJSXeBRP4prWz-N6rJy-QWOwlhPEkL81389gG0APioLGfi50pd8PLo4vUggS',
    role: 'CLIENT',                        // user role
    email: 'client_one@email.com',         // user email
    mailTypes: [],                         // all mail types
    emails: [],                            // current emails being shown
  },

  mutations: {
    SET_TOKEN(state, token) {
      state.token = token;
      localStorage.token = token;
      const decodedToken = jwt_decode(token);
      state.role = decodedToken.role || '';
      state.email = decodedToken.email || '';
    },
    REMOVE_TOKEN(state) {
      state.token = '';
      localStorage.token = '';
      state.role = '';
      state.email = '';
    },
    SET_MAIL_TYPES(state, mailTypes) {
      state.mailTypes = mailTypes;
    },
    SET_EMAILS(state, emails) {
      state.emails = emails;
    }
  },

  actions: {

    async register({ commit }, obj) {
      console.log(JSON.stringify(obj));
      const response = await fetch('', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(obj)
      });

      const json = await response.json();
      commit('SET_TOKEN', json.token);
    },

    async login({ commit }, obj) {
      const response = await fetch('', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(obj)
      })

      const json = await response.json();
      if (json.token) commit('SET_TOKEN', json.token);
      else alert('Login failed');
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

    // ADMIN EMAILS
    async fetchEmailsAdmin({ commit }, { selectedMailType, dateFrom, dateTo }) {
      if (selectedMailType === 'ALL') selectedMailType = null;

      let url = new URL('http://localhost:8083/notif-service/mail/all');

      if (dateFrom) url.searchParams.append('timestampFrom', dateFrom)
      if (dateTo) url.searchParams.append('timestampTo', dateTo);
      if (selectedMailType) url.searchParams.append('mailType', selectedMailType);

      const response = await fetch(url.toString(), {
        headers: {
          'Authorization': 'Bearer ' + this.state.token
        }
      });

      const json = await response.json();
      commit('SET_EMAILS', json.content);
    },

    // USER + MANAGER EMAILS
    async fetchEmailsUser({ commit }, { selectedMailType, dateFrom, dateTo }) {
      if (selectedMailType === 'ALL') selectedMailType = null;

      let url = new URL('http://localhost:8083/notif-service/mail/specific');

      if (dateFrom) url.searchParams.append('timestampFrom', dateFrom)
      if (dateTo) url.searchParams.append('timestampTo', dateTo);
      if (selectedMailType) url.searchParams.append('mailType', selectedMailType);
      url.searchParams.append('sentTo', this.state.email);

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
