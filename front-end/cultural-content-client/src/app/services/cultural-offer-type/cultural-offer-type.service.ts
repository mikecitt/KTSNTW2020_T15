import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferType } from '../../models/cultural-offer-type';
import { environment } from 'src/environments/environment';
import { TypePage } from 'src/app/models/type-page';

@Injectable()
export class CulturalOfferTypeService {
  headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
   }

  getAll(): Observable<CulturalOfferType[]>{
    return this.http.get<CulturalOfferType[]>(environment.api_url + "/types/all");
  }

  getAllPaginated(pageIndex: number, pageSize: number): Observable<TypePage>{
    let url = `/types?page=${pageIndex}&size=${pageSize}`;
    return this.http.get<TypePage>(environment.api_url + url);
  }

  createType(req: CulturalOfferType): Observable<CulturalOfferType>{
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
