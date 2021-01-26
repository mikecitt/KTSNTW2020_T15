import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { Review } from 'src/app/models/review';
import { ReviewPage } from 'src/app/models/review-page';
import { UserService } from 'src/app/services';
import { ReviewService } from 'src/app/services/reviews/review.service';
import { ReviewsFormComponent } from '../reviews-form/reviews-form.component';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {

  @Input()
  culturalOfferId: number;

  reviewFromDialog: Review = {
    rating: 0,
    comment: "",
    authorUsername: "",
    images: []
  };

  uploads_url = 'http://localhost:8080/uploads/';
  reviewPage: ReviewPage;
  public currentPage: number;
  private pageLimit: number = 2;

  constructor(private userService: UserService, private reviewService: ReviewService, public dialog: MatDialog, private snackBar: SnackBarComponent) { 
    this.currentPage = 0;
  }

  ngOnInit(): void {
    this.loadReviews();
   }
 
   get isAuthorized() {
     return this.userService.getRole() != null;
   }
 
   get isAdmin() {
     return this.userService.getRole() == 'ROLE_ADMIN';
   }
 
   get isUser() {
     return this.userService.getRole() == 'ROLE_USER';
   }

  ngOnChanges() {
    this.loadReviews();
  }

  loadReviews(): void{
    this.reviewService
        .getAll(this.culturalOfferId, this.currentPage, this.pageLimit)
        .subscribe(res => { this.reviewPage = res });
  }

  getNextPage(): void{
    this.currentPage++;
    this.loadReviews();
  }

  getPreviousPage(): void{
    this.currentPage--;
    this.loadReviews();
  }

  openDialog(): void{
    const dialogRef = this.dialog.open(ReviewsFormComponent, {
      width: '400px',
      data: {type: 'add', review: this.reviewFromDialog},
      disableClose: true
    });

  dialogRef.afterClosed().subscribe(result => {
    if(result.operation == "add")
      this.addReview();
    else if(result.operation == "cancelAdd") 
      this.clearForm();
    });
  }

  clearForm():void{
    this.reviewFromDialog = {
      comment: "",
      rating: 0,
      authorUsername: "",
      images: []
    };
  }

  
  addReview():void{
    this.reviewService.add(this.culturalOfferId, this.reviewFromDialog).subscribe((response) => {
      this.loadReviews();
      this.clearForm();
      this.snackBar.openSnackBar("Review successfuly added",'','green-snackbar');
    });
  }
}
