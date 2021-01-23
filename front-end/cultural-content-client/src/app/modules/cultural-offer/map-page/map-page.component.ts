import { Component, OnInit } from '@angular/core';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';
import { CulturalOfferService } from 'src/app/services/cultural-offer/cultural-offer.service';
import { CulturalOfferType } from 'src/app/models/cultural-offer-type';
import { CulturalOfferSubType } from 'src/app/models/culutral-offer-subType';
import { CulturalOfferTypeService } from 'src/app/services/cultural-offer-type/cultural-offer-type.service';
import { CulturalOfferSubTypeService } from 'src/app/services/cultural-offer-subtype/cultural-offer-sub-type.service';
import { FilterRequest } from 'src/app/models/filter-request';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { MatDialog } from '@angular/material/dialog';
import { NewCulturalOfferDialogComponent } from '../new-cultural-offer-dialog/new-cultural-offer-dialog.component';

@Component({
  selector: 'app-map-page',
  templateUrl: './map-page.component.html',
  styleUrls: ['./map-page.component.scss'],
})
export class MapPageComponent implements OnInit {
  culturalOfferLocations: CulturalOfferResponse[];
  types: CulturalOfferType[] = [];
  subTypes: CulturalOfferSubType[] = [];

  searchedLocation: string;

  constructor(
    private culturalOfferService: CulturalOfferService,
    private culturalOfferTypeService: CulturalOfferTypeService,
    private culturalOfferSubTypeService: CulturalOfferSubTypeService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadCulturalOffers();
    this.loadCulturalOfferTypes();
  }

  loadCulturalOffers(): void {
    this.culturalOfferService
      .getLocations()
      .subscribe((res) => (this.culturalOfferLocations = res));
  }
  loadCulturalOfferTypes(): void {
    this.culturalOfferTypeService
      .getAll()
      .subscribe((res) => (this.types = res));
  }
  loadSubTypes(typeId: any): void {
    this.culturalOfferSubTypeService
      .getAll(typeId)
      .subscribe((res) => (this.subTypes = res));
  }

  applyFilter(filterReq: any): void {
    this.findLocation(filterReq.location);
    this.culturalOfferService
      .filterCulturalOffers(filterReq.request)
      .subscribe((res) => (this.culturalOfferLocations = res));
  }

  resetFilter(reset: string): void {
    this.loadCulturalOffers();
  }

  findLocation(location: string) {
    this.searchedLocation = location;
  }

  openNewDialog() {
    const dialogRef = this.dialog.open(NewCulturalOfferDialogComponent, {
      width: '350px',
    });
  }
}
