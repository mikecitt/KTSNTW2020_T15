import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocomplete } from '@angular/material/autocomplete';
import { MatDialogRef } from '@angular/material/dialog';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { CulturalOfferService, CulturalOfferSubTypeService, CulturalOfferTypeService } from 'src/app/services';

import { NewCulturalOfferDialogComponent } from './new-cultural-offer-dialog.component';

describe('NewCulturalOfferDialogComponent', () => {
  let component: NewCulturalOfferDialogComponent;
  let fixture: ComponentFixture<NewCulturalOfferDialogComponent>;
  const formBuilder: FormBuilder = new FormBuilder();
  let service: CulturalOfferService;
  let typeService: CulturalOfferTypeService;
  let subTypeService: CulturalOfferSubTypeService;
  let dialogRef: MatDialogRef<NewCulturalOfferDialogComponent>;
  let snackBar: SnackBarComponent;

  beforeEach(async () => {
    const dialogMock = {
      close: jasmine.createSpy('close')
    };

    const serviceMock = {
      getAll : jasmine.createSpy('getAll'),
      insert : jasmine.createSpy('insert'),
      getMapboxLocations : jasmine.createSpy('getMapboxLocations')
    };

    const typeServiceMock = {
      getAll : jasmine.createSpy('getAll')
    };

    const subServiceMock = {
      getAll : jasmine.createSpy("getAll")
    };

    const snackBarMock = {
      openSnackBar: jasmine.createSpy('openSnackBar')
    }

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule],
      declarations: [ NewCulturalOfferDialogComponent, MatAutocomplete ],
      providers: [
        {provide: FormBuilder, useValue: formBuilder},
        {provide: MatDialogRef, useValue: dialogMock},
        {provide: CulturalOfferService, useValue: serviceMock},
        {provide: CulturalOfferSubTypeService, useValue: subServiceMock},
        {provide: CulturalOfferTypeService, useValue: typeServiceMock},
        {provide: SnackBarComponent, useValue: snackBarMock},
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCulturalOfferDialogComponent);
    component = fixture.componentInstance;
    component.form = formBuilder.group({
      name: "",
      description:'',
      location: "",
      images: [''],
      subType: [''],
    });
    fixture.detectChanges();
    service = TestBed.inject(CulturalOfferService);
    typeService = TestBed.inject(CulturalOfferTypeService);
    subTypeService = TestBed.inject(CulturalOfferSubTypeService);
    dialogRef = TestBed.inject(MatDialogRef);
    snackBar = TestBed.inject(SnackBarComponent);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


});
