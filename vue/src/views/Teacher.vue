<script setup>
import { onMounted, ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useDataStore } from '@/store/data'
import { getNotices, getClassStudents, addScore, askList, updateAsk } from '@/api/url'

const activeTab = ref('notice')
const selectedDetail = ref(null)
const dataStore = useDataStore()
const router = useRouter()
const noticeList = computed(() => dataStore.notices)
const students = computed(() => dataStore.students)
const leaveList = computed(() => dataStore.asks)

onMounted(() => {
    fetchNotices()
    fetchStudents()
    fetchAsks()
})

const fetchNotices = async () => {
    try {
        const res = await getNotices()
        dataStore.setNotices(res.data)
    } catch (e) {
        ElMessage.error('get notices failed')
    }
}

const fetchStudents = async () => {
    try {
        const clazz = dataStore.user.clazz
        const res = await getClassStudents(clazz)
        dataStore.setStudents(res.data)
    } catch (e) {
        ElMessage.error('get students failed')
    }
}

const fetchAsks = async () => {
    try {
        const masterId = dataStore.user.id
        const res = await askList(masterId)
        dataStore.setAsks(res.data)
    } catch (e) {
        ElMessage.error('get asks failed')
    }
}

const selectedStudent = ref(null)
const currentTerm = '26春'

const score = ref('')
const remark = ref('')
const selectedLeave = ref(null)

const selectStudent = (stu) => {
    selectedStudent.value = stu
    score.value = ''
    remark.value = ''
}

const submitScore = async () => {
    try {
        if (!score.value) {
            ElMessage.warning('内容が未入力です')
            return
        }
        const data = {
            studentId: selectedStudent.value.id,
            term: currentTerm,
            content: score.value,
            remark: remark.value
        }
        const res = await addScore(data)
        ElMessage.success('成績入力に成功しました')
        score.value = ''
        remark.value = ''
    } catch (e) {
        console.log(e)
        ElMessage.error('成績入力に失敗しました')
    }
}

const loginOut = () => {
    dataStore.loginOut()
    router.replace('/login')
}

const handleLeave = async (status) => {
    if (!selectedLeave.value) return
    try {
        const askStatus = status === 'approved' ? 1 : 2
        const id = selectedLeave.value.id
        await updateAsk(id, askStatus)
        ElMessage.success('承認リクエストが成功しました')
    } catch (e) {
        console.log(e)
        ElMessage.error('承認リクエストが失敗しました')
    }
    selectedLeave.value = null
    fetchAsks()
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
            <div :class="['tab-item', activeTab === 'notice' && 'active']" @click="activeTab = 'notice'">お知らせ</div>
            <div :class="['tab-item', activeTab === 'score' && 'active']" @click="activeTab = 'score'">成績登録</div>
            <div :class="['tab-item', activeTab === 'leave' && 'active']" @click="activeTab = 'leave'">休暇申請承認</div>
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
                    <div v-for="stu in students" :key="stu.id" class="list-item" @click="selectStudent(stu)">
                        {{ stu.username }}
                    </div>
                </div>
                <div class="detail">
                    <div v-if="selectedStudent" class="form">
                        <p>学生名: {{ selectedStudent.username }}</p>
                        <p>学期: {{ currentTerm }}</p>
                        <el-input v-model="score" type="textarea" :rows="4" placeholder="成績を入力してください" />
                        <el-input v-model="remark" type="textarea" :rows="4" placeholder="備考を入力してください" />
                        <el-button type="primary" class="submit-btn" @click="submitScore">
                            提出する
                        </el-button>
                    </div>
                </div>
            </div>
            <div v-if="activeTab === 'leave'" class="panel">
                <div class="list">
                    <div v-for="item in leaveList" :key="item.id" class="list-item" @click="selectedLeave = item">
                        {{ item.studentName }} - {{ item.type }}
                    </div>
                </div>
                <div class="detail">
                    <div v-if="selectedLeave" class="form">
                        <p>学生名：{{ selectedLeave.studentName }}</p>
                        <p>種類：{{ selectedLeave.type }}</p>
                        <p>理由：{{ selectedLeave.content }}</p>
                        <div v-if="selectedLeave.status === 0" class="actions">
                            <el-button type="success" @click="handleLeave('approved')">承認</el-button>
                            <el-button type="danger" @click="handleLeave('rejected')">拒否</el-button>
                        </div>
                        <div v-else>
                            <p>処理済み：{{ selectedLeave.status === 1 ? '承認済み' : '拒否済み' }}</p>
                        </div>
                    </div>
                </div>
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

.form {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.submit-btn {
    background: #1f3a5f;
    border-color: #1f3a5f;
    color: white;
    width: 420px;
    height: 48px;
    margin: 0 auto;
    align-self: center;
}

.submit-btn:hover {
    background: #162c47;
    border-color: #162c47;
}

.actions {
    display: flex;
    gap: 14px;
}
</style>