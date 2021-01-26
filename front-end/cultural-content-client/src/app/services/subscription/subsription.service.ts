import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NewsPage } from 'src/app/models/news-page';
import { SubscriptionPage } from 'src/app/models/subscription-page';

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

  getAll(page: number, limit: number): Observable<SubscriptionPage>{
    const params: HttpParams = new HttpParams()
        .append('page', page.toString())
        .append('size', limit.toString());
    return this.http.get<SubscriptionPage>(environment.api_url + "/subscriptions", { params : params });
  }
}
