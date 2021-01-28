import { LOCAL_STORAGE, StorageDecoder, StorageEncoder, StorageService, StorageTranscoder } from 'ngx-webstorage-service';
import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let storage: StorageService;

  const storageMock: StorageService = {
    has(key: string): boolean{return true; },
    get(key: string): undefined {return; },
    // get<X>(key: string, decoder: StorageDecoder<X>): X | undefined,
    set(key: string, value: string): void {},
    // set<X>(key: string, value: X, encoder: StorageEncoder<X>): void {},
    remove(key: string): void {},
    clear(): void {},
    withDefaultTranscoder<X>(transcoder: StorageTranscoder<X>): StorageService<X> {return JSON.parse('sdas') as StorageService; },
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: LOCAL_STORAGE, useValue: storageMock}
      ]
    });
    service = TestBed.inject(UserService);
    service.storage = storageMock;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set up user', () => {
    spyOn(service.storage, 'set');
    service.setupUser(null);
    expect(service.storage.set).toHaveBeenCalled();
  });
});
