import { SubscriptionPage } from './../../models/subscription-page';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';

import { SubscriptionService } from '../index';

describe('SubsriptionService', () => {
  let service: SubscriptionService;
  let http: HttpTestingController;

  const path = 'http://localhost:8080/api/subscriptions';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SubscriptionService]
    });
    service = TestBed.inject(SubscriptionService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getAll and return subscriptions', fakeAsync(() =>{
    let subPage : SubscriptionPage = {
      content:[],
      first:false,
      last:false,
      totalPages:0,
      totalElements:0
    }

    let mockSubs : SubscriptionPage = {
      content:[
        { id: 1, culturalOfferName: "Ponuda 1", text: "Prva vest", date: new Date(), images: [] },
        { id: 2, culturalOfferName: "Ponuda 2", text: "Druga vest", date: new Date(), images: [] },
        { id: 3, culturalOfferName: "Ponuda 3", text: "Treca vest", date: new Date(), images: [] },

      ],
      first:true,
      last:true,
      totalPages:1,
      totalElements:3
    }

    service.getAll(0,3).subscribe(data => {subPage = data})

    const req = http.expectOne(path + '?page=0&size=3');
    expect(req.request.method).toBe('GET');
    req.flush(mockSubs);

    tick();

    expect(subPage.content.length).toEqual(3);
    expect(subPage.totalElements).toBe(3);
    expect(subPage.totalPages).toBe(1);
    expect(subPage.content[0].id).toEqual(1);
    expect(subPage.content[0].text).toEqual("Prva vest");
    expect(subPage.content[0].culturalOfferName).toEqual("Ponuda 1");

    expect(subPage.content[1].id).toEqual(2);
    expect(subPage.content[1].text).toEqual("Druga vest");
    expect(subPage.content[1].culturalOfferName).toEqual("Ponuda 2");

    expect(subPage.content[2].id).toEqual(3);
    expect(subPage.content[2].text).toEqual("Treca vest");
    expect(subPage.content[2].culturalOfferName).toEqual("Ponuda 3");
  }));

  it('should subscribe to offer', fakeAsync(() => {
    service.subscribeToOffer(1).subscribe(res => {});
    const req = http.expectOne(path + '?id=1');
    expect(req.request.method).toBe('POST');
    req.flush({});
  }));

  it('should unsubscribe from offer', fakeAsync(() => {
    service.unsubscribeFromOffer(1).subscribe(res => {});
    const req = http.expectOne(path + '?id=1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));

  it('should check if user is subscribed', fakeAsync(() => {
    let result: boolean = false;
    let mockResult : boolean = true;
    service.isSubscribed(1).subscribe(res => result = res);
    const req = http.expectOne(path + '/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockResult);
    expect(result).toBeTruthy();
  }));
});
