<script setup>
import { onMounted, ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useDataStore } from '@/store/data'
import { getNotices, delNotice, addOneNotice } from '@/api/url'//通知相关接口
import { getAllStudents, delUser, getAllTeachers } from '@/api/url'//学生和老师

// 当前tab
const activeTab = ref('notice')
const dataStore = useDataStore()
const loading = ref(false)
onMounted(() => {
    fetchNotices()
    fetchStudents()
    fetchTeachers()
})
// ===== 通知 =====
const noticeList = computed(() => dataStore.notices)
// 当前选中
const selectedNotice = ref(null)
const newTitle = ref('')
const newContent = ref('')

const fetchNotices = async () => {
    try {
        const res = await getNotices()
        dataStore.setNotices(res.data)
    } catch (e) {
        ElMessage.error('get notices failed')
    }
}

// 点击列表
const selectNotice = (item) => {
    selectedNotice.value = item
}

// 关闭详情
const closeDetail = () => {
    selectedNotice.value = null
}

// 删除通知
const deleteNotice = async () => {
    if (!selectedNotice.value) return
    try {
        const id = selectedNotice.value.id
        await delNotice(id)
        ElMessage.success('削除しました')
        fetchNotices()
        selectedNotice.value = null
    } catch (e) {
        console.log(e)
        ElMessage.error('削除に失敗しました')
    }
}

// 新增通知
const addNotice = async () => {
    if (!newTitle.value || !newContent.value) {
        ElMessage.warning('未入力です')
        return
    }
    try {
        loading.value = true
        await addOneNotice({
            title: newTitle.value,
            author: dataStore.user.username,
            content: newContent.value
        })
        ElMessage.success('追加しました')
        selectedNotice.value = null
        fetchNotices()
    } catch (e) {
        console.log(e)
        ElMessage.error('追加に失敗しました')
    } finally {
        loading.value = false
        newTitle.value = ''
        newContent.value = ''
    }
}

// ===== 学生管理 =====
const students = computed(() => dataStore.students)
const selectedUser = ref(null)//选中的学生或老师

const fetchStudents = async () => {
    try {
        const res = await getAllStudents()
        dataStore.setStudents(res.data)
    } catch (e) {
        ElMessage.error('get students failed')
    }
}

// 点击学生
const selectStudent = (stu) => {
    selectedUser.value = stu
}

// 关闭
const closeStudent = () => {
    selectedUser.value = null
}

// 删除老师或学生
const deleteUser = async () => {
    if (!selectedUser.value) return
    try {
        loading.value = true
        await delUser(selectedUser.value.id)
        ElMessage.success('削除しました')
        selectedUser.value = null
        fetchStudents()
    } catch (e) {
        console.log(e)
        ElMessage.error('削除に失敗しました')
    } finally {
        loading.value = false
    }
}
// ===== 老师管理（模拟数据）=====
const teachers = computed(() => dataStore.teachers)

const fetchTeachers = async () => {
    try {
        const res = await getAllTeachers()
        console.log('返回老师数据：', res.data)
        dataStore.setTeachers(res.data)
    } catch (e) {
        console.log(e)
        ElMessage.error('get teachers failed')
    }
}

// 点击老师
const selectTeacher = (item) => {
    selectedUser.value = item
}

// 关闭
const closeTeacher = () => {
    selectedUser.value = null
}

