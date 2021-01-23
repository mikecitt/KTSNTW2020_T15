import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapItemOverviewComponent } from './map-item-overview.component';

describe('MapItemOverviewComponent', () => {
  let component: MapItemOverviewComponent;
  let fixture: ComponentFixture<MapItemOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapItemOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapItemOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
