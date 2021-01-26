import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { CulturalOfferType } from 'src/app/models/cultural-offer-type';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';
import { CulturalOfferSubType } from 'src/app/models/culutral-offer-subType';
import { CulturalOfferTypeService, CulturalOfferService, CulturalOfferSubTypeService } from 'src/app/services';

import { MapPageComponent } from './map-page.component';

describe('MapPageComponent', () => {
  let component: MapPageComponent;
  let fixture: ComponentFixture<MapPageComponent>;
  let culturalOfferService: CulturalOfferService;
  let typeService: CulturalOfferTypeService;
  let subTypeService: CulturalOfferSubTypeService;
  let dialog: MatDialog;

  beforeEach(async () => {
    const dialogMock = {
      close: jasmine.createSpy('close'),
      open : jasmine.createSpy('open').and.returnValue({
        afterClosed : jasmine.createSpy('afterClosed').and.returnValue( of({}) ), close: null
       })
    }

    const culturalOffers: CulturalOfferResponse[] = [
      {
        id: 1,
        name: 'Kulturna ponuda',
        description: '',
        location: {
          address: 'Beograd',
          latitude: 20.0,
          longitude: 20.0
        },
        subType:{
          id: 1,
          name: '',
          type:{
            id: 1,
            name: ''
          }
        },
        images: []
      }
    ];
    const types: CulturalOfferType[] = [
      {
        id: 1,
        name: 'tip1'
      },
      {
        id:2,
        name: 'tip2'
      }
    ];
    const subTypes: CulturalOfferSubType[] = [
      {
        id: 1,
        name: 'podtip1',
        type:{
          id: 1,
          name: 'tip1'
        }
      },
      {
        id: 2,
        name: 'podtip2',
        type: {
          id: 1,
          name: 'tip1'
        }
      }
    ];

    let culturalOfferServiceMock = {
      getLocations: jasmine.createSpy('getLocations')
                    .and.returnValue(of(culturalOffers)),
      filterCulturalOffers: jasmine.createSpy('filterCulturalOffers')
                            .and.returnValue(of(culturalOffers)),
    };
    let typeServiceMock = {
      getAll: jasmine.createSpy('getAll')
              .and.returnValue(of(types))
    };
    let subTypeSeviceMock = {
      getAll: jasmine.createSpy('getAll')
              .and.returnValue(of(subTypes))
    };

    await TestBed.configureTestingModule({
      declarations: [ MapPageComponent ],
      providers: [
        {provide: CulturalOfferService, useValue: culturalOfferServiceMock},
        {provide: CulturalOfferTypeService, useValue: typeServiceMock},
        {provide: CulturalOfferSubTypeService, useValue: subTypeSeviceMock},
        {provide: MatDialog, useValue: dialogMock},
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    culturalOfferService = TestBed.inject(CulturalOfferService);
    typeService = TestBed.inject(CulturalOfferTypeService);
    subTypeService = TestBed.inject(CulturalOfferSubTypeService);
    dialog = TestBed.inject(MatDialog);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch the type list and offer list on init', async () => {
    component.ngOnInit();

    expect(culturalOfferService.getLocations).toHaveBeenCalled();
    expect(typeService.getAll).toHaveBeenCalled();

    expect(component.culturalOfferLocations.length).toBe(1);
    const mapElem: DebugElement[] = fixture.debugElement.queryAll(By.css('app-map-item'));
    expect(mapElem.length).toBe(1);

    expect(component.types.length).toBe(2);

    expect(component.culturalOfferLocations[0].id).toBe(1);
    expect(component.culturalOfferLocations[0].name).toBe('Kulturna ponuda');
    expect(component.culturalOfferLocations[0].location.address).toBe('Beograd');
    expect(component.culturalOfferLocations[0].location.latitude).toBe(20.0);
    expect(component.culturalOfferLocations[0].location.longitude).toBe(20.0);

    expect(component.types[0].id).toBe(1);
    expect(component.types[0].name).toBe('tip1');
    expect(component.types[1].id).toBe(2);
    expect(component.types[1].name).toBe('tip2');

  });

  it('should load subtypes on loadSubType', async ()=>{
    component.loadSubTypes(1);

    expect(subTypeService.getAll).toHaveBeenCalledWith(1);
    expect(component.subTypes.length).toBe(2);

    expect(component.subTypes[0].id).toBe(1);
    expect(component.subTypes[0].name).toBe('podtip1');
    expect(component.subTypes[0].type.id).toBe(1);
    expect(component.subTypes[0].type.name).toBe('tip1');

    expect(component.subTypes[1].id).toBe(2);
    expect(component.subTypes[1].name).toBe('podtip2');
    expect(component.subTypes[1].type.id).toBe(1);
    expect(component.subTypes[1].type.name).toBe('tip1');
  });

  it('should filter cultural offer on applyFilter', async()=>{
    let filterReq = {
      request:{
        subTypeName: 'podtip',
        typeName: 'tip1'
      },
      location: 'lokacija'
    };
    component.applyFilter(filterReq);

    expect(component.searchedLocation).toBe(filterReq.location);
    expect(culturalOfferService.filterCulturalOffers).toHaveBeenCalledWith(filterReq.request);
    expect(component.culturalOfferLocations.length).toBe(1);

    expect(component.culturalOfferLocations[0].id).toBe(1);
    expect(component.culturalOfferLocations[0].name).toBe('Kulturna ponuda');
    expect(component.culturalOfferLocations[0].location.address).toBe('Beograd');
    expect(component.culturalOfferLocations[0].location.latitude).toBe(20.0);
    expect(component.culturalOfferLocations[0].location.longitude).toBe(20.0);
  });

  it('should reset filter form', async()=>{
    component.resetFilter("RESET");

    expect(culturalOfferService.getLocations).toHaveBeenCalledWith();
    expect(component.culturalOfferLocations.length).toBe(1);

    expect(component.culturalOfferLocations[0].id).toBe(1);
    expect(component.culturalOfferLocations[0].name).toBe('Kulturna ponuda');
    expect(component.culturalOfferLocations[0].location.address).toBe('Beograd');
    expect(component.culturalOfferLocations[0].location.latitude).toBe(20.0);
    expect(component.culturalOfferLocations[0].location.longitude).toBe(20.0);
  });

});
