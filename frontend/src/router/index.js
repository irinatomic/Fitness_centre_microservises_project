import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import FilterFreeView from '../views/FilterFreeView.vue'
import FilterReservedView from '../views/FilterReservedView.vue'
import EmailsUserView from '../views/EmailsUserView.vue'
import EmailsAdminView from '../views/EmailsAdminView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/filter-free',
    name: 'filter-free',
    component: FilterFreeView
  },
  {
    path: '/filter-reserved',
    name: 'filter-reserved',
    component: FilterReservedView
  },
  {
    path: '/emails-user',
    name: 'emails-user',
    component: EmailsUserView
  },
  {
    path: '/emails-admin',
    name: 'emails-admin',
    component: EmailsAdminView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
