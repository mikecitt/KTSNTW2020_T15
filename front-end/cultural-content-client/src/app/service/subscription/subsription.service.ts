import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubsriptionService {
  private readonly path = "http://localhost:8080/api/subscriptions";
  private readonly ht = new HttpHeaders({
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE2MTAxODg1MzcsImV4cCI6MTYxMDE5MDMzN30.qni3OxPzUI2bVI2vTWyajdEdb66PSDvTM3m8aa52cqTY2AqjKAMGQfHLajymnVXtwkTRJwAJSvibhRWHH708kA'});
  
  constructor(private http:HttpClient) { }

  subscribeToOffer(culturalOfferId: number):Observable<{}>{
    return this.http.post(this.path + "?id=" + culturalOfferId , null, {headers: this.ht});
  }
}
