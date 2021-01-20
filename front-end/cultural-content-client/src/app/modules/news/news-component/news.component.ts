import { SnackBarComponent } from './../../../core/snack-bar/snack-bar.component';
import { ConfirmDeleteComponent } from '../../../core/confirm-delete/confirm-delete.component';
import { NewsFormComponent } from '../news-form/news-form.component';
import { MatDialog } from '@angular/material/dialog';
import { SubscriptionService } from '../../../services/subscription/subsription.service';
import { NewsPage } from '../../../models/news-page';
import { NewsService } from '../../../services/news/news.service';
import { Component, DebugElement, OnInit } from '@angular/core';
import { News } from '../../../models/news';
import { MatCarousel, MatCarouselComponent } from '@ngmodule/material-carousel';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  newsPage: NewsPage;
  newsToAdd: News = {
    text: "",
    date: new Date(),
    images: []

  };
  slides = [{image: 'https://mdbootstrap.com/img/Photos/Slides/img%20(130).jpg'}, {image: 'https://gsr.dev/material2-carousel/assets/demo.png'},{image: 'https://gsr.dev/material2-carousel/assets/demo.png'}, {image: 'https://gsr.dev/material2-carousel/assets/demo.png'}, {image: 'https://gsr.dev/material2-carousel/assets/demo.png'}];

  private culturalOfferId: number = 1001;
  public currentNewsPage: number;
  private newsLimit: number = 2;

  constructor(
    private newsService:NewsService,
    private subService:SubscriptionService,
    public dialog: MatDialog,
    private snackBar: SnackBarComponent
  ) {
    this.currentNewsPage = 0;
   }

  ngOnInit(): void {
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
      this.snackBar.openSnackBar("Successfuly subscribed",'','green-snackbar');
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
      if(result.operation == "add") this.addNews();
      else if(result.operation == "cancel") this.clearNewsForm();
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

  openUpdateNewsDialog(): void{
    const dialogRef = this.dialog.open(NewsFormComponent, {
      width: '400px',
      data: {type: 'update', news: this.newsToAdd},
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result.operation == "update") this.updateNews();
      else if(result.operation == "cancel") this.cancelUpdating();
    });
  }

  updateNews(): void{
    console.log("update");
  }

  cancelUpdating(): void{
    console.log("cancel update");
  }

}
