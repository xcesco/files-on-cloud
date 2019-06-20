export interface User {
  displayName: string;
  email: string;

  password?: string;
  username: string;
}


export interface Administrator extends User {
  id: number;
}
