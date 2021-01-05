import { Component, OnInit } from '@angular/core';
import { CulturalOfferLocation } from 'src/app/model/culutral-offer-location';
import { CulturalOfferService } from 'src/app/service/cultural-offer/cultural-offer.service';
import { CulturalOfferType } from 'src/app/model/cultural-offer-type';
import { CulturalOfferSubType} from 'src/app/model/culutral-offer-subType';
import { CulturalOfferTypeService} from 'src/app/service/cultural-offer-type/cultural-offer-type.service';
import { CulturalOfferSubTypeService } from 'src/app/service/cultural-offer-subtype/cultural-offer-sub-type.service';

@Component({
  selector: 'app-map-page',
  templateUrl: './map-page.component.html',
  styleUrls: ['./map-page.component.scss']
})
export class MapPageComponent implements OnInit {

  culturalOffersLocations: CulturalOfferLocation[];
  culturalOfferTypes: CulturalOfferType[] = [];
  culturalOfferSubTypes: CulturalOfferSubType[] = [];

  selectedType: CulturalOfferType;

  constructor(
    private culturalOfferService: CulturalOfferService,
    private culturalOfferTypeService: CulturalOfferTypeService,
    private culturalOfferSubTypeService: CulturalOfferSubTypeService,
  ){}

  ngOnInit(): void {
    this.loadOffers();
    this.loadTypes();
  }

  onSelectChange(newSelected: any): void{
    //this.selected = newSelected.value;
    console.log(this.selectedType);
  }

  loadOffers(): void{
    this.culturalOfferService
        .getAll()
        .subscribe(res =>
          this.culturalOffersLocations = res.map(offer => offer.location)
        )
  }
  loadTypes(): void{
    this.culturalOfferTypeService
        .getAll()
        .subscribe(res => this.culturalOfferTypes = res)
  }


}
