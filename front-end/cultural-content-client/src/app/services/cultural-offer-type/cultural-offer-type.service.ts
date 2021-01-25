import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferType } from '../../models/cultural-offer-type';
import { environment } from 'src/environments/environment';
import { TypePage } from 'src/app/models/type-page';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class CulturalOfferTypeService {

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  getAll(): Observable<CulturalOfferType[]>{
    return this.http.get<CulturalOfferType[]>(environment.api_url + '/types/all');
  }

  getAllPaginated(pageIndex: number, pageSize: number): Observable<TypePage>{
    const url = `/types?page=${pageIndex}&size=${pageSize}`;
    return this.http.get<TypePage>(environment.api_url + url);
  }

  createType(req: CulturalOfferType): Observable<CulturalOfferType>{
    return this.http.post<CulturalOfferType>
      (environment.api_url + '/types', req);
  }

  updateType(req: CulturalOfferType): Observable<CulturalOfferType>{
    return this.http.put<CulturalOfferType>
      (environment.api_url + `/types/${req.id}`, req );
  }

  deleteType(type_id: any): Observable<void>{
    return this.http.delete<void>(environment.api_url + `/types/${type_id}`);
  }

}
