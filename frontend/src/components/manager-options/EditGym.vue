<template>
  <div id="app">
    <div class="card">
      <div class="card-header">
        <h3 v-if="!managerHasAGym == true"> Create your gym </h3>
        <h3 v-else> Edit gym </h3>
      </div>

      <div class="form">
        <div class="input-group">
          <label for="name">Name:</label>
          <input id="name" v-model="gym.name" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="description">Description:</label>
          <input id="description" v-model="gym.description" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="coaches-count">Coaches count:</label>
          <input id="coaches-count" v-model="gym.coachesCount" type="number" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="free-session-no">Free session number:</label>
          <input id="free-session-no" v-model="gym.freeSessionNo" type="number" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="opening-time">Opening time:</label>
          <input id="opening-time" v-model="gym.openingTime" type="time" class="form-control" required>
        </div>
        <div class="input-group">
          <label for="closing-time">Closing time:</label>
          <input id="closing-time" v-model="gym.closingTime" type="time" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary" @click="save">Save</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
  name: 'EditGym',
  data() {
    return {
      managerHasAGym: false,
      gymId: null,
      gym: {
        name: '',
        description: '',
        coachesCount: 0,
        freeSessionNo: 0,
        openingTime: null,
        closingTime: null
      }
    }
  },
  computed: {
    ...mapState(['gyms']),
  },
  methods: {
    ...mapActions(['createGym', 'updateGym', 'fetchGyms']),

    async save() {
      //  if we are creating a new gym
      if (!this.managerHasAGym) {
        await this.$store.dispatch('createGym', this.gym);
      } else {
        console.log("YGYM IDDD: ", this.gymId)
        await this.$store.dispatch('updateGym', { gymId: this.gymId, gym: this.gym });
      }
    }
  },
  async mounted() {
    await this.fetchGyms();

    this.managerHasAGym = this.gyms.find(gym => gym.managerId === this.$store.state.user.id);

    console.log(this.$store.state.gyms);
    if (!this.creating) {
      const temp = this.$store.state.gyms.find(gym => gym.managerId === this.$store.state.user.id);

      // Assign values only if they are not already set
      this.gym.name = this.gym.name || temp.name;
      this.gym.description = this.gym.description || temp.description;
      this.gym.coachesCount = this.gym.coachesCount || temp.coachesCount;
      this.gym.freeSessionNo = this.gym.freeSessionNo || temp.freeSessionNo;
      this.gym.openingTime = this.gym.openingTime || temp.openingTime;
      this.gym.closingTime = this.gym.closingTime || temp.closingTime;

      console.log("MOUNTED ", temp.id)
      this.gymId = temp.id;
    }
  },
}
</script>

<style scoped>
#app {
  display: flex;
  align-items: center;
  height: 100vh;
  margin-top: 20px;
}

.card {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.card-header {
  color: #008C8B;
}

.form {
  display: flex;
  flex-direction: column;
  align-items: center;
  /* Align the content of the form vertically centered */
}

.input-group {
  display: flex;
  align-items: center;
  justify-content: center;
  /* Add this line to horizontally center the row */
  margin-bottom: 15px;
}

label {
  color: #008C8B;
  margin-right: 10px;
  font-weight: bold;
  flex: 0 0 120px;
  /* Set a fixed width for labels */
}

input[type="time"] {
  width: 100px;
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