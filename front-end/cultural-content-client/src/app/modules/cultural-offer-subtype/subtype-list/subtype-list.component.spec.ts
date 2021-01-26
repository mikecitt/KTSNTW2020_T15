import { CulturalOfferSubType } from './../../../models/culutral-offer-subType';
import { CulturalOfferSubTypeService } from 'src/app/services/cultural-offer-subtype/cultural-offer-sub-type.service';
import { SubtypeFormComponent } from './../subtype-form/subtype-form.component';
import { SnackBarComponent } from './../../../core/snack-bar/snack-bar.component';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubtypeListComponent, SubTypeDialogData } from './subtype-list.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { of } from 'rxjs';
import { DebugElement } from '@angular/core';
import { subscribeOn } from 'rxjs/operators';

describe('SubtypeListComponent', () => {
  let component: SubtypeListComponent;
  let fixture: ComponentFixture<SubtypeListComponent>;
  let snackbar: SnackBarComponent;
  let dialog: MatDialog;
  let subtypeService: CulturalOfferSubTypeService;

  beforeEach(async () => {
    const snackbarMock = {
      openSnackBar: jasmine.createSpy('openSnackBar')
    }

    const dialogMock = {
      close: jasmine.createSpy('close'),
      open : jasmine.createSpy('open').and.returnValue({
        afterClosed : jasmine.createSpy('afterClosed').and.returnValue( of({}) ), close: null
       })
    }

    let serviceMock = {
      deleteSubType: jasmine.createSpy('deleteSubType')
            .and.returnValue(of()),
      createSubType: jasmine.createSpy('createSubType')
            .and.returnValue(of({ id: 0, name:""})),
      updateSubType: jasmine.createSpy('updateSubType')
            .and.returnValue(of({}))
    }

    await TestBed.configureTestingModule({
      declarations: [ SubtypeListComponent ],
      providers: [{provide: SnackBarComponent, useValue: snackbarMock},
                  {provide: MatDialog, useValue: dialogMock},
                  {provide: CulturalOfferSubTypeService, useValue: serviceMock}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubtypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    subtypeService = TestBed.inject(CulturalOfferSubTypeService);
    dialog = TestBed.inject(MatDialog);
    snackbar = TestBed.inject(SnackBarComponent);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should assign subtype list to dataSource', async()=>{
    component.ngOnInit();
    expect(component.dataSource).toBeDefined();
  });

  it('should assign subtype list to dataSource on changes', async()=>{
    component.ngOnChanges();
    expect(component.dataSource).toBeDefined();
  });

  it('should create dialog', async() =>{
    let data: SubTypeDialogData = {
      subType: {id: 1 , name: '', type: {id: 1, name:''}},
      formType: 'UPDATE'
    };
    component.createDialog(data);
    fixture.detectChanges();
    expect(dialog.open).toHaveBeenCalled();
  });

  it('should create subtype', async()=>{
    spyOn(component.refreshDataEvent,'emit');
    component.createSubType();
    expect(dialog.open).toHaveBeenCalled();
    

    fixture.whenStable().then(()=>{
      fixture.detectChanges();
      let elements: DebugElement[] = fixture.debugElement.query(el => el.name === '.mat-dialog-container').nativeElement;
      expect(elements.length).toBe(1);
    })
    fixture.detectChanges();
    expect(subtypeService.createSubType).toHaveBeenCalled();
    expect(snackbar.openSnackBar).toHaveBeenCalled();
    // expect(component.refreshDataEvent.emit).toHaveBeenCalled();
  });

  it('should delete subtype', async() =>{
    let data: CulturalOfferSubType = {
      id: 1 , name: '', type: {id: 1, name:''}
    };
    component.deleteSubType(data);
    fixture.detectChanges();
    expect(dialog.open).toHaveBeenCalled();

    fixture.whenStable().then(() => {
      fixture.detectChanges();
      let elements: DebugElement[] = fixture.debugElement.query(el => el.name === '.mat-dialog-container').nativeElement;
      expect(elements.length).toBe(1);
    });
    expect(subtypeService.deleteSubType).toHaveBeenCalled();
    // expect(snackbar.openSnackBar).toHaveBeenCalled();
  });

  it('should update subtype', async()=>{
    spyOn(component.refreshDataEvent,'emit');
    let data: CulturalOfferSubType = {
      id: 1 , name: '', type: {id: 1, name:''}
    };
    component.updateSubType(data);
    fixture.detectChanges();
    expect(dialog.open).toHaveBeenCalled();
    expect(subtypeService.updateSubType).toHaveBeenCalled();
    // expect(component.refreshDataEvent.emit).toHaveBeenCalled();

  });




});
