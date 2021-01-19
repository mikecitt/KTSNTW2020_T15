import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapFilterFormComponent } from './map-filter-form.component';

describe('MapFilterFormComponent', () => {
  let component: MapFilterFormComponent;
  let fixture: ComponentFixture<MapFilterFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapFilterFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapFilterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
