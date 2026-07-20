import { Component, inject, input, output} from '@angular/core';
import { AuthService } from '../shared/services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faBars, faX, faXmark } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'sidebar-component',
  standalone: true,
  imports: [ CommonModule, FontAwesomeModule],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.css',],
})
export class SidebarComponent {
    isSidebarCollapsed = input.required<boolean>();
    changesIsSidebarCollapsed = output<boolean>();

    menuIcon = faBars;
    closeIcon = faXmark;
    
    private readonly authService = inject(AuthService);
    private readonly router = inject(Router);
    

    logout(){
      console.log("Logging out...");
      this.authService.logout();
      this.router.navigate(['/login']);
    }
    

    toggleSidebar(){
      this.changesIsSidebarCollapsed.emit(!this.isSidebarCollapsed());
    }
}
