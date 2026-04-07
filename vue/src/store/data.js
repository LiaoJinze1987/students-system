import { defineStore } from "pinia"

export const useDataStore = defineStore('user', {
    state: () => ({
        token: localStorage.getItem('token') || '',
        user: JSON.parse(localStorage.getItem('user') || 'null'),
        master: JSON.parse(localStorage.getItem('master') || 'null'),
        students: JSON.parse(localStorage.getItem('students') || '[]'),
        notices: JSON.parse(localStorage.getItem('notices') || '[]'),
        scores: JSON.parse(localStorage.getItem('scores') || '[]'),
        asks: JSON.parse(localStorage.getItem('asks') || '[]'),
        teachers: JSON.parse(localStorage.getItem('teachers') || '[]')
    }),
    actions: {
        setUser(user) {
            this.user = user
            localStorage.setItem('user', JSON.stringify(user))
        },
        setToken(token) {
            this.token = token
            localStorage.setItem('token', token)
        },
        setMaster(master) {
            this.master = master
            localStorage.setItem('master', JSON.stringify(master))
        },

        setStudents(students) {
            this.students = students
            localStorage.setItem('students', JSON.stringify(students))
        },

        setTeachers(teachers) {
            this.teachers = teachers
            localStorage.setItem('teachers', JSON.stringify(teachers))
        },

        setNotices(notices) {
            this.notices = notices
            localStorage.setItem('notices', JSON.stringify(notices))
        },

        setScores(scores) {
            this.scores = scores
            localStorage.setItem('scores', JSON.stringify(scores))
        },

        setAsks(asks) {
            this.asks = asks
            localStorage.setItem('asks', JSON.stringify(asks))
        },

        loginOut() {
            this.token = ''
            this.user = null
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            localStorage.removeItem('master')
            localStorage.removeItem('students')
            localStorage.removeItem('notices')
            localStorage.removeItem('scores')
            localStorage.removeItem('asks')
            localStorage.removeItem('teachers')
        }
    }
})