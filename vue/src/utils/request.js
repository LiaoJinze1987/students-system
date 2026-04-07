import axios from 'axios'
import { useDataStore } from '@/store/data'

const request = axios.create({
    baseURL: 'http://localhost:8823',
    timeout: 10 * 1000
})

//token no need
const whiteList = [
    '/user/login',
    '/user/register',
    '/class/list'
]

let dataStore

//request interceptor(请求拦截器)
request.interceptors.request.use(config => {
    if (!dataStore) {
        dataStore = useDataStore()
    }
    //const isWhite = whiteList.some(url => config.url.includes(url))
    const isWhite = whiteList.some(url => config.url.startsWith(url))
    if (dataStore.token && !isWhite) {
        config.headers = config.headers || {}
        config.headers.Authorization = dataStore.token
    }
    return config
}, error => {
    return Promise.reject(error)
})

//response interceptor(响应拦截器)
request.interceptors.response.use(response => {
    const res = response.data
    if (res.code === 0) {//sucess
        return res
    }
    if (res.code === 401) {
        alert("Login has expired.Please login again.")
        if (!dataStore) {
            dataStore = useDataStore()
        }
        dataStore.loginOut()
        window.location.href = '/login'
        return Promise.reject(res)
    }
    alert(res.msg || 'request failed')
    return Promise.reject(res)
}, error => {
    if (error.response) {
        const status = error.response.status
        if (status === 401) {
            alert('Login has expired.Please login again.')
            if (!dataStore) {
                dataStore = useDataStore()
            }
            dataStore.loginOut()
            window.location.href = '/login'
        } else if (status === 500) {
            alert('Server error')
        } else {
            alert('request error:' + status)
        }
    } else {
        alert('Network error, please check if the backend is running.')
    }
    return Promise.reject(error)

})

export default request