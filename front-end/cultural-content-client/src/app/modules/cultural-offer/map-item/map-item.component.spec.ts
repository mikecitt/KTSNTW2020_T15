import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DebugElement } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick, waitForAsync } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import * as Mapboxgl from 'mapbox-gl';
import { of } from 'rxjs';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';
import { Geocoder } from 'src/app/models/geocoder';
import { CulturalOfferService } from 'src/app/services';
import { DynamicComponentService } from 'src/app/services/dynamic-component.service';
import { environment } from 'src/environments/environment';
import { MapItemComponent } from './map-item.component';

describe('MapItemComponent', () => {
  let component: MapItemComponent;
  let fixture: ComponentFixture<MapItemComponent>;
  let offerService: CulturalOfferService;
  let mapa: Mapboxgl.Map;
  let dynamicsService: DynamicComponentService;

  const culturalOffersMock: CulturalOfferResponse[] = [
    {
      id: 1,
      name: 'Kulturna ponuda',
      description: '',
      location: {
        address: 'Beograd',
        latitude: 20.0,
        longitude: 20.0
      },
      subType: {
        id: 1,
        name: '',
        type: {
          id: 1,
          name: ''
        }
      },
      images: []
    }
  ];

  const geocoderMock: Geocoder = {
    query: [],
    features: [
      {
        text: '',
        place_name: 'Beograd',
        bbox: [20, 20, 30],
        center: [20, 20]
      }
    ]
  };

  beforeEach(async () => {
    const div = document.createElement('div');
    const dynamicsServiceMock = {
      injectComponent: jasmine.createSpy('injectComponent')
                              .and.returnValue(div)
    };

    const mapaMock = {
      setCenter: jasmine.createSpy('setCenter'),
      setZoom: jasmine.createSpy('setZoom')
    };

    const geocoderMock: Geocoder = {
      query: [],
      features: [{
        text: 'fafa',
        place_name: 'dadas',
        center: [20, 20],
        bbox: [12, 332]
      }, {
        text: 'fafa',
        place_name: 'dadas',
        center: [20, 20],
        bbox: [12, 332]
      }]
    };

    const offerServiceMock = {
      getMapboxLocations : jasmine.createSpy('getMapboxLocations').and.returnValue(of( geocoderMock ))
    };

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ MapItemComponent ],
      providers: [
        { provide : Mapboxgl.Map, useValue: mapaMock},
        { provide: DynamicComponentService, useValue: dynamicsServiceMock},
        { provide: CulturalOfferService, useValue: offerServiceMock}
      ]
    }).compileComponents();

  });

  beforeEach(() => {

    fixture = TestBed.createComponent(MapItemComponent);
    component = fixture.componentInstance;
    component.culturalOffers = culturalOffersMock;
    fixture.detectChanges();
    offerService = TestBed.inject(CulturalOfferService);
    mapa = TestBed.inject(Mapboxgl.Map);
    dynamicsService = TestBed.inject(DynamicComponentService);
    fixture.detectChanges();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('onInit', async () => {
    component.initializeMap();

    expect(component.mapInitialized).toBeTruthy();
    expect(component.markers.length).toBe(1);

    fixture.whenStable()
    .then( () => {
       fixture.detectChanges();
       try {
        const tr = fixture.debugElement.query(el => el.name === 'tr').nativeElement;  // it works
        const matOption: DebugElement[] = fixture.debugElement.query(By.css('#map-mapbox')).nativeElement;
        expect(tr.length).toBe(1);
       } catch (error) {

       }
    });
  });

  it('should update markers when input is changed', () => {
    component.culturalOffers = [];
    fixture.detectChanges();
    component.culturalOffers = culturalOffersMock;
    fixture.detectChanges();

    expect(component.markers.length).toBe(component.culturalOffers.length);
  });

  // it('should update focus when location is changed', () => {
  //   component.location = "London";
  //   fixture.detectChanges();
  //   // component.location = "London";
  //   // fixture.detectChanges();

  //   // expect(mapa.setCenter).toHaveBeenCalled();
  //   expect(mapa.setZoom).toHaveBeenCalledWith(13.5);
  // });

});
