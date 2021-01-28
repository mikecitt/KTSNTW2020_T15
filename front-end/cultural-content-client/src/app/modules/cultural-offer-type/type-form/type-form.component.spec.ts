import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TypeDialogData } from 'src/app/models/cultural-offer-type';

import { TypeFormComponent } from './type-form.component';

describe('TypeFormComponent', () => {
  let component: TypeFormComponent;
  let fixture: ComponentFixture<TypeFormComponent>;
  let dialogRef: MatDialogRef<TypeFormComponent>;

  const matDialogDataCreate: TypeDialogData = {
    formType: 'CREATE',
    type: {
      id: 0,
      name: ''
    }
  };

  const matDialogDataUpdate: TypeDialogData = {
    formType: 'UPDATE',
    type: {
      id: 1,
      name: 'tip1'
    }
  };


  beforeEach(async () => {
    const dialogMock = {
      close: jasmine.createSpy('close')
    };

    await TestBed.configureTestingModule({
      declarations: [ TypeFormComponent ],
      providers: [
        {provide: MatDialogRef, useValue: dialogMock},
        {provide: MAT_DIALOG_DATA, useValue: {}}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeFormComponent);
    component = fixture.componentInstance;
    component.data = matDialogDataUpdate;
    fixture.detectChanges();
    dialogRef = TestBed.inject(MatDialogRef);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form onInit (create type)', async () => {
    component.data = matDialogDataCreate;
    component.ngOnInit();
    expect(component.form.value).toEqual(matDialogDataCreate.type);
  });

  it('should initialize form onInit (update type)', async () => {
    component.data = matDialogDataUpdate;
    component.ngOnInit();
    expect(component.form.value).toEqual(matDialogDataUpdate.type);
  });

  it('should click NO and quit updating', async () => {
    component.onNoClick();
    expect(dialogRef.close).toHaveBeenCalled();
  });

  it('should click SAVE and close dialog ', async () => {
    component.form.setValue({ id: 0, name: 'tip1'});
    component.onSaveClick();

    expect(component.form.valid).toBeTruthy();
    expect(dialogRef.close).toHaveBeenCalledWith({ id: 0, name: 'tip1'});
  });


});
