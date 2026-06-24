import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEyeSlash, faEye } from '@fortawesome/free-solid-svg-icons';
import * as Yup from 'yup';

type LoginDto = {
  username: string;
  password: string;
};

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: true,
  imports: [FontAwesomeModule, ReactiveFormsModule],
})
export class LoginComponent {
  usernameError = '';
  passwordError = '';
  error = '';
  hidePassword = true;
  errFlag = false;

  faEye = faEye;
  faEyeSlash = faEyeSlash;

  constructor(private http: HttpClient, private router: Router) {}

  fg = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  login() {
    this.errFlag = !this.errFlag;
    if (this.fg.get('username')!.invalid) {
      this.usernameError = 'Enter username';
      this.errFlag = !this.errFlag;
    }
    if(this.fg.get('password')!.invalid){
      this.passwordError = 'Enter password';
      this.errFlag = !this.errFlag;
    }
    if(this.errFlag){
      return
    }

    const dto = this.fg.value as LoginDto;

    this.http.post('/api/login', dto).subscribe({
      next: () => this.router.navigate(['/home']),
      error: () => (this.error = 'Login failed'),
    });
  }

  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }
}