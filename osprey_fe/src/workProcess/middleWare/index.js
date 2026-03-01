export const skillIndexModule = { // 这个是MSG中间件的索引位置，你可以把一个函数写在上面，当然你也可以import一个新的函数，传入值为一个结构体
  normal: (struct) => {
    return struct
  },
  toBlue: (struct) => {
    return struct
  }
}
