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

  private culturalOfferId: number;
  public currentNewsPage: number;
  private newsLimit: number = 1;

  constructor(
    private newsService:NewsService
  ) {
    this.currentNewsPage = 0;
   }

  ngOnInit(): void {
    this.loadNews();
  }

  loadNews(): void{
    this.newsService
        .getAll(1001, this.currentNewsPage, this.newsLimit)
        .subscribe(res => {this.newsPage = res; console.log(this.newsPage);});
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
