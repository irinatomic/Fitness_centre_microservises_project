<template>
  <div class="forbidden">
    <h3 class="header">Manage forbidden users</h3>
    <div class="segments">
      <!-- Forbid segment -->
      <div class="segment">
        <h3 class="header">Forbid a user</h3>
        <div class="input-group">
          <input type="text" v-model="forbidUsername" class="input-field" placeholder="Username" />
          <input type="text" v-model="forbidRole" class="input-field" placeholder="Role" />
          <button class="btn btn-primary" @click="forbidUserFunction">Forbid</button>
        </div>
      </div>

      <!-- Allow segment -->
      <div class="segment">
        <h3 class="header">Allow a user</h3>
        <div class="input-group">
          <input type="text" v-model="allowUsername" class="input-field" placeholder="Username" />
          <input type="text" v-model="allowRole" class="input-field" placeholder="Role" />
          <button class="btn btn-primary" @click="allowUserFunction">Allow</button>
        </div>
      </div>
    </div>

    <!-- Already forbidden segment -->
    <div class="segment">
      <h3 class="header">Forbidden users</h3>
      <div class="scrollable-table">
        <table>
          <thead>
            <tr>
              <th>Usernames</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in forbidden" :key="user">
              <td>{{ user }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';

export default {
  name: 'ForbiddenComponent',
  data() {
    return {
      forbidUsername: '',
      forbidRole: '',
      allowUsername: '',
      allowRole: ''
    };
  },
  computed: {
    ...mapState(['forbidden'])
  },
  methods: {
    ...mapActions(['fetchForbidden']),
    forbidUserFunction() {      
      this.$store.dispatch('forbidUser', {
        username: this.forbidUsername, 
        role: this.forbidRole
      });
    },
    allowUserFunction() {
      this.$store.dispatch('allowUser', {
        username: this.allowUsername, 
        role: this.allowRole
      });
    }
  },
  mounted() {
    this.fetchForbidden();
  }
};
</script>

<style scoped>
/* Updated styles for a more compact layout */
.header {
  color: #008c8b;
  margin-bottom: 20px;
  text-align: center;
}

.forbidden {
  padding-top: 20px;
  align-items: center;
}

.segments {
  display: flex;
  justify-content: space-around;
  margin-bottom: 15px;
}

.segment {
  flex: 0 0 30%;
  text-align: center;
  margin-inline: 10px;
}

.input-group {
  display: flex;
  flex-direction: column; /* Change the direction to stack items vertically */
  align-items: center; /* Align items at the center horizontally */
  margin-bottom: 10px;
}

.input-field {
  flex: 1;
  padding: 8px;
  margin-right: 10px;
}

.scrollable-table {
  height: 80px;
  overflow-y: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
  color: #008c8b;
}

.btn {
  margin-top: 10px;
  padding: 8px 15px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  color: white;
  background-color: #008c8b;
  font-weight: bold;
}

.btn:hover {
  background-color: #225e70;
}
</style>