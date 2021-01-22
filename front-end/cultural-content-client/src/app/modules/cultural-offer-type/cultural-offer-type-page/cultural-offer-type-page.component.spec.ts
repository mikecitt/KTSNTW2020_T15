import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Observable, of } from 'rxjs';
import { CulturalOfferTypeService } from 'src/app/services/cultural-offer-type/cultural-offer-type.service';
import { By } from '@angular/platform-browser';

import { CulturalOfferTypePageComponent } from './cultural-offer-type-page.component';
import { DebugElement } from '@angular/core';

describe('CulturalOfferTypePageComponent', () => {
  let component: CulturalOfferTypePageComponent;
  let fixture: ComponentFixture<CulturalOfferTypePageComponent>;
  let typeService: any;
  let dialog: any;

  beforeEach(async () => {
    let typeServiceMock = {
      getAllPaginated : jasmine.createSpy('getAllPaginated')
                         .and.returnValue(of({content: [{},{},{}], totalElements:3 })),
      deleteType: jasmine.createSpy('deleteType')
                         .and.returnValue(of()),
      createType: jasmine.createSpy('createType')
                         .and.returnValue(of({ id: 0, name:""})),
      updateType: jasmine.createSpy('updateType')
                         .and.returnValue(of({}))
    };
    let dialogMock = {
      open : jasmine.createSpy('open').and.returnValue({ afterClosed : of({}), close: null }),
      // afterClosed : jasmine.createSpy('afterClosed').and.returnValue(of({}))
    };


    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferTypePageComponent ],
      providers : [
        {provide: CulturalOfferTypeService, useValue: typeServiceMock},
        {provide: MatDialog, useValue: dialogMock}
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

    // fixture.whenStable()
    //   .then(() => {
    //     fixture.detectChanges();

    //     let elements: DebugElement[] = fixture.debugElement.query(el => el.name === 'mat-dialog-container').nativeElement;
    //     expect(elements.length).toBe(1);
    //   });


  });

});
