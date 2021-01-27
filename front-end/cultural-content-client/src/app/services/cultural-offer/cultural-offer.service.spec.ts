import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Component } from '@angular/core';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { CulturalOfferRequest } from 'src/app/models/cultural-offer-request';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { FilterRequest } from 'src/app/models/filter-request';
import { Geocoder } from 'src/app/models/geocoder';
import { environment } from 'src/environments/environment';

import { CulturalOfferService } from './cultural-offer.service';

describe('CulturalOfferService', () => {
  let service: CulturalOfferService;
  let httpMock: HttpTestingController;

  const path: string = 'http://localhost:8080/api/cultural-offer';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CulturalOfferService]
    });
    service = TestBed.inject(CulturalOfferService);
    httpMock = TestBed.inject(HttpTestingController);

  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getLocations() should return some cultural offers', fakeAsync(() => {
    let offers : CulturalOfferResponse[] = [];
    let offersMock : CulturalOfferResponse[] = [{
      id: 1,
      name: "kulturna ponuda",
      description: "neki opis",
      location: {
        address: "Novi Sad",
        longitude: 20,
        latitude: 20
      },
      subType: {
        id: 1,
        name: "podkategorija",
        type: {
          id: 1,
          name: "kategorija"
        }
      },
      images: []
    }];
    service.getLocations().subscribe(data => {offers = data});

    const req = httpMock.expectOne(path + '/all');
    expect(req.request.method).toBe('GET');
    req.flush(offersMock);

    tick();

    expect(offers.length).toBe(1);
    expect(offers[0].id).toBe(1);
    expect(offers[0].description).toBe("neki opis");
    expect(offers[0].name).toBe("kulturna ponuda");
    expect(offers[0].location.address).toBe("Novi Sad");
    expect(offers[0].location.latitude).toBe(20);
    expect(offers[0].location.longitude).toBe(20);
    expect(offers[0].subType.id).toBe(1);
    expect(offers[0].subType.name).toBe("podkategorija");
    expect(offers[0].subType.type.id).toBe(1);
    expect(offers[0].subType.type.name).toBe("kategorija");
  }));

  it('insert() should create cultural offer', fakeAsync(() => {
    let newOffer: CulturalOfferRequest = {
      name: "kulturna ponuda",
      description: "neki opis",
      location: {
        address: "Novi Sad",
        longitude: 20,
        latitude: 20
      },
      images: ["slika.jpg"]
    };

    let mockOffer: CulturalOfferResponse = {
      id: 1,
      name: "kulturna ponuda",
      description: "neki opis",
      location: {
        address: "Novi Sad",
        longitude: 20,
        latitude: 20
      },
      subType: {
        id: 1,
        name: "podkategorija",
        type: {
          id: 1,
          name: "kategorija"
        }
      },
      images: ["slika.jpg"]
    };

    let offerResponse: CulturalOfferResponse = {
      id: 0,
      name: "",
      description: "",
      location: {
        address: "",
        longitude: 0,
        latitude: 0
      },
      subType: {
        id: 0,
        name: "",
        type: {
          id: 0,
          name: ""
        }
      },
      images: []
    };

    service.insert(newOffer, 1).subscribe(res => offerResponse = res);

    const req = httpMock.expectOne(path + '?subTypeId=1');
    expect(req.request.method).toBe('POST');
    req.flush(mockOffer);

    tick();

    expect(offerResponse.id).toBe(1);
    expect(offerResponse.name).toBe("kulturna ponuda");
    expect(offerResponse.description).toBe("neki opis");
    expect(offerResponse.location.address).toBe("Novi Sad");
    expect(offerResponse.location.latitude).toBe(20);
    expect(offerResponse.location.longitude).toBe(20);
    expect(offerResponse.images[0]).toBe("slika.jpg");
    expect(offerResponse.subType.id).toBe(1);
    expect(offerResponse.subType.name).toBe("podkategorija");
    expect(offerResponse.subType.type.id).toBe(1);
    expect(offerResponse.subType.type.name).toBe("kategorija");
  }));

  it('update() should update cultural offer', fakeAsync(() => {
    let newOffer: CulturalOfferRequest = {
      name: "kulturna ponuda",
      description: "neki opis",
      location: {
        address: "Novi Sad",
        longitude: 20,
        latitude: 20
      },
      images: ["slika.jpg"]
    };

    let mockOffer: CulturalOfferResponse = {
      id: 1,
      name: "kulturna ponuda",
      description: "neki opis",
      location: {
        address: "Novi Sad",
        longitude: 20,
        latitude: 20
      },
      subType: {
        id: 1,
        name: "podkategorija",
        type: {
          id: 1,
          name: "kategorija"
        }
      },
      images: ["slika.jpg"]
    };

    let updatedResponse: CulturalOfferResponse = {
      id: 0,
      name: "",
      description: "",
      location: {
        address: "",
        longitude: 0,
        latitude: 0
      },
      subType: {
        id: 0,
        name: "",
        type: {
          id: 0,
          name: ""
        }
      },
      images: []
    };

    service.update(newOffer, 1).subscribe(res => updatedResponse = res);

    const req = httpMock.expectOne(path + '/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockOffer);

    tick();

    expect(updatedResponse.id).toBe(1);
    expect(updatedResponse.name).toBe("kulturna ponuda");
    expect(updatedResponse.description).toBe("neki opis");
    expect(updatedResponse.location.address).toBe("Novi Sad");
    expect(updatedResponse.location.latitude).toBe(20);
    expect(updatedResponse.location.longitude).toBe(20);
    expect(updatedResponse.images[0]).toBe("slika.jpg");
    expect(updatedResponse.subType.id).toBe(1);
    expect(updatedResponse.subType.name).toBe("podkategorija");
    expect(updatedResponse.subType.type.id).toBe(1);
    expect(updatedResponse.subType.type.name).toBe("kategorija");
  }));

  it('delete() should delete cultural offer', fakeAsync(() => {
    service.delete(1).subscribe( res => {});

    const req = httpMock.expectOne(path + '/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));

  it('filterCulturalOffer should return filtered offers', fakeAsync(() => {
    let filterRequest: FilterRequest = {
      subTypeName: "Koncert",
      typeName: "Manifestacija"
    };
    let response : CulturalOfferResponse[] = [];

    let offersMock : CulturalOfferResponse[] = [{
      id: 1,
      name: "kulturna ponuda",
      description: "neki opis",
      location: {
        address: "Novi Sad",
        longitude: 20,
        latitude: 20
      },
      subType: {
        id: 1,
        name: "Koncert",
        type: {
          id: 1,
          name: "Manifestacija"
        }
      },
      images: []
    }];

    service.filterCulturalOffers(filterRequest).subscribe(data => response = data);

    const req = httpMock.expectOne(path + `/search?culturalOfferName=&subTypeName=${filterRequest.subTypeName}&typeName=${filterRequest.typeName}`);
    expect(req.request.method).toBe('GET');
    req.flush(offersMock);

    expect(response.length).toBe(1);
    expect(response[0].id).toBe(1);
    expect(response[0].description).toBe("neki opis");
    expect(response[0].name).toBe("kulturna ponuda");
    expect(response[0].location.address).toBe("Novi Sad");
    expect(response[0].location.latitude).toBe(20);
    expect(response[0].location.longitude).toBe(20);
    expect(response[0].subType.id).toBe(1);
    expect(response[0].subType.name).toBe("Koncert");
    expect(response[0].subType.type.id).toBe(1);
    expect(response[0].subType.type.name).toBe("Manifestacija");
  }));

  it('getMapboxLocations() should return map location for given address', fakeAsync( () => {
    let location: Geocoder = {
      query: [],
      features: [{
        text: "",
        place_name: "",
        bbox: [],
        center: []
      }
    ]
    };

    let mockLocation: Geocoder = {
      query: [],
      features: [{
        text: "Neki tekst",
        place_name: "Novi Sad",
        bbox: [20,20,20,20],
        center: [20,20]
      }]
    };
    let mapPath = `https://api.mapbox.com/geocoding/v5/mapbox.places/
    Novi Sad.json?access_token=${environment.mapboxKey}`;

    service.getMapboxLocations("Novi Sad").subscribe(data => location = data);

    const req = httpMock.expectOne(mapPath);
    expect(req.request.method).toBe('GET');
    req.flush(mockLocation);

    tick();

    expect(location.features[0].text).toBe("Neki tekst");
    expect(location.features[0].place_name).toBe("Novi Sad");
    expect(location.features[0].bbox).toEqual([20,20,20,20]);
    expect(location.features[0].center).toEqual([20,20]);
  }));

});
