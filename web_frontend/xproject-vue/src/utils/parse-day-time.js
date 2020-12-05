export function getDatetimeStr(str) {
  let arr = str.match(/(.{10}).(.*)\..*\+.*/)
  return arr[1] + ' ' + arr[2]
}
