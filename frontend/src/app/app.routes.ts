import { Routes } from '@angular/router';
import { LoginComponent } from './login/login';
import { RegisterComponent } from './register/register';
import { HomeComponent } from './home/home';
import { authGuard } from './shared/guards/auth.guard';
import { guestGuard } from './shared/guards/guest.guard';

export const routes: Routes = [
    {path: "login", component: LoginComponent, canActivate: [guestGuard]}, 
    {path: "register", component: RegisterComponent, canActivate: [guestGuard]},
    {path: "", component: HomeComponent, canActivate: [authGuard]}
];
