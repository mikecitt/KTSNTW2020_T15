import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  NgForm,
  Validators,
} from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';
import { GeoFeature } from 'src/app/models/geocoder';
import { CulturalOfferService } from 'src/app/services';

@Component({
  selector: 'app-edit-cultural-offer-dialog',
  templateUrl: './edit-cultural-offer-dialog.component.html',
  styleUrls: ['./edit-cultural-offer-dialog.component.scss'],
})
export class EditCulturalOfferDialogComponent implements OnInit {
  fileMultiple = true;
  fileAccept = '.png, .jpeg, .jpg';

  form: FormGroup;

  private cultureOfferId: number;

  selectedLocation: GeoFeature | null = null;
  locations: GeoFeature[] = [];

  @ViewChild('editForm')
  private editForm!: NgForm;

  constructor(
    private formBuilder: FormBuilder,
    private service: CulturalOfferService,
    public dialogRef: MatDialogRef<EditCulturalOfferDialogComponent>,
    private snackBar: SnackBarComponent,
    @Inject(MAT_DIALOG_DATA) public data: CulturalOfferResponse
  ) {
    this.cultureOfferId = data.id;
    var location = {
      place_name: data.location.address,
      center: [data.location.longitude, data.location.latitude],
    };
    this.form = this.formBuilder.group({
      name: [
        data.name,
        Validators.compose([
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(50),
        ]),
      ],
      description: [
        data.description,
        Validators.compose([
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(256),
        ]),
      ],
      location: [
        location,
        Validators.compose([Validators.required, this.locationRequired()]),
      ],
      images: [[], Validators.compose([])],
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

  async update() {
    let formObj = this.form.getRawValue();

    for (let i = 0; i < formObj.images.length; i++) {
      if (formObj.images[i] instanceof File) {
        formObj.images[i] = await this.toBase64(formObj.images[i]);
      }
    }

    formObj.location = {
      address: formObj.location.place_name,
      longitude: formObj.location.center[0],
      latitude: formObj.location.center[1],
    };

    console.log(formObj);
    this.service.update(formObj, this.cultureOfferId).subscribe(
      (response: CulturalOfferResponse) => {
        this.dialogRef.close(response);
        this.snackBar.openSnackBar("Updated successully", "", "green-snackbar");
      },
      (error: any) => {}
    );
  }

  ngOnInit(): void {}
}
