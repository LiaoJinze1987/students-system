<script setup>
import { onMounted, ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useDataStore } from '@/store/data'
import { getNotices, getMaster, addAsk, getStudentScores } from '@/api/url'

const activeTab = ref('notice')
const selectedDetail = ref(null)
const leaveType = ref('')
const leaveReason = ref('')
const dataStore = useDataStore()
const router = useRouter()

const tabs = [
  { key: 'notice', label: 'お知らせ' },
  { key: 'score', label: '成績' },
  { key: 'leave', label: '休暇申請' }
]

const noticeList = computed(() => dataStore.notices)
const scoreList = computed(() => dataStore.scores)
const teachername = computed(() => dataStore.master.username)

onMounted(() => {
  fetchNotices()
  fetchScores()
  fetchMaster()
})

const fetchNotices = async () => {
  try {
    const res = await getNotices()
    dataStore.setNotices(res.data)
  } catch (e) {
    ElMessage.error('get notices failed')
  }
}

const fetchScores = async () => {
  try {
    const id = dataStore.user.id
    const res = await getStudentScores(id)
    dataStore.setScores(res.data)
  } catch (e) {
    ElMessage.error('get scores failed')
  }
}

const fetchMaster = async () => {
  try {
    const clazz = dataStore.user.clazz
    const res = await getMaster(clazz)
    dataStore.setMaster(res.data)
  } catch (e) {
    ElMessage.error('get master failed')
  }
}

const loginOut = () => {
  dataStore.loginOut()
  router.replace('/login')
}

const submitLeave = async () => {
  try {
    if (!leaveType.value || !leaveReason.value) {
      ElMessage.warning('内容が未入力です')
      return
    }
    const ask = {
      type: leaveType.value,
      content: leaveReason.value,
      studentId: dataStore.user.id,
      studentName: dataStore.user.username,
      masterId: dataStore.master.id,
      studentMaster: dataStore.master.username
    }
    const res = await addAsk(ask)
    console.log(res.data)
    ElMessage.success('申請が成功しました')
    leaveType.value = ''
    leaveReason.value = ''
  } catch (e) {
    console.log(e)
    ElMessage.error('申請失敗')
  }
}
</script>
<template>
  <div class="page">
    <div class="header">
      <div class="welcome">
        <h2>Welcome to School Control System!</h2>
      </div>
      <div class="right">
        <span class="name">{{ dataStore.user?.username }}</span>
        <el-button size="small" plain @click="loginOut" class="loginOut">LoginOut</el-button>
      </div>
    </div>
    <div class="tabs">
      <div v-for="tab in tabs" :key="tab.key" :class="['tab-item', activeTab === tab.key ? 'active' : '']"
        @click="activeTab = tab.key; selectedDetail = null">
        {{ tab.label }}
      </div>
    </div>
    <div class="content">
      <div v-if="activeTab === 'notice'" class="panel">
        <div class="list">
          <div v-for="item in noticeList" :key="item.id" class="list-item" @click="selectedDetail = item">
            {{ item.title }}
          </div>
        </div>
        <div class="detail">
          <div v-if="selectedDetail">
            <h3>{{ selectedDetail.title }}</h3>
            <p>{{ selectedDetail.content }}</p>
            <el-button type="primary" @click="selectedDetail = null" class="close">閉じる</el-button>
          </div>
        </div>
      </div>
      <div v-if="activeTab === 'score'" class="panel">
        <div class="list">
          <div v-for="item in scoreList" :key="item.id" class="list-item" @click="selectedDetail = item">
            {{ item.term }}
          </div>
        </div>
        <div class="detail">
          <div v-if="selectedDetail">
            <h3>{{ selectedDetail.term }}</h3>
            <p>{{ selectedDetail.content }}</p>
            <el-button type="primary" @click="selectedDetail = null" class="close">閉じる</el-button>
          </div>
        </div>
      </div>
      <!-- 请假页面 -->
      <div v-if="activeTab === 'leave'" class="leave">
        <el-select v-model="leaveType" placeholder="休暇タイプを選択">
          <el-option label="用事（事假）" value="事假" />
          <el-option label="病欠（病假）" value="病假" />
        </el-select>
        <div class="teacher-info">
          担任教師：{{ teachername }}
        </div>
        <el-input type="textarea" v-model="leaveReason" placeholder="休暇の理由を入力してください" :rows="14" />
        <el-button class="submit-btn" @click="submitLeave" type="primary">
          申請する
        </el-button>
      </div>
    </div>
  </div>
</template>
<style scoped>
.page {
  height: 100vh;
  background: #f0f2f5;
}

.header {
  height: 80px;
  background: linear-gradient(90deg, #3a5f8a, #2c4f73);
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
}

.right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.welcome {
  color: #e5e7eb;
  font-size: 24px;
  font-weight: bold;
  font-family: 'Times New Roman', Times, serif;
}

.name {
  color: white;
  font-size: 24px;
  font-weight: bold;
  font-family: Verdana, Geneva, Tahoma, sans-serif;
}

.loginOut {
  font-weight: 500;
  font-size: 13px;
  border-radius: 4px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  /* 初始状态：半透明边框和白色文字 */
  color: rgba(255, 255, 255, 0.9);
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.3);
  transition: all 0.3s ease;
}

.loginOut:hover {
  /* 悬停时：背景变白，文字变深蓝 */
  background: #ffffff;
  color: #2c4f73;
  border-color: #ffffff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.tabs {
  display: flex;
  gap: 12px;
  padding: 20px 40px;
}

.tab-item {
  padding: 10px 30px;
  border-radius: 12px;
  background: #e5e7eb;
  cursor: pointer;
  transition: 0.3s;
}

.tab-item:hover {
  background: #d1d5db;
  font-weight: normal;
}

.tab-item.active {
  background: #2c4f73;
  color: #ffffff;
  font-weight: bold;
  opacity: 1;
}

.content {
  flex: 1;
  padding: 0 50px;
  height: calc(100vh - 240px);
  /* 调整数值以适应 80px 的 header */
  position: relative;
  overflow-y: auto;
  /* 确保内容多时可以滚动 */
}

.panel {
  display: flex;
  gap: 20px;
  margin-top: 20px;
  height: 100%;
}

.list,
.detail {
  width: 50%;
  background: whitesmoke;
  padding: 20px;
  border-radius: 12px;
  height: 100%;
  overflow: auto;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.list-item {
  padding: 12px;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}

.list-item:hover {
  background: #f5f7fa;
}

.close {
  border-radius: 8px;
  margin-top: 20px;
  margin-bottom: 12px;
  color: whitesmoke;
  background: #ab1f1f;
  border: none;
  cursor: pointer;
}

.leave {
  width: 60%;
  /* 适当加宽 */
  margin: 20px auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
  min-height: 400px;
  /* 给个保底高度 */
}

.teacher {
  font-size: 16px;
  color: #374151;
}

.submit-btn {
  background: #1f3a5f;
  border-color: #1f3a5f;
  color: white;
  width: 420px;
  height: 48px;
  margin: 0 auto;
}

.submit-btn:hover {
  background: #162c47;
  border-color: #162c47;
}
</style>