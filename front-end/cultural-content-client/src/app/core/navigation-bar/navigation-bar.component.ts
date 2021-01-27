import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, UserService } from 'src/app/services';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss'],
})
export class NavigationBarComponent implements OnInit {
  constructor(
    private router: Router,
    private userService: UserService,
    private authService: AuthService
  ) {}

  role: string;

  ngOnInit(): void {
    this.role = this.userService.getRole();
  }

  get isAuthorized() {
    return this.userService.getRole() != null;
  }

  get isAdmin() {
    return this.userService.getRole() == 'ROLE_ADMIN';
  }

  get isUser() {
    return this.userService.getRole() == 'ROLE_USER';
  }

  get isLogin() {
    return this.router.url === '/login';
  }

  get isRegister() {
    return this.router.url === '/register';
  }

  logout() {
    this.role = null!;
    this.authService.logout();
  }
}
