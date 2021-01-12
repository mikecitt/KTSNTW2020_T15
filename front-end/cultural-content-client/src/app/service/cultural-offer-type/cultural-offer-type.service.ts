import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferType } from '../../model/cultural-offer-type';
import { environment } from 'src/environments/environment';
import { TypePage } from 'src/app/model/type-page';

@Injectable()
export class CulturalOfferTypeService {

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjEwMzg1MjE4LCJleHAiOjE2MTAzODcwMTh9.Vv1gXj_vBv9YW_05zSM2iCE-tHDYeC9EifCTO1h9zlkJC5dcTNvpTQaFVWFk68oNOvN9i9VLWAYPA1-Ilf_buw',
  });

  constructor(private http: HttpClient) { }

  getAll(): Observable<CulturalOfferType[]>{
    return this.http.get<CulturalOfferType[]>(environment.api_url + "/types/all");
  }

  getAllPaginated(pageIndex: number, pageSize: number): Observable<TypePage>{
    let url = `/types?page=${pageIndex}&size=${pageSize}`;
    return this.http.get<TypePage>(environment.api_url + url);
  }

  saveType(req: CulturalOfferType): Observable<CulturalOfferType>{
    return this.http.post<CulturalOfferType>
      (environment.api_url + "/types", req,{headers:this.headers});
  }

  updateType(req: CulturalOfferType): Observable<CulturalOfferType>{
    return this.http.put<CulturalOfferType>
      (environment.api_url + `/types/${req.id}`, req ,{headers:this.headers});
  }

  deleteType(type_id: any): Observable<void>{
    return this.http.delete<void>(environment.api_url + `/types/${type_id}`, {headers: this.headers});
  }

}
