import { Component, OnInit } from '@angular/core';
import { CulturalOfferLocation } from 'src/app/model/culutral-offer-location';
import { CulturalOfferService } from 'src/app/service/cultural-offer/cultural-offer.service';
import { CulturalOfferType } from 'src/app/model/cultural-offer-type';
import { CulturalOfferSubType} from 'src/app/model/culutral-offer-subType';
import { CulturalOfferTypeService} from 'src/app/service/cultural-offer-type/cultural-offer-type.service';
import { CulturalOfferSubTypeService } from 'src/app/service/cultural-offer-subtype/cultural-offer-sub-type.service';
import { FilterRequest } from 'src/app/model/filter-request';
import { CulturalOfferResponse } from 'src/app/model/cultural-offer-response';

@Component({
  selector: 'app-map-page',
  templateUrl: './map-page.component.html',
  styleUrls: ['./map-page.component.scss']
})
export class MapPageComponent implements OnInit {

  culturalOfferLocations: CulturalOfferLocation[];
  types: CulturalOfferType[] = [];
  subTypes: CulturalOfferSubType[] = [];

  searchedLocation: string;

  constructor(
    private culturalOfferService: CulturalOfferService,
    private culturalOfferTypeService: CulturalOfferTypeService,
    private culturalOfferSubTypeService: CulturalOfferSubTypeService,
  ){}

  ngOnInit(): void {
    this.loadCulturalOffers();
    this.loadCulturalOfferTypes();
  }

  loadCulturalOffers(): void{
    this.culturalOfferService
        .getLocations()
        .subscribe(res =>
          this.culturalOfferLocations = res
        )
  }
  loadCulturalOfferTypes(): void{
    this.culturalOfferTypeService
        .getAll()
        .subscribe(res => this.types = res)
  }
  loadSubTypes(typeId: any): void{
    console.log(this.culturalOfferLocations);
    this.culturalOfferSubTypeService
        .getAll(typeId)
        .subscribe(res => this.subTypes = res);
  }

  applyFilter(filterReq: any): void{
    this.findLocation(filterReq.location);
    this.culturalOfferService
        .filterCulturalOffers(filterReq.request)
        .subscribe(res => this.culturalOfferLocations = res);
  }

  resetFilter(reset: string): void{
    this.loadCulturalOffers();
  }

  findLocation(location: string){
    this.searchedLocation = location;
  }

}
