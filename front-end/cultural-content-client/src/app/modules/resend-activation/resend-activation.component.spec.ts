import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResendActivationComponent } from './resend-activation.component';

describe('ResendActivationComponent', () => {
  let component: ResendActivationComponent;
  let fixture: ComponentFixture<ResendActivationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResendActivationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResendActivationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
