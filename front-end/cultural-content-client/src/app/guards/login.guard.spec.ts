import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { UserService } from '../services';

import { LoginGuard } from './login.guard';

describe('LoginGuard', () => {
  let guard: LoginGuard;
  let router: Router;
  let userService: UserService;

  const routerMock = jasmine.createSpyObj('router', ['navigate']);

  const serviceMock = {
    currentUser : {}
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: UserService, useValue: serviceMock},
        {provide: Router, useValue: routerMock}]
    });
  });

  it('should be created', () => {
    userService = TestBed.inject(UserService);
    guard = TestBed.inject(LoginGuard);
    router = TestBed.inject(Router);
    expect(guard).toBeTruthy();
  });

  it('should not activate', async () => {
    userService = TestBed.inject(UserService);
    guard = TestBed.inject(LoginGuard);
    router = TestBed.inject(Router);
    const val = guard.canActivate(({data: { expectedRoles: ''}} as any) as ActivatedRouteSnapshot, ({url: ''} as any) as RouterStateSnapshot);

    expect(val).toBeFalsy();
    expect(router.navigate).toHaveBeenCalled();
  });

  it('should activate', async () => {
    const serviceMock2 = {
    };
    TestBed.overrideProvider(UserService, {useValue: serviceMock2});
    userService = TestBed.inject(UserService);
    guard = TestBed.inject(LoginGuard);
    router = TestBed.inject(Router);

    const val = guard.canActivate(({data: { expectedRoles: ''}} as any) as ActivatedRouteSnapshot, ({url: ''} as any) as RouterStateSnapshot);

    expect(val).toBeTruthy();
  });

});
