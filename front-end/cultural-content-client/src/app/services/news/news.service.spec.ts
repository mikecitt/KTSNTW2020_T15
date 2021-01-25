import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { TestBed, fakeAsync, tick } from '@angular/core/testing';

import { NewsService } from '../index';
import { NewsPage } from 'src/app/models/news-page';
import { News } from 'src/app/models/news';

describe('NewsService', () => {
  let service: NewsService;
  let http: HttpTestingController;

  const path = 'http://localhost:8080/api/news/';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [NewsService]
    });
    service = TestBed.inject(NewsService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getAll and return news', fakeAsync(() => {
    let newsPage : NewsPage = {
      content:[],
      first:false,
      last:false,
      totalPages:0,
      totalElements:0
    }

    let mockNews : NewsPage = {
      content:[
        { id: 1, text: "Prva vest", date: new Date(), images: [] },
        { id: 2, text: "Druga vest", date: new Date(), images: [] },
        { id: 3, text: "Treca vest", date: new Date(), images: [] },

      ],
      first:true,
      last:true,
      totalPages:1,
      totalElements:3
    }

    service.getAll(1,0,3).subscribe(data => {newsPage = data})

    const req = http.expectOne(path + '1?page=0&size=3');
    expect(req.request.method).toBe('GET');
    req.flush(mockNews);

    tick();

    expect(newsPage.content.length).toEqual(3);
    expect(newsPage.totalElements).toBe(3);
    expect(newsPage.totalPages).toBe(1);
    expect(newsPage.content[0].id).toEqual(1);
    expect(newsPage.content[0].text).toEqual("Prva vest");

    expect(newsPage.content[1].id).toEqual(2);
    expect(newsPage.content[1].text).toEqual("Druga vest");

    expect(newsPage.content[2].id).toEqual(3);
    expect(newsPage.content[2].text).toEqual("Treca vest");


  }));

  it('should delete news', fakeAsync(() => {
    service.deleteNews(1).subscribe(res => {});

    const req = http.expectOne(path + '1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));

  it('should add news', fakeAsync( () => {
    let newsToAdd: News = { id: 0, text: "Prva vest", date: new Date(), images: [] };

    const mockNews: News =  { id: 1, text: "Prva vest", date: new Date(), images: [] };

    service.addNews(1,newsToAdd).subscribe(res => newsToAdd = res);

    const req = http.expectOne(path + '1');
    expect(req.request.method).toBe('POST');
    req.flush(mockNews);

    tick();
    expect(newsToAdd).toBeDefined();
    expect(newsToAdd.id).toEqual(1);
    expect(newsToAdd.text).toEqual("Prva vest");
  }));

  it('should update news', fakeAsync( () =>  {
    let newsToUpdate: News = { id: 1, text: "Prva vest", date: new Date(), images: [] };

    const mockNews: News =  { id: 1, text: "Prva vest", date: new Date(), images: [] };


    service.updateNews(newsToUpdate).subscribe(res => newsToUpdate = res);

    const req = http.expectOne(path + '1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockNews);

    tick();

    expect(newsToUpdate).toBeDefined();
    expect(newsToUpdate.id).toBe(1);
    expect(newsToUpdate.text).toBe('Prva vest');

  }));
});
