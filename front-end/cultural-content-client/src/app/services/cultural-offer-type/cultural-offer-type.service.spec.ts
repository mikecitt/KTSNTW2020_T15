import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';

import { CulturalOfferTypeService } from './cultural-offer-type.service';
import { CulturalOfferType } from 'src/app/models/cultural-offer-type';
import { TypePage } from 'src/app/models/type-page';

describe('CulturalOfferTypeService', () => {
  let injector;
  let typeService: CulturalOfferTypeService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  const path = 'http://localhost:8080/api';

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
        name: 'tip1'
      },
      {
        id: 2,
        name: 'tip2'
      }];

    typeService.getAll().subscribe(data => {types = data; });

    const req = httpMock.expectOne(path + '/types/all');
    expect(req.request.method).toBe('GET');
    req.flush(mockTypes);

    tick();

    expect(types.length).toEqual(2, 'should contain given amount of types');
    expect(types[0].id).toEqual(1);
    expect(types[0].name).toEqual('tip1');

    expect(types[1].id).toEqual(2);
    expect(types[1].name).toEqual('tip2');

  }));

  it('GetAllPaginated() should return types', fakeAsync( () => {
    let typePage: TypePage = {
      content: [],
      first: false,
      last: false,
      totalPages: 0,
      totalElements: 0
    };
    const mockTypes: TypePage = {
      content: [{
        id: 1,
        name: 'tip1'
      },
      {
        id: 2,
        name: 'tip2'
      },
      {
        id: 3,
        name: 'tip3'
      }],
      first: true,
      last: true,
      totalElements: 3,
      totalPages: 1
    };

    typeService.getAllPaginated(0, 3).subscribe(data => {typePage = data; });

    const req = httpMock.expectOne(path + '/types?page=0&size=3');
    expect(req.request.method).toBe('GET');
    req.flush(mockTypes);

    tick();

    expect(typePage.content.length).toEqual(3, 'should contain given amount of types');
    expect(typePage.totalElements).toBe(3);
    expect(typePage.totalPages).toBe(1);
    expect(typePage.content[0].id).toEqual(1);
    expect(typePage.content[0].name).toEqual('tip1');

    expect(typePage.content[1].id).toEqual(2);
    expect(typePage.content[1].name).toEqual('tip2');

    expect(typePage.content[2].id).toEqual(3);
    expect(typePage.content[2].name).toEqual('tip3');
  }));

  it('saveType() should save a type', fakeAsync( () => {
    const newType: CulturalOfferType = {
      id: 0,
      name: 'tip1'
    };

    let typeResponse: CulturalOfferType = {
      id: 0,
      name: ''
    };

    const mockType: CulturalOfferType = {
      id: 1,
      name: 'tip1'
    };

    typeService.createType(newType).subscribe(res => typeResponse = res);

    const req = httpMock.expectOne(path + '/types');
    expect(req.request.method).toBe('POST');
    req.flush(mockType);

    tick();
    expect(typeResponse).toBeDefined();
    expect(typeResponse.id).toEqual(1);
    expect(typeResponse.name).toEqual('tip1');
  }));

  it('updateType() should update a type', fakeAsync( () =>  {
    const type: CulturalOfferType = {
      id: 1,
      name: 'tip1'
    };

    let updated: CulturalOfferType = {
      id: 0,
      name: ''
    };

    const mockType: CulturalOfferType = {
      id: 1,
      name: 'tip1'
    };

    typeService.updateType(type).subscribe(res => updated = res);

    const req = httpMock.expectOne(path + '/types/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockType);

    tick();

    expect(updated).toBeDefined();
    expect(updated.id).toBe(1);
    expect(updated.name).toBe('tip1');

  }));

  it('deleteType() should delete a type', fakeAsync(() => {
    typeService.deleteType(1).subscribe(res => {});

    const req = httpMock.expectOne(path + '/types/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));


});
