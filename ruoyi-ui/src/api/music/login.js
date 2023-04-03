import request from '@/utils/request'

// 查询用户登录列表
export function listLogin(query) {
  return request({
    url: '/music/login/list',
    method: 'get',
    params: query
  })
}

// 查询用户登录详细
export function getLogin(userId) {
  return request({
    url: '/music/login/' + userId,
    method: 'get'
  })
}

// 新增用户登录
export function addLogin(data) {
  return request({
    url: '/music/login',
    method: 'post',
    data: data
  })
}

// 修改用户登录
export function updateLogin(data) {
  return request({
    url: '/music/login',
    method: 'put',
    data: data
  })
}

// 删除用户登录
export function delLogin(userId) {
  return request({
    url: '/music/login/' + userId,
    method: 'delete'
  })
}
