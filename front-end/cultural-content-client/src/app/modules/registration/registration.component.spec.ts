import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { AuthService } from 'src/app/services';

import { RegistrationComponent } from './registration.component';

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;
  let authService: AuthService;
  const formBuilder: FormBuilder = new FormBuilder();
  let snackBar: SnackBarComponent;

  const authServiceMock = {
    register: jasmine.createSpy('register').and.returnValue(of({}))
  };

  const snackBarMock = {
    openSnackBar: jasmine.createSpy('openSnackBar')
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule],
      declarations: [ RegistrationComponent ],
      providers: [{provide: AuthService, useValue: authServiceMock},
                  {provide: FormBuilder, useValue: formBuilder},
                  {provide: SnackBarComponent, useValue: snackBarMock}, ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;

    component.form = formBuilder.group({
      email: null,
      username: null,
      password: null,
      rePassword: null
    });

    fixture.detectChanges();
    authService = TestBed.inject(AuthService);
    snackBar = TestBed.inject(SnackBarComponent);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call submit', fakeAsync (() => {
    fixture.detectChanges();
    tick();
    component.register();
    fixture.detectChanges();
    expect(authService.register).toHaveBeenCalled();
    fixture.detectChanges();
    expect(snackBar.openSnackBar).toHaveBeenCalledWith('Confirmation mail has been sent. Please activate your account', '', 'green-snackbar');
  }));
});
