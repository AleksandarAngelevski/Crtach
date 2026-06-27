import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEyeSlash, faEye } from '@fortawesome/free-solid-svg-icons';
import { PasswordInput } from "../shared/password-input/password-input";
import { LoginRequest } from '../models/login-request.model';
import { AuthService } from '../shared/services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: true,
  imports: [FontAwesomeModule, ReactiveFormsModule, PasswordInput],
})
export class LoginComponent {
  error = '';

  faEye = faEye;
  faEyeSlash = faEyeSlash;

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}

  fg = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(20)]),
    password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]),
  });

  login() {
    this.fg.markAllAsTouched();
    if (this.fg.invalid) return;

    const dto : LoginRequest = {
      username: this.fg.value.username!,
      password: this.fg.value.password!,
    };

    //Will implement when login endpoint is done
    // this.authService.login(dto).subscribe
    
  }

  get password() { return this.fg.get("password") as FormControl;}
  get uname() { return this.fg.get("username")!};
}