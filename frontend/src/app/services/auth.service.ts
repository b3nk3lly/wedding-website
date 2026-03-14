import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

export interface LoginResponse {
  token: string;
  expiresAt: Date;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private authStatusSubject = new BehaviorSubject<boolean>(false); // Initial state
  public isAuthenticated$ = this.authStatusSubject.asObservable();
  private readonly isBrowser: boolean;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private httpClient: HttpClient,
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);

    if (this.isBrowser) {
    }
  }

  public doLogin(username: string, password: string): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`/auth/login`, { username, password }).pipe(
      tap((response) => {
        console.log(response);
        localStorage.setItem('access_token', JSON.stringify(response));
        this.authStatusSubject.next(true);
      }),
    );
  }

  public isAuthenticated(): boolean {
    if (!this.isBrowser) {
      return true;
    }

    const accessTokenStr = localStorage.getItem('access_token');

    if (accessTokenStr) {
      const accessToken = JSON.parse(accessTokenStr);
      return accessToken.expiresAt < new Date().getTime();
    }

    return false;
  }
}
