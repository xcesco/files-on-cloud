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

export interface ChangePasswordUrl {
  url: string;
}

export interface Summary {
  summaryCount: number;
  uploaderDisplayName: string;
  uploaderId: number;
}

export interface DetailedSummary extends Summary {
  consumerId: number;
  consumerDisplayName: string;
}

export interface AdminReportItem {
  consumerCount: number;
  fileCount: number;
  uploaderDisplayName: number;
  uploaderId: number;
}

