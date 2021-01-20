import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CulturalOfferType } from 'src/app/models/cultural-offer-type';

import { UpdateTypeFormComponent } from './update-type-form.component';

describe('UpdateTypeFormComponent', () => {
  let component: UpdateTypeFormComponent;
  let fixture: ComponentFixture<UpdateTypeFormComponent>;
  let dialogRef: MatDialogRef<UpdateTypeFormComponent>

  let matDialogDataMock: CulturalOfferType = {
    id: 1,
    name: "tip1"
  };

  beforeEach(async () => {
    const dialogMock = {
      close: jasmine.createSpy('close')
    }

    await TestBed.configureTestingModule({
      declarations: [ UpdateTypeFormComponent ],
      providers: [
        {provide: MatDialogRef, useValue: dialogMock},
        {provide: MAT_DIALOG_DATA, useValue: {}}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateTypeFormComponent);
    component = fixture.componentInstance;

    component.data = matDialogDataMock;

    fixture.detectChanges();
    dialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form onInit', async () => {
    component.ngOnInit();
    expect(component.updateForm.value).toEqual(matDialogDataMock);

  });

  it('should click NO and quit updating', async () => {
    component.onNoClick();
    expect(dialogRef.close).toHaveBeenCalled();
  });

  it('should click SAVE and close dialog', async () => {
    component.onSaveClick();

    expect(component.updateForm.valid).toBeTruthy();
    expect(dialogRef.close).toHaveBeenCalledWith(matDialogDataMock);
  });

});
