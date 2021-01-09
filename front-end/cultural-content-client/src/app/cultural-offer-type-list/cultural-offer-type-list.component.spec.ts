import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferTypeListComponent } from './cultural-offer-type-list.component';

describe('CulturalOfferTypeListComponent', () => {
  let component: CulturalOfferTypeListComponent;
  let fixture: ComponentFixture<CulturalOfferTypeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferTypeListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
