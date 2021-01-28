import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { NewsFormComponent } from './news-form.component';

describe('NewsFormComponent', () => {
  let component: NewsFormComponent;
  let fixture: ComponentFixture<NewsFormComponent>;
  let dialogRef: MatDialogRef<NewsFormComponent>;
  let formBuilder: FormBuilder;
  const dialogDataMock = {
    type: 'add',
    news: {
      id: 1,
      text: 'string',
      date: new Date(),
      images: ['']

    }
  };

  const dialogMock = {
    close : jasmine.createSpy('close')
  };

  const formBuilderMock = {
    group: jasmine.createSpy('group')
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewsFormComponent ],
      providers: [{provide: MatDialogRef, useValue: dialogMock},
                  {provide: MAT_DIALOG_DATA, useValue: {}},
                  {provide: FormBuilder, useValue: formBuilderMock}, ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsFormComponent);
    component = fixture.componentInstance;

    component.data = dialogDataMock;
    fixture.detectChanges();

    formBuilder = TestBed.inject(FormBuilder);
    dialogRef = TestBed.inject(MatDialogRef);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call update', async () => {
    component.update();
    expect(dialogRef.close).toHaveBeenCalledWith({operation: 'update'});
  });

  it('should call add', async () => {
    component.add();
    expect(dialogRef.close).toHaveBeenCalledWith({operation: 'add'});
  });

  it('should call cancel add', async () => {
    component.cancelAdd();
    expect(dialogRef.close).toHaveBeenCalledWith({operation: 'cancelAdd'});
  });

  it('should call cancel update', async () => {
    component.cancelUpdate();
    expect(dialogRef.close).toHaveBeenCalledWith({operation: 'cancelUpdate'});
  });
});
