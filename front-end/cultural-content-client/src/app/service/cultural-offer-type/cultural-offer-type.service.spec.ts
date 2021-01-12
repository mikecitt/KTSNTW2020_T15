import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';

import { CulturalOfferTypeService } from './cultural-offer-type.service';
import { CulturalOfferType } from 'src/app/model/cultural-offer-type';

describe('CulturalOfferTypeService', () => {
  let injector;
  let typeService: CulturalOfferTypeService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CulturalOfferTypeService]
    });

    injector = getTestBed();
    typeService = TestBed.inject(CulturalOfferTypeService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(typeService).toBeTruthy();
  });

  it('getAll() should return some types', fakeAsync(() => {
    let types: CulturalOfferType[] = [];
    const mockTypes: CulturalOfferType[] = [
      {
        id: 1,
        name: "tip1"
      },
      {
        id: 2,
        name: "tip2"
      }];

    typeService.getAll().subscribe(data => {types = data})

    const req = httpMock.expectOne('api/types');
    expect(req.request.method).toBe('GET');
    req.flush(mockTypes);

    tick();

    expect(types.length).toEqual(2, 'should contain given amount of types');
    expect(types[0].id).toEqual(1);
    expect(types[0].name).toEqual('tip1');

    expect(types[1].id).toEqual(2);
    expect(types[1].name).toEqual('tip2');

  }));



});
