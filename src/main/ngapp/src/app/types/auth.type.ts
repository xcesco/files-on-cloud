type Role = 'ROLE_ADMINISTRATOR' | 'ROLE_UPLOADER' | 'ROLE_CONSUMER';

export interface JwtUser {
  username: string;
  displayName: string;
  id: number;
  isEnabled: boolean;
  email: string;
  roles: Role[];
}
