import { Component, inject } from '@angular/core';
import { AuthService } from '../shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'header-component',
  standalone: true,
  imports: [],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class HeaderComponent {
   private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  logout(){
    console.log("Logging out...");
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
