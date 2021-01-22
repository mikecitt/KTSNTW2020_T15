import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';
import { CulturalOfferTypeService, CulturalOfferService, CulturalOfferSubTypeService } from 'src/app/services';

import { MapPageComponent } from './map-page.component';

describe('MapPageComponent', () => {
  let component: MapPageComponent;
  let fixture: ComponentFixture<MapPageComponent>;
  let culturalOfferService: CulturalOfferService;
  let typeService: CulturalOfferTypeService;
  let subTypeService: CulturalOfferSubTypeService;

  beforeEach(async () => {
    const locations: CulturalOfferLocation[] = [
      {
        id: 1,
        name: 'Kulturna ponuda',
        location: {
          address: 'Beograd',
          latitude: 20.0,
          longitude: 20.0
        }
      }
    ];
    const types = [
      {
        id: 1,
        name: 'tip1'
      },
      {
        id:2,
        name: 'tip2'
      }
    ];
    const subTypes = [
      {
        _id: 1,
        name: 'podtip1',
        type_id: 1
      },
      {
        _id: 2,
        name: 'podtip2',
        type_id:1
      }
    ];

    let culturalOfferServiceMock = {
      getLocations: jasmine.createSpy('getLocations')
                    .and.returnValue(of(locations)),
      filterCulturalOffers: jasmine.createSpy('filterCulturalOffers')
                            .and.returnValue(of(locations)),
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
        {provide: CulturalOfferSubTypeService, useValue: subTypeSeviceMock}
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

    expect(component.subTypes[1].id).toBe(2);
    expect(component.subTypes[1].name).toBe('podtip2');
    expect(component.subTypes[1].type.id).toBe(1);

    // open options dialog  valjda je angular istestirao select
    // const matSelect = fixture.debugElement.query(By.css('.mat-select-trigger')).nativeElement;
    // matSelect.click();
    // fixture.detectChanges();
    // fixture.whenStable()
    //       .then( () => {
    //         const matOption: DebugElement[] = fixture.debugElement.query(By.css('.mat-option')).nativeElement;
    //         // matOption.click();
    //         expect(matOption.length).toBeGreaterThan(0);
    //       });
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
