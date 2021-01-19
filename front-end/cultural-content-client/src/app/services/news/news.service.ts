import { NewsPage } from '../../models/news-page';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { News } from 'src/app/models/news';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private readonly path = "http://localhost:8080/api/news/";
  private readonly ht = new HttpHeaders({
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE2MTEwODIwNjQsImV4cCI6MTYxMTA4Mzg2NH0.XuOghOrv2FqK6yF90du8kJC-P9nZx8ASUC9Hjrj3FE2JF7DpDy1IPGAugMXiDigiu8fI9z_8ozNMK8PibLFOqw'});
  
  constructor(private http:HttpClient) { }

  getAll(culturalOfferId: number, page: number, limit:number):Observable<NewsPage>{
    let params: HttpParams = new HttpParams()
        .append('page', page.toString())
        .append('size', limit.toString());
    
    return this.http.get<NewsPage>(this.path + "culturalOffer/" + culturalOfferId, {headers: this.ht, params : params});
  }

  deleteNews(newsId:number):Observable<{}>{
    return this.http.delete(this.path+ newsId, {headers: this.ht});    
  }

  addNews(culturalOfferId: number, newsToAdd: News): Observable<News>{
    return this.http.post<News>(environment.api_url + "/news/" + culturalOfferId, newsToAdd,{headers : this.ht});
  }

  
}
