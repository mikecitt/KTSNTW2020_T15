import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { CulturalOfferSubType } from 'src/app/models/culutral-offer-subType';
import { SubTypePage } from 'src/app/models/type-page';

import { CulturalOfferSubTypeService } from './cultural-offer-sub-type.service';

describe('CulturalOfferSubTypeService', () => {
  let service: CulturalOfferSubTypeService;
  let http: HttpTestingController;

  const path: string = 'http://localhost:8080/api/sub-types';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CulturalOfferSubTypeService]
    });
    service = TestBed.inject(CulturalOfferSubTypeService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all and return subtypes', fakeAsync(() => {
    let subTypes: CulturalOfferSubType[] = [];
    const mockSubTypes: CulturalOfferSubType[] = [
      {
        id: 1,
        name: "podtip1",
        type: {id: 1, name: ""}
      },
      {
        id: 2,
        name: "podtip2",
        type: {id: 1, name: ""}
      }];

    service.getAll(1).subscribe(data => {subTypes = data})

    const req = http.expectOne(path + '/all?typeId=' + 1);
    expect(req.request.method).toBe('GET');
    req.flush(mockSubTypes);

    tick();

    expect(subTypes.length).toEqual(2);
    expect(subTypes[0].id).toEqual(1);
    expect(subTypes[0].name).toEqual('podtip1');

    expect(subTypes[1].id).toEqual(2);
    expect(subTypes[1].name).toEqual('podtip2');

  }));

  it('should get paginated', fakeAsync( () => {
    let subtypePage: SubTypePage = {
      content:[],
      first:false,
      last:false,
      totalPages:0,
      totalElements:0
    };
    const mockSubTypes: SubTypePage = {
      content:[{
        id: 1,
        name: "podtip1",
        type: {id: 1, name: ""}
      },
      {
        id: 2,
        name: "podtip2",
        type: {id: 1, name: ""}
      },
      {
        id: 3,
        name: "podtip3",
        type: {id: 1, name: ""}
      }],
      first: true,
      last: true,
      totalElements: 3,
      totalPages: 1
    };

    service.getAllPaginated(0,3,1).subscribe(data => {subtypePage = data})

    const req = http.expectOne(path + '?typeId=1&page=0&size=3');
    expect(req.request.method).toBe('GET');
    req.flush(mockSubTypes);

    tick();

    expect(subtypePage.content.length).toEqual(3);
    expect(subtypePage.totalElements).toBe(3);
    expect(subtypePage.totalPages).toBe(1);
    expect(subtypePage.content[0].id).toEqual(1);
    expect(subtypePage.content[0].name).toEqual('podtip1');

    expect(subtypePage.content[1].id).toEqual(2);
    expect(subtypePage.content[1].name).toEqual('podtip2');

    expect(subtypePage.content[2].id).toEqual(3);
    expect(subtypePage.content[2].name).toEqual('podtip3');
  }));

  it('should save a subtype', fakeAsync( () => {
    let newSubType: CulturalOfferSubType = {
      id: 0,
      name: "podtip1",
      type: {id: 1, name: ''}
    };

    const mockSubType: CulturalOfferSubType = {
      id: 1,
      name: "podtip1",
      type: {id: 1, name: ''}
    };

    service.createSubType(newSubType).subscribe(res => newSubType = res);

    const req = http.expectOne(path + '?typeId=1');
    expect(req.request.method).toBe('POST');
    req.flush(mockSubType);

    tick();
    expect(newSubType).toBeDefined();
    expect(newSubType.id).toEqual(1);
    expect(newSubType.name).toEqual("podtip1");
  }));

  it('should update a subtype', fakeAsync( () =>  {
    let subtype: CulturalOfferSubType = {
      id: 1,
      name: "podtip1",
      type: {id: 1, name: ''}
    };

    const mockSubType: CulturalOfferSubType = {
      id: 1,
      name: "podtip1",
      type: {id: 1, name: ''}
    };

    service.updateSubType(subtype).subscribe(res => subtype = res);

    const req = http.expectOne(path + '/1?typeId=1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockSubType);

    tick();

    expect(subtype).toBeDefined();
    expect(subtype.id).toBe(1);
    expect(subtype.name).toBe('podtip1');

  }));

  it('should delete a subtype', fakeAsync(() =>{
    let subtype: CulturalOfferSubType = {
      id: 1,
      name: "podtip1",
      type: {id: 1, name: ''}
    };
    service.deleteSubType(subtype).subscribe(res => {});

    const req = http.expectOne(path + '/1?typeId=1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));
});
