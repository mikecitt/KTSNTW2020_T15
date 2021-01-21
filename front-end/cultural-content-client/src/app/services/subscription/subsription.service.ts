import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private readonly path = "http://localhost:8080/api/subscriptions";
  private readonly ht = new HttpHeaders({
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjExMTYxNjI0LCJleHAiOjE2MTExNjM0MjR9.fn5WNwaCdbWvc0KCNyr4W5dRMY6vaaLjgRrqShtPGpkUq5Wye6JmfIrV90LhURZ9t5ZauRW4ipHEBrVGk_wDaQ'});
  
  constructor(private http:HttpClient) { }

  subscribeToOffer(culturalOfferId: number):Observable<{}>{
    return this.http.post(this.path + "?id=" + culturalOfferId , null, {headers: this.ht});
  }
}
