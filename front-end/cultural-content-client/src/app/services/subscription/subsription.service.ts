import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  
  constructor(private http: HttpClient) { }

  subscribeToOffer(culturalOfferId: number): Observable<{}>{
    return this.http.post(environment.api_url + "/subscriptions?id=" + culturalOfferId , null);
  }

  unsubscribeFromOffer(culturalOfferId: number): Observable<{}>{
    return this.http.delete(environment.api_url + "/subscriptions?id=" + culturalOfferId);
  }

  isSubscribed(culturalOfferId: number): Observable<boolean>{
    return this.http.get<boolean>(environment.api_url + "/subscriptions/" + culturalOfferId);
  }
}
