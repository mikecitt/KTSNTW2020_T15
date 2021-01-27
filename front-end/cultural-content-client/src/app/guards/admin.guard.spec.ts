import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { TestBed } from '@angular/core/testing';

import { AdminGuard } from './admin.guard';
import { UserService } from '../services';

describe('AdminGuard', () => {
  let guard: AdminGuard;
  let router: Router;
  let userService: UserService;

  const routerMock = jasmine.createSpyObj('router', ['navigate']);

  const serviceMock = {
    currentUser : {},
    getRole : jasmine.createSpy('getRole')
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: UserService, useValue: serviceMock}, 
        {provide: Router, useValue: routerMock}]
    });
    
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should not activate', async() => {   
    userService = TestBed.inject(UserService);
    guard = TestBed.inject(AdminGuard);
    router = TestBed.inject(Router);
    const val = guard.canActivate(({data: { expectedRoles: 'ROLE_ADMIN'}} as any) as ActivatedRouteSnapshot, ({url: ""} as any) as RouterStateSnapshot);

    expect(val).toBeFalsy();
    expect(userService.getRole).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalled();
  });

  it('should activate', async() => {
    const serviceMock2 = {
      currentUser: {},
      getRole : jasmine.createSpy('getRole').and.returnValue('ROLE_ADMIN')
    }
    TestBed.overrideProvider(UserService, {useValue: serviceMock2});
    userService = TestBed.inject(UserService);
    guard = TestBed.inject(AdminGuard);
    router = TestBed.inject(Router);
    const val = guard.canActivate(({data: { expectedRoles: 'ROLE_ADMIN'}} as any) as ActivatedRouteSnapshot, ({url: ""} as any) as RouterStateSnapshot);

    expect(val).toBeTruthy();
    expect(userService.getRole).toHaveBeenCalled();
  });
});
