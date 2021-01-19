import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { FilterRequest } from 'src/app/models/filter-request';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';

@Injectable()
export class CulturalOfferService {

  private readonly path = "http://localhost:8080/api/cultural-offer";

  constructor(private http:HttpClient) { }

  getLocations():Observable<CulturalOfferLocation[]> {
    let ht = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE2MDk2ODIwODUsImV4cCI6MTYwOTY4Mzg4NX0.TYRQh3jehALCqTnP6ld_tSq9HUT_t-rBeBIqXibCWe_32V1Yn4TK4tqxuNkOCAzRg4TuhUzlVIRyeaWeIs650Q',
  });
    return this.http.get<CulturalOfferLocation[]>(this.path + "/all", {headers:ht});
  }
  filterCulturalOffers(filterReq: FilterRequest): Observable<CulturalOfferLocation[]>{
    return this.http.get<CulturalOfferLocation[]>(this.path + `/search?culturalOfferName=&subTypeName=${filterReq.subTypeName}&typeName=${filterReq.typeName}`);
  }

}
