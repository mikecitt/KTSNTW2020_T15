import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormBuilder, FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { AuthService } from 'src/app/services';
import { ResendActivationComponent } from './resend-activation.component';


describe('ResendActivationComponent', () => {
  let component: ResendActivationComponent;
  let fixture: ComponentFixture<ResendActivationComponent>;
  let authService: AuthService;
  const formBuilder: FormBuilder = new FormBuilder();
  let snackBar: SnackBarComponent;

  const snackBarMock = {
    openSnackBar: jasmine.createSpy('openSnackBar')
  }

  const authServiceMock = {
    resendActivation: jasmine.createSpy('resendActivation').and.returnValue(of({}))
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule],
      declarations: [ ResendActivationComponent, NgForm ],
      providers: [{provide: FormBuilder, useValue: formBuilder},
                  {provide: SnackBarComponent, useValue: snackBarMock},
                  {provide: AuthService, useValue: authServiceMock},]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResendActivationComponent);
    component = fixture.componentInstance;

    component.form = formBuilder.group({
      email: null
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
    component.submit();
    fixture.detectChanges();
    expect(authService.resendActivation).toHaveBeenCalled();
    fixture.detectChanges();
    expect(snackBar.openSnackBar).toHaveBeenCalledWith("Confirmation mail has been sent. Please activate your account",'','green-snackbar');
  }));

});
