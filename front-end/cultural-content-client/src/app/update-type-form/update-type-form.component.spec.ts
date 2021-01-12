import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateTypeFormComponent } from './update-type-form.component';

describe('UpdateTypeFormComponent', () => {
  let component: UpdateTypeFormComponent;
  let fixture: ComponentFixture<UpdateTypeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateTypeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateTypeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
