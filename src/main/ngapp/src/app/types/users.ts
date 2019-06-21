export interface User {
  displayName: string;
  email: string;

  id: number;
  password?: string;
  username: string;
}

export type Administrator = User;

export type Uploader = User;

export interface Consumer extends User {
  codiceFiscale: string;
}
