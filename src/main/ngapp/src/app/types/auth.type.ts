export interface AuthUser {
  username: string;
  displayName: string;
  id: number;
  email: string;
  role: 'ADMINISTRATOR' | 'UPLOADER' | 'CONSUMER';
}
