import { SubTypeDialogData } from './../subtype-list/subtype-list.component';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { SubtypeFormComponent } from './subtype-form.component';

describe('SubtypeFormComponent', () => {
  let component: SubtypeFormComponent;
  let fixture: ComponentFixture<SubtypeFormComponent>;
  let dialogRef: MatDialogRef<SubtypeFormComponent>;
  const dialogDataMock = {
    subType: {id: 1, name: 'elem.name', type: {id: 1, name: ''}},
    formType: 'ADD'

  };

  beforeEach(async () => {
    const dialogMock = {
      close: jasmine.createSpy('close')
    };
    await TestBed.configureTestingModule({
      declarations: [ SubtypeFormComponent ],
      providers: [ {provide: MatDialogRef, useValue: dialogMock},
                   {provide: MAT_DIALOG_DATA, useValue: {} } ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubtypeFormComponent);
    component = fixture.componentInstance;
    component.data = dialogDataMock;
    fixture.detectChanges();
    dialogRef = TestBed.inject(MatDialogRef);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should click NO and quit adding', async () => {
    component.onNoClick();
    expect(dialogRef.close).toHaveBeenCalled();
  });

  it('should click SAVE and close dialog', async () => {
    component.form.setValue({name : 'podtip1', id: ''});
    component.onSaveClick();

    expect(component.form.valid).toBeTruthy();
    expect(dialogRef.close).toHaveBeenCalledWith({ id: '', name: 'podtip1', type: { id: 1, name: '' } });
  });


});
