export interface AuthUser {
  username: string;
  displayName: string;
  id: number;
  email: string;
  ROLE: 'admin' | 'uploader' | 'consumer';
}
