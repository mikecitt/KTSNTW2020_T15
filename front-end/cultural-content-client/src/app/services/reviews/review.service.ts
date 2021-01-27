import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from 'src/app/models/review';
import { ReviewPage } from 'src/app/models/review-page';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http:HttpClient) { }

  add(culturalOfferId: number, reviewToAdd: Review): Observable<Review>{
    return this.http.post<Review>(environment.api_url + '/review?culturalOfferId=' + culturalOfferId, reviewToAdd);
  }

  getAll(culturalOfferId: number, page: number, limit: number): Observable<ReviewPage>{
    const params: HttpParams = new HttpParams()
        .append('page', page.toString())
        .append('size', limit.toString());
    return this.http.get<ReviewPage>(environment.api_url + '/review?culturalOfferId=' + culturalOfferId, {params : params});
  }
}
