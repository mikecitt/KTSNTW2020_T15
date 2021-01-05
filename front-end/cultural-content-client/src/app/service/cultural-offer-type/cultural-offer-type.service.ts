import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CulturalOfferType } from '../../model/cultural-offer-type';

@Injectable()
export class CulturalOfferTypeService {

  private readonly path = "http://localhost:8080/api/types";

  constructor(private http: HttpClient) { }

  getAll(): Observable<CulturalOfferType[]>{
    return this.http.get<CulturalOfferType[]>(this.path + "/all");
  }
}
