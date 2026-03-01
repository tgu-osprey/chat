import { shuffleArray } from './utils'

// var word = '我是赵香，我磕陈肖' // 测试语句
const pigWords = [
  // 猪语库
  '!',
  '@',
  '#',
  '$',
  '%',
  '^',
  '&',
  '*',
  '(',
  ')',
  '_',
  '+',
  '~',
  '`'
]

// 猪言猪语：使对方的话随机加上一些#￥%@的奇怪词语
export const pig_word = (word) => {
  let newPigWord = ''

  for (let i = 0; i < word.length; i++) {
    newPigWord += shuffleArray(pigWords)
      .slice(1, Math.floor(Math.random() * pigWords.length))
      .join('')
    newPigWord += word[i]
    newPigWord += shuffleArray(pigWords)
      .slice(1, Math.floor(Math.random() * pigWords.length))
      .join('')
  }

  return newPigWord
}
