import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginationBarComponent } from './pagination-bar.component';

describe('PaginationBarComponent', () => {
  let component: PaginationBarComponent;
  let fixture: ComponentFixture<PaginationBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginationBarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginationBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate next', () => {
    spyOn(component.navigateNextEvent, 'emit');
    component.navigateNext();
    expect(component.navigateNextEvent.emit).toHaveBeenCalled();
  });

  it('should navigate previous', () => {
    spyOn(component.navigatePrevEvent, 'emit');
    component.navigatePrevious();
    expect(component.navigatePrevEvent.emit).toHaveBeenCalled();
  });
});
