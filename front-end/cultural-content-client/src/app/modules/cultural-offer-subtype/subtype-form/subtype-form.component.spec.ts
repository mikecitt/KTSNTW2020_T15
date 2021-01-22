import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubtypeFormComponent } from './subtype-form.component';

describe('SubtypeFormComponent', () => {
  let component: SubtypeFormComponent;
  let fixture: ComponentFixture<SubtypeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubtypeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubtypeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
