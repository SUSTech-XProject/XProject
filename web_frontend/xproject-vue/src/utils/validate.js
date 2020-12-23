
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

export function validUsername(str) {
  return /^(?![0-9])(?![0-9]+$)[0-9A-Za-z]{8,24}$/.test(str)
}

export function validPassword(str) {
  return /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,32}$/.test(str);
}
