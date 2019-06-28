export interface AuthUser {
  username: string;
  displayName: string;
  id: string;
  email: string;
  ROLE: 'admin' | 'uploader' | 'consumer';
}
