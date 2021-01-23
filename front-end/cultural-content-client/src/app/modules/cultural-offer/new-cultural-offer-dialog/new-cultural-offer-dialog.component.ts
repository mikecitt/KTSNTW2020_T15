import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  NgForm,
  Validators,
} from '@angular/forms';
import { CulturalOfferType } from 'src/app/models/cultural-offer-type';
import { CulturalOfferSubType } from 'src/app/models/culutral-offer-subType';
import { Geocoder, GeoFeature } from 'src/app/models/geocoder';
import {
  CulturalOfferSubTypeService,
  CulturalOfferTypeService,
} from 'src/app/services';
import { CulturalOfferService } from 'src/app/services/cultural-offer/cultural-offer.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-new-cultural-offer-dialog',
  templateUrl: './new-cultural-offer-dialog.component.html',
  styleUrls: ['./new-cultural-offer-dialog.component.scss'],
})
export class NewCulturalOfferDialogComponent implements OnInit {
  fileMultiple = true;
  fileAccept = 'image/*';

  form: FormGroup;

  selectedLocation: GeoFeature | null = null;
  locations: GeoFeature[] = [];

  allTypes: CulturalOfferType[] = [];
  subTypes: CulturalOfferSubType[] = [];

  @ViewChild('insertForm')
  private insertForm!: NgForm;

  constructor(
    private formBuilder: FormBuilder,
    private service: CulturalOfferService,
    private typeService: CulturalOfferTypeService,
    private subTypeService: CulturalOfferSubTypeService
  ) {
    this.form = this.formBuilder.group({
      name: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(50),
        ]),
      ],
      description: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(256),
        ]),
      ],
      location: [
        '',
        Validators.compose([Validators.required, this.locationRequired()]),
      ],
      images: ['', Validators.compose([this.imageRequired()])],
      subType: ['', Validators.compose([Validators.required])],
    });
  }

  locationRequired() {
    return (control: AbstractControl): { [key: string]: any } | null => {
      return typeof control.value !== 'object'
        ? { locationRequired: true }
        : null;
    };
  }

  imageRequired() {
    return (control: AbstractControl): { [key: string]: any } | null => {
      return control.value.length < 1 ? { imageRequired: true } : null;
    };
  }

  locationChanged(value: string | GeoFeature) {
    if (typeof value == 'string') {
      this.selectedLocation = null;
      this.service.getMapboxLocations(value).subscribe((geo) => {
        this.locations = geo.features;
      });
    }
  }

  displayFn(feature: GeoFeature): string {
    this.selectedLocation = feature;
    return feature.place_name;
  }

  insert() {
    let formObj = this.form.getRawValue();
    console.log(formObj);
  }

  typeChanged(selection: any) {
    this.form.get('subType')?.reset();
    this.subTypeService.getAll(selection.value.id).subscribe((data) => {
      this.subTypes = data;
    });
  }

  ngOnInit(): void {
    this.typeService.getAll().subscribe((data) => {
      this.allTypes = data;
    });
  }
}
