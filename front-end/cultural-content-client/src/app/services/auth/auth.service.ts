import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';

export interface RegistrationForm {
  email: string;
  username: string;
  password: string;
}

export interface LoginForm {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  register(registration: RegistrationForm) {
    return this.http.post<any>(
      `${environment.api_url}/auth/register`,
      registration
    );
  }

  login(login: LoginForm) {
    return this.http.post<any>(`${environment.api_url}/auth/login`, login);
  }

  storeToken(token: string) {
    localStorage.setItem('token', token);
  }
}
