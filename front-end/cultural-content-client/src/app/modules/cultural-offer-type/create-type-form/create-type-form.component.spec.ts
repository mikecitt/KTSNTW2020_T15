import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef } from '@angular/material/dialog';

import { CreateTypeFormComponent } from './create-type-form.component';

describe('CreateTypeFormComponent', () => {
  let component: CreateTypeFormComponent;
  let fixture: ComponentFixture<CreateTypeFormComponent>;
  let dialogRef : MatDialogRef<CreateTypeFormComponent>;

  beforeEach(async () => {
    const dialogMock = {
      close: jasmine.createSpy('close')
    }

    await TestBed.configureTestingModule({
      declarations: [ CreateTypeFormComponent ],
      providers: [ {provide: MatDialogRef, useValue: dialogMock} ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTypeFormComponent);
    component = fixture.componentInstance;
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
    component.name.setValue('tip1');
    component.onSaveClick();

    expect(component.name.valid).toBeTruthy();
    expect(dialogRef.close).toHaveBeenCalledWith('tip1');
  });

  it('should click save with invalid form', async()=>{
    component.name.setValue('');
    component.onSaveClick();

    expect(component.name.invalid).toBeTruthy();
  });

});
