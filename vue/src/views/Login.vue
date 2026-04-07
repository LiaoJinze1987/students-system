<script setup>
import { reactive, ref, onMounted } from 'vue'
import { getClassList, login } from '@/api/url'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useDataStore } from '@/store/data'

const router = useRouter()
const dataStore = useDataStore()

const form = reactive({
  username: '',
  password: '',
  clazz: ''
})
const rules = {
  username: [
    { required: true, message: 'please input username', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'please input password', trigger: 'blur' }
  ],
  clazz: [
    { required: true, message: 'please select class', trigger: 'change' }
  ]
}
const formRef = ref()//获取 el-form 组件实例，用于调用校验、重置等方法

const loading = ref(false)
const classList = ref([])

const loadClassList = async () => {
  const list = await getClassList()
  console.log('返回数据：', list.data)
  classList.value = list.data
}
onMounted(() => {
  loadClassList()
})

const handleLogin = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    const info = await login({
      username: form.username,
      password: form.password,
      clazz: form.clazz
    })
    console.log('返回数据：', info.data)
    ElMessage.success('Login is success')
    formRef.value.resetFields()
    dataStore.setToken(info.data.token)
    dataStore.setUser(info.data.user)
    const type = info.data.user.type
    if (type === 'student') {
      router.replace('/student')
    } else if (type === 'teacher') {
      router.replace('/teacher')
    } else if (type === 'admin') {
      router.replace('ctrl')
    } else {
      ElMessage.error('未知用户类型')
    }
  } catch (e) {
    console.log(e)
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="login-container">
    <div class="left">
      <img src="/src/assets/login.jpg" alt="login" />
    </div>
    <div class="right">
      <el-card class="login-card">
        <h2>学生管理システム</h2>
        <el-form :model="form" :rules="rules" ref="formRef" label-width="160px">
          <el-form-item label="ユーザー(username)" prop="username">
            <el-input v-model="form.username" placeholder="please input username" />
          </el-form-item>
          <el-form-item label="パスワード(password)" prop="password">
            <el-input v-model="form.password" placeholder="please input password" type="password" show-password />
          </el-form-item>
          <el-form-item label="クラス(class)" prop="clazz">
            <el-select v-model="form.clazz" placeholder="please select class">
              <el-option v-for="item in classList" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item class="m-top">
            <el-button type="primary" class="login-btn" @click="handleLogin" style="width: 100%;">
              Login
            </el-button>
          </el-form-item>
          <el-form-item>
            <div class="register-wrapper">
              <router-link to="/register" class="register-link">
                新規登録はこちら(Register)
              </router-link>
            </div>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  height: 100vh;
  background-color: #f5f7fa;
}

.left {
  flex: 3;
}

.left img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.right {
  flex: 5;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-card {
  width: 540px;
}

.register-wrapper {
  width: 100%;
  text-align: right;
}

.register-link {
  color: #409EFF;
  cursor: pointer;
  font-size: 14px;
}

.register-link:hover {
  text-decoration: underline;
}

.m-top {
  margin-top: 48px;
}

.login-btn {
  background-color: #3B82F6;
  border-color: #3B82F6;
  color: white;
}

.login-btn:hover {
  background-color: #2563EB;
  border-color: #2563EB;
}
</style>
