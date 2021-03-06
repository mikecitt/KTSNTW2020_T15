import { NewsPage } from '../../models/news-page';
import { HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { News } from 'src/app/models/news';
import { environment } from 'src/environments/environment';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  getAll(culturalOfferId: number, page: number, limit: number): Observable<NewsPage>{
    const params: HttpParams = new HttpParams()
        .append('page', page.toString())
        .append('size', limit.toString());
    return this.http.get<NewsPage>(environment.api_url + '/news/culturalOffer/' + culturalOfferId, {params});
  }

  deleteNews(newsId: number): Observable<{}>{
    return this.http.delete(environment.api_url + '/news/' + newsId);
  }

  addNews(culturalOfferId: number, newsToAdd: News): Observable<News>{
    return this.http.post<News>(environment.api_url + '/news/' + culturalOfferId, newsToAdd);
  }

  updateNews(newsToUpdate: News): Observable<News>{
    return this.http.put<News>(environment.api_url + '/news/' + newsToUpdate.id, newsToUpdate);
  }

}
