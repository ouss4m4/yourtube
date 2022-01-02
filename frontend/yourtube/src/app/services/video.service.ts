import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  NgxFileDropEntry,
  FileSystemFileEntry,
  FileSystemDirectoryEntry,
} from 'ngx-file-drop';
import { Observable } from 'rxjs';
import { IUploadVideoResponse } from '../upload-video/UploadVideoResponse';

@Injectable({ providedIn: 'root' })
export class VideoService {
  constructor(private httpClient: HttpClient) {}

  uploadVideo(file: File): Observable<IUploadVideoResponse> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.httpClient.post<IUploadVideoResponse>(
      'http://localhost:8080/api/video/',
      formData
    );
  }
}
