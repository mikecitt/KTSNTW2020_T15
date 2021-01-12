import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferType } from '../../model/cultural-offer-type';
import { environment } from 'src/environments/environment';

@Injectable()
export class CulturalOfferTypeService {

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjEwMTAwNjQyLCJleHAiOjE2MTAxMDI0NDJ9.28id310-l8M519ubCsHSl9PLZB-7pqRE1M-q8i3d26GXepEmOUKbjhMlg0bCgU2HgbsULcpe7g_77A05VLrj1Q',
  });

  constructor(private http: HttpClient) { }

  getAll(): Observable<CulturalOfferType[]>{
    return this.http.get<CulturalOfferType[]>(environment.api_url + "/types/all");
  }

  saveType(req: CulturalOfferType): Observable<CulturalOfferType>{
    return this.http.post<CulturalOfferType>
      (environment.api_url + "/types", req,{headers:this.headers});
  }

  updateType(req: CulturalOfferType): Observable<CulturalOfferType>{
    return this.http.put<CulturalOfferType>
      (environment.api_url + `/types/${req._id}`, req ,{headers:this.headers});
  }

  deleteType(type_id: any): Observable<void>{
    return this.http.delete<void>(environment.api_url + `/types/${type_id}`, {headers: this.headers});
  }

}
