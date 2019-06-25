import {isNullOrUndefined} from 'util';

/**
 * verifica se l'oggetto passato come argomento contiene qualcosa che non sia null, undefined o stringa vuota
 * @param value
 *    valore da controllare
 * @return {boolean}
 *    true
 */
export function isNotBlank(value: any): boolean {
  if (isNullOrUndefined(value)) {
    return false;
  } else if (Number.isNaN(value)) {
    return false;
  } else if (isString(value)) {
    return value.trim().length > 0;
  } else {
    return true;
  }
}

/**
 * verifica se l'oggetto passato come argomento è undefined, null o una stringa vuota (solo spazi)
 * @param value
 * @return {boolean}
 */
export function isBlank(value: any) {
  return !isNotBlank(value);
}

export function isString(value: any): value is string {
  return typeof value === 'string';
}
