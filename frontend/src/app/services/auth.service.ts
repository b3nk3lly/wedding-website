import { inject, Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

export interface LoginResponse {
  token: string;
  expiresAt: Date;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private httpClient = inject(HttpClient);

  public doLogin(username: string, password: string): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`/auth/login`, { username, password }).pipe(
      tap((response) => {
        console.log(response);
        localStorage.setItem('access_token', JSON.stringify(response));
      }),
    );
  }

  public isAuthenticated(): boolean {
    const accessTokenStr = localStorage.getItem('access_token');

    if (accessTokenStr) {
      const accessToken = JSON.parse(accessTokenStr);
      return accessToken.expiresAt > new Date().getTime();
    }

    return false;
  }
}
