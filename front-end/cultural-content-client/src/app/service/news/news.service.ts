import { NewsPage } from './../../model/news-page';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private readonly path = "http://localhost:8080/api/news";
  private readonly ht = new HttpHeaders({'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjEwMTIzMzA0LCJleHAiOjE2MTAxMjUxMDR9.iNNYyzSumgiK6NCNCvua1IYxKEV38P6A4yATRtUDkDvizaH_Xg1v-YqqkLmButzsTfxL9NEm6dbdTv3AnlqEtQ'});
  
  constructor(private http:HttpClient) { }

  getAll(culturalOfferId: number, page: number, limit:number):Observable<NewsPage>{
    let params: HttpParams = new HttpParams()
        .append('page', page.toString())
        .append('size', limit.toString());
    
    return this.http.get<NewsPage>(this.path + "/culturalOffer/" + culturalOfferId, {headers: this.ht, params});
  }

  
}
