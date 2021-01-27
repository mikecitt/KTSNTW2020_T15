import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthService, UserService } from 'src/app/services';

import { NavigationBarComponent } from './navigation-bar.component';

describe('NavigationBarComponent', () => {
  let component: NavigationBarComponent;
  let fixture: ComponentFixture<NavigationBarComponent>;
  let router: Router;
  let userService: UserService;
  let authService: AuthService;

  beforeEach(async () => {
    const routerMock = {
    }

    const userServiceMock = {
      getRole : jasmine.createSpy('getRole')
    }

    const authServiceMock = {
      logout: jasmine.createSpy('logout')
    }

    await TestBed.configureTestingModule({
      declarations: [ NavigationBarComponent ],
      providers: [
        {provide: Router, useValue: routerMock},
        {provide: UserService, useValue: userServiceMock},
        {provide: AuthService, useValue: authServiceMock},
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    userService = TestBed.inject(UserService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should logout', () => {
    component.logout();
    expect(authService.logout).toHaveBeenCalled();
  });
});
