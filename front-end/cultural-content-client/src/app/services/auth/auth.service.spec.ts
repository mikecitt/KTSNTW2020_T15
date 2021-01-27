import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { Router } from '@angular/router';

import { AuthService, LoginForm, LoginResponse, RegistrationForm } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let http: HttpTestingController;
  let router: Router;

  const path = 'http://localhost:8080/api/auth/';

  beforeEach(() => {
    const routerMock = jasmine.createSpyObj('router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService,
            {provide: Router, useValue: routerMock}]
    });
    service = TestBed.inject(AuthService);
    http = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call login', fakeAsync(() => {
    let loginForm : LoginForm = {
      email: "user@example.com",
      password: "qwerty"
    }

    let loginResponseMock = {
      token: "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE2MTE3Nzg0NDIsImV4cCI6MTYxMTc4MDI0Mn0._RofUc8Vn4doeN8A8jALpIPRwnlIiUHiGcuo3CxAz6Mo-a6vFlBdazkVSfi7MHSnumksrDYhJO0c7eP3HT8t3Q",
      role: "USER",
      email: "user@example.com"
    }

    let loginResponse : LoginResponse;

    service.login(loginForm).subscribe();

    const req = http.expectOne(path + 'login');
    expect(req.request.method).toBe('POST');
    req.flush(loginResponseMock);
    tick();

  }));

  it('should call register', fakeAsync(() => {
    let registerForm : RegistrationForm = {
      email: "user@example.com",
      username: "user",
      password: "qwerty"
    }

    let registerResponseMock = "Account with the given email already exists";

    let registerResponse : string;

    service.register(registerForm).subscribe(res => {
      registerResponse = res;
  
      expect(registerResponse).toEqual("Account with the given email already exists");
    });

    const req = http.expectOne(path + 'register');
    expect(req.request.method).toBe('POST');
    req.flush(registerResponseMock);

  }));

  it('should call resendActivation', fakeAsync(() => {
    let resendForm : string = "test@email.com";

    service.resendActivation(resendForm).subscribe();

    const req = http.expectOne(path + 'resend?email=' + resendForm);
    expect(req.request.method).toBe('GET');

  }));

  
  it('should call logout', fakeAsync(() => {

    service.logout();
    expect(router.navigate).toHaveBeenCalled();

  }));

});
