import { Component, Input, OnInit, Output } from '@angular/core';
import { FilterRequest } from '../model/filter-request';
import { EventEmitter } from '@angular/core';
import { CulturalOfferType } from 'src/app/model/cultural-offer-type';
import { CulturalOfferSubType} from 'src/app/model/culutral-offer-subType';
import { FormGroup, FormControl } from '@angular/forms';
import { CulturalOfferSubTypeService } from 'src/app/service/cultural-offer-subtype/cultural-offer-sub-type.service';

@Component({
  selector: 'app-map-filter-form',
  templateUrl: './map-filter-form.component.html',
  styleUrls: ['./map-filter-form.component.scss']
})
export class MapFilterFormComponent implements OnInit {

  @Output()
  applyFilterEvent = new EventEmitter<FilterRequest>();
  @Output()
  resetFilterEvent = new EventEmitter<string>();
  @Output()
  getSubTypesEvent = new EventEmitter<CulturalOfferType>();

  @Input()
  culturalOfferTypes: CulturalOfferType[] = [];
  @Input()
  culturalOfferSubTypes: CulturalOfferSubType[] = [];

  filterForm = new FormGroup({
    typeName:new FormControl(''),
    subTypeName: new FormControl('')
  });

  selectedType: CulturalOfferType;
  selectedSubType: CulturalOfferSubType;
  disabled: boolean = true;

  constructor(
    private culturalOfferSubTypeService: CulturalOfferSubTypeService
    ) { }

  ngOnInit(): void {
  }

  onSubmit():void{
    let filterReq: FilterRequest;

    // if(this.filterForm.value == undefined){
    //   filterReq = {
    //     subTypeName: "",
    //     typeName: ""
    //   }
    // }
    filterReq = {
      subTypeName: this.filterForm.value.subTypeName.name == undefined ? "" : this.filterForm.value.subTypeName.name,
      typeName: this.filterForm.value.typeName.name == undefined ? "" : this.filterForm.value.typeName.name
    }

    this.applyFilterEvent.emit(filterReq);
  }

  onSelectChange(newSelected: any): void{
    if(newSelected.value == ""){
      this.culturalOfferSubTypes = [];
      return;
    }
    this.getSubTypesEvent.emit(this.filterForm.value.typeName.id);
    this.disabled = false;
  }

  resetFilter(): void{
    this.filterForm.reset({typeName: '', subTypeName: ''});
    this.disabled = true;
    this.resetFilterEvent.emit("RESET");
  }

}
