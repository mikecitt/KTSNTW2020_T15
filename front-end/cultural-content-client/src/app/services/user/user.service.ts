import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

const STORAGE_KEY = 'currentUser';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser;

  constructor(@Inject(LOCAL_STORAGE) public storage: StorageService) {
    this.currentUser = this.storage.get(STORAGE_KEY);
  }

  setupUser(user: Object | null) {
    this.currentUser = user;
    this.storage.set(STORAGE_KEY, this.currentUser);
  }

  getRole() {
    let role = null;
    if (this.currentUser) {
      role = this.currentUser.role;
    }

    return role;
  }

  getEmail() {
    let email = null;
    if (this.currentUser) {
      email = this.currentUser.email;
    }

    return email;
  }
}
