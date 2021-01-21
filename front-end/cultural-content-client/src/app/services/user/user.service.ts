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

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private http: HttpClient) {
    this.currentUser = this.storage.get(STORAGE_KEY);
  }

  setupUser(user: Object | null) {
    console.log(user);
    this.currentUser = user;
    this.storage.set(STORAGE_KEY, this.currentUser);
  }

  /*getMyInfo() {
    return this.http.get(`${environment.api_url}/whoami`)
      .pipe(map(user => {
        this.setupUser(user);
        return user;
      }));
  }*/

  getRole() {
    var role = null;
    if(this.currentUser) {
      role = this.currentUser['role'];
    }

    return role;
  }
}
