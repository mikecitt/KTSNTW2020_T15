import { TestBed } from '@angular/core/testing';

import { CulturalOfferTypeService } from './cultural-offer-type.service';

describe('CulturalOfferTypeService', () => {
  let service: CulturalOfferTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CulturalOfferTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
