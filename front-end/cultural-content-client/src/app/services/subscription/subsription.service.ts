import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private readonly path = "http://localhost:8080/api/subscriptions";
  private readonly ht = new HttpHeaders({
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjExMTQyNTU1LCJleHAiOjE2MTExNDQzNTV9.SFLAhv5auyiO22uv_BOcvNy9esk_r4WbxO8UOv5drIinZGOFHB2ZpYHAKwviufMaegMsQ0PjGRnn7Q1x8ciLrw'});
  
  constructor(private http:HttpClient) { }

  subscribeToOffer(culturalOfferId: number):Observable<{}>{
    return this.http.post(this.path + "?id=" + culturalOfferId , null, {headers: this.ht});
  }
}
