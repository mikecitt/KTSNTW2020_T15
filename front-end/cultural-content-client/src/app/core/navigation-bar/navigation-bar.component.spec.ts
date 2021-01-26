import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { NavigationBarComponent } from './navigation-bar.component';

describe('NavigationBarComponent', () => {
  let component: NavigationBarComponent;
  let fixture: ComponentFixture<NavigationBarComponent>;
  let router: Router;

  beforeEach(async () => {
    const routerMock = {
    }

    await TestBed.configureTestingModule({
      declarations: [ NavigationBarComponent ],
      providers: [
        {provide: Router, useValue: routerMock}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
