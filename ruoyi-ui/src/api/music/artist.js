import request from '@/utils/request'

// 查询作者信息列表
export function listArtist(query) {
  return request({
    url: '/music/artist/list',
    method: 'get',
    params: query
  })
}

// 查询作者信息详细
export function getArtist(artistId) {
  return request({
    url: '/music/artist/' + artistId,
    method: 'get'
  })
}

// 新增作者信息
export function addArtist(data) {
  return request({
    url: '/music/artist',
    method: 'post',
    data: data
  })
}

// 修改作者信息
export function updateArtist(data) {
  return request({
    url: '/music/artist',
    method: 'put',
    data: data
  })
}

// 删除作者信息
export function delArtist(artistId) {
  return request({
    url: '/music/artist/' + artistId,
    method: 'delete'
  })
}
