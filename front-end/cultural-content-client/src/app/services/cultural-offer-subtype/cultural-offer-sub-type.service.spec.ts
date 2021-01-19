import { TestBed } from '@angular/core/testing';

import { CulturalOfferSubTypeService } from './cultural-offer-sub-type.service';

describe('CulturalOfferSubTypeService', () => {
  let service: CulturalOfferSubTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CulturalOfferSubTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
