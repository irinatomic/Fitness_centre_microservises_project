import Vue from 'vue'
import Vuex from 'vuex'
import jwt from 'jsonwebtoken'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    user: {},                              // user object
    forbidden: [],                         // forbidden usernames (clients and managers)
    mailTypes: [],                         // all mail types
    emails: [],                            // current emails being shown

    gyms: [],
    trainingTypes: [],
    trainings: [],                         // for a gym (filter free)
    sessions: [],                          // for gym (filter reserved)
    timeSlots: [],                         // for a gym (filter free)
    trainings: {}                         // key: gymId, value: list of trainings
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
    },
    SET_FORBIDDEN(state, forbidden) {
      state.forbidden = forbidden;
    },
    SET_GYMS(state, gyms) {
      state.gyms = gyms;
    },
    SET_TRAINING_TYPES(state, trainingTypes) {
      state.trainingTypes = trainingTypes;
    },
    SET_SESSIONS(state, sessions) {
      state.sessions = sessions;
    },
    SET_TIME_SLOTS(state, timeSlots) {
      state.timeSlots = timeSlots;
    },
    SET_TRAININGS(state, {gymId, trainings}) {
      state.trainings[gymId] = trainings;
      console.log('store ', gymId, trainings, state.trainings[gymId])
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

    // ACTIVATE USER
    async activateUser({ commit }, { role, id }) {
      role = role.toLowerCase();

      const url = new URL(`http://localhost:8081/user-service/${role}/activate`);
      url.searchParams.append('id', id);

      const response = await fetch(url.toString(), { method: 'PUT' });
      if (response.status == 200)
        alert('User activated!');
    },

    // UPDATE USER
    async updateUser({ commit }, obj) {
      const role = this.state.user.role.toString().toLowerCase();
      const id = this.state.user.id;

      const url = new URL(`http://localhost:8081/user-service/${role}/${id}`);

      const response = await fetch(url.toString(), {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + this.state.token
        },
        body: JSON.stringify(obj)
      });

      if(response.status === 200) {
        const json = await response.json();
        commit('SET_USER', json);
      }
    },

    // FETCH FORBIDDEN
    async fetchForbidden({ commit }) {

      const responseClients = await fetch('http://localhost:8081/user-service/client/forbidden', {method: 'GET'});
      const responseManagers = await fetch('http://localhost:8081/user-service/manager/forbidden', {method: 'GET'});

      const jsonClients = await responseClients.json();
      const jsonManagers = await responseManagers.json();

      commit('SET_FORBIDDEN', jsonClients.concat(jsonManagers));
    },

    async forbidUser({ commit }, {username, role}) {
      const url = new URL('http://localhost:8081/user-service/admin/forbid');
      url.searchParams.append('username', username);
      url.searchParams.append('role', role);

      fetch(url.toString(), {
        method: 'PUT',
        headers: {
          'Authorization': 'Bearer ' + this.state.token
        }
      });
    },

    async allowUser({ commit }, {username, role}) {
      const url = new URL('http://localhost:8081/user-service/admin/unforbid');
      url.searchParams.append('username', username);
      url.searchParams.append('role', role);
      console.log(url.toString());

      fetch(url.toString(), {
        method: 'PUT',
        headers: {
          'Authorization': 'Bearer ' + this.state.token
        }
      });
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

    // GET GYMS
    async fetchGyms({ commit }) {
      const response = await fetch('http://localhost:8082/reservation-service/gym', {
        method: 'GET',
      });

      const json = await response.json();
      commit('SET_GYMS', json.content);
    },

    // GET TRAINING TYPES
    async fetchTrainingTypes({ commit }) {
      const response = await fetch('http://localhost:8082/reservation-service/training-type', {
        method: 'GET',
      });

      const json = await response.json();
      commit('SET_TRAINING_TYPES', json);
    },

    // SESSIONS -> FILTER 
    async fetchSessions({ commit }, { gymId, trainingTypeId, date }) {
      if (trainingTypeId === 'ALL') trainingTypeId = null;

      let url = new URL('http://localhost:8082/reservation-service/training-sessions/filter');

      if (gymId) url.searchParams.append('gymId', gymId);
      if (date) url.searchParams.append('date', date);
      if (trainingTypeId) url.searchParams.append('trainingTypeId', trainingTypeId);

      const response = await fetch(url.toString(), {
        method: 'GET',
      });

      const json = await response.json();
      commit('SET_SESSIONS', json);
    },

    // SESSION -> SIGN UP
    async signUpForSession({ commit }, sessionId) {
      const userDto = {
        clientId: this.state.user.id,
        firstName: this.state.user.firstName,
        lastName: this.state.user.lastName,
        email: this.state.user.email,
        trainingSessionId: sessionId
      }

      const url = new URL('http://localhost:8082/reservation-service/training-sessions/sign-up');
      url.searchParams.append('sessionId', sessionId);

      const response = await fetch(url.toString(), {
        method: 'PUT',
        headers: {
          'Authorization': 'Bearer ' + this.state.token,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(userDto)
      });

      if (response.status === 200) alert('Signed up!');
    },

    // SESSION -> CANCEL AS USER
    async cancelSessionAsUser({ commit }, sessionId) {
      const url = new URL('http://localhost:8082/reservation-service/training-sessions/cancel-as-user');
      url.searchParams.append('sessionId', sessionId);
      url.searchParams.append('userId', this.state.user.id);

      const response = await fetch(url.toString(), {
        method: 'PUT',
        headers: {
          'Authorization': 'Bearer ' + this.state.token,
          'Content-Type': 'application/json'
        }
      });

      if (response.status === 200) alert('Canceled!');
    },

    // SESSION -> CANCEL AS MANAGER
    async cancelSessionAsManager({ commit }, sessionId) {
      const url = new URL('http://localhost:8082/reservation-service/training-sessions/cancel-as-manager');
      url.searchParams.append('sessionId', sessionId);
      url.searchParams.append('managerId', this.state.user.id);

      console.log(this.state.token)
      const response = await fetch(url.toString(), {
        method: 'PUT',
        headers: {
          'Authorization': 'Bearer ' + this.state.token,
          'Content-Type': 'application/json'
        }
      });

      console.log(response)
      if (response.status === 200) alert('Canceled!');
    },

    // TIME SLOTS -> FILTER
    async fetchFreeTimeSlots({ commit }, { gymId, date }) {
      let url = new URL('http://localhost:8082/reservation-service/time-slots/free');

      url.searchParams.append('gymId', gymId);          // required
      if (date) url.searchParams.append('date', date);

      const response = await fetch(url.toString(), {
        method: 'GET',
      });

      const json = await response.json();
      commit('SET_TIME_SLOTS', json);
    },

    // FETCH TRAININGS FOR GYM 
    async fetchTrainingsForGym({ commit }, gymId) {
      if (this.state.trainings[gymId]) return;

      const url = new URL('http://localhost:8082/reservation-service/trainings/gym');
      url.searchParams.append('gymId', gymId);

      const response = await fetch(url.toString(), {
        method: 'GET',
      });

      const json = await response.json();
      console.log(json)
      commit('SET_TRAININGS', {
        gymId: gymId,
        trainings: json
      });
    }
  },

  getters: {
  },

  modules: {
  }
})
