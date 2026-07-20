import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../shared/services/auth.service';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header';
import { SidebarComponent } from '../sidebar/sidebar';

@Component({
  selector: 'app-home',
  imports: [ HeaderComponent, SidebarComponent],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class HomeComponent {
  isSidebarCollapsed = signal<boolean>(true);
  


  changeIsSideBarCollapsed(isSidebarCollapsed: boolean){
    this.isSidebarCollapsed.set(isSidebarCollapsed);
  }
}


