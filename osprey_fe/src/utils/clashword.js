import FastScanner from 'fastscan'
import { clasUseFile } from '@/utils/clash_word_file'
// export const clashArr = readTxt()

const scanner = new FastScanner(clasUseFile) // 敏感词检测
// 敏感词检测插件
export const searchWord = (content) => { // 快速搜索
  return scanner.search(content, {
    quick: true
  })
}
export const hitWord = (content) => { // 全量检测
  return scanner.hits(content)
}

export function readTxt () {
  let txt = ''
  const xmlhttp = new XMLHttpRequest()
  xmlhttp.open('GET', 'http://mc.haaland.top:5244/d/local/%E7%B4%A0%E6%9D%90/%E6%94%BF%E6%B2%BB%E7%B1%BB.txt', false)
  xmlhttp.send()
  if (xmlhttp.status === 200) {
    txt = xmlhttp.responseText
  }
  const arr = txt.split(/\n|,/)
  const words = []
  arr.forEach((item, index) => {
    if (item !== '') {
      words.push(item)
    }
  })
  console.log(words)
  return words
}
