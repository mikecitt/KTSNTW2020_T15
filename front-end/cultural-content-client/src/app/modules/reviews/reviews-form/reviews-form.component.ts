import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Review } from 'src/app/models/review';

export interface ReviewFormData {
  type: string;
  review: Review;
}

@Component({
  selector: 'app-reviews-form',
  templateUrl: './reviews-form.component.html',
  styleUrls: ['./reviews-form.component.scss']
})
export class ReviewsFormComponent implements OnInit {

  fileMultiple = true;
  fileAccept = '.png, .jpeg, .jpg';

  rating: number;

  form: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<ReviewsFormComponent>,
    private formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: ReviewFormData
    ) {
      this.form = this.formBuilder.group({
        rating: [
          '',
          Validators.compose([
            Validators.required,
            Validators.min(0),
            Validators.max(5),
          ]),
        ],
        comment: [
          '',
          Validators.compose([
            Validators.required,
            Validators.minLength(10),
            Validators.maxLength(256),
          ]),
        ],
        images: ['', ]
      });
     }

  ngOnInit(): void {
  }

  add(): void{
    this.dialogRef.close({operation: 'add'});
  }

  cancelAdd(): void{
    this.dialogRef.close({operation: 'cancel'});
  }

  filesChanged(): void{
    const self = this;
    this.data.review.images = [];
    // let files= element.target.files;
    const files = this.form.getRawValue().images;
    Array.from(files).forEach(function(file){
      const reader = new FileReader();
      reader.onloadend = function() {
        self.data.review.images.push(reader.result as string);

      };
      reader.readAsDataURL(file as Blob);

    });
  }

  onRatingChanged(rating: number) {
    this.form.controls.rating.setValue(rating);
    this.data.review.rating = rating;
    this.rating = rating;
  }

}
