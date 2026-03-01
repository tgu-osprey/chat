import md5 from "js-md5";
import axios from 'axios'


var appid = '20231126001892248';
var key = 'owz7fKz826hAnhJkNDVk';
var salt = (new Date).getTime();
// var query = '';
// 多个query可以用\n连接  如 query='apple\norange\nbanana\npear'
var from = 'zh';
var to = 'en';
async function  trans_English (word)  {
    // console.log(word)
    var str1 = appid + word._value + salt + key;
    var sign = md5(str1);
    var newWord = ''
    newWord = await axios.get(
        "/baidu?q=" + word._value +
        "&from=" + from +
        "&to=" + to +
        "&appid=20231126001892248&salt=" + salt +
        "&sign=" + sign
    )
    console.log(newWord.data.trans_result[0].dst)
    return newWord.data.trans_result[0].dst
}


export default trans_English