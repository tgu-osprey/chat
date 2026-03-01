import request from "../utils/request"

export function loginByName(name){
    return request({
        url: '/user/apply',
        method: 'post',
        data: {
            name
        }
    })
}

// 用uuid获取用户信息
export function getUserName(uuid){
    return request({
        url: '/user/login',
        method: 'post',
        data: {
            uuid
        }
    })
}

// 弃用昵称
export function abandonName(uuid){
    return request({
        url: '/user/abandon',
        method: 'post',
        data: {
            uuid
        }
    })
}

// roll昵称
export function rollName(){
    return request({
        url: '/user/randomusername',
        method: 'get'
    })
}