</script>
<template>
    <div class="page">
        <!-- 头 -->
        <div class="header">
            <div class="welcome">
                <h2>Welcome to School Control System!</h2>
            </div>
            <div class="right">
                <span class="name">{{ dataStore.user?.username }}</span>
                <el-button size="small" plain @click="loginOut" class="loginOut">LoginOut</el-button>
            </div>
        </div>
        <!-- tab -->
        <div class="tabs">
            <div :class="['tab-item', activeTab === 'notice' && 'active']" @click="activeTab = 'notice'">通知送信</div>
            <div :class="['tab-item', activeTab === 'score' && 'active']" @click="activeTab = 'score'">学生管理</div>
            <div :class="['tab-item', activeTab === 'leave' && 'active']" @click="activeTab = 'leave'">教師管理</div>
        </div>
        <div class="content">
            <!-- ===== 通知管理 ===== -->
            <div v-if="activeTab === 'notice'" class="panel">
                <!-- 左：列表 -->
                <div class="list">
                    <div v-for="item in noticeList" :key="item.id" class="list-item" @click="selectNotice(item)">
                        {{ item.title }}
                    </div>
                </div>
                <!-- 右：详情 or 新增 -->
                <div class="detail">
                    <!-- ===== 有选中（详情）===== -->
                    <div v-if="selectedNotice" class="form">
                        <h3>{{ selectedNotice.title }}</h3>
                        <p>{{ selectedNotice.content }}</p>
                        <div class="actions">
                            <el-button type="danger" @click="deleteNotice">削除</el-button>
                            <el-button @click="closeDetail">閉じる</el-button>
                        </div>
                    </div>
                    <!-- ===== 未选中（新增）===== -->
                    <div v-else class="form">
                        <el-input v-model="newTitle" placeholder="タイトルを入力" />
                        <el-input v-model="newContent" type="textarea" :rows="10" placeholder="内容を入力" />
                        <el-button type="primary" class="submit-btn" @click="addNotice">
                            送信する
                        </el-button>
                    </div>
                </div>
            </div>
            <!-- ===== 学生管理 ===== -->
            <div v-if="activeTab === 'score'" class="panel">
                <!-- 左：学生列表 -->
                <div class="list">
                    <div v-for="stu in students" :key="stu.id" class="list-item" @click="selectStudent(stu)">
                        {{ stu.clazz }}_{{ stu.username }}
                    </div>
                </div>
                <!-- 右：详情 -->
                <div class="detail">
                    <div v-if="selectedUser" class="form">
                        <p>学生名：{{ selectedUser.username }}</p>
                        <p>クラス：{{ selectedUser.clazz }}</p>
                        <p>担任教師：{{ selectedUser.masterName }}</p>
                        <div class="actions">
                            <el-button type="danger" @click="deleteUser">削除</el-button>
                            <el-button @click="closeStudent">閉じる</el-button>
                        </div>
                    </div>
                    <!-- 未选中状态 -->
                    <div v-else class="form">
                        <p>学生を選択してください</p>
                    </div>
                </div>
            </div>
            <!-- ===== 老师管理 ===== -->
            <div v-if="activeTab === 'leave'" class="panel">
                <!-- 左：老师列表 -->
                <div class="list">
                    <div v-for="item in teachers" :key="item.id" class="list-item" @click="selectTeacher(item)">
                        {{ item.username }}
                    </div>
                </div>
                <!-- 右：详情 -->
                <div class="detail">
                    <div v-if="selectedUser" class="form">
                        <p>教師名：{{ selectedUser.username }}</p>
                        <p>クラス：{{ selectedUser.clazz }}</p>
                        <div class="actions">
                            <el-button type="danger" @click="deleteUser">削除</el-button>
                            <el-button @click="closeTeacher">閉じる</el-button>
                        </div>
                    </div>
                    <!-- 未选中 -->
                    <div v-else class="form">
                        <p>教師を選択してください</p>
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
}

.tab-item.active {
    background: #2c4f73;
    color: white;
}

.content {
    padding: 0 50px;
    height: calc(100vh - 200px);
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
    background: white;
    padding: 20px;
    border-radius: 12px;
    overflow: auto;
}

.list-item {
    padding: 12px;
    border-bottom: 1px solid #eee;
    cursor: pointer;
}

.list-item:hover {
    background: #f5f7fa;
}

.form {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.submit-btn {
    width: 200px;
    align-self: center;
}

.actions {
    display: flex;
    gap: 12px;
}
</style>