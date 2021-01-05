import { Injectable } from '@angular/core';
import { CulturalOfferSubType } from 'src/app/model/culutral-offer-subType';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class CulturalOfferSubTypeService {

  private readonly path = "http://localhost:8080/api/sub-types";

  constructor(private http: HttpClient) { }

  getAll(typeId:any): Observable<CulturalOfferSubType[]>{
    return this.http.get<CulturalOfferSubType[]>(this.path + "/all?typeId=" + typeId);
  }
}
