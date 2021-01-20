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
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjExMTQyNTU1LCJleHAiOjE2MTExNDQzNTV9.SFLAhv5auyiO22uv_BOcvNy9esk_r4WbxO8UOv5drIinZGOFHB2ZpYHAKwviufMaegMsQ0PjGRnn7Q1x8ciLrw'});
  
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
