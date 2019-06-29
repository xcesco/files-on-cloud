import {Consumer, Uploader} from './users';

export interface CloudFile {
  consumer: Consumer;
  creationTime: Date;
  tags: string[];
  contentLength: number;
  fileName: string;
  mimeType: string;
  notified: boolean;
  uploader: Uploader;
  uuid: string;

  viewIp?: string;
  viewTime?: Date;
}

export interface CloudFileData {
  uploaderId;
  username: string;
  email: string;
  codiceFiscale: string;
  displayName: string;
  file: any;
  hashtag;
}