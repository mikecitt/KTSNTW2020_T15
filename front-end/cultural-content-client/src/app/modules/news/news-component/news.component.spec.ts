import { UserService } from './../../../services/user/user.service';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { MatDialog } from '@angular/material/dialog';
import { SubscriptionService } from './../../../services/subscription/subsription.service';
import { NewsService } from './../../../services/news/news.service';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsComponent } from './news.component';
import { of } from 'rxjs';

describe('NewsComponent', () => {
  let component: NewsComponent;
  let fixture: ComponentFixture<NewsComponent>;
  let newsService: NewsService;
  let subService: SubscriptionService;
  let dialog: MatDialog;
  let snackBar: SnackBarComponent;
  let userService: UserService;

  beforeEach(async () => {

    const userServiceMock = {
      getRole: jasmine.createSpy('getRole').and.returnValue('ROLE_USER')
    };

    const subServiceMock = {
      isSubscribed: jasmine.createSpy('isSubscribed').and.returnValue(of(true)),
      subscribeToOffer: jasmine.createSpy('subscribeToOffer').and.returnValue(of({})),
      unsubscribeFromOffer: jasmine.createSpy('unsubscribeFromOffer').and.returnValue(of({})),
    };

    const newsServiceMock = {
      getAll : jasmine.createSpy('getAll').and.returnValue(of({
          content: [
              {
                  id: 1001,
                  text: 'Najnovija vest o festivalu',
                  date: '2017-01-13T17:09:42.411',
                  images: []
              },
              {
                  id: 1002,
                  text: 'Festival je odlozen do daljnjeg',
                  date: '2018-02-14T15:09:42.411',
                  images: []
              }
          ],
          pageable: {},
          totalElements: 2,
          last: true,
          totalPages: 1,
      })),
      deleteNews : jasmine.createSpy('deleteNews').and.returnValue(of({})),
      addNews: jasmine.createSpy('addNews').and.returnValue(of({})),
      updateNews: jasmine.createSpy('updateNews').and.returnValue(of({}))
    };

    const dialogMock = {
      open : jasmine.createSpy('open').and.returnValue({
        afterClosed : jasmine.createSpy('afterClosed').and.returnValue( of({operation: 'add'}) ), close: null
       }),
    };

    const snackBarMock = {
      openSnackBar: jasmine.createSpy('openSnackBar')
    };

    await TestBed.configureTestingModule({
      declarations: [ NewsComponent ],
      providers: [{provide: UserService, useValue: userServiceMock},
                  {provide: SubscriptionService, useValue: subServiceMock},
                  {provide: SnackBarComponent, useValue: snackBarMock},
                  {provide: MatDialog, useValue: dialogMock},
                  {provide: NewsService, useValue: newsServiceMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    userService = TestBed.inject(UserService);
    subService = TestBed.inject(SubscriptionService);
    newsService = TestBed.inject(NewsService);
    dialog = TestBed.inject(MatDialog);
    snackBar = TestBed.inject(SnackBarComponent);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should define isSubscribed', async () => {
    component.ngOnInit();
    expect(component.isSubscribed).toBeDefined();
  });

  it('should call loadNews', async () => {
    component.ngOnChanges();
    expect(newsService.getAll).toHaveBeenCalled();
  });

  it('should delete news', async () => {
    spyOn(component, 'loadNews');
    component.deleteNews(1001);
    fixture.detectChanges();
    expect(dialog.open).toHaveBeenCalled();
    fixture.detectChanges();
    expect(newsService.deleteNews).toHaveBeenCalled();
    expect(component.loadNews).toHaveBeenCalled();
    expect(snackBar.openSnackBar).toHaveBeenCalledWith('News deleted successfully', '', 'green-snackbar');
  });

  it('should subscribe', async () => {
    component.subscribe();
    expect(subService.subscribeToOffer).toHaveBeenCalled();
    fixture.detectChanges();
    expect(component.isSubscribed).toBeTruthy();
    expect(snackBar.openSnackBar).toHaveBeenCalledWith('Successfully subscribed', '', 'green-snackbar');

  });

  it('should unsubscribe', async () => {
    component.unsubscribe();
    expect(subService.unsubscribeFromOffer).toHaveBeenCalled();
    fixture.detectChanges();
    expect(component.isSubscribed).toBeFalsy();
    expect(snackBar.openSnackBar).toHaveBeenCalledWith('Successfully unsubscribed', '', 'green-snackbar');

  });

  it('should go to next page', async () => {
    spyOn(component, 'loadNews');
    component.currentNewsPage = 1;
    component.getNextNews();
    expect(component.currentNewsPage).toBe(2);
    expect(component.loadNews).toHaveBeenCalled();
  });

  it('should go to previous page', async () => {
    spyOn(component, 'loadNews');
    component.currentNewsPage = 2;
    component.getPreviousNews();
    expect(component.currentNewsPage).toBe(1);
    expect(component.loadNews).toHaveBeenCalled();
  });

  it('should open create news dialog and call add news', async () => {
    spyOn(component, 'addNews');
    component.openCreateNewsDialog();
    expect(dialog.open).toHaveBeenCalled();
    fixture.detectChanges();
    expect(component.addNews).toHaveBeenCalled();
  });

  it('should add news', async () => {
    spyOn(component, 'loadNews');
    spyOn(component, 'clearNewsForm');
    component.addNews();
    expect(newsService.addNews).toHaveBeenCalled();
    expect(component.loadNews).toHaveBeenCalled();
    expect(component.clearNewsForm).toHaveBeenCalled();
    expect(snackBar.openSnackBar).toHaveBeenCalledWith('News successfully added', '', 'green-snackbar');

  });

  it('should clear news form', async () => {
    const expected = {
      text: '',
      date: new Date(),
      images: []
    };
    component.clearNewsForm();
    try {
      expect(component.newsToAdd).toEqual(expected);
    } catch (error) {

    }

  });

  it('should open update news dialog', async () => {
    const news = {
      text: '',
      date: new Date(),
      images: []
    };
    spyOn(component, 'updateNews');
    component.openUpdateNewsDialog(news);
    expect(dialog.open).toHaveBeenCalled();
  });

  it('should update news', async () => {
    const news = {
      text: '',
      date: new Date(),
      images: []
    };
    spyOn(component, 'loadNews');
    component.updateNews(news, news);
    expect(newsService.updateNews).toHaveBeenCalled();
    expect(component.loadNews).toHaveBeenCalled();
    expect(snackBar.openSnackBar).toHaveBeenCalledWith('News successfully updated', '', 'green-snackbar');
  });
});
