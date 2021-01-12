import { Injectable } from '@angular/core';
import { CulturalOfferSubType } from 'src/app/model/culutral-offer-subType';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable()
export class CulturalOfferSubTypeService {

  constructor(private http: HttpClient) { }

  getAll(typeId:any): Observable<CulturalOfferSubType[]>{
    return this.http.get<CulturalOfferSubType[]>(environment.api_url + "/sub-types/all?typeId=" + typeId);
  }
}
