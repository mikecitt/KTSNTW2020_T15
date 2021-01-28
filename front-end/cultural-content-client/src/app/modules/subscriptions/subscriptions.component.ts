import { NewsService, SubscriptionService, UserService } from 'src/app/services';
import { Component, Input, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { News } from 'src/app/models/news';
import { NewsPage } from 'src/app/models/news-page';
import { MatDialog } from '@angular/material/dialog';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { ConfirmDeleteComponent } from 'src/app/core/confirm-delete/confirm-delete.component';
import { NewsFormComponent } from '../news/news-form/news-form.component';
import { SubscriptionPage } from 'src/app/models/subscription-page';

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.scss']
})
export class SubscriptionsComponent implements OnInit {

  subscriptionPage: SubscriptionPage;
  uploads_url = 'http://localhost:8080/uploads/';

  public currentPage: number;
  private pageLimit = 2;

  constructor(
    private subscriptionService: SubscriptionService,
    public dialog: MatDialog
  ) {
    this.currentPage = 0;
   }



   ngOnInit(): void {
     this.loadSubscriptions();
   }

  ngOnChanges() {
    this.loadSubscriptions();
  }

  loadSubscriptions(): void {
    this.subscriptionService
        .getAll(this.currentPage, this.pageLimit)
        .subscribe(res => { this.subscriptionPage = res; });
  }

  getNextPage(): void{
    this.currentPage++;
    this.loadSubscriptions();
  }

  getPreviousPage(): void{
    this.currentPage--;
    this.loadSubscriptions();
  }

}
