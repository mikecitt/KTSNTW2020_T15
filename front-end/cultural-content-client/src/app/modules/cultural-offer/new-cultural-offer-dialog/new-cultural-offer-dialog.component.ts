import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  NgForm,
  Validators,
} from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
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
  fileAccept = '.png, .jpeg, .jpg';

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
    private subTypeService: CulturalOfferSubTypeService,
    public dialogRef: MatDialogRef<NewCulturalOfferDialogComponent>,
    private snackBar: SnackBarComponent
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
      images: [[]],
      subType: ['', Validators.compose([Validators.required])],
    });
  }

  toBase64 = (file: File) =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });

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

  async insert() {
    let formObj = this.form.getRawValue();

    for (let i = 0; i < formObj.images.length; i++) {
      if (formObj.images[i] instanceof File) {
        formObj.images[i] = await this.toBase64(formObj.images[i]);
      }
    }

    let subTypeId = formObj.subType.id;
    delete formObj['subType'];

    formObj.location = {
      address: formObj.location.place_name,
      longitude: formObj.location.center[0],
      latitude: formObj.location.center[1],
    };

    this.service.insert(formObj, subTypeId).subscribe(
      (response: CulturalOfferResponse) => {
        this.dialogRef.close(true);
        this.snackBar.openSnackBar("Created successully", "", "green-snackbar");
      },
      (error: any) => {}
    );
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
