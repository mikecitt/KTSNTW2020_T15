import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ReviewsFormComponent } from './reviews-form.component';

describe('ReviewsFormComponent', () => {
  let component: ReviewsFormComponent;
  let fixture: ComponentFixture<ReviewsFormComponent>;
  let dialogRef: MatDialogRef<ReviewsFormComponent>;
  const formBuilder: FormBuilder = new FormBuilder();
  let dialogDataMock = {
    type: "add",
    review: {
      id: 1,
      rating: 0,
      comment: "string string",
      authorUsername: "string",
      images: ['']
    
    }
  }

  const dialogMock = {
    close : jasmine.createSpy('close')
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewsFormComponent ],
      providers: [{provide: MatDialogRef, useValue: dialogMock},
                  {provide: MAT_DIALOG_DATA, useValue: {}},
                  {provide: FormBuilder, useValue: formBuilder},]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewsFormComponent);
    component = fixture.componentInstance;

    component.form = formBuilder.group({
      rating: null,
      comment: null,
      images: []
    });

    component.data = dialogDataMock;
    fixture.detectChanges();

    dialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call add', async () =>{
    component.add();
    expect(dialogRef.close).toHaveBeenCalledWith({operation: 'add'});
  });

  it('should call cancel add', async () =>{
    component.cancelAdd();
    expect(dialogRef.close).toHaveBeenCalledWith({operation: 'cancel'});
  });
});
