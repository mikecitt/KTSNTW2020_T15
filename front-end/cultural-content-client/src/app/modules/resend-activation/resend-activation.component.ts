import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { AuthService } from 'src/app/services';

@Component({
  selector: 'app-resend-activation',
  templateUrl: './resend-activation.component.html',
  styleUrls: ['./resend-activation.component.scss']
})
export class ResendActivationComponent implements OnInit {

  form: FormGroup;
  loading = false;

  @ViewChild('resendForm')
  public resendForm!: NgForm;

  constructor(private formBuilder: FormBuilder, private snackBar: SnackBarComponent, private authService: AuthService) {
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

    this.loading = true;

    this.authService.resendActivation(this.form.controls['email'].value).subscribe(
      (data) => {
        this.loading = false;
        this.form.reset();
        this.resendForm.resetForm();
        this.snackBar.openSnackBar("Confirmation mail has been sent. Please activate your account",'','green-snackbar');

      },
      (err) => {
        this.loading = false;
        this.snackBar.openSnackBar(err,'','red-snackbar');
      }
    );
  }

}
