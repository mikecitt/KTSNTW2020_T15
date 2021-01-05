import { TestBed } from '@angular/core/testing';

import { CulturalOfferService } from './cultural-offer.service';

describe('CulturalOfferService', () => {
  let service: CulturalOfferService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CulturalOfferService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
