import { Injectable } from '@angular/core';
import { CulturalOfferSubType } from 'src/app/models/culutral-offer-subType';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SubTypePage } from 'src/app/models/type-page';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class CulturalOfferSubTypeService {

  constructor(private http: HttpClient, private cookieService: CookieService) {}

  getAll(typeId:any): Observable<CulturalOfferSubType[]>{
    return this.http.get<CulturalOfferSubType[]>(environment.api_url + "/sub-types/all?typeId=" + typeId);
  }

  getAllPaginated(pageIndex: number, pageSize: number, typeId: number): Observable<SubTypePage>{
    let url = `/sub-types?typeId=${typeId}&page=${pageIndex}&size=${pageSize}`;
    return this.http.get<SubTypePage>(environment.api_url + url);
  }

  createSubType(req: CulturalOfferSubType): Observable<CulturalOfferSubType>{
    // this.headers = this.headers.append('typeId', req.type.id);  nesto ne radi
    return this.http.post<CulturalOfferSubType>
      (environment.api_url + `/sub-types?typeId=${req.type.id}`, req );
  }

  updateSubType(req: CulturalOfferSubType): Observable<CulturalOfferSubType>{
    const params: HttpParams = new HttpParams()
      .append('typeId', req.type.id);

    return this.http.put<CulturalOfferSubType>
      (environment.api_url + `/sub-types/${req.id}`, req, {headers: {}, params: params});
  }

  deleteSubType(subType: CulturalOfferSubType): Observable<void>{
    const params: HttpParams = new HttpParams()
        .append('typeId', subType.type.id);

    return this.http.delete<void>
      (environment.api_url + `/sub-types/${subType.id}`, {headers: {}, params: params});
  }

}
