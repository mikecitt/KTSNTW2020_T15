import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services';

@Component({
  selector: 'app-resend-activation',
  templateUrl: './resend-activation.component.html',
  styleUrls: ['./resend-activation.component.scss']
})
export class ResendActivationComponent implements OnInit {

  form: FormGroup;

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService, private _snackBar: MatSnackBar) {
    this.form = this.formBuilder.group({
      email: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$'),
          Validators.maxLength(50),
        ]),
      ],
    });
  }

  ngOnInit(): void {
  }

  submit() {

    this.authService.resendActivation(this.form.controls['email'].value).subscribe(
      (data) => {
        console.log(data);
        this.form.reset();
        let snackBarRef = this._snackBar.open(
          'Confirmation mail has been sent. Please activate your account.',
          'Go to Home page',
          {
            duration: 0,
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
          }
        );
        snackBarRef.afterDismissed().subscribe(() => {
          this.router.navigate(['']);
        });
      },
      (err) => {
        console.log(err.error);
      }
    );
  }

}
