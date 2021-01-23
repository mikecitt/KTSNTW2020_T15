import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Observable, of } from 'rxjs';
import { CulturalOfferTypeService } from 'src/app/services/cultural-offer-type/cultural-offer-type.service';
import { By } from '@angular/platform-browser';

import { CulturalOfferTypePageComponent } from './cultural-offer-type-page.component';
import { DebugElement } from '@angular/core';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { CulturalOfferSubTypeService } from 'src/app/services';

describe('CulturalOfferTypePageComponent', () => {
  let component: CulturalOfferTypePageComponent;
  let fixture: ComponentFixture<CulturalOfferTypePageComponent>;
  let typeService: any;
  let dialog: any;
  let snackBar: SnackBarComponent;
  let subTypeService: CulturalOfferSubTypeService;

  beforeEach(async () => {
    const typesMock = {
      content: [
        {id: 1, name: "tip1"},
        {id:2, name: "tip2"},
        {id: 3, name: "tip3"}
      ],
      totalElements: 3
    };
    const subTypesMock = {
      content: [
        {id: 1, name: 'podtip1'},
        {id: 1, name: 'podtip2'}
      ],
      totalElements: 2
    };

    let typeServiceMock = {
      getAllPaginated : jasmine.createSpy('getAllPaginated')
                         .and.returnValue(of(typesMock)),
      deleteType: jasmine.createSpy('deleteType')
                         .and.returnValue(of()),
      createType: jasmine.createSpy('createType')
                         .and.returnValue(of({ id: 0, name:""})),
      updateType: jasmine.createSpy('updateType')
                         .and.returnValue(of({}))
    };

    let subTypeServiceMock = {
      getAllPaginated: jasmine.createSpy('getAllPaginated').and.returnValue(of(subTypesMock))
    };

    let dialogMock = {
      open : jasmine.createSpy('open').and.returnValue({
         afterClosed : jasmine.createSpy('afterClosed').and.returnValue( of({}) ), close: null
        })
    };

    let snackBarMock = {
      openSnackBar: jasmine.createSpy('openSnackBar')
    };

    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferTypePageComponent ],
      providers : [
        {provide: CulturalOfferTypeService, useValue: typeServiceMock},
        {provide: MatDialog, useValue: dialogMock},
        {provide: SnackBarComponent, useValue: snackBarMock},
        {provide: CulturalOfferSubTypeService, useValue: subTypeServiceMock}
      ]
    });
    //.compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferTypePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    typeService = TestBed.inject(CulturalOfferTypeService);
    dialog = TestBed.inject(MatDialog);
    snackBar = TestBed.inject(SnackBarComponent);
    subTypeService = TestBed.inject(CulturalOfferSubTypeService);
  });

  it('should fetch the type list on init', async () => {
    component.ngOnInit();

    expect(typeService.getAllPaginated).toHaveBeenCalled();

    fixture.whenStable()
           .then( () => {
              expect(component.culturalOfferTypes.length).toBe(3);
              fixture.detectChanges();
              let tr = fixture.debugElement.query(el => el.name === 'tr').nativeElement;  // it works
              expect(tr.length).toBe(4);
           });
  });

  it('should go to next page in table', async () => {
    component.getNextType();

    expect(component.curentPageType).toBe(1);
    expect(typeService.getAllPaginated).toHaveBeenCalled();

    fixture.whenStable()
      .then( () => {
        expect(component.culturalOfferTypes.length).toBe(3);
        fixture.detectChanges();
        let elements: DebugElement[] = fixture.debugElement.query(el => el.name === 'tr').nativeElement;  // it works
        // let elements: DebugElement[] = fixture.debugElement.queryAll(By.css('tr'));
        expect(elements.length).toBe(4);
      })
  });

  it('should go to previous page in table', async () => {
    component.curentPageType = 1;
    component.getPreviousType();

    expect(component.curentPageType).toBe(0);
    expect(typeService.getAllPaginated).toHaveBeenCalled();

    fixture.whenStable()
      .then( () => {
        expect(component.culturalOfferTypes.length).toBe(3);
        fixture.detectChanges();
        let elements: DebugElement[] = fixture.debugElement.query(el => el.name === 'tr').nativeElement;  // it works
        //  let elements: DebugElement[] = fixture.debugElement.queryAll(By.css('tr'));
        expect(elements.length).toBe(4);
      })
  });

  it('should call delete type', async () => {
    component.deleteType(1);
    fixture.detectChanges();
    expect(dialog.open).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        fixture.detectChanges();
        let elements: DebugElement[] = fixture.debugElement.query(el => el.name === '.mat-dialog-container').nativeElement;
        expect(elements.length).toBe(1);
      });
    expect(typeService.deleteType).toHaveBeenCalled();
    expect(typeService.getAllPaginated).toHaveBeenCalled();
  });

  it('should open create dialog', async ()=>{
    component.openCreateDialog("Whatever");
    fixture.detectChanges();
    expect(dialog.open).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        fixture.detectChanges();
        let elements: DebugElement[] = fixture.debugElement.query(el => el.name === '.mat-dialog-container').nativeElement;
        expect(elements.length).toBe(1);
      });
    expect(typeService.createType).toHaveBeenCalled();
    expect(typeService.getAllPaginated).toHaveBeenCalled();

  });

});
