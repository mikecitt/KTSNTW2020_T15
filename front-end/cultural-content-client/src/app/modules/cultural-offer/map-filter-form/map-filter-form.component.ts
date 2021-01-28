import { Component, Input, OnInit, Output } from '@angular/core';
import { FilterRequest } from '../../../models/filter-request';
import { EventEmitter } from '@angular/core';
import { CulturalOfferType } from 'src/app/models/cultural-offer-type';
import { CulturalOfferSubType} from 'src/app/models/culutral-offer-subType';
import { FormGroup, FormControl } from '@angular/forms';
import { CulturalOfferSubTypeService } from 'src/app/services/cultural-offer-subtype/cultural-offer-sub-type.service';

@Component({
  selector: 'app-map-filter-form',
  templateUrl: './map-filter-form.component.html',
  styleUrls: ['./map-filter-form.component.scss']
})
export class MapFilterFormComponent implements OnInit {

  @Output()
  applyFilterEvent = new EventEmitter<any>();
  @Output()
  resetFilterEvent = new EventEmitter<string>();
  @Output()
  getSubTypesEvent = new EventEmitter<CulturalOfferType>();

  @Input()
  culturalOfferTypes: CulturalOfferType[] = [];
  @Input()
  culturalOfferSubTypes: CulturalOfferSubType[] = [];

  filterForm = new FormGroup({
    type: new FormControl(''),
    subType: new FormControl(''),
    searchLocation: new FormControl('')
  });

  selectedType: CulturalOfferType;
  selectedSubType: CulturalOfferSubType;
  disabled = true;

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(): void{
    let filterReq: FilterRequest;

    filterReq = {
      subTypeName: this.filterForm.value.subType.name == undefined ? '' : this.filterForm.value.subType.name,
      typeName: this.filterForm.value.type.name == undefined ? '' : this.filterForm.value.type.name,
    };
    this.applyFilterEvent.emit({ request: filterReq, location: this.filterForm.value.searchLocation});
  }

  onSelectChange(newSelected: any): void{
    this.getSubTypesEvent.emit(this.filterForm.value.type.id);
    this.disabled = false;
  }

  resetFilter(): void{
    this.filterForm.reset({type: '', subType: '', searchLocation: ''});
    this.disabled = true;
    this.resetFilterEvent.emit('RESET');
  }

}
