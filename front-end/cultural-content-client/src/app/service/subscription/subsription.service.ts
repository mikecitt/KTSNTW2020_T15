import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubsriptionService {
  private readonly path = "http://localhost:8080/api/subscriptions";
  private readonly ht = new HttpHeaders({
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjEwOTg2MDUxLCJleHAiOjE2MTA5ODc4NTF9.40hAKqQUq1LXT8dY6Q64DTx_hQ98eebfVlhtmcs5ErfHyEdChCcU9gLKFNw5AkevQOWUuvKT0jVYHKZsTlzWmA'});
  
  constructor(private http:HttpClient) { }

  subscribeToOffer(culturalOfferId: number):Observable<{}>{
    return this.http.post(this.path + "?id=" + culturalOfferId , null, {headers: this.ht});
  }
}
