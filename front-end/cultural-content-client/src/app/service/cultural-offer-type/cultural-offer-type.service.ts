import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferType } from '../../model/cultural-offer-type';
import { environment } from 'src/environments/environment';

@Injectable()
export class CulturalOfferTypeService {

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjEwMDMxNjk0LCJleHAiOjE2MTAwMzM0OTR9.xFwCSuI8SacfFZTUTQgSpb_viU9C1l0jsbmaokrXPUDa__4CS-JdXDneFB9UL_J2lSMtrhPBcAlJN5X4cta5RA',
  });

  constructor(private http: HttpClient) { }

  getAll(): Observable<CulturalOfferType[]>{
    return this.http.get<CulturalOfferType[]>(environment.api_url + "/types/all");
  }

  saveType(req: CulturalOfferType): Observable<CulturalOfferType>{
    return this.http.post<CulturalOfferType>
      (environment.api_url + "/types", req,{headers:this.headers});
  }

}
