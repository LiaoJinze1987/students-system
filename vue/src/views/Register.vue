<script setup>
import { reactive, ref, onMounted } from 'vue'
import { getClassList, register } from '@/api/url'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

const validatePwd = (_, value, callback) => {
  if (value !== form.password) {
    callback(new Error('Password mismatch'))
  } else {
    callback()
  }
}
const rules = {
  username: [
    { required: true, message: 'please input username', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'please input password', trigger: 'blur' }
  ],
  pwd_repeat: [
    { required: true, message: 'please input password again', trigger: 'blur' },
    { validator: validatePwd, trigger: 'blur'}
  ],
  clazz: [
    { required: true, message: 'please select class', trigger: 'change' }
  ],
  type: [
    { required: true, message: 'please select role', trigger: 'change' }
  ],
  email: [
    { required: true, message: 'please input email', trigger: 'blur' },
    { type: 'email', message: 'email format error', trigger: 'blur' }
  ]
}
const formRef = ref()//获取 el-form 组件实例，用于调用校验、重置等方法
const form = reactive({
  username: '',
  password: '',
  pwd_repeat: '',
  clazz: '',
  type: '',
  email: ''
})
const classList = ref([])
const typeList = [
  { id: 1, name: 'student' },
  { id: 2, name: 'teacher' },
]
const loading = ref(false)

const loadClassList = async () => {
  const list = await getClassList()
  console.log('返回数据：', list.data)
  classList.value = list.data
}
onMounted(() => {
  loadClassList()
})
const handleRegister = async () => {
  try{
    await formRef.value.validate()
    loading.value = true
    await register({
      username: form.username,
      password: form.password,
      type: form.type,
      clazz: form.clazz,
      email: form.email
    })
    ElMessage.success('Register is success')
    formRef.value.resetFields()
    router.replace('/login')
  } catch(e) {
    console.log(e)
  } finally {
    loading.value = false
  }
}
</script>
<template>
  <div class="register-container">
    <div class="left">
      <img src="/src/assets/register.jpg" alt="login" />
    </div>
    <div class="right">
      <el-card class="register-card">
        <h2>学生管理システム</h2>
        <el-form :model="form" :rules="rules" ref="formRef" label-width="160px">
          <el-form-item label="クラス(class)" prop="clazz">
            <el-select v-model="form.clazz" placeholder="please select class">
              <el-option v-for="item in classList" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="ロール(role)" prop="type">
            <el-select v-model="form.type" placeholder="please select role">
              <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.name" />
            </el-select>
          </el-form-item>
          <el-form-item label="ユーザー(username)" prop="username">
            <el-input v-model="form.username" placeholder="please input username" />
          </el-form-item>
          <el-form-item label="パスワード(password)" prop="password">
            <el-input v-model="form.password" placeholder="please input password" type="password" show-password />
          </el-form-item>
          <el-form-item label="パスワード確認" prop="pwd_repeat">
            <el-input v-model="form.pwd_repeat" placeholder="please input password again" type="password"
              show-password />
          </el-form-item>
          <el-form-item label="メールアドレス(email)" prop="email">
            <el-input v-model="form.email" placeholder="please input email" type="email" />
          </el-form-item>
          <el-form-item class="m-top">
            <el-button :loading="loading" type="primary" class="register-btn" @click="handleRegister" style="width: 100%;">
              Register
            </el-button>
          </el-form-item>
          <el-form-item>
            <div class="login-wrapper">
              <router-link to="/login" class="login-link">
                ログインはこちら(Login)
              </router-link>
            </div>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>
<style scoped>
.register-container {
  display: flex;
  height: 100vh;
  background-color: #f5f7fa;
  ;
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

.register-card {
  width: 540px;
}

.login-wrapper {
  width: 100%;
  text-align: right;
}

.login-link {
  color: #409EFF;
  cursor: pointer;
  font-size: 14px;
}

.login-link:hover {
  text-decoration: underline;
}

.m-top {
  margin-top: 48px;
}

.register-btn {
  background-color: #2ecc71;
  border-color: #2ecc71;
  color: white;
}

.register-btn:hover {
  background-color: #27ae60;
  border-color: #27ae60;
}

.spacer {
  height: 16px;
}
</style>