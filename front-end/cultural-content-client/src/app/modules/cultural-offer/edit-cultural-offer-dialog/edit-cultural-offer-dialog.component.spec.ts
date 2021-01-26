import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCulturalOfferDialogComponent } from './edit-cultural-offer-dialog.component';

describe('EditCulturalOfferDialogComponent', () => {
  let component: EditCulturalOfferDialogComponent;
  let fixture: ComponentFixture<EditCulturalOfferDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCulturalOfferDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCulturalOfferDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
