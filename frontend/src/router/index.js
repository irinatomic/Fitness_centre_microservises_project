import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView.vue'

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
    component:() => import('@/views/filter/FilterFreeView.vue')
  },
  {
    path: '/filter-reserved',
    name: 'filter-reserved',
    component:() => import('@/views/filter/FilterReservedView.vue')
  },
  {
    path: '/emails-user',
    name: 'emails-user',
    component:() => import('@/views/emails/EmailsUserView.vue')
  },
  {
    path: '/emails-admin',
    name: 'emails-admin',
    component:() => import('@/views/emails/EmailsAdminView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component:() => import('@/views/user/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component:() => import('@/views/user/RegisterView.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    component:() => import('@/views/user/ProfileView.vue')
  },
  {
    path: '/reserve/:timeSlotId', 
    name: 'reserve',
    component: () => import('@/views/filter/ReserveView.vue'),
    props: true
  },
  {
    path: '/activate',
    name: 'activate',
    component: () => import('@/views/user/ActivateView.vue'),
    props: route => ({
      role: route.query.role,
      id: route.query.id,
    }),
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
