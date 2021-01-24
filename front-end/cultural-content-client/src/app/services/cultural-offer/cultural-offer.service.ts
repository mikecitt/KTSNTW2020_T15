import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { FilterRequest } from 'src/app/models/filter-request';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';
import { Geocoder } from 'src/app/models/geocoder';
import { environment } from 'src/environments/environment';
import { CulturalOfferRequest } from 'src/app/models/cultural-offer-request';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class CulturalOfferService {
  private readonly path = 'http://localhost:8080/api/cultural-offer';

  constructor(private http: HttpClient, private cookieService: CookieService) {}

  getLocations(): Observable<CulturalOfferResponse[]> {
    let ht = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization:
        'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjdWx0dXJlY29udGVudCIsInN1YiI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE2MDk2ODIwODUsImV4cCI6MTYwOTY4Mzg4NX0.TYRQh3jehALCqTnP6ld_tSq9HUT_t-rBeBIqXibCWe_32V1Yn4TK4tqxuNkOCAzRg4TuhUzlVIRyeaWeIs650Q',
    });

    return this.http.get<CulturalOfferResponse[]>(this.path + '/all', {
      headers: ht,
    });
  }

  insert(
    request: CulturalOfferRequest,
    subTypeId: number
  ): Observable<CulturalOfferResponse> {
    console.log(this.cookieService.get('token'));
    let ht = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.cookieService.get('token')}`,
    });

    return this.http.post<CulturalOfferResponse>(
      this.path + `?subTypeId=${subTypeId}`,
      request,
      {
        headers: ht,
      }
    );
  }

  filterCulturalOffers(
    filterReq: FilterRequest
  ): Observable<CulturalOfferResponse[]> {
    return this.http.get<CulturalOfferResponse[]>(
      this.path +
        `/search?culturalOfferName=&subTypeName=${filterReq.subTypeName}&typeName=${filterReq.typeName}`
    );
  }
  getMapboxLocations(value: string): Observable<Geocoder> {
    return this.http.get<Geocoder>(
      `https://api.mapbox.com/geocoding/v5/mapbox.places/
    ${value}.json?access_token=${environment.mapboxKey}`
    );
  }
}
