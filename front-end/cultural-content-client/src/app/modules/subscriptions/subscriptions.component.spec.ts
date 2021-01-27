import { SubscriptionService } from './../../services/subscription/subsription.service';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionsComponent } from './subscriptions.component';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';

describe('SubscriptionsComponent', () => {
  let component: SubscriptionsComponent;
  let fixture: ComponentFixture<SubscriptionsComponent>;
  let service: SubscriptionService;
  let dialog: MatDialog;

  const dialogMock = {
    close: jasmine.createSpy('close'),
      open : jasmine.createSpy('open').and.returnValue({
        afterClosed : jasmine.createSpy('afterClosed').and.returnValue( of({
          id: 1 , name: '', type: {id: 1, name:''}
        }) ), close: null
       })
  }

  const serviceMock = {
    getAll : jasmine.createSpy('getAll').and.returnValue(of({})),
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubscriptionsComponent ],
      providers: [
        {provide: MatDialog, useValue: dialogMock},
        {provide: SubscriptionService, useValue: serviceMock},
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscriptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = TestBed.inject(SubscriptionService);
    dialog = TestBed.inject(MatDialog);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get next page and load subsciptions', async () => {
    spyOn(component, 'loadSubscriptions')
    component.currentPage = 0;
    component.getNextPage();
    expect(component.loadSubscriptions).toHaveBeenCalled();
    expect(component.currentPage).toBe(1);
  });

  it('should get prev page and load subsciptions', async () => {
    spyOn(component, 'loadSubscriptions')
    component.currentPage = 1;
    component.getPreviousPage();
    expect(component.loadSubscriptions).toHaveBeenCalled();
    expect(component.currentPage).toBe(0);
  });
});
