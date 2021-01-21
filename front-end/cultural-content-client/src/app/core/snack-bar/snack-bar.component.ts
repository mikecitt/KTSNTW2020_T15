import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-snack-bar',
  templateUrl: './snack-bar.component.html',
  styleUrls: ['./snack-bar.component.scss']
})
export class SnackBarComponent implements OnInit {

  constructor(public snackBar: MatSnackBar) {}

  // this function will open up snackbar on top right position with custom background color (defined in css)
	openSnackBar(message: string, action: string, className: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
      verticalPosition: 'bottom',
      horizontalPosition: 'start',
      panelClass: [className],
    });
	}

  ngOnInit(): void {
  }

}
