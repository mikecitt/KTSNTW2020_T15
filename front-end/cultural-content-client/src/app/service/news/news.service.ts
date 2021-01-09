import { NewsPage } from './../../model/news-page';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private readonly path = "http://localhost:8080/api/news/";
  private readonly ht = new HttpHeaders({
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE2MTAxODg1MzcsImV4cCI6MTYxMDE5MDMzN30.qni3OxPzUI2bVI2vTWyajdEdb66PSDvTM3m8aa52cqTY2AqjKAMGQfHLajymnVXtwkTRJwAJSvibhRWHH708kA'});
  
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

  
}
