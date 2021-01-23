import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCulturalOfferDialogComponent } from './new-cultural-offer-dialog.component';

describe('NewCulturalOfferDialogComponent', () => {
  let component: NewCulturalOfferDialogComponent;
  let fixture: ComponentFixture<NewCulturalOfferDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCulturalOfferDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCulturalOfferDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
