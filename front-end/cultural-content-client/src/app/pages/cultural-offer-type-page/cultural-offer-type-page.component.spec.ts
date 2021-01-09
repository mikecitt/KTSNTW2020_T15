import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferTypePageComponent } from './cultural-offer-type-page.component';

describe('CulturalOfferTypePageComponent', () => {
  let component: CulturalOfferTypePageComponent;
  let fixture: ComponentFixture<CulturalOfferTypePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferTypePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferTypePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
