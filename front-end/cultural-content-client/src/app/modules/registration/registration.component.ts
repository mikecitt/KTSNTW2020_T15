import { Component, OnInit, ViewChild } from '@angular/core';
import {
  FormGroup,
  FormControl,
  FormGroupDirective,
  NgForm,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthService } from '../../services';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const invalidParent = !!(
      control &&
      control.parent &&
      control.parent.invalid &&
      control.parent.dirty &&
      control.parent.hasError('notSame')
    );
    return invalidParent;
  }
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
})
export class RegistrationComponent implements OnInit {
  form: FormGroup;
  loading = false;

  @ViewChild('registerForm')
  private registerForm!: NgForm;

  matcher = new MyErrorStateMatcher();

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private snackBar: SnackBarComponent
  ) {
    this.form = this.formBuilder.group(
      {
        email: [
          '',
          Validators.compose([
            Validators.required,
            Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$'),
            Validators.maxLength(50),
          ]),
        ],
        username: [
          '',
          Validators.compose([
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(64),
          ]),
        ],
        password: [
          '',
          Validators.compose([
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(64),
          ]),
        ],
        rePassword: ['', Validators.compose([])],
      },
      { validator: this.checkPasswords }
    );
  }

  ngOnInit(): void {}

  checkPasswords(form: FormGroup) {
    let pass = form.controls.password.value;
    let confirmPass = form.controls.rePassword.value;

    return pass === confirmPass ? null : { notSame: true };
  }

  register() {
    let formObj = this.form.getRawValue();
    delete formObj['rePassword'];
    this.loading = true;

    this.authService.register(formObj).subscribe(
      (data) => {
        this.loading = false;
        this.form.reset();
        this.registerForm.resetForm();
        this.snackBar.openSnackBar("Confirmation mail has been sent. Please activate your account",'','green-snackbar');
      },
      (err) => {
        this.loading = false;
        console.log(err);
        this.snackBar.openSnackBar(err,'','red-snackbar');
      }
    );
  }
}
