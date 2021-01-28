import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { Review } from 'src/app/models/review';
import { ReviewPage } from 'src/app/models/review-page';

import { ReviewService } from './review.service';

describe('ReviewService', () => {
  let service: ReviewService;
  let http: HttpTestingController;

  const path = 'http://localhost:8080/api/review';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReviewService]
    });
    service = TestBed.inject(ReviewService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getAll and return reviews', fakeAsync(() => {
    let reviewPage: ReviewPage = {
      content: [],
      first: false,
      last: false,
      totalPages: 0,
      totalElements: 0
    };

    const mockReview: ReviewPage = {
      content: [
        { id: 1, authorUsername: 'Pera', comment: 'Super je, vidimo se opet!', rating: 5, images: [] },
        { id: 2, authorUsername: 'Steva', comment: 'Razocarenje totalno..', rating: 1, images: [] },
        { id: 3, authorUsername: 'Zika', comment: 'Nije lose, ali ocekivao sam vise.', rating: 3, images: [] },

      ],
      first: true,
      last: true,
      totalPages: 1,
      totalElements: 3
    };

    service.getAll(1001, 0, 3).subscribe(data => {reviewPage = data; });

    const req = http.expectOne(path + '?culturalOfferId=1001&page=0&size=3');
    expect(req.request.method).toBe('GET');
    req.flush(mockReview);

    tick();

    expect(reviewPage.content.length).toEqual(3);
    expect(reviewPage.totalElements).toBe(3);
    expect(reviewPage.totalPages).toBe(1);
    expect(reviewPage.content[0].id).toEqual(1);
    expect(reviewPage.content[0].comment).toEqual('Super je, vidimo se opet!');

    expect(reviewPage.content[1].id).toEqual(2);
    expect(reviewPage.content[1].comment).toEqual('Razocarenje totalno..');

    expect(reviewPage.content[2].id).toEqual(3);
    expect(reviewPage.content[2].comment).toEqual('Nije lose, ali ocekivao sam vise.');
  }));

  it('should add review', fakeAsync( () => {
    let reviewToAdd: Review = { id: 0, authorUsername: 'Pera', comment: 'Ekstra je stvarno!', rating: 5, images: [] };

    const mockNews: Review =  { id: 1, authorUsername: 'Pera', comment: 'Ekstra je stvarno!', rating: 5, images: [] };

    service.add(1001, reviewToAdd).subscribe(res => reviewToAdd = res);

    const req = http.expectOne(path + '?culturalOfferId=1001');
    expect(req.request.method).toBe('POST');
    req.flush(mockNews);

    tick();
    expect(reviewToAdd).toBeDefined();
    expect(reviewToAdd.id).toEqual(1);
    expect(reviewToAdd.comment).toEqual('Ekstra je stvarno!');
  }));

});
