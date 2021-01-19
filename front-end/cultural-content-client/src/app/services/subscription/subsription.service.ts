import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private readonly path = "http://localhost:8080/api/subscriptions";
  private readonly ht = new HttpHeaders({
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE2MTEwODIwNjQsImV4cCI6MTYxMTA4Mzg2NH0.XuOghOrv2FqK6yF90du8kJC-P9nZx8ASUC9Hjrj3FE2JF7DpDy1IPGAugMXiDigiu8fI9z_8ozNMK8PibLFOqw'});
  
  constructor(private http:HttpClient) { }

  subscribeToOffer(culturalOfferId: number):Observable<{}>{
    return this.http.post(this.path + "?id=" + culturalOfferId , null, {headers: this.ht});
  }
}
