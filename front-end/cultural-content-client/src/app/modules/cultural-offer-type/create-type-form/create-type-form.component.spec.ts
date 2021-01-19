import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTypeFormComponent } from './create-type-form.component';

describe('CreateTypeFormComponent', () => {
  let component: CreateTypeFormComponent;
  let fixture: ComponentFixture<CreateTypeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateTypeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTypeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
