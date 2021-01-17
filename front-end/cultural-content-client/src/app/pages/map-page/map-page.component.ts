import { Component, OnInit } from '@angular/core';
import { CulturalOfferLocation } from 'src/app/model/culutral-offer-location';
import { CulturalOfferService } from 'src/app/service/cultural-offer/cultural-offer.service';
import { CulturalOfferType } from 'src/app/model/cultural-offer-type';
import { CulturalOfferSubType} from 'src/app/model/culutral-offer-subType';
import { CulturalOfferTypeService} from 'src/app/service/cultural-offer-type/cultural-offer-type.service';
import { CulturalOfferSubTypeService } from 'src/app/service/cultural-offer-subtype/cultural-offer-sub-type.service';
import { FilterRequest } from 'src/app/model/filter-request';

@Component({
  selector: 'app-map-page',
  templateUrl: './map-page.component.html',
  styleUrls: ['./map-page.component.scss']
})
export class MapPageComponent implements OnInit {

  culturalOffersLocations: CulturalOfferLocation[];
  culturalOfferTypes: CulturalOfferType[] = [];
  culturalOfferSubTypes: CulturalOfferSubType[] = [];

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
        .getAll()
        .subscribe(res =>
          this.culturalOffersLocations = res.map(offer => offer.location)
        )
  }
  loadCulturalOfferTypes(): void{
    this.culturalOfferTypeService
        .getAll()
        .subscribe(res => this.culturalOfferTypes = res)
  }
  loadSubTypes(typeId: any): void{
    this.culturalOfferSubTypeService
        .getAll(typeId)
        .subscribe(res => this.culturalOfferSubTypes = res);
  }

  applyFilter(filterReq: any): void{
    this.findLocation(filterReq.location);
    this.culturalOfferService
        .filterCulturalOffers(filterReq.request)
        .subscribe(res => this.culturalOffersLocations = res.map(offer => offer.location));
  }

  resetFilter(reset: string): void{
    this.loadCulturalOffers();
  }

  findLocation(location: string){
    // console.log(location);
    this.searchedLocation = location;
  }

}
