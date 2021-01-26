import { Component, Input, OnInit } from '@angular/core';
import { ReviewPage } from 'src/app/models/review-page';
import { UserService } from 'src/app/services';
import { ReviewService } from 'src/app/services/reviews/review.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {

  @Input()
  culturalOfferId: number;

  uploads_url = 'http://localhost:8080/uploads/';
  reviewPage: ReviewPage;
  public currentPage: number;
  private pageLimit: number = 2;

  constructor(private userService: UserService, private reviewService: ReviewService) { 
    this.currentPage = 0;
  }

  ngOnInit(): void {
    console.log(this.culturalOfferId);
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
        .subscribe(res => { this.reviewPage = res; console.log(res) });
  }

  getNextPage(): void{
    this.currentPage++;
    this.loadReviews();
  }

  getPreviousPage(): void{
    this.currentPage--;
    this.loadReviews();
  }

}
