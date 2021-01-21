import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import * as Mapboxgl from 'mapbox-gl';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';

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

  beforeEach(async () => {
    const mapaMock = {
      setCenter: jasmine.createSpy('setCenter'),
      setZoom: jasmine.createSpy('setZoom')
    }

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ MapItemComponent ],
      providers: [
        { provider: Mapboxgl.Map, useValue: mapaMock},
      ]
    }).compileComponents();

  });

  beforeEach(() => {

    fixture = TestBed.createComponent(MapItemComponent);
    component = fixture.componentInstance;
    component.culturalOffers = culturalOffersMock;
    component.markers = [];
    fixture.detectChanges();
    httpMock = TestBed.inject(HttpTestingController);
    mapa = TestBed.inject(Mapboxgl.Map);
    fixture.detectChanges();

  });

  // afterEach(() => {
  //   httpMock.verify();
  // });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('onInit', async()=>{
    component.initializeMap();

    expect(component.mapInitialized).toBeTruthy();

    fixture.whenStable()
    .then( () => {
       fixture.detectChanges();
       let tr = fixture.debugElement.query(el => el.name === 'tr').nativeElement;  // it works
       const matOption: DebugElement[] = fixture.debugElement.query(By.css('#map-mapbox')).nativeElement;

       expect(tr.length).toBe(1);
    });

  });
});
