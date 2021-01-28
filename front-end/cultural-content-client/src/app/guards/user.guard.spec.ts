import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { UserService } from '../services';

import { UserGuard } from './user.guard';

describe('UserGuard', () => {
  let guard: UserGuard;
  let router: Router;
  let userService: UserService;

  const routerMock = jasmine.createSpyObj('router', ['navigate']);

  const serviceMock = {
    currentUser : {},
    getRole : jasmine.createSpy('getRole')
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
    guard = TestBed.inject(UserGuard);
    router = TestBed.inject(Router);
    expect(guard).toBeTruthy();
  });

  it('should not activate', async () => {
    userService = TestBed.inject(UserService);
    guard = TestBed.inject(UserGuard);
    router = TestBed.inject(Router);
    const val = guard.canActivate(({data: { expectedRoles: 'ROLE_USER'}} as any) as ActivatedRouteSnapshot, ({url: ''} as any) as RouterStateSnapshot);

    expect(val).toBeFalsy();
    expect(userService.getRole).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalled();
  });

  it('should activate', async () => {
    const serviceMock2 = {
      currentUser: {},
      getRole : jasmine.createSpy('getRole').and.returnValue('ROLE_USER')
    };
    TestBed.overrideProvider(UserService, {useValue: serviceMock2});
    userService = TestBed.inject(UserService);
    guard = TestBed.inject(UserGuard);
    router = TestBed.inject(Router);
    const val = guard.canActivate(({data: { expectedRoles: 'ROLE_USER'}} as any) as ActivatedRouteSnapshot, ({url: ''} as any) as RouterStateSnapshot);

    expect(val).toBeTruthy();
    expect(userService.getRole).toHaveBeenCalled();
  });
});
