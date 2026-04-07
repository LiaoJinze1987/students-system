import request from '@/utils/request'

// 获取班级列表
export const getClassList = () => {
  return request.get('/class/list')
}

//注册用户
export const register = (data) => {
  return request.post('/user/register', data)
}

//登录用户
export const login = (data) => {
  return request.post('/user/login', data)
}

//学生获取对应班主任
export const getMaster = (clazz) => {
  return request.get('/user/master', {
    params: { clazz }
  })
}

//获取对应班级学生
export const getClassStudents = (clazz) => {
  return request.get('/user/classStudents', {
    params: { clazz }
  })
}

//获取全部学生
export const getAllStudents = () => {
  return request.get('/user/allStudents')
}

//删除学生或老师
export const delUser = (userId) => {
  return request.delete(`user/delete?id=${userId}`)
}

//获取全部老师
export const getAllTeachers = () => {
  return request.get('/user/allTeachers')
}

//通知列表
export const getNotices = () => {
  return request.get('/notice/list')
}

//新增通知
export const addOneNotice = (data) => {
  return request.post('/notice/add', data)
}

//删除通知
export const delNotice = (id) => {
  return request.delete(`notice/delete?id=${id}`)
}

//学生成绩列表
export const getStudentScores = (studentId) => {
  return request.get('/score/list', {
    params: { studentId }
  })
}

//请假提交
export const addAsk = (data) => {
  return request.post('/ask/add', data)
}

//提交成绩
export const addScore = (data) => {
  return request.post('/score/add', data)
}

//请假列表
export const askList = (masterId) => {
  return request.get('/ask/list', {
    params: { masterId }
  })
}

//审批请假
export const updateAsk = (id, status) => {
  return request.put('/ask/update', null, {
    params: { id, status }
  })
}