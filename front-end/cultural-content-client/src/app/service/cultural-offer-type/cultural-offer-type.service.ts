import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferType } from '../../model/cultural-offer-type';
import { environment } from 'src/environments/environment';

@Injectable()
export class CulturalOfferTypeService {

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjEwMTkzMzQ0LCJleHAiOjE2MTAxOTUxNDR9.fctyV62Gm85u0u211wk9IwDnTD36uXUe8pBmWQevj2n0gGGaTLMQU7JLRPRzS7ph2AEJ9Q3JukWhCVX4dPgOzA',
  });

  constructor(private http: HttpClient) { }

  getAll(): Observable<CulturalOfferType[]>{
    return this.http.get<CulturalOfferType[]>(environment.api_url + "/types/all");
  }

  getAllPaginated(pageIndex: number): Observable<CulturalOfferType[]>{
    let url = `/types?page=${pageIndex}&size=5`;
    return this.http.get<CulturalOfferType[]>(environment.api_url + url);
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
