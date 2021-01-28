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
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private snackBar: SnackBarComponent
  ) {
    this.form = this.formBuilder.group({
      email: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$'),
          Validators.maxLength(50),
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
    });
  }

  ngOnInit(): void {}

  login() {
    const formObj = this.form.getRawValue();
    this.authService.login(formObj).subscribe(
      (data) => {
        // nece da vrati ovde normalan response
        // console.log(data);
        // localStorage.setItem('token', data.token);
        // this.form.reset();
        // this.loginForm.resetForm();
        // this.router.navigate(['']);
      },
      (err) => {
        if (err === 'Forbidden') {
          this.snackBar.openSnackBar(
            'Password is incorrect',
            '',
            'red-snackbar'
          );
        }
      }
    );
  }
}
