import {createRouter, createWebHistory} from 'vue-router'

  import Login from '@/views/Login.vue'
  import Register from '@/views/Register.vue'
  import Student from '@/views/Student.vue'
  import Teacher from '@/views/Teacher.vue'
  import Ctrl from '@/views/Ctrl.vue'

  const routes = [
    {path: '/', redirect: '/login'},//default
    {path: '/login', component: Login},
    {path: '/register', component: Register},
    {path: '/student', component: Student},
    {path: '/teacher', component: Teacher},
    {path: '/ctrl', component: Ctrl}
  ]

  const router = createRouter({
    history: createWebHistory(),
    routes
  })

  export default router