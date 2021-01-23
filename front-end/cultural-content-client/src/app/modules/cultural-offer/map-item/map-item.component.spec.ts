import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import * as Mapboxgl from 'mapbox-gl';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';
import { Geocoder } from 'src/app/models/geocoder';
import { environment } from 'src/environments/environment';
import { MapItemComponent } from './map-item.component';

describe('MapItemComponent', () => {
  let component: MapItemComponent;
  let fixture: ComponentFixture<MapItemComponent>;
  let httpMock: HttpTestingController;
  let mapa: Mapboxgl.Map;

  const culturalOffersMock: CulturalOfferLocation[] = [
    {
      id: 1,
      name: 'Exit',
      location: {
        address: 'Novi Sad',
        longitude: 20.0,
        latitude: 20.0
      }
    }
  ];

  const geocoderMock : Geocoder = {
    query: [],
    features: [
      {
        text: '',
        place_name: 'Beograd',
        bbox: [20,20,30],
        center: [20,20]
      }
    ]
  };

  beforeEach(async () => {
    const mapaMock = {
      setCenter: jasmine.createSpy('setCenter'),
      setZoom: jasmine.createSpy('setZoom')
    }

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ MapItemComponent ],
      providers: [
        { provide : Mapboxgl.Map, useValue: mapaMock},
      ]
    }).compileComponents();

  });

  beforeEach(() => {

    fixture = TestBed.createComponent(MapItemComponent);
    component = fixture.componentInstance;
    component.culturalOffers = culturalOffersMock;
    fixture.detectChanges();
    httpMock = TestBed.inject(HttpTestingController);
    mapa = TestBed.inject(Mapboxgl.Map);
    fixture.detectChanges();

  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('onInit', async()=>{
    component.initializeMap();

    expect(component.mapInitialized).toBeTruthy();
    expect(component.markers.length).toBe(1);

    fixture.whenStable()
    .then( () => {
       fixture.detectChanges();
       let tr = fixture.debugElement.query(el => el.name === 'tr').nativeElement;  // it works
       const matOption: DebugElement[] = fixture.debugElement.query(By.css('#map-mapbox')).nativeElement;
       expect(tr.length).toBe(1);
    });
  });

  it('should update markers when input is changed', () => {
    component.culturalOffers = [];
    fixture.detectChanges();
    component.culturalOffers = culturalOffersMock;
    fixture.detectChanges();

    expect(component.markers.length).toBe(component.culturalOffers.length);
  });

  it('should update focus when location is changed', () => {
    const path = `https://api.mapbox.com/geocoding/v5/mapbox.places/
                  London.json?access_token=${environment.mapboxKey}`;
    component.location = "";
    fixture.detectChanges();
    component.location = "Beograd";
    fixture.detectChanges();

    const req = httpMock.expectOne(path);
    expect(req.request.method).toBe('GET');
    req.flush(geocoderMock);

    tick();

    expect(mapa.setCenter).toHaveBeenCalled();
    expect(mapa.setZoom).toHaveBeenCalled();
  });

});
