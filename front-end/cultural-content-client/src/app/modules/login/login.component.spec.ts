import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { AuthService } from 'src/app/services';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  const formBuilder: FormBuilder = new FormBuilder();
  let authService: AuthService;
  let router: Router;
  let snackBar: SnackBarComponent;

  const authServiceMock = {
    login: jasmine.createSpy('login').and.returnValue(of({}))
  }

  const routerMock = jasmine.createSpyObj('router', ['navigate']);

  const snackbarMock = {
    openSnackBar: jasmine.createSpy('openSnackBar')
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FormsModule, ReactiveFormsModule],
      declarations: [ LoginComponent ],
      providers: [{provide: FormBuilder, useValue: formBuilder},
                  {provide: AuthService, useValue: authServiceMock},
                  {provide: Router, useValue: routerMock},
                  {provide: SnackBarComponent, useValue: snackbarMock},]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;

    component.form = formBuilder.group({
      email: null,
      password: null
    });
    fixture.detectChanges();
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    snackBar = TestBed.inject(SnackBarComponent);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call login', fakeAsync(() => {
    fixture.detectChanges();
    tick();
    component.login();
    fixture.detectChanges();
    expect(authService.login).toHaveBeenCalled();
  }));
});
