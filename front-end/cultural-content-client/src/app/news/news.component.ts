import { ConfirmDeleteComponent } from './../confirm-delete/confirm-delete.component';
import { NewsFormComponent } from './news-form/news-form.component';
import { MatDialog } from '@angular/material/dialog';
import { SubsriptionService } from './../service/subscription/subsription.service';
import { NewsPage } from './../model/news-page';
import { NewsService } from './../service/news/news.service';
import { Component, DebugElement, OnInit } from '@angular/core';
import { News } from '../model/news';
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
    private subService:SubsriptionService,
    public dialog: MatDialog
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
        this.newsService.deleteNews(id).subscribe(() => {this.loadNews()});
        alert("Deleted news successfuly");
      }
    })
  }

  subscribe():void{
    this.subService.subscribeToOffer(this.culturalOfferId).subscribe(() => {});
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
      data: this.newsToAdd,
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result.operation == "add") this.addNews();
      else if(result.operation == "cancel") this.cancelAdding();
    });
  }

  addNews():void{
    this.newsToAdd.date = new Date();
    this.newsService.addNews(this.culturalOfferId, this.newsToAdd).subscribe((response) => {
      this.loadNews();
    });
  }

  cancelAdding():void{
    this.newsToAdd = {
      text: "",
      date: new Date(),
      images: []
    };
  }

}
