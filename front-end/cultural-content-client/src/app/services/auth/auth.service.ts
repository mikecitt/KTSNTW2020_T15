import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { UserService } from '../user/user.service';

export interface RegistrationForm {
  email: string;
  username: string;
  password: string;
}

export interface LoginForm {
  email: string;
  password: string;
}

interface LoginResponse {
  token?: string;
  email: string;
  role: string;
}

const COOKIE_NAME = 'token';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private access_token = 'null';

  constructor(
    private http: HttpClient,
    private cookieService: CookieService,
    private userService: UserService,
    private router: Router
  ) {
    this.access_token = cookieService.get(COOKIE_NAME);
    if (!this.access_token) {
      userService.setupUser(null);
    }
  }

  register(registration: RegistrationForm) {
    return this.http.post<any>(
      `${environment.api_url}/auth/register`,
      registration
    );
  }

  login(login: LoginForm) {
    return this.http
      .post<LoginResponse>(`${environment.api_url}/auth/login`, login)
      .pipe(
        map((res) => {
          console.log(res);
          this.access_token = res.token!;
          this.cookieService.set(COOKIE_NAME, this.access_token);
          delete res.token;
          res.role = 'ROLE_' + res.role;
          this.userService.setupUser(res);
          this.router.navigate(['/']);
        })
      );
  }

  resendActivation(email: string) {
    return this.http.get<any>(
      `${environment.api_url}/auth/resend?email=${email}`
    );
  }

  logout() {
    this.cookieService.delete(COOKIE_NAME);
    this.userService.setupUser(null);
    this.router.navigate(['/']);
  }

  tokenIsPresent() {
    return this.access_token != undefined && this.access_token != null;
  }

  getToken() {
    return this.access_token;
  }
}
