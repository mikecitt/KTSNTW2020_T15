import { Injectable } from '@angular/core';
import { CulturalOfferSubType } from 'src/app/models/culutral-offer-subType';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SubTypePage } from 'src/app/models/type-page';

@Injectable()
export class CulturalOfferSubTypeService {
  headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
   }

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
      (environment.api_url + `/sub-types?typeId=${req.type.id}`, req ,{headers: this.headers});
  }

  updateSubType(req: CulturalOfferSubType): Observable<CulturalOfferSubType>{
    return this.http.put<CulturalOfferSubType>
      (environment.api_url + `/sub-types/${req.id}?typeId=${req.type.id}`, req, {headers: this.headers});
  }

  deleteSubType(subType: CulturalOfferSubType): Observable<void>{
    return this.http.delete<void>
      (environment.api_url + `/sub-types/${subType.id}?typeId=${subType.type.id}`, {headers: this.headers});
  }

}
