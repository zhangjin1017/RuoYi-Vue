import request from '@/utils/request'

// 查询Mv信息列表
export function listMv(query) {
  return request({
    url: '/music/mv/list',
    method: 'get',
    params: query
  })
}

// 查询Mv信息详细
export function getMv(mvId) {
  return request({
    url: '/music/mv/' + mvId,
    method: 'get'
  })
}

// 新增Mv信息
export function addMv(data) {
  return request({
    url: '/music/mv',
    method: 'post',
    data: data
  })
}

// 修改Mv信息
export function updateMv(data) {
  return request({
    url: '/music/mv',
    method: 'put',
    data: data
  })
}

// 删除Mv信息
export function delMv(mvId) {
  return request({
    url: '/music/mv/' + mvId,
    method: 'delete'
  })
}
