import { SubsriptionService } from './../service/subscription/subsription.service';
import { NewsPage } from './../model/news-page';
import { NewsService } from './../service/news/news.service';
import { Component, DebugElement, OnInit } from '@angular/core';
import { NewsResponse } from '../model/news-response';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  news: NewsResponse[] = [];
  newsPage: NewsPage;

  private culturalOfferId: number = 1001;
  public currentNewsPage: number;
  private newsLimit: number = 2;

  constructor(
    private newsService:NewsService,
    private subService:SubsriptionService
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
    this.newsService.deleteNews(id).subscribe(() => {this.loadNews()});
    
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

}
