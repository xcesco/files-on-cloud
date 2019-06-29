export interface JwtUser {
  username: string;
  displayName: string;
  id: number;
  isEnabled: boolean;
  email: string;
  roles: string[];
}
