import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { ReviewService, UserService } from 'src/app/services';

import { ReviewsComponent } from './reviews.component';

describe('ReviewsComponent', () => {
  let component: ReviewsComponent;
  let fixture: ComponentFixture<ReviewsComponent>;
  let userService: UserService;
  let reviewService: ReviewService;
  let snackBar: SnackBarComponent;
  let matDialog: MatDialog;

  beforeEach(async () => {

    const userServiceMock = {
      getRole: jasmine.createSpy('getRole').and.returnValue('ROLE_USER')
    };

    const reviewServiceMock = {
      getAll : jasmine.createSpy('getAll').and.returnValue(of({
        content: [
          {
              id: 1001,
              rating: 5,
              comment: 'extra',
              authorUsername: 'user123',
              images: []
          }
          ],
          pageable: {},
          last: true,
          totalPages: 1,
          totalElements: 1,
      })),

      add : jasmine.createSpy('add').and.returnValue(of({}))
    };

    const snackBarMock = {
      openSnackBar: jasmine.createSpy('openSnackBar')
    };

    const dialogMock = {
      open : jasmine.createSpy('open').and.returnValue({
        afterClosed : jasmine.createSpy('afterClosed').and.returnValue( of({operation: 'add'}) ), close: null
       }),
    };

    await TestBed.configureTestingModule({
      declarations: [ ReviewsComponent ],
      providers: [{provide: UserService, useValue: userServiceMock},
                  {provide: ReviewService, useValue: reviewServiceMock},
                  {provide: MatDialog, useValue: dialogMock},
                  {provide: SnackBarComponent, useValue: snackBarMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    userService = TestBed.inject(UserService);
    snackBar = TestBed.inject(SnackBarComponent);
    reviewService = TestBed.inject(ReviewService);
    matDialog = TestBed.inject(MatDialog);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call loadReviews', async () => {
    component.ngOnChanges();
    expect(reviewService.getAll).toHaveBeenCalled();
  });

  it('should go to next page', async () => {
    spyOn(component, 'loadReviews');
    component.currentPage = 1;
    component.getNextPage();
    expect(component.currentPage).toBe(2);
    expect(component.loadReviews).toHaveBeenCalled();
  });

  it('should go to previous page', async () => {
    spyOn(component, 'loadReviews');
    component.currentPage = 2;
    component.getPreviousPage();
    expect(component.currentPage).toBe(1);
    expect(component.loadReviews).toHaveBeenCalled();
  });

  it('should open create review dialog and call add review', async () => {
    spyOn(component, 'addReview');
    component.openDialog();
    expect(matDialog.open).toHaveBeenCalled();
    fixture.detectChanges();
    expect(component.addReview).toHaveBeenCalled();
  });
});
