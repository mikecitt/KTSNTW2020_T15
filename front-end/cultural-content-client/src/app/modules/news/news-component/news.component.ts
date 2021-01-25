import { UserService } from 'src/app/services';
import { SnackBarComponent } from './../../../core/snack-bar/snack-bar.component';
import { ConfirmDeleteComponent } from '../../../core/confirm-delete/confirm-delete.component';
import { NewsFormComponent } from '../news-form/news-form.component';
import { MatDialog } from '@angular/material/dialog';
import { SubscriptionService } from '../../../services/subscription/subsription.service';
import { NewsPage } from '../../../models/news-page';
import { NewsService } from '../../../services/news/news.service';
import { Component, Input, OnInit } from '@angular/core';
import { News } from '../../../models/news';
import { environment } from 'src/environments/environment';
@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  @Input() 
  culturalOfferId: number;
  role: string;
  email: string;
  environment = environment;
  newsPage: NewsPage;
  selectedImages: string[];
  isSubscribed: boolean;
  newsToAdd: News = {
    text: "",
    date: new Date(),
    images: []

  };
  public currentNewsPage: number;
  private newsLimit: number = 2;

  constructor(
    private newsService:NewsService,
    private subService:SubscriptionService,
    public dialog: MatDialog,
    private snackBar: SnackBarComponent,
    private userService: UserService
  ) {
    this.currentNewsPage = 0;
   }

   

   ngOnInit(): void {
    if(this.isUser){
    this.subService.isSubscribed(this.culturalOfferId).subscribe(res => {this.isSubscribed = res; console.log(res)});
    }
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
    this.loadNews();
  }

  loadNews(): void{
    this.newsService
        .getAll(this.culturalOfferId, this.currentNewsPage, this.newsLimit)
        .subscribe(res => this.newsPage = res);
  }

  deleteNews(id:number):void{
    const dialogRef = this.dialog.open(ConfirmDeleteComponent,{
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {}
    })
    dialogRef.afterClosed().subscribe(result =>{
      if(result){
        this.newsService.deleteNews(id).subscribe((res) => {
          this.loadNews();
          this.snackBar.openSnackBar("News deleted successfuly",'','green-snackbar');
        });
      }
    })
  }

  subscribe():void{
    this.subService.subscribeToOffer(this.culturalOfferId).subscribe((res) => {
      this.isSubscribed = true;
      this.snackBar.openSnackBar("Successfuly subscribed",'','green-snackbar');
    });
  }

  unsubscribe():void{
    this.subService.unsubscribeFromOffer(this.culturalOfferId).subscribe((res) => {
      this.isSubscribed = false;
      this.snackBar.openSnackBar("Successfuly unsubscribed",'','green-snackbar');
    });
  }

  getNextNews(): void{
    this.currentNewsPage++;
    this.loadNews();
  }

  getPreviousNews(): void{
    this.currentNewsPage--;
    this.loadNews();
  }

  openCreateNewsDialog(): void{
    const dialogRef = this.dialog.open(NewsFormComponent, {
      width: '400px',
      data: {type: 'add', news: this.newsToAdd},
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(this.newsToAdd.images);
      if(result.operation == "add") this.addNews();
      else if(result.operation == "cancelAdd") this.clearNewsForm();
    });
  }

  addNews():void{
    this.newsToAdd.date = new Date();
    this.newsService.addNews(this.culturalOfferId, this.newsToAdd).subscribe((response) => {
      this.loadNews();
      this.clearNewsForm();
      this.snackBar.openSnackBar("News successfuly added",'','green-snackbar');
    });
  }

  clearNewsForm():void{
    this.newsToAdd = {
      text: "",
      date: new Date(),
      images: []
    };
  }

  openUpdateNewsDialog(news : News): void{
    let newsToUpdate = {...news};
    const dialogRef = this.dialog.open(NewsFormComponent, {
      width: '400px',
      data: {type: 'update', news: newsToUpdate},
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result.operation == "update") this.updateNews(news, newsToUpdate);
    });
  }

  updateNews(news: News, updatedNews: News): void{
    console.log("update");
    console.log(updatedNews);
    updatedNews.date = new Date();
    this.newsService.updateNews(updatedNews).subscribe((response) => {
      this.loadNews();
      this.snackBar.openSnackBar("News successfuly updated",'','green-snackbar');
    });

  }

  cancelUpdating(news :News): void{
    console.log("cancel update");
    console.log(news);
  }

}
