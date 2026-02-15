import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

const host = "http://localhost:8080";

export interface LoginResponse {
  token: string;
  expiresAt: Date;
}

@Injectable({providedIn: 'root'})
export class AuthService {

  constructor(private httpClient: HttpClient) {
  }

  public login(username: string, password: string): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`${host}/auth/login`, {username, password})
  }
}
