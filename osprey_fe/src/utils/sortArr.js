export function quickSort(arr, attr = 'value', flag = false) {
    if (arr.length <= 1) {
      return arr;
    }
  
    const pivotIndex = Math.floor(arr.length / 2);
    const pivot = arr[pivotIndex][attr];
  
    const left = [];
    const right = [];
  
    for (let i = 0; i < arr.length; i++) {
      if (i === pivotIndex) {
        continue;
      }
  
      if ((arr[i][attr] <= pivot && flag) || (arr[i][attr] >= pivot && !flag)) {
        left.push(arr[i]);
      } else {
        right.push(arr[i]);
      }
    }
  
    return [...quickSort(left, attr, flag), arr[pivotIndex], ...quickSort(right, attr, flag)];
  }
  
  export function sortArr(arr, attr = 'value', flag = false) {
    return quickSort(arr, attr, flag);
  }
  